/**
 * -----------------------------------------------------------
 * @author Vishesh Narayan
 * @version 3/4/23
 * -----------------------------------------------------------
 * StarFighter class
 * -----------------------------------------------------------
 * Creates StarFighter class for game operation
 * -----------------------------------------------------------
 */
import javax.swing.JFrame;
import java.awt.Component;
import java.awt.Graphics;

public class StarFighter extends JFrame {
	public static int WIDTH = 800;
	public static int HEIGHT = 600;

	public StarFighter() {
		super("STARFIGHTER");

		setSize(WIDTH, HEIGHT);

		OuterSpace theGame = new OuterSpace(this);
		((Component)theGame).setFocusable(true);

      getContentPane().add( theGame );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main( String args[] ) {
		StarFighter run = new StarFighter();
	}
}