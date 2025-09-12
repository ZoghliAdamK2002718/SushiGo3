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
import java.awt.AlphaComposite;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



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
	private static final int cardWidth = 85;
	private static final int cardHeight = 139;
    private int currentPlayerIndex = 0;
    private int currentRound = 1;
    private final int totalRounds = 3;
    private boolean gameOver = false;
    private int selectionsRemainingThisTurn = 1;
    private Rectangle chopsticksButtonRect = null;
    private Rectangle nextButtonRect = null;

    private enum Phase { START, IN_ROUND, ROUND_OVER, GAME_OVER }
    private Phase phase = Phase.START;

    private JTextField[] nameFields = new JTextField[4];
    private JButton startButton;


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
        setOpaque(true);
        selectedHandIndex = -1;

        setLayout(null);
        for (int i = 0; i < 4; i++) {
            JTextField tf = new JTextField(new String[]{"Alice","Bob","Charlie","Diana"}[i]);
            tf.setBounds(450, 120 + i * 50, 300, 30);
            nameFields[i] = tf;
            add(tf);
        }
        startButton = new JButton("Start Game");
        startButton.setBounds(520, 340, 160, 36);
        startButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        add(startButton);
		

		
	}
    public void runRound(Graphics g)
    {
        displayCards(g,currentPlayerIndex);
    }
    public void resetRound()
    {
        for(int i = 0;i<players.size();i++)
        {
            players.get(i).endRound();
            players.get(i).clearHand();
            giveHand(i);
        }
        currentPlayerIndex = 0;
        selectionsRemainingThisTurn = 1;
    }
	@Override
    public void mouseClicked(MouseEvent e) {
        if (phase == Phase.ROUND_OVER) {
            if (nextButtonRect != null && nextButtonRect.contains(e.getX(), e.getY())) {
                if (currentRound < totalRounds) {
                    currentRound++;
                    resetRound();
                    phase = Phase.IN_ROUND;
                    repaint();
                }
            }
            return;
        } else if (phase == Phase.GAME_OVER) {
            return;
        } else if (phase == Phase.START) {
            return;
        }

        int x = e.getX(), y = e.getY();
        Player curr = players.get(currentPlayerIndex);
        Hand h = curr.getHand();

        if (chopsticksButtonRect != null && chopsticksButtonRect.contains(x, y)) {
            if (curr.hasChopsticks() && h.size() >= 2 && selectionsRemainingThisTurn == 1) {
                try {
                    curr.useChopsticks(); 
                    selectionsRemainingThisTurn = 2;
                    repaint();
                } catch (IllegalStateException ex) {
                }
            }
            return;
        }

        for (int i = 0; i < h.size(); i++) {
            Card c = h.get(i);
            Rectangle r = c.getRectangle();

            if (r != null && r.contains(x, y)) {
                if (selectedHandIndex == i) {
                    Card picked = h.remove(selectedHandIndex);
                    curr.addPendingCard(picked);
                    selectedHandIndex = -1;

                    selectionsRemainingThisTurn--;
                    if (selectionsRemainingThisTurn <= 0) {
                        curr.resetForNewTurn();
                        selectionsRemainingThisTurn = 1;
                        currentPlayerIndex++;
                        if (currentPlayerIndex >= players.size()) {
                            currentPlayerIndex = 0;
                            revealAndPass();
                        }
                    }
                    repaint();
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

    private void revealAndPass() {
        for (Player p : players) {
            ArrayList<Card> pend = new ArrayList<>(p.getPendingCards());
            // Snapshot unpaired wasabi that existed BEFORE this reveal
            ArrayList<Card> preWasabi = new ArrayList<>();
            for (Card pc : p.getPlayedCards()) {
                if ("wasabi".equals(pc.getType()) && !pc.hasPaired()) {
                    preWasabi.add(pc);
                }
            }

            for (Card c : pend) {
                if (isPudding(c)) {
                    p.addToPudding(c);
                } else if (isNigiri(c)) {
                    if (!preWasabi.isEmpty()) {
                        Card wz = preWasabi.remove(0);
                        wz.pair();
                        wz.setPairedCard(c);
                        c.pair();
                    }
                    p.addPlayedCard(c);
                } else {
                    p.addPlayedCard(c);
                }
            }
            p.clearPending();
        }

        passCardstoLeft();

        if (allPlayersHaveOneCard()) {
            endOfRoundScoring();
            if (currentRound < totalRounds) {
                phase = Phase.ROUND_OVER;
            } else {
                gameOver = true;
                finalPuddingScoring();
                phase = Phase.GAME_OVER;
            }
            repaint();
        }
    }

    private boolean allPlayersHaveOneCard() {
        for (Player p : players) {
            if (!p.hasOneCard()) return false;
        }
        return true;
    }

    private int nigiriValue(Card c) {
        return switch (c.getType()) {
            case "eggn" -> 1;
            case "salmonn" -> 2;
            case "squidn" -> 3;
            default -> 0;
        };
    }

    private void endOfRoundScoring() {
        int n = players.size();
        int[] maki = new int[n];
        for (int i = 0; i < n; i++) {
            int cnt = 0;
            for (Card c : players.get(i).getPlayedCards()) {
                switch (c.getType()) {
                    case "maki1" -> cnt += 1;
                    case "maki2" -> cnt += 2;
                    case "maki3" -> cnt += 3;
                }
            }
            maki[i] = cnt;
        }
        int max = 0, second = 0;
        for (int v : maki) { if (v > max) { second = max; max = v; } else if (v > second && v < max) { second = v; } }
        int maxCount = 0, secondCount = 0;
        for (int v : maki) if (v == max && max > 0) maxCount++;
        if (maxCount > 1) {
            for (int i = 0; i < n; i++) if (maki[i] == max && max > 0) players.get(i).addScore(6 / maxCount);
        } else {
            for (int i = 0; i < n; i++) if (maki[i] == max && max > 0) players.get(i).addScore(6);
            for (int v : maki) if (v == second && second > 0) secondCount++;
            if (secondCount > 0) for (int i = 0; i < n; i++) if (maki[i] == second && second > 0) players.get(i).addScore(3 / secondCount);
        }

        for (Player p : players) {
            int tempura = 0, sashimi = 0, dumpling = 0, nigiriPts = 0;
            ArrayList<Card> played = p.getPlayedCards();
            for (Card c : played) {
                String t = c.getType();
                switch (t) {
                    case "tempura" -> tempura++;
                    case "sashimi" -> sashimi++;
                    case "dumpling" -> dumpling++;
                    case "eggn", "salmonn", "squidn" -> {
                        int val = nigiriValue(c);
                        if (isPairedToWasabi(c, played)) val *= 3;
                        nigiriPts += val;
                    }
                }
            }
            p.addScore((tempura / 2) * 5);
            p.addScore((sashimi / 3) * 10);
            int dPts = switch (Math.min(dumpling, 5)) { case 0 -> 0; case 1 -> 1; case 2 -> 3; case 3 -> 6; case 4 -> 10; default -> 15; };
            p.addScore(dPts);
            p.addScore(nigiriPts);
        }
    }

    private void finalPuddingScoring() {
        int n = players.size();
        int[] pudd = new int[n];
        for (int i = 0; i < n; i++) {
            pudd[i] = players.get(i).getPuddingCards() == null ? 0 : players.get(i).getPuddingCards().size();
        }
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (int v : pudd) { if (v > max) max = v; if (v < min) min = v; }
        int maxCount = 0, minCount = 0;
        for (int v : pudd) { if (v == max) maxCount++; if (v == min) minCount++; }
        if (max > 0) {
            int each = 6 / Math.max(1, maxCount);
            for (int i = 0; i < n; i++) if (pudd[i] == max) players.get(i).addScore(each);
        }
        if (min < max) {
            int each = 6 / Math.max(1, minCount);
            for (int i = 0; i < n; i++) if (pudd[i] == min) players.get(i).addScore(-each);
        }
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
            case 1: { 
                int totalH  = (n - 1) * spacing + cardWidth;
                int startY  = (getHeight() - totalH) / 2;
                int centerX = cardHeight / 2 + 200;

                int drawn = 0;
                for (Card c : played) {
                    if (isNigiri(c) && isPairedToWasabi(c, played)) continue;
                    int cy = startY + drawn * spacing + cardWidth / 2;
                    if ("wasabi".equals(c.getType()) && c.hasPaired()) {
                        drawImageRotated(g, getCardImage("wasabi"), centerX, cy, cardWidth, cardHeight, angle);
                        Card nigiri = c.getPairedCard();
                        if (nigiri != null) {
                            int lift = 20;
                            drawImageRotated(g, getCardImage(nigiri.getType()), centerX, cy - lift, cardWidth, cardHeight, angle);
                        }
                    } else {
                        drawImageRotated(g, getCardImage(c.getType()), centerX, cy, cardWidth, cardHeight, angle);
                    }
                    drawn++;
                }

                g.setColor(Color.WHITE);
                g.drawString(name, centerX - cardHeight / 2, startY - 10);
                break;
            }

            case 2: { 
                int totalW  = (n - 1) * spacing + cardWidth;
                int startX  = (getWidth() - totalW) / 2;
                int centerY = cardHeight / 2 + 200;

                int drawn = 0;
                for (Card c : played) {
                    if (isNigiri(c) && isPairedToWasabi(c, played)) continue;
                    int cx = startX + drawn * spacing + cardWidth / 2;
                    if ("wasabi".equals(c.getType()) && c.hasPaired()) {
                        drawImageRotated(g, getCardImage("wasabi"), cx, centerY, cardWidth, cardHeight, angle);
                        Card nigiri = c.getPairedCard();
                        if (nigiri != null) {
                            int lift = 20;
                            drawImageRotated(g, getCardImage(nigiri.getType()), cx, centerY - lift, cardWidth, cardHeight, angle);
                        }
                    } else {
                        drawImageRotated(g, getCardImage(c.getType()), cx, centerY, cardWidth, cardHeight, angle);
                    }
                    drawn++;
                }

                g.setColor(Color.WHITE);
                g.drawString(name, startX, centerY - cardHeight / 2 - 12);
                break;
            }

            case 3: { 
                int totalH  = (n - 1) * spacing + cardWidth;
                int startY  = (getHeight() - totalH) / 2;
                int centerX = getWidth() - cardHeight / 2 - 200;

                int drawn = 0;
                for (Card c : played) {
                    if (isNigiri(c) && isPairedToWasabi(c, played)) continue;
                    int cy = startY + drawn * spacing + cardWidth / 2;
                    if ("wasabi".equals(c.getType()) && c.hasPaired()) {
                        drawImageRotated(g, getCardImage("wasabi"), centerX, cy, cardWidth, cardHeight, angle);
                        Card nigiri = c.getPairedCard();
                        if (nigiri != null) {
                            int lift = 20;
                            drawImageRotated(g, getCardImage(nigiri.getType()), centerX, cy - lift, cardWidth, cardHeight, angle);
                        }
                    } else {
                        drawImageRotated(g, getCardImage(c.getType()), centerX, cy, cardWidth, cardHeight, angle);
                    }
                    drawn++;
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
        int wasabiIndex = p.getUnpairedWasabiIndex();
        if (wasabiIndex != -1) {
            Card wasabi = p.getPlayedCards().get(wasabiIndex);
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
        tempHand.fill(sushiDeck); 
        players.get(playerID).setHand(tempHand);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(java.awt.Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        chopsticksButtonRect = null;

        if (phase == Phase.START) {
            drawStartScreen(g);
            return;
        }

        if (phase == Phase.IN_ROUND) {
            runRound(g);
            g.setColor(java.awt.Color.WHITE);
            g.drawString("Round " + currentRound + "/" + totalRounds, 10, 20);
        } else if (phase == Phase.ROUND_OVER || phase == Phase.GAME_OVER) {
            runRound(g);
            drawScoreboardOverlay((Graphics2D) g);
        }
    }

    private void drawStartScreen(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Sushi Go! Enter player names:", 480, 90);
    }

    private void drawScoreboardOverlay(Graphics2D g2) {
        int w = getWidth(), h = getHeight();
        Graphics2D gg = (Graphics2D) g2.create();
        gg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        gg.setColor(Color.BLACK);
        gg.fillRect(0, 0, w, h);
        gg.setComposite(AlphaComposite.SrcOver);

        int boxW = 500, boxH = 300;
        int bx = (w - boxW) / 2, by = (h - boxH) / 2;
        gg.setColor(new Color(30, 30, 30));
        gg.fillRect(bx, by, boxW, boxH);
        gg.setColor(Color.WHITE);
        gg.drawRect(bx, by, boxW, boxH);

        gg.drawString(phase == Phase.GAME_OVER ? "Final Scores" : ("End of Round " + currentRound), bx + 20, by + 30);
        int y = by + 60;
        for (Player p : players) {
            gg.drawString(p.getName() + ": " + p.getScore(), bx + 20, y);
            y += 24;
        }

        String btnText = (phase == Phase.GAME_OVER) ? "Game Over" : "Next Round";
        int btnW = 140, btnH = 36;
        int btx = bx + boxW - btnW - 20;
        int bty = by + boxH - btnH - 20;
        gg.setColor(new Color(180, 40, 40));
        gg.fillRect(btx, bty, btnW, btnH);
        gg.setColor(Color.WHITE);
        gg.drawRect(btx, bty, btnW, btnH);
        gg.drawString(btnText, btx + 20, bty + 24);
        nextButtonRect = new Rectangle(btx, bty, btnW, btnH);

        gg.dispose();
    }

    private void startGame() {
        for (JTextField tf : nameFields) remove(tf);
        remove(startButton);
        repaint();

        players.clear();
        for (int i = 0; i < 4; i++) {
            String name = nameFields[i] != null ? nameFields[i].getText().trim() : ("P" + (i+1));
            if (name.isEmpty()) name = "Player " + (i+1);
            players.add(new Player(name, i));
        }
        sushiDeck = new Deck();
        for (int i = 0; i < players.size(); i++) giveHand(i);

        currentRound = 1;
        currentPlayerIndex = 0;
        selectionsRemainingThisTurn = 1;
        gameOver = false;
        phase = Phase.IN_ROUND;
        revalidate();
        repaint();
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
    if (playerIndex == currentPlayerIndex) {
        chopsticksButtonRect = null;
    }

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
        int y = baseY;
        if("chopsticks".equals(type))
        {
            g.setColor(java.awt.Color.RED);
            g.drawRect(x,y-37,90,15);
            Rectangle chop = new Rectangle(x,y-37,90,15);
            if (playerIndex == currentPlayerIndex) {
                chopsticksButtonRect = chop;
            }
            g.setColor(java.awt.Color.WHITE);
            g.drawString("Use Chopsticks",x,y-25);
        }
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
        case 1: {
            int cx = w / 2 + 20;
            int top = 20;               
            for (int i = 0; i < puddings.size(); i++) {
                int cy = top + h / 2 + i * overlap;
                drawImageRotated(g, img, cx, cy, w, h, angle);
            }
            break;
        }
        case 2: { 
            int cx = getWidth() - w / 2 - 20;
            int top = 20;
            for (int i = 0; i < puddings.size(); i++) {
                int cy = top + h / 2 + i * overlap;
                drawImageRotated(g, img, cx, cy, w, h, angle);
            }
            break;
        }
        case 3: { 
            int count  = puddings.size();
            int totalH = (count - 1) * overlap + h;

            int cx = getWidth() - w / 2 - 20 + 15;                 
            int top = getHeight() - totalH - 20 - 25;             

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
