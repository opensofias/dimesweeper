/*
 * released to the public domain. see UNLICENSE.txt
 */
package dimesweeper;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author S.Bachmann
 */
public class MineSet extends HashSet <LinkedList <Integer>>
{
	private static final long serialVersionUID = 1L;

	/**
	 *
	 * @param mineCount
	 * @param fieldSize
	 * @param firstMove
	 */
	
	public MineSet (Integer mineCount, LinkedList<Integer> fieldSize, LinkedList<Integer> firstMove)
	{
		add (firstMove);
		
		while ( size () <= mineCount )
		{
			LinkedList <Integer> mine = new LinkedList <> (); 
			
			fieldSize.stream().forEach ((Integer dimension) ->
                    mine.add ((int) (dimension * Math.random())));
			
			add (mine);
		}
		
		remove (firstMove);
	}
	
	/**
	 *
	 * @param mineCount
	 * @param fieldsize
	 */
	public MineSet (int mineCount, LinkedList<Integer> fieldsize)
	{
		while ( size () < mineCount )
		{
			LinkedList <Integer> mine = new LinkedList <> (); 
			
			fieldsize.stream().forEach ((dimension) ->
                    mine.add ((int) (dimension * Math.random())));
			
			add (mine);
		}
	}
	
}
