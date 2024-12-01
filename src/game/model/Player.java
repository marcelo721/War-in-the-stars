package game.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class Player {

    private int x, y;
    private int dx, dy;
    private int altura, largura;
    private Image img;

    public Player() {
        this.x = 100;
        this.y = 100;
    }

    public void load() {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/player.png")));
        img = icon.getImage();

        altura = img.getHeight(null);
        largura = img.getWidth(null);
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void keyPressed(KeyEvent e) {
        int codigo = e.getKeyCode();

        if (codigo == KeyEvent.VK_UP) {
            dy = -3;
        }
        if (codigo == KeyEvent.VK_DOWN) {
            dy = 3;
        }
        if (codigo == KeyEvent.VK_LEFT) {
            dx = -3;
        }
        if (codigo == KeyEvent.VK_RIGHT) {
            dx = 3;
        }
    }

    public void release(KeyEvent e) {
        int codigo = e.getKeyCode();

        if (codigo == KeyEvent.VK_UP) {
            dy = 0;
        }
        if (codigo == KeyEvent.VK_DOWN) {
            dy = 0;
        }
        if (codigo == KeyEvent.VK_LEFT) {
            dx = 0;
        }
        if (codigo == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }
}
