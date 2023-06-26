package Milestone239;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author eliascruz
 *
 */
public class StoreFront {
	private static final String STORE_NAME = "PAWN SHOP";
	private static final String LINE_SEPARATOR = "-----------------------------";
    private static final String MENU_SEPARATOR = "=============================";
	private InventoryManager inventoryManager;
    private ShoppingCart shoppingCart;

    /**
     * 
     */
    public StoreFront() {
        this.inventoryManager = new InventoryManager();
        this.shoppingCart = new ShoppingCart();
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

            List<SalableProduct> products = objectMapper.readValue(
                    new File("inventory.json"),
                    new TypeReference<List<SalableProduct>>() {}
            );
            // populate the inventory with the deserialized products.
            for (SalableProduct product : products) {
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
        System.out.println("7. Exit Store");
        System.out.println(LINE_SEPARATOR);
        System.out.print("Enter your choice: ");
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
                    System.out.println("Thank you for shopping!");
                    break;
                
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.println(MENU_SEPARATOR);
        } while (choice != 7);

        scanner.close();
    }
    /**
     * returns stock inventory
     */
    private void viewInventory() {
        System.out.println("Store Inventory:");
        System.out.println(LINE_SEPARATOR);

        List<SalableProduct> inventory = inventoryManager.getInventory();
        for (SalableProduct product : inventory) {
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

        List<SalableProduct> inventory = inventoryManager.getInventory();
        SalableProduct product = findProductByName(productName, inventory);

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

        List<SalableProduct> cartItems = shoppingCart.getCartItems();
        SalableProduct product = findProductByName(productName, cartItems);

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

        List<SalableProduct> cartItems = shoppingCart.getCartItems();
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
    private SalableProduct findProductByName(String productName, List<SalableProduct> products) {
        for (SalableProduct product : products) {
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
        List<SalableProduct> cartItems = shoppingCart.getCartItems();
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
    /**
     * @param starts code
     */
    public static void main(String[] args) {
        StoreFront storeFront = new StoreFront();
        storeFront.displayWelcomeMessage();
        storeFront.initializeStore();
        storeFront.start();
    }
}
