package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.ImageConverter;
import bogdanov.plot.tools.taskmanager.TaskManager;

public class ConverterTablePointsGetCommand implements GetCommand {

    private ImageConverter converter = null;

    public ConverterTablePointsGetCommand(TaskManager taskManager) {
        this.converter = taskManager.getImageConverter();
    }

    @Override
    public Object execute() {
        return converter.getTablePoints();
    }
}