package com.trogdadr2tr.GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Steps are the things that can be modified to select the trigger conditions and outcomes of a scenario.
 * @author Nick Schmidt
 *
 */
public class Step
{

	/**
	 * The section that the step is tied to.
	 */
	private JPanel section;

	/**
	 * This
	 */
	private JPanel step;

	/**
	 * The firstSection of information related to the step.
	 */
	private JPanel firstSection;

	/**
	 * The secondSection of information related to the step.
	 */
	private JPanel secondSection;
	
	/**
	 * The thirdSection of information related to the step.
	 */
	private JPanel thirdSection;
	
	/**
	 * The MapEditorGUI this object is tied to.
	 */
	private MapEditorGUI mapEditor;

	/**
	 * Creates a step on the given section of the given mapEditor.
	 * @param section The Section the step belongs to.
	 * @param mapEditor The ScenarioEditor the step belongs to.
	 */
	public Step(JPanel section, ScenarioEditor mapEditor)
	{
		this.section = section;
		this.step = new JPanel();
		firstSection = new JPanel();
		secondSection = new JPanel();
		thirdSection = new JPanel();
		this.mapEditor = mapEditor.mapEditorGUI;
		buildStep();
	}

	/**
	 * Builds a blank step with the first Section editable.
	 */
	public void buildStep()
	{
		step.removeAll();
		buildFirstSection();
		step.setVisible(true);
		section.add(step);
	}

