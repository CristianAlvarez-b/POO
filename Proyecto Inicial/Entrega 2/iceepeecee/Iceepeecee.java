import java.awt.*;
import java.lang.Math;
import java.util.*;
/**
 * The simulator of the island group of Iceepeecee. It has 
 * islands and planes with cameras that fly over the islands 
 * taking photographs.
 *
 * @author (Juliana Briceño & Cristian Alvarez)
 * @version (1.0)
 */
public class Iceepeecee
{
    private boolean ok;
    private HashMap<String,Island> islands;
    private HashMap<String,Flight> flights;
    private static String[] colores = {"red", "blue", "yellow", "green", "magenta", "white", "cyan", "darkGray", "gray", "lightGray", "orange", "pink", "purple", "beige", "seaGreen", "slateBlue", "tomato", "turquoise", "fuchsia", "gold", "indigo", "ivory", "khaki", "lime", "limeGreen", "maroon", "navy", "olive", "peach", "royalBlue", "aliceBlue", "azure", "coral", "cornflowerBlue", "crimson", "fireBrick", "forestGreen", "ghostWhite", "goldenrod", "greenYellow", "indianRed", "lavender", "lawnGreen", "lemonChiffon", "lightBlue", "aquamarine", "blanchedAlmond", "cadetBlue", "chartreuse", "chocolate", "darkCyan", "darkGoldenrod", "darkGreen", "darkKhaki", "darkMagenta", "darkOliveGreen", "darkOrange", "darkOrchid", "darkPurple", "darkRed", "darkSalmon", "darkSeaGreen", "darkSlateBlue", "darkSlateGray", "darkTurquoise", "deepPink", "brown", "teal", "aqua", "silver", "violet", "plum", "limegreen", "orchid", "burlywood", "cadetblue", "darkorange", "hotpink", "lawngreen", "sienna", "lightblue", "peru", "rosybrown", "mediumseagreen"};
    private boolean visible;
    /**
     * Create an empty Iceepeecee space.
     * 
     * @param lenght of the canvas
     * @param width of the canvas
     */
    public Iceepeecee(int lenght, int width){
        Canvas canvas = Canvas.getCanvas(lenght,width);
        islands = new HashMap<>();
        flights = new HashMap<>();
        ok = true;
        visible = false;
    }
    
    /**
     * Create an Iceepeecee with islands and flights.
     * 
     * @param islands to add
     * @param flights to add
     */
    public Iceepeecee(int[][][] islands, int[][][] flights){
        this(500, 500);
        for(int i = 0; i < islands.length; i++){
            addIsland(selectColorIsland(), islands[i]);
        }
        for(int i = 0; i < flights.length; i++){
            addFlight(selectColorFlight(), flights[i][0], flights[i][1]);
        }
    }
    
    /**
     * Add a new island to Iceepeecee.
     * 
     * @param color of the island
     * @param coordinates of the vertices of the island
     */
    //validar que este en el canvas.
    public void addIsland(String color, int[][] polygon){
        if (!islands.containsKey(color)) {
            Island island = new Island(polygon, color);
            islands.put(color, island);
            this.ok = true;
        } else {
            this.ok = false;
            throw new IllegalArgumentException("The key '" + color + "' already exists in the HashMap.");
        }
    }
    
    /**
     * Add a new flight to Iceepeecee.
     * 
     * @param color of the flight
     * @param coordinates of the start of the flight
     * @param coordinates of the end of the flight
     */
    //validar que este en el canvas
    public void addFlight(String color, int[] from, int[] to){
        if (!flights.containsKey(color)) {
            Flight flight = new Flight(color, from, to);
            flights.put(color, flight);
            this.ok = true;
        } else {
            this.ok = false;
            throw new IllegalArgumentException("The key '" + color + "' already exists in the HashMap.");
        }
    }
    
    /**
     * Takes photographs with a specified angle (theta) for all flights in Iceepeecee.
     *
     * @param theta The angle (in degrees) at which the photographs will be taken.
     */
    public void photograph(int theta){
        for(String flight : flights.keySet()){
            flights.get(flight).makePhoto(theta);
        }
        borderIsland();
        this.ok = true;
    }
    
    /**
     * Takes photographs with a specified angle (theta) for all flights in Iceepeecee.
     *
     * @param theta The angle (in degrees) at which the photographs will be taken.
     */
    public void photograph(double theta){
        for(String flight : flights.keySet()){
            flights.get(flight).makePhoto(theta);
        }
        borderIsland();
        this.ok = true;
    }
    
