package com.trogdadr2tr.GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.trogdadr2tr.game.RTSPlayer;
import com.trogdadr2tr.resources.Building;
import com.trogdadr2tr.resources.Point;
import com.trogdadr2tr.socket.Client;

/** @author Connor Brown */
public class rtsScreen extends JFrame
{

	/** The bar that shrinks whenever the player takes damage */
	public JPanel damageBar;

	/** The maximum amount of troops you can have */
	public int unitCap = 10;

	/** The current number of troops you have */
	public int unitCount = 0;

	/** The amount of gold the player currently has */
	public int gold = 10;

	/** The amount of wood the player currently has */
	public int wood = 10;

	/** The amount of food the player currently has */
	public int food = 10;

	/** The amount of stone the player currently has */
	public int stone = 10;

	/** The max amount of health the player has */
	public int health = 1000;

	/** The amount of damage the player has taken */
	public int damage = 0;

	/** The player that this screen is shown to */
	public RTSPlayer player;

	/** References where to update the gold variable onto the
	 * screen */
	public JLabel goldLabel;

	/** References where to update the food variable onto the
	 * screen */
	public JLabel foodLabel;

	/** References where to update the wood variable onto the
	 * screen */
	public JLabel woodLabel;

	/** References where to update the stone variable onto the
	 * screen */
	public JLabel stoneLabel;

	/** References where to update the unitCap variable onto the
	 * screen */
	public JLabel unitCapLabel;

	/** References where to update the unitCount variable */
	public JLabel unitCountLabel;

	/** The size of the world in pixels */
	public int worldSize = 5000;

	/** This is the game window, which can be referenced */
	public JScrollPane gameWindowScrollPane;

	/** Why you want this eclipse??? */
	private static final long serialVersionUID = 1L;

	/** Contains everything visible on the screen */
	public Container screenContainer;

	/** Makes sure everything is visible */
	public void update()
	{
		getContentPane().setVisible(false);
		getContentPane().setVisible(true);
	}

	/** This creates the game screen for the RTSPlayer object
	 * given
	 * 
	 * @param player The RTSPlayer object for which the game
	 *            screen is created */
	public rtsScreen(RTSPlayer player)
	{
		// Sets the defaults for the screen
		this.player = player;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);

		// Adds all the different tabs to the screen
		// JPanel j = new JPanel();
		// setContentPane(j);
		// getContentPane().setLayout(null);
		screenContainer = buildBackground();
		setVisible(true);
		addResourcesTab(screenContainer);
		addBuildingsTab(screenContainer);
		addHealthBar(screenContainer);
		addBottomPart(screenContainer);
		// Client.getInstance().updater.placeBuilding("Base",
		// new Point(50, 50));
		
		gameWindowScrollPane.getHorizontalScrollBar().setValue(gameWindowScrollPane.getHorizontalScrollBar().getMaximum());
		gameWindowScrollPane.getVerticalScrollBar().setValue(gameWindowScrollPane.getVerticalScrollBar().getMaximum());
		
		update();
		// rtsScreen parent = this;

		// This is the keyListener for the escape menu
		this.addKeyListener(new KeyListener()
		{

			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					// addExitTab(parent);
					player.redraw();
				}
				/** For testing... don't get rid of yet */
				// if(e.getKeyCode() == KeyEvent.VK_B)
				// {
				// buildingTest();
				// }
			}

