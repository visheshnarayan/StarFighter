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
import java.util.HashMap;
import java.util.List;

public class AlienHorde {
	/**
	 * private vars
	 */
	private HashMap<Integer, Alien> aliens;
	private int n;
	private int rowSize;
	private int lw;

	// true -> right movement, false -> left movement
	private boolean leftRight;

	/**
	 * Constructors
	 * @param size
	 */
	public AlienHorde(int size) {
		this.aliens = new HashMap<Integer, Alien>();
		this.rowSize = size;
		this.n = 1;
		this.leftRight = true;
		this.lw = 30;
		for (int i = 0; i < size; i++) {
			updateN();
			add(new Alien(n*lw, 10, lw, lw, lw), i);
		}
		System.out.println("horde deployed");
	}

	/**
	 * add: adds Alien to horde
	 * @param al 
	 */
	public void add(Alien al, int i) {
		aliens.put(i, al);
	}

	/**
	 * drawEmAll: draws all aliens onto window 
	 * @param window
	 */
	public void drawEmAll(Graphics window) {
		for (int i = 0; i < aliens.size(); i++) {aliens.get(i).draw(window);}
	}

	/**
	 * moveEmAll: moves whole horde in appropriate section
	 * note: leading alien is always index 0
	 */
	public void moveEmAll(Graphics window) {
		// if last alien at edge: shift down and left
		if (aliens.get(getSize()-1).getX()>450) {
			for (int i = 0; i < aliens.size(); i++) {aliens.get(i).move("DOWN", window);}
			leftRight = false;
		}

		// if first alien at edge: shift down and right
		if (aliens.get(0).getX()<=50) {
			for (int i = 0; i < aliens.size(); i++) {aliens.get(i).move("DOWN", window);}
			leftRight = true;
		}

		// if right movement: shift right
		if (leftRight) {
			for (int i = 0; i < aliens.size(); i++) {aliens.get(i).move("RIGHT", window);}
		}

		// if left movement: shift left
		if (!leftRight) {
			for (int i = 0; i < aliens.size(); i++) {aliens.get(i).move("LEFT", window);}
		}
	}

	/**
	 * removeDeadOnes: removes dead aliens 
	 * @param shots
	 */
	public void removeDeadOnes(int alienID) {
		aliens.remove(alienID);
	}

	/**
	 * getList: returns list of aliens
	 * @return aliens
	 */
	public HashMap<Integer, Alien> getMap() {
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
