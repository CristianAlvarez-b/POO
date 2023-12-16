package domain;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;

import java.io.File;

/**
 * Clase que proporciona funciones para el registro de excepciones en un archivo de registro.
 */
public class Log {
    public static String nombre = "gomokuLog";
    /**
     * Método para registrar una excepción en un archivo de registro.
     *
     * @param e Excepción a registrar.
     */
    public static void record(Exception e) {
        try {
            Logger logger = Logger.getLogger(nombre);
            logger.setUseParentHandlers(false);

            String currentDirectory = System.getProperty("user.dir");
            File file = new File(currentDirectory + "/log/gomoku.log");

            FileHandler fileHandler = new FileHandler(file.toString(), true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.log(Level.SEVERE, e.toString(), e);
            fileHandler.close();
        } catch (Exception oe) {
            oe.printStackTrace();
            System.exit(0);
        }
    }
}