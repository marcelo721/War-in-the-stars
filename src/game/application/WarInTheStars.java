package game.application;

import game.model.Level;

import javax.swing.*;

public class WarInTheStars extends JFrame {

    public WarInTheStars(){
        add(new Level());
        setTitle("war in the stars");
        setSize(1024, 728);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        this.setResizable(false);
    }

}
