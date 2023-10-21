import java.util.HashMap;

/** NPTensor.java
 * 
 * @author Cristian Alvarez - Juliana Briceño
 */
    
public class NPTensor{
    
    private HashMap<String, Tensor> variables;
    private boolean isOk;
    /**
     * Constructs a new NPTensor object.
     */
    public NPTensor() {
        // Initialize the hashmap
        variables = new HashMap<>();
        isOk = false;
    }
    /**
     * Gets the hashmap of variables.
     *
     * @return The hashmap of variables.
     */
    public HashMap getVariables(){
        return this.variables;
    }
    /**
     * Assigns a tensor to a variable.
     *
     * @param name The name of the variable.
     * @param shape The shape of the tensor.
     * @param value The value of all elements of the tensor.
     */
    public void assign(String name, int shape[], int value ){
        // Check if the variable already exists
        // using the hashmap
        
        if (variables.containsKey(name)) {
            this.isOk = false;
            throw new IllegalArgumentException("Variable already exists");
        }
        // Create a new tensor
        Tensor tensor = new Tensor(shape, value);
        // Add the tensor to the variables
        variables.put(name, tensor);
        this.isOk = true;
    }    
     /**
     * Assigns a tensor to a variable.
     *
     * @param name The name of the variable.
     * @param shape The shape of the tensor.
     * @param values The values of the tensor.
     */
    public void assign(String name, int shape[], int[] values) {
        // Check if the variable already exists
        // using the hashmap
        
        if (variables.containsKey(name)) {
            this.isOk = false;
            throw new IllegalArgumentException("Variable already exists");
        }
    
        // Create a new tensor
        Tensor tensor = new Tensor(shape, values);
    
        // Add the tensor to the variables
        variables.put(name, tensor);
        this.isOk = true;
    }
     /**
     * Gets the value of the tensor associated with the specified variable.
     *
     * @param key The name of the variable.
     * @return The value of the tensor associated with the specified variable.
     */
    public String consult(String key){
        if(!variables.containsKey(key)){
            this.isOk = false;
            throw new IllegalArgumentException("Tensor don't exist.");
        }
        this.isOk = true;
        return(variables.get(key).toString());
    }
    /**
     *Assigns the value of an operation to a variable (unary operations)

     *
     * @param a The name of the variable to be asigned.
     * @param unary The shape of the tensor.
     * @param b The name of the variable to be take.
     */
    // a := unary b
    //The unary operators are: shape, reshape, shuffle
    public void assign(String a, String unary, String b) {
        // Check if the variable b exists
        this.isOk = false;
        Tensor tensorB = variables.get(b);
        
        if(tensorB == null){
            this.isOk = false;
            throw new IllegalArgumentException("Variable not exists");
        }
        // Get the shape and data of the tensor b
        int[] shape = tensorB.getShape();
        int[] values = tensorB.getDataIntoArray();
        // Do the unary operation
        if (unary.equals("shape")) {
            // Assign the shape to the tensor a
            assign(a, shape, values);
            this.isOk = true;
        }
        else if(unary.equals("reshape")){
            assign(a, shape, values);
            // Reverse the shape
            int[] shapeInvertido = new int[shape.length];
            for (int i = 0; i < shape.length; i++) {
                shapeInvertido[i] = shape[
                shape.length - i - 1];
            }
            // Reshape the tensor b into the inverted shape
            // Assign the reshaped tensor to the tensor a
            variables.get(a).reshape(shapeInvertido);
            this.isOk = true;
        }
        else if(unary.equals("shuffle")){
            // Shuffle the tensor b
            // Assign the shuffled tensor to the tensor a
            assign(a, shape, values);
            variables.get(a).shuffle();
            this.isOk = true;
        }else{
            // Throw an exception if the unary operation is not recognized
            this.isOk = false;
            throw new IllegalArgumentException("Funcion no reconocida");
        }
    }
    
