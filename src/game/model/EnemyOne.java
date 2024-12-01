package game.model;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class EnemyOne {

    private Image image;
    private int x, y;
    private int width, height;
    private boolean isVisible;
    private  int speed = 10;

    public EnemyOne(int x, int y) {
        this.x = x;
        this.y = y;
        isVisible = true;
    }

    public void load() {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/enemyOne.png")));
        image = icon.getImage();

        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    public void update() {
        this.x -= speed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public  int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

}
