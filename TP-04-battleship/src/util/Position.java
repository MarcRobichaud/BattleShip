package util;

import exception.PositionFormatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Position
{
    private int x;
    private int y;

    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    private String yToStringLetterFormat()
    {
       return Character.toString((char)('A' + y));
    }

    private String xToStringLetterFormat()
    {
        return Integer.toString(x + 1);
    }

    /**
     * Format ex:
     * x = 3, y = 5
     * output: F4
     */
    public String toStringLetterFormat()
    {
        return yToStringLetterFormat() + xToStringLetterFormat();
    }

    public static Position parsePosition(String string) throws PositionFormatException
    {
        //match format: x,y
        Pattern pattern = Pattern.compile("^(-?\\d+(.\\d+)?),\\w*(-?\\d+(.\\d+)?)$");
        Matcher matcher = pattern.matcher(string);

        //make new position using group1 & group3
        if (matcher.find())
            return new Position(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(3)));

        Debug.log("string = " + string);
        throw new PositionFormatException();
    }

    @Override
    public String toString()
    {
        return x + "," + y;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if (!(obj instanceof Position)) return false;
        Position position = (Position)obj;
        return position.x == this.x && position.y == this.y;
    }
}