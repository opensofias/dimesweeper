/*
 * released to the public domain. see UNLICENSE.txt
 */
package dimesweeper;

import dimesweeper.Game.NeighboorhoodType;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

/**
 * @author sofias.
 */
public class Launcher extends JFrame {
	private static final long serialVersionUID = 1L;

	private static final Map<String, Game.NeighboorhoodType> neighboorhoodTypeNames;
	private static final Map<String, Game.NeighboorhoodWrap> neighboorhoodWrapNames;

	private final JSplitPane pnAll;

	private final JPanel pnOptions;

	private JSpinner spDimensions;
	private Integer dimensions;

	private final JButton btRun;
	private JSpinner spMines;
	private JSlider slMines;

	private final SpinnerNumberModel spModelMines;

	private final JSpinner spRadius;
	private final JComboBox<String> cbNeigs, cbWraps;

	private final DimsField dimsField;


	/**
	 * @param args the command line arguments
	 */
	public final static void main (String[] args) {
		final Launcher sweeper = new Launcher ();
	}

	public Launcher () {
		dimensions = 1;

		setTitle ("dimesweeper");
		setVisible (true);
		setDefaultCloseOperation (EXIT_ON_CLOSE);

		setContentPane (pnAll = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT, pnOptions = new JPanel (), this.dimsField = new DimsField ()));

		pnOptions.setLayout (new GridLayout (8, 1));

		pnOptions.add (new JLabel ("dimensions:"));
		pnOptions.add (spDimensions = new JSpinner (new SpinnerNumberModel (1, 1, 9000, 1)));
		spDimensions.addChangeListener ((ChangeEvent e) ->
		{
			dimensions = (Integer) spDimensions.getValue ();
			dimsField.update ();
		});

		pnOptions.setLayout (new GridLayout (12, 1));

		pnOptions.add (new JLabel ("mines:"));
		pnOptions.add (spMines = new JSpinner (spModelMines = new SpinnerNumberModel (0, 0, 0, 1)));

		spMines.addChangeListener ((ChangeEvent e) ->
			slMines.setValue ((int) spMines.getValue ()));

		pnOptions.add (slMines = new JSlider (0, 0, 0));
		slMines.addChangeListener ((ChangeEvent e) ->
			spMines.setValue (slMines.getValue ()));

		pnOptions.add (new JLabel ("neighborhood:"));
		Set<String> strings = neighboorhoodTypeNames.keySet ();
		pnOptions.add (cbNeigs = new JComboBox<> (strings.toArray (new String[strings.size ()])));

		pnOptions.add (new JLabel ("radius:"));
		pnOptions.add (spRadius = new JSpinner (new SpinnerNumberModel (1, 1, 9000, 1)));

		pnOptions.add (new JLabel ("wrapping:"));
		strings = neighboorhoodWrapNames.keySet ();
		pnOptions.add (cbWraps = new JComboBox<> (strings.toArray (new String[strings.size ()])));

		pnOptions.add (btRun = new JButton ("launch game"));
		btRun.addActionListener ((ActionEvent e) ->
			launch ());

		pack ();
	}

	public final void launch () {
		try {
			final Game game = new Game
				(
					new ArrayList<> (dimsField.getValue ()),
					(Integer) spMines.getValue (),
					neighboorhoodTypeNames.get (cbNeigs.getSelectedItem ()),
					(Integer) spRadius.getValue (),
					neighboorhoodWrapNames.get (cbWraps.getSelectedItem ())
				);
		} catch (Exception e) {
			e.printStackTrace ();
			JOptionPane.showMessageDialog (this, e, "oh noez!", JOptionPane.ERROR_MESSAGE);
		}
	}

	private class DimsField extends JPanel {
		private static final long serialVersionUID = 1L;
		public LinkedList<JSpinner> spinners;

		public DimsField () {
			this.spinners = new LinkedList<> ();
			update ();
		}

		public final void update () {
			if (spinners.size () < dimensions) {
				JSpinner s = new JSpinner (new SpinnerNumberModel (1, 1, 9000, 1));
				s.addChangeListener ((ChangeEvent e) ->
				{
					spModelMines.setMaximum (getVolume () - 1);
					slMines.setMaximum (getVolume () - 1);
				});
				spinners.add (s);
				add (s);
				update ();
			} else if (spinners.size () > dimensions) {
				remove (spinners.removeLast ());
				update ();
			} else {
				setLayout (new GridLayout (dimensions, 1));
				this.validate ();
			}
		}

		public LinkedList<Integer> getValue () {
			LinkedList<Integer> result = new LinkedList<> ();
			for (JSpinner s : spinners) result.add ((Integer) s.getValue ());
			return result;
		}

		public Integer getVolume () {
			Integer result = 1;
			for (JSpinner s : spinners) result *= (Integer) s.getValue ();
			return result;
		}
	}

    static {
        Map<String, Game.NeighboorhoodType> typeNames = new HashMap<>();
        typeNames.put("square", Game.NeighboorhoodType.SQUARE);
        typeNames.put("orthogonal", Game.NeighboorhoodType.ORTHOGONAL);
		typeNames.put("diagonal", Game.NeighboorhoodType.DIAGONAL);
		typeNames.put("triagonal", Game.NeighboorhoodType.TRIAGONAL);
		typeNames.put("quadragonal", Game.NeighboorhoodType.QUADRAGONAL);
        typeNames.put("vertices", Game.NeighboorhoodType.VERTICES);
		typeNames.put ("knight", NeighboorhoodType.KNIGHT);
		typeNames.put ("ultraknight", NeighboorhoodType.ULTRAKNIGHT);
        neighboorhoodTypeNames = Collections.unmodifiableMap(typeNames);

        Map<String, Game.NeighboorhoodWrap> typeWraps = new HashMap<>();
        typeWraps.put("no", Game.NeighboorhoodWrap.NO);
        typeWraps.put("torus", Game.NeighboorhoodWrap.TORUS);
        typeWraps.put("reflect on cell", Game.NeighboorhoodWrap.REFLECT_CELL);
		typeWraps.put("reflect on edge", Game.NeighboorhoodWrap.REFLECT_EDGE);
        neighboorhoodWrapNames = Collections.unmodifiableMap(typeWraps);
    }
}
