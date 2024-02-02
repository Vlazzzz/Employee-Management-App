import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.function.Function;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;

public class UiUtils {
    public static JPanel createRowPanel(String labelText, JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setPreferredSize(new Dimension(640, 32));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        component.setFont(new Font("Arial", Font.PLAIN, 16));

        label.setPreferredSize(new Dimension(128, 32));
        component.setPreferredSize(new Dimension(160, 32));

        panel.add(label);
        panel.add(component);

        return panel;
    }
    public static JPanel createTitlePanel(String titleText) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 16, 64));

        JLabel titleLabel = new JLabel(titleText);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        panel.add(titleLabel);

        return panel;
    }

    public static JPanel createDataPanel(JPanel[] rows) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 16, 32));

        for (JPanel row : rows) {
            panel.add(row);
        }

        return panel;
    }

    public static JPanel createButtonPanel(
            Function<Void, Void> saveCallback,
            Function<Void, Void> cancelCallback
    ) {
        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener((ActionEvent e) -> {
            saveCallback.apply(null);
        });
        cancelButton.addActionListener((ActionEvent e) -> {
            cancelCallback.apply(null);
        });

        saveButton.setPreferredSize(new Dimension(250, 52));
        cancelButton.setPreferredSize(new Dimension(250, 52));

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    public static JComponent createDatePicker() {
        SqlDateModel dateModel = new SqlDateModel();
        Properties properties = new Properties();
        properties.put("text.day", "Day");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, properties);
        JDatePicker datePicker = new JDatePickerImpl(datePanel, new JFormattedTextField.AbstractFormatter() {
            @Override
            public Object stringToValue(String text) throws ParseException {
                return null;
            }

            @Override
            public String valueToString(Object value) throws ParseException {
                if (value != null) {
                    Calendar cal = (Calendar) value;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    return format.format(cal.getTime());
                }
                return "";
            }
        });
        datePicker.setTextEditable(true);

        return (JComponent) datePicker;
    }

    public static JPanel createDataTablePanel(
            String[][] data, String[] columnNames,
            Function<String, Void> editFunction,
            Function<String, Void> softDeleteFunction,
            Function<JPanel, Void> setMainPanelCallback,
            Function<Void, Void> returnCallback,
            Function<Void, Void> refreshUI
    ) {
        String[] columnNamesWithButtons = new String[columnNames.length + 2];
        System.arraycopy(columnNames, 0, columnNamesWithButtons, 0, columnNames.length);
        columnNamesWithButtons[columnNames.length] = "Edit";
        columnNamesWithButtons[columnNames.length + 1] = "Delete";

        String[][] tableContent = new String[data.length][columnNames.length + 2];
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(data[i], 0, tableContent[i], 0, columnNames.length);
            tableContent[i][columnNames.length] = "Edit";
            tableContent[i][columnNames.length + 1] = "Delete";
        }

        JTable table = new JTable(tableContent, columnNamesWithButtons) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setRowHeight(32);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setAlignmentX(JTable.CENTER_ALIGNMENT);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    if (col == columnNames.length) {
                        editFunction.apply(data[row][0]);
                    } else if (col == columnNames.length + 1) {
                        int response = JOptionPane.showConfirmDialog(
                                null,
                                "Are you sure you want to delete?",
                                "Confirmation",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (response == JOptionPane.YES_OPTION) {
                            softDeleteFunction.apply(data[row][0]);
                            refreshUI.apply(null);
                        }
                    }
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 32, 64, 32));
        panel.setLayout(new BorderLayout());
        panel.add(table);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        return panel;
    }

    public static void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        return menuBar;
    }

    public static JMenu createMenu(String menuName) {
        JMenu menu = new JMenu(menuName);
        menu.setPreferredSize(new Dimension(96, 32));
        return menu;
    }

    public static JMenuItem createMenuItem(String menuItemName) {
        JMenuItem menuItem = new JMenuItem(menuItemName);
        menuItem.setPreferredSize(new Dimension(156, 32));

        return menuItem;
    }
}
