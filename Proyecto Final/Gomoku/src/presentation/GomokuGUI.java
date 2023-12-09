package presentation;

import domain.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.Timer;

public class GomokuGUI extends JFrame {
    private JLabel numNormalJ1;
    private JLabel numPesadaJ1;
    private JLabel numTemporaryJ1;
    private JLabel numNormalJ2;
    private JLabel numPesadaJ2;
    private JLabel numTemporaryJ2;
    private JLabel name1;
    private JLabel name2;
    private JLabel punctuationJ1;
    private JLabel punctuationJ2;
    private JLabel tiempoLabel;
    private Gomoku gomoku;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel gamePanel;
    private JPanel boardPanel;
    private Cell[][] cellMatrix;
    private JPanel piedraJ1;
    private JPanel piedraJ2;
    private boolean turn = true;
    private Piedra piedra1;
    private Piedra piedra2;
    private int size = 15;
    private int stoneLimit = size * size;
    private int timeLimit = -1;
    private int porcentajeEspeciales = 50;
    public static Color colorJ1 = Color.BLACK;
    public static Color colorJ2 = Color.WHITE;
    private Stone selectedStoneJ1 = null;
    private Stone selectedStoneJ2 = null;
    private String nombreJ1;
    private String nombreJ2;
    private boolean canRefill;


    public GomokuGUI() throws Exception {
        prepareElements();
        prepareActions();
    }

    private void prepareElements() throws Exception {
        prepareScreens();
        setTitle("Gomoku");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Calcular el tamaño deseado de la ventana (ajustar según tus necesidades)
        int windowWidth = (int) (screenSize.width * 0.5);
        int windowHeight = (int) (screenSize.height * 0.5);

        // Establecer el tamaño de la ventana
        setSize(new Dimension(windowWidth, windowHeight));
        setResizable(false);
        setLocationRelativeTo(null);
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
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private JPanel createInitialPanel() {
        ImagePanel fondo = new ImagePanel("GomokuImages/inicio.png");
        JPanel initialPanel = new JPanel(new GridBagLayout());
        initialPanel.setOpaque(false);
        initialPanel.setBackground(Color.BLACK);

        JPanel titulo = new JPanel(new BorderLayout());
        titulo.setOpaque(false);
        JLabel titulo1 = new JLabel("", SwingConstants.CENTER);
        titulo1.setFont(new Font("Arial", Font.ITALIC, 400));
        titulo1.setPreferredSize(new Dimension(100, 100));
        titulo.add(titulo1, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20); // Espacio entre los botones

        JButton button1 = new JButton("NEW GAME");
        JButton button2 = new JButton("CONTINUE");
        JButton button3 = new JButton("RULES");

        // Configurar el tamaño de los botones
        Dimension buttonSize = new Dimension(200, 50);
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);

        // Añadir ActionListeners según sea necesario
        button1.addActionListener(e -> {
            cardLayout.show(cardPanel, "config");
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            // Calcular el tamaño deseado de la ventana (ajustar según tus necesidades)
            int windowWidth = (int) (screenSize.width * 0.3);
            int windowHeight = (int) (screenSize.height * 0.5);

            // Establecer el tamaño de la ventana
            setSize(new Dimension(windowWidth, windowHeight));
            setResizable(false);
            setLocationRelativeTo(null);
        });
        button2.addActionListener(e -> {
            optionOpen();
        });
        // Añadir los botones al panel con GridBagLayout
        buttonPanel.add(button1, gbc);

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

        fondo.add(initialPanel);
        return fondo;
    }


    private JPanel createConfiguraciones() {
        ImagePanel fondo = new ImagePanel("GomokuImages/configuraciones.jpg");
        JPanel configuraciones = new JPanel(new GridBagLayout());
        configuraciones.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0; // Inicia en la fila 0

        // Crear espacio vertical de 20 píxeles
        configuraciones.add(Box.createVerticalStrut(20), gbc);

        // Crear y centrar el panel J1
        JPanel panelJ1 = crearPanelJ1();
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        configuraciones.add(panelJ1, gbc);

        // Crear espacio vertical de 20 píxeles
        gbc.gridy++;
        configuraciones.add(Box.createVerticalStrut(20), gbc);

        // Crear y centrar el panel J2
        JPanel panelJ2 = crearPanelJ2();
        gbc.gridy++;
        configuraciones.add(panelJ2, gbc);

        // Crear espacio vertical de 20 píxeles
        gbc.gridy++;
        configuraciones.add(Box.createVerticalStrut(20), gbc);

        // Crear y centrar el panel Size
        JPanel panelSize = crearPanelSize();
        gbc.gridy++;
        configuraciones.add(panelSize, gbc);

        fondo.add(configuraciones);
        return fondo;
    }


    private JPanel crearPanelJ1() {
        HashMap<String, Color> coloresMap = new HashMap<>();
        coloresMap.put("NEGRO", Color.BLACK);
        coloresMap.put("GRIS", new Color(64, 64, 64));
        coloresMap.put("AZUL", new Color(0, 0, 128));
        coloresMap.put("VERDE", new Color(0, 100, 0));
        coloresMap.put("PURPURA", new Color(128, 0, 128));
        coloresMap.put("ROJO", Color.RED);

        JPanel jugador1 = new JPanel(new GridBagLayout());
        jugador1.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titulo = new JLabel("JUGADOR 1", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel nombreLabel = new JLabel("Ingresa tu nombre:");
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 15));
        JTextField nombre = new JTextField();
        nombre.setColumns(20);

