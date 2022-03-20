package bogdanov.plot.tools;

import bogdanov.plot.data.DataTable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ImageConverter {

    private DataTable table = new DataTable();
    private BufferedImage image = null;

    private Point x1 = null;
    private Point x2 = null;
    private Point y1 = null;
    private Point y2 = null;
    private double x1Value = -1;
    private double x2Value = -1;
    private double y1Value = -1;
    private double y2Value = -1;

    private int xOffset = -1;
    private int yOffset = -1;
    private double xPixelSize = -1;
    private double yPixelSize = -1;
    private double xValueOffset = 0;
    private double yValueOffset = 0;

    private Point[] plotArea = new Point[2];
    private double leftBorder = 0;
    private double rightBorder = 0;
    private double step = 0;
    private int numberOfSteps = 10;

    private Set<Point> markedPoints = new HashSet<>();
    private Set<Point> tablePoints = null;
    private Set<Point> plotPoints = null;

    private Map<Point, Integer> tmpMap = new HashMap<>();

    public ImageConverter(BufferedImage image) {
        if (image != null) {
            this.image = image;
        } else {
            throw new NullPointerException("new ImageConverter(BufferedImage image): image can't be NULL");
        }
//        File file = new File("out.txt");
//        if (file.isFile()) {
//            System.out.println("Reading File");
//            try (Scanner scanner = new Scanner(file)) {
//
//                setX1(new Point(scanner.nextInt(), scanner.nextInt()));
//                System.out.println("x1: " + x1);
//                setX2(new Point(scanner.nextInt(), scanner.nextInt()));
//                System.out.println("x2: " + x2);
//                setY1(new Point(scanner.nextInt(), scanner.nextInt()));
//                System.out.println("y1: " + y1);
//                setY2(new Point(scanner.nextInt(), scanner.nextInt()));
//                System.out.println("y2: " + y2);
//
//                setX1Value(scanner.nextDouble());
//                System.out.println("x1 Value: " + x1Value);
//                setX2Value(scanner.nextDouble());
//                System.out.println("x2 Value: " + x2Value);
//                setY1Value(scanner.nextDouble());
//                System.out.println("y1 Value: " + y1Value);
//                setY2Value(scanner.nextDouble());
//                System.out.println("y2 Value: " + y2Value);
//
//                setPixelSize();
//                System.out.println("x Size: " + xPixelSize);
//                System.out.println("x Offset: " + xOffset);
//                System.out.println("x Value Offset: " + xValueOffset);
//                System.out.println("y Size: " + yPixelSize);
//                System.out.println("y Offset: " + yOffset);
//                System.out.println("y Value Offset: " + yValueOffset);
//
//                scanner.nextLine();
//
//                setName(scanner.nextLine());
//                System.out.println("table Name: " + getName());
//                setXLabel(scanner.nextLine());
//                System.out.println("x Label: " + getXLabel());
//                setYLabel(scanner.nextLine());
//                System.out.println("y Label: " + getYLabel());
//                setXUnits(scanner.nextLine());
//                System.out.println("x Units: " + getXUnits());
//                setYUnits(scanner.nextLine());
//                System.out.println("y Units: " + getYUnits());
//
//                while (scanner.hasNext()) {
//                    tmpMap.put(new Point(scanner.nextInt(), scanner.nextInt()), scanner.nextInt());
//                }
//
//            } catch (FileNotFoundException e) {
//                System.out.println("File not found");
//            }
//        }
    }

    public ImageConverter(Image image) {
        if (image != null) {
            this.image = (BufferedImage) image;
        } else {
            throw new NullPointerException("new ImageConverter(Image image): image can't be NULL");
        }
    }


    public DataTable getTable() {
        return table;
    }

    public void setTable(DataTable table) {
        this.table = table;
    }

    public void setTable() {
        setPlotPoints();
        table.add(convertPoints(plotPoints));
        table.resize(leftBorder, rightBorder, step);
//        table.resize(100, 700, 1.0);
//        try (FileWriter writer = new FileWriter(new File("resizedTable.txt"))) {
//            writer.write(table.toString());
//        } catch (IOException e) {
//
//        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) throws NullPointerException {
        if (image != null) {
            this.image = image;
        } else {
            throw new NullPointerException("ImageConverter.setImage(BufferedImage image): image can't be NULL");
        }
    }

    public void setImage(Image image) throws NullPointerException {
        if (image != null) {
            this.image = (BufferedImage) image;
        } else {
            throw new NullPointerException("ImageConverter.setImage(Image image): image can't be NULL");
        }
    }


    public Point getX1() {
        return x1;
    }

    public void setX1(Point point) {
        x1 = point;
    }

    public Point getX2() {
        return x2;
    }

    public void setX2(Point point) {
        x2 = point;
    }

    public Point getY1() {
        return y1;
    }

    public void setY1(Point point) {
        y1 = point;
    }

    public Point getY2() {
        return y2;
    }

    public void setY2(Point point) {
        y2 = point;
    }


    public double getX1Value() {
        return x1Value;
    }

    public void setX1Value(double value) {
        x1Value = value;
    }

    public double getX2Value() {
        return x2Value;
    }

    public void setX2Value(double value) {
        x2Value = value;
    }

    public double getY1Value() {
        return y1Value;
    }

    public void setY1Value(double value) {
        y1Value = value;
    }

    public double getY2Value() {
        return y2Value;
    }

    public void setY2Value(double value) {
        y2Value = value;
    }


    public double[] getPixelSize() {
        return new double[]{xPixelSize, yPixelSize};
    }

    public void setPixelSize() {
        xPixelSize = x1.x < x2.x ?
                (x2Value - x1Value) / (x2.x - x1.x) :
                (x1Value - x2Value) / (x1.x - x2.x);
        System.out.println("xPixelSize = " + xPixelSize);
        xOffset = x1.x < x2.x ? x1.x : x2.x;
        xValueOffset = x1.x < x2.x ? x1Value : x2Value;

        yPixelSize = y2.y < y1.y ?
                (y2Value - y1Value) / (y2.y - y1.y) :
                (y1Value - y2Value) / (y1.y - y2.y);
        System.out.println("yPixelSize = " + yPixelSize);
        yOffset = y1.y < y2.y ? y1.y : y2.y;
        yValueOffset = y2.y < y1.y ? y2Value : y1Value;
    }

    public Point[] getPlotArea() {
        return plotArea;
    }

    public void setPlotArea(Point[] points) {
        if (points[0].x < points[1].x) {
            plotArea[0] = points[0];
            plotArea[1] = points[1];
        } else {
            plotArea[0] = points[1];
            plotArea[1] = points[0];
        }
    }

    public double getLeftBorder() {
        return leftBorder;
    }

    public void setLeftBorder(double value) {
        if (value < rightBorder) {
            leftBorder = value;
        } else {
            leftBorder = rightBorder;
            rightBorder = value;
        }
        step = (rightBorder - leftBorder) / (numberOfSteps - 1);
    }

    public double getRightBorder() {
        return rightBorder;
    }

    public void setRightBorder(double value) {
        if (value > leftBorder) {
            rightBorder = value;
        } else {
            rightBorder = leftBorder;
            leftBorder = value;
        }
        step = (rightBorder - leftBorder) / (numberOfSteps - 1);
    }

    public double[] getRange() {
        return new double[]{leftBorder, rightBorder};
    }

    public void setRange(double[] range) {
        if (range.length == 2) {
            if (range[0] < range[1]) {
                leftBorder = range[0];
                rightBorder = range[1];
            } else {
                leftBorder = range[1];
                rightBorder = range[0];
            }
        }
        step = (rightBorder - leftBorder) / (numberOfSteps - 1);
    }

    public double getStep() {
        return step;
    }

    public void setStep(double value) {
        if (value > 0) {
            step = value;
            calculateNumberOfSteps();
        } else {
            calculateStep();
        }
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(int value) {
        numberOfSteps = value > 0 && value < 1024 ? value : 10;
        calculateStep();
    }

    private void calculateNumberOfSteps() {
        if (step > 0) {
            double n = (rightBorder - leftBorder) / step;
            if (n >= 1024) {
                numberOfSteps = 10;
                calculateStep();
            } else if (n > 0) {
                numberOfSteps = (int) Math.round(n);
            } else {
                numberOfSteps = 10;
                calculateStep();
            }
        } else {
            numberOfSteps = 10;
            calculateStep();
        }
    }

    private void calculateStep() {
        step = (rightBorder - leftBorder) / numberOfSteps;
    }


    public Set<Point> getMarkedPoints() {
        return markedPoints;
    }

    public void setMarkedPoints(Set<Point> markedPoints) {
        this.markedPoints = markedPoints;
    }

    public void addMarkedPoints(Point point) {
        markedPoints.add(point);
    }

    public void addMarkedPoints(Set<Point> points) {
        markedPoints.addAll(points);
    }

    public void removeMarkedPoints(Point point) {
        markedPoints.remove(point);
    }

    public void removeMarkedPoints(Set<Point> points) {
        markedPoints.removeAll(points);
    }

    public Set<Point> getTablePoints() {
        setTablePoints();
        return tablePoints;
    }

    private void setTablePoints() {
        if (!table.isEmpty()) {
            tablePoints = new LinkedHashSet<>();
//            table.sort();
            int x, y;
            for (double[] p : table.getTable()) {
                x = (int) ((p[0] - xValueOffset) / xPixelSize) + xOffset;
                y = (int) ((p[1] - yValueOffset) / yPixelSize) + yOffset;
                tablePoints.add(new Point(x, y));
            }
        }
    }

    public String getName() {
        return table.getName();
    }

    public void setName(String name) {
        table.setName(name);
    }

    public String getXLabel() {
        return table.getXLabel();
    }

    public void setXLabel(String xLabel) {
        table.setXLabel(xLabel);
    }

    public String getYLabel() {
        return table.getYLabel();
    }

    public void setYLabel(String yLabel) {
        table.setYLabel(yLabel);
    }

    public String getXUnits() {
        return table.getXUnits();
    }

    public void setXUnits(String xUnits) {
        table.setXUnits(xUnits);
    }

    public String getYUnits() {
        return table.getYUnits();
    }

    public void setYUnits(String yUnits) {
        table.setYUnits(yUnits);
    }

    public Set<Point> getPlotPoints() {
        return plotPoints;
    }

    private void setPlotPoints() {
        plotPoints = new HashSet<>();
        if (!markedPoints.isEmpty()) {
            Map<Integer, ColourSubstitute> csMap = new HashMap<>();
            ColourSubstitute cs;
            int rgba;
            for (Point p : markedPoints) {
                rgba = image.getRGB(p.x, p.y);
                if (rgba != -1) {
                    cs = new ColourSubstitute(rgba, p.y);
                    if (csMap.containsKey(p.x)) {
                        csMap.get(p.x).add(cs);
                    } else {
                        csMap.put(p.x, cs);
                    }
                }
            }
            int x, y;
            for (Map.Entry<Integer, ColourSubstitute> e : csMap.entrySet()) {
                x = e.getKey();
                y = e.getValue().getY();
                if (y != 0) {
                    plotPoints.add(new Point(x, y));
                }
            }
        }
    }

    public void setPlotPoints(Map<Point, Integer> colorMap) {
        plotPoints = new HashSet<>();
        if (!colorMap.isEmpty()) {
            Map<Integer, ColourSubstitute> csMap = new HashMap<>();
            ColourSubstitute cs;
            int rgba;
            for (Point p : colorMap.keySet()) {
                rgba = colorMap.get(p);
                if (rgba != -1) {
                    cs = new ColourSubstitute(rgba, p.y);
                    if (csMap.containsKey(p.x)) {
                        csMap.get(p.x).add(cs);
                    } else {
                        csMap.put(p.x, cs);
                    }
                }
            }
            int x, y;
            for (Map.Entry<Integer, ColourSubstitute> e : csMap.entrySet()) {
                x = e.getKey();
                y = e.getValue().getY();
                if (y != 0) {
                    plotPoints.add(new Point(x, y));
                }
            }
        }
    }

    private double[] convertPoints(Point point) {
        return new double[]{
                (point.x - xOffset) * xPixelSize + xValueOffset,
                (point.y - yOffset) * yPixelSize + yValueOffset
        };
    }

    private Map<Double, Double> convertPoints(Set<Point> points) {
        Map<Double, Double> tmp = new HashMap<>();
        Double x, y;
        for (Point point : points) {
            x = (point.x - xOffset) * xPixelSize + xValueOffset;
            y = (point.y - yOffset) * yPixelSize + yValueOffset;
            tmp.put(x, y);
        }
        try (FileWriter writer = new FileWriter(new File("table.txt"))) {
            for (Map.Entry<Double, Double> e : tmp.entrySet()) {
                writer.write(String.format("%f %f\n", e.getKey(), e.getValue()));
            }
        } catch (IOException e) {

        }
        return tmp;
    }

    public int[] getOffset() {
        return new int[]{xOffset, yOffset};
    }

    public double[] getPointValue(Point point) {
        if (point != null && xOffset >= 0) {
            return new double[]{
                    (point.x - xOffset) * xPixelSize + xValueOffset,
                    (point.y - yOffset) * yPixelSize + yValueOffset
            };
        }
        return new double[0];
    }
}
