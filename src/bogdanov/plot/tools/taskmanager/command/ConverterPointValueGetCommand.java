package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.taskmanager.TaskManager;

import java.awt.*;

public class ConverterPointValueGetCommand implements GetCommand {

    private TaskManager taskManager = null;
    private Point point = null;

    public ConverterPointValueGetCommand(TaskManager taskManager, Point point) {
        this.taskManager = taskManager;
        this.point = point;
    }

    @Override
    public Object execute() {
        return taskManager.getImageConverter().getPointValue(point);
    }
}
