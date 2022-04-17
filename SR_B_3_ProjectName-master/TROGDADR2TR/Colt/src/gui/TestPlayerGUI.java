package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import game.Building;
import game.GameMap;
import game.Player;
import game.Unit;

public class TestPlayerGUI extends JPanel
{

	/** Eclipse why you need? */
	private static final long serialVersionUID = 1L;

	public GameMap gameMap;

	public Player pc;

	private final int topBarHeight = 20;

	private final int topBarButtonWidth = 60;

	/** Player GUI while in game.
	 * 
	 * @param frame */
	public TestPlayerGUI(final WindowBuilder frame)
	{
		super();
		frame.clear();
		setBounds((int) frame.getBounds().getMinX(),
				(int) frame.getBounds().getMinY() + topBarHeight,
				(int) frame.getBounds().getWidth(),
				(int) frame.getBounds().getHeight() - topBarHeight);
		// this.setBorder(new LineBorder(Color.BLACK));
		frame.getContentPane().add(this);
		setVisible(true);
		// setOpaque(true);
		setBackground(Color.black);
		setForeground(Color.black);

		// init the map
		gameMap = new GameMap();
		pc = new Player(gameMap.startingX, gameMap.startingY, 100, 1, 10, 1, "Argetlam");
		gameMap.addUnit(pc);

		addMenus(frame);
		frame.addKeyListener(pc);
		frame.getContentPane().addKeyListener(pc);
		frame.requestFocus();
		this.addKeyListener(pc);
		frame.repaint();

		Timer time = new Timer(10, new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				pc.xPos += pc.direction.x;
				pc.yPos += pc.direction.y;
				frame.repaint();
			}
		});
		time.stop();
		time.start();
	}

	/** Add menus on the top of the game window.
	 * 
	 * @param frame */
	private void addMenus(final WindowBuilder frame)
	{
		class InGameGUI implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				switch (e.getActionCommand())
				{
					case "Quit":
						frame.buildMainScreen();
						break;
				}
			}
		}
		// add top of screen menus and bottom of screen display
		JButton button = new JButton("Quit");
		button.setActionCommand("Quit");
		button.addActionListener(new InGameGUI());
		button.setBounds((int) frame.getBounds().getMaxX() - topBarButtonWidth,
				(int) frame.getBounds().getMinY(),
				topBarButtonWidth, topBarHeight);
		// button.setFont(button.getFont().deriveFont((float)
		// 10));
		button.setVisible(true);
		frame.getContentPane().add(button);
	}

	public void paintComponent(Graphics g)
	{
		int mapOffsetX = (int) (getWidth() / 2 - pc.xPos);
		int mapOffsetY = (int) (getHeight() / 2 - pc.yPos);
		setBackground(Color.black);
		g.setColor(Color.white);
		g.fillRect(mapOffsetX, mapOffsetY, gameMap.getWidth(), gameMap.getHeight());
		// g.drawRect(mapOffsetX, mapOffsetY, gameMap.getWidth(),
		// gameMap.getHeight());
		g.setColor(Color.blue);
		for (Building b : gameMap.buildings)
		{
			// g.setColor(b.getColor());
			g.fillRect((int) b.getX() + mapOffsetX, (int) b.getY() + mapOffsetY,
					(int) b.getWidth(),
					(int) b.getHeight());
		}
		g.setColor(Color.red);
		for (Unit u : gameMap.units)
		{
			if (u == pc)
			{
				g.setColor(Color.green);
			}
			g.fillOval((int) u.xPos + mapOffsetX - 5, (int) u.yPos + mapOffsetY - 5, 10,
					10);
			if (u == pc)
			{
				g.setColor(Color.red);
			}
		}
	}

}
