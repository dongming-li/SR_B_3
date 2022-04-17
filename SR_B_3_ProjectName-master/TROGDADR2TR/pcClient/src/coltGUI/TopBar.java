package coltGUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

// import com.trogdadr2tr.resources.*;

public class TopBar extends JPanel
{

	private JLabel woodLabel;

	private JLabel foodLabel;

	private JLabel goldLabel;

	private JLabel stoneLabel;

	private JLabel unitCapLabel;

	private JButton exit;

	/** yeah java */
	private static final long serialVersionUID = 1L;

	// private GameMap gameMap;

	public TopBar()
	{
		setBorder(new LineBorder(Color.RED));
		buildResourceBar();

		SpringLayout topBarLayout = new SpringLayout();

		// woodLabel center left in this
		topBarLayout.putConstraint(SpringLayout.NORTH, woodLabel, 0,
				SpringLayout.NORTH, this);
		topBarLayout.putConstraint(SpringLayout.WEST, woodLabel, 10,
				SpringLayout.WEST, this);

		// foodLabel right of woodLabel
		topBarLayout.putConstraint(SpringLayout.NORTH, foodLabel, 0,
				SpringLayout.NORTH, this);
		topBarLayout.putConstraint(SpringLayout.WEST, foodLabel, 10,
				SpringLayout.EAST, woodLabel);

		// goldLabel right of foodLabel
		topBarLayout.putConstraint(SpringLayout.NORTH, goldLabel, 0,
				SpringLayout.NORTH, this);
		topBarLayout.putConstraint(SpringLayout.WEST, goldLabel, 10,
				SpringLayout.EAST, foodLabel);

		// stoneLabel right of goldLabel
		topBarLayout.putConstraint(SpringLayout.NORTH, stoneLabel, 0,
				SpringLayout.NORTH, this);
		topBarLayout.putConstraint(SpringLayout.WEST, stoneLabel, 10,
				SpringLayout.EAST, goldLabel);

		// unitCapLabel right of stoneLabel
		topBarLayout.putConstraint(SpringLayout.NORTH, unitCapLabel, 0,
				SpringLayout.NORTH, this);
		topBarLayout.putConstraint(SpringLayout.WEST, unitCapLabel, 10,
				SpringLayout.EAST, stoneLabel);

		// bind this to the bottom of woodLabel
		topBarLayout.putConstraint(SpringLayout.SOUTH, this, 10,
				SpringLayout.SOUTH, woodLabel);

		// exit in the far right corner
		topBarLayout.putConstraint(SpringLayout.NORTH, exit, 0, SpringLayout.NORTH, this);
		topBarLayout.putConstraint(SpringLayout.EAST, exit, 0, SpringLayout.EAST, this);

		setLayout(topBarLayout);
	}

	private void buildResourceBar()
	{
		woodLabel = new JLabel();
		add(woodLabel);
		foodLabel = new JLabel();
		add(foodLabel);
		goldLabel = new JLabel();
		add(goldLabel);
		stoneLabel = new JLabel();
		add(stoneLabel);
		unitCapLabel = new JLabel();
		add(unitCapLabel);
		exit = new JButton("Exit");
		exit.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(ABORT);
			}

		});
		add(exit);
	}

	public void updateResources(int wood, int food, int gold, int stone)
	{
		woodLabel.setText("wood: " + wood);
		foodLabel.setText("food: " + food);
		goldLabel.setText("gold: " + gold);
		stoneLabel.setText("stone: " + stone);
	}

	public void updateUnitCap(int unitCount, int unitCap)
	{
		unitCapLabel.setText("Unit Cap: " + unitCount + "/" + unitCap);
	}
}
