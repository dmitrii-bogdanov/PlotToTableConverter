package bogdanov.plot.window;

import bogdanov.plot.tools.taskmanager.TaskManager;
import bogdanov.plot.tools.taskmanager.command.LoadImageCommand;
import bogdanov.plot.tools.taskmanager.command.SaveTableCommand;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class MenuBar extends JMenuBar {

    private TaskManager taskManager = null;

    private JFrame frame = null;

    FileMenu fileMenu;

    public MenuBar(TaskManager taskManager) {

        fileMenu = new FileMenu();

        setTaskManager(taskManager);

        add(fileMenu);

        revalidate();
        setVisible(true);
    }

    public MenuBar(TaskManager taskManager, String mode) {

        switch (mode.toLowerCase()) {
            case "plot converter":
                System.out.println("Plot Converter MODE");
                fileMenu = new FileMenu(
                        new OpenPlotImageMenuItem(taskManager, "Load Plot Image"),
                        new SaveTableMenuItem(taskManager, "Save Table", false),
                        new SaveTableMenuItem(taskManager, "Save Table As", true)
                );
                break;
            default:
                fileMenu = new FileMenu();
        }

        setTaskManager(taskManager);

        add(fileMenu);

//        JMenu fMenu = new JMenu("File");
//        JMenu vMenu = new JMenu("View");
//        add(fMenu);
//        add(vMenu);
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
        fileMenu.setFrame(frame);
    }
}

class FileMenu extends JMenu {

    JFrame frame = null;

    JMenuItem newMenuItem = new JMenuItem("New");
    OpenPlotImageMenuItem openMenuItem = null;
    SaveTableMenuItem saveMenuItem = null;
    SaveTableMenuItem saveAsMenuItem = null;
    JMenuItem closeMenuItem = new JMenuItem("Close");
    JMenuItem exitMenuItem = new JMenuItem("Exit");

    FileMenu() {
        System.out.println("File Menu Constructor ()");
        setText("File");
        setMnemonic(KeyEvent.VK_F);

        add(newMenuItem);
        add(openMenuItem);
        add(saveMenuItem);
        add(saveAsMenuItem);
        add(closeMenuItem);

        addSeparator();
        add(exitMenuItem);

        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newMenuItem.addActionListener(event -> new PlotConverterFrame());

        exitMenuItem.addActionListener(event -> getParent().getParent().disable());

        setVisible(true);
    }

    FileMenu(OpenPlotImageMenuItem openMenuItem, SaveTableMenuItem saveMenuItem, SaveTableMenuItem saveAsMenuItem) {
        System.out.println("File Menu Constructor(....)");
        this.openMenuItem = openMenuItem;
        this.saveMenuItem = saveMenuItem;
        this.saveAsMenuItem = saveAsMenuItem;

        setText("File");
        setMnemonic(KeyEvent.VK_F);

        add(newMenuItem);
        add(openMenuItem);
        add(saveMenuItem);
        add(saveAsMenuItem);
        add(closeMenuItem);

        addSeparator();
        add(exitMenuItem);

        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newMenuItem.addActionListener(event -> new PlotConverterFrame());

        exitMenuItem.addActionListener(event -> getParent().getParent().disable());

        setVisible(true);
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
        openMenuItem.setFrame(frame);
        saveMenuItem.setFrame(frame);
        saveAsMenuItem.setFrame(frame);
    }

}

class OpenPlotImageMenuItem extends JMenuItem {

    private TaskManager taskManager = null;
    private JFrame frame = null;

    private File imageFile = null;
    private JFileChooser fileChooser = new JFileChooser();

    public OpenPlotImageMenuItem(TaskManager taskManager, String text) {
        setTaskManager(taskManager);
        setText(text);

        setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        addActionListener(event -> {
            int fileChooserReturnValue = fileChooser.showOpenDialog(frame);
            if (fileChooserReturnValue == JFileChooser.APPROVE_OPTION) {
                imageFile = fileChooser.getSelectedFile();
                this.taskManager.execute(new LoadImageCommand(this.taskManager, imageFile));
                frame.repaint();
                frame.requestFocus();
            } else {
                //OPEN COMMAND WAS CANCELLED BY USER
                System.out.println("Open command was cancelled by user");
            }
        });
    }

    void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    void setFrame(JFrame frame) {
        this.frame = frame;
    }
}

class SaveTableMenuItem extends JMenuItem {

    private TaskManager taskManager = null;
    private JFrame frame = null;

    boolean saveAs;
    private File tableFile = null;
    private JFileChooser fileChooser = new JFileChooser();

    SaveTableMenuItem(TaskManager taskManager, String text, boolean saveAs) {
        setTaskManager(taskManager);
        setText(text);
        this.saveAs = saveAs;
        setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        addActionListener(event -> {
            if (tableFile == null || saveAs) {
                int fileChooserReturnValue = fileChooser.showSaveDialog(frame);
                if (fileChooserReturnValue == JFileChooser.APPROVE_OPTION) {
                    tableFile = fileChooser.getSelectedFile();
                    taskManager.execute(new SaveTableCommand(taskManager, tableFile));
                } else {
                    //SAVE COMMAND WAS CANCELLED BY USER
                    System.out.println("Save command was cancelled by user");
                }
            } else {
                taskManager.execute(new SaveTableCommand(taskManager, tableFile));
            }
        });
    }

    void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    void setFrame(JFrame frame) {
        this.frame = frame;
    }

}



