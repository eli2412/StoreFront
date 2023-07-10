package Milestone239;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class AdministrationService<T extends SalableProduct> extends InventoryManager<T>  {
	 // port number
	private static final int PORT = 12347;
    private NetworkServer networkServer;
    int port;
    private InventoryManager<T> inventoryManager;
    

    public NetworkServer getNetworkServer() {
        return networkServer;
    }
    
    public AdministrationService(StoreFront<T> storeFront) {
        super();
        this.networkServer = new NetworkServer(this);
        this.inventoryManager = new InventoryManager<>();
    }
/*
    public void startNetworkServer() {
        Thread serverThread = new Thread(networkServer::startServer);
        serverThread.setDaemon(true);
        serverThread.start();
    }
    */

    
    public void startService() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Please enter a command (U/R): ");
                String command = scanner.nextLine();

                if (command.equalsIgnoreCase("U")) {
                    // Prompt the user to enter the JSON payload for the new product
                    System.out.println("Enter the JSON payload for the new product:");
                    String payload = scanner.nextLine();
                    processCommand(command, payload);
                } else if (command.equalsIgnoreCase("R")) {
                    processCommand(command, null);
                } else {
                    System.out.println("Invalid command. Please try again.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error in Administration Service: " + e.getMessage());
        }
    }


    public void processCommand(String command, String payload) {
    	if (command.equalsIgnoreCase("U")) {
    		Scanner scanner = new Scanner(System.in);
    	    System.out.println("Enter the details of the new product:");

    	    System.out.print("Name: ");
    	    String name = scanner.nextLine();

    	    System.out.print("Description: ");
    	    String description = scanner.nextLine();

    	    System.out.print("Price: ");
    	    double price = scanner.nextDouble();

    	    System.out.print("Quantity: ");
    	    int quantity = scanner.nextInt();

    	    System.out.print("Type (armor/health/weapon): ");
    	    String type = scanner.next();

    	    // Create a new SalableProduct object with the entered details
    	    SalableProduct newProduct;
    	    if (type.equalsIgnoreCase("armor")) {
    	        System.out.print("Defense: ");
    	        int defense = scanner.nextInt();
    	        newProduct = new Armor(name, description, price, quantity, defense, type);
    	    } else if (type.equalsIgnoreCase("health")) {
    	        System.out.print("Healing Points: ");
    	        int healingPoints = scanner.nextInt();
    	        newProduct = new Health(name, description, price, quantity, healingPoints, type);
    	    } else if (type.equalsIgnoreCase("weapon")) {
    	        System.out.print("Damage: ");
    	        int damage = scanner.nextInt();
    	        newProduct = new Weapon(name, description, price, quantity, damage, type);
    	    } else {
    	        System.out.println("Invalid product type. Product not added to inventory.");
    	        return;
    	    }

    	    inventoryManager.addProduct((T) newProduct);
    	    System.out.println("New product added to inventory: " + newProduct.getName());

    	    // Write the updated inventory to the JSON file
    	    ObjectMapper objectMapper = new ObjectMapper();
    	    try {
    	        List<T> inventory = inventoryManager.getInventory();
    	        objectMapper.writeValue(new File("inventory.json"), inventory);
    	        System.out.println("Inventory updated successfully.");
    	    } catch (IOException e) {
    	        System.out.println("Error writing inventory to JSON file: " + e.getMessage());
    	    }
    	}
        
            else if (command.equalsIgnoreCase("R")) {
            // Process the retrieve command
            // Return all of the Salable Products from the Store Front Inventory in JSON format
            List<T> inventory = inventoryManager.getInventory();

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String inventoryJson = objectMapper.writeValueAsString(inventory);
                // Send the inventoryJson to the administration application or display it as needed
                System.out.println("New inventory retrieved successfully:\n" + inventoryJson);
            } catch (JsonProcessingException e) {
                System.out.println("Error serializing inventory to JSON: " + e.getMessage());
            }
        } else {
            // Invalid command
            System.out.println("Invalid command received: " + command);
        }
    }

    private void sendCommandToServer(String command, String payload) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getLocalHost();
            byte[] buffer = (command + "\n" + payload).getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, PORT);
            socket.send(packet);
            socket.close();
        } catch (IOException e) {
            System.out.println("Error sending command to server: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
    	StoreFront<SalableProduct> storeFront = new StoreFront<>();
    	AdministrationService<SalableProduct> administrationService = new AdministrationService<>(storeFront);  // Pass storeFront instance as a parameter
        // administrationService.startNetworkServer();
        administrationService.startService();
    }

    

}
