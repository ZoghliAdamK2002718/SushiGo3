import java.util.ArrayList;


public class Hand {
    ArrayList<Card> hand;
    String[] cardTypes = {"dumpling", "sashimi", "tempura", "maki1", "maki2", "maki3", "wasabi", "chopsticks", "pudding","eggn","squidn","salmonn"};
    public Hand() {
        hand = new ArrayList<Card>();
        for(int i = 0;i<7;i++)
        {
            int rand = (int)(Math.random()*cardTypes.length);
            hand.add(new Card(cardTypes[rand]));
        }
    }
    public Card remove(int index) {
        return hand.remove(index);
    }
    public boolean contains(Card card) {
        return hand.contains(card);
    }
    public int indexOf(Card card) {
        return hand.indexOf(card);
    }
    public void add(Card card) {
        hand.add(card);
    }
    public void clear()
    {
        hand.clear();
    }
    public void fill(Deck deck) {
        for(int i = 0;i<7;i++)
        {
            hand.add(deck.get(0));
            deck.remove(0);
        }
    }
    public int size() {
        return hand.size();
    }
}
