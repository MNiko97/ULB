
package Model;

public class Entity extends BlockBreakable implements Directable {

    private int direction = EAST;  
	private int inventorySize;
	private Inventory inventory;
	private Game game;
	private int damage;
	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Entity(int x, int y, int health, int damage, int color, int inventorySize, Game game) {	
		super(x, y, health, color, game);    
    	this.inventorySize = inventorySize;
    	this.game = game;
    	this.damage = damage;
	}
	
	//Crée un inventaire pour chaque Entity
	public Inventory addInventory() {										
		Inventory inventory = new Inventory(inventorySize, this.game, this);
		this.setInventory(inventory);
		return inventory;
	}
	
	public int getInventorySize() {
		return inventorySize;
	}

	public void setInventorySize(int inventorySize) {
		inventory.setMaxSize(inventorySize);
	}
	
	public void useWeapon() {
		int id =getInventory().getInventory().get(0).getID();
		switch(id) {
		case 1:
			
		}
	}
    
    public void move(int X, int Y) {
        this.posX = this.posX + X;
        this.posY = this.posY + Y;
    }

    public void rotate(int x, int y) {
        if(x == 0 && y == -1)
            direction = NORTH;
        else if(x == 0 && y == 1)
            direction = SOUTH;
        else if(x == 1 && y == 0)
            direction = EAST;
        else if(x == -1 && y == 0)
            direction = WEST;
    }

 
    public boolean isObstacle() {
        return true;
    }

    @Override
    public int getDirection() {
    return direction;
    }
    
    //Retourne la position X et Y de la case à "sight" blocks en face de l'entité
    public int getFrontX(int sight) {
        int delta = 0;
        if (direction % 2 == 0){
            delta += 1 - direction;
        }
        return this.posX + (delta*sight);
    }

    public int getFrontY(int sight) {
        int delta = 0;
        if (direction % 2 != 0){
            delta += direction - 2;
        }
        return this.posY + (delta*sight);
    }
	
    
}