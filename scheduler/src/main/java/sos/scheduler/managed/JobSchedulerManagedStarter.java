/**
 * Copyright (C) 2014 BigLoupe http://bigloupe.github.io/SoS-JobScheduler/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
/********************************************************* begin of preamble
**
** Copyright (C) 2003-2012 Software- und Organisations-Service GmbH. 
** All rights reserved.
**
** This file may be used under the terms of either the 
**
**   GNU General Public License version 2.0 (GPL)
**
**   as published by the Free Software Foundation
**   http://www.gnu.org/licenses/gpl-2.0.txt and appearing in the file
**   LICENSE.GPL included in the packaging of this file. 
**
** or the
**  
**   Agreement for Purchase and Licensing
**
**   as offered by Software- und Organisations-Service GmbH
**   in the respective terms of supply that ship with this file.
**
** THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
** IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
** THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
** PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
** BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
** CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
** SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
** INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
** CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
** ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
** POSSIBILITY OF SUCH DAMAGE.
********************************************************** end of preamble*/
package sos.scheduler.managed;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import sos.connection.SOSConnection;
import sos.connection.SOSOracleConnection;
import sos.scheduler.command.SOSSchedulerCommand;
import sos.scheduler.job.JobSchedulerJob;
import sos.settings.SOSProfileSettings;
import sos.spooler.*;
import sos.util.SOSClassUtil;
import sos.util.SOSDate;
import sos.util.SOSFile;
import sos.util.SOSLogger;
import sos.util.SOSSchedulerLogger;
import sos.util.SOSStandardLogger;
import sos.xml.SOSXMLXPath;


/**
 * This class submits Managed (v2) Job Scheduler configurations from
 * the database to the Job Scheduler.<br/>
 * It needs to be run as a startscript.
 * @version 2.0
 * @author Andreas Liebert 
 * @since 2007-04-13
 */
public class JobSchedulerManagedStarter extends JobSchedulerJob {
	
		
	/** is the job running as a startscript? */
    private boolean startscript = true;
    
    /** id of this Scheduler */
    private String schedulerID="";
		Variable_set orderParams = null;
    private boolean once=false;
    private boolean remove=false;
    
    private static Pattern xmlEncodingPattern = Pattern.compile("\\<\\?xml.*encoding=\"(.*)\".*\\?\\>");
    
    private boolean useLiveFolder = false;
    
    private File liveFolder;
    
    private Transformer managed2liveTransformer;
    
    private final static String indicatorRegex = "(.*\\.xml)\\s([a-f0-9]+)\\s(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}(\\.\\d)?)$";
    
    private Pattern indicatorRegexPattern;
    
    
    private final String  getWhat(String type) {
    	String what="unknown type:" + type;
      if (type.equalsIgnoreCase("o"))
  		   what = "order";
  	   if (type.equalsIgnoreCase("j"))
  		   what = "orderjob";
  	   if (type.equalsIgnoreCase("i"))
  		   what = "job";
  	   if (type.equalsIgnoreCase("f"))
  		   what = "job.global";
  	   if (type.equalsIgnoreCase("l"))
  		  what = "lock";
  	   if ( type.equalsIgnoreCase("c"))
   		  what = "job_chain";
  	   if ( type.equalsIgnoreCase("p"))
   		  what = "process_class";
  	   if ( type.equalsIgnoreCase("k"))
  		   what = "documentation";
       return what;
    }
    
    private final String  getType(String what) {
    	String type="unknown what:" + what;
    	
      if (what.equalsIgnoreCase("order"))
  		   type = "o";
  	   if (what.equalsIgnoreCase("orderjob"))
  	  	 type = "j";
  	   if (what.equalsIgnoreCase("job"))
  	  	 type = "i";
  	   if (what.equalsIgnoreCase("job.global"))
  	  	 type = "f";
  	   if (what.equalsIgnoreCase("lock"))
  	  	 type = "l";
  	   if ( what.equalsIgnoreCase("job_chain"))
  	  	 type = "c";
  	   if ( what.equalsIgnoreCase("process_class"))
  	  	 type = "p";
  	 if ( what.equalsIgnoreCase("documentation"))
  	  	 type = "k";
       return type;
    }    
    
