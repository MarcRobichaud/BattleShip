package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class BFrame
{
    private final JPanel titleBarPanel = new JPanel();
    private final JPanel rootPanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();
    private final BButton iconButton = new BButton();
    private final BLabel titleLabel = new BLabel();
    private final BButton closeButton = new BButton();
    private final BButton minimizeButton = new BButton();
    private final JFrame frame;

    ComponentAdapter componentAdapter;

    private int x, y;
    private int roundedCornerSize = 0;

    public BFrame(String title)
    {
        frame = new JFrame();
        titleLabel.setText(title);

        createComponentAdapter();
        setDefaultParameters();
        setLayouts();
        setComponentSettings();
        handleListeners();
        addElements();
    }

    private void createComponentAdapter()
    {
        componentAdapter = new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                changeCorner();
            }
        };
    }
    private void changeCorner()
    {
        frame.setShape(new RoundRectangle2D.Double(0, 0, frame.getWidth(), frame.getHeight(), roundedCornerSize, roundedCornerSize));
    }

    private void setComponentSettings()
    {
        resizeTitleBar(32);
        setRoundEdge(10);

        closeButton.setIcon(new ImageIcon("src/resources/close.png"));
        closeButton.setMargin(new Insets(0, 0, 0, 0));

        minimizeButton.setIcon(new ImageIcon("src/resources/minimize.png"));
        minimizeButton.setMargin(new Insets(0, 0, 0, 0));

        iconButton.setIcon(new ImageIcon("src/resources/icon.png"));
        iconButton.setMargin(new Insets(0, 0, 0, 0));
        iconButton.setEnabled(false);

        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
    }

    public void resizeTitleBar(int height)
    {
        titleBarPanel.setSize(titleBarPanel.getWidth(), height);
        titleBarPanel.setPreferredSize(new Dimension(titleBarPanel.getWidth(), height));

        Font closeButtonFont = closeButton.getFont();
        closeButton.setFont(new Font(closeButtonFont.getName(), closeButtonFont.getStyle(), height / 2));

        Font minimizeButtonFont = minimizeButton.getFont();
        minimizeButton.setFont(new Font(minimizeButtonFont.getName(), minimizeButtonFont.getStyle(), height / 2));

        Font titleLabelFont = titleLabel.getFont();
        titleLabel.setFont(new Font(titleLabelFont.getName(), titleLabelFont.getStyle(), height / 2));

        closeButton.setSize(height, height);
        minimizeButton.setSize(height, height);

        iconButton.setSize(height, buttonPanel.getHeight());
        iconButton.setPreferredSize(new Dimension(height, iconButton.getHeight()));

        buttonPanel.setSize(height * 2, buttonPanel.getHeight());
        buttonPanel.setPreferredSize(new Dimension(height * 2, buttonPanel.getHeight()));
    }

    public void setRoundEdge(int size)
    {
        roundedCornerSize = size;
        changeCorner();
        frame.repaint();
    }

    private void setLayouts()
    {
        titleBarPanel.setLayout(new BorderLayout());
        rootPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new GridLayout(1, 2, 0, 0));
    }

    private void setDefaultParameters()
    {
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        titleBarPanel.setBackground(closeButton.getBackground());
    }

    private void addElements()
    {
        buttonPanel.add(minimizeButton);
        buttonPanel.add(closeButton);

        titleBarPanel.add(buttonPanel, BorderLayout.EAST);
        titleBarPanel.add(iconButton, BorderLayout.WEST);
        titleBarPanel.add(titleLabel, BorderLayout.CENTER);

        // important to call super.add, otherwise, they would
        // be added to the rootPanel instead of the ContentPane
        frame.add(titleBarPanel, BorderLayout.NORTH);
        frame.add(rootPanel, BorderLayout.CENTER);
    }

    private void handleListeners()
    {
        minimizeButton.addActionListener(e -> frame.setState(JFrame.ICONIFIED));
        closeButton.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
        frame.addComponentListener(componentAdapter);
        titleBarAddListener();
    }

    private void titleBarAddListener()
    {
        MouseAdapter ma = getMouseAdapter();

        titleBarPanel.addMouseMotionListener(ma);
        titleBarPanel.addMouseListener(ma);
    }

    private MouseAdapter getMouseAdapter()
    {
        return new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                x = e.getX();
                y = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e)
            {
                int xx = e.getXOnScreen();
                int yy = e.getYOnScreen();
                frame.setLocation(xx - x, yy - y);
            }
        };
    }

    //region Getters
    public JButton getCloseButton()
    {
        return closeButton;
    }

    public JPanel getTitleBar()
    {
        return titleBarPanel;
    }
    //endregion

    //region Overriden methods
    public Component add(String name, Component comp)
    {
        return rootPanel.add(name, comp);
    }

    public Component add(Component comp, int index)
    {
        return rootPanel.add(comp, index);
    }

    public Component add(Component comp)
    {
        return rootPanel.add(comp);
    }

    public void add(Component comp, Object constraints)
    {
        rootPanel.add(comp, constraints);
    }

    public void add(Component comp, Object constraints, int index)
    {
        rootPanel.add(comp, constraints, index);
    }

    public void setLayout(LayoutManager manager)
    {
        rootPanel.setLayout(manager);
    }

    public Container getContentPane()
    {
        return rootPanel;
    }

    public void pack()
    {
        frame.pack();
    }

    public void setBackground(Color bg)
    {
        rootPanel.setBackground(bg);
    }

    public JFrame getFrame()
    {
        return frame;
    }

    public void setSize(Dimension dimension)
    {
        frame.setSize(dimension);
    }
    //endregion
}