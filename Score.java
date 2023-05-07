/**
 * -----------------------------------------------------------
 * @author Vishesh Narayan
 * @version 5/4/23
 * -----------------------------------------------------------
 * Score class
 * -----------------------------------------------------------
 * Creates Score elements for StarFighter
 * -----------------------------------------------------------
 */
import java.net.URL;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

// methods marked @implementation are required for implementation but do not have any function in Score.java
public class Score extends MovingThing {
	/**
	 * private vars
	 */
	private Image image;

	/**
	 * Constructors
	 */
	public Score() {
		this(200,200,100,10, "0");
	}

	public Score(int x, int y, int w, int h, String num) {
		super(x, y, w, h);
		try {
			URL url = getClass().getResource("/images/"+num+".jpg");
			image = ImageIO.read(url);
			System.out.println(num+" loaded in properly");
		} catch(Exception e) {
			System.out.println(num+" not loaded in properly");
		}
	}

	/**
	 * @implementation
	 */
	public void setSpeed(int s) {}

	/**
	 * @implementation
	 */
	public int getSpeed() {
	   return 0;
	}

	/**
	 * @implementation
	 */
	public void move(String direction) {}

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