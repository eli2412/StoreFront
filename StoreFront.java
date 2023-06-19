package Milestone239;


import java.util.List;
import java.util.Scanner;

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
    	Weapon weapon1 = new Weapon("Sword", "A sharp sword", 49.99, 5, 10);
        Weapon weapon2 = new Weapon("Axe", "A powerful axe", 59.99, 3, 15);
        Armor armor1 = new Armor("Helmet", "A protective helmet", 29.99, 10, 5);
        Armor armor2 = new Armor("Chestplate", "A sturdy chestplate", 39.99, 7, 8);
        Health health1 = new Health("Potion", "A healing potion", 9.99, 20, 20);
        Health health2 = new Health("Elixir", "Powerful healing elixir", 100, 10, 100);

        inventoryManager.addProduct(weapon1);
        inventoryManager.addProduct(weapon2);
        inventoryManager.addProduct(armor1);
        inventoryManager.addProduct(armor2);
        inventoryManager.addProduct(health1);
        inventoryManager.addProduct(health2);
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
