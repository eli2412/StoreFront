package Milestone239;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author eliascruz
 *
 */
public class StoreFront<T extends SalableProduct> {
	private static final String STORE_NAME = "PAWN SHOP";
	private static final String LINE_SEPARATOR = "-----------------------------";
    private static final String MENU_SEPARATOR = "=============================";
    
  
    private InventoryManager<T> inventoryManager;
    private ShoppingCart<T> shoppingCart;
    private NetworkServer networkServer;
    private AdministrationService<T> administrationService;
    
   


    /**
     * 
     */
    public StoreFront() {
    	this.inventoryManager = new InventoryManager<>();
        this.shoppingCart = new ShoppingCart<>();
        administrationService = new AdministrationService(this);
    }

    
    
    /**
     * items add for inventory
     */
    public void initializeStore() {
        // Add initial products to the inventory
    	try {
    		// read the JSON data from a file
            ObjectMapper objectMapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(SalableProduct.class, new SalableProductDeserializer());
            objectMapper.registerModule(module);

            List<T> products = objectMapper.readValue(
                    new File("inventory.json"),
                    new TypeReference<List<T>>() {}
            );
            // populate the inventory with the deserialized products.
            for (T product : products) {
                inventoryManager.addProduct(product);
            }

            System.out.println("Store inventory initialized successfully.");
        } catch (IOException e) {
            System.out.println("Failed to initialize store inventory: " + e.getMessage());
        }
    }
    
    /**
     * welcome message
     */
    public void displayWelcomeMessage() {
        System.out.println("Welcome to " + STORE_NAME);
        System.out.println(LINE_SEPARATOR);
    }
    
    /**
     * Options for user display
     */
    public void displayMenu() {
        System.out.println("\nActions:");
        System.out.println("1. View Inventory");
        System.out.println("2. Add Item to Cart");
        System.out.println("3. Remove Item from Cart");
        System.out.println("4. View Cart");
        System.out.println("5. Empty Cart");
        System.out.println("6. Purchase Items");
        System.out.println("7. Sort Items");
        System.out.println("8. Exit Store");
        System.out.println(LINE_SEPARATOR);
        System.out.print("Enter your choice: ");
    }
    
