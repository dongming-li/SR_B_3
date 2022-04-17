package com.trogdadr2tr.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.trogdadr2tr.database.DatabaseConnector;
import com.trogdadr2tr.resources.Building;
import com.trogdadr2tr.resources.Unit;

/**
 * The ScenarioMiniScreen displays information about the clicked resource and allows for the Scenario related to the selected
 * resource to be edited.
 * @author Nick Schmidt
 *
 */
public class ScenarioMiniScreen extends JFrame
{

	/** yeah java */
	private static final long serialVersionUID = 1L;

	/**
	 * TheMapEditorGUI Tied the miniscreen.
	 */
	private MapEditorGUI mapEditorGUI;

	/**
	 * This
	 */
	private JPanel miniScreen;

	/**
	 * Builds the ScenarioMiniScreen on the MapEditorGUI with the given parameters.
	 * @param screen The screen the ScenarioMiniScreen should be built on.
	 * @param x The x coordinate of the upper left corner of the miniScreen.
	 * @param y THe y coordinate of the upper left corner of the miniScreen.
	 * @param width The width of the miniScreen.
	 * @param height The height of the miniScreen
	 */
	public ScenarioMiniScreen(MapEditorGUI screen, int x, int y, int width, int height)
	{
		mapEditorGUI = screen;
		JPanel miniScreen = new JPanel();
		miniScreen.setLayout(new BoxLayout(miniScreen, BoxLayout.LINE_AXIS));
		miniScreen.setBounds(x, y, width, height);
		miniScreen.setBorder(BorderFactory.createLineBorder(Color.black));
		miniScreen.setVisible(true);
		screen.getWindowBuilder().add(miniScreen);
//		JTextField text = new JTextField();
//
//		Dimension maxSize = new Dimension(100, 100);
//
//		text.setMaximumSize(maxSize);
//
//		miniScreen.add(text);
//
//		class saveActionListener implements ActionListener
//
//		{
//
//			@Override
//
//			public void actionPerformed(ActionEvent e)
//
//			{
//				switch (e.getActionCommand())
//				{
//					case "Save":
//						try
//						{
//							DatabaseConnector db = new DatabaseConnector();
//							db.submitMap(mapEditorGUI.getMap(), text.getText());
//						}
//						catch (SQLException t)
//						{
//							System.out.println(t);
//						}
//				}
//			}
//		}
//
//		JButton button = new JButton("Save");
//
//		button.addActionListener(new saveActionListener());
//
//		miniScreen.add(button);

		this.miniScreen = miniScreen;
	}

	/**
	 * Creates a MiniScreen for the clicked object. When this object is clicked its information will be displayed
	 * and a button can be pressed to edit the scenario for the given object.
	 * @param object
	 */
	public void editScenario(Object object)
	{
		if (object instanceof Building)
		{
			miniScreen.removeAll();
			miniScreen.add(createInformation((Building) object));
			class scenarioEditorScreenActionListener implements ActionListener
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					switch (e.getActionCommand())
					{
						case "Edit Scenario":
							mapEditorGUI.getWindowBuilder()
									.removeKeyListener(mapEditorGUI.getListener());
							// ScenarioEditor temp =
							new ScenarioEditor(mapEditorGUI);
					}
				}

			}
			JButton button;

			// make a new button
			// set the action command
			// add the button to buttons array

			button = new JButton("Edit Scenario");
			button.setActionCommand("Edit Scenario");
			button.setToolTipText("Edit Scenario");
			button.addActionListener(new scenarioEditorScreenActionListener());
			miniScreen.add(button);
			miniScreen.setVisible(false);
			miniScreen.setVisible(true);
		}
		else if (object.getClass().equals(new Unit("Cavalry").getClass()))
		{

		}
	}

	/**
	 * Creates the information for the given Building in the miniscreen as a table.
	 * @param building The building which should be converted into a table to be shown.
	 * @return The table of the information related to the object.
	 */
	private JPanel createInformation(Building building)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JTable Test;
		String[] names = {"t", "t"};
		String[][] data = new String[3][3];
		data[0][0] = "Health:";
		data[0][1] = Integer.toString(building.getHealth());
		data[1][0] = "Height:";
		data[1][1] = Integer.toString(building.getHeight());
		data[2][0] = "Width:";
		data[2][1] = Integer.toString(building.getWidth());
		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), building.getName(),
				TitledBorder.CENTER, TitledBorder.TOP));
		Test = new JTable(data, names)
		{

			/** yeah java */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		Test.setTableHeader(null);
		Test.setCellSelectionEnabled(false);
		Test.getColumnModel().getColumn(0).setMaxWidth(100);
		Test.getColumnModel().getColumn(1).setMaxWidth(100);
		Test.setRowHeight(25);
		tablePanel.add(Test);
		tablePanel.setName(building.getName());
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.PAGE_AXIS));
		panel.add(tablePanel);
		return panel;
	}

}
