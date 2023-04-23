/**
 * -----------------------------------------------------------
 * @author Vishesh Narayan
 * @version 3/4/23
 * -----------------------------------------------------------
 * Ammo class
 * -----------------------------------------------------------
 * Creates Ammo for ammo in game
 * -----------------------------------------------------------
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;

public class Ammo extends MovingThing {
	/**
	 * private vars
	 */
	private int speed;
	private Image image;

	/**
	 * Constructors
	 */
	public Ammo() {
		this(0,0,0);
	}

	public Ammo(int x, int y) {
		this(x, y, 0);
	}

	public Ammo(int x, int y, int s) {
		super(x, y);
		this.speed = s;
		
		try {
			URL url = getClass().getResource("/images/bullet.png");
			image = ImageIO.read(url);
		} catch(Exception e) {
			System.out.println("bullet.png not loaded in properly");
		}
	}

	/**
	 * setSpeed: sets speed given s
	 * @param s
	 */
	public void setSpeed(int s) {
	   this.speed = s;
	}

	/**
	 * getSpeed: returns speed
	 */
	public int getSpeed() {
	   return this.speed;
	}

	/**
	 * draw: draws object in position
	 * @param window
	 */
	public void draw(Graphics window) {
		window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
	}

	/**
	 * moveAndDraw: graphically moves object
	 * @param window
	 */
	public void moveAndDraw(Graphics window) {
		// old loc 
		draw(window);

		// remove 
		remove(window);

		// new loc
		move("");

		// draw at new loc
		draw(window);
	}

	/**
	 * remove: removes bullet from current position
	 * @param window
	 */
	public void remove(Graphics window) {
		window.setColor(Color.BLACK);
		window.fillRect(getX(), getY(), getWidth(), getHeight());
	}
	
	/**
	 * move: moves direction given string indicating position
	 * NOTE: no if statement used since bullet will always move up
	 * @param direction
	 */
	public void move(String direction) {
		setY(getY()-getSpeed());
	}

	/**
	 * toString: return string when object is printed
	 */
	public String toString() {
		return super.toString()+"::bullet";
	}
}
