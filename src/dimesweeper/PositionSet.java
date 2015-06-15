/*
 * released to the public domain. see UNLICENSE.txt
 */
package dimesweeper;

import dimesweeper.positions.Position;
import dimesweeper.positions.PositionBuilder;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author sofias.
 */
public class PositionSet extends HashSet<Position> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param mineCount
	 * @param fieldSize
	 * @param firstMove
	 */

	public PositionSet () {};
	
	public void fillRandomlyWithout (Integer mineCount, ArrayList<Integer> fieldSize, Position firstMove)
	{
		add (firstMove);
		fillRandomly (mineCount, fieldSize);
		remove (firstMove);
	}

	public void fillRandomly (int mineCount, ArrayList<Integer> fieldsize) {
		while (size () < mineCount) {
			PositionBuilder mine = new PositionBuilder ();

			fieldsize.stream ().forEach ((dimension) ->
				mine.add ((int) (dimension * Math.random ())));

			add (mine.export ());
		}
	}
}
