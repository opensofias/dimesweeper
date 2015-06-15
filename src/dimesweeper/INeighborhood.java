package dimesweeper;

import dimesweeper.positions.Position;

/**
 * Created by EDave on 24.05.2015.
 */
public interface INeighborhood {
	PositionSet getNeighborPositions (Position pos, int radius);
}
