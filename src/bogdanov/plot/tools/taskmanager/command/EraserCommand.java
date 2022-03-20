package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.ImageDrawer;
import bogdanov.plot.tools.ImageConverter;
import bogdanov.plot.tools.taskmanager.TaskManager;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EraserCommand implements Command {

    private ImageDrawer drawer;
    private ImageConverter converter;

    private int size;
    private Map<Point, Integer> erasedPixels = new HashMap<>();

    public EraserCommand(TaskManager taskManager, Point point) {
        this.drawer = taskManager.getImageDrawer();
        this.converter = taskManager.getImageConverter();
        if (point != null) {
            erasedPixels.put(drawer.getPoint(point), drawer.getPixel(point));
        }
    }

    public EraserCommand(TaskManager taskManager, Set<Point> points) {
        this.drawer = taskManager.getImageDrawer();
        this.converter = taskManager.getImageConverter();
        if (points != null) {
            for (Point point : drawer.getPoint(points)) {
                erasedPixels.put(point, drawer.getPixel(point));
            }
        }
    }

    @Override
    public void execute() {
        drawer.erasePoints(erasedPixels.keySet());
        drawer.update();
        converter.setImage(drawer.getImage());
    }

    @Override
    public void undo() {
        drawer.changePoint(erasedPixels);
        drawer.update();
        converter.setImage(drawer.getImage());
    }
}
