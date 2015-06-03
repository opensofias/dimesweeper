/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dimesweeper.neighborhoods;

import dimesweeper.INeighborhood;
import dimesweeper.positions.Position;
import java.util.LinkedList;
import java.util.Set;

/**
 *
 * @author sofias
 */
public class Knight implements INeighborhood
{
	public final static Knight instance = new Knight ();
	
	private final LinkedList <Integer> leapList = new LinkedList<> ();
	private final Leap leap; // i tried to make it inherit from Leap, but that didn't seem to work

	public Knight ()
	{
		leapList.add (1); leapList.add (2);
		leap = new Leap (leapList);
	}

	@Override
	public Set<Position> getNeighborPositions (Position pos, int radius)
	{ return leap.getNeighborPositions (pos, radius); }

}
