package bogdanov.plot.window;

import bogdanov.plot.tools.taskmanager.command.*;
import org.w3c.dom.Text;
import tech.bogdanov.plot.tools.taskmanager.command.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class PlotConverterControlTabbedPane extends JTabbedPane {

    private MarkerOverlayPanel overlay = null;

    AxesStepPanel axesStepPanel = new AxesStepPanel(this);
    MarkStepPanel markStepPanel = new MarkStepPanel(this);
    CleanStepPanel cleanStepPanel = new CleanStepPanel(this);
    RangeStepPanel rangeStepPanel = new RangeStepPanel(this);
    NameStepPanel nameStepPanel = new NameStepPanel(this);
    ExportStepPanel exportStepPanel = new ExportStepPanel(this);

    public PlotConverterControlTabbedPane(MarkerOverlayPanel overlay) {
        setOverlay(overlay);
        overlay.setControlPane(this);
        setFocusable(false);
//        setPreferredSize(new Dimension(320, getParent().getPreferredSize().height - 310));

        JScrollPane axesStepScrollablePane = new JScrollPane(
                axesStepPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        axesStepScrollablePane.setPreferredSize(getPreferredSize());
        axesStepScrollablePane.setFocusable(false);

        JScrollPane markStepScrollablePane = new JScrollPane(
                markStepPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        markStepScrollablePane.setPreferredSize(getPreferredSize());
        markStepScrollablePane.setFocusable(false);

        JScrollPane cleanStepScrollablePane = new JScrollPane(
                cleanStepPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        cleanStepScrollablePane.setPreferredSize(getPreferredSize());
        cleanStepScrollablePane.setFocusable(false);

        JScrollPane rangeStepScrollablePane = new JScrollPane(
                rangeStepPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        rangeStepScrollablePane.setPreferredSize(getPreferredSize());
        rangeStepScrollablePane.setFocusable(false);

        JScrollPane nameStepScrollablePane = new JScrollPane(
                nameStepPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        nameStepScrollablePane.setPreferredSize(getPreferredSize());
        nameStepScrollablePane.setFocusable(false);

        JScrollPane exportStepScrollablePane = new JScrollPane(
                new ExportStepPanel(this),
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        exportStepScrollablePane.setPreferredSize(getPreferredSize());
        exportStepScrollablePane.setFocusable(false);

        addTab("1", axesStepScrollablePane);
        addTab("2", markStepScrollablePane);
        addTab("3", cleanStepScrollablePane);
        addTab("4", rangeStepScrollablePane);
        addTab("5", nameStepScrollablePane);
        addTab("End", exportStepScrollablePane);
    }

    public MarkerOverlayPanel getOverlay() {
        return overlay;
    }

    public void setOverlay(MarkerOverlayPanel overlay) {
        this.overlay = overlay;
        axesStepPanel.setOverlay(overlay);
        markStepPanel.setOverlay(overlay);
        cleanStepPanel.setOverlay(overlay);
        rangeStepPanel.setOverlay(overlay);
        nameStepPanel.setOverlay(overlay);
        exportStepPanel.setOverlay(overlay);
    }

    @Override
    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
        axesStepPanel.setFocusable(focusable);
        markStepPanel.setFocusable(focusable);
        cleanStepPanel.setFocusable(focusable);
        rangeStepPanel.setFocusable(focusable);
        nameStepPanel.setFocusable(focusable);
        exportStepPanel.setFocusable(focusable);
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);

        preferredSize = new Dimension(preferredSize.width - 50, preferredSize.height);
        axesStepPanel.setPreferredSize(preferredSize);
        markStepPanel.setPreferredSize(preferredSize);
        cleanStepPanel.setPreferredSize(preferredSize);
        rangeStepPanel.setPreferredSize(preferredSize);
        nameStepPanel.setPreferredSize(preferredSize);
        exportStepPanel.setPreferredSize(preferredSize);

        axesStepPanel.setMinimumSize(preferredSize);
        markStepPanel.setMinimumSize(preferredSize);
        cleanStepPanel.setMinimumSize(preferredSize);
        rangeStepPanel.setMinimumSize(preferredSize);
        nameStepPanel.setMinimumSize(preferredSize);
        exportStepPanel.setMinimumSize(preferredSize);
    }
}

class AxesStepPanel extends JPanel {

    private MarkerOverlayPanel overlay = null;

    JLabel stepLabel = new JLabel("Define Axes");
    JButton startButton = new JButton("Start");
    JToggleButton x1ToggleButton = new JToggleButton("X1 Point");
    JTextField x1ValueTextField = new JTextField("X1 Value");
    JToggleButton x2ToggleButton = new JToggleButton("X2 Point");
    JTextField x2ValueTextField = new JTextField("X2 Value");
    JToggleButton y1ToggleButton = new JToggleButton("Y1 Point");
    JTextField y1ValueTextField = new JTextField("Y1 Value");
    JToggleButton y2ToggleButton = new JToggleButton("Y2 Point");
    JTextField y2ValueTextField = new JTextField("Y2 Value");
    JButton confirmButton = new JButton("Confirm");
    JLabel task1Label = new JLabel("Choose 2 points on each axis (X and Y):");
    JLabel task2Label = new JLabel("1) Press 'Start' to begin");
    JLabel task3Label = new JLabel("2) Press red button");
    JLabel task4Label = new JLabel("3) Click point on the plot axis");
    JLabel task5Label = new JLabel("4) Input its value");
    JLabel task6Label = new JLabel("5) Press 'Confirm' to finish");
    JLabel task7Label = new JLabel("Press 'Restart' button to change input");

    private int pointSelector = 0;
    Point x1 = null;
    Point x2 = null;
    Point y1 = null;
    Point y2 = null;

    AxesStepPanel(PlotConverterControlTabbedPane controlPane) {

        int numberOfRows = 14;
        setLayout(new GridLayout(numberOfRows, 1, 2, 5));

        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stepLabel.setVerticalAlignment(SwingConstants.CENTER);
        x1ValueTextField.setHorizontalAlignment(SwingConstants.CENTER);
        x2ValueTextField.setHorizontalAlignment(SwingConstants.CENTER);
        y1ValueTextField.setHorizontalAlignment(SwingConstants.CENTER);
        y2ValueTextField.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel x1Panel = new JPanel();
        x1Panel.setLayout(new GridLayout(1, 2, 5, 0));
        x1Panel.add(x1ToggleButton);
        x1Panel.add(x1ValueTextField);

        JPanel x2Panel = new JPanel();
        x2Panel.setLayout(new GridLayout(1, 2, 5, 0));
        x2Panel.add(x2ToggleButton);
        x2Panel.add(x2ValueTextField);

        JPanel y1Panel = new JPanel();
        y1Panel.setLayout(new GridLayout(1, 2, 5, 0));
        y1Panel.add(y1ToggleButton);
        y1Panel.add(y1ValueTextField);

        JPanel y2Panel = new JPanel();
        y2Panel.setLayout(new GridLayout(1, 2, 5, 0));
        y2Panel.add(y2ToggleButton);
        y2Panel.add(y2ValueTextField);

        add(stepLabel);
        add(startButton);
        add(x1Panel);
        add(x2Panel);
        add(y1Panel);
        add(y2Panel);
        add(confirmButton);
        add(task1Label);
        add(task2Label);
        add(task3Label);
        add(task4Label);
        add(task5Label);
        add(task6Label);
        add(task7Label);


        x1ValueTextField.addFocusListener(new NumberTextFieldFocusAdapter());
        x2ValueTextField.addFocusListener(new NumberTextFieldFocusAdapter());
        y1ValueTextField.addFocusListener(new NumberTextFieldFocusAdapter());
        y2ValueTextField.addFocusListener(new NumberTextFieldFocusAdapter());

        x1ToggleButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (overlay.getTaskManager().getImageConverter() == null) {
                    System.out.println("IMAGE CONVERTER NULL");
                }
                x2ToggleButton.setSelected(false);
                y1ToggleButton.setSelected(false);
                y2ToggleButton.setSelected(false);
                overlay.getToolsManager().chooseTool("point selector");
                pointSelector = 1;
                overlay.requestFocus();
            }
        });

        x2ToggleButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                x1ToggleButton.setSelected(false);
                y1ToggleButton.setSelected(false);
                y2ToggleButton.setSelected(false);
                overlay.getToolsManager().chooseTool("point selector");
                pointSelector = 2;
                overlay.requestFocus();
            }
        });

        y1ToggleButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                x1ToggleButton.setSelected(false);
                x2ToggleButton.setSelected(false);
                y2ToggleButton.setSelected(false);
                overlay.getToolsManager().chooseTool("point selector");
                pointSelector = 3;
                overlay.requestFocus();
            }
        });

        y2ToggleButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                x1ToggleButton.setSelected(false);
                x2ToggleButton.setSelected(false);
                y1ToggleButton.setSelected(false);
                overlay.getToolsManager().chooseTool("point selector");
                pointSelector = 4;
                overlay.requestFocus();
            }
        });

        confirmButton.addActionListener(e -> {
            if (
                    TextFieldReader.isNumber(x1ValueTextField.getText()) &&
                            TextFieldReader.isNumber(x2ValueTextField.getText()) &&
                            TextFieldReader.isNumber(y1ValueTextField.getText()) &&
                            TextFieldReader.isNumber(y2ValueTextField.getText())
            ) {
                overlay.getTaskManager().execute(
                        new ConverterAxesValuesCommand(
                                overlay.getTaskManager(),
                                x1, x2, y1, y2,
                                TextFieldReader.read(x1ValueTextField.getText()),
                                TextFieldReader.read(x2ValueTextField.getText()),
                                TextFieldReader.read(y1ValueTextField.getText()),
                                TextFieldReader.read(y2ValueTextField.getText())
                        ));
                overlay.getToolsManager().off();
                x1ToggleButton.setSelected(false);
                x2ToggleButton.setSelected(false);
                y1ToggleButton.setSelected(false);
                y2ToggleButton.setSelected(false);
                controlPane.setSelectedIndex(1);
                overlay.clearMarkedPoints();
                overlay.requestFocus();

                try (FileWriter writer = new FileWriter(new File("out.txt"))) {
                    writer.write(String.format("%d %d\n%d %d\n%d %d\n%d %d\n%f\n%f\n%f\n%f\n",
                            x1.x, x1.y, x2.x, x2.y, y1.x, y1.y, y2.x, y2.y,
                            TextFieldReader.read(x1ValueTextField.getText()),
                            TextFieldReader.read(x2ValueTextField.getText()),
                            TextFieldReader.read(y1ValueTextField.getText()),
                            TextFieldReader.read(y2ValueTextField.getText())
                    ));
                } catch (IOException exc) {

                }

            }
        });
    }

    public void setPoint(Point point) {
        switch (pointSelector) {
            case 1:
                x1 = new Point(point);
                x1ToggleButton.setText(String.format("%d , %d", x1.x, x1.y));
                x1ToggleButton.setSelected(false);
                x1ToggleButton.setBackground(Color.GREEN);
                x2ToggleButton.doClick();
                break;
            case 2:
                x2 = new Point(point);
                x2ToggleButton.setText(String.format("%d , %d", x2.x, x2.y));
                x2ToggleButton.setSelected(false);
                x2ToggleButton.setBackground(Color.GREEN);
                y1ToggleButton.doClick();
                break;
            case 3:
                y1 = new Point(point);
                y1ToggleButton.setText(String.format("%d , %d", y1.x, y1.y));
                y1ToggleButton.setSelected(false);
                y1ToggleButton.setBackground(Color.GREEN);
                y2ToggleButton.doClick();
                break;
            case 4:
                y2 = new Point(point);
                y2ToggleButton.setText(String.format("%d , %d", y2.x, y2.y));
                y2ToggleButton.setSelected(false);
                y2ToggleButton.setBackground(Color.GREEN);
                if (x1ToggleButton.getText().equals("X1")) {
                    x1ToggleButton.doClick();
                }
                break;
        }
    }

    void setOverlay(MarkerOverlayPanel overlay) {
        this.overlay = overlay;
    }
}

