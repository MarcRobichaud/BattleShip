package model.player;

import controller.IController;
import util.PlayerState;
import util.Position;
import util.ShootType;

import java.util.ArrayList;

public interface IPlayer
{
    void setController(IController controller);

    ArrayList<Position> getPositionsWithShipsInDefenseBoard();

    boolean isAlive();

    PlayerState getState();

    void changeState(PlayerState state);

    void tryShootAt(Position position);

    ShootType getShotAt(Position position);
}