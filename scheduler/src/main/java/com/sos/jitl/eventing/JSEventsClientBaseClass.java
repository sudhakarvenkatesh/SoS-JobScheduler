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
package com.sos.jitl.eventing;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import sos.connection.SOSConnection;
import sos.scheduler.command.SOSSchedulerCommand;
import sos.scheduler.job.JobSchedulerConstants;
import sos.scheduler.job.JobSchedulerJobAdapter;
import sos.spooler.Supervisor_client;

import com.sos.JSHelper.Exceptions.JobSchedulerException;

// Super-Class for JobScheduler Java-API-Jobs
/**
 * \class 		JSEventsClientJSAdapterClass - JobScheduler Adapter for "Submit and Delete Events"
 *
 * \brief AdapterClass of JSEventsClient for the SOSJobScheduler
 *
 * This Class JSEventsClientJSAdapterClass works as an adapter-class between the SOS
 * JobScheduler and the worker-class JSEventsClient.
 *
 * see \see C:\Users\KB\AppData\Local\Temp\scheduler_editor-4778075809216214864.html for more details.
 *
 * \verbatim ;
 * mechanicaly created by C:\ProgramData\sos-berlin.com\jobscheduler\latestscheduler\config\JOETemplates\java\xsl\JSJobDoc2JSAdapterClass.xsl from http://www.sos-berlin.com at 20130109134235
 * \endverbatim
 */
public class JSEventsClientBaseClass extends JobSchedulerJobAdapter {
	private static final String		conNodeNameEVENTS				= "events";
	protected static final String	conNodeNameEVENT				= "event";
	private final String			conClassName					= "JSEventsClientJSAdapterClass";
	@SuppressWarnings({ "hiding" })
	private static Logger			logger							= Logger.getLogger(JSEventsClientBaseClass.class);
	protected final boolean			continue_with_spooler_process	= true;
	protected final boolean			continue_with_task				= true;

	protected JSEventsClientOptions	objO							= null;
	protected JSEventsClient		objR							= null;

	/** table name */
	protected static String			tableEvents						= "SCHEDULER_EVENTS";

	public void setOptions(final JSEventsClientOptions pobjOptions) {
		objO = pobjOptions;
	}

	protected void doProcessing() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::doProcessing";

		Initialize();