class MarkStepPanel extends JPanel {

    private MarkerOverlayPanel overlay = null;

    JButton previousStepButton = new JButton("Previous Step");
    JLabel stepLabel = new JLabel("Define Area of the Plot Line");
    JButton startButton = new JButton("Start");
    JToggleButton markerToggleButton = new JToggleButton("Marker");
    JLabel markerSizeLabel = new JLabel("Size:");
    JComboBox<Integer> markerSizeComboBox = new JComboBox<>();
    JToggleButton markEraserToggleButton = new JToggleButton("Mark Eraser");
    JLabel markEraserSizeLabel = new JLabel("Size:");
    JComboBox<Integer> markEraserSizeComboBox = new JComboBox<>();
    JButton confirmButton = new JButton("Confirm");
    JLabel task1Label = new JLabel("1) Press 'Start' button to begin");
    JLabel task2Label = new JLabel("2) Press 'Marker' button");
    JLabel task3Label = new JLabel("Mark area of the plot line by clicking on it");
    JLabel task4Label = new JLabel("Use shift to define a line between clicks");
    JLabel task5Label = new JLabel("A free space should be touched by marked area");
    JLabel task6Label = new JLabel("Use 'Mark Eraser' to remove marked area");
    JLabel task7Label = new JLabel("3) Press 'Confirm' button to finish");
    JLabel task8Label = new JLabel("Press 'Restart' button to change input");

