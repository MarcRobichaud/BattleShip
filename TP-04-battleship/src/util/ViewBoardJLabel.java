package util;

import javax.swing.*;
import java.awt.*;


public class ViewBoardJLabel extends JLabel
{
    public ViewBoardJLabel()
    {
        setFont(GameFont.MAIN);
    }

    public ViewBoardJLabel(String text)
    {
        super(text, SwingConstants.CENTER);
        setBackground(new Color(0,0,0,0));
        setForeground(GameColor.MenuTextColor);
        setOpaque(false);
        setFont(GameFont.MAIN);
    }
}