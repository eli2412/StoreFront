package Milestone239;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.Scanner;

/**
 * @author eliascruz
 *
 * @param <T>
 */
public class AdministrationService<T extends SalableProduct> {
	private static final int BUFFER_SIZE = 1024;
	static final int PORT = 13348;
     
    private NetworkServer networkServer;
    private InventoryManager<T> inventoryManager;

    public AdministrationService(StoreFront<T> storeFront) {
        this.networkServer = new NetworkServer(this);
        this.inventoryManager = storeFront.getInventoryManager();
        networkServer.startServer(); // start in background
        
    }
    
    /**
     * starts the NetworkServer on a separate thread and listens for commands from the user
     */
    public void startService() {
    	Thread serverThread = new Thread(networkServer);
        serverThread.start();
        try (Scanner scanner = new Scanner(System.in)) {
            boolean continueService = true;
            while (continueService) {
                System.out.print("Please enter a command (U/R): ");
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase("U")) {
                    processCommand(command);
                } else if (command.equalsIgnoreCase("R")) {
                    processCommand(command);
                } else {
                    System.out.println("Invalid command. Please try again.");
                }
                
                System.out.print("Continue service? (Y/N): ");
                String continueOption = scanner.nextLine();
                continueService = continueOption.equalsIgnoreCase("Y");
            }
        } catch (Exception e) {
            System.out.println("Error in Administration Service: " + e.getMessage());
        }
    }

    
    /**
     * @param command
     * add new product
     * enter the details of a new product
     */public void processCommand(String command) {
    	
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
                // Read the existing inventory from the file
                List<T> existingInventory = objectMapper.readValue(
                    new File("inventory.json"),
                    new TypeReference<List<T>>() {}
                );

                // Add the new product to the existing inventory
                existingInventory.addAll(inventoryManager.getInventory());

                // Write the updated inventory back to the file
                objectMapper.writeValue(new File("inventory.json"), existingInventory);
                System.out.println("Inventory updated successfully.");
            } catch (IOException e) {
                System.out.println("Error writing inventory to JSON file: " + e.getMessage());
            }
            
            sendCommandToServer(command, null);
        } else if (command.equalsIgnoreCase("R")) {
        	 // Process the retrieve command
            // Return the Salable Products from the Store Front Inventory in JSON format
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
                for (T product : products) {
                    inventoryManager.addProduct(product);
                }
                // Send the inventoryJson to the administration application or display it as needed
                System.out.println("New inventory retrieved successfully:\n");
                List<T> inventory = inventoryManager.getInventory();
                for (T product : inventory) {
                    System.out.println(product.getName() + " - $" + product.getPrice() + " - " + product.getQuantity() + " in stock");
                    System.out.println("Description: " + product.getDescription());
                    System.out.println("Type: " + product.getType());
                    System.out.println("-------");
                }
            } catch (IOException e) {
                System.out.println("Error serializing inventory to JSON: " + e.getMessage());
            }
        }
        
        else {
            // Invalid command
            System.out.println("Invalid command received: " + command);
        }
    }
    
    /**
     * @param command
     * @param payload json
     */
    private void sendCommandToServer(String command, String payload) {
    	int port = networkServer.getPort();
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getLocalHost();
            String requestData = command + "\n" + payload; // Include payload
            byte[] buffer = requestData.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);

            // Receive the response from the server
            byte[] responseBuffer = new byte[BUFFER_SIZE];
            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
            socket.receive(responsePacket);
            String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
            System.out.println("Server response: " + response);
        } catch (IOException e) {
            System.out.println("Error sending command to server: " + e.getMessage());
        }
    }


	/**
	 * @return json inventory
	 */
	public String getInventoryJson() {
		ObjectMapper objectMapper = new ObjectMapper();
	    try {
	        return objectMapper.writeValueAsString(inventoryManager.getInventory());
	    } catch (JsonProcessingException e) {
	        System.out.println("Error serializing inventory to JSON: " + e.getMessage());
	        return null;
	    }
	}
	public static void main(String[] args) {
    	System.out.println("Welcome Admin");
        StoreFront<SalableProduct> storeFront = new StoreFront<>();
        AdministrationService<SalableProduct> administrationService = new AdministrationService<>(storeFront);
        administrationService.startService();
       
    }
	
}
