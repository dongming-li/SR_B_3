package game;

public class Unit 
{
	public double xPos;
	public double yPos;
	private double health;
	private double speed;
	private double damage;
	private double range; 
	private String name;
	
	public Unit(double x, double y, double hp, double s, double dmg, double r, String n)
	{
		xPos = x;
		yPos = y;
		health = hp;
		speed = s;
		damage = dmg;
		range = r;
		name = n;
	}
	
	public double getHealth()
	{
		return health;
	}
	
	public double getSpeed()
	{
		return speed;
	}
	
	public double getDamage()
	{
		return damage;
	}
	
	public double getRange()
	{
		return range;
	}
	
	public String getName()
	{
		return name;
	}
	
}
