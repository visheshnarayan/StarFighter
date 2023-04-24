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
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class OuterSpace extends Canvas implements KeyListener, Runnable {
	
	/**
	 * private vars
	 */ 
	private Ship ship;
	private AlienHorde horde;
	private Bullets bullets;
	private Map<String, Boolean> keys;
	private Map<int[], Integer> alienLoc;
	private BufferedImage back;
	private long time;

	/**
	 * Constructor
	 * @param par
	 */
	public OuterSpace(JFrame par) {
		setBackground(Color.black);
		ship = new Ship(350, 400, 50, 50, 1);
		horde = new AlienHorde(10);
		// bullets = new ArrayList<Ammo>();
		bullets = new Bullets();

		keys = Stream.of(new Object[][] {
			{"LEFT", false}, 
			{"RIGHT", false}, 
			{"UP", false}, 
			{"DOWN", false}, 
			{"SPACE", false}, 
		}).collect(Collectors.toMap(data -> (String) data[0], data -> (Boolean) data[1]));
		alienLoc = new HashMap<int[], Integer>();
		HashMap<Integer, Alien> aliens = horde.getMap();
		for (int i = 0; i < horde.getSize(); i++) {
			alienLoc.put(new int[] {
					aliens.get(i).getX(),
					aliens.get(i).getY()
				},  i
			);
		}

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
		bullets.drawEmAll(window);

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
		if (keys.get("SPACE")) {bullets.add(new Ammo(ship.getX()+20, ship.getY(), 1));}
		
		/**
		 * collision detection
		 */
		bullets.moveEmAll(window);
		for (int i = 0; i < bullets.getList().size(); i++) {
			/**
			 * HashMap {int[alien location]: alienID}
			 * map lookup -> map.get(bulletLoc) => if look up works, bullet @alien -> pop alien from stack
			 */
			Ammo ammo = bullets.getList().get(i);
			int[] ammoLoc = new int[] {ammo.getX(), ammo.getY()};
			for (int[] arr: alienLoc.keySet()) {
				if (collision(arr, ammoLoc)) {
					System.out.println("collision with alien #" + alienLoc.get(arr));
					horde.removeDeadOnes(alienLoc.get(arr), window);

					// add new alien at random loc
					horde.add(
						new Alien(
							(int) ((Math.random() * (700 - 100)) + 100),
							(int) ((Math.random() * (300 - 10)) + 10),
							horde.getLW(),
							horde.getLW(),
							horde.getLW()
						), i
					);
				}
			}
		}

		/**
		 * aliens
		 */
		if ((System.currentTimeMillis()-time)%500==0) {horde.moveEmAll(window);}
		HashMap<Integer, Alien> aliens = horde.getMap();
		alienLoc = new HashMap<int[], Integer>();
		for (int id: aliens.keySet()) {
			alienLoc.put(new int[] {
					aliens.get(id).getX(),
					aliens.get(id).getY()
				},  id
			);
		}
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

	/**
	 * collision: checks if bullet is inside alien hit box
	 * @param alienLoc
	 * @param bulletLoc
	 * @return boolean
	 */
	private boolean collision(int[] alienLoc, int[] bulletLoc) {
		// first check y level -> then x
 		if (alienLoc[1]!=bulletLoc[1]) {return false;} else {
			if (bulletLoc[0]>=alienLoc[0] && bulletLoc[0]<=alienLoc[0]+30) {return true;}
			return false;
		}
	}
}