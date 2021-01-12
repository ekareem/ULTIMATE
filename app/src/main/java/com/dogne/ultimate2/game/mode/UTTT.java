package com.dogne.ultimate2.game.mode;

import android.util.Log;

import com.dogne.ultimate2.game.WinState;
import com.dogne.ultimate2.game.board.UTTTBoard;
import com.dogne.ultimate2.game.stack.RedoUndo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class UTTT implements TTT,Cloneable
{
    public static  final int ALL_PLAYABLE = -1;
    //no  winner state
    public static final int NONE_STATE = 0;
    //draw state
    public static final int DRAW_STATE = 100;
    //X state x getStartStopTimer by default
    public static final int X_STATE = 1;
    //O state
    public static final int O_STATE = -1;

    protected int row;
    protected int column;

    protected int boardState;
    protected int[] outerBoardState;
    protected ArrayList<Integer> playableIndex;
    protected int playableValue;
    protected int playableOuterIndex;
    protected int moveMade;

    public UTTTBoard utttBoard;
    protected WinState winStates;
    public RedoUndo<UTTT> stack;

    public UTTT(int row, int column)
    {
        this.row = row;
        this.column = column;
        boardState = NONE_STATE;
        outerBoardState = new int[getOuterLength()];
        playableIndex = new ArrayList<>();
        playableValue = NONE_STATE;
        moveMade = 0;
        playableOuterIndex = ALL_PLAYABLE;
        utttBoard = new UTTTBoard(getLength());
        winStates = new WinState(row,column);
        stack = new RedoUndo<>();

    }

    public UTTT(UTTT other)
    {
        row = other.row;
        column = other.column;
        boardState = other.boardState;
        outerBoardState = other.outerBoardState.clone();
        playableIndex = (ArrayList<Integer>) other.playableIndex.clone();
        playableValue = other.playableValue;
        moveMade = other.moveMade;
        playableOuterIndex = other.playableOuterIndex;
        utttBoard = (UTTTBoard) other.utttBoard.clone();
        winStates = (WinState) other.winStates.clone();
        stack = (RedoUndo<UTTT>) other.stack.clone();
    }

    public void cast(UTTT other)
    {
        row = other.row;
        column = other.column;
        boardState = other.boardState;
        outerBoardState = other.outerBoardState.clone();
        playableIndex = (ArrayList<Integer>) other.playableIndex.clone();
        playableValue = other.playableValue;
        moveMade = other.moveMade;
        playableOuterIndex = other.playableOuterIndex;
        utttBoard = (UTTTBoard) other.utttBoard.clone();
        winStates = (WinState) other.winStates.clone();
    }

    /**
     * gets the game ready
     */
    @Override
    public void getReady()
    {
        changeBoardState();
        changePlayableOuterIndex(ALL_PLAYABLE);
        changePlayableIndex();
        changePlayableValue();
        stack.add((UTTT) this.clone());
    }


    /**
     * inserts a value to board
     * @param index
     * @param value
     * @return
     */
    @Override
    public boolean insert(int index, int value)
    {
        if(!isPlayableIndex(index))
            return false;
        if(!isPlayableValue(value))
            return false;

        utttBoard.insert(index,value);

        afterInsert(index);
        return true;
    }

    /**
     * decides what happens after a succesful insert index inserted is passed in
     * @param indexInserted
     */
    protected void afterInsert(int indexInserted)
    {
        updateOuterBoardState(indexInserted);
        changeBoardState();
        changePlayableOuterIndex(indexInserted);
        changePlayableIndex();
        changePlayableValue();
        stack.add((UTTT) this.clone());
        moveMade = moveMade + 1;
    }


    /**
     * changes board state;
     */
    @Override
    public void changeBoardState()
    {
        if(boardState != NONE_STATE)
            return;

        updateBoardState();
    }

    /**
     * changes the playable value;
     */
    @Override
    public void changePlayableValue()
    {
        if (gameOver())
            return;

        //sets first value to x by default
        if (playableValue == NONE_STATE)
            playableValue = X_STATE;

            //if playbleValue is x change it to o and vice versa
        else if(playableValue == X_STATE)
            playableValue = O_STATE;

        else if(playableValue == O_STATE)
            playableValue = X_STATE;
    }

    /**
     * changes the palayable index base on the playableIndex
     * NOTE : changePlayableOuterIndex(int) should be ran before this
     */
    @Override
    public void changePlayableIndex()
    {
        //if game is over clear playableIndex;
        if (gameOver())
        {
            playableIndex.clear();
            return;
        }

        //clears playableIndex
        if(playableIndex != null)
            playableIndex.clear();

        if(playableIndex == null)
            playableIndex = new ArrayList<>();

        //if allIndexes have potential of being played
        if(playableOuterIndex == ALL_PLAYABLE)
        {
            //iterate over outer board
            for(int outerIndex = 0 ; outerIndex < getOuterLength() ; outerIndex++)
                //checks if outer board state has not winner or tie
                if(getOuterBoardState(outerIndex) == NONE_STATE)
                {
                    int[] indexes = getIndexesInOuterBoard(outerIndex);
                    for (int index : indexes)
                        if(getBoardValueAt(index) == NONE_STATE)
                            playableIndex.add(index);
                }
        }
        else
        {
            int [] indexes = getIndexesInOuterBoard(playableOuterIndex);
            for(int index : indexes)
            {
                if (getBoardValueAt(index) == NONE_STATE)
                    playableIndex.add(index);
            }
        }
    }

    /**
     * changes playable outer index
     * @param index
     */
    protected void changePlayableOuterIndex(int index)
    {
        //if game is over do nothing
        if (gameOver())
            return;

        //if all outer index is playable make playableOuterIndex All playable
        if (index  == ALL_PLAYABLE)
            playableOuterIndex = index;
        else
        {
            updateOuterBoardState(index);
            //get next outer index
            int outerIndex = UTTTBoard.indexToInnerIndex(index, row, column);
            //if board state of outer index is over make playerOuterIndex is ALL PLAYABLE
            if(getOuterBoardState(outerIndex) != NONE_STATE)
                changePlayableOuterIndex(ALL_PLAYABLE);
                //make playableOuterIndex outerIndex
            else
                playableOuterIndex = outerIndex;
        }
    }

    /**
     * gets indexes in outer board
     * @param outerIndex
     * @return
     */
    public int [] getIndexesInOuterBoard(int outerIndex)
    {
        int [] indexes = new int [getOuterLength()];
        for (int i = 0 ; i < getOuterLength() ; i++)
        {
            int index = outerIndex * getOuterLength() + i;
            indexes[i] = index;
        }
        return indexes;
    }

    /**
     * checks if gameState is over
     * @return
     */
    @Override
    public boolean gameOver()
    {
        return boardState != NONE_STATE;
    }

    /**
     * ets outer board state and updates boardState
     * @return
     */
    @Override
    public int[] updateBoardState()
    {
        //if boardState already has a value
        if (boardState != NONE_STATE)
            return null;

        //iterate over win states
        for (int[] winState : winStates.getStates())
        {
            int count = 0;

            //checks if very value in win state is the same by adding them
            for (int index : winState)
            {
                int value = outerBoardState[index];
                //System.out.println("\tindex :"+index+" value :"+value);
                count += value;
            }
            //System.out.println(Arrays.toString(winState)+" count :"+ count+" == "+(O_STATE * winState.length));

            if (count == (X_STATE * winState.length))
            {
                boardState = X_STATE;
                return winState;
            }
            if (count == (O_STATE * winState.length))
            {
                boardState = O_STATE;
                return winState;
            }
        }
        //if is full then its a draw
        if (isFull())
            boardState = DRAW_STATE;

        return null;
    }

    /**
     * ets outer board state and updates outerBoardState
     * @param indexes
     */
    @Override
    public int[] updateOuterBoardState(int indexes)
    {

        int outerIndex = UTTTBoard.indexToOuterIndex(indexes, row, column);
        //if boardState already has a value
        if (outerBoardState[outerIndex] != NONE_STATE)
            return null;

        //if boardState already has a value
        int[] innerIndexes = getIndexesInOuterBoard(outerIndex);
        for (int[] winState : winStates.getStates())
        {
            int count = 0;

            //checks if very value in win state is the same by adding them
            for (int index : winState)
            {
                int value = getBoardValueAt(innerIndexes[index]);
                count += value;
            }
            if (count == (X_STATE * winState.length))
            {
                outerBoardState[outerIndex] = X_STATE;
                return winState;
            }
            if (count == (O_STATE * winState.length))
            {
                outerBoardState[outerIndex] = O_STATE;
                return winState;
            }
        }
        //if is full then its a draw
        if (isFull(outerIndex))
            outerBoardState[outerIndex] = DRAW_STATE;
        return null;
    }



    /**
     * checks if all outer board state have a value if so its full
     * @return
     */
    @Override
    public boolean isFull()
    {
        for (int outerIndex = 0 ; outerIndex  < getOuterLength(); outerIndex ++)
            if(outerBoardState[outerIndex] == NONE_STATE)
                return false;
        return true;
    }

    /**
     * checks if every index index int outer board has a value if so it's full
     * @param outerIndex
     * @return
     */
    public boolean isFull(int outerIndex)
    {
        int [] Indexes = getIndexesInOuterBoard(outerIndex);
        for(int index : Indexes)
            if (getBoardValueAt(index) == NONE_STATE)
                return false;

        return true;
    }

    public boolean isPlayableIndex(int indexPlayed)
    {
        for (int index : playableIndex)
            if (index == indexPlayed)
                return true;

        return false;
    }

    public boolean isPlayableValue(int valuePlayed)
    {
        return playableValue == valuePlayed;
    }

    /**
     * forces set board state this is for if time ends
     * @param state
     */
    public void setBoardState(int state)
    {
        if(boardState == NONE_STATE)
        {
            boardState = state;
            stack.add((UTTT) this.clone());
        }
    }

    /**
     * goes back aboard state
     * @return
     */
    public boolean redo()
    {
        if(stack.redo())
        {
            cast(stack.getElement());
            return true;
        }
        return false;
    }

    public boolean undo()
    {
        if(stack.undo())
        {
            cast(stack.getElement());
            return true;
        }
        return false;
    }

    public int getPlayableValue()
    {
        return playableValue;
    }

    public int getBoardState()
    {
        return boardState;
    }

    public int getOuterBoardState(int outerIndex)
    {
        return outerBoardState[outerIndex];
    }

    /**
     * gets board length;
     * @return
     */
    public int getLength()
    {
        return row * column * row * column;
    }

    /**
     * get outer board length
     * @return
     */
    public int getOuterLength()
    {
        return row * column;
    }

    public int getBoardValueAt(int index)
    {
        return utttBoard.getValueAt(index);
    }

    public int getRow()
    {
        return row;
    }

    public int getColumn()
    {
        return column;
    }

    public int[] getOuterBoardState()
    {
        return outerBoardState;
    }

    public ArrayList<Integer> getPlayableIndex()
    {
        return playableIndex;
    }

    public int getPlayableOuterIndex()
    {
        return playableOuterIndex;
    }

    public int getMoveMade()
    {
        return moveMade;
    }

    public UTTTBoard getUtttBoard()
    {
        return utttBoard;
    }

    public WinState getWinStates()
    {
        return winStates;
    }

    @Override
    public Object clone()
    {
        return new UTTT(this);
    }

    /**
     * prints 3d version of table
     */
    @Override
    public void print()
    {
        int r = 0;
        int c = 0;
        for(int i = 0 ; i < row ; i++)
        {
            for(int j = 0 ; j < getOuterLength() ; j++)
            {
                r = j / column;
                c = j % column;
                System.out.print("|");
                for(int k = 0 ; k <column; k++)
                {
                    int index = (i * column + c) * getOuterLength() + (r * column + k);
                    System.out.print("|"+utttBoard.getValueAt(index)+"|");
                }
                System.out.print("|");
                if((j% column) == column-1) System.out.println();
            }
            System.out.println();
        }
    }

    @Override
    public String toString()
    {
        return "UTTT{" +
                "row=" + row +
                "\n column=" + column +
                "\n boardState=" + boardState +
                "\n outerBoardState=" + Arrays.toString(outerBoardState) +
                "\n playableIndex=" + playableIndex +
                "\n playableValue=" + playableValue +
                "\n playableOuterIndex=" + playableOuterIndex +
                "\n moveMade=" + moveMade +
                "\n utttBoard=" + utttBoard +
                "\n winStates=" + winStates +
                "\n stack=" + stack +
                '}';
    }
}
