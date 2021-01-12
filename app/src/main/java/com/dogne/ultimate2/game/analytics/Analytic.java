package com.dogne.ultimate2.game.analytics;

import com.dogne.ultimate2.game.player.Player;
import com.dogne.ultimate2.game.timer.Timer;

import java.util.ArrayList;

/**
 * basic analytics
 */

public class Analytic
{
    //list of moves
    private ArrayList<Move> moves;
    private int row;
    private int column;
    private String gameMode;
    private String opponentType;

    public Analytic()
    {
        moves = new ArrayList<>();
    }

    //adds moves
    public void addMove(Player player, int index)
    {
        Timer time = player.timer;
        if(player.timer == null)
            moves.add(new Move(player.getValue(),index,null));
        else
            moves.add(new Move(player.getValue(),index,time.getCurrentTime()));
    }

    //move object
    private class Move
    {
        public Move(int value, int index, Long time)
        {
            this.value = value;
            this.index = index;
            this.time = time;
        }

        //value
        public int value;
        //index
        public int index;
        //time played
        public Long time;
    }
}
