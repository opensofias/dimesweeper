package dimesweeper;

import java.util.Set;

/**
 * Created by EDave on 24.05.2015.
 */
public interface IWrap {
    public Set<Position> applyWrap (Set<Position> positions, Game game);
}
