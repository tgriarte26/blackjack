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

        if(playerHandValue == 21){
            System.out.println("You win!");
        }

        if(dealerHandValue == 21){
            System.out.println("You lost!");
        }
    }

    private void placeBets(){
        System.out.println("You may bet between $0 and $" + playerBank);
        currentBet = kb.nextInt();
        kb.nextLine();
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
        for (int i = 2; i < dealer.size(); i++) {
            var dealerHand = dealer.get(i);
            System.out.print(" "+ dealerHand);
        }
    }

    public boolean isGameOver(boolean state){
        if(state) {
            return true;
        } else {
            return false;
        }
    }

    private void run() {
        placeBets();
        dealCards();
        System.out.println("Dealer's hand:\t" + dealer.get(0) + " [?]");
        System.out.println("Player's hand:\t" + player.get(0) + " " + player.get(1));
        playerTurn();
        dealerTurn();
    }
}