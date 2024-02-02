import java.util.Arrays;
import java.util.List;

public class Department implements DbObject {
    private final int DepartmentId;
    private final String Name;
    private final int Budget;
    private final String Manager;
    public Department(
        int DepartmentId,
        String Name,
        int Budget,
        String Manager
    ) {
        this.DepartmentId = DepartmentId;
        this.Name = Name;
        this.Budget = Budget;
        this.Manager = Manager;
    }
    public int getDepartmentId() {
        return DepartmentId;
    }
    public String getName() {
        return Name;
    }
    public int getBudget() {
        return Budget;
    }
    public String getManager() {
        return Manager;
    }
    static public String getTableName() {
        return "Department";
    }
    static public List<String> getFields() {
        return Arrays.asList(
            "Name",
            "Budget",
            "Manager"
        );
    }
    @Override
    public List<String> getValues() {
        return Arrays.asList(
            Name,
            String.valueOf(Budget),
            Manager
        );
    }
}