    private final String  getDirectory(String what) {
    	String dir="unknown what:" + what;
    	      
  	 if ( what.equalsIgnoreCase("documentation"))
  	  	 dir = "jobs/";
       return dir;
    }
   
   
	/**
     * Spooler init is called for startscripts on Job Scheduler startup
     */
    public boolean spooler_init() {
    	boolean rc = super.spooler_init();
    	liveFolder = new File(spooler.configuration_directory());
    	useLiveFolder = false;
    	indicatorRegexPattern = Pattern.compile(indicatorRegex);
    	
    	startscript = spooler_job==null;
    	schedulerID = spooler.id().toLowerCase();
    	if (!rc) return false || startscript;    	
    	
    	if (startscript)
			try {
				setLogger(new SOSSchedulerLogger(spooler.log()));
				getLog().info("SchedulerManagedStarter is running as startscript");
				spooler.set_var("scheduler_managed_jobs_version","2");
			} catch (Exception e2) {}
		else
			try {
				setLogger(new SOSSchedulerLogger(spooler_log));
				getLog().info("SchedulerManagedStarter is running as job");
			} catch (Exception e3) {}
    	
		if (useLiveFolder){
			try{
				String key = "javax.xml.transform.TransformerFactory";
				String value = "org.apache.xalan.xsltc.trax.TransformerFactoryImpl";
				Properties props = System.getProperties();
				props.put(key, value);
				System.setProperties(props);
				TransformerFactory tFactory = TransformerFactory.newInstance();
				tFactory.setAttribute("translet-name", "managed2live");
				tFactory.setAttribute("package-name", "sos.scheduler.translet");
				tFactory.setAttribute("use-classpath", Boolean.TRUE);
				managed2liveTransformer = tFactory.newTransformer(new StreamSource("managed2live.xsl"));				
			} catch (Exception e){
				spooler_log.error("Failed to initialize translet: "+e);
				if (this.getConnection() !=  null) {
					try { this.getConnection().rollback(); } catch (Exception ex) {} // no error handling
					try { this.getConnection().disconnect(); } catch (Exception ex) {} // no error handling
					// this.setConnection(null);
				}
				return false;
			}
		}
		if (startscript){
			try {
				if (useLiveFolder){
					initializeManagedLiveFolders();
				}
				// initialize locks, jobs, job chains and orders
				initializeWhat("job.global");
		    	initializeWhat("lock");
		    	initializeWhat("process_class");
		    	initializeWhat("documentation");
		    	initializeWhat("orderjob");
		    	initializeWhat("job");
		    	initializeWhat("job_chain");
		    	initializeWhat("order");
		    	

				return true;
			}
			catch (Exception e) {
				try {
					getLog().error("error occurred in initialization: " + e.getMessage());
				} catch (Exception e1) {}
				
				return false || startscript;
			}
			finally {
				if (this.getConnection() !=  null) {
					try { this.getConnection().rollback(); } catch (Exception ex) {} // no error handling
					try { this.getConnection().disconnect(); } catch (Exception ex) {} // no error handling
					// this.setConnection(null);
				}
			}
		}
		return true;
    }
    
    
    private void initializeManagedLiveFolders() throws Exception {
		try{
			Vector folderList = SOSFile.getFolderlist(liveFolder.getAbsolutePath(), ".*", 0, true);
			Iterator folderIterator = folderList.iterator();
			while (folderIterator.hasNext()){
				File currentDir = (File) folderIterator.next();
				if (!currentDir.isDirectory()) continue;
				File managedIndicator = new File(currentDir, ".managed");
				if (!managedIndicator.exists()){
					getLog().debug3("Skipping "+currentDir.getAbsolutePath()+
							" . This is not a managed directory.");
					continue;
				}
				initializeManagedLiveFolder(currentDir);
			}
		} catch (Exception e){
			throw new Exception ("Error initializing managed live folders: "+e);
		}
	}

	private void initializeManagedLiveFolder(File currentDir) throws Exception {
		try{
			getLog().debug2("Processing managed live folder: "+currentDir.getAbsolutePath());
			Vector xmlFileList = SOSFile.getFilelist(currentDir.getAbsolutePath(), ".*\\.(job|order|job_chain|process_class|lock)\\.xml$", 0);
			Iterator fileIterator = xmlFileList.iterator();
			while (fileIterator.hasNext()){
				File currentFile = (File) fileIterator.next();
				if (currentFile.isDirectory()) continue;
				getLog().debug3("Deleting managed file "+currentFile.getAbsolutePath());
				if (!currentFile.delete()){
					throw new Exception("Failed to delete file "+currentFile.getAbsolutePath());
				}
			}
		} catch (Exception e){
			throw new Exception("Error initializing "+currentDir.getAbsolutePath()+
					": "+e,e);
		}		
	}

	private void handleOnce(String what, String name, String path,String xml, String answer) throws Exception {
		if (path.length()>0) name= path+"/"+name;
    	if (orderParams != null)
        once = orderParams.var("once") != null && orderParams.var("once").equalsIgnoreCase("yes");

        if(once)  getLog().debug3("once: " + what + " will be started");
        else      getLog().debug3("NOT once: " + what + " will NOT be started");
         
      if(once){
	      //Wenn ein Repeat-Intervall angegeben ist, nicht starten.
	      //Repeat f�r bestimmte Tage werden nicht ausgewertet.
      	
        SOSXMLXPath xpath = new SOSXMLXPath(new StringBuffer(xml));
        SOSXMLXPath xpath_answer = new SOSXMLXPath(new StringBuffer(answer));
    
          
          boolean start = true;
          String nodeQuery = "//add_order[@at]";
          Node runtime;
          if (what.equals("order")){
        	  
        	  Node atNode = xpath.selectSingleNode(nodeQuery);
        	  nodeQuery = "//run_time";
        	  runtime = xpath.selectSingleNode(nodeQuery);
        	  
        	  if(atNode == null && runtime == null){
        		  start = false;
        		  getLog().debug1("handleOnce:" + name + " Once=yes. Order will not be modified because 'no at and no runtime' is given in <add_order>");
        	  }
        	  
          }
          
          nodeQuery = "//run_time[@repeat]";
          runtime = xpath.selectSingleNode(nodeQuery);
          if(runtime != null){
          	  start = false;
              getLog().debug1("handleOnce:" + name + " Once=yes. "+what+" will not be modified because 'repeat' is given in <runtime>");
          }
          
          
          nodeQuery = "//run_time/period[@repeat]";
          runtime = xpath.selectSingleNode(nodeQuery);
          if(runtime != null && start){
          	  start = false;
              getLog().debug1("handleOnce:" + name + " Once=yes. "+what+" will not be startet because 'repeat' is given in <runtime>");
          }
          if(start){
          	  String command = "";
          	  if (what.equals("order")){
          	    String job_chain = xpath.selectSingleNodeValue("//add_order/@job_chain");
          	    String id = xpath_answer.selectSingleNodeValue("//spooler/answer/ok/order/@id");
                  
          	    command = "<modify_order at=\"now\" job_chain=\"" + job_chain + "\"  order=\"" + id + "\"/>";
                
          	  }
          	  if (what.equals("job")){
                command = "<start_job job=\"" + name + "\" at=\"now\"/>";
          	  }

          	  getLog().debug3(command);
          	  if(useLiveFolder){
          		  // load changes from files first
          		  execute_xml("<check_folders/>");
          	  }
              execute_xml(command);
              getLog().debug1("handleOnce:  " + what + " " + name + " was startet");
          }    	
      }
    }
   
    
    private void handleJobInit(String name, String xml)throws Exception {
      // Job ist evtl. in vorigem Job-Lauf angelegt worden
      try {
      if(!useLiveFolder && spooler.job(name) != null){
         getLog().debug1("handleJobInit:Job " + name + " will be removed");
         execute_xml("<modify_job job=\"" + name + "\" cmd=\"remove\"/>");
       }
       } catch (Exception e) {} // gracefully ignore this error
    }

