package util;

import javax.swing.*;
import java.awt.*;

public class CustomIconJButton extends JButton
{
    public CustomIconJButton(String filename)
    {
        setIconSettings(filename);

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBackground(GameColor.defaultBackGroundColor);

        setFont(GameFont.MAIN_MENU);
        setForeground(GameColor.MenuTextColor);
        setBorderPainted(false);
    }

    private void setIconSettings(String filename)
    {
        setBackground(new Color(0xFFFFFF));
        setPreferredSize(new Dimension(75,75));
        setIcon(new ImageIcon(filename));
    }
}