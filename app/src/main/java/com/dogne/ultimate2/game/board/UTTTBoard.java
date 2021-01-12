package com.dogne.ultimate2.game.board;

import android.util.Log;

import java.util.Arrays;

/**
 * Ultimate tictactoe board
 *
 * index types
 * index : treats the index board as a one dimensional array
 * outer index : gets the index of the outer board
 * inner index : gets index in each individual outer index 0 - outerBoard.length;
 */
public class UTTTBoard implements Board,Cloneable
{
    /**
     * gets the outer index that index resides
     * @param index
     * @param row
     * @param column
     * @return
     */
    public static int indexToOuterIndex(int index,int row,int column)
    {
        return (index / (row * column));
    }

    /**
     * finds the inner index that index resides
     * @param index
     * @param row
     * @param column
     * @return
     */
    public static int indexToInnerIndex(int index,int row, int column)
    {
        return (index % (row * column));
    }

    /**
     * one dimensional indexing to tow dimensional indexing
     * @param index
     * @param column
     * @return
     */
    public static int[] oneDToTowD(int index, int column)
    {
        int r = index % column;
        int c = index / column;

        return new int[]{r,c};
    }

    /**
     * tow dimensional indexing to one dimensional indexing
     * @param indexRow
     * @param indexColumn
     * @param row
     * @return
     */
    public static int towDToOneD(int indexRow, int indexColumn, int row)
    {
        return indexRow * row + indexColumn;
    }

    //array 1D representation of 4d UTTTFuture board
    private int [] board;

    public UTTTBoard(int length)
    {
        board = new int[length];
    }

    /**
     * copy constructor
     * @param other
     */
    public UTTTBoard(UTTTBoard other)
    {
        board = new int [other.board.length];

        //copies every non null element in
        for(int index = 0 ; index < other.board.length ; index ++)
                board[index] = other.board[index];
    }

    @Override
    public boolean insert(int index, int value)
    {
        board[index] = value;
        return true;
    }

    @Override
    public Integer getValueAt(int index)
    {
        return board[index];
    }

    public int getLength()
    {
        return board.length;
    }

    @Override
    public Object clone()
    {
        return new UTTTBoard(this);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof UTTTBoard)) return false;
        UTTTBoard that = (UTTTBoard) o;
        return Arrays.equals(board, that.board);
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(board);
    }

    @Override
    public String toString()
    {
        return "UTTTBoard{" +
                "board=" + Arrays.toString(board) +
                '}';
    }
}
