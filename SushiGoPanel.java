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


	public SushiGoPanel(String type) {
		try {
			dumpling = ImageIO.read(getClass().getResourceAsStream("images/dumpling.png"));
			sashimi = ImageIO.read(getClass().getResourceAsStream("images/sashimi.png"));
			tempura = ImageIO.read(getClass().getResourceAsStream("images/tempura.png"));
			maki1 = ImageIO.read(getClass().getResourceAsStream("images/maki1.png"));
			maki2 = ImageIO.read(getClass().getResourceAsStream("images/maki2.png"));
			maki3 = ImageIO.read(getClass().getResourceAsStream("images/maki3.png"));
			wasabi = ImageIO.read(getClass().getResourceAsStream("images/wasabi.png"));
			chopsticks = ImageIO.read(getClass().getResourceAsStream("images/chopsticks.png"));
			pudding = ImageIO.read(getClass().getResourceAsStream("images/pudding.png"));
			eggn = ImageIO.read(getClass().getResourceAsStream("images/eggn.png"));
			squidn = ImageIO.read(getClass().getResourceAsStream("images/squidn.png"));
			salmonn = ImageIO.read(getClass().getResourceAsStream("images/salmonn.png"));
		} catch (IOException e) {
			e.printStackTrace();
			}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {

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
	public void giveHands()
	{
		for(int i = 0;i<4;i++)
		{
			Hand tempHand = new Hand();
			for(int j = 0;j<8;j++)
			{
				tempHand.add(sushiDeck.remove(j));
			}
			players.get(i).setHand(tempHand);
		}
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage(dumpling,0,0,null);
		g.drawImage(sashimi,100,0,null);
		g.drawImage(tempura,200,0,null);
		g.drawImage(maki1,300,0,null);
		g.drawImage(maki2,400,0,null);
		g.drawImage(maki3,500,0,null);
		g.drawImage(wasabi,600,0,null);
		
	}
}

