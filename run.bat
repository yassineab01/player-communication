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
