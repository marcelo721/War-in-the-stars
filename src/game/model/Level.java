package game.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class Level extends JPanel implements ActionListener {

    private Image fundo;
    private Player player;
    private Timer timer;
    private java.util.List<EnemyOne> enemyOneList;

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
        inicializarInimigos();
    }

    public void inicializarInimigos(){
        int coordenadas[] = new int[40];
        enemyOneList = new ArrayList<>();

        for (int i = 0; i < coordenadas.length; i++) {
            int x = (int) (Math.random() * 8000 + 1024);
            int y = (int) (Math.random() * 650 + 30);
            enemyOneList.add(new EnemyOne(x,y));

        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(fundo, 0, 0, getWidth(), getHeight(), null);
        g2d.drawImage(player.getImg(), player.getX(), player.getY(), this);

        java.util.List<Tiro> tiros = player.getTiros();
        for (Tiro tiro : tiros) {
            tiro.load();
            g2d.drawImage(tiro.getImage(), tiro.getX(), tiro.getY(), this);
        }

        for (EnemyOne in : enemyOneList) {
            in.load();
            g2d.drawImage(in.getImage(), in.getX(), in.getY(), this);
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.move();
        java.util.List<Tiro> tiros = player.getTiros();
        for (int i = 0; i < tiros.size(); i++){

            Tiro tiro = tiros.get(i);
            if (tiro.isVisible()){
                tiro.update();
            }else {
                tiros.remove(i);
            }

        }

        for (int j = 0; j < enemyOneList.size(); j ++){
            EnemyOne in = enemyOneList.get(j);
            if (in.isVisible()){
                in.update();
            }else{
                enemyOneList.remove(j);
            }
        }
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
