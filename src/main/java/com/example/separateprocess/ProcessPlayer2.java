package com.example.separateprocess;

import java.io.*;
import java.net.*;

/**
 * The ProcessPlayer2 class initializes and starts Player2 in a separate process.
 */
public class ProcessPlayer2 {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345);
             Socket socket = serverSocket.accept()) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            int messageCounter = 0;
            while (messageCounter < 10) {
                String message = in.readLine();
                if (message != null) {
                    System.out.println("Player2 received: " + message);
                    messageCounter++;
                    String response = message + " " + messageCounter;
                    out.println(response);
                    System.out.println("Player2 sends: " + response);
                }
            }
            System.out.println("Player2 received 10 messages and sent back 10 messages.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
