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
    private boolean endGame;
    private java.util.List<Stars> stars;


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
        inicializarStars();
        this.endGame = true;
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

    public void inicializarStars() {
        int coordenadas[] = new int[200];
        stars = new ArrayList<>();

        for (int i = 0; i < coordenadas.length; i++) {
            int x = (int) (Math.random() * 1050 + 1024);
            int y = (int) (Math.random() * 768 + 0);
            stars.add(new Stars(x,y));
        }
    }


        @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (endGame == true){
            super.paint(g);
            g2d.drawImage(fundo, 0, 0, getWidth(), getHeight(), null);

            for (Stars stars : stars) {
                stars.load();
                g2d.drawImage(stars.getImage(), stars.getX(), stars.getY(), this);
            }

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
        }else {
            ImageIcon endGame = new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/gameOver.png")));
            g2d.drawImage(endGame.getImage(), 0, 0, getWidth(), getHeight(), null);

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.move();

        for (int p = 0; p < stars.size(); p++) {
            Stars on = stars.get(p);
            if (on.isVisible()){
                on.update();
            }else
                stars.remove(p);

        }

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
        checarColisoes();
        repaint();
    }

    public void checarColisoes(){
        Rectangle formaNave = player.getBounds();
        Rectangle formaEnemyOne;
        Rectangle formaTiro;

        for (int i = 0; i < enemyOneList.size(); i++) {
            EnemyOne tempEnemyOne = enemyOneList.get(i);
            formaEnemyOne = tempEnemyOne.getBounds();
            if (formaNave.intersects(formaEnemyOne)){
                player.setVisible(false);
                tempEnemyOne.setVisible(false);
                endGame = false;
            }
        }

        java.util.List<Tiro> tiros = player.getTiros();
        for (Tiro tiro : tiros) {
            formaTiro = tiro.getBounds();
            for(int o = 0; o < enemyOneList.size(); o++){
                EnemyOne tempEnemyOne = enemyOneList.get(o);
                formaEnemyOne = tempEnemyOne.getBounds();
                if (formaTiro.intersects(formaEnemyOne)){
                    tempEnemyOne.setVisible(false);
                    tiro.setVisible(false);
                }
            }
        }

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
