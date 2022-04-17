package com.trogdadr2tr.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import com.trogdadr2tr.resources.*;

/** Runs the engines managing resource generation, unit movement,
 * building and unit placement, and player movement. Sends and
 * receives data to and from 5 clients, and keeps track of their
 * roles in the game.
 * 
 * @author Colt Rogness */
public class GameRoom extends Thread implements Serializable
{

	/** GameRoom MK I */
	private static final long serialVersionUID = 1L;

	/** List of listeners tracking this instance of the
	 * gameroom. */
	private transient CopyOnWriteArrayList<GameListener> listeners;

	/** Thread handling resources. */
	private transient Timer resourceTimer;

	/** RTS player. Used mainly for server-side unit and building
	 * ownership. */
	private Player rtsPlayer;

	/** List of Hack and Slash players. */
	private CopyOnWriteArrayList<Player> HSPlayers;

	/** Game map. Initialized at the beggining of the game. */
	private GameMap map;

	/** List of moving units. */
	private volatile CopyOnWriteArrayList<UnitMove> movingUnits;

	/** List of attacking units. */
	private volatile CopyOnWriteArrayList<UnitAttack> attackingUnits;

	/** List of attacking buildings. */
	private volatile CopyOnWriteArrayList<BuildingAttack> defensiveBuildings;

	/** List of resource generating buildings. */
	private volatile CopyOnWriteArrayList<Building> resourceBuildings;

	/** RTS resource */
	private int wood;

	/** RTS resource */
	private int gold;

	/** RTS resource */
	private int stone;

	/** RTS resource */
	private int food;

	/** Keep track of whether the game should be running or
	 * not. */
	private Boolean runGame;

	/** Game room's brodcasted name. */
	private String roomName;

	/** Password required to join the room. Null if no password
	 * required. */
	private String unsecurePassword;

	/** Max number of Hack and Slash players. */
	private static final int playerCap = 4;

	/** Construct a game room with given host and room name.
	 * 
	 * @param rtsPlayer - room host
	 * @param roomName - room's brodcasted name */
	public GameRoom(Player rtsPlayer, String roomName)
	{
		this.rtsPlayer = rtsPlayer;
		this.roomName = roomName;

		this.HSPlayers = new CopyOnWriteArrayList<Player>();
		this.movingUnits = new CopyOnWriteArrayList<UnitMove>();
		this.attackingUnits = new CopyOnWriteArrayList<UnitAttack>();
		this.defensiveBuildings = new CopyOnWriteArrayList<BuildingAttack>();
		this.resourceBuildings = new CopyOnWriteArrayList<Building>();
		this.listeners = new CopyOnWriteArrayList<GameListener>();
		this.runGame = false;
		this.food = 500;
		this.stone = 500;
		this.gold = 500;
		this.wood = 500;

		resourceTimer = new Timer("Resources for " + roomName);

		map = null;
		unsecurePassword = null;
	}

	@Override
	public void run()
	{
		while (!runGame)
		{
			if (allReady())
			{
				runGame = true;
			}
		}
		map = new GameMap();
		// TODO - place players on the map in different places
		long lastLoopTime = System.currentTimeMillis();
		System.out.println("Starting game!");
		for (GameListener listener : listeners)
		{
			listener.sendMap(map);
			listener.startGame();
		}
		resourceTimer.schedule(new TimerTask()
		{

			@Override
			public void run()
			{
				for (Building building : resourceBuildings)
				{
					gold += building.goldRate();
					wood += building.woodRate();
					food += building.foodRate();
					stone += building.stoneRate();
				}
				for (GameListener listener : listeners)
				{
					if (listener == null)
					{
						cancel();
					}
					listener.updateResources(gold, wood, food, stone);
				}

			}

		}, 0, 1000);
		while (runGame)
		{
			if (System.currentTimeMillis() - lastLoopTime >= 500)
			{
				long deltaMilliseconds = System.currentTimeMillis() - lastLoopTime;
				lastLoopTime += deltaMilliseconds;
				for (Player player : HSPlayers)
				{
					player.doMove(deltaMilliseconds);
					for (GameListener listener : listeners)
					{
						listener.updatePlayer(player);
					}
				}
				for (UnitMove unit : movingUnits)
				{
					if (unit.doMove(deltaMilliseconds))
					{
						movingUnits.remove(unit);
					}
					else
					{
						for (GameListener listener : listeners)
						{
							System.out.println("Unit moved to position: "
									+ unit.unit.getX() + "   " + unit.unit.getY());
							listener.updateUnit(unit.unit);
						}
					}

				}
				for (UnitAttack unit : attackingUnits)
				{
					unit.doAttack(deltaMilliseconds);
				}
				for (BuildingAttack building : defensiveBuildings)
				{
					building.doAttack(HSPlayers, deltaMilliseconds);
				}
			}
		}
		close();
	}

