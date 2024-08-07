package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import utils.GraphPanelStates;

public class App extends JFrame {
    int DEFAULT_WIDTH = 800;
    int DEFAULT_HEIGHT = 600;
    String Title = "KruskalLimitedEdition.official.feat(Romchik,Danchik,Dianchik)";

    private JPanel MainPanel;
    private JPanel ToolsPanel;
    private JButton DrawButton;
    private JButton EditButton;
    private JButton DeleteButton;
    private JButton DownloadButton;
    private JButton UploadButton;
    private JPanel GraphEditPanel;
    private JScrollPane ConsolePanel;
    private JButton StartButton;
    private JButton PrevStepButton;
    private JButton NextStepButton;
    private JPanel ControlPanel;
    private JTextArea ConsoleTextArea;
    private JTextArea HelpText;

    public App() {
        setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width / 2 - DEFAULT_WIDTH / 2, dimension.height / 2 - DEFAULT_HEIGHT / 2, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("src/main/resources/main_icon.png");
        setIconImage(icon.getImage());
        setTitle(Title);

        HelpText.setFont(new Font(HelpText.getFont().getName(), HelpText.getFont().getStyle(), 12));
        HelpText.setText("""
                Нажмите ЛКМ чтобы добавить вершины и связать их ребром.
                Нажмите ПКМ чтобы удалить вершину/ребро.
                Нажмите и удерживайте колесико, чтобы перетащить вершину.""");

        ConsoleTextArea.setFont(new Font(HelpText.getFont().getName(), HelpText.getFont().getStyle(), 12));
        ConsoleTextArea.setLineWrap(true);
        ConsoleTextArea.setWrapStyleWord(true);

        DeleteButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphPanel g = (GraphPanel) GraphEditPanel;
                g.clearGraph();
            }
        });

        DrawButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphPanel g = (GraphPanel) GraphEditPanel;
                g.setState(GraphPanelStates.GRAPH_DRAWING);
                HelpText.setText("""
                Нажмите ЛКМ чтобы добавить вершины и связать их ребром.
                Нажмите ПКМ чтобы удалить вершину/ребро.
                Нажмите и удерживайте колесико, чтобы перетащить вершину.""");
            }
        });

        EditButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphPanel g = (GraphPanel) GraphEditPanel;
                g.setState(GraphPanelStates.WEIGHT_CHANGING);
                HelpText.setText("""
                Нажмите ЛКМ на ребро чтобы изменить вес.""");
            }
        });

        UploadButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphPanel g = (GraphPanel) GraphEditPanel;
                g.saveGraph();
            }
        });

        DownloadButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphPanel g = (GraphPanel) GraphEditPanel;
                g.loadGraph();
            }
        });

        StartButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphPanel g = (GraphPanel) GraphEditPanel;
                g.kruskalAlgorithmFunc(ConsoleTextArea);
            }
        });

        NextStepButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphPanel g = (GraphPanel) GraphEditPanel;
                g.kruskalNextStep(ConsoleTextArea);
            }
        });

        PrevStepButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphPanel g = (GraphPanel) GraphEditPanel;
                g.kruskalPrevStep(ConsoleTextArea);
            }
        });

        setContentPane(MainPanel);
        setVisible(true);

        setIcon(DrawButton, "src/main/resources/draw_icon.png");
        setIcon(EditButton, "src/main/resources/edit_icon.png");
        setIcon(DeleteButton, "src/main/resources/delete_icon.png");
        setIcon(DownloadButton, "src/main/resources/download_icon.png");
        setIcon(UploadButton, "src/main/resources/upload_icon.png");
        setIcon(StartButton, "src/main/resources/start_icon.png");
        setIcon(PrevStepButton, "src/main/resources/prev_step_icon.png");
        setIcon(NextStepButton, "src/main/resources/next_step_icon.png");
    }

    public static void setIcon(JButton button, String image_path) {
        ImageIcon originalIcon = new ImageIcon(image_path);
        Image scaledIcon = originalIcon.getImage().getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledIcon));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
//        button.setFocusPainted(false);
        button.setOpaque(false);
    }

    private void createUIComponents() {
        GraphEditPanel = new GraphPanel();
        ToolsPanel = new ToolkitPanel();
        HelpText = new HelpTextArea();
    }

}
