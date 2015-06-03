/*
 * released to the public domain. see UNLICENSE.txt
 */
package dimesweeper;

import dimesweeper.positions.Position;
import dimesweeper.positions.PositionBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author sofias.
 */
public class FieldRow extends JPanel {
	private static final long serialVersionUID = 1L;

	private final Position position;
	private final ArrayList<Integer> subFieldSize;

	private Boxlet boxlet = null;

	public FieldRow (ArrayList<Integer> fieldSize, Position pos, Game game) {
		this.position = pos;
		this.subFieldSize = fieldSize;

		if (subFieldSize.size () == 0) {
			setLayout (new GridLayout (1, 1));
			boxlet = new Boxlet (position, game);
			add (boxlet);
		} else {
			@SuppressWarnings("unchecked")
			ArrayList<Integer> subFieldSize = (ArrayList<Integer>) this.subFieldSize.clone ();
			subFieldSize.remove (0);
			Integer spacer = ((this.subFieldSize.size () - 1) / 2) * 4;
			if (this.subFieldSize.size () % 2 == 1) {
				setLayout (new GridLayout (1, this.subFieldSize.get (0), spacer, spacer));
			} else {
				setLayout (new GridLayout (this.subFieldSize.get (0), 1, spacer, spacer));
			}

			for (int index = 0; index < this.subFieldSize.get (0); index++) {
				@SuppressWarnings("unchecked")
				PositionBuilder subFieldPosition = new PositionBuilder (position);
				subFieldPosition.add (index);
				add (new FieldRow (subFieldSize, subFieldPosition.export (), game));
			}
		}
	}

	public Boxlet getBoxlet (Position pos) {
		if (subFieldSize.size () == 0)
			return boxlet;
		else {
			return ((FieldRow) getComponent (pos.getHead ())).getBoxlet (pos.getTail ());
		}
	}
}
