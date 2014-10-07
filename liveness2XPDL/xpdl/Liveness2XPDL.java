package aseme.transformations.xpdl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

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
import org.enhydra.jxpdl.elements.Lane;
import org.enhydra.jxpdl.elements.NodeGraphicsInfo;
import org.enhydra.jxpdl.elements.Package;
import org.enhydra.jxpdl.elements.Performer;
import org.enhydra.jxpdl.elements.Pool;
import org.enhydra.jxpdl.elements.WorkflowProcess;
import org.w3c.dom.Document;




import SRM.SRMPackage;
import SRM.SRMmodel;

public class Liveness2XPDL {

	static Package pkg = null;
	static Pool p = null;
	static WorkflowProcess wp =null;
	static Lane l1 = null;
	
	static List<String> roles = new LinkedList<String>();
	static String liveness = null;
	static String filename = "C:/Users/nek/Desktop/Myfirst.xpdl";
	static int xmiid = 0;
	static String poolname = "";
	private static Liveness2XPDL liveness2xpdl;
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		Application.setArgs(args);
		setLiveness2xpdl(new Liveness2XPDL());
		ResourceSet resourceSet = new ResourceSetImpl();
		//Live2xpdl live2xpdl = new Live2xpdl();
		// Register the appropriate resource factory to handle all file
				// extensions.
				//
				resourceSet
						.getResourceFactoryRegistry()
						.getExtensionToFactoryMap()
						.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
								new XMIResourceFactoryImpl());

				// Register the package to ensure it is available during loading.
				
				resourceSet.getPackageRegistry().put(SRMPackage.eNS_URI,
						SRMPackage.eINSTANCE);
				
				// load SRM model
				
				Resource r = null;
				if (args != null && args.length > 1) {
					r = resourceSet.getResource(URI.createFileURI(args[0]), true);
				} else {
					//r = resourceSet.getResource(URI.createFileURI("mms-initial-MeetingsManager.srm"),
							//true);
//					r = resourceSet.getResource(URI.createFileURI("mms-refined-PersonalAssistant.srm"),
//					true);
					
//					try {
//						r = resourceSet.getResource(URI.createFileURI("thesis-PersonalAssistant.srm"),
//								true);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					
//					try {
//						r = resourceSet.getResource(URI.createFileURI("thesis-Broker.srm"),
//								true);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					
					try {
						r = resourceSet.getResource(URI.createFileURI("thesis-ComplexProvider.srm"),
								true);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				//mms-initial-MeetingsManager.srm
			SRMmodel srm = (SRMmodel)r.getContents().get(0);
				Liveness2XPDL.liveness = srm.getRoles().get(0).getLiveness();
				Liveness2XPDL.liveness = Liveness2XPDL.liveness.replaceAll(" ", "");
				
				System.out.println("What is the liveness?"+Liveness2XPDL.liveness);
		
//		String agent1 = "ComplexProvider=SP~\n"
//						+"SP=ReceiveRequestMessage.ProcessRequest.SendResponseMessage\n"
//						+"ProcessRequest=(DecideRouteType.SR.SortRoutes)|(DecidePOITypes.SR.DecidePois.SR)\n"
//						+"SR=SendRequestMessage.ReceiveResponseMessage";
		
//		String agent2 = "Testmessager1=ReceiveRequestMessage.ProcessRequest.SendResponseMessage\n"
//						+"testreceiver2=SendRequestMessage.ReceiveResponseMessage";
//		
		//System.out.println("agent1 is"+agent1);
		///roles.add(agent1);
		//roles.add(agent2);
		roles.add(Liveness2XPDL.liveness);
		
		pkg = Liveness2XPDL.combineRoles(roles, filename);
		
		System.out.println("The rolesnameslist:"+Live2xpdl.rolesnames);
		
		for(int i=0; i<Live2xpdl.rolesnames.size(); i++){
		if(Live2xpdl.rolesnames.get(i).contains("Send")){
			System.out.println("Mpainei edo?");
			Inter_role_messages_definition.setthePackage(pkg);
			Inter_role_messages_definition.main(args);
			break;
		}
		}
		
		
		
	}

	
	
	public static Package combineRoles(List<String> roles,  String filename) throws Exception{
			Live2xpdl live2xpdl = new Live2xpdl();
			
			//dimiourgia toy file mas.
			File f = new File(filename);
			try {
				filename = f.getCanonicalPath();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 String name = f.getName().substring(0, ((File) f).getName().lastIndexOf("."));
			 System.out.println("Creating XPDL Model.\n");
			 
			 String id = name;
		      if (!XMLUtil.isIdValid(id)) {
		         id = "test";
		      }
			
		      System.out.println("...creating Package [Id="
	                  + id + ",Name=" + name + ",Script-type=text/javascript]");
		      	pkg = new Package();
		      	pkg.setId(id);
		      	pkg.setName(name);
		      	pkg.getPackageHeader().setXPDLVersion("2.1");
		      	pkg.getPackageHeader().setVendor("TUC");
		      	pkg.getPackageHeader().setCreated(XMLUtil.getCurrentDateAndTime());
		      	
		      	//pkg.getScript().setType("text/javascript");
		      	pkg.getScript().setType("http://www.w3.org/1999/XPath");
		      	
		      	
		      	System.out.println("What are the roles"+roles);
		      	
		      	StringTokenizer line2 = new StringTokenizer(roles.get(0), "=");
	      		poolname = line2.nextToken();
	      		
				System.out.println("What is the pool name:"+poolname);
		         //to pool mas
		         System.out.println("......creating Pool [Id="
	                     + poolname + ",Name=" + poolname + ",Process=" + poolname + "]");
		         p = (Pool) pkg.getPools().generateNewElement();
		         p.setId(poolname);
		         p.setName(poolname);
		         NodeGraphicsInfo ngip = (NodeGraphicsInfo) p.getNodeGraphicsInfos().generateNewElement();
		         ngip.setWidth(350);
		         ngip.setHeight(250);
		         ngip.getCoordinates().setXCoordinate("0");
		         ngip.getCoordinates().setYCoordinate("0");
		         p.getNodeGraphicsInfos().add(ngip);
		         
	
		         System.out.println("......creating WorkflowProcess [Id="
		          + poolname + ",Name=" + poolname + "]");
		         System.out.println("......creating WorkflowProcess [Id="
                         + poolname + ",Name=" + poolname + "]");
		      		wp = (WorkflowProcess) pkg.getWorkflowProcesses().generateNewElement();
		         	wp.setId(poolname);
		         	wp.setName(poolname);
		      	Live2xpdl.wp = wp;
	     	      	
	      	int i=0;
	      	for(i=0; i<roles.size(); i++){

	      		StringTokenizer line1 = new StringTokenizer(roles.get(i), "=");
	      		String tmp = line1.nextToken();
	      		//lane
		         System.out.println(".........creating Lane[Id=projectlane,Name="+poolname+"]");
		         l1 = (Lane) p.getLanes().generateNewElement();
		         //l1.setId("projectlane");
		         l1.setId(poolname);
		         l1.setName(tmp);
		         Performer perf1 = (Performer) l1.getPerformers().generateNewElement();
		         perf1.setValue(poolname);
		         l1.getPerformers().add(perf1);
		         NodeGraphicsInfo ngia = (NodeGraphicsInfo) l1.getNodeGraphicsInfos().generateNewElement();
		         System.out.println(".........creating NodeGraphicsInfo[LaneId=projectlane,Coordinates=20,30]");
		         //ngia.setLaneId("projectlane");
		         ngia.setLaneId(poolname);
		         ngia.setWidth(300);
		         ngia.setHeight(200);
		         ngia.getCoordinates().setXCoordinate("20");
		         ngia.getCoordinates().setYCoordinate("30");
		         l1.getNodeGraphicsInfos().add(ngia);
		        
		         Live2xpdl.setPoolname(poolname);
//		         
		         //kaloyme tin transform gia na mas dimioyrgisei mesa sto pool ti ginetai.
		         l1 = live2xpdl.transform(roles.get(i), pkg, filename);
		         
		         try {
					p.getLanes().add(l1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
	      	}
	      	
	      	pkg.getWorkflowProcesses().add(wp);
	      	pkg.getPools().add(p);
	      	
	      	writeToFile(filename, pkg);
	        System.out.println("\nWritting XPDL model into file \"" + filename + "\".");
	      	
		return pkg;
	}
	
	public static void writeToFile(String outputFile, Package pkg) throws Exception{
		// TODO Auto-generated method stub
		 // System.out.println("PKGEPS=" + pkg.getExternalPackageIds());
	      Document document = null;
	      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	      DocumentBuilder dbuilder = dbf.newDocumentBuilder();
	      document = dbuilder.newDocument();
	      // output stream will either be the FileOutputStream in the
	      // case of save as, or the ByteArrayOutputStream if we are
	      // saving an existing file
	      FileOutputStream os;
	      // try to open random access file as rw, if it fails
	      // the saving shouldn't occur
	      os = new FileOutputStream(outputFile, false);

	      // Here we get all document elements set
	      XPDLRepositoryHandler repH = new XPDLRepositoryHandler();
	      repH.setXPDLPrefixEnabled(true);
	      repH.toXML(document, pkg);

	      // Use a Transformer for output
	      TransformerFactory tFactory = TransformerFactory.newInstance();
	      Transformer transformer = tFactory.newTransformer();
	      transformer.setOutputProperty("indent", "yes");
	      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	      transformer.setOutputProperty("encoding", "UTF8");
	      DOMSource source = new DOMSource(document);
	      StreamResult result = new StreamResult(os);
	      transformer.transform(source, result);

	      os.close();
	}
	
	public Package getPackage(){
		return pkg;
	}



	public static Package createRole(String string, String filename2) {

		Live2xpdl live2xpdl = new Live2xpdl();
     	      	

      		StringTokenizer line1 = new StringTokenizer(string, "=");
      		String tmp = line1.nextToken();
      		//lane
	         System.out.println(".........creating Lane[Id=projectlane,Name="+poolname+"]");
	         l1 = (Lane) p.getLanes().generateNewElement();
	         l1.setId(poolname);
	         l1.setName(tmp);
	         Performer perf1 = (Performer) l1.getPerformers().generateNewElement();
	         perf1.setValue(poolname);
	         l1.getPerformers().add(perf1);
	         NodeGraphicsInfo ngia = (NodeGraphicsInfo) l1.getNodeGraphicsInfos().generateNewElement();
	         System.out.println(".........creating NodeGraphicsInfo[LaneId=projectlane,Coordinates=20,30]");
	         ngia.setLaneId(poolname);
	         ngia.setWidth(300);
	         ngia.setHeight(200);
	         ngia.getCoordinates().setXCoordinate("20");
	         ngia.getCoordinates().setYCoordinate("30");
	         l1.getNodeGraphicsInfos().add(ngia);
	        
	         Live2xpdl.setPoolname(poolname);

	         try {
				l1 = live2xpdl.transform(string, pkg, filename);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
			}
	         
	         try {
				p.getLanes().add(l1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
      	
      	
      	pkg.getWorkflowProcesses().add(wp);
      	pkg.getPools().add(p);
	return pkg;

	}



	public static Liveness2XPDL getLiveness2xpdl() {
		return liveness2xpdl;
	}



	public static void setLiveness2xpdl(Liveness2XPDL liveness2xpdl) {
		Liveness2XPDL.liveness2xpdl = liveness2xpdl;
	}
}
