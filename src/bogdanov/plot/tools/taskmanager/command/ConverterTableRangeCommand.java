package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.ImageConverter;
import bogdanov.plot.tools.taskmanager.TaskManager;

public class ConverterTableRangeCommand implements Command {

    private ImageConverter converter = null;

    private double leftBorder;
    private double rightBorder;
    private double step;
    private int numberOfSteps;

    private double oldLeftBorder;
    private double oldRightBorder;
    private double oldStep = 0;
    private int oldNumberOfSteps = 0;

    public ConverterTableRangeCommand(
            TaskManager taskManager,
            double leftBorder,
            double rightBorder,
            double step
    ) {
        this.converter = taskManager.getImageConverter();
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.step = step;

        oldLeftBorder = converter.getLeftBorder();
        oldRightBorder = converter.getRightBorder();
        oldStep = converter.getStep();
    }

    public ConverterTableRangeCommand(
            TaskManager taskManager,
            double leftBorder,
            double rightBorder,
            int numberOfSteps
    ) {
        this.converter = taskManager.getImageConverter();
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.numberOfSteps = numberOfSteps;

        oldLeftBorder = converter.getLeftBorder();
        oldRightBorder = converter.getRightBorder();
        oldNumberOfSteps = converter.getNumberOfSteps();
    }

    @Override
    public void execute() {
        converter.setLeftBorder(leftBorder);
        converter.setRightBorder(rightBorder);
        if (step == 0) {
            converter.setNumberOfSteps(numberOfSteps);
        } else {
            converter.setStep(step);
        }
    }

    @Override
    public void undo() {
        converter.setLeftBorder(oldLeftBorder);
        converter.setRightBorder(oldRightBorder);
        converter.setStep(oldStep);
    }
}
