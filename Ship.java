/**
 * -----------------------------------------------------------
 * @author Vishesh Narayan
 * @version 3/4/23
 * -----------------------------------------------------------
 * Ship class
 * -----------------------------------------------------------
 * Creates Ship class for Ship in game
 * -----------------------------------------------------------
 */
import java.net.URL;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Ship extends MovingThing {

	/**
	 * private vars
	 */
	private int speed;
	private Image image;

	/**
	 * Constructors
	 */
	public Ship() {
		this(200,200,100,100,5);
	}

	public Ship(int x, int y) {
		this(x,y,100,100,5);
	}

	public Ship(int x, int y, int s) {
		this(x,y,100,100,s);
	}

	public Ship(int x, int y, int w, int h, int s) {
		super(x, y, w, h);
		speed = s;
		try {
			URL url = getClass().getResource("/images/ship.jpg");
			image = ImageIO.read(url);
			System.out.println("ship.png loaded in properly");
		} catch(Exception e) {
			System.out.println("ship.png not loaded in properly");
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
		if (direction.equals("SPACE")) {}
	}

	/**
	 * draw: graphically moves object
	 * @param window
	 */
	public void draw(Graphics window) {
   		window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
	}

	/**
	 * toString: return string when object is printed
	 */
	public String toString() {
		return super.toString() + getSpeed();
	}
}