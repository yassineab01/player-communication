#!/bin/bash

echo "Running Single Process Mode..."
gnome-terminal -- bash -c "java -cp target/player-communication-1.0-SNAPSHOT.jar com.example.singleprocess.SingleProcessMain; exec bash"

echo "Waiting for 5 seconds..."
sleep 5

echo "Running Separate Process Mode..."
gnome-terminal -- bash -c "java -cp target/player-communication-1.0-SNAPSHOT.jar com.example.separateprocess.ProcessPlayer2; exec bash"
sleep 5
gnome-terminal -- bash -c "java -cp target/player-communication-1.0-SNAPSHOT.jar com.example.separateprocess.ProcessPlayer1; exec bash"