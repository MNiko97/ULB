package Model;

import java.util.ArrayList;

public class BlockBreakable extends Block implements Deletable, Activable {

    private ArrayList<DeletableObserver> observers = new ArrayList<DeletableObserver>();
    private int hp;
    private Game game;

	public BlockBreakable(int X, int Y, int hp, int color, Game game) {
        super(X, Y, color);
        this.hp = hp; 
        this.game = game;
    }
	
    public void setHP(int hp) {
		this.hp = hp;
	}
    
    public int getHP() {
		return hp;
	}

    //Détruis l'objet cassable concerné
    public void crush(){
        notifyDeletableObserver();
    }

    @Override
    public void attachDeletable(DeletableObserver po) {
        observers.add(po);
    }

    @Override
    public void notifyDeletableObserver() {
        @SuppressWarnings("unused")
		int i = 0;
        for (DeletableObserver o : observers) {
            i++;
            o.delete(this, null);
        }
    }

    @Override
    public boolean isObstacle() {
        return true;
    }
    
    //Intéraction avec les objets de type activable
	public void activate(){												
		int entityActor = game.getEntityType();
		int entityTarget = game.getEntityTarget();
		int hit = 0;
		Player player = game.getPlayer();
		Enemy enemy = game.getEnemy();
		
		
        if (entityTarget == 1) {			 									//Si la cible est de type Enemy
        	if (hp == 1){ 														
        		enemy.giveXP();  
        		crush();
        		if (game.getEnemyLeft() <= 0) {
        			game.getDoor().setLocked(false);
        		}
        		enemy.setHP(0);
        	}
        	else {
        		hp -= player.getDamage();
            	int defaultColor = this.getColor();
        		try {
        			this.color = 3;
        			Thread.sleep(100);
        			this.color = defaultColor;
        		} catch (InterruptedException e) {
        			e.printStackTrace();
        		}
        		if (hp <= 0) {
            		enemy.giveXP();
        			crush();
            		if (game.getEnemyLeft() <= 0) {
            			game.getDoor().setLocked(false);
            		}
            		enemy.setHP(0);
        		}
        	}
    	}
        
        else { 																	//Si la cible est du type Block
        	if (entityActor == 0) { 											//Si l'acteur est le Joueur
        		hit = player.getDamage();	
        	}
        	else { 																//Si l'acteur est de type Enemy
        		hit = enemy.getDamage();
        		System.out.println("HP : " + (hp-hit) + "/" + player.getMaxHP());
        		if (hp-hit <= 0) {
        			System.out.println("You Lose");
        		}
        	}
        	hp -= hit;
        	int defaultColor = this.getColor();
    		try {
    			this.color = 3;
    			Thread.sleep(100);
    			this.color = defaultColor;
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		if (hp <= 0) {
    			crush();
    		}
        }	
    }
}