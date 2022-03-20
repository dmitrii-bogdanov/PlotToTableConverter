package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.ImageConverter;
import bogdanov.plot.tools.taskmanager.TaskManager;

public class ConverterTableNameCommand implements Command {

    private ImageConverter converter = null;

    private String name = null;
    private String xLabel = null;
    private String yLabel = null;
    private String xUnits = null;
    private String yUnits = null;

    private String oldName = null;
    private String oldXLabel = null;
    private String oldYLabel = null;
    private String oldXUnits = null;
    private String oldYUnits = null;

    public ConverterTableNameCommand(TaskManager taskManager,
                                     String name,
                                     String xLabel, String yLabel,
                                     String xUnits, String yUnits) {

        this.converter = taskManager.getImageConverter();

        this.name = name;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.xUnits = xUnits;
        this.yUnits = yUnits;

        oldName = converter.getName();
        oldXLabel = converter.getXLabel();
        oldYLabel = converter.getYLabel();
        oldXUnits = converter.getXUnits();
        oldYUnits = converter.getYUnits();
    }

    @Override
    public void execute() {
        converter.setName(name);
        converter.setXLabel(xLabel);
        converter.setYLabel(yLabel);
        converter.setXUnits(xUnits);
        converter.setYUnits(yUnits);
    }

    @Override
    public void undo() {
        converter.setName(oldName);
        converter.setXLabel(oldXLabel);
        converter.setYLabel(oldYLabel);
        converter.setXUnits(oldXUnits);
        converter.setYUnits(oldYUnits);
    }
}