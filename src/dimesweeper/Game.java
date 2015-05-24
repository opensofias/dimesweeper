/*
 * released to the public domain. see UNLICENSE.txt
 */
package dimesweeper;

import java.awt.Color;
import java.util.*;
import javax.swing.*;

/**
 * @author S.Bachmann
 */
public class Game extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private final Integer minenCount;
	public MinenSet mines;
	
	private  Integer flagCount;
	public final HashSet <LinkedList <Integer>> flags;
		
	public Integer revealedCount = 0;
	private Integer boxletCount = 1;
	
	public final String neighborType;
	public final Integer reighborRadius, neigborWrap;
	
	public final LinkedList <Integer> fieldSize;
	
	public final FieldRow field;
	
	public boolean firstClick, hints;
	
	public Game (Integer feldX, Integer feldY , Integer minenCt)
	{ this (construct2D (feldX, feldY) , minenCt, "square", 1, 0); }	
	
	private static LinkedList <Integer> construct2D (Integer x, Integer y)
	{
		LinkedList <Integer> result = new LinkedList <> ();
		result.add (x); result.add (y);
		return result;
	}
	
	public Game (LinkedList <Integer> feldGr, Integer minenCt)
	{ this (feldGr, minenCt, "square", 1, 0); }
	
	@SuppressWarnings("unchecked")
	public Game (LinkedList <Integer> feldGr, Integer minenCt, String nType, Integer nRad, Integer nWrap)
	{
		hints = true; firstClick = true;
		
		fieldSize = feldGr; minenCount = minenCt;
		neighborType = nType; reighborRadius = nRad;
		neigborWrap = nWrap;
		
		flags = new HashSet <> ();
		
		field = new FieldRow (feldGr, new LinkedList <> (), this);

		boxletCount = countBoxlets (fieldSize);
		
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
		mines = new MinenSet (minenCount, fieldSize, click);
		this.firstClick = false;
	}
	
	public Integer flagsLeft ()
	{ return minenCount - flagCount; }
	
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
	{ if (revealedCount + minenCount == boxletCount) won(); }
	
	public final void won ()
	{
		end ();
		JOptionPane.showMessageDialog(this, "you won." + (minenCount == 0 ? "\nfor sure." : ""), "congraz", JOptionPane.INFORMATION_MESSAGE);
	}
}
