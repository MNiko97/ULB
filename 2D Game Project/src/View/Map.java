package View;

import Model.Directable;


import Model.GameObject;
import Model.Item;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Map extends JPanel {
  
	private static final long serialVersionUID = 1L;
	private ArrayList<GameObject> objects = null;
	private ArrayList<Item> items = null;
	//private ArrayList<Item> inventoryItems = null;
	private BufferedImage spriteSheet;
	private BufferedImage playerSS;
	private BufferedImage enemySS;
	private BufferedImage thiefSS;
	private BufferedImage healpotionSS;
	private BufferedImage backpackSS;
	private BufferedImage weaponSS;
	
	private SpriteSheet blockTexture;
	private SpriteSheet player;
	private SpriteSheet enemy;
	private SpriteSheet thiefEnemy;
	private SpriteSheet healpotion;
	private SpriteSheet backpack;
	private SpriteSheet weapon;
	
    
    public Map() {
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void paint(Graphics g) {
    	
    	spriteSheet = ImageLoader.loadImage("/textures/blockTexture.png");
    	blockTexture = new SpriteSheet(spriteSheet);
    	
    	playerSS = ImageLoader.loadImage("/textures/player.png");
    	player = new SpriteSheet(playerSS);
    	
    	enemySS = ImageLoader.loadImage("/textures/enemy.png");
    	enemy= new SpriteSheet(enemySS);
    	
    	thiefSS = ImageLoader.loadImage("/textures/thiefenemy.png");
    	thiefEnemy = new SpriteSheet(thiefSS);
    	
    	healpotionSS = ImageLoader.loadImage("/textures/healpotion.png");
    	healpotion = new SpriteSheet(healpotionSS);
    	
    	backpackSS = ImageLoader.loadImage("/textures/backpack.png");
    	backpack = new SpriteSheet(backpackSS);
    	
    	weaponSS = ImageLoader.loadImage("/textures/weapon.png");
    	weapon = new SpriteSheet(weaponSS);
    	
    	//Génération du sol
        for (int i = 0; i < 20; i++) { 
            for (int j = 0; j < 20; j++) {
                int x = i;
                int y = j;
                g.drawImage(blockTexture.crop(0, 0, 48, 48), x*48, y*48, null);     
            }
        }
        
        //Génération des objets utilisables
        synchronized (items) {
        	for (GameObject items : this.items) {
        		int x = items.getPosX();
        		int y = items.getPosY();
        		int color = items.getColor();
        		
        		if (color == 0) { 
        			g.drawImage(healpotion.crop(0, 0, 48, 48), x*48, y*48, null);	//HealPotion
        		}
        		if (color == 1) {
        			g.drawImage(backpack.crop(0, 0, 48, 48), x*48, y*48, null); //BackPack
        		}
        		if (color == 3) {
        			g.drawImage(weapon.crop(0, 0, 48, 48), x*48, y*48, null); //Weapon
        		}
        	}
        }
        
        //Génération des objets
        synchronized (objects) {
        	for (GameObject object : this.objects) {
            	int x = object.getPosX();
            	int y = object.getPosY();
            	int color = object.getColor();

            	if (color == 0) {														// Unbreakable Block
                	g.drawImage(blockTexture.crop(48, 0, 48, 48), x*48, y*48, null);
            	} else if (color == 1) {            									// Breakable Block
            		g.drawImage(blockTexture.crop(48, 48, 48, 48), x*48, y*48, null);
            	} else if (color == 2) {												// Player
            		g.drawImage(player.crop(0, 0, 48, 48), x*48, y*48, null);											
            	} else if (color == 3) {												// Hit	
                	g.setColor(Color.RED);												
            	} else if (color == 5){													//Thief Enemy
            		g.drawImage(thiefEnemy.crop(0, 0, 48, 48), x*48, y*48, null);
        		}else if (color == 6 ) {												// Enemy
            		g.drawImage(enemy.crop(0, 0, 48, 48), x*48, y*48, null);
            	}else if (color == 4) {													// Door
            		//g.setColor(Color.GREEN);
            		g.drawImage(blockTexture.crop(0, 48, 48, 48), x*48, y*48, null);
            	}
            	
            	//Déssine la direction des entités
            	if(object instanceof Directable) {
                	int direction = ((Directable) object).getDirection();
                
                	int deltaX = 0;
                	int deltaY = 0;
                
                	switch (direction) {
                	case Directable.EAST:
                    	deltaX = +24;
                    	break;
                	case Directable.NORTH:
                    	deltaY = -24;
                    	break;
                	case Directable.WEST:
                    	deltaX = -24;
                    	break;
                	case Directable.SOUTH:
                    	deltaY = 24;
                    	break;
                	}

                	int xCenter = x * 50 + 24;
                	int yCenter = y * 50 + 24;
                	g.drawLine(xCenter, yCenter, xCenter + deltaX, yCenter + deltaY);
            	}
        	}
        }
    }

    public void setObjects(ArrayList<GameObject> objects) {
        this.objects = objects;
    }
    
    public void setItems(ArrayList<Item> items) {
    	this.items = items;
    }

    public void redraw() {
        this.repaint();
    }
}
