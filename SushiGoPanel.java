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
import java.awt.Color;



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
	private static final int cardWidth = 75;
	private static final int cardHeight = 125;
	private int currentPlayerIndex = 0;
	private int currentRound;


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
				c.played();
				currentPlayerIndex++;
				if (currentPlayerIndex >= players.size()) {
					currentPlayerIndex = 0;
					for(Player p: players)
					{
						p.resetForNewTurn();
					}
					passCardstoLeft();
					if(players.get(0).hasOneCard())
					{
						for(int j = 0;j<players.size();j++)
						{

						}
						System.out.println("Round over. Calculate scores and start new round.");
					}
					
				}
				
            } else {
                selectedHandIndex = i;
                repaint();
            }
            break;
        }
    }
	}
	public void calculateScores()
	{
		for(int i = 0;i<players.size();i++)
		{
			ArrayList<Card> pC = players.get(i).getPlayedCards();
			if(pC.get(i).getType().equals("maki1"))
			{

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
    int spacing = 85;

    for (int i = 0; i < players.size(); i++) {
        if (i == currentPlayerIndex) continue;

        int relativeSeat = (i - currentPlayerIndex + 4) % 4;
        Hand h = players.get(i).getHand();
        double angle = seatAngle(relativeSeat);

        BufferedImage img = getCardImage("back");
        if (img == null) continue;

        int n = h.size();

        switch (relativeSeat) {
            case 1: { 
                int totalHeight = (n - 1) * spacing + cardWidth; 
                int startY = (getHeight() - totalHeight) / 2;
                int centerX = cardHeight / 2 + 20;

                for (int j = 0; j < n; j++) {
                    int centerY = startY + j * spacing + cardWidth / 2;
                    drawImageRotated(g, img, centerX, centerY, cardWidth, cardHeight, angle);
                }
                break;
            }
            case 2: {
                int startX = getWidth() / 2 - (n * spacing) / 2;
                int centerY = cardHeight / 2 + 20;

                for (int j = 0; j < n; j++) {
                    int centerX = startX + j * spacing + cardWidth / 2;
                    drawImageRotated(g, img, centerX, centerY, cardWidth, cardHeight, angle);
                }
                break;
            }
            case 3: { 
                int totalHeight = (n - 1) * spacing + cardWidth;
                int startY = (getHeight() - totalHeight) / 2;
                int centerX = getWidth() - cardHeight / 2 - 20;

                for (int j = 0; j < n; j++) {
                    int centerY = startY + j * spacing + cardWidth / 2;
                    drawImageRotated(g, img, centerX, centerY, cardWidth, cardHeight, angle);
                }
                break;
            }
        }
    }
}

	public void displayOtherPlayerPlayedCards(Graphics g) {
    final int spacing = 85;

    for (int i = 0; i < players.size(); i++) {
        if (i == currentPlayerIndex) continue;

        int relativeSeat = (i - currentPlayerIndex + 4) % 4;
        ArrayList<Card> played = players.get(i).getPlayedCards();
        if (played.isEmpty()) continue;

        double angle = seatAngle(relativeSeat);
        String name  = players.get(i).getName();
        int n = played.size();

        switch (relativeSeat) {
            case 1: { // left
                int totalH  = (n - 1) * spacing + cardWidth;
                int startY  = (getHeight() - totalH) / 2;
                int centerX = cardHeight / 2 + 200;

                for (int j = 0; j < n; j++) {
                    int cy = startY + j * spacing + cardWidth / 2;
                    Card c = played.get(j);
                    drawImageRotated(g, getCardImage(c.getType()), centerX, cy, cardWidth, cardHeight, angle);
                }

                g.setColor(Color.WHITE);
                g.drawString(name, centerX - cardHeight / 2, startY - 10);
                break;
            }

            case 2: { // top
                int totalW  = (n - 1) * spacing + cardWidth;
                int startX  = (getWidth() - totalW) / 2;
                int centerY = cardHeight / 2 + 200;

                for (int j = 0; j < n; j++) {
                    int cx = startX + j * spacing + cardWidth / 2;
                    Card c = played.get(j);
                    drawImageRotated(g, getCardImage(c.getType()), cx, centerY, cardWidth, cardHeight, angle);
                }

                g.setColor(Color.WHITE);
                g.drawString(name, startX, centerY - cardHeight / 2 - 12);
                break;
            }

            case 3: { // right
                int totalH  = (n - 1) * spacing + cardWidth;
                int startY  = (getHeight() - totalH) / 2;
                int centerX = getWidth() - cardHeight / 2 - 200;

                for (int j = 0; j < n; j++) {
                    int cy = startY + j * spacing + cardWidth / 2;
                    Card c = played.get(j);
                    drawImageRotated(g, getCardImage(c.getType()), centerX, cy, cardWidth, cardHeight, angle);
                }

                g.setColor(Color.WHITE);
                g.drawString(name, centerX + cardHeight / 2 + 8, startY - 10);
                break;
            }
        }

        int puddCnt = players.get(i).getPuddingCards() == null ? 0 : players.get(i).getPuddingCards().size();
        drawCornerPuddingsForSeat(g, relativeSeat, puddCnt); 
    }
}

private void drawCornerPuddingsForSeat(Graphics g, int relativeSeat, int count) {
    if (count <= 0) return;

    final int MARGIN = 20;
    final double SCALE = 0.5;

    int w = (int) (cardWidth  * SCALE); 
    int h = (int) (cardHeight * SCALE); 
    int overlap = (int) (h * 0.3);
    int totalH = (count - 1) * overlap + h;

    double angle = seatAngle(relativeSeat);

    int cx, cy;
    switch (relativeSeat) {
        case 1: 
            cx = w / 2 + MARGIN;
            cy = MARGIN + totalH / 2;
            break;

        case 2: 
            cx = getWidth() - w / 2 - MARGIN;
            cy = MARGIN + totalH / 2;
            break;

        case 3: 
            int pushLeft = h + 24;
            cx = getWidth() - w / 2 - MARGIN - pushLeft;
            cy = getHeight() - MARGIN - totalH / 2;
            break;

        default:
            return;
    }

    drawRotatedPuddingStack(g, cx, cy, angle, count);
}










	private void drawImageRotated(Graphics g, BufferedImage img, int centerX, int centerY, int w, int h, double radians) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.translate(centerX, centerY); 
    g2.rotate(radians);             
    g2.drawImage(img, -w / 2, -h / 2, w, h, null); 
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
			g.drawImage(getCardImage(type),c.setX((500+i*85)),c.setY(getHeight() - 120),cardWidth,cardHeight,null);
			c.setRectangle(c.getX(), c.getY());
	}
	public void selectCard(int playerIndex, int handIndex)
	{
    selectedHandIndex = (selectedHandIndex == handIndex) ? -1 : handIndex;
	
    repaint();
	}
	public void playSelectedCard(int playerIndex) {
    if (selectedHandIndex < 0) return;

    Player p = players.get(playerIndex);
    Hand h = p.getHand();

    if (selectedHandIndex >= h.size()) {
        selectedHandIndex = -1;
        return;
    }

    Card played = h.remove(selectedHandIndex);

    if (isNigiri(played) && p.hasWasabi()) {
        int wasabiIndex = p.getIndexOfWasabi();
        Card wasabi = p.getPlayedCards().get(wasabiIndex);

        if (!wasabi.hasPaired()) {
            wasabi.pair();
            wasabi.setPairedCard(played); 
            played.pair();
        }
    }

    if (isPudding(played)) {
        p.addToPudding(played);
    } else {
        p.addPlayedCard(played);
    }

    selectedHandIndex = -1;
    repaint();
}

	public boolean isPudding(Card c)
	{
		if(c.getType().equals("pudding"))
		{
			return true;
		}
		return false;
	}
	public boolean isNigiri(Card c)
	{
		if(c.getType().equals("salmonn") || c.getType().equals("eggn") || c.getType().equals("squidn"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

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
		g.setColor(java.awt.Color.BLACK);
    	g.fillRect(0, 0, getWidth(), getHeight());
		displayCards(g,currentPlayerIndex);
		
		
		
	}
	public void displayCards(Graphics g, int index) {
    displayPlayedCards(g, index);
    displayOtherPlayerCards(g);
    displayOtherPlayerPlayedCards(g);


    Hand h = players.get(index).getHand();
    int spacing = 85;
    int n = h.size();

    int totalWidth = (n > 0 ? (n - 1) * spacing + cardWidth : 0);
    int startX = (getWidth() - totalWidth) / 2;

    for (int i = 0; i < n; i++) {
        Card c = h.get(i);
        String type = c.getType();

        int x = startX + i * spacing;
        int y = (i == selectedHandIndex) ? (getHeight() - cardHeight - 20) : (getHeight() - cardHeight);

        c.setX(x);
        c.setY(y);
        c.setRectangle(x, y);

        g.drawImage(getCardImage(type), x, y, cardWidth, cardHeight, null);
    }
}

	private boolean isPairedToWasabi(Card nigiri, ArrayList<Card> playedCards) {
    for (Card c : playedCards) {
        if (c.getType().equals("wasabi") && c.hasPaired() && c.getPairedCard() == nigiri) {
            return true;
        }
    }
    return false;
	}
	public void displayPlayedCards(Graphics g, int playerIndex) {
    ArrayList<Card> played = players.get(playerIndex).getPlayedCards();
    int spacing = 85;
    int baseY = getHeight() - 320;
    int lift = 20;

    int visibleCount = 0;
    for (Card c : played) {
        if (isNigiri(c) && isPairedToWasabi(c, played)) continue;
        visibleCount++;
    }

    int totalWidth = (visibleCount > 0 ? (visibleCount - 1) * spacing + cardWidth : 0);
    int startX = (getWidth() - totalWidth) / 2;
	g.setColor(Color.WHITE);
    g.drawString(players.get(playerIndex).getName(), startX, baseY - 12);

    int drawnCount = 0;
    for (Card c : played) {
        if (isNigiri(c) && isPairedToWasabi(c, played)) continue;

        String type = c.getType();
        int x = startX + drawnCount * spacing;
        boolean isSelected = (drawnCount == selectedHandIndex);
        int y = isSelected ? (baseY - lift) : baseY;

        if ("wasabi".equals(type) && c.hasPaired()) {
            g.drawImage(getCardImage("wasabi"), x, y, cardWidth, cardHeight, null);

            Card nigiri = c.getPairedCard();
            if (nigiri != null) {
                g.drawImage(getCardImage(nigiri.getType()), x, y - lift, cardWidth, cardHeight, null);
                nigiri.setX(x);
                nigiri.setY(y - lift);
                nigiri.setRectangle(x, y - lift);
            }

            c.setX(x);
            c.setY(y);
            c.setRectangle(x, y);
            drawnCount++;
        } else {
            g.drawImage(getCardImage(type), x, y, cardWidth, cardHeight, null);
            c.setX(x);
            c.setY(y);
            c.setRectangle(x, y);
            drawnCount++;
        }
    }

    displayPuddings(g, playerIndex);
}


private void displayPuddings(Graphics g, int playerIndex) {
    ArrayList<Card> puddings = players.get(playerIndex).getPuddingCards();
    if (puddings == null || puddings.isEmpty()) return;

    double scale = 0.5; 
    int w = (int) (cardWidth * scale);
    int h = (int) (cardHeight * scale);
    int overlap = (int) (h * 0.3); 


    int x = getWidth() - w - 20;
    int startY = getHeight() - (puddings.size() * overlap + h) - 20;

    for (int i = 0; i < puddings.size(); i++) {
        int y = startY + i * overlap;
        g.drawImage(getCardImage("pudding"), x, y, w, h, null);
    }
}

public void displayOtherPlayerPuddings(Graphics g, int playerIndex, int relativeSeat) {
    ArrayList<Card> puddings = players.get(playerIndex).getPuddingCards();
    if (puddings == null || puddings.isEmpty()) return;

    double scale = 0.5;
    int w = (int)(cardWidth  * scale);
    int h = (int)(cardHeight * scale);
    int overlap = (int)(h * 0.3);

    BufferedImage img = getCardImage("pudding");
    double angle = seatAngle(relativeSeat);

    switch (relativeSeat) {
        case 1: { // left player – top-left
            int cx = w / 2 + 20;
            int top = 20;                       // pixels from top
            for (int i = 0; i < puddings.size(); i++) {
                int cy = top + h / 2 + i * overlap;
                drawImageRotated(g, img, cx, cy, w, h, angle);
            }
            break;
        }
        case 2: { // top player – top-right
            int cx = getWidth() - w / 2 - 20;
            int top = 20;
            for (int i = 0; i < puddings.size(); i++) {
                int cy = top + h / 2 + i * overlap;
                drawImageRotated(g, img, cx, cy, w, h, angle);
            }
            break;
        }
        case 3: { // right player – bottom-right, shifted UP 25 and RIGHT 15
            int count  = puddings.size();
            int totalH = (count - 1) * overlap + h;

            int cx = getWidth() - w / 2 - 20 + 15;                 // right 15
            int top = getHeight() - totalH - 20 - 25;              // up 25

            for (int i = 0; i < count; i++) {
                int cy = top + h / 2 + i * overlap;
                drawImageRotated(g, img, cx, cy, w, h, angle);
            }
            break;
        }
    }
}



private void drawRotatedLabel(Graphics g, String text, int x, int y, double radians) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setColor(java.awt.Color.WHITE);
    g2.rotate(radians, x, y);
    g2.drawString(text, x, y);
    g2.dispose();
}
private void drawRotatedPuddingStack(Graphics g, int cx, int cy, double radians, int count) {
    if (count <= 0) return;
    double scale = 0.5;
    int w = (int)(cardWidth  * scale);
    int h = (int)(cardHeight * scale);
    int overlap = (int)(h * 0.3);
    int totalH = (count - 1) * overlap + h;
    int startY = cy - totalH / 2;

    BufferedImage img = getCardImage("pudding");
    for (int i = 0; i < count; i++) {
        int y = startY + i * overlap;
        drawImageRotated(g, img, cx, y, w, h, radians);
    }
}










}