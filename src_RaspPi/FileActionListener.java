package tetris;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileActionListener implements ActionListener {
	
	public void actionPerformed(ActionEvent e) {
		
		//exit action
		
		System.out.println(e.getSource().toString());
		System.exit(0);
	}
}
