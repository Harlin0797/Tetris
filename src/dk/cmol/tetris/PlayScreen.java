package dk.cmol.tetris;

import java.util.Random;

import dk.cmol.tetris.figures.Figure;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class PlayScreen {

	final int ROWS, COLUMNS;
	Field [][] screenArray;
	Drawable[] images = new Drawable[8];
	Random ran = new Random();
	public final int BRICKSIZE;
	Rect c = new Rect();
	boolean tetris = false;
	
	// Constructor, takes a context, and min/max, rows/cols, and creates the 2D-array
	public PlayScreen(TetrisActivity ta, int rows, int cols) {
		ROWS = rows;
		COLUMNS = cols;
		
		screenArray = new Field[ROWS][COLUMNS];
		Resources r = ta.getResources();
		
		images[0] = r.getDrawable(R.drawable.red);
		images[1] = r.getDrawable(R.drawable.green);
		images[2] = r.getDrawable(R.drawable.blue);
		images[3] = r.getDrawable(R.drawable.purple);
		images[4] = r.getDrawable(R.drawable.yellow);
		images[5] = r.getDrawable(R.drawable.cyan);
		images[6] = r.getDrawable(R.drawable.brown);
		images[7] = r.getDrawable(R.drawable.edge);
		
		BRICKSIZE = images[0].getMinimumWidth() * 2;
	}
	
	// Fills screenArray with random data just for testing
	public void fillScreenArrayRandom() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				int color = ran.nextInt(8);
				screenArray[row][col] = (color == 7 ? null : new Field(color, true));
			}
		}
	}
	
	// Draws the screen
	public void paintScreen(Canvas c, Game game) {
		int widthView = game.getWidth();
		int heightView = game.getHeight();
		
		int leftX = (widthView - COLUMNS * BRICKSIZE) / 2;
		int topY = (heightView - ROWS * BRICKSIZE) / 2;
		
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(3);
		c.drawRect(leftX, topY, leftX + COLUMNS*BRICKSIZE, topY+ ROWS*BRICKSIZE, paint);
		
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				Field f = screenArray[row][col];
				if(f != null) {
					Drawable image = images[f.getColor()];
					image.setBounds(leftX + col*BRICKSIZE,
									topY + row*BRICKSIZE,
									leftX + col*BRICKSIZE + BRICKSIZE,
									topY + row*BRICKSIZE + BRICKSIZE);
					image.draw(c);
				}
			}
		}
	}
	
	// Removes full lines, moves lines down, and returns score
	public int lineScore() {
		boolean rowFull;
		int score = 0;
		
		// Loops through the rows from the top, to check if they are full
		for (int row = 0; row < ROWS; row++) {
			rowFull = true;
			
			// Check if the row is full
			for (int col = 0; col < COLUMNS; col++) {
				if (screenArray[row][col] == null) {
					rowFull = false;
					break;
				}
			}
			
			// If the row was full
			if (rowFull) {
				
				// Add to the score
				score++;
				
				// Cycle back up through the loop back up through the rows
				for (int rowMove = row; rowMove > 0; rowMove--) {
					// Row for row, move everything 1up
					for (int colMove = 0; colMove < COLUMNS; colMove++) {
						screenArray[rowMove][colMove] = screenArray[rowMove-1][colMove];
					}
					
					// Fill the first line with nulls
					for (int colInRow = 0; colInRow < COLUMNS; colInRow++) {
						screenArray[0][colInRow] = null;
					}
				}
			}
		}
		
		int totalScore = (score >= 4) ? (tetris ? 1200 : 800) : 100 * score;
		tetris = (score >= 4) ? true : false;
		return totalScore;
	}
	
	public void placeFigure(Figure fig, boolean perm) {
		for (Point p : fig.figBricksUsed) {
			screenArray[fig.yPos+p.y][fig.xPos+p.x] = new Field(fig.brickColor, perm);
		}
	}
	
	public void removeFigure() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				if (screenArray[row][col] != null && !screenArray[row][col].isPermanent()) {
					screenArray[row][col] = null;
				}
			}
		}
	}
	
	public boolean isCollision(Figure f) {
		boolean collision = false;
		for (Point p : f.figBricksUsed) {
			if (p.x+f.xPos < 0 || p.x+f.xPos > COLUMNS-1 || p.y+f.yPos > ROWS -1) {
				collision = true;
				break;
			}
			
			if(!collision && screenArray[p.y+f.yPos][p.x+f.xPos] != null) {
				collision = true;
			}
		}
		return collision;
	}
}