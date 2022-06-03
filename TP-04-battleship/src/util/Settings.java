package util;

public enum Settings
{
    MUSIC_VOLUME("MusicVolume"),
    SOUND_EFFECT_VOLUME("SoundEffectVolume");

    private final String setting;

    Settings(String setting)
    {
        this.setting = setting;
    }

    public String get()
    {
        return setting;
    }
}