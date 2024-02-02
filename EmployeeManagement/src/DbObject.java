import javax.swing.*;
import java.util.List;

public interface DbObject {
    List<String> getValues();
    static String[] convertListToArray(List<String> list) {
        String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
    static String[][] convertListToMatrix(List<? extends DbObject> list) {
        String[][] matrix = new String[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            matrix[i] = list.get(i).getValues().toArray(new String[0]);

        }
        return matrix;
    }
}
