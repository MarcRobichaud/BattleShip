package util;

public class RematchJButton extends MenuJButton
{
    public RematchJButton(String text)
    {
        super(text);
        sizeSettings();
    }

    private void sizeSettings()
    {
        setFont(GameFont.REMATCH_VIEW);
    }
}