package model.audio;

import util.Debug;
import util.SettingFromFile;
import util.Settings;
import util.SoundEffectType;

import javax.sound.sampled.*;
import java.io.InputStream;
import java.util.Objects;

public class SoundEffect
{
    public static synchronized void play(SoundEffectType type)
    {
        try
        {
            Clip clip = AudioSystem.getClip();

            InputStream stream = Music.class.getResourceAsStream(type.get());
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(stream));
            clip.open(inputStream);

            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue((float) Math.log10((SettingFromFile.getSetting(Settings.SOUND_EFFECT_VOLUME) + 0.1) * 0.005) * 20);

            clip.start();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}