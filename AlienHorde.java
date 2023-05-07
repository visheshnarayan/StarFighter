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
import java.util.HashMap;

public class AlienHorde {
	/**
	 * private vars
	 */
	private HashMap<Integer, Alien> aliens;
	private int n;
	private int lw;

	/**
	 * Constructors
	 * @param size
	 */
	public AlienHorde(int size) {
		this.aliens = new HashMap<Integer, Alien>();
		this.n = 1;
		this.lw = 30;
		for (int i = 0; i < size; i++) {
			updateN();
			add(new Alien(n*lw, 350, lw, lw, lw), i);
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
		for (int id: aliens.keySet()) {aliens.get(id).draw(window);}
	}

	/**
	 * moveEmAll: moves whole horde in appropriate section
	 * note: leading alien is always index 0
	 */
	public void moveEmAll(Graphics window) {
		if (getSize()!=0) {
			/*=============== New system ===============*/
			/**
			 * for each alien
			 * 		if alien out of bounds (too left or too right)
			 * 			move down 
			 * 			push back into bounds
			 * 			change direction of movement if left or right 0
			 * 		else
			 * 			moveAlien(direction of alien)
			 */
			/*==========================================*/
			for (Alien al: aliens.values()) {
				// horizontal check
				if (al.getX() < 30 || al.getX() > 700) {
					al.move("DOWN", window);
					if (al.getX() < 30) {
						al.move("RIGHT", window);
						al.setDireaction("RIGHT");
					} else {
						al.move("LEFT", window);
						al.setDireaction("LEFT");
					}
				} else {
					al.move(al.getDireaction(), window);
				}
			}
		}
	}

	/**
	 * removeDeadOnes: removes dead aliens 
	 * @param shots
	 */
	public void removeDeadOnes(int alienID, Graphics window) {
		aliens.get(alienID).remove(window);
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
	 * getLW: returns length/width of alien.jpg (square image)
	 * @return
	 */
	public int getLW() {
		return this.lw;
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