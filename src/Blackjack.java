import java.util.ArrayList;
import java.util.Scanner;

public class Blackjack {
    private Deck deck;
    private ArrayList<Card> player;
    private ArrayList<Card> dealer;

    Scanner kb;

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
        player.add(deck.getCard());
        dealer.add(deck.getCard());
        player.add(deck.getCard());
        dealer.add(deck.getCard());
    }

    private void playerTurn() {
        boolean turnOver = false;
        while(!turnOver){
            int playerScore = 0;
            System.out.println("hit or stand?");
            String response = kb.nextLine();
            if (response.toLowerCase().equals("hit")) {
                player.add(deck.getCard());
                System.out.print("Player's hand:\t" + player.get(0) + " " + player.get(1));
                for (int i = 2; i < player.size(); i++) {
                    var playerHand = player.get(i);
                    System.out.print(" "+ playerHand);
                }
                System.out.println("You have ___ points");
            }
            if (response.toLowerCase().equals("stand")){
                turnOver = true;
                //dealer's turns
            }
        }
    }

    private void dealerTurn(){
        int dealerHandValue = 0;
        System.out.println("Dealer's hand:\t" + dealer.get(0) + dealer.get(1));
        for (int i = 2; i < dealer.size(); i++) {
            var dealerHand = dealer.get(i);
            System.out.print(" "+ dealerHand);
        }
        if(dealerHandValue < 17){
            dealer.add(deck.getCard());
        }
    }

    private void run() {
        deck.shuffle();
        dealCards();
        System.out.println("Dealer's hand:\t" + dealer.get(0) + " [?]");
        System.out.println("Player's hand:\t" + player.get(0) + " " + player.get(1));
        playerTurn();
        dealerTurn();
    }
}