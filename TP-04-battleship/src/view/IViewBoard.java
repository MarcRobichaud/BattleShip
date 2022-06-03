package view;

import controller.IController;
import util.Position;

public interface IViewBoard
{
    void setController(IController controller);

    void changeTileOfAttackBoard(Position p, int color);

    void changeTileOfDefenseBoard(Position p, int color);

    void disableButtonAt(Position position);

    void changeConsoleOutput(String output);

    void disposeFrame();
}