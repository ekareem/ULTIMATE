package com.dogne.ultimate2;

import androidx.annotation.NonNull;

/**
 * sets the dimension rules
 */
public class Dimension implements Rule,Cloneable
{
    private int outerRow;
    private int outerColumn;
    private int innerRow;
    private int innerColumn;
    private int[] board;

    public Dimension(int outerRow, int outerColumn, int innerRow, int innerColumn)
    {
        this.outerRow = outerRow;
        this.outerColumn = outerColumn;
        this.innerRow = innerRow;
        this.innerColumn = innerColumn;
        board = new int[getLength()];
    }

    public Dimension(Dimension other)
    {
        this.outerRow = other.outerRow;
        this.outerColumn = other.outerColumn;
        this.innerRow = other.innerRow;
        this.innerColumn = other.innerColumn;
        this.board = other.board.clone();
    }

    @NonNull
    @Override
    protected Dimension clone()
    {
        return new Dimension(this);
    }

    /**
     * gets length
     * @return length
     */
    public int getLength()
    {
        return outerRow * outerColumn * innerRow * innerColumn;
    }

    /**
     * gets outer length
     * @return outer length
     */
    public int getOuterLength()
    {
        return outerRow * outerColumn;
    }

    /**
     * gets inner Length
     * @return inner Length
     */
    public int getInnerLength()
    {
        return innerRow * innerColumn;
    }

    /**
     * gets innerIndexes from outer outer index
     * @param outerIndex outer index
     * @return inner indexes
     */
    public int[] getInnerIndexesFromOuterIndex(int outerIndex)
    {
        int[] indexes = new int[getInnerLength()];
        for(int i = 0; i < getInnerLength(); i++)
        {
            int index = outerIndex * getInnerLength() + i;
            indexes[i] = index;
        }
        return indexes;
    }

    /**
     * gets outer index from an index
     * @param index index
     * @return outer index
     */
    public int getOuterIndexFromIndex(int index)
    {
        return index / getInnerLength();
    }

    /**
     * gets inner index form index
     * @param index inner index
     * @return inner indexes
     */
    public int[] getInnerIndexesFromInnerIndex(int index)
    {
        int outerIndex = getOuterIndexFromIndex(index);
        return getInnerIndexesFromOuterIndex(outerIndex);
    }

    public int getIndexFromOuterIndexAndInnerIndex(int outerIndex,int innerIndex)
    {
        return outerIndex * getInnerLength() + innerIndex;
    }

    public int getInnerIndexFromIndex(int index)
    {
        return index % getInnerLength();
    }

    public int getOuterRow()
    {
        return outerRow;
    }

    public int getOuterColumn()
    {
        return outerColumn;
    }

    public int getInnerRow()
    {
        return innerRow;
    }

    public int getInnerColumn()
    {
        return innerColumn;
    }

    public int[] getBoard()
    {
        return board;
    }
}
