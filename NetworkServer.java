package Milestone239;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetworkServer implements Runnable {
    private static final int BUFFER_SIZE = 1024;
    private static final int TIMEOUT = 50000; // Timeout in milliseconds
    static final int PORT = 13348;

    private AdministrationService administrationService;
    private DatagramSocket socket;
    private boolean running;

    public NetworkServer(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    public int getPort() {
        return PORT;
    }
    
    public void startServer() {
        Thread serverThread = new Thread(this);
        serverThread.start();
    }
    

    @Override
    public void run() {
        try {
            socket = new DatagramSocket(PORT);
            socket.setSoTimeout(TIMEOUT);
            running = true;
            System.out.println("Server started. Listening on port " + PORT);
            while (running) {
                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String receivedData = new String(packet.getData(), 0, packet.getLength());
                processReceivedData(receivedData, packet.getAddress(), packet.getPort());
            }
        } catch (IOException e) {
            System.out.println("Error in Network Server: " + e.getMessage());
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    private void processReceivedData(String receivedData, InetAddress clientAddress, int clientPort) {
        Thread processThread = new Thread(() -> {
            String[] parts = receivedData.split("\n", 2);
            String command = parts[0];
            String payload = parts.length > 1 ? parts[1] : null;
            System.out.println("Received payload: " + payload);
            administrationService.processCommand(command);

            // Send the response back to the client
            String response = "Command processed: " + command;
            byte[] responseData = response.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length,
                    clientAddress, clientPort);
            try {
                socket.send(responsePacket);
                String inventoryJson = administrationService.getInventoryJson();
                System.out.println("Response payload: " + inventoryJson);
            } catch (IOException e) {
                System.out.println("Error sending response to client: " + e.getMessage());
            }
        });
        processThread.start();
    }
}
