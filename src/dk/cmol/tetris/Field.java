package dk.cmol.tetris;

public class Field {
	
	private int brickColor;
	private boolean permanent;
	
	public Field(int color, boolean perm) {
		brickColor = color;
		permanent = perm;
	}
	
	public int getColor() {
		return brickColor;
	}
	
	public boolean isPermanent() {
		return permanent;
	}
}