# Player Communication Project

## Overview

This project demonstrates inter-process communication between two players using Java. There are two modes:
1. **Single Process Mode**: Both players run in the same Java process.
2. **Separate Process Mode**: Each player runs in a separate Java process with different PIDs.

## Project Structure

```
player-communication/
├── pom.xml
├── run.bat
├── run.sh
└── src/
    └── main/
        └── java/
            └── com/
                ├── example/
                │   ├── separateprocess/
                │   │   ├── ProcessPlayer1.java
                │   │   ├── ProcessPlayer2.java
                │   ├── singleprocess/
                │       ├── SingleProcessMain.java
                │       ├── Player.java
```

## Setup

### Prerequisites

- Java 8 or higher
- Maven 3.6 or higher


### Building the Project

Use Maven to clean and install the project:

```sh
mvn clean install
```

## Running the Project

### Single Process Mode

In this mode, both players run in the same Java process.

#### On Windows

1. Ensure you are in the project root directory.
2. Run the `run.bat` script:

```sh
.\run.bat
```

#### On Mac or Linux

1. Ensure you are in the project root directory.
2. Make the `run.sh` script executable:

```sh
chmod +x run.sh
```

3. Run the `run.sh` script:

```sh
./run.sh
```

This script will:
- Compile the project using Maven.
- Start the `SingleProcessMain` class in a new terminal window.

### Separate Process Mode

In this mode, each player runs in a separate Java process.

#### On Windows

1. Ensure you are in the project root directory.
2. Run the `run.bat` script:

```sh
.\run.bat
```

#### On Mac or Linux

1. Ensure you are in the project root directory.
2. Make the `run.sh` script executable:

```sh
chmod +x run.sh
```

3. Run the `run.sh` script:

```sh
./run.sh
```

This script will:
- Compile the project using Maven.
- Start `ProcessPlayer2` in a new terminal window.
- Wait for 5 seconds.
- Start `ProcessPlayer1` in another new terminal window.

## Project Details

### SingleProcessMain.java

This class initiates the messaging between two players in the same process.

```java
package com.example.singleprocess;

/**
 * The SingleProcessMain class initiates the messaging between two players in the same process.
 */
public class SingleProcessMain {
    public static void main(String[] args) {
        Player player1 = new Player("Player1(initiator)");
        Player player2 = new Player("Player2");

        // Start the messaging process by sending the first message from Player1 to Player2
        player1.sendMessage(player2, "Hello");
    }
}
```

### Player.java

This class represents a player who can send and receive messages.

```java
package com.example.singleprocess;

/**
 * The Player class represents a player in a messaging system.
 * Each player can send and receive messages from another player.
 * Both players send and receive a total of 10 messages each.
 */
public class Player {
    private String name;
    private int sentMessageCount;
    private int receivedMessageCount;
    private boolean hasPrintedFinalMessage;
    private static final int MESSAGE_LIMIT = 10;

    public Player(String name) {
        this.name = name;
        this.sentMessageCount = 0;
        this.receivedMessageCount = 0;
        this.hasPrintedFinalMessage = false;
    }

    public String getName() {
        return name;
    }

    public void sendMessage(Player receiver, String message) {
        if (sentMessageCount < MESSAGE_LIMIT) {
            sentMessageCount++;
            System.out.println(name + " sends: " + message);
            receiver.receiveMessage(this, message);
        }
        printFinalMessage();
    }

    public void receiveMessage(Player sender, String message) {
        if (receivedMessageCount < MESSAGE_LIMIT) {
            receivedMessageCount++;
            System.out.println(name + " received: " + message + " from " + sender.getName());
            sendMessage(sender, message + " " + receivedMessageCount);
        }
        printFinalMessage();
    }

    public void printFinalMessage() {
        if (!hasPrintedFinalMessage && sentMessageCount == MESSAGE_LIMIT && receivedMessageCount == MESSAGE_LIMIT) {
            System.out.println(name + " sent 10 messages and received back 10 messages.");
            hasPrintedFinalMessage = true;
        }
    }
}
```

### ProcessPlayer1.java

This class represents Player 1 running in a separate process.

```java
package com.example.separateprocess;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class represents Player 1 running in a separate process.
 */
public class ProcessPlayer1 {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            for (int i = 1; i <= 10; i++) {
                out.println("Message " + i + " from Player1");
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### ProcessPlayer2.java

This class represents Player 2 running in a separate process.

```java
package com.example.separateprocess;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class represents Player 2 running in a separate process.
 */
public class ProcessPlayer2 {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message;
            int messageCount = 0;
            while ((message = in.readLine()) != null) {
                messageCount++;
                System.out.println("Player2 received: " + message);
                if (messageCount >= 10) break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### run.sh

This script is used to compile the project and run the classes in different modes on Mac or Linux.

```sh
#!/bin/bash

echo "Running Single Process Mode..."
gnome-terminal -- bash -c "java -cp target/player-communication-1.0-SNAPSHOT.jar com.example.singleprocess.SingleProcessMain; exec bash"

echo "Waiting for 5 seconds..."
sleep 5

echo "Running Separate Process Mode..."
gnome-terminal -- bash -c "java -cp target/player-communication-1.0-SNAPSHOT.jar com.example.separateprocess.ProcessPlayer2; exec bash"
sleep 5
gnome-terminal -- bash -c "java -cp target/player-communication-1.0-SNAPSHOT.jar com.example.separateprocess.ProcessPlayer1; exec bash"
```
### run.bat

This script is used to compile the project and run the classes in different modes on Windows.

```bat
@echo off


echo Running SingleProcessMain...
start "SingleProcess Window" cmd /k "java -cp target/player-communication-1.0-SNAPSHOT.jar com.example.singleprocess.SingleProcessMain"
timeout /t 5 /nobreak > nul

echo Starting Player2...
start "Player2 Window" cmd /k "java -cp target/player-communication-1.0-SNAPSHOT.jar com.example.separateprocess.ProcessPlayer2"
timeout /t 5 /nobreak > nul

echo Starting Player1...
start "Player1 Window" cmd /k "java -cp target/player-communication-1.0-SNAPSHOT.jar com.example.separateprocess.ProcessPlayer1"

pause
```

## Notes

- Make sure to run the `run.bat` or `run.sh` script from the project root directory.
- If the terminal windows close immediately, you can add `read -p "Press enter to continue"` at the end of each command in the `run.sh` script or `pause` at the end of each Java class's `main` method for debugging purposes.
  
## Author

This project was created by Yassine Abid.
Enjoy your inter-process communication project in Java!
```

This `Documentation` file now includes instructions for running the project on both Windows and Mac/Linux, covering setup, building, and execution in both single and separate process modes.
