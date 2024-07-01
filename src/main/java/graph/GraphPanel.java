//package graph;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseMotionAdapter;
//import java.awt.geom.Line2D;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//public class GraphPanel extends JPanel {
//    private List<Node> nodes;
//    private List<Edge> edges;
//    private Node selectedNode;
//    private int edgeThickness = 3;
//
//    public GraphPanel() {
//        nodes = new ArrayList<>();
//        edges = new ArrayList<>();
//        selectedNode = null;
//
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                if (SwingUtilities.isLeftMouseButton(e)) {
//                    Node node = findNode(e.getX(), e.getY());
//                    if (node == null) {
//                        nodes.add(new Node(e.getX(), e.getY()));
//                    } else {
//                        selectedNode = node;
//                        selectedNode.setColor(Color.RED); // Установить цвет вершины в красный
//                        setEdgesColor(selectedNode, Color.RED); // Установить цвет рёбер в красный
//                    }
//                    repaint();
//                } else if (SwingUtilities.isRightMouseButton(e)) {
//                    Node node = findNode(e.getX(), e.getY());
//                    if (node != null) {
//                        removeNode(node);
//                    } else {
//                        Edge edge = findEdge(e.getX(), e.getY());
//                        if (edge != null) {
//                            removeEdge(edge);
//                        }
//                    }
//                    repaint();
//                } else if (SwingUtilities.isMiddleMouseButton(e)) {
//                    Node node = findNode(e.getX(), e.getY());
//                    if (node != null) {
//                        selectedNode = node;
//                        selectedNode.setColor(Color.RED); // Установить цвет вершины в красный при выборе
//                        setEdgesColor(selectedNode, Color.RED); // Установить цвет рёбер в красный при выборе
//                        repaint();
//                    }
//                }
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                if (SwingUtilities.isLeftMouseButton(e)) {
//                    if (selectedNode != null) {
//                        Node node = findNode(e.getX(), e.getY());
//                        if (node != null && node != selectedNode) {
//                            edges.add(new Edge(selectedNode, node, Color.RED)); // Установить цвет ребра в красный
//                        }
//                        selectedNode.setColor(Color.BLACK); // Вернуть цвет вершины в чёрный
//                        setEdgesColor(selectedNode, Color.BLACK); // Вернуть цвет рёбер в чёрный
//                        selectedNode = null;
//                    }
//                    repaint();
//                } else if (SwingUtilities.isMiddleMouseButton(e)) {
//                    if (selectedNode != null) {
//                        selectedNode.setColor(Color.BLACK); // Вернуть цвет вершины в чёрный
//                        setEdgesColor(selectedNode, Color.BLACK); // Вернуть цвет рёбер в чёрный
//                        selectedNode = null;
//                    }
//                    repaint();
//                }
//            }
//        });
//
//        addMouseMotionListener(new MouseMotionAdapter() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                if (SwingUtilities.isMiddleMouseButton(e) && selectedNode != null) {
//                    selectedNode.setPoint(e.getX(), e.getY());
//                    selectedNode.setColor(Color.RED); // Установить цвет вершины в красный при перетаскивании
//                    setEdgesColor(selectedNode, Color.RED); // Установить цвет рёбер в красный при перетаскивании
//                    repaint();
//                }
//            }
//        });
//    }
//
//    private Node findNode(int x, int y) {
//        for (Node node : nodes) {
//            if (node.contains(x, y)) {
//                return node;
//            }
//        }
//        return null;
//    }
//
//    private Edge findEdge(int x, int y) {
//        for (Edge edge : edges) {
//            Point p1 = edge.getStart().getPoint();
//            Point p2 = edge.getEnd().getPoint();
//            if (isPointNearLine(x, y, p1.x, p1.y, p2.x, p2.y)) {
//                return edge;
//            }
//        }
//        return null;
//    }
//
//    private boolean isPointNearLine(int x, int y, int x1, int y1, int x2, int y2) {
//        double distance = Line2D.ptSegDist(x1, y1, x2, y2, x, y);
//        return distance < 5.0;
//    }
//
//    private void removeNode(Node node) {
//        nodes.remove(node);
//        Iterator<Edge> iterator = edges.iterator();
//        while (iterator.hasNext()) {
//            Edge edge = iterator.next();
//            if (edge.getStart() == node || edge.getEnd() == node) {
//                iterator.remove();
//            }
//        }
//    }
//
//    private void removeEdge(Edge edge) {
//        edges.remove(edge);
//    }
//
//    private void setEdgesColor(Node node, Color color) {
//        for (Edge edge : edges) {
//            if (edge.getStart() == node || edge.getEnd() == node) {
//                edge.setColor(color);
//            }
//        }
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setStroke(new BasicStroke(edgeThickness));
//
//        for (Edge edge : edges) {
//            g2d.setColor(edge.getColor());
//            g2d.drawLine(edge.getStart().getPoint().x, edge.getStart().getPoint().y,
//                    edge.getEnd().getPoint().x, edge.getEnd().getPoint().y);
//        }
//
//        for (Node node : nodes) {
//            g2d.setColor(node.getColor()); // Использование цвета вершины
//            g2d.fillOval(node.getPoint().x - node.getRadius(), node.getPoint().y - node.getRadius(),
//                    2 * node.getRadius(), 2 * node.getRadius());
//            g2d.drawOval(node.getPoint().x - node.getRadius(), node.getPoint().y - node.getRadius(),
//                    2 * node.getRadius(), 2 * node.getRadius());
//        }
//    }
//}

package graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GraphPanel extends JPanel {
    private Graph graph;
    private Graph.Node selectedNode;
    private int edgeThickness = 3;

    public GraphPanel() {
        graph = new Graph();
        selectedNode = null;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Graph.Node node = graph.findNode(e.getX(), e.getY());
                    if (node == null) {
                        graph.addNode(e.getX(), e.getY());
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
                } else if (SwingUtilities.isMiddleMouseButton(e)) {
                    Graph.Node node = graph.findNode(e.getX(), e.getY());
                    if (node != null) {
                        selectedNode = node;
                        selectedNode.setColor(Color.RED);
                        setEdgesColor(selectedNode, Color.RED);
                        repaint();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (selectedNode != null) {
                        Graph.Node node = graph.findNode(e.getX(), e.getY());
                        if (node != null && node != selectedNode) {
                            graph.addEdge(selectedNode, node, Color.RED);
                        }
                        selectedNode.setColor(Color.BLACK);
                        setEdgesColor(selectedNode, Color.BLACK);
                        selectedNode = null;
                    }
                    repaint();
                } else if (SwingUtilities.isMiddleMouseButton(e)) {
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(edgeThickness));

        for (Graph.Edge edge : graph.getEdges()) {
            g2d.setColor(edge.getColor());
            g2d.drawLine(edge.getStart().getPoint().x, edge.getStart().getPoint().y,
                    edge.getEnd().getPoint().x, edge.getEnd().getPoint().y);
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

