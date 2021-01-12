package com.dogne.ultimate2.game.board;

/**
 * this is a skeleton for tic tac toe game
 *
 */
public interface Board
{
    /**
     * inserts a value into board ot spcified index
     * @param index
     * @param value
     * @return returns true if successfuly inserted
     */
    public boolean insert(int index, int value);

    /**
     * get value at a specific position in index
     * @param index
     * @return integerValues
     */
    public Integer getValueAt(int index);
}
