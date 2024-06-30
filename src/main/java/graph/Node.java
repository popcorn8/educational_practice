package graph;

import java.awt.*;

public class Node {
    private Point point;
    private int radius = 10;

    public Node(int x, int y) {
        this.point = new Point(x, y);
    }

    public Point getPoint() {
        return point;
    }

    public int getRadius() {
        return radius;
    }

    public boolean contains(int x, int y) {
        return point.distance(x, y) <= radius;
    }
}
