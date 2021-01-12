package com.dogne.ultimate2;

import androidx.annotation.NonNull;

public class PlayableIndex implements Rule,Cloneable
{
    private int playableOuterIndex;
    private final boolean playableIfOuterIndexIsWon;
    private final boolean playableIfOuterIndexIsFull;
    private final boolean winnableIfOuterIndexIsWon;
    private final int playerCount;


    public PlayableIndex(int playableOuterIndex, boolean playableIfOuterIndexIsWon, boolean playableIfOuterIndexIsFull, boolean winnableIfOuterIndexIsWon, int playerCount)
    {
        this.playableOuterIndex = playableOuterIndex;
        this.playableIfOuterIndexIsWon = playableIfOuterIndexIsWon;
        this.playableIfOuterIndexIsFull = playableIfOuterIndexIsFull;
        this.winnableIfOuterIndexIsWon = winnableIfOuterIndexIsWon;
        this.playerCount = playerCount;
    }

    public PlayableIndex(PlayableIndex other)
    {
        this.playableOuterIndex = other.playableOuterIndex;
        this.playableIfOuterIndexIsWon = other.playableIfOuterIndexIsWon;
        this.playableIfOuterIndexIsFull = other.playableIfOuterIndexIsFull;
        this.winnableIfOuterIndexIsWon = other.winnableIfOuterIndexIsWon;
        this.playerCount = other.playerCount;
    }

    @NonNull
    @Override
    protected PlayableIndex clone() throws CloneNotSupportedException
    {
        return new PlayableIndex(this);
    }

    public void setPlayableOuterIndex(int playableOuterIndex)
    {
        this.playableOuterIndex = playableOuterIndex;
    }

    public int getPlayableOuterIndex()
    {
        return playableOuterIndex;
    }

    public boolean isPlayableIfOuterIndexIsWon()
    {
        return playableIfOuterIndexIsWon;
    }

    public boolean isPlayableIfOuterIndexIsFull()
    {
        return playableIfOuterIndexIsFull;
    }

    public boolean isWinnableIfOuterIndexIsWon()
    {
        return winnableIfOuterIndexIsWon;
    }
}
