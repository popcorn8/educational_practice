package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

class HelpTextArea extends JTextArea {
    @Override
    protected void paintComponent(Graphics g) {
        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Shape shape = new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            g2.setClip(shape);
            super.paintComponent(g2);
        } else {
            super.paintComponent(g);
        }
    }
}