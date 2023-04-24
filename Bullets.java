/**
 * -----------------------------------------------------------
 * @author Vishesh Narayan
 * @version 4/23/23
 * -----------------------------------------------------------
 * Bullets class
 * -----------------------------------------------------------
 * Creates Bullet class as a collection for Ammo object
 * -----------------------------------------------------------
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class Bullets {
	/**
	 * private vars
	 */
	private List<Ammo> ammo;

	/**
	 * Constructors
	 */
	public Bullets() {
		this.ammo = new ArrayList<Ammo>();
	}

	/**
	 * add: adds Ammo to Bullets
	 * @param al 
	 */
	public void add(Ammo ammo) {
		this.ammo.add(ammo);
	}

	/**
	 * drawEmAll: draws all ammo onto window 
	 * @param window
	 */
	public void drawEmAll(Graphics window) {
		for (Ammo am: ammo) {am.draw(window);}
	}

	/**
	 * moveEmAll: moves all bullets forward
	 */
	public void moveEmAll(Graphics window) {
		if (ammo.size()!=0) {
			for (int i = 0; i < ammo.size(); i++) {

				// update bullet position
				Ammo am = ammo.get(i);
				if (am.getY() > 0) {
					am.moveAndDraw(window);
				}
				if (am.getY() == 0) {
					am.remove(window);
					ammo.remove(i);
				}
			}
		}
	}

	/**
	 * cleanEmUp: removes all bullets at the end of game
	 */
	public void cleanEmUp(Graphics window) {
		for (Ammo am: ammo) {am.remove(window);}
	}

	/**
	 * getList: returns collection of Ammo objects as List 
	 * @return List<Ammo>
	 */
	public List<Ammo> getList() {
		return ammo;
	}

	/**
	 * toString: returns string representation of object 
	 */
	public String toString() {
		return "BULLETS";
	}
}