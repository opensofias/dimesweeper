/*
 * released to the public domain. see UNLICENSE.txt
 */
package dimesweeper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Deque;

/**
 * @author S.Bachmann
 */
public class FieldRow extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private final Position position;
	private final ArrayList <Integer> subFieldSize;
	
	private Boxlet boxlet = null;
		
	public FieldRow (ArrayList<Integer> fieldSize, Position pos, Game game)
	{
		this.position = pos;
		this.subFieldSize = fieldSize;
		
		if (subFieldSize.size () == 0 )
		{
			setLayout (new GridLayout(1, 1));
			boxlet = new Boxlet (position, game);
			add (boxlet);
		}
		else
		{
			@SuppressWarnings("unchecked")
            ArrayList <Integer> subFieldSize = (ArrayList <Integer>) this.subFieldSize.clone();
			subFieldSize.remove(0);
			Integer spacer = ((this.subFieldSize.size() - 1) / 2) * 4;
			if (this.subFieldSize.size() % 2 == 1)
			{ setLayout (new GridLayout(1, this.subFieldSize.get(0), spacer, spacer)); }
			else
			{ setLayout (new GridLayout(this.subFieldSize.get(0), 1, spacer, spacer)); }

			for (int index = 0; index < this.subFieldSize.get(0); index ++)
			{
				@SuppressWarnings("unchecked")
                Position subFieldPosition = (Position) position.clone ();
				subFieldPosition.addLast(index);
				add (new FieldRow (subFieldSize, subFieldPosition, game));
			}
		}
	}

	public Boxlet getBoxlet (Deque<Integer> coordinates)
	{
		if (subFieldSize.size () == 0 )
			return boxlet;
		else
		{
			Integer c = coordinates.removeFirst ();
			return ((FieldRow) getComponent (c)).getBoxlet(coordinates);
		}
	}
}
