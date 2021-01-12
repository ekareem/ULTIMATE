package com.dogne.ultimate2.game.stack;

import androidx.annotation.NonNull;

import com.dogne.ultimate2.game.mode.UTTT;

import java.util.ArrayList;

/**
 * allows the ability to redo and undo
 * @param <T>
 */
public class RedoUndo<T> implements Cloneable
{
	//top of the stack
	private int top;
	//current stack index
	private int current;
	//fallback index
	private int beforeRedoUndo;
	public ArrayList <T>stack;


	public RedoUndo()
	{
		current = -1;
		top =  -1;
		beforeRedoUndo = -1;
		stack = new ArrayList<T>();
	}

	public RedoUndo(RedoUndo<T> other)
	{
		top = other.top;
		current = other.current;
		beforeRedoUndo = other.beforeRedoUndo;
		stack = (ArrayList<T>) other.stack.clone();
	}

	/**
	 * adds element to stack and increments beforeRedoUno
	 * @param element
	 */
	public void add(T element)
	{
		for(int i = current + 1; i <= top ; i++)
			stack.remove(current + 1);

		current += 1;
		top = current;
		beforeRedoUndo = current;
		stack.add(current,element);
	}

	/**
	 * adds element to stack doesn't increments beforeRedoUno
	 * @param element
	 */
	public void addFake(T element)
	{
		for(int i = current + 1; i <= top ; i++)
			stack.remove(current + 1);

		current += 1;
		top = current;

		stack.add(current,element);
	}

	/**
	 * move up in the stack
	 * @return
	 */
	public boolean redo()
	{
		//increments current
		if (current >= top)
			return false;
		current += 1;
		return true;
	}

	/**
	 * move down in the stack
	 * @return
	 */
	public boolean undo()
	{
		//decrements current
		if (current <= 0)
			return false;

		current -= 1;
		return true;
	}

	/**
	 * moves current to beforeRedoUndo
	 */
	public void backToBeforeRedoUndo()
	{
		current = beforeRedoUndo;

		for(int i = current + 1; i <= top ; i++)
			stack.remove(current + 1);
		top = current;
	}

	/**
	 * gets cuttent element
	 * @return
	 */
	public T getElement()
	{
		return stack.get(current);
	}

	/** getters **/
	public int getTop()
	{
		return top;
	}

	public int getCurrent()
	{
		return current;
	}

	public int getBeforeRedoUndo()
	{
		return beforeRedoUndo;
	}

	public ArrayList<T> getStack()
	{
		return stack;
	}

	@NonNull
	@Override
	public Object clone()
	{
		return new RedoUndo<T>(this);
	}

	@Override
	public String toString()
	{
		String str =  "RedoUndo{" +
				"stack=";
		for(T val : stack)
		{
			UTTT v = (UTTT) val;
			str += "\n\t"+v.utttBoard.toString();
		}
				str +='}';
		UTTT l = (UTTT) getElement();
		return str +"\n\tcurrent="+l.utttBoard.toString();
	}
}