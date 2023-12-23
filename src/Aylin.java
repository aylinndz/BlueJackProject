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

public boolean myLoop() {

    boolean doNotCheck = false;

    boolean playerWantCard = true;
    boolean computerWantCard = true;

    playerBoard = new Card[9];
    int playerBoardIndex = 0;
    computerBoard = new Card[9];
    int computerBoardIndex = 0;

    while (true) {
        if (!playerWantCard && !computerWantCard) {
            break;
        }

        if (playerWantCard) {
            playerBoard[playerBoardIndex] = gameDeck[gameDeckIndex];
            gameDeck[gameDeckIndex] = null;
            gameDeckIndex++;
            playerBoardIndex++;

            if (lengthBoard(playerBoard)) {
                if (sumOfCards(playerBoard) <= 20) {
                    System.out.println("Player won.");
                    playerGameWon++;
                } else {
                    System.out.println("Player Busted.");
                    computerGameWon++;
                }
                doNotCheck = true;
                break;
            }

            Scanner scanner = new Scanner(System.in);
            while (true) {
                printBoard();
                System.out.println("1-End, 2-Stand, 3-Pick Card");
                String userEntry = scanner.nextLine();
                int userChoice;
                try {
                    userChoice = Integer.parseInt(userEntry);
                } catch (Exception e) {
                    System.out.println("Wrong input.");
                    continue;
                }
                if (userChoice == 1) {
                    break;
                } else if (userChoice == 2) {
                    playerWantCard = false;
                    break;
                } else {
                    System.out.println("Enter the index of card you want to play: ");
                    userEntry = scanner.nextLine();
                    try {
                        userChoice = Integer.parseInt(userEntry);
                    } catch (Exception e) {
                        System.out.println("Wrong input.");
                        continue;
                    }
                    if (userChoice > 4 || userChoice < 1) {
                        System.out.println("Wrong input.");
                        continue;
                    }
                    Card card = playerDeck[userChoice - 1];
                    if (card == null) {
                        System.out.println("You played that card. Pick another.");
                        continue;
                    }

