package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.ImageConverter;
import bogdanov.plot.tools.taskmanager.TaskManager;

public class ConverterPixelSizeGetCommand implements GetCommand {

    private ImageConverter converter = null;

    public ConverterPixelSizeGetCommand(TaskManager taskManager) {
        this.converter = taskManager.getImageConverter();
    }

    @Override
    public Object execute() {
        return converter.getPixelSize();
    }
}