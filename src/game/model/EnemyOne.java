package game.model;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class EnemyOne {

    private Image image;
    private int x, y;
    private int width, height;
    private boolean isVisible;
    private  int speed;


    public EnemyOne(int x, int y) {
        this.x = x;
        this.y = y;
        isVisible = true;
    }

    public void load(){
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/enemyOne.png")));
        image = icon.getImage();

        this.width = image.getWidth(null);
        this.height = image.getHeight(null);

        if (!isVisible) {
            isVisible = true;
            verifyExplosion();
            Timer timer = new Timer(50, e -> {
                setVisible(false);
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    public void verifyExplosion(){
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/explosion.gif")));
        image = icon.getImage();

        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    public void update() {
        if (this.x < -width) {
            Random random = new Random();
            this.x = 1024 + random.nextInt(500);
            this.y = random.nextInt(768);
        } else {
            this.x -= speed;
        }
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
