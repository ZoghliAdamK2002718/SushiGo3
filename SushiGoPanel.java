import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.awt.Rectangle;


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
		while(players.get(3).returnHasPlayedCard() == false)
		{
			for(int i = 0;i<players.size();i++)
			{
				players.get(i).selectCard(i);
				players.get(i).playCard();
				repaint();
			}
		}

		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
    int x = e.getX(), y = e.getY();
    Hand h = players.get(0).getHand();

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
				}
				
            } else {
                selectedHandIndex = i;
                repaint();
            }
            break;
        }
    }
}
	public void selectCard(Graphics g,int index, int i)
	{
		Hand h = players.get(index).getHand();
			Card c = h.get(i);
			String type = c.getType();
			if(type.equals("dumpling"))
			{
				g.drawImage(dumpling,c.setX((600+i*100)),c.setY(getHeight() - 120),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("sashimi"))
			{
				g.drawImage(sashimi,c.setX(600+i*100),c.setY(getHeight() -120),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());

			}
			else if(type.equals("tempura"))
			{
				g.drawImage(tempura,c.setX(600+i*100),c.setY(getHeight() -120),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());

			}
			else if(type.equals("maki1"))
			{
				g.drawImage(maki1,c.setX(600+i*100),c.setY(getHeight() -120),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("maki2"))
			{
				g.drawImage(maki2,c.setX(600+i*100),c.setY(getHeight() -120),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("maki3"))
			{
				g.drawImage(maki3,c.setX(600+i*100),c.setY(getHeight() -120),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("wasabi"))
			{
				g.drawImage(wasabi,c.setX(600+i*100),c.setY(getHeight() -120),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("chopsticks"))
			{
				g.drawImage(chopsticks,c.setX(600+i*100),c.setY(getHeight() -120),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
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
		// Handle mouse press events
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Handle mouse release events
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Handle mouse enter events
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Handle mouse exit events
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
		for(int i = 0;i<players.size();i++)
		{
			displayPlayedCards(g,i);
		}
		
		
	}
	public void displayCards(Graphics g,int index)
	{

		Hand h = players.get(index).getHand();
		ArrayList<Card> played = players.get(index).getPlayedCards();

		for(int i = 0;i<h.size();i++)
		{
			Card c = h.get(i);
			String type = c.getType();
			int y = (i == selectedHandIndex) ? (getHeight() - 140) - 20  : getHeight() - 140;
			if(type.equals("dumpling"))
			{
				g.drawImage(dumpling,c.setX((600+i*100)),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("sashimi"))
			{
				g.drawImage(sashimi,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());

			}
			else if(type.equals("tempura"))
			{
				g.drawImage(tempura,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());

			}
			else if(type.equals("maki1"))
			{
				g.drawImage(maki1,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("maki2"))
			{
				g.drawImage(maki2,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("maki3"))
			{
				g.drawImage(maki3,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("wasabi"))
			{
				g.drawImage(wasabi,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("chopsticks"))
			{
				g.drawImage(chopsticks,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("pudding"))
			{
				g.drawImage(pudding,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("eggn"))
			{
				g.drawImage(eggn,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("salmonn"))
			{
				g.drawImage(salmonn,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("squidn"))
			{
				g.drawImage(squidn,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			
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
			if(type.equals("dumpling"))
			{
				g.drawImage(dumpling,c.setX((600+i*100)),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("sashimi"))
			{
				g.drawImage(sashimi,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());

			}
			else if(type.equals("tempura"))
			{
				g.drawImage(tempura,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());

			}
			else if(type.equals("maki1"))
			{
				g.drawImage(maki1,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("maki2"))
			{
				g.drawImage(maki2,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("maki3"))
			{
				g.drawImage(maki3,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("wasabi"))
			{
				g.drawImage(wasabi,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("chopsticks"))
			{
				g.drawImage(chopsticks,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("pudding"))
			{
				g.drawImage(pudding,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("eggn"))
			{
				g.drawImage(eggn,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("salmonn"))
			{
				g.drawImage(salmonn,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			else if(type.equals("squidn"))
			{
				g.drawImage(squidn,c.setX(600+i*100),c.setY(y),cardWidth,cardHeight,null);
				c.setRectangle(c.getX(), c.getY());
			}
			
		}


	}
}

