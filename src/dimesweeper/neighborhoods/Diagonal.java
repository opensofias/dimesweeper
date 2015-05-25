package dimesweeper.neighborhoods;

import dimesweeper.INeighborhood;
import dimesweeper.positions.Position;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by EDave on 24.05.2015.
 */
public class Diagonal implements INeighborhood {
	public final static Diagonal instance = new Diagonal ();

	private Diagonal () {
	}

	@Override
	public Set<Position> getNeighborPositions (Position pos, int radius) {
		Set<Position> positions = new HashSet<> ();
		for (; radius >= 1; radius--)
			positions.addAll (getNeighbors (pos, radius));
		return positions;
	}

	public Set<Position> getNeighbors (Position pos, int radius) {
		Set<Position> ret = new HashSet<> ();
		if (pos.getLength () <= 1) {
			int coord = pos.getHead ();
			ret.add (Position.create (coord + radius));
			ret.add (Position.create (coord - radius));
		} else {
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
