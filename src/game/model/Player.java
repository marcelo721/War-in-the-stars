package game.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isTutbo=  true) {
            turbo();
            isTutbo = false;
        }

        if (isTutbo == false){
            load();
        }
    }

    private int x, y;
    private int dx, dy;
    private int altura, largura;
    private Image img;
    private List<Tiro> tiros;
    private boolean isVisible, isTutbo;
    private Timer timer;

    public Player() {
        this.x = 100;
        this.y = 100;
        this.isVisible = true;
        this.isTutbo = false;

        tiros = new ArrayList<>();

        timer = new Timer(5000, this);
        timer.start();
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
        if (codigo == KeyEvent.VK_SPACE) {
            turbo();
        }
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,largura,altura);
    }

    public void release(KeyEvent e) {
        int codigo = e.getKeyCode();


        if (codigo == KeyEvent.VK_W) {
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

    public void turbo(){
        isTutbo = true;
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
