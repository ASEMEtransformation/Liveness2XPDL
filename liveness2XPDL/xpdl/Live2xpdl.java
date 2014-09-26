package aseme.transformations.xpdl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.enhydra.jxpdl.XMLUtil;
import org.enhydra.jxpdl.XPDLRepositoryHandler;
import org.enhydra.jxpdl.elements.Activity;
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

import java.util.Calendar;


public class Live2xpdl {
	Activity previous;
	int parenthesisflag=0;
	int bracketsflag=0;
	String lefthand = "";
	public static int activityid = 0;
	List<Activity> activitylist = new LinkedList<Activity>();
	int flag =0;
	int numberoforexpressions = 0;
	String connector;
	int startchecker = 1;
	int term=0;
	XMLUtil xml = new XMLUtil();
	int Xcoordinate = 0;
	int Ycoordinate = 0;
	int numberofpara = 0;
	int numberofseq = 0;
	int numberofors = 0;
	int previousterm = 0;
	int l = 0;
	String liveness = null;

	Hashtable<String, String> formulas = null;
	
	String orId="";
	List<String> findTerms = new LinkedList<String>();
	List<String> complexTerms = new LinkedList<String>();
	List<String> expressionsimpleTerm = new LinkedList<>();
	List<String>  expressionList = new LinkedList<>();
	List<String> foundTerms = new LinkedList<String>();
	public static List<String> senderList = new LinkedList<String>();
	public static List<String> senderid = new LinkedList<String>();
	public static List<String> receiverList = new LinkedList<String>();
	public static List<String> receiverid = new LinkedList<String>();
	
	public static List<String> rolesnames = new LinkedList<String>();
	private String roleandname = "";
	List<Matcher> matcherlist = new LinkedList<Matcher>();
	private String sendername;
	private String bracketsterm;
	private String bracket1;
	private String term123;
	String insideTerm1="";
	private String rolename="";
	public static String pname = "";
	
	static String outputFile= "C:/Users/nek/Desktop/Myfirst.xpdl";
	Pool p = null;
	static WorkflowProcess wp = null;
	static Lane l1 = null;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
			
		Live2xpdl live2xpdl = new Live2xpdl();
		
		if (args.length == 0) {
			live2xpdl.liveness = new String(
					//"ComplexProvider=SP~\n"
							//+"SP=ReceiveRequestMessage.ProcessRequest.SendResponseMessage\n"
							//+"ProcessRequest=(DecideRouteType.SR.SortRoutes)|(DecidePOITypes.SR.DecidePois.SR)\n"
							//+"SR=SendRequestMessage.ReceiveResponseMessage"
					//"PersonalAssistant = SR\n"
					//"SP=(ReceiveRequestMessage)~||ProcessRequest~||(SendResponseMessage)~\n"
					//+"SR=SendRequestMessage.ReceiveResponseMessage"
					//"x = A1.A2.A3.An"
					//"x=A+"			
					//"x = A*"
					//"x = A~"
					
					
);
		} else {
			live2xpdl.liveness = args[0];
		}
		live2xpdl.liveness = live2xpdl.liveness.replaceAll(" ", "");
		//live2xpdl.transform("C:/Users/Nektarios/Desktop/Myfirst.xpdl");
		
		
				//dimioyrgia toy file mas
				File f = new File(outputFile);
				outputFile = f.getCanonicalPath();
				 String name = f.getName().substring(0, ((File) f).getName().lastIndexOf("."));
				 System.out.println("Creating XPDL Model.\n");
				 
				 String id = name;
			      if (!XMLUtil.isIdValid(id)) {
			         id = "test";
			      }
			      
			      System.out.println("...creating Package [Id="
		                  + id + ",Name=" + name + ",Script-type=text/javascript]");
			      	Package pkg = new Package();
			      	pkg.setId(id);
			      	pkg.setName(name);
			      	pkg.getPackageHeader().setXPDLVersion("2.1");
			      	pkg.getPackageHeader().setVendor("TUC");
			      	pkg.getPackageHeader().setCreated(XMLUtil.getCurrentDateAndTime());
			      	
			      	//pkg.getScript().setType("text/javascript");
			      	pkg.getScript().setType("http://www.w3.org/1999/XPath");
		
			      	
			      	StringTokenizer line1 = new StringTokenizer(live2xpdl.liveness, "=");
					String poolname = line1.nextToken();
					System.out.println("What is the pool name:"+poolname);
			         //pool
			         System.out.println("......creating Pool [Id="
		                     + poolname + ",Name=" + poolname + ",Process=" + poolname + "]");
			         Pool p = (Pool) pkg.getPools().generateNewElement();
			         p.setId(poolname);
			         p.setName(poolname);
			         NodeGraphicsInfo ngip = (NodeGraphicsInfo) p.getNodeGraphicsInfos().generateNewElement();
			         ngip.setWidth(350);
			         ngip.setHeight(250);
			         ngip.getCoordinates().setXCoordinate("0");
			         ngip.getCoordinates().setYCoordinate("0");
			         p.getNodeGraphicsInfos().add(ngip);
		
			         setPoolname(poolname);
			         
			         System.out.println("......creating WorkflowProcess [Id="
			          + poolname + ",Name=" + poolname + "]");
			         System.out.println("......creating WorkflowProcess [Id="
	                         + poolname + ",Name=" + poolname + "]");
			         	wp = (WorkflowProcess) pkg.getWorkflowProcesses().generateNewElement();
			         	wp.setId(poolname);
			         	wp.setName(poolname);
			      	
			         	//lane
				         System.out.println(".........creating Lane[Id=projectlane,Name="+poolname+"]");
				         l1 = (Lane) p.getLanes().generateNewElement();
				         l1.setId(poolname);
				         l1.setName(poolname);
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
				        
			         	
			         	l1 = live2xpdl.transform(live2xpdl.liveness, pkg, outputFile);
		
			        p.getLanes().add(l1);
			      	pkg.getWorkflowProcesses().add(wp);
			      	pkg.getPools().add(p);
			      	
			      	System.out.println("The rolesnameslist:"+rolesnames);
			      	
			      	
			      	
