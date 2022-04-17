package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import game.RTSPlayer;
import structures.Building;

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
	public int health = 200;

	/** The amount of damage the player has taken */
	public int damage = 0;

	/** The player that this screen is shown to */
	public static RTSPlayer player;

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

	/** Why you want this eclipse??? */
	private static final long serialVersionUID = 1L;

	/** Makes sure everything is visible */
	public void update()
	{
		getContentPane().setVisible(false);
		getContentPane().setVisible(true);
	}

	/** This creates everything for the RTS players screen */
	public rtsScreen(RTSPlayer player)
	{
		// Sets the defaults for the screen
		rtsScreen.player = player;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);

		// Adds all the different tabs to the screen
		JPanel j = new JPanel();
		setContentPane(j);
		getContentPane().setLayout(null);
		setVisible(true);
		addResourcesTab();
		addBuildingsTab();
		addHealthBar();
		addBottomPart();
		update();
		rtsScreen parent = this;

		// This is the keyListener for the escape menu
		this.addKeyListener(new KeyListener()
		{

			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					addExitTab(parent);
					player.redraw();
				}
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
		this.addMouseMotionListener(new MouseMotionListener()
		{

			@Override
			public void mouseMoved(MouseEvent e)
			{
				if (e.getX() < 20)
				{
					player.moveLeft();
				}
				else if (e.getX() > getWidth() - 20)
				{
					player.moveRight();
				}
				if (e.getY() < 20)
				{
					player.moveUp();
				}
				else if (e.getY() > getHeight() - 20)
				{
					player.moveDown();
				}
			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
			}
		});
	}

	/** This is to have a little gap between the building select
	 * tab and the bottom which makes it so the player can move
	 * the screen downwards */
	private void addBottomPart() // only here to make the screen
									// move downwards
	{
		JPanel bottom = new JPanel();
		bottom.setBounds(0, getHeight() - 5, getWidth(), 5);
		bottom.setBackground(Color.black);
		getContentPane().add(bottom);
	}

	/** This adds the escape menu
	 * 
	 * @param parent The rtsScreen used to indicate how to get rid
	 *            of the escape menu
	 * @return A JScrollPane used as a window for the escape
	 *         menu */
	public JScrollPane addExitTab(rtsScreen parent)
	{
		JPanel exitPanel = new JPanel();
		exitPanel.setFocusable(false);
		JScrollPane scroll = new JScrollPane(exitPanel);

		int width = getWidth() / 6;
		int height = getHeight() / 4;
		int x = getWidth() / 2 - width / 2;
		int y = getHeight() / 2 - height / 2;

		scroll.setBounds(x, y, width, height);
		scroll.getViewport().setView(exitPanel);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scroll.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBorder(new LineBorder(Color.black));

		exitPanel.setLayout(null);
		exitPanel.setPreferredSize(new Dimension(width, height));
		exitPanel.setBorder(new LineBorder(Color.black, 2));
		exitPanel.setVisible(true);

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
					parent.dispose();
				}
			}
		});
		exitPanel.add(exit);

		add(scroll);
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
		}
		damageBar.setBounds(getWidth() / 2 - 100, 0, health - damage, 30);
		update();
	}

	/** Adds the max health bar and damageBar to the screen */
	public void addHealthBar()
	{
		JPanel healthBar = new JPanel();
		Rectangle fullBar = new Rectangle(getWidth() / 2 - 100, 0, 200, 30);
		healthBar.setBounds(fullBar);
		healthBar.setBackground(Color.red);
		damageBar = new JPanel();
		damageBar.setBounds(getWidth() / 2 - 100, 0, health - damage, 30);
		damageBar.setBackground(Color.blue);
		getContentPane().add(damageBar);
		getContentPane().add(healthBar);
	}

	/** Adds the resources tab to the screen. This displays the
	 * amount of each kind of resource the player has */
	public void addResourcesTab()
	{
		int x = 0;
		int height = getHeight() / 8;
		int y = getHeight() - height; // 39 is the size of my task
										// bar
		int width = getWidth() / 8;
		int offset = 20;
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

		JLabel goldLabel = new JLabel();
		goldLabel.setBounds(offset + width / 2, height / 3, width / 2, height / 3);
		goldLabel.setText("Gold: " + Integer.toString(preGold));
		goldLabel.setVisible(true);
		resources.add(goldLabel);
		this.goldLabel = goldLabel;

		JLabel foodLabel = new JLabel();
		foodLabel.setBounds(offset, height / 3, width / 2, height / 3);
		foodLabel.setText("Food: " + Integer.toString(preFood));
		foodLabel.setVisible(true);
		resources.add(foodLabel);
		this.foodLabel = foodLabel;

		JLabel woodLabel = new JLabel();
		woodLabel.setBounds(offset, 2 * height / 3, width / 2, height / 3);
		woodLabel.setText("Wood: " + Integer.toString(preWood));
		woodLabel.setVisible(true);
		resources.add(woodLabel);
		this.woodLabel = woodLabel;

		JLabel stoneLabel = new JLabel();
		stoneLabel.setBounds(offset + width / 2, 2 * height / 3, width / 2, height / 3);
		stoneLabel.setText("Stone: " + Integer.toString(preStone));
		stoneLabel.setVisible(true);
		resources.add(stoneLabel);
		this.stoneLabel = stoneLabel;

		JLabel troops = new JLabel();
		troops.setBounds(offset, 0, width / 2, height / 3);
		troops.setText("Units: " + unitCount);
		troops.setVisible(true);
		this.unitCountLabel = troops;
		resources.add(troops);

		JLabel unitMax = new JLabel();
		unitMax.setBounds(offset + width / 2, 0, width / 2, height / 3);
		unitMax.setText("Unit Cap: " + unitCap);
		unitMax.setVisible(true);
		this.unitCapLabel = unitMax;
		resources.add(unitMax);

		scroll.getViewport().setView(resources);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scroll.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBorder(new LineBorder(Color.black));
		add(scroll);
		scroll.setBounds(x, y - 5, width, height);
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

		JLabel gold = new JLabel();
		gold.setBounds(width, 0, width, height - 19);
		gold.setText("Gold Generator");
		gold.setBorder(new LineBorder(Color.black));
		toAdd.add(gold);

		JLabel wood = new JLabel();
		wood.setBounds(width * 2, 0, width, height - 19);
		wood.setText("Wood Generator");
		wood.setBorder(new LineBorder(Color.black));
		toAdd.add(wood);

		JLabel stone = new JLabel();
		stone.setBounds(width * 3, 0, width, height - 19);
		stone.setText("Stone Generator");
		stone.setBorder(new LineBorder(Color.black));
		toAdd.add(stone);

		JLabel garrison = new JLabel();
		garrison.setBounds(width * 4, 0, width, height - 19);
		garrison.setText("Garrison");
		garrison.setBorder(new LineBorder(Color.black));
		toAdd.add(garrison);

		JLabel LRDefense = new JLabel();
		LRDefense.setBounds(width * 5, 0, width, height - 19);
		LRDefense.setText("Long Range Defense");
		LRDefense.setBorder(new LineBorder(Color.black));
		toAdd.add(LRDefense);

		JLabel SRDefense = new JLabel();
		SRDefense.setBounds(width * 6, 0, width, height - 19);
		SRDefense.setText("Short Range Defense");
		SRDefense.setBorder(new LineBorder(Color.black));
		toAdd.add(SRDefense);

		JLabel spawner = new JLabel();
		spawner.setBounds(width * 7, 0, width, height - 19);
		spawner.setText("Spawner");
		spawner.setBorder(new LineBorder(Color.black));
		toAdd.add(spawner);

		JLabel wallV = new JLabel();
		wallV.setBounds(width * 8, 0, width, height - 19);
		wallV.setText("Wall Vertical");
		wallV.setBorder(new LineBorder(Color.black));
		toAdd.add(wallV);

		JLabel wallH = new JLabel();
		wallH.setBounds(width * 9, 0, width, height - 19);
		wallH.setText("Wall Horizontal");
		wallH.setBorder(new LineBorder(Color.black));
		toAdd.add(wallH);

		food.addMouseListener(new MouseListener()
		{

			@Override
			public void mousePressed(MouseEvent e)
			{
				moving = true;
				clickedBuildingName = food.getText();
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
	public boolean checkPosition(int x, int y, int w, int h)
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
	 * scrollable window */
	public void addBuildingsTab()
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

		JScrollPane scroll = new JScrollPane(buildings);
		scroll.getViewport().setView(createBuildings());
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scroll.setBorder(new LineBorder(Color.black));
		add(scroll);
		scroll.setBounds(x, y - 5, width, height);

		update();

		// This is a listener for spawning in a new building or
		// unit onto the screen
		addMouseListener(new MouseListener()
		{

			// This is to spawn in a new building
			@Override
			public void mouseReleased(MouseEvent e)
			{
				if (moving == true)
				{
					int x = e.getX() - player.currentX;
					int y = e.getY() - player.currentY;
					Building b = new Building(x, y, clickedBuildingName, player);
					int w = b.getWidth();
					int h = b.getHeight();

					boolean check1 = true;
					if (gold < b.getGoldCost() || food < b.getFoodCost()
							|| wood < b.getWoodCost() || stone < b.getStoneCost())
					{
						System.out.println(
								"You don't have enough resources for this building.");
						System.out.println("It requires " + b.getGoldCost() + " gold, "
								+ b.getFoodCost() + " food, " + b.getWoodCost() + " wood,"
								+ b.getStoneCost() + " stone.");
						check1 = false;
					}

					boolean check = checkPosition(x, y, w, h);

					if (check && check1)
					{
						player.map.addBuilding(b);
						gold -= b.getGoldCost();
						goldLabel.setText("Gold: " + gold);
						wood -= b.getWoodCost();
						woodLabel.setText("Wood: " + wood);
						food -= b.getFoodCost();
						foodLabel.setText("Food: " + food);
						stone -= b.getStoneCost();
						stoneLabel.setText("Stone: " + stone);
						player.redraw();

						if (b.getName() == "Garrison")
						{
							unitCap += 10;
							unitCapLabel.setText("Unit Cap: " + unitCap);
						}
					}
				}
				moving = false;
			}

			// This is to move a unit
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (player.unitMoving && player.unitIndex > -1)
				{
					// player.units.get(player.unitIndex).setPosition(e.getX()
					// - player.currentX, e.getY() -
					// player.currentY);
					player.unitMoving = false;
					int temp = player.unitIndex;
					player.unitIndex = -1;
					player.unitsToMove.add(temp);
					player.unitsToMovePosition.add(player.new Point(
							e.getX() - player.currentX, e.getY() - player.currentY));
					// moveUnit(temp, e.getX(), e.getY());
				}
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

	/** This moves a unit indicated by the index variable in the
	 * direction of its current position towards the indicated x
	 * and y coords
	 * 
	 * @param index The position in the ArrayList of units for the
	 *            unit to move
	 * @param x The x position of where the unit moves towards
	 * @param y The y position of where the unit moves towards
	 * @return True if the unit gets to the indicated x and y
	 *         position, false otherwise */
	public boolean moveUnit(int index, int x, int y)
	{
		int moveDistance = 20;
		if (System.currentTimeMillis() - player.lastUnitMoveUpdate > 70)
		{
			if (x < player.map.units.get(index).getX()) // left
			{
				int dis = Math.abs(player.map.units.get(index).getX() - x);
				if (dis < 50)
					player.map.units.get(index).setPosition(x,
							player.map.units.get(index).getY());
				else
					player.map.units.get(index).setPosition(
							player.map.units.get(index).getX() - moveDistance,
							player.map.units.get(index).getY());
			}
			else // right
			{
				int dis = Math.abs(player.map.units.get(index).getX() - x);
				if (dis < 50)
					player.map.units.get(index).setPosition(x,
							player.map.units.get(index).getY());
				else
					player.map.units.get(index).setPosition(
							player.map.units.get(index).getX() + moveDistance,
							player.map.units.get(index).getY());
			}
			if (y < player.map.units.get(index).getY()) // up
			{
				int dis = Math.abs(player.map.units.get(index).getY() - y);
				if (dis < 50)
					player.map.units.get(index)
							.setPosition(player.map.units.get(index).getX(), y);
				else
					player.map.units.get(index).setPosition(
							player.map.units.get(index).getX(),
							player.map.units.get(index).getY() - moveDistance);
			}
			else // down
			{
				int dis = Math.abs(player.map.units.get(index).getY() - y);
				if (dis < 50)
					player.map.units.get(index)
							.setPosition(player.map.units.get(index).getX(), y);
				else
					player.map.units.get(index).setPosition(
							player.map.units.get(index).getX(),
							player.map.units.get(index).getY() + moveDistance);
			}
			player.lastUnitMoveUpdate = System.currentTimeMillis();
			player.redraw();
		}
		if (distance(player.map.units.get(index).getX(),
				player.map.units.get(index).getY(), x, y) < 1)
			return true;
		else
			return false;
	}

	/** This calculates the distance from the fromX and fromY to
	 * the toX and toY
	 * 
	 * @param fromX Starting x position
	 * @param fromY Starting y position
	 * @param toX Finish x position
	 * @param toY Finish y position
	 * @return The distance from the indicated positions */
	public double distance(int fromX, int fromY, int toX, int toY)
	{
		return Math.sqrt((fromX - toX) * (fromX - toX) + (fromY - toY) * (fromY - toY));
	}
}
