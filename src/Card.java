public class Card {
    private boolean flipCard = false;
    private boolean doubleCard = false;

    private int number;
    private String colour;

    public Card(boolean flip_card, boolean double_card) {
        flipCard = flip_card;
        doubleCard = double_card;
    }
    public Card(int num, String color){
        number = num;
        colour = color;
    }
    public int getNumber() {
        if (flipCard) {
            return 0;
        }
        if (doubleCard) {
            return 0;
        }
        return number;
    }

    public String getColour() {
        if (flipCard) {
            return "Flip Card";
        }
        if (doubleCard) {
            return "Double Card";
        }
        return colour;
    }

}