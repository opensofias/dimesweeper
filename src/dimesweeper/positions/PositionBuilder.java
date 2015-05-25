package dimesweeper.positions;

import java.util.ArrayList;

/**
 * Created by EDave on 25.05.2015.
 */
public class PositionBuilder extends ArrayList<Integer> {
	public PositionBuilder () {
		super ();
	}

	public PositionBuilder (Position pos) {
		super ();
		while (!pos.isEmpty ()) {
			add (pos.getHead ());
			pos = pos.getTail ();
		}
	}

	public Position export () {
		Position ret = Position.NIL;
		for (int dim = size () - 1; dim >= 0; dim--) {
			ret = ret.prepend (get (dim));
		}
		return ret;
	}
}
