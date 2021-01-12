package com.dogne.ultimate2.game.mode;

public interface TTT
{
    void getReady();

    boolean insert(int index, int value);

    void changeBoardState();

    void changePlayableValue();

    void changePlayableIndex();

    boolean gameOver();

    int[] updateBoardState();

    int[] updateOuterBoardState(int indexes);

    boolean isFull();

    void print();
}
