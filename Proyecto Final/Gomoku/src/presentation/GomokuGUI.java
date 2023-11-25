package presentation;

import domain.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GomokuGUI extends JFrame {
    public static final int SIDE = 30;
    public static final int SIZE = 30;
    private Gomoku gomoku;
    private JPanel mainPanel;
    private JPanel boardPanel;
    private Cell[][] cellMatrix;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean turn = true;

    public GomokuGUI() throws Exception {
        gomoku = new Gomoku(15, 15, 800, 1000);
        prepareElements();
        prepareActions();
    }

    private void prepareElements() {
        setTitle("Gomoku");
        setLayout(new BorderLayout());
        setSize(new Dimension(SIDE * SIZE, SIDE * SIZE + 50));
        setResizable(false);
        mainPanel = new JPanel(new BorderLayout());
        prepareElementsBoard();
        add(mainPanel); // Add mainPanel to the frame
    }

    private void prepareElementsBoard() {
        if (boardPanel == null) {
            //gomoku = new G(row,column);
            // Si el tablero aún no se ha creado, crearlo y agregarlo al mainPanel
            cellMatrix = gomoku.board();
            boardPanel = new JPanel(new GridLayout(cellMatrix.length, cellMatrix[0].length));
            Piedra[][] piedras = new Piedra[cellMatrix.length][cellMatrix[0].length];

            for (int i = 0; i < cellMatrix.length; i++) {
                for (int j = 0; j < cellMatrix[0].length; j++) {
                    Piedra piedra = new Piedra(false);
                    piedras[i][j] = piedra;
                    piedra.addMouseListener(new CellClickListener(i, j));
                    boardPanel.add(piedra);

                }
            }
            mainPanel.add(boardPanel, BorderLayout.CENTER);
        } else {
            // Si el tablero ya está creado, simplemente refresca su contenido
            //gomoku = null;
            //boardPanel = null;
            prepareElements();
            refresh();
        }
    }

    private void refresh() {
        cellMatrix = gomoku.board();
        Component[] components = boardPanel.getComponents();
        for (int i = 0; i < cellMatrix.length; i++) {
            for (int j = 0; j < cellMatrix[0].length; j++) {
                if(cellMatrix[i][j].getStone() != null) {
                    Piedra piedra = (Piedra) components[i * cellMatrix[0].length + j];
                    // Configurar el color de las joyas y el fondo según la matriz
                    piedra.setPiedraColor(cellMatrix[i][j].getStone().getColor());
                    piedra.makeVisible();
                }
            }
        }
        boardPanel.revalidate(); // Asegurar que el panel se redibuje correctamente
        boardPanel.repaint();
    }

    private void prepareActions() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private class CellClickListener extends MouseAdapter {
        private final int row;
        private final int col;

        public CellClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                ponerFicha(row, col);
            } catch (GomokuException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void ponerFicha(int row, int col) throws GomokuException {
        Color color;
        if (turn) {
            color = Color.BLACK;
            turn = false;
        } else {
            color = Color.WHITE;
            turn = true;
        }
        try {
            gomoku.play(row, col, new Stone(color));
            refresh();
        } catch (GomokuException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static class Piedra extends JPanel {
        private Color piedraColor;
        private Color backgroundColor;
        //private int shape;
        private boolean isVisible;

        public Piedra(boolean isVisible) {
            this.piedraColor = Color.WHITE; // Color predeterminado
            this.backgroundColor = new Color(222, 184, 135, 80); // Color predeterminado
            this.isVisible = isVisible;
        }

        //public void setShape(int shape){
//        this.shape = shape;
//    }

        public void setPiedraColor(Color piedraColor) {
            this.piedraColor = piedraColor;
        }

        public void makeVisible() {
            this.isVisible = true;
        }

        public void makeInvisible() {
            this.isVisible = false;
        }

        public void setBackgroundColor(Color backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(backgroundColor);
            if (!isVisible) {
                g.fillRect(0, 0, getWidth(), getHeight());
                setBorder(new LineBorder(Color.BLACK, 1)); // Puedes ajustar el color y el grosor del borde según tus preferencias
            } else {
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(piedraColor);
                setBorder(new LineBorder(Color.BLACK, 1)); // Puedes ajustar el color y el grosor del borde según tus preferencias
                g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
            }

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                GomokuGUI gui = new GomokuGUI();
                gui.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

