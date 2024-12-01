package game.model;

import javax.swing.*;
import java.awt.*;
import java.security.PrivateKey;
import java.util.Objects;
import java.util.PrimitiveIterator;

public class Tiro {

    private Image image;
    private int x,y;
    private int largura, altura;
    private boolean isVisible;

    private static final int LARGURA = 938;
    private static  int VELOCIDADE = 2;

    public Tiro(int x, int y){
        this.x = x;
        this.y = y;
        isVisible = true;
    }

    public void load(){
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/tiro.png")));
         image = icon.getImage();

         this.largura = image.getWidth(null);
         this.altura = image.getHeight(null);
    }

    public void update(){
        this.x += VELOCIDADE;
        if (this.x > LARGURA)
            isVisible = false;
    }

    public static int getVELOCIDADE() {
        return VELOCIDADE;
    }

    public static void setVELOCIDADE(int VELOCIDADE) {
        Tiro.VELOCIDADE = VELOCIDADE;
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
}
