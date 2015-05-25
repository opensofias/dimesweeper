/*
 * released to the public domain. see UNLICENSE.txt
 */
package dimesweeper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author S.Bachmann
 */
public class MineSet extends HashSet <Position>
{
	private static final long serialVersionUID = 1L;

	/**
	 *
	 * @param mineCount
	 * @param fieldSize
	 * @param firstMove
	 */
	
	public MineSet (Integer mineCount, ArrayList<Integer> fieldSize, Position firstMove)
	{
		add (firstMove);
		
		while ( size () <= mineCount )
		{
			Position mine = new Position();
			
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
            Position mine = new Position ();
			
			fieldsize.stream().forEach ((dimension) ->
                    mine.add ((int) (dimension * Math.random())));
			
			add (mine);
		}
	}
	
}
