/**
 * -----------------------------------------------------------
 * @author Vishesh Narayan
 * @version 3/4/23
 * -----------------------------------------------------------
 * OuterSpace class
 * -----------------------------------------------------------
 * Creates OuterSpace class for space environment in game 
 * -----------------------------------------------------------
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class OuterSpace extends Canvas implements KeyListener, Runnable {
	
	/**
	 * private vars
	 */
	private Ship ship;
	private ArrayList<Ammo>  bullets; 
	private AlienHorde horde;
	// private Alien alien;
	// private Bullets shots;

	private Map<String, Boolean> keys;
	private BufferedImage back;

	private Timer timer; 
	private long time;

	// TODO: make bullets list -> queue (pop queue when a bullet reaches end)
	/**
	 * Constructor
	 * @param par
	 */
	public OuterSpace(JFrame par) {
		// initialize other vars when ready
		setBackground(Color.black);
		ship = new Ship(350, 400, 50, 50, 1);
		// horde = new AlienHorde(4);
		// alien = new Alien(400, 10, 100, 100, 1);
		horde = new AlienHorde(10);
		bullets = new ArrayList<Ammo>();

		keys = Stream.of(new Object[][] {
			{"LEFT", false}, 
			{"RIGHT", false}, 
			{"UP", false}, 
			{"DOWN", false}, 
			{"SPACE", false}, 
			{"W", false},
			{"A", false},
			{"S", false},
			{"D", false}
		}).collect(Collectors.toMap(data -> (String) data[0], data -> (Boolean) data[1]));
		time = System.currentTimeMillis();
		this.addKeyListener(this);
		new Thread(this).start();
		setVisible(true);
	}

	/**
	 * update: updates graphics for movement
	 */
   	public void update(Graphics window) {	
	   paint(window);
   	}

	/**
	 * paint: graphically moves object
	 */
	public void paint(Graphics window) {
		if (back==null) {back = (BufferedImage) (createImage(getWidth(),getHeight()));}

		//create a graphics reference to the back ground image
		//we will draw all changes on the background image
		Graphics2D graphToBack = back.createGraphics();

		// objects
		ship.draw(window);
		horde.drawEmAll(window);
		// alien.draw(window);

		// background
		graphToBack.setColor(Color.BLUE);
		graphToBack.drawString("StarFighter ", 25, 50 );
		graphToBack.setColor(Color.BLACK);
		graphToBack.fillRect(0,0,800,600);

		// controls
		if (keys.get("LEFT")) {ship.move("LEFT");}
		if (keys.get("RIGHT")) {ship.move("RIGHT");}
		if (keys.get("UP")) {ship.move("UP");}
		if (keys.get("DOWN")) {ship.move("DOWN");}
		if (keys.get("SPACE")) {bullets.add(new Ammo(ship.getX()+50, ship.getY(), 4));}

		// if (keys.get("W")) {alien.move("UP");}
		// if (keys.get("A")) {alien.move("LEFT");}
		// if (keys.get("S")) {alien.move("DOWN");}
		// if (keys.get("D")) {alien.move("RIGHT");}
		// if (keys.get("W")) {horde.moveEmAll();}

		/**
		 * update bullet positions
		 */
		for (int i = 0; i < bullets.size(); i++) {
			Ammo ammo = bullets.get(i);
			if (ammo.getY() > 0) {
				ammo.moveAndDraw(window);

			} else {
				ammo.remove(window);
				bullets.remove(i);
			}
		}

		if ((System.currentTimeMillis()-time)%20000==0) {horde.moveEmAll();}
		// if ((System.currentTimeMillis()-time)%20000==0) {
		// 	List<Alien> aliens = horde.getList();
		// 	for (int i = 0; i < horde.getSize(); i++) {
		// 		aliens.get(i).move("DOWN");
		// 	}
		// }
		// if ((System.currentTimeMillis()-time)%20000==0) {System.out.println("horde movement");}

		// timer = new Timer();
		// timer.scheduleAtFixedRate(new TimerTask() {
		// 	@Override
		// 	public void run() {
		// 		// Thread.sleep(20, ABORT);
		// 		horde.moveEmAll();
		// 		System.out.println("Horde moved");
		// 	}
		// }, 20*1000, 20*1000);
		//add in collision detection to see if Bullets hit the Aliens and if Bullets hit the Ship
	}


	/**
	 * keyPressed: detects key press for changing key array
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {keys.replace("LEFT", true);}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {keys.replace("RIGHT", true);}
		if (e.getKeyCode() == KeyEvent.VK_UP) {keys.replace("UP", true);}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {keys.replace("DOWN", true);}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {keys.replace("SPACE", true);}
		if (e.getKeyCode() == KeyEvent.VK_W) {keys.replace("W", true);}
		if (e.getKeyCode() == KeyEvent.VK_A) {keys.replace("A", true);}
		if (e.getKeyCode() == KeyEvent.VK_S) {keys.replace("S", true);}
		if (e.getKeyCode() == KeyEvent.VK_D) {keys.replace("D", true);}
		repaint();
	}

	/**
	 * keyReleased: detects key release for changing key array
	 */
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {keys.replace("LEFT", false);}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {keys.replace("RIGHT", false);}
		if (e.getKeyCode() == KeyEvent.VK_UP) {keys.replace("UP", false);}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {keys.replace("DOWN", false);}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {keys.replace("SPACE", false);}
		if (e.getKeyCode() == KeyEvent.VK_W) {keys.replace("W", false);}
		if (e.getKeyCode() == KeyEvent.VK_A) {keys.replace("A", false);}
		if (e.getKeyCode() == KeyEvent.VK_S) {keys.replace("S", false);}
		if (e.getKeyCode() == KeyEvent.VK_D) {keys.replace("D", false);}
		repaint();
	}

	/**
	 * keyTyped: needed for implementation
	 */
	public void keyTyped(KeyEvent e) {}

	/**
	 * run: runs animation
	 */
    public void run() {
   		try {
   			while(true) {
   				Thread.sleep(1);
            	repaint();
         	}
      	} catch(Exception e) {}
  	}
}

