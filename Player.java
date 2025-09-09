import java.util.ArrayList;

public class Player {
    private Hand hand;
    private int score;
    private final String name;
    private final int playerIndex;


    private ArrayList<Card> playedCards = new ArrayList<>();

    private int selectedIndex = -1;
    private boolean hasPlayedCard = false;
    private Card lastPlayedCard = null;

    public Player(String name, int playerIndex) {
        this.name = name;              
        this.playerIndex = playerIndex;
        this.score = 0;
    }
    public boolean hasOneCard() {
        return hand.size() == 1;
    }
    public boolean hasWasabi() {
        for(int i = 0;i<playedCards.size();i++) {
            if(playedCards.get(i).getType().equals("wasabi")) {
                return true;
            }
        }
        return false;
    }
    public int getIndexOfWasabi() {
        for(int i = 0;i<playedCards.size();i++) {
            if(playedCards.get(i).getType().equals("wasabi")) {
                return i;
            }
        }
        return -1;
    }
    public String getName() { return name; }
    public int getPlayerIndex() { return playerIndex; }
    public int getScore() { return score; }
    public void addScore(int delta) { score += delta; }
    public boolean returnHasPlayedCard() { return hasPlayedCard; }

    public Hand getHand() { return hand; }
    public void setHand(Hand h) { this.hand = h; }

    public ArrayList<Card> getPlayedCards() { return playedCards; }
    public Card getLastPlayedCard() { return lastPlayedCard; }
    public void hasPlayedThisTurn() { hasPlayedCard=true; }
    public void resetForNewTurn() { hasPlayedCard = false; selectedIndex = -1; }
    public int getSelectedIndex() { return selectedIndex; }

    public void selectCard(int index) {
        if (index >= 0 && index < hand.size()) {
            selectedIndex = index;
        } else {
            selectedIndex = -1;
        }
        hasPlayedThisTurn();
    }

    public void playCard() {
        if (hasPlayedCard) return;
        if (selectedIndex < 0 || selectedIndex >= hand.size()) {
            throw new IllegalStateException("No valid card selected to play.");
        }
        Card playedCard = hand.remove(selectedIndex);
        playedCards.add(playedCard);
        lastPlayedCard = playedCard;
        hasPlayedCard = true;
        selectedIndex = -1;
    }

    public boolean hasChopsticks() {
        for (Card c : playedCards) {
            if ("chopsticks".equals(c.getType())) return true;
        }
        return false;
    }

    public void useChopsticks() {
        for (int i = 0; i < playedCards.size(); i++) {
            if ("chopsticks".equals(playedCards.get(i).getType())) {
                hand.add(playedCards.remove(i));
                return;
            }
        }
        throw new IllegalStateException("No Chopsticks available to use.");
    }


    public void endRound() {
        playedCards.removeIf(c -> !"pudding".equals(c.getType()));
        hasPlayedCard = false;
        selectedIndex = -1;
        lastPlayedCard = null;
    }
    public void setHands(Hand h)
    {
        this.hand = h;
    }
    public void addPlayedCard(Card c)
    {
        playedCards.add(c);
    }
}
