package bogdanov.plot.window.tools;

import java.awt.*;
import java.util.Collections;
import java.util.Set;

public class ToolsManager {

    private Marker pointSelector = new Marker(10, new Color(255, 0, 0, 100), "cross");
    private Marker marker = new Marker(30, new Color(0, 255, 0, 100));
    private Marker markerEraser = new Marker(10, new Color(255, 0, 0, 100));
    private Marker eraser = new Marker(15, new Color(255, 255, 255, 200));
    private Marker pencil = new Marker (2, new Color(0, 255, 0, 100));

    private Marker[] tools = new Marker[]{pointSelector, marker, markerEraser, eraser, pencil};
    private String[] toolNames = new String[]{"point selector", "marker", "mark eraser", "eraser", "pencil"};
    private int activeTool = -1;

    public ToolsManager() {
    }

    public Marker getMarker() {
        return marker;
    }

    public Marker getMarkerEraser() {
        return markerEraser;
    }

    public Marker getEraser() {
        return eraser;
    }

    public Marker getPointSelector() {
        return pointSelector;
    }

    public Marker getPencil() {
        return pencil;
    }

    public void chooseTool(String tool) {
        String toolName = tool.toLowerCase();
        boolean isFound = false;
        int i;
        for (i = toolNames.length - 1; i >= 0; i--) {
            if (isFound = toolNames[i].equals(toolName)) {
                break;
            }
        }
        setActiveTool(i);
    }

    public void off() {
        setActiveTool(-1);
    }

    private void setActiveTool(int activeTool) {
        this.activeTool = activeTool;
        for (int i = 0; i < tools.length; i++) {
            tools[i].off();
        }
        if (activeTool >= 0) {
            tools[activeTool].on();
        }
        if (activeTool > -1) {
            System.out.println("Active Tool >>> " + toolNames[activeTool]);
        } else {
            System.out.println("Active Tool >>> NO ACTIVE TOOL");
        }
    }

    public Marker getActiveTool() {
        if (activeTool >= 0) {
            return tools[activeTool];
        }
        return null;
    }

    public String getActiveToolName() {
        return activeTool > -1 ? toolNames[activeTool] : "";
    }

    public Set<Point> getScreenArea(Point point) {
        return activeTool > -1 ? tools[activeTool].mark(point) : Collections.emptySet();
    }

    public Color getActiveColor() {
        return activeTool > -1 ? tools[activeTool].getColor() : new Color(255, 255, 255, 0);
    }

    public boolean toolIsActive() {
        return activeTool > -1 ? true : false;
    }
}
