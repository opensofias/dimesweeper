/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dimesweeper.neighborhoods;

import dimesweeper.INeighborhood;

/**
 *
 * @author sofias
 */
public class UltraKnight extends Leap implements INeighborhood
{
	public final static UltraKnight instance = new UltraKnight ();

	public UltraKnight () {
		super();
		pattern.add (1);
		pattern.add (2);
		pattern.add (4);
	}
}
