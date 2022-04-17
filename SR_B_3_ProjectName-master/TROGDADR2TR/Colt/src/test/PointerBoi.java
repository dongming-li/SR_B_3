package test;

public class PointerBoi
{

	public static void main(String[] args)
	{
		int butts = 10;
		PointerBoiMKII ayy = new PointerBoiMKII(butts);
		ayy.printer();
		butts = 100;
		ayy.printer();
	}
}

class PointerBoiMKII
{

	private int cancer;

	public PointerBoiMKII(int can)
	{
		cancer = can;
	}

	public void printer()
	{
		System.out.println(cancer);
	}

}
