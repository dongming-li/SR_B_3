package com.trogdadr2tr.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.trogdadr2tr.GUI.LobbyScreen;
import com.trogdadr2tr.GUI.rtsScreen;
import com.trogdadr2tr.resources.*;
import com.trogdadr2tr.socket.Client;
import com.trogdadr2tr.socket.ConnectionState;

import com.trogdadr2tr.socket.ServerListener;

public class RTSPlayer implements ServerListener
{
	/**
	 * The game Screen associated with this player
	 */
	public rtsScreen playerScreen;

	/**
	 * The GameRooms available
	 */
	public ArrayList<GameRoom> gameRooms;

	/**
	 * The current gameRoom this player is in
	 */
	public GameRoom gameRoom;

	/**
	 * The map for this gameRoom
	 */
	public GameMap map;

	/**
	 * ArrayList of GuiObjectConnecters which contain all of the buildings displayed on the screen
	 */
	public ArrayList<GuiObjectConnecter> onScreenBuildings;

	/**
	 * ArrayList of GuiObjectConnecters which contain all of the units displayed on the screen
	 */
	public ArrayList<GuiObjectConnecter> onScreenUnits;

	/**
	 * Current messgae
	 */
	public CopyOnWriteArrayList<Message> chatMessageList;

	/**
	 * Indicates the time when the screen last moved
	 */
	public long lastMoveUpdate;
	
	/**
	 * Indicates the time when the units last moved
	 */
	public long lastUnitMoveUpdate;

	/**
	 * Current x position of the players screen related to the map
	 */
	public int currentX;

	/**
	 * Current y position of the players screen related to the map
	 */
	public int currentY;

	/**
	 * All unit names to reference easily
	 */
	public String[] unitNames =
			{"Calvary", "Magic", "Soldier", "Minion", "Ranged", "Tank"};

	/**
	 * Indicates which unit is moving
	 */
	public int unitIndex = -1;

	/**
	 * The unit last clicked on
	 */
	public Unit selected;

	/**
	 * Indicates if a unit is moving
	 */
	public boolean unitMoving = false;
	
	/**
	 * Units that are staged to move
	 */
	public ArrayList<Unit> unitsToMove = new ArrayList<Unit>();
	
	/**
	 * Parallel array with unitsToMove for positions the units are moving to
	 */
	public ArrayList<Point> unitsToMovePosition = new ArrayList<Point>();

	/**
	 * The lobbyScreen this player is created in
	 */
	public LobbyScreen lobbyScreen;

	/**
	 * Creates a new RTSPlayer object
	 * @param l The LobbyScreen the player was created in
	 */
	public RTSPlayer(LobbyScreen l)
	{
		lobbyScreen = l;
		
		// Sets the defaults and draws the screen
		// try {
		// Client.init();
		// //Client.addListener();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		map = new GameMap();
		onScreenBuildings = new ArrayList<GuiObjectConnecter>();
		onScreenUnits = new ArrayList<GuiObjectConnecter>();
		chatMessageList = new CopyOnWriteArrayList<Message>();
		// playerScreen = new rtsScreen(this);
		//createScreen();
	}

