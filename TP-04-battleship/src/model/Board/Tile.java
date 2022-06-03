package model.Board;

import model.ship.Ship;
import util.Position;
import util.TileState;

public class Tile
{
    private final Position position;
    private TileState state;
    private Ship ship;

    public Tile(Position position)
    {
        state = TileState.UNKNOWN;
        this.position = position;
    }

    public Position getPosition()
    {
        return position;
    }

    public TileState getState()
    {
        return state;
    }

    public void setState(TileState state)
    {
        this.state = state;
    }

    public Ship getShip()
    {
        return ship;
    }

    public void setShip(Ship ship)
    {
        this.ship = ship;
    }
}