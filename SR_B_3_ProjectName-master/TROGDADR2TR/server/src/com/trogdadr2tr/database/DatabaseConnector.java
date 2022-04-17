package com.trogdadr2tr.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.trogdadr2tr.resources.Building;
import com.trogdadr2tr.resources.GameMap;

/** Connection to the database, store and fetch data.
 * 
 * @author Colt Rogness, Nick Schmidt */
public class DatabaseConnector
{

	/** Singleton variable */
	private static DatabaseConnector instance;

	/** Database, location and sign in info. */
	private MysqlDataSource database;

	/** Connection to the database. */
	private Connection dbConnection;

	/** Initialize the database and set up the connection.
	 * 
	 * @throws SQLException */
	public DatabaseConnector() throws SQLException
	{
		database = new MysqlDataSource();
		database.setUser("dbu309srb3");
		database.setPassword("GaTax1EX");
		database.setServerName("mysql.cs.iastate.edu");
		database.setDatabaseName("db309srb3");

		dbConnection = database.getConnection();
		System.out.println("database " + database + " connected @ " + dbConnection);
		// connectToDatabase();
	}

	/** Singletons boi. Get the only instance.
	 * 
	 * @return */
	public static DatabaseConnector getInstance()
	{
		if (instance == null)
		{
			try
			{
				instance = new DatabaseConnector();
			}
			catch (SQLException e)
			{
				return null;
			}
		}
		return instance;
	}

	/** Check login against database.
	 * 
	 * @param username attempted username
	 * @param password attempted password
	 * @return if login and password are correct */
	public Boolean submitLogin(String username, String password)
	{
		String command = "SELECT * FROM Users WHERE Username =\'" + username
				+ "\' AND Password = \'" + password + "\'";
		try
		{
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(command);
			if (rs.next())
			{
				System.out.println("Correct Login");
				return true;
			}
			else
			{
				System.out.println("Incorrect Login");
				return false;
			}
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}

	/** Submit new user with given username and login. Fails if
	 * username already exists.
	 * 
	 * @param username new username
	 * @param password new password
	 * @return true if the user was created, false otherwise. */
	public Boolean submitProfile(String username, String password)
	{
		String command = "INSERT INTO Users(Username, Password) VALUES (\'" + username
				+ "\', \'" + password + "\')";
		// System.out.println(command);
		try
		{
			Statement stmt = dbConnection.createStatement();
			stmt.executeUpdate(command);
			return true;
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}

	/** Given an Arraylist of buildings will insert the buildings
	 * into the database. DO NOT send repeats ie. if a building
	 * was in a previous arraylist remove it before calling
	 * again. */
	public void submitBuildings(ArrayList<Building> buildings, int mapID)
	{
		for (int i = 0; i < buildings.size(); i++)
		{
			Building temp = buildings.get(i);
			try
			{
				Statement stmt = dbConnection.createStatement();
				String command =
						"INSERT INTO Buildings(MapID, BuildingName, BuildingH, BuildingW, BuildingHP, BuildingX, BuildingY"
								+ ") VALUES (" + mapID + ", \'" + temp.getName() + "\', "
								+ temp.getHeight() + ", "
								+ temp.getWidth() + ", " + temp.getHealth() + ", "
								+ temp.getX() + ", " + temp.getY()
								+ ")";
				System.out.println(command);
				stmt.executeUpdate(command);
			}
			catch (SQLException e)
			{
				System.out.println(e);
			}
		}
	}

	/** Will return all of the buildings in a database with the
	 * given mapID as an arraylist */
	public ArrayList<Building> grabBuildings(int mapID)
	{
		ArrayList<Building> buildings = new ArrayList<Building>();
		try
		{
			Statement stmt = dbConnection.createStatement();
			String command = "SELECT BuildingName, BuildingX, BuildingY, BuildingH,"
					+ "BuildingW, BuildingHP FROM Buildings WHERE MapID =" + mapID;
			ResultSet rs = stmt.executeQuery(command);
			while (rs.next())
			{
				// buildings.add(
				// new Building(rs.getDouble("BuildingX"),
				// rs.getDouble("BuildingY"),
				// rs.getDouble("BuildingW"),
				// rs.getDouble("BuildingH"),
				// rs.getDouble("BuildingHP"),
				// rs.getString("BuildingName")));
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		// System.out.println("buildings = " + buildings);
		return buildings;
	}

	/** Upload map to the database.
	 * 
	 * @param map to upload
	 * @param name of the map */
	public void submitMap(GameMap map, String name)
	{

		try
		{

			Statement stmt = dbConnection.createStatement();

			String command = "INSERT INTO Maps(mapName) VALUES ('" + name + "')";

			stmt.executeUpdate(command);

			System.out.println("Made Past Insert");

			stmt = dbConnection.createStatement();

			command = "CREATE TABLE " + name
					+ "Buildings(buildingID INT, buildingX INT, buildingY INT, buildingType VARCHAR"

					+ "(255), PRIMARY KEY(buildingID));";

			System.out.println("Made Past Create");

			stmt.execute(command);

			for (int i = 0; i < map.buildings.size(); i++)
			{

				Building temp = map.buildings.get(i);

				stmt = dbConnection.createStatement();

				command = "INSERT INTO " + name
						+ "Buildings(buildingID, buildingX, buildingY, buildingType) VALUES"

						+ " (" + i + ", " + temp.getX() + ", " + temp.getY() + ", '"
						+ temp.getName() + "')";

				stmt.executeUpdate(command);

			}

		}

		catch (SQLException e)
		{

			System.out.println(e);

		}

	}

	/** Download map from the database.
	 * 
	 * @param mapName name of the map to download
	 * @return map */
	public GameMap loadMap(String mapName)
	{

		GameMap map = new GameMap();

		try
		{

			Statement stmt = dbConnection.createStatement();

			String command = "SELECT * FROM " + mapName + "Buildings";

			ResultSet rs = stmt.executeQuery(command);

			while (rs.next())
			{
				map.addBuilding(new Building(rs.getInt("buildingX"),
						rs.getInt("buildingY"), rs.getString("buildingType")));

			}

		}

		catch (SQLException e)
		{

			System.out.println(e);

		}

		return map;

	}

}
