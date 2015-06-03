package dimesweeper.neighborhoods;

import dimesweeper.INeighborhood;
import dimesweeper.positions.Position;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by EDave on 24.05.2015.
 */
public class Orthogonal implements INeighborhood {
	public final static Orthogonal instance = new Orthogonal ();

	private Orthogonal () {
	}

	@Override
	public Set<Position> getNeighborPositions (Position pos, int radius) {
		Set<Position> ret = new HashSet<> ();
		int currentHead = pos.getHead ();
		Position subcoordinates = pos.getTail ();

		for (int delta = -radius; delta <= radius; delta++) {
			if (delta == 0) continue;
			ret.add (subcoordinates.prepend (currentHead + delta));
		}

		if (!subcoordinates.isEmpty ()) {
			for (Position subposition : getNeighborPositions (subcoordinates, radius)) {
				ret.add (subposition.prepend (currentHead));
			}
		}

		return ret;
	}
}
