package shapes;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Canvas is a class to allow for simple graphical drawing on a canvas.
 * This is a modification of the general purpose Canvas, specially made for
 * the BlueJ "shapes" example. 
 *
 * @author: Bruce Quig
 * @author: Michael Kolling (mik)
 *
 * @version: 1.6 (shapes)
 */
public class Canvas{
    // Note: The implementation of this class (specifically the handling of
    // shape identity and colors) is slightly more complex than necessary. This
    // is done on purpose to keep the interface and instance fields of the
    // shape objects in this project clean and simple for educational purposes.

    private static Canvas canvasSingleton;

    /**
     * Factory method to get the canvas singleton object.
     */
    public static Canvas getCanvas(){
        if(canvasSingleton == null) {
            canvasSingleton = new Canvas("BlueJ Shapes Demo", 500, 500, 
                                         Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }
    
    public static Canvas getCanvas(int length, int width){
        if(canvasSingleton == null) {
            canvasSingleton = new Canvas("BlueJ Shapes Demo", width, length, 
                                         Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    //  ----- instance part -----

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColour;
    private Image canvasImage;
    private List <Object> objects;
    private HashMap <Object,ShapeDescription> shapes;
    
    /**
     * Create a Canvas.
     * @param title  title to appear in Canvas Frame
     * @param width  the desired width for the canvas
     * @param height  the desired height for the canvas
     * @param bgClour  the desired background colour of the canvas
     */
    private Canvas(String title, int width, int height, Color bgColour){
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColour = bgColour;
        frame.pack();
        objects = new ArrayList <Object>();
        shapes = new HashMap <Object,ShapeDescription>();
    }

    /**
     * Set the canvas visibility and brings canvas to the front of screen
     * when made visible. This method can also be used to bring an already
     * visible canvas to the front of other windows.
     * @param visible  boolean value representing the desired visibility of
     * the canvas (true or false) 
     */
    public void setVisible(boolean visible){
        if(graphic == null) {
            // first time: instantiate the offscreen image and fill it with
            // the background colour
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    /**
     * Draw a given shape onto the canvas.
     * @param  referenceObject  an object to define identity for this shape
     * @param  color            the color of the shape
     * @param  shape            the shape object to be drawn on the canvas
     */
     // Note: this is a slightly backwards way of maintaining the shape
     // objects. It is carefully designed to keep the visible shape interfaces
     // in this project clean and simple for educational purposes.
    public void draw(Object referenceObject,String color, Shape shape, int alpha){
        objects.remove(referenceObject);   // just in case it was already there
        objects.add(referenceObject);      // add at the end
        shapes.put(referenceObject, new ShapeDescription(shape, color, alpha));
        redraw();
    }
 
    /**
     * Erase a given shape's from the screen.
     * @param  referenceObject  the shape object to be erased 
     */
    public void erase(Object referenceObject){
        objects.remove(referenceObject);   // just in case it was already there
        shapes.remove(referenceObject);
        redraw();
    }

    /**
     * Set the foreground colour of the Canvas.
     * @param  newColour   the new colour for the foreground of the Canvas 
     */
    public void setForegroundColor(String colorString, int alpha) {
        switch (colorString) {
            //0
            case "red":
                Color red = new Color(255, 0, 0, alpha);
                graphic.setColor(red);
                break;
            case "blue":
                Color blue = new Color(0, 0, 255, alpha);
                graphic.setColor(blue);
                break;
            case "yellow":
                Color yellow = new Color(255, 255, 0, alpha);
                graphic.setColor(yellow);
                break;
            case "green":
                Color green = new Color(0, 255, 0, alpha);
                graphic.setColor(green);
                break;
            case "magenta":
                Color magenta = new Color(255, 0, 255, alpha);
                graphic.setColor(magenta);
                break;
            case "white":
                Color white = new Color(255, 255, 255, alpha);
                graphic.setColor(white);
                break;
            case "cyan":
                Color cyan = new Color(0, 255, 255, alpha);
                graphic.setColor(cyan);
                break;
            case "darkGray":
                Color darkGray = new Color(169, 169, 169, alpha);
                graphic.setColor(darkGray);
                break;
            case "gray":
                Color gray = new Color(128, 128, 128, alpha);
                graphic.setColor(gray);
                break;
            case "lightGray":
                Color lightGray = new Color(211, 211, 211, alpha);
                graphic.setColor(lightGray);
                break;
            //10
            case "orange":
                Color orange = new Color(255, 165, 0, alpha);
                graphic.setColor(orange);
                break;
            case "pink":
                Color pink = new Color(255, 192, 203, alpha);
                graphic.setColor(pink);
                break;
            case "purple":
                Color purple = new Color(128, 0, 128, alpha);
                graphic.setColor(purple);
                break;
            case "beige":
                Color beige = new Color(245, 245, 220, alpha);
                graphic.setColor(beige);
                break;
            case "seaGreen":
                Color seaGreen = new Color(46, 139, 87, alpha);
                graphic.setColor(seaGreen);
                break;
            case "slateBlue":
                Color slateBlue = new Color(106, 90, 205, alpha);
                graphic.setColor(slateBlue);
                break;
            case "tomato":
                Color tomato = new Color(255, 99, 71, alpha);
                graphic.setColor(tomato);
                break;
            case "turquoise":
                Color turquoise = new Color(64, 224, 208, alpha);
                graphic.setColor(turquoise);
                break;
            case "fuchsia":
                Color fuchsia = new Color(255, 0, 255, alpha);
                graphic.setColor(fuchsia);
                break;
            case "gold":
                Color gold = new Color(255, 215, 0, alpha);
                graphic.setColor(gold);
                break;
            //20
            case "indigo":
                Color indigo = new Color(75, 0, 130, alpha);
                graphic.setColor(indigo);
                break;
            case "ivory":
                Color ivory = new Color(255, 255, 240, alpha);
                graphic.setColor(ivory);
                break;
            case "khaki":
                Color khaki = new Color(240, 230, 140, alpha);
                graphic.setColor(khaki);
                break;
            case "lime":
                Color lime = new Color(0, 255, 0, alpha);
                graphic.setColor(lime);
                break;
            case "limeGreen":
                Color limeGreen = new Color(50, 205, 50, alpha);
                graphic.setColor(limeGreen);
                break;
            case "maroon":
                Color maroon = new Color(128, 0, 0, alpha);
                graphic.setColor(maroon);
                break;
            case "navy":
                Color navy = new Color(0, 0, 128, alpha);
                graphic.setColor(navy);
                break;
            case "olive":
                Color olive = new Color(128, 128, 0, alpha);
                graphic.setColor(olive);
                break;
            case "peach":
                Color peach = new Color(255, 218, 185, alpha);
                graphic.setColor(peach);
                break;
            case "royalBlue":
                Color royalBlue = new Color(65, 105, 225, alpha);
                graphic.setColor(royalBlue);
                break;
            //30
            case "aliceBlue":
                Color aliceBlue = new Color(240, 248, 255, alpha);
                graphic.setColor(aliceBlue);
                break;
            case "azure":
                Color azure = new Color(240, 255, 255, alpha);
                graphic.setColor(azure);
                break;
            case "coral":
                Color coral = new Color(255, 127, 80, alpha);
                graphic.setColor(coral);
                break;
            case "cornflowerBlue":
                Color cornflowerBlue = new Color(100, 99, 205, alpha);
                graphic.setColor(cornflowerBlue);
                break;
            case "crimson":
                Color crimson = new Color(220, 20, 60, alpha);
                graphic.setColor(crimson);
                break;
            case "fireBrick":
                Color fireBrick = new Color(178, 34, 34, alpha);
                graphic.setColor(fireBrick);
                break;
            case "forestGreen":
                Color forestGreen = new Color(34, 139, 34, alpha);
                graphic.setColor(forestGreen);
                break;
            case "ghostWhite":
                Color ghostWhite = new Color(248, 248, 255, alpha);
                graphic.setColor(ghostWhite);
                break;
            case "goldenrod":
                Color goldenrod = new Color(218, 165, 32, alpha);
                graphic.setColor(goldenrod);
                break;
            case "greenYellow":
                Color greenYellow = new Color(173, 255, 47, alpha);
                graphic.setColor(greenYellow);
                break;
            //40
            case "indianRed":
                Color indianRed = new Color(205, 92, 92, alpha);
                graphic.setColor(indianRed);
                break;
            case "lavender":
                Color lavender = new Color(230, 230, 250, alpha);
                graphic.setColor(lavender);
                break;
            case "lawnGreen":
                Color lawnGreen = new Color(124, 252, 0, alpha);
                graphic.setColor(lawnGreen);
                break;
            case "lemonChiffon":
                Color lemonChiffon = new Color(255, 250, 205, alpha);
                graphic.setColor(lemonChiffon);
                break;
            case "lightBlue":
                Color lightBlue = new Color(173, 216, 230, alpha);
                graphic.setColor(lightBlue);
                break;
            case "aquamarine":
                Color aquamarine = new Color(127, 255, 212, alpha);
                graphic.setColor(aquamarine);
                break;
            case "blanchedAlmond":
                Color blanchedAlmond = new Color(255, 235, 205, alpha);
                graphic.setColor(blanchedAlmond);
                break;
            case "cadetBlue":
                Color cadetBlue = new Color(95, 158, 160, alpha);
                graphic.setColor(cadetBlue);
                break;
            case "chartreuse":
                Color chartreuse = new Color(127, 255, 0, alpha);
                graphic.setColor(chartreuse);
                break;
            case "chocolate":
                Color chocolate = new Color(210, 105, 30, alpha);
                graphic.setColor(chocolate);
                break;
            //50
            case "darkCyan":
                Color darkCyan = new Color(0, 139, 139, alpha);
                graphic.setColor(darkCyan);
                break;
            case "darkGoldenrod":
                Color darkGoldenrod = new Color(186, 135, 20, alpha);
                graphic.setColor(darkGoldenrod);
                break;
            case "darkGreen":
                Color darkGreen = new Color(0, 100, 0, alpha);
                graphic.setColor(darkGreen);
                break;
            case "darkKhaki":
                Color darkKhaki = new Color(189, 183, 107, alpha);
                graphic.setColor(darkKhaki);
                break;
            case "darkMagenta":
                Color darkMagenta = new Color(139, 0, 139, alpha);
                graphic.setColor(darkMagenta);
                break;
            case "darkOliveGreen":
                Color darkOliveGreen = new Color(85, 107, 47, alpha);
                graphic.setColor(darkOliveGreen);
                break;
            case "darkOrange":
                Color darkOrange = new Color(255, 140, 0, alpha);
                graphic.setColor(darkOrange);
                break;
            case "darkOrchid":
                Color darkOrchid = new Color(153, 50, 204, alpha);
                graphic.setColor(darkOrchid);
                break;
            case "darkPurple":
                Color darkPurple = new Color(139, 0, 139, alpha);
                graphic.setColor(darkPurple);
                break;
            case "darkRed":
                Color darkRed = new Color(139, 0, 0, alpha);
                graphic.setColor(darkRed);
                break;
            //60
            case "darkSalmon":
                Color darkSalmon = new Color(233, 150, 122, alpha);
                graphic.setColor(darkSalmon);
                break;
            case "darkSeaGreen":
                Color darkSeaGreen = new Color(143, 188, 143, alpha);
                graphic.setColor(darkSeaGreen);
                break;
            case "darkSlateBlue":
                Color darkSlateBlue = new Color(72, 61, 139, alpha);
                graphic.setColor(darkSlateBlue);
                break;
            case "darkSlateGray":
                Color darkSlateGray = new Color(47, 79, 79, alpha);
                graphic.setColor(darkSlateGray);
                break;
            case "darkTurquoise":
                Color darkTurquoise = new Color(0, 206, 209, alpha);
                graphic.setColor(darkTurquoise);
                break;
            case "deepPink":
                Color deepPink = new Color(255, 20, 147, alpha);
                graphic.setColor(deepPink);
                break;
            case "brown":
                Color brown = new Color(165, 42, 42, alpha);
                graphic.setColor(brown);
                break;
            case "teal":
                Color teal = new Color(0, 128, 128, alpha);
                graphic.setColor(teal);
                break;
            case "aqua":
                Color aqua = new Color(0, 255, 255, alpha);
                graphic.setColor(aqua);
                break;
            case "silver":
                Color silver = new Color(192, 192, 192, alpha);
                graphic.setColor(silver);
                break;
            //70
            case "violet":
                Color violet = new Color(238, 130, 238, alpha);
                graphic.setColor(violet);
                break;
            case "plum":
                Color plum = new Color(221, 160, 221, alpha);
                graphic.setColor(plum);
                break;
            case "limegreen":
                Color limegreen = new Color(50, 205, 50, alpha);
                graphic.setColor(limegreen);
                break;  
            case "orchid":
                Color orchid = new Color(218, 112, 214, alpha);
                graphic.setColor(orchid);
                break;
            case "burlywood":
                Color burlywood = new Color(222, 184, 135, alpha);
                graphic.setColor(burlywood);
                break;
            case "cadetblue":
                Color cadetblue = new Color(95, 158, 160, alpha);
                graphic.setColor(cadetblue);
                break;
            case "darkorange":
                Color darkorange = new Color(255, 140, 0, alpha);
                graphic.setColor(darkorange);
                break;
            case "hotpink":
                Color hotpink = new Color(255, 105, 180, alpha);
                graphic.setColor(hotpink);
                break;
             case "lawngreen":
                Color lawngreen = new Color(124, 252, 0, alpha);
                graphic.setColor(lawngreen);
                break;
            case "sienna":
                Color sienna = new Color(160, 82, 45, alpha);
                graphic.setColor(sienna);
                break;   
            //80
            case "lightblue":
                Color lightblue = new Color(173, 216, 230, alpha);
                graphic.setColor(lightblue);
                break;
            case "peru":
                Color peru = new Color(205, 133, 63, alpha);
                graphic.setColor(peru);
                break;
            case "rosybrown":
                Color rosybrown = new Color(188, 143, 143, alpha);
                graphic.setColor(rosybrown);
                break;
            case "mediumseagreen":
                Color mediumseagreen = new Color(60, 179, 113, alpha);
                graphic.setColor(mediumseagreen);
                break;
            default:
                graphic.setColor(Color.black);
                break;
        }
    }
    
    /**
     * Wait for a specified number of milliseconds before finishing.
     * This provides an easy way to specify a small delay which can be
     * used when producing animations.
     * @param  milliseconds  the number 
     */
    public void wait(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e){
            // ignoring exception at the moment
        }
    }

    /**
     * Redraw ell shapes currently on the Canvas.
     */
    private void redraw(){
        erase();
        for(Iterator i=objects.iterator(); i.hasNext(); ) {
                       shapes.get(i.next()).draw(graphic);
        }
        canvas.repaint();
    }
       
    /**
     * Erase the whole canvas. (Does not repaint.)
     */
    private void erase(){
        Color original = graphic.getColor();
        graphic.setColor(backgroundColour);
        Dimension size = canvas.getSize();
        graphic.fill(new java.awt.Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }


    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class CanvasPane extends JPanel{
        public void paint(Graphics g){
            g.drawImage(canvasImage, 0, 0, null);
        }
    }
    
    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class ShapeDescription{
        private Shape shape;
        private String colorString;
        private int alpha;
        public ShapeDescription(Shape shape, String color, int alpha){
            this.shape = shape;
            colorString = color;
            this.alpha = alpha;
        }

        public void draw(Graphics2D graphic){
            setForegroundColor(colorString, alpha);
            graphic.draw(shape);
            graphic.fill(shape);  
        }
    }
}
