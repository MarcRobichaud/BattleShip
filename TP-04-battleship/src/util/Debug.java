package util;

public class Debug
{
    private static final boolean debugMode = false;

    public static void log(String string)
    {
        if (debugMode)
        {
            System.out.println(string);
        }
    }
}