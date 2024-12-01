package game.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isTurbo) {
            turbo();
            isTurbo = false;
        }

        if (!isTurbo){
            load();
        }
    }

    private int x, y;
    private int dx, dy;
    private int height, width;
    private Image img;
    private  List<Shot> shots;
    private boolean isVisible, isTurbo;
    private Timer timer;

    public Player() {
        this.x = 100;
        this.y = 100;
        this.isVisible = true;
        this.isTurbo = false;

        shots = new ArrayList<>();

        timer = new Timer(5000, this);
        timer.start();
    }

    public void load() {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/player.png")));
        img = icon.getImage();

        height = img.getHeight(null);
        width = img.getWidth(null);
    }

    public void shoot(){
        this.shots.add(new Shot(x+ width,y + (height /2)));
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            dy = -3;
        }
        if (code == KeyEvent.VK_DOWN) {
            dy = 3;
        }
        if (code == KeyEvent.VK_LEFT) {
            dx = -3;
        }
        if (code == KeyEvent.VK_RIGHT) {
            dx = 3;
        }
        if (code == KeyEvent.VK_SPACE) {
            turbo();
        }
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y, width, height);
    }

    public void release(KeyEvent e) {
        int code = e.getKeyCode();


        if (code == KeyEvent.VK_W) {
            shoot();
        }

        if (code == KeyEvent.VK_UP) {
            dy = 0;
        }
        if (code == KeyEvent.VK_DOWN) {
            dy = 0;
        }
        if (code == KeyEvent.VK_LEFT) {
            dx = 0;
        }
        if (code == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }

    public void turbo(){
        isTurbo = true;
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/player2.png")));
        img = icon.getImage();

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

    public List<Shot> getShots() {
        return shots;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isTurbo() {
        return isTurbo;
    }

    public void setTurbo(boolean turbo) {
        isTurbo = turbo;
    }
}
