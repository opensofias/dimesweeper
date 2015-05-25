package dimesweeper.neighborhoods;

import dimesweeper.INeighborhood;
import dimesweeper.positions.Position;
import dimesweeper.positions.PositionBuilder;

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

		for (int dim = 0; dim < pos.getLength (); dim++) {
			for (int delta = -radius; delta <= radius; delta++) {
				if (delta == 0) continue;
				PositionBuilder newPos = new PositionBuilder (pos);
				newPos.set (dim, newPos.get (dim) + delta);
				ret.add (newPos.export ());
			}
		}
		return ret;
	}
}
