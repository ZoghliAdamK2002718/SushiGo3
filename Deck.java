import java.util.ArrayList;
import java.util.Collections;
public class Deck {
    private ArrayList<Card> cards;
    private String[] cardTypes = {"dumpling", "sashimi", "tempura", "maki1", "maki2", "maki3", "wasabi","pudding","eggn","squidn","salmonn","chopsticks"};
    public Deck() {
        cards = new ArrayList<>();
	        for(int i = 0;i<14;i++)
            {
                cards.add(new Card("dumpling"));
            }
            for(int i = 0;i<14;i++)
            {
                cards.add(new Card("tempura"));
            }
            for(int i = 0;i<14;i++)
            {
                cards.add(new Card("sashimi"));
            }
            for(int i = 0;i<6;i++)
            {
                cards.add(new Card("maki1"));
            }
            for(int i = 0;i<12;i++)
            {
                cards.add(new Card("maki2"));
            }
            for(int i = 0;i<8;i++)
            {
                cards.add(new Card("maki3"));
            }
            for(int i = 0;i<6;i++)
            {
                cards.add(new Card("wasabi"));
            }
            for(int i = 0;i<4;i++)
            {
                cards.add(new Card("chopsticks"));
            }
            for(int i = 0;i<10;i++)
            {
                cards.add(new Card("pudding"));
            }
            for(int i = 0;i<10;i++)
            {
                cards.add(new Card("salmonn"));
            }
            for(int i = 0;i<5;i++)
            {
                cards.add(new Card("squidn"));
            }
            for(int i = 0;i<5;i++)
            {
                cards.add(new Card("eggn"));
            }

  
        Collections.shuffle(cards);


    }
    public Card remove(int index) {
        return cards.remove(index);
    }
    public Card get(int index) {
        return cards.get(index);
    }
   
   


}
