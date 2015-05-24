package dimesweeper;

import java.util.LinkedList;

/**
 * Created by David on 24.05.2015.
 */
public class Position extends LinkedList<Integer> {
    public boolean equals (Object o) {
        if (o instanceof Position) {
            Position other = (Position) o;
            if (this.size() != other.size()) return false;
            for (int i = 0; i < other.size(); i++) {
                if (!(other.get(i).equals(this.get(i))))
                    return false;
            }
            return true;
        } else {
            return false;
        }
    }
}
