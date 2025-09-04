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
		displayCards(g,0);
		
	}
	public void displayCards(Graphics g,int index)
	{

		Hand h = players.get(index).getHand();
		for(int i = 0;i<h.size();i++)
		{
			Card c = h.get(i);
			String type = c.getType();
			if(type.equals("dumpling"))
			{
				g.drawImage(dumpling,400+(i*130),0,150,210,null);
			}
			else if(type.equals("sashimi"))
			{
				g.drawImage(sashimi,400+(i*130),0,150,210,null);
			}
			else if(type.equals("tempura"))
			{
				g.drawImage(tempura,400+(i*130),0,150,210,null);
			}
			else if(type.equals("maki1"))
			{
				g.drawImage(maki1,400+(i*130),0,150,210,null);
			}
			else if(type.equals("maki2"))
			{
				g.drawImage(maki2,400+(i*130),0,150,210,null);
			}
			else if(type.equals("maki3"))
			{
				g.drawImage(maki3,400+(i*130),0,150,210,null);
			}
			else if(type.equals("wasabi"))
			{
				g.drawImage(wasabi,400+(i*130),0,150,210,null);
			}
			else if(type.equals("chopsticks"))
			{
				g.drawImage(chopsticks,400+(i*130),0,150,210,null);
			}
			else if(type.equals("pudding"))
			{
				g.drawImage(pudding,400+(i*130),0,150,210,null);
			}
			else if(type.equals("eggn"))
			{
				g.drawImage(eggn,400+(i*130),0,150,210,null);
			}
			else if(type.equals("salmonn"))
			{
				g.drawImage(salmonn,400+(i*130),0,150,210,null);
			}
			else if(type.equals("squidn"))
			{
				g.drawImage(squidn,400+(i*130),0,150,210,null);
			}
			
		}
	}
}

