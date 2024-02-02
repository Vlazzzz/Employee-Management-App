import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class DepartmentRowPanel extends JPanel {
    private final JTextField departmentNameTextField = new JTextField();
    private final JTextField departmentBudgetTextField = new JTextField();
    private final JTextField departmentManagerTextField = new JTextField();
    private final Function<Void, Void> returnCallback;
    private final Function<Void, Void> saveCallback;
    private final Function<Void, Void> cancelCallback;
    private boolean isNewEntry = true;
    private String oldDepartmentName = "";

    public DepartmentRowPanel(Function<Void, Void> returnCallback) {
        super();

        this.returnCallback = returnCallback;
        this.saveCallback = (Void v) -> {
            String departmentName = departmentNameTextField.getText();
            int departmentBudget = Integer.parseInt(departmentBudgetTextField.getText());
            String departmentManager = departmentManagerTextField.getText();
            if (isNewEntry) {
                DbUtils.insertDepartment(departmentName, departmentBudget, departmentManager);
            } else {
                DbUtils.updateDepartment(oldDepartmentName, departmentName, departmentBudget, departmentManager);
            }
            returnCallback.apply(null);
            return null;
        };
        this.cancelCallback = (Void v) -> {
            departmentNameTextField.setText("");
            departmentBudgetTextField.setText("");
            departmentManagerTextField.setText("");
            returnCallback.apply(null);
            return null;
        };

        setupUI();
    }

    public DepartmentRowPanel(Department department, Function<Void, Void> returnCallback) {
        this(returnCallback);
        this.isNewEntry = false;
        this.oldDepartmentName = department.getName();
        this.departmentNameTextField.setText(department.getName());
        this.departmentBudgetTextField.setText(String.valueOf(department.getBudget()));
        this.departmentManagerTextField.setText(department.getManager());
    }

    private void setupUI() {
        LayoutManager layout = new BorderLayout();
        setLayout(layout);

        JPanel titlePanel = UiUtils.createTitlePanel("Department");

        JPanel departmentNamePanel = UiUtils.createRowPanel("Name:", departmentNameTextField);
        JPanel departmentBudgetPanel = UiUtils.createRowPanel("Budget:", departmentBudgetTextField);
        JPanel departmentManagerPanel = UiUtils.createRowPanel("Manager:", departmentManagerTextField);

        JPanel dataPanel = UiUtils.createDataPanel(new JPanel[]{departmentNamePanel, departmentBudgetPanel, departmentManagerPanel});

        JPanel buttonPanel = UiUtils.createButtonPanel(saveCallback, cancelCallback);

        add(titlePanel, BorderLayout.NORTH);
        add(dataPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
