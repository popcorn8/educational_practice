package gui;

import javax.swing.*;
import java.awt.*;

public class ToolkitPanel extends JPanel {
    private final int cornerRadius;

    public ToolkitPanel() {
        super();
        this.cornerRadius = 20;
        setOpaque(false); // Устанавливаем непрозрачность в false для корректного отображения закругленных краев
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;

        // Настройки для улучшения качества отрисовки
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Рисуем закругленный прямоугольник
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
    }
}
