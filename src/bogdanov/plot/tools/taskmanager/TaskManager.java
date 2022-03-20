package bogdanov.plot.tools.taskmanager;

import bogdanov.plot.tools.ImageDrawer;
import bogdanov.plot.tools.taskmanager.command.Command;
import bogdanov.plot.tools.taskmanager.command.GetCommand;
import bogdanov.plot.tools.ImageConverter;

import java.awt.image.BufferedImage;

public class TaskManager {

    private ImageDrawer drawer = new ImageDrawer(null);
    private ImageConverter converter = null;

    private TaskQueue queue = new TaskQueue();

    public void execute(Command command) {
        if (command != null) {
            queue.enqueue(command);
            command.execute();
        }
    }

    public Object execute(GetCommand getCommand) {
        if (getCommand != null) {
            return getCommand.execute();
        }
        System.out.println("Executing GetCommand == NULL");
        return null;
    }

    public void undo() {
        Command command = queue.stepBack();
        if (command != null) {
            command.undo();
        }
    }

    public ImageDrawer getImageDrawer() {
        return drawer;
    }

    public void setImageDrawer(ImageDrawer drawer) {
        this.drawer = drawer;
    }

    public ImageConverter getImageConverter() {
        return converter;
    }

    public void setImageConverter(ImageConverter converter) {
        this.converter = converter;
    }

    public void restart() {
        drawer = null;
        converter = null;
        queue = new TaskQueue();
    }

    public BufferedImage getImage(){
        return drawer.getImage();
    }
}

class TaskQueue {

    private Command[] queue = null;
    private int start = 0, end = 0;
    private int size = 64;

    TaskQueue() {
        queue = new Command[size];

    }

    TaskQueue(int size) {
        this.size = size;
        queue = new Command[size];
    }

    void enqueue(Command command) {
        queue[end] = command;
        end++;
        if (end == size) {
            end = 0;
        }
    }

    Command dequeue() {
        if (start == end) {
            return null;
        } else {
            start = start == size - 1 ? 0 : start++;
            return queue[start == 0 ? size - 1 : start - 1];
        }
    }

    Command stepBack() {
        start = start == 0 ? size - 1 : start--;
        end = end == 0 ? size - 1 : end--;
        return queue[start];
    }

    boolean isEmpty() {
        return queue[start == 0 ? size - 1 : start - 1] == null;
    }

}
