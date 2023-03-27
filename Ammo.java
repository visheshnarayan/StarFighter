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
import java.io.File;
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
			URL url = getClass().getResource("/images/bullet.jpg");
			image = ImageIO.read(url);
			System.out.println("bullet.png loaded in properly");
		} catch(Exception e) {
			System.out.println("bullet.png not loaded in properly");
		}
	}

	public void setSpeed(int s) {
	   this.speed = s;
	}

	public int getSpeed() {
	   return this.speed;
	}

	public void draw(Graphics window) {
		window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
	}
	
	
	public void move(String direction) {
		
	}

	public String toString()
	{
		return super.toString()+"::bullet";
	}
}
