package util;

import javax.swing.*;
import java.awt.*;


public class BButton extends JButton
{
    public static final Font defaultFont = new Font("Segoe UI", Font.BOLD, 15);

    private Color hoverBackgroundColor = new Color(152, 152, 152);
    private Color pressedBackgroundColor = new Color(193, 193, 193);

    public BButton(String text)
    {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        setBackground(Color.darkGray);
        setForeground(Color.white);

        setFont(defaultFont);
    }

    public BButton()
    {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        setBackground(Color.darkGray);
        setForeground(Color.white);
    }

    public void setHoverBackground(Color color)
    {
        hoverBackgroundColor = color;
        repaint();
    }
    public void setPressedBackground(Color color)
    {
        pressedBackgroundColor = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        if (getModel().isPressed())
        {
            g.setColor(pressedBackgroundColor);
        }
        else if (getModel().isRollover())
        {
            g.setColor(hoverBackgroundColor);
        }
        else
        {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}