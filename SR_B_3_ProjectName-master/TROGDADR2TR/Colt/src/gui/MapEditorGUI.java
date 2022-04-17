package gui;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

/**
 * 
 * @author Nick, Colt Rogness
 *
 */
public class MapEditorGUI {

	public MapEditorGUI(WindowBuilder frame) {
		frame.clear();
		
		JTabbedPane resourceNavigator = new JTabbedPane();
		String[][] test1 = { { "test", "test2", "Test" }, { "test3", "test4", "test5" } };
		resourceNavigator.add("Buildings", createList(test1));
		resourceNavigator.add("Units", createList(test1));
		resourceNavigator.add("Textures", createList(test1));
		resourceNavigator.setBounds(1620, 0, 300, 1080);
		frame.getContentPane().add(resourceNavigator);
	}

	// private JScrollPane createList(String[] items) {
	// JScrollPane pane = new JScrollPane();
	// JList<String> list = new JList<String>(items);
	// pane.getViewport().add(list);
	//
	// return pane;
	// }

	private JScrollPane createList(Object[][] items) {
		JScrollPane pane = new JScrollPane();
		JTable test;
		Object[] names = { "test", "test", "test" };
		for (int i = 0; i < items.length; i++) {

			test = new JTable(items, names);
			test.setTableHeader(null);
			pane.getViewport().add(test);
		}
		JLabel label = new JLabel("Test Label");
		label.setBounds(5, 100, 100, 100);
		pane.getViewport().add(label);
		return pane;
	}
}
