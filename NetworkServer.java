package Milestone239;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class NetworkServer {
    private static final int PORT = 12341; // Choose a suitable port number
    private AdministrationService administrationService;

    public NetworkServer(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }
    
    public int getPort() {
        return PORT;
    }

    public void startServer() {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String command = new String(packet.getData(), 0, packet.getLength());
                administrationService.processCommand(command);
            }
        } catch (IOException e) {
            System.out.println("Error in Network Server: " + e.getMessage());
        }
    }
}
