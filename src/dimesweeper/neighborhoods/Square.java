package dimesweeper.neighborhoods;

import dimesweeper.INeighborhood;
import dimesweeper.Position;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by David on 24.05.2015.
 */
public class Square implements INeighborhood {
    public final static Square instance = new Square();

    private Square () {}

    @Override
    public List<Position> getNeighborPositions (Position pos, int radius) {
        int currentCoordinate = pos.removeFirst();
        List<Position> ret = new LinkedList<>();

        if (pos.size() == 0) { /* base case */
            for (int i = -radius; i <= radius; i++) {
                Position newPos = new Position();
                newPos.add(currentCoordinate + i);
                ret.add(newPos);
            }
        } else {
            List<Position> subpositions = getNeighborPositions(pos, radius); // we took out the head from pos when we got currentCoordinate
            for (Position subposition : subpositions) {
                for (int i = -radius; i <= radius; i++) {
                    Position newPos = (Position)subposition.clone();
                    newPos.addFirst(currentCoordinate + i);
                    ret.add(newPos);
                }
            }
        }
        return ret;
    }
}
