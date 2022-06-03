package util;

import exception.SettingNotFoundException;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingFromFile
{
    public static int getSetting(Settings setting) throws SettingNotFoundException
    {
        BufferedReader br;
        try (FileReader fr = new FileReader("src/resources/Settings"))
        {
            br = new BufferedReader(fr);

            String line;
            Pattern valuePattern = Pattern.compile("^(" + setting.get() + ".=.)([0-9]+)$");
            Matcher valueMatcher;

            while ((line = br.readLine()) != null)
            {
                valueMatcher = valuePattern.matcher(line);
                if (valueMatcher.find())
                {
                    fr.close();
                    br.close();
                    return Integer.parseInt(valueMatcher.group(2));
                }
            }
            fr.close();
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        throw new SettingNotFoundException();
    }

    public static void setSetting(Settings setting, int value)
    {
        BufferedReader br;
        try (FileReader fr = new FileReader("src/resources/Settings"))
        {
            br = new BufferedReader(fr);

            String line;
            Pattern valuePattern = Pattern.compile("^(" + setting.get() + ".=.)([0-9]+)$");
            Matcher valueMatcher;
            StringBuilder string = new StringBuilder();

            while ((line = br.readLine()) != null)
            {
                valueMatcher = valuePattern.matcher(line);
                if (valueMatcher.find())
                {
                    line = setting.get() + " = " + value;
                }
                string.append(line);
                string.append('\n');
            }
            fr.close();
            br.close();

            FileWriter fw = new FileWriter("src/resources/Settings");
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(string.toString());

            bw.close();
            fw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}