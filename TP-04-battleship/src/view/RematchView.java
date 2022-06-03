package view;

import model.network.HostClient;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RematchView
{
    private HostClient hostClient;

    BFrame frame = new BFrame("BattleShip - Rematch");

    EmptyJPanel northPanel = new EmptyJPanel();
    EmptyJPanel centerPanel = new EmptyJPanel();

    ViewBoardJLabel infoLabel = new ViewBoardJLabel("Player 2 wants to rematch");

    RematchJButton acceptButton = new RematchJButton("ACCEPT");
    RematchJButton refuseButton = new RematchJButton("REFUSE");

    public RematchView(HostClient hostClient)
    {
        this.hostClient = hostClient;

        initFrame();
        setLayouts();
        addElementsToFrame();
        northPanelBackground();
        initListener();
        frame.getFrame().setVisible(true);
    }

    private void initFrame()
    {
        frame.setSize(new Dimension(275, 150));
        frame.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getFrame().addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                refuse();
            }
        });
        frame.setBackground(GameColor.viewBoardBackGround);
    }

    private void setLayouts()
    {
        centerPanel.setLayout(new GridLayout(1,2));
    }

    private void addElementsToFrame()
    {
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        northPanel.add(infoLabel);

        centerPanel.add(acceptButton);
        centerPanel.add(refuseButton);
    }

    private void northPanelBackground()
    {
        northPanel.setOpaque(true);
        northPanel.setBackground(GameColor.viewBoardBorder);
    }

    private void initListener()
    {
        acceptButton.addActionListener(e -> accept());
        refuseButton.addActionListener(e -> refuse());
    }

    private void accept()
    {
        Debug.log("I accept");
        hostClient.acceptRematch();
        frame.getFrame().dispose();
    }

    private void refuse()
    {
        Debug.log("I refuse");
        hostClient.refuseRematch();
        frame.getFrame().dispose();
    }
}