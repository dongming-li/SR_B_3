package test;

import java.util.Scanner;

public class mathboi
{

	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		while (true)
		{
			System.out.print("Enter a numerator: ");
			int x = scan.nextInt();
			System.out.print("Enter a denominator: ");
			int y = scan.nextInt();
			double test = Math.sin(Math.PI * x / y);
			System.out.println("PI * " + x + " / " + y + " = " + test + ".");
			if (x == 69 && y == 69)
			{
				break;
			}
		}
		scan.close();
	}

}
