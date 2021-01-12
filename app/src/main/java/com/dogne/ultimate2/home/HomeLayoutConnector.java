package com.dogne.ultimate2.home;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.dogne.ultimate2.R;

public class HomeLayoutConnector
{
    public AppCompatActivity activity;

    public ConstraintLayout linearLayoutRoot;

    //size
    public LinearLayout linearLayoutSize;
    public LinearLayout linearLayoutRow;
    public TextView textDescriptionRow;
    public TextView textRow;
    public SeekBar seekBarRow;

    public LinearLayout linearLayoutColumn;
    public TextView text_descriptionColumn;
    public TextView textColumn;
    public SeekBar seekBarColumn;

    //game mode
    public LinearLayout linearLayoutGameMode;
    public RadioGroup radioGroupGameMode;
    public RadioButton radioButtonGameModeClassic;
    public RadioButton radioButtonGameModeAdvance;

    //opponentType
    public LinearLayout linearLayoutOpponentType;
    public RadioGroup radioGroupOpponentType;
    public RadioButton radioButtonOpponentTypePVP;
    public RadioButton radioButtonOpponentTypePVA;

    //playerPiece
    public LinearLayout linearLayoutPlayerPiece;
    public RadioGroup radioGroupPlayerPiece;
    public RadioButton radioButtonPlayerPieceX;
    public RadioButton radioButtonPlayerPieceO;

    //x time setting
    public LinearLayout linearLayoutXTimeSetting;
    public CheckBox checkBoxXTimeSettingTimed;
    public CheckBox checkBoxXTimeSettingCapped;
    public LinearLayout linearLayoutXTimeSettingMaxTime;
    public TextView textXTimeSettingMaxTime;
    public EditText editXTimeSettingMaxTime;
    public LinearLayout linearLayoutXTimeSettingAddTime;
    public TextView textXTimeSettingAddTime;
    public EditText editXTimeSettingAddTime;

    //o time setting
    public LinearLayout linearLayoutOTimeSetting;
    public CheckBox checkBoxOTimeSettingTimed;
    public CheckBox checkBoxOTimeSettingCapped;
    public LinearLayout linearLayoutOTimeSettingMaxTime;
    public TextView textOTimeSettingMaxTime;
    public EditText editOTimeSettingMaxTime;
    public LinearLayout linearLayoutOTimeSettingAddTime;
    public TextView textOTimeSettingAddTime;
    public EditText editOTimeSettingAddTime;

    public Button buttonPlay;
    private LayoutInflater inflater;
    private LinearLayout option;

    public HomeLayoutConnector(AppCompatActivity activity)
    {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);

        setConnection();
        initialize();
    }

    private void setConnection()
    {
        linearLayoutRoot = activity.findViewById(R.id.home_constraintLayout_root);
        option = (LinearLayout) inflater.inflate(R.layout.layout_option,linearLayoutRoot,false);
        linearLayoutRoot.addView(option);
        //size
        linearLayoutSize = activity.findViewById(R.id.option_linearLayout_size);

        linearLayoutRow = activity.findViewById(R.id.option_linearLayout_row);
        textDescriptionRow = activity.findViewById(R.id.option_size_text_row_description);
        textRow = activity.findViewById(R.id.option_size_text_row);
        seekBarRow = activity.findViewById(R.id.option_size_seekBar_row);

        linearLayoutColumn = activity.findViewById(R.id.option_linearLayout_column);
        text_descriptionColumn = activity.findViewById(R.id.option_size_text_column_description);
        textColumn = activity.findViewById(R.id.option_size_text_column);
        seekBarColumn = activity.findViewById(R.id.option_size_seekBar_column);

        //game mode
        linearLayoutGameMode = activity.findViewById(R.id.option_linearLayout_gameMode);
        radioGroupGameMode = activity.findViewById(R.id.option_radioGroup_gameMode);
        radioButtonGameModeClassic = activity.findViewById(R.id.option_gameMode_radioButton_classic);
        radioButtonGameModeAdvance = activity.findViewById(R.id.option_gameMode_radioButton_advance);

        //opponentType
        linearLayoutOpponentType = activity.findViewById(R.id.option_linearLayout_opponentType);
        radioGroupOpponentType = activity.findViewById(R.id.option_opponentType_radioGroup);
        radioButtonOpponentTypePVP = activity.findViewById(R.id.option_opponentType_radioButton_pvp);
        radioButtonOpponentTypePVA = activity.findViewById(R.id.option_opponent_type_radioButton_pva);

        //playerPiece
        linearLayoutPlayerPiece = activity.findViewById(R.id.option_linearLayout_playerPiece);
        radioGroupPlayerPiece = activity.findViewById(R.id.option_radioGroup_playerPiece);
        radioButtonPlayerPieceX = activity.findViewById(R.id.option_playerPiece_radioButton_x);
        radioButtonPlayerPieceO = activity.findViewById(R.id.option_playerPiece_radioButton_o);

        //x time setting
        linearLayoutXTimeSetting = activity.findViewById(R.id.option_linearLayout_x_timeSetting);
        checkBoxXTimeSettingTimed = activity.findViewById(R.id.option_checkBox_x_timed);
        checkBoxXTimeSettingCapped = activity.findViewById(R.id.option_timeSetting_x_capped);
        linearLayoutXTimeSettingMaxTime = activity.findViewById(R.id.option_timeSetting_linearLayout_x_maxTime);
        textXTimeSettingMaxTime = activity.findViewById(R.id.option_timeSetting_text_x_maxTime);
        editXTimeSettingMaxTime = activity.findViewById(R.id.option_timeSetting_edit_x_maxTime);
        linearLayoutXTimeSettingAddTime = activity.findViewById(R.id.option_timeSetting_linearLayout_x_addTime);
        textXTimeSettingAddTime = activity.findViewById(R.id.option_timeSetting_text_x_addTime);
        editXTimeSettingAddTime = activity.findViewById(R.id.option_timeSetting_edit_x_addTime);

        //x time setting
        linearLayoutOTimeSetting = activity.findViewById(R.id.option_linearLayout_o_timeSetting);
        checkBoxOTimeSettingTimed = activity.findViewById(R.id.option_checkBox_o_timed);
        checkBoxOTimeSettingCapped = activity.findViewById(R.id.option_timeSetting_o_capped);
        linearLayoutOTimeSettingMaxTime = activity.findViewById(R.id.option_timeSetting_linearLayout_o_maxTime);
        textOTimeSettingMaxTime = activity.findViewById(R.id.option_timeSetting_text_o_maxTime);
        editOTimeSettingMaxTime = activity.findViewById(R.id.option_timeSetting_edit_o_maxTime);
        linearLayoutOTimeSettingAddTime = activity.findViewById(R.id.option_timeSetting_linearLayout_o_addTime);
        textOTimeSettingAddTime = activity.findViewById(R.id.option_timeSetting_text_o_addTime);
        editOTimeSettingAddTime = activity.findViewById(R.id.option_timeSetting_edit_o_addTime);

        buttonPlay = activity.findViewById(R.id.option_button_play);
    }

    public void initialize()
    {
        textRow.setText("1");
        textColumn.setText("1");
        editOTimeSettingMaxTime.setText("300");
        editOTimeSettingAddTime.setText("0");
        editXTimeSettingMaxTime.setText("300");
        editXTimeSettingAddTime.setText("0");
    }
}