    MarkStepPanel(PlotConverterControlTabbedPane controlPane) {

        int numberOfRows = 16;
        setLayout(new GridLayout(numberOfRows, 1, 2, 5));

        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stepLabel.setVerticalAlignment(SwingConstants.CENTER);
        markerSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        markEraserSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel markerSizePanel = new JPanel();
        markerSizePanel.setLayout(new GridLayout(1, 2, 5, 0));
        markerSizePanel.add(markerSizeLabel);
        markerSizePanel.add(markerSizeComboBox);

        JPanel markEraserSizePanel = new JPanel();
        markEraserSizePanel.setLayout(new GridLayout(1, 2, 5, 0));
        markEraserSizePanel.add(markEraserSizeLabel);
        markEraserSizePanel.add(markEraserSizeComboBox);

        add(stepLabel);
        add(startButton);
        add(markerToggleButton);
        add(markerSizePanel);
        add(markEraserToggleButton);
        add(markEraserSizePanel);
        add(confirmButton);
        add(task1Label);
        add(task2Label);
        add(task3Label);
        add(task4Label);
        add(task5Label);
        add(task6Label);
        add(task7Label);
        add(task8Label);
        add(previousStepButton);

        markerToggleButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                overlay.getToolsManager().chooseTool("marker");
                markEraserToggleButton.setSelected(false);
                overlay.requestFocus();
            }
        });

        markEraserToggleButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                overlay.getToolsManager().chooseTool("mark eraser");
                markerToggleButton.setSelected(false);
                overlay.requestFocus();
            }
        });

        confirmButton.addActionListener(e -> {
            overlay.getToolsManager().off();
            markerToggleButton.setSelected(false);
            markEraserToggleButton.setSelected(false);
            controlPane.setSelectedIndex(2);
            overlay.requestFocus();
        });
    }

    void setOverlay(MarkerOverlayPanel overlay) {
        this.overlay = overlay;
    }
}

