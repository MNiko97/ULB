package Model;

public class Weapon extends Item implements Usable {
	
	private static final int DEFAULT_COLOR = 3;
	private static final int ID = 5;
	private static final int DAMAGE = 2;

	public Weapon (int X, int Y) {
		super(X, Y, DEFAULT_COLOR, ID);
	}
	
	//Mdifie les dégats infligés par le player
	public void use(Entity entity) {					
		int damage = entity.getDamage();
		entity.setDamage(damage + DAMAGE);
		System.out.println("DAMAGE : " + ((Player) entity).getDamage());
	}

}
