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
public class ReflectEdge implements IWrap {
	public final static ReflectEdge instance = new ReflectEdge ();

	private ReflectEdge () {
	}

	@Override
	public Set<Position> applyWrap (Set<Position> positions, Game game) {
		Iterator<Position> positionIterator = positions.iterator ();
		ArrayList<Integer> dimensionSizes = game.fieldSize;
		Set<Position> newPositions = new HashSet<> ();

		positions:
		while (positionIterator.hasNext ()) {
			PositionBuilder pos = new PositionBuilder (positionIterator.next ());
			for (int iCoord = 0; iCoord < pos.size (); iCoord++) {
				int coord = pos.get (iCoord);
				int max = dimensionSizes.get (iCoord);

				if (coord < 0 || coord >= max) {
					pos.set (iCoord, reflect (coord, max));
				}
			}
			newPositions.add (pos.export ());
		}

		return newPositions;
	}

	public int reflect (int coord, int max) {
		if ((coord / max) % 2 == 1) {
			return max - (coord % max) - 1;
		} else {
			return Math.abs (coord % max) - 1;
		}
	}
}
