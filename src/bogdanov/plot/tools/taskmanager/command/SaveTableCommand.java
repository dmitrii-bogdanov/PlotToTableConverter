package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.taskmanager.TaskManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class SaveTableCommand implements Command {

    private TaskManager taskManager = null;
    private File tableFile = null;
    private Set<String> oldFile = new LinkedHashSet<>();

    public SaveTableCommand(TaskManager taskManager, File tableFile) {
        this.taskManager = taskManager;
        this.tableFile = tableFile;
        if (tableFile != null) {
            try (Scanner scanner = new Scanner(tableFile)) {
                while (scanner.hasNext()) {
                    oldFile.add(scanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                //File not found
            }
        }
    }

    @Override
    public void execute() {
        try (FileWriter writer = new FileWriter(tableFile)) {
            writer.write(taskManager.getImageConverter().getTable().toString());
        } catch (IOException e) {
            JDialog errorDialog = new JDialog(new JFrame(), "File error", true);
            errorDialog.setSize(new Dimension(300, 200));
            errorDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            JLabel errorLabel = new JLabel(String.format(
                    "File %s cannot be written to.", tableFile.getName()
            ));
            errorDialog.add(errorLabel);
        }
    }

    @Override
    public void undo() {
        try (FileWriter writer = new FileWriter(tableFile)) {
            for (String str : oldFile) {
                writer.write(str);
            }
        } catch (IOException e) {
            JDialog errorDialog = new JDialog(new JFrame(), "File error", true);
            errorDialog.setSize(new Dimension(300, 200));
            errorDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            JLabel errorLabel = new JLabel(String.format(
                    "File %s cannot be written to.", tableFile.getName()
            ));
            errorDialog.add(errorLabel);
        }
    }
}