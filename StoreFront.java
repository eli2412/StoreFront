package Milestone239;

import java.util.List;
import java.util.Scanner;

/**
 * @author eliascruz
 *
 */
public class StoreFront {
	private static final String STORE_NAME = "PAWN SHOP";
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

        inventoryManager.addProduct(weapon1);
        inventoryManager.addProduct(weapon2);
        inventoryManager.addProduct(armor1);
        inventoryManager.addProduct(armor2);
        inventoryManager.addProduct(health1);
    }
    
    /**
     * welcome message
     */
    public void displayWelcomeMessage() {
        System.out.println("Welcome to " + STORE_NAME);
    }
    
    /**
     * Options for user
     */
    public void displayMenu() {
        System.out.println("\nActions:");
        System.out.println("1. View Inventory");
        System.out.println("2. Add Item to Cart");
        System.out.println("3. Remove Item from Cart");
        System.out.println("4. Purchase Items");
        System.out.println("5. Cancel Purchase");
        System.out.println("0. Exit");
    }

    /**
     * @param choices
     */
    public void executeAction(int choice) {
        switch (choice) {
            case 1:
                displayInventory();
                break;
            case 2:
                addItemToCart();
                break;
            case 3:
                removeItemFromCart();
                break;
            case 4:
                purchaseItems();
                break;
            case 5:
                cancelPurchase();
                break;
            case 0:
                System.out.println("Thank you for coming to PAWN SHOP. Goodbye!");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    /**
     * display inventory of items
     */
    public void displayInventory() {
        List<SalableProduct> inventory = inventoryManager.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("The inventory is empty.");
        } else {
            System.out.println("Inventory:");
            for (SalableProduct product : inventory) {
                System.out.println(product.getName() + " - $" + product.getPrice() + " (Quantity: " + product.getQuantity() + ")");
            }
        }
    }
    
    /**
     * items selected added to cart
     */
    public void addItemToCart() {
        Scanner scanner = new Scanner(System.in);
        displayInventory();
        System.out.print("Enter the name of the product to add to the cart: ");
        String productName = scanner.nextLine();

        SalableProduct product = inventoryManager.findProductByName(productName);
        if (product == null) {
            System.out.println("Product not found: " + productName);
        } else {
            shoppingCart.addItem(product);
            System.out.println("Item added to cart: " + product.getName());
        }
    }

    /**
     * checks for items in cart and selects item for removal
     */
    public void removeItemFromCart() {
        Scanner scanner = new Scanner(System.in);
        List<SalableProduct> cartItems = shoppingCart.getCartItems();
        if (cartItems.isEmpty()) {
            System.out.println("The cart is empty.");
        } else {
            System.out.println("Cart Items:");
            for (int i = 0; i < cartItems.size(); i++) {
                SalableProduct product = cartItems.get(i);
                System.out.println((i + 1) + ". " + product.getName());
            }
            System.out.print("Enter the number of the item to remove from the cart: ");
            int itemNumber = scanner.nextInt();

            if (itemNumber < 1 || itemNumber > cartItems.size()) {
                System.out.println("Invalid item number.");
            } else {
                SalableProduct product = cartItems.get(itemNumber - 1);
                shoppingCart.removeItem(product);
                System.out.println("Item removed from cart: " + product.getName());
            }
        }
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
     * checks for items and cancels purchase in cart
     */
    public void cancelPurchase() {
        List<SalableProduct> cartItems = shoppingCart.getCartItems();
        if (cartItems.isEmpty()) {
            System.out.println("The cart is empty. Nothing to cancel.");
        } else {
            System.out.println("Canceled Items:");
            for (SalableProduct product : cartItems) {
                System.out.println(product.getName() + " - $" + product.getPrice());
                inventoryManager.addProduct(product);
            }
            cartItems.clear();
            System.out.println("Purchase canceled successfully.");
        }
    }
    /*
     * runs programs 
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        displayWelcomeMessage();
        initializeStore();

        while (true) {
            displayMenu();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            executeAction(choice);
        }
    }

    public static void main(String[] args) {
        StoreFront storeFront = new StoreFront();
        storeFront.start();
    }
}
