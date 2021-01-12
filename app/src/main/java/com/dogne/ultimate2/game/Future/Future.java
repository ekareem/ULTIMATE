package com.dogne.ultimate2.game.Future;

/**
 * a class that predict next board state base on input
 */
public interface Future
{
    /**
     * inserts value to see what next state would be
     * @param index
     * @param playableValue
     * @return
     */
    boolean insert(int index, int playableValue);
}
