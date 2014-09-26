package aseme.transformations.xpdl;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

public class MyClass implements MouseListener{

	
	private JFrame frame; 
	private MouseEvent e;
	//private Object textArea;

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		frame.dispose();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void setFrame(JFrame frame1){
		
		frame = frame1;
	}
	
	public JFrame getFrame(){
		return frame;
		}
	
}
