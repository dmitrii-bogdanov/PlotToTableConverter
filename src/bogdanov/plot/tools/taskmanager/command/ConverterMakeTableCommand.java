package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.data.DataTable;
import bogdanov.plot.tools.ImageConverter;
import bogdanov.plot.tools.taskmanager.TaskManager;

public class ConverterMakeTableCommand implements Command {

    private ImageConverter converter = null;

    private DataTable oldTable = null;

    public ConverterMakeTableCommand(TaskManager taskManager) {

        this.converter = taskManager.getImageConverter();

        oldTable = taskManager.getImageConverter().getTable();
    }

    public void execute() {
        converter.setTable();
    }

    public void undo() {
        converter.setTable(oldTable);
    }
}