    private void handleJobClose(String name, String path, String xml, String answer) throws Exception {
      handleOnce("job",name, path, xml, answer);	
    }

  
    private void addParam(Document params, Element p, String name, String value) {
        Element param = params.createElement("param");
        param.setAttribute("name", name);
        param.setAttribute("value", value);
        p.appendChild(param);
    }

    private void handleProcessClassInit(String name, String xml) throws Exception {
    	String removeProcessClass = "<process_class.remove 	process_class  	= \"" + name + "\"/>";
     	try {
      	execute_xml(removeProcessClass);
  		} catch (Exception e) {}    	 
    }
    
    private String handleOrderInit(String name, String xml) throws Exception {
        getLog().debug3("handleOrderInit(" + name + ")");
        
       
        
        String scheduler_order_id = "";
        String scheduler_order_title = "";
        String scheduler_order_job_chain = "";
        String scheduler_order_state = "";
        
        SOSXMLXPath xpath = new SOSXMLXPath(new StringBuffer(xml));
        NamedNodeMap nodeMapSettings = null;
        String nodeQuery = "//add_order";
        Node add_order = xpath.selectSingleNode(nodeQuery);
       
        if (add_order != null){

        	 nodeMapSettings = add_order.getAttributes();
           
            if(nodeMapSettings != null && nodeMapSettings.getNamedItem("title") != null)
                scheduler_order_title = nodeMapSettings.getNamedItem("title").getNodeValue();
            if(nodeMapSettings != null && nodeMapSettings.getNamedItem("job_chain") != null)
                scheduler_order_job_chain = nodeMapSettings.getNamedItem("job_chain").getNodeValue();
            if(nodeMapSettings != null && nodeMapSettings.getNamedItem("state") != null)
                scheduler_order_state = nodeMapSettings.getNamedItem("state").getNodeValue();
        }

        getLogger().debug3("add_order: title=" + scheduler_order_title);
        getLogger().debug3("add_order: job_chain=" + scheduler_order_job_chain);
        getLogger().debug3("add_order: state=" + scheduler_order_state);

        
        if (remove){
        	String removeOrder = "<remove_order	job_chain  	= \"" + scheduler_order_job_chain + "\" order = \"" + name + "\"/>" ;
        	try {
          	execute_xml(removeOrder);
      		} catch (Exception e) {}
        }        
        
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        ByteArrayInputStream bai = new ByteArrayInputStream(xml.getBytes("UTF-8"));
        InputSource source = new InputSource(bai);
        
        Document xmlDoc = docBuilder.parse(source);
        Element p = null;
        NodeList params = xmlDoc.getElementsByTagName("params");
        
        if(params == null || params.item(0) == null) {
            p = xmlDoc.createElement("params");
            NodeList r = xmlDoc.getElementsByTagName("run_time");
            NodeList addOrder = xmlDoc.getElementsByTagName("add_order");
            if (addOrder != null) {
               addOrder.item(0).insertBefore(p, r.item(0));
            }
        } else {
            p = (Element)params.item(0);
        }
        
        
        addParam(xmlDoc, p, "scheduler_order_id", scheduler_order_id);
        addParam(xmlDoc, p, "scheduler_order_title", scheduler_order_title);
        addParam(xmlDoc, p, "scheduler_order_job_chain", scheduler_order_job_chain);
        addParam(xmlDoc, p, "scheduler_order_state", scheduler_order_state);
        
        boolean ignore_runtime = false;
        if (orderParams != null)
           ignore_runtime = orderParams.var("ignore_runtime") != null && orderParams.var("ignore_runtime").equalsIgnoreCase("yes");
       

          if (ignore_runtime)  {
            getLog().debug3("ignore_runtime: yes");
            NodeList r = xmlDoc.getElementsByTagName("run_time");            
            if (r!=null && r.item(0)!=null && r.item(0).getParentNode()!=null){
            	Element e = (Element)  r.item(0).getParentNode();            	
            	e.removeAttribute("id");     
            	e.removeAttribute("at");
            	r.item(0).getParentNode().removeChild(r.item(0));            	
            }
          

          }
          else  getLog().debug3("ignore_runtime: No");
       
          
          
        StringWriter out = new StringWriter();
        XMLSerializer serializer = new XMLSerializer(out, new OutputFormat(xmlDoc));
        serializer.serialize(xmlDoc);
        getLog().info("adding order [" + scheduler_order_id + "] to job chain [" + scheduler_order_job_chain + "]: ");
        getLog().debug3(out.toString());

        return  out.toString();
    }


