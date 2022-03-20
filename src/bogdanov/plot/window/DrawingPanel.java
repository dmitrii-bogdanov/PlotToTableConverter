package bogdanov.plot.window;

import bogdanov.plot.tools.taskmanager.TaskManager;
import bogdanov.plot.tools.taskmanager.command.*;
import bogdanov.plot.window.tools.ToolsManager;
import tech.bogdanov.plot.tools.taskmanager.command.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

//public class PlotDrawingScrollablePane extends JScrollPane {
//
//    private TaskManager taskManager = null;
//    static DrawingPanel drawingPanel = new DrawingPanel();
//
//    public PlotDrawingScrollablePane(TaskManager taskManager) {
//        super(drawingPanel, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_ALWAYS);
//        setTaskManager(taskManager);
//        add(drawingPanel);
//        setVisible(true);
//    }
//
//    @Override
//    public void repaint() {
//        super.repaint();
////        drawingPanel.repaint();
//    }
//
//    @Override
//    public void setPreferredSize(Dimension preferredSize) {
//        super.setPreferredSize(preferredSize);
//        drawingPanel.setPreferredSize(preferredSize);
//    }
//
//    public TaskManager getTaskManager() {
//        return taskManager;
//    }
//
//    public void setTaskManager(TaskManager taskManager) {
//        this.taskManager = taskManager;
//        drawingPanel.setTaskManager(taskManager);
//    }
//
//}

public class DrawingPanel extends JPanel {

    private TaskManager taskManager = null;
    MarkerOverlayPanel overlay = new MarkerOverlayPanel(this);
    private Image image = null;

    DrawingPanel(TaskManager taskManager) {
        setTaskManager(taskManager);
        setLayout(new BorderLayout());
        add(overlay, BorderLayout.CENTER);
        setBackground(Color.WHITE);
        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        image = (Image) taskManager.execute(new ResizedImageGetCommand(taskManager));
        if (image != null) {
            setSize(new Dimension(image.getWidth(null), image.getHeight(null)));
            setPreferredSize(getSize());
            g.drawImage(image, 0, 0, null);
        }
    }

    MarkerOverlayPanel getOverlay() {
        return overlay;
    }

    @Override
    public void repaint() {
        super.repaint();
//        markerOverlayPanel.repaint();
    }

    @Override
    public void setSize(Dimension size) {
        super.setSize(size);
        overlay.setSize(size);
        overlay.repaint();
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        overlay.setPreferredSize(preferredSize);
        overlay.repaint();
    }

//    @Override
//    public void scrollRectToVisible(Rectangle view) {
//        super.scrollRectToVisible(view);
//        overlay.scrollRectToVisible(view);
//    }

    TaskManager getTaskManager() {
        return taskManager;
    }

    void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
        overlay.setTaskManager(taskManager);
    }
}

class MarkerOverlayPanel extends JPanel {

    private TaskManager taskManager = null;
    private PlotConverterControlTabbedPane controlPane = null;
    private OverlayMouseAdapter mouseAdapter = new OverlayMouseAdapter(this);

    private Set<Point> resizedMarkedPoints = new HashSet<>();
    private Set<Point> markedPoints = new HashSet<>();
    private CoordinateOverlayPanel coordinateOverlayPanel = new CoordinateOverlayPanel();
    private DrawingPanel drawingPanel = null;
    private Point position = null;
    private Point selectedPoint = new Point();

    private ToolsManager toolsManager = new ToolsManager();

    private Map<Point, Integer> tmp = new HashMap<>();

    MarkerOverlayPanel(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
        setTaskManager(drawingPanel.getTaskManager());
        setLayout(new BorderLayout(0, 0));
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        add(coordinateOverlayPanel, BorderLayout.CENTER);
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        setVisible(true);
        setFocusable(true);
        setRequestFocusEnabled(true);
    }

    DrawingPanel getDrawingPanel() {
        return drawingPanel;
    }

//    @Override
//    public void setBackground(Color bg) {
//        super.setBackground(bg);
//        coordinateOverlayPanel.setBackground(bg);
//    }

