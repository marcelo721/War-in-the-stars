package game.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Menu extends JPanel implements ActionListener {

    private Button startButton;
    private Button exitButton;
    private Image backGroundImage;
    private final Image startGameButton;
    private boolean isLoading = false;


    public Menu() {

        ImageIcon backGround = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/start.gif")));
        backGroundImage = backGround.getImage();

        ImageIcon start = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/startGame.gif")));
        startGameButton = start.getImage();

        startButton = new Button(start, 400, 200);
        startButton.addActionListener(this);
        add(startButton);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (!isLoading && backGroundImage != null) {
            g.drawImage(startGameButton, 0, 0, 1000, 1000, this);
            g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);
        }else {
            startButton.setVisible(false);
            ImageIcon backGround = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game/images/loadGame.gif")));
            backGroundImage = backGround.getImage();
            g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        isLoading = true;
        repaint();
        Timer timer = new Timer(3500, e -> {

            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (parentFrame instanceof WarInTheStars) {
                ((WarInTheStars) parentFrame).showLevel();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public Button getStartButton() {
        return startButton;
    }

    public void setStartButton(Button startButton) {
        this.startButton = startButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public void setExitButton(Button exitButton) {
        this.exitButton = exitButton;
    }

    public Image getBackGroundImage() {
        return backGroundImage;
    }

    public void setBackGroundImage(Image backGroundImage) {
        this.backGroundImage = backGroundImage;
    }
}
