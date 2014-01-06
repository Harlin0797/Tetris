package dk.cmol.tetris.figures;

import android.graphics.Point;

public class FigS extends Figure {
	public FigS(int xStart) {
		figBricksOriginal.add(new Point(0,-1));
		figBricksOriginal.add(new Point(1,-1));
		figBricksOriginal.add(new Point(0, 0));
		figBricksOriginal.add(new Point(-1,0));
		
		reset(xStart);
		brickColor = Colors.GREEN;
	}
}