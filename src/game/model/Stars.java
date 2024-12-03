package game.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Random;

public class Stars implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isTurbo) {
            turbo();
        }else {
            load();
        }
    }

    private Image image;
    private int x, y;
    private int width, height;
    private boolean isVisible;
    private boolean isTurbo;

    private static int speed = 5;

    public Stars(int x, int y) {
        this.x = x;
        this.y = y;
        isVisible = true;
    }

    public void turbo() {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/starsTurbo.png")));
        image = icon.getImage();
    }
    public void load() {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/stars.png")));
        image = icon.getImage();

        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    public void update() {

        if (this.x < 0){
            this.x += width;

            Random a = new Random();
            int m = a.nextInt(500);
            this.x = m + 1024;

            Random r = new Random();
            int n = r.nextInt(768);
            this.y = n;
        }else
            this.x -= speed;
    }


    public static int getSpeed() {
        return speed;
    }

    public static void setSpeed(int speed) {
        Stars.speed = speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public Image getImage() {
        return image;
    }

    public boolean isTurbo() {
        return isTurbo;
    }

    public void setTurbo(boolean turbo) {
        isTurbo = turbo;
    }


    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
