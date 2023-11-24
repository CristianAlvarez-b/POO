package Gomoku.src.presentation;

import javax.swing.*;
import java.awt.*;

public class GomokuGUI extends JFrame {
    private JPanel boardPanel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel mainPanel;

    private GomokuGUI(){
        prepareElements();
        //prepareActions();
    }
    private void prepareElements(){
        prepareScreens();
    }
    private void prepareScreens(){
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        JPanel gamePanel = createGamePanel();
        cardPanel.add(gamePanel, "game");
    }
    private JPanel createGamePanel(){
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        createBoardPanel();
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        return mainPanel;
    }
    private JPanel createBoardPanel(){
        JPanel panel = new JPanel(new GridLayout(15, 15));

        // Create and add buttons for each intersection
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(40, 40)); // Adjust the size as needed
                panel.add(button);
            }
        }

        return panel;
    }
    public static void main(String[] args) {
        GomokuGUI gui=new GomokuGUI();
        gui.setVisible(true);
    }
}
