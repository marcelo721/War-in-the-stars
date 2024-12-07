package game.application;

import game.model.levelOne.Level;

import javax.swing.*;

public class WarInTheStars extends JFrame {

    private Level level;
    private final Menu menu;

    public WarInTheStars(){

        menu = new Menu();
        add(menu);

        setTitle("war in the stars");
        setSize(1024, 728);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        this.setResizable(false);
    }

    public void showLevel() {
        level = new Level();
        remove(menu);
        add(level);
        revalidate();
        repaint();
        level.requestFocusInWindow();
    }
}
