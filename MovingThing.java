/**
 * -----------------------------------------------------------
 * @author Vishesh Narayan
 * @version 3/4/23
 * -----------------------------------------------------------
 * MovingThing class
 * -----------------------------------------------------------
 * Creates MovingThing class for moving objects in game
 * -----------------------------------------------------------
 */
import java.awt.Graphics;

public abstract class MovingThing implements Moveable {
	
	/**
	 * private vars
	 */
	private int xPos;
	private int yPos;
	private int width;
	private int height;

	/**
	 * Constructors
	 */
	public MovingThing() {
		xPos = 10;
		yPos = 10;
		width = 10;
		height = 10;
	}

	public MovingThing(int x, int y) {
		xPos = x;
		yPos = y;
		width = 10;
		height = 10;
	}

	public MovingThing(int x, int y, int w, int h) {
		xPos = x;
		yPos = y;
		width = w;
		height = h;
	}

	/**
	 * setPos: sets position of object given x and y
	 * @param x
	 * @param y
	 */
	public void setPos(int x, int y) {
		setX(x); 
		setY(y);
	}

	/**
	 * setX: sets x position of object given x
	 * @param x
	 */
	public void setX(int x) {
		xPos = x;
	}

	/**
	 * setY: sets y position of object given y
	 * @param y
	 */
	public void setY(int y) {
		yPos = y;
	}

	/**
	 * getX: returns x position
	 */
	public int getX() {
		return xPos;
	}

	/**
	 * getY: returns y position
	 */
	public int getY() {
		return yPos;
	}

	/**
	 * setWidth: sets width given w
	 * @param w
	 */
	public void setWidth(int w) {
		width = w;
	}

	/**
	 * setHeight: sets height given h
	 * @param h
	 */
	public void setHeight(int h) {
		height = h;
	}

	/**
	 * getWidth: returns width of object
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * getHeight: returns height of object
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * move: moves object
	 * @param direction
	 */
	public abstract void move(String direction);

	/**
	 * draw: draws object (graphically moves object)
	 * @param window
	 */
	public abstract void draw(Graphics window);

	/**
	 * toString: return string when object is printed
	 */
	public String toString() {
		return getX() + " " + getY() + " " + getWidth() + " " + getHeight();
	}
}