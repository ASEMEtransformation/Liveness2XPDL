package aseme.transformations.xpdl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.enhydra.jxpdl.XMLUtil;
import org.enhydra.jxpdl.XPDLRepositoryHandler;
import org.enhydra.jxpdl.elements.Activities;
import org.enhydra.jxpdl.elements.Activity;
import org.enhydra.jxpdl.elements.Association;
import org.enhydra.jxpdl.elements.Join;
import org.enhydra.jxpdl.elements.Lane;
import org.enhydra.jxpdl.elements.NodeGraphicsInfo;
import org.enhydra.jxpdl.elements.Package;
import org.enhydra.jxpdl.elements.Participant;
import org.enhydra.jxpdl.elements.Performer;
import org.enhydra.jxpdl.elements.Pool;
import org.enhydra.jxpdl.elements.Transition;
import org.enhydra.jxpdl.elements.TransitionRestriction;
import org.enhydra.jxpdl.elements.TransitionRestrictions;
import org.enhydra.jxpdl.elements.WorkflowProcess;
import org.w3c.dom.Document;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.ItemSelectable;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class App extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8813458766906383490L;
	private static JComboBox<String> c = new JComboBox<String>();
	private static JLabel senders = new JLabel("Message to be send");
	private static JLabel label1 = new JLabel("Agent: Activity");
	private static JLabel receivers = new JLabel("Possible receivers");
	private static JLabel newline = new JLabel("\n");
	private static JLabel newline1 = new JLabel("\n");
	private static JLabel newline2 = new JLabel("\n");
	private static JLabel newline3 = new JLabel("\n");
	private static JLabel onthetob = new JLabel("Choose the message flow between the participants.                                         \n");
	private static JButton b = new JButton("submit");
	private static JButton help = new JButton("Help");
	private static JButton exit = new JButton("exit");
	private static JLabel icon = new JLabel();
	private static JList<String> list = new JList<String>();
	private static JPopupMenu popup = new JPopupMenu();
	private ActionListener listener;
	private ActionEvent actionevent1;
	private ActionEvent event1;
	private static String sendername;
	
	static List<String> senderList = new LinkedList<String>();
	static List<String> senderid = new LinkedList<String>();
	static List<String> receiverList = new LinkedList<String>();
	static List<String> receiverid = new LinkedList<String>();
	static List<String> tempsenderlist = new LinkedList<String>();
	
	public static Package pkg1 = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		pkg1 = getthePackage();
		WorkflowProcess wp = pkg1.getWorkflowProcess(Liveness2XPDL.poolname);
        Activities workflowElements;
		try {
			workflowElements = wp.getActivities();
			workflowElements.size();
			
			System.out.println("Edo mpikes?");
	        System.out.println(""+workflowElements.size());
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			//e2.printStackTrace();
		}
       
        
        int m = 0;
        //for(m=1; m<Live2xpdl.activityid; m++){
       	 //System.out.println("Mpikes edo?");
       	 //Activity tempact = wp.getActivities().getActivity(""+m);
       	 
        System.out.println("Print sender list"+Live2xpdl.senderList);
        System.out.println("Print sender id"+Live2xpdl.senderid);
        System.out.println("Print receiver list"+Live2xpdl.receiverList);
        System.out.println("Print receiver id"+Live2xpdl.receiverid);
        System.out.println("");
      
        
        if(!(Live2xpdl.senderList.isEmpty())){
        String[] testreceivers1 = Live2xpdl.receiverList.toArray(new String[Live2xpdl.receiverList.size()]);
       // String[] test2 =  receiverList[];
        int n=0;
        for(n=0; n<Live2xpdl.senderList.size(); n++){
       	 c.addItem(Live2xpdl.senderList.get(n));
        }
        int o=0;
        for(o=0; o<Live2xpdl.receiverList.size(); o++){
       	 
			testreceivers1[o] = Live2xpdl.receiverList.get(o);
		
        }
                System.out.println("Check here"+Arrays.deepToString(testreceivers1));
                c.add(popup);
                list.setListData(testreceivers1);
      
        MyClass listener = new MyClass();
        exit.addMouseListener(listener);
        
//       // Combolistener combolistener = new Combolistener();
//        //combolistener.setCombobox(c);
//       // c.addActionListener(combolistener);
//        
//        //edo to layout gia ta messages...
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Combine Roles");
        frame.setSize(700, 400);
        frame.getContentPane().setBackground(Color.white);
        frame.setLayout(new FlowLayout());
        listener.setFrame(frame);
        
        
        Box mainbox = Box.createVerticalBox();
        
        Box secondbox = Box.createHorizontalBox();
        Box vb = Box.createVerticalBox();
        Box hb = Box.createVerticalBox();
        Box third = Box.createVerticalBox();
        
        secondbox.add(vb);
        secondbox.add(third);
        mainbox.add(onthetob);
        
        mainbox.add(secondbox);
        
        frame.add(mainbox);
        //frame.add(hb);
//        
       vb.setLocation(0, 30);
       //vb.setBounds(0, 500, 350, 500);
       vb.add(senders);
       vb.add(label1);
       vb.add(c);
        vb.add(newline2);
        vb.add(help);
        vb.add(new JLabel(new ImageIcon("C:/Users/nek/Desktop/Capture.png")));
//             
        hb.setLocation(80, 30);
        hb.setBorder(BorderFactory.createLineBorder(Color.black));
        hb.add(receivers);
        hb.add(list);
        hb.add(newline1);        
        hb.add(b);
        hb.add(newline3);
        
        
        third.add(hb);
        third.add(newline);
        third.add(exit);
//        
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
            ItemSelectable is = (ItemSelectable)actionEvent.getSource();
            String name=selectedString(is);
            setsendername(name);
            JOptionPane.showMessageDialog(null,"You have Selected: " + name);
            }
