import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

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


	public SushiGoPanel() {
		try {
			dumpling = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_dumpling.jpg"));
			sashimi = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_sashimi.jpg"));
			tempura = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_tempura.jpg"));
			maki1 = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_maki1.png"));
			maki2 = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_maki2.png"));
			maki3 = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_maki3.png"));
			wasabi = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_wasabi.jpg"));
			chopsticks = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_chopsticks.jpg"));
			eggn = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_eggnigiri.jpg"));
			salmonn = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_salmonnigiri.jpg"));
			squidn = ImageIO.read(SushiGoPanel.class.getResource("/images/sushigo_squidnigiri.jpg"));
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

		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (e.getButton() == MouseEvent.BUTTON1) {
			
		} else if (e.getButton() == MouseEvent.BUTTON3) {

		}
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
		g.drawImage(dumpling,0,0,200,200,null);
		g.drawImage(sashimi,100,0,200,200,null);
		g.drawImage(tempura,200,0,200,200,null);
		g.drawImage(maki1,300,0,200,200,null);
		g.drawImage(wasabi,600,0,200,200,null);
		
	}
	public void displayCards()
	{
		Hand h = players.get(0).getHand();
		for(int i = 0;i<h.size();i++)
		{
			Card c = h.get(i);
			String type = c.getType();
			if(type.equals("dumpling"))
			{
				g.drawImage(dumpling,0,0+(i*130),dumpling.getWidth(),dumpling.getHeight(),null);
			}
			else if(type.equals("sashimi"))
			{
				g.drawImage(sashimi,0,0+(i*130),sashimi.getWidth(),sashimi.getHeight(),null);
			}
			else if(type.equals("tempura"))
			{
				g.drawImage(tempura,0,0+(i*130),tempura.getWidth(),tempura.getHeight(),null);
			}
			else if(type.equals("maki1"))
			{
				g.drawImage(maki1,0,0+(i*130),maki1.getWidth(),maki1.getHeight(),null);
			}
			else if(type.equals("maki2"))
			{
				g.drawImage(maki2,0,0+(i*130),maki2.getWidth(),maki2.getHeight(),null);
			}
			else if(type.equals("maki3"))
			{
				g.drawImage(maki3,0,0+(i*130),maki3.getWidth(),maki3.getHeight(),null);
			}
			else if(type.equals("wasabi"))
			{
				g.drawImage(wasabi,0,0+(i*130),wasabi.getWidth(),wasabi.getHeight(),null);
			}
			else if(type.equals("chopsticks"))
			{
				g.drawImage(chopsticks,0,0+(i*130),chopsticks.getWidth(),chopsticks.getHeight(),null);
			}
			else if(type.equals("pudding"))
			{
				g.drawImage(pudding,0,0+(i*130),pudding.getWidth(),pudding.getHeight(),null);
			}
			else if(type.equals("eggnigiri"))
			{
				g.drawImage(eggn,0,0+(i*130),eggn.getWidth(),eggn.getHeight(),null);
			}
			else if(type.equals("salmonnigiri"))
			{
				g.drawImage(salmonn,0,0+(i*130),salmonn.getWidth(),salmonn.getHeight(),null);
			}
			else if(type.equals("squidnigiri"))
			{
				g.drawImage(squidn,0,0+(i*130),squidn.getWidth(),squidn.getHeight(),null);
			}
			
		}
	}
}

