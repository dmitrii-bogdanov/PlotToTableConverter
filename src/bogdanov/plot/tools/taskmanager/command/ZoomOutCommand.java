package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.ImageDrawer;
import bogdanov.plot.tools.taskmanager.TaskManager;

public class ZoomOutCommand implements Command {

    private ImageDrawer drawer = null;

    public ZoomOutCommand(TaskManager taskManager) {
        System.out.println("ZoomOut Constructor()");
        this.drawer = taskManager.getImageDrawer();
    }

    @Override
    public void execute() {
        System.out.println("Executing Command >>> ZoomOutCommand");
        drawer.zoomOut();
    }

    @Override
    public void undo() {
        drawer.zoomIn();
    }
}
