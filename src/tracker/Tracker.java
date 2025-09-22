package tracker;

import java.io.*;
import java.net.*;
import java.util.*;

public class Tracker {
    // Map of fileName -> list of peers
    private static Map<String, List<String>> fileRegistry = new HashMap<>();

    public static void main(String[] args) {
        int port = 5000;
        System.out.println("Tracker started on port " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handle peer communication
    static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            ) {
                String request = in.readLine();
                if (request == null) return;

                // Commands: REGISTER filename peerAddress | QUERY filename
                String[] parts = request.split(" ");
                String command = parts[0];

                if ("REGISTER".equalsIgnoreCase(command)) {
                    String fileName = parts[1];
                    String peerAddress = parts[2];

                    fileRegistry.putIfAbsent(fileName, new ArrayList<>());
                    fileRegistry.get(fileName).add(peerAddress);

                    out.println("File registered successfully.");
                } else if ("QUERY".equalsIgnoreCase(command)) {
                    String fileName = parts[1];
                    List<String> peers = fileRegistry.getOrDefault(fileName, new ArrayList<>());
                    out.println(peers.isEmpty() ? "File not found." : String.join(",", peers));
                } else {
                    out.println("Invalid command.");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
