import java.util.ArrayList;
import java.util.Scanner;

public class Blackjack {
    private Deck deck;
    private ArrayList<Card> player;
    private ArrayList<Card> dealer;

    Scanner kb;
    int playerBank = 1000;
    int currentBet = 0;
    int playerHandValue = 0;
    int dealerHandValue = 0;
    boolean gameOver = true;

    public Blackjack() {
        deck = new Deck();
        player = new ArrayList<>();
        dealer = new ArrayList<>();
        kb = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Blackjack game = new Blackjack();
        game.run();
    }

    private void dealCards() {
        deck.shuffle();

        player.add(deck.getCard());
        dealer.add(deck.getCard());
        player.add(deck.getCard());
        dealer.add(deck.getCard());

        for(Card tempValue : player){
            playerHandValue += tempValue.getValue();
        }
        for(Card tempValue : dealer){
            dealerHandValue += tempValue.getValue();
        }
    }
    private void placeBets(){
        System.out.println("You may bet between $10 and $" + playerBank);
        do{
            currentBet = kb.nextInt();
            kb.nextLine();
            if (currentBet < 10 || currentBet > playerBank) {
                System.out.println("Invalid bet amount. Please bet between $10 and $" + playerBank);
            }
        } while (currentBet < 10 || currentBet > playerBank);

    }

    private void payBack(){
        // player loses to dealer
        if(playerHandValue > 21 || playerHandValue < dealerHandValue) {
            System.out.println("LOSER!. You lost $" + currentBet);
            playerBank -= currentBet;
        }
        // player wins against dealer
        else if(playerHandValue > dealerHandValue && playerHandValue < 21) {
            if(playerHandValue == 21) {
                currentBet *= 1.5;
                System.out.println("BLACKJACK!. You won $" + currentBet);
                playerBank *= currentBet;
            } else {
                System.out.println("WINNER!. You won $" + currentBet);
                playerBank += currentBet;
            }
        }
        // player ties with dealer
        else {
            System.out.println("PUSH!");
        }
    }

    private void playerTurn() {
        boolean turnOver = false;
        while(!turnOver){
            System.out.println("\n"+"hit or stand? ");
            String response = kb.nextLine();
            if (response.toLowerCase().equals("hit")) {
                player.add(deck.getCard());
                System.out.print("Player's hand:\t" + player.get(0) + " " + player.get(1));
                for (int i = 2; i < player.size(); i++) {
                    var playerHand = player.get(i);
                    System.out.print(" "+ playerHand);
                }
            }
            if (response.toLowerCase().equals("stand")){
                turnOver = true;
            }
        }
    }
    private void dealerTurn(){
        System.out.println("Dealer's hand:\t" + dealer.get(0) + " " + dealer.get(1));
        while (calculateHandValue(dealer) < 17) {
            dealer.add(deck.getCard());
        }
        for (int i = 2; i < dealer.size(); i++) {
            var dealerHand = dealer.get(i);
            System.out.print(" "+ dealerHand);
        }

        System.out.println();
    }

    private int calculateHandValue(ArrayList<Card> hand) {
        int value = 0;
        int numAces = 0;

        for (Card card : hand) {
            int cardValue = card.getValue();

            if (cardValue == 11) {
                numAces++;
            }

            value += cardValue;
        }

        // Handle Aces
        while (value > 21 && numAces > 0) {
            value -= 10;
            numAces--;
        }

        return value;
    }


    private void run() {
        while (playerBank > 0){
            placeBets();
            dealCards();
            System.out.println("Dealer's hand:\t" + dealer.get(0) + " [?]");
            System.out.print("Player's hand:\t" + player.get(0) + " " + player.get(1));
            playerTurn();
            dealerTurn();
            payBack();
        }
        System.out.println("Thank you for playing. You are now poor.");
    }
}