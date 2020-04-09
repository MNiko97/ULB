package Model;

public abstract class Item extends GameObject {
	
	private int ID;

	public Item(int X, int Y, int color, int id) {
		super(X, Y, color);
		this.ID = id;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	@Override
	public boolean isObstacle() {
		return false;
	}

}
