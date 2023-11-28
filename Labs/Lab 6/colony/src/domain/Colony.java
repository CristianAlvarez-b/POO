package domain;

import java.io.*;
import java.util.ArrayList;

/**
 * La clase Colony representa una colonia de entidades, incluyendo hormigas, flores y abejas.
 */
public class Colony implements Serializable {
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
        setEntity(28, 0, new Food());
        setEntity(29, 0, new Food());
        setEntity(27, 0, new Food());
        setEntity(29, 1, new Food());
        setEntity(28, 1, new Food());
        someEntities();
    }

    /**
     * Obtiene el tamaño de la colonia.
     * @return Tamaño de la colonia.
     */
    public int getLength() {
        return LENGTH;
    }



    public Entity[][] getColony(){return colony;}
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
        Ant flik = new Ant( this, 10, 10);
        Ant molt = new Ant( this, 15, 15);
        HungryAnt rachel =  new HungryAnt(this, 5, 5);
        HungryAnt monica = new HungryAnt( this, 11, 11);
        Flower rose = new Flower( this, 28, 7);
        Flower violet = new Flower( this, 28, 24);
        Bee juliana = new Bee(this, 8, 20);
        Bee cristian = new Bee( this, 22, 10);
        GuardianAnt  juliana1 = new GuardianAnt(this, 0, 29);
        GuardianAnt cristian2 = new GuardianAnt(this, 11, 0);
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
    private void clearEntities(){
        for (int r = 0; r < LENGTH; r++) {
            for (int c = 0; c < LENGTH; c++) {
                colony[r][c] = null;
            }
        }
    }
    public static Colony open00(File archivo) throws ColonyException{
        throw new ColonyException("Opcion open en construcción. Archivo: " + archivo.getName());
    }
    public void save00(File archivo) throws ColonyException{
        throw new ColonyException("Opcion save en construcción. Archivo: " + archivo.getName());
    }

    public static Colony importt00(File archivo) throws ColonyException{
        throw new ColonyException("Opcion open en construcción. Archivo: " + archivo.getName());
    }
    public void export00(File archivo) throws ColonyException{
        throw new ColonyException("Opcion export en construcción. Archivo: " + archivo.getName());
    }

    public static Colony open01(File archivo) throws Exception{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo));
        String s = (String)in.readObject();
        Colony c = (Colony)in.readObject();
        in.close();
        return c;
    }
    public void save01(File archivo) throws Exception{
        File archivoFinal = new File(archivo.getPath() + ".dat");
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivoFinal));
        out.writeObject("Colony storage\n");
        out.writeObject(this);
        out.close();
    }
    public static Colony importt01(File archivo) throws Exception{
        Colony colony = new Colony();
        colony.clearEntities();
        BufferedReader bIn = new BufferedReader(new FileReader(archivo));
        String line = bIn.readLine();
        while(line != null){
            line = line.trim();
            String[] entidad = line.split("\\s+");
            if(!(entidad.length == 3)){
                throw new ColonyException("Informacion de la entidad invalida.");
            }
            int numero1 = Integer.parseInt(entidad[1]);
            int numero2 = Integer.parseInt(entidad[2]);
            Entity instancia;
            String className = "domain." + entidad[0];
            if (entidad[0].equals("Food")) {
                instancia = (Entity) Class.forName(className).getConstructor().newInstance();
            } else {
                instancia = (Entity) Class.forName(className).getConstructor(Colony.class, int.class, int.class)
                        .newInstance(colony, numero1, numero2);
            }
            colony.setEntity(numero1, numero2, instancia);
            line = bIn.readLine();
        }
        bIn.close();
        return colony;
    }
    public void export01(File archivo) throws Exception{
        File archivoFinal = new File(archivo.getPath() + ".txt");
        PrintWriter pw = new PrintWriter(new FileOutputStream(archivoFinal));
        for(int i = 0; i < colony.length; i++)
            for (int j = 0; j < colony[0].length; j++) {
                if(colony[i][j] != null){
                    pw.println(colony[i][j].getClass().getSimpleName()+" " + i +" "+ j);
                }
            }
        pw.close();
    }
    public static Colony importt02(File archivo) throws Exception {
        Colony colony = new Colony();
        colony.clearEntities();

        try (BufferedReader bIn = new BufferedReader(new FileReader(archivo))) {
            String line = bIn.readLine();

            while (line != null) {
                line = line.trim();
                String[] entidad = line.split("\\s+");

                if (entidad.length != 3) {
                    throw new ColonyException("Informacion de la entidad invalida en el archivo.");
                }

                int numero1, numero2;

                try {
                    numero1 = Integer.parseInt(entidad[1]);
                    numero2 = Integer.parseInt(entidad[2]);
                } catch (NumberFormatException e) {
                    throw new ColonyException("Error al convertir coordenadas a números.");
                }

                Entity instancia;

                String className = "domain." + entidad[0];

                try {
                    if (entidad[0].equals("Food")) {
                        instancia = (Entity) Class.forName(className).getConstructor().newInstance();
                    } else {
                        instancia = (Entity) Class.forName(className).getConstructor(Colony.class, int.class, int.class)
                                .newInstance(colony, numero1, numero2);
                    }
                } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
                         IllegalAccessException  e) {
                    throw new ColonyException("Error al crear una instancia de la entidad.");
                }

                colony.setEntity(numero1, numero2, instancia);
                line = bIn.readLine();
            }
        } catch (IOException e) {
            throw new ColonyException("Error al leer el archivo: "+ archivo.getName());
        }

        return colony;
    }
    public void export02(File archivo) throws ColonyException {
        File archivoFinal = new File(archivo.getPath() + ".txt");

        try (PrintWriter pw = new PrintWriter(new FileOutputStream(archivoFinal))) {
            for (int i = 0; i < colony.length; i++) {
                for (int j = 0; j < colony[0].length; j++) {
                    if (colony[i][j] != null) {
                        pw.println(colony[i][j].getClass().getSimpleName() + " " + i + " " + j);
                    }
                }
            }
        } catch (IOException e) {
            throw new ColonyException("Error al escribir en el archivo." + archivo.getName());
        }
    }
    public static Colony open(File archivo) throws ColonyException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            String header = (String) in.readObject();
            if (!header.equals("Colony storage\n")) {
                throw new ColonyException("El archivo no es un archivo de almacenamiento de colonias válido.");
            }
            return (Colony) in.readObject();
        } catch (FileNotFoundException e) {
            throw new ColonyException("El archivo no existe: " + archivo.getName());
        } catch (IOException e) {
            throw new ColonyException("Error al leer el archivo: " + archivo.getName());
        }
    }
    public void save(File archivo) throws ColonyException {
        File archivoFinal = new File(archivo.getPath() + ".dat");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivoFinal))) {
            out.writeObject("Colony storage\n");
            out.writeObject(this);
        } catch (IOException e) {
            throw new ColonyException("Error al escribir en el archivo: "+ archivo.getName());
        }
    }
    public static Colony importt(File archivo) throws Exception {
        Colony colony = new Colony();
        colony.clearEntities();

        try (BufferedReader bIn = new BufferedReader(new FileReader(archivo))) {
            String line;
            int lineNumber = 1;

            while ((line = bIn.readLine()) != null) {
                line = line.trim();
                String[] entidad = line.split("\\s+");

                if (entidad.length != 3) {
                    throw new ColonyException("Informacion de la entidad invalida en el archivo. Línea: " + lineNumber + ", Contenido: " + line);
                }

                int numero1, numero2;

                try {
                    numero1 = Integer.parseInt(entidad[1]);
                    numero2 = Integer.parseInt(entidad[2]);
                } catch (NumberFormatException e) {
                    throw new ColonyException("Error al convertir coordenadas a números. Línea: " + lineNumber + ", Contenido: " + line);
                }

                Entity instancia;

                String className = "domain." + entidad[0];

                try {
                    if (entidad[0].equals("Food")) {
                        instancia = (Entity) Class.forName(className).getConstructor().newInstance();
                    } else {
                        instancia = (Entity) Class.forName(className).getConstructor(Colony.class, int.class, int.class)
                                .newInstance(colony, numero1, numero2);
                    }
                } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
                         IllegalAccessException e) {
                    throw new ColonyException("Error al crear una instancia de la entidad. Línea: " + lineNumber + ", Contenido: " + line);
                }

                colony.setEntity(numero1, numero2, instancia);
                lineNumber++;
            }
        } catch (IOException e) {
            throw new ColonyException("Error al leer el archivo: " + archivo.getName());
        }
        return colony;
    }
    public void export(File archivo) throws ColonyException {
        File archivoFinal = new File(archivo.getPath() + ".txt");

        try (PrintWriter pw = new PrintWriter(new FileOutputStream(archivoFinal))) {
            for (int i = 0; i < colony.length; i++) {
                for (int j = 0; j < colony[0].length; j++) {
                    if (colony[i][j] != null) {
                        pw.println(colony[i][j].getClass().getSimpleName() + " " + i + " " + j);
                    }
                }
            }
        } catch (IOException e) {
            throw new ColonyException("Error al escribir en el archivo." + archivo.getName());
        }
    }
}
