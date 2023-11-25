package presentation;

import domain.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private int row = 15;
    private int col = 15;

    public GomokuGUI() throws Exception {
        gomoku = new Gomoku(row, col, 800, 1000);
        prepareElements();
        prepareActions();
    }

    private void prepareElements() {
        setTitle("Gomoku");
        setLayout(new BorderLayout());
        setSize(new Dimension(SIDE * SIZE, SIDE * SIZE + 50));
        setResizable(false);
        mainPanel = new JPanel(new BorderLayout());
        addTopPanel();
        addLeftPanel();
        addRightPanel();
        addBottomPanel();
        prepareElementsBoard();
        prepareElementsMenu();
        add(mainPanel); // Add mainPanel to the frame
    }
    private void prepareElementsMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        JMenuItem nuevoMenuItem = new JMenuItem("Nuevo");

        JSeparator separator1 = new JSeparator();

        JMenuItem abrirMenuItem = new JMenuItem("Abrir");
        JMenuItem salvarMenuItem = new JMenuItem("Guardar como");

        JSeparator separator2 = new JSeparator();

        JMenuItem importarMenuItem = new JMenuItem("Importar");
        JMenuItem exportarMenuItem = new JMenuItem("Exportar");

        JSeparator separator3 = new JSeparator();

        JMenuItem salirMenuItem = new JMenuItem("Salir");

        menu.add(nuevoMenuItem);
        menu.add(separator1);
        menu.add(abrirMenuItem);
        menu.add(salvarMenuItem);
        menu.add(separator2); // Separador
        menu.add(importarMenuItem);
        menu.add(exportarMenuItem);
        menu.add(separator3);
        menu.add(salirMenuItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);
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
                    //piedra.setOpaque(false);
                    boardPanel.add(piedra);
                }
            }
            mainPanel.add(boardPanel, BorderLayout.CENTER);
        } else {
            // Si el tablero ya está creado, simplemente refresca su contenido
            gomoku = null;
            boardPanel = null;
            prepareElements();
            refresh();
        }
    }
    private void addTopPanel() {
        // Crear un panel para la parte superior
        JPanel topPanel = new JPanel(new BorderLayout());
        // Agregar el título
        ImageIcon gomoku = new ImageIcon("GomokuImages/tituloImagen.png");
        ImageIcon resized = new ImageIcon(gomoku.getImage().getScaledInstance(900,100, Image.SCALE_SMOOTH));
        JLabel image = new JLabel(resized, JLabel.CENTER);
        topPanel.add(image, BorderLayout.NORTH);
        mainPanel.add(topPanel, BorderLayout.NORTH);
    }
    private void addLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());

        // Cargar la imagen
        ImageIcon imagen = new ImageIcon("GomokuImages/player1.png");
        ImageIcon resized = new ImageIcon(imagen.getImage().getScaledInstance(150, 75, Image.SCALE_SMOOTH));

        // Crear un JLabel con la imagen y establecer un borde
        JLabel imageLabel = new JLabel(resized, JLabel.CENTER);
        if (turn) {
            imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        } else {
            imageLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        }

        // Agregar el JLabel al JPanel
        leftPanel.add(imageLabel, BorderLayout.NORTH);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        // Agregar el JPanel al contenedor principal (mainPanel)
        mainPanel.add(leftPanel, BorderLayout.WEST);
    }

    private void addRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Cargar la imagen
        ImageIcon imagen = new ImageIcon("GomokuImages/player2.png");
        ImageIcon resized = new ImageIcon(imagen.getImage().getScaledInstance(150, 75, Image.SCALE_SMOOTH));

        // Crear un JLabel con la imagen y establecer un borde
        JLabel imageLabel = new JLabel(resized, JLabel.CENTER);
        if (!turn) {
            imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        } else {
            imageLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        }

        // Agregar el JLabel al JPanel
        rightPanel.add(imageLabel, BorderLayout.NORTH);

        // Agregar el JPanel al contenedor principal (mainPanel)
        mainPanel.add(rightPanel, BorderLayout.EAST);
    }
    private void addBottomPanel() {
        // Crear un panel para la parte inferior
        JPanel bottomPanel = new JPanel();
        // Agregar los botones "Finalizar" y "Resetear"
        JButton guardarFichaButton = new JButton("Guardar ficha");

        // Agregar ActionListener a los botones según sea necesario
        guardarFichaButton.addActionListener(e -> guardarFicha());
        bottomPanel.add(guardarFichaButton);
        // Agregar el panel inferior a la parte inferior de la pantalla principal
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
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
        updateBorders();
        boardPanel.revalidate(); // Asegurar que el panel se redibuje correctamente
        boardPanel.repaint();
    }

    private void prepareActions() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        prepareActionsMenu();
    }
    private void prepareActionsMenu(){
        getJMenuBar().getMenu(0).getItem(0).addActionListener(e -> {
            try {
                optionNew();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error inesperado");
            }
        });
        getJMenuBar().getMenu(0).getItem(2).addActionListener(e -> {
            //optionOpen();
        });
        getJMenuBar().getMenu(0).getItem(3).addActionListener(e -> {
            //optionSave();
        });
        getJMenuBar().getMenu(0).getItem(5).addActionListener(e -> {
            //optionImport();
        });
        getJMenuBar().getMenu(0).getItem(6).addActionListener(e -> {
            //optionExport();
        });
        getJMenuBar().getMenu(0).getItem(8).addActionListener(e -> {
            optionExit();
        });
    }
    private void optionNew() throws Exception {
        mainPanel.remove(boardPanel);
        boardPanel = null;
        cellMatrix = null;
        // Crear un nuevo tablero y panel
        gomoku = new Gomoku(row, col, 800, 1000);
        prepareElementsBoard();
        refresh();
    }
    private void optionExit(){
        System.exit(0);
    }
    private void guardarFicha(){
        //en contruccion
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
                setBorder(new LineBorder(new Color(139, 69, 19), 1)); // Puedes ajustar el color y el grosor del borde según tus preferencias
            } else {
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(piedraColor);
                setBorder(new LineBorder(new Color(139, 69, 19), 1)); // Puedes ajustar el color y el grosor del borde según tus preferencias
                g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
            }

        }
    }
    private void updateBorders() {
        Component[] leftPanelComponents = ((JPanel) mainPanel.getComponent(1)).getComponents(); // Obtener componentes del panel izquierdo
        Component[] rightPanelComponents = ((JPanel) mainPanel.getComponent(2)).getComponents(); // Obtener componentes del panel derecho

        if (turn) {
            // Si es el turno de player1 (izquierda), actualizar el borde de player1 y quitar el borde de player2
            ((JLabel) leftPanelComponents[0]).setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            ((JLabel) rightPanelComponents[0]).setBorder(null);
        } else {
            // Si es el turno de player2 (derecha), actualizar el borde de player2 y quitar el borde de player1
            ((JLabel) leftPanelComponents[0]).setBorder(null);
            ((JLabel) rightPanelComponents[0]).setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
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

