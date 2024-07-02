package graph;

import javax.swing.*;
import java.awt.*;

public class Edge extends JComponent {
    private Node start;
    private Node end;
    private Color color;
    private int thickness;
    private String label;

    public Edge(Node start, Node end) {
        this(start, end, Color.BLACK, 2, "");
    }

    public Edge(Node start, Node end, Color color, int thickness, String label) {
        this.start = start;
        this.end = end;
        this.color = color;
        this.thickness = thickness;
        this.label = label;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    protected void paintComponent(Graphics g) {

    }
}
