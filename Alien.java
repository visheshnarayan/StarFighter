/**
 * -----------------------------------------------------------
 * @author Vishesh Narayan
 * @version 3/4/23
 * -----------------------------------------------------------
 * Alien class
 * -----------------------------------------------------------
 * Creates Alien class for Alien in game
 * -----------------------------------------------------------
 */
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Alien extends MovingThing {

    /**
	 * private vars
	 */
	private int speed;
	private Image image;

	/**
	 * Constructors
	 */
	public Alien() {
		this(0, 0, 50, 50,  0);
	}

	public Alien(int x, int y) {
		this(x, y, 50, 50, 0);
	}

	public Alien(int x, int y, int s) {
		this(x, y, 50, 50, s);
	}

	public Alien(int x, int y, int w, int h, int s) {
		super(x, y, w, h);
		speed = s;
		try {
			URL url = getClass().getResource("/images/alien.jpg");
			image = ImageIO.read(url);
			System.out.println("Alien.png loaded in properly");
		} catch(Exception e) {
			System.out.println("Alien.png not loaded in properly");
		}
	}


	/**
	 * setSpeed: sets speed given s
	 * @param s
	 */
	public void setSpeed(int s) {
	   speed = s;
	}

	/**
	 * getSpeed: returns speed
	 */
	public int getSpeed() {
	   return speed;
	}

	/**
	 * move: moves direction given string indicating position
	 * @param direction
	 */
	public void move(String direction) {
		if (direction.equals("LEFT")) {setX(getX()-getSpeed());}
		if (direction.equals("RIGHT")) {setX(getX()+getSpeed());} 
		if (direction.equals("UP")) {setY(getY()-getSpeed());}
		if (direction.equals("DOWN")) {setY(getY()+getSpeed());}
	}

	/**
	 * draw: graphically moves object
	 * @param window
	 */
	public void draw(Graphics window) {
   		window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
	}

	/**
	 * moveAndDraw: graphically moves object
	 * @param window
	 */
	// public void moveAndDraw(Graphics window) {
	// 	// old loc 
	// 	draw(window);

	// 	// remove 
	// 	remove(window);

	// 	// // new loc
	// 	// move("DOWN");

	// 	// draw at new loc
	// 	draw(window);
	// }

	/**
	 * remove: removes bullet from current position
	 * @param window
	 */
	// private void remove(Graphics window) {
	// 	window.setColor(Color.BLACK);
	// 	window.fillRect(getX(), getY(), getWidth(), getHeight());
	// }

	/**
	 * toString: return string when object is printed
	 */
	public String toString() {
		return super.toString() + getSpeed();
	}
}
