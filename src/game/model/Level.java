package game.model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class Level extends JPanel implements ActionListener {

    private final Image backGround;
    private final Player player;
    private java.util.List<EnemyOne> enemyOneList;
    private boolean endGame;
    private java.util.List<Stars> stars;
    private Clip clip;


    public Level() {
        setFocusable(true);
        setDoubleBuffered(true);

        ImageIcon referencia = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/background.png")));
        backGround = referencia.getImage();

        player = new Player();
        player.load();
        addKeyListener(new KeyAdapter());

        Timer timer = new Timer(10, this);
        timer.start();
        createEnemies();
        createStars();
        this.endGame = true;
    }

    public void createEnemies() {
        int[] coordinates = new int[40];
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
            stars.add(new Stars(x, y));
        }
    }


    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (endGame) {
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
        } else {
            ImageIcon endGame = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/gameOver.gif")));
            g2d.drawImage(endGame.getImage(), 0, 0, getWidth(), getHeight(), null);
            stopSound();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        playSound();
        player.move();

        if (player.isTurbo()) {
            for (EnemyOne enemyOne : enemyOneList) {
                enemyOne.setSpeed(50);
            }
        } else {
            for (EnemyOne enemyOne : enemyOneList) {
                enemyOne.setSpeed(10);
            }
        }

        for (int p = 0; p < stars.size(); p++) {
            Stars on = stars.get(p);
            if (on.isVisible()) {
                on.update();
            } else
                stars.remove(p);

        }

        java.util.List<Shot> shots = player.getShots();
        for (int i = 0; i < shots.size(); i++) {

            Shot shot = shots.get(i);
            if (shot.isVisible()) {
                shot.update();
            } else {
                shots.remove(i);
            }

        }

        for (EnemyOne enemyOne : enemyOneList) {
            if (enemyOne.isVisible()) {
                enemyOne.update();
            } else {
                enemyOne.setX(1024 + (int) (Math.random() * 500));
                enemyOne.setY((int) (Math.random() * 768));
                enemyOne.setVisible(true);
            }
        }
        checkCollisions();
        repaint();
    }

    public void checkCollisions() {
        Rectangle formNave = player.getBounds();
        Rectangle formEnemyOne;
        Rectangle formShot;

        for (int i = 0; i < enemyOneList.size(); i++) {
            EnemyOne tempEnemyOne = enemyOneList.get(i);
            formEnemyOne = tempEnemyOne.getBounds();
            if (formNave.intersects(formEnemyOne)) {
                player.setVisible(false);
                player.setAlive(false);
                tempEnemyOne.setVisible(false);
                endGame = false;
            }
        }

        java.util.List<Shot> shots = player.getShots();
        for (Shot shot : shots) {
            formShot = shot.getBounds();
            for (int o = 0; o < enemyOneList.size(); o++) {
                EnemyOne tempEnemyOne = enemyOneList.get(o);
                formEnemyOne = tempEnemyOne.getBounds();
                if (formShot.intersects(formEnemyOne)) {
                    shot.setVisible(false);
                    tempEnemyOne.setVisible(false);
                }
            }
        }

    }
    private class KeyAdapter implements KeyListener {
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

    public void playSound() {
        if (clip == null) {
            try {

                URL url = Level.class.getResource("/game/sounds/warGame.wav");
                assert url != null;
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                clip = AudioSystem.getClip();
                clip.open(audioIn);


                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
