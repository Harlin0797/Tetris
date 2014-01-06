package dk.cmol.tetris;

import java.util.Random;

import dk.cmol.tetris.figures.*;
import android.graphics.Canvas;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public abstract class BasicGame extends View implements OnTouchListener, Runnable
{
	boolean quit = false;
	int sleepMillis = 50;
	TetrisActivity tetrisAct;
	PlayScreen playScreen;
	int NUM_FIGURES = 7;
	
	Figure[] figures = new Figure[NUM_FIGURES];
	Figure currentFig;
	
	Random figRand;
	
	final int COLUMNS;
	
	TextView txtScore;
	int score = 0;
	
	public BasicGame(TetrisActivity ta, int rows, int cols) {
		super(ta);
		tetrisAct = ta;
		playScreen = new PlayScreen(ta, rows, cols);
		
		figures[0] = new FigI(cols/2);
		figures[1] = new FigJ(cols/2);
		figures[2] = new FigL(cols/2-1);
		figures[3] = new FigO(cols/2-1);
		figures[4] = new FigS(cols/2-1);
		figures[5] = new FigT(cols/2-1);
		figures[6] = new FigZ(cols/2-1);
		//figures[7] = new FigII(cols/2);
		
		figRand = new Random();
		
		COLUMNS = cols;
		
		this.setOnTouchListener(this);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		update(canvas);
	}
	
	public void run() {
		while (!quit) {
			postInvalidate();
			try {
				Thread.sleep(sleepMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public abstract void update(Canvas c);
}