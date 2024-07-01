package graph;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Graph {
    private List<Node> nodes;
    private List<Edge> edges;

    public Graph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addNode(int x, int y) {
        nodes.add(new Node(x, y));
    }

    public void removeNode(Node node) {
        nodes.remove(node);
        Iterator<Edge> iterator = edges.iterator();
        while (iterator.hasNext()) {
            Edge edge = iterator.next();
            if (edge.getStart() == node || edge.getEnd() == node) {
                iterator.remove();
            }
        }
    }

    public void addEdge(Node start, Node end, Color color) {
        edges.add(new Edge(start, end, color));
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    public Node findNode(int x, int y) {
        for (Node node : nodes) {
            if (node.contains(x, y)) {
                return node;
            }
        }
        return null;
    }

    public Edge findEdge(int x, int y) {
        for (Edge edge : edges) {
            Point p1 = edge.getStart().getPoint();
            Point p2 = edge.getEnd().getPoint();
            if (isPointNearLine(x, y, p1.x, p1.y, p2.x, p2.y)) {
                return edge;
            }
        }
        return null;
    }

    private boolean isPointNearLine(int x, int y, int x1, int y1, int x2, int y2) {
        double distance = Line2D.ptSegDist(x1, y1, x2, y2, x, y);
        return distance < 5.0;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public static class Node {
        private Point point;
        private int radius = 10;
        private Color color;

        public Node(int x, int y) {
            this.point = new Point(x, y);
            this.color = Color.BLACK;
        }

        public Point getPoint() {
            return point;
        }

        public int getRadius() {
            return radius;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public void setPoint(int x, int y) {
            this.point.setLocation(x, y);
        }

        public boolean contains(int x, int y) {
            return point.distance(x, y) <= radius;
        }
    }

    public static class Edge {
        private Node start;
        private Node end;
        private Color color;

        public Edge(Node start, Node end) {
            this(start, end, Color.BLACK);
        }

        public Edge(Node start, Node end, Color color) {
            this.start = start;
            this.end = end;
            this.color = color;
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
    }
}
