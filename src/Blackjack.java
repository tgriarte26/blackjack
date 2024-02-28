import java.util.ArrayList;
import java.util.Scanner;

public class Blackjack {
    private Deck deck;
    private ArrayList<Card> player;
    private ArrayList<Card> dealer;

    Scanner kb;

    public Blackjack(){
        deck = new Deck();
        player = new ArrayList<>();
        dealer = new ArrayList<>();
        kb = new Scanner(System.in);
    }
    public static void main(String[] args){
        Blackjack game = new Blackjack();
        game.run();
    }

    private void dealCards(){
        player.add(deck.getCard());
        dealer.add(deck.getCard());
        player.add(deck.getCard());
        dealer.add(deck.getCard());
    }

    private void playerTurn(){
    }
    private void run() {
        deck.shuffle();
        dealCards();
        System.out.println("Dealer's hand:\t"+dealer.get(0)+" [?]");
        System.out.println("Player's hand:\t"+player.get(0)+" "+player.get(1));
        System.out.println();
        System.out.println("hit or stand?");
        String response = kb.nextLine();
        while(response.toLowerCase().equals("hit")){
            player.add(deck.getCard());
            for(int i = 2; i < player.size(); i++){
                System.out.println(player.get(i));
            }
        }

    }
}
