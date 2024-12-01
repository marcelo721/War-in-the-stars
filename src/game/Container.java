package game;

import game.model.Level;

import javax.swing.*;

public class Container extends JFrame {

    public Container(){
        add(new Level());
        setTitle("war in the stars");
        setSize(1024, 728);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        this.setResizable(false);
    }

    public static void main(String[] args) {
        new Container();
    }
}
