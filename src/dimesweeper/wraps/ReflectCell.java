package dimesweeper.wraps;

import dimesweeper.Game;
import dimesweeper.IWrap;
import dimesweeper.positions.Position;
import dimesweeper.positions.PositionBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by EDave on 24.05.2015.
 */
public class ReflectCell implements IWrap {
	public final static ReflectCell instance = new ReflectCell ();

	private ReflectCell () {
	}

	@Override
	public Set<Position> applyWrap (Set<Position> positions, Game game) {
		Iterator<Position> positionIterator = positions.iterator ();
		ArrayList<Integer> dimensionSizes = game.fieldSize;
		Set<Position> newPositions = new HashSet<> ();

		for (Position pos : positions) {
			PositionBuilder pb = new PositionBuilder (pos);
			for (int iCoord = 0; iCoord < pb.size (); iCoord++) {
				int coord = pb.get (iCoord);
				int max = dimensionSizes.get (iCoord);

				if (coord < 0 || coord >= max) {
					pb.set (iCoord, reflect (coord, max));
				}
			}
			newPositions.add (pb.export ());
		}

		return newPositions;
	}

	public int reflect (int coord, int max) {
		if ((coord / max) % 2 == 1) {
			return max - (coord % max) - 2;
		} else {
			return Math.abs (coord % max);
		}
	}
}