class CleanStepPanel extends JPanel {

    private MarkerOverlayPanel overlay = null;

    JButton previousStepButton = new JButton("Previous Step");
    JLabel stepLabel = new JLabel("Clean Area around the Plot Line");
    JButton startButton = new JButton("Start");
    JToggleButton eraserButton = new JToggleButton("Eraser");
    JLabel eraserSizeLabel = new JLabel("Size:");
    JComboBox<Integer> eraserSizeComboBox = new JComboBox<>();
    JButton confirmButton = new JButton("Confirm");
    JLabel task1Label = new JLabel("1) Press 'Start' button to begin");
    JLabel task2Label = new JLabel("2) Press 'Eraser' button if needed");
    JLabel task3Label = new JLabel("Erase other lines on the image");
    JLabel task4Label = new JLabel("inside of the marked area");
    JLabel task5Label = new JLabel("3) Press 'Confirm' button to finish");
    JLabel task6Label = new JLabel("Press 'Restart' button to change input");

    CleanStepPanel(PlotConverterControlTabbedPane controlPane) {

        int numberOfRows = 12;
        setLayout(new GridLayout(numberOfRows, 1, 2, 5));

        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stepLabel.setVerticalAlignment(SwingConstants.CENTER);
        eraserSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel eraserSizePanel = new JPanel();
        eraserSizePanel.setLayout(new GridLayout(1, 2, 5, 0));
        eraserSizePanel.add(eraserSizeLabel);
        eraserSizePanel.add(eraserSizeComboBox);

        add(stepLabel);
        add(startButton);
        add(eraserButton);
        add(eraserSizePanel);
        add(confirmButton);
        add(task1Label);
        add(task2Label);
        add(task3Label);
        add(task4Label);
        add(task5Label);
        add(task6Label);
        add(previousStepButton);

        eraserButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                overlay.getToolsManager().chooseTool("eraser");
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                overlay.getToolsManager().off();
            }
            overlay.requestFocus();
        });

        confirmButton.addActionListener(e -> {
            overlay.getTaskManager().execute(new ConverterMarkerCommand(
                    overlay.getTaskManager(), overlay.getMarkedPoints()
            ));
            eraserButton.setSelected(false);
            overlay.getToolsManager().off();
            controlPane.setSelectedIndex(3);
            overlay.requestFocus();
            try (FileWriter writer = new FileWriter(new File("out.txt"), true)) {
                int pixel;
                for (Point p : overlay.getMarkedPoints()) {
                    pixel = overlay.getTaskManager().getImageDrawer().getPixel(p);
                    writer.write(String.format("%d %d %d\n", p.x, p.y, pixel));
                }
            } catch (IOException exc) {

            }
        });
    }

    void setOverlay(MarkerOverlayPanel overlay) {
        this.overlay = overlay;
    }
}

