package dk.cmol.tetris;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

public class TetrisActivity extends Activity {
    /** Called when the activity is first created. */
    
	MediaPlayer mp;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Make the window fullscreen by removing title bar and setting fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.main);
        
        mp = MediaPlayer.create(this, R.raw.django_tetris);
        
    }
    
    public void startGame(View v) {
    	int rows = Integer.parseInt(((EditText)findViewById(R.id.txtRows)).getText().toString());
    	int cols = Integer.parseInt(((EditText)findViewById(R.id.txtCols)).getText().toString());
    	setContentView(R.layout.gamelayout);
    	Game game = new Game(this, rows, cols);
    	((LinearLayout)findViewById(R.id.gameLinearLayout)).addView(game);
    	
    	mp.start();
    	mp.setLooping(true);
    	
    	Thread gameThread = new Thread(game);
    	gameThread.start();
    }
    
    public void endGame(View v) {
    	endGame();
    }
    
    public void endGame() {
    	setContentView(R.layout.main);
    	mp.stop();
    	mp = null;
    	mp = MediaPlayer.create(this, R.raw.django_tetris);
    }
    
    @Override
    public void onBackPressed() {
    	endGame();
    return;
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	mp.stop();
    	mp = null;
    	mp = MediaPlayer.create(this, R.raw.django_tetris);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	setContentView(R.layout.main);
    }
}