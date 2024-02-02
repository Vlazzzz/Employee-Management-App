import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class DepartmentTablePanel extends JPanel {
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
    public DepartmentTablePanel(Function<JPanel, Void> setMainPanelCallback) {
        super();
        this.setMainPanelCallback = setMainPanelCallback;
        this.returnCallback  = (Void v) -> {
            setMainPanelCallback.apply(this);
            refreshUI.apply(null);
            return null;
        };
        this.editCallback = (String departmentName) -> {
            JPanel departmentRowPanel = new DepartmentRowPanel(
                    DbUtils.selectDepartmentAllActiveWhere(departmentName),
                    returnCallback
            );
            setMainPanelCallback.apply(departmentRowPanel);
            return null;
        };
        setupUI();
    }
    private void setupUI() {
        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);

        JPanel titlePanel = UiUtils.createTitlePanel("Department");

        String[] columnNames = DbObject.convertListToArray(Department.getFields());

        String[][] data = DbObject.convertListToMatrix(DbUtils.selectAllDepartments());

        JPanel dataTablePanel = UiUtils.createDataTablePanel(
                data,
                columnNames,
                editCallback,
                DbUtils::softDeleteDepartment,
                setMainPanelCallback,
                returnCallback,
                refreshUI
        );

        add(titlePanel);
        add(dataTablePanel);
    }
}
