package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.ImageDrawer;
import bogdanov.plot.tools.taskmanager.TaskManager;

import java.awt.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ResizedPointsGetCommand implements GetCommand {

    private ImageDrawer drawer = null;
    private Set<Point> points = null;

    public ResizedPointsGetCommand(TaskManager taskManager, Point point) {
        this.drawer = taskManager.getImageDrawer();
        if (point != null) {
            this.points = new HashSet<>();
            points.add(point);
        }
    }

    public ResizedPointsGetCommand(TaskManager taskManager, Set<Point> points) {
        this.drawer = taskManager.getImageDrawer();
        this.points = points;
    }


    @Override
    public Object execute() {
        if (drawer != null) {
                return points != null ? drawer.getResizedPoints(points) : null;
        }
        return Collections.emptySet();
    }
}
