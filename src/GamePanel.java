import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import java.util.Scanner;



public class GamePanel extends JPanel implements ActionListener {

	static final int SCREEN_WIDTH =600;
	static final int SCREEN_HEIGHT =600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNIT = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 500;
	final int x[] = new int[GAME_UNIT];
	final int y[] = new int[GAME_UNIT];
	int bodypart = 1;
	int appleEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;

	

	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new Mykeyadapter());
		startGame();
	}

	public void startGame () {
		newApple();
		running = true;
		timer = new Timer (DELAY,this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		if(running) {
			for( int i=0;i<=SCREEN_HEIGHT/UNIT_SIZE;i++){
				g.drawLine (i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
				g.drawLine (0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
 			}
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		
			for (int i=0;i<bodypart;i++) {
				if(i==0) {
					g.setColor(Color.orange);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else{
					g.setColor(new Color(45,180,0));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.cyan);
			g.setFont(new Font ("ink free",Font.BOLD,30));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Start:"+(appleEaten*5),(SCREEN_WIDTH-metrics.stringWidth("Start:"+appleEaten
					))/2,g.getFont().getSize());
		}
		else {
			GameOver(g);
		}
	}
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	public void move() {
		for(int i=bodypart;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(direction) {
		case 'U':
			y[0] = y[0]-UNIT_SIZE;
			break;
		case 'D':
			y[0]=y[0]+UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0]-UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0]+UNIT_SIZE;
			break;
		}
		
	}
	public void checkApple() {
		if((x[0]==appleX)&&(y[0]==appleY)){
			bodypart++;
			appleEaten++;
			newApple();
		}
	}
	public void checkCollision () { 

		for(int i=bodypart;i>0;i--) {
			if((x[0]==x[i])&&((y[0])==y[i])) {
				running=false;
			}
		}
		// This checks the left border of the game.
		if((x[0]<0)) {
			running=false;
		}
		// This checks the right border of the game.
		if((x[0]>SCREEN_WIDTH)) {
			running=false;
		}
		// This checks the top border of the game.
		if((y[0]<0)) {
			running=false;
		}
		// This checks the right border of the game.
		if((y[0]>SCREEN_HEIGHT)) {
			running=false;
		}
		if(!running) {
			timer.stop();
			String x= "";
			
		}
	}
	public void GameOver (Graphics g) {
		g.setColor(Color.cyan);
		g.setFont(new Font ("ink free",Font.BOLD,40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Start:"+(appleEaten*5),(SCREEN_WIDTH-metrics1.stringWidth("Start:"+appleEaten))/2,g.getFont().getSize());
		
		g.setColor(Color.cyan);
		g.setFont(new Font ("ink free",Font.BOLD,100));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("GAME OVER",(SCREEN_WIDTH-metrics.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);
		
		g.setColor(Color.magenta);
		g.setFont(new Font ("ink free",Font.BOLD,35));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("To restart press ENTER",(SCREEN_WIDTH-metrics.stringWidth("ENTER"))/2, SCREEN_HEIGHT/4);
		//RestartGame();
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkApple();
			checkCollision();
		}
		repaint();
	}
	
	public class Mykeyadapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction !='R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction !='L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction !='D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction !='U') {
					direction = 'D';
				}
				break;
			case KeyEvent.VK_ENTER:
				if(direction !='C') {
					direction = 'E';
				}	
			}
		}
	}
}
