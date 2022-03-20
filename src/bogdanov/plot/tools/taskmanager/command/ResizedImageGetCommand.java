package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.ImageDrawer;
import bogdanov.plot.tools.taskmanager.TaskManager;

public class ResizedImageGetCommand implements GetCommand {

    private ImageDrawer drawer = null;

    public ResizedImageGetCommand(TaskManager taskManager) {
        this.drawer = taskManager.getImageDrawer();
    }

    @Override
    public Object execute() {
        return drawer.getResizedImage();
    }
}