	/** Clean up after the game is over. */
	public void close()
	{
		resourceTimer.cancel();
		// TODO - kill everyone, delete room
	}

	/** Check if all the players are ready.
	 * 
	 * @return */
	private Boolean allReady()
	{
		for (Player player : HSPlayers)
		{
			if (!player.isReady())
			{
				return false;
			}
		}
		return rtsPlayer.isReady();
	}

	/** Check if the given building can be placed.
	 * 
	 * @param building to be placed
	 * @return if the building will fit */
	private Boolean buildingFits(Building building)
	{
		// TODO - do some math in the map.
		return true;
	}

	/** Check if RTS player can afford the given building.
	 * 
	 * @param building
	 * @return */
	private Boolean canAffordBuilding(Building building)
	{
		return (gold > building.getGoldCost() && wood > building.getWoodCost()
				&& food > building.getFoodCost() && stone > building.getStoneCost());
	}

	/** Check if RTS player can afford the given unit.
	 * 
	 * @param unit
	 * @return */
	private Boolean canAffordUnit(Unit unit)
	{
		return (gold > unit.getGoldCost() && wood > unit.getWoodCost()
				&& food > unit.getFoodCost() && stone > unit.getStoneCost());
	}

	/** Getter for the room's name.
	 * 
	 * @return */
	public String getRoomName()
	{
		return roomName;
	}

	/** Setter for the room's name.
	 * 
	 * @param roomName */
	public void setRoomName(String roomName)
	{
		this.roomName = roomName;
	}

	/** Getter for the room's password.
	 * 
	 * @return */
	public String getUnsecurePassword()
	{
		return unsecurePassword;
	}

	/** Setter for the room's password.
	 * 
	 * @param unsecurePassword */
	public void setUnsecurePassword(String unsecurePassword)
	{
		this.unsecurePassword = unsecurePassword;
	}

	/** Deduct the cost of <building> from the RTS player's
	 * resources.
	 * 
	 * @param building */
	private void deductCost(Building building)
	{
		gold -= building.getGoldCost();
		wood -= building.getWoodCost();
		food -= building.getFoodCost();
		stone -= building.getStoneCost();
	}

	/** Deduvt the cost of <unit> form the RTS player's resources.
	 * 
	 * @param unit */
	private void deductCost(Unit unit)
	{
		gold -= unit.getGoldCost();
		wood -= unit.getWoodCost();
		food -= unit.getFoodCost();
		stone -= unit.getStoneCost();
	}

	/** Update the game room with the given room.
	 * 
	 * @param room */
	public void update(GameRoom room)
	{
		unsecurePassword = room.unsecurePassword;
		roomName = room.roomName;
		// TODO - other changes?
	}

	/** Called by <player> to join this room.
	 * 
	 * @param player player to join
	 * @throws IllegalStateException */
	public void joinRoom(Player player)
			throws IllegalStateException
	{
		if (HSPlayers.size() >= playerCap)
		{
			throw new IllegalStateException();
		}
		HSPlayers.add(player);
	}

	/** Remove <player> from the room.
	 * 
	 * @param player */
	public void leaveRoom(Player player)
	{
		if (rtsPlayer == player)
		{
			rtsPlayer = null;
			close();
		}
		HSPlayers.remove(player);
	}

	/** Push <message> out to all the players.
	 * 
	 * @param message */
	public void sendMessage(Message message)
	{
		for (GameListener listener : listeners)
		{
			listener.sendMessage(message);
		}
	}

	/** Place a building at the request of a player.
	 * 
	 * @param player who placed the building
	 * @param buildingType type of building to place
	 * @param location where to place the building */
	public void placeBuilding(Player player, String buildingType, Point location)
	{
		Building toPlace = new Building(location, buildingType);
		if (buildingFits(toPlace) && canAffordBuilding(toPlace))
		{
			deductCost(toPlace);
			map.buildings.add(toPlace);
			if (toPlace.getName().contains("Generator"))
			{
				this.resourceBuildings.add(toPlace);
			}
			else if (toPlace.getName().contains("Defense"))
			{
				this.defensiveBuildings.add(new BuildingAttack(toPlace));
			}
			for (GameListener listener : listeners)
			{
				listener.addBuilding(toPlace);
			}
		}
	}

