package aseme.transformations.xpdl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class Combolistener implements ActionListener{

	private String temp;
	private JComboBox<String> ComboBox;
	private ActionEvent e;
	@Override
	public void actionPerformed(ActionEvent e) {
		
		 
			temp = (String) ComboBox.getSelectedItem();
			System.out.println("You have selected:"+temp);
	}

	public String getSelection(){
		return temp;
	}
	
	public void setCombobox(JComboBox<String> tempbox){
		ComboBox = tempbox;
	}
	
	public JComboBox<String> getCombo(){
		return ComboBox;
	}
}
