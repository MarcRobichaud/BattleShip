package util;

import javax.swing.*;
import java.awt.*;

public class MenuJButton extends JButton
{
    public MenuJButton(String text)
    {
        super(text);

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBackground(GameColor.defaultBackGroundColor);

        setFont(GameFont.MAIN_MENU);
        setForeground(GameColor.MenuTextColor);
        setBorderPainted(false);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        if(getModel().isPressed())
        {
            g.setColor(GameColor.pressedBackGroundColor);
        }
        else if(getModel().isRollover())
        {
            g.setColor(GameColor.hoverBackgroundColor);
        }
        else
        {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}