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
import java.awt.event.ItemEvent;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Clase que representa la interfaz gráfica de la aplicación Gomoku.
 */
public class GomokuGUI extends JFrame {
    private final int windowWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int windowHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    private boolean volumen = true;
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
    private Class<? extends Player> machineType = Human.class;
    private String gameMode = "Normal";
    private Timer timerGUI;
    private static boolean clickEnabled = true;

    /**
     * Constructor de la clase GomokuGUI.
     * @throws Exception Excepción en caso de algún problema.
     */
    public GomokuGUI() throws Exception {
        prepareElements();
        prepareActions();
    }
    /**
     * Inicializa y prepara los elementos de la interfaz gráfica.
     * @throws Exception Excepción en caso de algún problema.
     */
    private void prepareElements() throws Exception {
        prepareScreens();
        setTitle("Gomoku");
        // Establecer el tamaño de la ventana
        setSize(new Dimension((int) (windowWidth * 0.5), (int) (windowHeight * 0.5)));
        setResizable(false);
        setLocationRelativeTo(null);
    }
    /**
     * Inicializa y prepara las pantallas de la interfaz gráfica.
     * @throws Exception Excepción en caso de algún problema.
     */
    private void prepareScreens() throws Exception {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        setTitle("Gomoku.");
        JPanel initialPanel = createInitialPanel();
        cardPanel.add(initialPanel, "initial");
        JPanel rulePanel = createRulePanel();
        cardPanel.add(rulePanel, "Rules");
        JPanel configurePanel = createConfiguraciones();
        cardPanel.add(configurePanel, "config");
        JPanel gamePanel = createGamePanel();
        cardPanel.add(gamePanel, "game");
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }
    /**
     * Crea el panel inicial de la interfaz.
     * @return Panel inicial de la interfaz.
     */
    private JPanel createInitialPanel() {
        ImagePanel fondo = new ImagePanel("GomokuImages/inicio.png");
        JPanel initialPanel = new JPanel(new GridBagLayout());
        initialPanel.setOpaque(false);
        initialPanel.setBackground(Color.BLACK);

        JPanel titulo = new JPanel(new BorderLayout());
        titulo.setOpaque(false);
        JLabel titulo1 = new JLabel("", SwingConstants.CENTER);
        titulo1.setFont(new Font("Arial", Font.ITALIC, 400));
        titulo1.setPreferredSize(new Dimension((int) (windowWidth * 0.052083333), (int)(windowHeight * 0.092592593)));
        titulo.add(titulo1, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets((int)(windowHeight * 0.018518519), (int) (windowWidth * 0.010416667), (int)(windowHeight * 0.018518519), (int) (windowWidth * 0.010416667)); // Espacio entre los botones

        JButton button1 = new JButton("NEW GAME");
        JButton button2 = new JButton("CONTINUE");
        JButton button3 = new JButton("HOW TO PLAY");

        // Configurar el tamaño de los botones
        Dimension buttonSize = new Dimension((int)(windowWidth * 0.104166667), (int)(windowHeight * 0.046296296));
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);

        // Añadir ActionListeners según sea necesario
        button1.addActionListener(e -> {
            cardLayout.show(cardPanel, "config");
            // Establecer el tamaño de la ventana
            setSize(new Dimension((int)(windowWidth * 0.25), (int)(windowHeight * 0.6)));
            setResizable(false);
            setLocationRelativeTo(null);
        });
        button2.addActionListener(e -> optionOpen());
        // Añadir los botones al panel con GridBagLayout
        button3.addActionListener(e -> {
            cardLayout.show(cardPanel, "Rules");
            setSize(new Dimension((int)(windowWidth * 0.75), (int)(windowHeight * 0.8)));
            setResizable(false);
            setLocationRelativeTo(null);
        });
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
    /**
     * Crea el panel de reglas de la interfaz.
     * @return Panel de reglas de la interfaz.
     */
    private JPanel createRulePanel() {
        ImagePanel fondo = new ImagePanel("GomokuImages/fondoReglas.jpg");
        JPanel rulePanel = new JPanel(new GridBagLayout());
        rulePanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;

        JPanel howToPlayPanel = new JPanel(new BorderLayout());
        howToPlayPanel.setOpaque(false);
        JLabel titulo = new JLabel("HOW TO PLAY",  SwingConstants.CENTER);
        titulo.setForeground(Color.RED);
        titulo.setFont(new Font("Arial", Font.BOLD, (int)(windowHeight * 0.016666667)));
        JLabel ganar = new JLabel(
                "  - El objetivo es alinear cinco piedras exactas de un color consecutivas en cualquier dirección.\n"
        );
        ganar.setForeground(Color.WHITE);

        ImageIcon imagen = new ImageIcon("GomokuImages/victoria.png");
        ImageIcon resized = new ImageIcon(imagen.getImage().getScaledInstance(
                (int)(windowWidth * 0.078125),
                (int)(windowHeight * 0.04),
                Image.SCALE_SMOOTH
        ));

        JLabel imagenVictoria = new JLabel(resized);

        howToPlayPanel.add(titulo, BorderLayout.NORTH);

        // Espacio entre imagen y texto
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(imagenVictoria, BorderLayout.CENTER);
        centerPanel.add(Box.createRigidArea(new Dimension((int)(windowWidth * 0.005208333), 0)), BorderLayout.WEST);  // Ajusta el tamaño del espacio según sea necesario
        howToPlayPanel.add(centerPanel, BorderLayout.CENTER);

        howToPlayPanel.add(ganar, BorderLayout.WEST);
        //howToPlayPanel.add(defecto, BorderLayout.SOUTH);
        rulePanel.add(howToPlayPanel, gbc);
        gbc.gridy ++;
        rulePanel.add(Box.createRigidArea(new Dimension(0, (int)(windowHeight * 0.005))), gbc);


        JPanel maquinaPanel = new JPanel(new BorderLayout());
        maquinaPanel.setOpaque(false);

        JPanel modoJuego = new JPanel();
        modoJuego.setOpaque(false);
        modoJuego.setLayout(new BoxLayout(modoJuego, BoxLayout.Y_AXIS));
        JLabel tituloModoJuego = new JLabel("MODOS DE JUEGO:",  SwingConstants.CENTER);
        tituloModoJuego.setForeground(Color.RED);
        JLabel jugadorVsJugador = new JLabel("  - Jugador vs Jugador: En este modo se tendrán dos jugadores humanos.");
        jugadorVsJugador.setForeground(Color.WHITE);

        JLabel jugadorVsMaquina = new JLabel("  - Jugador vs Máquina: En esta versión uno de los dos jugadores es automático.");
        jugadorVsMaquina.setForeground(Color.WHITE);

        JPanel tiposMaquina = new JPanel();
        tiposMaquina.setOpaque(false);
        tiposMaquina.setLayout(new BoxLayout(tiposMaquina, BoxLayout.Y_AXIS));
        JLabel tituloTiposMaquina = new JLabel("TIPOS DE MAQUINAS:",  SwingConstants.CENTER);
        tituloTiposMaquina.setForeground(Color.RED);
        JLabel agresiva = new JLabel("  - Agresiva: Siempre juega a la defensiva intentando bloquear las piedras del otro jugador.");
        agresiva.setForeground(Color.WHITE);
        JLabel experta = new JLabel("  - Experta: Se limita a hacer un 5 en raya sin importar las piedras del otro jugador.");
        experta.setForeground(Color.WHITE);
        JLabel miedosa = new JLabel("  - Miedosa: Evita estar cerca a las piedras del otro jugador.");
        miedosa.setForeground(Color.WHITE);
        modoJuego.add(tituloModoJuego);
        modoJuego.add(jugadorVsJugador);
        modoJuego.add(jugadorVsMaquina);

        tiposMaquina.add(tituloTiposMaquina);
        tiposMaquina.add(agresiva);
        tiposMaquina.add(experta);
        tiposMaquina.add(miedosa);

        maquinaPanel.add(modoJuego, BorderLayout.WEST);
        maquinaPanel.add(tiposMaquina, BorderLayout.EAST);

        rulePanel.add(maquinaPanel, gbc);
        gbc.gridy++;
        //rulePanel.add(Box.createRigidArea(new Dimension(0, (int)(windowHeight * 0.005))));


        JPanel tiposDePiedra = new JPanel();
        JLabel tituloTipos = new JLabel("TIPOS DE PIEDRAS:", SwingConstants.LEFT);
        tituloTipos.setForeground(Color.RED);
        tiposDePiedra.setOpaque(false);
        tiposDePiedra.setLayout(new BoxLayout(tiposDePiedra, BoxLayout.Y_AXIS));

// Panel para piedraNormal
        JPanel piedraNormal = new JPanel(new FlowLayout(FlowLayout.LEFT));
        piedraNormal.setOpaque(false);
        JLabel texto = new JLabel("  - Normal: Ocupa una casilla.");
        texto.setForeground(Color.WHITE);
        ImageIcon imagen2 = new ImageIcon("GomokuImages/normalStone.png");
        ImageIcon resized2 = new ImageIcon(imagen2.getImage().getScaledInstance(
                (int)(windowHeight * 0.03),
                (int)(windowHeight * 0.03),
                Image.SCALE_SMOOTH
        ));

        JLabel imagenNormal = new JLabel(resized2);
        piedraNormal.add(texto);
        piedraNormal.add(imagenNormal);

// Panel para piedraPesada
        JPanel piedraPesada = new JPanel(new FlowLayout(FlowLayout.LEFT));
        piedraPesada.setOpaque(false);
        JLabel texto2 = new JLabel("  - Pesada: Vale por dos piedras en esa posicion");
        texto2.setForeground(Color.WHITE);
        ImageIcon imagen3 = new ImageIcon("GomokuImages/pesadaStone.png");
        ImageIcon resized3 = new ImageIcon(imagen3.getImage().getScaledInstance(
                (int)(windowHeight * 0.03),
                (int)(windowHeight * 0.03),
                Image.SCALE_SMOOTH
        ));

        JLabel imagenPesada = new JLabel(resized3);
        piedraPesada.add(texto2);
        piedraPesada.add(imagenPesada);

// Panel para piedraTemporal
        JPanel piedraTemporal = new JPanel(new FlowLayout(FlowLayout.LEFT));
        piedraTemporal.setOpaque(false);
        JLabel texto3 = new JLabel("  - Temporal: Permanece por tres turnos.");
        texto3.setForeground(Color.WHITE);
        ImageIcon imagen4 = new ImageIcon("GomokuImages/temporalStone.png");
        ImageIcon resized4 = new ImageIcon(imagen4.getImage().getScaledInstance(
                (int)(windowHeight * 0.03),
                (int)(windowHeight * 0.03),
                Image.SCALE_SMOOTH
        ));
        JLabel imagenTemporal = new JLabel(resized4);
        piedraTemporal.add(texto3);
        piedraTemporal.add(imagenTemporal);

        tiposDePiedra.add(tituloTipos);
        tiposDePiedra.add(piedraNormal);
        tiposDePiedra.add(piedraPesada);
        tiposDePiedra.add(piedraTemporal);

        rulePanel.add(tiposDePiedra, gbc);
        gbc.gridy++;
        //rulePanel.add(Box.createRigidArea(new Dimension(0, (int)(windowHeight * 0.005))));



        JPanel tiposDeCasillas = new JPanel();
        JLabel tituloTiposCasillas = new JLabel("TIPOS DE CASILLAS:", SwingConstants.LEFT);
        tituloTiposCasillas.setForeground(Color.RED);
        tiposDeCasillas.setOpaque(false);
        tiposDeCasillas.setLayout(new BoxLayout(tiposDeCasillas, BoxLayout.Y_AXIS));

    // Panel para casillaNormal
        JPanel casillaNormal = new JPanel(new FlowLayout(FlowLayout.LEFT));
        casillaNormal.setOpaque(false);
        JLabel texto4 = new JLabel("  - NORMAL: Sin ningun efecto especial.");
        texto4.setForeground(Color.WHITE);
        ImageIcon imagen5 = new ImageIcon("GomokuImages/casillaNormal.png");
        ImageIcon resized5 = new ImageIcon(imagen5.getImage().getScaledInstance(
                (int)(windowHeight * 0.03),
                (int)(windowHeight * 0.03),
                Image.SCALE_SMOOTH
        ));

        JLabel imagenCasillaNormal = new JLabel(resized5);
        casillaNormal.add(texto4);
        casillaNormal.add(imagenCasillaNormal);

    //Panel para casillaMina
        JPanel casillaMina = new JPanel(new FlowLayout(FlowLayout.LEFT));
        casillaMina.setOpaque(false);
        JLabel texto5 = new JLabel("  - MINE: Explota en un espacio 3x3 eliminando todas las piedras en este espacio.");
        texto5.setForeground(Color.WHITE);
        ImageIcon imagen6 = new ImageIcon("GomokuImages/casillaMine.png");
        ImageIcon resized6 = new ImageIcon(imagen6.getImage().getScaledInstance(
                (int)(windowHeight * 0.03),
                (int)(windowHeight * 0.03),
                Image.SCALE_SMOOTH
        ));
        JLabel imagenCasillaMina = new JLabel(resized6);
        casillaMina.add(texto5);
        casillaMina.add(imagenCasillaMina);

// Panel para piedraTemporal
        JPanel casillaTeleport = new JPanel(new FlowLayout(FlowLayout.LEFT));
        casillaTeleport.setOpaque(false);
        JLabel texto6 = new JLabel("  - TELEPORT: Teletransporta a la piedra a otro lugar aleatorio en el tablero.");
        texto6.setForeground(Color.WHITE);
        ImageIcon imagen7 = new ImageIcon("GomokuImages/casillaTelepor.png");
        ImageIcon resized7 = new ImageIcon(imagen7.getImage().getScaledInstance(
                (int)(windowHeight * 0.03),
                (int)(windowHeight * 0.03),
                Image.SCALE_SMOOTH
        ));
        JLabel imagenCasillaTemporal = new JLabel(resized7);
        casillaTeleport.add(texto6);
        casillaTeleport.add(imagenCasillaTemporal);

        JPanel casillaGolden = new JPanel(new FlowLayout(FlowLayout.LEFT));
        casillaGolden.setOpaque(false);
        JLabel texto7 = new JLabel("  - GOLDEN: Proporciona al jugador un tipo de piedra aleatorio. Si es normal, el jugador debe usar dos piedras normales en el siguiente turno.");
        texto7.setForeground(Color.WHITE);
        ImageIcon imagen8 = new ImageIcon("GomokuImages/casillaGolden.png");
        ImageIcon resized8 = new ImageIcon(imagen8.getImage().getScaledInstance(
                (int)(windowHeight * 0.03),
                (int)(windowHeight * 0.03),
                Image.SCALE_SMOOTH
        ));
        JLabel imagenCasillaGolden = new JLabel(resized8);
        casillaGolden.add(texto7);
        casillaGolden.add(imagenCasillaGolden);



        tiposDeCasillas.add(tituloTiposCasillas);
        tiposDeCasillas.add(casillaNormal);
        tiposDeCasillas.add(casillaMina);
        tiposDeCasillas.add(casillaTeleport);
        tiposDeCasillas.add(casillaGolden);

        rulePanel.add(tiposDeCasillas, gbc);
        gbc.gridy++;

        JPanel puntuaciones = new JPanel();
        puntuaciones.setLayout(new BoxLayout(puntuaciones, BoxLayout.Y_AXIS));
        String imagePath = "GomokuImages/flecha.png";

        // Crear un ImageIcon

        // Redimensionar la imagen
        ImageIcon ima = new ImageIcon("GomokuImages/flecha.png");
        ImageIcon res = new ImageIcon(ima.getImage().getScaledInstance((int)(windowWidth * 0.006481481), (int)(windowHeight * 0.0069444444), Image.SCALE_SMOOTH));

        // Crear un botón con la imagen redimensionada
        JButton back = new JButton(res);

        back.addActionListener(e -> optionNewInicio());
        JLabel tituloPuntuaciones = new JLabel("PUNTUACIÓN:", SwingConstants.CENTER);
        tituloPuntuaciones.setForeground(Color.RED);
        JLabel informacion2 = new JLabel("     + 100 puntos al eliminar una piedra enemiga");
        informacion2.setForeground(Color.WHITE);
        JLabel informacion3 = new JLabel( "     -  50 puntos cuando una piedra propia es eliminada.");
        informacion3.setForeground(Color.WHITE);
        JLabel informacion4 = new JLabel("     + 100 puntos al usar una piedra especial");
        informacion4.setForeground(Color.WHITE);
        JLabel informacion5 = new JLabel("  Con la obtención de 1000 puntos se pone una piedra extra al jugador correspondiente.");
        informacion5.setForeground(Color.WHITE);
        puntuaciones.setForeground(Color.WHITE);
        puntuaciones.setOpaque(false);
        puntuaciones.add(tituloPuntuaciones);
        puntuaciones.add(informacion2);
        puntuaciones.add(informacion3);
        puntuaciones.add(informacion4);
        puntuaciones.add(informacion5);
        puntuaciones.add(new JLabel(" "));
        puntuaciones.add(back);
        rulePanel.add(puntuaciones, gbc);

        fondo.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        fondo.add(rulePanel, gbc);
        return fondo;
    }


    /**
     * Crea el panel de configuraciones de la interfaz.
     * @return Panel de configuraciones de la interfaz.
     */
    private JPanel createConfiguraciones() {
        ImagePanel fondo = new ImagePanel("GomokuImages/configuraciones.jpg");
        JPanel configuraciones = new JPanel(new GridBagLayout());
        configuraciones.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0; // Inicia en la fila 0

        // Crear espacio vertical de 20 píxeles
        configuraciones.add(Box.createVerticalStrut((int)(windowHeight * 0.0023148148)), gbc);
        // Crear y centrar el panel J1
        JPanel panelJ1 = crearPanelJ1();
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        configuraciones.add(panelJ1, gbc);

        // Crear espacio vertical de 20 píxeles
        gbc.gridy++;
        configuraciones.add(Box.createVerticalStrut((int)(windowHeight * 0.0023148148)), gbc);

        // Crear y centrar el panel J2
        JPanel panelJ2 = crearPanelJ2();
        gbc.gridy++;
        configuraciones.add(panelJ2, gbc);

        // Crear espacio vertical de 20 píxeles
        gbc.gridy++;
        configuraciones.add(Box.createVerticalStrut((int)(windowHeight * 0.023148148)), gbc);

        // Crear y centrar el panel Size
        JPanel panelSize = crearPanelSize();
        panelSize.setOpaque(false);
        gbc.gridy++;
        configuraciones.add(panelSize, gbc);
        configuraciones.setOpaque(false);

        fondo.add(configuraciones);
        return fondo;
    }

    /**
     * Crea el panel del jugador1 de la interfaz.
     * @return Panel de jugador1 de la interfaz.
     */
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
        titulo.setFont(new Font("Arial", Font.BOLD, (int)(windowHeight * 0.016666667)));

        JLabel nombreLabel = new JLabel("Ingresa tu nombre:");
        nombreLabel.setFont(new Font("Arial", Font.BOLD, (int)(windowHeight * 0.013888889)));
        JTextField nombre = new JTextField();
        nombre.setColumns((int)(windowWidth * 0.010416667));

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
                SwingUtilities.invokeLater(() -> nombreJ1 = nombre.getText().isEmpty() ? "Jugador 1" : nombre.getText());
            }
        });

        JPanel selectColorFicha = new JPanel(new FlowLayout());
        selectColorFicha.setOpaque(false);
        JLabel colorLabel1 = new JLabel("Selecciona el color de ficha:");
        colorLabel1.setFont(new Font("Arial", Font.BOLD, (int)(windowHeight * 0.012037037)));
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
    /**
     * Crea el panel del jugador2 de la interfaz.
     * @return Panel de jugador2 de la interfaz.
     */
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
        titulo.setFont(new Font("Arial", Font.BOLD, (int)(windowHeight * 0.016666667)));

        JLabel nombreLabel = new JLabel("Ingresa tu nombre:");
        nombreLabel.setFont(new Font("Arial", Font.BOLD, (int)(windowHeight * 0.013888889)));
        JTextField nombre = new JTextField();
        nombre.setColumns((int)(windowWidth * 0.010416667));

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
                SwingUtilities.invokeLater(() -> nombreJ2 = nombre.getText().isEmpty() ? "Jugador 2" : nombre.getText());
            }
        });

        JPanel selectColorFicha = new JPanel(new FlowLayout());
        selectColorFicha.setOpaque(false);
        JLabel colorLabel1 = new JLabel("Selecciona el color de ficha:");
        colorLabel1.setFont(new Font("Arial", Font.BOLD, (int)(windowHeight * 0.013888889)));
        String[] coloresJ2 = {"BLANCO", "AMARILLO", "AZUL", "VERDE", "ROSA", "ROJO"};
        JComboBox<String> ficha2 = new JComboBox<>(coloresJ2);
        ficha2.addActionListener(e -> {
            String colorSeleccionado = (String) ficha2.getSelectedItem();
            colorJ2 = coloresMap.get(colorSeleccionado);
        });
        JCheckBox jugadorCheckBox = new JCheckBox("Jugar contra maquina");
        jugadorCheckBox.setFont(new Font("Arial", Font.BOLD, (int)(windowWidth * 0.006770833)));
        jugadorCheckBox.setSelected(false);

        jugadorCheckBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                // Se ejecuta cuando el JCheckBox se desactiva
                machineType = Human.class;
            } else if (e.getStateChange() == ItemEvent.SELECTED) {
                machineType = Agressive.class;
            }
        });
        JPanel maquinaPanel = new JPanel(new FlowLayout());
        maquinaPanel.setOpaque(false);
        JLabel maquinaLabel = new JLabel("Selecciona la máquina:");
        maquinaLabel.setFont(new Font("Arial", Font.BOLD, (int)(windowHeight * 0.012037037)));
        String[] opcionesMaquina = {"Agresiva", "Miedosa", "Experta"};
        JComboBox<String> maquinaComboBox = new JComboBox<>(opcionesMaquina);

        // Configuración de JComboBox para la máquina
        maquinaComboBox.setEnabled(false);  // Inicialmente desactivado
        maquinaComboBox.addActionListener(e -> {
            // Acciones a realizar cuando se selecciona un elemento en el JComboBox

            String seleccion = (String) maquinaComboBox.getSelectedItem();
            // Luego puedes realizar acciones adicionales según la selección
            if ("Agresiva".equals(seleccion)) {
                try {
                    machineType = Agressive.class;
                } catch (Exception ex) {
                    Log.record(ex);
                    throw new RuntimeException(ex);
                }
            } else if ("Miedosa".equals(seleccion)) {
                try {
                    machineType = Fearful.class;
                } catch (Exception ex) {
                    Log.record(ex);
                    throw new RuntimeException(ex);
                }
                // Acciones para la opción "Miedosa"
            } else if ("Experta".equals(seleccion)) {
                try {
                    machineType = Expert.class;
                } catch (Exception ex) {
                    Log.record(ex);
                    throw new RuntimeException(ex);
                }
            }
        });

        maquinaPanel.add(maquinaLabel);
        maquinaPanel.add(maquinaComboBox);

        jugadorCheckBox.addItemListener(e -> maquinaComboBox.setEnabled(jugadorCheckBox.isSelected()));

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

    /**
     * Crea el panel de la seleccion de size de la interfaz.
     * @return Panel de la seleccion de size de la interfaz.
     */
    private JPanel crearPanelSize() {
        JPanel panelSize = new JPanel(new GridBagLayout());
        panelSize.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // Agregar relleno alrededor de los componentes
        Insets insets = new Insets((int)(windowHeight * 0.00462963), (int)(windowHeight * 0.002604167), (int)(windowHeight * 0.00462963), (int)(windowHeight * 0.002604167));

        JLabel sizeLabel = new JLabel("Tamaño del tablero:");
        sizeLabel.setFont(new Font("Arial", Font.BOLD, (int)(windowHeight * 0.013888889)));
        JTextField sizeTextField = new JTextField();
        sizeTextField.setColumns((int)(windowWidth * 0.005208333));

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
        especialesLabel.setFont(new Font("Arial", Font.BOLD, (int)(windowHeight * 0.013888889)));
        JTextField especialesTextField = new JTextField();
        especialesTextField.setColumns((int)(windowWidth * 0.005208333));
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
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "No estás ingresando un número válido.");
                        Log.record(e);
                        especialesTextField.setText("");
                    }
                });
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

        gbc.gridx = 1;
        panelSize.add(sizeTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelSize.add(especialesLabel, gbc);

        gbc.gridx = 1;
        panelSize.add(especialesTextField, gbc);

        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets((int)(windowHeight * 0.002777778), (int)(windowWidth * 0.0015625), (int)(windowHeight * 0.002777778), (int)(windowWidth * 0.0015625));

        gbc.gridy = 3;
        JButton modosJuego = new JButton("Iniciar Juego");
        ImageIcon ima = new ImageIcon("GomokuImages/flecha.png");
        ImageIcon res = new ImageIcon(ima.getImage().getScaledInstance((int)(windowWidth * 0.006481481), (int)(windowHeight * 0.0069444444), Image.SCALE_SMOOTH));
        // Crear un botón con la imagen redimensionada
        JButton devolverse = new JButton(res);
        modosJuego.addActionListener(e -> {
            try {
                if(size < 10){
                    JOptionPane.showMessageDialog(null, "El valor minimo para el tablero es de 10.");
                }else if(porcentajeEspeciales < 0 || porcentajeEspeciales > 100){
                    JOptionPane.showMessageDialog(null, "El porcentaje de especiales es un numero entre 0 y 100.");
                }else{
                    if(nombreJ1.isEmpty()){
                        nombreJ1 = "Jugador1";
                    } else if (nombreJ2.isEmpty()) {
                        nombreJ2 = "Jugador2";
                    }
                    mostrarDialogo();
                }
            } catch (Exception ex) {
                Log.record(ex);
                throw new RuntimeException(ex);
            }
        });
        devolverse.addActionListener(e -> optionNewInicio());
        panelSize.add(modosJuego, gbc);

        gbc.gridx = 1; // Nueva posición para el botón
        gbc.gridy = 5; // Mismo nivel que el JComboBox
        gbc.gridwidth = 1; // Restaurar gridwidth a 1
        gbc.anchor = GridBagConstraints.EAST; // Alineación a la derecha

        // Agregar botón de devolverse a la derecha del botón de inicio
        panelSize.add(devolverse, gbc);

        return panelSize;
    }
    /**
     * Muestra un diálogo para seleccionar el modo de juego y maneja las opciones seleccionadas.
     * @throws Exception Excepción en caso de algún problema.
     */
    private void mostrarDialogo() throws Exception {
        Object[] opciones = {"Normal", "Piedras Limitadas", "Tiempo Limitado"};
        int seleccion = JOptionPane.showOptionDialog(
                null,
                "Selecciona el modo de juego:",
                "Modo de Juego",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);
        // Aquí puedes manejar la opción seleccionada
        if (seleccion == JOptionPane.CLOSED_OPTION) {
        }else if (opciones[seleccion] == "Normal"){
            gameMode = "Normal";
            empezarJuego();
        } else if (opciones[seleccion] == "Piedras Limitadas") {
            String cantidadPiedras = JOptionPane.showInputDialog("Ingresa la cantidad de piedras de limite:");
            stoneLimit = size*size;
            gameMode = "LimitedStones";
            try{
                stoneLimit = Integer.parseInt(cantidadPiedras);
                porcentajeEspeciales = 0;
                if(stoneLimit <= 0){
                    JOptionPane.showMessageDialog(null, "Ingresa un numero positivo");
                    stoneLimit = size*size;
                }else{
                    empezarJuego();
                }
            }catch (Exception e){
                Log.record(e);
                JOptionPane.showMessageDialog(null, "No ingresaste un numero válido");
            }
        } else {
            String tiempoLimite = JOptionPane.showInputDialog("Ingresa la cantidad de segundos que deseas jugar:");
            try{
                gameMode = "QuickTime";
                timeLimit = Integer.parseInt(tiempoLimite);
                if(timeLimit <= 0){
                    JOptionPane.showMessageDialog(null, "Ingresa un tiempo positivo");
                    timeLimit = 10000000;
                }else{
                    empezarJuego();
                }
            }catch (Exception e){
                Log.record(e);
                JOptionPane.showMessageDialog(null, "No ingresaste un numero válido");
            }
        }
    }
    /**
     * Inicia el juego con la configuración seleccionada.
     * @throws Exception Excepción en caso de algún problema.
     */
    private void empezarJuego() throws Exception {
        updateRemainingLabels();
        optionNew();
        timerGUI.start();
        cardLayout.show(cardPanel, "game");
    }
    /**
     * Crea y retorna el panel de juego.
     * @return Panel de juego.
     * @throws Exception Excepción en caso de algún problema.
     */
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
        gamePanel.add(Box.createHorizontalStrut((int)(windowWidth * 0.010416667)));
        return gamePanel;
    }
    /**
     * Prepara los elementos del menú, incluyendo la barra de menú, opciones de archivo y configuraciones.
     */
    private void prepareElementsMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        JMenuItem nuevoMenuItem = new JMenuItem("INICIO");

        JSeparator separator1 = new JSeparator();

        JMenuItem abrirMenuItem = new JMenuItem("ABRIR");
        JMenuItem salvarMenuItem = new JMenuItem("GUARDAR COMO");

        JSeparator separator2 = new JSeparator();

        JMenuItem configuracionesMenuItem = new JMenuItem("CONFIGURACIONES");

        JSeparator separator3 = new JSeparator();

        JMenuItem salirMenuItem = new JMenuItem("SALIR");

        menu.add(nuevoMenuItem);
        menu.add(separator1);
        menu.add(abrirMenuItem);
        menu.add(salvarMenuItem);
        menu.add(separator2); // Separador
        menu.add(configuracionesMenuItem);
        menu.add(separator3);
        menu.add(salirMenuItem);
        menuBar.add(menu);

        JMenu menuConfiguraciones = new JMenu("Volumen");
        JCheckBoxMenuItem menuItemMute = new JCheckBoxMenuItem("Mute");

        menuItemMute.addActionListener(e -> {
            volumen = !menuItemMute.isSelected(); // Mute
        });
        menuConfiguraciones.add(menuItemMute);

        // Agregar menús a la barra de menú
        menuBar.add(menu);
        menuBar.add(menuConfiguraciones);
        setJMenuBar(menuBar);
    }
    /**
     * Prepara los elementos del tablero, creando el tablero de juego y las piedras asociadas.
     * @throws Exception Excepción en caso de algún problema.
     */
    private void prepareElementsBoard() throws Exception {
        if (boardPanel == null) {
            gomoku = new Gomoku(size, stoneLimit, timeLimit, porcentajeEspeciales, gameMode);
            // Si el tablero aún no se ha creado, crearlo y agregarlo al mainPanel
            gomoku.setPlayers(Human.class, machineType);
            Cell[][] cellMatrix = gomoku.board();
            boardPanel = new JPanel(new GridLayout(cellMatrix.length, cellMatrix[0].length));
            Piedra[][] piedras = new Piedra[cellMatrix.length][cellMatrix[0].length];

            for (int i = 0; i < cellMatrix.length; i++) {
                for (int j = 0; j < cellMatrix[0].length; j++) {
                    Piedra piedra = new Piedra(false);
                    piedras[i][j] = piedra;
                   // piedra.setBackType(chooseColorOfBackgroundPiedra(cellMatrix[i][j]));
                    piedra.addMouseListener(new CellClickListener(i, j));
                    boardPanel.add(piedra);
                }
            }
            gamePanel.add(boardPanel, BorderLayout.CENTER);
        } else {
            refresh();
        }
    }

    /**
     * Agrega el panel superior que muestra el tiempo transcurrido.
     */
    private void addTopPanel() {

        JPanel topPanel = new JPanel(new BorderLayout());


        tiempoLabel = new JLabel("EL TIEMPO TRANSCURRIDO ES: ", SwingConstants.CENTER);
        topPanel.add(tiempoLabel, BorderLayout.CENTER);

        // Agregar el topPanel al gamePanel
        gamePanel.add(topPanel, BorderLayout.NORTH);
    }
    /**
     * Agrega el panel izquierdo que contiene información sobre el jugador 1, como nombre, imagen, tipo de piedra, y puntaje.
     */
    private void addLeftPanel() {
        ImagePanel fondo = new ImagePanel("GomokuImages/playerPanels.png");

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        JPanel datosJ1 = new JPanel(new GridBagLayout());
        datosJ1.setOpaque(false);
        GridBagConstraints gbcDatosJ1 = new GridBagConstraints();

        // Cargar la imagen
        ImageIcon imagen = new ImageIcon("GomokuImages/player1.png");
        ImageIcon resized = new ImageIcon(imagen.getImage().getScaledInstance((int)(windowWidth * 0.078125), (int)(windowHeight * 0.069444444), Image.SCALE_SMOOTH));

        // Crear un JLabel con la imagen y establecer un borde
        JLabel imageLabel = new JLabel(resized, JLabel.CENTER);

        // Crear un JLabel para el nombre del jugador
        name1 = new JLabel(nombreJ1);
        // Crear un JLabel para el texto "Juegas con las fichas:"
        JLabel texto = new JLabel("Juegas con las fichas:");

        // Crear un JPanel para la piedra
        piedraJ1 = new JPanel(new BorderLayout());
        piedra1 = new Piedra(false);
        piedraJ1.setPreferredSize(new Dimension((int)(windowWidth * 0.078125), (int)(windowHeight * 0.138888889)));
        piedra1.setPiedraColor(colorJ1);
        piedra1.setType('n');
        piedra1.makeVisible();
        piedraJ1.add(texto, BorderLayout.NORTH);
        piedraJ1.add(piedra1, BorderLayout.CENTER);
        piedraJ1.setBorder(BorderFactory.createLineBorder(Color.BLUE, (int)(windowWidth * 0.001041667)));

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
        gbc.insets = new Insets((int)(windowHeight * 0.013), (int)(windowWidth * 0.008854167), (int)(windowHeight * 0.013), (int)(windowWidth * 0.008854167)); // Márgenes entre los componentes

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

        Dimension buttonSize = new Dimension((int)(windowWidth * 0.0625), (int)(windowHeight * 0.037037037)); // Tamaño deseado para los botones
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

    /**
     * Agrega el panel izquierdo que contiene información sobre el jugador 2, como nombre, imagen, tipo de piedra, y puntaje.
     */
    private void addRightPanel() {
        ImagePanel fondo = new ImagePanel("GomokuImages/playerPanels.png");
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);

        // Cargar la imagen
        JPanel datosJ2 = new JPanel(new GridBagLayout());
        datosJ2.setOpaque(false);
        GridBagConstraints gbcDatosJ2 = new GridBagConstraints();

        // Cargar la imagen
        ImageIcon imagen = new ImageIcon("GomokuImages/player2.png");
        ImageIcon resized = new ImageIcon(imagen.getImage().getScaledInstance((int)(windowWidth * 0.078125), (int)(windowHeight * 0.069444444), Image.SCALE_SMOOTH));

        // Crear un JLabel con la imagen y establecer un borde
        JLabel imageLabel = new JLabel(resized, JLabel.CENTER);

        // Crear un JLabel para el nombre del jugador
        name2 = new JLabel(nombreJ2);
        // Crear un JLabel para el texto "Juegas con las fichas:"
        JLabel texto = new JLabel("Juegas con las fichas:");

        // Crear un JPanel para la piedra
        piedraJ2 = new JPanel(new BorderLayout());
        piedra2 = new Piedra(false);
        piedraJ2.setPreferredSize(new Dimension((int)(windowWidth * 0.078125), (int)(windowHeight * 0.138888889)));
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
        gbc.insets = new Insets((int)(windowHeight * 0.013), (int)(windowWidth * 0.008854167), (int)(windowHeight * 0.013), (int)(windowWidth * 0.008854167)); // Márgenes entre los componentes

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
                piedra2.setLife(7);
                piedra2.setType('t');
                piedra2.repaint();
            }
        });
        Dimension buttonSize = new Dimension((int)(windowWidth * 0.0625), (int)(windowHeight * 0.037037037)); // Tamaño deseado para los botones
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
    /**
     * Agrega el panel inferior que contiene botones para finalizar el juego y reiniciar.
     */
    private void addBottomPanel() {
        // Crear un panel para la parte inferior
        JPanel bottomPanel = new JPanel();

        // Agregar los botones "Finalizar" y "Resetear"
        JButton guardarFichaButton = new JButton("FINALIZAR JUEGO");
        JButton resetButton = new JButton("RESET");

        // Agregar ActionListener a los botones según sea necesario
        guardarFichaButton.addActionListener(e -> {
            try {
                int respuesta = confirmarEleccion("¿Estás seguro de que deseas finalizar el juego?");

                // Si el usuario confirma, ejecutar la acción
                if (respuesta == JOptionPane.YES_OPTION) {
                    optionNew();
                    optionNewInicio();
                    reiniciarDefault();
                    cardLayout.show(cardPanel, "initial");
                }
            } catch (Exception ex) {
                Log.record(ex);
                throw new RuntimeException(ex);
            }
        });
        resetButton.addActionListener(e -> {
            try {
                int respuesta = confirmarEleccion("¿Estás seguro de que deseas reiniciar el juego?");
                // Si el usuario confirma, ejecutar la acción
                if (respuesta == JOptionPane.YES_OPTION) {
                    CellClickListener.enableClick();
                    optionNew();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Agregar los botones al panel inferior
        bottomPanel.add(guardarFichaButton);
        bottomPanel.add(resetButton);

        // Agregar el panel inferior a la parte inferior de la pantalla principal
        gamePanel.add(bottomPanel, BorderLayout.SOUTH);
    }
    /**
     * Muestra un cuadro de diálogo de confirmación con el mensaje especificado.
     * @param message El mensaje a mostrar en el cuadro de diálogo.
     * @return La opción seleccionada por el usuario (JOptionPane.YES_OPTION o JOptionPane.NO_OPTION).
     */
    private int confirmarEleccion(String message) {
        // Mostrar un cuadro de diálogo de confirmación
        return JOptionPane.showConfirmDialog(
                this,
                message,
                "Confirmación",
                JOptionPane.YES_NO_OPTION);
    }
    /**
     * Reinicia el estado del tablero y lo actualiza visualmente.
     */
    private void reset() {
        boardPanel.getComponents();
        updateBorders();
        updateRemainingLabels();
        boardPanel.revalidate(); // Asegurar que el panel se redibuje correctamente
        boardPanel.repaint();
    }
    /**
     * Actualiza la apariencia del tablero y las piedras en función del estado actual del juego.
     */
    private void refresh() {
        turn = gomoku.getBoard().getTurn();
        Cell[][] cellMatrix = gomoku.board();
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
        updateBorders();
        updateRemainingLabels();
        boardPanel.revalidate(); // Ensure that the panel is redrawn correctly
        boardPanel.repaint();
    }

    /**
     * Prepara las acciones asociadas al cierre de la ventana principal.
     */
    private void prepareActions() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        prepareActionsMenu();
    }
    /**
     * Prepara las acciones asociadas al menú de la aplicación.
     */
    private void prepareActionsMenu(){
        getJMenuBar().getMenu(0).getItem(0).addActionListener(e -> {
            try {
                reiniciarDefault();
                optionNewInicio();
            } catch (Exception ex) {
                Log.record(ex);
                JOptionPane.showMessageDialog(null, "Error inesperado");
            }
        });
        getJMenuBar().getMenu(0).getItem(2).addActionListener(e -> optionOpen());
        getJMenuBar().getMenu(0).getItem(3).addActionListener(e -> optionSave());
        getJMenuBar().getMenu(0).getItem(5).addActionListener(e -> {


            // Establecer el tamaño de la ventana
            setSize(new Dimension((int)(windowWidth * 0.25), (int)(windowHeight * 0.6)));
            setResizable(false);
            setLocationRelativeTo(null);
            cardLayout.show(cardPanel, "config");
        });
        getJMenuBar().getMenu(0).getItem(7).addActionListener(e -> optionExit());
    }
    /**
     * Realiza las acciones necesarias para la opción de inicio.
     */
    private void optionNewInicio(){

        // Establecer el tamaño de la ventana
        setSize(new Dimension((int)(windowWidth * 0.5), (int)(windowHeight * 0.5)));
        setResizable(false);
        setLocationRelativeTo(null);
        cardLayout.show(cardPanel, "initial");
    }
    /**
     * Realiza las acciones necesarias para la opción de inicio de un nuevo juego.
     * @throws Exception Si ocurre un error durante la preparación del nuevo juego.
     */
    private void optionNew() throws Exception {
        // Eliminar el componente boardPanel del gamePanel
        gamePanel.remove(boardPanel);

        // Liberar recursos relacionados con el tablero (piedras, etc.)
        boardPanel = null;
        turn = true;
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
        setSize((int)(windowWidth * 0.9), (int)(windowHeight * 0.9));
        setResizable(false);
        setLocationRelativeTo(null);
    }
    /**
     * Realiza las acciones necesarias para la opción de abrir un juego guardado.
     */
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
                gomoku.getBoard().starTimer();
                cardLayout.show(cardPanel, "game");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
            Log.record(e);
        }
    }
    /**
     * Realiza las acciones necesarias para la opción de guardar la partida.
     */
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
            Log.record(e);
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    /**
     * Realiza las acciones necesarias para la opción de salir del juego.
     */
    private void optionExit(){
        System.exit(0);
    }
    /**
     * Reproduce un sonido desde el archivo especificado.
     *
     * @param filePath La ruta del archivo de sonido.
     */
    private void reproducirSonido(String filePath) {
        try {
            if(volumen){
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            }
        } catch (Exception e) {
            Log.record(e);
        }
    }
    /**
     * Coloca una ficha en la posición dada en el tablero del juego.
     *
     * @param row La fila en la que se coloca la ficha.
     * @param col La columna en la que se coloca la ficha.
     * @throws Exception Si ocurre un error durante la colocación de la ficha.
     */
    private void ponerFicha(int row, int col) throws Exception {
        Stone selectedStone;
        Player currentPlayer = turn ? gomoku.getPlayer1() : gomoku.getPlayer2();
        Stone[] selectedStoneArray = new Stone[]{turn ? selectedStoneJ1 : selectedStoneJ2};
        try {
            if (!currentPlayer.getExtraStones().isEmpty()) {
                selectedStone = gomoku.getBoard().handleExtraStones(currentPlayer, selectedStoneArray);
                if(turn){
                    turn = gomoku.getBoard().isFlag();
                }else{
                    turn = !(gomoku.getBoard().isFlag());
                }
            } else {
                selectedStone =  gomoku.getBoard().handleRemainingStones(currentPlayer, selectedStoneArray);
                turn = !turn;
            }
            if(gomoku.play(row, col, selectedStone)){
                reproducirSonido("GomokuSounds/victory.wav");
                timerGUI.stop();
                winnerOption();
                showOptionDialog();
            }
            gomoku.getBoard().setTurn(turn);
            Cell[][] cellMatrix = gomoku.board();
            Component[] components = boardPanel.getComponents();
            Piedra piedra = (Piedra) components[row * cellMatrix[0].length + col];
            refresh();
            if (cellMatrix[row][col] instanceof Golden && piedra.isSound() && !(currentPlayer instanceof Machine)) {
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

    /**
     * Maneja las excepciones específicas de Gomoku y muestra mensajes de error o información.
     *
     * @param e La excepción de Gomoku.
     * @throws Exception Si ocurre un error inesperado.
     */
    private void handleException(GomokuException e) throws Exception {
        if (e.getMessage().equals(GomokuException.STONE_OVERLOAP)) {
            turn = !turn;
            JOptionPane.showMessageDialog(null, e.getMessage());
            Log.record(e);
        } else if (e.getMessage().equals(GomokuException.FULL_BOARD)) {
            refresh();
            JOptionPane.showMessageDialog(null, "Empate.");
        }else if(e.getMessage().equals(GomokuException.NO_STONE_FOUND)){
            turn = !turn;
            JOptionPane.showMessageDialog(null, "No te quedan mas piedras de ese tipo, elige otra.");
        }else if(e.getMessage().equals(GomokuException.DRAW)) {
            refresh();
            CellClickListener.disableClick();
            JOptionPane.showMessageDialog(null, "Empate.");
        }
        else {
            throw new Exception(e.getMessage());
        }
    }
    /**
     * Elige un carácter que representa el tipo de piedra (normal, pesada, temporal).
     *
     * @param stone La piedra para la cual se selecciona el carácter.
     * @return El carácter correspondiente al tipo de piedra.
     */
    public char chooseCharForAStone(Stone stone){
        if(stone instanceof Heavy){
            return 'h';
        }else if(stone instanceof Temporary){
            return 't';
        }else{
            return 'n';
        }
    }
    /**
     * Elige un carácter que representa el tipo de fondo de la piedra (mina, teleport, dorada, común).
     *
     * @param cell La celda para la cual se selecciona el carácter.
     * @return El carácter correspondiente al tipo de fondo de la piedra.
     */
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
    /**
     * Reproduce un sonido según el tipo de celda en la posición dada en el tablero.
     *
     * @param row La fila de la celda.
     * @param column La columna de la celda.
     */
    private void reproducirSonidoCasillas(int row, int column){
        Component[] components = boardPanel.getComponents();
        Cell[][] cellMatrix = gomoku.board();
        Piedra piedra = (Piedra) components[row * cellMatrix[0].length + column];
        if(volumen && piedra.isSound()){
            if(gomoku.board()[row][column] instanceof Teleport){
                reproducirSonido("GomokuSounds/teleport1.wav");
                piedra.setSound(false);
            }else if(gomoku.board()[row][column] instanceof Mine){
                reproducirSonido("GomokuSounds/mine.wav");
                piedra.setSound(false);
            }else if(gomoku.board()[row][column] instanceof Golden){
                reproducirSonido("GomokuSounds/golden.wav");
                piedra.setSound(false);
            }
        }
    }
    /**
     * Actualiza los bordes y etiquetas que muestran la información de las piedras restantes y la puntuación.
     */
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
    /**
     * Actualiza las etiquetas que muestran la información de las piedras restantes y la puntuación.
     */
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
    }
    /**
     * Inicia un temporizador que actualiza el tiempoLabel cada segundo y realiza acciones
     * automáticas si el jugador actual es una máquina en el modo de juego QuickTime.
     */
    private void startTimer() {
        // Actualiza el tiempoLabel cada segundo
        timerGUI = new Timer(1000, e -> {
            // Actualiza el tiempoLabel cada segundo
            try {
                tiempoLabel.setText(gomoku.getBoard().obtenerTiempoActual());
            } catch (GomokuException ex) {
                timerGUI.stop();
                JOptionPane.showMessageDialog(null, ex.getMessage());
                winnerOption();
                showOptionDialog();
            }
            if(!turn && gomoku.getPlayer2() instanceof Machine){
                try {
                    if(gomoku.getPlayer2().contieneStone(Stone.class)){
                        selectedStoneJ2 = new Stone(colorJ2);
                    }else if(gomoku.getPlayer2().contieneStone(Heavy.class)){
                        selectedStoneJ2 = new Heavy(colorJ2);
                    }else {
                        selectedStoneJ2 = new Temporary(colorJ2);
                    }
                    if(gameMode.equals("QuickTime")){
                        try
                        {
                            Thread.sleep(1000);
                            ponerFicha(0,0);
                            reproducirSonido("GomokuSounds/ponerFicha.wav");
                        }
                        catch (InterruptedException ie)
                        {
                            Log.record(ie);
                        }
                    }else{
                        ponerFicha(0,0);
                        reproducirSonido("GomokuSounds/ponerFicha.wav");
                    }
                } catch (Exception ex) {
                    Log.record(ex);
                    throw new RuntimeException(ex);
                }
            }
        });
        timerGUI.start();
    }
    /**
     * Muestra un cuadro de diálogo anunciando al ganador y actualiza la interfaz gráfica.
     */
    private void winnerOption(){
        if (!gomoku.getBoard().getTurn()) {
            refresh();
            JOptionPane.showMessageDialog(null, "GANAASTEEEEEEEE. " + gomoku.getPlayer1().getName());

        } else {
            refresh();
            JOptionPane.showMessageDialog(null, "GANAASTEEEEEEEE. " + gomoku.getPlayer2().getName());

        }
    }
    /**
     * Restaura los valores predeterminados del juego.
     */
    private void reiniciarDefault(){
        CellClickListener.enableClick();
        porcentajeEspeciales = 50;
        gameMode = "Normal";
        timeLimit = -1;
        stoneLimit = size*size;
    }
    /**
     * Muestra un cuadro de diálogo con las opciones de "Nuevo Juego" y "Cerrar" al finalizar la partida.
     */
    private void showOptionDialog() {
        String[] options = {"Nuevo Juego", "Cerrar"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "¿Qué deseas hacer?",
                "Opciones",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]); // Índice de la opción predeterminada

        // Procesar la elección del usuario
        switch (choice) {
            case 0:
                reiniciarDefault();
                optionNewInicio();
                break;
            case 1:
                dispose();
                break;
            case JOptionPane.CLOSED_OPTION:
                CellClickListener.disableClick();
                break;
        }
    }

    /**
     * Clase interna que representa un listener para clics en las celdas del tablero.
     */
    private class CellClickListener extends MouseAdapter {
        private final int row;
        private final int col;
        /**
         * Constructor que inicializa las coordenadas de la celda.
         *
         * @param row Fila de la celda.
         * @param col Columna de la celda.
         */
        public CellClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }
        /**
         * Maneja el evento de clic del mouse en la celda.
         *
         * @param e Evento del mouse.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                if (clickEnabled) {
                    // Llama al método para colocar una ficha en la celda y reproduce sonidos asociados.
                    ponerFicha(row, col);
                    reproducirSonido("GomokuSounds/ponerFicha.wav");
                    reproducirSonidoCasillas(row, col);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                Log.record(ex);
            }
        }
        /**
         * Desactiva los clics en las celdas del tablero.
         */
        private static void disableClick() {
            clickEnabled = false;
        }

        /**
         * Activa los clics en las celdas del tablero.
         */
        private static void enableClick() {
            clickEnabled = true;
        }

    }
    /**
     * Clase que representa una piedra en el tablero del juego Gomoku.
     * Extiende JPanel para ser renderizada en la interfaz gráfica.
     */
    public static class Piedra extends JPanel {
        private Color piedraColor;
        private Color backgroundColor;
        private char type;
        private char backType;
        private int life = 6;
        private boolean isVisible;
        private boolean sound = true;
        /**
         * Constructor de la clase Piedra.
         *
         * @param isVisible Indica si la piedra es visible o no.
         */
        public Piedra(boolean isVisible) {
            this.piedraColor = Color.WHITE; // Color predeterminado
            this.backgroundColor = new Color(222, 184, 135, 80); // Color predeterminado
            this.isVisible = isVisible;
        }
        /**
         * Establece el tipo de piedra.
         *
         * @param type Tipo de piedra ('n' para normal, 'h' para pesada, 't' para temporal).
         */
        public void setType(char type){
            this.type = type;
        }
        /**
         * Establece el tipo de fondo de la piedra.
         *
         * @param backType Tipo de fondo ('m' para mina, 'p' para teletransportador, 'g' para dorada).
         */
        public void setBackType(char backType){this.backType = backType;}
        /**
         * Establece el color de la piedra.
         *
         * @param piedraColor Color de la piedra.
         */
        public void setPiedraColor(Color piedraColor) {
            this.piedraColor = piedraColor;
        }
        /**
         * Establece si se reproduce sonido al interactuar con la piedra.
         *
         * @param sound true si se reproduce sonido, false de lo contrario.
         */

        public void setSound(boolean sound) {
            this.sound = sound;
        }
        /**
         * Establece la vida de la piedra temporal.
         *
         * @param life Vida de la piedra temporal.
         */
        public void setLife(int life) {
            this.life = life;
        }

        /**
         * Hace visible la piedra.
         */
        public void makeVisible() {
            this.isVisible = true;
        }
        /**
         * Hace invisible la piedra.
         */

        public void makeInvisible() {
            this.isVisible = false;
        }
        /**
         * Verifica si la piedra reproduce sonido al interactuar.
         *
         * @return true si reproduce sonido, false de lo contrario.
         */

        public boolean isSound() {
            return sound;
        }
        /**
         * Método de pintado que define la apariencia de la piedra en el componente gráfico.
         *
         * @param g Objeto Graphics para pintar.
         */
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
        /**
         * Dibuja una estrella en el centro de la piedra pesada.
         *
         * @param g Objeto Graphics para dibujar.
         * @param x Coordenada x del centro.
         * @param y Coordenada y del centro.
         */
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
        /**
         * Establece el color de fondo de la piedra según el tipo de fondo.
         *
         * @param g Objeto Graphics para dibujar.
         */
        private void setBackgroundColor(Graphics g) {
            switch (backType) {
                case 'm':
                    g.setColor(new Color(255, 0, 0, 80));
                    this.backgroundColor = new Color(255, 0, 0, 80);
                    break;
                case 'p':
                    g.setColor(new Color(0, 0, 255, 80)); // Cambia a tu color de fondo deseado
                    this.backgroundColor = new Color(0, 0, 255, 80);
                    break;
                case 'g':
                    g.setColor(new Color(255, 215, 0, 80)); // Cambia a tu color de fondo deseado
                    this.backgroundColor = new Color(255, 215, 0, 80);
                    break;
                default:
                    g.setColor(backgroundColor);
                    break;
            }
        }
    }
    /**
     * Método principal que inicia la interfaz gráfica del juego Gomoku.
     * Se ejecuta en el hilo de eventos de Swing utilizando SwingUtilities.invokeLater().
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Crear una instancia de la interfaz gráfica GomokuGUI
                GomokuGUI gui = new GomokuGUI();
                // Iniciar el temporizador del juego
                gui.startTimer();
                // Hacer visible la interfaz gráfica
                gui.setVisible(true);
            } catch (Exception e) {
                // Manejar excepciones mostrando un mensaje de error y registrando la excepción
                JOptionPane.showMessageDialog(null, "Paso algo inesperado");
                Log.record(e);
            }
        });
    }
}

