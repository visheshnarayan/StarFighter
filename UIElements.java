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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

// methods marked @implementation are required for implementation but do not have any function in Score.java
public class UIElements extends MovingThing {
	/**
	 * private vars
	 */
	private Image image;

	/**
	 * Constructors
	 */
	public UIElements() {
		this(200,200,100,10, "0");
	}

	public UIElements(int x, int y, int w, int h, String num) {
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
	 * remove: removes score element from current position
	 * @param window
	 */
	public void remove(Graphics window) {
		window.setColor(Color.BLACK);
		window.fillRect(getX(), getY(), getWidth(), getHeight());
	}

	/**
	 * toString: return string when object is printed
	 */
	public String toString() {
		return super.toString() + getSpeed();
	}
}