package com.dogne.ultimate2.game.timer;

import android.os.CountDownTimer;

import androidx.annotation.NonNull;

public class Timer implements Cloneable
{
    public static final Long countDownInterval = 1000L;     //interval for update
    private Long maxTime;                                   //max time also getStartStopTimer time
    private Long currentTime;                               //current time
    private Long timeAdded;                                 //time to be added when time stopped
    public CountDownTimer countDownTimer;                  //count down timer object
    private boolean timeCapped;                               //decides if time added goes over maxTime
    private boolean isRunning;                              //checks if time is running
    private boolean isDone;                                 //checks if count down timer is down

    public Timer(Long maxTime, Long timeAdded, boolean timeCapped)
    {
        this.maxTime = maxTime;
        currentTime = maxTime;
        this.timeAdded = timeAdded;
        this.timeCapped = timeCapped;

        isRunning = false;
        isDone = false;
    }

    /**
     * copy constructor
     * NOTE does not copy countdown timer so countdown timer will always be null
     * @param
     */
    public Timer(Timer other)
    {
        maxTime = other.maxTime;
        currentTime = other.currentTime;
        timeAdded = other.timeAdded;
        countDownTimer = null;
        timeCapped = other.timeCapped;
        isRunning = other.isRunning;
        isDone = other.isDone;
    }

    /**
     * starts the timer if the time is running stops the time if otherwise
     */
    /**
    public void startStop()
    {
        //if Timer isn't runnable neither getStartStopTimer or stop
        if(isRunnable())
            return;

        //if timer is done neither getStartStopTimer or stop
        if (isDone)
            return;

        if(isRunning)
        {
            stopTimer();
            //adds time if neede
            addTime();
        }
        else
            startTimer();
    }**/

    /**
     * adds time to current after time stopped
     */
    public void addTime()
    {
        //don't add time if not runnable
        if(isRunnable())
            return;

        //only add time if time is not running and is not done
        if(!isRunning && !isDone)
            if(timeCapped)
                currentTime = (currentTime + timeAdded > maxTime) ? maxTime : currentTime + timeAdded;
            else
                currentTime += timeAdded;
    }

    /**
     * resets the timer
     */
    public void reset()
    {
        if(countDownTimer== null)
            return;

        if(isRunnable())
            return;

        currentTime = maxTime;
        isRunning = false;
        isDone = false;
        countDownTimer.cancel();
    }

    /**
     * stops the timer
     */

    public void stopTimer()
    {
        if(countDownTimer== null)
            return;

        if(isRunnable())
            return;

        countDownTimer.cancel();
        isRunning = false;
    }

    /**
     * starts the timer
     */
    /**
    public void startTimer()
    {
        if(isRunnable())
            return;

        isRunning = true;

        countDownTimer = new CountDownTimer(currentTime,Timer.countDownInterval)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                currentTime = millisUntilFinished;
            }

            @Override
            public void onFinish()
            {
                onTimerFinished();
            }
        }.start();
    }**/

    public void onTimerFinished()
    {
        isDone = true;
        isRunning = false;
    }

    /**
     * checks if timer is in a runnable state
     * @return boolean
     */
    public boolean isRunnable()
    {
        return maxTime == null;
    }

    public Long setCurrentTime(Long currentTime)
    {
        return this.currentTime = currentTime;
    }

    public void setRunning(boolean running)
    {
        isRunning = running;
    }

    /**
     * gets Max time
     * @return Long max time
     */
    public Long getMaxTime()
    {
        return maxTime;
    }

    /**
     * gets current time
     * @return Long
     */
    public Long getCurrentTime()
    {
        return currentTime;
    }

    /**
     * get time Added
     * @return Long
     */
    public Long getTimeAdded()
    {
        return timeAdded;
    }

    /**
     * checks if time is Capped
     * @return boolean
     */
    public boolean isTimeCapped()
    {
        return timeCapped;
    }

    /**
     * checks if time is running
     * @return boolean
     */
    public boolean isRunning()
    {
        return isRunning;
    }

    /**
     * checks if timer is done
     * @return boolean
     */
    public boolean isDone()
    {
        return isDone;
    }

    @NonNull
    @Override
    public Object clone()
    {
        return new Timer(this);
    }

    @Override
    public String toString()
    {
        return "Timer{" +
                "maxTime=" + maxTime +
                "\n currentTime=" + currentTime +
                "\n timeAdded=" + timeAdded +
                "\n countDownTimer=" + countDownTimer +
                "\n isRunning=" + isRunning +
                "\n isDone=" + isDone +
                '}';
    }

}
