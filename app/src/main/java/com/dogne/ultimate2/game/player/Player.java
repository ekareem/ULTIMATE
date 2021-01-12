package com.dogne.ultimate2.game.player;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dogne.ultimate2.game.mode.UTTT;
import com.dogne.ultimate2.game.timer.Timer;

/**
 * player class
 */
public class Player implements Cloneable
{
    //value that can be played
    public int value;
    //board that player is playing on
    public UTTT uttt;
    //player time
    public Timer timer;

    protected String type;

    public Player(UTTT uttt, int value,Long maxTime, Long timeAdded, boolean overtime)
    {
        this.uttt = uttt;
        this.value = value;
        timer = new Timer(maxTime,timeAdded,overtime);
        type = "player";
    }

    public Player(UTTT uttt,int value, Timer timer)
    {
        this.uttt = uttt;
        this.value = value;
        this.timer = timer;
        type = "player";
    }

    public Player(Player other)
    {
        uttt = (UTTT)other.uttt.clone();
        value = other.value;
        timer = (Timer)other.timer.clone();
        type = other.type;
    }

    /**
     * insets value in to board and stops timer
     * @param index
     * @return
     */
    public boolean insert(int index)
    {
        if(timer != null)
        {
            if(!timer.isDone())
                if (uttt.insert(index,value))
                {
                    startStopTimer();
                    return true;
                }
        }
        else if (timer == null)
        {
            if (uttt.insert(index,value))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * forces insert on index this this is for board with timer
     * @param index
     * @return
     */
    public boolean forceInsert(int index)
    {
        return uttt.insert(index,value);
    }

    /**
     * stats and stops timer
     */
    public void startStopTimer()
    {

        if(timer == null)
            return;

        //if Timer isn't runnable neither getStartStopTimer or stop
        if(timer.isRunnable())
            return;

        //if timer is done neither getStartStopTimer or stop
        if (timer.isDone())
            return;

        if(timer.isRunning())
        {
            stopTimer();
            //adds time if neede
            timer.addTime();
        }
        else
        {
            startTimer();
        }
    }

    public void stopTimer()
    {
        if(timer == null)
            return;

        timer.stopTimer();
    }

    /**
     * starts timer
     */
    public void startTimer()
    {
        if(timer == null)
            return;

        timer.setRunning(true);
        timer.countDownTimer = new CountDownTimer(timer.getCurrentTime(),Timer.countDownInterval)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                if(uttt.getBoardState() != UTTT.NONE_STATE)
                {
                    timer.stopTimer();
                }
                timer.setCurrentTime(millisUntilFinished);
            }

            //if time finishes update game sate current player loses
            @Override
            public void onFinish()
            {
                if(value == UTTT.X_STATE)
                    uttt.setBoardState(UTTT.O_STATE);
                if(value == UTTT.O_STATE)
                    uttt.setBoardState(UTTT.X_STATE);
                timer.onTimerFinished();
            }
        }.start();
    }

    /** getters ***/
    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public UTTT getUttt()
    {
        return uttt;
    }

    public void setUttt(UTTT uttt)
    {
        this.uttt = uttt;
    }

    public Timer getTimer()
    {
        return timer;
    }

    public String getType()
    {
        return type;
    }

    /** setter **/
    public void setTimer(Timer timer)
    {
        this.timer = timer;
    }

    public Long getCurrentTime()
    {
        if(timer == null)
            return null;
        return timer.getCurrentTime();
    }

    @NonNull
    @Override
    public Object clone()
    {
        return new Player(this);
    }

    @Override
    public String toString()
    {
        return "Player{" +
                "value=" + value +
                ", uttt=" + uttt +
                ", timer=" + timer +
                '}';
    }
}
