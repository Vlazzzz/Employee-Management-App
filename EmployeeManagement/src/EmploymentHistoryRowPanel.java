import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class EmploymentHistoryRowPanel extends JPanel {
    private final JTextField employmentHistoryPositionTextField = new JTextField();
    private final JComponent employmentHistoryEmplDateDatePicker = UiUtils.createDatePicker();
    private final JTextField employmentHistoryEmployeeNameTextField = new JTextField();
    private final Function<Void, Void> returnCallback;
    private final Function<Void, Void> saveCallback;
    private final Function<Void, Void> cancelCallback;
    private boolean isNewEntry = true;
    private String oldEmploymentHistoryPosition = "";

    public EmploymentHistoryRowPanel(Function<Void, Void> returnCallback) {
        super();

        this.returnCallback = returnCallback;
        this.saveCallback = (Void v) -> {
            String employeePosition = employmentHistoryPositionTextField.getText();
            String emplDate = ((JDatePickerImpl) employmentHistoryEmplDateDatePicker).getJFormattedTextField().getText();
            String EmployeeName = employmentHistoryEmployeeNameTextField.getText();
            if (isNewEntry) {
                DbUtils.insertEmploymentHistory(employeePosition, emplDate, EmployeeName);
            } else {
                DbUtils.updateEmploymentHistory(oldEmploymentHistoryPosition, employeePosition, emplDate, EmployeeName);
            }
            returnCallback.apply(null);
            return null;
        };
        this.cancelCallback = (Void v) -> {
            employmentHistoryPositionTextField.setText("");
            returnCallback.apply(null);
            return null;
        };

        setupUI();
    }

    EmploymentHistoryRowPanel(EmploymentHistory employmentHistory, Function<Void, Void> returnCallback) {
        this(returnCallback);
        this.isNewEntry = false;
        this.oldEmploymentHistoryPosition = employmentHistory.getPosition();
        this.employmentHistoryPositionTextField.setText(employmentHistory.getPosition());
        ((JDatePickerImpl) employmentHistoryEmplDateDatePicker).getJFormattedTextField().setText(employmentHistory.getHiringDate());
        this.employmentHistoryEmployeeNameTextField.setText(employmentHistory.getEmployeeName());
    }

    private void setupUI() {
        LayoutManager layout = new BorderLayout();
        setLayout(layout);

        JPanel titlePanel = UiUtils.createTitlePanel("Employee");

        JPanel employmentHistoryPositionPanel = UiUtils.createRowPanel("Position:", employmentHistoryPositionTextField);
        JPanel employmentHistoryHiringDatePanel = UiUtils.createRowPanel("Hiring Date:", employmentHistoryEmplDateDatePicker);
        JPanel employmentHistoryEmployeePanel = UiUtils.createRowPanel("Employee:", employmentHistoryEmployeeNameTextField);

        JPanel dataPanel = UiUtils.createDataPanel(new JPanel[]{employmentHistoryPositionPanel, employmentHistoryHiringDatePanel, employmentHistoryEmployeePanel});

        JPanel buttonPanel = UiUtils.createButtonPanel(saveCallback, cancelCallback);

        add(titlePanel, BorderLayout.NORTH);
        add(dataPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