class RangeStepPanel extends JPanel {

    private MarkerOverlayPanel overlay = null;

    JButton previousStepButton = new JButton("Previous Step");
    JLabel stepLabel = new JLabel("Define Range and Step");
    JButton startButton = new JButton("Start");
    JLabel rangeFromLabel = new JLabel("From:");
    JLabel rangeToLabel = new JLabel("To:");
    JTextField rangeFromTextField = new JTextField("From");
    JTextField rangeToTextField = new JTextField("To");
    JLabel numberOfStepsLabel = new JLabel("From:");
    JLabel stepSizeLabel = new JLabel("To:");
    JTextField numberOfStepsTextField = new JTextField("Number of Steps");
    JTextField stepSizeTextField = new JTextField("Step Size");
    JButton confirmButton = new JButton("Confirm");
    JLabel task1Label = new JLabel("1) Press 'Start' button to begin");
    JLabel task2Label = new JLabel("2) Input the range of points");
    JLabel task3Label = new JLabel("3) Input the number of points");
    JLabel task4Label = new JLabel("   or size of a step");
    JLabel task5Label = new JLabel("4) Press 'Confirm' button to finish");
    JLabel task6Label = new JLabel("Press 'Restart' button to change input");

    RangeStepPanel(PlotConverterControlTabbedPane controlPane) {

        int numberOfRows = 14;
        setLayout(new GridLayout(numberOfRows, 1, 2, 5));

        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stepLabel.setVerticalAlignment(SwingConstants.CENTER);
        rangeFromLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rangeFromTextField.setHorizontalAlignment(SwingConstants.CENTER);
        rangeToLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rangeToTextField.setHorizontalAlignment(SwingConstants.CENTER);
        numberOfStepsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numberOfStepsTextField.setHorizontalAlignment(SwingConstants.CENTER);
        stepSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stepSizeTextField.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel rangeLabelsPanel = new JPanel();
        rangeLabelsPanel.setLayout(new GridLayout(1, 2, 5, 0));
        rangeLabelsPanel.add(rangeFromLabel);
        rangeLabelsPanel.add(rangeToLabel);

        JPanel rangeTextFieldsPanel = new JPanel();
        rangeTextFieldsPanel.setLayout(new GridLayout(1, 2, 5, 0));
        rangeTextFieldsPanel.add(rangeFromTextField);
        rangeTextFieldsPanel.add(rangeToTextField);

        JPanel stepLabelsPanel = new JPanel();
        stepLabelsPanel.setLayout(new GridLayout(1, 2, 5, 0));
        stepLabelsPanel.add(numberOfStepsLabel);
        stepLabelsPanel.add(stepSizeLabel);

        JPanel stepTextFieldsPanel = new JPanel();
        stepTextFieldsPanel.setLayout(new GridLayout(1, 2, 5, 0));
        stepTextFieldsPanel.add(numberOfStepsTextField);
        stepTextFieldsPanel.add(stepSizeTextField);

        add(stepLabel);
        add(startButton);
        add(rangeLabelsPanel);
        add(rangeTextFieldsPanel);
        add(stepLabelsPanel);
        add(stepTextFieldsPanel);
        add(confirmButton);
        add(task1Label);
        add(task2Label);
        add(task3Label);
        add(task4Label);
        add(task5Label);
        add(task6Label);
        add(previousStepButton);

        rangeFromTextField.addActionListener(e -> swapRange());

        rangeToTextField.addActionListener(e -> swapRange());

        stepSizeTextField.addActionListener(e -> setNumberOfSteps());

        numberOfStepsTextField.addActionListener(e -> setStep());

        confirmButton.addActionListener(e -> {
            overlay.getTaskManager().execute(
                    new ConverterTableRangeCommand(
                            overlay.getTaskManager(),
                            TextFieldReader.read(rangeFromTextField.getText()),
                            TextFieldReader.read(rangeToTextField.getText()),
                            TextFieldReader.read(stepSizeTextField.getText())
                    )
            );
            controlPane.setSelectedIndex(4);
        });
    }

