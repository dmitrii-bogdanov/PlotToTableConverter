package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.taskmanager.TaskManager;

public class ConverterNumberOfStepsCommand implements Command {

    private TaskManager taskManager;
    private int numberOfSteps;
    private int oldNumberOfSteps;

    public ConverterNumberOfStepsCommand(TaskManager taskManager, int numberOfSteps) {
        this.taskManager = taskManager;
        this.numberOfSteps = numberOfSteps;
        oldNumberOfSteps = taskManager.getImageConverter().getNumberOfSteps();
    }

    @Override
    public void execute() {
        taskManager.getImageConverter().setNumberOfSteps(numberOfSteps);
    }

    @Override
    public void undo() {
        taskManager.getImageConverter().setNumberOfSteps(oldNumberOfSteps);
    }
}
