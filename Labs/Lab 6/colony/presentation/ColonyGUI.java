package presentation;
import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ColonyGUI extends JFrame{  
    public static final int SIDE=21;
    public static final int SIZE=31;

    private JButton buttonTicTac;
    private JPanel panelControl;
    private PhotoColony photo;
    private Colony colony;

    private ColonyGUI() {
        colony=new Colony();
        prepareElements();
        prepareActions();
    }
    
    private void prepareElements() {
        setTitle("Colony");
        photo=new PhotoColony(this);
        buttonTicTac=new JButton("Tic-tac");
        setLayout(new BorderLayout());
        add(photo,BorderLayout.NORTH);
        add(buttonTicTac,BorderLayout.SOUTH);
        setSize(new Dimension(SIDE*SIZE,SIDE*SIZE+50)); 
        setResizable(false);
        photo.repaint();
        prepareElementsMenu();
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
    private void prepareActionsMenu(){
        getJMenuBar().getMenu(0).getItem(0).addActionListener(e -> {
            optionNew();
        });
        getJMenuBar().getMenu(0).getItem(2).addActionListener(e -> {
            optionOpen();
        });
        getJMenuBar().getMenu(0).getItem(3).addActionListener(e -> {
            optionSave();
        });
        getJMenuBar().getMenu(0).getItem(5).addActionListener(e -> {
            optionImport();
        });
        getJMenuBar().getMenu(0).getItem(6).addActionListener(e -> {
            optionExport();
        });
        getJMenuBar().getMenu(0).getItem(8).addActionListener(e -> {
            optionExit();
        });
    }
    private void optionOpen(){
        try{
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == 0) {
                File selectedFile = fileChooser.getSelectedFile();
                this.colony = Colony.open(selectedFile);
                photo.repaint();
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(this,ColonyException.ERROR_GENERAL);
        }
    }
    private void optionSave(){
        try{
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);
            if (result == 0) {
                File selectedFile = fileChooser.getSelectedFile();
                colony.save(selectedFile);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(this,ColonyException.ERROR_GENERAL);
        }
    }
    private void optionImport(){
        try{
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == 0) {
                File selectedFile = fileChooser.getSelectedFile();
                this.colony = Colony.importt(selectedFile);
                photo.repaint();
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }
    private void optionExport(){
        try{
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);
            if (result == 0) {
                File selectedFile = fileChooser.getSelectedFile();
                colony.export(selectedFile);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }
    private void optionNew(){
        colony = null;
        colony = new Colony();
        photo.repaint();
    }
    private void optionExit(){
        System.exit(0);
    }
    private void prepareActions(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);       
        buttonTicTac.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    buttonTicTacAction();
                }
            });
        prepareActionsMenu();
    }

    private void buttonTicTacAction() {
        colony.ticTac();
        photo.repaint();
    }

    public Colony getColony(){
        return colony;
    }
    
    public static void main(String[] args) {
        ColonyGUI cg=new ColonyGUI();
        cg.setVisible(true);
    }  
}

class PhotoColony extends JPanel{
    private ColonyGUI gui;

    public PhotoColony(ColonyGUI gui) {
        this.gui=gui;
        setBackground(Color.white);
        setPreferredSize(new Dimension(gui.SIDE*gui.SIZE, gui.SIDE*gui.SIZE));         
    }