    void setOverlay(MarkerOverlayPanel overlay) {
        this.overlay = overlay;
    }

    private void swapRange() {
        if (TextFieldReader.isNumber(rangeFromTextField.getText()) &&
                TextFieldReader.isNumber((rangeToTextField.getText()))) {
            if (TextFieldReader.read(rangeFromTextField.getText()) >
                    TextFieldReader.read(rangeToTextField.getText())) {
                String tmp = rangeFromTextField.getText();
                rangeFromTextField.setText(rangeToTextField.getText());
                rangeToTextField.setText(tmp);
            }
        }
    }

    private void setNumberOfSteps() {
        if (TextFieldReader.isNumber(stepSizeTextField.getText()) &&
                TextFieldReader.isNumber(rangeFromTextField.getText()) &&
                TextFieldReader.isNumber(rangeToTextField.getText())) {
            overlay.getTaskManager().execute(
                    new ConverterStepCommand(
                            overlay.getTaskManager(),
                            TextFieldReader.read(stepSizeTextField.getText())
                    ));
            numberOfStepsTextField.setText(String.valueOf(
                    (int) overlay.getTaskManager().execute(
                            new ConverterNumberOfStepsGetCommand(
                                    overlay.getTaskManager()
                            ))));
        }
    }

    private void setStep() {
        if (TextFieldReader.isNumber(numberOfStepsTextField.getText()) &&
                TextFieldReader.isNumber(rangeFromTextField.getText()) &&
                TextFieldReader.isNumber(rangeToTextField.getText())) {
            overlay.getTaskManager().execute(
                    new ConverterNumberOfStepsCommand(
                            overlay.getTaskManager(),
                            TextFieldReader.readInt(numberOfStepsTextField.getText())
                    ));
            numberOfStepsTextField.setText(String.valueOf(
                    (int) overlay.getTaskManager().execute(
                            new ConverterStepGetCommand(
                                    overlay.getTaskManager()
                            ))));
        }
    }

}

