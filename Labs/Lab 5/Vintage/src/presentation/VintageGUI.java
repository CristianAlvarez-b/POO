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
    private Vintage vintage;
    private Color[] coloresPersonalizados = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.ORANGE, new Color(128, 0, 128), Color.WHITE, Color.CYAN};
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
        prepareElementsMenu();
        //prepareConfigScreen();
    }

    private void prepareScreens(){
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        setTitle("Vintage");

        // Crear pantalla inicial con dos botones
        JPanel initialPanel = createInitialPanel();
        cardPanel.add(initialPanel, "initial");
        JPanel continuePanel = createContinuePanel();
        cardPanel.add(continuePanel, "continue");
        // Crear pantalla principal con el juego
        JPanel configurePanel = configuracionesIniciales();
        cardPanel.add(configurePanel, "initConfig");
        JPanel configureSpecificPanel = configuracionesPersonalizadas();
        cardPanel.add(configureSpecificPanel, "personConfig");
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

        button1.addActionListener(e -> cardLayout.show(cardPanel, "initConfig"));
        button2.addActionListener(e -> cardLayout.show(cardPanel, "continue"));
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
    private JPanel createContinuePanel(){
        JPanel continuePanel = new JPanel(new BorderLayout());

        // Crear un panel para los botones con un GridLayout centrado
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));

        JButton button1 = new JButton("Slot 1");
        JButton button2 = new JButton("Slot 2");
        JButton button3 = new JButton("Back");

        button1.addActionListener(e -> abrirArchivo());
        button2.addActionListener(e -> abrirArchivo());
        button3.addActionListener(e -> cardLayout.show(cardPanel, "initial"));

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        continuePanel.add(buttonPanel, BorderLayout.CENTER);
        return continuePanel;
    }
    private JPanel configuracionesIniciales(){
        JPanel configuracionesPanel = new JPanel(new BorderLayout());

        // Crear un panel para los botones con un GridLayout centrado
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));

        JButton defaultButton = new JButton("Default");
        JButton personalizadaButton = new JButton("Personalizada");

        // Asociar ActionListener a los botones según sea necesario
        defaultButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                configuracionesDefalut();
            }
        });
        personalizadaButton.addActionListener(e -> cardLayout.show(cardPanel, "personConfig"));

        buttonPanel.add(defaultButton);
        buttonPanel.add(personalizadaButton);

        // Agregar el panel de botones al centro de la pantalla de configuraciones
        configuracionesPanel.add(buttonPanel, BorderLayout.CENTER);

        return configuracionesPanel;
    }
    private void configuracionesDefalut(){
        cardLayout.show(cardPanel, "game");
    }
    private JPanel configuracionesPersonalizadas() {
        JPanel configuracionesPanel = new JPanel(new GridLayout(1, 3));

        // Panel para el JColorChooser de la joya 1
        JPanel colorChooserPanel1 = crearColorChooserPanel("Color Joya 1:");

        // Panel para el JColorChooser de la joya 2
        JPanel colorChooserPanel2 = crearColorChooserPanel("Color Joya 2:");

        JPanel colorChooserPanel3 = crearColorChooserPanel("Color Joya 3:");

        JPanel colorChooserPanel4 = crearColorChooserPanel("Color Joya 4:");

        JPanel colorChooserPanel5 = crearColorChooserPanel("Color Joya 5:");

        JPanel colorChooserPanel6 = crearColorChooserPanel("Color Joya 6:");

        JPanel colorChooserPanel7 = crearColorChooserPanel("Color Joya 7:");

        // Continuar con paneles para las joyas 3 a 7...

        // Panel para el JTextField doble y textos explicativos
        JPanel textFieldPanel = new JPanel(new BorderLayout());
        JLabel filasLabel = new JLabel("Filas del tablero:");
        JLabel columnasLabel = new JLabel("Columnas del tablero:");
        JTextField filasTextField = new JTextField();
        JTextField columnasTextField = new JTextField();
        textFieldPanel.add(filasLabel, BorderLayout.NORTH);
        textFieldPanel.add(filasTextField, BorderLayout.CENTER);
        textFieldPanel.add(columnasLabel, BorderLayout.WEST);
        textFieldPanel.add(columnasTextField, BorderLayout.EAST);

        // Panel de botones
        JPanel botonesPanel = new JPanel(new FlowLayout());
        JButton aplicarButton = new JButton("Aplicar");
        JButton cancelarButton = new JButton("Cancelar");
        aplicarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aplicarConfiguracion();
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrarVentana();
            }
        });
        botonesPanel.add(aplicarButton);
        botonesPanel.add(cancelarButton);

        configuracionesPanel.add(colorChooserPanel1);
        configuracionesPanel.add(colorChooserPanel2);
        configuracionesPanel.add(colorChooserPanel3);
        configuracionesPanel.add(colorChooserPanel4);
        configuracionesPanel.add(colorChooserPanel5);
        configuracionesPanel.add(colorChooserPanel6);
        configuracionesPanel.add(colorChooserPanel7);


        configuracionesPanel.add(textFieldPanel);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(configuracionesPanel, BorderLayout.CENTER);
        mainPanel.add(botonesPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel crearColorChooserPanel(String label) {
        JPanel colorChooserPanel = new JPanel(new BorderLayout());
        JLabel colorChooserLabel = new JLabel(label);
        JButton colorChooserButton = new JButton("Seleccionar Color");
        colorChooserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                elegirColor();
            }
        });
        colorChooserPanel.add(colorChooserLabel, BorderLayout.NORTH);
        colorChooserPanel.add(colorChooserButton, BorderLayout.CENTER);
        return colorChooserPanel;
    }

    private void elegirColor() {
        JColorChooser colorChooser = new JColorChooser();
        Color colorElegido = JColorChooser.showDialog(this, "Seleccionar Color", Color.BLACK);

        if (colorElegido != null) {
            // Agregar el color elegido al array coloresPersonalizados
            for (int i = 0; i < coloresPersonalizados.length; i++) {
                    coloresPersonalizados[i] = colorElegido;
            }
        }
    }

    private void aplicarConfiguracion() {
        // Implementa la lógica para aplicar la configuración personalizada
        JOptionPane.showMessageDialog(this, "Configuración personalizada aplicada");
        cardLayout.show(cardPanel, "game");
    }

    private void cerrarVentana() {
        // Cierra la ventana de configuraciones personalizadas
        dispose();
    }
    private JPanel createGamePanel() {
        vintage = new Vintage(8,8);
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
        addTopPanel();  // Agregar el panel superior con el título y las puntuaciones
        addMiddlePanel();  // Agregar el panel central con el tablero
        addBottomPanel();  // Agregar el panel inferior con los botones
        prepareElementsBoard();  // Preparar el tablero
        //addPlayerLabels();  // Agregar las etiquetas de los jugadores
        return mainPanel;
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
                jewel.setJewelColor(coloresPersonalizados[0]);
                break;
            case 'b':
                jewel.setJewelColor(coloresPersonalizados[1]);
                break;
            case 'a':
                jewel.setJewelColor(coloresPersonalizados[2]);
                break;
            case 'v':
                jewel.setJewelColor(coloresPersonalizados[3]);
                break;
            case 'o':
                jewel.setJewelColor(coloresPersonalizados[4]);
                break;
            case 'm':
                jewel.setJewelColor(coloresPersonalizados[5]);
                break;
            case 'l':
                jewel.setJewelColor(coloresPersonalizados[6]);
                break;
            case 't':
                jewel.setJewelColor(coloresPersonalizados[7]);
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
        actualizarPuntuaciones();
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
        JPanel topPanel = new JPanel(new BorderLayout());

        // Agregar el título
        JLabel titleLabel = new JLabel("Vintage Jewelry", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        // Agregar las puntuaciones debajo del título
        JPanel scorePanel = new JPanel(new GridLayout(1, 2));
        int puntuacion_J1 = vintage.getJewels()[0];
        int puntuacion_J2 = vintage.getJewels()[1];
        player1Label = new JLabel("Joyas J1: " + puntuacion_J1);
        player2Label = new JLabel("Joyas J2: " + puntuacion_J2);
        String turnos;
        if(turn){
            turnos = "J1";
            turn = false;
        }else{
            turnos = "J2";
            turn = true;
        }
        turno = new JLabel("turno de: " + turnos);
        scorePanel.add(turno);
        scorePanel.add(player1Label);
        scorePanel.add(player2Label);
        topPanel.add(scorePanel, BorderLayout.CENTER);

        // Agregar el panel superior a la parte superior de la pantalla principal
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

        // Agregar los botones "Finalizar" y "Resetear"
        JButton finalizarButton = new JButton("Finalizar");
        JButton resetearButton = new JButton("Resetear");

        // Agregar ActionListener a los botones según sea necesario
        finalizarButton.addActionListener(e -> finalizarAccion());
        resetearButton.addActionListener(e -> resetearAccion());

        bottomPanel.add(finalizarButton);
        bottomPanel.add(resetearButton);

        // Agregar el panel inferior a la parte inferior de la pantalla principal
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
    private void actualizarPuntuaciones() {
        int puntuacion_J1 = vintage.getJewels()[0];
        int puntuacion_J2 = vintage.getJewels()[1];

        player1Label.setText("Joyas J1: " + puntuacion_J1);
        player2Label.setText("Joyas J2: " + puntuacion_J2);
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
    private void finalizarAccion() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres finaliar el juego?", "Confirmar finalizar",
                JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            cardLayout.show(cardPanel, "initial");
        }
    }


    private void resetearAccion() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres resetear?", "Confirmar cierre",
                JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            vintage = new Vintage(8, 8);
            refresh();
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
