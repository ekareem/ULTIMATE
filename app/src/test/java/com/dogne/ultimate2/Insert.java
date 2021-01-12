package com.dogne.ultimate2;

public class Insert implements Rule
{
    Dimension dimension;
    PlayableIndex playableIndex;
    WinIndex outerWinIndex;
    WinIndex innerWinIndex;
    IndexMap indexMap;

    public Insert(Dimension dimension, PlayableIndex playableIndex, WinIndex outerWinIndex, WinIndex innerWinIndex, IndexMap indexMap)
    {
        this.dimension = dimension;
        this.playableIndex = playableIndex;
        this.outerWinIndex = outerWinIndex;
        this.innerWinIndex = innerWinIndex;
        this.indexMap = indexMap;
    }


    public void insert(int player, int value)
    {

    }
}
