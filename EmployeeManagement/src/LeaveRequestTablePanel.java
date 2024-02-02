import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class LeaveRequestTablePanel extends JPanel {
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
    public LeaveRequestTablePanel(Function<JPanel, Void> setMainPanelCallback) {
        super();
        this.setMainPanelCallback = setMainPanelCallback;
        this.returnCallback  = (Void v) -> {
            setMainPanelCallback.apply(this);
            refreshUI.apply(null);
            return null;
        };
        this.editCallback = (String leaveRequestLocation) -> {
            JPanel leaveRequestRowPanel = new LeaveRequestRowPanel(
                    DbUtils.selectLeaveRequestAllActiveWhere(leaveRequestLocation),
                    returnCallback
            );
            setMainPanelCallback.apply(leaveRequestRowPanel);
            return null;
        };
        setupUI();
    }
    private void setupUI() {
        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);

        JPanel titlePanel = UiUtils.createTitlePanel("Leave Requests");

        String[] columnNames = DbObject.convertListToArray(LeaveRequest.getFields());

        String[][] data = DbObject.convertListToMatrix(DbUtils.selectAllLeaveRequests());

        JPanel dataTablePanel = UiUtils.createDataTablePanel(
                data,
                columnNames,
                editCallback,
                DbUtils::softDeleteLeaveRequest,
                setMainPanelCallback,
                returnCallback,
                refreshUI
        );

        add(titlePanel, BorderLayout.NORTH);
        add(dataTablePanel, BorderLayout.CENTER);
    }
}
