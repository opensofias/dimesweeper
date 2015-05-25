package dimesweeper.neighborhoods;

import dimesweeper.INeighborhood;
import dimesweeper.Position;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by EDave on 24.05.2015.
 */
public class Diagonal implements INeighborhood {
    public final static Diagonal instance = new Diagonal();

    private Diagonal () {
    }

    @Override
    public Set<Position> getNeighborPositions (Position pos, int radius) {
        Set<Position> ret = new HashSet<>();
        Position newPos;

        for (int i = 1; i <= radius; i++) {
            Set<Position> radiusPositions = new HashSet<>();

            for (int dim = 0; dim < pos.size(); dim++) {
                int coord = pos.get(dim);
                if (dim == 0) {
                    newPos = new Position();
                    newPos.add(coord + i);
                    radiusPositions.add(newPos);

                    newPos = new Position();
                    newPos.add(coord - i);
                    radiusPositions.add(newPos);
                } else {
                    Set<Position> oldPositions = radiusPositions;
                    radiusPositions = new HashSet<>();

                    for (Position oldPosition : oldPositions) {
                        newPos = (Position) oldPosition.clone();
                        newPos.add(coord + i);
                        radiusPositions.add(newPos);

                        newPos = (Position) oldPosition.clone();
                        newPos.add(coord - i);
                        radiusPositions.add(newPos);
                    }
                }
            }

            ret.addAll(radiusPositions);
        }

        return ret;
    }
}
