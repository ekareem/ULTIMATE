package com.dogne.ultimate2.pva;


import com.dogne.ultimate2.game.Future.UTTTFuture;
import com.dogne.ultimate2.game.analytics.Analytic;
import com.dogne.ultimate2.game.mode.UTTT;
import com.dogne.ultimate2.game.player.AI;
import com.dogne.ultimate2.game.player.Player;
import com.dogne.ultimate2.game.timer.Timer;

public class PvAGameManager
{
    Analytic analytic;
    //current uttt
    public UTTT uttt;
    public UTTTFuture future;
    //virtual uttt
    public UTTT virtual;
    //real uttt
    public UTTT reality;

    public Player X;
    public Player O;
    public Player currentPlayer;

    public PvAGameManager()
    {
        analytic = new Analytic();
        uttt = null;
        future = new UTTTFuture();
        X = null;
        O = null;
        virtual = null;
        reality = null;
        currentPlayer = null;
    }

    public void setUttt(int row, int column)
    {
        reality = new UTTT(row,column);
        uttt = reality;
    }

    public void setUttt(UTTT uttt)
    {
        reality = uttt;
        this.uttt = reality;
    }


    public void setPlayer(int player,Timer timer)
    {
        if(player == UTTT.X_STATE)
        {
            X = new Player(uttt, UTTT.X_STATE, timer);
            O = new AI(uttt,UTTT.O_STATE,null);
        }
        else if(player == UTTT.O_STATE)
        {
            X = new AI(uttt,UTTT.X_STATE,null);
            O = new Player(uttt, UTTT.O_STATE, timer);

        }
    }

    public void setPlayer(int player,Long maxTime, Long timeAdded, boolean overtime)
    {
        Timer timer =  new Timer(maxTime,timeAdded,overtime);
        if(player == UTTT.X_STATE)
        {
            X = new Player(uttt, UTTT.X_STATE, timer);
            O = new AI(uttt,UTTT.O_STATE,null);
        }
        else if(player == UTTT.O_STATE)
        {
            X = new AI(uttt,UTTT.X_STATE,null);
            O = new Player(uttt, UTTT.O_STATE, timer);

        }
    }

    /**
     * insert currentplayer value in board
     * @param index
     * @return
     */
    public boolean insert(int index)
    {
        //if current player is null change currentplayer
        if(currentPlayer == null)
            changeCurrentPlayer();

        //inserts value to board
        if(currentPlayer.insert(index))
        {
            if(uttt == reality)
                analytic.addMove(currentPlayer,index);

            changeCurrentPlayer();
            currentPlayer.startStopTimer();
            return true;
        }
        //force insert to board if there is game is timed
        else if(currentPlayer.uttt == virtual)
        {
            if(currentPlayer.forceInsert(index))
            {
                changeCurrentPlayer();
                return true;
            }
        }
        return false;
    }

    public boolean autoInsert(int index)
    {
        //if current player is null change currentplayer
        if(currentPlayer == null)
            changeCurrentPlayer();

        if(uttt == reality)
        {
            if(currentPlayer.getType().equals("player"))
            {
                if(currentPlayer.insert(index))
                {
                    analytic.addMove(currentPlayer,index);
                    changeCurrentPlayer();
                    autoInsert(-1);
                    return true;
                }
            }
            else if (currentPlayer.getType().equals("ai"))
            {
                if(((AI)currentPlayer).insert())
                {
                    analytic.addMove(currentPlayer,index);
                    changeCurrentPlayer();
                    currentPlayer.startStopTimer();
                    return true;
                }
            }
        }
        else if(uttt == virtual)
        {
            return insert(index);
        }
        return false;
    }

    /**
     * gets next board state
     * @param index
     * @return
     */
    public boolean futureInert(int index)
    {
        future.setUttt((UTTT) uttt.clone());
        int value = currentPlayer.getValue();
        return future.insert(index,value);
    }

    /**
     * go back a board state
     * @return
     */
    public boolean redo()
    {
        if(uttt == reality)
        {
            virtual = (UTTT) uttt.clone();
            switchToVirtual();
        }
        if(uttt.redo())
        {
            changeCurrentPlayer();
            return true;
        }
        return false;
    }

    /**
     * go forward a board state
     * @return
     */
    public boolean undo()
    {
        if(uttt == reality)
        {
            virtual = (UTTT) uttt.clone();
            switchToVirtual();
        }
        if(uttt.undo())
        {
            changeCurrentPlayer();
            return true;
        }

        return false;
    }

    /**
     * switch between Reality and virtual mode
     */
    public void switchRealityVirtual()
    {

        if(uttt == reality)
            if(virtual == null)
                virtual = (UTTT) uttt.clone();
            else
                switchToVirtual();
        else if(uttt == virtual)
            switchToReality();
    }


    public void switchToVirtual()
    {
        uttt = virtual;
        X.uttt = virtual;
        O.uttt = virtual;
    }

    public void switchToReality()
    {
        uttt = reality;
        X.uttt = reality;
        O.uttt = reality;
    }

    public int getWinner()
    {
        return reality.getBoardState();
    }

    public void changeCurrentPlayer()
    {
        if (uttt.getPlayableValue() == UTTT.X_STATE)
        {
            currentPlayer = X;
            return;
        }
        else if (uttt.getPlayableValue() == UTTT.O_STATE)
        {
            currentPlayer = O;
            return;
        }
    }

    @Override
    public String toString()
    {
        return "OfflinePvPGameManager{" +
                "uttt=" + uttt +
                "\n future=" + future +
                "\n X=" + X +
                "\n O=" + O +
                "\n currentPlayer=" + currentPlayer +
                '}';
    }
}
