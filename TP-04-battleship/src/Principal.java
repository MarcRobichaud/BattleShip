import controller.Controller;
import model.audio.Music;
import view.MainMenu;

public class Principal
{
    public static void main(String[] args)
    {
        Music.playSound();
        new Controller(new MainMenu());
    }
}