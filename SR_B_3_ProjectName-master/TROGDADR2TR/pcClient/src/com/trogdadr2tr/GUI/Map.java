package com.trogdadr2tr.GUI;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import com.trogdadr2tr.resources.Building;
import com.trogdadr2tr.resources.GameMap;
import com.trogdadr2tr.resources.Unit;

/**
 * Object that contains the map information (building locations/onscreen buildings/units) as well as handles all
 * of the drawing of the map.
 * @author Nick Schmidt
 *
 */

public class Map extends JFrame
{

	/** Yeah java */
	private static final long serialVersionUID = 1L;

	/**
	 * Contains the buildings currently displayed on the screen
	 */
	public static ArrayList<GuiObjectConnecter> onScreenBuildings;

	/**
	 * Contains the units currently displayed on the screen
	 */
	public static ArrayList<GuiObjectConnecter> onScreenUnits;

	/**
	 * Reference for the MapEditorGUI
	 */
	public static MapEditorGUI playerScreen;

	/**
	 * The GameMap containing all the buildings and units in the map
	 */
	public GameMap map;

	/**
	 * The current x coordinate for the screens position
	 */
	public int currentX;

	/**
	 * The current y coordinate for the screens position
	 */
	public int currentY;

	public JPanel Map;

	private boolean buildingEdit;
	/** Constructs a map on the passed in screen at the given (x,y) and with the given width and height
	 * 
	 * @param screen The screen the map should be built on.
	 * @param x	The upper left x coordinate of the map.
	 * @param y The upper left y coordinate of the map.
	 * @param width The width of the map.
	 * @param height The height of the map.
	 */
	public Map(MapEditorGUI screen, int x, int y, int width, int height)
	{
		Map = new JPanel();
		Map.setLayout(null);
		onScreenBuildings = new ArrayList<GuiObjectConnecter>();
		onScreenUnits = new ArrayList<GuiObjectConnecter>();
		playerScreen = screen;
		map = new GameMap();
		currentX = x;
		currentY = y;
		Map.setBounds(x, y, width, height);
		Map.setVisible(true);
		this.redraw();
		screen.getWindowBuilder().add(Map);
		buildingEdit = true;
		KeyListener mapListener = new KeyListener()
		{

			@Override
			public void keyPressed(KeyEvent arg0)
			{
				if (arg0.getKeyCode() == KeyEvent.VK_W)
				{
					currentY += 10;
					redraw();
					System.out.println("W");
					System.out.println(currentY);
				}
				else if (arg0.getKeyCode() == KeyEvent.VK_A)
				{
					currentX += 10;
					redraw();
					System.out.println("A");
					System.out.println(currentX);
				}
				else if (arg0.getKeyCode() == KeyEvent.VK_S)
				{
					if (currentY >= 10)
					{
						currentY -= 10;
						redraw();
						System.out.println("S");
						System.out.println(currentY);
					}
				}
				else if (arg0.getKeyCode() == KeyEvent.VK_D)
				{
					if (currentX >= 10)
					{
						currentX -= 10;
						redraw();
						System.out.println("D");
						System.out.println(currentX);
					}
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
		playerScreen.getWindowBuilder().addKeyListener(mapListener);
	}

	/**
	 * Allows for the map to be redrawn on the windowbuilder.
	 */
	public void mapReset()
	{
		playerScreen.getWindowBuilder().add(Map);
	}

	/**
	 * Adds a building to the map.
	 * @param building The building that should be added to the map.
	 */
	public void addBuilding(Building building)
	{
		if (map.checkBuildingOverlap(building))
		{
			map.addBuilding(building);
			this.redraw();
		}
	}

	/**
	 * Adds a unit to the map.
	 * @param unit The unit that should be added to the map.
	 */
	public void addUnit(Unit unit)
	{
		// if(map.checkUnitOverlap(unit)) {
		//
		// }
		map.addUnit(unit);
		this.redraw();
	}

	/** Checks if the given coordinate is approximately on screen
	 * or not
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return True if on screen, false otherwise */
	public boolean onScreen(double x, double y)
	{
		if (x < currentX * -1 - 150 || x > currentX * -1 + Map.getWidth() + 150)
		{
			return false;
		}
		if (y < currentY * -1 - 150 || y > currentY * -1 + Map.getHeight())
		{
			return false;
		}
		return true;
	}

	/**
	 * Redraws the map, updating positions and onscreen buildings/units.
	 */
	public void redraw()
	{
		Map.removeAll();
		while (!onScreenBuildings.isEmpty())
		{
			playerScreen.remove(onScreenBuildings.get(0).getPanel());
			onScreenBuildings.remove(0);
		}
		for (int i = 0; i < map.buildings.size(); i++)
		{
			if (onScreen(map.buildings.get(i).getX(), map.buildings.get(i).getY()))
			{
				JPanel add = new JPanel();
				add.setBounds(
						currentX + map.buildings.get(i).getX()
								- map.buildings.get(i).getWidth() / 2,
						currentY + map.buildings.get(i).getY()
								- map.buildings.get(i).getHeight() / 2,
						map.buildings.get(i).getWidth(),
						map.buildings.get(i).getHeight());
				// add.setName(map.buildings.get(i).getName());
				add.setName("" + i);
				add.setVisible(true);
				add.setBorder(new MatteBorder(2, 3, 2, 3, Color.green));

				GuiObjectConnecter g = new GuiObjectConnecter(add, map.buildings.get(i));

				if (buildingEdit == true)
				{
					add.addMouseListener(new MouseListener()
					{

						@Override
						public void mouseClicked(MouseEvent arg0)
						{
							System.out.println(arg0.getComponent().getName());
							playerScreen.editScenario(map.buildings.get(
									Integer.parseInt(arg0.getComponent().getName())));
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
						}

					});
				}
				onScreenBuildings.add(g);
				Map.add(add);
			}
		}

		while (!onScreenUnits.isEmpty())
		{
			playerScreen.remove(onScreenUnits.get(0).getPanel());
			onScreenUnits.remove(0);
		}
		for (int i = 0; i < map.units.size(); i++)
		{
			if (onScreen(map.units.get(i).getX(), map.units.get(i).getY()))
			{
				JPanel u = new JPanel();
				Unit spawner = map.units.get(i);
				u.setBounds(
						currentX + (int) map.units.get(i).getX()
								- (int) map.units.get(i).getWidth() / 2,
						currentY + (int) map.units.get(i).getY()
								- (int) map.units.get(i).getHeight() / 2,
						(int) map.units.get(i).getWidth(),
						(int) map.units.get(i).getHeight());
				u.setVisible(true);
				u.setBorder(new LineBorder(Color.DARK_GRAY));
				u.setBackground(Color.cyan);

				GuiObjectConnecter g = new GuiObjectConnecter(u, spawner);

				onScreenUnits.add(g);
				Map.add(u);
			}
		}
		Map.setVisible(false);
		Map.setVisible(true);
		onScreenUnits.removeAll(onScreenUnits);
	}

	class GuiObjectConnecter
	{

		private JPanel panel;

		private Object object;

		public GuiObjectConnecter(JPanel p, Object o)
		{
			panel = p;
			object = o;
		}

		public JPanel getPanel()
		{
			return panel;
		}

		public Object getObject()
		{
			return object;
		}
	}
}
