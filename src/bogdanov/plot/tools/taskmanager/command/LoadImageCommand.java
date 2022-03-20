package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.ImageConverter;
import bogdanov.plot.tools.ImageFileHandler;
import bogdanov.plot.tools.taskmanager.TaskManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LoadImageCommand implements Command {

    private TaskManager taskManager = null;
    private File imageFile = null;

    public LoadImageCommand(TaskManager taskManager, File imageFile) {
        this.taskManager = taskManager;
        this.imageFile = imageFile;
        System.out.println("LoadImageCommand Constructor()");
    }

    @Override
    public void execute() {
        System.out.println("Executing Command >>> LoadImageCommand");
        try {
            System.out.println("Executing Command >>> LoadImageCommand >>> TRY");
            taskManager.getImageDrawer().setImage(ImageFileHandler.load(imageFile));
            taskManager.setImageConverter(new ImageConverter(
                    taskManager.getImageDrawer().getImage()
            ));
        } catch (IOException e) {
            System.out.println("Executing Command >>> LoadImageCommand >>> CATCH");
            JDialog errorDialog = new JDialog(new JFrame(), "File error", true);
            errorDialog.setSize(new Dimension(300, 200));
            errorDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            JLabel errorLabel = new JLabel(String.format(
                    "File %s is not an image or cannot be read.", imageFile.getName()
            ));
            errorDialog.add(errorLabel);
            imageFile = null;
        }
    }

    @Override
    public void undo() {

    }
}