    private void sendCommandToServer(String command) {
    	int port = networkServer.getPort();
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getLocalHost();
            byte[] buffer = command.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
        } catch (IOException e) {
            System.out.println("Error sending command to server: " + e.getMessage());
        }
    }

    /**
     * calls from specific user input
     */
    public void start() {
    	
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewInventory();
                    break;
                case 2:
                    addItemToCart();
                    break;
                case 3:
                    removeItemFromCart();
                    break;
                case 4:
                    viewCart();
                    break;
                case 5:
                    emptyCart();
                    break;
                case 6:
                	purchaseItems();
                	break;
                case 7:
                	sortItems();
                	break;
                case 8:
                    System.out.println("Thank you for shopping!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.println(MENU_SEPARATOR);
        } while (choice != 8);

        scanner.close();
    }
    /**
     * returns stock inventory
     */
    private void viewInventory() {
        System.out.println("Store Inventory:");
        System.out.println(LINE_SEPARATOR);

        List<T> inventory = inventoryManager.getInventory();
        for (T product : inventory) {
            System.out.println(product.getName() + " - $" + product.getPrice() + " - " + product.getQuantity() + " in stock");
            System.out.println("Description: " + product.getDescription());
            System.out.println("Type: " + product.getType());
            System.out.println(LINE_SEPARATOR);
        }
    }
    
    /**
     * sorting items Ascending or descending by name or price
     */
    private void sortItems()
    {
    	System.out.println("Store Inventory:");
        System.out.println(LINE_SEPARATOR);
    	
        List<T> inventory = inventoryManager.getInventory();

        // Sorting options
        System.out.println("Sort by:");
        System.out.println("1. Name (A-Z)");
        System.out.println("2. Name (Z-A)");
        System.out.println("3. Price (Low - High)");
        System.out.println("4. Price (High to Low)");
        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int sortChoice = scanner.nextInt();
        scanner.nextLine();
        System.out.println(LINE_SEPARATOR);
    	
        switch (sortChoice) {
        case 1:
            inventory.sort(Comparator.comparing(SalableProduct::getName));
            break;
        case 2:
            inventory.sort(Comparator.comparing(SalableProduct::getName).reversed());
            break;
        case 3:
            inventory.sort(Comparator.comparing(SalableProduct::getPrice));
            break;
        case 4:
            inventory.sort(Comparator.comparing(SalableProduct::getPrice).reversed());
            break;
        default:
            System.out.println("Invalid choice. Showing unsorted inventory.");
            break;
        }
         // Print the sorted inventory
            for (T product : inventory) {
                System.out.println(product.getName() + " - $" + product.getPrice() + " - " + product.getQuantity() + " in stock");
                System.out.println("Description: " + product.getDescription());
                System.out.println("Type: " + product.getType());
                System.out.println(LINE_SEPARATOR);
            
    }
    }

    /**
     * add items to cart
     */
    private void addItemToCart() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the product to add: ");
        String productName = scanner.nextLine();

        List<T> inventory = inventoryManager.getInventory();
        T product = findProductByName(productName, inventory);

        if (product != null) {
            if (product.getQuantity() > 0) {
                shoppingCart.addItem(product);
                product.setQuantity(product.getQuantity() - 1);
                System.out.println("Item added to cart.");
            } else {
                System.out.println("Sorry, the product is out of stock.");
            }
        } else {
            System.out.println("Product not found in the inventory.");
        }
    }

    /**
     * removes items from cart
     */
    private void removeItemFromCart() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the product to remove: ");
        String productName = scanner.nextLine();

        List<T> cartItems = shoppingCart.getCartItems();
        T product = findProductByName(productName, cartItems);

        if (product != null) {
            shoppingCart.removeItem(product);
            product.setQuantity(product.getQuantity() + 1);
            System.out.println("Item removed from cart.");
        } else {
            System.out.println("Product not found in the cart.");
        }
    }
    
    /**
     * returns items if in cart
     */
    private void viewCart() {
        System.out.println("Shopping Cart:");
        System.out.println(LINE_SEPARATOR);

        List<T> cartItems = shoppingCart.getCartItems();
        double totalPrice = 0;

        for (SalableProduct product : cartItems) {
            System.out.println(product.getName() + " - $" + product.getPrice());
            totalPrice += product.getPrice();
        }

        System.out.println(LINE_SEPARATOR);
        System.out.println("Total Price: $" + totalPrice);
    }
    /**
     * empties cart
     */
    private void emptyCart() {
        shoppingCart.emptyCart();
        System.out.println("Cart emptied.");
    }
    /**
     * @param productName
     * @param products
     * @return product by name
     */
    private T findProductByName(String productName, List<T> products) {
        for (T product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }

    /**
     * @param product
     * Checks for item in inventory and cart
     * Allows for purchase of item
     */
    public void purchaseItems() {
    	List<T> cartItems = shoppingCart.getCartItems();
        if (cartItems.isEmpty()) {
            System.out.println("The cart is empty. Nothing to purchase.");
        } else {
            System.out.println("Purchased Items:");
            for (SalableProduct product : cartItems) {
                System.out.println(product.getName() + " - $" + product.getPrice());
            }
            cartItems.clear();
            System.out.println("Purchase completed successfully.");
        }
    }
    public void addProductToInventory(T product) {
        inventoryManager.addProduct(product);
        // Serialize the updated inventory to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<T> inventory = inventoryManager.getInventory();
            String inventoryJson = objectMapper.writeValueAsString(inventory);
            // Write the JSON string to the inventory.json file
            Files.writeString(Paths.get("inventory.json"), inventoryJson);
            System.out.println("New product added to inventory: " + product.getName());
        } catch (IOException e) {
            System.out.println("Error writing inventory to JSON file: " + e.getMessage());
        }
    }


    
    /**
     * @param starts code
     */
    	public static void main(String[] args) {
    		StoreFront<SalableProduct> storeFront = new StoreFront<>();
            storeFront.displayWelcomeMessage();
            storeFront.initializeStore();
            
            
            storeFront.start();
    	}

    }
