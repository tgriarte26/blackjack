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
    private void resetHandValues(){
        playerHandValue = 0;
        dealerHandValue = 0;
    }
    private void dealCards() {
        // clears hands
        player.clear();
        dealer.clear();
        deck = new Deck();
        //shuffle
        deck.shuffle();

        //dealing cards
        player.add(deck.getCard());
        dealer.add(deck.getCard());
        player.add(deck.getCard());
        dealer.add(deck.getCard());

        resetHandValues();

        playerHandValue = calculateHandValue(player);
        dealerHandValue = calculateHandValue(dealer);
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

    private void endGame() {
        int playerValue = calculateHandValue(player);
        int dealerValue = calculateHandValue(dealer);

        System.out.println("Final scores:");
        System.out.println("Dealer's hand: " + dealerHandValue);
        System.out.println("Player's hand: " + playerHandValue);

        if (playerValue > 21) {
            System.out.println("LOSER! You went over 21. You lose $" + currentBet);
            playerBank -= currentBet;
        } else if (dealerValue > 21 || playerValue > dealerValue) {
            System.out.println("WINNER! You win $" + currentBet);
            playerBank += currentBet;
        } else if (dealerValue > playerValue) {
            System.out.println("LOSER! Dealer wins. You lose $" + currentBet);
            playerBank -= currentBet;
        } else {
            System.out.println("PUSH! It's a tie. Bet is returned.");
            // No change to player's bankroll on a tie
        }

        System.out.println("Bank: " + playerBank);
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
        while (calculateHandValue(dealer) < 17 && calculateHandValue(dealer) <= 21) {
            Card drawnCard = deck.getCard();
            dealer.add(drawnCard);
            System.out.println("Dealer draws a card: " + drawnCard);
        }
        for (int i = 1; i < dealer.size(); i++) {
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

            if (cardValue >= 11 && cardValue <= 13) {
                value += 10;
            } else if (cardValue == 14){
                value += 11;
                numAces++;
            } else {
                value += cardValue;
            }
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
            endGame();
        }
        System.out.println("Thank you for playing. You are now poor.");
    }
}