	/**
	 * Creates a rtsScreen for this player
	 */
	public void createScreen()
	{
		playerScreen = new rtsScreen(this);
//		Client.getInstance().updater.placeBuilding("Base", new Point(playerScreen.worldSize - playerScreen.getWidth() / 2, playerScreen.worldSize - playerScreen.getHeight() / 2));
		
		currentX = playerScreen.worldSize - playerScreen.getWidth();
		currentY = playerScreen.worldSize - playerScreen.getHeight();
		
		redraw();

		// This is basically the game engine for the player
		// while(true)
		// {
		Thread resource = new Thread()
		{

			@Override
			public void run()
			{
				long lastResourceUpdate = System.currentTimeMillis();
				while (true)
				{
					if (System.currentTimeMillis() - lastResourceUpdate > 1000)
					{
						lastResourceUpdate = System.currentTimeMillis();
						playerScreen.goldLabel.setText("Gold: " + playerScreen.gold);
						playerScreen.foodLabel.setText("Food: " + playerScreen.food);
						playerScreen.woodLabel.setText("Wood: " + playerScreen.wood);
						playerScreen.stoneLabel.setText("Stone: " + playerScreen.stone);
					}
					if(playerScreen.gold > 575 && playerScreen.food > 575 && playerScreen.wood > 575 && playerScreen.stone > 575)
					{
						playerScreen.WinGame();
					}
				}
			}
		};
		resource.start();
		
		Thread unitsMoving = new Thread(unitEngine);
		unitsMoving.start();
		
		Thread damage = new Thread()
		{
			@Override
			public void run()
			{
				long damageUpdateTime = System.currentTimeMillis();
				while(playerScreen.health > 0)
				{
					if(System.currentTimeMillis() - damageUpdateTime > 3000)
					{
						playerScreen.takeDamage(50);
						damageUpdateTime = System.currentTimeMillis();
					}
				}
			}
		};
		damage.start();
	}
	/**
	 * Creates the engine for unit movement
	 */
	 Runnable unitEngine = new Runnable()
	  {
	    @Override
	    public void run()
	    {
	      while (true)
	      {
	        if (System.currentTimeMillis() - lastUnitMoveUpdate > 70)
	        {
//	          for (int i = 0; i < unitsToAttack.size(); i++)
//	          {
//	            boolean remove = false;
//	            remove = attackEnemy(unitsToAttack.get(i),
//	                unitsBeingAttacked.get(i));
//	            redraw(unitsToAttack.get(i));
//	            if (remove)
//	            {
//	              unitsToAttack.remove(i);
//	              unitsBeingAttacked.remove(i);
//	              i--;
//	            }
//	          }
//	          for(int i = 0; i < unitsToAttackBuildings.size(); i++)
//	          {
//	            if(buildingAttackContinue)
//	            {
//	              boolean remove = false;
//	              if(map.buildings.contains(buildingsBeingAttacked))
//	              {
//	                remove = attackBuilding(unitsToAttackBuildings.get(i),
//	                    buildingsBeingAttacked.get(i));
//	                redraw(unitsToAttackBuildings.get(i));
//	              }
//	              else
//	              {
//	                remove = true;
//	              }
//	              if(remove)
//	              { 
//	                unitsToAttackBuildings.remove(i);
//	                buildingsBeingAttacked.remove(i);
//	                i--;
//	                buildingAttackContinue = false;
//	              }
//	            }
//	          }
	          boolean check = false;
	          for (int i = 0; i < unitsToMove.size(); i++)
	          {
	            boolean remove = false;
	            try
	            {
	              remove = moveUnit(unitsToMove.get(i),
	                  unitsToMovePosition.get(i));
	              check = true;
	            }
	            catch (NullPointerException e)
	            {
	              System.out.println("This is lies");
	            }
	            if (remove)
	            {
	              unitsToMove.remove(i);
	              unitsToMovePosition.remove(i);
	              i--;
	            }
	          }
	          if(check)
	          {
	              redraw();
	              check = false;
	          }
	          lastUnitMoveUpdate = System.currentTimeMillis();
	        }
	      }
	    }
	  };
	  
	  /**
	   * Moves the specified unit to the destination point
	   * @param unit The unit to move
	   * @param destination The point the unit is moving towards
	   * @return True if the unit has reached the destination, false otherwise
	   */
	public boolean moveUnit(Unit unit, Point destination)
	{
		long deltaMilliseconds = System.currentTimeMillis() - lastUnitMoveUpdate;
		double x = unit.getX();
		double y = unit.getY();
		double distance = unit.getSpeed() * deltaMilliseconds / 10;
		if (x == destination.getX() && y == destination.getY())
		{
			return true;
		}
		if (Math.abs(x - destination.getX()) < distance)
		{
			x = destination.getX();
		}
		else if (x > destination.getX())
		{
			x -= distance;
		}
		else if (x < destination.getX())
		{
			x += distance;
		}
		if (Math.abs(y - destination.getY()) < distance)
		{
			y = destination.getY();
		}
		else if (y > destination.getY())
		{
			y -= distance;
		}
		else if (y < destination.getY())
		{
			y += distance;
		}
		Double X = new Double(x);
		x = X.intValue();
		Double Y = new Double(y);
		y = Y.intValue();
		unit.setPosition(x, y);
		// return whether the unit is done moving
		return ((int) unit.getX() == destination.getX()
				&& (int) unit.getY() == destination.getY());
	}

