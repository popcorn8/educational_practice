package graph;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Graph {
    private List<Node> nodes;
    private List<Edge> edges;
    private static final int NODE_PROXIMITY_RADIUS = 25;

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

    public void addEdge(Node start, Node end, Color color, int thickness) {
        edges.add(new Edge(start, end, color, thickness));
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    public Node findNode(int x, int y) {
        for (Node node : nodes) {
            if (node.contains(x, y) || isNearNode(node, x, y)) {
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

    public void clearGraph() {
        nodes.clear();
        edges.clear();
        //TODO: не забыть добавить repaint(); при нажатии удаления.

    }

    private boolean isPointNearLine(int x, int y, int x1, int y1, int x2, int y2) {
        double distance = Line2D.ptSegDist(x1, y1, x2, y2, x, y);
        return distance < 5.0;
    }

    private boolean isNearNode(Node node, int x, int y) {
        return node.getPoint().distance(x, y) <= NODE_PROXIMITY_RADIUS;
    }

    public void loadGraph(String filename) {
        clearGraph();  // Clear the existing graph

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            List<Node> fileNodes = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts[0].equals("N")) {
                    // Node line
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    Node node = new Node(x, y);
                    addNode(x, y);
                    fileNodes.add(node);
                } else if (parts[0].equals("E")) {
                    // Edge line
                    int startIdx = Integer.parseInt(parts[1]);
                    int endIdx = Integer.parseInt(parts[2]);
                    int r = Integer.parseInt(parts[3]);
                    int g = Integer.parseInt(parts[4]);
                    int b = Integer.parseInt(parts[5]);
                    int thickness = Integer.parseInt(parts[6]);
                    Color color = new Color(r, g, b);

                    if (startIdx < fileNodes.size() && endIdx < fileNodes.size()) {
                        addEdge(fileNodes.get(startIdx), fileNodes.get(endIdx), color, thickness);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveGraph(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get(i);
                bw.write(String.format("N %d %d\n", node.getPoint().x, node.getPoint().y));
            }
            for (int i = 0; i < edges.size(); i++) {
                Edge edge = edges.get(i);
                int startIdx = nodes.indexOf(edge.getStart());
                int endIdx = nodes.indexOf(edge.getEnd());
                int r = edge.getColor().getRed();
                int g = edge.getColor().getGreen();
                int b = edge.getColor().getBlue();
                bw.write("E" + " " + startIdx + " " + endIdx + " " + r + " " + g + " " + b + " " + edge.getThickness() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        private int thickness;

        public Edge(Node start, Node end) {
            this(start, end, Color.BLACK, 3);
        }

        public Edge(Node start, Node end, Color color, int thickness) {
            this.start = start;
            this.end = end;
            this.color = color;
            this.thickness = thickness;
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
    }
}
