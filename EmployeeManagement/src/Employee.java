import java.util.Arrays;
import java.util.List;

public class Employee implements DbObject {
    private final int EmployeeId;
    private final String Name;
    public Employee(
        int EmployeeId,
        String Name
    ) {
        this.EmployeeId = EmployeeId;
        this.Name = Name;
    }
    public int getEmployeeId() {
        return EmployeeId;
    }
    public String getName() {
        return Name;
    }
    static public String getTableName() {
        return "Employee";
    }
    static public List<String> getFields() {
        return Arrays.asList(
            "Name"
        );
    }
    @Override
    public List<String> getValues() {
        return Arrays.asList(
            Name
        );
    }
}
