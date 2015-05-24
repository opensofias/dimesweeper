/*
 * released to the public domain. see UNLICENSE.txt
 */
package dimesweeper;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * @author S.Bachmann
 */
public class FieldRow extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private final LinkedList <Integer> position;
	private final LinkedList <Integer> subFieldSize;
	
	private Boxlet boxlet = null;
		
	public FieldRow (LinkedList <Integer> fieldSize, LinkedList <Integer> pos, Game game)
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
			LinkedList <Integer> subFieldSize = (LinkedList <Integer>) this.subFieldSize.clone();
			subFieldSize.removeFirst();
			Integer spacer = ((this.subFieldSize.size() - 1) / 2) * 4;
			if (this.subFieldSize.size() % 2 == 1)
			{ setLayout (new GridLayout(1, this.subFieldSize.getFirst(), spacer, spacer)); }
			else
			{ setLayout (new GridLayout(this.subFieldSize.getFirst(), 1, spacer, spacer)); }

			for (int index = 0; index < this.subFieldSize.getFirst(); index ++)
			{
				@SuppressWarnings("unchecked")
				LinkedList <Integer> subFieldPosition = (LinkedList <Integer>) position.clone ();
				subFieldPosition.addLast(index);
				add (new FieldRow (subFieldSize, subFieldPosition, game));
			}
		}
	}

	public Boxlet getBoxlet (LinkedList <Integer> coordinates)
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