    private void handleOrderClose(String name, String path, String xml, String answer) throws Exception {
      handleOnce("order",name, path,xml, answer);	
    }
    
    private void handleJobchainClose(String name, String path, String xml) throws Exception {
    	if (path.length()>0) name= path+"/"+name;
    	if (!useLiveFolder){
    		boolean exist = spooler.job_chain_exists(name);
    	
    		if(!exist){
    			throw new Exception("Job_chain "+name+" could not be created");
    		}
    	}
    }  
    
    private void handleJobchainInit(String name, String xml) throws Exception {
    boolean exist = spooler.job_chain_exists(name);
    	
    getLog().debug3("looking for Job_chain " + name);
    if(exist && !useLiveFolder){
      	Job_chain j = spooler.job_chain(name);
      	j.remove();
        getLog().debug3("Job_chain " + name + " was deleted for replacement");
      }
    }

    private String handleInit(String xml, String what, String name) throws Exception {
     	getLog().debug3("handleInit(" + what + "," + name + ")");
      
     	if (orderParams != null)
     	remove = orderParams.var("remove") != null && orderParams.var("remove").equalsIgnoreCase("yes");
      
      //if(what.equals("job"))
        //    handleJobInit(name, xml);
      if(what.equals("order"))
           xml = handleOrderInit(name, xml);
      if(what.equals("job_chain"))
           handleJobchainInit(name, xml);
      if(what.equals("process_class"))
           handleProcessClassInit(name, xml);
      return xml;
    }

    private void handleClose(String xml, String answer, String what, String name, String path) throws Exception {
        if(what.equals("job"))
            handleJobClose(name, path, xml, answer);
        if(what.equals("order"))
          handleOrderClose(name, path, xml, answer);
        if(what.equals("job_chain"))
          handleJobchainClose(name, path, xml);
    }

    private void initializeWhat(String what) throws Exception {
		
        String type = getType(what);
    		try {
    			getLog().info("initializing " + what + "s...");
    			String query = "SELECT ";
    			query += " o.\"NAME\", o.\"ID\", t.\"MODIFIED\" ";
    			if (useLiveFolder) query += ", t.\"PATH\"";
    			
    			if(!(getConnection() instanceof SOSOracleConnection)){
    				query += " FROM " + JobSchedulerManagedObject.tableManagedObjects +" o LEFT OUTER JOIN "
    			        + " "+JobSchedulerManagedObject.tableManagedTree+" t ON o.\"ID\"=t.\"ITEM_ID\""
    					+ " WHERE (t.\"LINK_ID\"=0 OR t.\"LINK_ID\" IS NULL)";
    			}else{
    				query += " FROM " + JobSchedulerManagedObject.tableManagedObjects +" o,"
    				+ " "+JobSchedulerManagedObject.tableManagedTree+" t"
    				+ " WHERE o.\"ID\"=t.\"ITEM_ID\"(+) AND t.\"LINK_ID\"(+)=0";
    			}

    			query+= " AND o.\"SUSPENDED\"=0 AND o.\"TYPE\"='" + type + "' AND " + "(\"SPOOLER_ID\"='"
				+ schedulerID + "' OR \"SPOOLER_ID\" IS NULL)";
    			
    			ArrayList results = getConnection().getArray(query);
    			Iterator iter = results.iterator();
    			while (iter != null && iter.hasNext()) {
    				HashMap result = (HashMap) iter.next();
    				String name = null;
    				if (result.get("name") != null) {

            		String blob_query = "SELECT \"XML\"";
    				   	blob_query += " FROM " + JobSchedulerManagedObject.tableManagedObjects 
    							+ " WHERE \"ID\" = " + result.get("id").toString();
    					
    					   String xml = getConnection().getClob(blob_query);
    					
    					
    					   name = result.get("name").toString();
    					   String modified = result.get("modified").toString();
    					   String path = "";
    					   if (useLiveFolder){
    						   path = result.get("path").toString();
    						   path = path.substring(0, path.lastIndexOf("/"));
    					   }
    					   getLog().debug1("Initializing " + what + " \"" + name + "\"");
    					   try{
    						 submitXml(xml, what, name, modified, path);
    					   } catch (Exception e){
    						   getLog().warn("Error occurred initializing " + what + " \"" + name + "\": "+e);
    					   }
    				}else {
    					getLog().warn("NAME is null");
    				}
    			}
    		} catch (Exception e) {
    			throw new Exception("Error occured initializing " + what + ": " + e);
    		}
    	}  

