package presentation;

import domain.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class GomokuGUI extends JFrame {
    private JPanel configuraciones;
    public static final int SIDE = 30;
    public static final int SIZE = 30;
    private Gomoku gomoku;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel mainPanel;
    private JPanel gamePanel;
    private JPanel boardPanel;
    private Cell[][] cellMatrix;
    private String nombreJ1 = "Jugador 1.";
    private String nombreJ2 = "Jugador 2.";
    private boolean turn = true;
    private int size = 10;
    private int stoneLimit = size*size;
    private int timeLimit = -1;
    private  Color colorJ1 = Color.BLACK;
    private  Color colorJ2 = Color.WHITE;
    private boolean canRefill;
    private Stone[] piedrasJ1;
    private Stone[] piedrasJ2;
    public GomokuGUI() throws Exception {
        gomoku = new Gomoku(size ,stoneLimit, timeLimit);
        prepareElements();
        prepareActions();
    }

    private void prepareElements() throws Exception {
        prepareScreens();
        prepareActionsMenu();
        setTitle("Gomoku");
        setSize(new Dimension(SIDE * SIZE+150, SIDE * SIZE + 50));
         // Add mainPanel to the frame
    }
    private void prepareScreens() throws Exception {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        setTitle("Gomoku.");
        JPanel initialPanel = createInitialPanel();
        cardPanel.add(initialPanel, "initial");
        JPanel configurePanel = createConfiguraciones();
        cardPanel.add(configurePanel, "config");
        JPanel gamePanel = createGamePanel();
        cardPanel.add(gamePanel, "game");
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }
    private JPanel createInitialPanel() {
        JPanel initialPanel = new JPanel(new GridBagLayout());
        initialPanel.setBackground(Color.BLACK);
        // Crear un título con "Go" en negrita y "moku" en normal
        JPanel titulo = new JPanel(new BorderLayout());
        JLabel titulo1 = new JLabel("<html><font size=800><b>GO</b></font ><font size=800>MOKU</font></html>", SwingConstants.CENTER);
        titulo1.setFont(new Font("Arial", Font.ITALIC, 400));
        titulo1.setPreferredSize(new Dimension(400, 400));
        titulo.add(titulo1, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Espacio entre los botones

        JButton button1 = new JButton("NEW GAME");
        JButton button2 = new JButton("CONTINUE");
        JButton button3 = new JButton("RULES");

        // Configurar el tamaño de los botones
        Dimension buttonSize = new Dimension(400, 100);
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);

        // Añadir ActionListeners según sea necesario
        button1.addActionListener(e -> cardLayout.show(cardPanel, "config"));

        // Añadir los botones al panel con GridBagLayout
        buttonPanel.add(button1, gbc);
        buttonPanel.setBackground(Color.BLACK);

        gbc.gridy++;
        buttonPanel.add(button2, gbc);

        gbc.gridy++;
        buttonPanel.add(button3, gbc);

        // Añadir el título y el panel de botones al panel principal
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH; // Alineación del título
        initialPanel.add(titulo, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER; // Alineación del panel de botones
        initialPanel.add(buttonPanel, gbc);


        return initialPanel;
    }

    private JPanel createConfiguraciones(){

        configuraciones = new JPanel(new GridBagLayout());
        crearPanelJ1();
        crearPanelJ2();
        crearPanelTamaño();
        return configuraciones;
    }
    private void crearPanelJ1(){
        HashMap<String, Color> coloresMap = new HashMap<>();
        coloresMap.put("NEGRO", Color.BLACK);
        coloresMap.put("GRIS", Color.GRAY);
        coloresMap.put("AZUL", Color.BLUE);
        coloresMap.put("VERDE", Color.GREEN);
        coloresMap.put("PURPURA", new Color(128, 0, 128)); // Puedes ajustar estos valores según tus preferencias
        coloresMap.put("ROJO", Color.RED);
        JPanel jugador1 = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("JUGADOR 1", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        GridBagConstraints gbc = new GridBagConstraints();
        // Add the label "Ingresa tu nombre:"
        JLabel nombreLabel = new JLabel("Ingresa tu nombre:");

        JTextArea nombre = new JTextArea();

        JPanel selectColorFicha = new JPanel(new FlowLayout());
        JLabel colorLabel1 = new JLabel("Selecciona el color de ficha:");
        String[] coloresJ1 = {"NEGRO", "GRIS", "AZUL", "VERDE", "PURPURA", "ROJO"};
        JComboBox<String> ficha1 = new JComboBox<>(coloresJ1);
        selectColorFicha.add(colorLabel1);
        selectColorFicha.add(ficha1);

        jugador1.add(titulo, BorderLayout.NORTH);

        // Add the nombreLabel and nombre to jugador1
        jugador1.add(nombreLabel, BorderLayout.WEST);
        jugador1.add(nombre, BorderLayout.CENTER);

        ficha1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String colorSeleccionado = (String) ficha1.getSelectedItem();
                colorJ1 = coloresMap.get(colorSeleccionado);
            }
        });
        jugador1.add(selectColorFicha, BorderLayout.SOUTH);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        configuraciones.add(jugador1, gbc);
    }
    private void crearPanelJ2() {
        HashMap<String, Color> coloresMap = new HashMap<>();
        coloresMap.put("BLANCO", Color.WHITE);
        coloresMap.put("AMARILLO", Color.YELLOW);
        coloresMap.put("AZUL", Color.BLUE);
        coloresMap.put("VERDE", Color.GREEN);
        coloresMap.put("PURPURA", new Color(128, 0, 128));
        coloresMap.put("ROJO", Color.RED);

        JPanel jugador2 = new JPanel();
        jugador2.setLayout(new BoxLayout(jugador2, BoxLayout.Y_AXIS)); // Set layout to BoxLayout

        JLabel titulo = new JLabel("JUGADOR 2", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel nombreLabel = new JLabel("Ingresa tu nombre:");
        JTextArea nombre = new JTextArea();

        JPanel selectColorFicha = new JPanel(new FlowLayout());
        JLabel colorLabel1 = new JLabel("Selecciona el color de ficha:");
        String[] coloresJ2 = {"BLANCO", "AMARILLO", "AZUL", "VERDE", "PURPURA", "ROJO"};
        JComboBox<String> ficha2 = new JComboBox<>(coloresJ2);

        JCheckBox jugadorCheckBox = new JCheckBox("Jugar contra maquina");
        jugadorCheckBox.setSelected(false);  // Default to human player

        selectColorFicha.add(colorLabel1);
        selectColorFicha.add(ficha2);

        ficha2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String colorSeleccionado = (String) ficha2.getSelectedItem();
                colorJ2 = coloresMap.get(colorSeleccionado);
            }
        });

        jugador2.add(titulo);
        jugador2.add(nombreLabel);
        jugador2.add(nombre);
        jugador2.add(selectColorFicha);
        jugador2.add(jugadorCheckBox);

        gbc.gridx = 0;
        gbc.gridy = 1; // Assuming the second panel goes below the first one
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        configuraciones.add(jugador2, gbc);
    }
    private void crearPanelTamaño() {
        GridBagConstraints gbc = new GridBagConstraints();

        // Primer TextField
        JLabel tamañoLabel = new JLabel("Tamaño del tablero:");
        JTextField tamañoTextField = new JTextField();
        tamañoTextField.setColumns(10);
        tamañoTextField.setColumns(10); // Puedes ajustar el tamaño según tus preferencias
        tamañoTextField.addActionListener(e -> {
            try {
                size = Integer.parseInt(tamañoTextField.getText());
            } catch (NumberFormatException ex) {
                // Manejar la excepción si el texto no es un número entero válido
                // Puedes mostrar un mensaje de error o realizar otras acciones según tus necesidades
            }
        });
        // Segundo TextField
        JLabel especialesLabel = new JLabel("Porcentaje de especiales:");
        JTextField especialesTextField = new JTextField();
        especialesTextField.setColumns(10); // Puedes ajustar el tamaño según tus preferencias
//        especialesTextField.addActionListener(e -> {
//            try {
//                size = Integer.parseInt(especialesTextField.getText());
//            } catch (NumberFormatException ex) {
//                // Manejar la excepción si el texto no es un número entero válido
//                // Puedes mostrar un mensaje de error o realizar otras acciones según tus necesidades
//            }
//        });
        // Botón
        JButton boton = new JButton("Iniciar Juego");
        boton.addActionListener(e -> cardLayout.show(cardPanel, "game"));

        // Agregar componentes al panel de configuraciones
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        configuraciones.add(tamañoLabel, gbc);

        gbc.gridy = 3;
        configuraciones.add(tamañoTextField, gbc);

        gbc.gridy = 4;
        configuraciones.add(especialesLabel, gbc);

        gbc.gridy = 5;
        configuraciones.add(especialesTextField, gbc);

        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        configuraciones.add(boton, gbc);
    }

    private JPanel createGamePanel() throws Exception {
        gamePanel = new JPanel(new BorderLayout());
        addTopPanel();
        addLeftPanel();
        addRightPanel();
        addBottomPanel();
        prepareElementsBoard();
        prepareElementsMenu();
        return gamePanel;
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
    private void prepareElementsBoard() throws Exception {
        if (boardPanel == null) {
            gomoku = new Gomoku(size, stoneLimit, timeLimit);
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
            gamePanel.add(boardPanel, BorderLayout.CENTER);
        } else {
            // Si el tablero ya está creado, simplemente refresca su contenido
            gomoku = null;
            boardPanel = null;
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
        gamePanel.add(topPanel, BorderLayout.NORTH);
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
        gamePanel.add(leftPanel, BorderLayout.WEST);
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
        gamePanel.add(rightPanel, BorderLayout.EAST);
    }
    private void addBottomPanel() {
        // Crear un panel para la parte inferior
        JPanel bottomPanel = new JPanel();
        // Agregar los botones "Finalizar" y "Resetear"
        JButton guardarFichaButton = new JButton("Usar ficha especial");

        // Agregar ActionListener a los botones según sea necesario
        guardarFichaButton.addActionListener(e -> {
            try {
                optionNew();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        bottomPanel.add(guardarFichaButton);
        // Agregar el panel inferior a la parte inferior de la pantalla principal
        gamePanel.add(bottomPanel, BorderLayout.SOUTH);
    }
    private void refresh() {
        cellMatrix = gomoku.board();
        Component[] components = boardPanel.getComponents();
        for (int i = 0; i < cellMatrix.length; i++) {
            for (int j = 0; j < cellMatrix[0].length; j++) {
                if(cellMatrix[i][j].getStone() != null) {
                    Piedra piedra = (Piedra) components[i * cellMatrix[0].length + j];
                    // Configurar el color de las joyas y el fondo según la matriz
                    if(cellMatrix[i][j].getStone().getColor().equals(Color.BLACK)){
                        piedra.setPiedraColor(colorJ1);
                        piedra.makeVisible();
                    }else{
                        piedra.setPiedraColor(colorJ2);
                        piedra.makeVisible();
                    }

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
        // Eliminar el componente boardPanel del gamePanel
        gamePanel.remove(boardPanel);

        // Liberar recursos relacionados con el tablero (piedras, etc.)
        cellMatrix = null;
        boardPanel = null;
        turn = true;
        // Crear un nuevo tablero y panel
        gomoku = new Gomoku(size, stoneLimit, timeLimit);
        prepareElementsBoard();
        refresh();

        // Agregar el nuevo boardPanel al gamePanel
        gamePanel.add(boardPanel, BorderLayout.CENTER);

        // Revalidar y repintar el gamePanel para asegurar la actualización en la interfaz gráfica
        gamePanel.revalidate();
        gamePanel.repaint();
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
            if(gomoku.play(row, col, new Stone(color))){
                refresh();
                if(turn){
                    JOptionPane.showMessageDialog(null, "GANAASTEEEEEEEE."+nombreJ2);
                    optionNew();
                    cardLayout.show(cardPanel, "initial");
                }else{
                    JOptionPane.showMessageDialog(null, "GANAASTEEEEEEEE."+nombreJ1);
                    optionNew();
                    cardLayout.show(cardPanel, "initial");
                }

            }
            refresh();
        } catch (Exception e) {
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
        Component[] leftPanelComponents = ((JPanel) gamePanel.getComponent(1)).getComponents(); // Obtener componentes del panel izquierdo
        Component[] rightPanelComponents = ((JPanel) gamePanel.getComponent(2)).getComponents(); // Obtener componentes del panel derecho

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

