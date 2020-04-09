package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Model.Game;
import Model.Player;

public class Keyboard implements KeyListener {
	
    private Game game;
    private Player player;
    
    public Keyboard(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        switch (key) {
        case KeyEvent.VK_RIGHT:
        	player.rotate(1, 0);
        	if (game.checkObstacle(1, 0, this.player) == false) {
        		player.move(1, 0);   
        		player.isOnDoor();
        	}
        	game.notifyView();
            break;
        case KeyEvent.VK_LEFT:
        	player.rotate(-1, 0);
        	if (game.checkObstacle(-1, 0, this.player) == false) {
        		player.move(-1, 0);  
        		player.isOnDoor();
        	}
        	game.notifyView();
            break;
        case KeyEvent.VK_DOWN:
        	player.rotate(0, 1);
        	if (game.checkObstacle(0, 1, this.player) == false) {
        		player.move(0, 1);   
        		player.isOnDoor();
        	}
        	game.notifyView();
            break;
        case KeyEvent.VK_UP:
        	player.rotate(0, -1);
        	if (game.checkObstacle(0, -1, this.player) == false) {
        		player.move(0, -1);  
        		player.isOnDoor();
        	}
        	game.notifyView();
            break;
        case KeyEvent.VK_SPACE:
            game.action(this.player);	//Attaque au corps a corps.
            break;
        case KeyEvent.VK_P:
            player.showStats();
            break;
        case KeyEvent.VK_E:
        	player.takeObject(game);  	//ramasse un objet qui se trouve sur la Map.
        	break;
        case KeyEvent.VK_1:			
        	player.select(0);
        	player.useItem(0);        
        	break;
        case KeyEvent.VK_2:			
        	player.select(1);
        	player.useItem(1);        
        	break;
        case KeyEvent.VK_3:			
        	player.select(2);
        	player.useItem(2);        
        	break;
        case KeyEvent.VK_4:			
        	player.select(3);
        	player.useItem(3);        
        	break;
        case KeyEvent.VK_5:			
        	player.select(4);
        	player.useItem(4);        
        	break;
        }    
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
