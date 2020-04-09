package Model;

import java.util.ArrayList;

public class Inventory {

	private int maxSize;
	private ArrayList<Item> inventory = new  ArrayList<Item>(); 
	private Entity entity;
	
	public int getSelect() {
		return select;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public void setSelect(int select) {
		this.select = select;
	}

	private int select=0;

	public Inventory (int inventorySize, Game game, Entity entity) {
		this.maxSize = inventorySize;
		this.entity = entity;
	}

	public boolean isFull(ArrayList<Item> inventory) {
		boolean full = false;
		if(inventory.size() == maxSize) {
			full = true;
		}
        return full;
    }
	
	public int getInventorySize() {
		return inventory.size();
	}

	public void setInventorySize(int inventorySize) {
		this.maxSize = inventorySize;
	}
    
    public ArrayList<Item> getInventory() {
		return inventory;
	}
    
    public void setInventory(ArrayList<Item> inventory) {
		this.inventory = inventory;
	}

	public Inventory removemethod(int i) {
		inventory.remove(i);
		return null;
	}
	
	/*
	 * Permet d'utiliser un item dans l'inventaire en fonction 
	 * du type d'objet grâce à son ID
	 */
	
	public void useItem() {
		if (inventory.size() != 0) {
			int id = inventory.get(select).getID();
			Usable obj = (Usable) inventory.get(select);
			switch(id) {
			case 1: 														//Heal Potion
				HealPotion healPotion = (HealPotion) obj;
				Player player = (Player) entity;
				if(!player.isAtMaxHP(healPotion)) {
					System.out.println("Healing ...");
					obj.use(this.entity);
					inventory.remove(select);
				}
				break;
			case 3 :													 	//BackPack
				System.out.println("New room for new stuff");
				obj.use(this.entity);
				inventory.remove(select);
				break;
			case 5: 														//Weapon
				System.out.println("Damage increased");
				obj.use(this.entity);
				inventory.remove(select);
				break;
			}
		}
	}
}	