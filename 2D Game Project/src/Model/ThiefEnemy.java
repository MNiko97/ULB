package Model;

import java.util.ArrayList;
import java.util.Random;

public class ThiefEnemy extends Enemy {
	
	private static final float DEFAULT_RANGE = 8;
	private static final int DEFAULT_COLOR = 5;
	private Game game;
	
	public ThiefEnemy(int x, int y, Game game) {
		super(x, y, DEFAULT_COLOR, game);
		this.game = game;
	}
	
	//Vole et détruis un élément de l'inventaire du player
	public void robPlayer () {								
		Player player = game.getPlayer();
		ArrayList<Item> playerInventory = player.getPlayerInventory();
		int inventorySize = player.getInventory().getInventorySize();
		if (inventorySize != 0) {
			Random rand = new Random();
			int randi = rand.nextInt(1);
			playerInventory.remove(randi);
			player.setPlayerInventory(playerInventory);
			System.out.println("You've been robbed ! " + "Your inventory : " + player.getPlayerInventory());
		}
		else
		{
		}
	}
	
	//Comportement de ThiefEnemy
	public void run() {
	    while(isRunning()) {
	    	if (this.getHP() <= 0 || game.getPlayer().getHP() <=0) {
	    		stop();
	    	}
	    	
	    	float d = distancePlayer(this.getPosX(), this.getPosY(), this.game);
	   
	    	
	    	if (d <= DEFAULT_RANGE) {
	    		chasingPlayer();
	    		try {
	    			Thread.sleep(500);
	    			game.notifyView();
	    		} catch (InterruptedException e) {
	    			e.printStackTrace();
	    		}
	    		game.action(this);
	    		d = distancePlayer(this.getPosX(), this.getPosY(), this.game);
	    			
	    	}	
	    	
	    	else {
	    		randomMove(4);
	    		try {
	    			Thread.sleep(1000);
	    			game.notifyView();
	    		} catch (InterruptedException e) {
	    			e.printStackTrace();
	    		}
	    		game.action(this);
	    	}
	    }
	}
}

