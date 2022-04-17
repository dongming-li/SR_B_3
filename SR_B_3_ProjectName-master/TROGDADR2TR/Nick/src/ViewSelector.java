import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewSelector {
	public ViewSelector(Container screen){
		JPanel viewContainer = new JPanel();
	    viewContainer.setBounds(2,5,1615,35);
	    JLabel label = new JLabel("Views:");
	    viewContainer.add(label);
		viewContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		
	    JCheckBox chinButton = new JCheckBox("Chin");
	    viewContainer.add(chinButton);
//	    chinButton.setMnemonic(KeyEvent.VK_C); 
//	    chinButton.setSelected(true);

	    screen.add(viewContainer);
	}
}
