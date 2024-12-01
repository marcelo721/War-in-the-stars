package game.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {

    private int x, y;
    private int dx, dy;
    private int altura, largura;
    private Image img;
    private List<Tiro> tiros;
    private boolean isVisible;

    public Player() {
        this.x = 100;
        this.y = 100;
        this.isVisible = true;

        tiros = new ArrayList<>();
    }

    public void load() {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/player.png")));
        img = icon.getImage();

        altura = img.getHeight(null);
        largura = img.getWidth(null);
    }

    public void tirosSimples(){
        this.tiros.add(new Tiro(x+largura,y + (altura/2)));
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

    public Rectangle getBounds(){
        return new Rectangle(x,y,largura,altura);
    }

    public void release(KeyEvent e) {
        int codigo = e.getKeyCode();


        if (codigo == KeyEvent.VK_SPACE) {
            tirosSimples();
        }

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImg() {
        return img;
    }

    public List<Tiro> getTiros() {
        return tiros;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
