import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class EmployeeRowPanel extends JPanel {
    private final JTextField employeeNameTextField = new JTextField();
    private final Function<Void, Void> returnCallback;
    private final Function<Void, Void> saveCallback;
    private final Function<Void, Void> cancelCallback;
    private boolean isNewEntry = true;
    private String oldEmployeeName = "";

    public EmployeeRowPanel(Function<Void, Void> returnCallback) {
        super();

        this.returnCallback = returnCallback;
        this.saveCallback = (Void v) -> {
            String employeeName = employeeNameTextField.getText();
            if (isNewEntry) {
                DbUtils.insertEmployee(employeeName);
            } else {
                DbUtils.updateEmployee(oldEmployeeName, employeeName);
            }
            returnCallback.apply(null);
            return null;
        };
        this.cancelCallback = (Void v) -> {
            employeeNameTextField.setText("");
            returnCallback.apply(null);
            return null;
        };

        setupUI();
    }

    EmployeeRowPanel(Employee employee, Function<Void, Void> returnCallback) {
        this(returnCallback);
        this.isNewEntry = false;
        this.oldEmployeeName = employee.getName();
        this.employeeNameTextField.setText(oldEmployeeName);
    }

    private void setupUI() {
        LayoutManager layout = new BorderLayout();
        setLayout(layout);

        JPanel titlePanel = UiUtils.createTitlePanel("Employee");

        JPanel employeeNamePanel = UiUtils.createRowPanel("Name:", employeeNameTextField);

        JPanel dataPanel = UiUtils.createDataPanel(new JPanel[]{employeeNamePanel});

        JPanel buttonPanel = UiUtils.createButtonPanel(saveCallback, cancelCallback);

        //UiUtils.colorPanels(new JPanel[]{titlePanel, dataPanel, buttonPanel});

        add(titlePanel, BorderLayout.NORTH);
        add(dataPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
