package game.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends JButton {

    private final ImageIcon icon;

    public Button(ImageIcon icon, int width, int height) {

        this.icon = icon;
        setVisible(true);
        setOpaque(false);
        setPreferredSize(new Dimension(width, height));
        setBorder(BorderFactory.createMatteBorder(0,0,0,0,Color.WHITE));

        addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                moveButton(18, 0);
            }

            public void mouseExited(MouseEvent e) {
                moveButton(-18, 0);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (icon != null) {
            g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
    }

    private void moveButton(int xOffset, int yOffset) {
        Point currentLocation = getLocation();
        setLocation(currentLocation.x + xOffset, currentLocation.y + yOffset);
    }
}
