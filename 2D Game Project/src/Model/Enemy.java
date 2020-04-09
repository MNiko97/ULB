package Model;

import java.util.Random;

public class Enemy extends Entity implements Runnable {
	
	private static final int DEFAULT_HEALTH = 10;
	private static final float DEFAULT_RANGE = 8;
	private static final int DEFAULT_INVENTORY_SIZE = 1;
	private static final int DEFAULT_XP_CAPACITY = 25;
	private static final int DEFAULT_DAMAGE = 1;
	
	private Game game;
	private long speed;
	private boolean running = false;
	private Thread thread;
	
	public Enemy(int x, int y, int color, Game game) {
		super(x, y, DEFAULT_HEALTH, DEFAULT_DAMAGE, color, DEFAULT_INVENTORY_SIZE, game);
		this.game = game;
		
	}
	
	public long getSpeed() {
		return speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}
	
	public void giveXP () {				
		this.getHP();
		loot();
		Player player = game.getPlayer();
		player.setXp(player.getXp() + DEFAULT_XP_CAPACITY);
		System.out.println("XP : " + player.getXp() + " /100");
		player.levelUp();
		
	}
	
	//Ajoute un item aléatoire sur la dépouille d'un ennemi
	public void loot() {
		Random rand = new Random();
		int probability = rand.nextInt(20);
		
		if (probability <=1) {
			game.addItem(new Weapon(this.getPosX(), this.getPosY()));
		}
		if (probability >=2 && probability <=6) {
			game.addItem(new HealPotion(this.getPosX(), this.getPosY()));
		}
		if (probability == 10) {
			game.addItem(new BackPack(this.getPosX(), this.getPosY()));
		}
		else {
		}
		
	}
	
	//Calcul la distance du joueur par rapport à l'ennemi à vole par vol d'oiseau
	public float distancePlayer(int xEnemy, int yEnemy, Game game) {				
		int xPlayer = game.getGameObjects().get(0).getPosX();
		int yPlayer = game.getGameObjects().get(0).getPosY();
		this.game = game;
		
		return  (float) Math.sqrt(Math.pow(xEnemy - xPlayer,2) + (Math.pow(yEnemy - yPlayer,2)));
	}
	
	//méthode permettant à l'ennemi de se déplacer dans l'espace aléatoirement
	synchronized public void randomMove(int chaseMovement) {					
		
		int randomMovement = -1;
		if (chaseMovement == 4) {
			Random rand = new Random();
			randomMovement = rand.nextInt(4);
		}
		else {
			randomMovement = chaseMovement;
		}
		
		//rotation suivi d'un mouvement
		switch (randomMovement) {												
			case 0:
	        	if (game.checkObstacle(1, 0, this) == false) {
	        		this.rotate(1, 0);
	        		this.move(1, 0);	
	        	}

	        	game.notifyView();
	            break;
			case 1:
	        	if (game.checkObstacle(-1, 0, this) == false) {
	        		this.rotate(-1, 0);
	        		this.move(-1, 0);	 
	        	}
	        	
	        	game.notifyView();
				break;
			case 2:
	        	if (game.checkObstacle(0, 1, this) == false) {
	        		this.rotate(0, 1);
	        		this.move(0, 1);	

	        	}
	        	
	        	game.notifyView();
				break;
			case 3:
	        	if (game.checkObstacle(0, -1, this) == false) {
	        		this.rotate(0, -1);
	        		this.move(0, -1);	  
	        	}
	        	
	        	game.notifyView();
				break;
			}
	}
	
	//Chase le player selon le chemin qu'il est susceptible de parcourir
	synchronized public void chasingPlayer() {   
		
		float actualDistance = distancePlayer(this.getPosX(), this.getPosY(), this.game);
		if (distancePlayer(this.getPosX() + 1, this.getPosY(), this.game) < actualDistance && game.checkObstacle(1, 0, this) == false) {
			randomMove(0);			
		}
		else if (distancePlayer(this.getPosX() - 1, this.getPosY(), this.game) < actualDistance && game.checkObstacle(-1, 0, this) == false) {
			randomMove(1);			
		}
		else if (distancePlayer(this.getPosX(), this.getPosY() + 1, this.game) < actualDistance && game.checkObstacle(0, 1, this) == false) {
			randomMove(2);
			if (actualDistance <= 1) {
				inDirection();
		}
		}
		else if (distancePlayer(this.getPosX(), this.getPosY() - 1, this.game) < actualDistance && game.checkObstacle(1, -1, this) == false) {
			randomMove(3);			
		}		
		if (actualDistance <= 1) {
			inDirection();
		}
	}

	//Réoriante l'ennemi dans la direction du player
	public void inDirection() {											
		int xPlayer = game.getGameObjects().get(0).getPosX();
		int yPlayer = game.getGameObjects().get(0).getPosY();
		int xEnemy = this.getPosX();
		int yEnemy = this.getPosY();
		
		if ((game.checkObstacle(1, 0, this) == true) && (xPlayer - xEnemy) >= 1 ) {
			this.rotate(1, 0);
		}
			
		else if (game.checkObstacle(-1, 0, this) == true && (xPlayer - xEnemy) <= 1) {
			this.rotate(-1, 0);
		}
				
		else if (game.checkObstacle(0, 1, this) == true && (yPlayer - yEnemy) >= 1) {
			this.rotate(0, 1);	
		}
			
		else if (game.checkObstacle(0, -1, this) == true && (yPlayer - yEnemy) <= 1) {
			this.rotate(0, -1);	
		}
	}
		
		
	//Thread générant les ennemis et leur comportement
	public void run() {	
	    while(running) {												
	    	if (this.getHP() <= 0 || game.getPlayer().getHP() <= 0) {
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
	    	}	
	    	
	    	else {
	    		randomMove(4);
	    		try {
	    			Thread.sleep(1000);
	    			game.notifyView();
	    		} catch (InterruptedException e) {
	    			e.printStackTrace();
	    		}game.action(this);
	    	}
	    }
	}
	    
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	//Démarre le thread de l'ennemi
	public void start() {
		if(running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	//Eteind le thread de l'ennemi
	public void stop() {
		if (!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}		