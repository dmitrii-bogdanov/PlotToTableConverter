package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.ImageConverter;
import bogdanov.plot.tools.taskmanager.TaskManager;

import java.awt.*;
import java.util.Set;

public class ConverterMarkerCommand implements Command {

    private ImageConverter converter = null;

    private Set<Point> markedPoints = null;
    private Set<Point> oldMarkedPoints = null;

    public ConverterMarkerCommand(TaskManager taskManager, Set<Point> markedPoints) {

        this.converter = taskManager.getImageConverter();
        this.markedPoints = markedPoints;
        this.oldMarkedPoints = converter.getMarkedPoints();
    }

    @Override
    public void execute() {
        converter.addMarkedPoints(markedPoints);
    }

    @Override
    public void undo() {
        converter.getMarkedPoints().clear();
        converter.addMarkedPoints(oldMarkedPoints);
    }
}
