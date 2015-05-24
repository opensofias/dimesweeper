package dimesweeper.warps;

import dimesweeper.Game;
import dimesweeper.IWarp;
import dimesweeper.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by David on 24.05.2015.
 */
public class Torus implements IWarp {
    public final static Torus instance = new Torus();

    private Torus () {}

    @Override
    public Set<Position> applyWarp (Set<Position> positions, Game game) {
        Iterator<Position> positionIterator = positions.iterator();
        ArrayList<Integer> dimensionSizes = game.fieldSize;

        positions: while (positionIterator.hasNext()) {
            Position pos = positionIterator.next();
            for (int iCoord = 0; iCoord < pos.size(); iCoord++) {
                int coord = pos.get(iCoord);
                int max = dimensionSizes.get(iCoord);

                if (coord < 0 || coord >= max) {
                    pos.set(iCoord, (coord + max) % max);
                }
            }
        }

        return positions;
    }
}
