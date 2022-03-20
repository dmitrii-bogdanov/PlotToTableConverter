package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.ImageDrawer;
import bogdanov.plot.tools.taskmanager.TaskManager;

import java.awt.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ResizedCoordinateGetCommand implements GetCommand {

    private ImageDrawer drawer = null;
    private Point point = null;

    public ResizedCoordinateGetCommand(TaskManager taskManager, Point point) {
        this.drawer = taskManager.getImageDrawer();
        this.point = point;
    }


    @Override
    public Object execute() {
        if (drawer != null) {
            return drawer.getResizedCoordinate(point);
        }
        return null;
    }
}