  private void remove(String type, String name){
	  if (type.equalsIgnoreCase("f")){
		  removeSection(name);
	  }
	  if (type.equalsIgnoreCase("k")){
		  removeSchedulerFile(name, getWhat(type));
	  }
  }
  
  

private void initialize(String id) throws Exception {
		String what = "managed object";
		try {
			String query = "SELECT o.\"REFERENCE\", o.\"TYPE\", o.\"NAME\", t.\"MODIFIED\"";
			if (useLiveFolder) query += ", t.\"PATH\"";
			String blob_query = "SELECT \"XML\"";
			
	    /*String name = "managedChain2";
			String reference = "44";
			String type = "j";
			*/
 
			if(!(getConnection() instanceof SOSOracleConnection)){
				query += " FROM " + JobSchedulerManagedObject.tableManagedObjects +" o LEFT OUTER JOIN "
			        + " "+JobSchedulerManagedObject.tableManagedTree+" t ON o.\"ID\"=t.\"ITEM_ID\""
					+ " WHERE (t.\"LINK_ID\"=0 OR t.\"LINK_ID\" IS NULL)";
			}else{
				query += " FROM " + JobSchedulerManagedObject.tableManagedObjects +" o,"
				+ " "+JobSchedulerManagedObject.tableManagedTree+" t"
				+ " WHERE o.\"ID\"=t.\"ITEM_ID\"(+) AND t.\"LINK_ID\"(+)=0";
			}

			query+= " AND o.\"ID\"=" + id ;
			
						
			blob_query += " FROM " + JobSchedulerManagedObject.tableManagedObjects + " o "
			+ "WHERE o.\"ID\"=" + id;

 			 ArrayList results = getConnection().getArray(query);
			 Iterator iter = results.iterator();
			 
			 if (!iter.hasNext()) {
				 throw new Exception("no configuration found with id=" + id);
			 } else{
				 
				 String xml = getConnection().getClob(blob_query);

			   HashMap result = (HashMap) iter.next();
	  	   String name = result.get("name").toString();
			   String type = result.get("type").toString();
			   String modified = result.get("modified").toString();
			   String path = "";
			   if (useLiveFolder){
				   path = result.get("path").toString();
				   path = path.substring(0, path.lastIndexOf("/"));
			   }
	 
  	 
					  	
         what = getWhat(type);

			   getLog().debug1("Initializing " + what + " \"" + name + "\"");

			// String xml = "<add_order id=\"myOrder\" job_chain=\"jobchain\"
			// title=\"Test fuer Managed Starter\" state=\"100\"> <run_time
			// let_run=\"no\"/></add_order>";
			// String xml = "<job_chain name=\"managedChain2\"
			// orders_recoverable=\"yes\" visible=\"yes\"> <job_chain_node
			// state=\"100\" job=\"job100\" next_state=\"200\"/> <job_chain_node
			// state=\"200\"/></job_chain>";
			
			//String xml = "<email_settings	to=\"re@sos-berlin.com\"	cc=\"oh@sos-berlin.com\"	bcc=\"ur@sos-berlin.com\"></email_settings>";

//			String xml = "<job name=\"job100\" order=\"yes\" title=\"Shellscript auf 8of9 ausfuehren\"> <script language=\"javascript\"> <![CDATA[ function spooler_process(){ spooler_log.info(\"100: Replaced\"); return true; } ]]> </script> <delay_after_error error_count=\"10\" delay=\"00:01\"/> <run_time let_run=\"no\"/> </job> "; 
			submitXml(xml, what, name, modified, path);
 		 }
		} catch (Exception e) {
			throw new Exception("Error occured initializing " + what + ": " + e);
		}
	}
	
	private void writeSection(String source, String section,Properties entries) throws Exception {
		/** section pattern */
	  Pattern SECTION_PATTERN = Pattern.compile("^\\s*\\[([^\\]]*)\\].*$");

	   
	  String sectionName = null;
	  BufferedReader in  = null;
	  BufferedWriter out = null;
	  String line        = null;
	  boolean sectionStart = false;
	  boolean otherSectionStart = false;
	  boolean haveSection = false;
	  String newIni      = "";

	    try {

	      File file = new File(source);
	      if ( !file.exists() )
	      throw (new Exception( "couldn't find profile [" + source + "]."));

	      in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        
	      while (true) {
	        line = in.readLine();
	        if (line == null)
	          break;
	        Matcher matcher = SECTION_PATTERN.matcher(line);
	        if (matcher.matches()) {
	          sectionName = matcher.group(1);
	          if (sectionName.equalsIgnoreCase(section)) {
	          	sectionStart = true;  //Damit Sektion �berlesen werden kann.
	          	otherSectionStart = false;
	          } else otherSectionStart = true;
	        }
	        if (sectionStart) {
	        	haveSection=true;
	        	if (entries.isEmpty()){//Sektion l�schen
	        		getLog().debug3("Found section "+section+". Deleting section...");
	        	} else{
	        		getLog().debug3("Found section "+section+". Modifying section...");
	        		newIni += line + "\n";  //Die Sektion [job ... ]schreiben
	        		Enumeration keyNames = entries.propertyNames();
	        		while (keyNames.hasMoreElements()){  //Die Entries schreiben.
	        			String keyName= keyNames.nextElement().toString();      
	        			String keyValue= entries.getProperty(keyName);      
	        			newIni += keyName + "=" + keyValue + "\n";
	        		}
	        	}
	        	sectionStart = false; //Jetzt werden die bisherigen Eintr�ge der Sektion geschrieben oder gel�scht
	        }else {
	        	if (haveSection && !otherSectionStart) {
	        		//Die Entries aus den Properties nicht schreiben
	        		if (line.startsWith(";")){ //Kommentarzeilen immer schreiben
	  	        	newIni += line + "\n";
	        		}else {
	        		   StringTokenizer t = new StringTokenizer(line, "=");
	        		   if (t.hasMoreTokens()){
	        			   String token = t.nextToken();
	        			   if (!entries.containsKey(token) && !entries.isEmpty()){// entries empty: Sektion soll gel�scht werden
	        				   newIni += line + "\n";
	        			   }
	        		   } else{ //keine name=wert Zeile-->uebernehmen, ausser wenn Sektion gel�scht wird
	        			   if (!entries.isEmpty()){
	        				   newIni += line + "\n";
	        			   }
	        		   }
	        		        
	        		}
	        		
	        	}else {
		        	newIni += line + "\n";
	        	}
//	        	System.out.println(line);
	        }
	      }
	      
	      
        if (!haveSection) {
        	newIni += "\n[" + section + "]" + "\n";  //Die Sektion [job ... ]schreiben
        	Enumeration keyNames = entries.propertyNames();
        	while (keyNames.hasMoreElements()){  //Die Entries schreiben.
             String keyName= keyNames.nextElement().toString();      
             String keyValue= entries.getProperty(keyName);      
             newIni += keyName + "=" + keyValue + "\n";
 	        }
        }
	      
	      
	      out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
	      out.write(newIni);
	      out.close();
	      
	      if (getLog() != null) getLog().debug3(SOSClassUtil.getMethodName() + ": profile [" + source + "] successfully loaded.");
	    } catch(Exception e) {
	      throw (new Exception( SOSClassUtil.getMethodName() + ": " + e.toString()));
	    } finally {
	      if ( in == null) try { in.close(); } catch(Exception e) {}
	    }
	  }
	
