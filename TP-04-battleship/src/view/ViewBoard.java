package view;

import controller.IController;
import util.*;

import javax.swing.*;
import java.awt.*;

public class ViewBoard implements IViewBoard
{
    IController controller;

    JButton[][] defenseBoard = new JButton[10][10];
    JButton[][] attackBoard = new JButton[10][10];

    BFrame frame = new BFrame("BattleShip - Board");

    JPanel centerPanel = new JPanel();
    JPanel mainPanel = new JPanel();
    JPanel leftPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JPanel defensePanel = new JPanel();
    JPanel attackPanel = new JPanel();
    JPanel leftNorthPanel = new JPanel();
    JPanel rightNorthPanel = new JPanel();
    JPanel southPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel eastPanel = new JPanel();
    JPanel restartPanel = new JPanel();
    JPanel optionPanel = new JPanel();
    JPanel quitPanel = new JPanel();

    CustomIconJButton restartButton = new CustomIconJButton("src/resources/restart.png");
    CustomIconJButton optionButton = new CustomIconJButton("src/resources/setting.png");
    CustomIconJButton quitButton = new CustomIconJButton("src/resources/exit.png");

    ViewBoardJLabel defenseLabel = new ViewBoardJLabel("YOUR BOARD");
    ViewBoardJLabel attackLabel = new ViewBoardJLabel("ATTACK BOARD");
    ViewBoardJLabel consoleOutputLabel = new ViewBoardJLabel("Welcome and good luck");

    public ViewBoard()
    {
        initFrame();
        setLayouts();
        addElementsToFrame();
        initBoards();
        labelFont();
        panelSettings();
        sidePanelButtonsSettings();
        initSidePanelButtonsListener();
        frame.getFrame().setVisible(true);
    }
    
    @Override
    public void setController(IController controller)
    {
        this.controller = controller;
    }

    private JButton getButtonAt(Position position, JButton[][] board)
    {
        return board[position.getY()][position.getX()];
    }

    @Override
    public void changeTileOfAttackBoard(Position position, int color)
    {
        getButtonAt(position, attackBoard).setBackground(new Color(color));
    }

    @Override
    public void changeTileOfDefenseBoard(Position position, int color)
    {
        getButtonAt(position, defenseBoard).setBackground(new Color(color));
    }

    @Override
    public void disableButtonAt(Position position)
    {
        attackBoard[position.getY()][position.getX()].setEnabled(false);
    }

    @Override
    public void changeConsoleOutput(String output)
    {
        consoleOutputLabel.setText(output);
    }

    //region constructor methods
    private void initFrame()
    {
        frame.setSize(new Dimension(1100, 600));
    }

    private void setLayouts()
    {
        centerPanel.setLayout(new BorderLayout());
        mainPanel.setLayout(new GridLayout(1, 2));

        southPanel.setLayout(new GridLayout(0,1));

        leftPanel.setLayout(new BorderLayout());
        defensePanel.setLayout(new GridLayout(11, 11));
        leftNorthPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        rightPanel.setLayout(new BorderLayout());
        attackPanel.setLayout(new GridLayout(11, 11));
        rightNorthPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        buttonPanel.setLayout(new GridLayout(0,1));
        restartPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        quitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    }

    private void addElementsToFrame()
    {
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(eastPanel, BorderLayout.EAST);

        centerPanel.add(mainPanel, BorderLayout.CENTER);
        centerPanel.add(southPanel, BorderLayout.SOUTH);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        leftPanel.add(leftNorthPanel, BorderLayout.NORTH);
        leftPanel.add(defensePanel, BorderLayout.CENTER);
        leftPanel.add(new EmptyJPanel(), BorderLayout.EAST);

        leftNorthPanel.add(defenseLabel);

        rightPanel.add(rightNorthPanel, BorderLayout.NORTH);
        rightPanel.add(attackPanel, BorderLayout.CENTER);
        rightPanel.add(new EmptyJPanel(), BorderLayout.WEST);

        rightNorthPanel.add(attackLabel);

        southPanel.add(consoleOutputLabel);

        buttonPanel.add(restartPanel);
        buttonPanel.add(new EmptyJPanel());
        buttonPanel.add(new EmptyJPanel());
        buttonPanel.add(optionPanel);
        buttonPanel.add(new EmptyJPanel());
        buttonPanel.add(new EmptyJPanel());
        buttonPanel.add(quitPanel);

        restartPanel.add(restartButton);
        optionPanel.add(optionButton);
        quitPanel.add(quitButton);
    }

