import javax.swing.JFrame;

public class SushiFrame extends JFrame {
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 480;
	public SushiFrame(String title)
	{
		super(title);
		setSize(WIDTH,HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SushiGoPanel panel = new SushiGoPanel();
		setVisible(true);
	}
	public static void main(String[]args)
	{
		
	}
	
}
