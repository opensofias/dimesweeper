package dimesweeper;

import java.util.Set;

/**
 * Created by David on 24.05.2015.
 */
public interface IWarp {
    public Set<Position> applyWarp (Set<Position> positions, Game game);
}
