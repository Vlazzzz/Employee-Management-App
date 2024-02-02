import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Function;

public class EmploymentHistoryTablePanel extends JPanel {
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
    public EmploymentHistoryTablePanel(Function<JPanel, Void> setMainPanelCallback) {
        super();
        this.setMainPanelCallback = setMainPanelCallback;
        this.returnCallback  = (Void v) -> {
            setMainPanelCallback.apply(this);
            refreshUI.apply(null);
            return null;
        };
        this.editCallback = (String employmentHistoryName) -> {
            JPanel employmentHistoryRowPanel = new EmploymentHistoryRowPanel(
                    DbUtils.selectEmploymentHistoryAllActiveWhere(employmentHistoryName),
                    returnCallback
            );
            setMainPanelCallback.apply(employmentHistoryRowPanel);
            return null;
        };
        setupUI();
    }
    private void setupUI() {
        LayoutManager layout = new BorderLayout();
        setLayout(layout);

        JPanel titlePanel = UiUtils.createTitlePanel("Employment History");

        String[] columnNames = DbObject.convertListToArray(EmploymentHistory.getFields());

        List<? extends DbObject> list = DbUtils.selectAllEmploymentHistorys();
        String[][] data = DbObject.convertListToMatrix(list);

        JPanel dataTablePanel = UiUtils.createDataTablePanel(
                data,
                columnNames,
                editCallback,
                DbUtils::softDeleteEmploymentHistory,
                setMainPanelCallback,
                returnCallback,
                refreshUI
        );

        add(titlePanel, BorderLayout.NORTH);
        add(dataTablePanel, BorderLayout.CENTER);
    }
}
