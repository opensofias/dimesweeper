package dimesweeper;

import dimesweeper.positions.Position;

import java.util.Set;

/**
 * Created by EDave on 24.05.2015.
 */
public interface INeighborhood {
	Set<Position> getNeighborPositions (Position pos, int radius);
}
