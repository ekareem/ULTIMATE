package com.dogne.ultimate2.game.player;

import com.dogne.ultimate2.game.mode.UTTT;
import com.dogne.ultimate2.game.timer.Timer;

import java.util.ArrayList;
import java.util.Random;

public class AI extends Player
{

    public AI(UTTT uttt, int value, Long maxTime, Long timeAdded, boolean overtime)
    {
        super(uttt, value, null);
        type = "ai";
    }

    public AI(UTTT uttt, int value, Timer timer)
    {
        super(uttt, value, null);
        type = "ai";
    }

    public AI(AI other)
    {
        super(other);
    }

    public boolean insert()
    {
        ArrayList<Integer> playableIndex = uttt.getPlayableIndex();
        int index = playableIndex.get(new Random().nextInt(playableIndex.size()));
        return uttt.insert(index,value);
    }

}