    /**
     *Assigns the value of an operation to a variable (unary operations)

     *
     * @param a The name of the variable to be asigned.
     * @param unary The shape of the tensor.
     * @param b The name of the variable to be taken.
     * @param parameters
     */
    // a := unary b
    //The unary operators with parameters are: slide [start, end, step], mean (axis), find (value)
    public void assign(String a, String unary, String b, int [] parameters){
        Tensor tensorB = variables.get(b);
        this.isOk = false;
        if(tensorB == null){
            this.isOk = false;
            throw new IllegalArgumentException("Variable not exists");
        }
        int[] shape = tensorB.getShape();
        int[] values = tensorB.getDataIntoArray();
        if (unary.equals("slice")) {
            if(parameters.length == 3){
                int [] sliceList = tensorB.slice(values, parameters[0], parameters[1], parameters[2]);
                int [] sliceShape = {1 , sliceList.length};
                assign(a, sliceShape, sliceList);
                this.isOk = true;
            }
            else if(parameters.length == 2){
                int [] sliceList = tensorB.slice(values, parameters[0], parameters[1], 1);
                int [] sliceShape = {1 , sliceList.length};
                assign(a, sliceShape, sliceList);
                this.isOk = true;
            }
            else{
                this.isOk = false;
                throw new IllegalArgumentException("Wrong number of parameters.");
            }
        }
        else if(unary.equals("mean")){
            if(parameters[0] == 0){
                switch(tensorB.getNumberOfDimensions()){
                    case 2:
                        int [][] list = tensorB.getData2D();
                        int [] meanList = meanColumns2D(list);
                        int [] defaultShape = {1, meanList.length};
                        assign(a, defaultShape, meanList);
                        this.isOk = true;
                        break;
                    case 3:
                        int [][][] list1 = tensorB.getData3D();
                        int [] meanList1 = meanColumns3D(list1);
                        int [] defaultShape1 = {1, 1, meanList1.length};
                        assign(a, defaultShape1, meanList1);
                        this.isOk = true;
                        break;
                    case 4:
                        int [][][][] list2 = tensorB.getData4D();
                        int [] meanList2 = meanColumns4D(list2);
                        int [] defaultShape2 = {1, 1, 1, meanList2.length};
                        assign(a, defaultShape2, meanList2);
                        this.isOk = true;
                        break;
                }
            }
            else if(parameters[0] == 1){
                switch(tensorB.getNumberOfDimensions()){
                    case 2:
                        int [][] list = tensorB.getData2D();
                        int [] meanList = meanRows2D(list);
                        int [] defaultShape = {1, meanList.length};
                        assign(a, defaultShape, meanList);
                        this.isOk = true;
                        break;
                    case 3:
                        int [][][] list1 = tensorB.getData3D();
                        int [] meanList1 = meanRows3D(list1);
                        int [] defaultShape1 = {1, 1, meanList1.length};
                        assign(a, defaultShape1, meanList1);
                        this.isOk = true;
                        break;
                    case 4:
                        int [][][][] list2 = tensorB.getData4D();
                        int [] meanList2 = meanRows4D(list2);
                        int [] defaultShape2 = {1, 1, 1, meanList2.length};
                        assign(a, defaultShape2, meanList2);
                        this.isOk = true;
                        break;
                }
            }else{
                this.isOk = false;
                throw new IllegalArgumentException("Only valid axis = 0 or axis = 1.");
            }
        }
        else if(unary.equals("find")){
            if(parameters.length > 1){
                this.isOk = false;
                throw new IllegalArgumentException("Only valid one value for search");
            }
            int [] indexList = findIndices(values, parameters[0]);
            int [] defaultShape = {1, indexList.length};
            this.isOk = true;
            assign(a, defaultShape, indexList);
        }
    }
    /**
     *Assigns the value of an operation to a variable (simple binary operations)

     *
     * @param a The name of the variable to be asigned.
     * @param sBinary The shape of the tensor.
     * @param b The name of the variable to be operated.
     * @param c The name of the variable to be operated.
     */
    //
    // a := b simple c
    //The simple operators are: +, -, * (one to one)
    public void assign(String a, String b, String sBinary, String c){
        Tensor tensorB = variables.get(b);
        Tensor tensorC = variables.get(c);     
        this.isOk = false;
        if(tensorB == null || tensorC == null){
            this.isOk = false;
            throw new IllegalArgumentException("The tensors has to exists");
        }
        if(sBinary.equals("+")){
            Tensor tensorAdd = tensorB.add(tensorC);
            int [] shape = tensorAdd.getShape();
            int [] values = tensorAdd.getDataIntoArray();
            assign(a, shape, values);
            this.isOk = true;
        }
        else if(sBinary.equals("-")){
            Tensor tensorSustract = tensorB.sustract(tensorC);
            int [] shape = tensorSustract.getShape();
            int [] values = tensorSustract.getDataIntoArray();
            assign(a, shape, values);
            this.isOk = true;
        }
        else if(sBinary.equals("*")){
            Tensor tensorMultiply = tensorB.multiply(tensorC);
            int [] shape = tensorMultiply.getShape();
            int [] values = tensorMultiply.getDataIntoArray();
            assign(a, shape, values);
            this.isOk = true;
        }else{
            this.isOk = false;
            throw new IllegalArgumentException("The operation don't exists.");
        }
    }   
    
