package com.trogdadr2tr.game;

/**
 * Created by Danny on 12/1/2017.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import com.trogdadr2tr.resources.*;

public class GameRoom extends Thread implements Serializable
{
    /** GameRoom MK I */
    private static final long serialVersionUID = 1L;

    /** This is a list of game listeners */
    private transient CopyOnWriteArrayList<GameListener> listeners;

    /** This is the resource time */
    private transient Timer resourceTimer;

    /** The RTS player */
    private Player rtsPlayer;

    /** List of Hack-'n-Slash players */
    private CopyOnWriteArrayList<Player> HSPlayers;

    /** The game map */
    private GameMap map;

    /** List of unit movements */
    private volatile CopyOnWriteArrayList<UnitMove> movingUnits;

    /** List of unit attacks */
    private volatile CopyOnWriteArrayList<UnitAttack> attackingUnits;

    /** List of defensive buildings */
    private volatile CopyOnWriteArrayList<BuildingAttack> defensiveBuildings;

    /** List of resource buildings */
    private volatile CopyOnWriteArrayList<Building> resourceBuildings;

    /** Amount of wood */
    private int wood;

    /** Amount of gold */
    private int gold;

    /** Amount of stone */
    private int stone;

    /** Amount of food */
    private int food;

    /** Boolean if the game is on run */
    private Boolean runGame;

    /** Name of the room name */
    private String roomName;

    /** Name of the unsecured password */
    private String unsecurePassword;

    /** Max Hack-n'-Slash players */
    private static final int playerCap = 4;

    /**
     * The game room for the games
     * @param rtsPlayer the RTS player received from the caller
     * @param roomName The name of the room received from the caller
     */
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

    /**
     * Starts the game and sends the map
     */
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

    /**
     * Kills everyone and deletes the room
     */
    public void close()
    {
        resourceTimer.cancel();
        // TODO - kill everyone, delete room
    }

    /**
     * Checks to see if all players are ready
     * @return True if all players are ready, or false if they are not
     */
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

    /**
     * Checks to see if the building fit
     * @param building
     * @return
     */
    private Boolean buildingFits(Building building)
    {
        // TODO - do some math in the map.
        return true;
    }

    /**
     * Checks to see if you are able to afford the building
    * @param building The value for the building received from the caller
     * @return True if you have enough resources, false if you do not
    */
    private Boolean canAffordBuilding(Building building)
    {
        return (gold > building.getGoldCost() && wood > building.getWoodCost()
                && food > building.getFoodCost() && stone > building.getStoneCost());
    }

    /**
     *  Checks to see if you are able to afford the unit
     * @param unit The value for the unit received from the caller
     * @return True if you have enough resources, false if you do not
     */
    private Boolean canAffordUnit(Unit unit)
    {
        return (gold > unit.getGoldCost() && wood > unit.getWoodCost()
                && food > unit.getFoodCost() && stone > unit.getStoneCost());
    }

    /**
     * Gets the room name
     * @return the room name
     */
    public String getRoomName()
    {
        return roomName;
    }

    /**
     * Sets the room name
     * @param roomName The value for room name received from the caller
     */
    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
    }

    /**
     * Gets the unsecured password
     * @return the unsecured password
     */
    public String getUnsecurePassword()
    {
        return unsecurePassword;
    }

    /**
     * sets the unsecured password
     * @param unsecurePassword The value for the unsecured password received from the caller
     */
    public void setUnsecurePassword(String unsecurePassword)
    {
        this.unsecurePassword = unsecurePassword;
    }

    /**
     * Deducts the amount of resources for choosing a building
     * @param building The value for building received from the caller
     */
    private void deductCost(Building building)
    {
        gold -= building.getGoldCost();
        wood -= building.getWoodCost();
        food -= building.getFoodCost();
        stone -= building.getStoneCost();
    }

    /**
     * Deducts the amount of resources for choosing a unit
     * @param unit The value for unit received from the caller
     */
    private void deductCost(Unit unit)
    {
        gold -= unit.getGoldCost();
        wood -= unit.getWoodCost();
        food -= unit.getFoodCost();
        stone -= unit.getStoneCost();
    }

    /**
     * updates the game room
     * @param room The value for room received from the caller
     */
    public void update(GameRoom room)
    {
        unsecurePassword = room.unsecurePassword;
        // TODO - other changes?
    }

    /**
     * Joining the room
     * @param player The value for the amount of players received from the caller
     * @throws IllegalStateException
     */
    public void joinRoom(Player player)
            throws IllegalStateException
    {
        if (HSPlayers.size() >= playerCap)
        {
            throw new IllegalStateException();
        }
        HSPlayers.add(player);
    }

    /**
     * Leaving a room
     * @param player The value for the player received from the caller
     */
    public void leaveRoom(Player player)
    {
        if (rtsPlayer == player)
        {
            rtsPlayer = null;
            close();
        }
        HSPlayers.remove(player);
    }

    /**
     * Sends a message
     * @param message The value for the messsage received from the caller
     */
    public void sendMessage(Message message)
    {
        for (GameListener listener : listeners)
        {
            listener.sendMessage(message);
        }
    }

    /**
     * Places a building at a point
     * @param player The value for player received from the caller
     * @param buildingType The value for the building type received from the caller
     * @param location The value for X and Y in location received from the caller
     */
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

    /**
     * Deleted building if it belongs to a player
     * @param player The value for t he player received from the caller
     * @param building The value for the building received from the caller
     */
    public void deleteBuilding(Player player, Building building)
    {
        // TODO - delete building if owned by player
    }

    /**
     * Moves the unit only if the player owns that unit
     * @param player The value for the player received from the caller
     * @param unit The value for the unit received from the caller
     * @param destination The value for for the new destination received from the caller
     */
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
        else {
            System.out.println("unit not in array");
        }
    }

    /**
     * Moves all teh units to a new location
     * @param player The value for player received from the caller
     * @param units The value for units received from the caller
     * @param destination The value for the destination received from the caller
     */
    public void moveUnits(Player player, ArrayList<Unit> units, Point destination)
    {
        for (Unit unit : units)
        {
            moveUnit(player, unit, destination);
        }
    }

    /**
     * The value for player received from the caller
     * @param player The value for player received from the caller
     * @param move The value for direction movment received from the caller
     * @param currentLocation The value for the current location of a player received from the caller
     */
    public void movePlayer(Player player, MovementDirection move, Point currentLocation)
    {
        if (player.playerType == PlayerType.HS)
        {
            player.direction = move;
            player.x = currentLocation.getX();
            player.y = currentLocation.getY();
        }
    }

    /**
     * Adds a listner
     * @param listener The value for a listener received from the caller
     */
    public void addListener(GameListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Spawns a new unit from a building that belongs to a certain player
     * @param spawningBuilding The value for spawned building received from the caller
     * @param unitType The value for unit type received from the caller
     */
    public void spawnUnit(Building spawningBuilding, String unitType)
    {
        // TODO - make unit owner same as building owner
        System.out.println(spawningBuilding);
        System.out.println(map.buildings);
        Unit unit = new Unit(unitType);
        if (canAffordUnit(unit) && map.buildings.indexOf(spawningBuilding) >= 0)
        {
            if (map.buildings.get(map.buildings.indexOf(spawningBuilding)).spawn(unit))
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
}
