/**
 * -----------------------------------------------------------
 * @author Vishesh Narayan
 * @version 4/14/23
 * -----------------------------------------------------------
 * AlienHorde class
 * -----------------------------------------------------------
 * Creates AlienHorde class for aliens in game 
 * -----------------------------------------------------------
 */
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


// TODO: make alien disapear from previous position
public class AlienHorde {
	/**
	 * private vars
	 */
	private List<Alien> aliens;
	private int n = 1;
	// true -> right movement, false -> left movement
	// private boolean leftRight = true;

	/**
	 * Constructors
	 * @param size
	 */
	public AlienHorde(int size) {
		aliens = new ArrayList<Alien>();
		for (int i = 0; i < size; i++) {
			updateN();
			add(new Alien(n*20, 10, 20, 20, 10));
		}
		System.out.println("horde deployed");
	}

	/**
	 * add: adds Alien to horde
	 * @param al 
	 */
	public void add(Alien al) {
		aliens.add(al);
	}

	/**
	 * drawEmAll: draws all aliens onto window 
	 * @param window
	 */
	public void drawEmAll(Graphics window) {
		// for (Alien alien: aliens) {alien.draw(window);}
		for (int i = 0; i < aliens.size(); i++) {aliens.get(i).draw(window);}
	}

	/**
	 * moveEmAll: moves whole horde down 
	 */
	public void moveEmAll() {
		// for (Alien alien: aliens) {alien.move("DOWN");}
		for (int i = 0; i < aliens.size(); i++) {
			aliens.get(i).move("DOWN");
			try {Thread.sleep(1);} catch (Exception e) {}
		}
	}

	/**
	 * removeDeadOnes: removes dead aliens 
	 * @param shots
	 */
	public void removeDeadOnes(List<Ammo> shots) {
		// TODO: count how many bullets hit alien
	}

	/**
	 * getList: returns list of aliens
	 * @return aliens
	 */
	public List<Alien> getList() {
		return aliens;
	}
	
	/**
	 * getSize: returns size of horde
	 * @return size
	 */
	public int getSize() {
		return aliens.size();
	}

	/**
	 * updateN: updates private variable n to account for next alien placement
	 */
	private void updateN() {
		if (n==10) {n = 1;} else {n++;}
	}

	/**
	 * toString: returns string representation of object 
	 */
	public String toString() {
		return "HORDE";
	}
}
