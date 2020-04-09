package Model;

import java.util.ArrayList;

public class Player extends Entity{
	
	
	private static final int DEFAULT_COLOR = 2;
	private static final int DEFAULT_INVENTORY_SIZE = 1;
	private static final int DEFAULT_LEVEL = 0;
	private static final int DEFAULT_XP = 0;
	private static final int DEFAULT_DAMAGE = 2;
	private static final int DEFAULT_XSPAWN = 1;
	private static final int DEFAULT_HP = 100;
	private static final int DEFAULT_YSPAWN = 1;
	
	private Game game;
	private ArrayList<Item> playerInventory;
	private Inventory inventory;
	private int xp;
	private int level;
	private boolean isMaxHP = true;
	private int maxHP = DEFAULT_HP;

	public Player(Game game) {
		super(DEFAULT_XSPAWN, DEFAULT_YSPAWN, DEFAULT_HP, DEFAULT_DAMAGE, DEFAULT_COLOR, DEFAULT_INVENTORY_SIZE, game);
		this.inventory = this.addInventory();
		this.game = game;
		this.playerInventory = inventory.getInventory();
		this.xp = DEFAULT_XP;
		this.level = DEFAULT_LEVEL;
	}
	
	/*
	 * Téléporte le joueur si la porte est déverouillée 
	 * et que le pllayer se trouve sur la porte
	 */
	
	public void isOnDoor() {
		if (this.getPosX() == game.getDoor().getPosX() && this.getPosY() == game.getDoor().getPosY()) {
			game.getDoor().teleport();
		}
	}
	
	//Donne l'information si le player peut utilser une potion
	public boolean isAtMaxHP(HealPotion healPotion) {
		isMaxHP = true;
		if (this.getHP() + healPotion.getHealPoint() <= maxHP) {
			isMaxHP = false;
		}
		return isMaxHP;
	}
	
	public void select(int selection) {
		inventory.setSelect(selection);
	}

	public void useItem(int select) {
		if (playerInventory.size() >= select + 1) {
			inventory.useItem();
		}
		
	}
	
	synchronized public void takeObject(Game game) {
		ArrayList<Item> items = game.getItems();
		for (int i = items.size()-1; i >= 0; i--) {
			if (items.get(i).isAtPosition(this.getPosX(), this.getPosY()))
                if (!inventory.isFull(playerInventory)) {
                	playerInventory.add(items.get(i));
					items.remove(i);
					game.notifyView();
                }
                else{
                	System.out.println("You have too much object in your backpack !");
                }
			}
		System.out.println("Your Inventory : " + playerInventory);
		}
	
	public void levelUp () {
		if (xp >= 100) {
			level += 1;
			xp -= 100;
			maxHP += 10;
			System.out.println("NEW LEVEL : " + level);
			System.out.println("MAX HP + 10 : " + this.maxHP);
		}
	}
	
	public void showStats() {
        System.out.println("HP : " + this.getHP() + "/" + this.maxHP);  
        System.out.println("DAMAGE : " + this.getDamage());
        System.out.println("LEVEL : " + this.getLevel() + "   XP : " + this.getXp() + "/100");
	}
	
	/*
	 * Getter && Setter
	 */
	
	public ArrayList<Item> getPlayerInventory() {
		return playerInventory;
	}

	public void setPlayerInventory(ArrayList<Item> playerInventory) {
		this.playerInventory = playerInventory;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}
	
	public int getMaxHP() {
		return maxHP;
	}
}
