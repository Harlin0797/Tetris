package dk.cmol.tetris.figures;

import android.graphics.Point;

public class FigL extends Figure {
	public FigL(int xStart) {
		figBricksOriginal.add(new Point(0,-1));
		figBricksOriginal.add(new Point(0, 0));
		figBricksOriginal.add(new Point(0, 1));
		figBricksOriginal.add(new Point(1, 1));
		
		reset(xStart);
		brickColor = Colors.BROWN;
	}
}
