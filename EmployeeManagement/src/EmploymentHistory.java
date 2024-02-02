import java.util.Arrays;
import java.util.List;

public class EmploymentHistory implements DbObject {
    private final int EmploymentHistoryId;
    private final String Position;
    private final String HiringDate;
    private final String EmployeeName;
    public EmploymentHistory(
        int EmploymentHistoryId,
        String Position,
        String HiringDate,
        String EmployeeName
    ) {
        this.EmploymentHistoryId = EmploymentHistoryId;
        this.Position = Position;
        this.HiringDate = HiringDate;
        this.EmployeeName = EmployeeName;
    }
    public int getEmploymentHistoryId() {
        return EmploymentHistoryId;
    }
    public String getPosition() {
        return Position;
    }
    public String getHiringDate() {
        return HiringDate;
    }
    public String getEmployeeName() {
        return EmployeeName;
    }
    static public String getTablePosition() {
        return "Employment History";
    }
    static public List<String> getFields() {
        return Arrays.asList(
            "Position",
            "Employment Date",
            "Employee Name"
        );
    }
    @Override
    public List<String> getValues() {
        return Arrays.asList(
            Position,
            HiringDate.toString(),
            EmployeeName
        );
    }
}
