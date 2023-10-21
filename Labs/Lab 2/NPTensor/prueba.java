import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 * Write a description of class prueba here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class prueba
{
    @Test
    public void shouldPass() {
        int result = divide(10, 2);
        assertEquals(5, result);
    }

    @Test
    public void shouldFail() {
        int result = subtract(5, 3);
        assertEquals(2, result);
    }

    @Test
    public void shouldErr() {
        try {
            int result = divide(10, 0);
        } catch (ArithmeticException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "Cannot divide by zero");
            return;
        }
        
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }
}
