package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.ImageDrawer;
import bogdanov.plot.tools.taskmanager.TaskManager;

public class ZoomInCommand implements Command {

    private ImageDrawer drawer = null;

    public ZoomInCommand(TaskManager taskManager) {
        System.out.println("ZoomIn Constructot()");
        this.drawer = taskManager.getImageDrawer();
    }

    @Override
    public void execute() {
        System.out.println("Executing Command >>> ZoomInCommand");
        drawer.zoomIn();
    }

    @Override
    public void undo() {
        drawer.zoomOut();
    }
}
