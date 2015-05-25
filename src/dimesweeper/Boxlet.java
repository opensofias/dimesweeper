/*
 * released to the public domain. see UNLICENSE.txt
 */
package dimesweeper;

import dimesweeper.positions.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * @author S.Bachmann
 */
public class Boxlet extends JToggleButton implements MouseListener {
	private static final long serialVersionUID = 1L;

	public Position position;
	public final Game game;

	private java.util.List<Boxlet> neighbors;

	public Boxlet (Position position, Game game) {
		this.game = game;
		this.position = position;
		setMinimumSize (new Dimension (64, 64));
		addMouseListener (this);
	}

	public java.util.List<Boxlet> getNeighbors () {
		if (neighbors == null) {
			neighbors = new ArrayList<> ();
			for (Position neighborPos : game.findNeighbors (position)) {
				neighbors.add (game.getBoxlet (neighborPos));
			}
		}
		return neighbors;
	}

	public Integer getNeighborMines () {
		Integer counter = 0;
		for (Boxlet neigh : this.getNeighbors ()) {
			if (neigh.getMine ()) counter++;
		}
		return counter;
	}

	public boolean getMine () {
		return game.mines.contains (position);
	}

	public boolean getFlag () {
		return game.flags.contains (position);
	}

	public void setFlag (boolean f) {
		if (f) {
			game.flags.add (position);
			setText ("F");
		} else {
			game.flags.remove (position);
			setText ("");
		}
	}

	public void reveal () {
		if (!getFlag ()) {
			setSelected (true);
			setEnabled (false);
			if (getMine ()) game.lost (this);
			else if (getNeighborMines () > 0) setText (getNeighborMines ().toString ());
			else for (Boxlet n : getNeighbors ()) if (n.isEnabled ()) n.reveal ();
			game.revealedCount++;
			game.checkWon ();
		}
	}

	@Override
	public void mouseClicked (MouseEvent e) {
		switch (e.getButton ()) {
			case 1:
				if (game.firstClick) game.firstClick (position);
				if (getFlag ()) setFlag (false);
				else if (isEnabled ()) reveal ();
				break;
			case 3:
				if (isEnabled ()) setFlag (!getFlag ());
		}
		if (e.getButton () == 1 && e.getClickCount () == 2) {
			for (Boxlet n : getNeighbors ()) if (n.isEnabled ()) n.reveal ();
		}

	}

	@Override
	public void mousePressed (MouseEvent e) {
	}

	@Override
	public void mouseReleased (MouseEvent e) {
	}

	@Override
	public void mouseEntered (MouseEvent e) {
		if (game.hints) for (Boxlet n : getNeighbors ()) n.setBackground (Color.yellow);
	}

	@Override
	public void mouseExited (MouseEvent e) {
		for (Boxlet n : getNeighbors ()) if (n.getBackground () == Color.yellow) n.setBackground (null);
	}
}
