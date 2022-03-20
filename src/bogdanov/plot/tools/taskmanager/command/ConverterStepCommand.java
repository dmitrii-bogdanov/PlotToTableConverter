package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.taskmanager.TaskManager;

public class ConverterStepCommand implements Command {

    private TaskManager taskManager;
    private double step;
    private double oldStep;

    public ConverterStepCommand(TaskManager taskManager, double step) {
        this.taskManager = taskManager;
        this.step = step;
        oldStep = taskManager.getImageConverter().getStep();
    }

    @Override
    public void execute() {
        taskManager.getImageConverter().setStep(step);
    }

    @Override
    public void undo() {
        taskManager.getImageConverter().setStep(oldStep);
    }
}
