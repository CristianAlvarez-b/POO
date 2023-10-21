package domain;
import java.util.*;

/*No olviden adicionar la documentacion*/
public class Colony{
    static private int LENGTH=30;
    private Entity[][] colony;
    
    public Colony() {
        colony=new Entity[LENGTH][LENGTH];
        for (int r=0;r<LENGTH;r++){
            for (int c=0;c<LENGTH;c++){
                colony[r][c]=null;
            }
        }
        setEntity(0,0,new Food());
        setEntity(0,1,new Food());
        setEntity(0,2,new Food());
        setEntity(1,0,new Food());
        setEntity(1,1,new Food());
        someEntities();
    }

    public int  getLength(){
        return LENGTH;
    }

    public Entity getEntity(int r,int c){
        return colony[r][c];
    }

    public void setEntity(int r, int c, Entity e){
        colony[r][c]=e;
    }

    public void someEntities(){
        new Ant("flik",this, 10, 10);
        new Ant("molt",this, 15, 15);
        new HungryAnt("rachel",this, 5, 5);
        new HungryAnt("monica", this, 11, 11);
        new Flower("rose",this, 28, 7);
        new Flower("violet", this, 28, 24);
        new Bee("juliana", this, 8, 20);
        new Bee("cristian", this, 22,10);
        new GuardianAnt("juliana", this, 0, 29);
        new GuardianAnt("cristian", this, 11, 0);
    }
    
    /**
     * Avanza un paso de tiempo en la colonia, realizando las acciones necesarias para cada entidad.
     */
    public void ticTac() {
        ArrayList<Entity> entidades = new ArrayList<>();
        for (int r = 0; r < LENGTH; r++) {
            for (int c = 0; c < LENGTH; c++) {
                Entity entity = colony[r][c];
                if (entity != null && !entidades.contains(entity)) {
                    // Realizar una acción para cada entidad viva
                    entity.act();
                    entidades.add(entity);
                }
            }
        }
    }
    
    public int[] findNearestEntityPosition(int startRow, int startColumn, Class<?> targetType) {
        int[] nearestPosition = new int[]{-1, -1};
        int minDistance = Integer.MAX_VALUE;
        
        for (int r = 0; r < LENGTH; r++) {
            for (int c = 0; c < LENGTH; c++) {
                if (targetType.isInstance(colony[r][c])) {
                    int distance = Math.abs(r - startRow) + Math.abs(c - startColumn); // Distancia Manhattan
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestPosition[0] = r;
                        nearestPosition[1] = c;
                    }
                }
            }
        }
        return nearestPosition;
    }
}
