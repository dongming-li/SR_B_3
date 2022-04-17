import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

public class ResourceNavigator extends JFrame{

	/**
	 * Why you need eclipse?
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNavigator(Container screen){
		JTabbedPane ResourceNavigator = new JTabbedPane();
		String[][][] Test1 = {{{"test", "test2", "Test"}, {"test3", "test4", "test5"}},{{"2test","2test2","2test3"}}};
		ResourceNavigator.add("Buildings", createList(Test1));
		ResourceNavigator.add("Units", createList(Test1));
		ResourceNavigator.add("Textures", createList(Test1));
		ResourceNavigator.setBounds(1620, 0, 300, 1080);
		screen.add(ResourceNavigator);
	}
    
    private JScrollPane createList(Object[][][] items) {
        JScrollPane pane = new JScrollPane();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JTable Test;
        Object[] names = {"test", "test", "test"};
        for(int i = 0; i < items.length; i++){
            JPanel tablePanel = new JPanel ();
            tablePanel.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (), "Table Title", TitledBorder.CENTER,TitledBorder.TOP));
        	Test = new JTable(items[i], names);
        	tablePanel.add(Test.getTableHeader());
        	tablePanel.add(Test);
        	tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.PAGE_AXIS));
        	panel.add(tablePanel);
        }
        pane.getViewport().add(panel);
        return pane;
    } 
}
