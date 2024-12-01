package game.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class Level extends JPanel implements ActionListener {

    private Image fundo;
    private Player player;
    private Timer timer;

    public Level() {

        setFocusable(true);
        setDoubleBuffered(true);

        ImageIcon referencia = new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/blackground.png")));
        fundo = referencia.getImage();

        player = new Player();
        player.load();
        addKeyListener(new KeyAdapter());

        timer = new Timer(5, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(fundo, 0, 0, getWidth(), getHeight(), null);
        g2d.drawImage(player.getImg(), player.getX(), player.getY(), this);
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.move();
        repaint();
    }

    private class KeyAdapter implements KeyListener{


        @Override
        public void keyTyped(KeyEvent ke) {
        }

        @Override
        public void keyPressed(KeyEvent ke) {
            player.keyPressed(ke);
        }

        @Override
        public void keyReleased(KeyEvent ke) {
            player.release(ke);
        }

    }
}
