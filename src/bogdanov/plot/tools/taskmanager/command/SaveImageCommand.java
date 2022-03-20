package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.ImageFileHandler;
import bogdanov.plot.tools.taskmanager.TaskManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SaveImageCommand implements Command {

    private TaskManager taskManager = null;
    private File imageFile = null;
    private BufferedImage oldImage = null;

    public SaveImageCommand(TaskManager taskManager, File imageFile) {
        this.taskManager = taskManager;
        this.imageFile = imageFile;
        if (imageFile.isFile()) {
            try {
                oldImage = ImageFileHandler.load(imageFile);
            } catch (IOException e) {
                oldImage = null;
            }
        }
    }

    @Override
    public void execute() {
        try {
            ImageFileHandler.save(taskManager.getImageDrawer().getImage(), imageFile);
        } catch (IOException e) {
            JDialog errorDialog = new JDialog(new JFrame(), "File error", true);
            errorDialog.setSize(new Dimension(300, 200));
            errorDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            JLabel errorLabel = new JLabel(String.format(
                    "File %s cannot be written to", imageFile.getName()
            ));
            errorDialog.add(errorLabel);
        }
    }

    @Override
    public void undo() {
        if (oldImage != null) {
            try {
                ImageFileHandler.save(oldImage, imageFile);
            } catch (IOException e) {
                //File wasn't saved
            }
        }
    }
}
