package dimesweeper.neighborhoods;

import dimesweeper.INeighborhood;
import dimesweeper.positions.Position;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 *
 * @author sofias.
 */
public class Leap implements INeighborhood
{
	private final DropList <Integer> pattern;
	
	public Leap (DropList <Integer> pattern) { this.pattern = pattern; }
	
	@Override
	public Set<Position> getNeighborPositions (Position pos, int radius)
	{
		Set<Position> result = new HashSet<> ();
		
		if (pos.isEmpty ()) result.add (Position.NIL);
		else
		{
			if ( pos.getLength () > pattern.size () )
				for (Position subposition : getNeighborPositions (pos.getTail (), radius))
					result.add (subposition.prepend (pos.getHead ()));
			
			for (Integer displacement : pattern)
			{
				Leap newLeap = new Leap (pattern.drop (displacement));
				
				for (Position subposition : newLeap.getNeighborPositions (pos.getTail (), radius))
				{
					for (int i = 1; i < radius; i++)
					{
						result.add (subposition.prepend (pos.getHead () + i * displacement));
						result.add (subposition.prepend (pos.getHead () - i * displacement));
					}
				}
			}
		}
		
		return result;
	}
	
	public class DropList <E> extends LinkedList <E>
	{
		DropList <E> drop (E toDrop)
		{
			DropList <E> result = new DropList <> ();
			boolean skipped = false;
			for (E elements : this)
				if (toDrop == elements && !skipped) skipped = true;
				else result.add (elements);
			return result;
		}
	}
}