	private void removeSection(String name) {
		try{
			String section = "job " + name;
			String factoryIni = spooler.ini_path();
			SOSProfileSettings p = new SOSProfileSettings(factoryIni, "emptySection");
			Properties job = p.getSection();
			writeSection(factoryIni, section, job);
		} catch (Exception e){
			try{
				getLog().warn("Error occured removing job settings \""+name+"\": "+e);
			} catch (Exception ex) {}
		}
		
	}
	
	private void updateFactoryIni(String xml, String name) throws Exception {
    String to="";
    String cc= "";
    String bcc="";
    String subject="";
    String body="";
    String factoryIni = spooler.ini_path();
    // String factoryIni = "c:\\scheduler.al\\config\\factory.ini";
    
    String section = "job " + name; 
    
    SOSXMLXPath xpath = new SOSXMLXPath(new StringBuffer(xml));
    NamedNodeMap nodeMapSettings = null;
    String nodeQuery = "//email_settings";
    Node emailSettings = xpath.selectSingleNode(nodeQuery);
    if(emailSettings != null){
        nodeMapSettings = emailSettings.getAttributes();
        if(nodeMapSettings != null && nodeMapSettings.getNamedItem("to") != null)
          to = nodeMapSettings.getNamedItem("to").getNodeValue();
        if(nodeMapSettings != null && nodeMapSettings.getNamedItem("cc") != null)
          cc = nodeMapSettings.getNamedItem("cc").getNodeValue();
        if(nodeMapSettings != null && nodeMapSettings.getNamedItem("bcc") != null)
          bcc = nodeMapSettings.getNamedItem("bcc").getNodeValue();
        if(nodeMapSettings != null && nodeMapSettings.getNamedItem("subject") != null)
          subject = nodeMapSettings.getNamedItem("subject").getNodeValue();
    }
    
    getLog().debug3("Update factory.ini:" + factoryIni + " Section=" + section);
    
    //
    
    SOSProfileSettings p = new SOSProfileSettings(factoryIni, "emptySection");
    
    
//    SOSProfileSettings p = new SOSProfileSettings(factoryIni, section, getLogger());
    
    Properties job = p.getSection();
    
    
    job.setProperty("log_mail_to", to);
    job.setProperty("log_mail_cc", cc);
    job.setProperty("log_mail_bcc", bcc);
    job.setProperty("log_mail_subject", subject);
    //job.setProperty("body", body);
    
    writeSection(factoryIni, section, job);
    
    
	}

	  private String execute_xml(String xml) throws Exception {
		  if (xml.indexOf("<?xml version")==-1){
			  xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"+xml;
		  }
      String response = spooler.execute_xml(xml);
		  getLog().debug3("...executed");
			getLogger().debug9("Response from Job Scheduler: "+response);
			
			SOSXMLXPath xpath = new SOSXMLXPath(new StringBuffer(response));

		
      String errorMsg = "";
			if (xpath.selectSingleNodeValue("//spooler/answer/ERROR") != null) {
			   String errorCode = xpath.selectSingleNodeValue("//spooler/answer/ERROR/@code");
         String errorText = xpath.selectSingleNodeValue("//spooler/answer/ERROR/@text");
         errorMsg = errorCode + ":"  + errorText;
			}
      
		  if (!errorMsg.equals("")){
			  throw new Exception ("Scheduler answer ERROR:" + errorMsg);
		  }
			return response;
	  }
	  
    private void submitXml(String xml, String what, String name, String modified, String path) throws Exception {
     
      String answer="";
            xml = handleInit(xml, what, name);
        		if (what.equalsIgnoreCase("job.global")) {
    				updateFactoryIni(xml,name);
    			} else if (what.equalsIgnoreCase("documentation")){
    				updateSchedulerFile(xml,name, modified, what);
    			}
        		else {
    					if (!remove) {
    						if (!useLiveFolder){
    							getLog().debug3("execute_xml: " + xml);
    							answer = execute_xml(xml);
    						} else{
    							writeConfigFile(xml, what, name, path, modified);
    						}
    					}
    				}
            handleClose(xml,answer, what, name, path);
    }
    
