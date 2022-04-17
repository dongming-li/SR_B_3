package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import GUI.rtsScreen;
import structures.Building;
import units.Unit;

public class RTSPlayer implements ActionListener
{
	public rtsScreen playerScreen;
	public GameMap map;
	public ArrayList<JLabel> onScreenBuildings;
	public ArrayList<JPanel> onScreenUnits;
	public long lastResourceUpdate;
	public long checkMoveUpdate = 50;
	public long lastMoveUpdate;
	public long lastUnitMoveUpdate;
	public int baseResourceValue = 1;
	public int currentX;;
	public int currentY;
	public int moveDistance = 15;
	public String clickedBuilding;
	public String[] unitNames = {"Calvary", "Magic", "Soldier", "Minion", "Ranged", "Tank" };
	public int unitIndex = -1;
	public boolean unitMoving = false;
	public ArrayList<Integer> unitsToMove = new ArrayList<Integer>();
	public ArrayList<Point> unitsToMovePosition = new ArrayList<Point>();
	
	public static void main(String args[])
	{
		new RTSPlayer();
	}
	
	/**
	 * Creates a new RTSPlayer Object
	 */
	public RTSPlayer()
	{
		//Sets the defaults and draws the screen
		map = new GameMap();
		onScreenBuildings = new ArrayList<JLabel>();
		onScreenUnits = new ArrayList<JPanel>();
		playerScreen = new rtsScreen(this);
		currentX = 0;
		currentY = 0;
		Building base = new Building(playerScreen.getWidth()/2, playerScreen.getHeight()/2, "Base", this);
		map.addBuilding(base);
		redraw();
		
		lastResourceUpdate = System.currentTimeMillis();
		lastMoveUpdate = System.currentTimeMillis();
		lastUnitMoveUpdate = System.currentTimeMillis();
		
		//This is basically the game engine for the player
		while(true)
		{
			resourceUpdate();
			for(int i = 0; i < map.buildings.size(); i++)
			{
				try {//fixes error messages below somehow??????
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				try
				{
					if(map.buildings.get(i).getName().equals("Long Range Defense"))
					{
						map.buildings.get(i).LRDAttack();
					}
					else if(map.buildings.get(i).getName().equals("Short Range Defense"))
					{
						map.buildings.get(i).SRDAttack();
					}
				} catch(NullPointerException e)
				{
					System.out.println();
					System.out.println("This is fake news, please ignore");
				}
				
			}
			for(int i = 0; i < unitsToMove.size(); i++)
			{
				boolean remove = false;
				try {//fixes error messages below somehow??????
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				try
				{
					remove = playerScreen.moveUnit(unitsToMove.get(i), unitsToMovePosition.get(i).getX(), unitsToMovePosition.get(i).getY());
				}
				catch(NullPointerException e)
				{
					System.out.println("This is lies");
				}
				if(remove)
				{
					unitsToMove.remove(i);
					unitsToMovePosition.remove(i);
				}
			}
		}
	}
	
	/**
	 * Updates the values in the resource tab, if a certain amount of time has gone by since the last resourceUpdate
	 */
	public void resourceUpdate()
	{
		if(System.currentTimeMillis() - lastResourceUpdate > 1000)
		{
			lastResourceUpdate = System.currentTimeMillis();
			playerScreen.gold += baseResourceValue*numGoldGens() + 10;
			playerScreen.goldLabel.setText("Gold: " + playerScreen.gold);
			playerScreen.food += baseResourceValue*numFoodGens() + 10;
			playerScreen.foodLabel.setText("Food: " + playerScreen.food);
			playerScreen.wood += baseResourceValue*numWoodGens() + 10;
			playerScreen.woodLabel.setText("Wood: " + playerScreen.wood);
			playerScreen.stone += baseResourceValue*numStoneGens() + 10;
			playerScreen.stoneLabel.setText("Stone: " + playerScreen.stone);
		}
	}
	
	/**
	 * Moves the screen down
	 */
	public void moveDown()
	{
		if(System.currentTimeMillis() - lastMoveUpdate > checkMoveUpdate)
		{
			currentY -= moveDistance;
			redraw();
			lastMoveUpdate = System.currentTimeMillis();
		}

	}
	
	/**
	 * Moves the screen up
	 */
	public void moveUp()
	{
		if(System.currentTimeMillis() - lastMoveUpdate > checkMoveUpdate)
		{
			currentY += moveDistance;
			redraw();
			lastMoveUpdate = System.currentTimeMillis();
		}
	}
	
	/**
	 * Moves the screen right
	 */
	public void moveRight()
	{
		if(System.currentTimeMillis() - lastMoveUpdate > checkMoveUpdate)
		{
			currentX -= moveDistance;
			redraw();
			lastMoveUpdate = System.currentTimeMillis();
		}
	}
	
	/**
	 * Moves the screen left
	 */
	public void moveLeft()
	{
		if(System.currentTimeMillis() - lastMoveUpdate > checkMoveUpdate)
		{
			currentX += moveDistance;
			redraw();
			lastMoveUpdate = System.currentTimeMillis();
		}
	}
	
	/**
	 * Redraws all of the units and buildings that are onscreen currently
	 */
	public void redraw()
	{
		while(!onScreenBuildings.isEmpty())
		{
			playerScreen.remove(onScreenBuildings.get(0));
			onScreenBuildings.remove(0);
		}
		for(int i = 0; i < map.buildings.size(); i++)
		{
			if(onScreen(map.buildings.get(i).getX(), map.buildings.get(i).getY()))
			{
				JLabel add = new JLabel();
				add.setBounds(currentX + map.buildings.get(i).getX() - map.buildings.get(i).getWidth()/2, currentY + map.buildings.get(i).getY() - map.buildings.get(i).getHeight()/2, map.buildings.get(i).getWidth(), map.buildings.get(i).getHeight());
				add.setText(map.buildings.get(i).getName());
				add.setVisible(true);
				add.setBorder(new MatteBorder(2, 3, 2, 3, Color.green));
				
				add.addMouseListener(new MouseListener() {
					@Override
					public void mouseReleased(MouseEvent e) {
					}
					@Override
					public void mousePressed(MouseEvent e) {
					}
					@Override
					public void mouseExited(MouseEvent e) {
					}
					@Override
					public void mouseEntered(MouseEvent e) {
					}
					@Override
					public void mouseClicked(MouseEvent e) {
						clickedBuilding = add.getText();						
						buildingScreen(add.getX(), add.getY());
					}
				});
				
				onScreenBuildings.add(add);
				playerScreen.getContentPane().add(add);
			}
		}
		
		while(!onScreenUnits.isEmpty())
		{
			playerScreen.remove(onScreenUnits.get(0));
			onScreenUnits.remove(0);
		}
		for(int i = 0; i < map.units.size(); i++)
		{
			if(onScreen(map.units.get(i).getX(), map.units.get(i).getY()))
			{
				JPanel u = new JPanel();
				Unit spawner = map.units.get(i);
				u.setBounds(currentX + map.units.get(i).getX() - map.units.get(i).getWidth()/2, currentY + map.units.get(i).getY() - map.units.get(i).getHeight()/2, map.units.get(i).getWidth(), map.units.get(i).getHeight());
				u.setVisible(true);
				u.setBorder(new LineBorder(Color.DARK_GRAY));
				u.setBackground(Color.cyan);
				
				u.addMouseListener(new MouseListener() {
					@Override
					public void mouseReleased(MouseEvent e) {
					}
					@Override
					public void mousePressed(MouseEvent e) {
					}
					@Override
					public void mouseExited(MouseEvent e) {
					}
					@Override
					public void mouseEntered(MouseEvent e) {
					}
					@Override
					public void mouseClicked(MouseEvent e) {
						unitIndex = map.units.indexOf(spawner);
						unitMoving = true;
					}
				});
				
				onScreenUnits.add(u);
				playerScreen.getContentPane().add(u);
			}
		}
		playerScreen.update();
	}
	
	//Might get rid of this if the other buildings don't need special screens
	public void buildingScreen(int x, int y)
	{
		switch(clickedBuilding) {
		case "Spawner": 
			spawnerScreen(x, y);
			break;
		case "Base":
			break;
		case "Long Range Defense":
			break;
		case "Short Range Defense":
			break;
		}
		clickedBuilding = null;
		redraw();
		playerScreen.requestFocus();
	}
	
	/**
	 * This is the popup window when the player clicks on the spawner
	 * @param x
	 * 		The x coordinate of the screen/spawner
	 * @param y
	 * 		The y coordinate of the screen/spawner
	 */
	public void spawnerScreen(int x, int y)
	{
		JPanel l = new JPanel();
		l.setLayout(null);
		int width = 200;
		int height = 200;
		l.setBounds(x, y, width, height);
		l.setPreferredSize(new Dimension(width, height));
		l.setVisible(true);
		l.setBorder(new LineBorder(Color.black));
		
		JButton exit = new JButton("Exit");
		exit.setBounds(width/2 - width/4, height - 100, width/2, height/4);
		exit.setVisible(true);
		exit.setActionCommand("Resume");
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("Resume"))
				{
					l.setVisible(false);
					playerScreen.requestFocus();
				}
			}
		});
		
		l.add(exit);
		
		//This is the drop down menu for selecting a unit to spawn in
		JComboBox<String> unitList = new JComboBox<String>(unitNames);
		unitList.setBounds(width/2 - width/4, 10, width/2, height/6);
		unitList.setSelectedIndex(2);
		unitList.addActionListener(this);
		unitList.setVisible(true);
		l.add(unitList);
		playerScreen.getContentPane().add(l);
	}
	
	/**
	 * Checks if the given coordinate is approximately on screen or not
	 * @param x
	 * 		The x coordinate
	 * @param y
	 * 		The y coordinate
	 * @return
	 * 		True if on screen, false otherwise
	 */
	public boolean onScreen(int x, int y)
	{
		if(x < currentX*-1 - 150 || x > currentX*-1 + playerScreen.getWidth() + 150)
		{
			return false;
		}
		if(y < currentY*-1 - 150 || y > currentY*-1 + playerScreen.getHeight())
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Counts the number of Gold Generators in the map
	 * @return
	 * 		The number of gold generators in the map
	 */
	public int numGoldGens()
	{
		int number = 0;
		for(int i = 0; i < map.buildings.size(); i++)
		{
			if(map.buildings.get(i).getName().equals("Gold Generator"))
			{
				number ++;
			}
		}
		return number;
	}
	
	/**
	 * Counts the number of Food Generators in the map
	 * @return
	 * 		The number of food generators in the map
	 */
	public int numFoodGens()
	{
		int number = 0;
		for(int i = 0; i < map.buildings.size(); i++)
		{
			if(map.buildings.get(i).getName().equals("Food Generator"))
			{
				number ++;
			}
		}
		return number;
	}
	
	/**
	 * Counts the number of Wood Generators in the map
	 * @return
	 * 		The number of wood generators in the map
	 */
	public int numWoodGens()
	{
		int number = 0;
		for(int i = 0; i < map.buildings.size(); i++)
		{
			if(map.buildings.get(i).getName().equals("Wood Generator"))
			{
				number ++;
			}
		}
		return number;
	}
	
	/**
	 * Counts the number of Stone Generators in the map
	 * @return
	 * 		The number of stone generators in the map
	 */
	public int numStoneGens()
	{
		int number = 0;
		for(int i = 0; i < map.buildings.size(); i++)
		{
			if(map.buildings.get(i).getName().equals("Stone Generator"))
			{
				number ++;
			}
		}
		return number;
	}
	
	/**
	 * This chooses a random location around the given position to spawn a unit
	 * @param x
	 * 		The x position of the spawner building
	 * @param y
	 * 		The y position of the spawner building
	 * @param w
	 * 		The width of the spawner building
	 * @param h
	 * 		The height of the spawner building
	 * @return
	 * 		A Point Object of where to spawn a unit
	 */
	public Point chooseUnitSpawnPoint(int x, int y, int w, int h)
	{
		Point p = null;
		boolean check = false;
		Random r = new Random();
		int xCheck = 0;
		int yCheck = 0;
		while(!check)
		{
			xCheck = r.nextInt(100);
			yCheck = r.nextInt(100);
			if(r.nextBoolean())
			{
				xCheck = xCheck * -1;
			}
			if(r.nextBoolean())
			{
				yCheck = yCheck * -1;
			}
			xCheck -= currentX;
			yCheck -= currentY;
			if(r.nextBoolean())
			{
				if(r.nextBoolean())
				{
					xCheck += x;
					yCheck += y;
				}
				else
				{
					xCheck += x + w;
					yCheck += y;
				}
			}
			else 
			{
				if(r.nextBoolean())
				{
					xCheck += x;
					yCheck += y + h;
				}
				else
				{
					xCheck += x + w;
					yCheck += y + h;
				}
			}
			check = playerScreen.checkPosition(xCheck, yCheck, 20, 20);
		}
		p = new Point(xCheck, yCheck);
		return p;
	}
	
	/**
	 * This spawns a unit of the indicated name at the indicated location
	 * @param x
	 * 		The x position of the spawner Object, not the unit location
	 * @param y
	 * 		The y position of the spawner Object, not the unit location
	 * @param w
	 * 		The width of the spawner Object, not the unit location
	 * @param h
	 * 		The height of the spawner Object, not the unit location
	 * @param name
	 * 		The name which indicates the type of unit spawned in
	 */
	public void spawnUnit(int x, int y, int w, int h, String name)
	{
		//This is the position of where to spawn the unit in
		Point p = chooseUnitSpawnPoint(x, y, w, h);
		Unit spawner = new Unit(p.getX(), p.getY(), name, this);
		JPanel u = new JPanel();
		u.setBounds(spawner.getX(), spawner.getY(), spawner.getWidth(), spawner.getHeight());
		u.setVisible(true);
		u.setBorder(new LineBorder(Color.DARK_GRAY));
		u.setBackground(Color.cyan);
		map.addUnit(spawner);
		onScreenUnits.add(u);
		
		playerScreen.unitCount += spawner.getCost();
		playerScreen.unitCountLabel.setText("Units: " + playerScreen.unitCount);
		
		playerScreen.getContentPane().add(u);
		
		redraw();
	}
	
	/**
	 * This spawns in a unit based on the unit clicked in the drop down menu
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		@SuppressWarnings("unchecked")
		JComboBox<String> cb = (JComboBox<String>) e.getSource();
		spawnUnit(cb.getParent().getX(), cb.getParent().getY(), cb.getParent().getWidth(), cb.getParent().getHeight(), (String)cb.getSelectedItem());
		//buildings.get(0).special(cb.getSelectedItem());
		//System.out.println(cb.getSelectedItem());
	}
	
	//Used to store x and y coordinates
	//Very simple class... no set methods...
	public class Point
	{
		int x,y;
		
		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		public int getX()
		{
			return x;
		}
		
		public int getY()
		{
			return y;
		}
	}
	
}