        // Agregar DocumentListener al JTextField
        nombre.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarNombreJ1();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarNombreJ1();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarNombreJ1();
            }

            private void actualizarNombreJ1() {
                SwingUtilities.invokeLater(() -> {
                    nombreJ1 = nombre.getText().isEmpty() ? "Jugador 1" : nombre.getText();
                });
            }
        });

        JPanel selectColorFicha = new JPanel(new FlowLayout());
        selectColorFicha.setOpaque(false);
        JLabel colorLabel1 = new JLabel("Selecciona el color de ficha:");
        colorLabel1.setFont(new Font("Arial", Font.BOLD, 13));
        String[] coloresJ1 = {"NEGRO", "GRIS", "AZUL", "VERDE", "PURPURA", "ROJO"};
        JComboBox<String> ficha1 = new JComboBox<>(coloresJ1);

        selectColorFicha.add(colorLabel1);
        selectColorFicha.add(ficha1);

        ficha1.addActionListener(e -> {
            String colorSeleccionado = (String) ficha1.getSelectedItem();
            colorJ1 = coloresMap.get(colorSeleccionado);
        });

        // Configuración de GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        jugador1.add(titulo, gbc);

        gbc.gridy = 1;
        jugador1.add(nombreLabel, gbc);

        gbc.gridy = 2;
        jugador1.add(nombre, gbc);

        gbc.gridy = 3;
        jugador1.add(selectColorFicha, gbc);

        return jugador1;
    }

    private JPanel crearPanelJ2() {
        HashMap<String, Color> coloresMap = new HashMap<>();
        coloresMap.put("BLANCO", Color.WHITE);
        coloresMap.put("AMARILLO", Color.YELLOW);
        coloresMap.put("AZUL", new Color(135, 206, 235));
        coloresMap.put("VERDE", new Color(144, 238, 144));
        coloresMap.put("ROSA", new Color(128, 0, 128));
        coloresMap.put("ROJO", new Color(255, 192, 203));

        JPanel jugador2 = new JPanel(new GridBagLayout());
        jugador2.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titulo = new JLabel("JUGADOR 2", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel nombreLabel = new JLabel("Ingresa tu nombre:");
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 13));
        JTextField nombre = new JTextField();
        nombre.setColumns(20);

        nombre.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarNombreJ2();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarNombreJ2();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarNombreJ2();
            }

            private void actualizarNombreJ2() {
                SwingUtilities.invokeLater(() -> {
                    nombreJ2 = nombre.getText().isEmpty() ? "Jugador 2" : nombre.getText();
                });
            }
        });

        JPanel selectColorFicha = new JPanel(new FlowLayout());
        selectColorFicha.setOpaque(false);
        JLabel colorLabel1 = new JLabel("Selecciona el color de ficha:");
        colorLabel1.setFont(new Font("Arial", Font.BOLD, 15));
        String[] coloresJ2 = {"BLANCO", "AMARILLO", "AZUL", "VERDE", "ROSA", "ROJO"};
        JComboBox<String> ficha2 = new JComboBox<>(coloresJ2);

        JCheckBox jugadorCheckBox = new JCheckBox("Jugar contra maquina");
        jugadorCheckBox.setFont(new Font("Arial", Font.BOLD, 13));
        jugadorCheckBox.setSelected(false);

        JPanel maquinaPanel = new JPanel(new FlowLayout());
        maquinaPanel.setOpaque(false);
        JLabel maquinaLabel = new JLabel("Selecciona la máquina:");
        maquinaLabel.setFont(new Font("Arial", Font.BOLD, 13));
        String[] opcionesMaquina = {"Agresiva", "Miedosa", "Experta"};
        JComboBox<String> maquinaComboBox = new JComboBox<>(opcionesMaquina);

        // Configuración de JComboBox para la máquina
        maquinaComboBox.setEnabled(false);  // Inicialmente desactivado
        maquinaPanel.add(maquinaLabel);
        maquinaPanel.add(maquinaComboBox);

        jugadorCheckBox.addItemListener(e -> {
            maquinaComboBox.setEnabled(jugadorCheckBox.isSelected());
        });

        selectColorFicha.add(colorLabel1);
        selectColorFicha.add(ficha2);

        // Configuración de GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        jugador2.add(titulo, gbc);

        gbc.gridy = 1;
        jugador2.add(nombreLabel, gbc);

        gbc.gridy = 2;
        jugador2.add(nombre, gbc);

        gbc.gridy = 3;
        jugador2.add(selectColorFicha, gbc);

        gbc.gridy = 4;
        jugador2.add(jugadorCheckBox, gbc);

        gbc.gridy = 5;
        jugador2.add(maquinaPanel, gbc);

        return jugador2;
    }


    private JPanel crearPanelSize() {
        JPanel panelSize = new JPanel(new GridBagLayout());
        panelSize.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // Agregar relleno alrededor de los componentes
        Insets insets = new Insets(5, 5, 5, 5);

        JLabel sizeLabel = new JLabel("Tamaño del tablero:");
        sizeLabel.setFont(new Font("Arial", Font.BOLD, 15));
        JTextField sizeTextField = new JTextField();
        sizeTextField.setColumns(10);

        sizeTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSize();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSize();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSize();
            }

            private void updateSize() {
                SwingUtilities.invokeLater(() -> {
                    try {
                        size = Integer.parseInt(sizeTextField.getText());
                        System.out.println("Tamaño del tablero: " + size);
                        stoneLimit = size * size;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "No estás ingresando un número válido.");
                        Log.record(e);
                        sizeTextField.setText("");
                    }
                });
            }
        });

        JLabel especialesLabel = new JLabel("Porcentaje de especiales:");
        especialesLabel.setFont(new Font("Arial", Font.BOLD, 15));
        JTextField especialesTextField = new JTextField();
        especialesTextField.setColumns(10);
        especialesTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarEspeciales();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarEspeciales();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarEspeciales();
            }

            private void actualizarEspeciales() {
                SwingUtilities.invokeLater(() -> {
                    try {
                        porcentajeEspeciales = Integer.parseInt(especialesTextField.getText());
                        System.out.println("Porcentaje especiales: " + porcentajeEspeciales);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "No estás ingresando un número válido.");
                        Log.record(e);
                        especialesTextField.setText("");
                    }
                });
            }
        });

        JButton boton = new JButton("Iniciar Juego");

        boton.addActionListener(e -> {
            try {
                empezarJuego();
            } catch (Exception ex) {
                Log.record(ex);
                JOptionPane.showMessageDialog(null, "Algo elegiste mal");
            }
        });

        // Configuración de GridBagConstraints
        gbc.insets = insets;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;

        panelSize.add(sizeLabel, gbc);

        gbc.gridy = 1;
        panelSize.add(sizeTextField, gbc);

        gbc.gridy = 2;
        panelSize.add(especialesLabel, gbc);

        gbc.gridy = 3;
        panelSize.add(especialesTextField, gbc);

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        panelSize.add(boton, gbc);

        return panelSize;
    }



    private void empezarJuego() throws Exception {
        updateRemainingLabels();
        optionNew();
        cardLayout.show(cardPanel, "game");
    }
    private JPanel createGamePanel() throws Exception {
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBackground(new Color(255, 192, 203));
        addTopPanel();
        addLeftPanel();
        addRightPanel();
        addBottomPanel();
        prepareElementsBoard();
        prepareElementsMenu();
        updateRemainingLabels();
        gamePanel.add(Box.createHorizontalStrut(20));
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

        JMenuItem inicioMenuItem = new JMenuItem("Inicio");
        JMenuItem configuracionesMenuItem = new JMenuItem("Configuraciones");

        JSeparator separator3 = new JSeparator();

        JMenuItem salirMenuItem = new JMenuItem("Salir");

        menu.add(nuevoMenuItem);
        menu.add(separator1);
        menu.add(abrirMenuItem);
        menu.add(salvarMenuItem);
        menu.add(separator2); // Separador
        menu.add(inicioMenuItem);
        menu.add(configuracionesMenuItem);
        menu.add(separator3);
        menu.add(salirMenuItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }
    private void prepareElementsBoard() throws Exception {
        if (boardPanel == null) {
            gomoku = new Gomoku(size, stoneLimit, timeLimit, porcentajeEspeciales);
            // Si el tablero aún no se ha creado, crearlo y agregarlo al mainPanel
            cellMatrix = gomoku.board();
            boardPanel = new JPanel(new GridLayout(cellMatrix.length, cellMatrix[0].length));
            Piedra[][] piedras = new Piedra[cellMatrix.length][cellMatrix[0].length];

            for (int i = 0; i < cellMatrix.length; i++) {
                for (int j = 0; j < cellMatrix[0].length; j++) {
                    Piedra piedra = new Piedra(false);
                    piedras[i][j] = piedra;
                    piedra.setBackType(chooseColorOfBackgroundPiedra(cellMatrix[i][j]));
                    piedra.addMouseListener(new CellClickListener(i, j));
                    //piedra.setOpaque(false);
                    boardPanel.add(piedra);
                }
            }
            gamePanel.add(boardPanel, BorderLayout.CENTER);
        } else {

            refresh();
        }
    }
    private void addTopPanel() {
        // Crear un panel para la parte superior
        JPanel topPanel = new JPanel(new BorderLayout());


        // Agregar el tiempoLabel al centro
        tiempoLabel = new JLabel("EL TIEMPO TRANSCURRIDO ES: ", SwingConstants.CENTER);
        topPanel.add(tiempoLabel, BorderLayout.CENTER);

        // Agregar el topPanel al gamePanel
        gamePanel.add(topPanel, BorderLayout.NORTH);
    }
    private void addLeftPanel() {
        ImagePanel fondo = new ImagePanel("GomokuImages/playerPanels.png");

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        JPanel datosJ1 = new JPanel(new GridBagLayout());
        datosJ1.setOpaque(false);
        GridBagConstraints gbcDatosJ1 = new GridBagConstraints();
        gbcDatosJ1.insets = new Insets(5, 5, 5, 5); // Márgenes entre los componentes

        // Cargar la imagen
        ImageIcon imagen = new ImageIcon("GomokuImages/player1.png");
        ImageIcon resized = new ImageIcon(imagen.getImage().getScaledInstance(150, 75, Image.SCALE_SMOOTH));

        // Crear un JLabel con la imagen y establecer un borde
        JLabel imageLabel = new JLabel(resized, JLabel.CENTER);

        // Crear un JLabel para el nombre del jugador
        name1 = new JLabel(nombreJ1);
        // Crear un JLabel para el texto "Juegas con las fichas:"
        JLabel texto = new JLabel("Juegas con las fichas:");

        // Crear un JPanel para la piedra
        piedraJ1 = new JPanel(new BorderLayout());
        piedra1 = new Piedra(false);
        piedraJ1.setPreferredSize(new Dimension(150, 150));
        piedra1.setPiedraColor(colorJ1);
        piedra1.setType('n');
        piedra1.makeVisible();
        piedraJ1.add(texto, BorderLayout.NORTH);
        piedraJ1.add(piedra1, BorderLayout.CENTER);
        piedraJ1.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

        // Configurar GridBagConstraints para datosJ1
        gbcDatosJ1.gridx = 0;
        gbcDatosJ1.gridy = 0;
        datosJ1.add(name1, gbcDatosJ1);

        gbcDatosJ1.gridx = 0;
        gbcDatosJ1.gridy = 1;
        datosJ1.add(imageLabel, gbcDatosJ1);

        gbcDatosJ1.gridx = 0;
        gbcDatosJ1.gridy = 2;
        datosJ1.add(piedraJ1, gbcDatosJ1);

        JPanel piedrasJ1 = new JPanel(new GridBagLayout());
        piedrasJ1.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(17, 17, 17, 17); // Márgenes entre los componentes

        JLabel normalRemaining = new JLabel("Escoge la ficha que vas a utilizar: ");
        normalRemaining.setForeground(Color.WHITE);
        numNormalJ1 = new JLabel();
        numNormalJ1.setForeground(Color.WHITE);
        numPesadaJ1 = new JLabel();
        numPesadaJ1.setForeground(Color.WHITE);
        numTemporaryJ1 = new JLabel();
        numTemporaryJ1.setForeground(Color.WHITE);
        JButton normal = new JButton("NORMAL");
        JButton pesada = new JButton("HEAVY");
        JButton temporal = new JButton("TEMPORARY");
        normal.addActionListener(e -> {
            if(turn){
                // Acción para el botón NORMAL
                selectedStoneJ1 =  new Stone(colorJ1);
                piedra1.setType('n');
                piedra1.repaint();
            }
        });
        pesada.addActionListener(e -> {
            if(turn){
                // Acción para el botón HEAVY
                selectedStoneJ1 = new Heavy(colorJ1);
                piedra1.setType('h');
                piedra1.repaint();
            }
        });
        temporal.addActionListener(e -> {
            if(turn){
                // Acción para el botón TEMPORARY
                selectedStoneJ1 = new Temporary(colorJ1);
                piedra1.setType('t');
                piedra1.setLife(7);
                piedra1.repaint();
            }
        });

        Dimension buttonSize = new Dimension(120, 40); // Tamaño deseado para los botones
        normal.setPreferredSize(buttonSize);
        pesada.setPreferredSize(buttonSize);
        temporal.setPreferredSize(buttonSize);


        gbc.gridx = 0;
        gbc.gridy = 0;
        piedrasJ1.add(numNormalJ1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        piedrasJ1.add(numPesadaJ1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        piedrasJ1.add(numTemporaryJ1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        piedrasJ1.add(normalRemaining, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        piedrasJ1.add(normal, gbc);


        gbc.gridx = 0;
        gbc.gridy = 5;
        piedrasJ1.add(pesada, gbc);


        gbc.gridx = 0;
        gbc.gridy = 6;
        piedrasJ1.add(temporal, gbc);


        JPanel puntuacionTiempo = new JPanel(new BorderLayout());
        punctuationJ1 = new JLabel("Tu puntuación es: ");
        puntuacionTiempo.add(punctuationJ1, BorderLayout.NORTH);



        // Agregar el JLabel al JPanel
        leftPanel.add(datosJ1, BorderLayout.NORTH);
        leftPanel.add(piedrasJ1, BorderLayout.CENTER);
        leftPanel.add(puntuacionTiempo, BorderLayout.SOUTH);
        fondo.add(leftPanel);

        // Agregar el JPanel al contenedor principal (mainPanel)
        gamePanel.add(fondo, BorderLayout.WEST);
    }


    private void addRightPanel() {
        ImagePanel fondo = new ImagePanel("GomokuImages/playerPanels.png");
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);

        // Cargar la imagen
        JPanel datosJ2 = new JPanel(new GridBagLayout());
        datosJ2.setOpaque(false);
        GridBagConstraints gbcDatosJ2 = new GridBagConstraints();
        gbcDatosJ2.insets = new Insets(5, 5, 5, 5); // Márgenes entre los componentes

        // Cargar la imagen
        ImageIcon imagen = new ImageIcon("GomokuImages/player2.png");
        ImageIcon resized = new ImageIcon(imagen.getImage().getScaledInstance(150, 75, Image.SCALE_SMOOTH));

        // Crear un JLabel con la imagen y establecer un borde
        JLabel imageLabel = new JLabel(resized, JLabel.CENTER);

        // Crear un JLabel para el nombre del jugador
        name2 = new JLabel(nombreJ2);
        // Crear un JLabel para el texto "Juegas con las fichas:"
        JLabel texto = new JLabel("Juegas con las fichas:");

        // Crear un JPanel para la piedra
        piedraJ2 = new JPanel(new BorderLayout());
        piedra2 = new Piedra(false);
        piedraJ2.setPreferredSize(new Dimension(150, 150));
        piedra2.setPiedraColor(colorJ2);
        piedra2.setType('n');
        piedra2.makeVisible();
        piedraJ2.add(texto, BorderLayout.NORTH);
        piedraJ2.add(piedra2, BorderLayout.CENTER);

        // Configurar GridBagConstraints para datosJ1
        gbcDatosJ2.gridx = 0;
        gbcDatosJ2.gridy = 0;
        datosJ2.add(name2, gbcDatosJ2);

        gbcDatosJ2.gridx = 0;
        gbcDatosJ2.gridy = 1;
        datosJ2.add(imageLabel, gbcDatosJ2);

        gbcDatosJ2.gridx = 0;
        gbcDatosJ2.gridy = 2;
        datosJ2.add(piedraJ2, gbcDatosJ2);

        JPanel piedrasJ2 = new JPanel(new GridBagLayout());
        piedrasJ2.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Márgenes entre los componentes

        JLabel normalRemaining = new JLabel("Escoge la ficha que vas a utilizar: ");
        normalRemaining.setForeground(Color.WHITE);
        normalRemaining.setOpaque(false);
        numNormalJ2 = new JLabel();
        numNormalJ2.setForeground(Color.WHITE);
        numPesadaJ2 = new JLabel();
        numPesadaJ2.setForeground(Color.WHITE);
        numTemporaryJ2 = new JLabel();
        numTemporaryJ2.setForeground(Color.WHITE);
        JButton normal = new JButton("NORMAL");
        JButton pesada = new JButton("HEAVY");
        JButton temporal = new JButton("TEMPORARY");
        normal.addActionListener(e -> {
            if(!turn){
                // Acción para el botón NORMAL
                selectedStoneJ2 = new Stone(colorJ2);
                piedra2.setType('n');
                piedra2.repaint();
            }
        });
        pesada.addActionListener(e -> {
            if(!turn){
                // Acción para el botón HEAVY
                selectedStoneJ2 = new Heavy(colorJ2);
                piedra2.setType('h');
                piedra2.repaint();
            }
        });
        temporal.addActionListener(e -> {
            if(!turn){
                // Acción para el botón TEMPORARY
                selectedStoneJ2 = new Temporary(colorJ2);
                piedra2.setType('t');
                piedra2.setLife(7);
                piedra2.repaint();
            }
        });
        Dimension buttonSize = new Dimension(120, 40); // Tamaño deseado para los botones
        normal.setPreferredSize(buttonSize);
        pesada.setPreferredSize(buttonSize);
        temporal.setPreferredSize(buttonSize);


        gbc.gridx = 0;
        gbc.gridy = 0;
        piedrasJ2.add(numNormalJ2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        piedrasJ2.add(numPesadaJ2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        piedrasJ2.add(numTemporaryJ2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        piedrasJ2.add(normalRemaining, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        piedrasJ2.add(normal, gbc);


        gbc.gridx = 0;
        gbc.gridy = 5;
        piedrasJ2.add(pesada, gbc);


        gbc.gridx = 0;
        gbc.gridy = 6;
        piedrasJ2.add(temporal, gbc);


        JPanel puntuacionTiempo = new JPanel(new BorderLayout());
        punctuationJ2 = new JLabel("Tu puntuación es: ");
        puntuacionTiempo.add(punctuationJ2, BorderLayout.NORTH);

        // Agregar el JLabel al JPanel
        rightPanel.add(datosJ2, BorderLayout.NORTH);
        rightPanel.add(piedrasJ2, BorderLayout.CENTER);
        rightPanel.add(puntuacionTiempo, BorderLayout.SOUTH);
        // Agregar el JPanel al contenedor principal (mainPanel)
        fondo.add(rightPanel);
        gamePanel.add(fondo, BorderLayout.EAST);
    }
    private void addBottomPanel() {
        // Crear un panel para la parte inferior
        JPanel bottomPanel = new JPanel();
        // Agregar los botones "Finalizar" y "Resetear"
        JButton guardarFichaButton = new JButton("RESETEAR");

        // Agregar ActionListener a los botones según sea necesario
        guardarFichaButton.addActionListener(e -> {
            try {
                optionNew();
            } catch (Exception ex) {
                Log.record(ex);
                throw new RuntimeException(ex);
            }
        });
        bottomPanel.add(guardarFichaButton);
        // Agregar el panel inferior a la parte inferior de la pantalla principal
        gamePanel.add(bottomPanel, BorderLayout.SOUTH);
    }
    private void reset() {
        cellMatrix = gomoku.board();
        boardPanel.getComponents();
        updateBorders();
        updateRemainingLabels();
        boardPanel.revalidate(); // Asegurar que el panel se redibuje correctamente
        boardPanel.repaint();
    }
    private void refresh() {
        turn = gomoku.getBoard().getTurn();
        cellMatrix = gomoku.board();
        Component[] components = boardPanel.getComponents();
        for (int i = 0; i < cellMatrix.length; i++) {
            for (int j = 0; j < cellMatrix[0].length; j++) {
                Piedra piedra = (Piedra) components[i * cellMatrix[0].length + j];
                if(cellMatrix[i][j].getStone() != null) {
                    // Configurar el color de las joyas y el fondo según la matriz
                    if(cellMatrix[i][j].getStone().getColor().equals(Color.BLACK)){
                        piedra.setPiedraColor(colorJ1);
                    }else{
                        piedra.setPiedraColor(colorJ2);
                    }
                    piedra.setBackType(chooseColorOfBackgroundPiedra(cellMatrix[i][j]));
                    piedra.setType(chooseCharForAStone(cellMatrix[i][j].getStone()));
                    piedra.makeVisible();
                }else{
                    if(!cellMatrix[i][j].isActive()){
                        piedra.setBackType(chooseColorOfBackgroundPiedra(cellMatrix[i][j]));
                    }
                    piedra.makeInvisible();
                }
            }
        }
        //refreshStoneAppearance(piedra, cellMatrix[row][column].getStone());
        updateBorders();
        updateRemainingLabels();
        boardPanel.revalidate(); // Ensure that the panel is redrawn correctly
        boardPanel.repaint();
    }


    private void prepareActions() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        prepareActionsMenu();
    }
    private void prepareActionsMenu(){
        getJMenuBar().getMenu(0).getItem(0).addActionListener(e -> {
            try {
                optionNewInicio();
            } catch (Exception ex) {
                Log.record(ex);
                JOptionPane.showMessageDialog(null, "Error inesperado");
            }
        });
        getJMenuBar().getMenu(0).getItem(2).addActionListener(e -> {
            optionOpen();
        });
        getJMenuBar().getMenu(0).getItem(3).addActionListener(e -> {
            optionSave();
        });
        getJMenuBar().getMenu(0).getItem(5).addActionListener(e -> cardLayout.show(cardPanel, "initial"));
        getJMenuBar().getMenu(0).getItem(6).addActionListener(e -> {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            // Calcular el tamaño deseado de la ventana (ajustar según tus necesidades)
            int windowWidth = (int) (screenSize.width * 0.3);
            int windowHeight = (int) (screenSize.height * 0.5);

            // Establecer el tamaño de la ventana
            setSize(new Dimension(windowWidth, windowHeight));
            setResizable(false);
            setLocationRelativeTo(null);
            cardLayout.show(cardPanel, "config");
        });
        getJMenuBar().getMenu(0).getItem(8).addActionListener(e -> optionExit());
    }
    private void optionNewInicio(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Calcular el tamaño deseado de la ventana (ajustar según tus necesidades)
        int windowWidth = (int) (screenSize.width * 0.5);
        int windowHeight = (int) (screenSize.height * 0.5);

        // Establecer el tamaño de la ventana
        setSize(new Dimension(windowWidth, windowHeight));
        setResizable(false);
        setLocationRelativeTo(null);
        cardLayout.show(cardPanel, "initial");
    }
    private void optionNew() throws Exception {
        // Eliminar el componente boardPanel del gamePanel
        gamePanel.remove(boardPanel);

        // Liberar recursos relacionados con el tablero (piedras, etc.)
        cellMatrix = null;
        boardPanel = null;
        turn = true;
        // Crear un nuevo tablero y panel
        gomoku = new Gomoku(size, stoneLimit, timeLimit, porcentajeEspeciales);
        prepareElementsBoard();
        piedra1.setType('n');
        piedra2.setType('n');
        selectedStoneJ1 = null;
        piedra1.repaint();
        piedra2.repaint();
        gomoku.getPlayer1().setColor(colorJ1);
        gomoku.getPlayer2().setColor(colorJ2);
        gomoku.getPlayer1().setName(Objects.requireNonNullElse(nombreJ1, "Jugador 1"));
        gomoku.getPlayer2().setName(Objects.requireNonNullElse(nombreJ2, "Jugador 2"));
        reset();

        // Agregar el nuevo boardPanel al gamePanel
        gamePanel.add(boardPanel, BorderLayout.CENTER);

        // Revalidar y repintar el gamePanel para asegurar la actualización en la interfaz gráfica
        gamePanel.revalidate();
        gamePanel.repaint();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowWidth = (int) (screenSize.width * 0.7);
        int windowHeight = (int) (screenSize.height * 0.8);
        setSize(windowWidth, windowHeight);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    private void optionOpen(){
        try{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("Slots/"));
            int result = fileChooser.showOpenDialog(this);
            if (result == 0) {
                File selectedFile = fileChooser.getSelectedFile();
                Gomoku temporal = Gomoku.open(selectedFile);
                size = temporal.getSize();
                colorJ1 = temporal.getPlayer1().getColor();
                colorJ2 = temporal.getPlayer2().getColor();
                optionNew();
                this.gomoku = temporal;
                refresh();
                turn = gomoku.getTurn();
                cardLayout.show(cardPanel, "game");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
            Log.record(e);
        }
    }
    private void optionSave() {
        try {
            if (gomoku != null) {  // Asegurarse de que gomoku esté inicializado
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("Slots/"));
                int result = fileChooser.showSaveDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    gomoku.save(selectedFile);
                }
            } else {
                // Manejar el caso en el que gomoku es nulo
                JOptionPane.showMessageDialog(this, "La instancia de Gomoku no está inicializada.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            Log.record(e);
        }
    }
    private void optionExit(){
        System.exit(0);
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
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                Log.record(ex);
            }
        }
    }
    private void ponerFicha(int row, int col) throws Exception {
        Stone selectedStone;

        Player currentPlayer = turn ? gomoku.getPlayer1() : gomoku.getPlayer2();
        Player opponentPlayer = turn ? gomoku.getPlayer2() : gomoku.getPlayer1();
        Stone[] selectedStoneArray = new Stone[]{turn ? selectedStoneJ1 : selectedStoneJ2};
        try {
            if (!currentPlayer.getExtraStones().isEmpty()) {
                selectedStone = handleExtraStones(currentPlayer, selectedStoneArray);
                turn = !turn;
            } else {
                selectedStone = handleRemainingStones(currentPlayer, selectedStoneArray);
                turn = !turn;
            }
            if(gomoku.play(row, col, selectedStone)){
                if (!gomoku.getBoard().getTurn()) {
                    refresh();
                    JOptionPane.showMessageDialog(null, "GANAASTEEEEEEEE. " + gomoku.getPlayer1().getName());
                } else {
                    refresh();
                    JOptionPane.showMessageDialog(null, "GANAASTEEEEEEEE. " + gomoku.getPlayer2().getName());
                }
            }
            gomoku.getBoard().setTurn(turn);
            refresh();
            if (cellMatrix[row][col] instanceof Golden) {
                String message;
                if(currentPlayer.getExtraStones().get(0).getClass().getSimpleName().equals("Stone")){
                    message = "Obtuviste una ficha: Stone. Estas obligado a jugar 2 normales en tu siguiente turno.";
                }else{
                    message = "Obtuviste una ficha: " + currentPlayer.getExtraStones().get(0).getClass().getSimpleName();
                }
                JOptionPane.showMessageDialog(null, message);
            }

        } catch (GomokuException e) {
            handleException(e);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Log.record(ex);
        }
    }

    private Stone handleExtraStones(Player currentPlayer, Stone[] selectedStoneArray) throws Exception {
        Stone extraStone = currentPlayer.getExtraStones().get(0);
        currentPlayer.getRemainingStones().add(extraStone);
        if (extraStone.getClass() == Stone.class) {
            selectedStoneArray[0] = getFirstStoneOfType(currentPlayer.getExtraStones(), extraStone.getClass());
            currentPlayer.eliminateStone(currentPlayer.getExtraStones(), extraStone.getClass());
            turn = !turn;
            return selectedStoneArray[0];
        }
        if (selectedStoneArray[0] == null) {
            handleRemainingStones(currentPlayer, selectedStoneArray);
        }
        currentPlayer.eliminateStone(currentPlayer.getExtraStones(), extraStone.getClass());
        return selectedStoneArray[0];
    }

    private Stone handleRemainingStones(Player currentPlayer, Stone[] selectedStoneArray){
        if (selectedStoneArray[0] == null) {
            selectedStoneArray[0] = getFirstStoneOfType(currentPlayer.getRemainingStones(), Stone.class);
        }
        return selectedStoneArray[0];
    }

    private void handleException(GomokuException e) throws Exception {
        if (e.getMessage().equals(GomokuException.STONE_OVERLOAP)) {
            turn = !turn;
            JOptionPane.showMessageDialog(null, e.getMessage());
            Log.record(e);
        } else if (e.getMessage().equals(GomokuException.FULL_BOARD)) {
            refresh();
            JOptionPane.showMessageDialog(null, "Empate.");
        } else {
            throw new Exception(e.getMessage());
        }
    }

    public char chooseCharForAStone(Stone stone){
        if(stone instanceof Heavy){
            return 'h';
        }else if(stone instanceof Temporary){
            return 't';
        }else{
            return 'n';
        }
    }
    public char chooseColorOfBackgroundPiedra(Cell cell){
        if(cell instanceof Mine){
            return 'm';
        } else if (cell instanceof Teleport) {
            return 'p';
        }else if(cell instanceof Golden){
            return 'g';
        }else{
            return 'c';
        }
    }

    public static class Piedra extends JPanel {
        private Color piedraColor;
        private Color backgroundColor;
        private char type;
        private char backType;
        private int life = 6;
        private boolean isVisible;


        public Piedra(boolean isVisible) {
            this.piedraColor = Color.WHITE; // Color predeterminado
            this.backgroundColor = new Color(222, 184, 135, 80); // Color predeterminado
            this.isVisible = isVisible;
        }
        public void setType(char type){
            this.type = type;
        }
        public void setBackType(char backType){this.backType = backType;}
        public void setPiedraColor(Color piedraColor) {
            this.piedraColor = piedraColor;
        }

        public void setLife(int life) {
            this.life = life;
        }

        public void makeVisible() {
            this.isVisible = true;
        }

        public void makeInvisible() {
            this.isVisible = false;
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackgroundColor(g);
            if (!isVisible) {
                g.fillRect(0, 0, getWidth(), getHeight());
                setBorder(new LineBorder(new Color(139, 69, 19), 1)); // Puedes ajustar el color y el grosor del borde según tus preferencias
            } else {
                switch (type){
                    case 'n':
                        g.fillRect(0, 0, getWidth(), getHeight());
                        g.setColor(piedraColor);
                        setBorder(new LineBorder(new Color(139, 69, 19), 1)); // Puedes ajustar el color y el grosor del borde según tus preferencias
                        g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
                        break;
                    case 'h':
                        int width = getWidth();
                        int height = getHeight();

                        // Establecer el color de fondo antes de dibujar el círculo
                        g.setColor(backgroundColor);
                        g.fillRect(0, 0, getWidth(), getHeight());

                        // Dibujar el círculo
                        g.setColor(piedraColor);
                        g.fillOval(5, 5, width - 10, height - 10);

                        // Dibujar la estrella
                        drawStar(g, width / 2, height / 2);
                        break;
                    case 't':
                        // Dibujar el óvalo
                        g.setColor(backgroundColor);
                        g.fillRect(0, 0, getWidth(), getHeight());
                        g.setColor(piedraColor);
                        g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
                        // Dibujar el número 3 en la mitad del óvalo
                        if(piedraColor.equals(colorJ1)){
                            g.setColor(colorJ2);
                        }else{
                            g.setColor(colorJ1);
                        }
                        Font font = new Font("Arial", Font.BOLD, 30);
                        g.setFont(font);
                        FontMetrics fontMetrics = g.getFontMetrics();
                        String numero;
                        if(life < 7 && life > 4){
                            numero = "▼";
                            life--;
                        } else if (life < 5 && life > 2) {
                            numero = "V";
                            life--;
                        }else if(life < 3 && life > 0){
                            numero = "/";
                            life--;
                        }else{
                            life = 5;
                            numero = "▼";
                        }

                        int x = (getWidth() - fontMetrics.stringWidth(numero)) / 2;
                        int y = (getHeight() - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
                        g.drawString(numero, x, y);
                }
            }
        }
        private void drawStar(Graphics g, int x, int y) {
            int[] xPoints = new int[2 * 5];
            int[] yPoints = new int[2 * 5];

            double angle = Math.PI / 5;

            for (int i = 0; i < 2 * 5; i++) {
                double r = (i % 2 == 0) ? 15 : 15 / 2.0;
                xPoints[i] = x + (int) (r * Math.cos(i * angle));
                yPoints[i] = y + (int) (r * Math.sin(i * angle));
            }
            if(piedraColor.equals(colorJ1)){
                g.setColor(colorJ2);
            }else{
                g.setColor(colorJ1);
            }
            g.fillPolygon(xPoints, yPoints, 2 * 5);
            g.setColor(backgroundColor);
        }
        private void setBackgroundColor(Graphics g) {
            switch (backType) {
                case 'm':
                    g.setColor(new Color(255, 0, 0, 80));
                    break;
                case 'p':
                    g.setColor(new Color(0, 0, 255, 80)); // Cambia a tu color de fondo deseado
                    break;
                case 'g':
                    g.setColor(new Color(255, 215, 0, 80)); // Cambia a tu color de fondo deseado
                    break;
                default:
                    g.setColor(backgroundColor);
                    break;
            }
        }
    }
    private void updateBorders() {

        if (turn) {
            // Si es el turno de player1 (izquierda), actualizar el borde de player1 y quitar el borde de player2
            piedraJ1.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            piedraJ2.setBorder(null);
        } else {
            // Sí es el turno de player2 (derecha), actualizar el borde de player2 y quitar el borde de player1
            piedraJ1.setBorder(null);
            piedraJ2.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        }
    }
    public Stone getFirstStoneOfType(ArrayList<Stone> stones,Class<?> type) {
        for (Stone stone : stones) {
            if (type.isInstance(stone) && stone.getClass().equals(type)) {
                return stone;
            }
        }
        return null; // Si no se encuentra un objeto del tipo especificado
    }
    private void updateRemainingLabels() {
        piedra1.setPiedraColor(colorJ1);
        piedra2.setPiedraColor(colorJ2);
        name1.setText(gomoku.getPlayer1().getName());
        name1.setForeground(Color.WHITE);
        name2.setText(gomoku.getPlayer2().getName());
        name2.setForeground(Color.WHITE);
        numNormalJ1.setText("Normales restantes: "+ gomoku.getPlayer1().numOfType(Stone.class));
        numPesadaJ1.setText("Pesadas restantes: "+ gomoku.getPlayer1().numOfType(Heavy.class));
        numTemporaryJ1.setText("Temporales restantes: "+ gomoku.getPlayer1().numOfType(Temporary.class));
        numNormalJ2.setText("Normales restantes: "+ gomoku.getPlayer2().numOfType(Stone.class));
        numPesadaJ2.setText("Pesadas restantes: "+ gomoku.getPlayer2().numOfType(Heavy.class));
        numTemporaryJ2.setText("Temporales restantes: "+ gomoku.getPlayer2().numOfType(Temporary.class));
        punctuationJ1.setText("La puntuacion es de: " + gomoku.getPlayer1().getPunctuation());
        punctuationJ2.setText("La puntuacion es de: " + gomoku.getPlayer2().getPunctuation());
        tiempoLabel.setText("El tiempo transcurrido es: " + gomoku.getBoard().getSegundosTranscurridos());
    }
    private void startTimer() {
        // Actualiza el tiempoLabel cada segundo
        Timer timer = new Timer(1000, e -> {
            // Actualiza el tiempoLabel cada segundo
            tiempoLabel.setText("El tiempo transcurrido es: " + gomoku.getBoard().getSegundosTranscurridos());
        });
        timer.start();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                GomokuGUI gui = new GomokuGUI();
                gui.startTimer();
                gui.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Paso algo inseperado");
                Log.record(e);
            }
        });
    }
}

