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
public class Diagonal extends Leap implements INeighborhood
{
	public final static Diagonal instance = new Diagonal ();

	public Diagonal () {
		super();
		pattern.add(1); pattern.add(1);
	}
}
