/*
 * released to the public domain. see UNLICENSE.txt
 */
package dimesweeper;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author S.Bachmann
 */
public class MinenSet extends HashSet <LinkedList <Integer>>
{
	private static final long serialVersionUID = 1L;

	/**
	 *
	 * @param minenCount
	 * @param feldgröße
	 * @param firstMove
	 */
	
	public MinenSet (Integer minenCount, LinkedList <Integer> feldgröße, LinkedList <Integer> firstMove)
	{
		add (firstMove);
		
		while ( size () <= minenCount )
		{
			LinkedList <Integer> mine = new LinkedList <> (); 
			
			feldgröße.stream ().forEach ((Integer dimension) ->
			{
				mine.add ((int) (dimension * Math.random()));
			});
			
			add (mine);
		}
		
		remove (firstMove);
	}
	
	/**
	 *
	 * @param minenCount
	 * @param feldgröße
	 */
	public MinenSet (int minenCount, LinkedList <Integer> feldgröße)
	{
		while ( size () < minenCount )
		{
			LinkedList <Integer> mine = new LinkedList <> (); 
			
			feldgröße.stream ().forEach ((dimension) ->
			{
				mine.add ((int) (dimension * Math.random()));
			});
			
			add (mine);
		}
	}
	
}