	/** Redraws all of the units and buildings that are onscreen
	 * currently */
	public void redraw()
	{
		for(GuiObjectConnecter g: onScreenBuildings)
		{
			g.getLabel().setBounds(-1 * currentX + ((Building)g.getObject()).getX() - ((Building)g.getObject()).getWidth() / 2,
					-1 * currentY + ((Building)g.getObject()).getY() - ((Building)g.getObject()).getHeight() / 2,
					((Building)g.getObject()).getWidth(), ((Building)g.getObject()).getHeight());
		}
		for(GuiObjectConnecter g: onScreenUnits)
		{
			g.getLabel().setBounds(-1 * currentX + (int) ((Unit)g.getObject()).getX() - (int) ((Unit)g.getObject()).getWidth() / 2,
					-1 * currentY + (int) ((Unit)g.getObject()).getY() - (int) ((Unit)g.getObject()).getHeight() / 2,
					(int) ((Unit)g.getObject()).getWidth(), (int) ((Unit)g.getObject()).getHeight());
		}
//		for(int i = 0; i < NumberOfHackNSlashPlayers; i++)
//		{
//			if(onScreen(NumberOfHackNSlashPlayers.get(i).getX(), NumberOfHackNSlashPlayers.get(i).getY()))
//			{
//				JLabel p = new JLabel();
//				p.setBounds(NumberOfHackNSlashPlayers.get(i).getX(), NumberOfHackNSlashPlayers.get(i).getY(), 100, 100);
//				p.setBackground(Color.white);
//				p.setVisible(true);
//				p.setBorder(new LineBorder(Color.white));
//				
//				playerScreen.gameWindowScrollPane.add(p, 0);
//			}
//		}
		
		playerScreen.update();
	}
	
//	/** Redraws all of the units and buildings that are onscreen
//	 * currently */
//	public void reddraw()
//	{
//		while (!onScreenBuildings.isEmpty())
//		{
//			playerScreen.remove(onScreenBuildings.get(0).label);
//			if(playerScreen.gameWindowScrollPane.getComponentCount() > 0)
//			{
//				playerScreen.gameWindowScrollPane.remove((onScreenBuildings.get(0).label));
//			}
//			onScreenBuildings.remove(0);
//		}
//		while (!onScreenUnits.isEmpty())
//		{
//			playerScreen.remove(onScreenUnits.get(0).label);
//			if(playerScreen.gameWindowScrollPane.getComponentCount() > 0)
//			{
//				playerScreen.gameWindowScrollPane.remove((onScreenUnits.get(0).label));
//			}
//			onScreenUnits.remove(0);
//		}
//		for (Building building : map.buildings)
//		{
//			if (onScreen(building.getX(), building.getY()))
//			{
//				JLabel add = new JLabel();
//				
//				//
//				//TODO set the building to a different image based on building name
//				//
//				String f = new File("src/Images/building2.png").getAbsolutePath(); 
//			    f = f.replace("\\", "/"); 
//			    ImageIcon i = new ImageIcon(f);
//			    Image image = i.getImage();
//			    image = image.getScaledInstance(building.getWidth(), building.getHeight(), Image.SCALE_SMOOTH);
//			    i = new ImageIcon(image);
//			    add.setIcon(i);				
//			    //
//			    //
//			    add.setBounds(-1 * currentX + building.getX() - building.getWidth() / 2,
//						-1 * currentY + building.getY() - building.getHeight() / 2,
//						building.getWidth(), building.getHeight());
//				add.setName(building.getName());
//				// add.setText(building.getName());
//				add.setVisible(true);
//				//add.setBorder(new MatteBorder(2, 3, 2, 3, Color.green));
//
//				GuiObjectConnecter g = new GuiObjectConnecter(add, building);
//
//				add.addMouseListener(new MouseListener()
//				{
//
//					@Override
//					public void mouseReleased(MouseEvent e)
//					{
//						switch (e.getButton())
//						{
//							// left click
//							case MouseEvent.BUTTON1:
//								if (add.getName().equals("Spawner"))
//								{
//									spawnerScreen(building, add.getX(), add.getY());
//									redraw();
//								}
//								break;
//							// middle click
//							case MouseEvent.BUTTON2:
//								break;
//							// right click
//							case MouseEvent.BUTTON3:
//								break;
//						}
//
//					}
//
//					@Override
//					public void mousePressed(MouseEvent e)
//					{
//					}
//
//					@Override
//					public void mouseExited(MouseEvent e)
//					{
//					}
//
//					@Override
//					public void mouseEntered(MouseEvent e)
//					{
//					}
//
//					@Override
//					public void mouseClicked(MouseEvent e)
//					{
//					}
//				});
//
//				onScreenBuildings.add(g);
//				playerScreen.gameWindowScrollPane.add(add, 0);
////				playerScreen.screenContainer.add(add, 0);
////				playerScreen.getContentPane().add(add);
//			}
//		}
//		for (
//
//		Unit unit : map.units)
//		{
//			if (onScreen(unit.getX(), unit.getY()))
//			{
//				JLabel u = new JLabel();
//				//
//				//TODO change unit picture based on unit name
//				//
//				String f = new File("src/Images/unit.jpg").getAbsolutePath(); 
//			    f = f.replace("\\", "/"); 
//			    ImageIcon i = new ImageIcon(f);
//			    Image image = i.getImage();
//			    image = image.getScaledInstance((int)unit.getWidth(), (int)unit.getHeight(), Image.SCALE_SMOOTH);
//			    i = new ImageIcon(image);
//			    u.setIcon(i);	
//			    //
//			    //
//				u.setBounds(-1 * currentX + (int) unit.getX() - (int) unit.getWidth() / 2,
//						-1 * currentY + (int) unit.getY() - (int) unit.getHeight() / 2,
//						(int) unit.getWidth(), (int) unit.getHeight());
//				u.setVisible(true);
//				//u.setBorder(new LineBorder(Color.DARK_GRAY));
//				//u.setBackground(Color.cyan);
//
//				GuiObjectConnecter g = new GuiObjectConnecter(u, unit);
//
//				u.addMouseListener(new MouseListener()
//				{
//
//					@Override
//					public void mouseReleased(MouseEvent e)
//					{
//						System.out.println("clicked on a unit");
//						switch (e.getButton())
//						{
//							// left click
//							case MouseEvent.BUTTON1:
//								selected = unit;
//								break;
//							// middle click
//							case MouseEvent.BUTTON2:
//								break;
//							// right click
//							case MouseEvent.BUTTON3:
//								break;
//						}
//					}
//
//					@Override
//					public void mousePressed(MouseEvent e)
//					{
//					}
//
//					@Override
//					public void mouseExited(MouseEvent e)
//					{
//					}
//
//					@Override
//					public void mouseEntered(MouseEvent e)
//					{
//					}
//
//					@Override
//					public void mouseClicked(MouseEvent e)
//					{
//					}
//
//				});
//
//				onScreenUnits.add(g);
//				playerScreen.gameWindowScrollPane.add(u, 0);
////				playerScreen.screenContainer.add(u, 0);
////				playerScreen.getContentPane().add(u);
//			}
//		}
//		playerScreen.update();
//	}

