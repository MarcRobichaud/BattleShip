package util;

import javax.swing.*;
import java.awt.*;

public class BLabel extends JLabel
{
    public BLabel()
    {
        setOpaque(false);
        setForeground(Color.white);
        setFont(GameFont.TITLE_BAR);
    }
}