    @Override
    public void paintComponent(Graphics g) {
        if (!markedPoints.isEmpty()) {
            resizedMarkedPoints =
                    (HashSet) taskManager.execute(new ResizedPointsGetCommand(taskManager, markedPoints));
            if (toolsManager.getActiveToolName().equals("pencil")) {
                g.setColor(toolsManager.getActiveColor());
                for (Point p : resizedMarkedPoints) {
                    g.fillRect(p.x - 2, p.y - 2, 5, 5);
                }
            } else {
                g.setColor(toolsManager.getMarker().getColor());
                for (Point p : resizedMarkedPoints) {
                    g.fillRect(p.x, p.y, 1, 1);
                }
            }
        }

//        if (taskManager.getImageConverter() != null) {
//            if (!tmp.isEmpty()) {
//                System.out.println("ColorMap is NOT empty");
//            } else {
//                System.out.println("ColorMap IS empty");
//            }
//            taskManager.getImageConverter().setPlotPoints(tmp);
//            g.setColor(new Color(255, 0, 0, 100));
//            Set<Point> plotPoints = taskManager.getImageConverter().getPlotPoints();
//            try (FileWriter writer = new FileWriter(new File("plotpoints.txt"))) {
//                for (Point p : taskManager.getImageConverter().getPlotPoints()) {
//                    writer.write(String.format("%d %d\n", p.x, p.y));
//                    g.fillRect(p.x - 2, p.y - 2, 5, 5);
//                }
//            } catch (IOException e) {
//
//            }
//        } else {
//            try (Scanner scanner = new Scanner(new File("out.txt"))) {
//                for (int i = 0; i < 13; i++) {
//                    scanner.nextLine();
//                }
//                g.setColor(Color.RED);
//                g.fillRect(0, 0, 1000, 1000);
//                int x, y, color, a, b, c;
//                while (scanner.hasNext()) {
//                    x = scanner.nextInt();
//                    y = scanner.nextInt();
//                    color = scanner.nextInt();
//                    if (color != -1) {
//                        tmp.put(new Point(x, y), color);
//                        a = color & 0xff;
//                        b = (color >> 8) & 0xff;
//                        c = (color >> 16) & 0xff;
//                        g.setColor(new Color(c, b, a, 255));
//                        g.fillRect(x, y, 1, 1);
//                    }
//                }
//
//            } catch (FileNotFoundException e) {
//
//            }
//        }

        g.setColor(toolsManager.getActiveColor());
        for (Point p : toolsManager.getScreenArea(position)) {
            g.fillRect(p.x, p.y, 1, 1);
        }
    }

    public void setControlPane(PlotConverterControlTabbedPane controlPane) {
        this.controlPane = controlPane;
    }

    public void setPosition(Point point) {
        position = point;
        coordinateOverlayPanel.setPosition(point);
        repaint();
    }

    public void selectPoint(Point point) {
        selectedPoint.x = point.x;
        selectedPoint.y = point.y;
        controlPane.axesStepPanel.setPoint(selectedPoint);
        toolsManager.off();
    }

    public Point getSelectedPoint() {
        return selectedPoint;
    }

    @Override
    public void setPreferredSize(Dimension size) {
        super.setPreferredSize(size);
        coordinateOverlayPanel.setPreferredSize(size);
    }

    @Override
    public void setSize(Dimension size) {
        super.setSize(size);
        coordinateOverlayPanel.setSize(size);
    }

//    @Override
//    public void repaint() {
//        super.repaint();
//        coordinateOverlayPanel.repaint();
//    }

    @Override
    public void scrollRectToVisible(Rectangle view) {
        super.scrollRectToVisible(view);
        drawingPanel.scrollRectToVisible(view);
        coordinateOverlayPanel.scrollRectToVisible(view);
    }

    public Set<Point> getMarkedPoints() {
        return markedPoints;
    }

    public void clearMarkedPoints() {
        markedPoints.clear();
        resizedMarkedPoints.clear();
        repaint();
    }

    public void add(Point point) {
        markedPoints.addAll(resize(point));
        repaint();
    }

    public void add(Set<Point> points) {
        markedPoints.addAll(resize(points));
        repaint();
    }

    public void remove(Point point) {
        markedPoints.removeAll(resize(point));
        repaint();
    }

    public void remove(Set<Point> points) {
        markedPoints.removeAll(resize(points));
        repaint();
    }

    private Set<Point> resize(Point point) {
        if (point != null) {
            Set<Point> points = new HashSet<>();
            points.add(point);
            return resize(points);
        }
        return Collections.emptySet();
    }

    private Set<Point> resize(Set<Point> points) {
        if (points != null) {
            return (HashSet) taskManager.execute(
                    new PointGetCommand(taskManager, points));
        }
        return Collections.emptySet();
    }

    TaskManager getTaskManager() {
        return taskManager;
    }

    void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
        coordinateOverlayPanel.setTaskManager(taskManager);
    }

    ToolsManager getToolsManager() {
        return toolsManager;
    }
}

class CoordinateOverlayPanel extends JPanel {

    private TaskManager taskManager = null;

    private Point position;
    private Point coordinate;
    private JLabel posLabel = new JLabel();
    private double[] pointValue = null;
    private int posLabelOffset = 5;
    private Rectangle posLabelBounds = new Rectangle(0, 0, 100, 100);
    private Dimension size = null;

