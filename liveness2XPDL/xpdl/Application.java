package aseme.transformations.xpdl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import SRM.SRMPackage;
import SRM.SRMmodel;

public class Application extends JFrame implements ActionListener, ItemListener, MouseListener{
	
	/**
	 * 
	 */
	static String input = "";
	static Application frame = null;
	private static final long serialVersionUID = 2583778912265829335L;
	static JMenuBar menuBar = new JMenuBar();
	static JMenu fileMenu = new JMenu("File");
	static JMenu transformMenu = new JMenu("Transform");
	static JMenu helpMenu = new JMenu("Help");
	static JMenuItem opensrm1 = new JMenuItem("Open this SRM");
	static JMenuItem edit = new JMenuItem("Edit Formula");
	static JMenuItem exit = new JMenuItem("Exit");
	static JMenuItem singlerole = new JMenuItem("Single Role Transformation");
	static JMenuItem multirole = new JMenuItem("Multiple Roles Transformation");
	static JMenuItem about = new JMenuItem("About");
	private static JLabel thelist = new JLabel("List of roles");
	public static JList<String> rolelist = new JList<String>();
	static JButton write = new JButton("Write to target file");
	static JButton exitbutton = new JButton("exit");
	private static JLabel newline = new JLabel("\n");
	private static JLabel newline1 = new JLabel("\n");
	private static JLabel livenesslabel = null;
	private static JFrame frame2 = null;
	static ResourceSet resourceSet = new ResourceSetImpl();
	static DefaultListModel<String> model = new DefaultListModel<String>();
	static DefaultListModel<String> modelforselection = new DefaultListModel<String>();
	static List<String>  listofroles = new LinkedList<String>();
	private static String[] arguments;
	private static Box mainbox = null;
	private static List<String> miniroles =  new LinkedList<String>();
	private static List<String> formulalist = new LinkedList<String>();
	private static JLabel selectionlist = new JLabel("Selection list");
	private static JList<String> selectedlist = new JList<String>();
	private static JButton clearselectedlist = new JButton("Clear selected list");
	
	
	public Application(){

		// TODO Auto-generated method stub
        
        //String srmplace = JOptionPane.showInputDialog(frame, "Please insert the location of the SRM model");
        
        
        
        fileMenu.add(opensrm1);
        //opensrm.addA
        opensrm1.setActionCommand("open");
        opensrm1.addActionListener(this);
        
        edit.setActionCommand("edit");
        edit.addActionListener(this);

        exit.setActionCommand("exit");
        exit.addActionListener(this);

        about.setActionCommand("about");
        about.addActionListener(this);
        
        singlerole.setActionCommand("single");
        singlerole.addActionListener(this);

        multirole.setActionCommand("multi");
        multirole.addActionListener(this);
        
     
//     exitbutton.addMouseListener(new MouseListener(){
////			@Override
//			public void mouseClicked(MouseEvent e) {
//				frame.dispose();
//			}
////
////			@Override
//			public void mouseEntered(MouseEvent e) {
////				// TODO Auto-generated method stub
////				
//			}
////
////			@Override
//			public void mouseExited(MouseEvent e) {
////				// TODO Auto-generated method stub
////				
//			}
////
////			@Override
//			public void mousePressed(MouseEvent e) {
////				// TODO Auto-generated method stub
////				
//			}
////
////			@Override
//			public void mouseReleased(MouseEvent e) {
////				// TODO Auto-generated method stub
////				
//			}
////  	 
//  });
     
      
//     write.addMouseListener(new MouseListener(){
////			@Override
//			public void mouseClicked(MouseEvent e) {
//				String newfile ="";
//			try {
//				JFileChooser fc = new JFileChooser();
//				int returnval = fc.showSaveDialog(frame);
//				if(returnval == JFileChooser.APPROVE_OPTION){
//					File file = fc.getSelectedFile();
//					newfile = file.toString();
//				}
//				Liveness2XPDL.writeToFile(newfile, Liveness2XPDL.pkg);
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				//e1.printStackTrace();
//			}
//	        System.out.println("\nWritting XPDL model into file \"" + newfile + "\".");
//		}
////
////			@Override
//			public void mouseEntered(MouseEvent e) {
////				// TODO Auto-generated method stub
////				
//			}
////
////			@Override
//			public void mouseExited(MouseEvent e) {
////				// TODO Auto-generated method stub
////				
//			}
////
////			@Override
//			public void mousePressed(MouseEvent e) {
////				// TODO Auto-generated method stub
////				
//			}
////
////			@Override
//			public void mouseReleased(MouseEvent e) {
////				// TODO Auto-generated method stub
////				
//			}
////  	 
//  });
     
     
     rolelist.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                if (evt.getValueIsAdjusting())
                return;
//                //System.out.println("Selected from " + evt.getFirstIndex() + " to " + evt.getLastIndex());
                //list.get
                miniroles = rolelist.getSelectedValuesList();
                //miniroles.add(rolelist.getSelectedValue());
                //String temp = rolelist.getSelectedValue();
                //modelforselection.addElement(temp);
                //selectedlist.setModel(modelforselection);
                //JOptionPane.showMessageDialog(null, "You have selected:"+rolelist.getSelectedValue());
				//JOptionPane.showMessageDialog(null, "You have selected:"+String.valueOf(evt.getFirstIndex()));
                //JOptionPane.showMessageDialog(null,"Selected from " + evt.getFirstIndex() + " to " + evt.getLastIndex());
                System.out.println("The minirolelist is:"+miniroles);
              }
            });
     
     clearselectedlist.addMouseListener(new MouseListener(){
////			@Override
			public void mouseClicked(MouseEvent e) {
				miniroles = new LinkedList<String>();
				modelforselection.removeAllElements();
                selectedlist.setModel(modelforselection);
			}
////
////			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
////
////			@Override
		public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
////				
		}
////
////			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
////				
		}
