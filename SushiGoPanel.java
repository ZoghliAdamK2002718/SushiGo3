import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.Rectangle;
import java.awt.Graphics2D;



public class SushiGoPanel extends JPanel implements MouseListener {
	private BufferedImage dumpling;
	private BufferedImage sashimi;
	private BufferedImage tempura;
	private BufferedImage maki1;
	private BufferedImage maki2;
	private BufferedImage maki3;
	private BufferedImage wasabi;
	private BufferedImage chopsticks;
	private BufferedImage pudding;
	private BufferedImage eggn;
	private BufferedImage squidn;
	private BufferedImage salmonn;
	private BufferedImage back;
	private ArrayList<Player> players = new ArrayList<Player>();
	private Deck sushiDeck = new Deck();
	private int selectedHandIndex;
	private static final int cardWidth = 100;
	private static final int cardHeight = 140;
	private int currentPlayerIndex = 0;


	public SushiGoPanel() {
		try {
			dumpling = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_dumpling.jpg"));
			sashimi = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_sashimi.jpg"));
			tempura = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_tempura.jpg"));
			maki1 = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_maki1.jpg"));
			maki2 = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_maki2.jpg"));
			maki3 = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_maki3.jpg"));
			wasabi = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_wasabi.jpg"));
			chopsticks = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_chopsticks.jpg"));
			eggn = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_eggn.jpg"));
			salmonn = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_salmonn.jpg"));
			squidn = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_squidn.jpg"));
			pudding = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_pudding.jpg"));
			back = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_back.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
			}
		addMouseListener(this);
		players.add(new Player("Alice",0));
		players.add(new Player("Bob",1));
		players.add(new Player("Charlie",2));
		players.add(new Player("Diana",3));
		for(int i = 0;i<players.size();i++)
		{
			giveHand(i);
		}
		selectedHandIndex = -1;
		

		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
    int x = e.getX(), y = e.getY();
    Hand h = players.get(currentPlayerIndex).getHand();

    for (int i = 0; i < h.size(); i++) {
        Card c = h.get(i);
        Rectangle r = c.getRectangle();
        if (r != null && r.contains(x, y)) {

            if (selectedHandIndex == i) {
                playSelectedCard(currentPlayerIndex);
				currentPlayerIndex++;
				if (currentPlayerIndex >= players.size()) {
					currentPlayerIndex = 0;
					for(Player p: players)
					{
						p.resetForNewTurn();
					}
					passCardstoLeft();
					
				}
				
            } else {
                selectedHandIndex = i;
                repaint();
            }
            break;
        }
    }
	}
	public void passCardstoLeft()
	{
		Hand temp = players.get(0).getHand();
		for(int i = 0;i<players.size()-1;i++)
		{
			players.get(i).setHand(players.get(i+1).getHand());
		}
		players.get(players.size()-1).setHand(temp);
		repaint();
	}
	public BufferedImage getCardImage(String type) {
	return switch (type) {
		case "dumpling" -> dumpling;
		case "sashimi" -> sashimi;
		case "tempura" -> tempura;
		case "maki1" -> maki1;
		case "maki2" -> maki2;
		case "maki3" -> maki3;
		case "wasabi" -> wasabi;
		case "chopsticks" -> chopsticks;
		case "pudding" -> pudding;
		case "eggn" -> eggn;
		case "salmonn" -> salmonn;
		case "squidn" -> squidn;
		case "back" -> back;
		default -> null;
	};
	}
	public void displayOtherPlayerCards(Graphics g) {
    for (int i = 0; i < players.size(); i++) {
        if (i == currentPlayerIndex) continue; // Skip current player

        Hand h = players.get(i).getHand();
        double angle = seatAngle(i);

        for (int j = 0; j < h.size(); j++) {
            Card c = h.get(j);
            BufferedImage img = getCardImage("back");

            if (img == null) continue;

            int x = 0, y = 0;

            switch (i) {
                case 1: // Right side
                    x = getWidth() - 150;
                    y = 100 + j * (cardHeight / 3);
                    break;
                case 2: // Top
                    x = getWidth() / 2 - h.size() * (cardWidth / 2) + j * (cardWidth / 2);
                    y = 50;
                    break;
                case 3: // Left side
                    x = 50;
                    y = 100 + j * (cardHeight / 3);
                    break;
            }

            drawImageRotated(g, img, x, y, cardWidth, cardHeight, angle);
        }
    }
}