    CoordinateOverlayPanel() {
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
//        posLabel.setBackground(new Color(225, 255, 0, 100));
        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (taskManager.getImageConverter() != null) {

            g.setColor(new Color(255, 0, 0, 150));
            g.fillRect(position.x, 0, 1, getSize().height);
            g.fillRect(0, position.y, getSize().width, 1);

            posLabel.setBounds(setPosLabelBounds());

            if (coordinate != null) {
                posLabel.setText(String.format("<html>%d %d<br>%d %d", position.x, position.y, coordinate.x, coordinate.y));

                pointValue = (double[]) taskManager.execute(
                        new ConverterPointValueGetCommand(taskManager, position));
                if (pointValue.length > 1) {
                    posLabel.setText(String.format("<html>%d %d<br>%d %d<br>%.5f %.5f",
                            position.x, position.y, coordinate.x, coordinate.y, pointValue[0], pointValue[1]));
                }
            }
            add(posLabel);
        }
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
        coordinate = (Point) taskManager.execute(new ResizedCoordinateGetCommand(taskManager, position));
    }

    public Point getCoordinate() {
        return coordinate;
    }

    void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    private Rectangle setPosLabelBounds() {
        size = getPreferredSize();
        posLabelBounds.x = position.x > size.width - posLabelOffset - posLabelBounds.width ?
                position.x - posLabelOffset - posLabelBounds.width :
                position.x + posLabelOffset;
        posLabelBounds.y = position.y < posLabelOffset + posLabelBounds.height ?
                position.y + posLabelOffset :
                position.y - posLabelOffset - posLabelBounds.height;
        return posLabelBounds;
    }

}

class OverlayMouseAdapter extends MouseAdapter {

    private Point startPoint = null;
    private Point previousPoint = null;
    private Point currentPoint = null;

    private MarkerOverlayPanel overlay = null;

    public OverlayMouseAdapter(MarkerOverlayPanel markerOverlayPanel) {
        this.overlay = markerOverlayPanel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = new Point(e.getPoint());
    }

    public void MouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse was Clicked");
        previousPoint = e.isShiftDown() ? currentPoint : null;
        currentPoint = e.getPoint();

        switch (overlay.getToolsManager().getActiveToolName()) {
            case "point selector":
                overlay.add(
                        overlay.getToolsManager().
                                getActiveTool().mark(e.getPoint()));
                System.out.println(e.getPoint().toString());
                overlay.selectPoint(e.getPoint());
                break;
            case "marker":
                if (e.isShiftDown()) {
                    overlay.add(
                            overlay.getToolsManager().getActiveTool().
                                    markLine(previousPoint, currentPoint));
                } else {
                    overlay.add(e.getPoint());
                }
                break;
            case "mark eraser":
                if (e.isShiftDown()) {
                    overlay.remove(
                            overlay.getToolsManager().getActiveTool().
                                    markLine(previousPoint, currentPoint));
                } else {
                    overlay.remove(e.getPoint());
                }
                break;
            case "eraser":
                if (e.isShiftDown()) {
                    overlay.getTaskManager().execute(
                            new EraserCommand(
                                    overlay.getTaskManager(),
                                    overlay.getToolsManager().getActiveTool().
                                            markLine(previousPoint, currentPoint)));
                } else {
                    overlay.getTaskManager().execute(
                            new EraserCommand(
                                    overlay.getTaskManager(),
                                    overlay.getToolsManager().getScreenArea(e.getPoint())));
                }
                overlay.getDrawingPanel().repaint();
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (overlay.getToolsManager().getActiveToolName()) {
            case "point selector":
                overlay.add(
                        overlay.getToolsManager().
                                getActiveTool().mark(e.getPoint()));
                System.out.println(e.getPoint().toString());
                overlay.selectPoint(e.getPoint());
                break;
            case "marker":
                if (e.isShiftDown()) {
                    overlay.add(
                            overlay.getToolsManager().getActiveTool().
                                    markLine(previousPoint, currentPoint));
                } else {
                    overlay.add(e.getPoint());
                }
                break;
            case "mark eraser":
                if (e.isShiftDown()) {
                    overlay.remove(
                            overlay.getToolsManager().getActiveTool().
                                    markLine(previousPoint, currentPoint));
                } else {
                    overlay.remove(e.getPoint());
                }
                break;
            case "eraser":
                if (e.isShiftDown()) {
                    overlay.getTaskManager().execute(
                            new EraserCommand(
                                    overlay.getTaskManager(),
                                    overlay.getToolsManager().getActiveTool().
                                            markLine(previousPoint, currentPoint)));
                } else {
                    overlay.getTaskManager().execute(
                            new EraserCommand(
                                    overlay.getTaskManager(),
                                    overlay.getToolsManager().getScreenArea(e.getPoint())));
                }
                overlay.getDrawingPanel().repaint();
                break;
        }
        JViewport viewport = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, overlay);
        if (startPoint != null) {
            int dX = startPoint.x - e.getX();
            int dY = startPoint.y - e.getY();

            Rectangle view = viewport.getViewRect();
            view.x += dX;
            view.y += dY;

            overlay.getDrawingPanel().scrollRectToVisible(view);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        overlay.setPosition(e.getPoint());
    }

}