class NameStepPanel extends JPanel {

    private MarkerOverlayPanel overlay = null;

    JButton previousStepButton = new JButton("Previous Step");
    JLabel stepLabel = new JLabel("Define Range and Step");
    JButton startButton = new JButton("Start");
    JLabel tableNameLabel = new JLabel("Name of the Table");
    JTextField tableNameTextField = new JTextField("Name of the Table");
    JLabel xLabel = new JLabel("X Axis Label");
    JTextField xLabelTextField = new JTextField("X Axis Label");
    JLabel yLabel = new JLabel("Y Axis Label");
    JTextField yLabelTextField = new JTextField("Y Axis Label");
    JLabel xUnitsLabel = new JLabel("X Axis Units");
    JTextField xUnitsTextField = new JTextField("X Axis Units");
    JLabel yUnitsLabel = new JLabel("Y Axis Units");
    JTextField yUnitsTextField = new JTextField("Y Axis Units");
    JButton confirmButton = new JButton("Confirm");
    JLabel task1Label = new JLabel("1) Press 'Start' button to begin");
    JLabel task2Label = new JLabel("2) Input name of the table");
    JLabel task3Label = new JLabel("3) Input names of axes");
    JLabel task4Label = new JLabel("3) Input units of axes");
    JLabel task5Label = new JLabel("4) Press 'Confirm' button to finish");
    JLabel task6Label = new JLabel("Press 'Restart' button to change input");

