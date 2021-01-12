package com.dogne.ultimate2.home;

import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.dogne.ultimate2.offline_pvp.OfflinePvPActivity;
import com.dogne.ultimate2.pva.PvAActivity;

public class HomeLayoutManager
{
    private AppCompatActivity activity;
    private HomeLayoutConnector layoutConnector;
    public int row;
    public int column;
    public String gameMode;
    public String opponentMode;
    public String playerPiece;
    public boolean oTimed;
    public boolean oCapped;
    public Long oMaxTime;
    public Long oAddedTime;

    public boolean xTimed;
    public boolean xCapped;
    public Long xMaxTime;
    public Long xAddedTime;

    public HomeLayoutManager(AppCompatActivity activity)
    {
        this.activity = activity;
        layoutConnector = new HomeLayoutConnector(activity);

        row =  Integer.parseInt(layoutConnector.textRow.getText().toString());
        column =  Integer.parseInt(layoutConnector.textColumn.getText().toString());
        gameMode = "Classic";
        opponentMode = "P V P";
        playerPiece = "x";

        oTimed = false;
        oCapped = false;
        oMaxTime = -1L;
        oAddedTime = -1L;

        xTimed = false;
        xCapped = false;
        xMaxTime = -1L;
        xAddedTime = -1L;

        rowSeekBar();
        columnSeekBar();
        playButtonOnclick();
    }

    public void rowSeekBar()
    {
        layoutConnector.seekBarRow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                row = (progress / 26)+1;
                layoutConnector.textRow.setText(String.valueOf(row));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
    }

    public void columnSeekBar()
    {
        layoutConnector.seekBarColumn.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                column = (progress / 26)+1;

                layoutConnector.textColumn.setText(String.valueOf(column));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
    }

    public void gameModeRadioButton()
    {
        int radioId = layoutConnector.radioGroupGameMode.getCheckedRadioButtonId();
        RadioButton radioButton = activity.findViewById(radioId);
        gameMode = radioButton.getText().toString();
    }

    public void opponentModeRadioButton()
    {
        int radioId = layoutConnector.radioGroupOpponentType.getCheckedRadioButtonId();
        RadioButton radioButton = activity.findViewById(radioId);
        opponentMode = radioButton.getText().toString();
    }

    public void playerPieceRadioButton()
    {
        int radioId = layoutConnector.radioGroupPlayerPiece.getCheckedRadioButtonId();
        RadioButton radioButton = activity.findViewById(radioId);
        playerPiece = radioButton.getText().toString();
    }

    public void xTimedCheckBox()
    {
        xTimed = layoutConnector.checkBoxXTimeSettingTimed.isChecked();
    }

    public void xCappedCheckBox()
    {
        xCapped = layoutConnector.checkBoxXTimeSettingCapped.isChecked();
    }

    public void xMaxTime()
    {
        xMaxTime = Long.parseLong(layoutConnector.editXTimeSettingMaxTime.getText().toString())*1000L;
    }

    public void xAddedTime()
    {
        xAddedTime = Long.parseLong(layoutConnector.editXTimeSettingAddTime.getText().toString())*1000L;
    }
    public void oTimedCheckBox()
    {
        oTimed = layoutConnector.checkBoxOTimeSettingTimed.isChecked();
    }

    public void oCappedCheckBox()
    {
        oCapped = layoutConnector.checkBoxOTimeSettingCapped.isChecked();
    }

    public void oMaxTime()
    {
        oMaxTime = Long.valueOf(layoutConnector.editOTimeSettingMaxTime.getText().toString()) * 1000L;
    }

    public void oAddedTime()
    {
        oAddedTime = Long.valueOf(layoutConnector.editOTimeSettingAddTime.getText().toString()) *1000L;
    }

    public void createIntent()
    {
        Intent intent = null;
        if(opponentMode.equals("P V P"))
        {
             intent = new Intent(activity, OfflinePvPActivity.class);
        }
        if(opponentMode.equals("P V A"))
        {
            intent = new Intent(activity, PvAActivity.class);
        }

        intent.putExtra("row",row);
        intent.putExtra("column",column);
        intent.putExtra("gameMode",gameMode);
        intent.putExtra("opponentMode",opponentMode);
        intent.putExtra("playerPiece",playerPiece);
        intent.putExtra("oTimed",oTimed);
        intent.putExtra("oCapped",oCapped);
        intent.putExtra("oMaxTime",oMaxTime);
        intent.putExtra("oAddedTime",oAddedTime);
        intent.putExtra("xTimed",xTimed);
        intent.putExtra("xCapped",xCapped);
        intent.putExtra("xMaxTime",xMaxTime);
        intent.putExtra("xAddedTime",xAddedTime);

        activity.startActivity(intent);
    }

    public void playButtonOnclick()
    {
        layoutConnector.buttonPlay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                gameModeRadioButton();
                opponentModeRadioButton();
                playerPieceRadioButton();
                xTimedCheckBox();
                xCappedCheckBox();
                xMaxTime();
                xAddedTime();
                oTimedCheckBox();
                oCappedCheckBox();
                oMaxTime();
                oAddedTime();
                createIntent();
            }
        });
    }

    @Override
    public String toString()
    {
        return "HomeLayoutManager{" +
                "row=" + row +
                "\n column=" + column +
                "\n gameMode='" + gameMode + '\'' +
                "\n opponentMode='" + opponentMode + '\'' +
                "\n playerPiece='" + playerPiece + '\'' +
                "\n oTimed=" + oTimed +
                "\n oCapped=" + oCapped +
                "\n oMaxTime=" + oMaxTime +
                "\n oAddedTime=" + oAddedTime +
                "\n xTimed=" + xTimed +
                "\n xCapped=" + xCapped +
                "\n xMaxTime=" + xMaxTime +
                "\n xAddedTime=" + xAddedTime +
                '}';
    }
}
