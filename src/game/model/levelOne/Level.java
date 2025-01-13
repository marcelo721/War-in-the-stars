package game.model.levelOne;

import game.archives.utils.ArchiveUtils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
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
    private java.util.List<EnemyTwo> enemytwoList;
    private final String maxScore;
    private final String PATH;
    private Image lifeImage;
    private Font retroFont;


    public Level() {
        setFocusable(true);
        setDoubleBuffered(true);

        PATH = "C:\\Users\\mh047\\OneDrive\\Área de Trabalho\\game2d\\WarInTheStars\\src\\game\\archives\\MaxScore.txt";
        ImageIcon reference = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/stars.gif")));
        backGround = reference.getImage();

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/lifeOne.png")));
        lifeImage = icon.getImage();

        player = new Player();
        player.load();
        addKeyListener(new KeyAdapter());

        Timer timer = new Timer(10, this);
        timer.start();
        createEnemies();
        createEnemiesTwo();
        this.endGame = true;
        maxScore = ArchiveUtils.readArchives(PATH);

        try {
            retroFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/game/archives/fonts/PressStart2P-Regular.ttf")).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(retroFont);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a fonte retrô!");
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

            for (EnemyTwo in : enemytwoList) {
                in.load();
                g2d.drawImage(in.getImage(), in.getX(), in.getY(), this);
            }

            g2d.drawImage(lifeImage, 4, 10, this);

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);


            g2d.setColor(new Color(255, 255, 0));
            g2d.setFont(retroFont);
            g2d.drawString("enemies killed: " + cont, 15, 55);

            g2d.setColor(new Color(0, 0, 0, 120));
            g2d.drawString("enemies killed: " + cont, 17, 67);

            g2d.setColor(new Color(0, 255, 0));
            g2d.setFont(retroFont);
            g2d.drawString("Recorde: " + maxScore, 15, 75);

            g2d.setColor(new Color(0, 0, 0, 120));
            g2d.drawString("Recorde: " + maxScore, 17, 87);
        } else {

            int newRecord = Integer.parseInt(maxScore);
            if (cont > newRecord) {
                String text = String.valueOf(cont);
                ArchiveUtils.writeArchives(PATH, text);
            }

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
            for (EnemyTwo enemytwo : enemytwoList) {
                enemytwo.setSpeed(40);
            }
        } else {
            for (EnemyOne enemyOne : enemyOneList) {
                enemyOne.setSpeed(7);
            }
            for (EnemyTwo enemytwo : enemytwoList) {
                enemytwo.setSpeed(5);
            }
        }

        java.util.List<Shot> shots = player.getShots();
        for (int i = shots.size() - 1; i >= 0; i--) {

            Shot shot = shots.get(i);
            if (shot.isVisible()) {
                shot.update();
            } else {
                shots.remove(i);
            }
        }

        if (cont < 10) {
            for (EnemyOne enemyOne : enemyOneList) {
                if (enemyOne.isVisible()) {
                    enemyOne.update();
                } else {
                    enemyOne.setX(1024 + (int) (Math.random() * 500));
                    enemyOne.setY((int) (Math.random() * 768));
                    enemyOne.setVisible(true);
                }
            }
        } else {
            enemyOneList.clear();

            for (EnemyTwo enemytwo : enemytwoList) {
                if (enemytwo.isVisible()) {
                    enemytwo.update();
                } else {
                    enemytwo.setX(1024 + (int) (Math.random() * 500));
                    enemytwo.setY((int) (Math.random() * 768));
                    enemytwo.setVisible(true);
                }
            }
        }
        checkCollisions();
        setLifeImage();
        repaint();
    }

    public void createEnemies() {
        int[] coordinates = new int[50];
        enemyOneList = new ArrayList<>();

        for (int i = 0; i < coordinates.length; i++) {
            int x = (int) (Math.random() * 8000 + 1024);
            int y = (int) (Math.random() * 650 + 30);
            enemyOneList.add(new EnemyOne(x, y));
        }
    }

    public void createEnemiesTwo() {
        int[] coordinates = new int[50];
        enemytwoList = new ArrayList<>();

        for (int i = 0; i < coordinates.length; i++) {
            int x = (int) (Math.random() * 8000 + 1024);
            int y = (int) (Math.random() * 650 + 30);
            enemytwoList.add(new EnemyTwo(x, y));
        }
    }

    public void checkCollisions() {
        Rectangle formNave = player.getBounds();
        Rectangle formEnemyOne;
        Rectangle formShot;

        for (int i = enemyOneList.size() - 1; i > 1; i--) {
            EnemyOne tempEnemyOne = enemyOneList.get(i);
            formEnemyOne = tempEnemyOne.getBounds();

            if (formNave.intersects(formEnemyOne) && player.getLife() > 0) {
                tempEnemyOne.setVisible(false);
                player.setLife(player.getLife() - 1);
                enemyOneList.remove(i);


            } else if (player.getLife() <= 0) {
                player.setVisible(false);
                player.setAlive(false);
                endGame = false;
            }
        }

        for (int i = enemytwoList.size() - 1; i > 1; i--) {
            EnemyTwo tempEnemy = enemytwoList.get(i);
            formEnemyOne = tempEnemy.getBounds();

            if (formNave.intersects(formEnemyOne) && player.getLife() > 0) {
                tempEnemy.setVisible(false);
                player.setLife(player.getLife() - 2);
                enemytwoList.remove(i);

            } else if (player.getLife() <= 0) {
                player.setVisible(false);
                player.setAlive(false);
                endGame = false;
            }
        }

        java.util.List<Shot> shots = player.getShots();
        for (int i = shots.size() - 1; i >= 0; i--) {
            formShot = shots.get(i).getBounds();

            for (EnemyOne tempEnemyOne : enemyOneList) {
                formEnemyOne = tempEnemyOne.getBounds();

                if (formShot.intersects(formEnemyOne)) {
                    shots.get(i).setVisible(false);
                    tempEnemyOne.setVisible(false);
                    shots.remove(i);
                    cont++;
                    break;
                }
            }
        }

        for (int i = shots.size() - 1; i >= 0; i--) {
            formShot = shots.get(i).getBounds();

            for (EnemyTwo tempEnemy : enemytwoList) {
                formEnemyOne = tempEnemy.getBounds();

                if (formShot.intersects(formEnemyOne)) {
                    shots.get(i).setVisible(false);
                    tempEnemy.setVisible(false);
                    shots.remove(i);
                    cont++;
                    break;
                }
            }
        }
    }

    private void setLifeImage() {
        ImageIcon reference;
        switch (player.getLife()) {
            case 0:
                 reference = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/lifeSix.png")));
                lifeImage = reference.getImage();
                break;
            case 1:
                 reference = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/lifeFive.png")));
                lifeImage = reference.getImage();
                break;
            case 2:
                reference = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/lifeFor.png")));
                lifeImage = reference.getImage();
                break;

            case 3:
                reference = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/lifeThree.png")));
                lifeImage = reference.getImage();
                break;

            case 4:
                reference = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/lifeTwo.png")));
                lifeImage = reference.getImage();
                break;
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
