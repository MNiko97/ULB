package Model;

import View.Window;
import Controller.Keyboard;

import java.util.ArrayList;
import java.util.Random;

public class Game implements DeletableObserver, Runnable {
	
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private Window window;
    private int size = 20;
    
	private int regularEnemy = 0;
    private int specialEnemy = 1;
    private int enemyLeft = 0;
    private int entityActor = -1;
    private int entityTarget = -1;
    private int level = 1;
    private Player player;
    private Enemy enemy;
    private ThiefEnemy thiefEnemy;
    private Thread thread;
    private Door door;
      
    public Game(Window window) {
    	this.window = window;
        Player player = new Player(this);
        this.player = player;
        newPlayer(player);
        Keyboard keyboard = new Keyboard(this, player);
        window.setKeyListener(keyboard);
        thread = new Thread(this);
		thread.start();
    }
    
    //Thread principal du jeu
    @Override
	public void run() {
		drawMap();
    	drawObject();
    	while (player.getHP() >= 1) {
    		if (player.getHP() <= 0) {
    			break;
    		}
    	}
    	try {
    		
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void drawMap () {
    	for (int i = 0; i < size; i++) {
            objects.add(new BlockUnbreakable(i, 0));
            objects.add(new BlockUnbreakable(0, i));
            objects.add(new BlockUnbreakable(i, size - 1));
            objects.add(new BlockUnbreakable(size - 1, i));
        }
    	
        for (int x = 2; x < size-2; x++) {
        	for (int y = 2; y < size-2; y++) {			
        		Random rand = new Random();													
        		int blockProbability = rand.nextInt(5);									//Génération aléatoire du nombre de blocs
        		int blockTypeProbability = rand.nextInt(6);								//Génération aléatoire du type bloc (Cassable/Incassable)
        		if (blockProbability <= 0) {
        			if (blockTypeProbability < 2) {
        			BlockBreakable bBlock = new BlockBreakable(x, y, 2, 1, this);
        			bBlock.attachDeletable(this);
        			objects.add(bBlock);
        			}
        			else {
        				BlockUnbreakable uBlock = new BlockUnbreakable(x, y);
        				objects.add(uBlock);
        			}
        		}
        	}
        }
        
    }
    
    public void drawObject() {
        
        // Création d'ennemi simple
        for (int i = 0; i < regularEnemy; i++) {
        	Random spawn = new Random();
        	int xSpawn = spawn.nextInt(16)+1;
        	int ySpawn = spawn.nextInt(16)+1;
        	Enemy enemy = new Enemy(xSpawn, ySpawn,6 , this);
        	enemy.attachDeletable(this);
        	objects.add(enemy);
        	this.enemy = enemy;
        	enemy.start();
        }
        enemyLeft += regularEnemy;
        
        //Création d'ennemi de type ThiefEnemy
        for (int i = 0; i < specialEnemy; i++) {
        	Random spawn = new Random();
        	int xSpawn = spawn.nextInt(16)+1;
        	int ySpawn = spawn.nextInt(16)+1;
        	ThiefEnemy thiefenemy = new ThiefEnemy(xSpawn, ySpawn, this);
        	thiefenemy.attachDeletable(this);
        	objects.add(thiefenemy);
        	this.thiefEnemy = thiefenemy;
        	thiefenemy.start();
        	
        }
        enemyLeft += specialEnemy;
        
    	//Création de la porte
        Door door = new Door(18, 1, this);
        this.door = door;
        objects.add(door);  
        window.setGameObjects(this.getGameObjects());
        window.setItems(this.getItems());
        notifyView();
    }
   
    //Ajout du player
	public void newPlayer(Player player) {
		player.attachDeletable(this);
		objects.add(player);
	}
	
	
	
	
    synchronized public boolean checkObstacle (int x, int y, Entity entity) {
	   
        int nextX = entity.getPosX() + x;
        int nextY = entity.getPosY() + y;

        boolean obstacle = false;
        for (int i = objects.size()-1; i >= 0; i--) {
            if (objects.get(i).isAtPosition(nextX, nextY)) {
                obstacle = objects.get(i).isObstacle();
            }    
        }
        if (obstacle == true) {
            return true;
        }
        else {
        	return false;
        }	
    }

    synchronized public void action(Entity entity) {									//Fonction qui permet d'intéragir avec le décor, enemy-player, player-enemy
    	
        Activable aimedObject = null;
        
        if (entity.getClass() == Player.class){
        	entityActor = 0; //Player
        	for(int i = objects.size()-1; i >= 0; i--){
        		if(objects.get(i).isAtPosition(entity.getFrontX(1),entity.getFrontY(1)) && objects.get(i) instanceof Activable){
        			aimedObject = (Activable) objects.get(i);
        			entityTarget = 0;
        			if (objects.get(i).getClass() == Enemy.class || objects.get(i).getClass() == ThiefEnemy.class) {
        				entityTarget = 1; 							//Enemy
        				enemy = (Enemy) objects.get(i);
        			}
        		}
        	}
        }
        
        else {
        	entityActor = 1; //Enemy
        	entityTarget = 0;
        	for (int i = objects.size()-1; i >= 0; i--) {
        		if(objects.get(i).isAtPosition(entity.getFrontX(1),entity.getFrontY(1)) && objects.get(i).getClass() == Player.class && objects.get(0) instanceof Activable){
        			if (entity.getClass() == ThiefEnemy.class) {
        				thiefEnemy.robPlayer();											//ThiefEnemy ne peut que voler le joueur
        			}
        			else{
        				aimedObject = (Activable) objects.get(i); 
        			}
        						
        		}
        	}
        }
        
		if(aimedObject != null){
		    aimedObject.activate();
            notifyView();
		}
    }

	public void notifyView() {
        window.update();
    }

    @SuppressWarnings("unlikely-arg-type")
	@Override
    synchronized public void delete(Deletable ps, ArrayList<GameObject> loot) {
        objects.remove(ps);
        if (loot != null) {
            objects.addAll(loot);
        }
        notifyView();
        if (ps.getClass() == Enemy.class || ps.getClass() == ThiefEnemy.class) {
        	enemyLeft --;
        }
    }
    
	public void showInfo(Player player) {
        System.out.println("(x : " +player.getFrontX(2) + ", y : " + player.getFrontY(2) + ")");
        System.out.println("Player life : " + player.getHP());  
        System.out.println(player.getPosX() + ":" + player.getPosY());
	}
	
	public void addItem(Item obj) {
		System.out.println("Object to add : " + obj);
		items.add(obj);
		notifyView();
	}
	
	
	/*
	 * Getter && Setter
	 */
	
    public ArrayList<GameObject> getGameObjects() {
        return this.objects;
    }
    
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public ArrayList<Item> getItems() {
        return this.items;
    }
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

    public int getEntityTarget() {
		return entityTarget;
	}
    
    public Door getDoor() {
		return door;
	}
    
    public int getEnemyLeft() {
		return enemyLeft;
	}

	public void setEnemyLeft(int enemyLeft) {
		this.enemyLeft = enemyLeft;
	}

	public Enemy getEnemy() {
		return enemy;
	}

	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}

	public int getEntityType() {
		return entityActor;
	}

	public void setEntityType(int entityType) {
		this.entityActor = entityType;
	}
	
	public int getSpecialEnemy() {
		return specialEnemy;
	}

	public void setSpecialEnemy(int specialEnemy) {
		this.specialEnemy = specialEnemy;
	}
	
	public void setRegularEnemy(int regularEnemy) {
		this.regularEnemy = regularEnemy;
	}

	public int getRegularEnemy() {
		return regularEnemy;
	}
}