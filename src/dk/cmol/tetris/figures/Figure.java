package dk.cmol.tetris.figures;

import java.util.ArrayList;
import android.graphics.Point;

public class Figure {
	public int xPos, yPos;
	public int brickColor;
	protected ArrayList<Point> figBricksOriginal = new ArrayList<Point>();
	public ArrayList<Point> figBricksUsed;
	
	public void reset(int xStart) {
		xPos = xStart;
		yPos = 2;
		figBricksUsed = (ArrayList<Point>) figBricksOriginal.clone();
	}
	
	public void turnRight() {
		for (Point p : figBricksUsed) {
			int tmp = p.x;
			p.x = -p.y;
			p.y = tmp;
		}
	}
	
	public void turnLeft() {
		for (Point p : figBricksUsed) {
			int tmp = p.x;
			p.x = p.y;
			p.y = -tmp;
		}
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Figure figClone = new Figure();
		figClone.xPos = xPos;
		figClone.yPos = yPos;
		figClone.brickColor = brickColor;
		figClone.figBricksUsed = (ArrayList<Point>)figBricksUsed.clone();
		figClone.figBricksOriginal = (ArrayList<Point>)figBricksOriginal.clone();
		return figClone;
	}
	
	public Figure getShadowFig() {
		Figure figShadow = null;
		try {
			figShadow = (Figure)this.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		figShadow.brickColor = Colors.WHITE;
		return figShadow;
	}
}