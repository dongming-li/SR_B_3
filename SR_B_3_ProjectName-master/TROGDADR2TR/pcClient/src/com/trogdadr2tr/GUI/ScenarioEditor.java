package com.trogdadr2tr.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The Scenario Editor is a graphical coding language which allows for trigger conditions to be set.
 * When these conditions are met the given action will take place.
 * @author Nick Schmidt
 *
 */
public class ScenarioEditor extends JFrame
{

	/** yeah java */
	private static final long serialVersionUID = 1L;

	/**
	 * The MapEditorGUI tied to this Scenario Editor
	 */
	public MapEditorGUI mapEditorGUI;

	/**
	 * This
	 */
	private JPanel scenarioEditor;

	/**
	 * Glue for the GUI to pad
	 */
	private Box.Filler glue;

	/**
	 * Builds the ScenarioEditor on the given MapEditorGUI
	 * @param screen
	 */
	public ScenarioEditor(MapEditorGUI screen)
	{
		this.mapEditorGUI = screen;
		this.scenarioEditor = new JPanel();
		scenarioEditor.setLayout(new BoxLayout(scenarioEditor, BoxLayout.PAGE_AXIS));
		glue = (Box.Filler) Box.createVerticalGlue();
		glue.changeShape(glue.getMinimumSize(),
				new Dimension(0, Short.MAX_VALUE), // make glue
													// greedy
				glue.getMaximumSize());
		mapEditorGUI.getWindowBuilder().clear();
		scenarioEditor.setVisible(true);
		scenarioEditor.setBounds(5, 5, 1910, 1070);
		scenarioEditor.setBorder(BorderFactory.createLineBorder(Color.black));
		mapEditorGUI.getWindowBuilder().add(scenarioEditor);
		KeyListener listener = new KeyListener()
		{

			@Override
			public void keyPressed(KeyEvent arg0)
			{
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					screen.buildScreen();
					screen.setFocusable(false);
					screen.removeKeyListener(this);
				}
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
			}

			@Override
			public void keyTyped(KeyEvent e)
			{
			}
		};
		mapEditorGUI.getWindowBuilder().addKeyListener(listener);
		buildSection();
		scenarioEditor.add(glue);
	}

	/**
	 * Builds a new Section on the ScenarioEditor for organizational purposes.
	 */
	public void buildSection()
	{
		System.out.println("buildSectionCalled");
		JPanel container = new JPanel();
		JPanel newSection = new JPanel();
		container.setBorder(BorderFactory.createLineBorder(Color.black));
		newSection.setLayout(new BoxLayout(newSection, BoxLayout.LINE_AXIS));
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));

		class scenarioEditorScreenActionListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				switch (e.getActionCommand())
				{
					case "Add Step":
						container.remove(newSection);
						addStep(container);
						container.add(newSection);
						break;
					case "Add Section":
						buildSection();
						break;
					case "Return":
						mapEditorGUI.buildScreen();
				}
			}

		}

		JButton button;

		// make a new button
		// set the action command
		// add the button to buttons array

		button = new JButton("Add Step");
		button.setActionCommand("Add Step");
		button.setToolTipText("Add Step");
		button.addActionListener(new scenarioEditorScreenActionListener());

		newSection.add(button);

		button = new JButton("Add Section");
		button.setActionCommand("Add Section");
		button.setToolTipText("Add Section");
		button.addActionListener(new scenarioEditorScreenActionListener());
		
		newSection.add(button);
		
		button = new JButton("Return");
		button.setActionCommand("Return");
		button.addActionListener(new scenarioEditorScreenActionListener());

		newSection.add(button);
		newSection.setVisible(false);
		newSection.setVisible(true);
		container.add(newSection);
		scenarioEditor.remove(this.glue);
		scenarioEditor.add(container);
		scenarioEditor.add(glue);
		scenarioEditor.setVisible(false);
		scenarioEditor.setVisible(true);
	}

	/**
	 * Adds a new step the given section.
	 * @param section The section the step should be added to.
	 */
	public void addStep(JPanel section)
	{
		new Step(section, this);
		scenarioEditor.setVisible(false);
		scenarioEditor.setVisible(true);
		System.out.println("Step Added");
	}
}
