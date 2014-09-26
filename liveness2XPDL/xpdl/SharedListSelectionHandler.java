package aseme.transformations.xpdl;

import java.io.OutputStream;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SharedListSelectionHandler implements ListSelectionListener {

	@Override
	public void valueChanged(ListSelectionEvent e) {
		   ListSelectionModel lsm = (ListSelectionModel)e.getSource();

	        int firstIndex = e.getFirstIndex();
	        int lastIndex = e.getLastIndex();
	        boolean isAdjusting = e.getValueIsAdjusting();
//	        output.append("Event for indexes "
//	                      + firstIndex + " - " + lastIndex
//	                      + "; isAdjusting is " + isAdjusting
//	                      + "; selected indexes:");
	        System.out.println("Event for indexes "
                    + firstIndex + " - " + lastIndex
                    + "; isAdjusting is " + isAdjusting
                    + "; selected indexes:");
	        if (lsm.isSelectionEmpty()) {
	            //output.append(" <none>");
	            System.out.println("<none>");
	        } else {
	            // Find out which indexes are selected.
	            int minIndex = lsm.getMinSelectionIndex();
	            int maxIndex = lsm.getMaxSelectionIndex();
	            for (int i = minIndex; i <= maxIndex; i++) {
	                if (lsm.isSelectedIndex(i)) {
	                    //output.append(" " + i);
	                	System.out.println(""+i);
	                }
	            }
	        }
	        //output.append(newline);
	        System.out.println("\n");
	}

}
