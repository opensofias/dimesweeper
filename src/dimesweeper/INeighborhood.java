package dimesweeper;

import java.util.Set;

/**
 * Created by David on 24.05.2015.
 */
public interface INeighborhood {
    public Set<Position> getNeighborPositions (Position pos, int radius);
}
