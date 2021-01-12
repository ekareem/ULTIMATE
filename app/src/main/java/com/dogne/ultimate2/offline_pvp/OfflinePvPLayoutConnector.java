package com.dogne.ultimate2.offline_pvp;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.dogne.ultimate2.GameResourceManager;
import com.dogne.ultimate2.MagicText.MagicTextView;
import com.dogne.ultimate2.R;

import java.util.ArrayList;

public class OfflinePvPLayoutConnector
{
    private int row;
    private int column;
    private AppCompatActivity activity;

    public ConstraintLayout root;
    public ConstraintLayout boardContainer;
    public LinearLayout board;
    public ConstraintLayout xGameBar;
    public ConstraintLayout oGameBar;
    public View xCard;

    public ConstraintLayout xGameBarRoot;
    public LinearLayout xAccentColor;
    public MagicTextView xTime;
    public ImageView xPieceImage;
    public ConstraintLayout oGameBarRoot;
    public LinearLayout oAccentColor;
    public MagicTextView oTime;
    public ImageView oPieceImage;
    public View oCard;

    public LinearLayout menu;
    public LinearLayout runningMenu;
    public LinearLayout notRunningMenu;

    public LayoutInflater inflater;

    public ArrayList<ImageButton> inputButtons;
    public ArrayList<LinearLayout> inputLayouts;
    public ArrayList<LinearLayout> innerRows;
    public ArrayList<LinearLayout> outerRectangles;
    public ArrayList<LinearLayout> outerRows;

    public OfflinePvPLayoutConnector(int row, int column, AppCompatActivity activity)
    {
        this.row = row;
        this.column = column;
        this.activity = activity;

        inflater = LayoutInflater.from(activity);

        inputButtons = new ArrayList<>();
        inputLayouts = new ArrayList<>();
        innerRows = new ArrayList<>();
        outerRectangles = new ArrayList<>();
        outerRows = new ArrayList<>();
        setUp();
    }

    private void setUp()
    {
        gameBarConnection();
        gameBoardConnection();

        xGameBar.setBackgroundResource(GameResourceManager.xGameBarImage);
        oGameBar.setBackgroundResource(GameResourceManager.oGameBarImage);
        xAccentColor.setBackgroundResource(GameResourceManager.xAccentColor);
        oAccentColor.setBackgroundResource(GameResourceManager.oAccentColor);
        xPieceImage.setImageResource(GameResourceManager.xPieceImage);
        oPieceImage.setImageResource(GameResourceManager.oPieceImage);
        boardContainer.setBackgroundResource(GameResourceManager.boardImage);
    }

    private void gameBarConnection()
    {
        root = activity.findViewById(R.id.offline_pvp_constraintLayout_root);
        boardContainer = activity.findViewById(R.id.offline_pvp_constraintLayout_board);
        board = activity.findViewById(R.id.offline_pvp_linearLayout_board);
        xGameBar =  activity.findViewById(R.id.offline_pvp_constraintLayout_X_GameBar);
        oGameBar =  activity.findViewById(R.id.offline_pvp_constraintLayout_O_GameBar);
        menu = activity.findViewById(R.id.offline_pvp_linearLayout_menu);

        xCard = inflater.inflate(R.layout.layout_gamebar, xGameBar,false);
        oCard = inflater.inflate(R.layout.layout_gamebar, oGameBar,false);

        xGameBar.addView(xCard);
        oGameBar.addView(oCard);

        runningMenu = (LinearLayout) inflater.inflate(R.layout.layout_offlinepvp_menu_running, menu,false);
        notRunningMenu = (LinearLayout) inflater.inflate(R.layout.layout_offlinepvp_menu_notrunning, menu,false);
        menu.addView(runningMenu);

        xGameBarRoot = xCard.findViewById(R.id.gameBar_constraintLayout_root);
        oGameBarRoot = oCard.findViewById(R.id.gameBar_constraintLayout_root);

        xAccentColor = xCard.findViewById(R.id.gameBar_Layout_accentColor);
        oAccentColor = oCard.findViewById(R.id.gameBar_Layout_accentColor);

        xPieceImage =  xCard.findViewById(R.id.gameBar_image_piece);
        oPieceImage =  oCard.findViewById(R.id.gameBar_image_piece);

        xTime = xCard.findViewById(R.id.gameBar_text_time);
        oTime= oCard.findViewById(R.id.gameBar_text_time);


    }

    private void gameBoardConnection()
    {
        for (int i = 0; i < row ; i++)
        {
            LinearLayout outerRow = (LinearLayout) inflater.inflate(R.layout.layout_outer_row,board,false);
            outerRow.setTag(row);
            outerRows.add(outerRow);

            for (int j = 0; j < column; j++)
            {
                LinearLayout outerRectangle = (LinearLayout)inflater.inflate(R.layout.layout_outer_rectangle,outerRow,false);
                outerRow.addView(outerRectangle);
                int outerIndex = i * column + j;
                outerRectangle.setTag(outerIndex);
                outerRectangles.add(outerRectangle);

                for (int k = 0; k < row ; k++)
                {
                    LinearLayout innerRow = (LinearLayout)inflater.inflate(R.layout.layout_inner_row,outerRectangle,false);
                    outerRectangle.addView(innerRow);
                    innerRow.setTag(row);
                    innerRows.add(innerRow);

                    for (int l = 0; l < column ; l++)
                    {
                        LinearLayout input = (LinearLayout)inflater.inflate(R.layout.layout_input,innerRow,false);
                        int index = (i * column + j) * (row * column) + (k * column + l);
                        input.setTag(index);
                        inputLayouts.add(input);
                        innerRow.addView(input);

                        final ImageButton button = (ImageButton) input.getChildAt(0);
                        button.setTag(index);
                        inputButtons.add(button);
                    }
                }
            }
            board.addView(outerRow);
        }
    }
}
