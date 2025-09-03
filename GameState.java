import java.util.ArrayList;


public class GameState
{
    private int numPlayers;
    private ArrayList<Hand> playerHands;
    private int currentPlayerIndex;
    private ArrayList<Card> discardPile;
    private Deck deck;
    private Card selectedCard;
    private boolean hasUsedChopsticks;
    private boolean isGameOver;
    private int[] playerScores;
    private int roundNumber;
    private int totalRounds = 3;
    private boolean playedCard;
   


}
