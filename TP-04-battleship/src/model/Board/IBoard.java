package model.Board;

import model.ship.Ship;
import util.Position;
import util.TileState;

import java.util.ArrayList;

public interface IBoard
{
    ArrayList<Tile> getTiles();

    ArrayList<Position> getPositionsWithShip();

    Ship getShipAt(Position position);

    boolean isTileFilled(Position p);

    TileState getTileStateAt(Position p);

    void changeTileStateAt(Position p);

    void fillTiles(ArrayList<Ship> positions);
}