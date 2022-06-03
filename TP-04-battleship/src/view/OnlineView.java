package view;

import controller.IController;
import model.network.Client;
import model.network.Host;
import util.*;

import javax.swing.*;
import java.awt.*;

public class OnlineView
{
    private final IController controller;
    private final MainMenu mainMenu;
    private Host host;

    private final ImageIcon background = new ImageIcon("src\\resources\\OnlineMenu.jpg");

    private final BFrame frame = new BFrame("BattleShip - Online");

    private final JPanel buttonPanel = new JPanel();
    private final JPanel hostWaitingPanel = new JPanel();
    private final JPanel joinPanel = new JPanel();
    private final JPanel joinTextPanel = new JPanel();

    private final JLabel backgroundLabel = new JLabel(background);
    private final JLabel hostWaitingLabel = new JLabel("Waiting...", SwingConstants.CENTER);
    private final JLabel ipLabel = new JLabel("Host IP Address", SwingConstants.CENTER);
    private final JLabel wrongIPLabel = new JLabel("", SwingConstants.CENTER);

    private final JTextField ipTextField = new JTextField("127.0.0.1", 8);

    private final MenuJButton hostButton = new MenuJButton("Host");
    private final MenuJButton joinButton = new MenuJButton("Join");
    private final MenuJButton backButton = new MenuJButton("Back");
    private final MenuJButton joinBackButton = new MenuJButton("Back");
    private final MenuJButton cancelButton = new MenuJButton("Cancel");
    private final MenuJButton connectButton = new MenuJButton("Connect");

    public OnlineView(IController controller, MainMenu mainMenu)
    {
        this.controller = controller;
        this.mainMenu = mainMenu;
        initFrame();
        setLayouts();
        addElementsToFrame();
        elementsSettings();
        initListener();
        frame.getFrame().setVisible(true);
    }

    private void initFrame()
    {
        frame.setSize(new Dimension(1000, 580));
        frame.getFrame().setResizable(false);
    }

    private void setLayouts()
    {
        backgroundLabel.setLayout(new GridLayout(2,3));
        buttonPanel.setLayout(new GridLayout(0,1,0,10));
        hostWaitingPanel.setLayout(new GridLayout(0,1,0,10));
        joinPanel.setLayout(new GridLayout(0,1, 0, 10));
        joinTextPanel.setLayout(new GridLayout(0,1));
    }

    private void addElementsToFrame()
    {
        frame.add(backgroundLabel, BorderLayout.CENTER);

        fillWithEmptyPanel();
        backgroundLabel.add(buttonPanel);

        buttonPanel.add(hostButton);
        buttonPanel.add(joinButton);
        buttonPanel.add(backButton);

        joinPanel.add(ipLabel);
        joinPanel.add(ipTextField);
        joinPanel.add(connectButton);
        joinPanel.add(wrongIPLabel);
        joinPanel.add(joinBackButton);

        hostWaitingPanel.add(new EmptyJPanel());
        hostWaitingPanel.add(hostWaitingLabel);
        hostWaitingPanel.add(new EmptyJPanel());
        hostWaitingPanel.add(cancelButton);
    }

    private void fillWithEmptyPanel()
    {
        for (int i = 0; i < 5 ; i++)
        {
            backgroundLabel.add(new EmptyJPanel());
        }
    }

    private void elementsSettings()
    {
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,70,10,10));

        joinPanel.setOpaque(false);
        joinPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        joinTextPanel.setOpaque(false);

        hostWaitingPanel.setOpaque(false);
        hostWaitingPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        ipLabel.setFont(GameFont.MENU_LABEL);
        ipLabel.setForeground(GameColor.MenuTextColor);

        hostWaitingLabel.setFont(GameFont.MAIN_MENU);
        hostWaitingLabel.setForeground(GameColor.MenuTextColor);

        wrongIPLabel.setFont(GameFont.MENU_LABEL);
        wrongIPLabel.setForeground(Color.red);

        ipTextField.setBorder(null);
        ipTextField.setOpaque(false);
        ipTextField.setFont(GameFont.MENU_LABEL);
        ipTextField.setHorizontalAlignment(JTextField.CENTER);
    }

    private void initListener()
    {
        hostButton.addActionListener(e -> host());
        joinButton.addActionListener(e -> join());
        backButton.addActionListener(e -> back());
        joinBackButton.addActionListener(e -> joinBack());
        cancelButton.addActionListener(e-> cancel());
        connectButton.addActionListener(e -> connect());
    }

    private void host()
    {
        backgroundLabel.remove(buttonPanel);
        backgroundLabel.add(hostWaitingPanel, BorderLayout.CENTER);

        frame.getFrame().setVisible(true);
        frame.getFrame().repaint();

        host = new Host(controller, this);
    }

    private void join()
    {
        backgroundLabel.remove(buttonPanel);
        backgroundLabel.add(joinPanel, BorderLayout.CENTER);
        frame.getFrame().setVisible(true);
        frame.getFrame().repaint();
    }

    private void back()
    {
        frame.getFrame().dispose();
        mainMenu.visible();
    }

    private void joinBack()
    {
        backgroundLabel.remove(joinPanel);
        backgroundLabel.add(buttonPanel, BorderLayout.CENTER);
        frame.getFrame().setVisible(true);
        frame.getFrame().repaint();
    }

    private void cancel()
    {
        host.cancel();
        backgroundLabel.remove(hostWaitingPanel);
        backgroundLabel.add(buttonPanel, BorderLayout.CENTER);
        frame.getFrame().setVisible(true);
        frame.getFrame().repaint();
    }

    private void connect()
    {
        new Client(ipTextField.getText(), controller, this);
    }

    public void dispose()
    {
        frame.getFrame().dispose();
    }

    public void setTextWrongIPLabel(String text)
    {
        wrongIPLabel.setText(text);
    }
}