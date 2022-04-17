package com.trogdadr2tr.GUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import com.trogdadr2tr.resources.Building;
import com.trogdadr2tr.resources.GameMap;
import com.trogdadr2tr.resources.Unit;

/** 
 * The MapEditor GUI object contains all the subelements of the MapEditor. It handles map drawing and building placing as well
 * as scenario editing.
 * @author Nick Schmidt*/
public class MapEditorGUI extends JFrame
{

	/** yeah java */
	private static final long serialVersionUID = 1L;

	/**
	 * The WindowBuilder this object is based on.
	 */
	private WindowBuilder frame;

	/**
	 * The Map object connected to this MapEditorGUI, holds all map data and drawing.
	 */
	private Map map;

	/**
	 * The ScenarioMiniScreen connected to this MapEditorGUI
	 */
	private ScenarioMiniScreen miniScreen;

	/**
	 * The KeyListener tied to this GUI
	 */
	private KeyListener listener;

	/**
	 * Builds a map editor gui on the given frame without initializing map.
	 * @param frame The WindowBuilder this MapEditorGUI is built on.
	 */
	public MapEditorGUI(WindowBuilder frame)
	{
		this.frame = frame;
		buildScreen();
	}
	
	/**
	 * Builds a MapEditorGUI on the given frame using existing map data.
	 * @param frame The WindowBuilder this MapEditorGUI is built on.
	 * @param map The map that should be used to build this MapEditorGUI
	 */
	  public MapEditorGUI(WindowBuilder frame, GameMap map)
	  
	  {
	 
	    this.frame = frame;
	 
	    this.map = new Map(this, 0, 40, 1620, 860);
	 
	    this.map.map = map;
	 
	    for(int i = 0; i < this.map.map.buildings.size(); i++) {
	 
	      System.out.println(this.map.map.buildings.get(i).getName());
	 
	    }
	 
	    buildScreen();
	 
	  }
	 

	  /**
	   * Builds the MapEditorScreen, adding all components and creating/updating the map.
	   * Also adds a KeyListener to exit the mode by pressing escape.
	   */
	public void buildScreen()
	{
		frame.setFocusable(true);
		frame.clear();
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		new ResourceNavigator(this, 1620, 0, 300, 905);
		new ViewSelector(this, 2, 5, 1615, 35);
		if (map == null)
		{
			map = new Map(this, 0, 40, 1620, 860);
		}
		else
		{
			map.mapReset();
			map.redraw();
		}
		miniScreen = new ScenarioMiniScreen(this, 2, 910, 1915, 168);
		KeyListener listener = new KeyListener()
		{

			@Override
			public void keyPressed(KeyEvent arg0)
			{
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					frame.buildMainScreen();
					frame.setFocusable(false);
					frame.removeKeyListener(this);
				}
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
			}

			@Override
			public void keyTyped(KeyEvent e)
			{
			}
		};
		this.listener = listener;
		frame.addKeyListener(listener);

	}

	/**
	 * Gets the listener attached to this MapEditorGUI.
	 * @return The KeyListener attatched to this MapEditorGUI.
	 */
	public KeyListener getListener()
	{
		return listener;
	}

	/**
	 * Gets the windowBuilder this MapEditorGUI is built on.
	 * @return The WindowBuilder the GUI is built on.
	 */
	public WindowBuilder getWindowBuilder()
	{
		return this.frame;
	}

	/**
	 * Adds a building to the map.
	 * @param building The building to be added.
	 */
	public void addBuilding(Building building)
	{
		map.addBuilding(building);
	}
	
	/**
	 * Opens the Scenario Editor for the given object.
	 * @param object The object to be edited.
	 */
	public void editScenario(Object object)
	{
		miniScreen.editScenario(object);
	}

	/**
	 * Adds a unit the map.
	 * @param unit The unit to be added.
	 */
	public void addUnit(Unit unit)
	{
		map.addUnit(unit);
	}
	
	/**
	 * Returns the GameMap attatched to this MapEditorGUI.
	 * @return The GameMap
	 */
	public GameMap getMap() {
	    return this.map.map;
	 }
		 
}
