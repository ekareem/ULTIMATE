package com.dogne.ultimate2.game.Future;

import com.dogne.ultimate2.game.mode.UTTT;

public class UTTTFuture implements Future
{
    public UTTT uttt;

    public UTTTFuture()
    {
        uttt = null;
    }

    public void setUttt(UTTT other)
    {
        uttt = (UTTT) other.clone();
    }

    @Override
    public boolean insert(int index, int playableValue)
    {
        if(uttt != null)
            return uttt.insert(index,playableValue);
        return false;
    }

    @Override
    public String toString()
    {
        return "UTTTFuture{" +
                "uttt=" + uttt +
                '}';
    }
}