			         writeToFile(outputFile, pkg);
			         System.out.println("\nWritting XPDL model into file \"" + outputFile + "\".");
	}
	
	public Lane transform(String liveness, Package pkg, String outputFile) throws Exception{
		
		formulas = new Hashtable<String, String>();
		StringTokenizer line = new StringTokenizer(liveness, "\n");
		
		while (line.hasMoreTokens()) {
			String tmp = line.nextToken();
			StringTokenizer formulaElements = new StringTokenizer(tmp, "=");
			String leftHandSide = formulaElements.nextToken();
			String formula = formulaElements.nextToken();
			System.out.println("What is the formula?" +formula);
			String formula1 = preprocessing(formula);
			System.out.println("What is the formula1"+formula1);
			formulas.put(leftHandSide, formula1);
		}
		
		System.out.println("What are the formulas?"+formulas);
		line = new StringTokenizer(liveness, "\n");
		
		  	//o participant mas	          
	        	 StringTokenizer formulaElements = new StringTokenizer(line.nextToken(),
	     				"=");
	     		String leftHandSide = formulaElements.nextToken();
	     		
	     		 Participant p1 = (Participant) pkg.getParticipants().generateNewElement();
		         p1.setId(leftHandSide);
		         p1.setName(leftHandSide);
		         p1.getParticipantType().setTypeROLE();
		         pkg.getParticipants().add(p1);
		         rolename = leftHandSide;
	         
	         
		     Calendar cal2 = Calendar.getInstance();
		     long d = cal2.getTimeInMillis();
	         //start         
	         activityid = activityid+1;
		        Activity start = (Activity) wp.getActivities().generateNewElement();
		         System.out.println("......creating start Event");
		         start.setId(""+d+""+rolename+""+activityid);
		         start.setName("start");
		         start.getActivityTypes().setImplementation();
		         start.getActivityTypes().setEvent();
		         start.getActivityTypes().getEvent().getEventTypes().setStartEvent();
		         start.setFirstPerformer(leftHandSide);
		         NodeGraphicsInfo ngiact = (NodeGraphicsInfo)  start.getNodeGraphicsInfos().generateNewElement();
		         ngiact.setLaneId(getPoolname());
		         ngiact.setHeight(31);
		         ngiact.setWidth(31);
		         ngiact.getCoordinates().setXCoordinate("50");
		         ngiact.getCoordinates().setYCoordinate("60");
		         start.getNodeGraphicsInfos().add(ngiact);
	         
	         wp.getActivities().add(start);
	         
	         lefthand = leftHandSide;
	         Activity act = this.createProcess(formulas.get(leftHandSide), wp, start);
	         
	         
	         d= cal2.getTimeInMillis();
	         activityid = activityid+1;
	         //end
	         Activity endact = (Activity) wp.getActivities().generateNewElement();
	         System.out.println(".......creating end Event");
	         endact.setId(""+d+""+rolename+""+activityid);
	         endact.setName("end");
	         endact.getActivityTypes().setImplementation();
	         endact.getActivityTypes().setEvent();
	         endact.getActivityTypes().getEvent().getEventTypes().setEndEvent();
	         endact.setFirstPerformer(leftHandSide);
	         NodeGraphicsInfo ngiact1 = (NodeGraphicsInfo) endact.getNodeGraphicsInfos().generateNewElement();
	         ngiact1.setLaneId(getPoolname());
	         ngiact1.setShape("annotation");
	         ngiact1.setHeight(31);
	         ngiact1.setWidth(31);
	         ngiact1.getCoordinates().setXCoordinate("50");
	         ngiact1.getCoordinates().setYCoordinate("250");
	         endact.getNodeGraphicsInfos().add(ngiact1);
	         
	         Transition endtra = (Transition) wp.getTransitions().generateNewElement();
	         activityid = activityid+1;
	         endtra.setId(""+activityid);
	         endtra.setFrom(act.getId());
	         endtra.setTo(endact.getId());
	        wp.getTransitions().add(endtra);
	         
	         System.out.println("......creating ending Transition[Id = endtra, From=acttemp, To=end, Type=null]");
	        wp.getActivities().add(endact);
	           	
	         return l1;
	}


	protected void setsendername(String name){
		sendername = name;
	}
	
	protected String getsendername() {
		// TODO Auto-generated method stub
		return sendername;
	}

	private String preprocessing(String formula) {
		String test = formula;
	
		Pattern patternParallelManyTimesTerm = Pattern
				.compile("\\|[a-zA-Z_](\\w+)*~\\|\\d+");
		Matcher parallelManyTimesTermMatcher = patternParallelManyTimesTerm
				.matcher(formula);
		if(parallelManyTimesTermMatcher.find()){
			matcherlist.add(parallelManyTimesTermMatcher);
			System.out.println("The number of listelements are:" +matcherlist.size());
			
			int starting = parallelManyTimesTermMatcher.regionStart();
			System.out.println("What is the starting point" +starting);
			int ending = parallelManyTimesTermMatcher.regionEnd();
			System.out.println("What is the ending point" +ending);
			int startof = parallelManyTimesTermMatcher.start();
			System.out.println("What is the start of match" +startof);
			int end = parallelManyTimesTermMatcher.end();
			System.out.println("What is the end of match" +end);
			
			String inside = parallelManyTimesTermMatcher.group();
			String inside1 = inside.substring(1, inside.length()-2);
		
			char endofstring = formula.charAt(formula.length()-1);
			System.out.println("What is the endofstring"+endofstring);
			int x = Character.getNumericValue(inside.charAt(inside.length()-1));
			System.out.println("What is the x?"+x);
			
			int  i=0;
			formula = "(";
			for(i=0; i<x; i++){
				formula = formula + inside1 +"||";
			}
			
			String betterformula = formula.substring(0, formula.length()-2);
			System.out.println("The better formula is:"+betterformula);
			betterformula = betterformula + ")";
			System.out.println("The better formula is:"+betterformula);
			
			//prepei na kratame kai apo poy ksekinaei
			String newstarttest = test.substring(0, startof);
			System.out.println("What is the start of string?" +newstarttest);
			String newendtest = test.substring(end, test.length());
			System.out.println("What is the newtest:" +newendtest);
			
			String concat1 = newstarttest.concat(betterformula);
			String concat = concat1.concat(newendtest);
			System.out.println("What is the concatenation:" +concat);

			
			test = preprocessing(concat);
		}
		
		
			return test;
	}

	public Activity createProcess(String expression, WorkflowProcess wp,
			 Activity actprevious) {
		
		   
	     Calendar cal2 = Calendar.getInstance();
	     long d = cal2.getTimeInMillis();
	     String left = lefthand;
		Transition currentend = (Transition) wp.getTransitions().generateNewElement();
		activityid = activityid +1;
		currentend.setId(""+d+""+left+""+activityid);
		// TODO Auto-generated method stub
						int expressionType = 0;
						//pattern for parallelExpression
						Pattern patternParallelExpression = Pattern
								.compile("(((\\|.+~|(\\d))|(\\[.+\\])|(\\(.+\\))|([\\w&&[^()]])+)([~+*]?)\\|\\|)+((\\|.+~\\|(\\d))|(\\[.+\\])|(\\(.+\\))|([\\w&&[^()]])+)([~+*]?)");
						Matcher parallelMatcher = patternParallelExpression.matcher(expression);
						// pattern for orExpression : expressionType=2
						Pattern patternOrExpression = Pattern
								.compile("(((\\|.+~|(\\d))|(\\[.+\\])|(\\(.+\\))|([\\w&&[^()]])+)([~+*]?)\\|)+((\\|.+~\\|(\\d))|(\\[.+\\])|(\\(.+\\))|([\\w&&[^()]])+)([~+*]?)");
						Matcher orMatcher = patternOrExpression.matcher(expression);
						// pattern for sequentialExpression : expressionType=3
						Pattern patternSequentialExpression = Pattern
								.compile("(((\\|.+~\\|\\d)|(\\[.+\\])|(\\(.+\\))|([\\w&&[^()]])+)([~+*]?)\\.)+((\\|.+~\\|\\d)|(\\[.+\\])|(\\(.+\\))|([\\w&&[^()]])+)([~+*]?)");
						
						Matcher sequentialMatcher = patternSequentialExpression.matcher(expression);
					
						
						//exclusive gateway
						Activity actexjoin = (Activity) wp.getActivities().generateNewElement();
						TransitionRestrictions transrestrs1 = new TransitionRestrictions(actexjoin);
						TransitionRestriction transrest1 = new TransitionRestriction(transrestrs1);
						Join exjoin = new Join(transrest1);
						exjoin.setTypeExclusive();
						
						List<String> myTerms = null;
						
						switch (expressionType) {
												case 0:
													myTerms = new LinkedList<String>();
													myTerms.add(expression);										
													break;
												}					
//		
						
						if (sequentialMatcher.find()
								&& (sequentialMatcher.group().length() == expression.length())) {
							expressionType = 3;
							
							System.out.print("a sequential expression processed: " + expression
									+ "\n");
							setconnector(".");
							List<String> seqterms = this.findTermsInExpression(expression, ".",  wp);

							System.out.println("Seqterms are:" +seqterms);
							
							int b = 0;
							for(b=0; b < seqterms.size(); b++){
								String currentterm = seqterms.get(b);
								System.out.println("The current term is:" +currentterm);
								
								Activity seqact = createProcess(currentterm, wp, actprevious);
								actprevious = seqact;
								}
							}else if (orMatcher.find()
								&& (orMatcher.group().length() == expression.length())) {
							expressionType = 2;
							
							System.out.print("an or expression processed: " + expression + "\n");
							numberoforexpressions = numberoforexpressions+1;
							System.out.println("The numberoforexpressions is:"+numberoforexpressions);
							
							d = cal2.getTimeInMillis();
							activityid = activityid+1;
							String rightor = remover(expression);
							Activity XORgate = (Activity) wp.getActivities().generateNewElement();
							XORgate.setName("X:"+rightor);
							XORgate.setId(""+d+""+left+""+activityid);
							XORgate.getActivityTypes().setImplementation();
							XORgate.getActivityTypes().getImplementation().getImplementationTypes().setValue("1");
							XORgate.getActivityTypes().getImplementation().getImplementationTypes().setNo();
							XORgate.getActivityTypes().getRoute().setGatewayTypeExclusive();
							NodeGraphicsInfo ngiact4 = (NodeGraphicsInfo) XORgate.getNodeGraphicsInfos().generateNewElement();
							ngiact4.setShape("annotation");
							 ngiact4.setLaneId(getPoolname());
							 ngiact4.getCoordinates().setXCoordinate(""+Xcoordinate + 200+"");
							 ngiact4.getCoordinates().setYCoordinate(""+Ycoordinate +40+ ""); 
							 ngiact4.setHeight(43);
							 ngiact4.setWidth(31);
							 ngiact4.setBorderColor("0,0,0");
							 ngiact4.setFillColor("255,229,124");
							 XORgate.getNodeGraphicsInfos().add(ngiact4);
							 
							 d = cal2.getTimeInMillis();
							 activityid = activityid+1;
					         System.out.println(".........creating Activity[Id=actd,Name=Decision,Type=Route,Split-type=Exclusive]");
					         Activity actd = (Activity) wp.getActivities().generateNewElement();
					         actd.setId(""+d+""+left+""+activityid);
					         actd.setName("Decision");
					         actd.getActivityTypes().setRoute();
					         System.out.println("............creating TransitionRestriction");
					         TransitionRestriction tre = (TransitionRestriction) XORgate.getTransitionRestrictions()
					            .generateNewElement();
					         tre.getSplit().setTypeExclusive();
					         actd.getTransitionRestrictions().add(tre);
					         System.out.println("............creating NodeGraphicsInfo[LaneId=lane1,Coordinates=200,60]");
					         NodeGraphicsInfo ngid = (NodeGraphicsInfo) actd.getNodeGraphicsInfos()
					            .generateNewElement();
					         ngid.setLaneId(getPoolname());
					         ngid.setWidth(40);
					         ngid.setHeight(40);
					         ngid.getCoordinates().setXCoordinate("200");
					         ngid.getCoordinates().setYCoordinate("60");
					         actd.getNodeGraphicsInfos().add(ngid);
					         wp.getActivities().add(actd);
							 
					         d = cal2.getTimeInMillis();
							 activityid = activityid+1;
							 Activity XORgate2 = (Activity) wp.getActivities().generateNewElement();
							 XORgate2.setName("X2:"+rightor);
							 XORgate2.setId(""+d+""+left+""+activityid);
							 XORgate2.getActivityTypes().setImplementation();
							 XORgate2.getActivityTypes().getImplementation().getImplementationTypes().setValue("1");
							 XORgate2.getActivityTypes().getImplementation().getImplementationTypes().setNo();
							 XORgate2.getActivityTypes().getRoute().setGatewayTypeExclusive();
							 NodeGraphicsInfo ngiact5 = (NodeGraphicsInfo) XORgate2.getNodeGraphicsInfos().generateNewElement();
							 ngiact5.setShape("annotation");
							 ngiact5.setLaneId(getPoolname());
							 ngiact5.getCoordinates().setXCoordinate(""+Xcoordinate + 200+"");
							 ngiact5.getCoordinates().setYCoordinate(""+Ycoordinate +40+ "");
							 ngiact5.setHeight(43);
							 ngiact5.setWidth(31);
							 ngiact5.setBorderColor("0,0,0");
							 ngiact5.setFillColor("255,229,124");
							 XORgate2.getNodeGraphicsInfos().add(ngiact5);
							 //wp.getActivities().add(XORgate2);
							 
							 d = cal2.getTimeInMillis();
							 activityid = activityid+1;
					         System.out.println(".........creating Activity[Id=actd,Name=Decision,Type=Route,Split-type=Exclusive]");
					         Activity actd1 = (Activity) wp.getActivities().generateNewElement();
					         actd1.setId(""+d+""+left+""+activityid);
					         actd1.setName("Decision2");
					         actd1.getActivityTypes().setRoute();
					         System.out.println("............creating TransitionRestriction");
					         TransitionRestriction tre1 = (TransitionRestriction) XORgate2.getTransitionRestrictions()
					            .generateNewElement();
					         tre1.getSplit().setTypeExclusive();
					         actd1.getTransitionRestrictions().add(tre1);
					         System.out.println("............creating NodeGraphicsInfo[LaneId=lane1,Coordinates=200,60]");
					         NodeGraphicsInfo ngid1 = (NodeGraphicsInfo) actd1.getNodeGraphicsInfos()
					            .generateNewElement();
					         ngid1.setLaneId(getPoolname());
					         ngid1.setWidth(40);
					         ngid1.setHeight(40);
					         ngid1.getCoordinates().setXCoordinate("200");
					         ngid1.getCoordinates().setYCoordinate("60");
					         actd1.getNodeGraphicsInfos().add(ngid1);
					         wp.getActivities().add(actd1);
							 
					         d = cal2.getTimeInMillis();
							 Transition orfirst = (Transition) wp.getTransitions().generateNewElement();
							 activityid = activityid +1;
								orfirst.setId(""+d+""+left+""+activityid);
								orfirst.setFrom(actprevious.getId());
								orfirst.setTo(actd.getId());
								wp.getTransitions().add(orfirst);
								
								if(parenthesisflag == 1){
						        	setActivity(actd);
						        	parenthesisflag = 0;
						        }
								
							 List<String> orterms = this.findTermsInExpression(expression, "|",  wp);
								System.out.println("the orterms are:"+orterms);
								
							int or = 0;
							for(or=0; or < orterms.size(); or++){
								String currentterm = orterms.get(or);
								System.out.println("The current term is:"+currentterm);
								actprevious = actd;
								
								Activity ortemp = createProcess(currentterm, wp, actprevious);
								
								d = cal2.getTimeInMillis();
								activityid = activityid +1;
								Transition ortotemp = (Transition) wp.getTransitions().generateNewElement();
								ortotemp.setId(""+d+""+left+""+activityid);
								ortotemp.setFrom(ortemp.getId());
								ortotemp.setTo(actd1.getId());
								wp.getTransitions().add(ortotemp);	
							}
							
							actprevious = actd1;
					}else if (parallelMatcher.find()&& (parallelMatcher.group().length() == expression.length())) {
							expressionType = 1;
							System.out.print("a parallel expression processed: " + expression
									+ "\n");
							
							String rightexpression = remover(expression);
							d = cal2.getTimeInMillis();
							activityid = activityid +1;
						Activity paralleljoin = (Activity) wp.getActivities().generateNewElement();
							paralleljoin.setId(""+d+""+left+""+activityid);
							paralleljoin.setName("para1");
							paralleljoin.getActivityTypes().setImplementation();
						    paralleljoin.getActivityTypes().getImplementation().getImplementationTypes().setNo();
						    paralleljoin.getActivityTypes().getRoute().setGatewayTypeParallel();
							NodeGraphicsInfo ngiact4 = (NodeGraphicsInfo) paralleljoin.getNodeGraphicsInfos().generateNewElement();
					         ngiact4.setLaneId(getPoolname());
					         ngiact4.getCoordinates().setXCoordinate(""+Xcoordinate +200+"");
					         ngiact4.getCoordinates().setYCoordinate(""+Ycoordinate +40+ "");
					         ngiact4.setHeight(43);
					         ngiact4.setWidth(31);
					         ngiact4.setBorderColor("0,0,0");
					         ngiact4.setFillColor("255,229,124");
					         paralleljoin.getNodeGraphicsInfos().add(ngiact4);
					         
					         d = cal2.getTimeInMillis();
					         activityid = activityid+1;
					         System.out.println(".........creating Activity[Id=actd,Name=Decision,Type=Route,Split-type=Parallel]");
					         Activity actd3 = (Activity) wp.getActivities().generateNewElement();
					         actd3.setId(""+d+""+left+""+activityid);
					         actd3.setName("parallel");
					         actd3.getActivityTypes().setRoute();
					         actd3.getActivityTypes().getRoute().setGatewayTypeParallel();
					         System.out.println("............creating TransitionRestriction");
					         TransitionRestriction tre2 = (TransitionRestriction) paralleljoin.getTransitionRestrictions().generateNewElement();
					         tre2.getSplit().setTypeParallel();
					         actd3.getTransitionRestrictions().add(tre2);
					         System.out.println("............creating NodeGraphicsInfo[LaneId=lane1,Coordinates=200,60]");
					         NodeGraphicsInfo ngid2 = (NodeGraphicsInfo) actd3.getNodeGraphicsInfos().generateNewElement();
					         ngid2.setLaneId(getPoolname());
					         ngid2.setWidth(40);
					         ngid2.setHeight(40);
					         ngid2.getCoordinates().setXCoordinate("200");
					         ngid2.getCoordinates().setYCoordinate("60");
					         actd3.getNodeGraphicsInfos().add(ngid2);
					         wp.getActivities().add(actd3); 
			
					         d = cal2.getTimeInMillis();
					     activityid = activityid +1;
						Activity paralleljoin2 = (Activity) wp.getActivities().generateNewElement();
						paralleljoin2.setName("para:2:"+rightexpression);
						paralleljoin2.setId(""+d+""+left+""+activityid);
						paralleljoin2.getActivityTypes().setImplementation();
						paralleljoin2.getActivityTypes().getImplementation().getImplementationTypes().setNo();
						NodeGraphicsInfo ngiact5 = (NodeGraphicsInfo) paralleljoin2.getNodeGraphicsInfos().generateNewElement();
						ngiact5.setShape("annotation");
						ngiact5.setLaneId(getPoolname());
						ngiact5.getCoordinates().setXCoordinate(""+Xcoordinate + 400+"");
						ngiact5.getCoordinates().setYCoordinate(""+Ycoordinate +40+ "");
						ngiact5.setHeight(43);
					    ngiact5.setWidth(31);
						ngiact5.setBorderColor("0,0,0");
						ngiact5.setFillColor("255,229,124");
						paralleljoin2.getNodeGraphicsInfos().add(ngiact5);
						
						
						d = cal2.getTimeInMillis();
						activityid = activityid+1;
				         System.out.println(".........creating Activity[Id=actd,Name=Decision,Type=Route,Split-type=Exclusive]");
				         Activity actd1 = (Activity) wp.getActivities().generateNewElement();
				         actd1.setId(""+d+""+left+""+activityid);
				         actd1.setName("parallel2");
				         actd1.getActivityTypes().setRoute();
				         actd1.getActivityTypes().getRoute().setGatewayTypeParallel();
				         System.out.println("............creating TransitionRestriction");
				         TransitionRestriction tre1 = (TransitionRestriction) paralleljoin2.getTransitionRestrictions()
				            .generateNewElement();
				         tre1.getSplit().setTypeParallel();
				         actd1.getTransitionRestrictions().add(tre1);
				         System.out.println("............creating NodeGraphicsInfo[LaneId=lane1,Coordinates=200,60]");
				         NodeGraphicsInfo ngid3 = (NodeGraphicsInfo) actd1.getNodeGraphicsInfos()
				            .generateNewElement();
				         ngid3.setLaneId(getPoolname());
				         ngid3.setWidth(40);
				         ngid3.setHeight(40);
				         ngid3.getCoordinates().setXCoordinate("200");
				         ngid3.getCoordinates().setYCoordinate("60");
				         actd1.getNodeGraphicsInfos().add(ngid3);
				         wp.getActivities().add(actd1);         
					
				         d = cal2.getTimeInMillis();
					Transition previoustopara = (Transition) wp.getTransitions().generateNewElement();
						activityid = activityid+1;
						previoustopara.setId(""+d+""+left+""+activityid);
						previoustopara.setFrom(actprevious.getId());
						previoustopara.setTo(actd3.getId());
						wp.getTransitions().add(previoustopara);
						
						if(parenthesisflag == 1){
				        	setActivity(actd3);
				        	parenthesisflag = 0;
				        }
						
							
							 List<String> paraterms = this.findTermsInExpression(expression, "||", wp);
								System.out.println("the parallel terms are:"+paraterms);
								
								int par = 0;
								for (par=0; par<paraterms.size(); par++){
									String currentterm = paraterms.get(par);
									System.out.println("The current term is:"+currentterm);
									actprevious = actd3;
									
									Activity paratemp = createProcess(currentterm, wp, actprevious);
									
									
									if(!paratemp.getId().equals(actd3.getId())){
										d = cal2.getTimeInMillis();
									Transition parafirst = (Transition) wp.getTransitions().generateNewElement();
									activityid = activityid +1;
									parafirst.setId(""+d+""+left+""+activityid);
									parafirst.setFrom(paratemp.getId());
									parafirst.setTo(actd1.getId());
									wp.getTransitions().add(parafirst);
									}
									}
								
								actprevious = actd1;
													
						}else{
							String term1 = expression;
							setTerm1(term1);
							System.out.println("term1 is:" +term1);
							Pattern patternBasicTerm = Pattern.compile("^\\w+$");
							Matcher basicTermMatcher = patternBasicTerm.matcher(term1);
							// pattern for (term)
							Pattern patternComplexParenthesisTerm = Pattern
									.compile("^\\(.+\\)$");
							Matcher complexParenthesisTermMatcher = patternComplexParenthesisTerm
									.matcher(term1);
							// pattern for [term]
							Pattern patternComplexOptionalTerm = Pattern.compile("^\\[.+\\]$");
							Matcher complexOptionalTermMatcher = patternComplexOptionalTerm
									.matcher(term1);
							// pattern for term~
							Pattern patternForeverTerm = Pattern.compile(".+~$");
							Matcher foreverTermMatcher = patternForeverTerm.matcher(term1);
							// pattern for term+
							Pattern patternOneOrMoreTimesTerm = Pattern.compile(".+\\+$");
							Matcher oneOrMoreTimesTermMatcher = patternOneOrMoreTimesTerm
									.matcher(term1);
							// pattern for term*
							Pattern patternZeroOrMoreTimesTerm = Pattern.compile(".+\\*$");
							Matcher zeroOrMoreTimesTermMatcher = patternZeroOrMoreTimesTerm
									.matcher(term1);
							
							
							if (complexParenthesisTermMatcher.find()
									&& (complexParenthesisTermMatcher.group().length() == term1
											.length())){
								System.out.println("A parenthesis term is being processed");
								String insideTerm = term1.substring(1, term1.length() - 1);
								System.out.println("the inside term is:"+insideTerm);
								
								Activity parenth = createProcess(insideTerm, wp, actprevious);
								actprevious = parenth;
							}else if (complexOptionalTermMatcher.find()
									&& (complexOptionalTermMatcher.group().length() == term1
									.length())){
								System.out.println("A brackets term is being processed.");
								//x optional [x]
									
									d = cal2.getTimeInMillis();
									activityid = activityid+1;
									Activity oroptional = (Activity) wp.getActivities().generateNewElement();
									oroptional.setName("Xbrackets:"+term1);
							         oroptional.setId(""+d+""+left+""+activityid);
							         oroptional.getActivityTypes().setImplementation();
							         oroptional.getActivityTypes().getImplementation().getImplementationTypes().setNo();
										NodeGraphicsInfo ngiact5 = (NodeGraphicsInfo) oroptional.getNodeGraphicsInfos().generateNewElement();
										ngiact5.setShape("annotation");
										ngiact5.setLaneId(getPoolname());
										ngiact5.getCoordinates().setXCoordinate(""+Xcoordinate + 200+"");
										ngiact5.getCoordinates().setYCoordinate(""+Ycoordinate +40+ "");
										ngiact5.setHeight(43);
										ngiact5.setWidth(31);
										ngiact5.setShape("annotation");
										ngiact5.setBorderColor("0,0,0");
										ngiact5.setFillColor("255,255,51");
								         oroptional.getNodeGraphicsInfos().add(ngiact5);
								         
								         d = cal2.getTimeInMillis();
								         activityid = activityid+1;
								         System.out.println(".........creating Activity[Id=actd,Name=Decision,Type=Route,Split-type=Exclusive]");
								         Activity actd = (Activity) wp.getActivities().generateNewElement();
								         actd.setId(""+d+""+left+""+activityid);
								         actd.setName("Decision:"+term1);
								         actd.getActivityTypes().setRoute();
								         System.out.println("............creating TransitionRestriction");
								         TransitionRestriction tre = (TransitionRestriction) oroptional.getTransitionRestrictions()
								            .generateNewElement();
								         tre.getSplit().setTypeExclusive();
								         actd.getTransitionRestrictions().add(tre);
								         System.out.println("............creating NodeGraphicsInfo[LaneId=lane1,Coordinates=200,60]");
								         NodeGraphicsInfo ngid = (NodeGraphicsInfo) actd.getNodeGraphicsInfos()
								            .generateNewElement();
								         ngid.setLaneId(getPoolname());
								         ngid.setWidth(40);
								         ngid.setHeight(40);
								         ngid.getCoordinates().setXCoordinate("200");
								         ngid.getCoordinates().setYCoordinate("60");
								         actd.getNodeGraphicsInfos().add(ngid);
								         wp.getActivities().add(actd); 
								         
								         d = cal2.getTimeInMillis();
								         activityid = activityid+1;
								         Activity optionaltoend = (Activity) wp.getActivities().generateNewElement();
								         optionaltoend.setName("Xbrackets1:"+term1);
								         optionaltoend.setId(""+d+""+left+""+activityid);
								         optionaltoend.getActivityTypes().setImplementation();
								         optionaltoend.getActivityTypes().getImplementation().getImplementationTypes().setNo();
											NodeGraphicsInfo ngiact6 = (NodeGraphicsInfo) optionaltoend.getNodeGraphicsInfos().generateNewElement();
											ngiact6.setShape("annotation");
											ngiact6.setLaneId(getPoolname());
											ngiact6.getCoordinates().setXCoordinate(""+Xcoordinate + 200+"");
											ngiact6.getCoordinates().setYCoordinate(""+Ycoordinate +40+ "");
											ngiact6.setHeight(43);
											ngiact6.setWidth(31);
											ngiact6.setShape("annotation");
											ngiact6.setBorderColor("0,0,0");
											ngiact6.setFillColor("255,255,51");
									         optionaltoend.getNodeGraphicsInfos().add(ngiact6);
									       
									         
									         d = cal2.getTimeInMillis();
									         activityid = activityid+1;
									         System.out.println(".........creating Activity[Id=actd,Name=Decision,Type=Route,Split-type=Exclusive]");
									         Activity actd1 = (Activity) wp.getActivities().generateNewElement();
									         actd1.setId(""+d+""+left+""+activityid);
									         actd1.setName("Decision2:"+term1);
									         actd1.getActivityTypes().setRoute();
									         System.out.println("............creating TransitionRestriction");
									         TransitionRestriction tre1 = (TransitionRestriction) optionaltoend.getTransitionRestrictions()
									            .generateNewElement();
									         tre1.getSplit().setTypeExclusive();
									         actd1.getTransitionRestrictions().add(tre1);
									         System.out.println("............creating NodeGraphicsInfo[LaneId=lane1,Coordinates=200,60]");
									         NodeGraphicsInfo ngid1 = (NodeGraphicsInfo) actd1.getNodeGraphicsInfos()
									            .generateNewElement();
									         ngid1.setLaneId(getPoolname());
									         ngid1.setWidth(40);
									         ngid1.setHeight(40);
									         ngid1.getCoordinates().setXCoordinate("200");
									         ngid1.getCoordinates().setYCoordinate("60");
									         actd1.getNodeGraphicsInfos().add(ngid1);
									         wp.getActivities().add(actd1);
								        
									  d = cal2.getTimeInMillis();
							         Transition tooroptional = (Transition) wp.getTransitions().generateNewElement();
							         activityid = activityid +1;
										tooroptional.setId(""+d+""+left+""+activityid);
										tooroptional.setFrom(actprevious.getId());
										tooroptional.setTo(actd.getId());
										wp.getTransitions().add(tooroptional);
										
										d = cal2.getTimeInMillis();
										Transition optionaltoend1 = (Transition) wp.getTransitions().generateNewElement();
										activityid = activityid +1;
										optionaltoend1.setId(""+d+""+left+""+activityid);
										optionaltoend1.setFrom(actd.getId());
										optionaltoend1.setTo(actd1.getId());
										wp.getTransitions().add(optionaltoend1);
						        		 
							        	String insideTerm = term1.substring(1, term1.length()-1);
							 			System.out.println("the inside term is:"+insideTerm);
							 			
							 			setinsideTerm(insideTerm);
							 			
							 			if(parenthesisflag == 1){
								        	setActivity(actd);
								        	parenthesisflag = 0;
								        }
							 			
							 				 			
							 			Activity xbrackets = createProcess(insideTerm, wp, actd);
							 			
							 			d = cal2.getTimeInMillis();
							 			Transition xbracketstooptional = (Transition) wp.getTransitions().generateNewElement();
							 			activityid = activityid +1;
							 			xbracketstooptional.setId(""+d+""+left+""+activityid);
							 			xbracketstooptional.setFrom(xbrackets.getId());
							 			xbracketstooptional.setTo(actd1.getId());
							 			wp.getTransitions().add(xbracketstooptional);
							 			
							 			if(bracketsflag == 1){
							 				d = cal2.getTimeInMillis();
							 				Transition bracketstostarting = (Transition) wp.getTransitions().generateNewElement();
							 				activityid = activityid +1;
							 				bracketstostarting.setId(""+d+""+left+""+activityid);
							 				bracketstostarting.setFrom(actd1.getId());
							 				bracketstostarting.setTo(actd.getId());
								 			wp.getTransitions().add(bracketstostarting);
								 			bracketsflag = 0;
							 			}
							 			
							 			actprevious = actd1;
							 			
								}else if (foreverTermMatcher.find() && (foreverTermMatcher.group().length() == term1.length())){
									
									if(term1.startsWith("(") && term1.endsWith(")~")){
										System.out.println("A parenthesis tilda term is being processed");
										String insideTerm = term1.substring(0, term1.length() - 1);
										System.out.println("the inside term is:"+insideTerm);
										
										parenthesisflag =1;
										Activity parenth = createProcess(insideTerm, wp, actprevious);
										actprevious = parenth;
										Activity previous1 = getActivity();
										
										d = cal2.getTimeInMillis();
										Transition toxtilda = (Transition) wp.getTransitions().generateNewElement();
								         activityid = activityid +1;
											toxtilda.setId(""+d+""+left+""+activityid);
											toxtilda.setFrom(actprevious.getId());
											toxtilda.setTo(previous1.getId());
											wp.getTransitions().add(toxtilda);
									}else if(term1.startsWith("[") && term1.endsWith("]~")){
										System.out.println("A brackets tilda term is being processed");
										String insideTerm = term1.substring(0, term1.length() - 1);
										System.out.println("the inside term is:"+insideTerm);
										
										bracketsflag=1;
										Activity parenth = createProcess(insideTerm, wp, actprevious);
										actprevious = parenth;
									}else{
										
										Activity xtilda = null;
								         if(formulas.keySet().contains(term1.substring(0, term1.length()-1))){
											  
								        	 parenthesisflag =1;
								        	 xtilda = handleBasicTerm(term1.substring(0, term1.length()-1), wp, actprevious);
											  actprevious = xtilda;
											  Activity previous1 = getActivity();
											  			
											  d = cal2.getTimeInMillis();
													Transition fromxtilda = (Transition) wp.getTransitions().generateNewElement();
										         activityid = activityid +1;
													fromxtilda.setId(""+d+""+left+""+activityid);
													fromxtilda.setFrom(xtilda.getId());
													fromxtilda.setTo(previous1.getId());
													wp.getTransitions().add(fromxtilda);
												
												actprevious = xtilda;
											}else{//activity x~
												d = cal2.getTimeInMillis();
									activityid = activityid +1;
									xtilda = (Activity) wp.getActivities().generateNewElement();
									xtilda.setName(term1);
									xtilda.setId(""+d+""+left+""+activityid);
									xtilda.getActivityTypes().setImplementation();
									xtilda.getActivityTypes().getImplementation().getImplementationTypes().setNo();
									xtilda.setFirstPerformer(lefthand);
							         NodeGraphicsInfo ngiact = (NodeGraphicsInfo)  xtilda.getNodeGraphicsInfos().generateNewElement();
							         ngiact.setLaneId(getPoolname());
							         ngiact.setShape("annotation");
							         ngiact.getCoordinates().setXCoordinate("50");
							         ngiact.getCoordinates().setYCoordinate("60");
							         ngiact.setBorderColor("0,0,0");
							         ngiact.setFillColor("51,255,255");
							         xtilda.getNodeGraphicsInfos().add(ngiact);
							         wp.getActivities().add(xtilda);
							         roleandname = rolename+":"+term1;
							         rolesnames.add(roleandname);
							         try {
											String tempname = xtilda.getName();
											
											 System.out.println("The name is:"+roleandname);
											 if(tempname.contains("Send")){
												 senderList.add(roleandname);
												 senderid.add(xtilda.getId());
											 }else if(tempname.contains("Receive")){
												 receiverList.add(roleandname);
												 receiverid.add(xtilda.getId());
										 }
										} catch (Exception e) {
//											// TODO Auto-generated catch block
											System.out.println("No name activity.");
										} 
							        
							         d = cal2.getTimeInMillis();
							         Transition toxtilda = (Transition) wp.getTransitions().generateNewElement();
							         activityid = activityid +1;
										toxtilda.setId(""+d+""+left+""+activityid);
										toxtilda.setFrom(actprevious.getId());
										toxtilda.setTo(xtilda.getId());
										wp.getTransitions().add(toxtilda);
							         
										d = cal2.getTimeInMillis();
										Transition fromxtilda = (Transition) wp.getTransitions().generateNewElement();
							         activityid = activityid +1;
										fromxtilda.setId(""+d+""+left+""+activityid);
										fromxtilda.setFrom(xtilda.getId());
										fromxtilda.setTo(xtilda.getId());
										wp.getTransitions().add(fromxtilda);
										
										if(parenthesisflag == 1){
								        	setActivity(xtilda);
								        	parenthesisflag = 0;
								        }
										
										actprevious = xtilda;
										}
									}
								}else if (oneOrMoreTimesTermMatcher.find() && (oneOrMoreTimesTermMatcher.group().length() == term1
										.length())) {
									//activity X+
									if(term1.startsWith("(") && term1.endsWith(")+")){
										System.out.println("A parenthesis plus term is being processed");
										String insideTerm = term1.substring(0, term1.length() - 1);
										System.out.println("the inside term is:"+insideTerm);
										
										d = cal2.getTimeInMillis();
										 activityid = activityid+1;
								         Activity optionaltoend = (Activity) wp.getActivities().generateNewElement();
								         optionaltoend.setName("Xbrackets1:"+term1);
								         optionaltoend.setId(""+d+""+left+""+activityid);
								         optionaltoend.getActivityTypes().setImplementation();
								         optionaltoend.getActivityTypes().getImplementation().getImplementationTypes().setNo();
											NodeGraphicsInfo ngiact6 = (NodeGraphicsInfo) optionaltoend.getNodeGraphicsInfos().generateNewElement();
											ngiact6.setShape("annotation");
											ngiact6.setLaneId(getPoolname());
											ngiact6.getCoordinates().setXCoordinate(""+Xcoordinate + 200+"");
											ngiact6.getCoordinates().setYCoordinate(""+Ycoordinate +40+ "");
											ngiact6.setHeight(43);
											ngiact6.setWidth(31);
											ngiact6.setShape("annotation");
											ngiact6.setBorderColor("0,0,0");
											ngiact6.setFillColor("255,255,51");
									         optionaltoend.getNodeGraphicsInfos().add(ngiact6);
									         
									         d = cal2.getTimeInMillis();
									         activityid = activityid+1;
									         System.out.println(".........creating Activity[Id=actd,Name=Decision,Type=Route,Split-type=Exclusive]");
									         Activity actd1 = (Activity) wp.getActivities().generateNewElement();
									         actd1.setId(""+d+""+left+""+activityid);
									         actd1.setName("Decision:"+term1);
									         actd1.getActivityTypes().setRoute();
									         System.out.println("............creating TransitionRestriction");
									         TransitionRestriction tre1 = (TransitionRestriction) optionaltoend.getTransitionRestrictions()
									            .generateNewElement();
									         tre1.getSplit().setTypeExclusive();
									         actd1.getTransitionRestrictions().add(tre1);
									         System.out.println("............creating NodeGraphicsInfo[LaneId=lane1,Coordinates=200,60]");
									         NodeGraphicsInfo ngid1 = (NodeGraphicsInfo) actd1.getNodeGraphicsInfos()
									            .generateNewElement();
									         ngid1.setLaneId(getPoolname());
									         ngid1.setWidth(40);
									         ngid1.setHeight(40);
									         ngid1.getCoordinates().setXCoordinate("200");
									         ngid1.getCoordinates().setYCoordinate("60");
									         actd1.getNodeGraphicsInfos().add(ngid1);
									         wp.getActivities().add(actd1);
								         
										
										parenthesisflag =1;
										Activity parenth = createProcess(insideTerm, wp, actprevious);
										actprevious = parenth;
										Activity previous1 = getActivity();
										
										d = cal2.getTimeInMillis();
										Transition parenthtoxor = (Transition) wp.getTransitions().generateNewElement();
										activityid = activityid+1;
										parenthtoxor.setId(""+d+""+left+""+activityid);
										parenthtoxor.setFrom(parenth.getId());
										parenthtoxor.setTo(actd1.getId());
										wp.getTransitions().add(parenthtoxor);
										
										d = cal2.getTimeInMillis();
										Transition xortoprevious = (Transition) wp.getTransitions().generateNewElement();
										activityid = activityid +1;
										xortoprevious.setId(""+d+""+left+""+activityid);
										xortoprevious.setFrom(actd1.getId());
										xortoprevious.setTo(previous1.getId());
										wp.getTransitions().add(xortoprevious);
										
										actprevious = actd1;
									}else if(term1.startsWith("[") && term1.endsWith("]+")){
										System.out.println("A brackets plus term is being processed");
										String insideTerm = term1.substring(0, term1.length() - 1);
										System.out.println("the inside term is:"+insideTerm);
										
										bracketsflag=1;
										Activity parenth = createProcess(insideTerm, wp, actprevious);
										actprevious = parenth;
									}else{
										d = cal2.getTimeInMillis();
							         activityid = activityid+1;
							         Activity optionaltoend = (Activity) wp.getActivities().generateNewElement();
							         optionaltoend.setName("Xbrackets1:"+term1);
							         optionaltoend.setId(""+d+""+left+""+activityid);
							         optionaltoend.getActivityTypes().setImplementation();
							         optionaltoend.getActivityTypes().getImplementation().getImplementationTypes().setNo();
										NodeGraphicsInfo ngiact6 = (NodeGraphicsInfo) optionaltoend.getNodeGraphicsInfos().generateNewElement();
										ngiact6.setShape("annotation");
										ngiact6.setLaneId(getPoolname());
										ngiact6.getCoordinates().setXCoordinate(""+Xcoordinate + 200+"");
										ngiact6.getCoordinates().setYCoordinate(""+Ycoordinate +40+ "");
										ngiact6.setHeight(43);
										ngiact6.setWidth(31);
										ngiact6.setShape("annotation");
										ngiact6.setBorderColor("0,0,0");
										ngiact6.setFillColor("255,255,51");
								         optionaltoend.getNodeGraphicsInfos().add(ngiact6);
								         
								         d = cal2.getTimeInMillis();
								         activityid = activityid+1;
								         System.out.println(".........creating Activity[Id=actd,Name=Decision,Type=Route,Split-type=Exclusive]");
								         Activity actd1 = (Activity) wp.getActivities().generateNewElement();
								         actd1.setId(""+d+""+left+""+activityid);
								         actd1.setName("Decision:"+term1);
								         actd1.getActivityTypes().setRoute();
								         System.out.println("............creating TransitionRestriction");
								         TransitionRestriction tre1 = (TransitionRestriction) optionaltoend.getTransitionRestrictions()
								            .generateNewElement();
								         tre1.getSplit().setTypeExclusive();
								         actd1.getTransitionRestrictions().add(tre1);
								         System.out.println("............creating NodeGraphicsInfo[LaneId=lane1,Coordinates=200,60]");
								         NodeGraphicsInfo ngid1 = (NodeGraphicsInfo) actd1.getNodeGraphicsInfos()
								            .generateNewElement();
								         ngid1.setLaneId(getPoolname());
								         ngid1.setWidth(40);
								         ngid1.setHeight(40);
								         ngid1.getCoordinates().setXCoordinate("200");
								         ngid1.getCoordinates().setYCoordinate("60");
								         actd1.getNodeGraphicsInfos().add(ngid1);
								         wp.getActivities().add(actd1);
								         
								         Activity xplus = null;
								         if(formulas.keySet().contains(term1.substring(0, term1.length()-1))){
											  
								        	 parenthesisflag =1;
								        	 xplus = handleBasicTerm(term1.substring(0, term1.length()-1), wp, actprevious);
											  actprevious = xplus;
											  Activity previous1 = getActivity();
											  
											  d = cal2.getTimeInMillis();
											  Transition parenthtoxor = (Transition) wp.getTransitions().generateNewElement();
												activityid = activityid+1;
												parenthtoxor.setId(""+d+""+left+""+activityid);
												parenthtoxor.setFrom(xplus.getId());
												parenthtoxor.setTo(actd1.getId());
												wp.getTransitions().add(parenthtoxor);
												
												d = cal2.getTimeInMillis();
												Transition xortoprevious = (Transition) wp.getTransitions().generateNewElement();
												activityid = activityid +1;
												xortoprevious.setId(""+d+""+left+""+activityid);
												xortoprevious.setFrom(actd1.getId());
												xortoprevious.setTo(previous1.getId());
												wp.getTransitions().add(xortoprevious);
												
												actprevious = actd1;
											}else{
												d = cal2.getTimeInMillis();
											activityid = activityid +1;
											xplus = (Activity) wp.getActivities().generateNewElement();
											xplus.setId(""+d+""+left+""+activityid);
											xplus.setName(term1);
											xplus.getActivityTypes().setImplementation();
											xplus.getActivityTypes().getImplementation().getImplementationTypes().setNo();
											xplus.setFirstPerformer(lefthand);
									         NodeGraphicsInfo ngiact = (NodeGraphicsInfo)  xplus.getNodeGraphicsInfos().generateNewElement();
									         ngiact.setLaneId(getPoolname());
									         ngiact.setShape("annotation");
									         ngiact.getCoordinates().setXCoordinate("50");
									         ngiact.getCoordinates().setYCoordinate("60");
									         ngiact.setBorderColor("0,0,0");
									         ngiact.setFillColor("120,120,98");
									         xplus.getNodeGraphicsInfos().add(ngiact);
									         wp.getActivities().add(xplus);
									         roleandname = rolename+":"+term1;
									         rolesnames.add(roleandname);
									         try {
													String tempname = xplus.getName();
													 System.out.println("The name is:"+roleandname);
													 if(tempname.contains("Send")){
														 senderList.add(roleandname);
														 senderid.add(xplus.getId());
													 }else if(tempname.contains("Receive")){
														 receiverList.add(roleandname);
														 receiverid.add(xplus.getId());
												 }
												} catch (Exception e) {
//													// TODO Auto-generated catch block
													System.out.println("No name activity.");
												} 
									         
									         
									         d = cal2.getTimeInMillis();
									         Transition previoustoplus = (Transition) wp.getTransitions().generateNewElement();
									         activityid = activityid+1;
									         previoustoplus.setId(""+d+""+left+""+activityid);
									         previoustoplus.setFrom(actprevious.getId());
									         previoustoplus.setTo(xplus.getId());
									         wp.getTransitions().add(previoustoplus);
									         
									         d = cal2.getTimeInMillis();
									         Transition xplustoxor = (Transition) wp.getTransitions().generateNewElement();
									         activityid = activityid+1;
									         xplustoxor.setId(""+d+""+left+""+activityid);
									         xplustoxor.setFrom(xplus.getId());
									         xplustoxor.setTo(actd1.getId());
									         wp.getTransitions().add(xplustoxor);
									         
									         d = cal2.getTimeInMillis();
									         Transition xortoxplus = (Transition) wp.getTransitions().generateNewElement();
									         activityid = activityid+1;
									         xortoxplus.setId(""+d+""+left+""+activityid);
									         xortoxplus.setFrom(actd1.getId());
									         xortoxplus.setTo(xplus.getId());
									         wp.getTransitions().add(xortoxplus);
									         
									         if(parenthesisflag == 1){
										        	setActivity(xplus);
										        	parenthesisflag = 0;
										        }
									         
									         actprevious = actd1;
											}
							         
							        
								}
									}
							else if ((zeroOrMoreTimesTermMatcher.find()	&& (zeroOrMoreTimesTermMatcher.group().length() == term1.length()))){
								if(term1.startsWith("(") && term1.endsWith(")*")){
									
									System.out.println("A parenthesis star term is being processed");
									
									d = cal2.getTimeInMillis();			        
							         activityid = activityid+1;
							         Activity xstargate = (Activity) wp.getActivities().generateNewElement();
							         xstargate.setId(""+d+""+left+""+activityid);
							         xstargate.setName("X1:"+term1);
							         xstargate.getActivityTypes().setImplementation();
							         xstargate.getActivityTypes().getImplementation().getImplementationTypes().setNo();
										NodeGraphicsInfo ngiact4 = (NodeGraphicsInfo) xstargate.getNodeGraphicsInfos().generateNewElement();
										ngiact4.setShape("annotation");
								         ngiact4.setLaneId(getPoolname());
								         ngiact4.getCoordinates().setXCoordinate(""+Xcoordinate + 200+"");
								         ngiact4.getCoordinates().setYCoordinate(""+Ycoordinate +40+ "");
								         ngiact4.setHeight(43);
								         ngiact4.setWidth(31);
								         ngiact4.setShape("annotation");
								         ngiact4.setBorderColor("0,0,0");
								         ngiact4.setFillColor("255,229,124");
								         xstargate.getNodeGraphicsInfos().add(ngiact4);
								         
								         d = cal2.getTimeInMillis();
								         activityid = activityid+1;
								         System.out.println(".........creating Activity[Id=actd,Name=Decision,Type=Route,Split-type=Exclusive]");
								         Activity actd = (Activity) wp.getActivities().generateNewElement();
								         actd.setId(""+d+""+left+""+activityid);
								         actd.setName("Decision:"+term1);
								         actd.getActivityTypes().setRoute();
								         System.out.println("............creating TransitionRestriction");
								         TransitionRestriction tre = (TransitionRestriction) xstargate.getTransitionRestrictions()
								            .generateNewElement();
								         tre.getSplit().setTypeExclusive();
								         actd.getTransitionRestrictions().add(tre);
								         System.out.println("............creating NodeGraphicsInfo[LaneId=lane1,Coordinates=200,60]");
								         NodeGraphicsInfo ngid = (NodeGraphicsInfo) actd.getNodeGraphicsInfos()
								            .generateNewElement();
								         ngid.setLaneId(getPoolname());
								         ngid.setWidth(40);
								         ngid.setHeight(40);
								         ngid.getCoordinates().setXCoordinate("200");
								         ngid.getCoordinates().setYCoordinate("60");
								         actd.getNodeGraphicsInfos().add(ngid);
								         wp.getActivities().add(actd);
								         
								         d = cal2.getTimeInMillis();
								         activityid = activityid+1;
								         Activity xstargate2 = (Activity) wp.getActivities().generateNewElement();
								         xstargate2.setId(""+d+""+left+""+activityid);
								         xstargate2.setName("X2:"+term1);
								         xstargate2.getActivityTypes().setImplementation();
								         xstargate2.getActivityTypes().getImplementation().getImplementationTypes().setNo();
											NodeGraphicsInfo ngiact5 = (NodeGraphicsInfo) xstargate2.getNodeGraphicsInfos().generateNewElement();
											ngiact5.setShape("annotation");
											ngiact5.setLaneId(getPoolname());
											ngiact5.getCoordinates().setXCoordinate(""+Xcoordinate + 200+"");
											ngiact5.getCoordinates().setYCoordinate(""+Ycoordinate +40+ "");
											ngiact5.setHeight(43);
											ngiact5.setWidth(31);
											ngiact5.setShape("annotation");
											ngiact5.setBorderColor("0,0,0");
											ngiact5.setFillColor("255,229,124");
									         xstargate2.getNodeGraphicsInfos().add(ngiact5);
									         
									         d = cal2.getTimeInMillis();
									         activityid = activityid+1;
									         System.out.println(".........creating Activity[Id=actd,Name=Decision,Type=Route,Split-type=Exclusive]");
									         Activity actd1 = (Activity) wp.getActivities().generateNewElement();
									         actd1.setId(""+d+""+left+""+activityid);
									         actd1.setName("Decision2:"+term1);
									         actd1.getActivityTypes().setRoute();
									         System.out.println("............creating TransitionRestriction");
									         TransitionRestriction tre1 = (TransitionRestriction) xstargate2.getTransitionRestrictions()
									            .generateNewElement();
									         tre1.getSplit().setTypeExclusive();
									         actd1.getTransitionRestrictions().add(tre1);
									         System.out.println("............creating NodeGraphicsInfo[LaneId=lane1,Coordinates=200,60]");
									         NodeGraphicsInfo ngid1 = (NodeGraphicsInfo) actd1.getNodeGraphicsInfos()
									            .generateNewElement();
									         ngid1.setLaneId(getPoolname());
									         ngid1.setWidth(40);
									         ngid1.setHeight(40);
									         ngid1.getCoordinates().setXCoordinate("200");
									         ngid1.getCoordinates().setYCoordinate("60");
									         actd1.getNodeGraphicsInfos().add(ngid1);
									         wp.getActivities().add(actd1);
									         
									         String insideTerm = term1.substring(0, term1.length() - 1);
												System.out.println("the inside term is:"+insideTerm);
												
												Activity parenth = createProcess(insideTerm, wp, actd);
												
												d = cal2.getTimeInMillis();
									         Transition tra1tostar = (Transition) wp.getTransitions().generateNewElement();
									         activityid = activityid +1;
												tra1tostar.setId(""+d+""+left+""+activityid);
											tra1tostar.setFrom(actprevious.getId());
											tra1tostar.setTo(actd.getId());
											wp.getTransitions().add(tra1tostar);
											
											d = cal2.getTimeInMillis();
											Transition x1tox2 = (Transition) wp.getTransitions().generateNewElement();
											activityid = activityid +1;
											x1tox2.setId(""+d+""+left+""+activityid);
											x1tox2.setFrom(actd.getId());
											x1tox2.setTo(actd1.getId());
											wp.getTransitions().add(x1tox2);
											
											d = cal2.getTimeInMillis();
									         Transition startox2 = (Transition) wp.getTransitions().generateNewElement();
									         activityid = activityid +1;
												startox2.setId(""+d+""+left+""+activityid);
												startox2.setFrom(parenth.getId());
												startox2.setTo(actd1.getId());
												wp.getTransitions().add(startox2);
												
											d = cal2.getTimeInMillis();
									         Transition x2tostar = (Transition) wp.getTransitions().generateNewElement();
									         activityid = activityid +1;
												x2tostar.setId(""+d+""+left+""+activityid);
												x2tostar.setFrom(actd1.getId());
												x2tostar.setTo(actd.getId());
												wp.getTransitions().add(x2tostar);
												
												if(parenthesisflag == 1){
										        	setActivity(actd);
										        	parenthesisflag = 0;
										        }
												
												actprevious = actd1;	
									
								}else if(term1.startsWith("[") && term1.endsWith("]*")){
									System.out.println("A brackets star term is being processed");
									
									d = cal2.getTimeInMillis();			        
							         activityid = activityid+1;
							         Activity xstargate = (Activity) wp.getActivities().generateNewElement();
							         xstargate.setId(""+d+""+left+""+activityid);
							         xstargate.setName("X1:"+term1);
							         xstargate.getActivityTypes().setImplementation();
							         xstargate.getActivityTypes().getImplementation().getImplementationTypes().setNo();
										NodeGraphicsInfo ngiact4 = (NodeGraphicsInfo) xstargate.getNodeGraphicsInfos().generateNewElement();
										ngiact4.setShape("annotation");
								         ngiact4.setLaneId(getPoolname());
								         ngiact4.getCoordinates().setXCoordinate(""+Xcoordinate + 200+"");
								         ngiact4.getCoordinates().setYCoordinate(""+Ycoordinate +40+ "");
								         ngiact4.setHeight(43);
								         ngiact4.setWidth(31);
								         ngiact4.setShape("annotation");
								         ngiact4.setBorderColor("0,0,0");
								         ngiact4.setFillColor("255,229,124");
								         xstargate.getNodeGraphicsInfos().add(ngiact4);
								         
								         d = cal2.getTimeInMillis();
								         activityid = activityid+1;
								         System.out.println(".........creating Activity[Id=actd,Name=Decision,Type=Route,Split-type=Exclusive]");
								         Activity actd = (Activity) wp.getActivities().generateNewElement();
								         actd.setId(""+d+""+left+""+activityid);
								         actd.setName("Decision:"+term1);
								         actd.getActivityTypes().setRoute();
								         System.out.println("............creating TransitionRestriction");
								         TransitionRestriction tre = (TransitionRestriction) xstargate.getTransitionRestrictions()
								            .generateNewElement();
								         tre.getSplit().setTypeExclusive();
								         actd.getTransitionRestrictions().add(tre);
								         System.out.println("............creating NodeGraphicsInfo[LaneId=lane1,Coordinates=200,60]");
								         NodeGraphicsInfo ngid = (NodeGraphicsInfo) actd.getNodeGraphicsInfos()
								            .generateNewElement();
								         ngid.setLaneId(getPoolname());
								         ngid.setWidth(40);
								         ngid.setHeight(40);
								         ngid.getCoordinates().setXCoordinate("200");
								         ngid.getCoordinates().setYCoordinate("60");
								         actd.getNodeGraphicsInfos().add(ngid);
								         wp.getActivities().add(actd);
								         
								         d = cal2.getTimeInMillis();
								         activityid = activityid+1;
								         Activity xstargate2 = (Activity) wp.getActivities().generateNewElement();
								         xstargate2.setId(""+d+""+left+""+activityid);
								         xstargate2.setName("X2:"+term1);
								         xstargate2.getActivityTypes().setImplementation();
								         xstargate2.getActivityTypes().getImplementation().getImplementationTypes().setNo();
											NodeGraphicsInfo ngiact5 = (NodeGraphicsInfo) xstargate2.getNodeGraphicsInfos().generateNewElement();
											ngiact5.setShape("annotation");
											ngiact5.setLaneId(getPoolname());
											ngiact5.getCoordinates().setXCoordinate(""+Xcoordinate + 200+"");
											ngiact5.getCoordinates().setYCoordinate(""+Ycoordinate +40+ "");
											ngiact5.setHeight(43);
											ngiact5.setWidth(31);
											ngiact5.setShape("annotation");
											ngiact5.setBorderColor("0,0,0");
											ngiact5.setFillColor("255,229,124");
									         xstargate2.getNodeGraphicsInfos().add(ngiact5);
									         
									         d = cal2.getTimeInMillis();
									         activityid = activityid+1;
									         System.out.println(".........creating Activity[Id=actd,Name=Decision,Type=Route,Split-type=Exclusive]");
									         Activity actd1 = (Activity) wp.getActivities().generateNewElement();
									         actd1.setId(""+d+""+left+""+activityid);
									         actd1.setName("Decision2:"+term1);
									         actd1.getActivityTypes().setRoute();
									         System.out.println("............creating TransitionRestriction");
									         TransitionRestriction tre1 = (TransitionRestriction) xstargate2.getTransitionRestrictions()
									            .generateNewElement();
									         tre1.getSplit().setTypeExclusive();
									         actd1.getTransitionRestrictions().add(tre1);
									         System.out.println("............creating NodeGraphicsInfo[LaneId=lane1,Coordinates=200,60]");
									         NodeGraphicsInfo ngid1 = (NodeGraphicsInfo) actd1.getNodeGraphicsInfos()
									            .generateNewElement();
									         ngid1.setLaneId(getPoolname());
									         ngid1.setWidth(40);
									         ngid1.setHeight(40);
									         ngid1.getCoordinates().setXCoordinate("200");
									         ngid1.getCoordinates().setYCoordinate("60");
									         actd1.getNodeGraphicsInfos().add(ngid1);
									         wp.getActivities().add(actd1);
									         
									         String insideTerm = term1.substring(0, term1.length() - 1);
												System.out.println("the inside term is:"+insideTerm);
												
												Activity parenth = createProcess(insideTerm, wp, actd);
											
											d = cal2.getTimeInMillis();
									         Transition tra1tostar = (Transition) wp.getTransitions().generateNewElement();
									         activityid = activityid +1;
												tra1tostar.setId(""+d+""+left+""+activityid);
											tra1tostar.setFrom(actprevious.getId());
											tra1tostar.setTo(actd.getId());
											wp.getTransitions().add(tra1tostar);
											
											d = cal2.getTimeInMillis();
											Transition x1tox2 = (Transition) wp.getTransitions().generateNewElement();
											activityid = activityid +1;
											x1tox2.setId(""+d+""+left+""+activityid);
											x1tox2.setFrom(actd.getId());
											x1tox2.setTo(actd1.getId());
											wp.getTransitions().add(x1tox2);
											
											d = cal2.getTimeInMillis();
									         Transition startox2 = (Transition) wp.getTransitions().generateNewElement();
									         activityid = activityid +1;
												startox2.setId(""+d+""+left+""+activityid);
												startox2.setFrom(parenth.getId());
												startox2.setTo(actd1.getId());
												wp.getTransitions().add(startox2);
												
											d = cal2.getTimeInMillis();
									         Transition x2tostar = (Transition) wp.getTransitions().generateNewElement();
									         activityid = activityid +1;
												x2tostar.setId(""+d+""+left+""+activityid);
												x2tostar.setFrom(actd1.getId());
												x2tostar.setTo(actd.getId());
												wp.getTransitions().add(x2tostar);
												
												if(parenthesisflag == 1){
										        	setActivity(actd);
										        	parenthesisflag = 0;
										        }
												
												actprevious = actd1;	
								}else{	Activity xstar = null;
										
											d = cal2.getTimeInMillis();
								         activityid = activityid+1;
								         Activity xstargate = (Activity) wp.getActivities().generateNewElement();
								         xstargate.setId(""+d+""+left+""+activityid);
								         xstargate.setName("X1:"+term1);
								         xstargate.getActivityTypes().setImplementation();
								         xstargate.getActivityTypes().getImplementation().getImplementationTypes().setNo();
											NodeGraphicsInfo ngiact4 = (NodeGraphicsInfo) xstargate.getNodeGraphicsInfos().generateNewElement();
											ngiact4.setShape("annotation");
									         ngiact4.setLaneId(getPoolname());
									         ngiact4.getCoordinates().setXCoordinate(""+Xcoordinate + 200+"");
									         ngiact4.getCoordinates().setYCoordinate(""+Ycoordinate +40+ "");
									         ngiact4.setHeight(43);
									         ngiact4.setWidth(31);
									         ngiact4.setShape("annotation");
									         ngiact4.setBorderColor("0,0,0");
									         ngiact4.setFillColor("255,229,124");
									         xstargate.getNodeGraphicsInfos().add(ngiact4);
									         
									         d = cal2.getTimeInMillis();
									         activityid = activityid+1;
									         System.out.println(".........creating Activity[Id=actd,Name=Decision,Type=Route,Split-type=Exclusive]");
									         Activity actd = (Activity) wp.getActivities().generateNewElement();
									         actd.setId(""+d+""+left+""+activityid);
									         actd.setName("Decision:"+term1);
									         actd.getActivityTypes().setRoute();
									         System.out.println("............creating TransitionRestriction");
									         TransitionRestriction tre = (TransitionRestriction) xstargate.getTransitionRestrictions()
									            .generateNewElement();
									         tre.getSplit().setTypeExclusive();
									         actd.getTransitionRestrictions().add(tre);
									         System.out.println("............creating NodeGraphicsInfo[LaneId=lane1,Coordinates=200,60]");
									         NodeGraphicsInfo ngid = (NodeGraphicsInfo) actd.getNodeGraphicsInfos()
									            .generateNewElement();
									         ngid.setLaneId(getPoolname());
									         ngid.setWidth(40);
									         ngid.setHeight(40);
									         ngid.getCoordinates().setXCoordinate("200");
									         ngid.getCoordinates().setYCoordinate("60");
									         actd.getNodeGraphicsInfos().add(ngid);
									         wp.getActivities().add(actd);
									         
									         if(formulas.keySet().contains(term1.substring(0, term1.length()-1))){
													xstar = handleBasicTerm(term1.substring(0, term1.length()-1), wp, actd);
												}else{	
												d = cal2.getTimeInMillis();
												activityid = activityid+1;
												xstar = (Activity) wp.getActivities().generateNewElement();
												xstar.setId(""+d+""+left+""+activityid);
												xstar.setName(term1);
												xstar.getActivityTypes().setImplementation();
												xstar.getActivityTypes().getImplementation().getImplementationTypes().setNo();
												xstar.setFirstPerformer(lefthand);
										         NodeGraphicsInfo ngiact = (NodeGraphicsInfo)  xstar.getNodeGraphicsInfos().generateNewElement();
										         ngiact.setLaneId(getPoolname());
										         ngiact.setShape("annotation");
										         ngiact.getCoordinates().setXCoordinate("50");
										         ngiact.getCoordinates().setYCoordinate("60");
										         ngiact.setBorderColor("0,0,0");
										         ngiact.setFillColor("0,0,255");
										         xstar.getNodeGraphicsInfos().add(ngiact);
										         wp.getActivities().add(xstar);
										         roleandname = rolename+":"+term1;
										         rolesnames.add(roleandname);
										         try {
														String tempname = xstar.getName();
														 System.out.println("The name is:"+roleandname);
														 if(tempname.contains("Send")){
															 senderList.add(roleandname);
															 senderid.add(xstar.getId());
														 }else if(tempname.contains("Receive")){
															 receiverList.add(roleandname);
															 receiverid.add(xstar.getId());
													 }
													} catch (Exception e) {
//														// TODO Auto-generated catch block
														System.out.println("No name activity.");
													} 
												}
									         
									         d = cal2.getTimeInMillis();
									         activityid = activityid+1;
									         Activity xstargate2 = (Activity) wp.getActivities().generateNewElement();
									         xstargate2.setId(""+d+""+left+""+activityid);
									         xstargate2.setName("X2:"+term1);
									         xstargate2.getActivityTypes().setImplementation();
									         xstargate2.getActivityTypes().getImplementation().getImplementationTypes().setNo();
												NodeGraphicsInfo ngiact5 = (NodeGraphicsInfo) xstargate2.getNodeGraphicsInfos().generateNewElement();
												ngiact5.setShape("annotation");
												ngiact5.setLaneId(getPoolname());
												ngiact5.getCoordinates().setXCoordinate(""+Xcoordinate + 200+"");
												ngiact5.getCoordinates().setYCoordinate(""+Ycoordinate +40+ "");
												ngiact5.setHeight(43);
												ngiact5.setWidth(31);
												ngiact5.setShape("annotation");
												ngiact5.setBorderColor("0,0,0");
												ngiact5.setFillColor("255,229,124");
										         xstargate2.getNodeGraphicsInfos().add(ngiact5);
										         
										         d = cal2.getTimeInMillis();
										         activityid = activityid+1;
										         System.out.println(".........creating Activity[Id=actd,Name=Decision,Type=Route,Split-type=Exclusive]");
										         Activity actd1 = (Activity) wp.getActivities().generateNewElement();
										         actd1.setId(""+d+""+left+""+activityid);
										         actd1.setName("Decision2:"+term1);
										         actd1.getActivityTypes().setRoute();
										         System.out.println("............creating TransitionRestriction");
										         TransitionRestriction tre1 = (TransitionRestriction) xstargate2.getTransitionRestrictions()
										            .generateNewElement();
										         tre1.getSplit().setTypeExclusive();
										         actd1.getTransitionRestrictions().add(tre1);
										         System.out.println("............creating NodeGraphicsInfo[LaneId=lane1,Coordinates=200,60]");
										         NodeGraphicsInfo ngid1 = (NodeGraphicsInfo) actd1.getNodeGraphicsInfos()
										            .generateNewElement();
										         ngid1.setLaneId(getPoolname());
										         ngid1.setWidth(40);
										         ngid1.setHeight(40);
										         ngid1.getCoordinates().setXCoordinate("200");
										         ngid1.getCoordinates().setYCoordinate("60");
										         actd1.getNodeGraphicsInfos().add(ngid1);
										         wp.getActivities().add(actd1);
										         
										         d = cal2.getTimeInMillis();
										         Transition tra1tostar = (Transition) wp.getTransitions().generateNewElement();
										         activityid = activityid +1;
													tra1tostar.setId(""+d+""+left+""+activityid);
												tra1tostar.setFrom(actprevious.getId());
												tra1tostar.setTo(actd.getId());
												wp.getTransitions().add(tra1tostar);
												
												d = cal2.getTimeInMillis();
										         Transition x1tostar = (Transition) wp.getTransitions().generateNewElement();
										         activityid = activityid +1;
													x1tostar.setId(""+d+""+left+""+activityid);
												x1tostar.setFrom(actd.getId());
												x1tostar.setTo(xstar.getId());
												wp.getTransitions().add(x1tostar);
												
												d = cal2.getTimeInMillis();
												Transition x1tox2 = (Transition) wp.getTransitions().generateNewElement();
												activityid = activityid +1;
												x1tox2.setId(""+d+""+left+""+activityid);
												x1tox2.setFrom(actd.getId());
												x1tox2.setTo(actd1.getId());
												wp.getTransitions().add(x1tox2);
												
												d = cal2.getTimeInMillis();
										         Transition startox2 = (Transition) wp.getTransitions().generateNewElement();
										         activityid = activityid +1;
													startox2.setId(""+d+""+left+""+activityid);
													startox2.setFrom(xstar.getId());
													startox2.setTo(actd1.getId());
													wp.getTransitions().add(startox2);
													
													d = cal2.getTimeInMillis();
										         Transition x2tostar = (Transition) wp.getTransitions().generateNewElement();
										         activityid = activityid +1;
													x2tostar.setId(""+d+""+left+""+activityid);
													x2tostar.setFrom(actd1.getId());
													x2tostar.setTo(xstar.getId());
													wp.getTransitions().add(x2tostar);
													
													if(parenthesisflag == 1){
											        	setActivity(actd);
											        	parenthesisflag = 0;
											        }
													
													actprevious = actd1;	
										}
									}else if(basicTermMatcher.find()){
										
										if(formulas.keySet().contains(term1)){
											Activity act = handleBasicTerm(term1, wp, actprevious);
											actprevious = act;
										}else{
											d = cal2.getTimeInMillis();
										activityid = activityid +1;
										Activity activitytemp = (Activity) wp.getActivities().generateNewElement();
										activitytemp.setId(""+d+""+left+""+activityid);
										activitytemp.setName(term1);
										activitytemp.getActivityTypes().setImplementation();
										activitytemp.getActivityTypes().getImplementation().getImplementationTypes().setNo();
										activitytemp.setFirstPerformer(lefthand);
								         NodeGraphicsInfo ngiact2 = (NodeGraphicsInfo) activitytemp.getNodeGraphicsInfos().generateNewElement();
								         ngiact2.setLaneId(getPoolname());
								         ngiact2.setShape("annotation");
							         ngiact2.getCoordinates().setXCoordinate(""+Xcoordinate + 200+"");
							         ngiact2.getCoordinates().setYCoordinate(""+Ycoordinate +40+ "");
							         activitytemp.getNodeGraphicsInfos().add(ngiact2);
							         wp.getActivities().add(activitytemp);
							         roleandname = rolename+":"+term1;
							         rolesnames.add(roleandname);
							         try {
											String tempname = activitytemp.getName();
											 System.out.println("The name is:"+roleandname);
											 if(tempname.contains("Send")){
												 senderList.add(roleandname);
												 senderid.add(activitytemp.getId());
											 }else if(tempname.contains("Receive")){
												 receiverList.add(roleandname);
												 receiverid.add(activitytemp.getId());
										 }
										} catch (Exception e) {
//											// TODO Auto-generated catch block
											System.out.println("No name activity.");
										} 
							         d = cal2.getTimeInMillis(); 
							        Transition previoustobasic = (Transition) wp.getTransitions().generateNewElement();
							        activityid = activityid+1;
							        previoustobasic.setId(""+d+""+left+""+activityid);
							        previoustobasic.setFrom(actprevious.getId());
							        previoustobasic.setTo(activitytemp.getId());
							        wp.getTransitions().add(previoustobasic);
							        
							        if(parenthesisflag == 1){
							        	setActivity(activitytemp);
							        	parenthesisflag = 0;
							        }
							        
							        actprevious = activitytemp;
									}
							}
						}
					return actprevious;	
}					
 

	private void setActivity(Activity actprevious) {
		// TODO Auto-generated method stub
		previous = actprevious;
	}

	private Activity getActivity(){
		return previous;
	}
	public List<String> findTermsInExpression(String expression,
				String connector, WorkflowProcess wp) {
			
			System.out.println("The connector is:"+connector);
			List<String> paraTerms = new LinkedList<String>();
			List<String> orTerms = new LinkedList<String>();
			List<String> seqTerms = new LinkedList<String>();
			
			if (connector == "."){
				StringTokenizer t = new StringTokenizer(expression, connector);
				String currentTerm = new String();
			while (t.hasMoreTokens()) {
				
				int parenthesis = 0;
				int brackets = 0;
				currentTerm = currentTerm + t.nextToken();
				System.out.println("The currentTerm is:"+currentTerm);
				for (int i = 0; i < currentTerm.length(); i++) {
					if (currentTerm.regionMatches(i, "(", 0, 1))
						parenthesis++;
					if (currentTerm.regionMatches(i, ")", 0, 1))
						parenthesis--;
					if (currentTerm.regionMatches(i, "[", 0, 1))
						brackets++;
					if (currentTerm.regionMatches(i, "]", 0, 1))
						brackets--;
			}
				System.out.println("The currentTerm is:"+currentTerm);
				if ((parenthesis == 0) && (brackets == 0)) {
					foundTerms.add(currentTerm);
					System.out.print("found term: " + currentTerm + "\n");
					System.out.println("The found terms are:" +foundTerms);
					findTerms.add(currentTerm);
					System.out.println("List is:"+findTerms);
					
					if (connector == "."){
						  numberofseq = numberofseq+1;
							System.out.println("Numberofsequential:"+numberofseq);
							  seqTerms.add(currentTerm);
							  System.out.println("The seqterms are:" +seqTerms);
							  expressionList.add(currentTerm);
					  System.out.println("simpleTerm is:" +expressionList); 
					}
					else if(connector == "||")
					{
							numberofpara = numberofpara +1;
							System.out.println("Numberofparallel:"+numberofpara);
							paraTerms.add(currentTerm);
							expressionList.add(currentTerm);
							  System.out.println("simpleTerm is:" +expressionList);
						}
					else if(connector == "|")
					{
							numberofors = numberofors + 1;
							System.out.println("Numberofors:"+numberofors);
							orTerms.add(currentTerm);
							expressionList.add(currentTerm);
							  System.out.println("simpleTerm is:" +expressionList);	
					}	else{
						expressionList.add(currentTerm);
					}
					currentTerm = new String();				
			}
				 else
					 currentTerm = currentTerm + connector;
				System.out.println("The temp term is:" +currentTerm);
			}
		}else if(connector == "|"){
				List<String> characterlist = new LinkedList<>();
				int parenthesis =0;
				int brackets = 0;
				int i =0;
				System.out.println("The expression is:"+expression);
				
				for(i=0; i<expression.length(); i++){
					String newterm = "";
					char character = expression.charAt(i);
					
					newterm = newterm + character;
					characterlist.add(newterm);
				}
				System.out.println("The characterlist is:"+characterlist);
				
				String tempterm = "";
				String tempop ="";
				int k = 0;
				for(k=0; k<characterlist.size(); k++){
					tempterm = tempterm + characterlist.get(k);
					try {
						tempop = characterlist.get(k+1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
					if(characterlist.get(k).equals("(")){
						parenthesis = parenthesis +1;
					}else if(characterlist.get(k).equals(")")){
						parenthesis = parenthesis -1;
						if(parenthesis==0){	
						if(tempop.equals("~")|tempop.equals("+")|tempop.equals("*")){
							orTerms.add(tempterm+tempop);
							tempterm="";
						}else{	
							orTerms.add(tempterm);
							tempterm = "";		
							}
						}
					}else if(characterlist.get(k).equals("[")){
						brackets = brackets+1;
					}else if(characterlist.get(k).equals("]")){
						brackets = brackets-1;
						if(brackets==0){	
							if(tempop.equals("~")|tempop.equals("+")|tempop.equals("*")){
								orTerms.add(tempterm+tempop);
								tempterm="";
							}else{	
								orTerms.add(tempterm);
								tempterm = "";		
								}
							}
					}else if(characterlist.get(k).equals("|") && (parenthesis==0 && brackets==0)){
						if(tempterm.equals("|")){
							tempterm="";
						}
						else{tempterm = tempterm.substring(0, tempterm.length()-1);
						if(tempterm.equals("~")|tempterm.equals("+")|tempterm.equals("*")){
							tempterm="";
						}else{
						orTerms.add(tempterm);
						tempterm = "";
						}
						}
						
					}else if(k==characterlist.size()-1){
						if(tempterm.equals("~")|tempterm.equals("+")|tempterm.equals("*")){
							tempterm="";
						}else{
						orTerms.add(tempterm);
						}
					}

				}
				//parallel
			}else if(connector == "||"){

				List<String> characterlist = new LinkedList<>();
				int parenthesis =0;
				int brackets = 0;
				int i =0;
				System.out.println("The expression is:"+expression);
				
				for(i=0; i<expression.length(); i++){
					String newterm = "";
					char character = expression.charAt(i);
					newterm = newterm + character;
					characterlist.add(newterm);
				}
				System.out.println("The characterlist is:"+characterlist);
				
				String tempterm = "";
				int k = 0;
				String tempop ="";
				for(k=0; k<characterlist.size(); k++){
					
					tempterm = tempterm + characterlist.get(k);
					try {
						tempop = characterlist.get(k+1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
					if(characterlist.get(k).equals("(")){
						parenthesis = parenthesis +1;
					}else if(characterlist.get(k).equals(")")){
						parenthesis = parenthesis -1;
						if(parenthesis==0){
							if(tempterm.startsWith("|")){
								tempterm = tempterm.substring(1, tempterm.length());
								
								if(tempop.equals("~")|tempop.equals("+")|tempop.equals("*")){
									paraTerms.add(tempterm+tempop);
									tempterm="";
								}else{	
									paraTerms.add(tempterm);
									tempterm = "";		
									}
							}
						}
					}else if(characterlist.get(k).equals("[")){
						brackets = brackets+1;
					}else if(characterlist.get(k).equals("]")){
						brackets = brackets-1;
						if(brackets==0){
							if(tempterm.startsWith("|")){
								tempterm = tempterm.substring(1, tempterm.length());
								if(tempop.equals("~")|tempop.equals("+")|tempop.equals("*")){
									paraTerms.add(tempterm+tempop);
									tempterm="";
								}else{	
									paraTerms.add(tempterm);
									tempterm = "";		
									}
							}	}
					}else if(characterlist.get(k).equals("|") && characterlist.get(k+1).equals("|") && ((parenthesis==0) && (brackets==0))){
						if(tempterm.equals("")){
							tempterm="";
						}
						else if(tempterm.equals("~")|tempterm.equals("+")|tempterm.equals("*")){
							tempterm="";
						}else{
							if(tempterm.startsWith("|") && tempterm.endsWith("|") && !(tempterm =="|")){
								if(tempterm.equals("~")|tempterm.equals("+")|tempterm.equals("*")){
									tempterm="";
								}else{
								if(tempterm.equals("|")){
									tempterm = tempterm.substring(0);
									tempterm="";
								}else{
								tempterm = tempterm.substring(1, tempterm.length()-1);
								if(tempterm.equals("")){}
								else{paraTerms.add(tempterm);
								tempterm="";
								}}}
							}else if(!(tempterm.startsWith("|")) && tempterm.endsWith("|")){
								if(tempterm.equals("~")|tempterm.equals("+")|tempterm.equals("*")){
									tempterm="";
								}else{
							tempterm = tempterm.substring(0, tempterm.length()-1);
							if(tempterm.endsWith("|")){
								
								tempterm = tempterm.substring(0, tempterm.length()-1);
								if(tempterm.equals("")){}
								else{
								paraTerms.add(tempterm);
								tempterm="";
								}}else{
								if(tempterm.equals("~")|tempterm.equals("+")|tempterm.equals("*")){
									tempterm="";
								}
								if(tempterm.endsWith("|")){
									tempterm = tempterm.substring(0, tempterm.length()-1);
									if(tempterm.equals("")){}
									else{
									paraTerms.add(tempterm);
									tempterm="";
								}}else{
									if(tempterm.equals("")){}
									else{
							paraTerms.add(tempterm);
							tempterm = "";
									}}
							}
							}}else{
								if(tempterm.equals("~")|tempterm.equals("+")|tempterm.equals("*")){
									tempterm="";
								}else{
							paraTerms.add(tempterm);
								}}
						tempterm = "";
					}}else if (k==characterlist.size()-1){
						if(tempterm.equals("~")|tempterm.equals("+")|tempterm.equals("*")){
							tempterm="";
						}else{
						if(tempterm.startsWith("|")){
						tempterm = tempterm.substring(1, tempterm.length());
						if(tempterm.equals("")){}
						else{
						paraTerms.add(tempterm);}
						}else{
							if(tempterm.equals("~")|tempterm.equals("+")|tempterm.equals("*")){
								tempterm="";
							}else{
						paraTerms.add(tempterm);
						tempterm="";
							}
						}
					}
					}
					}
				}
			System.out.println("found terms"+foundTerms);
			System.out.println("expressionList"+expressionList);
			
			if (connector == "."){
				return seqTerms;
			}else if (connector == "|"){
				return orTerms;
			}else if (connector == "||"){
				return paraTerms;
			}
			else return foundTerms;
				
		}

	
	public void setinsideTerm(String insideTerm) {
		insideTerm1 = insideTerm;
	}

	public String getinsideTerm(){
		return insideTerm1;
	}
	private String remover(String term1) {
		
		String testterm1 = term1.replace("~", "");
		String testterm2 = testterm1.replace("*", "");
		String testterm3 = testterm2.replace("(", "");
		String testterm4 = testterm3.replace(")", "");
		String testterm5 = testterm4.replace("[", "");
		String testterm6 = testterm5.replace("]", "");
		String testterm7 = testterm6.replace("|", "");
		String testterm8 = testterm7.replace("+", "");
		
		return testterm8;
		}

	public void setconnector(String connector1){
		 connector=connector1;
	}
	
	public String getconnector(){
		return connector;
	}

	
	public static void writeToFile(String outputFile, Package pkg) throws Exception{
		// TODO Auto-generated method stub
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
	

	public Activity handleBasicTerm(String term, WorkflowProcess wp, Activity actprevious){
		Activity tmp = createProcess(formulas.get(term), wp, actprevious);
		return tmp;
	}
	
	public String getOrId(){
		return orId;
		}
	
	public void setOrId(String input){
		orId = input;
	}
	
	public String getBracketsname(){
		return bracketsterm;
	}
	
	public void setBracketsname(String name){
		bracketsterm = name;
	}
	
	public String getBrackets(){
		return bracket1;
	}
	
	public void setBrackets(String name){
		bracket1 = name;
	}
	
	public String getTerm1(){
		return term123;
	}
	
	public void setTerm1(String name){
		term123 = name;
	}
	
	public Transition setendTransition(Transition name){
		return name;
	}
	
	public static void setPoolname(String poolname){
		pname = poolname;
	}

	public String getPoolname(){
		return pname;
	}
}
	
