package com.example.separateprocess;

import java.io.*;
import java.net.*;

/**
 * The ProcessPlayer1 class initializes and starts Player1 in a separate process.
 */
public class ProcessPlayer1 {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            int messageCounter = 0;
            String message = "Hello";
            while (messageCounter < 10) {
                out.println(message);
                System.out.println("Player1 sends: " + message);

                message = in.readLine();
                if (message != null) {
                    System.out.println("Player1 received: " + message);
                    messageCounter++;
                }
            }
            System.out.println("Player1 sent 10 messages and received back 10 messages.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
