package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.ImageConverter;
import bogdanov.plot.tools.taskmanager.TaskManager;

import java.awt.*;
import java.util.Set;

public class ConverterMarkerEraserCommand implements Command {

    private ImageConverter converter = null;

    private Set<Point> erasedPoints = null;

    public ConverterMarkerEraserCommand(TaskManager taskManager, Set<Point> erasedPoints) {
        this.converter = taskManager.getImageConverter();
        this.erasedPoints = erasedPoints;
    }

    @Override
    public void execute() {
        converter.removeMarkedPoints(erasedPoints);
    }

    @Override
    public void undo() {
        converter.addMarkedPoints(erasedPoints);
    }
}