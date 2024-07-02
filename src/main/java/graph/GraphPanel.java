package graph;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.NumberFormat;

public class GraphPanel extends JPanel {
    private Graph graph;
    private Graph.Node selectedNode;

    public GraphPanel() {
        this.graph = new Graph();
        selectedNode = null;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Graph.Node node = graph.findNode(e.getX(), e.getY());
                    if (node == null) {
                        graph.addNode(e.getX(), e.getY());
                        repaint();
                    } else {
                        selectedNode = node;
                        selectedNode.setColor(Color.RED);
                        setEdgesColor(selectedNode, Color.RED);
                    }
                    repaint();
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    Graph.Node node = graph.findNode(e.getX(), e.getY());
                    if (node != null) {
                        graph.removeNode(node);
                    } else {
                        Graph.Edge edge = graph.findEdge(e.getX(), e.getY());
                        if (edge != null) {
                            graph.removeEdge(edge);
                        }
                    }
                    repaint();
                } else if (e.getButton() == MouseEvent.BUTTON2) { // Middle mouse button
                    Graph.Node node = graph.findNode(e.getX(), e.getY());
                    if (node != null) {
                        selectedNode = node;
                        selectedNode.setColor(Color.RED);
                        setEdgesColor(selectedNode, Color.RED);
                        repaint();
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3 + 1) { // Fourth mouse button
                    Graph.Edge edge = graph.findEdge(e.getX(), e.getY());
                    if (edge != null) {
                        String label = showInputDialog("Enter edge label:", edge.getLabel());
                        if (label != null) {
                            edge.setLabel(label);
                        }
                    }
                    repaint();
                } else if (e.getButton() == MouseEvent.BUTTON3 + 2) { // Fifth mouse button
                    graph.loadGraph("src/main/resources/graph1.txt");
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (selectedNode != null) {
                        Graph.Node node = graph.findNode(e.getX(), e.getY());
                        if (node != null && node != selectedNode) {
                            graph.addEdge(selectedNode, node, Color.RED, 3, ""); // Default empty label
                        }
                        selectedNode.setColor(Color.BLACK);
                        setEdgesColor(selectedNode, Color.BLACK);
                        selectedNode = null;
                    }
                    repaint();
                } else if (e.getButton() == MouseEvent.BUTTON2) { // Middle mouse button
                    if (selectedNode != null) {
                        selectedNode.setColor(Color.BLACK);
                        setEdgesColor(selectedNode, Color.BLACK);
                        selectedNode = null;
                    }
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isMiddleMouseButton(e) && selectedNode != null) {
                    selectedNode.setPoint(e.getX(), e.getY());
                    selectedNode.setColor(Color.RED);
                    setEdgesColor(selectedNode, Color.RED);
                    repaint();
                }
            }
        });
    }

    private void setEdgesColor(Graph.Node node, Color color) {
        for (Graph.Edge edge : graph.getEdges()) {
            if (edge.getStart() == node || edge.getEnd() == node) {
                edge.setColor(color);
            }
        }
    }

    private String showInputDialog(String message, String initialValue) {
        JFormattedTextField textField = createNumberTextField();
        if (initialValue != null && !initialValue.isEmpty()) {
            try {
                textField.setValue(Integer.parseInt(initialValue));
            } catch (NumberFormatException e) {
                textField.setValue(0); // Если initialValue не является числом, установить значение по умолчанию
            }
        } else {
            textField.setValue(0); // Установить значение по умолчанию, если initialValue пустой
        }
        int result = JOptionPane.showConfirmDialog(this, textField, message, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            return textField.getText();
        }
        return null;
    }

    private JFormattedTextField createNumberTextField() {
        NumberFormat format = NumberFormat.getIntegerInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0); // Set minimum value if needed
        formatter.setMaximum(Integer.MAX_VALUE); // Set maximum value if needed
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        return new JFormattedTextField(formatter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        for (Graph.Edge edge : graph.getEdges()) {
            g2d.setColor(edge.getColor());
            g2d.setStroke(new BasicStroke(edge.getThickness()));
            Point p1 = edge.getStart().getPoint();
            Point p2 = edge.getEnd().getPoint();
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);

            // Draw the label in the middle of the edge
            String label = edge.getLabel();
            if (label != null && !label.isEmpty()) {
                int midX = (p1.x + p2.x) / 2;
                int midY = (p1.y + p2.y) / 2;

                // Offset the label position slightly
                int offsetX = 10;
                int offsetY = 10;

                // Create a bold font
                Font boldFont = g2d.getFont().deriveFont(Font.BOLD, 12f);
                g2d.setFont(boldFont);
                g2d.setColor(Color.BLUE); // Set label color

                // Draw the string with an offset
                g2d.drawString(label, midX + offsetX, midY - offsetY);
            }
        }

        for (Graph.Node node : graph.getNodes()) {
            g2d.setColor(node.getColor());
            g2d.fillOval(node.getPoint().x - node.getRadius(), node.getPoint().y - node.getRadius(),
                    2 * node.getRadius(), 2 * node.getRadius());
            g2d.drawOval(node.getPoint().x - node.getRadius(), node.getPoint().y - node.getRadius(),
                    2 * node.getRadius(), 2 * node.getRadius());
        }
    }
}
