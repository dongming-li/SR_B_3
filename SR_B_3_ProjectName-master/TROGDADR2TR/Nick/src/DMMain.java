import javax.swing.JFrame;
import javax.swing.JPanel;

public class DMMain extends JFrame {

	/**
	 * Why you need eclipse?
	 */
	private static final long serialVersionUID = 1L;

	public DMMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// removes the borders and buttons on the top of the window
		setUndecorated(true);
		// make the window fullscreen
		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(0, 0, 1920, 1040); // old size setting

		JPanel DMScreen = new JPanel();
		setContentPane(DMScreen);
		getContentPane().setLayout(null);
		setVisible(true);
		buildScreen();
		System.out.println("Built DMMain");
	}

	private void buildScreen() {
		ResourceNavigator rNav = new ResourceNavigator(getContentPane());
		ViewSelector viewSelect = new ViewSelector(getContentPane());
	}

	//
	// private JPanel createPanel(String text) {
	//
	// JPanel panel = new JPanel();
	// JLabel lbl = new JLabel(text);
	// panel.add(lbl);
	//
	// return panel;
	// }
}
