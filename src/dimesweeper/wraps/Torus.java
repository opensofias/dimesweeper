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
public class Torus implements IWrap {
	public final static Torus instance = new Torus ();

	private Torus () {
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
					pb.set (iCoord, (coord + max) % max);
				}
			}
			newPositions.add (pb.export ());
		}

		return newPositions;
	}
}
