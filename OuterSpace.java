/**
 * -----------------------------------------------------------
 * @author Vishesh Narayan
 * @version 3/4/23
 * -----------------------------------------------------------
 * OuterSpace class
 * -----------------------------------------------------------
 * Creates OuterSpace class for space environment in game 
 * -----------------------------------------------------------
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Canvas;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class OuterSpace extends Canvas implements KeyListener, Runnable {
	/**
	 * private vars
	 */ 
	private Ship ship;
	private AlienHorde horde;
	private Bullets bullets;
	private Map<String, Boolean> keys;
	private Map<int[], Integer> alienLoc;
	private BufferedImage back;
	private long time;
	private int score;
	private int last;
	private int lives;
	private ArrayList<Ship> livesShips;
	private UIElements[] UIElements;

	/**
	 * Constructor
	 * @param par
	 */
	public OuterSpace(JFrame par) {
		setBackground(Color.black);
		score = 0;
		// last used to check if score has changed so functions are not called in infinite loop
		last = 0;
		ship = new Ship(350, 400, 50, 50, 1);
		horde = new AlienHorde(10);
		bullets = new Bullets();

		UIElements = new UIElements [18];
		for (int i = 0; i < 10; i++) {
			UIElements[i] = new UIElements(10, 10, 40, 50, Integer.toString(i));
		}
		UIElements[10] = new UIElements(10, 10, 100, 50, "score");
		UIElements[11] = new UIElements(650, 10, 140, 50, "starfighter");
		UIElements[12] = new UIElements(250, 10, 140, 50, "lives");
		UIElements[13] = new UIElements(330, 200, 140, 50, "life-1");
		UIElements[14] = new UIElements(300, 200, 200, 100, "game_over");
		UIElements[15] = new UIElements(300, 200, 200, 100, "ready");
		UIElements[16] = new UIElements(330, 200, 140, 50, "life+1");
		UIElements[17] = new UIElements(300, 200, 200, 110, "speeding_up");

		livesShips = new ArrayList<Ship>();
		lives = 3;
		for (int i = 1; i <= lives; i++) {
			livesShips.add(new Ship((UIElements[12].getX()+UIElements[12].getWidth()-50)+i*ship.getWidth(), 10, 50, 50, 1));
		}

		keys = Stream.of(new Object[][] {
			{"LEFT", false}, 
			{"RIGHT", false}, 
			{"UP", false}, 
			{"DOWN", false}, 
			{"SPACE", false}, 
		}).collect(Collectors.toMap(data -> (String) data[0], data -> (Boolean) data[1]));
		alienLoc = new HashMap<int[], Integer>();
		HashMap<Integer, Alien> aliens = horde.getMap();
		for (int i = 0; i < horde.getSize(); i++) {
			alienLoc.put(new int[] {
					aliens.get(i).getX(),
					aliens.get(i).getY()
				},  i
			);
		}

		time = System.currentTimeMillis();
		this.addKeyListener(this);
		new Thread(this).start();
		setVisible(true);
	}

	/**
	 * update: updates graphics for movement
	 */
   	public void update(Graphics window) {	
	   paint(window);
   	}

	/**
	 * paint: graphically moves object
	 */
	public void paint(Graphics window) {
		if (back==null) {back = (BufferedImage) (createImage(getWidth(),getHeight()));}

		// objects
		ship.draw(window);
		horde.drawEmAll(window);
		bullets.drawEmAll(window);

		// controls
		if (keys.get("LEFT") && ship.getX() > 10) {ship.move("LEFT");}
		if (keys.get("RIGHT") && ship.getX() < 700) {ship.move("RIGHT");}
		if (keys.get("UP") && ship.getY() > 60) {ship.move("UP");}
		if (keys.get("DOWN") && ship.getY() < 450) {ship.move("DOWN");}
		if (keys.get("SPACE")) {bullets.add(new Ammo(ship.getX()+20, ship.getY(), 1));}
		
		/**
		 * collision detection
		 */
		bullets.moveEmAll(window);
		for (int i = 0; i < bullets.getList().size(); i++) {
			/**
			 * HashMap {int[alien location]: alienID}
			 * map lookup -> map.get(bulletLoc) => if look up works, bullet @alien -> pop alien from stack
			 * assign old id to new alien spanned
			 */
			Ammo ammo = bullets.getList().get(i);
			int[] ammoLoc = new int[] {ammo.getX(), ammo.getY()};
			for (int[] arr: alienLoc.keySet()) {
				int id = alienLoc.get(arr);
				if (collision(arr, ammoLoc)) {
					score++;
					last++;
					System.out.println("collision with alien #" + id);
					horde.removeDeadOnes(id, window);

					// add new alien at random loc
					// randint(min, max) = (int) (Math.random() * (max - min) + min)
					int x = (int) ((Math.random() * (700 - 100)) + 100);
					int y = (int) ((Math.random() * (300 - 150)) + 150);

					// round down 30 to lock alien into grid 
					x = x - (x % horde.getLW());
					y = y - (y % horde.getLW());
					horde.add(
						new Alien(
							x, 
							y,
							horde.getLW(),
							horde.getLW(),
							horde.getLW()
						), id
					);
				}
			}
			// remove bullet if reaches top
			if (ammo.getY() < 60) {
				bullets.getList().remove(i);
				ammo.remove(window);
			}
		}

		/**
		 * aliens
		 */
		if ((System.currentTimeMillis()-time)%500==0) {horde.moveEmAll(window);}
		HashMap<Integer, Alien> aliens = horde.getMap();
		alienLoc = new HashMap<int[], Integer>();
		for (int i = 0; i < aliens.size(); i++) {
			// update alien locs 
			int id = aliens.keySet().toArray(new Integer[aliens.size()])[i];
			alienLoc.put(new int[] {
					aliens.get(id).getX(),
					aliens.get(id).getY()
				},  id
			);

			// check if alien hit ship OR alien went past bounds
			if (collision(
				new int[] {ship.getX(), ship.getY()},
				new int[] {aliens.get(id).getX(), aliens.get(id).getY()}) || aliens.get(id).getY() > 400) {
				// decrease score
				System.out.println("ship hit!");
				score--;
				last++;

				// pause and unpause to show 1 life is lost
				removeLife(window);

				horde.removeDeadOnes(id, window);
				updateLives(false, window);
			}
		}

		/**
		 * UI
		 */
		// position "score" and "starfighter" and "lives"
		UIElements[10].draw(window);
		UIElements[11].draw(window);
		UIElements[12].draw(window);

		// draw in ships representing lives
		for (Ship ship: livesShips) {ship.draw(window);}

		// draw score
		drawScore(score, window);

		/**
		 * mechanics
		 */
		if (checkEnd()) {
			endGame(window);
		}

		// if not end then check if level update needed or new life needed
		if (last!=0) {
			checkPoints(window);
			last = 0;
		}
	}


	/**
	 * keyPressed: detects key press for changing key array
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {keys.replace("LEFT", true);}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {keys.replace("RIGHT", true);}
		if (e.getKeyCode() == KeyEvent.VK_UP) {keys.replace("UP", true);}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {keys.replace("DOWN", true);}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {keys.replace("SPACE", true);}
		repaint();
	}

	/**
	 * keyReleased: detects key release for changing key array
	 */
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {keys.replace("LEFT", false);}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {keys.replace("RIGHT", false);}
		if (e.getKeyCode() == KeyEvent.VK_UP) {keys.replace("UP", false);}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {keys.replace("DOWN", false);}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {keys.replace("SPACE", false);}
		repaint();
	}

	/**
	 * keyTyped: needed for implementation
	 */
	public void keyTyped(KeyEvent e) {}

	/**
	 * run: runs animation
	 */
    public void run() {
   		try {
   			while(true) {
   				Thread.sleep(1);
            	repaint();
         	}
      	} catch(Exception e) {}
  	}

	/**
	 * collision: checks if location one and location 2 overlap in hit box
	 * @param loc1 (object)
	 * @param loc2 (target)
	 * @return boolean
	 */
	private boolean collision(int[] loc1, int[] loc2) {
		// first check y level -> then x
		if (!(loc2[1]+15>=loc1[1] && loc2[1]+15<=loc1[1]+30)) {return false;} else {
			if (loc2[0]+15>=loc1[0] && loc2[0]+15<=loc1[0]+30) {
				return true;
			}
			return false;
		}
	}

	/**
	 * drawScore: draws current score onto window
	 * @param score
	 * @param window
	 */
	private void drawScore(int score, Graphics window) {
		// incase score is less than 0 -> do nothing, game will end in following function calls 
		if (score < 0) {return;}

		// digits 
		int hundred = (int) (score/100);
		int ten = (int) (score/10) - (hundred*10);
		int one = (int) (score%10);
		if (score < 10) {
			// position 0
			UIElements[0].setX(UIElements[10].getWidth()+10);
			UIElements[0].draw(window);

			// position single digit score value
			UIElements[one].setX(UIElements[0].getX()+UIElements[0].getWidth()-10);
			UIElements[one].draw(window);
		} else if (score < 100) {
			// position first digit
			UIElements[ten].setX(UIElements[10].getWidth()+10);
			UIElements[ten].draw(window);

			// position second digit
			UIElements[one].setX(UIElements[ten].getX()+UIElements[0].getWidth()-10);
			UIElements[one].draw(window);
		} else {
			// position first digit
			UIElements[hundred].setX(UIElements[10].getWidth()+10);
			UIElements[hundred].draw(window);

			// position second digit
			UIElements[ten].setX(UIElements[hundred].getX()+UIElements[0].getWidth()-10);
			UIElements[ten].draw(window);

			// position second digit
			UIElements[one].setX(UIElements[ten].getX()+UIElements[0].getWidth()-10);
			UIElements[one].draw(window);
		}
	}

	/**
	 * updateLives: updates lives player has by either increasing or decreasing based on bool=True or False
	 * @param bool
	 */
	// TODO: fix so game ends if lives == 0
	private void updateLives(boolean bool, Graphics window) {
		if (bool) {
			if (lives < 5) {
				lives++;
				livesShips.add(new Ship((livesShips.get(livesShips.size()-1)).getX()+ship.getWidth(), 10, 50, 50, 1));
				UIElements[16].draw(window);
				pause(3000);
				UIElements[16].remove(window);
			}
		} else {
			if (lives>0) {
				lives--;
				livesShips.remove(lives);
			} else {endGame(window);}
		}
	}

	/**
	 * pause: pauses screen 
	 * @param s
	 */
	private void pause(long s) {
		try {Thread.sleep(s);} catch (Exception e) {}
	}
	
	/**
	 * removeLife: displays animation displaying one life has been lost
	 * @param window
	 */
	private void removeLife(Graphics window) {
		if (livesShips.size()!=0) {
			Ship ship = livesShips.get(lives-1);
			int shipX = ship.getX();
			int shipY = ship.getY();
			int shipW = ship.getWidth();
			int shipH = ship.getHeight();

			UIElements[13].draw(window);

			pause(1000);
			window.setColor(Color.BLACK);
			window.fillRect(shipX, shipY, shipW, shipH);
			
			pause(300);
			ship.draw(window);

			pause(300);
			window.setColor(Color.BLACK);
			window.fillRect(shipX, shipY, shipW, shipH);

			pause(300);
			ship.draw(window);

			pause(500);
			window.setColor(Color.BLACK);
			window.fillRect(shipX, shipY, shipW, shipH);
			UIElements[13].remove(window);

			pauseHorde();
			window.setColor(Color.BLACK);
			window.fillRect(shipX, shipY, shipW, shipH);

			UIElements[15].draw(window);
			pause(3000);

			UIElements[15].remove(window);
			resumeHorde();
		}
	}

	/**
	 * checkPoints: changes speed of aliens every 50 points and adds new life every 100 points
	 */
	private void checkPoints(Graphics window) {
		if (score!=0 && score%50==0) {changeHordeSpeed(window);}
		if (score!=0 && score%100==0) {updateLives(true, window);}
	}

	/**
	 * checkEnd: checks if game is over through score or lives
	 * @return boolean
	 */
	private boolean checkEnd() {
		if (score < 0 || lives < 0) {
			return true; 
		}
		return false;
	}

	/**
	 * pauseHorde: pauses horde during intermissions
	 */
	private void pauseHorde() {
		for (Alien al: horde.getMap().values()) {al.setSpeed(0);}
	}

	/**
	 * resumeHorde: resumes horde after intermission
	 */
	private void resumeHorde() {
		for (Alien al: horde.getMap().values()) {al.setSpeed(horde.getLW());}
	}

	/**
	 * changeHordeSpeed: changes horde's speed by adding 30
	 */
	private void changeHordeSpeed(Graphics window) {
		for (Alien al: horde.getMap().values()) {al.setSpeed(horde.getSpeed()+30);}
		pause(2000);

		UIElements[17].draw(window);
		pause(2000);

		window.setColor(Color.BLACK);
		window.fillRect(UIElements[17].getX(), UIElements[17].getY(), UIElements[17].getWidth(), UIElements[17].getHeight());
		pause(100);
	}

	/**
	 * endGame: starts ending game sequence and prompts player to play again
	 * @param window
	 */
	private void endGame(Graphics window) {
		pauseHorde();
		UIElements[14].draw(window);
	}
}