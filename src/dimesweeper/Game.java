/*
 * released to the public domain. see UNLICENSE.txt
 */
package dimesweeper;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author S.Bachmann
 */
public class Game extends JFrame
{
    public enum NeighboorhoodType { SQUARE, PLUS, KNIGHT }
    public enum NeighboorhoodWarp { NO, TORUS, REFLECT }

	private static final long serialVersionUID = 1L;
	
	private final Integer mineCount;
	public MineSet mines;
	
	private  Integer flagCount;
	public final HashSet <LinkedList <Integer>> flags;
		
	public Integer revealedCount = 0;
	private Integer boxletCount = 1;
	
	public final String neighborhoodType;
	public final Integer neighborhoodRadius, neighborhoodWrap;
	
	public final LinkedList <Integer> fieldSize;
	
	public final FieldRow field;
	
	public boolean firstClick, hints;
	
	public Game (Integer fieldX, Integer fieldY , Integer mineCount)
	{ this (construct2D (fieldX, fieldY) , mineCount, "square", 1, 0); }
	
	private static LinkedList <Integer> construct2D (Integer x, Integer y)
	{
		LinkedList <Integer> result = new LinkedList <> ();
		result.add (x); result.add (y);
		return result;
	}
	
	public Game (LinkedList <Integer> fieldSize, Integer mineCount)
	{ this (fieldSize, mineCount, "square", 1, 0); }
	
	@SuppressWarnings("unchecked")
	public Game (LinkedList <Integer> fieldSize, Integer mineCount, String neighborhoodType, Integer neighborhoodRadius, Integer neighborhoodWrap)
	{
		hints = true; firstClick = true;
		
		this.fieldSize = fieldSize;
        this.mineCount = mineCount;
		this.neighborhoodType = neighborhoodType;
        this.neighborhoodRadius = neighborhoodRadius;
		this.neighborhoodWrap = neighborhoodWrap;
		
		flags = new HashSet <> ();
		
		field = new FieldRow (fieldSize, new LinkedList <> (), this);

		boxletCount = countBoxlets (this.fieldSize);
		
		add(field);
		pack();
		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		setVisible (true);
		
		checkWon ();
	}

	public static Integer countBoxlets (LinkedList <Integer> f)
	{
		Integer result = 1;
		for (Integer n : f)
			result = result * n;
		return result;
	}
	
	public final void firstClick (LinkedList <Integer> click)
	{
		mines = new MineSet(mineCount, fieldSize, click);
		this.firstClick = false;
	}
	
	public Integer flagsLeft ()
	{ return mineCount - flagCount; }
	
	public Boxlet getBoxlet (LinkedList <Integer> coordinates)
	{ return field.getBoxlet (coordinates); }

	public final void end ()
	{
		hints = false;
		for (LinkedList <Integer> mine : mines)
		{
			if (flags.contains (mine))
				getBoxlet (mine).setBackground (Color.green);
			else
				getBoxlet (mine).setText ("M");
		}
		for (LinkedList <Integer> flag : flags)
		{ if (! mines.contains (flag)) getBoxlet (flag).setBackground (Color.red) ; }
	}
	
	public final void lost (Boxlet aThis)
	{
		end ();
		aThis.setBackground (Color.red);
		JOptionPane.showMessageDialog(this, "you lost.", "guess what?", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public final void checkWon()
	{ if (revealedCount + mineCount == boxletCount) won(); }
	
	public final void won ()
	{
		end ();
		JOptionPane.showMessageDialog(this, "you won." + (mineCount == 0 ? "\nfor sure." : ""), "congraz", JOptionPane.INFORMATION_MESSAGE);
	}
}
