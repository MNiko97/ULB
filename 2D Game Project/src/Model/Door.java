package Model;

public class Door extends GameObject {
	
	private static final int DEFAULT_COLOR = 4;
	private boolean locked = true;
	
	private Game game;
	
	public Door(int X, int Y, Game game) {
		super(X, Y, DEFAULT_COLOR);
		this.game = game;
	}

	@Override
	public boolean isObstacle() {
		return false;
	}
	
	public boolean isLocked () {
		return locked;
	}
	
	/*
	 * Teleporte le joueur dans le prochain niveau en gardant les statistiques du joueur
	 * et augemente le nombre d'ennemis en fonction du niveau
	 */
	public void teleport () {						
		if (!locked) {
			Player player = game.getPlayer();
			game.setLevel(game.getLevel() + 1);
			game.getGameObjects().clear();
			game.newPlayer(player);
			game.getItems().clear();
			game.setRegularEnemy(game.getRegularEnemy() + 1);
			if (game.getLevel() % 5 == 0) {
				game.setSpecialEnemy(game.getSpecialEnemy() + 1);
			}
			game.drawMap();
			game.drawObject();
		}
	}
	public void setLocked(boolean locked) {				
		this.locked = locked;
	}

}


