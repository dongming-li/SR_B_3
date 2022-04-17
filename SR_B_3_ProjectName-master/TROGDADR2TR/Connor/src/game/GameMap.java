package game;

import java.util.ArrayList;

import structures.Building;
import units.Unit;

/**
 * Store all map data and perform any functions related to the map.
 * 
 * @author Colt Rogness
 *
 */
public class GameMap {

	private int width;
	
	private int height;
	
	public double startingX;
	
	public double startingY;
	
	//public ArrayList<Terrain> terrain;

	public ArrayList<Building> buildings;

	public ArrayList<Unit> units;

	public GameMap() {
		width = 1000;
		height = 1000;
		startingX = 500;
		startingY = 500;
		//terrain = new ArrayList<Terrain>();
		buildings = new ArrayList<Building>();
		units = new ArrayList<Unit>();
	}
	
	public void addBuilding(Building b)
	{
		buildings.add(b);
	}
	
	public void addUnit(Unit u) {
		units.add(u);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	// // public MapEntity[][] mapGrid;
	//
	// public ArrayList<ArrayList<MapEntity>> mapGrid;
	//
	// /**
	// * 3d ArrayList, first dimension is y, second x, and the third is units at
	// that
	// * tile.
	// */
	// public ArrayList<ArrayList<ArrayList<Unit>>> unitGrid;
	//
	// /**
	// * Construct a new map
	// */
	// public GameMap() {
	// int dimension = 1000;
	// // sorted as y, x, stack
	// mapGrid = new ArrayList<ArrayList<MapEntity>>(dimension);
	// unitGrid = new ArrayList<ArrayList<ArrayList<Unit>>>(dimension);
	// for (int n = 0; n < dimension; n++) {
	// mapGrid.add(new ArrayList<MapEntity>());
	// unitGrid.add(new ArrayList<ArrayList<Unit>>(dimension));
	// for (int m = 0; m < dimension; m++) {
	// unitGrid.get(n).add(new ArrayList<Unit>());
	// }
	// }
	// System.out.println(unitGrid.get(0).get(0).size());
	// System.out.println(mapGrid.get(0).size());
	// }
	//
	// /**
	// * Construct a map based on the specifications in the given filename.
	// *
	// * @param filename
	// * - name of the file used to generate the map
	// */
	// public GameMap(String filename) {
}