	/** Remove a building from the game.
	 * 
	 * @param player building owner
	 * @param building to be removed */
	public void deleteBuilding(Player player, Building building)
	{
		// TODO - delete building if owned by player
	}

	/** Queue a unit to be moved
	 * 
	 * @param player owner of the unit
	 * @param unit to be moved
	 * @param destination for the unit */
	public void moveUnit(Player player, Unit unit, Point destination)
	{
		// TODO - move unit only if it belongs to the player
		// TODO - remove old unitMove if there is one
		if (map.units.indexOf(unit) >= 0)
		{
			System.out.println(unit);
			System.out.println(map.units);
			this.movingUnits.add(
					new UnitMove(map.units.get(map.units.indexOf(unit)), destination));
		}
		else
		{
			System.out.println("unit not in array");
		}
	}

	/** Queue Multiple units: calls moveUnit on each one.
	 * 
	 * @param player
	 * @param units
	 * @param destination */
	public void moveUnits(Player player, ArrayList<Unit> units, Point destination)
	{
		for (Unit unit : units)
		{
			moveUnit(player, unit, destination);
		}
	}

	/** Move a player.
	 * 
	 * @param player to move
	 * @param move movement direction
	 * @param currentLocation player's location as of last
	 *            update */
	public void movePlayer(Player player, MovementDirection move, Point currentLocation)
	{
		if (player.playerType == PlayerType.HS)
		{
			player.direction = move;
			player.x = currentLocation.getX();
			player.y = currentLocation.getY();
		}
	}

	/** Add a GameListener to this game room.
	 * 
	 * @param listener */
	public void addListener(GameListener listener)
	{
		listeners.add(listener);
	}

	/** Spawn a <unitType> unit from <spawningBuilding>.
	 * 
	 * @param spawningBuilding
	 * @param unitType */
	public void spawnUnit(Building spawningBuilding, String unitType)
	{
		// TODO - make unit owner same as building owner
		System.out.println(spawningBuilding);
		System.out.println(map.buildings);
		Unit unit = new Unit(unitType);
		if (canAffordUnit(unit) && map.buildings.indexOf(spawningBuilding) >= 0)
		{
			if (map.buildings.get(map.buildings.indexOf(spawningBuilding)).spawn(unit,
					chooseUnitSpawnPoint(spawningBuilding.getX(),
							spawningBuilding.getY())))
			{
				deductCost(unit);
				map.units.add(unit);
				for (GameListener listener : listeners)
				{
					listener.addUnit(unit);
				}

			}

		}
	}

	/** Finds an advailable location for the unit to spawn in.
	 * 
	 * @param x
	 * @param y
	 * @return */
	public Point chooseUnitSpawnPoint(int x, int y)
	{
		Point p = null;
		boolean check = false;
		Random r = new Random();
		int xCheck = 0;
		int yCheck = 0;
		while (!check)
		{
			xCheck = r.nextInt(100);
			yCheck = r.nextInt(100);
			if (r.nextBoolean())
			{
				xCheck = xCheck * -1;
			}
			if (r.nextBoolean())
			{
				yCheck = yCheck * -1;
			}
			if (r.nextBoolean())
			{
				if (r.nextBoolean())
				{
					xCheck += x;
					yCheck += y;
				}
				else
				{
					xCheck += x + 20;
					yCheck += y;
				}
			}
			else
			{
				if (r.nextBoolean())
				{
					xCheck += x;
					yCheck += y + 20;
				}
				else
				{
					xCheck += x + 20;
					yCheck += y + 20;
				}
			}
			check = checkPosition(xCheck, yCheck, 20, 20);
		}
		p = new Point(xCheck, yCheck);
		return p;
	}

	/** Check if there is a building overlapping the area covered
	 * by the rectangle made by x, y, w, and h.
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @param w width
	 * @param h height
	 * @return */
	public boolean checkPosition(int x, int y, int w, int h)
	{
		boolean check = true;
		for (int i = 0; i < map.buildings.size(); i++)
		{
			Building current = map.buildings.get(i);
			if (Math.abs(x - current.getX()) < w / 2 + current.getWidth() / 2 + 2
					&& Math.abs(y - current.getY()) < h / 2 + current.getHeight() / 2 + 2)
			{
				check = false;
				break;
			}
		}
		return check;
	}
}
