package dimesweeper.neighborhoods;

import dimesweeper.INeighborhood;
import dimesweeper.Position;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by David on 24.05.2015.
 */
public class Plus implements INeighborhood {
    public final static Plus instance = new Plus();

    private Plus () {}

    @Override
    public Set<Position> getNeighborPositions (Position pos, int radius) {
        Set<Position> ret = new HashSet<>();

        for (int dim = 0; dim < pos.size(); dim++) {
            for (int delta = -radius; delta <= radius; delta++) {
                if (delta == 0) continue;
                Position newPos = (Position) pos.clone();
                newPos.set(dim, newPos.get(dim) + delta);
                ret.add(newPos);
            }
        }
        return ret;
    }
}
