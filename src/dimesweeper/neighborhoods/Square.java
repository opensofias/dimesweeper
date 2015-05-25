package dimesweeper.neighborhoods;

import dimesweeper.INeighborhood;
import dimesweeper.positions.Position;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by EDave on 24.05.2015.
 */
public class Square implements INeighborhood {
	public final static Square instance = new Square ();

	private Square () {
	}

	@Override
	public Set<Position> getNeighborPositions (Position pos, int radius) {
		int currentCoordinate = pos.getHead ();
		Set<Position> ret = new HashSet<> ();

		if (pos.getLength () <= 1) { /* base case */
			for (int i = -radius; i <= radius; i++) {
				ret.add (Position.create (currentCoordinate + 1));
			}
		} else {
			Set<Position> subpositions = getNeighborPositions (pos.getTail (), radius); // we took out the head from pos when we got currentCoordinate
			for (Position subposition : subpositions) {
				for (int i = -radius; i <= radius; i++) {
					ret.add (subposition.prepend (currentCoordinate + i));
				}
			}
		}

		return ret;
	}
}