		objO.CheckMandatory();
		objR.Execute();
	} // doProcessing

	protected void Initialize() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::doInitialize";

		initializeLog4jAppenderClass();

		objR = new JSEventsClient();
		objO = objR.Options();

		// Check Supervisor if no EventService is defined in the options
		if (objO.scheduler_event_handler_host.isDirty() == false) { // no definition given ...
			if (spooler != null) {
				Supervisor_client supervisor = null;
				try {
					supervisor = spooler.supervisor_client();
					objO.scheduler_event_handler_host.Value(supervisor.hostname());
					objO.scheduler_event_handler_port.value(supervisor.tcp_port());
				}
				catch (Exception e) { // there is no supervisor
					objO.scheduler_event_handler_host.Value(spooler.hostname());
					objO.scheduler_event_handler_port.value(spooler.tcp_port());
				}
			}
			else {
				throw new JobSchedulerException("No Event Service specified. Parameter " + objO.scheduler_event_handler_host.getShortKey());
			}
		}

		objR.setJSJobUtilites(this);
		objR.setJSCommands(this);
		try {
			objO.CurrentNodeName(this.getCurrentNodeName());
			objO.setAllOptions(getSchedulerParameterAsProperties(getJobOrOrderParameters()));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new JobSchedulerException("error", e);
		}
	} // doInitialize

	@Override
	public boolean spooler_init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_init"; //$NON-NLS-1$
		return super.spooler_init();
	}

	@Override
	public void spooler_exit() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_exit";
		super.spooler_exit();
	}

	private class EventNode {
		public Element			event	= null;
		public HashMap<?, ?>	record	= null;

		EventNode(final Element pevent, final HashMap<?, ?> hshmap) {
			event = pevent;
			record = hshmap;
		}

		public void setAttr(final String pstrName) {
			event.setAttribute(pstrName, getVal(pstrName));
		}

		public String getParameters() {
			return record.get("parameters").toString();
		}

		public boolean hasParameters() {
			return getParameters().length() > 0;
		}

		public String getVal(final String pstrName) {
			String strR = record.get(pstrName).toString();
			return strR;
		}
	}

	public Document readEventsFromDB(final SOSConnection conn) throws Exception {
		Document eventsDoc = null;

		try {
			conn.executeUpdate("DELETE FROM " + getEventsTableName()
					+ " WHERE \"EXPIRES\"<=%now AND (\"SPOOLER_ID\" IS NULL OR \"SPOOLER_ID\"='' OR \"SPOOLER_ID\"='" + spooler.id() + "')");
			conn.commit();

			Vector<?> vEvents = conn.getArrayAsVector("SELECT \"SPOOLER_ID\", \"REMOTE_SCHEDULER_HOST\", \"REMOTE_SCHEDULER_PORT\", \"JOB_CHAIN\", \"ORDER_ID\", \"JOB_NAME\", \"EVENT_CLASS\", \"EVENT_ID\", \"EXIT_CODE\", \"CREATED\", \"EXPIRES\", \"PARAMETERS\" FROM "
					+ getEventsTableName()
					+ " WHERE (\"SPOOLER_ID\" IS NULL OR \"SPOOLER_ID\"='' OR \"SPOOLER_ID\"='"
					+ spooler.id()
					+ "') ORDER BY \"ID\" ASC");

			String[] strAttr = new String[] { "remote_scheduler_host", "remote_scheduler_port", "job_chain", "order_id", "job_name", "event_class", "event_id",
					"exit_code", "expires", "created", };
			Iterator<?> vIterator = vEvents.iterator();
			int vCount = 0;
			eventsDoc = createEventsDocument(conNodeNameEVENTS);
			while (vIterator.hasNext()) {
				HashMap<?, ?> record = (HashMap<?, ?>) vIterator.next();
				Element event = eventsDoc.createElement(conNodeNameEVENT);
				EventNode objEvent = new EventNode(event, record);

				event.setAttribute("scheduler_id", objEvent.getVal("spooler_id"));
				for (String strAttributeName : strAttr) {
					objEvent.setAttr(strAttributeName);
				}

				if (objEvent.hasParameters()) {
					Document eventParameters = createEventsDocument(new InputSource(new StringReader(objEvent.getParameters())));
					logger.debug("Importing params node...");
					Node impParameters = eventsDoc.importNode(eventParameters.getDocumentElement(), true);
					logger.debug("appending params child...");
					event.appendChild(impParameters);
				}
				eventsDoc.getLastChild().appendChild(event);
				vCount++;
			}
			logger.info(vCount + " events read from database");
		}
		catch (Exception e) {
			throw new JobSchedulerException("Failed to read events from database: " + e, e);
		}
		return eventsDoc;
	}

	/**
	 * @return the tableEvents
	 */
	public String getEventsTableName() {
		return tableEvents;
	}

	/**
	 *
	* \brief getEventsDocument
	*
	* \details
	* Creates and returns an xml-document for the events
	*
	* \return Document
	*
	 */
	protected Document createDomDocument() {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		Document eventDocument = null;

		try {
			docBuilder = docFactory.newDocumentBuilder();
			eventDocument = docBuilder.newDocument();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return eventDocument;
	}

	/**
	 *
	* \brief getEventsDocument
	*
	* \details
	* Creates and returns an xml-document for the events
	*
	* \return Document
	*
	 */
	protected Document createEventsDocument(final String pstrNodeName) {
		Document eventDocument = createDomDocument();
		eventDocument.appendChild(eventDocument.createElement(conNodeNameEVENTS));
		return eventDocument;
	}

	protected Document createEventsDocument(final InputSource pobjInputSource) {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		Document eventDocument = null;

		try {
			docBuilder = docFactory.newDocumentBuilder();
			eventDocument = docBuilder.parse(pobjInputSource);
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return eventDocument;
	}

	private String getText(final Node n) {
		if (n != null) {
			return n.getNodeValue();
		}
		else {
			return "";
		}
	}

	/**
	 *
	*
	* \brief getEventsFromSchedulerVar
	*
	* \details
	*
	* \return Document
	*
	 */
	public Document getEventsFromSchedulerVar() throws Exception {
		Document eventDocument = null;
		SOSConnection objConn = null;
		try {
			String eventSet = "";
			if (spooler == null) {
				eventSet = sendCommand("<param.get name=\"" + JobSchedulerConstants.eventVariableName + "\"/>");
				if (eventSet.equals("")) {
					String strM = String.format("No Answer from Scheduler %1$s:%2$s", objO.scheduler_event_handler_host.Value(),
							objO.scheduler_event_handler_port.Value());
					logger.error(strM);
				}
				Document doc = createEventsDocument(new InputSource(new StringReader(eventSet)));
				NodeList params = doc.getElementsByTagName("param");
				if (params.item(0) == null) {
					logger.error("No events param found in JobScheduler answer");
				}
				else {
					NamedNodeMap attr = params.item(0).getAttributes();
					eventSet = getText(attr.getNamedItem("value"));
					eventSet = modifyXMLTags(eventSet);
				}
			}
			else {
				eventSet = spooler.var(JobSchedulerConstants.eventVariableName);
				objConn = getConnection();
			}

			if (objConn != null && (eventSet == null || eventSet.length() == 0)) {
				eventDocument = readEventsFromDB(objConn);
			}
			else {
				if (eventSet.length() <= 0) {
					return eventDocument;
				}
				logger.debug("current event set: " + eventSet);
				eventDocument = createEventsDocument(new InputSource(new StringReader(eventSet)));
			}
		}
		catch (Exception e) {
			throw new JobSchedulerException(e);
		}
		return eventDocument;
	}

	private String modifyXMLTags(final String pstrEventString) {
		// TODO the JS should take care about <> in variable values and replace it to &lt; and &gt; in xml-response
		return pstrEventString.replaceAll(String.valueOf((char) 254), "<").replaceAll(String.valueOf((char) 255), ">");
	}

	private String sendCommand(final String command) {
		String s = "";
		SOSSchedulerCommand socket = null;
		logger.debug("...sendCommand: " + command);
		try {

			// System.out.println(command);
			if (socket == null) {
				socket = new SOSSchedulerCommand();
				socket.connect(objO.scheduler_event_handler_host.Value(), objO.scheduler_event_handler_port.value());
			}
			socket.sendRequest(command);
			s = socket.getResponse();
			logger.debug("Response = " + modifyXMLTags(s));
		}
		catch (Exception ee) {
			throw new JobSchedulerException(String.format("Error sending command to Job Scheduler: '%1$s' \n %2$s", command, ee.getMessage()), ee);

		}
		finally {

		}
		return s;
	}

}
