package domain;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.*;

public class ColonyTest {
    @Test
    public void saveTest() {
        // Crear un objeto de la clase que contiene el método save
        Colony colony = new Colony(); // Asegúrate de reemplazar "TuClase" con el nombre real de tu clase

        // Crear un archivo temporal para la prueba
        File archivoTemp = null;
        try {
            archivoTemp = File.createTempFile("testFile", ".txt");
        } catch (IOException e) {
            fail("Error al crear el archivo temporal");
        }

        // Llamar al método save y verificar si no lanza excepciones
        try {
            colony.save(archivoTemp);
        } catch (ColonyException e) {
            fail("Se lanzó una excepción inesperada: " + e.getMessage());
        }

        // Verificar que el archivo ".dat" se ha creado
        File archivoFinal = new File(archivoTemp.getPath() + ".dat");
        assertTrue(archivoFinal.exists(), "El archivo .dat no se ha creado correctamente");
    }
    @Test
    public void openValidFileTest() {

        // Crear un archivo temporal para la prueba
        File archivoTemp = null;
        try {
            archivoTemp = File.createTempFile("testFile", ".dat");
            // Llenar el archivo con contenido válido
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivoTemp))) {
                out.writeObject("Colony storage\n");
                out.writeObject(new Colony());
            }
        } catch (IOException e) {
            fail("Error al crear el archivo temporal");
        }

        // Llamar al método open y verificar si no lanza excepciones
        try {
            Colony colonia = Colony.open(archivoTemp);
            assertNotNull(colonia, "La colonia no debería ser nula");
            // Puedes realizar más aserciones sobre la colonia si es necesario
        } catch (ColonyException | ClassNotFoundException e) {
            fail("Se lanzó una excepción inesperada: " + e.getMessage());
        }
    }

    @Test
    public void openInvalidFileTest() {
        File archivoTemp = null;
        try {
            archivoTemp = File.createTempFile("testFile", ".dat");
        } catch (IOException e) {
            fail("Error al crear el archivo temporal");
        }

        // Llamar al método open con un archivo que no tiene el encabezado correcto
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivoTemp))) {
            out.writeObject("Encabezado incorrecto\n");
        } catch (IOException e) {
            fail("Error al escribir en el archivo temporal");
        }

        // Verificar que se lance la excepción adecuada
        File finalArchivoTemp = archivoTemp;
        assertThrows(ColonyException.class, () -> Colony.open(finalArchivoTemp), "No se lanzó la excepción esperada para un archivo no válido");
    }

    @Test
    public void openNonexistentFileTest() {
        File archivoTemp = new File("ruta_no_existente");  // Reemplaza con una ruta que no exista

        // Verificar que se lance la excepción adecuada para un archivo que no existe
        assertThrows(ColonyException.class, () -> Colony.open(archivoTemp), "No se lanzó la excepción esperada para un archivo que no existe");
    }
    @Test
    public void importValidFileTest() {
        File archivoTemp = null;

        try {
            // Crear un archivo temporal con contenido válido
            archivoTemp = File.createTempFile("testFile", ".txt");
            try (PrintWriter writer = new PrintWriter(archivoTemp)) {
                writer.println("Food 1 1");
                writer.println("Ant 2 2");
                writer.println("Ant 3 3");
            }
        } catch (IOException e) {
            fail("Error al crear el archivo temporal");
        }

        // Llamar al método importt y verificar si no lanza excepciones
        try {
            Colony resultColony = Colony.importt(archivoTemp);
            assertNotNull(resultColony, "La colonia resultante no debería ser nula");
            // Puedes realizar más aserciones sobre la colonia resultante si es necesario
        } catch (Exception e) {
            fail("Se lanzó una excepción inesperada: " + e.getMessage());
        }
    }

    @Test
    public void importInvalidFileTest() {
        File archivoTemp = null;
        try {
            // Crear un archivo temporal con contenido inválido (entidad incompleta)
            archivoTemp = File.createTempFile("testFile", ".txt");
            try (PrintWriter writer = new PrintWriter(archivoTemp)) {
                writer.println("Food 1");  // Entidad incompleta
            }
        } catch (IOException e) {
            fail("Error al crear el archivo temporal");
        }
        // Verificar que se lance la excepción adecuada
        File finalArchivoTemp = archivoTemp;
        assertThrows(ColonyException.class, () -> Colony.importt(finalArchivoTemp), "No se lanzó la excepción esperada para un archivo no válido");
    }
    @Test
    public void importNonexistentFileTest() {
        // Verificar que se lance la excepción adecuada para un archivo que no existe
        assertThrows(ColonyException.class, () -> Colony.importt(new File("ruta_no_existente")), "No se lanzó la excepción esperada para un archivo que no existe");
    }
    @Test
    public void exportValidFileTest() {
        Colony colony = new Colony();
        // Supongamos que inicializas algunos elementos en la colonia para la prueba
        File archivoTemp;
        try {
            // Crear un archivo temporal
            archivoTemp = File.createTempFile("testFile", ".txt");
            // Llamar al método export y verificar si no lanza excepciones
            colony.export(archivoTemp);
            // Puedes realizar más aserciones si es necesario, como verificar el contenido del archivo
            assertTrue(archivoTemp.exists(), "El archivo exportado no existe");
        } catch (ColonyException | IOException e) {
            fail("Se lanzó una excepción inesperada: " + e.getMessage());
        }
    }
}
