package com.trogdadr2tr.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.trogdadr2tr.database.DatabaseConnector;

/**
 * Contains the View Selections which are not implemented yet. Also contains the save and quit options.
 * Text can be entered into the field and the save button can be pressed to submit the map.
 * @author Nick Schmidt
 *
 */
public class ViewSelector
{

	/**
	 * Builds a ViewSelector with the given parameters.
	 * @param screen The MapEditorGUI the ViewSelector should be built on.
	 * @param x The x position of the upper left corner.
	 * @param y The y position of the upper left corner.
	 * @param width The width of the ViewSelector.
	 * @param height The height of the ViewSelector.
	 */
	public ViewSelector(MapEditorGUI screen, int x, int y, int width, int height)
	{
		
		JPanel viewContainer = new JPanel();
		viewContainer.setBounds(x, y, width, height);
		JLabel label = new JLabel("Views:");
		viewContainer.setLayout(new BoxLayout(viewContainer, BoxLayout.LINE_AXIS));
		viewContainer.add(label);
		viewContainer.setBorder(BorderFactory.createLineBorder(Color.black));

		JCheckBox chinButton = new JCheckBox("Chin");
		viewContainer.add(chinButton);

		screen.getWindowBuilder().add(viewContainer);
		
		JTextField text = new JTextField();

		Dimension maxSize = new Dimension(100, 30);

		text.setMaximumSize(maxSize);
//		text.setSize(200, 50);

		viewContainer.add(text);

		class saveActionListener implements ActionListener

		{

			@Override

			public void actionPerformed(ActionEvent e)

			{
				switch (e.getActionCommand())
				{
					case "Save":
						try
						{
							DatabaseConnector db = new DatabaseConnector();
							db.submitMap(screen.getMap(), text.getText());
						}
						catch (SQLException t)
						{
							System.out.println(t);
						}
					case "Quit":
						screen.getWindowBuilder().buildMainScreen();
				}
			}
		}

		JButton button = new JButton("Save");
		JButton button2 = new JButton("Quit");

		button2.addActionListener(new saveActionListener());
		button.addActionListener(new saveActionListener());

		viewContainer.add(button);
		viewContainer.add(button2);
	}
}
