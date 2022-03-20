package bogdanov.plot.window.tools;

import bogdanov.plot.tools.taskmanager.TaskManager;
import bogdanov.plot.tools.taskmanager.command.ZoomInCommand;
import bogdanov.plot.tools.taskmanager.command.ZoomOutCommand;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class PlotConverterKeyAdapter extends KeyAdapter {

    private TaskManager taskManager = null;
    private Scroller scroller = null;
    boolean[] pressedKeys = new boolean[256];
    private JFrame frame = null;

    public PlotConverterKeyAdapter (TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public PlotConverterKeyAdapter (TaskManager taskManager,
                                    Scroller scroller) {
        this.taskManager = taskManager;
        setScroller(scroller);
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public Scroller getScroller() {
        return scroller;
    }

    public void setScroller(Scroller scroller) {
        this.scroller = scroller;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys[e.getKeyCode()] = true;
        if (e.getKeyCode() == 39 || e.getKeyCode() == 37) {
            e.consume();
        }

        if (keysArePressed(pressedKeys, 17, 79)) {
            pressedKeys[17] = false;
            pressedKeys[79] = false;
        } else if (keysArePressed(pressedKeys, 17, 83)) {
            pressedKeys[17] = false;
            pressedKeys[83] = false;
        } else if (keysArePressed(pressedKeys, 40)) {
            if (scroller != null) {
                scroller.scrollDown();
            }
        } else if (keysArePressed(pressedKeys, 39)) {
            if (scroller != null) {
                scroller.scrollRight();
            }
        } else if (keysArePressed(pressedKeys, 38)) {
            if (scroller != null) {
                scroller.scrollUp();
            }
        } else if (keysArePressed(pressedKeys, 37)) {
            if (scroller != null) {
                scroller.scrollLeft();
            }
        } else if (keysArePressed(pressedKeys, 17, 61)) {
            System.out.println("Pressed Zoom In");
            taskManager.execute(new ZoomInCommand(taskManager));
            if (scroller!= null) {
                scroller.getScrollablePane().repaint();
            }

        } else if (keysArePressed(pressedKeys, 17, 45)) {
            System.out.println("Pressed Zoom Out");
            taskManager.execute(new ZoomOutCommand(taskManager));
            if (scroller != null) {
                scroller.getScrollablePane().repaint();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys[e.getKeyCode()] = false;
        if (e.getKeyCode() == 39 || e.getKeyCode() == 37) {
            e.consume();
        }
        System.out.println(e.getKeyCode());
    }

    private boolean keysArePressed(boolean[] pressedKeys, int... keyCode) {
        boolean n = true;
        boolean u = false;
        int i = 0, j = -2;
        System.out.println(keyCode.length);
        for (int key : keyCode) {
            System.out.print(key);
        }
        System.out.println();
        if (keyCode.length > 0) {
            for (i = 0; i < keyCode.length; i++) {
                n &= pressedKeys[keyCode[i]];
                if (!n) {
                    return false;
                }
//                System.out.println(String.format("n = %b | i = %d | pK = %b", n, keyCode[i], pressedKeys[keyCode[i]]));
            }

            for (i = 0; i < keyCode.length; i++) {
                for (j += 2; j < keyCode[i]; j++) {
                    u |= pressedKeys[j];
//                    System.out.println(String.format("u = %b | j = %d | pK = %b", u, j, pressedKeys[j]));
                    if (u) {
                        return false;
                    }
                }
            }
            for (j = keyCode[i - 1] + 1; j < pressedKeys.length; j++) {
                u |= pressedKeys[j];
//                System.out.println(String.format("u = %b | j = %d | pK = %b", u, j, pressedKeys[j]));
                if (u) {
                    return false;
                }
            }
            System.out.println(true);
            return true;
        }
        System.out.println(false);
        return false;
    }
}
