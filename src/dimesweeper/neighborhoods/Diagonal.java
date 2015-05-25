package dimesweeper.neighborhoods;

import dimesweeper.INeighborhood;
import dimesweeper.positions.Position;
import dimesweeper.positions.PositionBuilder;

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
		Set<Position> ret = new HashSet<> ();
		PositionBuilder pb = new PositionBuilder (pos);
		PositionBuilder newPos;

		for (int i = 1; i <= radius; i++) {
			Set<PositionBuilder> radiusPositions = new HashSet<> ();

			for (int dim = 0; dim < pos.getLength (); dim++) {
				int coord = pb.get (dim);
				if (dim == 0) {
					newPos = new PositionBuilder ();
					newPos.add (coord + i);
					radiusPositions.add (newPos);

					newPos = new PositionBuilder ();
					newPos.add (coord - i);
					radiusPositions.add (newPos);
				} else {
					Set<PositionBuilder> oldPositions = radiusPositions;
					radiusPositions = new HashSet<> ();

					for (PositionBuilder oldPosition : oldPositions) {
						newPos = (PositionBuilder) oldPosition.clone ();
						newPos.add (coord + i);
						radiusPositions.add (newPos);

						newPos = (PositionBuilder) oldPosition.clone ();
						newPos.add (coord - i);
						radiusPositions.add (newPos);
					}
				}
			}

			for (PositionBuilder radiusPosition : radiusPositions) {
				ret.add (radiusPosition.export ());
			}
		}

		return ret;
	}
}
