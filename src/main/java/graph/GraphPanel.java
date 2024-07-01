package graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

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
//                        System.out.println(graph.getNodes().getFirst().getPoint().getX() + " " + graph.getNodes().getFirst().getPoint().getY());
//                        graph.loadGraph("src/main/resources/save_graph.txt");
//                        graph.saveGraph("src/main/resources/save_graph.txt");
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
                            graph.addEdge(selectedNode, node, Color.RED, 3);
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

        for (Graph.Edge edge : graph.getEdges()) {
            g2d.setColor(edge.getColor());
            g2d.setStroke(new BasicStroke(edge.getThickness()));
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
