/*
 * released to the public domain. see UNLICENSE.txt
 */
package dimesweeper;

import dimesweeper.neighborhoods.Diagonal;
import dimesweeper.neighborhoods.Orthogonal;
import dimesweeper.neighborhoods.Square;
import dimesweeper.neighborhoods.Knight;
import dimesweeper.positions.Position;
import dimesweeper.wraps.Non;
import dimesweeper.wraps.ReflectCell;
import dimesweeper.wraps.ReflectEdge;
import dimesweeper.wraps.Torus;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sofias.
 */
public class Game extends JFrame
{
    public enum NeighboorhoodType { SQUARE, ORTHOGONAL, DIAGONAL, KNIGHT }
    public enum NeighboorhoodWrap { NO, TORUS, REFLECT_EDGE, REFLECT_CELL }

	private static final long serialVersionUID = 1L;

	private final Integer mineCount;
	public MineSet mines;

	private Integer flagCount;
	public final HashSet<Position> flags;

	public Integer revealedCount = 0;
	private Integer boxletCount = 1;

	public final INeighborhood neighborhoodType;
	public final Integer neighborhoodRadius;
	public final IWrap neighborhoodWrap;

	public final ArrayList<Integer> fieldSize;

	public final FieldRow field;

	public boolean firstClick, hints;

	public Game (Integer fieldX, Integer fieldY, Integer mineCount) {
		this (construct2D (fieldX, fieldY), mineCount, NeighboorhoodType.SQUARE, 1, NeighboorhoodWrap.NO);
	}

	private static ArrayList<Integer> construct2D (Integer x, Integer y) {
		ArrayList<Integer> result = new ArrayList<> ();
		result.add (x);
		result.add (y);
		return result;
	}

	public Game (ArrayList<Integer> fieldSize, Integer mineCount) {
		this (fieldSize, mineCount, NeighboorhoodType.SQUARE, 1, NeighboorhoodWrap.NO);
	}

	@SuppressWarnings("unchecked")
	public Game (ArrayList<Integer> fieldSize, Integer mineCount, NeighboorhoodType neighborhoodType, Integer neighborhoodRadius, NeighboorhoodWrap neighborhoodWrap) {
		hints = true;
		firstClick = true;

		this.fieldSize = fieldSize;
        this.mineCount = mineCount;
        this.neighborhoodRadius = neighborhoodRadius;

        switch (neighborhoodType) {
            case SQUARE: this.neighborhoodType = Square.instance; break;
            case ORTHOGONAL: this.neighborhoodType = Orthogonal.instance; break;
            case DIAGONAL: this.neighborhoodType = Diagonal.instance; break;
			case KNIGHT: this.neighborhoodType = Knight.instance; break;
            default:
                throw new RuntimeException ("Unimplemented neighborhood type");
        }

        switch (neighborhoodWrap) {
            case NO: this.neighborhoodWrap = Non.instance; break;
            case TORUS: this.neighborhoodWrap = Torus.instance; break;
			case REFLECT_EDGE: this.neighborhoodWrap = ReflectEdge.instance; break;
			case REFLECT_CELL: this.neighborhoodWrap = ReflectCell.instance; break;
            default:
                throw new RuntimeException ("Unimplemented wrap type");
        }

		flags = new HashSet <> ();
		
		field = new FieldRow (fieldSize, Position.NIL, this);

		boxletCount = countBoxlets (this.fieldSize);

		add (field);
		pack ();
		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		setVisible (true);

		checkWon ();
	}

	public static Integer countBoxlets (ArrayList<Integer> f) {
		Integer result = 1;
		for (Integer n : f)
			result = result * n;
		return result;
	}

	public final void firstClick (Position click) {
		mines = new MineSet (mineCount, fieldSize, click);
		this.firstClick = false;
	}

	public Integer flagsLeft () {
		return mineCount - flagCount;
	}

	public Boxlet getBoxlet (Position pos) {
		return field.getBoxlet (pos);
	}

	public final void end () {
		hints = false;
		for (Position mine : mines) {
			if (flags.contains (mine))
				getBoxlet (mine).setBackground (Color.green);
			else
				getBoxlet (mine).setText ("M");
		}
		for (Position flag : flags) {
			if (!mines.contains (flag)) getBoxlet (flag).setBackground (Color.red);
		}
	}

	public final void lost (Boxlet aThis) {
		end ();
		aThis.setBackground (Color.red);
		JOptionPane.showMessageDialog (this, "you lost.", "guess what?", JOptionPane.INFORMATION_MESSAGE);
	}

	public final void checkWon () {
		if (revealedCount + mineCount == boxletCount) won ();
	}

	public final void won () {
		end ();
		JOptionPane.showMessageDialog (this, "you won." + (mineCount == 0 ? "\nfor sure." : ""), "congraz", JOptionPane.INFORMATION_MESSAGE);
	}

	public final Set<Position> findNeighbors (Position position) {
		Set<Position> neighbors;
		if (neighborhoodType != null) {
			neighbors = neighborhoodType.getNeighborPositions (position, neighborhoodRadius);
		} else {
			return new HashSet<> ();
		}
		if (neighborhoodWrap != null) {
			neighbors = neighborhoodWrap.applyWrap (neighbors, this);
		}

		neighbors.removeIf (neighbor ->
				neighbor.equals (position)
		);

		return neighbors;
	}
}