////
////  	 
@Override
public void mouseReleased(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}
  });
     
        fileMenu.add(edit);
        fileMenu.add(exit);
        
        transformMenu.add(singlerole);
        transformMenu.add(multirole);
        
        helpMenu.add(about);
        
        menuBar.setBounds(0, 0, 700, 15);
        menuBar.add(fileMenu);
        menuBar.add(transformMenu);
        menuBar.add(helpMenu);
        menuBar.setVisible(true);
        
        rolelist.add(newline);
        
        mainbox  = Box.createVerticalBox();
        //Box box1 = Box.createHorizontalBox();
        
        //mainbox.add(menuBar);
        mainbox.add(newline);
        mainbox.add(thelist);
        mainbox.add(rolelist);
//        box1.add(write);
//        box1.add(exitbutton);
        mainbox.add(newline1);
        mainbox.add(selectionlist);
        mainbox.add(selectedlist);
        mainbox.add(clearselectedlist);
       // mainbox.add(box1);
        
        
        //listener.setFrame(frame);
	}
	
	public static void createAndShowGUI(){
		frame = new Application();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Liveness2XPDL Transformation Application");
        frame.setSize(400, 400);
        frame.getContentPane().setBackground(Color.white);
        frame.setLayout(new FlowLayout());
        setFrame(frame);
        
        menuBar.setLocation(0, 0);
        frame.setJMenuBar(menuBar);
        frame.add(mainbox);
		
        frame.setVisible(true);
		
	}
	
	public static void main(final String[] args) {
		
		setArgs(args);
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				createAndShowGUI();
			}
		});
	}

		
	public static void setArgs(String[] args) {
		// TODO Auto-generated method stub
		arguments = args;
	}
	
	public static String[] getArgs(){
		return arguments;
	}

	public static void setFrame(JFrame frame1){
		
		frame2 = frame1;
	}
	
	public static JFrame getFrame(){
		return frame2;
		}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if("open".equals(e.getActionCommand())){

			
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(frame);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				input = file.toString();
			}
			//String input = JOptionPane.showInputDialog(null, "Please insert the location of the SRM model");
			
			String agent = null;
			try {
				resourceSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new XMIResourceFactoryImpl());

