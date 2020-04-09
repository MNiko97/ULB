package View;

import Model.GameObject;
import Model.Item;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Window {
	
    private Map map = new Map();

    public Window() {
        JFrame window = new JFrame("2D Java Game Project");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1920, 1080);
        window.getContentPane().setBackground(Color.gray);
        window.getContentPane().add(this.map);
        window.setLocationRelativeTo(null); 
        window.setVisible(true);
    }

    public void update() {
        this.map.redraw();
    }

    public void setKeyListener(KeyListener keyboard) {
        this.map.addKeyListener(keyboard);
    }

	public void setGameObjects(ArrayList<GameObject> objects) {
        this.map.setObjects(objects);
        this.map.redraw();
    }
	
	public void setItems(ArrayList<Item> items) {
		this.map.setItems(items);
        this.map.redraw();
	}
}