    /**
     * Returns the string representtion of a tensor
     * @param variable the key of the Tensor.
     */
    //Returns the string representtion of a tensor
    public String toString(String variable){
        this.isOk = true;
        return variables.get(variable).toString();
    }
    /**
     * return the mean of the columns in a 2D list
     * @param list is a list2D
     */
    private static int[] meanColumns2D(int[][] list) {
        int[] result = new int[list[0].length];
        for (int j = 0; j < list[0].length; j++) {
            int sum = 0;
            for (int i = 0; i < list.length; i++) {
                sum += list[i][j];
            }
            result[j] = (int) Math.round(sum / list.length);
        }
        return result;
    }
    /**
     * return the mean of the rows in a 2D list
     * @param list is a list2D
     */
    private static int[] meanRows2D(int[][] list) {
        int[] result = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            int sum = 0;
            for (int j = 0; j < list[0].length; j++) {
                sum += list[i][j];
            }
            result[i] = (int) Math.round(sum / list[0].length);
        }
        return result;
    }
    /**
     * return the mean of the columns in a 3D list
     * @param list is a list3D
     */
    private static int[] meanColumns3D(int[][][] list) {
        int[] result = new int[list[0][0].length];
        for (int k = 0; k < list[0].length; k++) {
            for (int j = 0; j < list[0][0].length; j++) {
                int sum = 0;
                for (int i = 0; i < list.length; i++) {
                    sum += list[i][k][j];
                }
                result[j] += (int) Math.round(sum / list.length);
            }
        }
        return result;
    }
    /**
     * return the mean of the rows in a 3D list
     * @param list is a list3D
     */
    private static int[] meanRows3D(int[][][] list) {
        int[] result = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            int sum = 0;
            for (int j = 0; j < list[0][0].length; j++) {
                for (int k = 0; k < list[0].length; k++) {
                    sum += list[i][j][k];
                }
            }
            result[i] = (int) Math.round(sum / (list[0][0].length * list[0].length));
        }
        return result;
    }
    /**
     * return the mean of the columns in a 4D list
     * @param list is a list4D
     */
    private static int[] meanColumns4D(int[][][][] list) {
        int[] result = new int[list[0][0][0].length];
        for (int l = 0; l < list[0].length; l++) {
            for (int k = 0; k < list[0][0].length; k++) {
                for (int j = 0; j < list[0][0][0].length; j++) {
                    int sum = 0;
                    for (int i = 0; i < list.length; i++) {
                        sum += list[i][l][k][j];
                    }
                    result[j] += (int) Math.round(sum / list.length);
                }
            }
        }
        return result;
    }
    /**
     * return the mean of the rows in a 4D list
     * @param list is a list4D
     */
    private static int[] meanRows4D(int[][][][] list) {
        int[] result = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            int sum = 0;
            for (int j = 0; j < list[0][0][0].length; j++) {
                for (int k = 0; k < list[0][0].length; k++) {
                    for (int l = 0; l < list[0].length; l++) {
                        sum += list[i][l][k][j];
                    }
                }
            }
            result[i] = (int) Math.round(sum / (list[0][0][0].length * list[0][0].length * list[0].length));
        }
        return result;
    }
    /**
     * return the index of a determinated value
     * @param list
     * @param value to find.
     */
    private static int[] findIndices(int[] list, int value) {
        int[] lista = new int[list.length];
             for (int i = 0; i < list.length; i++) {
                if (list[i] == value) {
                        lista[i] = i;
                }
            }
        return lista;
    }
     /**
     * return the state of the last operation was successful
     * 
     */
    //
    public boolean ok(){
        return isOk;
    }
}
    



