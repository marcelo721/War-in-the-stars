package game.model;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Stars {

    private Image image;
    private int x, y;
    private int largura, altura;
    private boolean isVisible;

    private static int VELOCIDADE = 5;

    public Stars(int x, int y) {
        this.x = x;
        this.y = y;
        isVisible = true;
    }

    public void load() {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/stars.png")));
        image = icon.getImage();

        this.largura = image.getWidth(null);
        this.altura = image.getHeight(null);
    }

    public void update() {

        if (this.x < 0){
            this.x += largura;

            Random a = new Random();
            int m = a.nextInt(500);
            this.x = m + 1024;

            Random r = new Random();
            int n = r.nextInt(768);
            this.y = n;
        }else
            this.x -= VELOCIDADE;

    }


    public static int getVELOCIDADE() {
        return VELOCIDADE;
    }

    public static void setVELOCIDADE(int VELOCIDADE) {
        Stars.VELOCIDADE = VELOCIDADE;
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
