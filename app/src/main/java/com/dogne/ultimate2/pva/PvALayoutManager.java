package com.dogne.ultimate2.pva;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.dogne.ultimate2.GameResourceManager;
import com.dogne.ultimate2.R;
import com.dogne.ultimate2.game.board.UTTTBoard;
import com.dogne.ultimate2.game.mode.UTTT;
import com.dogne.ultimate2.game.timer.Timer;

public class PvALayoutManager
{
    public PvAGameManager gameManager;
    public PvALayoutConnector layoutConnector;
    public CountDownTimer countDownTimer;

    public Integer currentlySelectedIndex;
    public Integer previouslySelectedIndex;

    public PvALayoutManager(AppCompatActivity activity, UTTT uttt, int firstMove, Timer timer)
    {
        layoutConnector = new PvALayoutConnector(uttt.getRow(),uttt.getColumn(),activity);
        gameManager = new PvAGameManager();
        gameManager.setUttt(uttt);

        currentlySelectedIndex = null;
        previouslySelectedIndex = null;

        gameManager.setPlayer(firstMove,timer);
        gameManager.uttt.getReady();

        gameManager.changeCurrentPlayer();
        gameManager.X.startStopTimer();
        inputButtonOnClick();
        updateButtonBackgroundColor();
        updateTime();
        runningMenuOnClickListener();
        notRunningMenuOnClickListener();
    }

