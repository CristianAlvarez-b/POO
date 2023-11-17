package presentation;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.File;
import domain.Vintage;
import domain.VintageException;

public class VintageGUI extends JFrame{

    private JMenuBar menuBar;
    private JMenu menu;
    private JPanel mainPanel;
    private JPanel boardPanel;
    private JLabel player1Label;
    private JLabel player2Label;
    private JLabel turno;
    private Jewel[][] jewels;
    private boolean turn;
    private char[][][] boardMatrix;
    private char[] player1Jewels = {'r', 'b', 'a', 'v', 'o'}; // Ejemplo, reemplazar con las joyas reales del jugador 1
    private Vintage vintage;

    private int selectedRow = -1;
    private int selectedCol = -1;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private VintageGUI(){
        prepareElements();
        prepareActions();
    }
    private void prepareElements() {
        prepareScreens();
        //prepareConfigScreen();
    }

    private void prepareScreens(){
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        setTitle("Vintage");

        // Crear pantalla inicial con dos botones
        JPanel initialPanel = createInitialPanel();
        cardPanel.add(initialPanel, "initial");

        // Crear pantalla principal con el juego
        JPanel gamePanel = createGamePanel();
        cardPanel.add(gamePanel, "game");

        // Configurar el contenido principal
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private JPanel createInitialPanel() {
        JPanel initialPanel = new JPanel(new BorderLayout());

        // Crear un panel para los botones con un GridLayout centrado
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));

        JButton button1 = new JButton("Iniciar juego");
        JButton button2 = new JButton("Continuar juego");
        JButton button3 = new JButton("Salir");

