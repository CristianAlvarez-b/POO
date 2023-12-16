package presentation;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
    /**
     * Clase que representa un panel con una imagen de fondo en una aplicación de interfaz gráfica.
     */
    public class ImagePanel extends JPanel {
        private Image backgroundImage;

        /**
         * Constructor de la clase ImagePanel.
         *
         * @param imagePath Ruta de la imagen de fondo.
         */
        public ImagePanel(String imagePath) {
            backgroundImage = new ImageIcon(imagePath).getImage();
        }

        /**
         * Pinta la imagen de fondo en el panel.
         *
         * @param g Objeto Graphics para dibujar en el componente.
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

