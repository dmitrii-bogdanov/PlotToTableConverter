package bogdanov.plot.window;

import bogdanov.plot.tools.taskmanager.TaskManager;
import bogdanov.plot.window.tools.PlotConverterKeyAdapter;
import bogdanov.plot.window.tools.Scroller;

import javax.swing.*;
import java.awt.*;

public class PlotConverterFrame extends JFrame {

    private static Rectangle screenBounds;

    static {
        GraphicsDevice graphicsDevice = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice();
        GraphicsConfiguration graphicsConfiguration = graphicsDevice.getConfigurations()[0];
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(graphicsConfiguration);

        screenBounds = graphicsConfiguration.getBounds();
        screenBounds.x += screenInsets.left;
        screenBounds.y += screenInsets.top;
        screenBounds.width -= screenInsets.left + screenInsets.right;
        screenBounds.height -= screenInsets.top + screenInsets.bottom;
    }

    private TaskManager taskManager = new TaskManager();
    private MenuBar menuBar = new MenuBar(taskManager, "plot converter");
    private DrawingPanel drawingPanel = new DrawingPanel(taskManager);
    private PlotConverterControlTabbedPane controlPane = new PlotConverterControlTabbedPane(drawingPanel.getOverlay());

    private PlotConverterKeyAdapter keyAdapter = new PlotConverterKeyAdapter(taskManager);

    public PlotConverterFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setBounds(screenBounds.x, screenBounds.y, screenBounds.width, screenBounds.height);
        setSize(screenBounds.width, screenBounds.height);
        setPreferredSize(getSize());
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        keyAdapter.setFrame(this);
        addKeyListener(keyAdapter);

        JPanel mainPanel = new JPanel();
        {
            mainPanel.setPreferredSize(new Dimension(screenBounds.width, screenBounds.height - menuBar.getHeight()));
            mainPanel.setLayout(new BorderLayout(0,0));
            JPanel northPanel = new JPanel();
            northPanel.setBackground(Color.WHITE);
            northPanel.setPreferredSize(new Dimension(screenBounds.width, 20));
            JPanel southPanel = new JPanel();
            southPanel.setBackground(Color.WHITE);
            southPanel.setPreferredSize(new Dimension(screenBounds.width, 20));
            JPanel westPanel = new JPanel();
            westPanel.setBackground(Color.WHITE);
            westPanel.setPreferredSize(new Dimension(20, screenBounds.height));
            JPanel eastPanel = new JPanel();
            eastPanel.setBackground(Color.WHITE);
            eastPanel.setPreferredSize(new Dimension(20, screenBounds.height));

            mainPanel.add(northPanel, BorderLayout.NORTH);
            mainPanel.add(westPanel, BorderLayout.WEST);
            mainPanel.add(southPanel, BorderLayout.SOUTH);
            mainPanel.add(eastPanel, BorderLayout.EAST);

            drawingPanel.setSize(new Dimension(screenBounds.width - 40 - 300,
                    screenBounds.height - 40 - menuBar.getHeight()));
            drawingPanel.setPreferredSize(drawingPanel.getSize());

            JPanel centerPanel = new JPanel();
            centerPanel.setSize(screenBounds.width - 40, screenBounds.height - 40 - menuBar.getHeight());
            centerPanel.setPreferredSize(centerPanel.getSize());
            centerPanel.setLayout(new BorderLayout(0,0));

            controlPane.setBackground(Color.BLUE);
            controlPane.setSize(screenBounds.width - 60 - drawingPanel.getWidth(), screenBounds.height - 40 - menuBar.getHeight());
            controlPane.setPreferredSize(controlPane.getSize());

            JScrollPane drawingScrollablePane = new JScrollPane(drawingPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED){
                @Override
                public void repaint(){
                    super.repaint();
                    drawingPanel.repaint();
                }
            };

            drawingPanel.overlay.requestFocus();
            drawingPanel.overlay.addKeyListener(keyAdapter);

            centerPanel.add(controlPane, BorderLayout.EAST);
            centerPanel.add(drawingScrollablePane, BorderLayout.CENTER);
            mainPanel.add(centerPanel, BorderLayout.CENTER);

            mainPanel.setVisible(true);

            keyAdapter.setScroller(new Scroller(drawingScrollablePane));
        };

        menuBar.setFrame(this);
        add(menuBar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);



//        menuBar.fileMenu.openMenuItem.doClick();

//        pack();
//        revalidate();
        setVisible(true);
    }

    @Override
    public void repaint() {
        super.repaint();
        System.out.println("Frame was Repainted");
//        drawingPanel.repaint();
//        drawingPanel.paintComponent(getGraphics());
    }

    @Override
    public void requestFocus() {
        drawingPanel.overlay.requestFocus();
    }
}