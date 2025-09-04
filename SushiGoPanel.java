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
			dumpling = ImageIO.read(getClass().getResourceAsStream("/images/sushigo_dumpling.jpg"));
			sashimi = ImageIO.read(getClass().getResourceAsStream("/images/sushigo_sashimi.jpg"));
			tempura = ImageIO.read(getClass().getResourceAsStream("/images/sushigo_tempura.jpg"));
			maki1 = ImageIO.read(getClass().getResourceAsStream("/images/sushigo_maki1.png"));
			wasabi = ImageIO.read(getClass().getResource("/images/sushigo_wasabi.jpg"));
			chopsticks = ImageIO.read(getClass().getResourceAsStream("/images/sushigo_chopsticks.jpg"));
			eggn = ImageIO.read(getClass().getResourceAsStream("/images/sushsigo_eggn.jpg"));
			squidn = ImageIO.read(getClass().getResourceAsStream("/images/sushigo_squidn.jpg"));
			salmonn = ImageIO.read(getClass().getResourceAsStream("/images/sushigo_salmonn.jpg"));
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
		g.drawImage(wasabi,600,0,null);
		
	}
}

