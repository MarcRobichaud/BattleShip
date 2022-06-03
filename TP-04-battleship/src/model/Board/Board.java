package model.Board;

import model.ship.Ship;
import util.Position;
import util.TileState;

import java.util.ArrayList;

public class Board implements IBoard
{
    public static int width = 10, height = 10;
    private final ArrayList<Tile> tiles = new ArrayList<>();

    public Board()
    {
        initTiles();
    }

    private void initTiles()
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                tiles.add(new Tile(new Position(x, y)));
            }
        }
    }

    @Override
    public void fillTiles(ArrayList<Ship> ships)
    {
        for (Ship ship : ships)
        {
            for (int i = 0; i < ship.getSize(); i++)
            {
                getTileAt(ship.getPositions()[i]).setShip(ship);
            }
        }
    }

    @Override
    public ArrayList<Position> getPositionsWithShip()
    {
        ArrayList<Position> filledTiles = new ArrayList<>();
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                Position position = new Position(x, y);
                if (getTileAt(position).getShip() != null)
                    filledTiles.add(position);
            }
        }
        return filledTiles;
    }

    private Tile getTileAt(Position p)
    {
        return tiles.get(p.getY() * width + p.getX());
    }

    @Override
    public ArrayList<Tile> getTiles()
    {
        return tiles;
    }

    @Override
    public TileState getTileStateAt(Position p)
    {
        return getTileAt(p).getState();
    }

    @Override
    public Ship getShipAt(Position position)
    {
        return getTileAt(position).getShip();
    }

    @Override
    public boolean isTileFilled(Position p)
    {
        return getTileAt(p).getShip() != null;
    }

    @Override
    public void changeTileStateAt(Position p)
    {
        getTileAt(p).setState(TileState.KNOWN);
    }
}