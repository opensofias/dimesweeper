/*
 * released to the public domain. see UNLICENSE.txt
 */
package dimesweeper;

import java.awt.GridLayout;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 * @author S.Bachmann
 */
public class FieldRow extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private final LinkedList <Integer> position;
	private final LinkedList <Integer> subFieldSize;
	
	private Boxlet boxlet = null;
		
	public FieldRow (LinkedList <Integer> feldGr, LinkedList <Integer> pos, Game spiel)
	{
		this.position = pos;
		this.subFieldSize = feldGr;
		
		if (subFieldSize.size () == 0 )
		{
			setLayout (new GridLayout(1, 1));
			boxlet = new Boxlet (position, spiel);
			add (boxlet);
		}
		else
		{
			@SuppressWarnings("unchecked")
			LinkedList <Integer> subFeldGröße = (LinkedList <Integer>) subFieldSize.clone();
			subFeldGröße.removeFirst ();
			Integer spacer = ((subFieldSize.size () - 1) / 2) * 4;
			if (subFieldSize.size () % 2 == 1)
			{ setLayout (new GridLayout(1, subFieldSize.getFirst (), spacer, spacer)); }
			else
			{ setLayout (new GridLayout(subFieldSize.getFirst (), 1, spacer, spacer)); }

			for (int index = 0; index < subFieldSize.getFirst (); index ++)
			{
				@SuppressWarnings("unchecked")
				LinkedList <Integer> subFeldPosition = (LinkedList <Integer>) position.clone ();
				subFeldPosition.addLast (index);
				add (new FieldRow (subFeldGröße, subFeldPosition, spiel));
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
