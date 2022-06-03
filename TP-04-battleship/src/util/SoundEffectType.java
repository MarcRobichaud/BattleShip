package util;

import model.audio.SoundEffect;

public enum SoundEffectType
{
    BOOM("..\\..\\resources\\boom.wav"),
    SPLASH("..\\..\\resources\\splash.wav");

    String path;

    SoundEffectType(String path)
    {
        this.path = path;
    }

    public String get()
    {
        return path;
    }

}