	/** This is the popup window when the player clicks on the
	 * spawner
	 * @param b The building this window is created over
	 * @param x The x coordinate of the screen/spawner
	 * @param y The y coordinate of the screen/spawner */
	public void spawnerScreen(Building b, int x, int y)
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
		exit.setBounds(width / 2 - width / 4, height - 100, width / 2, height / 4);
		exit.setVisible(true);
		exit.setActionCommand("Resume");
		exit.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (e.getActionCommand().equals("Resume"))
				{
					l.setVisible(false);
					playerScreen.requestFocus();
				}
			}
		});

		l.add(exit);

		// This is the drop down menu for selecting a unit to
		// spawn in
		JComboBox<String> unitList = new JComboBox<String>(unitNames);
		unitList.setBounds(width / 2 - width / 4, 10, width / 2, height / 6);
		unitList.setSelectedIndex(2);
		unitList.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				try
				{
					Client.getInstance().updater.spawnUnit(b,
							(String) unitList.getSelectedItem());
				}
				catch (IllegalStateException e1)
				{
				}
			}
		});
		unitList.setVisible(true);
		l.add(unitList);
		playerScreen.screenContainer.add(l, 0);
		playerScreen.update();
//		playerScreen.getContentPane().add(l);
	}

	/** Checks if the given coordinate is approximately on screen
	 * or not
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return True if on screen, false otherwise */
	public boolean onScreen(double x, double y)
	{
		if (x < currentX * -1 - 150 || x > currentX * -1 + playerScreen.getWidth() + 150)
		{
			return false;
		}
		if (y < currentY * -1 - 150 || y > currentY * -1 + playerScreen.getHeight())
		{
			return false;
		}
		return true;
	}

	private class GuiObjectConnecter
	{
		/**
		 * The JLabel this object contains
		 */
		public JLabel label;

		/**
		 * The object this guiObjectConnector contains (typcially a unit or building)
		 */
		public Object object;

		/**
		 * Creates a GuiObjectConnector that contains the given label (add) and Object (o)
		 * @param add
		 * @param o
		 */
		public GuiObjectConnecter(JLabel add, Object o)
		{
			label = add;
			object = o;
		}
		
		/**
		 * Gets the object of this container
		 * @return The object of this container
		 */
		public Object getObject()
		{
			return object;
		}
		
		/**
		 * Gets the label of this container
		 * @return The label of this container
		 */
		public JLabel getLabel()
		{
			return label;
		}

	}

	@Override
	public void requestLogin()
	{
		// TODO attempt login? need screen
	}

	@Override
	public void serverErrorMessage(String errorMessage)
	{
		System.err.println(errorMessage);
	}

	@Override
	public void downloadGameRooms(ArrayList<GameRoom> gameRooms)
	{
		this.gameRooms = gameRooms;
	}

	@Override
	public void newMessage(Message message)
	{
		this.chatMessageList.add(message);
		// print message or something.
	}

	@Override
	public void downloadMap(GameMap map)
	{
		this.map = map;
	}

	@Override
	public void updateUnit(Unit unit)
	{
		System.out.println("Unit updated to (" + unit.getX() + ", " + unit.getY() + ").");
//		unit.setPosition(position.getX(), position.getY());
		map.units.set(map.units.indexOf(unit), unit);
		//redraw();
	}

	@Override
	public void updateBuilding(Building building)
	{
		map.buildings.set(map.buildings.indexOf(building), building);
	}

	@Override
	public void addUnit(Unit unit)
	{
		System.out.println("Unit Added");
		map.units.add(unit);
		JLabel u = new JLabel();
		//
		//TODO change unit picture based on unit name
		//
		String f = new File("src/Images/unit2.jpg").getAbsolutePath(); 
	    f = f.replace("\\", "/"); 
	    ImageIcon i = new ImageIcon(f);
	    Image image = i.getImage();
	    image = image.getScaledInstance((int)unit.getWidth(), (int)unit.getHeight(), Image.SCALE_SMOOTH);
	    i = new ImageIcon(image);
	    u.setIcon(i);	
	    //
	    //
		u.setBounds((int) unit.getX(), (int) unit.getY(),
				(int) unit.getWidth(), (int) unit.getHeight());
		u.setVisible(true);
		//u.setBorder(new LineBorder(Color.DARK_GRAY));
		//u.setBackground(Color.cyan);

		GuiObjectConnecter g = new GuiObjectConnecter(u, unit);

		u.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				System.out.println("clicked on a unit");
				switch (e.getButton())
				{
					// left click
					case MouseEvent.BUTTON1:
						selected = unit;
						break;
					// middle click
					case MouseEvent.BUTTON2:
						break;
					// right click
					case MouseEvent.BUTTON3:
						break;
				}
			}
				@Override
			public void mousePressed(MouseEvent e)
			{
			}
			@Override
			public void mouseExited(MouseEvent e)
			{
			}
			@Override
			public void mouseEntered(MouseEvent e)
			{
			}
			@Override
			public void mouseClicked(MouseEvent e)
			{
			}
		});
		
		onScreenUnits.add(g);
