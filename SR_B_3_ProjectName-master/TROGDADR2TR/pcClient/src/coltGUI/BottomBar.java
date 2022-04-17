package coltGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import com.trogdadr2tr.resources.Building;

import com.trogdadr2tr.game.ClientGameLogic;

public class BottomBar extends JPanel
{

	/** yeah java */
	private static final long serialVersionUID = 1L;

	private ClientGameLogic clientActor;

	private JPanel controlsWindow;

	// private JPanel statsWindow;

	// private JPanel otherWindow;

	// private int buttonSize;

	public BottomBar(ClientGameLogic clientActor)
	{
		// buttonSize = 20;
		this.clientActor = clientActor;
	}

	public void updateControls()
	{
		// get rid of all the old components
		removeAll();

		if (clientActor.selected == null)
		{
			// TODO - draw building creation toolbar
			// Icon icon;
			ArrayList<JButton> buttons = new ArrayList<JButton>();

			buttons.add(new JButton("Wood Generator"));
			buttons.add(new JButton("Food Generator"));
			buttons.add(new JButton("Gold Generator"));
			buttons.add(new JButton("Stone Generator"));
			buttons.add(new JButton("Garrison"));
			buttons.add(new JButton("Long Range Defence"));
			buttons.add(new JButton("Short Range Defence"));
			buttons.add(new JButton("Unit Spawner"));
			buttons.add(new JButton("Vertical Wall"));
			buttons.add(new JButton("Horizontal Wall"));
			buttons.add(new JButton("Base"));

			controlsWindow = new JPanel();
			add(controlsWindow);
			for (JButton button : buttons)
			{
				controlsWindow.add(button);

				button.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e)
					{
						clientActor.selected = "Place " + button.getText();
					}

				});
			}
			controlsWindow.setBorder(new LineBorder(Color.BLACK));
			controlsWindow.setLayout(new GridLayout(5, 5));

			SpringLayout layout = new SpringLayout();

			layout.putConstraint(SpringLayout.NORTH, controlsWindow,
					10, SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.WEST, controlsWindow,
					10, SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.SOUTH, this,
					10, SpringLayout.SOUTH, controlsWindow);

			setLayout(layout);
			// for (JButton b : buttons)
			// {
			// System.out.println(b.getText()+
			// b.getLocation());
			// }

			return;
		}

		if (clientActor.selected instanceof Building)
		{
			Building building = (Building) clientActor.selected;
			JLabel toAdd = new JLabel(building.getName());
			Font font = new Font("Colt's", Font.BOLD, 20);
			toAdd.setFont(font);
			add(toAdd);

			// TODO - display building controls

			switch (building.getName())
			{
				case "Base":
					break;
				case "Wood Generator":
					break;
				case "Food Generator":
					break;
				case "Gold Generator":
					break;
				case "Stone Generator":
					break;
				case "Garrison":
					break;
				case "Long Range Defense":
					break;
				case "Short Range Defense":
					break;
				case "Spawner":
					break;
				case "Wall Verticle":
					break;
				case "Wall Horizontal":
					break;
				default:
					break;
			}
		}

	}

}
