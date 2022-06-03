package model.player;

import model.Board.IBoard;
import util.PlayerState;
import util.Position;
import util.TileState;

public class LocalPlayer extends Player
{
    public LocalPlayer(IBoard attackBoard, IBoard defenseBoard, String name)
    {
        super(attackBoard, defenseBoard, name);
        state = PlayerState.ENEMY_TURN;
    }

    @Override
    public void tryShootAt(Position position)
    {
        if (state == PlayerState.MY_TURN && attackBoard.getTileStateAt(position) == TileState.UNKNOWN)
        {
            attackBoard.changeTileStateAt(position);
            controller.shootPlayer2At(position);
        }
    }

    @Override
    protected String getMissOutput(Position position)
    {
        return "Player 2 : Missed at " + position.toStringLetterFormat();
    }

    @Override
    protected String getHitOutput(Position position)
    {
        return "Player 2 : Hit at " + position.toStringLetterFormat();
    }

    @Override
    protected String getLostOutput()
    {
        return "You lost";
    }
}