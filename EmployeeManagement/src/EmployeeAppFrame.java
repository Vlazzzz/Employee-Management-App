import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class EmployeeAppFrame extends JFrame {
    private JPanel currentPanel;
    private final Function<JPanel, Void> setMainPanelContent;
    public EmployeeAppFrame() {
        super("Employee Management App");

        // Set the icon image
        ImageIcon icon = new ImageIcon("D:\\facultate\\II\\SEM I\\MIP\\EmployeeManagement\\assets\\2019661-200.png"); // Replace with the path to your icon file
        setIconImage(icon.getImage());

        this.setMainPanelContent = (JPanel panel) -> {
            currentPanel = panel;
            setContentPane(currentPanel);
            revalidate();
            repaint();
            return null;
        };

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 620);
        setLocationRelativeTo(null);

        JMenuBar menuBar = UiUtils.createMenuBar();

        JMenu menuHome = UiUtils.createMenu("Home");
        JMenuItem menuShowHome = UiUtils.createMenuItem("Home Page");
        menuShowHome.addActionListener(e -> setMainPanelContent.apply(new HomePanel()));
        menuHome.add(menuShowHome);
        menuBar.add(menuHome);

        JMenu menuEmployee = UiUtils.createMenu("Employee");
        JMenuItem menuEmployeeList = UiUtils.createMenuItem("Employee List");
        menuEmployeeList.addActionListener(e -> setMainPanelContent.apply(new EmployeeTablePanel(setMainPanelContent)));
        JMenuItem menuEmployeeAdd = UiUtils.createMenuItem("Add Employee");
        menuEmployeeAdd.addActionListener(e -> setMainPanelContent.apply(new EmployeeRowPanel(
                (Void v) -> {
                    setMainPanelContent.apply(new EmployeeTablePanel(setMainPanelContent));
                    return null;
                }
        )));
        menuEmployee.add(menuEmployeeList);
        menuEmployee.add(menuEmployeeAdd);
        menuBar.add(menuEmployee);

        JMenu menuLeaveRequest = UiUtils.createMenu("Leave Requests");
        JMenuItem menuLeaveRequestList = UiUtils.createMenuItem("Leave Request List");
        menuLeaveRequestList.addActionListener(e -> setMainPanelContent.apply(new LeaveRequestTablePanel(setMainPanelContent)));
        JMenuItem menuLeaveRequestAdd = UiUtils.createMenuItem("Add Leave Request");
        menuLeaveRequestAdd.addActionListener(e -> setMainPanelContent.apply(new LeaveRequestRowPanel(
                (Void v) -> {
                    setMainPanelContent.apply(new LeaveRequestTablePanel(setMainPanelContent));
                    return null;
                }
        )));
        menuLeaveRequest.add(menuLeaveRequestList);
        menuLeaveRequest.add(menuLeaveRequestAdd);
        menuBar.add(menuLeaveRequest);

        JMenu menuEmploymentHistory = UiUtils.createMenu("EmpHistory");
        JMenuItem menuEmploymentHistoryList = UiUtils.createMenuItem("EmpHistory List");
        menuEmploymentHistoryList.addActionListener(e -> setMainPanelContent.apply(new EmploymentHistoryTablePanel(setMainPanelContent)));
        JMenuItem menuEmploymentHistoryAdd = UiUtils.createMenuItem("Add EmpHistory");
        menuEmploymentHistoryAdd.addActionListener(e -> setMainPanelContent.apply(new EmploymentHistoryRowPanel(
                (Void v) -> {
                    setMainPanelContent.apply(new EmploymentHistoryTablePanel(setMainPanelContent));
                    return null;
                }
        )));
        menuEmploymentHistory.add(menuEmploymentHistoryList);
        menuEmploymentHistory.add(menuEmploymentHistoryAdd);
        menuBar.add(menuEmploymentHistory);

        JMenu menuDepartment = UiUtils.createMenu("Department");
        JMenuItem menuDepartmentList = UiUtils.createMenuItem("Department List");
        menuDepartmentList.addActionListener(e -> setMainPanelContent.apply(new DepartmentTablePanel(setMainPanelContent)));
        JMenuItem menuDepartmentAdd = UiUtils.createMenuItem("Add Department");
        menuDepartmentAdd.addActionListener(e -> setMainPanelContent.apply(new DepartmentRowPanel(
                (Void v) -> {
                    setMainPanelContent.apply(new DepartmentTablePanel(setMainPanelContent));
                    return null;
                }
        )));
        menuDepartment.add(menuDepartmentList);
        menuDepartment.add(menuDepartmentAdd);
        menuBar.add(menuDepartment);

        currentPanel = new HomePanel();

        setJMenuBar(menuBar);
        setContentPane(currentPanel);

        setVisible(true);
    }
}
