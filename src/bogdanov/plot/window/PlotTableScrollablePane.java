package bogdanov.plot.window;

import bogdanov.plot.tools.taskmanager.TaskManager;

import javax.swing.*;
import javax.swing.table.TableColumn;

public class PlotTableScrollablePane extends JScrollPane {

    private TaskManager taskManager = null;

    public PlotTableScrollablePane(TaskManager taskManager) {
        this.taskManager = taskManager;
    }
}

class PlotTablePanel extends JPanel {
    private TaskManager taskManager = null;

    JTable table = null;

    PlotTablePanel (TaskManager taskManager) {
        this.taskManager = taskManager;
        setPreferredSize(getParent().getPreferredSize());

        table = new JTable();

        TableColumn x = new TableColumn();
        TableColumn y = new TableColumn();

        x.setResizable(true);
        y.setResizable(true);

        table.addColumn(x);
        table.addColumn(y);



    }
}
