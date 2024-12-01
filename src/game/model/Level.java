package game.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class Level extends JPanel implements ActionListener {

    private Image backGround;
    private Player player;
    private Timer timer;
    private java.util.List<EnemyOne> enemyOneList;
    private boolean endGame;
    private java.util.List<Stars> stars;


    public Level() {

        setFocusable(true);
        setDoubleBuffered(true);

        ImageIcon referencia = new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/blackground.png")));
        backGround = referencia.getImage();

        player = new Player();
        player.load();
        addKeyListener(new KeyAdapter());

        this.timer = new Timer(10, this);
        this.timer.start();
        createEnemies();

        createStars();
        this.endGame = true;
    }

    public void createEnemies(){
        int coordinates[] = new int[100];
        enemyOneList = new ArrayList<>();

        for (int i = 0; i < coordinates.length; i++) {
            int x = (int) (Math.random() * 8000 + 1024);
            int y = (int) (Math.random() * 650 + 30);
            enemyOneList.add(new EnemyOne(x,y));
        }
    }

    public void createStars() {
        int[] coordinates = new int[300];
        stars = new ArrayList<>();

        for (int i = 0; i < coordinates.length; i++) {
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
            g2d.drawImage(backGround, 0, 0, getWidth(), getHeight(), null);

            for (Stars stars : stars) {
                stars.load();
                g2d.drawImage(stars.getImage(), stars.getX(), stars.getY(), this);
            }

            g2d.drawImage(player.getImg(), player.getX(), player.getY(), this);

            java.util.List<Shot> shots = player.getShots();
            for (Shot shot : shots) {
                shot.load();
                g2d.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
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

        if (player.isTurbo()) {
            for (EnemyOne enemyOne : enemyOneList){
                enemyOne.setSpeed(50);
            }
        } else {
            for (EnemyOne enemyOne : enemyOneList){
                enemyOne.setSpeed(10);
            }
        }

        for (int p = 0; p < stars.size(); p++) {
            Stars on = stars.get(p);
            if (on.isVisible()){
                on.update();
            }else
                stars.remove(p);

        }

        java.util.List<Shot> shots = player.getShots();
        for (int i = 0; i < shots.size(); i++){

            Shot shot = shots.get(i);
            if (shot.isVisible()){
                shot.update();
            }else {
                shots.remove(i);
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
        checkCollisions();
        repaint();
    }

    public void checkCollisions(){
        Rectangle formaNave = player.getBounds();
        Rectangle formEnemyOne;
        Rectangle formShot;

        for (int i = 0; i < enemyOneList.size(); i++) {
            EnemyOne tempEnemyOne = enemyOneList.get(i);
            formEnemyOne = tempEnemyOne.getBounds();
            if (formaNave.intersects(formEnemyOne)){
                player.setVisible(false);
                tempEnemyOne.setVisible(false);
                endGame = false;
            }
        }

        java.util.List<Shot> shots = player.getShots();
        for (Shot shot : shots) {
            formShot = shot.getBounds();
            for(int o = 0; o < enemyOneList.size(); o++){
                EnemyOne tempEnemyOne = enemyOneList.get(o);
                formEnemyOne = tempEnemyOne.getBounds();
                if (formShot.intersects(formEnemyOne)){
                    tempEnemyOne.setVisible(false);
                    shot.setVisible(false);
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
