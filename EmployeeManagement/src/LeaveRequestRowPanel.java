import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class LeaveRequestRowPanel extends JPanel {
    private final JTextField leaveRequestLeaveRequestNumberTextField = new JTextField();
    private final JTextField leaveRequestSalaryPercentageTextField = new JTextField();
    private final JComponent leaveRequestStartDateDatePicker = UiUtils.createDatePicker();
    private final JComponent leaveRequestEndDateDatePicker = UiUtils.createDatePicker();
    private final JTextField leaveRequestEmployeeTextField = new JTextField();
    private final Function<Void, Void> returnCallback;
    private final Function<Void, Void> saveCallback;
    private final Function<Void, Void> cancelCallback;
    private boolean isNewEntry = true;
    private String oldLeaveRequestLeaveRequestNumber = "";

    public LeaveRequestRowPanel(Function<Void, Void> returnCallback) {
        super();

        this.returnCallback = returnCallback;
        this.saveCallback = (Void v) -> {
            String leaveRequestName = leaveRequestLeaveRequestNumberTextField.getText();
            float leaveRequestSalaryPercentage = Float.parseFloat(leaveRequestSalaryPercentageTextField.getText());
            String leaveRequestStartDate = ((JDatePickerImpl) leaveRequestStartDateDatePicker).getJFormattedTextField().getText();
            String leaveRequestEndDate = ((JDatePickerImpl) leaveRequestEndDateDatePicker).getJFormattedTextField().getText();
            String leaveRequestEmployee = leaveRequestEmployeeTextField.getText();
            if (isNewEntry) {
                DbUtils.insertLeaveRequest(leaveRequestName, leaveRequestSalaryPercentage, leaveRequestStartDate, leaveRequestEndDate, leaveRequestEmployee);
            } else {
                DbUtils.updateLeaveRequest(oldLeaveRequestLeaveRequestNumber, leaveRequestName, leaveRequestSalaryPercentage, leaveRequestStartDate, leaveRequestEndDate, leaveRequestEmployee);
            }
            returnCallback.apply(null);
            return null;
        };
        this.cancelCallback = (Void v) -> {
            leaveRequestLeaveRequestNumberTextField.setText("");
            leaveRequestSalaryPercentageTextField.setText("");
            ((JDatePickerImpl) leaveRequestStartDateDatePicker).getJFormattedTextField().setText("");
            ((JDatePickerImpl) leaveRequestEndDateDatePicker).getJFormattedTextField().setText("");
            leaveRequestEmployeeTextField.setText("");
            returnCallback.apply(null);
            return null;
        };

        setupUI();
    }

    public LeaveRequestRowPanel(LeaveRequest leaveRequest, Function<Void, Void> returnCallback) {
        this(returnCallback);
        this.isNewEntry = false;
        this.oldLeaveRequestLeaveRequestNumber = leaveRequest.getLeaveRequestNumber();
        this.leaveRequestLeaveRequestNumberTextField.setText(leaveRequest.getLeaveRequestNumber());
        this.leaveRequestSalaryPercentageTextField.setText(String.valueOf(leaveRequest.getSalaryPercentage()));
        ((JDatePickerImpl) leaveRequestStartDateDatePicker).getJFormattedTextField().setText(leaveRequest.getStartDate());
        ((JDatePickerImpl) leaveRequestEndDateDatePicker).getJFormattedTextField().setText(leaveRequest.getEndDate());
        this.leaveRequestEmployeeTextField.setText(leaveRequest.getEmployee());
    }

    private void setupUI() {
        LayoutManager layout = new BorderLayout();
        setLayout(layout);

        JPanel titlePanel = UiUtils.createTitlePanel("Leave request");

        JPanel leaveRequestLeaveRequestNumberPanel = UiUtils.createRowPanel("Leave Request Number:", leaveRequestLeaveRequestNumberTextField);
        JPanel leaveRequestSalaryPercentagePanel = UiUtils.createRowPanel("Salary Percentage:", leaveRequestSalaryPercentageTextField);
        JPanel leaveRequestStartDatePanel = UiUtils.createRowPanel("Start date:", leaveRequestStartDateDatePicker);
        JPanel leaveRequestEndDatePanel = UiUtils.createRowPanel("End date:", leaveRequestEndDateDatePicker);
        JPanel leaveRequestEmployeePanel = UiUtils.createRowPanel("Employee:", leaveRequestEmployeeTextField);

        JPanel dataPanel = UiUtils.createDataPanel(new JPanel[]{leaveRequestLeaveRequestNumberPanel, leaveRequestSalaryPercentagePanel, leaveRequestStartDatePanel, leaveRequestEndDatePanel, leaveRequestEmployeePanel});

        JPanel buttonPanel = UiUtils.createButtonPanel(saveCallback, cancelCallback);

        add(titlePanel, BorderLayout.NORTH);
        add(dataPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