	/**
	 * Builds the first section of a step containing the type of event that should be created.
	 */
	public void buildFirstSection()
	{
		firstSection = new JPanel();
		String[] firstOptionsText =
				{" ", "Spawn unit", "Spawn building", "Change health"};

		JComboBox<String> firstOptions = new JComboBox<String>(firstOptionsText);
		firstOptions.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				String text = (String) firstOptions.getSelectedItem();
				if (text == "Spawn unit"){
					addSpawnUnit();
				}
				else if (text == "Spawn building"){
					addSpawnBuilding();
				}
				else if(text == "Change health") {
					 addChangeHealth();
				}
				else if (text == " "){
					step.removeAll();
					buildFirstSection();
					section.setVisible(false);
					section.setVisible(true);
				}
			}
		});
		firstSection.add(firstOptions);
		step.add(firstSection);
		step.setVisible(false);
		step.setVisible(true);

		section.setVisible(false);
		section.setVisible(true);
	}
	
	/**
	 * Builds a change health step containing options on which object should trigger condition and which sould be affected.
	 */
	public void addChangeHealth(){
		secondSection.removeAll();
		thirdSection.removeAll();
		JLabel intro = new JLabel("When ");
		JLabel intro2 = new JLabel(" dies, set health of ");
		JLabel intro3 = new JLabel(" to");
		
		String[] options = {"unit", "building"};
		JComboBox<String> optionSelect = new JComboBox<String>(options);
		String[] availThings;
		
		JTextField health = new JTextField();
		Dimension maxSize = new Dimension(100, 30);
		health.setMaximumSize(maxSize);
		health.setPreferredSize(maxSize);
		
		ArrayList<String> stuff = new ArrayList<String>();
		if(optionSelect.getSelectedItem().toString().equals("building")){
			availThings = new String[mapEditor.getMap().buildings.size()];
			for(int i = 0; i < availThings.length; i++){
				availThings[i] = mapEditor.getMap().buildings.get(i).getName() + " " + mapEditor.getMap().buildings.get(i).uniqueBuildingID;
				stuff.add(availThings[i]);
			}
			for(int i = 0; i < mapEditor.getMap().units.size(); i++){
				stuff.add(mapEditor.getMap().units.get(i).getName() + " " + mapEditor.getMap().units.get(i).uniqueUnitID);
			}
		}
		else{
			availThings = new String[mapEditor.getMap().units.size()];
			for(int i = 0; i < availThings.length; i++){
				availThings[i] = mapEditor.getMap().units.get(i).getName() + " " + mapEditor.getMap().units.get(i).uniqueUnitID;
				stuff.add(availThings[i]);
			}
			for(int i = 0; i < mapEditor.getMap().buildings.size(); i++){
				stuff.add(mapEditor.getMap().buildings.get(i).getName() + " " + mapEditor.getMap().buildings.get(i).uniqueBuildingID);
			}
		}
		String[] allThings = new String[stuff.size()];
		for(int i = 0; i < allThings.length; i++){
			allThings[i] = stuff.get(i);
		}
		JComboBox<String> choices = new JComboBox<String>(availThings);
		JComboBox<String> allChoices = new JComboBox<String>(allThings);
		optionSelect.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				thirdSection.removeAll();
				String[] availThings;
				if(optionSelect.getSelectedItem().toString().equals("building")){
					availThings = new String[mapEditor.getMap().buildings.size()];
					for(int i = 0; i < availThings.length; i++){
						availThings[i] = mapEditor.getMap().buildings.get(i).getName() + " " + mapEditor.getMap().buildings.get(i).uniqueBuildingID;
					}
				}
				else{
					availThings = new String[mapEditor.getMap().units.size()];
					for(int i = 0; i < availThings.length; i++){
						availThings[i] = mapEditor.getMap().units.get(i).getName() + " " + mapEditor.getMap().units.get(i).uniqueUnitID;
					}
				}
				JComboBox<String> choices = new JComboBox<String>(availThings);
				JButton button = new JButton("Save Step");
				thirdSection.add(choices);
				thirdSection.add(intro2);
				thirdSection.add(allChoices);
				thirdSection.add(intro3);
				thirdSection.add(health);
				thirdSection.add(button);
				step.add(thirdSection);
				step.setVisible(false);
				step.setVisible(true);
			}
		});
		JButton button = new JButton("Save Step");
		secondSection.add(intro);
		secondSection.add(optionSelect);
		thirdSection.add(choices);
		thirdSection.add(intro2);
		thirdSection.add(allChoices);
		thirdSection.add(intro3);
		thirdSection.add(health);
		thirdSection.add(button);
		step.add(secondSection);
		step.add(thirdSection);
		step.setVisible(false);
		step.setVisible(true);
	}

	/**
	 * Creates a spawn Unit step and handles which unit should be spawned where and which resource should trigger it.
	 */
	public void addSpawnUnit()
	{
		secondSection.removeAll();
		thirdSection.removeAll();
		String[] unitOptions =
				{"Calvary", "Magic", "Soldier", "Minion",
						"Ranged", "Tank"};
		JComboBox<String> units = new JComboBox<String>(unitOptions);
		String [] options = {"unit", "building"};
		JComboBox<String> optionSelect = new JComboBox<String>(options);
		optionSelect.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				thirdSection.removeAll();
				String[] availThings;
				if(optionSelect.getSelectedItem().toString().equals("building")){
					availThings = new String[mapEditor.getMap().buildings.size()];
					for(int i = 0; i < availThings.length; i++){
						availThings[i] = mapEditor.getMap().buildings.get(i).getName() + " " + mapEditor.getMap().buildings.get(i).uniqueBuildingID;
					}
				}
				else{
					availThings = new String[mapEditor.getMap().units.size()];
					for(int i = 0; i < availThings.length; i++){
						availThings[i] = mapEditor.getMap().units.get(i).getName() + " " + mapEditor.getMap().units.get(i).uniqueUnitID;
					}
				}
				JComboBox<String> availableBuildings = new JComboBox<String>(availThings);
				thirdSection.add(diesEvent(availableBuildings));
				step.add(thirdSection);
				step.setVisible(false);
				step.setVisible(true);
			}
		});
		String[] availThings;
		if(optionSelect.getSelectedItem().toString().equals("building")){
			availThings = new String[mapEditor.getMap().buildings.size()];
			for(int i = 0; i < availThings.length; i++){
				availThings[i] = mapEditor.getMap().buildings.get(i).getName() + " " + mapEditor.getMap().buildings.get(i).uniqueBuildingID;
			}
		}
		else{
			availThings = new String[mapEditor.getMap().units.size()];
			for(int i = 0; i < availThings.length; i++){
				availThings[i] = mapEditor.getMap().units.get(i).getName() + " " + mapEditor.getMap().units.get(i).uniqueUnitID;
			}
		}
		JComboBox<String> availableBuildings = new JComboBox<String>(availThings);
		secondSection.add(units);
		secondSection.add(optionSelect);
		thirdSection.add(diesEvent(availableBuildings));
		step.add(secondSection);
		step.add(thirdSection);
		step.setVisible(false);
		step.setVisible(true);
	}
	
	/**
	 * Adds a Spawn Building step which handles spawning a building at a location and which resource should trigger it.
	 */
	public void addSpawnBuilding()
	{
		secondSection.removeAll();
		thirdSection.removeAll();
		String[] buildingOptions =
				{"Base", "Food Generator", "Gold Generator", "Wood Generator",
						"Stone Generator", "Garrison", "Long Range Defense",
						"Short Range Defense",
						"Spawner", "Wall Vertical", "Wall Horizontal"};
		JComboBox<String> buildings = new JComboBox<String>(buildingOptions);
		String [] options = {"unit", "building"};
		JComboBox<String> optionSelect = new JComboBox<String>(options);

		optionSelect.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				thirdSection.removeAll();
				String[] availThings;
				if(optionSelect.getSelectedItem().toString().equals("building")){
					availThings = new String[mapEditor.getMap().buildings.size()];
					for(int i = 0; i < availThings.length; i++){
						availThings[i] = mapEditor.getMap().buildings.get(i).getName() + " " + mapEditor.getMap().buildings.get(i).uniqueBuildingID;
					}
				}
				else{
					availThings = new String[mapEditor.getMap().units.size()];
					for(int i = 0; i < availThings.length; i++){
						availThings[i] = mapEditor.getMap().units.get(i).getName() + " " + mapEditor.getMap().units.get(i).uniqueUnitID;
					}
				}
				JComboBox<String> availableBuildings = new JComboBox<String>(availThings);
				thirdSection.add(diesEvent(availableBuildings));
				step.add(thirdSection);
				step.setVisible(false);
				step.setVisible(true);
			}
		});
		String[] availThings;
		if(optionSelect.getSelectedItem().toString().equals("building")){
			availThings = new String[mapEditor.getMap().buildings.size()];
			for(int i = 0; i < availThings.length; i++){
				availThings[i] = mapEditor.getMap().buildings.get(i).getName() + " " + mapEditor.getMap().buildings.get(i).uniqueBuildingID;
			}
		}
		else{
			availThings = new String[mapEditor.getMap().units.size()];
			for(int i = 0; i < availThings.length; i++){
				availThings[i] = mapEditor.getMap().units.get(i).getName() + " " + mapEditor.getMap().units.get(i).uniqueUnitID;
			}
		}
		JComboBox<String> availableBuildings = new JComboBox<String>(availThings);
		secondSection.add(buildings);
		secondSection.add(optionSelect);
		thirdSection.add(diesEvent(availableBuildings));
		step.add(secondSection);
		step.add(thirdSection);
		step.setVisible(false);
		step.setVisible(true);
	}
	
	/**
	 * Adds the text relating to something dying from the list of available things. 
	 * @param availableThings The list of things that can be chosen to die
	 * @return The full text and options for the list of available things death options.
	 */
	public JPanel diesEvent(JComboBox<String>availableThings){
		JPanel container = new JPanel();
		JLabel at = new JLabel("at X:");
		JTextField xPos = new JTextField();
		JTextField yPos = new JTextField();
		Dimension maxSize = new Dimension(100, 30);
		xPos.setMaximumSize(maxSize);
		xPos.setPreferredSize(maxSize);
		yPos.setPreferredSize(maxSize);
		yPos.setSize(maxSize);
		JLabel y = new JLabel(" Y: ");
		JLabel dies = new JLabel (" dies.");
		JButton save = new JButton ("Save Step");
		save.addActionListener(new addStepListener());
		container.add(at);
		container.add(xPos);
		container.add(y);
		container.add(yPos);
		container.add(availableThings);
		container.add(dies);
		container.add(save);
		return container;
	}
	class addStepListener implements ActionListener

	{

		@Override

		public void actionPerformed(ActionEvent e)

		{
			switch (e.getActionCommand())
			{
				case "Save Step":
			}
		}
	}

}
