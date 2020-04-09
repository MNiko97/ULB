package Default;

import View.Window;
import Model.Game;

public class Main {
    public static void main(String[] args) {
        Window window = new Window();
        @SuppressWarnings("unused")
		Game game = new Game(window);
    }
}
