package dk.cmol.tetris;

import dk.cmol.tetris.figures.Figure;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class Game extends BasicGame {
	
	int xBlocks = 200;
	int yBlocks = 0;
	int widthBlocks, heightBlocks;
	Drawable tBlocks;
	private final int FALL_INTERVAL = 10;
	private int fallCounter = 0;
	
	int xRef, yRef;
	
	boolean turned = false;
	
	public Game(TetrisActivity ta, int rows, int cols) {
		super(ta, rows, cols);
		tBlocks = tetrisAct.getResources().getDrawable(R.drawable.t_blocks);
		widthBlocks = tBlocks.getMinimumWidth();
		heightBlocks = tBlocks.getMinimumHeight();
		
		currentFig = figures[figRand.nextInt(NUM_FIGURES)];
		txtScore = (TextView)ta.findViewById(R.id.score);
	}

	public boolean onTouch(View v, MotionEvent event) {
		int xNew = (int) event.getX();
		int yNew = (int) event.getY();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xRef = xNew;
			yRef = yNew;
			break;
		case MotionEvent.ACTION_MOVE:
			
			// Move left
			if ((xNew - xRef) > playScreen.BRICKSIZE) {
				xRef = xNew;
				currentFig.xPos++;
				if (playScreen.isCollision(currentFig)) {
					currentFig.xPos--;
				}
			}
			// Move right
			else if ((xRef - xNew) > playScreen.BRICKSIZE) {
				xRef = xNew;
				currentFig.xPos--;
				if (playScreen.isCollision(currentFig)) {
					currentFig.xPos++;
				}
			}
			
			// Move up on touch screen to turn currentFig right
			if ((yRef - yNew) > playScreen.BRICKSIZE * 2 ) {
				if (turned == false) {
					currentFig.turnRight();
					if (playScreen.isCollision(currentFig)) currentFig.turnLeft();
					turned = true;
				}
			}
			
			// Move down on the touch screen to pull currentFig down
			if ((yNew - yRef) > playScreen.BRICKSIZE ) {
				currentFig.yPos++;
				yRef = yNew;
				if (playScreen.isCollision(currentFig)) currentFig.yPos--;
			}
			break;
		
		case MotionEvent.ACTION_UP:
			turned = false;
			break;

		default:
			break;
		}
		
		return true;
	}


	@Override
	public void update(Canvas c) {
		// Shadow Figure
		Figure shadowFigure = currentFig.getShadowFig();
		
		do {
			shadowFigure.yPos++;
		} while (!playScreen.isCollision(shadowFigure));
		shadowFigure.yPos--;
		playScreen.placeFigure(shadowFigure, false);
		
		// Current things
		//playScreen.fillScreenArrayRandom();
		if (!quit) {
			playScreen.placeFigure(currentFig, false);
		}
		playScreen.paintScreen(c, this);
		playScreen.removeFigure();
		
		fallCounter++;
		if (fallCounter == FALL_INTERVAL) {
			fallCounter = 0;
			currentFig.yPos++;
			
			// Check if the figure is landed 
			if (playScreen.isCollision(currentFig)) {
				currentFig.yPos--;
				playScreen.placeFigure(currentFig, true);
				currentFig.reset(COLUMNS/2);
				currentFig = figures[figRand.nextInt(NUM_FIGURES)];
				if (playScreen.isCollision(currentFig)) {
					quit = true;
				}
				score += playScreen.lineScore();
				txtScore.setText("Score: "+ score);
			}
		}
	}
}