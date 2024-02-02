import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Function;

public class EmployeeTablePanel extends JPanel {
    private final Function<JPanel, Void> setMainPanelCallback;
    private final Function<Void, Void> returnCallback;
    private final Function<String, Void> editCallback;
    private final Function<Void, Void> refreshUI = (Void v) -> {
        removeAll();
        setupUI();
        revalidate();
        repaint();
        return null;
    };
    public EmployeeTablePanel(Function<JPanel, Void> setMainPanelCallback) {
        super();
        this.setMainPanelCallback = setMainPanelCallback;
        this.returnCallback  = (Void v) -> {
            setMainPanelCallback.apply(this);
            refreshUI.apply(null);
            return null;
        };
        this.editCallback = (String employeeName) -> {
            JPanel employeeRowPanel = new EmployeeRowPanel(
                    DbUtils.selectEmployeeAllActiveWhere(employeeName),
                    returnCallback
            );
            setMainPanelCallback.apply(employeeRowPanel);
            return null;
        };
        setupUI();
    }

    private void setupUI() {
        LayoutManager layout = new BorderLayout();
        setLayout(layout);

        JPanel titlePanel = UiUtils.createTitlePanel("Employee");

        String[] columnNames = DbObject.convertListToArray(Employee.getFields());

        List<? extends DbObject> employeeList = DbUtils.selectAllEmployees();
        String[][] data = DbObject.convertListToMatrix(employeeList);

        JPanel dataTablePanel = UiUtils.createDataTablePanel(
                data,
                columnNames,
                editCallback,
                DbUtils::softDeleteEmployee,
                setMainPanelCallback,
                returnCallback,
                refreshUI
        );

        add(titlePanel, BorderLayout.NORTH);
        add(dataTablePanel, BorderLayout.CENTER);
    }
}