// Register the package to ensure it is available during loading.

resourceSet.getPackageRegistry().put(SRMPackage.eNS_URI,
				SRMPackage.eINSTANCE);

// load SRM model
//String[] arguments1 = getArgs();

Resource r = null;
if (arguments != null && arguments.length > 1) {
r = resourceSet.getResource(URI.createFileURI(arguments[0]), true);
} else {
//r = resourceSet.getResource(URI.createFileURI("mms-initial-MeetingsManager.srm"),
					//true);
//r = resourceSet.getResource(URI.createFileURI("mms-refined-PersonalAssistant.srm"),
r = resourceSet.getResource(URI.createFileURI(input),
true);
}

//mms-initial-MeetingsManager.srmr = resourceSet.getResource(URI.createFileURI("mms-refined-PersonalAssistant.srm"),
SRMmodel srm = (SRMmodel)r.getContents().get(0);
Liveness2XPDL.liveness = srm.getRoles().get(0).getLiveness();
Liveness2XPDL.liveness = Liveness2XPDL.liveness.replaceAll(" ", "");

listofroles.add(Liveness2XPDL.liveness);

//System.out.println("What is the liveness?"+Liveness2XPDL.liveness);
StringTokenizer temp = new StringTokenizer(Liveness2XPDL.liveness, "=");
String leftHandSide = temp.nextToken();
agent = "Role:" + leftHandSide;
model.addElement(agent);
rolelist.setModel(model);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
	
	
	
	
	//rolelist.notifyAll();

			
		
		}
	else if("edit".equals(e.getActionCommand())){

		
		//"elamoromoy = x.x1~.x2+.x3*.[x4]"
		//"bigpara = x||x3+||x2*||x5~||[x6]"
		//"sequentialoptional = [z.k.s]"
		//"optionalor = [x|y*]"
		String input = JOptionPane.showInputDialog(null, "Please insert your liveness formula.");
		
		listofroles.add(input);

//System.out.println("What is the liveness?"+Liveness2XPDL.liveness);
		try {
			StringTokenizer temp = new StringTokenizer(input, "=");
			String leftHandSide = temp.nextToken();
			String agent = "Role:" + leftHandSide;


			model.addElement(agent);
			rolelist.setModel(model);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
	
	
		}
	else if("exit".equals(e.getActionCommand())){
		frame.dispose();
	}
	else if("about".equals(e.getActionCommand())){
		JOptionPane.showMessageDialog(null, "Welcome to the Liveness2XPDL transformation application\n"
											+ "You can open a SRM file to be transformed from File-->Open\n"
											+ "You can write your own liveness formula from File-->Edit\n"
											+ "If you want to transform one role Transform-->Single Role Transformation\n"
											+ "If you want to transform multiple roles Transform -->Multiple Roles Transformation\n"
											+ "If you want to write the xpdl model to a certain file press the 'write to target file' button.");
	}
	else if("single".equals(e.getActionCommand())){

			JOptionPane.showMessageDialog(null, "You will create a single role model.");
			
			  if(miniroles.isEmpty()){
		        	 JOptionPane.showMessageDialog(frame, "Please select a role.");
		         }else{
			
			File f = new File(Liveness2XPDL.filename);
			try {
				Liveness2XPDL.filename = f.getCanonicalPath();
			} catch (IOException z) {
				// TODO Auto-generated catch block
				z.printStackTrace();
			}
			 String name = f.getName().substring(0, ((File) f).getName().lastIndexOf("."));
			 System.out.println("Creating XPDL Model.\n");
			
			String id = name;
		      if (!XMLUtil.isIdValid(id)) {
		         id = "test";
		      }
			System.out.println("...creating Package [Id="
	                  + id + ",Name=" + name + ",Script-type=text/javascript]");
			Liveness2XPDL.pkg = new Package();
			Liveness2XPDL.pkg.setId(id);
			Liveness2XPDL.pkg.setName(name);
			Liveness2XPDL.pkg.getPackageHeader().setXPDLVersion("2.1");
			Liveness2XPDL.pkg.getPackageHeader().setVendor("TUC");
			Liveness2XPDL.pkg.getPackageHeader().setCreated(XMLUtil.getCurrentDateAndTime());
		      	
		      	//pkg.getScript().setType("text/javascript");
			Liveness2XPDL.pkg.getScript().setType("http://www.w3.org/1999/XPath");
		      	
		      	StringTokenizer line1 = new StringTokenizer(listofroles.get(0), "=");
		      	Liveness2XPDL.poolname = line1.nextToken();
				System.out.println("What is the pool name:"+Liveness2XPDL.poolname);
		         //to pool mas
		         System.out.println("......creating Pool [Id="
	                     + Liveness2XPDL.poolname + ",Name=" + Liveness2XPDL.poolname + ",Process=" + Liveness2XPDL.poolname + "]");
		         Liveness2XPDL.p = (Pool) Liveness2XPDL.pkg.getPools().generateNewElement();
		         Liveness2XPDL.p.setId(Liveness2XPDL.poolname);
		         Liveness2XPDL.p.setName(Liveness2XPDL.poolname);
		         NodeGraphicsInfo ngip = (NodeGraphicsInfo) Liveness2XPDL.p.getNodeGraphicsInfos().generateNewElement();
		         ngip.setWidth(350);
		         ngip.setHeight(250);
		         ngip.getCoordinates().setXCoordinate("0");
		         ngip.getCoordinates().setYCoordinate("0");
		         Liveness2XPDL.p.getNodeGraphicsInfos().add(ngip);
		         
	
		         System.out.println("......creating WorkflowProcess [Id="
		          + Liveness2XPDL.poolname + ",Name=" + Liveness2XPDL.poolname + "]");
		         Liveness2XPDL.wp = (WorkflowProcess) Liveness2XPDL.pkg.getWorkflowProcesses().generateNewElement();
		         Liveness2XPDL.wp.setId(Liveness2XPDL.poolname);
		         Liveness2XPDL.wp.setName(Liveness2XPDL.poolname);
		         Live2xpdl.wp = Liveness2XPDL.wp;
		         
		         
		         //JOptionPane.showMessageDialog(frame, "Please select a role.");
		         //for(int i=0; i<miniroles.size(); i++){
		       
			      		String temp = miniroles.get(0);
			      		
			      		String temp1 = temp.substring(5);
			      		System.out.println("What is the substring?"+temp1);
			      		
			      		for(int k=0; k<listofroles.size(); k++){
			      			if(listofroles.get(k).startsWith(temp1)){
			      				formulalist.add(listofroles.get(k));
			      				
			      			}
			      		}
			      	//}
			
		     //to get(0) na allaxtei gia na ginei mallon analoga me to selection.
		         try {
					Liveness2XPDL.pkg = Liveness2XPDL.combineRoles(formulalist, Liveness2XPDL.filename);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					//e2.printStackTrace();
				}
			
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
	        
	        miniroles = new LinkedList<String>();
		         }
	}
	else if("multi".equals(e.getActionCommand())){
		JOptionPane.showMessageDialog(null, "You will create a multi role model.");
		
		if(miniroles.isEmpty()){
       	 JOptionPane.showMessageDialog(frame, "Please select one or more roles.");
        }
		else{
		File f = new File(Liveness2XPDL.filename);
		try {
			Liveness2XPDL.filename = f.getCanonicalPath();
		} catch (IOException z) {
			// TODO Auto-generated catch block
			//z.printStackTrace();
		}
		 String name = f.getName().substring(0, ((File) f).getName().lastIndexOf("."));
		 System.out.println("Creating XPDL Model.\n");
		 
		 String id = name;
	      if (!XMLUtil.isIdValid(id)) {
	         id = "test";
	      }
		
	      System.out.println("...creating Package [Id="
                  + id + ",Name=" + name + ",Script-type=text/javascript]");
	      Liveness2XPDL.pkg = new Package();
	      Liveness2XPDL.pkg.setId(id);
	      Liveness2XPDL.pkg.setName(name);
	      Liveness2XPDL.pkg.getPackageHeader().setXPDLVersion("2.1");
	      Liveness2XPDL.pkg.getPackageHeader().setVendor("TUC");
	      Liveness2XPDL.pkg.getPackageHeader().setCreated(XMLUtil.getCurrentDateAndTime());
	      	
	      	//pkg.getScript().setType("text/javascript");
	      Liveness2XPDL.pkg.getScript().setType("http://www.w3.org/1999/XPath");
	      	
	      	//StringTokenizer line1 = new StringTokenizer(agent1, "=");
	      	//Liveness2XPDL.poolname = line1.nextToken();
	      	String multiagent = "multiagent";
			System.out.println("What is the pool name:"+multiagent);
	         //to pool mas
	         System.out.println("......creating Pool [Id="
                     + multiagent + ",Name=" + multiagent + ",Process=" + multiagent + "]");
	         Liveness2XPDL.p = (Pool) Liveness2XPDL.pkg.getPools().generateNewElement();
	         Liveness2XPDL.p.setId(multiagent);
	         Liveness2XPDL.p.setName(multiagent);
	         NodeGraphicsInfo ngip = (NodeGraphicsInfo) Liveness2XPDL.p.getNodeGraphicsInfos().generateNewElement();
	         ngip.setWidth(350);
	         ngip.setHeight(250);
	         ngip.getCoordinates().setXCoordinate("0");
	         ngip.getCoordinates().setYCoordinate("0");
	         Liveness2XPDL.p.getNodeGraphicsInfos().add(ngip);
	         

	         System.out.println("......creating WorkflowProcess [Id="
	          + Liveness2XPDL.poolname + ",Name=" + Liveness2XPDL.poolname + "]");
	         System.out.println("......creating WorkflowProcess [Id="
                     + Liveness2XPDL.poolname + ",Name=" + Liveness2XPDL.poolname + "]");
	         Liveness2XPDL.wp = (WorkflowProcess) Liveness2XPDL.pkg.getWorkflowProcesses().generateNewElement();
	         Liveness2XPDL.wp.setId(multiagent);
	         Liveness2XPDL.wp.setName(multiagent);
	      	Live2xpdl.wp = Liveness2XPDL.wp;
	      	

	      	System.out.println("What is the miniroles list?"+miniroles);
	      	for(int i=0; i<miniroles.size(); i++){
	      		String temp1 = "";
				try {
					String temp = miniroles.get(i);
					temp1 = temp.substring(5);
					System.out.println("What is the substring?"+temp1);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					System.out.println("Listener exception");
				}
	      		
	      		for(int k=0; k<listofroles.size(); k++){
	      			if(listofroles.get(k).startsWith(temp1)){
	      				formulalist.add(listofroles.get(k));
	      			}
	      		}
	      	}
	      	try {
	      		System.out.println("What is the formulalist:"+formulalist);
				Liveness2XPDL.pkg = Liveness2XPDL.combineRoles(formulalist, Liveness2XPDL.filename);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				//e2.printStackTrace();
			}


	      	System.out.println("The rolesnameslist:"+Live2xpdl.rolesnames);

	      	for(int i=0; i<Live2xpdl.rolesnames.size(); i++){
	      		try {
					if(Live2xpdl.rolesnames.get(i).contains("Send")){
						System.out.println("Mpainei edo?");
						App.setthePackage(Liveness2XPDL.pkg);
						App.main(getArgs());
						Liveness2XPDL.pkg = App.getthePackage();
						break;
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	      	}
	      	
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
	        
	        //miniroles = new LinkedList<String>();
		}
	}
	}
}
