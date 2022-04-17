package com.trogdadr2tr.GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import com.trogdadr2tr.resources.*;

/**
 * The ResourceNavigator is a multiple tabbed bar that contains all the available resources.
 * These resources will be displayed as tables with their stats.
 * These tables can be clicked and dragged to the map to place the respective resource.
 * @author Nick Schmidt
 *
 */
public class ResourceNavigator extends JFrame
{

	/** Why you need eclipse? */
	private static final long serialVersionUID = 1L;

	/**
	 * The MapEditorGUI this ResourceNavigator is built on.
	 */
	private MapEditorGUI screen;

	/**
	 * This
	 */
	JTabbedPane resourceNavigator;

	/**
	 * Builds the ResourceNavigator at the given (x,y) with the given width and height.
	 * @param screen The MapEditorGUI this ResourceNavigator should be built on.
	 * @param x	The x coord of the upper left corner.
	 * @param y The y coord of the upper left corner.
	 * @param width	The width of the ResourceNavigator.
	 * @param height	The Height of the ResourceNavigator.
	 */
	public ResourceNavigator(MapEditorGUI screen, int x, int y, int width, int height)
	{
		this.screen = screen;
		resourceNavigator = new JTabbedPane();
		resourceNavigator.add("Buildings", createBuildingList(createBuildings()));
		resourceNavigator.add("Units", createUnitList(createUnits()));
		// ResourceNavigator.add("Textures",
		// createList(buildings));
		resourceNavigator.setBounds(x, y, width, height);
		screen.getWindowBuilder().add(resourceNavigator);
	}

	/**
	 * Create a list of all the buildings that can be placed.
	 * @return The list of all the buildings that can be placed.
	 */
	private ArrayList<Building> createBuildings()
	{
		ArrayList<Building> buildings = new ArrayList<Building>();
		buildings.add(new Building("Base"));
		buildings.add(new Building("Food Generator"));
		buildings.add(new Building("Gold Generator"));
		buildings.add(new Building("Wood Generator"));
		buildings.add(new Building("Stone Generator"));
		buildings.add(new Building("Garrison"));
		buildings.add(new Building("Long Range Defense"));
		buildings.add(new Building("Short Range Defense"));
		buildings.add(new Building("Spawner"));
		buildings.add(new Building("Wall Vertical"));
		buildings.add(new Building("Wall Horizontal"));
		return buildings;
	}

	/**
	 * Creates a list of all the units that can be created.
	 * @return The list of all the units that can be placed.
	 */
	private ArrayList<Unit> createUnits()
	{
		ArrayList<Unit> units = new ArrayList<Unit>();
		units.add(new Unit("Calvary"));
		units.add(new Unit("Magic"));
		units.add(new Unit("Soldier"));
		units.add(new Unit("Minion"));
		units.add(new Unit("Ranged"));
		units.add(new Unit("Tank"));
		return units;
	}
	
	/**
	 * Creates tables for each building that can be placed with its stats and returns them all as a scrolling pane.
	 * These tables can be dragged to the map to place the building at the given position.
	 * @param buildings The list of available buildings.
	 * @return A JScrollPane which contains all of the buildings as placable tables with stats.
	 */
	private JScrollPane createBuildingList(ArrayList<Building> buildings)
	{
		JScrollPane pane = new JScrollPane();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JTable Test;
		Object[] names = {"test", "test"};
		for (int i = 0; i < buildings.size(); i++)
		{
			String[][] data = new String[3][3];
			data[0][0] = "Health:";
			data[0][1] = Integer.toString(buildings.get(i).getHealth());
			data[1][0] = "Height:";
			data[1][1] = Integer.toString(buildings.get(i).getHeight());
			data[2][0] = "Width:";
			data[2][1] = Integer.toString(buildings.get(i).getWidth());
			JPanel tablePanel = new JPanel();
			tablePanel.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(), buildings.get(i).getName(),
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
			tablePanel.add(Test);
			tablePanel.setName(buildings.get(i).getName());
			tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.PAGE_AXIS));
			panel.add(tablePanel);
			tablePanel.addMouseListener(new MouseListener()
			{

				@Override
				public void mouseClicked(MouseEvent arg0)
				{
				}

				@Override
				public void mouseEntered(MouseEvent arg0)
				{
				}

				@Override
				public void mouseExited(MouseEvent arg0)
				{
				}

				@Override
				public void mousePressed(MouseEvent arg0)
				{
				}

				@Override
				public void mouseReleased(MouseEvent arg0)
				{
					Building building = new Building(arg0.getXOnScreen(),
							arg0.getYOnScreen(), arg0.getComponent().getName());
					screen.addBuilding(building);
				}

			});
		}
		pane.getViewport().add(panel);
		return pane;
	}

	/**
	 * Creates a ScrollPane containing tables with all the available units stats.
	 * These tables can be dragged to the map to place the unit.
	 * @param units A list of the units that can be placed.
	 * @return A JScrollPane containing all of the units as placable tables.
	 */
	private JScrollPane createUnitList(ArrayList<Unit> units)
	{
		JScrollPane pane = new JScrollPane();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JTable Test;
		Object[] names = {"test", "test"};
		for (int i = 0; i < units.size(); i++)
		{
			String[][] data = new String[6][6];
			data[0][0] = "Health:";
			data[0][1] = Double.toString(units.get(i).getMaxHealth());
			data[1][0] = "Height:";
			data[1][1] = Double.toString(units.get(i).getHeight());
			data[2][0] = "Width:";
			data[2][1] = Double.toString(units.get(i).getWidth());
			data[3][0] = "Speed:";
			data[3][1] = Double.toString(units.get(i).getSpeed());
			data[4][0] = "Range:";
			data[4][1] = Double.toString(units.get(i).getRange());
			data[5][0] = "Damage:";
			data[5][1] = Double.toString(units.get(i).getDamage());
			JPanel tablePanel = new JPanel();
			tablePanel.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(), units.get(i).getName(),
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
			Test.setDragEnabled(false);
			Test.setTableHeader(null);
			Test.setCellSelectionEnabled(false);
			tablePanel.add(Test);
			tablePanel.setName(units.get(i).getName());
			tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.PAGE_AXIS));
			tablePanel.addMouseListener(new MouseListener()
			{

				@Override
				public void mouseClicked(MouseEvent arg0)
				{
				}

				@Override
				public void mouseEntered(MouseEvent arg0)
				{
				}

				@Override
				public void mouseExited(MouseEvent arg0)
				{
				}

				@Override
				public void mousePressed(MouseEvent arg0)
				{
				}

				@Override
				public void mouseReleased(MouseEvent arg0)
				{
					Unit unit = new Unit(arg0.getXOnScreen(), arg0.getYOnScreen(),
							arg0.getComponent().getName());
					screen.addUnit(unit);
				}

			});
			panel.add(tablePanel);
		}
		pane.getViewport().add(panel);
		return pane;
	}

}
