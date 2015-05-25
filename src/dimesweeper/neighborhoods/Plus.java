package dimesweeper.neighborhoods;

import dimesweeper.INeighborhood;
import dimesweeper.positions.Position;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by EDave on 24.05.2015.
 */
public class Plus implements INeighborhood {
	public final static Plus instance = new Plus ();

	private Plus () {
	}

	@Override
	public Set<Position> getNeighborPositions (Position pos, int radius) {
		Set<Position> ret = new HashSet<> ();
		int currentCoord = pos.getHead ();
		Position subcoordinates = pos.getTail ();

		for (int delta = -radius; delta <= radius; delta++) {
			if (delta == 0) continue;
			ret.add (subcoordinates.prepend (currentCoord + delta));
		}

		if (!subcoordinates.isEmpty ()) {
			for (Position subposition : getNeighborPositions (subcoordinates, radius)) {
				ret.add (subposition.prepend (currentCoord));
			}
		}

		return ret;
	}
}
