package Model;

public class HealPotion extends Item implements Usable {
	
	private static final int DEFAULT_COLOR = 0;
	private static final int HEAL_POINT = 30;
	private static final int ID = 1;
	
	public HealPotion(int X, int Y) {
		super(X, Y, DEFAULT_COLOR, ID);
	}

	//Augmente la vie du player
	public void use(Entity entity) {		
		int health = entity.getHP();
		health += HEAL_POINT;
		entity.setHP(health);
		System.out.println("HP : " + entity.getHP() + "/" + ((Player) entity).getMaxHP());
	}

	public int getHealPoint() {
		return HEAL_POINT;
	}
}