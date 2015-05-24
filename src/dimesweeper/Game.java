/*
 * released to the public domain. see UNLICENSE.txt
 */
package dimesweeper;

import dimesweeper.neighborhoods.Plus;
import dimesweeper.neighborhoods.Square;
import dimesweeper.warps.Non;
import dimesweeper.warps.Torus;

import javax.swing.*;
import java.awt.*;
import java.util.*;

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
	
	public final INeighborhood neighborhoodType;
	public final Integer neighborhoodRadius;
    public final IWarp neighborhoodWrap;
	
	public final ArrayList<Integer> fieldSize;
	
	public final FieldRow field;
	
	public boolean firstClick, hints;
	
	public Game (Integer fieldX, Integer fieldY , Integer mineCount)
	{ this (construct2D (fieldX, fieldY), mineCount, NeighboorhoodType.SQUARE, 1, NeighboorhoodWarp.NO); }
	
	private static ArrayList<Integer> construct2D (Integer x, Integer y)
	{
        ArrayList <Integer> result = new ArrayList <> ();
		result.add (x);
        result.add (y);
		return result;
	}
	
	public Game (ArrayList<Integer> fieldSize, Integer mineCount)
	{ this (fieldSize, mineCount, NeighboorhoodType.SQUARE, 1, NeighboorhoodWarp.NO); }
	
	@SuppressWarnings("unchecked")
	public Game (ArrayList <Integer> fieldSize, Integer mineCount, NeighboorhoodType neighborhoodType, Integer neighborhoodRadius, NeighboorhoodWarp neighborhoodWrap)
	{
		hints = true; firstClick = true;
		
		this.fieldSize = fieldSize;
        this.mineCount = mineCount;
        this.neighborhoodRadius = neighborhoodRadius;

        switch (neighborhoodType) {
            case SQUARE: this.neighborhoodType = Square.instance; break;
            case PLUS: this.neighborhoodType = Plus.instance; break;
            default:
                this.neighborhoodType = null;
        }

        switch (neighborhoodWrap) {
            case NO: this.neighborhoodWrap = Non.instance; break;
            case TORUS: this.neighborhoodWrap = Torus.instance; break;
            default: this.neighborhoodWrap = null;
        }

		flags = new HashSet <> ();
		
		field = new FieldRow (fieldSize, new Position(), this);

		boxletCount = countBoxlets (this.fieldSize);
		
		add(field);
		pack();
		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		setVisible (true);
		
		checkWon ();
	}

	public static Integer countBoxlets (ArrayList <Integer> f)
	{
		Integer result = 1;
		for (Integer n : f)
			result = result * n;
		return result;
	}
	
	public final void firstClick (Position click)
	{
		mines = new MineSet(mineCount, fieldSize, click);
		this.firstClick = false;
	}
	
	public Integer flagsLeft ()
	{ return mineCount - flagCount; }
	
	public Boxlet getBoxlet (Deque<Integer> coordinates)
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

    public final Set<Position> findNeighbors(Position position)
    {
        Set<Position> neighbors;
        if (neighborhoodType != null) {
            neighbors = neighborhoodType.getNeighborPositions((Position) position.clone(), neighborhoodRadius);
        } else {
            return new HashSet<>();
        }
        if (neighborhoodWrap != null) {
            neighbors = neighborhoodWrap.applyWarp(neighbors, this);
        }

        neighbors.remove(position);

        return neighbors;
    }
}
