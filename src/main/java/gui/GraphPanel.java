package gui;

import javax.naming.InitialContext;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;

import graph.*;
import utils.*;


public class GraphPanel extends JPanel {
    private Graph graph;
    private Node selectedNode;
    private GraphPanelStates current_state = GraphPanelStates.GRAPH_DRAWING;
    int step;
    int old_graph_hash;
    KruskalAlgorithm kruskal;
    ArrayList<Integer> kruskal_steps;

    Color DEFAULT_GRAPH_COLOR = Color.BLACK;
    Color DEFAULT_COLOR_FOR_OST = Color.GREEN;
    Color DEFAULT_PREV_STEP_COLOR = Color.BLUE;

    public GraphPanel() {

        this.graph = new Graph();
        selectedNode = null;
        step = 0;
        this.old_graph_hash = Objects.hash(graph.getNodes(), graph.getEdges());
        this.kruskal = new KruskalAlgorithm(graph);
        this.kruskal_steps = new ArrayList<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (current_state == GraphPanelStates.GRAPH_DRAWING) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        Node node = graph.findNode(e.getX(), e.getY());
                        if (node == null) {
                            graph.addNode(e.getX(), e.getY());
                            repaint();
                        } else {
                            selectedNode = node;
                            selectedNode.setColor(Color.RED);
//                            setEdgesColor(selectedNode, Color.RED);
                        }
                        repaint();
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        Node node = graph.findNode(e.getX(), e.getY());
                        if (node != null) {
                            graph.removeNode(node);
                        } else {
                            Edge edge = graph.findEdge(e.getX(), e.getY());
                            if (edge != null) {
                                graph.removeEdge(edge);
                            }
                        }
                        repaint();
                    } else if (SwingUtilities.isMiddleMouseButton(e)) {
                        Node node = graph.findNode(e.getX(), e.getY());
                        if (node != null) {
                            selectedNode = node;
                            selectedNode.setColor(Color.RED);
                            setEdgesColor(selectedNode, Color.RED);
                            repaint();
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (selectedNode != null) {
                        Node node = graph.findNode(e.getX(), e.getY());
                        if (node != null && node != selectedNode) {
                            String label = showInputDialog("Введите вес ребра:", "");
                            graph.addEdge(selectedNode, node, Color.RED, 3, label);
                        }
                        selectedNode.setColor(DEFAULT_GRAPH_COLOR);
                        setEdgesColor(selectedNode, DEFAULT_GRAPH_COLOR);
                        selectedNode = null;
                    }
                    repaint();
                } else if (e.getButton() == MouseEvent.BUTTON2) { // Middle mouse button
                    if (selectedNode != null) {
                        selectedNode.setColor(DEFAULT_GRAPH_COLOR);
                        setEdgesColor(selectedNode, DEFAULT_GRAPH_COLOR);
                        selectedNode = null;
                    }
                    repaint();
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (current_state == GraphPanelStates.WEIGHT_CHANGING) {
                    Edge edge = graph.findEdge(e.getX(), e.getY());
                    if (edge != null) {
                        String label = showInputDialog("Введите вес ребра:", edge.getLabel());
                        if (label != null) {
                            edge.setLabel(label);
                        }
                    }
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (current_state == GraphPanelStates.GRAPH_DRAWING) {
                    if (SwingUtilities.isMiddleMouseButton(e) && selectedNode != null) {
                        selectedNode.setPoint(e.getX(), e.getY());
                        selectedNode.setColor(Color.RED);
                        setEdgesColor(selectedNode, Color.RED);
                        repaint();
                    }
                }
            }
        });
    }

    private void setEdgesColor(Node node, Color color) {
        for (Edge edge : graph.getEdges()) {
            if (edge.getStart() == node || edge.getEnd() == node) {
                edge.setColor(color);
            }
        }
    }

    public void setState(GraphPanelStates state) {
        current_state = state;
    }

    public void clearGraph() {
        graph.clearGraph();
        step = 0;
        repaint();
    }

    public void loadGraph() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Загрузить граф");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);

        int option = fileChooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            if (fileToLoad != null && fileToLoad.getName().endsWith(".txt")) {
                graph.loadGraph(fileToLoad.getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(this, "Выбранный файл не является текстовым файлом (*.txt)", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
        repaint();
        step = 0;
    }

    public void saveGraph() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Сохранить граф");

        int option = fileChooser.showSaveDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filename.toLowerCase().endsWith(".txt")) {
                filename += ".txt"; // Пример: сохранение в текстовый файл
            }
            graph.saveGraph(filename);
        }
    }


    private String showInputDialog(String message, String initialValue) {
        JFormattedTextField textField = createNumberTextField();

        if (initialValue != null && !initialValue.isEmpty()) {
            try {
                textField.setValue(Integer.parseInt(initialValue));
            } catch (NumberFormatException e) {
                textField.setValue(0); // Если initialValue не является числом, установить значение по умолчанию
                initialValue = "0";
            }
        } else {
            initialValue = "0";
        }

        int result = JOptionPane.showConfirmDialog(this, textField, message, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        textField.requestFocusInWindow();
        if (result == JOptionPane.OK_OPTION) {
            String input = textField.getText();
            if (input == null || input.trim().isEmpty()) {
                input = "0"; // Устанавливаем значение по умолчанию, если ввод пустой
            }
            return input;
        }
        return initialValue;
    }

    private JFormattedTextField createNumberTextField() {
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false); // Отключаем разделение на группы
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0); // Set minimum value if needed
        formatter.setMaximum(Integer.MAX_VALUE); // Set maximum value if needed
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        return new JFormattedTextField(formatter);
    }


    public void kruskalAlgorithmFunc(JTextArea console) {
        int current_graph_hash = Objects.hash(graph.getNodes(), graph.getEdges());
//        System.out.println(current_graph_hash);
        if (current_graph_hash != old_graph_hash) {
            kruskal = new KruskalAlgorithm(graph);
            old_graph_hash = current_graph_hash;
            kruskal_steps = kruskal.KruskalOST();
        }

        if (kruskal_steps.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Для такого графа алгоритм не сработает!");
        } else {
            for (int i = 0; i < kruskal_steps.size(); i++) {
//                System.out.println(index + "-> " + graph.getEdges().get(index) + ": " + graph.getEdges().get(index).getLabel());
                Edge edge = graph.getEdges().get(kruskal_steps.get(i));
                edge.setColor(DEFAULT_COLOR_FOR_OST);
                graph.getNodes().get(graph.getNodes().indexOf(edge.getStart())).setColor(DEFAULT_COLOR_FOR_OST);
                graph.getNodes().get(graph.getNodes().indexOf(edge.getEnd())).setColor(DEFAULT_COLOR_FOR_OST);
                console.setText(console.getText() + "Шаг " + i + ": " + "Выбрано ребро с весом " + edge.getLabel() + "\n");
            }
            step = kruskal_steps.size();
            repaint();
        }

    }

    public void kruskalNextStep(JTextArea console) {
        int current_graph_hash = Objects.hash(graph.getNodes(), graph.getEdges());
//        System.out.println(current_graph_hash);
        if (current_graph_hash != old_graph_hash) {
            kruskal = new KruskalAlgorithm(graph);
            old_graph_hash = current_graph_hash;
            kruskal_steps = kruskal.KruskalOST();
        }

        if (kruskal_steps.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Для такого графа алгоритм не сработает!");
        } else {
            if (step >= kruskal_steps.size()) {

                JOptionPane.showMessageDialog(this, "Алгоритм завершен!");
            } else {
                int index = kruskal_steps.get(step);
                Edge edge = graph.getEdges().get(index);
                edge.setColor(DEFAULT_COLOR_FOR_OST);
                graph.getNodes().get(graph.getNodes().indexOf(edge.getStart())).setColor(DEFAULT_COLOR_FOR_OST);
                graph.getNodes().get(graph.getNodes().indexOf(edge.getEnd())).setColor(DEFAULT_COLOR_FOR_OST);
                if (step > 1) {
                    index = kruskal_steps.get(step - 2);
                    Edge predprev_edge = graph.getEdges().get(index);
                    predprev_edge.setColor(DEFAULT_COLOR_FOR_OST);
                    graph.getNodes().get(graph.getNodes().indexOf(predprev_edge.getStart())).setColor(DEFAULT_COLOR_FOR_OST);
                    graph.getNodes().get(graph.getNodes().indexOf(predprev_edge.getEnd())).setColor(DEFAULT_COLOR_FOR_OST);
                }
                if (step > 0 && step < kruskal_steps.size() - 1) {
                    index = kruskal_steps.get(step - 1);
                    Edge prev_edge = graph.getEdges().get(index);
                    prev_edge.setColor(DEFAULT_PREV_STEP_COLOR);
                    graph.getNodes().get(graph.getNodes().indexOf(prev_edge.getStart())).setColor(DEFAULT_PREV_STEP_COLOR);
                    graph.getNodes().get(graph.getNodes().indexOf(prev_edge.getEnd())).setColor(DEFAULT_PREV_STEP_COLOR);
                }
                console.setText(console.getText() + "Шаг " + step + ": " + "Выбрано ребро с весом " + edge.getLabel() + "\n");
                step++;
            }
            repaint();
        }

    }

    public void kruskalPrevStep(JTextArea console) {
        int current_graph_hash = Objects.hash(graph.getNodes(), graph.getEdges());
//        System.out.println(current_graph_hash);
        if (current_graph_hash != old_graph_hash) {
            kruskal = new KruskalAlgorithm(graph);
            old_graph_hash = current_graph_hash;
            kruskal_steps = kruskal.KruskalOST();
        }

        if (kruskal_steps.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Для такого графа алгоритм не сработает!");
        } else {
            if (step == 0) {
                JOptionPane.showMessageDialog(this, "Текущий шаг алгоритма - первый!");
            } else {
                step--;
                int index = kruskal_steps.get(step);
                Edge edge = graph.getEdges().get(index);
                edge.setColor(DEFAULT_GRAPH_COLOR);
                Node node1 = graph.getNodes().get(graph.getNodes().indexOf(edge.getStart()));
                Node node2 = graph.getNodes().get(graph.getNodes().indexOf(edge.getEnd()));
                boolean flag1 = true;
                boolean flag2 = true;
                for (int ind = 0; ind < step; ind++) {
                    int i = kruskal_steps.get(ind);
                    if (graph.getEdges().get(i).getStart().equals(node1) || graph.getEdges().get(i).getEnd().equals(node1))
                        flag1 = false;
                    if (graph.getEdges().get(i).getStart().equals(node2) || graph.getEdges().get(i).getEnd().equals(node2))
                        flag2 = false;
                }
                if (flag1) node1.setColor(DEFAULT_GRAPH_COLOR);
                if (flag2) node2.setColor(DEFAULT_GRAPH_COLOR);
                if (step > 0) {
                    index = kruskal_steps.get(step - 1);
                    Edge prev_edge = graph.getEdges().get(index);
                    prev_edge.setColor(DEFAULT_COLOR_FOR_OST);
                    graph.getNodes().get(graph.getNodes().indexOf(prev_edge.getStart())).setColor(DEFAULT_COLOR_FOR_OST);
                    graph.getNodes().get(graph.getNodes().indexOf(prev_edge.getEnd())).setColor(DEFAULT_COLOR_FOR_OST);
                }
                console.setText(console.getText() + "Шаг " + step + ": " + "Выбрано ребро с весом " + edge.getLabel() + "\n");
            }
            repaint();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        for (Edge edge : graph.getEdges()) {
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

                int squareSize = 20;

                // Позиция метки
                int squareX = midX - squareSize / 2;
                int squareY = midY - squareSize / 2;

                // Залить рамку белым цветом
                g2d.setColor(Color.WHITE);
                g2d.fillRect(squareX, squareY, squareSize, squareSize);

                // Нарисовать контур черным цветом
                g2d.setColor(DEFAULT_GRAPH_COLOR);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRect(squareX, squareY, squareSize, squareSize);

                // Позиция текста (чтобы текст был в центре квадрата)
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(label);
                int textHeight = fm.getAscent();

                int textX = squareX + (squareSize - textWidth) / 2;
                int textY = squareY + ((squareSize - textHeight) / 2) + fm.getAscent();

                // Нарисовать текст
                g2d.drawString(label, textX, textY);
            }
        }

        for (Node node : graph.getNodes()) {
            g2d.setColor(node.getColor());
            g2d.fillOval(node.getPoint().x - node.getRadius(), node.getPoint().y - node.getRadius(),
                    2 * node.getRadius(), 2 * node.getRadius());
//            g2d.drawOval(node.getPoint().x - node.getRadius(), node.getPoint().y - node.getRadius(),
//                    2 * node.getRadius(), 2 * node.getRadius());
        }
    }

}
