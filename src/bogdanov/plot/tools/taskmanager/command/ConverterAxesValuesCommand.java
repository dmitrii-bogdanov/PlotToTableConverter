package bogdanov.plot.tools.taskmanager.command;

import bogdanov.plot.tools.ImageConverter;
import bogdanov.plot.tools.taskmanager.TaskManager;

import java.awt.*;

public class ConverterAxesValuesCommand implements Command {

    private ImageConverter converter = null;

    private Point x1 = null;
    private Point x2 = null;
    private Point y1 = null;
    private Point y2 = null;

    private double x1Value = 0;
    private double x2Value = 0;
    private double y1Value = 0;
    private double y2Value = 0;

    private Point oldX1 = null;
    private Point oldX2 = null;
    private Point oldY1 = null;
    private Point oldY2 = null;

    private double oldX1Value = 0;
    private double oldX2Value = 0;
    private double oldY1Value = 0;
    private double oldY2Value = 0;


    public ConverterAxesValuesCommand(TaskManager taskManager,
                                      Point x1, Point x2,
                                      Point y1, Point y2,
                                      double x1Value,
                                      double x2Value,
                                      double y1Value,
                                      double y2Value) {

        this.converter = taskManager.getImageConverter();

        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;

        this.x1Value = x1Value;
        this.x2Value = x2Value;
        this.y1Value = y1Value;
        this.y2Value = y2Value;

        System.out.println("x1 = " + x1);
        System.out.println("x1Value = " + x1Value);
        System.out.println("x2 = " + x2);
        System.out.println("x2Value = " + x2Value);
        System.out.println("y1 = " + y1);
        System.out.println("y1Value = " + y1Value);
        System.out.println("y2 = " + y2);
        System.out.println("y2Value = " + y2Value);

        oldX1 = converter.getX1();
        oldX2 = converter.getX2();
        oldY1 = converter.getY1();
        oldY2 = converter.getY2();
        oldX1Value = converter.getX1Value();
        oldX2Value = converter.getX2Value();
        oldY1Value = converter.getY1Value();
        oldY2Value = converter.getY2Value();

    }

    @Override
    public void execute() {
        converter.setX1(x1);
        converter.setX2(x2);
        converter.setY1(y1);
        converter.setY2(y2);

        converter.setX1Value(x1Value);
        converter.setX2Value(x2Value);
        converter.setY1Value(y1Value);
        converter.setY2Value(y2Value);

        converter.setPixelSize();
    }

    @Override
    public void undo() {
        converter.setX1(oldX1);
        converter.setX2(oldX2);
        converter.setY1(oldY1);
        converter.setY2(oldY2);

        converter.setX1Value(oldX1Value);
        converter.setX2Value(oldX2Value);
        converter.setY1Value(oldY1Value);
        converter.setY2Value(oldY2Value);

        converter.setPixelSize();
    }
}