    /**
     * Takes a photograph with a specified angle (theta) for a specific flight in Iceepeecee.
     *
     * @param flight The identifier of the flight for which the photograph will be taken.
     * @param theta  The angle (in degrees) at which the photograph will be taken.
     * @throws IllegalArgumentException If the specified flight does not exist.
     */
    //border en diagrama
    public void photograph(String flight, int theta){
        if(flights.containsKey(flight)){
            flights.get(flight).makePhoto(theta);
            borderIsland();
            this.ok = true; 
        }else{
            this.ok = false;
            throw new IllegalArgumentException("The flight doesn't exists.");
        }
    }
    
    /**
     * Delete an island.
     * 
     * @param color of the island to delete
     */
    public void delIsland(String color){
        if (islands.containsKey(color)) {
            islands.get(color).makeInvisible();
            islands.remove(color);
            this.ok = true;
        } else {
            this.ok = false;
            throw new IllegalArgumentException("The key '" + color + "' doesn't exists in the HashMap.");
        }
    }
    
    /**
     * Delete a flight.
     * 
     * @param color of the flight to delete
     */
    public void delFlight(String color){
        if (flights.containsKey(color)) {
            flights.get(color).makeInvisible();
            Photograph photo = flights.get(color).getPhotograph();
            for(String c: islands.keySet()){
                PolygonShape polP = photo.getPolygon();
                PolygonShape polI = islands.get(c).getPolygon();
                if(polP.verificaInterseccion(polI)){
                    islands.get(c).eraseBorder();
                }
            }
            flights.remove(color);
            this.ok = true;
        } else {
            this.ok = false;
            throw new IllegalArgumentException("The key '" + color + "' doesn't exists in the HashMap.");
        }
        ArrayList<Photograph> photographs = new ArrayList<>();
        for(String c: flights.keySet()){
            Photograph p = flights.get(c).getPhotograph();
            if(p != null){
                photographs.add(p);
            }
        }
    }
    
    /**
     * Get the location of an island.
     * 
     * @param color of the island
     */
    public int[][] islandLocation(String island){
        if (islands.containsKey(island)) {
            int[][] location = islands.get(island).getLocation();
            this.ok = true;
            return location;
        } else {
            this.ok = false;
            throw new IllegalArgumentException("The key '" + island + "' doesn't exists in the HashMap.");
        }
    }
    
    /**
     * Get the location of a flight.
     * 
     * @param color of the flight
     */
    public int[][] flightLocation(String flight){
        if (flights.containsKey(flight)) {
            int[][] location = flights.get(flight).getLocation();
            this.ok = true;
            return location;
        } else {
            this.ok = false;
            throw new IllegalArgumentException("The key '" + flight + "' doesn't exists in the HashMap.");
        }
    }
    
    /**
     * Retrieves the angle (theta) of the camera for a specific flight in Iceepeecee.
     *
     * @param color The identifier of the flight for which the camera angle is requested.
     * @return The angle (in degrees) of the camera for the specified flight.
     * @throws IllegalArgumentException If the specified flight doesn't exist or if it doesn't have a photograph.
     */
    public int flightCamera(String color){
        if(flights.containsKey(color)){
            if(flights.get(color).getPhotograph() != null){
                this.ok = true;
                return flights.get(color).getPhotograph().getTheta(); 
            }else{
                this.ok = false;
                throw new IllegalArgumentException("The key '" + color + "' doesn't have photograph.");
            }
        }else{
            this.ok = false;
            throw new IllegalArgumentException("The key '" + color + "' doesn't exists in the HashMap."); 
        }
    }
    
    /**
     * Make Iceepeecee and its elements visible.
     */
    public void makeVisible(){
        this.visible = true;
        this.ok = true;
        for(String island : islands.keySet()){
            islands.get(island).makeVisible();
        }
        for(String flight : flights.keySet()){
            flights.get(flight).makeVisible();
        }
    }
    
    /**
     * Make Iceepeecee and its elements invisible.
     */
    public void makeInvisible(){
        this.visible = false;
        this.ok = true;
        for(String island : islands.keySet()){
            islands.get(island).makeInvisible();
        }
        for(String flight : flights.keySet()){
            flights.get(flight).makeInvisible();
        }
    }
    