    public void paintComponent(Graphics g){
        Colony Colony=gui.getColony();
        super.paintComponent(g);
         
        for (int f=0;f<=Colony.getLength();f++){
            g.drawLine(f*gui.SIDE,0,f*gui.SIDE,Colony.getLength()*gui.SIDE);
        }
        for (int c=0;c<=Colony.getLength();c++){
            g.drawLine(0,c*gui.SIDE,Colony.getLength()*gui.SIDE,c*gui.SIDE);
        }       
        for (int f=0;f<Colony.getLength();f++){
            for(int c=0;c<Colony.getLength();c++){
                if (Colony.getEntity(f,c)!=null){
                    g.setColor(Colony.getEntity(f,c).getColor());
                    if(Colony.getEntity(f,c).shape()==Entity.FLOWER){
                        if(Colony.getEntity(f,c).is()){
                            drawFlower(g, gui.SIDE * c + gui.SIDE / 2, gui.SIDE * f + gui.SIDE / 2, Colony.getEntity(f,c).getColor());
                        }else{
                            g.setColor(Colony.getEntity(f, c).getColor());
                            // Dibuja la flor como desees
                            // Por ejemplo, aquí se dibuja un círculo rojo:
                            g.fillOval(gui.SIDE * c + 5, gui.SIDE * f + 5, gui.SIDE - 10, gui.SIDE - 10);
                        }
                    }else if (Colony.getEntity(f,c).shape()==Entity.INSECT){
                        if (Colony.getEntity(f,c) instanceof Bee) {
                            int x = gui.SIDE * c + 1;
                            int y = gui.SIDE * f + 5;
                            int size = gui.SIDE - 12;
                            drawBee(g, x, y, size);
                        }
                        g.drawOval(gui.SIDE*c+1,gui.SIDE*f+5,gui.SIDE-12,gui.SIDE-12);
                        g.drawOval(gui.SIDE*c+gui.SIDE-15,gui.SIDE*f+gui.SIDE-10,gui.SIDE-5,gui.SIDE-12);
                        if (Colony.getEntity(f,c).is()){
                            g.fillOval(gui.SIDE*c+1,gui.SIDE*f+5,gui.SIDE-12,gui.SIDE-12);
                            g.fillOval(gui.SIDE*c+gui.SIDE-15,gui.SIDE*f+gui.SIDE-10,gui.SIDE-5,gui.SIDE-12);
                        }    
                    }else if (Colony.getEntity(f,c).shape()==Entity.SQUARE){  
                        g.drawRoundRect(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2,2,2); 
                        if (Colony.getEntity(f,c).is()){
                            g.fillRoundRect(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2,2,2);
                        }
                    
                    }else{
                        g.drawOval(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2);
                        if (Colony.getEntity(f,c).is()){
                            g.fillOval(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2);
                        } 
                    }
                }
            }
        }
    }
    private void drawFlower(Graphics g, int x, int y, Color color) {
        // Dibuja una flor personalizada
        g.setColor(color); // Establece el color de los pétalos de la flor
        int petalSize = gui.SIDE / 3; // Tamaño de los pétalos
        int centerX = x - petalSize / 2;
        int centerY = y - petalSize / 2;
        
        // Dibuja los pétalos de la flor
        for (int i = 0; i < 6; i++) {
            int angle = i * 60;
            int xOffset = (int) (Math.cos(Math.toRadians(angle)) * petalSize);
            int yOffset = (int) (Math.sin(Math.toRadians(angle)) * petalSize);
            g.fillOval(centerX + xOffset, centerY + yOffset, petalSize, petalSize);
        }
        
        g.setColor(color); // Establece el color del centro de la flor
        int centerSize = petalSize / 2;
        g.fillOval(x - centerSize, y - centerSize, centerSize * 2, centerSize * 2); // Dibuja el centro de la flor
        
        g.setColor(color); // Establece el color del tallo
        g.drawLine(x, y, x, y + gui.SIDE / 3); // Dibuja el tallo de la flor
    }
    private void drawBee(Graphics g, int x, int y, int size) {
        // Cuerpo de la abeja
        g.setColor(Color.yellow);
        g.fillOval(x, y, size, size);
    
        // Alas de la abeja
        g.setColor(Color.black);
        g.drawLine(x + size / 4, y + size / 4, x - size / 4, y - size / 4);
        g.drawLine(x + 3 * size / 4, y + size / 4, x + 5 * size / 4, y - size / 4);
    
        // Cabeza de la abeja
        g.fillOval(x + size / 3, y - size / 3, size / 3, size / 3);
    
    
        // Antenas
        g.setColor(Color.yellow);
        g.drawLine(x + size / 3 + size / 12, y - size / 6, x + size / 4, y - size / 4);
        g.drawLine(x + size / 2, y - size / 6, x + size / 2, y - size / 4);
    
    }

}