package peer;

import java.io.*;
import java.net.*;
import java.util.*;

public class Peer {
    private String peerAddress;
    private String trackerHost = "localhost";
    private int trackerPort = 5000;

    public Peer(String peerAddress) {
        this.peerAddress = peerAddress;
    }

    // Register file with tracker
    public void registerFile(String fileName) {
        try (Socket socket = new Socket(trackerHost, trackerPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("REGISTER " + fileName + " " + peerAddress);
            System.out.println("Tracker response: " + in.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Query tracker for peers with file
    public void queryFile(String fileName) {
        try (Socket socket = new Socket(trackerHost, trackerPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("QUERY " + fileName);
            System.out.println("Tracker response: " + in.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Main method to test
    public static void main(String[] args) {
        Peer peer = new Peer("127.0.0.1:6000");

        // Register files
        peer.registerFile("movie.mp4");
        peer.registerFile("song.mp3");

        // Query files
        peer.queryFile("movie.mp4");
        peer.queryFile("document.pdf");
    }
}
