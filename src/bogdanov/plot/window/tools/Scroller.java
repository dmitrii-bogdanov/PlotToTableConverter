package bogdanov.plot.window.tools;


import javax.swing.*;
import java.awt.*;

public class Scroller {
    private JScrollPane scrollablePane = null;

    public Scroller(JScrollPane scrollablePane) {
        this.scrollablePane = scrollablePane;
    }

    public JScrollPane getScrollablePane() {
        return scrollablePane;
    }

    public void setScrollablePane(JScrollPane scrollablePane) {
        this.scrollablePane = scrollablePane;
    }


    public void scrollUp() {
        int position = scrollablePane.getVerticalScrollBar().getValue();
        int min = scrollablePane.getVerticalScrollBar().getMinimum();
        int max = scrollablePane.getVerticalScrollBar().getMaximum();
        int step = max * scrollablePane.getHeight() / scrollablePane.getPreferredSize().height * 9 / 24;
        scrollablePane.getVerticalScrollBar().setValue(position > step ? position - step : min);
    }

    public void scrollLeft() {
        int position = scrollablePane.getHorizontalScrollBar().getValue();
        System.out.println("Left pos = " + position);
        int max = scrollablePane.getHorizontalScrollBar().getMaximum();
        System.out.println("Left max = " + max);
        int step = max * scrollablePane.getWidth() / scrollablePane.getPreferredSize().width * 9 / 24;
        scrollablePane.getHorizontalScrollBar().setValue(position - step);
    }

    public void scrollRight() {
        int position = scrollablePane.getHorizontalScrollBar().getValue();
        int max = scrollablePane.getHorizontalScrollBar().getMaximum();
        int step = max * scrollablePane.getWidth() / scrollablePane.getPreferredSize().width * 9 / 24;
        scrollablePane.getHorizontalScrollBar().setValue(position + step);
    }

    public void scrollDown() {
        int position = scrollablePane.getVerticalScrollBar().getValue();
        int min = scrollablePane.getVerticalScrollBar().getMinimum();
        int max = scrollablePane.getVerticalScrollBar().getMaximum();
        int step = max * scrollablePane.getHeight() / scrollablePane.getPreferredSize().height * 9 / 24;
        scrollablePane.getVerticalScrollBar().setValue(position + step);
    }
}
