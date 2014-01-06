package dk.cmol.tetris.figures;

import android.graphics.Point;

public class FigII extends Figure {
	public FigII(int xStart) {
		figBricksOriginal.add(new Point(-1,-1));
		figBricksOriginal.add(new Point(-1, 0));
		figBricksOriginal.add(new Point(-1, 1));
		figBricksOriginal.add(new Point(-1, 2));
		figBricksOriginal.add(new Point( 1,-1));
		figBricksOriginal.add(new Point( 1, 0));
		figBricksOriginal.add(new Point( 1, 1));
		figBricksOriginal.add(new Point( 1, 2));
		
		reset(xStart);
		brickColor = Colors.CYAN;
	}
}