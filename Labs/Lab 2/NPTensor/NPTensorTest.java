
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 * The test class NPTensorTest.
 *
 * @author  (Cristian Alvarez - Juliana Briceño)
 * @version (1.0)
 */
public class NPTensorTest
{
    private NPTensor npTensor;
    @BeforeClass
    public static void beforeClass(){
        
    }
    
    @Before
    public void setUp(){
        npTensor = new NPTensor();
    }
    //Ciclo 1
    @Test
    public void shouldAssignTensorOneValue() {
        String tensorOneValue = "A";
        int [] shape = {2,2};
        int value = 4;
        npTensor.assign(tensorOneValue, shape, value);
        assertEquals("[[4, 4], [4, 4]]", npTensor.getVariables().get(tensorOneValue).toString());
    }
    @Test
    public void shouldNotAssignTensorOneValue(){
        String tensorOneValue = "A";
        int [] shape = {2,2};
        int value = 4;
        npTensor.assign("A", shape , value);
        // Se espera que la asignación falle
        try {
            npTensor.assign(tensorOneValue, shape, value);
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "Variable already exists");
            return;
        }
    }
    
    @Test
    public void shouldAssignTensorGivenValues(){
        String tensorGivenValues = "B";
        int [] shape = {2,2};
        int values [] = {1,2,3,4};
        npTensor.assign(tensorGivenValues, shape, values);
        assertEquals("[[1, 2], [3, 4]]", npTensor.getVariables().get(tensorGivenValues).toString());
    }
    
    @Test
    public void shouldNotAssignTensorGivenValues(){
        String tensorOneValue = "A";
        int [] shape = {2,2};
        int [] values = {1,2,3,4};
        npTensor.assign("A", shape , values);
        // Se espera que la asignación falle
        try {
            npTensor.assign(tensorOneValue, shape, values);
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "Variable already exists");
            return;
        }
    }

    @Test
    public void shouldConsult(){
        int [] shape = {2,2};
        int value = 4;
        npTensor.assign("A", shape , value);
        assertEquals("[[4, 4], [4, 4]]", npTensor.consult("A"));
    }
    @Test
    public void shouldNotConsultDontExist(){
        try {
            npTensor.consult("A");
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "Tensor don't exist.");
            return;
        }
    }

    //Ciclo 2

    @Test 
    public void shouldAssignSameShape(){
        int [] shape = {2,2};
        int value = 3;
        String tensorA = "A";
        String tensorB = "B";
        npTensor.assign(tensorA ,shape, value);
        npTensor.assign(tensorB, "shape", tensorA);
        assertEquals(npTensor.getVariables().get(tensorA).toString(), npTensor.getVariables().get(tensorB).toString());    
    }
    @Test
    public void shouldAssignReShape(){
        int [] shape = {3,4};
        int value = 1;
        String tensorA = "A";
        String tensorB = "B";
        npTensor.assign(tensorA ,shape, value);
        npTensor.assign(tensorB, "reshape", tensorA);
        assertEquals("[[1, 1, 1], [1, 1, 1], [1, 1, 1], [1, 1, 1]]", npTensor.getVariables().get(tensorB).toString());    
        
    }
    @Test
    public void shouldAssignShuffle(){
        int [] shape = {2,2};
        int [] values = {1,2,3,4};
        String tensorA = "A";
        String tensorB = "B";
        npTensor.assign(tensorA ,shape, values);
        npTensor.assign(tensorB, "shuffle", tensorA);
        assertFalse(npTensor.getVariables().get(tensorB).toString().equals(npTensor.getVariables().get(tensorA).toString()));
    }
    @Test
    public void shouldNotAssignVariableNotExists(){
        int[] shape = {2,2};
        int value = 2;
        try {
            npTensor.assign("A", "shape", "B");
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "Variable not exists");
            return;
        }
    }
    @Test
    public void shouldNotAssignUnknowOperation(){
        int[] shape = {2,2};
        int value = 2;
        npTensor.assign("C", shape , value);
        try {
            npTensor.assign("B", "dont" , "C");
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "Funcion no reconocida");
            return;
        }
    }
    //Ciclo 3
    @Test
    public void shouldAssignSlice(){
        int [] shape = {3,3};
        int [] values = {1,2,3,4,5,6,7,8,9};
        npTensor.assign("TensorB", shape , values);
        int [] parameters = {1,5};
        npTensor.assign("TensorA", "slice", "TensorB", parameters);
        assertEquals("[[2, 3, 4, 5]]", npTensor.getVariables().get("TensorA").toString());
    }
    @Test
    public void shouldNotAssignSlice(){
        int [] shape = {3,3};
        int [] values = {1,2,3,4,5,6,7,8,9};
        npTensor.assign("TensorB", shape , values);
        int [] parameters = {1};
        try {
            npTensor.assign("TensorA", "slice", "TensorB", parameters);
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "Wrong number of parameters.");
            return;
        }
    }
    @Test
    public void shouldAssignMean(){
        int [] shape = {3,3};
        int [] values = {1,2,3,4,5,6,7,8,9};
        npTensor.assign("TensorB", shape , values);
        int [] parameters = {0};
        npTensor.assign("TensorA", "mean", "TensorB", parameters);
        assertEquals("[[4, 5, 6]]", npTensor.getVariables().get("TensorA").toString());
    }
    @Test
    public void shouldNotAssignMean(){
        int [] shape = {3,3};
        int [] values = {1,2,3,4,5,6,7,8,9};
        npTensor.assign("TensorB", shape , values);
        int [] parameters = {3};
        try {
            npTensor.assign("TensorA", "mean", "TensorB", parameters);
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "Only valid axis = 0 or axis = 1.");
            return;
        }
    }
    @Test
    public void shouldAssignFind(){
        int [] shape = {2,2};
        int [] values = {1,2,3,4};
        npTensor.assign("TensorB", shape , values);
        int [] parameters = {3};
        npTensor.assign("TensorA", "find", "TensorB", parameters);
        assertEquals("[[0, 0, 2, 0]]", npTensor.getVariables().get("TensorA").toString());
    }
    @Test
    public void shouldNotAssignFind(){
        int [] shape = {2,2};
        int [] values = {1,2,3,4};
        npTensor.assign("TensorB", shape , values);
        int [] parameters = {3, 2};
        try {
            npTensor.assign("TensorA", "find", "TensorB", parameters);
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "Only valid one value for search");
            return;
        }
    }
      
    //Ciclo 4
    @Test
    public void shouldAssignAddition(){
        int [] shape = {2,2};
        int value = 2;
        npTensor.assign("TensorB", shape , value);
        npTensor.assign("TensorC", shape , value);
        npTensor.assign("TensorA", "TensorB", "+", "TensorC");
        assertEquals("[[4, 4], [4, 4]]", npTensor.getVariables().get("TensorA").toString());
    }
    @Test
    public void shouldAssignSustract(){
        int [] shape = {2,2};
        int value = 2;
        npTensor.assign("TensorB", shape , value);
        npTensor.assign("TensorC", shape , value);
        npTensor.assign("TensorA", "TensorB", "-", "TensorC");
        assertEquals("[[0, 0], [0, 0]]", npTensor.getVariables().get("TensorA").toString());
    }
    @Test
    public void shouldAssignMultiply(){
        int [] shape = {2,2};
        int value = 3;
        npTensor.assign("TensorB", shape , value);
        npTensor.assign("TensorC", shape , value);
        npTensor.assign("TensorA", "TensorB", "*", "TensorC");
        assertEquals("[[9, 9], [9, 9]]", npTensor.getVariables().get("TensorA").toString());
    }
    @Test
    public void shouldNotAssignAdditionDifferentDimensions(){
        int [] shape1 = {2,2};
        int [] shape2 = {3,3};
        int value = 2;
        npTensor.assign("TensorB", shape1 , value);
        npTensor.assign("TensorC", shape2 , value);
        try {
            npTensor.assign("TensorA", "TensorB", "+", "TensorC");
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "Los tensores deben tener el mismo número de dimensiones");
            return;
        }
    }
    @Test
    public void shouldNotAssignNotExistTensor(){
        int [] shape1 = {2,2};
        int value = 2;
        npTensor.assign("TensorB", shape1 , value);
        try {
            npTensor.assign("TensorA", "TensorB", "+", "TensorC");
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The tensors has to exists");
            return;
        }
    }
    @Test
    public void shouldNotAssignUnknowOpe(){
        int [] shape1 = {2,2};
        int [] shape2 = {3,3};
        int value = 2;
        npTensor.assign("TensorB", shape1 , value);
        npTensor.assign("TensorC", shape2 , value);
        try {
            npTensor.assign("TensorA", "TensorB", "Wow", "TensorC");
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The operation don't exists.");
            return;
        }
    }
}
