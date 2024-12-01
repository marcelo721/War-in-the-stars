package game.model;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Shot {

    private Image image;
    private int x,y;
    private int width, height;
    private boolean isVisible;

    private static final int WIDTH = 938;
    private static  int HEIGHT = 4;

    public Shot(int x, int y){
        this.x = x;
        this.y = y;
        isVisible = true;
    }

    public void load(){
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/tiro.png")));
         image = icon.getImage();

         this.width = image.getWidth(null);
         this.height = image.getHeight(null);
    }

    public void update(){
        this.x += HEIGHT;
        if (this.x > WIDTH)
            isVisible = false;
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y, width, height);
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static void setHEIGHT(int HEIGHT) {
        Shot.HEIGHT = HEIGHT;
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
