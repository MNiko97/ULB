 package Model;

public class BackPack extends Item implements Usable {
	
	private static final int DEFAULT_COLOR = 1;
	private static final int SPACE = 1;
	private static final int ID = 3;

	public BackPack(int X, int Y) {
		super(X, Y, DEFAULT_COLOR, ID);
	}
	
	//Augmente la capacité maximum de l'inventaire
	@Override
	public void use(Entity entity) {
		int inventorySize = entity.getInventorySize();
		if (inventorySize + SPACE <= 5) {
			entity.setInventorySize(inventorySize + SPACE);
			System.out.println("You can now stock " + (entity.getInventorySize() +1) + " objects");
		}
		else{
			System.out.println("Maximum capacity reached : 5 objects");
		}
	} 
}

