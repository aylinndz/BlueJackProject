import java.io.FileWriter;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;

public class Aylin {
    private int playerGameWon;
    private int computerGameWon;

    private Card[] gameDeck;
    private int gameDeckIndex;
    private Card[] playerDeck;
    private Card[] playerBoard;
    private Card[] computerDeck;
    private Card[] computerBoard;
}

public Aylin() {
    makeDecks("gameDeck");
    makeDecks("player");
    makeDecks("computer");

    playerGameWon = 0;
    computerGameWon = 0;

    boolean a = true;
    while (a) {
        a = myLoop();
    }

    String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    appendFile("history.txt", "Player:" + playerGameWon + " - " + "Computer:" + computerGameWon + ", " + date);
    String[] file = readFile("history.txt").split("\n");
    if (file.length == 11) {
        StringBuilder formatFile = new StringBuilder();
        for (int i = 1; i < file.length; i++) {
            formatFile.append(file[i]).append("\n");
        }
        writeFile("history.txt", formatFile.toString());
    }
}
