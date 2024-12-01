package game.model;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class EnemyOne {

    private Image image;
    private int x,y;
    private int largura, altura;
    private boolean isVisible;

    //private static final int LARGURA = 938;
    private static  int VELOCIDADE = 4;

    public EnemyOne(int x, int y){
        this.x = x;
        this.y = y;
        isVisible = true;
    }

    public void load(){
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/enemyOne.png")));
         image = icon.getImage();

         this.largura = image.getWidth(null);
         this.altura = image.getHeight(null);
    }

    public void update(){
        this.x -= VELOCIDADE;
//        if (this.x > LARGURA)
//            isVisible = false;
    }

    public static int getVELOCIDADE() {
        return VELOCIDADE;
    }

    public static void setVELOCIDADE(int VELOCIDADE) {
        EnemyOne.VELOCIDADE = VELOCIDADE;
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
}
