package graphing;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Grapher extends JFrame{

	public static final int width = 900, height = 500;
	public JFrame frame;
	
	public Grapher(String title, GraphingPlotter plot) {
		
		frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
			
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(plot);
		frame.setVisible(true);
		plot.start();
			
		
	}
	
	
}
