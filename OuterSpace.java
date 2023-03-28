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
import java.awt.Canvas;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;

public class OuterSpace extends Canvas implements KeyListener, Runnable {
	
	/**
	 * private vars
	 */
	private Ship ship;
	private Alien alienOne;
	private Alien alienTwo;
	private ArrayList<Ammo>  bullets; 
	// private AlienHorde horde;
	// private Bullets shots;

	private Map<String, Boolean> keys;
	private BufferedImage back;

	/**
	 * Constructor
	 * @param par
	 */
	public OuterSpace(JFrame par) {
		// initialize other vars when ready
		setBackground(Color.black);
		ship = new Ship(350, 400, 100, 100, 3);
		alienOne = new Alien(100, 100, 100, 100, 3);
		alienTwo = new Alien(600, 100, 100, 100, 3);
		bullets = new ArrayList<Ammo>();

		keys = Stream.of(new Object[][] {
			{"LEFT", false}, 
			{"RIGHT", false}, 
			{"UP", false}, 
			{"DOWN", false}, 
			{"SPACE", false}, 
		}).collect(Collectors.toMap(data -> (String) data[0], data -> (Boolean) data[1]));

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
		if (back == null) {back = (BufferedImage) (createImage(getWidth(),getHeight()));}

		//create a graphics reference to the back ground image
		//we will draw all changes on the background image
		Graphics graphToBack = back.createGraphics();

		// objects
		ship.draw(window);
		alienOne.draw(window);
		alienTwo.draw(window);

		// background
		graphToBack.setColor(Color.BLUE);
		graphToBack.drawString("StarFighter ", 25, 50 );
		graphToBack.setColor(Color.BLACK);
		graphToBack.fillRect(0,0,800,600);

		// controls
		if (keys.get("LEFT")) {
			ship.move("LEFT");
			System.out.println("Left movement");
		}
		if (keys.get("RIGHT")) {
			ship.move("RIGHT");
			System.out.println("Right movement");
		}
		if (keys.get("UP")) {
			ship.move("UP");
			System.out.println("Up movement");
		}
		if (keys.get("DOWN")) {
			ship.move("DOWN");
			System.out.println("Down movement");
		}
		if (keys.get("SPACE")) {
			bullets.add(new Ammo(ship.getX()+50, ship.getY(), 4));
		}

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

		//add in collision detection to see if Bullets hit the Aliens and if Bullets hit the Ship
	}


	/**
	 * keyPressed: detects key press for changing key array
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keys.replace("LEFT", true);
			System.out.println("LEFT");
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keys.replace("RIGHT", true);
			System.out.println("RIGHT");
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			keys.replace("UP", true);
			System.out.println("UP");
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keys.replace("DOWN", true);
			System.out.println("DOWN");
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys.replace("SPACE", true);
			System.out.println("SPACE");
		}
		repaint();
	}

	/**
	 * keyReleased: detects key release for changing key array
	 */
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keys.replace("LEFT", false);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keys.replace("RIGHT", false);
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			keys.replace("UP", false);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keys.replace("DOWN", false);
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys.replace("SPACE", false);
		}
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
   				Thread.sleep(5);
            	repaint();
         	}
      	} catch(Exception e) {}
  	}
}

