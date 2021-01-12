package com.dogne.ultimate2.game;
import java.util.ArrayList;

/**
* creates wins state for game
*/
public class WinState implements Cloneable
{
	private ArrayList<int[]> winStates;
	
	public WinState(int row, int column)
	{
		winStates = new ArrayList();
		createWinState(row,column);
	}

	public WinState(WinState other)
	{
		winStates = new ArrayList<int[]>();

		for(int [] winState : other.winStates)
			winStates.add(winState.clone());
	}
	
	public WinState(int [][] states)
	{
		insertState(states);
	}

	@Override
	public Object clone()
	{
		return new WinState(this);
	}

	/**
	* inserts state 
	* @param state
	*/
	public void insertState(int []state)
	{
		winStates.add(state);
	}
	
	/**
	* inserts collection of state 
	* @param states
	*/
	public void insertState(int [][] states)
	{
		for (int [] state : states)
			winStates.add(state);
	}
	
	/**
	* creates win rate based on row and column
	* @param row
	* @param column
	*/
	private void createWinState(int row, int column)
	{
		if(column > 1)
			for(int r = 0 ; r < row ; r++)
			{
				int [] winState = new int[column];
				for(int c = 0 ; c < column ; c++)
				{
					winState[c] = rowColumnToIndex(r,c,column);
				}
				insertState(winState);
			}
		if(row > 1)
			for(int c = 0 ; c < column ; c++)
			{
				int [] winState = new int[row];
				for(int r = 0 ; r < row ; r++)
				{
					winState[r] = rowColumnToIndex(r,c,column);
				}
				insertState(winState);
			}
		
		if(row == column && row > 1 && column > 1)
		{
			int r = 0;
			int c = 0;
			
			int [] winState = new int[column];
			while(r != row && r != column)
			{
				winState[r%column] = rowColumnToIndex(r,c,column);
				r++;
				c++;
			}
			insertState(winState);
			r = column-1;
			c = 0;
			winState = new int[column];
			while(r != -1 && r != row)
			{
				winState[r%column] = rowColumnToIndex(r,c,column);
				r--;
				c++;
			}
			insertState(winState);
		}
	}
	
	/**
	* converts 2D row,Columnn to 1D index
	* @param row
	* @param column
	* @return int[] index
	*/
	private int rowColumnToIndex(int row, int column,int columnSize)
	{
		int index = row * columnSize + column;	
		return index;
	}
	
	/**
	* to string overide
	* @return String
	*/
	public String toString()
	{
		String str = "[";
		for (int i = 0 ; i < winStates.size() ; i++)
		{
			int [] a = winStates.get(i);
			str += "[";

			for(int k = 0 ; k < a.length ; k++)
			{
				str += a[k];
				if (k < a.length - 1)
					str += ",";
			}
			str += "]";
			if (i <  winStates.size() - 1)
					str += ",";
		}
		str += "]";
		return str;
	}

	public ArrayList<int[]> getStates()
	{
		return winStates;
	}
}