import java.util.*;
import java.lang.*;
import java.util.Random;
/**
 * @author Cristian Alvarez - Juliana Briceño
 *
 */

public class Tensor {
    private int[][] data2D;
    private int[][][] data3D;
    private int[][][][] data4D;
    private int [] shape;
    /**
     * Constructs a tensor with the specified shape and value.
     *
     * @param shape The shape of the tensor.
     * @param value The value of all elements of the tensor.
     */
    public Tensor(int[] shape, int value) {
        this.shape = shape;
        switch(shape.length) {
            case 2:
                this.data2D = new int[shape[0]][shape[1]];
                for (int i = 0; i < shape[0]; i++) {
                    for (int j = 0; j < shape[1]; j++) {
                        this.data2D[i][j] = value;
                    }
                }
                break;
            case 3:
                this.data3D = new int[shape[0]][shape[1]][shape[2]];
                for (int i = 0; i < shape[0]; i++) {
                    for (int j = 0; j < shape[1]; j++) {
                        for (int k = 0; k < shape[2]; k++) {
                            this.data3D[i][j][k] = value;
                            
                        }
                    }
                }
                break;
            case 4:
                this.data4D = new int[shape[0]][shape[1]][shape[2]][shape[3]];
                for (int i = 0; i < shape[0]; i++) {
                    for (int j = 0; j < shape[1]; j++) {
                        for (int k = 0; k < shape[2]; k++) {
                            for (int l = 0; l < shape[3]; l++) {
                                this.data4D[i][j][k][l] = value;
                            }
                        }
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Número de dimensiones inválido");
            }
        }
    /**
     * Constructs a tensor with the specified shape and values.
     *
     * @param shape The shape of the tensor.
     * @param values The values of the tensor.
     */
    public Tensor(int[] shape, int[] values) {
        this.shape = shape;
        switch(shape.length) {
            case 2:
                this.data2D = new int[shape[0]][shape[1]];
                for (int i = 0; i < shape[0]; i++) {
                    for (int j = 0; j < shape[1]; j++) {
                        this.data2D[i][j] = values[i * shape[1] + j];
                    }
                }
                break;
            case 3:
                this.data3D = new int[shape[0]][shape[1]][shape[2]];
                for (int i = 0; i < shape[0]; i++) {
                    for (int j = 0; j < shape[1]; j++) {
                        for (int k = 0; k < shape[2]; k++) {
                            this.data3D[i][j][k] = values[i * shape[1] * shape[2] + j * shape[2] + k];
                        }
                    }
                }
                break;
            case 4:
                this.data4D = new int[shape[0]][shape[1]][shape[2]][shape[3]];
                for (int i = 0; i < shape[0]; i++) {
                    for (int j = 0; j < shape[1]; j++) {
                        for (int k = 0; k < shape[2]; k++) {
                            for (int l = 0; l < shape[3]; l++) {
                                this.data4D[i][j][k][l] = values[i * shape[1] * shape[2] * shape[3] + j * shape[2] * shape[3] + k * shape[3] + l];
                            }
                        }
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Número de dimensiones inválido");
            }
    }   
    /**
     * Gets the value of the tensor at the specified indices.
     *
     * @param index The indices of the value to get.
     * @return The value of the tensor at the specified indices.
     * @throws IllegalArgumentException If the number of indices does not match the number of dimensions of the tensor.
     */
    public int value(int[] index) {
        if (index.length != this.getNumberOfDimensions()) {
            throw new IllegalArgumentException("El número de índices no coincide con el número de dimensiones del tensor");
        }
        int value = 0;
        switch(this.getNumberOfDimensions()) {
            case 2:
                value = this.data2D[index[0]][index[1]];
                break;
            case 3:
                value = this.data3D[index[0]][index[1]][index[2]];
                break;
            case 4:
                value = this.data4D[index[0]][index[1]][index[2]][index[3]];
                break;
        }
        return value;
    }
     /**
     * Gets the number of dimensions of the tensor.
     *
     * @return The number of dimensions of the tensor.
     */
    public int getNumberOfDimensions() {
        return this.shape.length;
    }
    /**
     * Gets the number of elements of the tensor.
     */
    public int getNumberOfElements(){
        int value = 0;
        switch(getNumberOfDimensions()){
            case 2:
                value = this.shape[0]*this.shape[1];
                break;
            case 3:
                value = this.shape[0]*this.shape[1]*this.shape[2];
                break;
            case 4:
                value = this.shape[0]*this.shape[1]*this.shape[2]*this.shape[3];
                break;
        }
        return value;
    }
    /**
     * Gets the data2D of the tensor.
     */
    public int[][] getData2D(){
        return this.data2D;
    }
    /**
     * Gets the data3D of the tensor.
     */
    public int[][][] getData3D(){
        return this.data3D;
    }
    /**
     * Gets the data4D of the tensor.
     */
    public int[][][][] getData4D(){
        return this.data4D;
    }
    /**
     * Gets the shape of the tensor.
     *
     * @return The shape of the tensor.
     */
    public int[] getShape() {
        return this.shape;
    }
    
    /**
     * Gets the data of the tensor as a single array.
     *
     * @return The data of the tensor as a single array.
     */
    
    public int[] getDataIntoArray() {
    
        int[] data = new int[this.getNumberOfElements()];
        int arrayIndex = 0;
        switch(this.getNumberOfDimensions()) {
            case 2:
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        data[arrayIndex++] = this.data2D[i][j];
                    }
                }
                break;
            case 3:
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        for (int k = 0; k < this.shape[2]; k++) {
                            data[arrayIndex++] = this.data3D[i][j][k];
                        }
                    }
                }
                break;
            case 4:
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        for (int k = 0; k < this.shape[2]; k++) {
                            for (int l = 0; l < this.shape[3]; l++) {
                                data[arrayIndex++] = this.data4D[i][j][k][l];
                            }
                        }
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Tensor must have at most 4 dimensions");
        }
        return data;
        }
    /**
     * Reshapes the tensor's data into a new specified shape.
     *
     * @param shape An array of integers representing the new shape of the tensor.
     * @return A reference to the tensor with the data rearranged in the new shape.
     * @throws IllegalArgumentException If the length of the given shape does not match the number of dimensions of the tensor or if the total number of elements in the new shape does not match the total number of elements in the original tensor.
     */    
    public Tensor reshape(int[] shape){
        int checkData = 1;
        for(int i = 0; i < shape.length; i++){
            checkData *= shape[i];
        }
        // Check if the new shape is compatible with the number of elements in the original tensor
        if (this.getNumberOfElements() != checkData){
            throw new IllegalArgumentException("The given dimension are incorrect.");
        }
        // Get the data from the original tensor into an array
        int [] values = getDataIntoArray();
        this.data2D = null;
        this.data3D = null;
        this.data4D = null;
        // Perform the reshaping based on the length of the new shape
        switch(shape.length){
            case 2:
                this.data2D = new int[shape[0]][shape[1]];
                this.shape = shape;
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        this.data2D[i][j] = values[i * this.shape[1] + j];
                    }
                }
                break;
            case 3:  
                this.data3D = new int[shape[0]][shape[1]][shape[2]];
                this.shape = shape;
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        for (int k = 0; k < this.shape[2]; k++) {
                            this.data3D[i][j][k] = values[i * this.shape[1] * this.shape[2] + j * this.shape[2] + k];
                        }
                    }
                }
                break;
            case 4:
                this.data4D = new int[shape[0]][shape[1]][shape[2]][shape[3]];
                this.shape = shape;
                for (int i = 0; i < shape[0]; i++) {
                    for (int j = 0; j < shape[1]; j++) {
                        for (int k = 0; k < shape[2]; k++) {
                            for (int l = 0; l < shape[3]; l++) {
                                this.data4D[i][j][k][l] = values[i * shape[1] * shape[2] * shape[3] + j * shape[2] * shape[3] + k * shape[3] + l];
                            }
                        }
                    }
                }
                break;
        }
        return this;
    }
    /**
     * Return a Tensor with their values shuffle to a random mode.
     */
    public Tensor shuffle(){
        int[] values = getDataIntoArray();
        int[] shuffleArray = randomArray(values);
        this.data2D = null;
        this.data3D = null;
        this.data4D = null;
        // Perform the shuffle based on the length of the shape and using the shuffleArray.
        switch(this.shape.length){
            case 2:
                this.data2D = new int[shape[0]][shape[1]];
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        this.data2D[i][j] = shuffleArray[i * this.shape[1] + j];
                    }
                }
                break;
            case 3:  
                this.data3D = new int[shape[0]][shape[1]][shape[2]];
                this.shape = shape;
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        for (int k = 0; k < this.shape[2]; k++) {
                            this.data3D[i][j][k] = shuffleArray[i * this.shape[1] * this.shape[2] + j * this.shape[2] + k];
                        }
                    }
                }
                break;
            case 4:
                this.data4D = new int[shape[0]][shape[1]][shape[2]][shape[3]];
                this.shape = shape;
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        for (int k = 0; k < this.shape[2]; k++) {
                            for (int l = 0; l < this.shape[3]; l++) {
                                this.data4D[i][j][k][l] = shuffleArray[i * this.shape[1] * this.shape[2] * this.shape[3] + j * this.shape[2] * this.shape[3] + k * this.shape[3] + l];
                            }
                        }
                    }
                }
                break;
        }
        return this;
    }
    /**
     * Mix randomly the elements of a array.
     * @param array
     */
    public int[] randomArray(int[] array){
         Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            // Generamos un número aleatorio entre 0 y i
            int indiceAleatorio = random.nextInt(i + 1);

            // Intercambiamos los elementos en las posiciones i y indiceAleatorio
            int temp = array[i];
            array[i] = array[indiceAleatorio];
            array[indiceAleatorio] = temp;
        }
        return array;
    }
    /**
     * slice an array.
     * @param list
     * @start beginning of the slice
     * @end final element of the slice
     * @step how many steps to jump
     */
    public int[] slice(int[] list, int start, int end, int step) {
        int[] result = new int[0];
        for (int i = start; i < end; i += step) {
            result = Arrays.copyOf(result, result.length + 1);
            result[result.length - 1] = list[i];
        }
        return result;
    }

    /**
     * Adds the specified tensor to this tensor.
     *
     * @param other The tensor to add.
     * @return The sum of the two tensors.
     * @throws IllegalArgumentException If the tensors do not have the same number of dimensions.
     */

    public Tensor add(Tensor other) {
        if (this.getNumberOfDimensions() != other.getNumberOfDimensions() || this.getNumberOfElements() != other.getNumberOfElements()) {
            throw new IllegalArgumentException("Los tensores deben tener el mismo número de dimensiones");
        }
        Tensor result = new Tensor(this.shape, 0);
        switch(this.getNumberOfDimensions()) {
            case 2:
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        result.data2D[i][j] = this.data2D[i][j] + other.data2D[i][j];
                    }
                }
                break;
            case 3:
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        for (int k = 0; k < this.shape[2]; k++) {
                            result.data3D[i][j][k] = this.data3D[i][j][k] + other.data3D[i][j][k];
                        }
                    }
                }
                break;
            case 4:
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        for (int k = 0; k < this.shape[2]; k++) {
                            for (int l = 0; l < this.shape[3]; l++) {
                                result.data4D[i][j][k][l] = this.data4D[i][j][k][l] + other.data4D[i][j][k][l];
                            }
                        }
                    }
                }
                break;
        }
        return result;
    }
    /**
     * sustract the specified tensor to this tensor.
     *
     * @param other The tensor to sustract.
     * @return The sustraction of the two tensors.
     * @throws IllegalArgumentException If the tensors do not have the same number of dimensions.
     */
    public Tensor sustract(Tensor other) {
        if (this.getNumberOfDimensions() != other.getNumberOfDimensions() || this.getNumberOfElements() != other.getNumberOfElements()) {
            throw new IllegalArgumentException("Los tensores deben tener el mismo número de dimensiones");
        }
        Tensor result = new Tensor(this.shape, 0);
        switch(this.getNumberOfDimensions()) {
            case 2:
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        result.data2D[i][j] = this.data2D[i][j] - other.data2D[i][j];
                    }
                }
                break;
            case 3:
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        for (int k = 0; k < this.shape[2]; k++) {
                            result.data3D[i][j][k] = this.data3D[i][j][k] - other.data3D[i][j][k];
                        }
                    }
                }
                break;
            case 4:
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        for (int k = 0; k < this.shape[2]; k++) {
                            for (int l = 0; l < this.shape[3]; l++) {
                                result.data4D[i][j][k][l] = this.data4D[i][j][k][l] - other.data4D[i][j][k][l];
                            }
                        }
                    }
                }
                break;
        }
        return result;
    }
    /**
     * multiply the specified tensor to this tensor.
     *
     * @param other The tensor to multiply.
     * @return The multiplication of the two tensors.
     * @throws IllegalArgumentException If the tensors do not have the same number of dimensions.
     */
    public Tensor multiply(Tensor other) {
        if (this.getNumberOfDimensions() != other.getNumberOfDimensions() || this.getNumberOfElements() != other.getNumberOfElements()) {
            throw new IllegalArgumentException("Los tensores deben tener el mismo número de dimensiones");
        }
        Tensor result = new Tensor(this.shape, 0);
        switch(this.getNumberOfDimensions()) {
            case 2:
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        result.data2D[i][j] = this.data2D[i][j] * other.data2D[i][j];
                    }
                }
                break;
            case 3:
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        for (int k = 0; k < this.shape[2]; k++) {
                            result.data3D[i][j][k] = this.data3D[i][j][k] * other.data3D[i][j][k];
                        }
                    }
                }
                break;
            case 4:
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        for (int k = 0; k < this.shape[2]; k++) {
                            for (int l = 0; l < this.shape[3]; l++) {
                                result.data4D[i][j][k][l] = this.data4D[i][j][k][l] * other.data4D[i][j][k][l];
                            }
                        }
                    }
                }
                break;
        }
        return result;
    }
    
    /**
     * Checks if this tensor is equal to the specified tensor.
     *
     * @param other The tensor to compare to.
     * @return True if the tensors are equal, false otherwise.
     */
    public boolean equals(Tensor other) {
        if(!Arrays.equals(this.shape, other.shape)){
            return false;
        }
        switch(this.getNumberOfDimensions()) {
        case 2:
            for (int i = 0; i < this.shape[0]; i++) {
                for (int j = 0; j < this.shape[1]; j++) {
                    if(this.data2D[i][j] != other.data2D[i][j]){
                        return false;
                    }
                }
            }
            break;
        case 3:
            for (int i = 0; i < this.shape[0]; i++) {
                for (int j = 0; j < this.shape[1]; j++) {
                    for (int k = 0; k < this.shape[2]; k++) {
                        if(this.data3D[i][j][k] != other.data3D[i][j][k]){
                            return false;
                        }
                    }
                }
            }
            break;
        case 4:
            for (int i = 0; i < this.shape[0]; i++) {
                for (int j = 0; j < this.shape[1]; j++) {
                    for (int k = 0; k < this.shape[2]; k++) {
                        for (int l = 0; l < this.shape[3]; l++) {
                            if(this.data4D[i][j][k][l] != other.data4D[i][j][k][l]){
                                return false;
                            }
                        }
                    }
                }
            }
            break;
        }
        return true;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        return equals((Tensor) other);
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        switch (this.getNumberOfDimensions()) {
            case 2:
                sb.append("[[");
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        sb.append(this.data2D[i][j]);
                        if (j < this.shape[1] - 1) {
                            sb.append(", ");
                        }
                    }
                    if (i < this.shape[0] - 1) {
                        sb.append("], [");
                    } else {
                        sb.append("]");
                    }
                }
                sb.append("]");
                System.out.println(sb);
                break;
             case 3:
                sb.append("[[");
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        sb.append("[");
                        for (int k = 0; k < this.shape[2]; k++) {
                            sb.append(this.data3D[i][j][k]);
                            if (k < this.shape[2] - 1) {
                                sb.append(", ");
                            }
                        }
                        if(i != (shape[0] - 1) || j != shape[1] - 1){
                                if(j != shape[1] - 1){
                                    sb.append("], ");
                                }else{
                                    sb.append("]");
                                }
                            }
                    }
                    if(i != shape[0] - 1){
                        sb.append("], [");
                    }else{
                        sb.append("]");
                    }
                }
                sb.append("]]");
                break;
            case 4:
                sb.append("[[");
                for (int i = 0; i < this.shape[0]; i++) {
                    for (int j = 0; j < this.shape[1]; j++) {
                        sb.append("[");
                        for (int k = 0; k < this.shape[2]; k++) {
                            sb.append("[");
                            for (int l = 0; l < this.shape[3]; l++) {
                                sb.append(this.data4D[i][j][k][l]);
                                if (l < this.shape[3] - 1) {
                                    sb.append(", ");
                                }
                            }
                            sb.append("]");
                            if (k < this.shape[2] - 1) {
                                sb.append(", ");
                            }
                        }
                        if (i != this.shape[0] - 1 || j != this.shape[1] - 1) {
                            if (j != this.shape[1] - 1) {
                                sb.append("], ");
                            } else {
                                sb.append("]");
                            }
                        }
                    }
                    if (i != this.shape[0] - 1) {
                        sb.append("], [");
                    } else {
                        sb.append("]");
                    }
                }
                sb.append("]]");
                break;
            default:
                break;
        }     
        return sb.toString();
    }
}

 



    