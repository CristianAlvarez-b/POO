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
            canvasSingleton = new Canvas("BlueJ Shapes Demo", 300, 300, 
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
    public void draw(Object referenceObject, String color, Shape shape){
        objects.remove(referenceObject);   // just in case it was already there
        objects.add(referenceObject);      // add at the end
        shapes.put(referenceObject, new ShapeDescription(shape, color));
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
    public void setForegroundColor(String colorString) {
        switch (colorString) {
            
            case "red":
                graphic.setColor(Color.red);
                break;
                
            case "blue":
                graphic.setColor(Color.blue);
                break;
            case "yellow":
                graphic.setColor(Color.yellow);
                break;
            case "green":
                graphic.setColor(Color.green);
                break;
            case "magenta":
                graphic.setColor(Color.magenta);
                break;
            case "white":
                graphic.setColor(Color.white);
                break;
            case "cyan":
                graphic.setColor(Color.cyan);
                break;
            case "darkGray":
                graphic.setColor(Color.darkGray);
                break;
            case "gray":
                graphic.setColor(Color.gray);
                break;
            case "lightGray":
                graphic.setColor(Color.lightGray);
                break;
            case "orange":
                graphic.setColor(Color.orange);
                break;
            case "pink":
                graphic.setColor(Color.pink);
                break;
            case "purple":
                Color colorTranslucido = new Color(0, 0, 255);
                graphic.setColor(colorTranslucido);
                break;
                
            case "beige":
                Color beige = new Color(245, 245, 220);
                graphic.setColor(beige);
                break;
            case "seaGreen":
                Color seaGreen = new Color(46, 139, 87);
                graphic.setColor(seaGreen);
                break;
            case "slateBlue":
                Color slateBlue = new Color(106, 90, 205);
                graphic.setColor(slateBlue);
                break;
            case "tomato":
                Color tomato = new Color(255, 99, 71);
                graphic.setColor(tomato);
                break;
            case "turquoise":
                Color turquoise = new Color(64, 224, 208);
                graphic.setColor(turquoise);
                break;
            case "fuchsia":
                Color fuchsia = new Color(255, 0, 255);
                graphic.setColor(fuchsia);
                break;
            case "gold":
                Color gold = new Color(255, 215, 0);
                graphic.setColor(gold);
                break;
            case "indigo":
                Color indigo = new Color(75, 0, 130);
                graphic.setColor(indigo);
                break;
            case "ivory":
                Color ivory = new Color(255, 255, 240);
                graphic.setColor(ivory);
                break;
            case "khaki":
                Color khaki = new Color(240, 230, 140);
                graphic.setColor(khaki);
                break;
            case "lime":
                Color lime = new Color(0, 255, 0);
                graphic.setColor(lime);
                break;
            case "limeGreen":
                Color limeGreen = new Color(50, 205, 50);
                graphic.setColor(limeGreen);
                break;
            case "maroon":
                Color maroon = new Color(128, 0, 0);
                graphic.setColor(maroon);
                break;
            case "navy":
                Color navy = new Color(0, 0, 128);
                graphic.setColor(navy);
                break;
            case "olive":
                Color olive = new Color(128, 128, 0);
                graphic.setColor(olive);
                break;
            case "peach":
                Color peach = new Color(255, 218, 185);
                graphic.setColor(peach);
                break;
            case "royalBlue":
                Color royalBlue = new Color(65, 105, 225);
                graphic.setColor(royalBlue);
                break;
            case "aliceBlue":
                Color aliceBlue = new Color(240, 248, 255);
                graphic.setColor(aliceBlue);
                break;
            case "azure":
                Color azure = new Color(240, 255, 255);
                graphic.setColor(azure);
                break;
            case "coral":
                Color coral = new Color(255, 127, 80);
                graphic.setColor(coral);
                break;
            case "cornflowerBlue":
                Color cornflowerBlue = new Color(100, 99, 205);
                graphic.setColor(cornflowerBlue);
                break;
            case "crimson":
                Color crimson = new Color(220, 20, 60);
                graphic.setColor(crimson);
                break;
            case "fireBrick":
                Color fireBrick = new Color(178, 34, 34);
                graphic.setColor(fireBrick);
                break;
            case "forestGreen":
                Color forestGreen = new Color(34, 139, 34);
                graphic.setColor(forestGreen);
                break;
            case "ghostWhite":
                Color ghostWhite = new Color(248, 248, 255);
                graphic.setColor(ghostWhite);
                break;
            case "goldenrod":
                Color goldenrod = new Color(218, 165, 32);
                graphic.setColor(goldenrod);
                break;
            case "greenYellow":
                Color greenYellow = new Color(173, 255, 47);
                graphic.setColor(greenYellow);
                break;
            case "indianRed":
                Color indianRed = new Color(205, 92, 92);
                graphic.setColor(indianRed);
                break;
            case "lavender":
                Color lavender = new Color(230, 230, 250);
                graphic.setColor(lavender);
                break;
            case "lawnGreen":
                Color lawnGreen = new Color(124, 252, 0);
                graphic.setColor(lawnGreen);
                break;
            case "lemonChiffon":
                Color lemonChiffon = new Color(255, 250, 205);
                graphic.setColor(lemonChiffon);
                break;
            case "lightBlue":
                Color lightBlue = new Color(173, 216, 230);
                graphic.setColor(lightBlue);
                break;
            case "aquamarine":
                Color aquamarine = new Color(127, 255, 212);
                graphic.setColor(aquamarine);
                break;
            case "blanchedAlmond":
                Color blanchedAlmond = new Color(255, 235, 205);
                graphic.setColor(blanchedAlmond);
                break;
            case "cadetBlue":
                Color cadetBlue = new Color(95, 158, 160);
                graphic.setColor(cadetBlue);
                break;
            case "chartreuse":
                Color chartreuse = new Color(127, 255, 0);
                graphic.setColor(chartreuse);
                break;
            case "chocolate":
                Color chocolate = new Color(210, 105, 30);
                graphic.setColor(chocolate);
                break;
            case "darkCyan":
                Color darkCyan = new Color(0, 139, 139);
                graphic.setColor(darkCyan);
                break;
            case "darkGoldenrod":
                Color darkGoldenrod = new Color(186, 135, 20);
                graphic.setColor(darkGoldenrod);
                break;
            case "darkGreen":
                Color darkGreen = new Color(0, 100, 0);
                graphic.setColor(darkGreen);
                break;
            case "darkKhaki":
                Color darkKhaki = new Color(189, 183, 107);
                graphic.setColor(darkKhaki);
                break;
            case "darkMagenta":
                Color darkMagenta = new Color(139, 0, 139);
                graphic.setColor(darkMagenta);
                break;
            case "darkOliveGreen":
                Color darkOliveGreen = new Color(85, 107, 47);
                graphic.setColor(darkOliveGreen);
                break;
            case "darkOrange":
                Color darkOrange = new Color(255, 140, 0);
                graphic.setColor(darkOrange);
                break;
            case "darkOrchid":
                Color darkOrchid = new Color(153, 50, 204);
                graphic.setColor(darkOrchid);
                break;
            case "darkPurple":
                Color darkPurple = new Color(139, 0, 139);
                graphic.setColor(darkPurple);
                break;
            case "darkRed":
                Color darkRed = new Color(139, 0, 0);
                graphic.setColor(darkRed);
                break;
            case "darkSalmon":
                Color darkSalmon = new Color(233, 150, 122);
                graphic.setColor(darkSalmon);
                break;
            case "darkSeaGreen":
                Color darkSeaGreen = new Color(143, 188, 143);
                graphic.setColor(darkSeaGreen);
                break;
            case "darkSlateBlue":
                Color darkSlateBlue = new Color(72, 61, 139);
                graphic.setColor(darkSlateBlue);
                break;
            case "darkSlateGray":
                Color darkSlateGray = new Color(47, 79, 79);
                graphic.setColor(darkSlateGray);
                break;
            case "darkTurquoise":
                Color darkTurquoise = new Color(0, 206, 209);
                graphic.setColor(darkTurquoise);
                break;
            case "deepPink":
                Color deepPink = new Color(255, 20, 147);
                graphic.setColor(deepPink);
                break;
            case "brown":
                graphic.setColor(new Color(165, 42, 42));
                break;
            case "teal":
                graphic.setColor(new Color(0, 128, 128));
                break;
            case "aqua":
                graphic.setColor(new Color(0, 255, 255));
                break;
            case "silver":
                graphic.setColor(new Color(192, 192, 192));
                break;
            case "violet":
                graphic.setColor(new Color(238, 130, 238));
                break;
            case "plum":
                graphic.setColor(new Color(221, 160, 221));
                break;
            case "limegreen":
                graphic.setColor(new Color(50, 205, 50));
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

        public ShapeDescription(Shape shape, String color){
            this.shape = shape;
            colorString = color;
        }

        public void draw(Graphics2D graphic){
            setForegroundColor(colorString);
            graphic.draw(shape);
            graphic.fill(shape);
        }
    }

}
