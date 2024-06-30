package gui;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    int DEFAULT_WIDTH = 800;
    int DEFAULT_HEIGHT = 600;
    String Title = "Facebook";

    private JPanel MainPanel;
    private JPanel ToolsPanel;
    private JButton DrawButton;
    private JButton EditButton;
    private JButton DeleteButton;
    private JButton DownloadButton;
    private JButton UploadButton;
    private JPanel GraphPanel;
    private JScrollPane ConsolePanel;
    private JButton StartButton;
    private JButton PrevStepButton;
    private JButton NextStepButton;
    private JPanel ControlPanel;
    private JTextArea ConsoleTextArea;

    public App() {
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("src/main/resources/main_icon.png");
        setIconImage(icon.getImage());
        setTitle(Title);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width/2 - DEFAULT_WIDTH/2, dimension.height/2 - DEFAULT_HEIGHT/2, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setContentPane(MainPanel);
        GraphPanel.setPreferredSize(new Dimension(3*MainPanel.getWidth()/4, MainPanel.getHeight()));
        ToolsPanel.setPreferredSize(new Dimension(MainPanel.getWidth()/4, MainPanel.getHeight()));

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

}
