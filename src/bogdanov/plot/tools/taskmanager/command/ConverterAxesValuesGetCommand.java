package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.ImageConverter;
import bogdanov.plot.tools.taskmanager.TaskManager;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConverterAxesValuesGetCommand implements GetCommand {

    private ImageConverter converter = null;

    public ConverterAxesValuesGetCommand(TaskManager taskManager) {
        this.converter = taskManager.getImageConverter();
    }

    @Override
    public Object execute() {
        Map<Point, Double> tmp = new LinkedHashMap<>();
        tmp.put(converter.getX1(), converter.getX1Value());
        tmp.put(converter.getX2(), converter.getX2Value());
        tmp.put(converter.getY1(), converter.getY1Value());
        tmp.put(converter.getY2(), converter.getY2Value());

        return tmp;
    }
}
