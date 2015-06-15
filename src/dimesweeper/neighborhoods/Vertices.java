package dimesweeper.neighborhoods;

import dimesweeper.INeighborhood;
import dimesweeper.PositionSet;
import dimesweeper.positions.Position;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by EDave on 24.05.2015.
 */

public class Vertices implements INeighborhood {
	public final static Vertices instance = new Vertices ();

	private Vertices () {
	}

	@Override
	public PositionSet getNeighborPositions (Position pos, int radius) {
		PositionSet positions = new PositionSet ();
		for (; radius >= 1; radius--)
			positions.addAll (getNeighbors (pos, radius));
		return positions;
	}

	public Set<Position> getNeighbors (Position pos, int radius) {
		Set<Position> ret = new HashSet<> ();
		if (pos.isEmpty ()) { ret.add (Position.NIL); }
		else
		{
			int coord = pos.getHead ();
			Position tailCoords = pos.getTail ();
			for (Position subposition : getNeighbors (tailCoords, radius)) {
				ret.add (subposition.prepend (coord + radius));
				ret.add (subposition.prepend (coord - radius));
			}
		}
		return ret;
	}

}
