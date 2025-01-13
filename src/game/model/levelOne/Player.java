package game.model.levelOne;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player implements ActionListener {



    private int x, y;
    private int dx, dy;
    private int height, width;
    private Image img;
    private final List<Shot> shots;
    private boolean isVisible, isTurbo;
    private Timer timer;
    private Clip clip;
    private boolean isAlive;
    private int life;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isTurbo) {
            turbo();
            isTurbo = false;
        }
        load();
    }

    public Player() {
        this.x = 100;
        this.y = 100;
        this.isVisible = true;
        this.isTurbo = false;
        this.isAlive = true;
        this.life = 5;

        shots = new ArrayList<>();

        timer = new Timer(5000, this);
        timer.start();
    }

    public void load() {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/player.png")));
        img = icon.getImage();

        height = img.getHeight(null);
        width = img.getWidth(null);

    }

    public void shoot() {
        this.shots.add(new Shot(x + width, y + (height / 2)));
    }

    public void move() {
        int screenWidth = 1024;
        int screenHeight = 695;

        x += dx;
        y += dy;

        if (x < 0) {
            x = 0;
        } else if (x + width > screenWidth) {
            x = screenWidth - width;
        }

        if (y < 0) {
            y = 0;
        } else if (y + height > screenHeight) {
            y = screenHeight - height;
        }
    }

    public void keyPressed(KeyEvent e) {


        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            shoot();
            playSound();
        }
        if (code == KeyEvent.VK_UP) {
            dy = -10;
        }
        if (code == KeyEvent.VK_DOWN) {
            dy = 10;
        }
        if (code == KeyEvent.VK_LEFT) {
            dx = -15;
        }
        if (code == KeyEvent.VK_RIGHT) {
            dx = 10;
        }
        if (code == KeyEvent.VK_SPACE) {
            turbo();
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void release(KeyEvent e) {
        int code = e.getKeyCode();


        if (code == KeyEvent.VK_UP) {
            dy = 0;
        }
        if (code == KeyEvent.VK_DOWN) {
            dy = 0;
        }
        if (code == KeyEvent.VK_LEFT) {
            dx = 0;
        }
        if (code == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }

    public void turbo() {
        isTurbo = true;
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/playerTurbo.png")));
        img = icon.getImage();
    }

    public void playSound() {

        if (isAlive) {
            try {
                URL url = Level.class.getResource("/game/sounds/shotSound.wav");
                assert url != null;
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                clip = AudioSystem.getClip();
                clip.open(audioIn);

                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImg() {
        return img;
    }

    public List<Shot> getShots() {
        return shots;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isTurbo() {
        return isTurbo;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
