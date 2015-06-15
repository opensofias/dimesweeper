package dimesweeper.neighborhoods;

import dimesweeper.INeighborhood;
import dimesweeper.PositionSet;
import dimesweeper.positions.Position;
import java.util.LinkedList;

/**
 *
 * @author sofias.
 */
public class Leap implements INeighborhood
{
	protected final DropList pattern = new DropList ();

	public Leap () {}
	public Leap (LinkedList <Integer> pattern) 
	{ 
		this.pattern.addAll (pattern); 
	}
	
	@Override
	public PositionSet getNeighborPositions (Position pos, int radius)
	{
		PositionSet result = new PositionSet ();
		
		if (pos.isEmpty ()) result.add (Position.NIL);
		else if (radius > 1)
			for (int i = 1; i <= radius; i++)
			{
				Leap newLeap = new Leap (pattern.scale (i));
				for (Position scaledPositions : newLeap.getNeighborPositions (pos, 1) )
					result.add (scaledPositions);
			}
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
					result.add (subposition.prepend (pos.getHead () + displacement));
					result.add (subposition.prepend (pos.getHead () - displacement));
				}
			}
		}
		return result;
	}
	
	public class DropList extends LinkedList <Integer>
	{
		DropList drop (Integer toDrop)
		{
			DropList result = new DropList ();
			boolean skipped = false;
			for (Integer elements : this)
				if (toDrop == elements && !skipped) skipped = true;
				else result.add (elements);
			return result;
		}
		
		DropList scale (Integer multiplier)
		{
			DropList result = new DropList ();
			for (Integer elements : this) result.add (elements * multiplier);
			return result;
		}
	}
}
