package Milestone239;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;



public class AdministrationService extends InventoryManager<SalableProduct> {
    private static final int PORT = 12345; // Choose a suitable port number
    
    public AdministrationService() {
        super(); // Call the constructor of the parent class (InventoryManager)
    }

    public void startService() {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String command = new String(packet.getData(), 0, packet.getLength());
                processCommand(command);
            }
        } catch (IOException e) {
            System.out.println("Error in Administration Service: " + e.getMessage());
        }
    }

    private void processCommand(String command) {
    	
    	String jSonFile = "inventory.json";
        if (command.equalsIgnoreCase("U")) {
            // Process the update command
            // Update the Store Inventory Management System with new Salable Products
            // The data payload will be a JSON string of Salable Products

            // Assuming you have received the JSON string as a separate variable called jsonData
            ObjectMapper objectMapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(SalableProduct.class, new SalableProductDeserializer());
            objectMapper.registerModule(module);

            try {
                List<SalableProduct> products = objectMapper.readValue(jSonFile, new TypeReference<List<SalableProduct>>() {});
                // Add the products to the inventory
                for (SalableProduct product : products) {
                    addProduct(product);
                }
                System.out.println("Inventory updated successfully.");
            } catch (IOException e) {
                System.out.println("Error updating inventory: " + e.getMessage());
            }

        } else if (command.equalsIgnoreCase("R")) {
            // Process the retrieve command
            // Return all of the Salable Products from the Store Inventory Management System in JSON format

            // Get the inventory
            List<SalableProduct> inventory = getInventory();

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String inventoryJson = objectMapper.writeValueAsString(inventory);
                // Send the inventoryJson to the administration application or display it as needed
                System.out.println("Inventory retrieved successfully:\n" + inventoryJson);
            } catch (JsonProcessingException e) {
                System.out.println("Error serializing inventory to JSON: " + e.getMessage());
            }

        } else {
            // Invalid command
            System.out.println("Invalid command received: " + command);
        }
    }


}
