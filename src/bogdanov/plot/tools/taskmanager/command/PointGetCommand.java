package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.taskmanager.TaskManager;

import java.awt.*;
import java.util.Set;

public class PointGetCommand implements GetCommand {

    private TaskManager taskManager = null;
    private Point point = null;
    private Set<Point> points = null;

    public PointGetCommand(TaskManager taskManager, Point point) {
        this.taskManager = taskManager;
        this.point = point;
    }

    public PointGetCommand(TaskManager taskManager, Set<Point> points) {
        this.taskManager = taskManager;
        this.points = points;
    }

    @Override
    public Object execute() {
        return points == null ?
                taskManager.getImageDrawer().getPoint(point) :
                taskManager.getImageDrawer().getPoint(points);
    }
}
