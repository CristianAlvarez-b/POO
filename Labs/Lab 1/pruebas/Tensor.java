import java.util.ArrayList;

public class Tensor {
    private ArrayList<Integer> data;
    private int[] shape;

    public Tensor(int[] shape, int value) {
        this.shape = shape;
        this.data = new ArrayList<>();
        fillTensor(0, value);
    }

    private void fillTensor(int dimension, int value) {
        if (dimension == shape.length) {
            data.add(value);
        } else {
            for (int i = 0; i < shape[dimension]; i++) {
                fillTensor(dimension + 1, value);
            }
        }
    }

    public ArrayList<Integer> getData() {
        return data;
    }

    public static void main(String[] args) {
        int[] shape = {2, 2, 3};
        int value = 5;
        Tensor tensor = new Tensor(shape, value);
        ArrayList<Integer> tensorData = tensor.getData();

        int dataIndex = 0;

        // Imprimir el tensor
        for (int i = 0; i < shape[0]; i++) {
            for (int j = 0; j < shape[1]; j++) {
                for (int k = 0; k < shape[2]; k++) {
                    System.out.print(tensorData.get(dataIndex) + " ");
                    dataIndex++;
                }
                System.out.print("| ");
            }
            System.out.println();
        }
    }
}
