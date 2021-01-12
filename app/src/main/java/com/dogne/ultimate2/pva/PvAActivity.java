package com.dogne.ultimate2.pva;

import androidx.appcompat.app.AppCompatActivity;

import com.dogne.ultimate2.R;
import com.dogne.ultimate2.game.mode.Advance;
import com.dogne.ultimate2.game.mode.Classic;
import com.dogne.ultimate2.game.mode.UTTT;
import com.dogne.ultimate2.game.timer.Timer;

import android.content.Intent;
import android.os.Bundle;

public class PvAActivity extends AppCompatActivity
{
    public int row;
    public int column;
    public String gameMode;
    public String playerPiece;
    public boolean oTimed;
    public boolean oCapped;
    public Long oMaxTime;
    public Long oAddedTime;

    public boolean xTimed;
    public boolean xCapped;
    public Long xMaxTime;
    public Long xAddedTime;
    public UTTT uttt;
    public Timer timerX;
    public Timer timerO;
    private Timer timer;
    private int firstmove;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent i = getIntent();
        row = i.getIntExtra("row", 3);
        column = i.getIntExtra("column", 3);
        gameMode = i.getStringExtra("gameMode");
        playerPiece = i.getStringExtra("playerPiece");
        oTimed = i.getBooleanExtra("oTimed", false);
        oCapped = i.getBooleanExtra("oCapped", false);
        oMaxTime = i.getLongExtra("oMaxTime", 300L);
        oAddedTime = i.getLongExtra("oAddedTime", 0L);
        xTimed = i.getBooleanExtra("xTimed", false);
        xCapped = i.getBooleanExtra("xCapped", false);
        xMaxTime = i.getLongExtra("xMaxTime", 300L);
        xAddedTime = i.getLongExtra("xAddedTime", 0L);

        if (!xTimed)
            timerX = null;
        else
            timerX = new Timer(xMaxTime, xAddedTime, xCapped);

        if (!oTimed)
            timerO = null;
        else
            timerO = new Timer(oMaxTime, oAddedTime, oCapped);

        if (playerPiece.equals("X"))
        {
            timer = timerX;
            firstmove = UTTT.X_STATE;
        }
        if (playerPiece.equals("O"))
        {
            timer = timerO;
            firstmove = UTTT.O_STATE;
        }

        if(gameMode.equals("Classic"))
            uttt = new Classic(row,column);
        else if (gameMode.equals("Advance"))
            uttt = new Advance(row,column);

        PvALayoutManager layoutManager = new PvALayoutManager(this,uttt,firstmove,timer);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        this.finish();
    }
}