    /**
     * Consult the existing islands.
     */
    public String[] islands(){
        String[] existingIslands = new String[islands.size()];
        int pos = 0;
        for(String island : islands.keySet()){
            existingIslands[pos] = island;
            pos++;
        }
        this.ok = true;
        return existingIslands;
    }
    
    /**
     * Consult the existing flights.
     */
    public String[] flights(){
        String[] existingFlights = new String[flights.size()];
        int pos = 0;
        for(String flight : flights.keySet()){
            existingFlights[pos] = flight;
            pos++;
        }
        this.ok = true;
        return existingFlights;
    }
    
    /**
     * Selects a color for a new island based on the available colors and existing islands.
     * If all available colors are already associated with islands, it returns the first color in the list.
     *
     * @return A color (as a string) for a new island.
     */
    private String selectColorIsland(){
        String color = colores[0];
        for(int i = 0; i < colores.length; i++){
            if(islands.containsKey(colores[i]) == false){
                color = colores[i];
                break;
            }
        }
        return color;
    }
    
    /**
     * Selects a color for a new flight based on the available colors and existing flights.
     * If all available colors are already associated with flights, it returns the first color in the list.
     *
     * @return A color (as a string) for a new flight.
     */
    //un solo metodo
    private String selectColorFlight(){
        String color = colores[0];
        for(int i = 0; i < colores.length; i++){
            if(flights.containsKey(colores[i]) == false){
                color = colores[i];
                break;
            }
        }
        return color; 
    }
    
    /**
     * Retrieves the colors of islands observed by flights in Iceepeecee based on the flight photographs.
     *
     * @return An array of strings containing the colors of observed islands.
     */
    public String[] observedIslands(){
        ArrayList<String> observedIslandsList = new ArrayList<>();
        ArrayList<Photograph> photographs = new ArrayList<>();
        for(String c: flights.keySet()){
            Photograph p = flights.get(c).getPhotograph();
            if(p != null){
                photographs.add(p);
            }
        }
        for(Photograph p: photographs){
            for(String c: islands.keySet()){
                PolygonShape polP = p.getPolygon();
                PolygonShape polI = islands.get(c).getPolygon();
                if(polP.verificaInterseccion(polI)){
                    String color = polI.getColor();
                    if(!observedIslandsList.contains(color)){
                        observedIslandsList.add(color);
                    }
                }
            }
        }
        String[] observedIslands = observedIslandsList.toArray(new String[observedIslandsList.size()]);
        this.ok = true;
        return observedIslands;
    }
    
    /**
     * Retrieves the colors of flights that have taken photographs without capturing any observed islands.
     *
     * @return An array of strings containing the colors of useless flights.
     */
    public String[] uselessFlights(){
        ArrayList<String> uselessFlights = new ArrayList<>();
        HashMap<String, Photograph> photographs = new HashMap<>();
        for(String c: flights.keySet()){
            Photograph p = flights.get(c).getPhotograph();
            if(p != null){
                photographs.put(c, p);
            }
        }
        for(String p: photographs.keySet()){
            boolean flag = false;
            for(String c: islands.keySet()){
                PolygonShape polP = photographs.get(p).getPolygon();
                PolygonShape polI = islands.get(c).getPolygon();
                if(polP.verificaInterseccion(polI) || uselessFlights.contains(p)){
                    flag = true;
                    break;
                }
            }
            if (!flag){
              uselessFlights.add(p);  
            }
        }
        String[] uselessFlightsList = uselessFlights.toArray(new String[uselessFlights.size()]);
        this.ok = true;
        return uselessFlightsList;
    }
    
    /**
     * Checks the operational status of the Iceepeecee simulator.
     *
     * @return True if the simulator is in an operational state, false otherwise.
     */
    public boolean ok(){
        return this.ok;
    }
    
    /**
     * Draws borders around observed islands based on flight photographs and erases borders from non-observed islands.
     * This method updates the visual representation of the islands in the Iceepeecee simulation.
     */
    private void borderIsland(){
        String[] observedIslands = observedIslands();
        for(String i: islands.keySet()){
            islands.get(i).eraseBorder();
        }
        for(String c: observedIslands){
            islands.get(c).drawBorder();
        }
    }
    
    /**
     * Checks the visibility status of the Iceepeecee simulation.
     *
     * @return True if the simulation is currently visible, false otherwise.
     */
    public boolean isVisible(){
        return this.visible;
    }
    
    /**
     * Finish the simulator.
     */
    public void finish(){
        System.exit(0);
    }
}
