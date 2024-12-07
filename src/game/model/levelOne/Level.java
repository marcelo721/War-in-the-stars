package game.model.levelOne;

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
    private Clip clip;
    private int cont = 0;

    public Level() {
        setFocusable(true);
        setDoubleBuffered(true);

        ImageIcon reference = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/stars.gif")));
        backGround = reference.getImage();

        player = new Player();
        player.load();
        addKeyListener(new KeyAdapter());

        Timer timer = new Timer(10, this);
        timer.start();
        createEnemies();
        this.endGame = true;
    }

    public void createEnemies() {
        int[] coordinates = new int[50];
        enemyOneList = new ArrayList<>();

        for (int i = 0; i < coordinates.length; i++) {
            int x = (int) (Math.random() * 8000 + 1024);
            int y = (int) (Math.random() * 650 + 30);
            enemyOneList.add(new EnemyOne(x,y));
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (endGame) {
            super.paint(g);
            g2d.drawImage(backGround, 0, 0, getWidth(), getHeight(), null);

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

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("Vidas: " + player.getLife(), 10, 30);

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("inimigos abatidos: " + cont, 10, 50);
            g.dispose();
        } else{
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

            if (formNave.intersects(formEnemyOne) && player.getLife() > 0) {
                tempEnemyOne.setVisible(false);
                player.setLife(player.getLife() - 1);
                enemyOneList.remove(i);

            }else if(player.getLife() == 0){
                player.setVisible(false);
                player.setAlive(false);
                endGame = false;
            }
        }

        java.util.List<Shot> shots = player.getShots();
        for (int i = 0; i < shots.size(); i++) {
            formShot = shots.get(i).getBounds();

            for (int o = 0; o < enemyOneList.size(); o++) {
                EnemyOne tempEnemyOne = enemyOneList.get(o);
                formEnemyOne = tempEnemyOne.getBounds();


                if (formShot.intersects(formEnemyOne) && shots.size() > 1) {
                    shots.get(i).setVisible(false);
                    tempEnemyOne.setVisible(false);
                    shots.remove(i);
                    cont++;
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
