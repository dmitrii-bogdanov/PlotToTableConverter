package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.taskmanager.TaskManager;

public class ConverterNumberOfStepsGetCommand implements GetCommand {

    private TaskManager taskManager = null;

    public ConverterNumberOfStepsGetCommand(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public Object execute() {
        return taskManager.getImageConverter().getNumberOfSteps();
    }
}