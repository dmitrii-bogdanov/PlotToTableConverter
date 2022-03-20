package bogdanov.plot.window.tools;

import java.awt.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Marker {

    private int size = 10;
    private String form = "square";
    private Color color = new Color(0, 255, 0, 100);
    private boolean state = false;

    public Marker() {
    }

    public Marker(int size) {
        setSize(size);
    }

    public Marker(String form) {
        setForm(form);
    }

    public Marker(int size, String form) {
        setSize(size);
        setForm(form);
    }

    public Marker(Color color) {
        this.color = color;
    }

    public Marker(int size, Color color) {
        setSize(size);
        this.color = color;
    }

    public Marker(Color color, String form) {
        this.color = color;
        setForm(form);
    }

    public Marker(int size, Color color, String form) {
        setSize(size);
        this.color = color;
        setForm(form);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size > 0 ? size : 10;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        switch (form.toLowerCase()) {
            case "cross":
                this.form = "cross";
                break;
            default:
                this.form = "square";
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Set<Point> mark(Point point) {
        switch (form) {
            case "cross":
                return markCross(point);
            default:
                return markSquare(point);
        }
    }

    public Set<Point> markLine(Point p1, Point p2) {
        if (p1 != null) {
            Set<Point> points = new HashSet<>();
            Point p = new Point();
            float k = (float) (p2.y - p1.y) / (p2.x - p1.x);
            float b = (float) (p1.y * p2.x - p1.x * p2.y) / (p2.x - p1.x);
            points.addAll(mark(p1));
            points.addAll(mark(p2));
            if (p1.x <= p2.x) {
                for (int i = p1.x + 1; i < p2.x; i++) {
                    p.x = i;
                    p.y = (int) (k * i + b);
//                    points.addAll(markVerticalLine(p));
                    points.addAll(mark(p));
                }
            } else {
                for (int i = p2.x + 1; i < p1.x; i++) {
                    p.x = i;
                    p.y = (int) (k * i + b);
                    points.addAll(mark(p));
//                    points.addAll(markVerticalLine(p));
                }
            }
            return points;
        } else {
            return mark(p2);
        }
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void on() {
        setState(true);
    }

    public void off() {
        setState(false);
    }

    private Set<Point> markSquare(Point point) {
        if (point != null) {
            Set<Point> tmp = new HashSet<>();
            int x;
            int y;
            for (int i = -size; i <= size; i++) {
                for (int j = -size; j <= size; j++) {
                    x = point.x + i;
                    y = point.y + j;
                    if (x >= 0 && y >= 0) {
                        tmp.add(new Point(x, y));
                    }
                }
            }
            return tmp;
        }
        return Collections.emptySet();
    }

    private Set<Point> markCross(Point point) {
        if (point != null) {
            Set<Point> tmp = new HashSet<>();
            int x;
            int y;
            for (int i = -size; i <= size; i++) {
                for (int j = -size >> 1; j <= size >> 1; j++) {
                    x = point.x + i;
                    y = point.y + j;
                    if (x >= 0 && y >= 0) {
                        tmp.add(new Point(x, y));
                    }
                    x = point.x + j;
                    y = point.y + i;
                    if (x >= 0 && y >= 0) {
                        tmp.add(new Point(x, y));
                    }
                }
            }
            return tmp;
        }
        return Collections.emptySet();
    }

    private Set<Point> markVerticalLine(Point point) {
        if (point != null) {
            Set<Point> tmp = new HashSet<>();
            int y;
            for (int i = -size; i <= size; i++) {
                    y = point.y + i;
                    if (y >= 0) {
                        tmp.add(new Point(point.x, y));
                    }
                }
            return tmp;
        }
        return Collections.emptySet();
    }
}
