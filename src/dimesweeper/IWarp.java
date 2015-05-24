package dimesweeper;

import java.util.List;

/**
 * Created by David on 24.05.2015.
 */
public interface IWarp {
    public List<Position> applyWarp (List<Position> positions, Game game);
}
