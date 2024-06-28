package com.example.singleprocess;

/**
 * The SingleProcessPlayer class represents a player in a messaging system.
 * Each player can send and receive messages from another player.
 * Both players send and receive a total of 10 messages each.
 */
public class SingleProcessPlayer {
    private String name;
    private SingleProcessPlayer receiver;
    private int sentMessageCount;
    private int receivedMessageCount;
    private boolean hasPrintedFinalMessage;
    private static final int MESSAGE_LIMIT = 10;

    /**
     * Constructor for the SingleProcessPlayer class.
     * @param name the name of the player
     */
    public SingleProcessPlayer(String name) {
        this.name = name;
        this.sentMessageCount = 0;
        this.receivedMessageCount = 0;
        this.hasPrintedFinalMessage = false;
    }

    /**
     * Sets the receiver for the player.
     * @param receiver the player to whom messages will be sent
     */
    public void setReceiver(SingleProcessPlayer receiver) {
        this.receiver = receiver;
    }

    /**
     * Sends a message to another player and increments the message counter.
     * If the sent message count reaches the limit, it calls the method to print the final message.
     * @param message the message content
     */
    public void sendMessage(String message) {
        if (sentMessageCount < MESSAGE_LIMIT) {
            sentMessageCount++;
            System.out.println(name + " sends: " + message);
            receiver.receiveMessage(message);
        }
        printFinalMessage();
    }

    /**
     * Receives a message from another player, increments the message counter,
     * and sends back a concatenated message.
     * If the received message count reaches the limit, it calls the method to print the final message.
     * @param message the message content received
     */
    public void receiveMessage(String message) {
        if (receivedMessageCount < MESSAGE_LIMIT) {
            receivedMessageCount++;
            System.out.println(name + " received: " + message + " from " + receiver.name);
            sendMessage(message + " " + receivedMessageCount);
        }
        printFinalMessage();
    }

    /**
     * Prints a final message when the player has sent and received the limit number of messages.
     * This message is printed only once for each player.
     */
    public void printFinalMessage() {
        if (!hasPrintedFinalMessage && sentMessageCount == MESSAGE_LIMIT && receivedMessageCount == MESSAGE_LIMIT) {
            System.out.println(name + " sent 10 messages and received back 10 messages.");
            hasPrintedFinalMessage = true;
        }
    }
}
