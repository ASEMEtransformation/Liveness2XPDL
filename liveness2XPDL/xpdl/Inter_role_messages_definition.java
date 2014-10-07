package aseme.transformations.xpdl;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.ItemSelectable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.UIManager;

import org.enhydra.jxpdl.elements.Activities;
import org.enhydra.jxpdl.elements.Activity;
import org.enhydra.jxpdl.elements.Association;
import org.enhydra.jxpdl.elements.Package;
import org.enhydra.jxpdl.elements.WorkflowProcess;

public class Inter_role_messages_definition extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5657667412211602793L;
	private static Package pkg1;
	private static String sendername;
	private JPanel contentPane;
	private JList<String> list;
	static List<String> senderList = new LinkedList<String>();
	static List<String> senderid = new LinkedList<String>();
	static List<String> receiverList = new LinkedList<String>();
	static List<String> receiverid = new LinkedList<String>();
	static List<String> tempsenderlist = new LinkedList<String>();
	private static JPopupMenu popup = new JPopupMenu();
	private static JFrame frame2;
	static DefaultListModel<String> model = new DefaultListModel<String>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inter_role_messages_definition frame = new Inter_role_messages_definition();
					frame.setBounds(0, 0, 800, 400);
					frame.setVisible(true);
					setFrame(frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Inter_role_messages_definition() {
		
		
		
		
		
		setBackground(Color.white);
		setFont(UIManager.getFont("List.font"));
		setTitle("Inter-role Messages Definition");
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.white);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel label = new JLabel("Choose the message flow between the participants.");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(label, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblAgentactivity = new JLabel("Agent:Activity");
		panel_2.add(lblAgentactivity, BorderLayout.NORTH);
		lblAgentactivity.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.WHITE);
		panel_2.add(panel_6, BorderLayout.CENTER);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		
		
		JComboBox<String> comboBox = new JComboBox<String>();
		 
		panel_6.add(comboBox, BorderLayout.NORTH);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JTextArea txtrWelcomeToThe = new JTextArea();
		panel_3.add(txtrWelcomeToThe, BorderLayout.CENTER);
		txtrWelcomeToThe.setText("Welcome to the messager.\r\nHere you can create the messages between the roles.\r\nFirst choose a sending activity from the combo box.\r\nThen choose one or more possible receiving activities.\r\nThen click submit.");
		
		JLabel lblNewLabel = new JLabel("");
		panel_3.add(lblNewLabel, BorderLayout.SOUTH);
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\nek\\Desktop\\Capture.PNG"));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("possible receivers");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblNewLabel_1, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_4.add(scrollPane);
		
		list = new JList<String>();
		panel_4.add(list, BorderLayout.CENTER);
		
		JButton btnNewButton = new JButton("Submit");
		panel_4.add(btnNewButton, BorderLayout.SOUTH);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		panel_1.add(panel_5, BorderLayout.SOUTH);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton_1 = new JButton("exit");
		panel_5.add(btnNewButton_1);
		
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
       
        
       // int m = 0;
       	 
        System.out.println("Print sender list"+Live2xpdl.senderList);
        System.out.println("Print sender id"+Live2xpdl.senderid);
        System.out.println("Print receiver list"+Live2xpdl.receiverList);
        System.out.println("Print receiver id"+Live2xpdl.receiverid);
        System.out.println("");
      
        
        if(!(Live2xpdl.senderList.isEmpty())){
        final String[] testreceivers1 = Live2xpdl.receiverList.toArray(new String[Live2xpdl.receiverList.size()]);
       // String[] test2 =  receiverList[];
        int n=0;
        for(n=0; n<Live2xpdl.senderList.size(); n++){
       	 comboBox.addItem(Live2xpdl.senderList.get(n));
        }
        
        ActionListener actionListener = new ActionListener() {
        	
            public void actionPerformed(ActionEvent actionEvent) {
            	model = new DefaultListModel<String>();
            	list.setModel(model);
            ItemSelectable is = (ItemSelectable)actionEvent.getSource();
            String name=selectedString(is);
            setsendername(name);
            JOptionPane.showMessageDialog(null,"You have Selected: " + name);
            StringTokenizer t1 = new StringTokenizer(name, ":");
            t1.nextToken();
            String currentTerm1 = t1.nextToken();
            
            System.out.println("What is the sendername?"+name);
            int m=0;
            for(m=0; m<Live2xpdl.receiverList.size(); m++){
            	String tempname;
				try {
					tempname = Live2xpdl.receiverList.get(m);
					//System.out.println("What is the receiver:"+tempname);
					StringTokenizer t = new StringTokenizer(tempname, ":");
					String currentTerm = new String();
					//System.out.println("What is the currentTerm"+currentTerm);
					t.nextToken();
					currentTerm=t.nextToken();
						if(currentTerm1.substring(4).equals(currentTerm.substring(7))){
							System.out.println("Mpikes edo?");
							model.addElement(Live2xpdl.receiverList.get(m));
							list.setModel(model);
						}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
            	}   
            }
//
			private String selectedString(ItemSelectable is) {
				 Object selected[] = is.getSelectedObjects();
				    return ((selected.length == 0) ? "null" : (String)selected[0]);
			}	
	 }; 
        comboBox.addActionListener(actionListener);
        
        btnNewButton_1.addMouseListener(new MouseListener(){
//			@Override
			public void mouseClicked(MouseEvent e) {
				String newfile ="";
				try {
					JFileChooser fc = new JFileChooser();
					int returnval = fc.showSaveDialog(frame2);
					if(returnval == JFileChooser.APPROVE_OPTION){
						File file = fc.getSelectedFile();
						newfile = file.toString();
					}
					Liveness2XPDL.writeToFile(newfile, pkg1);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
		        System.out.println("\nWritting XPDL model into file \"" + newfile + "\".");
				
				frame2.dispose();
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
        
        btnNewButton.addMouseListener(new MouseListener(){
//			@Override
			public void mouseClicked(MouseEvent e) {
//				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "You will create a message please select a sender and one ore more receivers.");
//				
				String sendername = getsendername();
				JOptionPane.showMessageDialog(null, "The sender is:"+sendername);
				 StringTokenizer t1 = new StringTokenizer(sendername, ":");
		            t1.nextToken();
		            String currentTerm1 = t1.nextToken();
				
//				
				String tempsenderid ="";
				int m=0;
				for(m=0; m<Live2xpdl.activityid; m++){
					 try {
						 Activity tempact = (Activity) Live2xpdl.wp.getActivities().get(m);
						String tempname = tempact.getName();
						 if(currentTerm1.equals(tempname)){
							 tempsenderid = tempact.getId();
						 }
				} catch (Exception e1) {
					}
				}
//				
				System.out.println("What is the tempsenderid" +tempsenderid);
				
		for(m=0; m<Live2xpdl.activityid; m++){
			
			Activity tempact;
			try{
				tempact = (Activity) Liveness2XPDL.wp.getActivities().get(m);
				System.out.println("Mpikes edo?");
				String tempname = tempact.getName();
				//String tempname1 = "Receive"+tempname;
				for(int i=0; i<tempsenderlist.size(); i++){	
					try {
						System.out.println("Mpikes kai edo?");
						//String tempterm = tempsenderlist.get(i).substring(4);
						if(tempsenderlist.get(i).contains(tempname)){
						//System.out.println("What is the tempterm:"+tempterm);
							//if(tempterm.equals(tempname1)){
							 //for(int o=0; o<Liveness2XPDL.pkg.getAssociations().size(); o++){
								Live2xpdl.activityid = Live2xpdl.activityid + 1;
								 System.out.println("mpike edo?");
								 System.out.println("What is the receiverid"+tempact.getId());
								 Association asoc = (Association) pkg1.getAssociations().generateNewElement();
						 			//System.out.println("What is the receiverid"+tempact.getId());
						 			asoc.setId(""+Live2xpdl.activityid);
						 			asoc.setName(tempsenderid+tempname);
						 			asoc.setSource(tempsenderid);
						 			asoc.setTarget(tempact.getId());
						 			asoc.setAssociationDirectionFROM();
						 		//if(Liveness2XPDL.pkg.getAssociations().get(i).equals(asoc.containsName(tempsenderid+tempname))){
						 		//Liveness2XPDL.pkg1.getAssociations().add(asoc);
						 			pkg1.getAssociations().add(asoc);
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
 		
//   	  	String newfile ="";
//		try {
//			JFileChooser fc = new JFileChooser();
//			int returnval = fc.showSaveDialog(frame2);
//			if(returnval == JFileChooser.APPROVE_OPTION){
//				File file = fc.getSelectedFile();
//				newfile = file.toString();
//			}
//			Liveness2XPDL.writeToFile(newfile, pkg1);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		}
//        System.out.println("\nWritting XPDL model into file \"" + newfile + "\".");
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
        
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                if (evt.getValueIsAdjusting())
                return;
                tempsenderlist = list.getSelectedValuesList();
                System.out.println("The minirolelist is:"+tempsenderlist);
              }
        
            });
        
        }
	}
	
	
	
	protected static void setsendername(String name){
		sendername = name;
	}
	
	protected static String getsendername() {
		// TODO Auto-generated method stub
		return sendername;
	}

	public static Package getthePackage() {
		// TODO Auto-generated method stub
		return pkg1;
	}
	
	public static void setthePackage(Package pkg){
		pkg1 = pkg;
	}
	
public static void setFrame(JFrame frame1){
		
		frame2 = frame1;
	}
	
	public static JFrame getFrame(){
		return frame2;
		}
	

}