//
			private String selectedString(ItemSelectable is) {
				 Object selected[] = is.getSelectedObjects();
				    return ((selected.length == 0) ? "null" : (String)selected[0]);
			}
       }; 
        c.addActionListener(actionListener);
//        
//        //SharedListSelectionHandler listlistener = new SharedListSelectionHandler();        
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                if (evt.getValueIsAdjusting())
                return;
//                //System.out.println("Selected from " + evt.getFirstIndex() + " to " + evt.getLastIndex());
                //list.get
                tempsenderlist.add(list.getSelectedValue());
                evt.toString();
                //JOptionPane.showMessageDialog(null, "You have selected:"+list.getSelectedValue());
				//JOptionPane.showMessageDialog(null, "You have selected:"+String.valueOf(evt.getFirstIndex()));
                //JOptionPane.showMessageDialog(null,"Selected from " + evt.getFirstIndex() + " to " + evt.getLastIndex());
                System.out.println("The tempsenderlist is:"+tempsenderlist);
              }
            });
//        
        help.addMouseListener(new MouseListener(){
//			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "                         Welcome to the messager\n"
													+ "Here you can create your messages between the activities\n"
													+ " -->First choose a sender.\n"
													+ "	-->Then choose one or more possible receivers.\n"
													+ "	-->Then click submit.");
			}
//
//			@Override
			public void mouseEntered(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
			}
//
//			@Override
			public void mouseExited(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
			}
//
//			@Override
			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
			}
//
//			@Override
			public void mouseReleased(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
			}
//       	 
       });
        b.addMouseListener(new MouseListener(){
//			@Override
			public void mouseClicked(MouseEvent e) {
//				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "You will create a message please select a sender and one ore more receivers.");
//				
				String sendername = getsendername();
				//JOptionPane.showMessageDialog(null, "The sender is:"+sendername);
//				
				String tempsenderid ="";
				int m=0;
				for(m=1; m<Live2xpdl.activityid; m++){
					 Activity tempact = Liveness2XPDL.wp.getActivities().getActivity(""+m);
					 try {
						String tempname = tempact.getName();
						 if(sendername.contains(tempname)){
							 tempsenderid = tempact.getId();
						 }
				} catch (Exception e1) {
//						// TODO Auto-generated catch block
//						//e1.printStackTrace();
					}
				}
//				
				System.out.println("What is the tempsenderid" +tempsenderid);
//				//Activity tempsender = wp.getActivities().getActivity();
//				
//				//kodikas gia receivers boitheia mas
				
		for(m=1; m<Live2xpdl.activityid; m++){
			
			Activity tempact = Liveness2XPDL.wp.getActivities().getActivity(""+m);
			try{
				String tempname = tempact.getName();
				String tempname1 = "Receive"+tempname;
				for(int i=0; i<tempsenderlist.size(); i++){	
					try {
						//String tempterm = tempsenderlist.get(i).substring(4);
						if(tempsenderlist.get(i).contains(tempname1)){
						//System.out.println("What is the tempterm:"+tempterm);
							//if(tempterm.equals(tempname1)){
							 //for(int o=0; o<Liveness2XPDL.pkg.getAssociations().size(); o++){
								Live2xpdl.activityid = Live2xpdl.activityid + 1;
								 System.out.println("mpike edo?");
								 System.out.println("What is the receiverid"+tempact.getId());
								 Association asoc = (Association) Liveness2XPDL.pkg.getAssociations().generateNewElement();
						 			//System.out.println("What is the receiverid"+tempact.getId());
						 			asoc.setId(""+Live2xpdl.activityid);
						 			asoc.setName(tempsenderid+tempname);
						 			asoc.setSource(tempsenderid);
						 			asoc.setTarget(tempact.getId());
						 			asoc.setAssociationDirectionFROM();
						 		//if(Liveness2XPDL.pkg.getAssociations().get(i).equals(asoc.containsName(tempsenderid+tempname))){
						 		Liveness2XPDL.pkg.getAssociations().add(asoc);
								//}else{
						 			
							 		//}
							 	//}
							}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
					}
					}
			}catch (Exception e1) {
//				// TODO Auto-generated catch block
//				//e1.printStackTrace();
			}
   	      }   
   	         JOptionPane.showMessageDialog(null, "Messages created and submitted");
   	         tempsenderlist = new LinkedList<String>();
//   	      try {
//			Liveness2XPDL.writeToFile(Liveness2XPDL.filename, Liveness2XPDL.pkg);
//			
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//          System.out.println("\nWritting XPDL model into file \"" + Liveness2XPDL.filename + "\".");
//  		
   	  	String newfile ="";
		try {
			JFileChooser fc = new JFileChooser();
			int returnval = fc.showSaveDialog(frame);
			if(returnval == JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				newfile = file.toString();
			}
			Liveness2XPDL.writeToFile(newfile, Liveness2XPDL.pkg);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
        System.out.println("\nWritting XPDL model into file \"" + newfile + "\".");
			}
//
//			@Override
			public void mouseEntered(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
			}
//
//			@Override
			public void mouseExited(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
			}
//
//			@Override
			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
			}
//
//			@Override
			public void mouseReleased(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
			}
//       	 
       });
      // hb.add(exit);
        frame.setVisible(true);
      
}
 else{}
		
	}

	public static Package getthePackage() {
		// TODO Auto-generated method stub
		return pkg1;
	}
	
	public static void setthePackage(Package pkg){
		pkg1 = pkg;
	}

	protected static void setsendername(String name){
		sendername = name;
	}
	
	protected static String getsendername() {
		// TODO Auto-generated method stub
		return sendername;
	}
	
}
