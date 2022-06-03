package model.audio;

import util.Debug;
import util.SettingFromFile;
import util.Settings;

import javax.sound.sampled.*;
import java.io.InputStream;
import java.util.Objects;

public class Music
{
    public static FloatControl control;

    public static synchronized void playSound() {
        try
        {
            Clip clip = AudioSystem.getClip();

            InputStream stream = Music.class.getResourceAsStream("..\\..\\resources\\soundtrack01.wav");
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(stream));
            clip.open(inputStream);

            control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue((float) Math.log10((SettingFromFile.getSetting(Settings.MUSIC_VOLUME) + 0.1) * 0.005) * 20);

            clip.start();

            clip.addLineListener(event -> {
                Debug.log(event.toString());
                if( event.getType() == LineEvent.Type.STOP)
                {
                    playSecondMusic();
                }
            });
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    private static synchronized void playSecondMusic()
    {
        try
        {
            Clip clip = AudioSystem.getClip();

            InputStream stream = Music.class.getResourceAsStream("..\\..\\resources\\soundtrack02.wav");
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(stream));
            clip.open(inputStream);

            control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue((float) Math.log10((SettingFromFile.getSetting(Settings.MUSIC_VOLUME) + 0.1) * 0.005) * 20);

            clip.loop(5);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}