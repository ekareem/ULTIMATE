package com.dogne.ultimate2.game.mode;

import com.dogne.ultimate2.game.board.UTTTBoard;

public class Advance extends UTTT
{
    public Advance(int row, int column)
    {
        super(row, column);
    }

    public Advance(Advance other)
    {
        super(other);
    }

    /**
     * changes playable outer index
     * @param index
     */
    @Override
    protected void changePlayableOuterIndex(int index)
    {
        //if game is over do nothing
        if (gameOver())
            return;

        //if all outer index is playable make playableOuterIndex All playable
        if (index  == ALL_PLAYABLE)
            playableOuterIndex = index;
        else
        {
            updateOuterBoardState(index);
            //get next outer index
            int outerIndex = UTTTBoard.indexToInnerIndex(index, row, column);
            //if board is full outer index is over make playerOuterIndex is ALL PLAYABLE
            if(isFull(outerIndex))
                changePlayableOuterIndex(ALL_PLAYABLE);
                //make playableOuterIndex outerIndex
            else
                playableOuterIndex = outerIndex;
        }
    }
}
