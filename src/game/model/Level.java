package game.model;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Level extends JPanel {

    private Image fundo;
    public Level() {
        ImageIcon referencia = new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/blackground.png")));
        fundo = referencia.getImage();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(fundo, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
    }

}
