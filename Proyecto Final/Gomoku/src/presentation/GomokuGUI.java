package presentation;

import domain.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
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
    private JLabel numNormalJ1;
    private JLabel numPesadaJ1;
    private JLabel numTemporaryJ1;
    private JLabel numNormalJ2;
    private JLabel numPesadaJ2;
    private JLabel numTemporaryJ2;
    private JLabel name1;
    private JLabel name2;
    private Gomoku gomoku;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel mainPanel;
    private JPanel gamePanel;
    private JPanel boardPanel;
    private Cell[][] cellMatrix;
    private JPanel piedraJ1;
    private JPanel piedraJ2;

    private String nombreJ1 = "Jugador 1.";
    private String nombreJ2 = "Jugador 2.";
    private boolean turn = true;
    private Piedra piedra1;
    private Piedra piedra2;
    private int size = 15;
    private int stoneLimit = size*size;
    private int timeLimit = -1;
    private int porcentajeEspeciales = 50;
    public static Color colorJ1 = Color.BLACK;
    public static Color colorJ2 = Color.WHITE;
    private Stone selectedStoneJ1 = null;
    private Stone selectedStoneJ2 = null;
    private boolean canRefill;
    private static ArrayList<Stone> stonesJ1 = new ArrayList<>();
    private static ArrayList<Stone> stonesJ2 = new ArrayList<>();
    public GomokuGUI() throws Exception {
        prepareElements();
        prepareActions();
    }

    private void prepareElements() throws Exception {
        prepareScreens();
        prepareActionsMenu();
        setTitle("Gomoku");
        setSize(new Dimension(SIDE * SIZE+200, SIDE * SIZE + 50));
        setResizable(false);
        setLocationRelativeTo(null);
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
        ImagePanel fondo = new ImagePanel("GomokuImages/inicio.png");
        JPanel initialPanel = new JPanel(new GridBagLayout());
        initialPanel.setOpaque(false);
        initialPanel.setBackground(Color.BLACK);
        // Crear un título con "Go" en negrita y "moku" en normal
        JPanel titulo = new JPanel(new BorderLayout());
        titulo.setOpaque(false);
        JLabel titulo1 = new JLabel("", SwingConstants.CENTER);
        titulo1.setFont(new Font("Arial", Font.ITALIC, 400));
        titulo1.setPreferredSize(new Dimension(200, 250));
        titulo.add(titulo1, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

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
        fondo.add(initialPanel);
        return fondo;
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
        coloresMap.put("GRIS", new Color(64, 64, 64));
        coloresMap.put("AZUL", new Color(0, 0, 128));
        coloresMap.put("VERDE", new Color(0, 100, 0));
        coloresMap.put("PURPURA", new Color(128, 0, 128)); // Puedes ajustar estos valores según tus preferencias
        coloresMap.put("ROJO", Color.RED);
        JPanel jugador1 = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("JUGADOR 1", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        GridBagConstraints gbc = new GridBagConstraints();
        // Add the label "Ingresa tu nombre:"
        JLabel nombreLabel = new JLabel("Ingresa tu nombre:");

        JTextField nombre = new JTextField();
        nombre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cuando se presiona Enter en el JTextField, se actualizará el nombre
                actualizarNombreJ1();
            }
            private void actualizarNombreJ1() {
                // Actualiza la variable de clase con el texto actual del JTextField
                nombreJ1 = nombre.getText();
                System.out.println("Nombre actualizado del jugador 1: " + nombreJ1);
            }
        });


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
        coloresMap.put("AZUL", new Color(135, 206, 235));
        coloresMap.put("VERDE", new Color(144, 238, 144));
        coloresMap.put("ROSA", new Color(128, 0, 128));
        coloresMap.put("ROJO", new Color(255, 192, 203));

        JPanel jugador2 = new JPanel();
        jugador2.setLayout(new BoxLayout(jugador2, BoxLayout.Y_AXIS)); // Set layout to BoxLayout

        JLabel titulo = new JLabel("JUGADOR 2", SwingConstants.LEFT);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel nombreLabel = new JLabel("Ingresa tu nombre:");
        JTextField nombre = new JTextField();
        nombre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cuando se presiona Enter en el JTextField, se actualizará el nombre
                actualizarNombreJ2();
            }
            private void actualizarNombreJ2() {
                // Actualiza la variable de clase con el texto actual del JTextField
                nombreJ2 = nombre.getText();
                System.out.println("Nombre actualizado del jugador 1: " + nombreJ2);
            }
        });

        JPanel selectColorFicha = new JPanel(new FlowLayout());
        JLabel colorLabel1 = new JLabel("Selecciona el color de ficha:");
        String[] coloresJ2 = {"BLANCO", "AMARILLO", "AZUL", "VERDE", "ROSA", "ROJO"};
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

        jugador2.add(titulo, BorderLayout.NORTH);
        jugador2.add(nombreLabel, BorderLayout.WEST);
        jugador2.add(nombre, BorderLayout.CENTER);
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

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ingresa un número válido.");
            }
        });
        // Segundo TextField
        JLabel especialesLabel = new JLabel("Porcentaje de especiales:");
        JTextField especialesTextField = new JTextField();
        tamañoTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cuando se presiona Enter en el JTextField, se actualizará el nombre
                actualizarTamaño();
            }
            private void actualizarTamaño() {
                // Actualiza la variable de clase con el texto actual del JTextField
                size = Integer.parseInt(tamañoTextField.getText());
                System.out.println("Tamaño del tablero: " + size);
                stoneLimit = size*size;
            }
        });
        JButton boton = new JButton("Iniciar Juego");

        boton.addActionListener(e -> {
            try {
                empezarJuego();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"Algo elegiste mal");
            }
        });

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
    private void empezarJuego() throws Exception {
        updateRemainingLabels();
        optionNew();
        cardLayout.show(cardPanel, "game");
    }
    private JPanel createGamePanel() throws Exception {
        gamePanel = new JPanel(new BorderLayout());
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
            gomoku = new Gomoku(size, stoneLimit, timeLimit);
            gomoku.getPlayer1().refillStones(stoneLimit/2, porcentajeEspeciales);
            gomoku.getPlayer2().refillStones(stoneLimit/2, porcentajeEspeciales);
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
            reset();
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
        JPanel datosJ1 = new JPanel(new GridBagLayout());
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Márgenes entre los componentes

        JLabel normalRemaining = new JLabel("Escoge la ficha que vas a utilizar: ");
        numNormalJ1 = new JLabel();
        numPesadaJ1 = new JLabel();
        numTemporaryJ1 = new JLabel();
        JButton normal = new JButton("NORMAL");
        JButton pesada = new JButton("HEAVY");
        JButton temporal = new JButton("TEMPORARY");
        normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para el botón NORMAL
                selectedStoneJ1 = getFirstStoneOfType(stonesJ1, Stone.class);
            }
        });
        pesada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para el botón HEAVY
                selectedStoneJ1 = getFirstStoneOfType(stonesJ1, Heavy.class);
            }
        });
        temporal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para el botón TEMPORARY
                JOptionPane.showMessageDialog(null, "En construccion.");
            }
        });

        Dimension buttonSize = new Dimension(120, 30); // Tamaño deseado para los botones
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
        JLabel puntuacion = new JLabel("Tu puntuación es: ");
        JLabel tiempo = new JLabel("El tiempo transcurrido es: ");
        puntuacionTiempo.add(puntuacion, BorderLayout.NORTH);
        puntuacionTiempo.add(tiempo, BorderLayout.SOUTH);


        // Agregar el JLabel al JPanel
        leftPanel.add(datosJ1, BorderLayout.NORTH);
        leftPanel.add(piedrasJ1, BorderLayout.CENTER);
        leftPanel.add(puntuacionTiempo, BorderLayout.SOUTH);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Agregar el JPanel al contenedor principal (mainPanel)
        gamePanel.add(leftPanel, BorderLayout.WEST);
    }

    private void addRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Cargar la imagen
        JPanel datosJ2 = new JPanel(new GridBagLayout());
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Márgenes entre los componentes

        JLabel normalRemaining = new JLabel("Escoge la ficha que vas a utilizar: ");
        numNormalJ2 = new JLabel();
        numPesadaJ2 = new JLabel();
        numTemporaryJ2 = new JLabel();
        JButton normal = new JButton("NORMAL");
        JButton pesada = new JButton("HEAVY");
        JButton temporal = new JButton("TEMPORARY");
        normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para el botón NORMAL
                selectedStoneJ2 = getFirstStoneOfType(stonesJ2, Stone.class);
            }
        });
        pesada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para el botón HEAVY
                selectedStoneJ2 = getFirstStoneOfType(stonesJ2, Heavy.class);
            }
        });
        temporal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para el botón TEMPORARY
                JOptionPane.showMessageDialog(null, "En construccion.");
            }
        });
        Dimension buttonSize = new Dimension(120, 30); // Tamaño deseado para los botones
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
        JLabel puntuacion = new JLabel("Tu puntuación es: ");
        JLabel tiempo = new JLabel("El tiempo transcurrido es: ");
        puntuacionTiempo.add(puntuacion, BorderLayout.NORTH);
        puntuacionTiempo.add(tiempo, BorderLayout.SOUTH);


        // Agregar el JLabel al JPanel
        rightPanel.add(datosJ2, BorderLayout.NORTH);
        rightPanel.add(piedrasJ2, BorderLayout.CENTER);
        rightPanel.add(puntuacionTiempo, BorderLayout.SOUTH);
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Agregar el JPanel al contenedor principal (mainPanel)
        gamePanel.add(rightPanel, BorderLayout.EAST);
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
                throw new RuntimeException(ex);
            }
        });
        bottomPanel.add(guardarFichaButton);
        // Agregar el panel inferior a la parte inferior de la pantalla principal
        gamePanel.add(bottomPanel, BorderLayout.SOUTH);
    }
    private void reset() {
        stonesJ1 = gomoku.getPlayer1().getRemainingStones();
        stonesJ2 = gomoku.getPlayer2().getRemainingStones();
        cellMatrix = gomoku.board();
        Component[] components = boardPanel.getComponents();
        updateBorders();
        updateRemainingLabels();
        boardPanel.revalidate(); // Asegurar que el panel se redibuje correctamente
        boardPanel.repaint();
    }
    private void refresh(int row, int column) {
        stonesJ1 = gomoku.getPlayer1().getRemainingStones();
        stonesJ2 = gomoku.getPlayer2().getRemainingStones();
        cellMatrix = gomoku.board();
        Component[] components = boardPanel.getComponents();
        Piedra piedra = (Piedra) components[row * cellMatrix[0].length + column];
        refreshStoneAppearance(piedra, cellMatrix[row][column].getStone());
        updateBorders();
        updateRemainingLabels();
        boardPanel.revalidate(); // Ensure that the panel is redrawn correctly
        boardPanel.repaint();
    }

    private void refreshStoneAppearance(Piedra piedra, Stone stone) {
        if (stone.getColor().equals(Color.BLACK)) {
            piedra.setPiedraColor(colorJ1);
        } else {
            piedra.setPiedraColor(colorJ2);
        }
        piedra.setType(chooseCharForAStone(stone));
        piedra.makeVisible();
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
            cardLayout.show(cardPanel, "initial");
        });
        getJMenuBar().getMenu(0).getItem(6).addActionListener(e -> {
            cardLayout.show(cardPanel, "config");
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
        reset();

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
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Selecciona el tipo de ficha que quieres poner!");
            }
        }
    }
    private void ponerFicha(int row, int col) throws Exception {
        Stone selectedStone;
        if (turn) {
            if(selectedStoneJ1 == null){
                selectedStoneJ1 = getFirstStoneOfType(stonesJ1, Stone.class);
            }
            selectedStone = selectedStoneJ1;

        } else {
            if(selectedStoneJ2 == null){
                selectedStoneJ2 = getFirstStoneOfType(stonesJ2, Stone.class);
            }
            selectedStone = selectedStoneJ2;
        }
        try {
            if(gomoku.play(row, col, selectedStone)){
                if(turn){
                    refresh(row, col);
                    JOptionPane.showMessageDialog(null, "GANAASTEEEEEEEE. "+nombreJ2);
                    cardLayout.show(cardPanel, "initial");
                }else{
                    refresh(row, col);
                    JOptionPane.showMessageDialog(null, "GANAASTEEEEEEEE. "+nombreJ1);
                    cardLayout.show(cardPanel, "initial");
                }
            }
            if(turn){
                removeFirstStoneOfType(stonesJ1, selectedStone.getClass());
                turn = false;
            }else{
                removeFirstStoneOfType(stonesJ2, selectedStone.getClass());
                turn = true;
            }
            refresh(row, col);
        } catch (Exception e) {
            if(e.getMessage().equals(GomokuException.STONE_OVERLOAP)){
                if(turn){
                    turn = false;
                }else{
                    turn = true;
                }
                JOptionPane.showMessageDialog(null, e.getMessage());
            }else{
                throw new Exception(e.getMessage());
            }
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

    public static class Piedra extends JPanel {
        private Color piedraColor;
        private Color backgroundColor;
        private char type;
        //private int shape;
        private boolean isVisible;

        public Piedra(boolean isVisible) {
            this.piedraColor = Color.WHITE; // Color predeterminado
            this.backgroundColor = new Color(222, 184, 135, 80); // Color predeterminado
            this.isVisible = isVisible;
        }
        public void setType(char type){
            this.type = type;
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
                        drawStar(g, width / 2, height / 2, 20, 5);
                        break;
                    case 't':

                }
            }
        }
        private void drawStar(Graphics g, int x, int y, int radius, int numPoints) {
            int[] xPoints = new int[2 * numPoints];
            int[] yPoints = new int[2 * numPoints];

            double angle = Math.PI / numPoints;

            for (int i = 0; i < 2 * numPoints; i++) {
                double r = (i % 2 == 0) ? radius : radius / 2.0;
                xPoints[i] = x + (int) (r * Math.cos(i * angle));
                yPoints[i] = y + (int) (r * Math.sin(i * angle));
            }
            if(piedraColor.equals(colorJ1)){
                g.setColor(colorJ2);
            }else{
                g.setColor(colorJ1);
            }
            g.fillPolygon(xPoints, yPoints, 2 * numPoints);
            g.setColor(backgroundColor);
        }
    }
    private void updateBorders() {

        if (turn) {
            // Si es el turno de player1 (izquierda), actualizar el borde de player1 y quitar el borde de player2
            piedraJ1.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            piedraJ2.setBorder(null);
        } else {
            // Si es el turno de player2 (derecha), actualizar el borde de player2 y quitar el borde de player1
            piedraJ1.setBorder(null);
            piedraJ2.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        }
    }
    public Stone getFirstStoneOfType(ArrayList<Stone> stones,Class<?> type) {
        for (Stone stone : stones) {
            if (type.isInstance(stone) && stone.getClass() == type) {
                return stone;
            }
        }
        return null; // Si no se encuentra un objeto del tipo especificado
    }
    public Stone removeFirstStoneOfType(ArrayList<Stone> stones, Class<?> type) {
        Iterator<Stone> iterator = stones.iterator();
        while (iterator.hasNext()) {
            Stone stone = iterator.next();
            if (type.isInstance(stone) && stone.getClass() == type) {
                iterator.remove();
                return stone;
            }
        }
        return null; // Si no se encuentra un objeto del tipo especificado
    }
    private void updateRemainingLabels() {
        piedra1.setPiedraColor(colorJ1);
        piedra2.setPiedraColor(colorJ2);
        name1.setText(nombreJ1);
        name2.setText(nombreJ2);
        numNormalJ1.setText("Normales restantes: "+ gomoku.getPlayer1().numOfType(Stone.class));
        numPesadaJ1.setText("Pesadas restantes: "+ gomoku.getPlayer1().numOfType(Heavy.class));
        numTemporaryJ1.setText("Temporales restantes: "+ gomoku.getPlayer1().numOfType(Temporary.class));
        numNormalJ2.setText("Normales restantes: "+ gomoku.getPlayer2().numOfType(Stone.class));
        numPesadaJ2.setText("Pesadas restantes: "+ gomoku.getPlayer2().numOfType(Heavy.class));
        numTemporaryJ2.setText("Temporales restantes: "+ gomoku.getPlayer2().numOfType(Temporary.class));
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

