package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

/**
 * 
 * @author Connor Brown
 *
 */
public class ServerScreen extends JFrame
{
	public static ServerScreen parent;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) 
	{
		new ServerScreen();
	}
	
	public ServerScreen()
	{
		parent = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);
		
		addExitButton();
		addServerSelect();
	}
	
	public void addServerSelect()
	{
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(800, 800));
		Container c = new Container();
		JScrollPane scroll = new JScrollPane(panel);
				
		int GNwidth = 250;
		int baseHeight = 50;
		int otherWidth = 200;
		
		int serverNumber = 10;
		c.setPreferredSize(new Dimension((GNwidth+(3*otherWidth)), (baseHeight*(serverNumber + 1))));
		
		JLabel gameName = new JLabel();
		gameName.setBounds(0, 0, GNwidth, baseHeight);
		gameName.setText("Game Name");
		gameName.setVisible(true);
		gameName.setBorder(new LineBorder(Color.black));
		c.add(gameName);
		
		JLabel gameType = new JLabel();
		gameType.setBounds(GNwidth, 0, otherWidth, baseHeight);
		gameType.setText("Game Type");
		gameType.setVisible(true);
		gameType.setBorder(new LineBorder(Color.black));
		c.add(gameType);
		
		JLabel playerNumber = new JLabel();
		playerNumber.setBounds(GNwidth+otherWidth, 0, otherWidth, baseHeight);
		playerNumber.setText("Number of Players");
		playerNumber.setVisible(true);
		playerNumber.setBorder(new LineBorder(Color.black));
		c.add(playerNumber);
		
		JLabel isPrivate = new JLabel();
		isPrivate.setBounds(GNwidth+otherWidth+otherWidth, 0, otherWidth, baseHeight);
		isPrivate.setText("Private");
		isPrivate.setVisible(true);
		isPrivate.setBorder(new LineBorder(Color.black));
		c.add(isPrivate);
		
		addServers(c);
		
		scroll.setViewportView(c);
		scroll.setBorder(new LineBorder(Color.black));
		add(scroll);
		scroll.setBounds(0, 0, getWidth()*2/3, getHeight());
	}
	
	private void addServers(Container c)
	{
		int firstWidth = 250;
		int height = 50;
		
		int serverNumber = 10;
		for(int i = 0; i < serverNumber; i++)
		{
			JButton name = new JButton("NAME TEXT");
			name.setBounds(0, height*(i+1), firstWidth, height);
			c.add(name);
		}
	}
	
	public void addExitButton()
	{
		int width = getWidth()/16;
		int height = getHeight()/20;
		int x = getWidth() - width;
		int y = 0;
		
		JButton exit = new JButton("Exit");
		exit.setBounds(x, y, width, height);
		exit.setActionCommand("Exit");
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("Exit"))
				{
					parent.dispatchEvent(new WindowEvent(parent, WindowEvent.WINDOW_CLOSING));
				}
			}
		});
		getContentPane().add(exit);
	}
	
}
