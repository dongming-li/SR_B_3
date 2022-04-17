package coltGUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import com.trogdadr2tr.game.*;
import com.trogdadr2tr.resources.*;
import com.trogdadr2tr.socket.Client;

public class GameWindow extends JPanel
{

	/** yeah java */
	private static final long serialVersionUID = 1L;

	public ClientGameLogic clientActor;

	public GameWindow(ClientGameLogic clientActor)
	{
		this.clientActor = clientActor;
		this.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseClicked(MouseEvent e)
			{
				switch (e.getButton())
				{
					case MouseEvent.BUTTON1:
						if (clientActor.selected != null
								&& clientActor.selected instanceof String)
						{
							String str = (String) clientActor.selected;
							if (str.startsWith("Place"))
							{
								str = str.substring(5).trim();
								Client.getInstance().updater.placeBuilding(str,
										new Point(10, 10));
								clientActor.selected = null;
							}
						}
						else
						{
							// TODO - select what was clicked on
						}
						break;
					case MouseEvent.BUTTON2:
						if (clientActor.selected == null)
						{
							// do nothing
							break;
						}
						else if (clientActor.selected instanceof Unit)
						{
							// TODO - move unit
							Client.getInstance().updater
									.moveUnit((Unit) clientActor.selected, new Point(e.getPoint().x, e.getPoint().y));
						}
						break;
					case MouseEvent.BUTTON3:
						break;
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

	public void paintComponent(Graphics g)
	{
		int mapOffsetX = (int) (getWidth() / 2 - clientActor.centerScreen.getX());
		int mapOffsetY = (int) (getHeight() / 2 - clientActor.centerScreen.getY());
		setBackground(Color.BLACK);
		g.setColor(Color.white);
		g.fillRect(mapOffsetX, mapOffsetY, clientActor.map.getWidth(),
				clientActor.map.getHeight());
		// g.drawRect(mapOffsetX, mapOffsetY,
		// gameMap.getWidth(),
		// gameMap.getHeight());
		g.setColor(Color.GREEN);
		for (Building b : clientActor.map.buildings)
		{
			// g.setColor(b.getColor());
			g.fillRect((int) b.getX() + mapOffsetX, (int) b.getY() + mapOffsetY,
					(int) b.getWidth(),
					(int) b.getHeight());
		}
		g.setColor(Color.BLUE);
		for (Unit u : clientActor.map.units)
		{
			g.fillOval((int) u.getX() + mapOffsetX - 5,
					(int) u.getY() + mapOffsetY - 5,
					10,
					10);
		}

	}
}
