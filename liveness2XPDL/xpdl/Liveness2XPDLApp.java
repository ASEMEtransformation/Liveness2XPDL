package aseme.transformations.xpdl;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import javax.swing.SwingConstants;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;





import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.enhydra.jxpdl.elements.Package;





import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import SRM.SRMPackage;
import SRM.SRMmodel;

public class Liveness2XPDLApp extends JFrame implements MouseListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7396635470457189928L;
	private static JFrame frame2;
	private static String[] arguments;
	private JPanel contentPane;
	private String input;
	static ResourceSet resourceSet = new ResourceSetImpl();
	static List<String>  listofroles = new LinkedList<String>();
	static DefaultListModel<String> model = new DefaultListModel<String>();
	public static JList<String> rolelist = null;
	private static List<String> miniroles =  new LinkedList<String>();
	private static List<String> formulalist = new LinkedList<String>();
	private static Package Applicationpkg = null;
	static String filename = "C:/Users/nek/Desktop/Myfirst.xpdl";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		setArgs(args);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Liveness2XPDLApp frame = new Liveness2XPDLApp();
					setFrame(frame);
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Liveness2XPDLApp() {
		setTitle("Liveness2XPDL Transformation Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 307);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Open SRM");
		mntmNewMenuItem_1.setActionCommand("open");
		mntmNewMenuItem_1.addActionListener(this);
		mnFile.add(mntmNewMenuItem_1);
		
		
		JMenuItem mntmEditFormula = new JMenuItem("Edit Gaia Formula");
		mntmEditFormula.setActionCommand("edit");
		mntmEditFormula.addActionListener(this);
		mnFile.add(mntmEditFormula);
		
		JMenuItem mntmDeleteSelectedRoles = new JMenuItem("Delete Selected Role(s)");
		mntmDeleteSelectedRoles.setActionCommand("delete");
		mntmDeleteSelectedRoles.addActionListener(this);
		mnFile.add(mntmDeleteSelectedRoles);
		
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setActionCommand("exit");
		mntmExit.addActionListener(this);
		mnFile.add(mntmExit);
		
		
		
		
		JMenu mnTransform = new JMenu("Transform");
		menuBar.add(mnTransform);
		
		JMenuItem mntmSingleRoleTransformation = new JMenuItem("Single Role Transformation");
		mntmSingleRoleTransformation.setActionCommand("single");
		mntmSingleRoleTransformation.addActionListener(this);
		mnTransform.add(mntmSingleRoleTransformation);
		
		JMenuItem mntmMultipleRoleTransformation = new JMenuItem("Multiple Role Transformation");
		mntmMultipleRoleTransformation.setActionCommand("multi");
		mntmMultipleRoleTransformation.addActionListener(this);
		mnTransform.add(mntmMultipleRoleTransformation);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.setActionCommand("about");
		mntmAbout.addActionListener(this);
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblListOfRoles = new JLabel("List of Roles");
		contentPane.add(lblListOfRoles, BorderLayout.NORTH);
		lblListOfRoles.setVerticalAlignment(SwingConstants.TOP);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		rolelist = new JList<String>();
		rolelist.setModel(model);
		scrollPane.setViewportView(rolelist);
		
		 rolelist.addListSelectionListener(new ListSelectionListener() {
	            public void valueChanged(ListSelectionEvent evt) {
	                if (evt.getValueIsAdjusting())
	                return;
//	                //System.out.println("Selected from " + evt.getFirstIndex() + " to " + evt.getLastIndex());
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
		 
		 rolelist.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_DELETE){
					System.out.println("Delete pressed");
					int count = rolelist.getSelectedIndices().length;
					for(int i=0; i<count; i++){
						model.removeElementAt(rolelist.getSelectedIndex());
					}
					}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			 
		 });
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		if("open".equals(e.getActionCommand())){

			
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(getFrame());
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

String namegiven = JOptionPane.showInputDialog("Give a name for your role");

StringTokenizer temp = new StringTokenizer(Liveness2XPDL.liveness, "=");
String leftHandSide = temp.nextToken();
agent = "Role:"+leftHandSide;
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
			String namegiven = JOptionPane.showInputDialog("Give a name for your role");
			StringTokenizer temp = new StringTokenizer(input, "=");
			String leftHandSide = temp.nextToken();
			String agent = "Role:" +leftHandSide;


			model.addElement(agent);
			rolelist.setModel(model);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
	
	
		}
	else if("exit".equals(e.getActionCommand())){
		getFrame().dispose();
	}
	else if("about".equals(e.getActionCommand())){
		JOptionPane.showMessageDialog(null, "Welcome to the Liveness2XPDL transformation application\n"
											+ "You can open a SRM file to be transformed from File-->Open\n"
											+ "You can write your own liveness formula from File-->Edit Gaia Formula\n"
											+ "You can delete one or more roles from File--> Delete Selected Roles\n"
											+ "Or by selecting one or more roles in the list and pressing the delete key\n"
											+ "If you want to transform one role Transform-->Single Role Transformation\n"
											+ "If you want to transform multiple roles Transform -->Multiple Roles Transformation\n"
											+ "If you want to write the xpdl model to a certain file press the 'write to target file' button.");
	}
	else if("single".equals(e.getActionCommand())){

			JOptionPane.showMessageDialog(null, "You will create a single role model.");
			Applicationpkg = new Package();
			
			  if(miniroles.isEmpty()){
		        	 JOptionPane.showMessageDialog(getFrame(), "Please select a role.");
		         }else{
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
					Applicationpkg = Liveness2XPDL.combineRoles(formulalist, filename);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					//e2.printStackTrace();
				}
			
			String newfile ="";
			try {
				JFileChooser fc = new JFileChooser();
				int returnval = fc.showSaveDialog(getFrame());
				if(returnval == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					newfile = file.toString();
				}
				Liveness2XPDL.writeToFile(newfile, Applicationpkg);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
	        System.out.println("\nWritting XPDL model into file \"" + newfile + "\".");
	        
	        miniroles = new LinkedList<String>();
	        Live2xpdl.rolesnames = new LinkedList<String>();
	        formulalist = new LinkedList<String>();
		         }
	}
	else if("multi".equals(e.getActionCommand())){
		JOptionPane.showMessageDialog(null, "You will create a multi role model.");
		
		Applicationpkg = new Package();
		
		if(miniroles.isEmpty()){
       	 JOptionPane.showMessageDialog(getFrame(), "Please select one or more roles.");
        }
		else{
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
				Applicationpkg = Liveness2XPDL.combineRoles(formulalist, filename);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				//e2.printStackTrace();
			}


	      	System.out.println("The rolesnameslist:"+Live2xpdl.rolesnames);

	      	for(int i=0; i<Live2xpdl.rolesnames.size(); i++){
	      		try {
					if(Live2xpdl.rolesnames.get(i).contains("Send")){
						System.out.println("Mpainei edo?");
						//Inter_role_messages_definition.setthePackage(Liveness2XPDL.pkg);
						Inter_role_messages_definition.setthePackage(Applicationpkg);
						Inter_role_messages_definition.main(getArgs());
						Applicationpkg = Inter_role_messages_definition.getthePackage();
						break;
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
	      	}
	      	
	      	String newfile ="";
			try {
				JFileChooser fc = new JFileChooser();
				int returnval = fc.showSaveDialog(getFrame());
				if(returnval == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					newfile = file.toString();
				}
				Liveness2XPDL.writeToFile(newfile, Applicationpkg);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
	        System.out.println("\nWritting XPDL model into file \"" + newfile + "\".");
	        
	        miniroles = new LinkedList<String>();
	        Live2xpdl.rolesnames = new LinkedList<String>();
	        formulalist = new LinkedList<String>();
		}
	}else if("delete".equals(e.getActionCommand())){
		int count = rolelist.getSelectedIndices().length;
		for(int i=0; i<count; i++){
			model.removeElementAt(rolelist.getSelectedIndex());
		}
	}
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
	
public static void setFrame(JFrame frame1){
		
		frame2 = frame1;
	}
	
	public static JFrame getFrame(){
		return frame2;
		}
	
	public static void setArgs(String[] args) {
		// TODO Auto-generated method stub
		arguments = args;
	}
	
	public static String[] getArgs(){
		return arguments;
	}

}
