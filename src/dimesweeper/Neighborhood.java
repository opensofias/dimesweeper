/*
 * released to the public domain. see UNLICENSE.txt
 */
package dimesweeper;

import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author S.Bachmann
 */
public class Neighborhood extends LinkedList <Boxlet>
{
	private static final long serialVersionUID = 1L;
	
	public static LinkedList <String> types = new LinkedList<> ();
	
	private final String type; 
	private final LinkedList <Integer> fieldSize;
	private final Integer radius;
	private final Integer wrap;
	private final Boxlet boxlet;
	
	public Neighborhood (Boxlet k)
	{
		boxlet = k;
		Game spiel = boxlet.game;
		
		type = spiel.neighborhoodType;
		fieldSize = spiel.fieldSize;
		radius = spiel.neighborhoodRadius;
		wrap = spiel.neighborhoodWrap;
		
		for (LinkedList <Integer> coords : findNeigbors ())
		{ add (spiel.getBoxlet (coords)); }
	}
	

	@SuppressWarnings("unchecked")
	public final CopyOnWriteArrayList <LinkedList <Integer>> findNeigbors ()
	{
		LinkedList <Integer> position = boxlet.position;
		
		CopyOnWriteArrayList <LinkedList <Integer>> neighs = new CopyOnWriteArrayList <> ();
		
		switch (type)
		{
			case "square":
				for (int i = 0; i < fieldSize.size (); i++)
				{
					neighs.add ((LinkedList <Integer>) position.clone ());
					
					for (LinkedList <Integer> n : neighs)
					{
						
						for (int j = 1; j <= radius; j++)
						{
							@SuppressWarnings("unchecked")
							LinkedList <Integer> n1 = (LinkedList <Integer>) n.clone ();
							n1.set (i, position.get (i) + j);
							neighs.add(n1);

							@SuppressWarnings("unchecked")
							LinkedList <Integer> n2 = (LinkedList <Integer>) n.clone ();
							n2.set (i, position.get (i) - j);
							neighs.add(n2);
						}
					}
					neighs.remove ((LinkedList <Integer>) position.clone ());
				}
				break;
				
				
			case "plus":
				for (int i = 0; i < fieldSize.size (); i++)
				{
					for (int j = 1; j <= radius; j++)
					{
						@SuppressWarnings("unchecked")
						LinkedList <Integer> n1 = (LinkedList <Integer>) position.clone ();
						n1.set (i, n1.get (i) + j);
						neighs.add(n1);
					
						@SuppressWarnings("unchecked")
						LinkedList <Integer> n2 = (LinkedList <Integer>) position.clone ();
						n2.set (i, n2.get (i) - j);
						neighs.add (n2);
					}
				}
				
				break;
				
			case "knight":
				for (int j = 0; j < 4; j++)
				{
					LinkedList <Integer> n1 = (LinkedList <Integer>) position.clone ();
					n1.set (0, j % 2 == 0 ? n1.get (0) + 2 : n1.get (0) - 2);
					n1.set (1, j % 4 < 2 ? n1.get (1) + 1 : n1.get (1) - 1);
					neighs.add (n1);
					
					LinkedList <Integer> n2 = (LinkedList <Integer>) position.clone ();
					n2.set (0, j % 2 == 0 ? n2.get (0) + 1 : n2.get (0) - 1);
					n2.set (1, j % 4 < 2 ? n2.get (1) + 2 : n2.get (1) - 2);
					neighs.add (n2);
				}
				break;
			default:
				break;
				

		}
		
		for (LinkedList <Integer> n : neighs)
		{
			if (wraps (n))
			switch (wrap)
			{
				
				case 1:	// torus.
					for (int i = 0; i < fieldSize.size (); i++)
					{ n.set (i, (n.get (i) + fieldSize.get (i)) % fieldSize.get (i)); }
					break;
				case 2: // reflect. todo. doesn't work. can't brain.
					for (int i = 0; i < fieldSize.size (); i++)
					{ n.set (i, fieldSize.get (i) - ((n.get (i) + fieldSize.get (i)) % fieldSize.get (i) )); }
					break;
				default: case 0:
					neighs.remove (n); break;
					
			}
			
			
		}
		
		return neighs;
	}
	
	public boolean wraps (LinkedList <Integer> test)
	{
		for (Integer i = 0; i < fieldSize.size (); i++)
			if (test.get (i) < 0 || test.get (i) >= fieldSize.get (i)) return true;
		return false;
	}
}