//		playerScreen.getContentPane().add(u);
		playerScreen.screenContainer.add(u, 0);

		redraw();

	}

	@Override
	public void addBuilding(Building building)
	{
		map.buildings.add(building);
		JLabel add = new JLabel();
		
		//
		//TODO set the building to a different image based on building name
		//
		String f = new File("src/Images/building2.png").getAbsolutePath(); 
	    f = f.replace("\\", "/"); 
	    ImageIcon i = new ImageIcon(f);
	    Image image = i.getImage();
	    image = image.getScaledInstance(building.getWidth(), building.getHeight(), Image.SCALE_SMOOTH);
	    i = new ImageIcon(image);
	    add.setIcon(i);				
	    //
	    //
	    add.setBounds(-1 * currentX + building.getX() - building.getWidth() / 2,
				-1 * currentY + building.getY() - building.getHeight() / 2,
				building.getWidth(), building.getHeight());
		add.setName(building.getName());
		// add.setText(building.getName());
		add.setVisible(true);
		//add.setBorder(new MatteBorder(2, 3, 2, 3, Color.green));

		GuiObjectConnecter g = new GuiObjectConnecter(add, building);

		add.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseReleased(MouseEvent e)
			{
				switch (e.getButton())
				{
					// left click
					case MouseEvent.BUTTON1:
						if (add.getName().equals("Spawner"))
						{
							spawnerScreen(building, add.getX(), add.getY());
							redraw();
						}
						break;
					// middle click
					case MouseEvent.BUTTON2:
						break;
					// right click
					case MouseEvent.BUTTON3:
						break;
				}

			}

			@Override
			public void mousePressed(MouseEvent e)
			{
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
			}
		});

		onScreenBuildings.add(g);
		playerScreen.gameWindowScrollPane.add(add, 0);
		playerScreen.update();
		//redraw();
	}

	@Override
	public void removeUnit(Unit unit)
	{
		map.units.remove(unit);
		onScreenUnits.remove(findUnitLoc(unit));
	}
	
	/**
	 * Finds the location of the given unit in the onScreenUnits ArrayList
	 * @param unit The unit to find the index of
	 * @return An int of the unit location in onScreenUnits, -1 if the unit is not in the ArrayList
	 */
	private int findUnitLoc(Unit unit)
	{
		for(int i = 0; i < onScreenUnits.size(); i++)
		{
			if(onScreenUnits.get(i).equals(unit))
			{
				return i;
			}
		}
		return -1;
	}

	@Override
	public void removeBuilding(Building building)
	{
		map.buildings.remove(building);
		onScreenBuildings.remove(findBuildingLoc(building));
	}
	
	/**
	 * Finds the location of the given building in the onScreenBuildings ArrayList
	 * @param unit The building to find the index of
	 * @return An int of the building location in onScreenBuildings, -1 if the unit is not in the ArrayList
	 */
	private int findBuildingLoc(Building b)
	{
		for(int i = 0; i < onScreenBuildings.size(); i++)
		{
			if(onScreenBuildings.get(i).equals(b))
			{
				return i;
			}
		}
		return -1;
	}

	@Override
	public void updateConnectionState(ConnectionState state)
	{
		System.out.println("Connection state changed: " + state);
		if (state == ConnectionState.LOGGED_IN)
		{
			try
			{
				Client.getInstance().updater.hostRoom("Cancer.jpeg");
			}
			catch (IllegalStateException e)
			{
			}
		}
		if (state == ConnectionState.RTS_IN_GAME)
		{
			createScreen();
		}

	}

	@Override
	public void downloadGameRoom(GameRoom gameRoom)
	{
		this.gameRoom = gameRoom;
		lobbyScreen.setGameRoom(gameRoom);
	}

	@Override
	public void updateResources(int gold, int wood, int food, int stone)
	{
		if (playerScreen == null)
		{
			// it isn't your time.
			return;
		}
		playerScreen.gold = gold;
		playerScreen.wood = wood;
		playerScreen.food = food;
		playerScreen.stone = stone;
	}
}
