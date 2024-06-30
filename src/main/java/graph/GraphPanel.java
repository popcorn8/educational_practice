package graph;

import graph.Edge;
import graph.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GraphPanel extends JPanel {
    private java.util.List<Node> nodes;
    private List<Edge> edges;
    private Node selectedNode;
    private Color vertexColor = Color.BLACK;
    private Color edgeColor = Color.BLACK;
    private int edgeThickness = 3;

    public GraphPanel() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        selectedNode = null;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Node node = findNode(e.getX(), e.getY());
                    if (node == null) {
                        nodes.add(new Node(e.getX(), e.getY()));
                    } else {
                        selectedNode = node;
                    }
                    repaint();
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    Node node = findNode(e.getX(), e.getY());
                    if (node != null) {
                        removeNode(node);
                    } else {
                        Edge edge = findEdge(e.getX(), e.getY());
                        if (edge != null) {
                            removeEdge(edge);
                        }
                    }
                    repaint();
                } else if (SwingUtilities.isMiddleMouseButton(e)) {
                    Node node = findNode(e.getX(), e.getY());
                    if (node != null) {
                        selectedNode = node;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (selectedNode != null) {
                        Node node = findNode(e.getX(), e.getY());
                        if (node != null && node != selectedNode) {
                            edges.add(new Edge(selectedNode, node));
                        }
                        selectedNode = null;
                    }
                    repaint();
                } else if (SwingUtilities.isMiddleMouseButton(e)) {
                    selectedNode = null;
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isMiddleMouseButton(e) && selectedNode != null) {
                    selectedNode.setPoint(e.getX(), e.getY());
                    repaint();
                }
            }
        });
    }

    private Node findNode(int x, int y) {
        for (Node node : nodes) {
            if (node.contains(x, y)) {
                return node;
            }
        }
        return null;
    }

    private Edge findEdge(int x, int y) {
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
        return distance < 5.0; // You can adjust this threshold
    }

    private void removeNode(Node node) {
        nodes.remove(node);
        Iterator<Edge> iterator = edges.iterator();
        while (iterator.hasNext()) {
            Edge edge = iterator.next();
            if (edge.getStart() == node || edge.getEnd() == node) {
                iterator.remove();
            }
        }
    }

    private void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(edgeThickness));

        for (Edge edge : edges) {
            g2d.setColor(edgeColor);
            g2d.drawLine(edge.getStart().getPoint().x, edge.getStart().getPoint().y,
                    edge.getEnd().getPoint().x, edge.getEnd().getPoint().y);
        }

        for (Node node : nodes) {
            g2d.setColor(vertexColor);
            g2d.fillOval(node.getPoint().x - node.getRadius(), node.getPoint().y - node.getRadius(),
                    2 * node.getRadius(), 2 * node.getRadius());
            g2d.drawOval(node.getPoint().x - node.getRadius(), node.getPoint().y - node.getRadius(),
                    2 * node.getRadius(), 2 * node.getRadius());
        }
    }
}