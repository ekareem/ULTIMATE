package com.dogne.ultimate2.offline_pvp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dogne.ultimate2.R;
import com.dogne.ultimate2.game.mode.Advance;
import com.dogne.ultimate2.game.mode.Classic;
import com.dogne.ultimate2.game.mode.UTTT;
import com.dogne.ultimate2.game.timer.Timer;

public class OfflinePvPActivity extends AppCompatActivity
{
    public int row;
    public int column;
    public String gameMode;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent i = getIntent();
        row = i.getIntExtra("row",3);
        column = i.getIntExtra("column",3);
        gameMode = i.getStringExtra("gameMode");
        oTimed = i.getBooleanExtra("oTimed",false);
        oCapped = i.getBooleanExtra("oCapped",false);
        oMaxTime = i.getLongExtra("oMaxTime",300L);
        oAddedTime = i.getLongExtra("oAddedTime",0L);
        xTimed = i.getBooleanExtra("xTimed",false);
        xCapped = i.getBooleanExtra("xCapped",false);
        xMaxTime = i.getLongExtra("xMaxTime",300L);
        xAddedTime = i.getLongExtra("xAddedTime",0L);

        if(!xTimed)
            timerX = null;
        else
            timerX = new Timer(xMaxTime,xAddedTime,xCapped);

        if(!oTimed)
            timerO = null;
        else
            timerO = new Timer(oMaxTime,oAddedTime,oCapped);

        if(gameMode.equals("Classic"))
            uttt = new Classic(row,column);
        else if (gameMode.equals("Advance"))
            uttt = new Advance(row,column);

        OfflinePvPLayoutManager layoutManager = new OfflinePvPLayoutManager(this,uttt,timerX,timerO);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        this.finish();
    }
}