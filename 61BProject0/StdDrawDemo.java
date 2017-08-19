/* 
 *
 * Make sure to see the more detailed description of
 * StdDraw at: http://introcs.cs.princeton.edu/java/15inout/ 
 * 
 * The link above also provides additional examples like BouncingBall.java
 *
 * Or you can see the full documentation at:
 *   http://introcs.cs.princeton.edu/java/15inout/javadoc/StdDraw.html
 */

public class StdDrawDemo {
	public static String imageToDraw = "starfield.jpg";

	/* Draws three copies of the image in a rectangular pattern. */
	public static void drawThree() {
		/** Sets up the universe so it goes from 
		  * -100, -100 up to 100, 100 */
		StdDraw.setScale(-100, 100);

		/* Clears the drawing window. */
		StdDraw.clear();

		/* Stamps three copies of advice.png in a triangular pattern. */
		StdDraw.picture(0, 75, imageToDraw);
		StdDraw.picture(-75, -75, imageToDraw);
		StdDraw.picture(75, -75, imageToDraw);

		/* Shows the drawing to the screen, and waits 2000 milliseconds. */	
		StdDraw.show(2000);		
	}

	

	/** Stick a copy of the image in the dead center of the image,
	  * which is position (0, 0). Slowly zoom in on the image, 
	  * then zoom back out (but faster than we zoomed in). */
	public static void drawZoom() {
		double size = 100;
		while (size < 500) {
			StdDraw.clear();
			StdDraw.picture(0, 0, imageToDraw, size, size);
			StdDraw.show(10);
			size += 1;
		}

		while (size > 1) {
			StdDraw.clear();
			StdDraw.picture(0, 0, imageToDraw, size, size);
			StdDraw.show(1);
			size -= 1;
		}
	}

	public static void main(String[] args) {
		
		drawThree();
		drawZoom();
	}
} 