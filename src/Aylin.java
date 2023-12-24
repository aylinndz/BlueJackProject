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
                        playerBoard[playerBoardIndex] = card;
                        playerBoardIndex++;
                        playerDeck[userChoice - 1] = null;

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
                    }
                }

                if (sumOfCards(playerBoard) > 20) {
                    System.out.println("Player busted.");
                    computerGameWon++;
                    doNotCheck = true;
                    break;
                } else if (sumOfCards(playerBoard) == 20) {
                    boolean blue = true;
                    for (Card card : playerBoard) {
                        if (card == null) {
                            continue;
                        }
                        if (!card.getColour().equals("Blue")) {
                            blue = false;
                            break;
                        }
                    }
                    if (blue) {
                        System.out.println("Player have BlueJack");
                        playerGameWon += 3;
                        doNotCheck = true;
                        break;
                    }
                }
            }

            if (computerWantCard) {
                computerBoard[computerBoardIndex] = gameDeck[gameDeckIndex];
                gameDeck[gameDeckIndex] = null;
                gameDeckIndex++;
                computerBoardIndex++;

                if (lengthBoard(computerBoard)) {
                    if (sumOfCards(computerBoard) <= 20) {
                        System.out.println("Computer won.");
                        computerGameWon++;
                    } else {
                        System.out.println("Computer Busted.");
                        playerGameWon++;
                    }
                    doNotCheck = true;
                    break;
                }

                Random random = new Random();
                while (true) {
                    if (random.nextInt(2) == 0) {
                        computerWantCard = random.nextBoolean();
                        break;
                    } else {
                        int selectCard = random.nextInt(4);
                        Card card = computerDeck[selectCard];
                        if (card == null) {
                            continue;
                        }
                        computerBoard[computerBoardIndex] = card;
                        computerBoardIndex++;
                        computerDeck[selectCard] = null;

                        if (lengthBoard(computerBoard)) {
                            if (sumOfCards(computerBoard) <= 20) {
                                System.out.println("Computer won.");
                                computerGameWon++;
                            } else {
                                System.out.println("Computer Busted.");
                                playerGameWon++;
                            }
                            doNotCheck = true;
                            break;
                        }
                    }
                }
                if (sumOfCards(computerBoard) > 20) {
                    System.out.println("Computer busted.");
                    doNotCheck = true;
                    playerGameWon++;
                    break;
                } else if (sumOfCards(computerBoard) == 20) {
                    boolean blue = true;
                    for (Card card : computerBoard) {
                        if (card == null) {
                            continue;
                        }
                        if (!card.getColour().equals("Blue")) {
                            blue = false;
                            break;
                        }
                    }
                    if (blue) {
                        System.out.println("Computer have BlueJack");
                        doNotCheck = true;
                        computerGameWon += 3;
                        break;
                    }
                }

            }
        }

        if (!doNotCheck) {
            if (sumOfCards(playerBoard) > sumOfCards(computerBoard)) {
                System.out.println("Player won.");
                playerGameWon++;
            } else if (sumOfCards(playerBoard) < sumOfCards(computerBoard)) {
                System.out.println("Computer won.");
                computerGameWon++;
            } else {
                System.out.println("Draw.");
            }
        }

        if (playerGameWon >= 3) {
            System.out.println("Player took the game.");
            return false;
        }
        if (computerGameWon >= 3) {
            System.out.println("Computer took the game.");
            return false;
        }
        return true;
    }


    private void makeDecks(String deckString) {
        final int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        final String[] colours = {"Blue", "Yellow", "Red", "Green"};
        switch (deckString) {
            case "gameDeck" -> {
                gameDeck = new Card[40];
                gameDeckIndex = 0;
                for (int number : numbers) {
                    for (String colour : colours) {
                        gameDeck[gameDeckIndex] = new Card(number, colour);
                        gameDeckIndex++;
                    }
                }
                gameDeckIndex = 0;
                shuffleCards(gameDeck);
            }
            case "player" -> {
                playerDeck = new Card[4];

                Random random = new Random();

                makeDecks("gameDeck");
                Card[] myCards = new Card[10];
                myCards[0] = gameDeck[gameDeck.length - 1];
                myCards[1] = gameDeck[gameDeck.length - 2];
                myCards[2] = gameDeck[gameDeck.length - 3];
                myCards[3] = gameDeck[gameDeck.length - 4];
                myCards[4] = gameDeck[gameDeck.length - 5];

                for (int i = 0; i < 5; i++) {
                    if (i >= 3) {
                        if (random.nextInt(5) == 1) {
                            if (random.nextInt(2) == 1) {
                                myCards[5 + i] = new Card(true, false);
                            } else {
                                myCards[5 + i] = new Card(false, true);
                            }
                            continue;
                        }
                    }
                    if (random.nextInt(2) == 0) {
                        myCards[5 + i] = new Card((random.nextInt(6) + 1) * -1, colours[random.nextInt(4)]);
                    } else {
                        myCards[5 + i] = new Card((random.nextInt(6) + 1), colours[random.nextInt(4)]);
                    }
                }
                shuffleCards(myCards);

                System.arraycopy(myCards, 0, playerDeck, 0, playerDeck.length);
            }
            case "computer" -> {
                computerDeck = new Card[4];

                Random random = new Random();

                makeDecks("gameDeck");
                Card[] myCards = new Card[10];
                myCards[0] = gameDeck[0];
                myCards[1] = gameDeck[1];
                myCards[2] = gameDeck[2];
                myCards[3] = gameDeck[3];
                myCards[4] = gameDeck[4];

                for (int i = 0; i < 5; i++) {
                    if (i >= 3) {
                        if (random.nextInt(5) == 1) {
                            if (random.nextInt(2) == 1) {
                                myCards[5 + i] = new Card(true, false);
                            } else {
                                myCards[5 + i] = new Card(false, true);
                            }
                            continue;
                        }
                    }
                    if (random.nextInt(2) == 0) {
                        myCards[5 + i] = new Card((random.nextInt(6) + 1) * -1, colours[random.nextInt(4)]);
                    } else {
                        myCards[5 + i] = new Card((random.nextInt(6) + 1), colours[random.nextInt(4)]);
                    }
                }
                shuffleCards(myCards);

                System.arraycopy(myCards, 0, computerDeck, 0, computerDeck.length);
            }
        }
    }

    private void shuffleCards(Card[] cards) {
        Random rnd = new Random();
        for (int i = cards.length - 1; i > 0; i--) {
            int i1 = rnd.nextInt(i + 1);
            Card tempcard = cards[i];
            cards[i] = cards[i1];
            cards[i1] = tempcard;
        }
    }


    private int sumOfCards(Card[] cards) {
        int result = 0;
        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            if (card == null) {
                continue;
            }
            if (card.getColour().equals("Flip Card")) {
                for (int j = 1; j < 3; j++) {
                    if (i - j < 0) {
                        continue;
                    }
                    Card beforeCard = cards[i - j];
                    if (beforeCard.getColour().equals("Flip Card") || beforeCard.getColour().equals("Double Card")) {
                        continue;
                    }
                    result -= beforeCard.getNumber() * 2;
                }
            } else if (card.getColour().equals("Double Card")) {
                for (int j = 1; j < 3; j++) {
                    if (i - j < 0) {
                        continue;
                    }
                    Card beforeCard = cards[i - j];
                    if (beforeCard.getColour().equals("Flip Card") || beforeCard.getColour().equals("Double Card")) {
                        continue;
                    }
                    result += beforeCard.getNumber();
                }
            }
            result += card.getNumber();
        }
        return result;
    }


    private void printBoard() {
        System.out.println();
        System.out.println("Player: " + playerGameWon);
        System.out.println("Computer: " + computerGameWon);
        System.out.println();
        System.out.print("Computer Hand: ");
        printCard(computerDeck, false);
        System.out.print("Computer Board: ");
        printCard(computerBoard, true);

        System.out.println();
        System.out.print("Player Board: ");
        printCard(playerBoard, true);
        System.out.print("Player Hand: ");
        printCard(playerDeck, true);

    }


    private void printCard(Card[] cards, boolean show) {
        for (int i = 0; i < cards.length; i++) {
            if (!show) {
                System.out.print((i + 1) + ". X ");
                continue;
            }
            Card card = cards[i];
            if (card == null) {
                continue;
            }
            System.out.print((i + 1) + ". " + card + " ");
        }
        System.out.println();

    }

    private boolean lengthBoard(Card[] board) {
        for (Card card : board) {
            if (card == null) {
                return false;
            }
        }
        return true;
    }

    public static String readFile(String path) {
        Scanner reader = null;
        StringBuilder output = new StringBuilder();

        try {
            reader = new Scanner(Paths.get(path));

            while (reader.hasNextLine()) {
                output.append(reader.nextLine()).append("\n");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return output.toString();
    }

    public static void writeFile(String path, String text) {
        try (Formatter formatter = new Formatter(path)) {
            formatter.format(text);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void appendFile(String path, String text) {
        Formatter formatter = null;
        FileWriter fileWriter;

        try {
            fileWriter = new FileWriter(path, true);
            formatter = new Formatter(fileWriter);
            formatter.format(text + "\n");
            fileWriter.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (formatter != null) {
                formatter.close();
            }
        }
    }
}







