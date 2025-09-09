import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Card {
	private BufferedImage card;
	private String type;
	private int x;
	private int y;
	private Rectangle coords;
	private boolean isPlayed = false;
	private boolean isPaired = false;

	public Card(String t) {
		this.type = t;
		coords = new Rectangle(x, y, 100, 140);
	}
	public boolean hasPaired()
	{
		return isPaired;
	}
	public void pair()
	{
		isPaired = true;
	}
	public void played() {
		isPlayed = true;
	}
	public boolean isPlayed() {
		return isPlayed;
	}
	public String getType() {
		return type;
	}
	public int setX(int x) {
		this.x = x;
		coords.x = x;
		return x;
	}
	public int setY(int y) {
		this.y = y;
		coords.y = y;
		return y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setRectangle(int x, int y) {
		coords.setRect(x, y, 100, 140);
	}
	public Rectangle getRectangle() {
		return coords;
	}
	

}



