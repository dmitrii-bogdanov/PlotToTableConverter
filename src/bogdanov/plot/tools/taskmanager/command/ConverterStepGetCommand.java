package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.taskmanager.TaskManager;

public class ConverterStepGetCommand implements GetCommand {

    private TaskManager taskManager = null;

    public ConverterStepGetCommand(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public Object execute() {
        return taskManager.getImageConverter().getStep();
    }
}