    private void writeConfigFile(String xml, String what, String name, String path, String modified) throws Exception{
    	try{
    		String fileName="";
    		SOSXMLXPath xpath = new SOSXMLXPath(new StringBuffer(xml));
    		if (what.equalsIgnoreCase("order")){
    			String job_chain = xpath.selectSingleNodeValue("//add_order/@job_chain");
    			fileName=job_chain+",";
    		}
    		fileName+=name+".";
    		String fileIdentifier = what;
    		if(fileIdentifier.equalsIgnoreCase("orderjob")) fileIdentifier="job";
    		fileName+=fileIdentifier + ".xml";
    		File managedDir = new File(liveFolder,path);
    		File managedFile = new File(managedDir,fileName);
    		if (!managedDir.exists()) createManagedDir(managedDir);
    		getLog().debug3("Filename for configuration file: "+fileName);    		
    		writeLiveFile(xpath, what, managedFile);
    		updateManagedIndicator(managedFile, modified);
    	} catch(Exception e){
    		throw new Exception("Error occured updating config file in hot folder: "+e);
    	}
    }
    
    

    private void updateManagedIndicator(File managedFile, String dbModified) throws Exception{
    	File managedIndicator = new File(managedFile.getParentFile(), ".managed");
    	try{
    		getLog().debug5("Updating "+managedIndicator.getAbsolutePath());
    		String md5 = sos.util.SOSCrypt.MD5encrypt(managedFile);
    		String lineWithMD5 = managedFile.getName()+" "+md5+" "+dbModified;
    		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(managedIndicator)));
            String line = null;
            String output="";
            boolean found = false;
  	        while (true) {
  	          line = in.readLine();
  	          if (line == null)
    	          break;
  	          //getLog().debug9("current line: "+line);
  	          if (line.startsWith(managedFile.getName())){
  	        	  found = true;
  	        	  //getLog().debug9(" overwriting with "+lineWithMD5);
  	        	  output += lineWithMD5+"\n";
  	          } else{
  	        	  if (line.trim().length()>0){
  	        		  output += line+"\n";
  	        		//getLog().debug9(" keeping line");
  	        	  }
  	          }
    	    }
  	        if (!found) {
  	        	//getLog().debug9(" adding line "+lineWithMD5);
  	        	output += lineWithMD5;
  	        }
  	        in.close();
  	        //getLog().debug9(" complete content:\n"+output);
  	        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(managedIndicator)));
	        out.write(output);
	        out.close();
    	}catch (Exception e){
    		throw new Exception("Error updating "+managedIndicator.getAbsolutePath()+" for file "+managedFile.getName()+": "+e);
    	}
    }
    
    
    private void createManagedDir(File managedDir) throws Exception{
		if (managedDir.equals(liveFolder)) return;
		try{
			File parent = managedDir.getParentFile();
			if (!parent.exists()) createManagedDir(parent);
			getLog().debug3("creating managed folder "+managedDir.getAbsolutePath());
			if(!managedDir.mkdir()) throw new Exception("mkdir failed");
			File managedIndicator = new File(managedDir, ".managed");
			getLog().debug5("Creating file "+managedIndicator.getAbsolutePath());
			if(!managedIndicator.createNewFile()){
				throw new Exception("Failed to create file "+managedIndicator.getAbsolutePath());
			}
		} catch(Exception e){
			throw new Exception("Error creating managed folder "+managedDir.getAbsolutePath()+": "+e,e);
		}		
	}

	private void writeLiveFile(SOSXMLXPath xpath, String what, File xmlFile) throws Exception{
		try{
			if (!xmlFile.exists()){
				if (!xmlFile.createNewFile()) throw new Exception("Failed to create "+xmlFile.getAbsolutePath());
			}
			FileOutputStream fos = new FileOutputStream(xmlFile);
			DOMResult domResult = new DOMResult();
			StreamResult result = new StreamResult(fos);
			DOMSource source = new DOMSource(xpath.document);
			getLog().debug9("managed2liveTransformer: "+managed2liveTransformer);
			
			managed2liveTransformer.transform(source, result);
			/*getLog().debug9("Nodename: "+result.getNode().getNodeName());
			getLog().debug9("ChildNodes: "+result.getNode().getChildNodes());
			getLog().debug9("ChildNodes length: "+result.getNode().getChildNodes().getLength());
			getLog().debug9("ChildNodes [1]: "+result.getNode().getChildNodes().item(0).getNodeName());
			*/
		} catch(Exception e){
			throw new Exception("Error preparing xml: "+e);
		}		
	}

	private void removeSchedulerFile(String name, String what){
    	try{
    		File schedulerFile = new File(getDirectory(what)+name);
    		getLog().info("removing "+what+" "+schedulerFile.getAbsolutePath());
    		schedulerFile.delete();
    	}catch (Exception e){
    		try{
    			getLog().warn("Error occured removing "+what+" \""+name+"\": "+e);
    		} catch (Exception ex) {}
    	}
    }
    
    private void updateSchedulerFile(String xml, String name, String modified, String what) throws Exception {
    	try{
    		// compare File and XML Date    	
    		Date modDate = SOSDate.getTime(modified);
    		getLog().debug5("modDate: "+modDate);
    		File schedulerFile = new File(getDirectory(what)+name);
    		boolean update = false;
    		if (schedulerFile.exists()){
    			Date fileDate = new Date(schedulerFile.lastModified());
    			getLog().debug5("fileDate: "+fileDate);
    			if (fileDate.before(modDate)){
    				update=true;
    				getLog().debug1(what+" "+schedulerFile.getAbsolutePath()+" is older than "+what+" in database. " +
    						"Updating file.");
    			}else{
    				getLog().debug1(what+" "+schedulerFile.getAbsolutePath()+" is newer than "+what+" in database. " +
					"Doing nothing.");
    			}
    			
    		}
    		else{
    			getLog().debug1(what+" "+schedulerFile.getAbsolutePath()+" doesn't exist. Creating new file.");
    			update=true;    			
    		}
    		// if XML is newer, update file
    		if (update){
    			// read XML
    			/*DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    	        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    	        ByteArrayInputStream bai = new ByteArrayInputStream(xml.getBytes());
    	        InputSource source = new InputSource(bai);
    	        
    	        Document xmlDoc = docBuilder.parse(source);*/
    			ByteArrayInputStream bis = new ByteArrayInputStream(xml.getBytes("UTF-8"));
    	        //SOSXMLXPath xpath = new SOSXMLXPath(new StringBuffer(xml));
    			SOSXMLXPath xpath = new SOSXMLXPath(bis);
    	        if (what.equalsIgnoreCase("documentation")){
    	        	String xmlContent = xpath.selectSingleNodeValue("/documentation");
    	        	getLog().debug9("Content of /documentation:\n"+xmlContent);
    	        	String outputEncoding = parseEncoding(xmlContent);
    	        	if (outputEncoding==null || outputEncoding.length()==0) {
    	        		outputEncoding="UTF-8";
    	        		xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+xmlContent;
    	        	}
    	        	
    	        	/*BufferedWriter wri = new BufferedWriter(
    	        			new FileWriter(schedulerFile,false));
    	        	*/
    	        	/*
    	        	PrintWriter target = new PrintWriter(new FileWriter(schedulerFile,false));
    	        	target.print(xmlContent);
    	        	target.close();
    	        	*/
    	        	
    	        	OutputStream fout = new FileOutputStream(schedulerFile,false);
    	        	//OutputStream bout= new BufferedOutputStream(fout);
    	        	OutputStreamWriter out = new OutputStreamWriter(fout, outputEncoding);
    	        	out.write(xmlContent);
    	        	out.flush();
    	        	out.close();
    	        }
    		}
    	} catch (Exception e){
    		throw new Exception("Error occured updating job documentation file \""+name+"\": "+e);
    	}
		
	}

	private String parseEncoding(String xmlContent) {
		Matcher matcher = xmlEncodingPattern.matcher(xmlContent);
		if (matcher.find()){
			if (matcher.groupCount()>0) return matcher.group(1);			
		}
		return null;
	}

	public SOSLogger getLog(){
		return getLogger();
		/*
        if(startscript) return spooler.log();
        else            return spooler_log;
        */
    }

    public boolean spooler_process() throws Exception  {
        try {
            Order order = spooler_task.order();
            orderParams = order.params();
            String managedObjectID = orderParams.var("managed_object_id");
            getLog().debug3("managed_object_id: " + managedObjectID);
            
            String remove = orderParams.var("remove");
            if (remove!=null && remove.equalsIgnoreCase("yes")){
            	String type = orderParams.var("managed_object_type");
            	String name = orderParams.var("managed_object_name");
            	remove(type, name);
            }else {
            	try{
                	Integer.parseInt(managedObjectID);
                } catch(Exception e){
                	throw new Exception("Missing or illegal value for parameter managed_object_id: \""+managedObjectID+"\"");
                }
            	initialize(managedObjectID);
            }
            return true;
        }catch(Exception e) {
        	  getLog().warn("Error processing order: " + e.getMessage());
            spooler_task.order().setback();
            spooler_task.order().params().set_var("message", e.getMessage());
            spooler_task.end();
            return false;
        } finally {
            if (this.getConnection() !=  null) {
                try { this.getConnection().rollback(); } catch (Exception ex) {} // no error handling
            }
            
        }
    }

    private void testOrder() {
    	startscript=false;
      String name = "Testorder";
      String xml = "<add_order id=\"myOrder\"  job_chain=\"jobchain\"   title=\"Test fuer Managed Starter\"  state=\"100\">       <run_time let_run=\"no\"/></add_order>";
      try  {
          handleOrderInit(name, xml);
      }catch(Exception e){
          e.printStackTrace();
      }
  }
    private void testFactoryIni() throws Exception{
    	startscript=false;
    	SOSLogger log = new SOSStandardLogger(SOSLogger.DEBUG9);
    	//setLogger(log);
      String name = "TestJob_new";
			String xml = "<email_settings to=\"oh@sos-berlin.com\"/>";
      try  {
      	updateFactoryIni(xml,name);
      }catch(Exception e){
          e.printStackTrace();
      }
  }
    
    private void testDocumentation() throws Exception{
    	startscript=false;
      SOSLogger log = new SOSStandardLogger(SOSLogger.DEBUG9);
      SOSConnection conn = SOSConnection.createInstance("j:/e/java/al/sos.scheduler/config/sos_setup_settings.ini", log);
      //setLogger(log);
      try{
      
      conn.connect();
	  String xml = conn.getClob("SELECT \"XML\" FROM SCHEDULER_MANAGED_OBJECTS WHERE \"ID\" = 347");
	  
      try  {
      	updateSchedulerFile(xml, "New_job_documentation.xml", SOSDate.getCurrentTimeAsString(), "documentation");
      		
      }catch(Exception e){
          e.printStackTrace();
      }
      } catch(Exception e){
    	  e.printStackTrace();
      } finally{
    	  if (conn!=null) conn.disconnect();
      }
  }

    public static void main(String args[]) throws Exception {
        JobSchedulerManagedStarter x = new JobSchedulerManagedStarter();
        //x.testDocumentation();
//        x.testOrder();
        x.testFactoryIni();
    }

    
}
