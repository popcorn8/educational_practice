package graph;

import javax.swing.*;
import java.awt.*;

public class Edge {
    private Node start;
    private Node end;
    private Color color;
    private int thickness;
    private String label;

    public Edge(Node start, Node end) {
        this(start, end, Color.BLACK, 3, "");
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

//    @Override
//    public String toString() {
//        return String.format(
//                "Edge[start=(%d, %d), end=(%d, %d), color=%s, thickness=%d, label=%s]",
//                start.getPoint().x, start.getPoint().y,
//                end.getPoint().x, end.getPoint().y,
//                color.toString(), thickness, label
//        );
//    }

    @Override
    public String toString() {
        return String.format(
                "label=%s",
                label
        );
    }
}