    private void panelSettings()
    {
        panelSize();
        panelBackground();
        panelOpacity();
        buttonPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        consoleOutputLabel.setFont(GameFont.CONSOLE_OUTPUT);
    }

    private void panelSize()
    {
        eastPanel.setPreferredSize(new Dimension(15, eastPanel.getPreferredSize().height));
        leftNorthPanel.setPreferredSize(new Dimension(southPanel.getPreferredSize().width, 60));
        rightNorthPanel.setPreferredSize(new Dimension(southPanel.getPreferredSize().width, 60));
        southPanel.setPreferredSize(new Dimension(southPanel.getPreferredSize().width, 60));
        buttonPanel.setPreferredSize(new Dimension(100, buttonPanel.getPreferredSize().height));
    }

    private void panelBackground()
    {
        centerPanel.setBackground(GameColor.viewBoardBackGround);
        eastPanel.setBackground(GameColor.viewBoardBackGround);
        southPanel.setBackground(GameColor.viewBoardBackGround);
        buttonPanel.setBackground(GameColor.viewBoardBorder);
    }

    private void panelOpacity()
    {
        mainPanel.setOpaque(false);
        leftPanel.setOpaque(false);
        leftNorthPanel.setOpaque(false);
        rightPanel.setOpaque(false);
        rightNorthPanel.setOpaque(false);
        attackPanel.setOpaque(false);
        defensePanel.setOpaque(false);
        restartPanel.setOpaque(false);
        optionPanel.setOpaque(false);
        quitPanel.setOpaque(false);
    }

    private void labelFont()
    {
        defenseLabel.setFont(GameFont.VIEW_BOARD_TITLE);
        attackLabel.setFont(GameFont.VIEW_BOARD_TITLE);
    }

    private void initBoards()
    {
        initBoard(defenseBoard, defensePanel, false);
        initBoard(attackBoard, attackPanel, true);
    }

    private void initBoard(JButton[][] board, JPanel panel, boolean enable)
    {
        // for X position label
        panel.add( new ViewBoardJLabel());
        for (int i = 1; i <= 10; i++)
        {
            ViewBoardJLabel label = new ViewBoardJLabel(Integer.toString(i));
            panel.add(label);
        }

        //place all buttons in the panel and add them to the array
        for (int y = 0; y < 10; y++)
        {
            // convert y to A-Z format for Y position label
            panel.add(new ViewBoardJLabel(Character.toString((char) ('A' + y))));
            for (int x = 0; x < 10; x++)
            {
                var button = new ViewBoardJButton(enable);
                if (enable)
                    initBoardButtonListener(button, new Position(x, y));
                board[y][x] = button;
                panel.add(button);
            }
        }
    }

    private void initBoardButtonListener(JButton button, Position position)
    {
        button.addActionListener(e -> controller.shoot(position));
    }

    private void sidePanelButtonsSettings()
    {
        restartButton.setBackground(GameColor.viewBoardBorder);
        optionButton.setBackground(GameColor.viewBoardBorder);
        quitButton.setBackground(GameColor.viewBoardBorder);
    }

    private void initSidePanelButtonsListener()
    {
        restartButton.addActionListener(e -> restart());
        optionButton.addActionListener(e -> option());
        quitButton.addActionListener(e -> quit());
    }

    private void quit()
    {
        controller.quit();
        frame.getFrame().dispose();
    }

    private void option()
    {
        controller.option();
    }

    private void restart()
    {
        controller.restart();
    }
    //endregion

    public void disposeFrame()
    {
        frame.getFrame().dispose();
    }
}