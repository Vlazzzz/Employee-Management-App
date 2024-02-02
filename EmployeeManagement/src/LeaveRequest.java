import java.util.Arrays;
import java.util.List;

public class LeaveRequest implements DbObject {
    private final int LeaveRequestId;
    private final String LeaveRequestNumber;
    private final float SalaryPercentage;
    private final String StartDate;
    private final String EndDate;
    private final String Employee;
    public LeaveRequest(
        int LeaveRequestId,
        String LeaveRequestNumber,
        float SalaryPercentage,
        String StartDate,
        String EndDate,
        String Employee
    ) {
        this.LeaveRequestId = LeaveRequestId;
        this.LeaveRequestNumber = LeaveRequestNumber;
        this.SalaryPercentage = SalaryPercentage;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.Employee = Employee;
    }
    public int getLeaveRequestId() {
        return LeaveRequestId;
    }
    public String getLeaveRequestNumber() {
        return LeaveRequestNumber;
    }
    public float getSalaryPercentage() {
        return SalaryPercentage;
    }
    public String getStartDate() {
        return StartDate;
    }
    public String getEndDate() {
        return EndDate;
    }
    public String getEmployee() {
        return Employee;
    }
    static public String getTableName() {
        return "Leave Request";
    }
    static public List<String> getFields() {
        return Arrays.asList(
            "Leave Request Number",
            "Salary Percentage",
            "Start Date",
            "End Date"
        );
    }
    @Override
    public List<String> getValues() {
        return Arrays.asList(
            LeaveRequestNumber,
            String.valueOf(SalaryPercentage),
            StartDate.toString(),
            EndDate.toString()
        );
    }
}
