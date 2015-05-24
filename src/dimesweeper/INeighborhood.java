package dimesweeper;

import java.util.List;

/**
 * Created by David on 24.05.2015.
 */
public interface INeighborhood {
    public List<Position> getNeighborPositions (Position pos, int radius);
}
