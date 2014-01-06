package dk.cmol.tetris.figures;

import android.graphics.Point;

public class FigI extends Figure {
	public FigI(int xStart) {
		figBricksOriginal.add(new Point(0,-2));
		figBricksOriginal.add(new Point(0,-1));
		figBricksOriginal.add(new Point(0, 0));
		figBricksOriginal.add(new Point(0, 1));
		
		reset(xStart);
		brickColor = Colors.CYAN;
	}
}