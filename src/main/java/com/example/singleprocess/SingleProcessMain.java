package com.example.singleprocess;

/**
 * The SingleProcessMain class initiates the messaging between two players in the same process.
 */
public class SingleProcessMain {
    public static void main(String[] args) {
        SingleProcessPlayer player1 = new SingleProcessPlayer("player 1 (Initiator)");
        SingleProcessPlayer player2 = new SingleProcessPlayer("player 2 (Responder)");

        // Link the players
        player1.setReceiver(player2);
        player2.setReceiver(player1);

        // Start the messaging process by sending the first message from Player1 to Player2
        player1.sendMessage("Hello");
    }
}