    public PvALayoutManager(AppCompatActivity activity,int row , int column)
    {
        layoutConnector = new PvALayoutConnector(row,column,activity);
        gameManager = new PvAGameManager();
        gameManager.setUttt(row,column);

        currentlySelectedIndex = null;
        previouslySelectedIndex = null;

        gameManager.setPlayer(UTTT.X_STATE,(Timer)null);
        gameManager.uttt.getReady();

        gameManager.changeCurrentPlayer();
        gameManager.X.startStopTimer();
        inputButtonOnClick();
        updateButtonBackgroundColor();
        updateTime();
        runningMenuOnClickListener();
        notRunningMenuOnClickListener();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void inputButtonOnClick()
    {
        for (final ImageButton button : layoutConnector.inputButtons)
        {
            button.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    int eventAction = event.getAction();
                    switch (eventAction)
                    {
                        case MotionEvent.ACTION_DOWN:
                            insert((Integer) button.getTag());
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                    }
                    return false;
                }
            });
        }
    }

    /**
     * inserts currentPlayer value to index
     * @param index
     */
    private void insert(Integer index)
    {
        //sets currently selectedIndex
        setCurrentlySelectedIndex(index);
        //updatesButtonBackgroundColorBasedOnSelectedIndex
        updateSelectedIndex();
        if(doubleClicked())
        {
            if(gameManager.autoInsert(index))
            {
                updateTime();
                update();
            }
        }
    }


    /**
     * changes the currently selected index to the button selected and changes the previously selected index to currently selected index
     * @param currentlySelectedIndex
     */
    public void setCurrentlySelectedIndex(Integer currentlySelectedIndex)
    {
        previouslySelectedIndex = this.currentlySelectedIndex;
        this.currentlySelectedIndex = currentlySelectedIndex;
    }

    /**
     * checks if previously selected index and currently selected index are the same
     * @return
     */
    private boolean doubleClicked()
    {
        return(previouslySelectedIndex == currentlySelectedIndex && previouslySelectedIndex != null);
    }

    /**
     * updates all button image based on the value at the index
     */
    private void updateInputButtonImage()
    {
        for(ImageButton imageButton : layoutConnector.inputButtons)
        {
            updateInputButtonValue(imageButton);
        }
    }

    /**
     * updates button image based on the value at the index
     * @param button
     */
    private void updateInputButtonValue(ImageButton button)
    {
        int value = gameManager.uttt.getBoardValueAt((Integer) button.getTag());
        if(value == UTTT.NONE_STATE)
        {
            button.setImageResource(0);
        }
        else if (value == UTTT.X_STATE)
        {
            button.setImageResource(GameResourceManager.xPieceImage);
        }
        else if(value == UTTT.O_STATE)
        {
            button.setImageResource(GameResourceManager.oPieceImage);
        }
    }

    private void updateOuterStateImage(int outerIndex)
    {
        LinearLayout rect = layoutConnector.outerRectangles.get(outerIndex);

        int value = gameManager.uttt.getOuterBoardState((int) rect.getTag());
        if(value != UTTT.NONE_STATE)
        {
            if (value == UTTT.X_STATE)
                rect.setBackgroundResource(GameResourceManager.xAccentColor);
            else if (value == UTTT.O_STATE)
                rect.setBackgroundResource(GameResourceManager.oAccentColor);
            else if (value == UTTT.DRAW_STATE)
                rect.setBackgroundResource(GameResourceManager.drawPieceImage);
        }
        else
        {
            rect.setBackgroundResource(0);
        }
    }

    private void updateOuterStateImage()
    {
        for(LinearLayout rect : layoutConnector.outerRectangles)
        {
            int value = gameManager.uttt.getOuterBoardState((int) rect.getTag());
            if(value != UTTT.NONE_STATE)
            {
                if (value == UTTT.X_STATE)
                    rect.setBackgroundResource(GameResourceManager.xAccentColor);
                else if (value == UTTT.O_STATE)
                    rect.setBackgroundResource(GameResourceManager.oAccentColor);
                else if (value == UTTT.DRAW_STATE)
                    rect.setBackgroundResource(GameResourceManager.drawPieceImage);
            }
            else
            {
                rect.setBackgroundResource(0);
            }
        }
    }

    /**
     * update piece image
     */
    private void updateGameStateImage()
    {
        int state = gameManager.uttt.getBoardState();

        if(state != UTTT.NONE_STATE)
        {
            if (state == UTTT.X_STATE)
                layoutConnector.board.setBackgroundResource(GameResourceManager.xPieceImage);
            else if (state == UTTT.O_STATE)
                layoutConnector.board.setBackgroundResource(GameResourceManager.oPieceImage);
            else if (state == UTTT.DRAW_STATE)
                layoutConnector.board.setBackgroundResource(GameResourceManager.drawPieceImage);

            for (int i = 0; i < gameManager.uttt.getRow(); i++)
                layoutConnector.board.getChildAt(i).setAlpha(0.5F);

            layoutConnector.menu.removeAllViews();
            layoutConnector.menu.addView(layoutConnector.notRunningMenu);
            updateTypeButton();
        }
        else
        {
            layoutConnector.board.setBackgroundResource(0);
            for (int i = 0; i < gameManager.uttt.getRow(); i++)
                layoutConnector.board.getChildAt(i).setAlpha(1.0F);
        }
    }

    /**
     * highLights future playable index
     */
    private void updateButtonBackgroundColor()
    {
        for(ImageButton button :layoutConnector.inputButtons)
            if(gameManager.uttt.isPlayableIndex((Integer) button.getTag()))
                button.setBackgroundResource(GameResourceManager.playableIndexColor);
            else
            {
                int index = (int) button.getTag();
                int outerIndex = UTTTBoard.indexToOuterIndex(index, gameManager.uttt.getRow(), gameManager.uttt.getColumn());
                if (gameManager.uttt.getOuterBoardState(outerIndex) == UTTT.X_STATE && gameManager.uttt.getBoardValueAt(index) != UTTT.NONE_STATE)
                    layoutConnector.inputButtons.get(index).setBackgroundResource(GameResourceManager.xSelectedAccentColor);
                else if (gameManager.uttt.getOuterBoardState(outerIndex) == UTTT.O_STATE && gameManager.uttt.getBoardValueAt(index) != UTTT.NONE_STATE)
                    layoutConnector.inputButtons.get(index).setBackgroundResource(GameResourceManager.oSelectedAccentColor);
                else
                    button.setBackgroundResource(GameResourceManager.nonePlayableIndexColor);
            }
    }

    /**
     * highLights future playable index
     */
    private void changeCurrentlySelectedButtonBackgroundColor()
    {
        if (currentlySelectedIndex != null)
            if(gameManager.uttt.isPlayableIndex(currentlySelectedIndex))
                if(gameManager.currentPlayer.value == UTTT.O_STATE)
                    layoutConnector.inputButtons.get(currentlySelectedIndex).setBackgroundResource(GameResourceManager.oSelectedAccentColor);
                else if((gameManager.currentPlayer.value == UTTT.X_STATE))
                    layoutConnector.inputButtons.get(currentlySelectedIndex).setBackgroundResource(GameResourceManager.xSelectedAccentColor);
    }

    /**
     * highLights future playable index
     */
    public void highlightFuturePlayableButtonBackgroundColor()
    {
        if(gameManager.uttt.isPlayableIndex(currentlySelectedIndex))
        {
            gameManager.futureInert(currentlySelectedIndex);
            for (int index : gameManager.future.uttt.getPlayableIndex())
                if (gameManager.currentPlayer.value == UTTT.X_STATE)
                    layoutConnector.inputButtons.get(index).setBackgroundResource(GameResourceManager.oAccentColor);
                else if (gameManager.currentPlayer.value == UTTT.O_STATE)
                    layoutConnector.inputButtons.get(index).setBackgroundResource(GameResourceManager.xAccentColor);
        }
    }

    /**
     * update selected index
     */
    public void updateSelectedIndex()
    {
        updateButtonBackgroundColor();
        changeCurrentlySelectedButtonBackgroundColor();
        highlightFuturePlayableButtonBackgroundColor();
    }

    /**
     * updates text about time
     */
    private void updateTime()
    {
        if(gameManager.currentPlayer.timer == null)
            return;

        if(gameManager.uttt == gameManager.virtual)
            return;

        if(gameManager.getWinner() != UTTT.NONE_STATE)
            return;
        countDownTimer = new CountDownTimer(gameManager.currentPlayer.getCurrentTime()*2,1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                if(gameManager.X.timer == null)
                    layoutConnector.xTime.setText("");
                else
                    layoutConnector.xTime.setText(String.valueOf(gameManager.X.getCurrentTime()/1000));

                if(gameManager.O.timer == null)
                    layoutConnector.oTime.setText("");
                else
                    layoutConnector.oTime.setText(String.valueOf(gameManager.O.getCurrentTime()/1000));
                updateGameStateImage();
            }

            @Override
            public void onFinish()
            {

            }
        }.start();
    }

    /**
     * set notRunning menu button onclickListener
     */
    public void runningMenuOnClickListener()
    {
        layoutConnector.runningMenu.findViewById(R.id.pva_menu_running_button_toggleVirtual).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentlySelectedIndex = null;
                gameManager.switchRealityVirtual();
                update();

            }
        });
        layoutConnector.runningMenu.findViewById(R.id.pva_menu_running_button_undo).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentlySelectedIndex = null;
                gameManager.undo();
                update();
            }
        });
        layoutConnector.runningMenu.findViewById(R.id.pva_menu_running_button_redo).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentlySelectedIndex = null;
                gameManager.redo();
                update();
            }
        });
        layoutConnector.runningMenu.findViewById(R.id.pva_menu_running_button_forfeit).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (gameManager.currentPlayer.value == UTTT.X_STATE)
                    gameManager.uttt.setBoardState(UTTT.O_STATE);
                else if (gameManager.currentPlayer.value == UTTT.O_STATE)
                    gameManager.uttt.setBoardState(UTTT.X_STATE);
                update();
            }
        });
    }

    /**
     * set notRunning menu button onclickListener
     */
    public void notRunningMenuOnClickListener()
    {
        layoutConnector.notRunningMenu.findViewById(R.id.pva_menu_notrunning_button_toggleVirtual).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentlySelectedIndex = null;
                gameManager.switchRealityVirtual();
                update();

            }
        });
        layoutConnector.notRunningMenu.findViewById(R.id.pva_menu_notrunning_button_undo).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentlySelectedIndex = null;
                gameManager.undo();
                update();
            }
        });
        layoutConnector.notRunningMenu.findViewById(R.id.pva_menu_notrunning_button_redo).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentlySelectedIndex = null;
                gameManager.redo();
                update();
            }
        });
        layoutConnector.notRunningMenu.findViewById(R.id.pva_menu_notrunning_button_save).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentlySelectedIndex = null;

            }
        });
    }

    /**
     * set button text to reflect virtual or reality
     */
    private void updateTypeButton()
    {
        LinearLayout menu = (LinearLayout) layoutConnector.menu.getChildAt(0);
        Button button = (Button) menu.getChildAt(0);
        if(gameManager.uttt == gameManager.reality)
        {
            button.setText("virtual");
        }
        else if(gameManager.uttt == gameManager.virtual)
        {
            button.setText("reality");
        }
    }

    /**
     * updates game background and images
     */
    public void update()
    {
        updateInputButtonImage();
        updateButtonBackgroundColor();
        updateOuterStateImage();
        updateGameStateImage();
        updateTypeButton();
    }

}
