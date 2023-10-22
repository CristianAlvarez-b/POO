package domain;

import java.util.ArrayList;

/**
 * La clase Colony representa una colonia de entidades, incluyendo hormigas, flores y abejas.
 */
public class Colony {
    // Tamaño predeterminado de la colonia
    static private int LENGTH = 30;
    // Matriz que almacena las entidades en la colonia
    private Entity[][] colony;

    /**
     * Constructor de la colonia que inicializa la matriz y agrega algunas entidades iniciales.
     */
    public Colony() {
        colony = new Entity[LENGTH][LENGTH];
        for (int r = 0; r < LENGTH; r++) {
            for (int c = 0; c < LENGTH; c++) {
                colony[r][c] = null;
            }
        }
        setEntity(0, 0, new Food());
        setEntity(0, 1, new Food());
        setEntity(0, 2, new Food());
        setEntity(1, 0, new Food());
        setEntity(1, 1, new Food());
        someEntities();
    }

    /**
     * Obtiene el tamaño de la colonia.
     * @return Tamaño de la colonia.
     */
    public int getLength() {
        return LENGTH;
    }

    /**
     * Obtiene la entidad en una ubicación específica de la colonia.
     * @param r Fila de la ubicación.
     * @param c Columna de la ubicación.
     * @return Entidad en la ubicación especificada.
     */
    public Entity getEntity(int r, int c) {
        return colony[r][c];
    }

    /**
     * Establece una entidad en una ubicación específica de la colonia.
     * @param r Fila de la ubicación.
     * @param c Columna de la ubicación.
     * @param e Entidad a establecer.
     */
    public void setEntity(int r, int c, Entity e) {
        colony[r][c] = e;
    }

    /**
     * Agrega algunas entidades iniciales a la colonia.
     */
    public void someEntities() {
        new Ant("flik", this, 10, 10);
        new Ant("molt", this, 15, 15);
        new HungryAnt("rachel", this, 5, 5);
        new HungryAnt("monica", this, 11, 11);
        new Flower("rose", this, 28, 7);
        new Flower("violet", this, 28, 24);
        new Bee("juliana", this, 8, 20);
        new Bee("cristian", this, 22, 10);
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

    /**
     * Encuentra la posición de la entidad más cercana de un tipo específico a partir de una ubicación de inicio.
     * @param startRow Fila de inicio.
     * @param startColumn Columna de inicio.
     * @param targetType Tipo de entidad a buscar.
     * @return Arreglo de dos elementos que representa la fila y la columna de la entidad más cercana.
     */
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
