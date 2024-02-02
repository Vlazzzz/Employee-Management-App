import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DbUtils {
    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
                "databaseName=dbEmployeeSystem;user=sa1;password=1q2w3e;encrypt=true;trustServerCertificate=true";

            connection = DriverManager.getConnection(connectionUrl);

            if (connection != null) {
                System.out.println("Connected");
            } else {
                System.out.println("Not connected");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Employee> selectAllEmployees() {
        Connection connection = DbUtils.getConnection();

        List<Employee> employees = new ArrayList<>();

        try {
            String storedProcedure = "{call spEmployeeSelectAllActive}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    int employeeId = resultSet.getInt("EmployeeId");
                    String name = resultSet.getString("Name");

                    Employee employee = new Employee(employeeId, name);
                    employees.add(employee);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return employees;
    }

    public static Employee selectEmployeeAllActiveWhere(String employeeName) {
        Connection connection = DbUtils.getConnection();

        Employee employee = null;

        try {
            String storedProcedure = "{call spEmployeeSelectAllActiveWhereName(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, employeeName);
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    int employeeId = resultSet.getInt("EmployeeId");
                    String name = resultSet.getString("Name");

                    employee = new Employee(employeeId, name);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return employee;
    }

    public static void insertEmployee(String name) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spEmployeeInsert(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, name);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }
    }

    public static void updateEmployee(String oldName, String newName) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spEmployeeUpdate(?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, oldName);
                callableStatement.setString(2, newName);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }
    }

    public static Void softDeleteEmployee(String name) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spEmployeeSoftDelete(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, name);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return null;
    }

    public static List<EmploymentHistory> selectAllEmploymentHistorys() {
        Connection connection = DbUtils.getConnection();

        List<EmploymentHistory> EmploymentHistorys = new ArrayList<>();

        try {
            String storedProcedure = "{call spEmploymentHistorySelectAllActive}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    int EmploymentHistoryId = resultSet.getInt("EmploymentHistoryId");
                    String position = resultSet.getString("Position");
                    java.util.Date date = resultSet.getDate("HiringDate");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String EmployeeName = resultSet.getString("EmployeeName");

                    EmploymentHistory EmploymentHistory = new EmploymentHistory(EmploymentHistoryId, position, dateFormat.format(date), EmployeeName);
                    EmploymentHistorys.add(EmploymentHistory);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return EmploymentHistorys;
    }

    public static EmploymentHistory selectEmploymentHistoryAllActiveWhere(String EmploymentHistoryName) {
        Connection connection = DbUtils.getConnection();

        EmploymentHistory EmploymentHistory = null;

        try {
            String storedProcedure = "{call spEmploymentHistorySelectAllActiveWhere(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, EmploymentHistoryName);
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    int EmploymentHistoryId = resultSet.getInt("EmploymentHistoryId");
                    String position = resultSet.getString("Position");
                    java.util.Date date = resultSet.getDate("HiringDate");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String EmployeeName = resultSet.getString("EmployeeName");

                    EmploymentHistory = new EmploymentHistory(EmploymentHistoryId, position, dateFormat.format(date), EmployeeName);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return EmploymentHistory;
    }

    public static void insertEmploymentHistory(String position, String hiringDate, String EmployeeName) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spEmploymentHistoryInsert(?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, position);
                java.sql.Date sqlDate = convertToSqlDate(hiringDate);
                callableStatement.setDate(2, sqlDate);
                callableStatement.setString(3, EmployeeName);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            UiUtils.showErrorMessage(e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }
    }

    public static void updateEmploymentHistory(String oldPosition, String newPosition, String hiringDate, String EmployeeName) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spEmploymentHistoryUpdate(?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, oldPosition);
                callableStatement.setString(2, newPosition);
                java.sql.Date sqlDate = convertToSqlDate(hiringDate);
                callableStatement.setDate(3, sqlDate);
                callableStatement.setString(4, EmployeeName);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }
    }

    public static Void softDeleteEmploymentHistory(String position) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spEmploymentHistorySoftDelete(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, position);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return null;
    }

    public static List<LeaveRequest> selectAllLeaveRequests() {
        Connection connection = DbUtils.getConnection();

        List<LeaveRequest> LeaveRequests = new ArrayList<>();

        try {
            String storedProcedure = "{call spLeaveRequestSelectAllActive}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    int LeaveRequestId = resultSet.getInt("LeaveRequestId");
                    String LeaveRequestNumber = resultSet.getString("LeaveRequestNumber");
                    int SalaryPercentage = resultSet.getInt("SalaryPercentage");
                    java.util.Date StartDate = resultSet.getDate("StartDate");
                    java.util.Date EndDate = resultSet.getDate("EndDate");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String employee = resultSet.getString("Employee");

                    LeaveRequest LeaveRequest = new LeaveRequest(LeaveRequestId, LeaveRequestNumber, SalaryPercentage, dateFormat.format(StartDate), dateFormat.format(EndDate), employee);
                    LeaveRequests.add(LeaveRequest);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return LeaveRequests;
    }

    public static LeaveRequest selectLeaveRequestAllActiveWhere(String LeaveRequestName) {
        Connection connection = DbUtils.getConnection();

        LeaveRequest LeaveRequest = null;

        try {
            String storedProcedure = "{call spLeaveRequestSelectAllActiveWhere(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, LeaveRequestName);
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    int LeaveRequestId = resultSet.getInt("LeaveRequestId");
                    String LeaveRequestNumber = resultSet.getString("LeaveRequestNumber");
                    int SalaryPercentage = resultSet.getInt("SalaryPercentage");
                    java.util.Date StartDate = resultSet.getDate("StartDate");
                    java.util.Date EndDate = resultSet.getDate("EndDate");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String employee = resultSet.getString("Employee");

                    LeaveRequest = new LeaveRequest(LeaveRequestId, LeaveRequestNumber, SalaryPercentage, dateFormat.format(StartDate), dateFormat.format(EndDate), employee);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return LeaveRequest;
    }

    public static void insertLeaveRequest(String LeaveRequestNumber, float SalaryPercentage, String StartDate, String EndDate, String employee) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spLeaveRequestInsert(?, ?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, LeaveRequestNumber);
                callableStatement.setFloat(2, SalaryPercentage);
                java.sql.Date sqlDate = convertToSqlDate(StartDate);
                callableStatement.setDate(3, sqlDate);
                sqlDate = convertToSqlDate(EndDate);
                callableStatement.setDate(4, sqlDate);
                callableStatement.setString(5, employee);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            UiUtils.showErrorMessage(e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }
    }

    public static void updateLeaveRequest(String oldLeaveRequestNumber, String newLeaveRequestNumber, float SalaryPercentage, String StartDate, String EndDate, String employee) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spLeaveRequestUpdate(?, ?, ?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, oldLeaveRequestNumber);
                callableStatement.setString(2, newLeaveRequestNumber);
                callableStatement.setFloat(3, SalaryPercentage);
                java.sql.Date sqlDate = convertToSqlDate(StartDate);
                callableStatement.setDate(4, sqlDate);
                sqlDate = convertToSqlDate(EndDate);
                callableStatement.setDate(5, sqlDate);
                callableStatement.setString(6, employee);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }
    }

    public static Void softDeleteLeaveRequest(String LeaveRequestNumber) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spLeaveRequestSoftDelete(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, LeaveRequestNumber);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return null;
    }

    public static List<Department> selectAllDepartments() {
        Connection connection = DbUtils.getConnection();

        List<Department> Departments = new ArrayList<>();

        try {
            String storedProcedure = "{call spDepartmentSelectAllActive}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    int DepartmentId = resultSet.getInt("DepartmentId");
                    String name = resultSet.getString("Name");
                    int Budget = resultSet.getInt("Budget");
                    String manager = resultSet.getString("Manager");

                    Department Department = new Department(DepartmentId, name, Budget, manager);
                    Departments.add(Department);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return Departments;
    }

    public static Department selectDepartmentAllActiveWhere(String DepartmentName) {
        Connection connection = DbUtils.getConnection();

        Department Department = null;

        try {
            String storedProcedure = "{call spDepartmentSelectAllActiveWhere(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, DepartmentName);
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    int DepartmentId = resultSet.getInt("DepartmentId");
                    String name = resultSet.getString("Name");
                    int Budget = resultSet.getInt("Budget");
                    String manager = resultSet.getString("Manager");

                    Department = new Department(DepartmentId, name, Budget, manager);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return Department;
    }

    public static void insertDepartment(String name, int Budget, String Manager) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spDepartmentInsert(?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, name);
                callableStatement.setInt(2, Budget);
                callableStatement.setString(3, Manager);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            UiUtils.showErrorMessage(e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }
    }

    public static void updateDepartment(String oldName, String newName, int Budget, String Manager) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spDepartmentUpdate(?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, oldName);
                callableStatement.setString(2, newName);
                callableStatement.setInt(3, Budget);
                callableStatement.setString(4, Manager);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }
    }

    public static Void softDeleteDepartment(String name) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spDepartmentSoftDelete(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, name);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return null;
    }

    private static java.sql.Date convertToSqlDate(String dateString) {
        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Parse the string to obtain a java.util.Date object
            java.util.Date utilDate = dateFormat.parse(dateString);

            // Convert java.util.Date to java.sql.Date
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace(); // Handle the parsing exception or throw it
            return null; // Return null in case of an error (you might want to handle it differently)
        }
    }
}