    NameStepPanel(PlotConverterControlTabbedPane controlPane) {

        int numberOfRows = 18;
        setLayout(new GridLayout(numberOfRows, 1, 2, 5));

        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stepLabel.setVerticalAlignment(SwingConstants.CENTER);
        tableNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tableNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
        xLabel.setHorizontalAlignment(SwingConstants.CENTER);
        xLabelTextField.setHorizontalAlignment(SwingConstants.CENTER);
        yLabel.setHorizontalAlignment(SwingConstants.CENTER);
        yLabelTextField.setHorizontalAlignment(SwingConstants.CENTER);
        xUnitsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        xUnitsTextField.setHorizontalAlignment(SwingConstants.CENTER);
        yUnitsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        yUnitsTextField.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel xUnitsPanel = new JPanel();
        xUnitsPanel.setLayout(new GridLayout(1, 2, 5, 0));
        xUnitsPanel.add(xUnitsLabel);
        xUnitsPanel.add(xUnitsTextField);

        JPanel yUnitsPanel = new JPanel();
        yUnitsPanel.setLayout(new GridLayout(1, 2, 5, 0));
        yUnitsPanel.add(yUnitsLabel);
        yUnitsPanel.add(yUnitsTextField);

        add(stepLabel);
        add(startButton);
        add(tableNameLabel);
        add(tableNameTextField);
        add(xLabel);
        add(xLabelTextField);
        add(yLabel);
        add(yLabelTextField);
        add(xUnitsPanel);
        add(yUnitsPanel);
        add(confirmButton);
        add(task1Label);
        add(task2Label);
        add(task3Label);
        add(task4Label);
        add(task5Label);
        add(task6Label);
        add(previousStepButton);

        confirmButton.addActionListener(e -> {
            overlay.getTaskManager().execute(new ConverterTableNameCommand(
                    overlay.getTaskManager(),
                    tableNameTextField.getText(),
                    xLabelTextField.getText(), yLabelTextField.getText(),
                    xUnitsTextField.getText(), yUnitsTextField.getText()
            ));
            controlPane.setSelectedIndex(5);
            try (FileWriter writer = new FileWriter(new File("out.txt"), true)) {
                    writer.write(tableNameTextField.getText()+"\n");
                    writer.write(xLabelTextField.getText()+"\n");
                    writer.write(yLabelTextField.getText()+"\n");
                    writer.write(xUnitsTextField.getText()+"\n");
                    writer.write(yUnitsTextField.getText()+"\n");
            } catch (IOException exc) {

            }
        });
    }

    void setOverlay(MarkerOverlayPanel overlay) {
        this.overlay = overlay;
    }
}

class ExportStepPanel extends JPanel {

    private MarkerOverlayPanel overlay = null;

    JButton previousStepButton = new JButton("Previous Step");
    JLabel stepLabel = new JLabel("Export Table");
    JButton saveButton = new JButton("Save as File");
    JButton exportButton = new JButton("Export to Data File");
    JButton plotButton = new JButton("Plot on the image");
    JLabel emptyLabel = new JLabel();

    ExportStepPanel(PlotConverterControlTabbedPane controlPane) {

        int numberOfRows = 6;
        setLayout(new GridLayout(numberOfRows, 1, 2, 5));

        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stepLabel.setVerticalAlignment(SwingConstants.CENTER);

        add(stepLabel);
        add(saveButton);
        add(exportButton);
        add(plotButton);
        add(emptyLabel);
        add(previousStepButton);

        plotButton.addActionListener(e -> {
            this.overlay = controlPane.getOverlay();
            overlay.getTaskManager().execute(new ConverterMakeTableCommand(overlay.getTaskManager()));
            System.out.println(overlay.getTaskManager().getImageConverter().getTable());
            overlay.getToolsManager().chooseTool("pencil");
            overlay.requestFocus();
            overlay.clearMarkedPoints();
            overlay.add((LinkedHashSet) controlPane.getOverlay().getTaskManager().execute(
                    new ConverterTablePointsGetCommand(controlPane.getOverlay().getTaskManager()
                    )));

        });
    }

    void setOverlay(MarkerOverlayPanel overlay) {
        this.overlay = overlay;
    }
}

class siTextFieldReader {
    static boolean isNumber(String str) {
        return str.matches("[-+]?\\d*[.,]?\\d+");
    }

    static double read(String str) {
        return Double.parseDouble(str.replace(',', '.'));
    }

    static int readInt(String str) {
        return (int) Math.round(read(str));
    }
}

class NumberTextFieldFocusAdapter extends FocusAdapter {

    NumberTextFieldFocusAdapter() {
    }

    @Override
    public void focusGained(FocusEvent e) {
        super.focusGained(e);
        ((JTextField) (e.getSource())).selectAll();
    }

    @Override
    public void focusLost(FocusEvent e) {
        super.focusLost(e);
        if (TextFieldReader.isNumber(((JTextField) (e.getSource())).getText())) {
            ((JTextField) (e.getSource())).setBackground(Color.GREEN);
        } else {
            ((JTextField) (e.getSource())).setBackground(Color.RED);
        }
    }
}