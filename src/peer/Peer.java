package peer;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Peer {
    private String peerAddress;  // e.g., 127.0.0.1:6000
    private String trackerHost = "localhost";
    private int trackerPort = 5000;
    private int peerPort;

    public Peer(String peerAddress, int peerPort) {
        this.peerAddress = peerAddress;
        this.peerPort = peerPort;
    }

    // ----------------------
    // Tracker Communication
    // ----------------------
    public void registerFile(String fileName) {
        try (Socket socket = new Socket(trackerHost, trackerPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("REGISTER " + fileName + " " + peerAddress + ":" + peerPort);
            System.out.println("Tracker response: " + in.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] queryFile(String fileName) {
        try (Socket socket = new Socket(trackerHost, trackerPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("QUERY " + fileName);
            String response = in.readLine();
            System.out.println("Tracker response: " + response);
            if (response.equals("File not found.")) {
                return new String[0];
            }
            return response.split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    // ----------------------
    // Peer-to-Peer File Transfer
    // ----------------------

    // Start file server (to serve files to other peers)
    public void startFileServer() {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(peerPort)) {
                System.out.println("Peer server started on port " + peerPort);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    executor.submit(() -> handleClient(clientSocket));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleClient(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            OutputStream out = clientSocket.getOutputStream();
        ) {
            String fileName = in.readLine();
            File file = new File("shared/" + fileName);

            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                fis.close();
                System.out.println("Sent file: " + fileName);
            } else {
                out.write("File not found".getBytes());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Download file from another peer
    public void downloadFile(String peerAddress, int peerPort, String fileName) {
        try (Socket socket = new Socket(peerAddress, peerPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             InputStream in = socket.getInputStream();
             FileOutputStream fos = new FileOutputStream("downloads/" + fileName)) {

            out.println(fileName);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            System.out.println("Downloaded file: " + fileName + " from " + peerAddress + ":" + peerPort);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ----------------------
    // Main (Test Run)
    // ----------------------
   public static void main(String[] args) {
    if (args.length < 1) {
        System.out.println("Usage: java peer.Peer <port>");
        return;
    }

    int port = Integer.parseInt(args[0]);
    Peer peer = new Peer("127.0.0.1", port);

    peer.startFileServer();

    // Register file (make sure test.txt is in "shared" folder)
    peer.registerFile("test.txt");

    // Query tracker for peers with the file
    String[] peers = peer.queryFile("test.txt");

    if (peers.length > 0) {
        String[] parts = peers[0].split(":");
        String ip = parts[0];
        int peerPort = Integer.parseInt(parts[1]);
        peer.downloadFile(ip, peerPort, "test.txt");
    }
} 
}
