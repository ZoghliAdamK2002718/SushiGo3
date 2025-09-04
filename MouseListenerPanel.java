import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.Timer;
import javax.swing.JPanel;

public class MouseListenerPanel extends JPanel implements MouseListener {
    private ArrayList<Hand> hands;
    private Deck gameDeck;
    private int players;
    private int playerturn;
    private int round;
    private int cardSelected;
    private ArrayList<Integer> playerScores;
    private boolean cardClicked;
    private boolean roundOver;
    private boolean gameOver;
    private BufferedImage tableImage;
    

}