			@Override
			public void keyTyped(KeyEvent e)
			{
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
			}
		});

		// This is the listener for screen movement
		// this.addMouseMotionListener(new MouseMotionListener()
		// {
		//
		// @Override
		// public void mouseMoved(MouseEvent e)
		// {
		// // TODO - use another thread to move screen when
		// // mouse is near the edge. currently only updates
		// // when mouse is moving and near the edge.
		// int deltaPixels = 10;
		// if (System.currentTimeMillis()
		// - player.lastMoveUpdate > 20)
		// {
		// Boolean update = false;
		// if (e.getX() < 20)
		// {
		// player.currentX += deltaPixels;
		// update = true;
		// gameWindowScrollPane.getHorizontalScrollBar().setValue(gameWindowScrollPane.getHorizontalScrollBar().getValue()
		// - deltaPixels);
		// System.out.println("left");
		// }
		// else if (e.getX() > getWidth() - 20)
		// {
		// player.currentX -= deltaPixels;
		// update = true;
		// gameWindowScrollPane.getHorizontalScrollBar().setValue(gameWindowScrollPane.getHorizontalScrollBar().getValue()
		// + deltaPixels);
		// System.out.println("right");
		// }
		// if (e.getY() < 20)
		// {
		// player.currentY += deltaPixels;
		// update = true;
		// System.out.println("up");
		// }
		// else if (e.getY() > getHeight() - 20)
		// {
		// player.currentY -= deltaPixels;
		// update = true;
		// System.out.println("down");
		// }
		// if (update)
		// {
		// player.redraw();
		// player.lastMoveUpdate = System.currentTimeMillis();
		// }
		// }
		// }
		//
		// @Override
		// public void mouseDragged(MouseEvent e)
		// {
		// }
		// });

		// This is the listener for moving units
		// this.addMouseListener(new MouseListener()
		gameWindowScrollPane.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (player.selected != null)
				{
					System.out.println("Trying to move a unit");
					// TODO - calculate real destination
					double x = e.getX() + player.currentX;
					double y = e.getY() + player.currentY;
					System.out.println("Unit position: " + player.selected.getX() + "   " + player.selected.getY());
					System.out.println("Trying to move to point: " + x + "   " + y);
					Client.getInstance().updater.moveUnit(player.selected,
							new Point(x, y));
					player.unitsToMove.add(player.selected);
					player.unitsToMovePosition.add(new Point(x, y));
					player.selected = null;
				}
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

		});
	}

	/** For testing... don't get rid of yet */
	// public void buildingTest()
	// {
	// JLabel b = new JLabel();
	// String f = new
	// File("src/Images/building2.png").getAbsolutePath();
	// f = f.replace("\\", "/");
	// ImageIcon i = new ImageIcon(f);
	// Image image = i.getImage();
	// image = image.getScaledInstance(100, 100,
	// Image.SCALE_SMOOTH);
	// i = new ImageIcon(image);
	// b.setIcon(i);
	// b.setBounds(50, 50, i.getIconWidth(), i.getIconHeight());
	// b.setVisible(true);
	// add(b);
	// update();
	// }

	/** This is where the background (scrollable) image is
	 * created */
	public Container buildBackground()
	{
		Container c = new Container();
		JLabel container = new JLabel();
		String f = new File("src/Images/grassland.jpg").getAbsolutePath();
		f = f.replace("\\", "/");
		ImageIcon i = new ImageIcon(f);
		Image image = i.getImage();
		image = image.getScaledInstance(worldSize, worldSize, Image.SCALE_SMOOTH);
		i = new ImageIcon(image);
		container.setIcon(i);
		container.setVisible(true);
		container.setBounds(0, 0, worldSize, worldSize);
		gameWindowScrollPane = new JScrollPane(container);
		gameWindowScrollPane.setBounds(0, 20, getWidth(),
				getHeight() - getHeight() / 8 - 24);
		gameWindowScrollPane.setBorder(new LineBorder(Color.black));
		gameWindowScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		gameWindowScrollPane.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		c.add(gameWindowScrollPane);
		this.setContentPane(c);
		rtsScreen parent = this;
		// This is the keyListener for the escape menu
		addKeyListener(new KeyListener()
		{

			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					addExitTab(c, parent);
					player.redraw();
				}
				/** For testing... don't get rid of yet */
				// if(e.getKeyCode() == KeyEvent.VK_B)
				// {
				// buildingTest();
				// }
			}

			@Override
			public void keyTyped(KeyEvent e)
			{
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
			}
		});

		gameWindowScrollPane.addMouseMotionListener(new MouseMotionListener()
		{

			@Override
			public void mouseMoved(MouseEvent e)
			{
				// TODO - use another thread to move screen when
				// mouse is near the edge. currently only updates
				// when mouse is moving and near the edge.
				int deltaPixels = 10;
				if (System.currentTimeMillis()
						- player.lastMoveUpdate > 20)
				{
					Boolean update = false;
					if (e.getX() < 20 && player.currentX > 0)
					{
						player.currentX -= deltaPixels;
						update = true;
						gameWindowScrollPane.getHorizontalScrollBar().setValue(
								gameWindowScrollPane.getHorizontalScrollBar().getValue()
										- deltaPixels);
					}
					else if (e.getX() > getWidth() - 20 && player.currentX < worldSize - getWidth())
					{
						player.currentX += deltaPixels;
						update = true;
						gameWindowScrollPane.getHorizontalScrollBar().setValue(
								gameWindowScrollPane.getHorizontalScrollBar().getValue()
										+ deltaPixels);
					}
					if (e.getY() < 20 && player.currentY > 0)
					{
						player.currentY -= deltaPixels;
						update = true;
						gameWindowScrollPane.getVerticalScrollBar().setValue(
								gameWindowScrollPane.getVerticalScrollBar().getValue()
										- deltaPixels);
					}
					else if (e.getY() > getHeight() - 20 && player.currentY < worldSize - getHeight())
					{
						player.currentY += deltaPixels;
						update = true;
						gameWindowScrollPane.getVerticalScrollBar().setValue(
								gameWindowScrollPane.getVerticalScrollBar().getValue()
										+ deltaPixels);
					}
					if (update)
					{
						player.redraw();
						player.lastMoveUpdate = System.currentTimeMillis();
					}
				}
			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
			}
		});

		getContentPane().addMouseMotionListener(new MouseMotionListener()
		{

			@Override
			public void mouseMoved(MouseEvent e)
			{
				// TODO - use another thread to move screen when
				// mouse is near the edge. currently only updates
				// when mouse is moving and near the edge.
				int deltaPixels = 10;
				if (System.currentTimeMillis()
						- player.lastMoveUpdate > 20)
				{
					Boolean update = false;
					if (e.getX() < 20 && player.currentX > 0)
					{
						player.currentX -= deltaPixels;
						update = true;
						gameWindowScrollPane.getHorizontalScrollBar().setValue(
								gameWindowScrollPane.getHorizontalScrollBar().getValue()
										- deltaPixels);
					}
					else if (e.getX() > getWidth() - 20 && player.currentX < worldSize - getWidth())
					{
						player.currentX += deltaPixels;
						update = true;
						gameWindowScrollPane.getHorizontalScrollBar().setValue(
								gameWindowScrollPane.getHorizontalScrollBar().getValue()
										+ deltaPixels);
					}
					if (e.getY() < 20 && player.currentY > 0)
					{
						player.currentY -= deltaPixels;
						update = true;
						gameWindowScrollPane.getVerticalScrollBar().setValue(
								gameWindowScrollPane.getVerticalScrollBar().getValue()
										- deltaPixels);
					}
					else if (e.getY() > getHeight() - 20 && player.currentY < worldSize - getHeight())
					{
						player.currentY += deltaPixels;
						update = true;
						gameWindowScrollPane.getVerticalScrollBar().setValue(
								gameWindowScrollPane.getVerticalScrollBar().getValue()
										+ deltaPixels);
					}
					if (update)
					{
						player.redraw();
						player.lastMoveUpdate = System.currentTimeMillis();
					}
				}
			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
			}
		});

		return c;

		// String f = new
		// File("src/Images/grassland.jpg").getAbsolutePath();
		// f = f.replace("\\", "/");
		// File fi = new File(f);
		// BufferedImage k;
		// try {
		// k = ImageIO.read(fi);
		// k.getScaledInstance(5000, 5000, Image.SCALE_SMOOTH);
		// this.setContentPane(new ImagePanel(k));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// setVisible(true);
	}

	/** This is shown when the player wins the game */
	public void WinGame()
	{
		JLabel lose = new JLabel();
		int width = getWidth();
		int height = getHeight() / 10;
		lose.setBounds(getWidth() / 2 - width / 2, getHeight() / 2 - height / 2, width,
				height);
		lose.setHorizontalAlignment(SwingConstants.CENTER);
		lose.setFont(new Font("Winner", Font.BOLD, lose.getHeight()));
		lose.setText("You Win");
		lose.setVisible(true);

		getContentPane().add(lose);
		update();
	}

	/** This is shown when the player loses the game */
	public void LoseGame()
	{
		JLabel lose = new JLabel();
		int width = getWidth();
		int height = getHeight() / 10;
		lose.setBounds(getWidth() / 2 - width / 2, getHeight() / 2 - height / 2, width,
				height);
		lose.setHorizontalAlignment(SwingConstants.CENTER);
		lose.setFont(new Font("Loser", Font.BOLD, lose.getHeight()));
		lose.setText("You Lose");
		lose.setVisible(true);

		getContentPane().add(lose);
		update();
	}

	/** This is to have a little gap between the building select
	 * tab and the bottom which makes it so the player can move
	 * the screen downwards
	 * 
	 * @param c The Container object which holds everything in the
	 *            screen */
	private void addBottomPart(Container c) // only here to make
											// the screen
	// move downwards
	{
		JPanel bottom = new JPanel();
		bottom.setBounds(0, getHeight() - 5, getWidth(), 5);
		bottom.setBackground(Color.black);
		// getContentPane().add(bottom);
		c.add(bottom);
	}

	/** This adds the escape menu
	 * 
	 * @param c The Container object which holds everything in the
	 *            screen
	 * @param parent The rtsScreen used to indicate how to get rid
	 *            of the escape menu
	 * @return A JScrollPane used as a window for the escape
	 *         menu */
	public JScrollPane addExitTab(Container c, rtsScreen parent)
	{
		JPanel exitPanel = new JPanel();
		exitPanel.setFocusable(false);
		JScrollPane scroll = new JScrollPane(exitPanel);

		int width = getWidth() / 6;
		int height = getHeight() / 4;
		int x = getWidth() / 2 - width / 2;
		int y = getHeight() / 2 - height / 2;

		// scroll.setBounds(x, y, width, height);
		scroll.setBounds(50, 50, 400, 400);
		scroll.getViewport().setView(exitPanel);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scroll.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBorder(new LineBorder(Color.black));

		exitPanel.setLayout(null);
		// exitPanel.setPreferredSize(new Dimension(width,
		// height));
		exitPanel.setBounds(x, y, width, height);
		exitPanel.setBorder(new LineBorder(Color.black, 2));
		exitPanel.setVisible(true);
		exitPanel.setBackground(Color.decode("0x00a80d"));

		int buttonWidth = width / 2;
		int buttonHeight = height / 4;
		int buttonX = width / 2 - buttonWidth / 2;
		int resumeY = height / 2 - (int) (buttonHeight * 1.2);
		int exitY = height / 2;

		JButton resume = new JButton("Resume");
		resume.setFocusable(false);
		resume.setBounds(buttonX, resumeY, buttonWidth, buttonHeight);
		resume.setActionCommand("Resume");
		resume.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (e.getActionCommand().equals("Resume"))
				{
					c.remove(exitPanel);
					update();
					scroll.setVisible(false);
					// parent.remove(exitPanel);
				}
			}
		});
		exitPanel.add(resume);

		JButton exit = new JButton("Exit");
		exit.setBounds(buttonX, exitY, buttonWidth, buttonHeight);
		exit.setActionCommand("Exit");
		exit.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (e.getActionCommand().equals("Exit"))
				{
					// TODO exit game
					System.exit(0);
				}
			}
		});
		exitPanel.add(exit);

		c.add(exitPanel, 0);
		update();

		return scroll;
	}

	/** Shrinks the damageBar by amount
	 * 
	 * @param amount the damage taken as an int value */
	public void takeDamage(int amount)
	{
		damage += amount;
		if (damage > health)
		{
			damage = health;
			LoseGame();
		}
		damageBar.setBounds(0, 0, (getWidth() * (health - damage)) / health, 20);
		damageBar.setVisible(false);
		damageBar.setVisible(true);
	}

	/** Adds the max health bar and damageBar to the screen
	 * 
	 * @param c The Container object which holds everything in the
	 *            screen */
	public void addHealthBar(Container c)
	{
		JPanel healthBar = new JPanel();
		// Rectangle fullBar = new Rectangle(getWidth() / 2 -
		// health / 2, 0, health, 20);
		Rectangle fullBar = new Rectangle(0, 0, getWidth(), 20);
		healthBar.setBounds(fullBar);
		healthBar.setBackground(Color.red);
		damageBar = new JPanel();
		// damageBar.setBounds(getWidth() / 2 - health / 2, 0,
		// health - damage, 20);
		damageBar.setBounds(0, 0, getWidth() - damage, 20);
		damageBar.setBackground(Color.blue);
		// getContentPane().add(damageBar);
		// getContentPane().add(healthBar);
		c.add(damageBar);
		c.add(healthBar);
	}

	/** Adds the resources tab to the screen. This displays the
	 * amount of each kind of resource the player has
	 * 
	 * @param c The Container object which holds everything in the
	 *            screen */
	public void addResourcesTab(Container c)
	{
		int x = 0;
		int height = getHeight() / 8;
		int y = getHeight() - height; // 39 is the size of my task
										// bar
		int width = getWidth() / 8;
		// int offset = 20;
		int preGold = gold;
		int preWood = wood;
		int preFood = food;
		int preStone = stone;

		JPanel panel = new JPanel();
		Container resources = new Container();

		// This is a scrollPane because that fixes the bug of
		// spawned buildings being put over this tab
		JScrollPane scroll = new JScrollPane(panel);

		panel.setBounds(x, y, width, height);
		panel.setPreferredSize(new Dimension(width, height));
		panel.setOpaque(true);
		panel.setBackground(Color.decode("0xDEB887"));

		JLabel goldLabel = new JLabel();
		goldLabel.setBounds(width / 2, height / 3, width / 2, height / 3);
		goldLabel.setText("Gold: " + Integer.toString(preGold));
		goldLabel.setHorizontalAlignment(JLabel.CENTER);
		goldLabel.setVisible(true);
		resources.add(goldLabel);
		goldLabel.setOpaque(true);
		goldLabel.setBackground(Color.decode("0xDEB887"));
		this.goldLabel = goldLabel;

		JLabel foodLabel = new JLabel();
		foodLabel.setBounds(0, height / 3, width / 2, height / 3);
		foodLabel.setText("Food: " + Integer.toString(preFood));
		foodLabel.setHorizontalAlignment(JLabel.CENTER);
		foodLabel.setVisible(true);
		resources.add(foodLabel);
		foodLabel.setOpaque(true);
		foodLabel.setBackground(Color.decode("0xDEB887"));
		this.foodLabel = foodLabel;

		JLabel woodLabel = new JLabel();
		woodLabel.setBounds(0, 2 * height / 3, width / 2, height / 3);
		woodLabel.setText("Wood: " + Integer.toString(preWood));
		woodLabel.setHorizontalAlignment(JLabel.CENTER);
		woodLabel.setVisible(true);
		resources.add(woodLabel);
		woodLabel.setOpaque(true);
		woodLabel.setBackground(Color.decode("0xDEB887"));
		this.woodLabel = woodLabel;

		JLabel stoneLabel = new JLabel();
		stoneLabel.setBounds(width / 2, 2 * height / 3, width / 2, height / 3);
		stoneLabel.setText("Stone: " + Integer.toString(preStone));
		stoneLabel.setHorizontalAlignment(JLabel.CENTER);
		stoneLabel.setVisible(true);
		resources.add(stoneLabel);
		stoneLabel.setOpaque(true);
		stoneLabel.setBackground(Color.decode("0xDEB887"));
		this.stoneLabel = stoneLabel;

		JLabel troops = new JLabel();
		troops.setBounds(0, 0, width / 2, height / 3);
		troops.setText("Units: " + unitCount);
		troops.setHorizontalAlignment(JLabel.CENTER);
		troops.setVisible(true);
		this.unitCountLabel = troops;
		resources.add(troops);
		troops.setOpaque(true);
		troops.setBackground(Color.decode("0xDEB887"));

		JLabel unitMax = new JLabel();
		unitMax.setBounds(width / 2, 0, width / 2, height / 3);
		unitMax.setText("Unit Cap: " + unitCap);
		unitMax.setHorizontalAlignment(JLabel.CENTER);
		unitMax.setVisible(true);
		this.unitCapLabel = unitMax;
		resources.add(unitMax);
		unitMax.setOpaque(true);
		unitMax.setBackground(Color.decode("0xDEB887"));

		scroll.getViewport().setView(resources);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scroll.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBorder(new LineBorder(Color.black));
		c.add(scroll);
		scroll.setBounds(x, y - 5, width, height);
		scroll.setOpaque(true);
		scroll.setBackground(Color.decode("0xDEB887"));

		scroll.addMouseMotionListener(new MouseMotionListener()
		{

			@Override
			public void mouseMoved(MouseEvent e)
			{
				// TODO - use another thread to move screen when
				// mouse is near the edge. currently only updates
				// when mouse is moving and near the edge.
				int deltaPixels = 10;
				if (System.currentTimeMillis()
						- player.lastMoveUpdate > 20)
				{
					Boolean update = false;
					if (e.getX() < 20 && player.currentX > 0)
					{
						player.currentX -= deltaPixels;
						update = true;
						gameWindowScrollPane.getHorizontalScrollBar().setValue(
								gameWindowScrollPane.getHorizontalScrollBar().getValue()
										- deltaPixels);
					}
					else if (e.getX() > getWidth() - 20 && player.currentX < worldSize - getWidth())
					{
						player.currentX += deltaPixels;
						update = true;
						gameWindowScrollPane.getHorizontalScrollBar().setValue(
								gameWindowScrollPane.getHorizontalScrollBar().getValue()
										+ deltaPixels);
					}
					if (e.getY() < 20 && player.currentY > 0)
					{
						player.currentY -= deltaPixels;
						update = true;
						gameWindowScrollPane.getVerticalScrollBar().setValue(
								gameWindowScrollPane.getVerticalScrollBar().getValue()
										- deltaPixels);
					}
					else if (e.getY() > getHeight() - 20 && player.currentY < worldSize - getHeight())
					{
						player.currentY += deltaPixels;
						update = true;
						gameWindowScrollPane.getVerticalScrollBar().setValue(
								gameWindowScrollPane.getVerticalScrollBar().getValue()
										+ deltaPixels);
					}
					if (update)
					{
						player.redraw();
						player.lastMoveUpdate = System.currentTimeMillis();
					}
				}
			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
			}
		});
	}

	/** This creates all the buildings that will be put into the
	 * scrollable window in the buildings tab
	 * 
	 * @return a Container object that contains all of the
	 *         different kinds of buildings */
	public Container createBuildings()
	{
		Container toAdd = new Container();
		int width = 300;
		int height = getHeight() / 8;
		toAdd.setPreferredSize(new Dimension(width * 10, height));

		JLabel food = new JLabel();
		food.setBounds(0, 0, width, height - 19); // 19 is the
													// size of the
													// scroll bar
		food.setText("Food Generator");
		food.setBorder(new LineBorder(Color.black));
		toAdd.add(food);
		food.setHorizontalAlignment(JLabel.CENTER);
		food.setOpaque(true);
		food.setBackground(Color.decode("0xDEB887"));

		JLabel gold = new JLabel();
		gold.setBounds(width, 0, width, height - 19);
		gold.setText("Gold Generator");
		gold.setBorder(new LineBorder(Color.black));
		toAdd.add(gold);
		gold.setHorizontalAlignment(JLabel.CENTER);
		gold.setOpaque(true);
		gold.setBackground(Color.decode("0xDEB887"));

		JLabel wood = new JLabel();
		wood.setBounds(width * 2, 0, width, height - 19);
		wood.setText("Wood Generator");
		wood.setBorder(new LineBorder(Color.black));
		toAdd.add(wood);
		wood.setHorizontalAlignment(JLabel.CENTER);
		wood.setOpaque(true);
		wood.setBackground(Color.decode("0xDEB887"));

		JLabel stone = new JLabel();
		stone.setBounds(width * 3, 0, width, height - 19);
		stone.setText("Stone Generator");
		stone.setBorder(new LineBorder(Color.black));
		toAdd.add(stone);
		stone.setHorizontalAlignment(JLabel.CENTER);
		stone.setOpaque(true);
		stone.setBackground(Color.decode("0xDEB887"));

		JLabel garrison = new JLabel();
		garrison.setBounds(width * 4, 0, width, height - 19);
		garrison.setText("Garrison");
		garrison.setBorder(new LineBorder(Color.black));
		toAdd.add(garrison);
		garrison.setHorizontalAlignment(JLabel.CENTER);
		garrison.setOpaque(true);
		garrison.setBackground(Color.decode("0xDEB887"));

		JLabel LRDefense = new JLabel();
		LRDefense.setBounds(width * 5, 0, width, height - 19);
		LRDefense.setText("Long Range Defense");
		LRDefense.setBorder(new LineBorder(Color.black));
		toAdd.add(LRDefense);
		LRDefense.setHorizontalAlignment(JLabel.CENTER);
		LRDefense.setOpaque(true);
		LRDefense.setBackground(Color.decode("0xDEB887"));

		JLabel SRDefense = new JLabel();
		SRDefense.setBounds(width * 6, 0, width, height - 19);
		SRDefense.setText("Short Range Defense");
		SRDefense.setBorder(new LineBorder(Color.black));
		toAdd.add(SRDefense);
		SRDefense.setHorizontalAlignment(JLabel.CENTER);
		SRDefense.setOpaque(true);
		SRDefense.setBackground(Color.decode("0xDEB887"));

		JLabel spawner = new JLabel();
		spawner.setBounds(width * 7, 0, width, height - 19);
		spawner.setText("Spawner");
		spawner.setBorder(new LineBorder(Color.black));
		toAdd.add(spawner);
		spawner.setHorizontalAlignment(JLabel.CENTER);
		spawner.setOpaque(true);
		spawner.setBackground(Color.decode("0xDEB887"));

		JLabel wallV = new JLabel();
		wallV.setBounds(width * 8, 0, width, height - 19);
		wallV.setText("Wall Vertical");
		wallV.setBorder(new LineBorder(Color.black));
		toAdd.add(wallV);
		wallV.setHorizontalAlignment(JLabel.CENTER);
		wallV.setOpaque(true);
		wallV.setBackground(Color.decode("0xDEB887"));

		JLabel wallH = new JLabel();
		wallH.setBounds(width * 9, 0, width, height - 19);
		wallH.setText("Wall Horizontal");
		wallH.setBorder(new LineBorder(Color.black));
		toAdd.add(wallH);
		wallH.setHorizontalAlignment(JLabel.CENTER);
		wallH.setOpaque(true);
		wallH.setBackground(Color.decode("0xDEB887"));

		food.addMouseListener(new MouseListener()
		{

			@Override
			public void mousePressed(MouseEvent e)
			{
				moving = true;
				clickedBuildingName = food.getText();
				System.out.println("clicked on food");
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
			}
		});

		gold.addMouseListener(new MouseListener()
		{

			@Override
			public void mousePressed(MouseEvent e)
			{
				moving = true;
				clickedBuildingName = gold.getText();
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
			}
		});

		wood.addMouseListener(new MouseListener()
		{

			@Override
			public void mousePressed(MouseEvent e)
			{
				moving = true;
				clickedBuildingName = wood.getText();
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
			}
		});

		stone.addMouseListener(new MouseListener()
		{

			@Override
			public void mousePressed(MouseEvent e)
			{
				moving = true;
				clickedBuildingName = stone.getText();
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
			}
		});

		garrison.addMouseListener(new MouseListener()
		{

			@Override
			public void mousePressed(MouseEvent e)
			{
				moving = true;
				clickedBuildingName = garrison.getText();
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
			}
		});

		LRDefense.addMouseListener(new MouseListener()
		{

			@Override
			public void mousePressed(MouseEvent e)
			{
				moving = true;
				clickedBuildingName = LRDefense.getText();
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
			}
		});

		SRDefense.addMouseListener(new MouseListener()
		{

			@Override
			public void mousePressed(MouseEvent e)
			{
				moving = true;
				clickedBuildingName = SRDefense.getText();
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
			}
		});

		spawner.addMouseListener(new MouseListener()
		{

			@Override
			public void mousePressed(MouseEvent e)
			{
				moving = true;
				clickedBuildingName = spawner.getText();
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
			}
		});

		wallV.addMouseListener(new MouseListener()
		{

			@Override
			public void mousePressed(MouseEvent e)
			{
				moving = true;
				clickedBuildingName = wallV.getText();
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
			}
		});

		wallH.addMouseListener(new MouseListener()
		{

			@Override
			public void mousePressed(MouseEvent e)
			{
				moving = true;
				clickedBuildingName = wallH.getText();
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
			}
		});

		return toAdd;
	}

	/** This indicates whether or not you are in the process of
	 * putting a building onto the screen */
	private boolean moving;

	/** This is the building name you are currently putting onto
	 * the screen */
	private String clickedBuildingName;

	/** This checks whether there is a building in the indicated
	 * space
	 * 
	 * @param x The x coord of the space
	 * @param y The y coord of the space
	 * @param w The width coord of the space
	 * @param h The height coord of the space
	 * @return true if position is not occupied by a building */
	public boolean checkPositionx(int x, int y, int w, int h)
	{
		boolean check = true;
		for (int i = 0; i < player.map.buildings.size(); i++)
		{
			Building current = player.map.buildings.get(i);
			if (Math.abs(x - current.getX()) < w / 2 + current.getWidth() / 2 + 2
					&& Math.abs(y - current.getY()) < h / 2 + current.getHeight() / 2 + 2)
			{
				check = false;
				break;
			}
		}
		return check;
	}

	/** This adds the building tab to the screen, which is a
	 * scrollable window
	 * 
	 * @param c The Container object which holds everything in the
	 *            screen */
	public void addBuildingsTab(Container c)
	{
		int x = getWidth() / 8 - 1; // The -1 makes it so that the
									// resources tab and this tab
									// don't have a double thick
									// line between them
		int height = getHeight() / 8;
		int y = getHeight() - height;
		int width = getWidth() - getWidth() / 8;

		JPanel buildings = new JPanel();

		buildings.setBounds(x, y, width, height);
		buildings.setPreferredSize(new Dimension(3000, height));
		buildings.setBackground(Color.decode("0xDEB887"));

		JScrollPane scroll = new JScrollPane(buildings);
		scroll.getViewport().setView(createBuildings());
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scroll.setBorder(new LineBorder(Color.black));
		c.add(scroll);
		scroll.setBounds(x, y - 5, width, height);
		scroll.setBackground(Color.decode("0xDEB887"));

		update();

		// This is a listener for spawning in a new building or
		// unit onto the screen
		gameWindowScrollPane.addMouseListener(new MouseListener()
		{

			// This is to spawn in a new building
			@Override
			public void mouseReleased(MouseEvent e)
			{
				if (moving == true)
				{
					System.out.println("tried");
					int x = e.getX() + player.currentX;
					int y = e.getY() + player.currentY;

					try
					{
						Client.getInstance().updater.placeBuilding(clickedBuildingName,
								new Point(x, y));
					}
					catch (IllegalStateException e1)
					{
					}
					player.redraw();
//					//Building b = new Building(x, y, clickedBuildingName);
//
//					if (gold < b.getGoldCost() || food < b.getFoodCost()
//							|| wood < b.getWoodCost() || stone < b.getStoneCost())
//					{
//						// System.out.println(
//						// "You don't have enough resources for
//						// this building.\n"
//						// + "It requires " + b.getGoldCost() + "
//						// gold, "
//						// + b.getFoodCost() + " food, " +
//						// b.getWoodCost()
//						// + " wood," + b.getStoneCost() + "
//						// stone.");
//					}

				}
				moving = false;
			}

			// This is to move a unit
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (player.unitMoving)
				{
					// player.units.get(player.unitIndex).setPosition(e.getX()
					// - player.currentX, e.getY() -
					// player.currentY);
					// index = player.unitIndex;

					// player.unitsToMove.add(index);
					// player.unitsToMovePosition.add(player.new
					// Point(e.getX()-player.currentX,
					// e.getY()-player.currentY));
					// loc1 = e.getX() - player.currentX;
					// loc2 = e.getY() - player.currentY;
					// player.unitsToMovePosition.add(new
					// Point(loc1, loc2));
					// player.unitsToMove.add(player.map.units.get(index));
					player.unitIndex = -1;
					// Thread move = new Thread(unitMove);
					// move.start();
					// moveUnit(index, e.getX()-player.currentX,
					// e.getY()-player.currentY);
				}
				player.unitMoving = false;
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
	}

	/** @author cmbrown */
	class ImagePanel extends JComponent
	{

		/** Eclipse wants this for Java swing stuff */
		private static final long serialVersionUID = 1L;

		/** The image this object holds */
		private Image image;

		/** Creates a new image panel object which contains the
		 * given image
		 * 
		 * @param image The Image this object contains */
		public ImagePanel(Image image)
		{
			this.image = image;
		}

		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(image, 0, 0, this);
		}
	}
}