        button1.addActionListener(e -> cardLayout.show(cardPanel, "game"));
        button2.addActionListener(e -> abrirArchivo());
        button3.addActionListener(e -> confirmarCierre());

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);

        // Crear un panel para la imagen con un BorderLayout
        JPanel imagePanel = new JPanel(new BorderLayout());

        ImageIcon imagen = new ImageIcon("C:\\Users\\Equipo\\OneDrive\\Escritorio\\POOB\\Labs\\Lab 5\\ImagesGUI\\Vintage.jpg");
        JLabel imageLabel = new JLabel("Bienvenido a Vintage Jewelry", imagen, JLabel.CENTER);
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // Agregar los paneles al panel principal
        initialPanel.add(imagePanel, BorderLayout.CENTER);
        initialPanel.add(buttonPanel, BorderLayout.SOUTH);

        return initialPanel;
    }
    private JPanel createGamePanel(){
        setTitle("Vintage");
        turn = true;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);

        // Crear el panel principal con BorderLayout
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // Agregar elementos a la pantalla principal
        addTopPanel();
        addMiddlePanel();
        addBottomPanel();
        prepareElementsBoard();
        prepareElementsMenu();
        addPlayerLabels();
        return mainPanel;
    }
    private void addPlayerLabels() {
        JPanel playerLabelsPanel = new JPanel(new GridLayout());
        String turnos = "J1";
        // Panel para "Joyas J1"
        player1Label = new JLabel("Joyas J1:");
        for (char jewel : player1Jewels) {
            player1Label.add(new Jewel());
        }
        playerLabelsPanel.add(player1Label);

        // Panel para "Joyas J2"
        player2Label = new JLabel("Joyas J2");
        playerLabelsPanel.add(player2Label);

        turno = new JLabel("turno de: " + turnos);
        playerLabelsPanel.add(turno);
        mainPanel.add(playerLabelsPanel, BorderLayout.SOUTH);
    }
    private void prepareActions(){
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev){
                confirmarCierre();
            }
        });
        prepareActionsMenu();
    }
    private void prepareActionsMenu() {
        ActionListener salirListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmarCierre();
            }
        };
        JMenuItem salirMenuItem = getJMenuBar().getMenu(0).getItem(5);
        salirMenuItem.addActionListener(salirListener);

        // Asociar oyentes para las opciones Nuevo, Abrir y Salvar
        getJMenuBar().getMenu(0).getItem(0).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Acción Nuevo
                JOptionPane.showMessageDialog(VintageGUI.this, "Funcionalidad Nuevo en construcción");
            }
        });

        getJMenuBar().getMenu(0).getItem(2).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Acción Abrir
                abrirArchivo();
            }
        });

        getJMenuBar().getMenu(0).getItem(3).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Acción Salvar
                salvarArchivo();
            }
        });
    }
    private void prepareElementsBoard() {
        vintage = new Vintage(8,8);
        boardMatrix = vintage.getBoard();
        boardPanel = new JPanel(new GridLayout(boardMatrix.length, boardMatrix[0].length));
        jewels = new Jewel[boardMatrix.length][boardMatrix[0].length];

        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[0].length; j++) {
                Jewel jewel = new Jewel();
                jewels[i][j] = jewel;
                jewel.addMouseListener(new CellClickListener(i, j));
                boardPanel.add(jewel);

                // Configurar el color de las joyas y el fondo según la matriz
                setColorFromChar(jewel, boardMatrix[i][j]);
            }
        }

        mainPanel.add(boardPanel, BorderLayout.CENTER);
    }

    private void setColorFromChar(Jewel jewel, char colorChar[]) {
        Color lightBrown = new Color(222, 184, 135);

        switch (colorChar[0]) {
            case 'r':
                jewel.setJewelColor(Color.RED);
                break;
            case 'b':
                jewel.setJewelColor(Color.BLUE);
                break;
            case 'a':
                jewel.setJewelColor(Color.YELLOW);
                break;
            case 'v':
                jewel.setJewelColor(Color.GREEN);
                break;
            case 'o':
                jewel.setJewelColor(Color.ORANGE);
                break;
            case 'm':
                jewel.setJewelColor(new Color(128, 0, 128)); // Color morado
                break;
            case 'l':
                jewel.setJewelColor(Color.WHITE);
                break;
            case 't':
                jewel.setJewelColor(Color.CYAN);
                break;
        }
        switch (colorChar[1]){
            case 'c':
                jewel.setBackgroundColor(lightBrown);
                break;
            case 'n':
                jewel.setBackgroundColor(Color.BLACK);
                break;
        }
    }


    private void refresh() {
        boardMatrix = vintage.getBoard(); // Actualizar el estado del tablero
        Component[] components = boardPanel.getComponents();

        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[0].length; j++) {
                Jewel jewel = (Jewel) components[i * boardMatrix[0].length + j];

                // Configurar el color de las joyas y el fondo según la matriz
                setColorFromChar(jewel, boardMatrix[i][j]);
            }
        }

        boardPanel.revalidate(); // Asegurar que el panel se redibuje correctamente
        boardPanel.repaint();    // Forzar la repintura del panel
        if(turn){
            turno.setText ("turno de: J1");
            this.turn = false;
        }else{
            turno.setText ("turno de: J2");
            this.turn = true;
        }
    }



    private void handleCellClick(int row, int col) {
        if (selectedRow == -1 && selectedCol == -1) {
            // No hay celda seleccionada, seleccionar la actual
            selectedRow = row;
            selectedCol = col;
            boardPanel.getComponent(row * boardMatrix[0].length + col).setBackground(Color.YELLOW);
            // Marcar la celda seleccionada
        } else {
            // Intercambiar las joyas entre las dos celdas
            //swapCells(selectedRow, selectedCol, row, col);
            try {
                boolean gameOver = vintage.play(selectedRow, selectedCol, row, col);
                if(gameOver){
                    //pantallaFinal
                }
                refresh();
            }
            catch (VintageException e){
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            selectedRow = -1;
            selectedCol = -1;
        }
    }


    private class CellClickListener extends MouseAdapter {
        private int row;
        private int col;

        public CellClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            handleCellClick(row, col);
        }
    }

    private class Jewel extends JPanel {
        private Color jewelColor;
        private Color backgroundColor;

        public Jewel() {
            this.jewelColor = Color.WHITE; // Color predeterminado
            this.backgroundColor = Color.WHITE; // Color predeterminado
        }

        public Color getJewelColor() {
            return jewelColor;
        }

        public void setJewelColor(Color jewelColor) {
            this.jewelColor = jewelColor;
        }

        public Color getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(Color backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(backgroundColor);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(jewelColor);
            g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
        }
    }
    private void addTopPanel() {
        // Crear un panel para la parte superior
        JPanel topPanel = new JPanel();
        JLabel titleLabel = new JLabel("Vintage Jewelry");
        topPanel.add(titleLabel);

        // Agregar el panel a la parte superior de la pantalla principal
        mainPanel.add(topPanel, BorderLayout.NORTH);
    }

    private void addMiddlePanel() {
        // Crear un panel para la parte central (tablero)
        JPanel middlePanel = new JPanel(); // Aquí debes agregar tu lógica para el tablero

        // Agregar el panel a la parte central de la pantalla principal
        mainPanel.add(middlePanel, BorderLayout.CENTER);
    }

    private void addBottomPanel() {
        // Crear un panel para la parte inferior
        JPanel bottomPanel = new JPanel();
        JLabel scoreLabel = new JLabel("Puntuación: Jugador 1");
        JLabel scoreLabel2 = new JLabel("Puntuación: Jugador 2");
        JLabel turno = new JLabel("turno de: ");
        bottomPanel.add(scoreLabel);
        bottomPanel.add(scoreLabel2);
        bottomPanel.add(turno);

        // Agregar el panel a la parte inferior de la pantalla principal
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void abrirArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Funcionalidad Abrir en construcción. Archivo seleccionado: " + selectedFile.getName());
        }
    }

    private void salvarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Funcionalidad Salvar en construcción. Archivo seleccionado: " + selectedFile.getName());
        }
    }
    private void confirmarCierre() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres salir?", "Confirmar cierre",
                JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            // Si el usuario elige "Sí", cerrar la aplicación
            System.exit(0);
            dispose();
            }
        }
    private void prepareElementsMenu() {
        // Crear la barra de menú
        menuBar = new JMenuBar();

        // Crear el menú Archivo
        menu = new JMenu("Menu");

        // Crear las opciones del menú
        JMenuItem nuevoMenuItem = new JMenuItem("Nuevo");

        JSeparator separator1 = new JSeparator();

        JMenuItem abrirMenuItem = new JMenuItem("Abrir");
        JMenuItem salvarMenuItem = new JMenuItem("Salvar");

        // Crear un separador entre las opciones
        JSeparator separator2 = new JSeparator();

        // Crear la opción de salir
        JMenuItem salirMenuItem = new JMenuItem("Salir");

        // Agregar las opciones al menú
        menu.add(nuevoMenuItem);
        menu.add(separator1);
        menu.add(abrirMenuItem);
        menu.add(salvarMenuItem);
        menu.add(separator2); // Separador
        menu.add(salirMenuItem);

        // Agregar el menú a la barra de menú
        menuBar.add(menu);

        // Establecer la barra de menú en la ventana
        setJMenuBar(menuBar);
    }

    public static void main(String args[]){
        VintageGUI gui=new VintageGUI();
        gui.setVisible(true);
    }
}
