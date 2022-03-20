package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.ImageDrawer;
import bogdanov.plot.tools.taskmanager.TaskManager;

public class ImageGetCommand implements GetCommand {

    private ImageDrawer drawer = null;

    public ImageGetCommand(TaskManager taskManager) {
        this.drawer = taskManager.getImageDrawer();
    }

    @Override
    public Object execute() {
        return drawer.getImage();
    }
}
