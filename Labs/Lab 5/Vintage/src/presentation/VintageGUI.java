package presentation;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.io.File;
import domain.Vintage;
import domain.VintageException;

/**
 * The VintageGUI class represents the graphical user interface for the Vintage game.
 * It extends the JFrame class and provides a user-friendly interface for playing the game.
 */
public class VintageGUI extends JFrame{

    private JPanel mainPanel;
    private JPanel boardPanel;
    private JLabel player1Label;
    private JLabel player2Label;
    private JLabel turno;
    private boolean turn;
    private char[][][] boardMatrix;
    private Vintage vintage;
    private  Color[] coloresPersonalizados = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.ORANGE, new Color(147, 15, 194, 255), Color.cyan, new Color(222, 184, 135, 80),Color.BLACK};
    private int row = 8;
    private int column = 8;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private JComboBox<String> dimensionComboBox;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    /**
     * Constructs a VintageGUI object, initializing the elements and actions for the graphical interface.
     *
     * @throws VintageException If an exception specific to the Vintage game occurs.
     */
    private VintageGUI() throws VintageException {
        prepareElements();
        prepareActions();
    }
    /**
     * Prepares the elements of the graphical interface.
     *
     * @throws VintageException If an exception specific to the Vintage game occurs.
     */
    private void prepareElements() throws VintageException {

        prepareScreens();
        prepareElementsMenu();
    }
    /**
     * Prepares the screens for the graphical interface.
     *
     * @throws VintageException If an exception specific to the Vintage game occurs.
     */
    private void prepareScreens() throws VintageException {
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
        JPanel winnerPanel = createWinnerPanel();
        cardPanel.add(winnerPanel, "gameOver");

        // Configurar el contenido principal
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }
    /**
     * Crea y retorna un panel inicial que muestra una imagen de bienvenida y botones para iniciar, continuar o salir del juego.
     *
     * @return JPanel con la interfaz gráfica inicial.
     */
    private JPanel createInitialPanel() {
        setTitle("Vintage");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);

        JPanel initialPanel = new JPanel(new BorderLayout());

        // Crear un panel para la imagen con un BorderLayout
        JPanel imagePanel = new JPanel(new BorderLayout());

        ImageIcon imagen = new ImageIcon("ImagesGUI/Vintage.jpg");
        JLabel imageLabel = new JLabel("Bienvenido a Vintage Jewelry", imagen, JLabel.CENTER);
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // Agregar el panel de imagen al centro del panel principal
        initialPanel.add(imagePanel, BorderLayout.CENTER);

        // Crear un panel para los botones con un BorderLayout
        JPanel buttonPanel = new JPanel(new BorderLayout());

        // Cargar las imágenes para los botones
        ImageIcon startIcon = new ImageIcon("ImagesGUI/NG.png");
        ImageIcon continueIcon = new ImageIcon("ImagesGUI/continue.png");
        ImageIcon exitIcon = new ImageIcon("ImagesGUI/exit.jpg");
        ImageIcon resizedStartIcon = new ImageIcon(startIcon.getImage().getScaledInstance(300, 75, Image.SCALE_SMOOTH));
        ImageIcon resizedContinueIcon = new ImageIcon(continueIcon.getImage().getScaledInstance(300, 75, Image.SCALE_SMOOTH));
        ImageIcon resizedExitIcon = new ImageIcon(exitIcon.getImage().getScaledInstance(300, 75, Image.SCALE_SMOOTH));

        JButton button1 = new JButton(resizedStartIcon);
        button1.setBackground(Color.BLACK);
        JButton button2 = new JButton(resizedContinueIcon);
        button2.setBackground(Color.BLACK);
        JButton button3 = new JButton(resizedExitIcon);
        button3.setBackground(Color.RED);

        // Establecer el texto debajo de los iconos
        button1.setVerticalTextPosition(SwingConstants.BOTTOM);
        button1.setHorizontalTextPosition(SwingConstants.CENTER);

        button2.setVerticalTextPosition(SwingConstants.BOTTOM);
        button2.setHorizontalTextPosition(SwingConstants.CENTER);

        button3.setVerticalTextPosition(SwingConstants.BOTTOM);
        button3.setHorizontalTextPosition(SwingConstants.CENTER);

        button1.addActionListener(e -> cardLayout.show(cardPanel, "initConfig"));
        button2.addActionListener(e -> cardLayout.show(cardPanel, "continue"));
        button3.addActionListener(e -> confirmarCierre());

        // Agregar los botones al panel de botones
        buttonPanel.add(button1, BorderLayout.WEST);
        buttonPanel.add(button2, BorderLayout.CENTER);
        buttonPanel.add(button3, BorderLayout.EAST);

        // Agregar el panel de botones al sur del panel principal
        initialPanel.add(buttonPanel, BorderLayout.SOUTH);

        return initialPanel;
    }
    /**
     * Crea y retorna un panel de continuación que permite al usuario cargar partidas guardadas o volver al panel inicial.
     *
     * @return JPanel con la interfaz gráfica de continuación.
     */
    private JPanel createContinuePanel(){
        JPanel continuePanel = new JPanel(new BorderLayout());

        // Crear un panel para los botones con un GridLayout centrado
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));

        ImageIcon startIcon = new ImageIcon("ImagesGUI/save.png");
        ImageIcon continueIcon = new ImageIcon("ImagesGUI/save.png");
        ImageIcon backIcon = new ImageIcon("ImagesGUI/back.png");
        ImageIcon resizedStartIcon = new ImageIcon(startIcon.getImage().getScaledInstance(700, 200, Image.SCALE_SMOOTH));
        ImageIcon resizedContinueIcon = new ImageIcon(continueIcon.getImage().getScaledInstance(700, 150, Image.SCALE_SMOOTH));
        ImageIcon resizedExitIcon = new ImageIcon(backIcon.getImage().getScaledInstance(935, 170, Image.SCALE_SMOOTH));

        JButton button1 = new JButton("Slot 1", resizedStartIcon);
        JButton button2 = new JButton("Slot 2", resizedContinueIcon);
        JButton button3 = new JButton( resizedExitIcon);


        button1.addActionListener(e -> abrirArchivo());
        button2.addActionListener(e -> abrirArchivo());
        button3.addActionListener(e -> cardLayout.show(cardPanel, "initial"));

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        continuePanel.add(buttonPanel, BorderLayout.CENTER);
        return continuePanel;
    }
    /**
     * Crea y retorna un panel de configuraciones iniciales con opciones predeterminadas o personalizadas.
     *
     * @return JPanel con la interfaz gráfica de configuraciones iniciales.
     */
    private JPanel configuracionesIniciales(){
        JPanel configuracionesPanel = new JPanel(new BorderLayout());

        // Crear un panel para los botones con un GridLayout centrado
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));

        ImageIcon defaultIcon = new ImageIcon("ImagesGUI/default.png");
        ImageIcon personalizadoIcon = new ImageIcon("ImagesGUI/custom.png");
        ImageIcon backIcon = new ImageIcon("ImagesGUI/back.png");
        ImageIcon resizedStartIcon = new ImageIcon(defaultIcon.getImage().getScaledInstance(935, 200, Image.SCALE_SMOOTH));
        ImageIcon resizedContinueIcon = new ImageIcon(personalizadoIcon.getImage().getScaledInstance(935, 150, Image.SCALE_SMOOTH));
        ImageIcon resizedExitIcon = new ImageIcon(backIcon.getImage().getScaledInstance(935, 170, Image.SCALE_SMOOTH));

        JButton defaultButton = new JButton(resizedStartIcon);
        defaultButton.setBackground(Color.GRAY);
        JButton personalizadaButton = new JButton(resizedContinueIcon);
        personalizadaButton.setBackground(Color.white);
        JButton backButton = new JButton(resizedExitIcon);
        backButton.setBackground(Color.white);

        // Asociar ActionListener a los botones según sea necesario
        defaultButton.addActionListener(e -> {
            try {
                configuracionesDefalut();
            } catch (VintageException ex) {
                throw new RuntimeException(ex);
            }
        });
        personalizadaButton.addActionListener(e -> cardLayout.show(cardPanel, "personConfig"));
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "initial"));
        buttonPanel.add(defaultButton);
        buttonPanel.add(personalizadaButton);
        buttonPanel.add(backButton);

        // Agregar el panel de botones al centro de la pantalla de configuraciones
        configuracionesPanel.add(buttonPanel, BorderLayout.CENTER);

        return configuracionesPanel;
    }
    /**
     * Muestra un cuadro de diálogo para confirmar la configuración predeterminada y procede con el juego si es aceptado.
     *
     * @throws VintageException Si hay un problema con la configuración inicial.
     */
    private void configuracionesDefalut() throws VintageException {
        int opcion = JOptionPane.showConfirmDialog(this, "La configuración inicial tiene un tamaño de tablero de 8x8 y colores de gemas predefinidos, ¿quieres continuar?", "Confirmar finalizar",
                JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.NO_OPTION) {
            cardLayout.show(cardPanel, "initial");
        }else{
            resetColoresDefault();
            prepareElementsBoard();
            cardLayout.show(cardPanel, "game");
        }
    }
    /**
     * Crea y retorna un panel de configuraciones personalizadas que permite al usuario ajustar colores y tipos de gemas.
     *
     * @return JPanel con la interfaz gráfica de configuraciones personalizadas.
     */
    private JPanel configuracionesPersonalizadas() {
        JPanel configuracionesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel para el JColorChooser de las joyas
        JPanel colorChooserPanel = new JPanel(new GridLayout(3, 4, 10, 10)); // Añadí espacio entre las celdas (10 píxeles)
        for (int i = 0; i < 7; i++) {
            JPanel jewelColorPanel = crearColorChooserPanel("Color Joya " + (i + 1) + ":", i);
            colorChooserPanel.add(jewelColorPanel);
        }

        JPanel boardColorPanel = crearColorChooserPanel("Color principal del Tablero"  + ":", 7);
        colorChooserPanel.add(boardColorPanel);
        JPanel boardColorPanel2 = crearColorChooserPanel("Color secundario del Tablero"  + ":", 8);
        colorChooserPanel.add(boardColorPanel2);


        // Añadí más espacio entre las celdas para que ocupen más pantalla
        colorChooserPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel para mostrar el color seleccionado
        JPanel selectedColorPanel = new JPanel(new FlowLayout());

        // Panel para elegir filas y columnas
        JPanel dimensionsPanel = new JPanel(new FlowLayout());
        JLabel dimensionLabel = new JLabel("Dimensiones del tablero:");
        String[] dimensionOptions = {"6x6" ,"7x7", "8x8", "9x9", "10x10", "11x11"};
        dimensionComboBox = new JComboBox<>(dimensionOptions);
        dimensionsPanel.add(dimensionLabel);
        dimensionsPanel.add(dimensionComboBox);

        // Panel de botones
        JPanel botonesPanel = new JPanel(new FlowLayout());
        JButton aplicarButton = new JButton("Aplicar");
        JButton cancelarButton = new JButton("Cancelar");
        aplicarButton.addActionListener(e -> {
            try {
                aplicarConfiguracion();
            } catch (VintageException ex) {
                throw new RuntimeException(ex);
            }
        });
        cancelarButton.addActionListener(e -> cancelar());
        botonesPanel.add(aplicarButton);
        botonesPanel.add(cancelarButton);

        // Configuración para el colorChooserPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        configuracionesPanel.add(colorChooserPanel, gbc);

        // Configuración para el selectedColorPanel
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        configuracionesPanel.add(selectedColorPanel, gbc);

        // Configuración para el dimensionsPanel
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        configuracionesPanel.add(dimensionsPanel, gbc);

        // Configuración para el botonesPanel
        gbc.gridy = 1;
        configuracionesPanel.add(botonesPanel, gbc);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(configuracionesPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    /**
     * Crea y retorna un panel que permite al usuario seleccionar un color para una joya específica.
     *
     * @param label Etiqueta que describe la acción del panel.
     * @param joya Identificador de la joya para la cual se seleccionará el color.
     * @return JPanel con un botón para seleccionar el color.
     */
    private JPanel crearColorChooserPanel(String label, int joya) {
        JPanel colorChooserPanel = new JPanel(new BorderLayout());
        JLabel colorChooserLabel = new JLabel(label);
        JButton colorChooserButton = new JButton("Seleccionar Color");
        colorChooserButton.addActionListener(e -> {
            elegirColor(joya);
            colorChooserButton.setBackground(coloresPersonalizados[joya]);
        });

        colorChooserPanel.add(colorChooserLabel, BorderLayout.NORTH);
        colorChooserPanel.add(colorChooserButton, BorderLayout.CENTER);
        return colorChooserPanel;
    }
    /**
     * Abre un selector de color y guarda el color seleccionado en el arreglo de colores personalizados.
     *
     * @param joya Identificador de la joya para la cual se seleccionará el color.
     */
    private void elegirColor(int joya) {
        new JColorChooser();
        Color colorElegido = JColorChooser.showDialog(this, "Seleccionar Color", Color.BLACK);
        if (colorElegido != null) {
            if (joya == 7 || joya == 8) {
                // Establecer la opacidad al 80%
                int alpha = 80;
                coloresPersonalizados[joya] = new Color(colorElegido.getRed(), colorElegido.getGreen(), colorElegido.getBlue(), alpha);
            } else {
                coloresPersonalizados[joya] = colorElegido;
            }
        }
    }
    /**
     * Crea y retorna un panel que muestra un mensaje de felicitaciones al ganador, una imagen de trofeo y un botón para volver al menú principal.
     *
     * @return JPanel con la interfaz gráfica del panel de ganador.
     */
    private JPanel createWinnerPanel() {
        String ganador;
        if(vintage.getJewels()[0] > vintage.getJewels()[1]){
            ganador = "Jugador 1";
        }else{
            ganador = "Jugador 2";
        }
        JPanel winnerPanel = new JPanel();
        winnerPanel.setLayout(new BorderLayout());

        JLabel mensajeLabel = new JLabel("<html><center><h1>¡Felicidades, " + ganador + "!</h1><p>Eres el ganador.</p></center></html>");
        mensajeLabel.setHorizontalAlignment(JLabel.CENTER);

        ImageIcon trophyIcon = new ImageIcon("ImagesGUI/trophy.jpg"); // Reemplaza "trophy.png" con la ruta de tu imagen de trofeo
        JLabel trophyLabel = new JLabel(trophyIcon);
        trophyLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton volverButton = new JButton("Volver al Menú Principal");
        volverButton.addActionListener(e -> cardLayout.show(cardPanel, "initial"));
        winnerPanel.add(mensajeLabel, BorderLayout.NORTH);
        winnerPanel.add(trophyLabel, BorderLayout.CENTER);
        winnerPanel.add(volverButton, BorderLayout.SOUTH);

        return winnerPanel;
    }
    /**
     * Aplica la configuración personalizada seleccionada por el usuario y muestra un mensaje de confirmación.
     *
     * @throws VintageException Si hay un problema con la configuración personalizada.
     */
    private void aplicarConfiguracion() throws VintageException {
        // Implementa la lógica para aplicar la configuración personalizada
        JOptionPane.showMessageDialog(this, "Configuración personalizada aplicada");

        // Obtener el valor seleccionado del desplegable
        String selectedDimension = (String) dimensionComboBox.getSelectedItem();

        // Dividir la cadena para obtener filas y columnas
        assert selectedDimension != null;
        String[] dimensions = selectedDimension.split("x");
        int selectedRows = Integer.parseInt(dimensions[0]);
        int selectedColumns = Integer.parseInt(dimensions[1]);

        // Asignar los valores a los atributos
        this.row = selectedRows;
        this.column = selectedColumns;

        // Actualizar el tablero
        prepareElementsBoard();
        cardLayout.show(cardPanel, "game");
    }
    /**
     * Muestra un cuadro de diálogo para confirmar la cancelación y vuelve al menú principal si es aceptado.
     */
    private void cancelar() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres cancelar?", "Confirmar cancelacion",
                JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            cardLayout.show(cardPanel, "initial");
        }
    }
    /**
     * Crea y retorna un panel de juego principal con elementos como el título, el tablero y los botones.
     *
     * @return JPanel con la interfaz gráfica del panel de juego.
     * @throws VintageException Si hay un problema al preparar el tablero.
     */
    private JPanel createGamePanel() throws VintageException {

        // Crear el panel principal con BorderLayout
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // Agregar elementos a la pantalla principal
        addTopPanel();  // Agregar el panel superior con el título y las puntuaciones
        addMiddlePanel();  // Agregar el panel central con el tablero
        addBottomPanel();  // Agregar el panel inferior con los botones
        prepareElementsBoard();
        return mainPanel;
    }
    /**
     * Prepara las acciones para el cierre de la ventana y el menú.
     */
    private void prepareActions(){
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev){
                confirmarCierre();
            }
        });
        prepareActionsMenu();
    }
    /**
     * Prepara y muestra la barra de menú en la ventana principal con opciones como Nuevo, Abrir, Salvar y Salir.
     */
    private void prepareElementsMenu() {
        // Crear la barra de menú
        JMenuBar menuBar = new JMenuBar();

        // Crear el menú Archivo
        JMenu menu = new JMenu("Menu");

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
    /**
     * Asocia acciones a los elementos del menú, como Nuevo, Abrir, Salvar y Salir.
     */
    private void prepareActionsMenu() {
        ActionListener salirListener = e -> confirmarCierre();
        JMenuItem salirMenuItem = getJMenuBar().getMenu(0).getItem(5);
        salirMenuItem.addActionListener(salirListener);

        // Asociar oyentes para las opciones Nuevo, Abrir y Salvar
        getJMenuBar().getMenu(0).getItem(0).addActionListener(e -> {
            // Acción Nuevo
            cardLayout.show(cardPanel, "initConfig");
        });

        getJMenuBar().getMenu(0).getItem(2).addActionListener(e -> {
            // Acción Abrir
            abrirArchivo();
        });

        getJMenuBar().getMenu(0).getItem(3).addActionListener(e -> {
            // Acción Salvar
            salvarArchivo();
        });
    }
    /**
     * Prepara el tablero de juego y lo muestra en la ventana principal.
     *
     * @throws VintageException Si hay un problema al preparar el tablero.
     */
    private void prepareElementsBoard() throws VintageException {

        if (boardPanel == null) {
            vintage = new Vintage(row,column);
            // Si el tablero aún no se ha creado, crearlo y agregarlo al mainPanel
            boardMatrix = vintage.getBoard();
            boardPanel = new JPanel(new GridLayout(boardMatrix.length, boardMatrix[0].length));
            Jewel[][] jewels = new Jewel[boardMatrix.length][boardMatrix[0].length];

            for (int i = 0; i < boardMatrix.length; i++) {
                for (int j = 0; j < boardMatrix[0].length; j++) {
                    Jewel jewel = new Jewel(0);
                    jewels[i][j] = jewel;
                    jewel.addMouseListener(new CellClickListener(i, j));
                    boardPanel.add(jewel);

                    // Configurar el color de las joyas y el fondo según la matriz
                    setColorFromChar(jewel, boardMatrix[i][j]);
                }
            }

            mainPanel.add(boardPanel, BorderLayout.CENTER);
        } else {
            // Si el tablero ya está creado, simplemente refresca su contenido
            vintage = null;
            boardPanel = null;
            prepareElements();
            refresh();
        }
    }
    /**
     * Establece el color de una joya y su fondo basándose en un carácter específico de la matriz de tablero.
     *
     * @param jewel     Joya a la que se le aplicará el color.
     * @param colorChar Carácter que representa el color en la matriz de tablero.
     */
    private void setColorFromChar(Jewel jewel, char[] colorChar) {
        switch (colorChar[0]) {
            case 'r':
                jewel.setJewelColor(coloresPersonalizados[0]);
                jewel.setShape(0);
                break;
            case 'b':
                jewel.setJewelColor(coloresPersonalizados[1]);
                jewel.setShape(1);
                break;
            case 'a':
                jewel.setJewelColor(coloresPersonalizados[2]);
                jewel.setShape(2);
                break;
            case 'v':
                jewel.setJewelColor(coloresPersonalizados[3]);
                jewel.setShape(0);
                break;
            case 'o':
                jewel.setJewelColor(coloresPersonalizados[4]);
                jewel.setShape(0);
                break;
            case 'm':
                jewel.setJewelColor(coloresPersonalizados[5]);
                jewel.setShape(1);
                break;
            case 'l':
                jewel.setJewelColor(coloresPersonalizados[6]);
                jewel.setShape(2);
                break;
            case 't':
                jewel.setJewelColor(coloresPersonalizados[7]);
                jewel.setShape(3);
                break;
        }
        switch (colorChar[1]){
            case 'c':
                jewel.setBackgroundColor(coloresPersonalizados[7]);
                break;
            case 'n':
                jewel.setBackgroundColor(coloresPersonalizados[8]);
                break;
        }
    }


    /**
     * Actualiza la apariencia del tablero de juego y muestra información actualizada como el turno y las puntuaciones.
     */
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
            turno.setText ("turno de: Jugador 1");
            this.turn = false;
        }else{
            turno.setText ("turno de: Jugador 2");
            this.turn = true;
        }
        actualizarPuntuaciones();
        prepareActionsMenu();
    }


    /**
     * Maneja el evento de clic en una celda del tablero.
     *
     * @param row Fila de la celda clicada.
     * @param col Columna de la celda clicada.
     */
    private void handleCellClick(int row, int col) {
        if (selectedRow == -1 && selectedCol == -1) {
            // No hay celda seleccionada, seleccionar la actual
            selectedRow = row;
            selectedCol = col;
            // Marcar la celda seleccionada
            boardPanel.getComponent(row * boardMatrix[0].length + col).setBackground(Color.YELLOW);
        } else {
            try {
                boolean gameOver = vintage.play(selectedRow, selectedCol, row, col);
                if (gameOver) {
                    cardLayout.show(cardPanel, "gameOver");
                }
                refresh();
            } catch (VintageException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            // Desmarcar la celda seleccionada después de realizar la acción
            boardPanel.getComponent(selectedRow * boardMatrix[0].length + selectedCol).setBackground(null);
            selectedRow = -1;
            selectedCol = -1;
        }
    }

    /**
     * Clase interna que escucha los clics en una celda del tablero.
     */
    private class CellClickListener extends MouseAdapter {
        private final int row;
        private final int col;

        public CellClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            handleCellClick(row, col);
        }
    }

    /**
     * Clase que representa una joya en el tablero de juego.
     */
    public static class Jewel extends JPanel {
        private Color jewelColor;
        private Color backgroundColor;
        private int shape; // 0: Círculo, 1: Triángulo, 2: Cuadrado, 3: Pentágono, 4: Hexágono, 5: Heptágono

        public Jewel(int shape) {
            this.jewelColor = Color.WHITE; // Color predeterminado
            this.backgroundColor = Color.WHITE; // Color predeterminado
            this.shape = shape;
        }

        public void setShape(int shape){
            this.shape = shape;
        }

        public void setJewelColor(Color jewelColor) {
            this.jewelColor = jewelColor;
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
            setBorder(new LineBorder(Color.BLACK, 1)); // Puedes ajustar el color y el grosor del borde según tus preferencias
            int[] xPoints, yPoints;

            switch (shape) {
                case 0: // Círculo
                    g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
                    break;
                case 1: // Triángulo
                    xPoints = new int[]{getWidth() / 2, 5, getWidth() - 5};
                    yPoints = new int[]{5, getHeight() - 5, getHeight() - 5};
                    g.fillPolygon(xPoints, yPoints, 3);
                    break;
                case 2: // Triángulo Invertido
                    xPoints = new int[]{getWidth() / 2, 5, getWidth() - 5};
                    yPoints = new int[]{getHeight() - 5, 5, 5};
                    g.fillPolygon(xPoints, yPoints, 3);
                    break;

                case 3: // Rombo
                    xPoints = new int[]{getWidth() / 2, getWidth() / 4, getWidth() / 2, 3 * getWidth() / 4, getWidth() / 2};
                    yPoints = new int[]{5, getHeight() / 2, getHeight() - 5, getHeight() / 2, 5};
                    g.fillPolygon(xPoints, yPoints, 5);
                    break;

            }
        }
    }
    /**
     * Agrega el panel superior que contiene el título, las puntuaciones y el turno al mainPanel.
     */
    private void addTopPanel() {
        // Crear un panel para la parte superior
        JPanel topPanel = new JPanel(new BorderLayout());

        // Agregar el título
        JLabel titleLabel = new JLabel("Vintage Jewelry", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        // Agregar las puntuaciones debajo del título
        JPanel scorePanel = new JPanel(new GridLayout(1, 2));
        int puntuacion_J1 = 0;
        int puntuacion_J2 = 0;
        player1Label = new JLabel("Joyas Jugador 1: " + puntuacion_J1);
        player2Label = new JLabel("Joyas Jugador 2: " + puntuacion_J2);
        String turnos;
        if(turn){
            turnos = "Jugador 1";
            turn = false;
        }else{
            turnos = "Jugador 2";
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
    /**
     * Agrega el panel central (tablero) al mainPanel.
     */
    private void addMiddlePanel() {
        // Crear un panel para la parte central (tablero)
        JPanel middlePanel = new JPanel(); // Aquí debes agregar tu lógica para el tablero

        // Agregar el panel a la parte central de la pantalla principal
        mainPanel.add(middlePanel, BorderLayout.CENTER);
    }
    /**
     * Agrega el panel inferior con botones "Finalizar" y "Resetear" al mainPanel.
     */
    private void addBottomPanel() {
        // Crear un panel para la parte inferior
        JPanel bottomPanel = new JPanel();

        // Agregar los botones "Finalizar" y "Resetear"
        JButton finalizarButton = new JButton("Finalizar");
        JButton resetearButton = new JButton("Resetear");

        // Agregar ActionListener a los botones según sea necesario
        finalizarButton.addActionListener(e -> finalizarAccion());
        resetearButton.addActionListener(e -> {
            try {
                resetearAccion();
            } catch (VintageException ex) {
                throw new RuntimeException(ex);
            }
        });

        bottomPanel.add(finalizarButton);
        bottomPanel.add(resetearButton);

        // Agregar el panel inferior a la parte inferior de la pantalla principal
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
    /**
     * Abre un cuadro de diálogo para que el usuario seleccione un archivo. Muestra un mensaje con el nombre del archivo seleccionado.
     */
    private void abrirArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == 0) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Funcionalidad Abrir en construcción. Archivo seleccionado: " + selectedFile.getName());
        }
    }
    /**
     * Actualiza las etiquetas de puntuación con las puntuaciones actuales de los jugadores.
     */
    private void actualizarPuntuaciones() {
        int puntuacion_J1 = vintage.getJewels()[0];
        int puntuacion_J2 = vintage.getJewels()[1];

        player1Label.setText("Joyas J1: " + puntuacion_J1);
        player2Label.setText("Joyas J2: " + puntuacion_J2);
    }
    /**
     * Abre un cuadro de diálogo para que el usuario seleccione la ubicación y el nombre de un archivo para salvar. Muestra un mensaje con el nombre del archivo seleccionado.
     */
    private void salvarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == 0) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Funcionalidad Salvar en construcción. Archivo seleccionado: " + selectedFile.getName());
        }
    }
    /**
     * Muestra un cuadro de diálogo de confirmación para cerrar la aplicación. Cierra la aplicación si el usuario elige "Sí".
     */
    private void confirmarCierre() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres salir?", "Confirmar cierre",
                JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            // Si el usuario elige "Sí", cerrar la aplicación
            System.exit(0);
            dispose();
        }
    }
    /**
     * Muestra un cuadro de diálogo de confirmación para finalizar el juego. Cambia al panel inicial si el usuario elige "Sí".
     */
    private void finalizarAccion() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres finaliar el juego?", "Confirmar finalizar",
                JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            cardLayout.show(cardPanel, "initial");
        }
    }

    /**
     * Muestra un cuadro de diálogo de confirmación para resetear el juego. Cambia al panel inicial si el usuario elige "Sí" y resetea el juego.
     * @throws VintageException Excepción específica de la aplicación Vintage.
     */
    private void resetearAccion() throws VintageException{
        int opcion = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres resetear?", "Confirmar cierre",
                JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            vintage = null;
            boardMatrix = null;
            turn = true;
            vintage = new Vintage(row, column);
            refresh();
        }
    }
    /**
     * Resetea los colores personalizados a los colores predeterminados.
     */
    private void resetColoresDefault(){
        this.coloresPersonalizados = new Color[]{
                Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.ORANGE,
                new Color(147, 15, 194, 255), Color.CYAN, new Color(222, 184, 135, 80), Color.BLACK
        };
    }
    /**
     * Método principal que inicia la aplicación VintageGUI.
     * @param args Argumentos de la línea de comandos.
     * @throws VintageException Excepción específica de la aplicación Vintage.
     */
    public static void main(String[] args) throws VintageException {
        VintageGUI gui=new VintageGUI();
        gui.setVisible(true);
    }
}
