package game.model.levelOne;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Shot {

    private Image image;
    private  int x;
    private  final int y;
    private int width, height;
    private boolean isVisible;

    private static final int WIDTH = 938;
    private static final int SPEED = 10;

    public Shot(int x, int y){
        this.x = x;
        this.y = y;
        isVisible = true;
    }

    public void load(){
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/shot.png")));
         image = icon.getImage();

         this.width = image.getWidth(null);
         this.height = image.getHeight(null);
    }

    public void update(){
        this.x += SPEED;
        if (this.x > WIDTH)
            isVisible = false;
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
