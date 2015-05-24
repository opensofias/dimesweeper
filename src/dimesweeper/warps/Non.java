package dimesweeper.warps;

import dimesweeper.Game;
import dimesweeper.IWarp;
import dimesweeper.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by David on 24.05.2015.
 */
public class Non implements IWarp {
    public final static Non instance = new Non();

    private Non () {}

    @Override
    public List<Position> applyWarp (List<Position> positions, Game game) {
        Iterator<Position> positionIterator = positions.iterator();
        ArrayList<Integer> dimensionSizes = game.fieldSize;

        positions: while (positionIterator.hasNext()) {
            Position pos = positionIterator.next();
            for (int iCoord = 0; iCoord < pos.size(); iCoord++) {
                int coord = pos.get(iCoord);
                int max = dimensionSizes.get(iCoord);

                if (coord < 0 || coord >= max) {
                    positionIterator.remove();
                    continue positions;
                }
            }
        }

        return positions;
    }
}