	private void drawImageRotated(Graphics g, BufferedImage img,int x, int y, int w, int h, double radians) 
	{
    Graphics2D g2 = (Graphics2D) g.create();
    g2.translate(x + w/2, y + h/2);
    g2.rotate(radians);
    g2.drawImage(img, -w/2, -h/2, w, h, null);
    g2.dispose();
	}
	private double seatAngle(int seat) {
    return switch (seat) {
        case 0 -> 0.0;              
        case 1 -> Math.PI / 2;  
        case 2 -> Math.PI;           
        case 3 -> 3 * Math.PI / 2;    
        default -> 0.0;
    };
}


	public void selectCard(Graphics g,int index, int i)
	{
		Hand h = players.get(index).getHand();
			Card c = h.get(i);
			String type = c.getType();
			g.drawImage(getCardImage(type),c.setX((600+i*125)),c.setY(getHeight() - 120),cardWidth,cardHeight,null);
			c.setRectangle(c.getX(), c.getY());
	}
	public void selectCard(int playerIndex, int handIndex)
	{
    selectedHandIndex = (selectedHandIndex == handIndex) ? -1 : handIndex;
    repaint();
	}
	public void playSelectedCard(int playerIndex) 
	{
    if (selectedHandIndex < 0) return;

    Player p = players.get(playerIndex);
    Hand h = p.getHand();

    if (selectedHandIndex >= h.size()) {
        selectedHandIndex = -1;
        return;
    }

    Card played = h.remove(selectedHandIndex);

    p.addPlayedCard(played);      
    selectedHandIndex = -1;
    repaint();
	}

	

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	public void loadPlayers(String[] n)
	{
		for(int i = 0;i<4;i++)
		{
			players.add(new Player(n[i],i));
		}
	}
	public void giveHand(int playerID)
	{
		Hand tempHand = new Hand();
		for(int j = 0;j<8;j++)
		{
			tempHand.add(sushiDeck.remove(j));
		}
		players.get(playerID).setHand(tempHand);
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		displayCards(g,currentPlayerIndex);
		
		
		
	}
	public void displayCards(Graphics g,int index)
	{
		displayPlayedCards(g, index);
		displayOtherPlayerCards(g);
		g. drawString("Current Player: " + players.get(index).getName(), 50, 50);	
		Hand h = players.get(index).getHand();
		ArrayList<Card> played = players.get(index).getPlayedCards();

		for(int i = 0;i<h.size();i++)
		{
			Card c = h.get(i);
			String type = c.getType();
			int y = (i == selectedHandIndex) ? (getHeight() - 140) - 20  : getHeight() - 140;
			g.drawImage(getCardImage(type),c.setX((600+i*125)),c.setY(y),cardWidth,cardHeight,null);
			c.setRectangle(c.getX(), c.getY());
		}


	}
	public void displayPlayedCards(Graphics g,int index)
	{

		ArrayList<Card> played = players.get(index).getPlayedCards();

		for(int i = 0;i<played.size();i++)
		{
			Card c = played.get(i);
			String type = c.getType();
			int y = (i == selectedHandIndex) ? (getHeight() - 320) - 20  : getHeight() - 320;
			g.drawImage(getCardImage(type),c.setX((600+i*125)),c.setY(y),cardWidth,cardHeight,null);
			c.setRectangle(c.getX(), c.getY());
		}


	}
}

