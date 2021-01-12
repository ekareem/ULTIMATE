package com.dogne.ultimate2;

import androidx.annotation.NonNull;

import com.dogne.ultimate2.game.WinState;

import java.util.ArrayList;

public class WinIndex implements Rule,Cloneable
{
    private ArrayList<int[]> winIndexes;

    public WinIndex()
    {
        winIndexes = new ArrayList<>();
    }

    public WinIndex(WinIndex other)
    {
        this.winIndexes = (ArrayList<int[]>) other.winIndexes.clone();
    }

    @NonNull
    @Override
    protected WinIndex clone() throws CloneNotSupportedException
    {
        return new WinIndex(this);
    }

    public void add(int [] indexes)
    {
        winIndexes.add(indexes);
    }
}
