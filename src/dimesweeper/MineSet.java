/*
 * released to the public domain. see UNLICENSE.txt
 */
package dimesweeper;

import dimesweeper.positions.Position;
import dimesweeper.positions.PositionBuilder;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author S.Bachmann
 */
public class MineSet extends HashSet<Position> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param mineCount
	 * @param fieldSize
	 * @param firstMove
	 */

	public MineSet (Integer mineCount, ArrayList<Integer> fieldSize, Position firstMove) {
		add (firstMove);
		buildMineSet (mineCount, fieldSize);
		remove (firstMove);
	}

	/**
	 * @param mineCount
	 * @param fieldsize
	 */
	public MineSet (int mineCount, ArrayList<Integer> fieldsize) {
		buildMineSet (mineCount, fieldsize);
	}

	private void buildMineSet (int mineCount, ArrayList<Integer> fieldsize) {
		while (size () < mineCount) {
			PositionBuilder mine = new PositionBuilder ();

			fieldsize.stream ().forEach ((dimension) ->
				mine.add ((int) (dimension * Math.random ())));

			add (mine.export ());
		}
	}
}
