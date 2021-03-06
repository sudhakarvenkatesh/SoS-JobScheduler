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
package com.sos.jitl.jasperreports;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import sos.connection.SOSConnection;
import sos.connection.SOSConnectionFileProcessor;
import sos.net.SOSMailOrder;
import sos.scheduler.managed.JobSchedulerManagedJob;
import sos.settings.SOSConnectionSettings;
import sos.spooler.Order;
import sos.spooler.Variable_set;
import sos.spooler.Web_service_request;
import sos.spooler.Web_service_response;
import sos.spooler.Xslt_stylesheet;
import sos.textprocessor.SOSPlainTextProcessor;
import sos.util.SOSClassUtil;
import sos.util.SOSDate;
import sos.util.SOSSchedulerLogger;
import sos.util.SOSString;
import sos.xml.SOSXMLXPath;


/**
 * Klasse JobSchedulerJasperReportJob
 * 
 *  <br>Der Job erzeugt aus einer Report-Konfiguration mit JasperReports einen Bericht, 
 *      (siehe http://jasperreports.sourceforge.net)
 *  <br>
 *  <br>
 *  <br>Eine Report-Konfiguration legen Sie i.d.R. mit einem visuellen Report-Editor an, 
 *      z.B. mit iReport (siehe http://ireport.sourceforge.net).
 *  <br>
 *  <br> 
 *  <br>Es kann wahlweise die Datenbankverbindung des Job Schedulers oder eine separat angebbare 
 *  <br>Datenbankverbindung f�r die Abfrage der Berichtsdaten verwendet werden.
 *  <br>
 *  <br>
 *  <br>Die Berichtsdaten k�nnen auch mittels Parameter generiert werden. Alle Jobparameter werden den JasperReport als 
 *      Parameter zum Weiterverarbeiten weitergegeben.
 *  <br>
 *  <br>
 *  <br>Der Job unterst�tzt die Ausgabe in die Berichtsformate PDF, HTML, RTF, XML und XLS.
 *  <br>
 *  <br>Alle Generierten Reports k�nnen als emails versendet werden.
 *  <br>
 *  <br>Generieren von Reports mit JasperReport:
 *  <br>
 *  <br>�ber die Jobparameter bzw. Orderparameter k�nnen folgende Parametern �bergeben werden:
 *  <br> - Name der Jasper Vorlage. Die jasper-Vorlage mit Pfadangabe angeben.
 *  <br> <b>parameter:</b> name="<i>report_filename</i>" value="<i>jasperreports/config/dod/dod.jasper</i>"
 *  <br>              
 *  <br> - Ausgabeverzeichnis 
 *  <br> <b>parameter:</b> name="<i>output_filename</i>" value="<i>jasperreports/reports/dod_intervall.pdf"                                               
 *  <br>
 *  <br> - Das Report wird hier zweimal erzeugt. Einmal in html und einmal in pdf
 *  <br> <b>parameter:</b> name="<i>output_type</i>" value="<i>html;pdf</i>"
 *  <br>
 *  <br> - Titel des Reports (gilt nur f�r dod-reports)
 *  <br> <b>parameter:</b> name="<i>title</i>" value="<i>DOD Yearly Order Report: ETH Zuerich</i>"   
 *  <br>
 *  <br> - Verzeichnis der Sub Reports, falls Unterformulare vorhanden ist. Bitte schlie�ende Slash nicht vergessen  
 *  <br> <b>parameter:</b> name="<i>SUBREPORT_DIR</i>" value="<i>/home/test/scheduler.jasper/jasperreports/config/dod/dod_sub_report/</i>"        
 *  <br>
 *  <br> - Konfigurationsdatei der Datenbankverbindung. Ist kein "settings_filename" angegeben, dann wird der Connection des schedulers �bergeben  
 *  <br> <b>parameter:</b> name="<i>settings_filename</i>" value="<i>/home/test/scheduler.jasper/config/sos_settings_dod.ini</i>" 
 *  <br>
 *  <br> - Der Parameter benennt den Pfad und Namen einer Eingabedatei mit SQL-Statements. 
 *  <br> Wird dem Parameter ein Dateiname zugewiesen, dann wird die in dieser Datei enthaltene Abfrage f�r den Report verwendet 
 *  <br> anstelle der in der Berichtskonfiguration enthaltenen Abfrage, siehe Parameterreport_filename.
 *  <br> <b>parameter:</b> name="<i>query_filename</i>" value="<i>dod.sql</i>"
 *  <br>
 *  <br> - Der Parameter gibt eine SQL-Dateiname an. Ist dem Parameter eine Dateiname zugewiesen, 
 *  <br> dann wird die Abfrage ausgef�hrt und das Resultat der Abfrage mit den Datenbankverbindungen 
 *  <br> den JasperReports als Parameter �bergeben. 
 *  <br> Die Resultat der Abfrage sind freier Parametern, der zur weitergabe an den JasperReports 
 *  <br> Parameter verwendet wird. In DOD-Report werden z.B. dated_from und dated_to als 
 *  <br> Parameter gebraucht, die aus der SQL-Statements selektiert werden 
 *  <br> <b>parameter:</b> name="<i>parameter_query_filename</i>" value="<i>dod_report_year.sql</i>" 
 *  <br> 
 *  <br>
 *  <br> -die Parameter dated_from und dated_to direkt �bergeben z.B. 
 *  <br> Die Parameter ist ein freier Parameter, der zur weitergabe an den JasperReports Parameter 
 *  <br> verwendet wird. Es handelt sich um die Titel des Reports und wird in DOD-Reports verwendet.
 *  <br> <b>parameter:</b> name="<i>dated_from</i>" value="<i>2000-01-01</i>" 
 *  <br> <b>parameter:</b> name="<i>dated_to</i>" value="<i>2007-01-01</i>" 
 *  <br>
 *  <br> - Ist ein Druckername angegeben, dann wird das Dokument gedruckt.  
 *  <br> <b>parameter:</b> name="<i>printer_name</i>" value=""
 *  <br> 
 *  <br> - Anzahl zu druckenden Exemplarer 
 *  <br> <b>parameter:</b> name="<i>printer_copies" value =""
 *  <br>
 *  <br> - Sprache der Reports festlegen 
 *  <br> <b>parameter:</b> name="<i>report_locale" value ="de oder en"
 *  <br>
 *  <br>
 *  <br>- Parameter zur Mailversenden    
 *  <br>mail_it funktion. Ist eine mail_it Parameter angegeben bzw auf true gesetzt, dann wird mit
 *  <br>der Scheduler Mail Funktion emails versendet. Es wird �berpr�ft, ob zus�tzlich
 *  <br>die Parameter "mail_to", "mail_cc", "mail_bcc", "mail_subject" und "mail_body" angegeben 
 *  <br>sind. Wenn ja, dann werden zu diesen Adressanten mit den Betreff und body emails versendet. 
 *  <br>Wenn diese Parametern nicht existieren, dann werden die Empf�nger aus den Einstellungen 
 *  <br>des Schedulers (factory.ini) genommen. 
 *  <br>Ist der Jobparameter mail_it nicht vorhanden bzw. als false gesetzt, dann wird der email mit 
 *  <br>Hilfe der Klasse SOSMailOrder versendet. 
 *  <br>Die Voraussetzung zum email versenden mit der Klasse SOSMailOrder ist 
 *  <br>  1. Datenbankverbindung ( hier ist die Datenbankverbindung des Schedulers oder die Datengemeint)
 *  <br>  2. das Existieren der Tabelle "SETTINGS"
 *  <br>  3. Eintr�ge zum email versenden in der Tabelle "SETTINGS"   
 *  <br> Die Empf�nger und der Subject und Body werden aus der Jobparameter ausgelesen. 
 *  <br>
 *  <br><b>parameter:</b> name="<i>mail_it</i>" value="true/false"
 *  
 *  <br>-Geben Sie hier eine oder mehrere eMail Adressen an, die den Report erhalten. Mehrere eMail Adressen werden durch Komma getrennt.
 *  <br>Ist diese Einstellung leer, dann werden Reports nicht per eMail versendet.
 *  <br><b>parameter:</b> name="<i>mail_to</i>" value="" 
 *  
 *  <br>-Geben Sie hier eine oder mehrere eMail Adressen an, die den Report als carbon copy erhalten. 
 *  Mehrere eMail Adressen werden durch Komma getrennt.
 *  <br><b>parameter:</b> name="<i>mail_cc</i>" value=""
 *  
 *  <br>-Geben Sie hier eine oder mehrere eMail Adressen an, die den Report als blind carbon copy erhalten. Mehrere eMail Adressen werden durch Komma getrennt.
 *  <br><b>parameter:</b> name="<i>mail_bcc</i>" value=""
 *  
 *  <br>-Der Inhalt des Parameters wird als Betreff der eMail des Reports verwendet und muss ausgef�llt sein, wenn Reports per eMail versendet werden.
 *  <br>Mit diesem Parameter kann das Layout des Reports im HTML-Format oder als plain text angegeben werden. Zur Bestimmen des Layout Typs wird dem Parameter folgender Prefix zugeordnet:
 *  <br>  - factory: 
 *  <br>  - factory_file: 
 *  <br>  - plain: 
 *  <br>  - plain_file: 
 *  <br>Es k�nnen folgende Platzhalter verwendet werden, die durch [ und] geklammert werden, z.B. in der Form [date]:
 *  <br>  - [date] - aktuelles Datum 
 *  <br>  - [datetime] - aktuelles Datum und Uhrzeit 
 *  <br>  - [orderid] - ID des Auftrags 
 *  <br>  - [jobname] - Name des Jobs 
 *  <br>  - [taskid] - ID der Task 
 *  <br><b>parameter:</b> name="<i>mail_subject</i>" value="<i>JasperReports: Report Delivery</i>"
 *  <br>
 *  <br>-Mit diesem Parameter kann das Layout des Reports im HTML-Format oder als plain text angegeben werden. Zur Bestimmen des Layout Typs wird dem Parameter folgender Prefix zugeordnet:
 *  <br>  - factory: 
 *  <br>  - factory_file: 
 *  <br>  - plain: 
 *  <br>  - plain_file: 
 *  <br>
 *  <br>Innerhalb des Layouts k�nnen folgende Platzhalter verwendet werden, die jeweils mit [ und ] geklammert werden, z.B. in der Form [date]:
 *  <br>  - [date] - aktuelles Datum 
 *  <br>  - [datetime] - aktuelles Datum und Uhrzeit 
 *  <br>  - [orderid] - ID des Auftrags 
 *  <br>  - [jobname] - Name des Jobs 
 *  <br>  - [taskid] - ID der Task 
 *  <br>  - [sql] - SQL-Query, aus der der Report erstellt wurde 
 *  <br>  - [xml] - aus dem Ergebnis der Abfrage generiertes xml vor der Transformation 
 *  <br><b>parameter:</b> name="<i>mail_body</i>" value=""	 	
 *  <br>
 *  <br>
 *  <br>@author andreas.pueschel@sos-berlin.com; mueruevet.oeksuez@sos-berlin.com
 *  <br>@version 1.0
 *  <br>@resource sos.scheduler.jar, sos.spooler.jar, sos.stacks.jar, sos.util.jar,
 *      sos.xml.jar, sos.connection.jar, jasperreports-1.2.3.jar, poi-2.0-final-20040126.jar, 
 *      itext-1.3.1.jar, commons-digester-1.7.jar, commons-javaflow-20060411.jar, commons-collections-2.1.jar, 
 *      org.apache.commons.jar, sos.documentfactory.jar, sos.mail.jar, sos.settings.jar, sos.net.jar,
 *      activation, sos.hostware.jar,   
 *  <br>
 *  <br>
 *  <br>@see job documentation in the package jobdoc for details
 */
public class JobSchedulerJasperReportJob extends JobSchedulerManagedJob {	
	 
	/** Konfigurationsdatei der Datenbankverbindungen	 */
	private String settingsFilename                     = "";
	
	/** Vorlagenname: Extention ist jasper*/
	private String reportFilename                       = "";
	
	/** SQL-Dateiname, wird dieser Parameter gesetzt dann wird den JasperReport
	 * die ResultSet ohne Datenbankverbindung �bergeben.*/
	private String queryFilename                        = "";
	
	/** Ausgabetyp der Reportdatei*/
	private String outputType                           = "pdf";
	
	/** Name der Ausgabedatei oder eine Verzeichnisname */
	private String outputFilename                       = "";
		
	/** Wenn der Druckername eine prefix = "factory:" enth�lt, dann wird dieser 
	 * �ber die Documentfactory ermittelt. Sonst wird �ber diesen Druckername gedruckt. */
	private String printerName      					= "";
	
	/** Einstellungsdatei f�r Datenbankverbindung, wenn Documentfactory printerName / mail von einer 
	 * anderen Datenbank ermittelt werden soll */
	private String factorySettingsFile                  = "";
	
	/** sos.util.SOSString Objekt*/
	private SOSString sosString 						= new SOSString();
	
	/** tempor�re Ausgabe hilfsdateiname */
	private File filledReportFile 						= null;
	
	/** Liste aller generierten Report Dokumente*/
	private ArrayList listOfOutputFilename              = null;
	
	/** Ist ein parameter_query_filename angegeben, so wird dieser hier in java ausgef�hrt und das 
	 * Ergebis als Parameter den jasperreport �bergebenden */
	private String parameterQueryFilename				= "";
	
	/** sos.spooler.Order Objekt */
	protected Order order 								= null;
	
	/** sos.spooler.Variable_set Objekt */
	protected Variable_set orderData 						= null;
	
	/** Anzahl zu druckenden Exemplarer*/
	private int printerCopies							= 1;
	
	/** Ist eine mail_it Parameter angegeben bzw auf true gesetzt, dann wird mit
	 * der Scheduler Mail Funktion emails versendet. Es wird �berpr�ft, ob zus�tzlich
	 * die Parameter "mail_to", "mail_cc", "mail_bcc", "mail_subject" und "mail_body" angegeben
	 * sind. Wenn ja, dann werden zu diesen Adressanten mit den Betreff und body emails versendet.
	 * Wenn diese Parametern nicht existieren, dann werden die Empf�nger aus den Einstellungen
	 * des Schedulers (factory.ini) genommen.*/
	private boolean mailIt								= false;
	
	/** Geben Sie hier eine oder mehrere eMail Adressen an, die den Report erhalten.
	 * Mehrere eMail Adressen werden durch Komma getrennt.<br/>
	 * Ist diese Einstellung leer, dann werden Reports nicht per eMail versendet.*/
	private String mailTo								= "";
	
	/** Geben Sie hier eine oder mehrere eMail Adressen an, die den Report als carbon copy erhalten.
	 * Mehrere eMail Adressen werden durch Komma getrennt. 	 */
	private String mailCc								= "";
	
	/**
	 * Geben Sie hier eine oder mehrere eMail Adressen an, die den Report als blind carbon copy erhalten.
	 * Mehrere eMail Adressen werden durch Komma getrennt.          
	 */
	private String mailBcc								= "";
	
	/** Der Inhalt des Parameters wird als Betreff der eMail des
	 * Reports verwendet und muss ausgef�llt sein, wenn Reports per
	 * eMail versendet werden.
	 * <p>
	 *  Es k�nnen folgende Platzhalter verwendet werden, die durch [
	 *  und] geklammert werden, z.B. in der Form [date]:
	 * </p>
	 * <ul>
	 *  <li>[date] - aktuelles Datum</li>
	 *  <li>[datetime] - aktuelles Datum und Uhrzeit</li>
	 *  <li>[orderid] - ID des Auftrags</li>
	 *  <li>[jobname] - Name des Jobs</li>
	 *  <li>[taskid] - ID der Task</li>
	 *</ul>
	 */
	private String mailSubject							= "";
	
	/**
	 * L�scht den alten Bericht bevor neue generiert wird.
	 * Das verhindert, falls keine neue Bericht erstellt wird, z.B. wegen DB oder SQL Fehler,
	 * dann soll auch keine alten Bericht abgeholt werden
	 */
	private boolean deleteOldFilename                = false;
	
	
	/**
	 * <p>
	 * Mit diesem Parameter kann das Layout
	 * des Reports im HTML-Format oder als plain text angegeben
	 * werden. Innerhalb des Layouts k�nnen folgende Platzhalter verwendet werden, die jeweils mit [
	 * und ] geklammert werden, z.B. in der Form [date]:
	 * </p>
	 * <ul>
	 *  <li>[date] - aktuelles Datum</li>
	 *  <li>[datetime] - aktuelles Datum und Uhrzeit</li>
	 *  <li>[orderid] - ID des Auftrags</li>
	 *  <li>[jobname] - Name des Jobs</li>
	 *  <li>[taskid] - ID der Task</li>
	 *  <li>[sql] - SQL-Query, aus der der Report erstellt wurde</li>
	 *  <li>
	 *  [xml] - aus dem Ergebnis der Abfrage generiertes xml vor der
	 *  Transformation
	 *  </li>
	 * </ul>
	 *
	 */
	private String mailBody								= "";
	
	/** SQL-Statement in base64 encodiert. Diese Variable wird zuerst decodiert und anschlie�end wird der
	 * Inhalt ausgef�hrt und der ResultSet den JasperReport ohne Datenbankverbindung �bergeben.*/
	private String queryStatement                        = "";
	
	/** Schalter f�r das versenden bzw. nicht versenden von Reports als Attachment; 
	 * Fall: suspend_attachment=true  -> dann werden keine Reports versender
	 * Fall: suspend_attachment=false -> dann werden Reports mit versendet
	 * default: suspend_attachment=false */
	private boolean suspendAttachment                    = false;
	
	/**
	 * initialization
	 */	
	public boolean spooler_init() {		
		
		try { // to fetch default job parameters			
			if (!super.spooler_init()) 
				return false;
			//init();
			//this.getJobParameters();
			return true;
		} catch (Exception e) {
			spooler_log.error("error occurred processing spooler_init(): " + e.getMessage());
			return false;
		}
	}
	
	
	/**
	 * process report
	 */	
	public boolean spooler_process() {
		
		//Order order = null;
		//Variable_set orderData = null;
		order = null;
		orderData = null;
		
		File settingsFile = null;
		File reportFile = null;
		File queryFile = null;	
		File currQueryFile = null;
		File queryStatementFile = null;
		File outputFile = null;
		File parameterQueryFile = null;
		String stateText = "";
		String tmpOutputFileWithoutExtension = ""; //hilfsvariable
		SOSConnectionFileProcessor queryProcessor = null;
		
		try {
			spooler_log.debug3("******************spooler_process*****************************");
			this.prepareParams();
			
			listOfOutputFilename = new ArrayList();			
			this.setSettingsFilename("");
			this.setReportFilename("");
			this.setQueryFilename("");
			this.setOutputType("pdf");
			this.setOutputFilename("");
			this.setQueryStatement("");
			
//			get job parameters from job configuration (scheduler.xml)
			this.getJobParameters();  
//			to fetch parameters from orders that have precedence to job parameters
			this.getOrderParameters();						
//			to check report parameters
			checkParams();			
			
			try { // to process report
										
				
				reportFile = new File(this.getReportFilename());
				if (!reportFile.exists()) 
					throw new Exception("report file does not exist: " + reportFile.getCanonicalPath());
				
				filledReportFile = File.createTempFile("sos", ".tmp");
				filledReportFile.deleteOnExit();
				
				queryFile = new File(this.getQueryFilename());
				
				outputFile = null;
				if (this.getOutputFilename() != null && this.getOutputFilename().length() > 0) {
					String outputFile_ = maskFilename(this.getOutputFilename());
					outputFile  = new File(outputFile_);					
				} else {
					outputFile = File.createTempFile("sos", ".tmp");
					outputFile.deleteOnExit();
				}
				
				//eventuell vorhandene alte Berichte l�schen
				if(this.isDeleteOldFilename()) {
					if(outputFile.exists()) {
						spooler_log.debug3("..deleting old File " + outputFile.getCanonicalPath());
						if(!outputFile.delete()) {
							spooler_log.warn("..could not delete old File " + outputFile.getCanonicalPath());
						} else {
							spooler_log.debug3("..successfully delete old File " + outputFile.getCanonicalPath());
						}
					}
				}
				
				if (this.getSettingsFilename() != null && this.getSettingsFilename().length() > 0) {
					settingsFile = new File(this.getSettingsFilename());
					if (!settingsFile.exists()) throw new Exception("settings file does not exist: " + settingsFile.getCanonicalPath());
					queryProcessor = new SOSConnectionFileProcessor(settingsFile.getCanonicalPath(), new sos.util.SOSSchedulerLogger(spooler_log)); 
				} else {
					if (this.getConnection() == null) throw new Exception("job scheduler runs without database");
					queryProcessor = new SOSConnectionFileProcessor(this.getConnection(),  new sos.util.SOSSchedulerLogger(spooler_log));
				}
				
				//alle Jobparametern werden den jasperreport �bergeben
				Map parameters = new HashMap();
				Variable_set params = spooler_task.params();
				
				if (orderData != null) {
					spooler_log.debug6(".......orderDatanames: " + orderData.names());
					java.util.StringTokenizer tokenizero = new java.util.StringTokenizer(orderData.names(), ";"); 
					while( tokenizero.hasMoreTokens() ) {
						String name = tokenizero.nextToken();
						parameters.put(name, orderData.var(name));
						spooler_log.debug6(".......orderData: " + name + "=" + orderData.var(name));
					} 
				}
				
				
				spooler_log.debug6(".......paramsnames: " + params.names());
				java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(params.names(), ";"); 
				while( tokenizer.hasMoreTokens() ) {
					String name = tokenizer.nextToken();					
					parameters.put(name, params.var(name));		
					spooler_log.debug6(".......jobparameter: " + name + "=" + params.var(name));
				}   
				
				//Ausf�hren der sql-script und das Ergebnis den Jasperreport als Parameter �bergeben
				if (sosString.parseToString(parameterQueryFilename).length() > 0) {
					parameterQueryFile = new File (this.parameterQueryFilename);
					if (parameterQueryFile.exists()) {
						queryProcessor.process(parameterQueryFile);		
						try {
							parameters.putAll(queryProcessor.getConnection().get());
						} catch (Exception e) {
							spooler_log.warn("..error while get Resultset from query Processor Connection: " + e.toString());
						}
					} else {
						spooler_log.warn(".." +  parameterQueryFilename + " not exists");
					}
				}
				
				
				if (sosString.parseToString(parameters.get("report_locale")).length() > 0) {
					parameters.put(JRParameter.REPORT_LOCALE, new Locale(sosString.parseToString(parameters.get("report_locale"))));					
				} else {
					parameters.put(JRParameter.REPORT_LOCALE, Locale.GERMAN);
				}
				
				//Ausgaben aller Parametern, die den JasperReport �bergeben werden
				Object[] param = parameters.entrySet().toArray();
				for (int i = 0; i < param.length; i++) {
					spooler_log.debug3( "..report parameter " + param[i].toString());
				}
				
				//java.util.ResourceBundle.getBundle("com.sos.jitl.jasperreports.dod").toString();	
				//parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, java.util.ResourceBundle.getBundle("com.sos.jitl.jasperreports.dod").toString());
								
				if (sosString.parseToString(queryStatement).length() > 0) {
					queryStatementFile = this.decodeBase64(this.queryStatement);
					this.spooler_log.debug3("queryStatementFile: " + queryStatementFile);
					if (queryStatementFile.exists()) {
						queryProcessor.process(queryStatementFile);
					}
				}
				 
				
				
				 				
				//existiert ein queryfile, dann wird das Ergebnis der Queryfile ohne Connection den jasperreport �bergeben
				//Vorher werden alle Platzhalter in der query_filname ersetzt mit Parameter
				if (queryFile.exists()) { 
					//queryProcessor.process(queryFile);
//					Paltzhalter in der query_filename parsieren
					SOSPlainTextProcessor processor_ = new SOSPlainTextProcessor();
					//File currQueryFile = File.createTempFile("temp", ".sql", queryFile.getParentFile());
					currQueryFile = processor_.process(queryFile, (HashMap)parameters);
					currQueryFile.deleteOnExit();
		        	queryProcessor.process(currQueryFile);
		        	
		        	
		        	
		        	this.spooler_log.debug5("query " + processor_.getDocumentContent());
				}
				
				if (queryFile.exists() || (queryStatementFile != null  && queryStatementFile.exists())) {
					JasperFillManager.fillReportToFile(reportFile.getCanonicalPath(), filledReportFile.getCanonicalPath(), 
							parameters,
							new JRResultSetDataSource(queryProcessor.getConnection().getResultSet()));
				} else {
					JasperFillManager.fillReportToFile(reportFile.getCanonicalPath(), 
							filledReportFile.getCanonicalPath(), 
							parameters,
							queryProcessor.getConnection().getConnection()); 	
				}										
				
				tmpOutputFileWithoutExtension = outputFile.getCanonicalPath().substring(0, outputFile.getCanonicalPath().lastIndexOf(".") ) + ".";
				
				if (getOutputType().indexOf("pdf") > -1) {
					outputFile = new File(tmpOutputFileWithoutExtension + "pdf");					
					JasperExportManager.exportReportToPdfFile(filledReportFile.getCanonicalPath(), outputFile.getCanonicalPath());
					listOfOutputFilename.add(outputFile);
				}
				
				if (getOutputType().indexOf("htm") > -1 || getOutputType().indexOf("html") > -1) {
					if (getOutputType().indexOf("html") > -1)
						outputFile = new File(tmpOutputFileWithoutExtension + "html");
					else  
						outputFile = new File(tmpOutputFileWithoutExtension + "htm");
					// JasperExportManager.exportReportToHtmlFile(filledReportFile.getCanonicalPath(), outputFile.getCanonicalPath());
					JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(filledReportFile);
					JRHtmlExporter exporter = new JRHtmlExporter();					
					exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jasperPrint);
					exporter.setParameter(JRHtmlExporterParameter.OUTPUT_FILE_NAME, outputFile.getCanonicalPath());
					exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN , Boolean.FALSE); 					
					exporter.exportReport();
					listOfOutputFilename.add(outputFile);
				}
				
				if (this.getOutputType().indexOf("xml") > -1) {
					outputFile = new File(tmpOutputFileWithoutExtension + "xml");
					JasperExportManager.exportReportToXmlFile(filledReportFile.getCanonicalPath(), outputFile.getCanonicalPath(), true);
					listOfOutputFilename.add(outputFile);
				}
				
				if (this.getOutputType().indexOf("xls") > -1) {
					outputFile = new File(tmpOutputFileWithoutExtension + "xls");
					JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(filledReportFile);
					JRXlsExporter exporter = new JRXlsExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFile.getCanonicalPath());
					exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
					exporter.exportReport();
					listOfOutputFilename.add(outputFile);
				} 
				
				if (this.getOutputType().indexOf("rtf") > -1) {
					outputFile = new File(tmpOutputFileWithoutExtension + "rtf");
					JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(filledReportFile);
					JRRtfExporter exporter = new JRRtfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFile.getCanonicalPath());
					exporter.exportReport();             
					listOfOutputFilename.add(outputFile);
				} 
				
				stateText = printDocument();								
				
				stateText = stateText + "..report generated in output file: " + tmpOutputFileWithoutExtension + "[" + getOutputType() + "] " + stateText; 
				
				stateText = sendEmail(stateText); 
				
				spooler_log.info(stateText);
				
				spooler_job.set_state_text(stateText);
				
				
				
			} catch (Exception e) {
				throw new Exception("error occurred processing report: " + e);                
			}
			
			
			try { // to fetch parameters from orders that have precedence to job parameters
				if (spooler_task.job().order_queue() != null) {
					order = spooler_task.order();
					
					// create response for web service request
					if (order.web_service_operation_or_null() != null) {
						Web_service_response response = order.web_service_operation().response();
						
						if (this.getOutputFilename() != null && this.getOutputFilename().length() > 0) {
							// return SOAP response
							// should the response be previously transformed ...
							if (order.web_service().params().var("response_stylesheet") != null && order.web_service().params().var("response_stylesheet").length() > 0) {
								Xslt_stylesheet stylesheet = spooler.create_xslt_stylesheet();
								stylesheet.load_file(order.web_service().params().var("response_stylesheet"));
								String xml_document = stylesheet.apply_xml(order.xml());
								spooler_log.debug3("content of response transformation:/n" + xml_document);
								response.set_string_content(xml_document);
							} else {
								response.set_string_content(order.xml());
							}
						} else {
							// return binary content
							BufferedInputStream in = new BufferedInputStream( new FileInputStream(outputFile));
							ByteArrayOutputStream out = new ByteArrayOutputStream();
							byte buffer[] = new byte[1024];
							int bytesRead;
							while ( (bytesRead = in.read(buffer)) != -1) {
								out.write(buffer, 0, bytesRead);
							}
							order.web_service_operation().response().set_binary_content(out.toByteArray());
						}
						
						response.send();
						spooler_log.debug3("web service response successfully processed for order \"" + order.id() + "\"");
					}
				}
			} catch (Exception e) {
				throw new Exception("error occurred processing web service response: " + e.getMessage());
			}
			
			
			if(filledReportFile != null && filledReportFile.exists())
				 spooler_log.debug5( "..delete " + filledReportFile.getCanonicalPath() + ": " + filledReportFile.delete());
			
			
			if(currQueryFile != null && currQueryFile.exists())
				 spooler_log.debug5( "delete " + currQueryFile.getCanonicalPath() + ": " + currQueryFile.delete());
			
			// return value for classic and order driven processing
			return (spooler_task.job().order_queue() != null);
			
		} catch (Exception e) {
			spooler_job.set_state_text(e.getMessage());
			try {
				if(filledReportFile != null && filledReportFile.exists())
					spooler_log.debug( "could delete " + filledReportFile.getCanonicalPath() + ": " + filledReportFile.delete());
			} catch (Exception se) {}
			spooler_log.warn(e.getMessage());			
			return false;
		} finally {			
			
		}
	}
	
	
	/**
	 * Liefert true, wenn in output_type g�ltige Typen definiert ist:
	 * G�ltige output_type sind: [pdf, htm, xml, xls, rtfl]
	 * @return
	 * @throws Exception
	 */
	private boolean isValidOutputType() throws Exception{
		try {
			if ((this.getOutputType().indexOf("pdf") > -1 ) ||
					(this.getOutputType().indexOf("htm") > -1 ) ||
					(this.getOutputType().indexOf("html") > -1 ) ||
					(this.getOutputType().indexOf("xml") > -1 ) ||
					(this.getOutputType().indexOf("xls") > -1 ) ||
					(this.getOutputType().indexOf("rtf") > -1 ) 
			) {
				return true;
			} else {
				return false;				
			}						
		} catch (Exception e) {
			throw  new Exception("..error in " + SOSClassUtil.getClassName() + ": " + e);
		}
		
	}
	

		public File decodeBase64(String sencode) throws Exception {
		try {
			/*String hallo = "Hallo";			
			byte[] b = hallo.getBytes();
			String sencode = new sun.misc.BASE64Encoder().encode(b);
			byte[] buf = new sun.misc.BASE64Decoder().decodeBuffer(sencode);
			
			// writes to the file            
            FileOutputStream fos = new FileOutputStream( "C:/temp/a.sql" );
            fos.write( buf );
            fos.close();
            */									
			//sencode = sencode.replaceAll("\r","");
			//sencode = sencode.replaceAll("\n","");
			//this.getLogger().debug("l�sch mich: " + sencode);
			
            byte[] buf = new sun.misc.BASE64Decoder().decodeBuffer(sencode.trim());            
			
            File f = File.createTempFile("query_statement", ".sql");
            //File f = new File("C:/temp/a.sql");
            f.deleteOnExit();
            //this.getLogger().info("test l�sch mich "+ f.getCanonicalPath());
			// writes to the file            
            FileOutputStream fos = new FileOutputStream( f );
            fos.write( buf );
            fos.close();
            
            return f;
		} catch (Exception e) {
			throw new Exception ("..error in " +  SOSClassUtil.getClassName() + ": " + e);
		}
	}
	
	/**
	 * Dokument drucken.
	 * 
	 * @throws Exception
	 */
	private String printDocument() throws Exception {
		try {
//			druckername bestimmen
			String prName = getPrinter(); 
			if (sosString.parseToString(prName).length() > 0 ) {
				JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(filledReportFile);
				net.sf.jasperreports.engine.export.JRPrintServiceExporter exporter = new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
//				set the report to print
				exporter.setParameter( net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.JASPER_PRINT, jasperPrint);
//				count of report to print				
				javax.print.attribute.PrintRequestAttributeSet aset = new javax.print.attribute.HashPrintRequestAttributeSet();				
				aset.add(new Copies(getPrinterCopies()));				
				aset.add(MediaSizeName.ISO_A4);
				exporter.setParameter( net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, aset);
//				let the exporter know which printer we want to print on
				javax.print.attribute.PrintServiceAttributeSet serviceAttributeSet = new javax.print.attribute.HashPrintServiceAttributeSet();
				serviceAttributeSet.add(new javax.print.attribute.standard.PrinterName(prName, null)); 
				exporter.setParameter( net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, serviceAttributeSet);
//				print it
				exporter.exportReport();
				 spooler_log.info("..report successfully printed " + getPrinterCopies() + "x.");
				return "..report successfully printed " + getPrinterCopies() + "x.";
			} 
			return "";
		} catch (Exception e) {
			throw new Exception ("..error in " + SOSClassUtil.getMethodName() + " " + e);
		}
	}
	
	/**
	 * Name der Ausgabedatei auslesen
	 * @return String
	 */
	public String getOutputFilename() {
		return outputFilename;
	}
	
	/**
	 * Name der Ausgabedatei setzen
	 * @param outputFilename The outputFilename to set.
	 */
	public void setOutputFilename(String outputFilename) {
		this.outputFilename = outputFilename;
	}
	
	/**
	 * Liefert den Ausgabedateitypen. Es k�nnen mehrere in semikolon getrennt angegeben werden:
	 * G�ltige Typen sind: [pdf, html, xml, xls, rtf]
	 * @return String
	 */
	public String getOutputType() {
		return outputType;
	}
	
	/**
	 * Setzen der Ausgabedateitypen:Es k�nnen mehrere in semikolon getrennt angegeben werden:
	 * G�ltige Typen sind: [pdf, html, xml, xls, rtf]
	 * @param outputType
	 */
	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}
	
	/**
	 * Jasper Report Dateiname auslesen. 
	 * Die Jasper Report Dateiname ist die Vorlage, die parsiert wird.
	 * Die Extension ist jasper.
	 * 
	 * @return String
	 */
	public String getReportFilename() {
		return reportFilename;
	}
	
	/**
	 * Jasper Report Dateiname setzen. 
	 * Die Jasper Report Dateiname ist die Vorlage, die parsiert wird.
	 * Die Extension ist jasper.
	 * 
	 * @param reportFilename
	 */
	public void setReportFilename(String reportFilename) {
		this.reportFilename = reportFilename;
	}
	
	
	/**
	 * SQL Dateiname auslesen. Beinhaltet eine SQL-Select-Statement. 
	 * 
	 * Wird dieser Parameter gesetzt dann wird den JasperReport
	 * die ResultSet der SQL-Select-Statement ohne Datenbankverbindung �bergeben.
	 * 
	 * @return String
	 */
	public String getQueryFilename() {
		return queryFilename;
	}
	
	/**
	 * SQL Dateiname auslesen. Beinhaltet eine SQL-Select-Statement. 
	 * 
	 * Wird dieser Parameter gesetzt dann wird den JasperReport
	 * die ResultSet der SQL-Select-Statement ohne Datenbankverbindung �bergeben.
	 * 
	 * Ist dieser Parameter leer bzw. existiert nicht, dann wird dem JasperReport 
	 * der Datenbankverbindung �bergeben.
	 * 
	 * @return queryFilename
	 */
	public void setQueryFilename(String queryFilename) {
		this.queryFilename = queryFilename;
	}
	
	/**
	 * SQL Dateiname auslesen. Beinhaltet eine SQL-Select-Statement. 
	 * 
	 * Ist ein parameter_query_filename angegeben, so wird dieser hier in java ausgef�hrt und das 
	 * Ergebis als Parameter den jasperreport mit der Datenbankverbindungen �bergebenden
	 * 
	 * @return String
	 */
	public String getParameterQueryFilename() {
		return parameterQueryFilename;
	}
	
	
	/**
	 * SQL Dateiname setzen. Beinhaltet eine SQL-Select-Statement. 
	 * 
	 * Ist ein parameter_query_filename angegeben, so wird dieser hier in java ausgef�hrt und das 
	 * Ergebis als Parameter den jasperreport mit der Datenbankverbindungen �bergebenden
	 * 
	 * @return String
	 */
	public void setParameterQueryFilename(String parameterQueryFilename) {
		this.parameterQueryFilename = parameterQueryFilename;
	}
	
	/**
	 * Konfigurationsdatei der Datenbankverbindungen auslesen.
	 * Ist eine Angabe nicht vorhanden, dann wird der Scheduler Datenbanken
	 * �bergeben.
	 * 
	 * @return String
	 */
	public String getSettingsFilename() {
		return settingsFilename;
	}
	
	
	/**
	 * Konfigurationsdatei der Datenbankverbindungen setzen.
	 * Ist eine Angabe nicht vorhanden, dann wird der Scheduler Datenbanken
	 * �bergeben.
	 *  
	 * @param settingsFilename The settingsFilename to set.
	 */
	public void setSettingsFilename(String settingsFilename) {
		this.settingsFilename = settingsFilename;
	}	
	
	/**
	 * Auslesen der Druckername.
	 * Wenn der Druckername eine prefix = "factory:" enth�lt, dann wird dieser 
	 * �ber die Documentfactory ermittelt. Sonst wird �ber diesen Druckername gedruckt.
	 * 
	 * @return String
	 */
	public String getPrinterName() {
		return printerName;
	}
	
	/**
	 * Druckername setzen.
	 * 
	 * Wenn der Druckername eine prefix = "factory:" enth�lt, dann wird dieser 
	 * �ber die Documentfactory ermittelt. Sonst wird �ber diesen Druckername gedruckt.
	 * @param printerName
	 */
	public void setPrinterName(
			String printerName) { 		
		this.printerName = printerName;
	}
	
	/**
	 * E-Mail Adresse auslesen.
	 * 
	 * Der Inhalt der Parameter "scheduler_order_report_mailto" ist die E-Mail-Adresse.
	 * Existiert ein Eintrag, dann wird versucht �ber diesen Adresse eine E-Mail zu versenden. 
	 * 
	 * @return String
	 */
	/*public String getEmailAddress() {
	 return emailAddress;
	 }*/
	
	
	/**
	 * E-Mail Adresse setzen.
	 * 
	 * Der Inhalt der Parameter "scheduler_order_report_mailto" ist die E-Mail-Adresse.
	 * Existiert ein Eintrag, dann wird versucht �ber diesen Adresse eine E-Mail zu versenden. 
	 * 
	 * @return String
	 */
	/*public void setEmailAddress(String emailAddress) {
	 this.emailAddress = emailAddress;
	 }*/
	
	/**
	 * Name der Konfigurationsdatei f�r Datenbankverbindung, wenn Documentfactory printerName  von einer 
	 * anderen Datenbank ermittelt werden soll. 
	 * 
	 * Oder der Emailversand soll per Dokumentfactory laufen und diese Datenbank verwenden.
	 * 
	 * @return String
	 */
	public String getFactorySettingsFile() {
		return factorySettingsFile;
	}
	
	/**
	 * Setzen der Name der Konfigurationsdatei f�r Datenbankverbindung, wenn 
	 * Documentfactory printerName / mail von einer anderen Datenbank ermittelt werden soll. 
	 * 
	 * @param factorySettingsFile
	 */
	public void setFactorySettingsFile(String factorySettingsFile) {
		this.factorySettingsFile = factorySettingsFile;
	}
	
	/**
	 * Anzahl zu druckenden Exemplarer auslesen.
	 * 
	 * @return int
	 */
	public int getPrinterCopies() {
		return printerCopies;
	}
	
	/**
	 * Anzahl zu druckenden Exemplarer setzen.
	 * 
	 * @param printerCopies
	 */
	public void setPrinterCopies(int printerCopies) {
		this.printerCopies = printerCopies;
	}
	
	/**
	 * Auslesen eine oder mehrere eMail Adressen an, die den Report als blind carbon copy erhalten.
	 * Mehrere eMail Adressen werden durch Komma getrennt.
	 * @return String          
	 */
	public String getMailBcc() {
		return mailBcc;
	}
	
	/**
	 * Geben Sie hier eine oder mehrere eMail Adressen an, die den Report als blind carbon copy erhalten.
	 * Mehrere eMail Adressen werden durch Komma getrennt.          
	 */
	public void setMailBcc(String mailBcc) {
		this.mailBcc = mailBcc;
	}
	
	
	/**
	 * <p>
	 * Mit diesem Parameter kann das Layout des Reports im HTML-Format oder als plain text angegeben
	 * werden. Zur Bestimmen des Layouts wird dem Parameter folgender Prefix zugeordnet:
	 * 
	 * <ul>
	 *  <li> factory:</li>
	 *  <li> factory_file:</li>
	 *  <li> plain:</li>
	 *  <li> plain_file:</li>
	 * </ul>
	 *  
	 * Innerhalb des Layouts k�nnen folgende Platzhalter verwendet werden, die jeweils mit [
	 * und ] geklammert werden, z.B. in der Form [date]:
	 * </p>
	 * <ul>
	 *  <li>[date] - aktuelles Datum</li>
	 *  <li>[datetime] - aktuelles Datum und Uhrzeit</li>
	 *  <li>[orderid] - ID des Auftrags</li>
	 *  <li>[jobname] - Name des Jobs</li>
	 *  <li>[taskid] - ID der Task</li>
	 *  <li>[sql] - SQL-Query, aus der der Report erstellt wurde</li>
	 *  <li>
	 *  [xml] - aus dem Ergebnis der Abfrage generiertes xml vor der
	 *  Transformation
	 *  </li>
	 * </ul>
	 * 
	 * @return String
	 */
	public String getMailBody() {
		return mailBody;
	}
	
	/**
	 * <p>
	 * Mit diesem Parameter kann das Layout des Reports im HTML-Format oder als plain text angegeben
	 * werden. Zur Bestimmen des Layouts wird dem Parameter folgender Prefix zugeordnet:
	 * 
	 * <ul>
	 *  <li> factory:</li>
	 *  <li> factory_file:</li>
	 *  <li> plain:</li>
	 *  <li> plain_file:</li>
	 * </ul>
	 *  
	 * Innerhalb des Layouts k�nnen folgende Platzhalter verwendet werden, die jeweils mit [
	 * und ] geklammert werden, z.B. in der Form [date]:
	 * </p>
	 * <ul>
	 *  <li>[date] - aktuelles Datum</li>
	 *  <li>[datetime] - aktuelles Datum und Uhrzeit</li>
	 *  <li>[orderid] - ID des Auftrags</li>
	 *  <li>[jobname] - Name des Jobs</li>
	 *  <li>[taskid] - ID der Task</li>
	 *  <li>[sql] - SQL-Query, aus der der Report erstellt wurde</li>
	 *  <li>
	 *  [xml] - aus dem Ergebnis der Abfrage generiertes xml vor der
	 *  Transformation
	 *  </li>
	 * </ul>
	 * 
	 * @param String
	 */
	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}
	
	/** Geben Sie hier eine oder mehrere eMail Adressen an, die den Report als carbon copy erhalten.
	 * Mehrere eMail Adressen werden durch Komma getrennt. 	 
	 * @return String
	 * */
	public String getMailCc() {
		return mailCc;
	}
	
	/** Geben Sie hier eine oder mehrere eMail Adressen an, die den Report als carbon copy erhalten.
	 * Mehrere eMail Adressen werden durch Komma getrennt. 	 
	 * @param mailCc - String
	 * */
	public void setMailCc(String mailCc) {
		this.mailCc = mailCc;
	}
	
	/** Der Inhalt des Parameters wird als Betreff der eMail des
	 * Reports verwendet und muss ausgef�llt sein, wenn Reports per
	 * eMail versendet werden. 
	 * Zur Bestimmen des Layouts wird dem Parameter folgender Prefix zugeordnet:
	 * 
	 * <ul>
	 *  <li> factory:</li>
	 *  <li> factory_file:</li>
	 *  <li> plain:</li>
	 *  <li> plain_file:</li>
	 * </ul>
	 * <p>
	 *  Es k�nnen folgende Platzhalter verwendet werden, die durch [
	 *  und] geklammert werden, z.B. in der Form [date]:
	 * </p>
	 * <ul>
	 *  <li>[date] - aktuelles Datum</li>
	 *  <li>[datetime] - aktuelles Datum und Uhrzeit</li>
	 *  <li>[orderid] - ID des Auftrags</li>
	 *  <li>[jobname] - Name des Jobs</li>
	 *  <li>[taskid] - ID der Task</li>
	 *</ul>
	 *
	 *@return String
	 */
	public String getMailSubject() {
		return mailSubject;
	}
	
	/** Der Inhalt des Parameters wird als Betreff der eMail des
	 * Reports verwendet und muss ausgef�llt sein, wenn Reports per
	 * eMail versendet werden. 
	 * Zur Bestimmen des Layouts wird dem Parameter folgender Prefix zugeordnet:
	 * 
	 * <ul>
	 *  <li> factory:</li>
	 *  <li> factory_file:</li>
	 *  <li> plain:</li>
	 *  <li> plain_file:</li>
	 * </ul>
	 * <p>
	 *  Es k�nnen folgende Platzhalter verwendet werden, die durch [
	 *  und] geklammert werden, z.B. in der Form [date]:
	 * </p>
	 * <ul>
	 *  <li>[date] - aktuelles Datum</li>
	 *  <li>[datetime] - aktuelles Datum und Uhrzeit</li>
	 *  <li>[orderid] - ID des Auftrags</li>
	 *  <li>[jobname] - Name des Jobs</li>
	 *  <li>[taskid] - ID der Task</li>
	 *</ul>
	 *
	 *@param String
	 */
	public void setMailSubject(String mailsubject) {
		this.mailSubject = mailsubject;
	}
	
	/** Auslesen eine oder mehrere eMail Adressen an, die den Report erhalten.
	 * Mehrere eMail Adressen werden durch Komma getrennt.<br/>
	 * Ist diese Einstellung leer, dann werden Reports nicht per eMail versendet.
	 * @return String 
	 * */
	
	public String getMailTo() {
		return mailTo;
	}
	
	/**
	 * Geben Sie hier eine oder mehrere eMail Adressen an, die den Report erhalten.
	 * Mehrere eMail Adressen werden durch Komma getrennt.<br/>
	 * Ist diese Einstellung leer, dann werden Reports nicht per eMail versendet.
	 * @return mailTo - String*/
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}
	
	/** Ist eine mail_it Parameter angegeben bzw auf true gesetzt, dann wird mit
	 * der Scheduler Mail Funktion emails versendet. Es wird �berpr�ft, ob zus�tzlich
	 * die Parameter "mail_to", "mail_cc", "mail_bcc", "mail_subject" und "mail_body" angegeben
	 * sind. Wenn ja, dann werden zu diesen Adressanten mit den Betreff und body emails versendet.
	 * Wenn diese Parametern nicht existieren, dann werden die Empf�nger aus den Einstellungen
	 * des Schedulers (factory.ini) genommen.
	 * 
	 * @return boolean
	 * */
	public boolean isMailIt() {
		return mailIt;
	}
	
	/** Ist eine mail_it Parameter angegeben bzw auf true gesetzt, dann wird mit
	 * der Scheduler Mail Funktion emails versendet. Es wird �berpr�ft, ob zus�tzlich
	 * die Parameter "mail_to", "mail_cc", "mail_bcc", "mail_subject" und "mail_body" angegeben
	 * sind. Wenn ja, dann werden zu diesen Adressanten mit den Betreff und body emails versendet.
	 * Wenn diese Parametern nicht existieren, dann werden die Empf�nger aus den Einstellungen
	 * des Schedulers (factory.ini) genommen.
	 * @param boolean
	 * */
	public void setMailIt(boolean mailIt) {
		this.mailIt = mailIt;
	}
	
	/**
	 * Datenbankverbindung holen, wenn Documentfactory printerName von einer anderen Datenbank 
	 * ermittelt werden soll.
	 * 
	 * @return sos.connection.SOSConnection
	 * @throws Exception
	 */
	private SOSConnection getFactoryConnection() throws Exception {
		
		SOSConnection factoryConnection = null;
		try {
			spooler_log.debug5("DB Connecting.. .");
			factoryConnection = SOSConnection.createInstance(
					this.getFactorySettingsFile(),  new sos.util.SOSSchedulerLogger(spooler_log));
			factoryConnection.connect();
			spooler_log.debug5("DB Connected");
			return factoryConnection;
		} catch (Exception e) {
			throw new Exception("\n -> ..error occurred in "
					+ SOSClassUtil.getMethodName() + ": " + e);
		}
	}
	
	/**
	 * Ermittelt den Druckername.
	 * Entweder der Druckername ist direkt angegeben oder 
	 * es wird �ber die Druckerid �ber die Tabelle Dokumentfactory.LF_PPRINTERS ermittelt, wenn der .	
	 * 
	 * @throws Exception
	 */
	private String getPrinter() throws Exception {		
		
		SOSConnection facConn = null;		
		String printName = ""; 
		String prefix      = "";
		try {	
			if (this.getPrinterName().toLowerCase().startsWith("factory:")) {
				prefix = "factory";
				printName = getPrinterName().substring("factory:".length());
			} else {
				printName = getPrinterName();
			}
			
			if(sosString.parseToString(prefix).length() == 0 ){
				//druckername ist direkt angegeben
				printName = this.getPrinterName();
				
			} else {
				//druckername �ber die Tabelle Dokumentfactory.LF_PPRINTERS ermittelt.
				if (sosString.parseToString(getFactorySettingsFile()).length() > 0 ) {
					facConn = getFactoryConnection();
				} else {
					facConn = this.getConnection();
				}
				//printName = DocumentFactoryClass.getActiveFactoryPrinter(facConn, printName );
				printName = getActiveFactoryPrinter(facConn, printName );
			}
			spooler_log.debug4("..gedruckt wird auf der Drucker: " + printName + " ");
			return printName;
			
			
		} catch (Exception e) {
			throw new Exception ("..error in " + SOSClassUtil.getClassName() + ": " + e);
		} finally {
			if (facConn != null) {
				facConn.rollback();
				facConn.disconnect();
			}
		}
	}
	
	/**
	 * @param sosConnection connection zur Document Factory Datenbank
	 * @param queue Name der Print Queue
	 * @return Name des Druckers oder Leerstring falls der Drucker nicht gefunden wurde
	 * @throws Exception falls Drucker, Queue oder Resource gesperrt ist
	 */
	public static String getActiveFactoryPrinter(SOSConnection sosConnection, String queue) throws Exception{
		String rv="";
		/** Tabelle der Warteschlangen */
		String tableQueues                     = "LF_QUEUES";
		/** Tabelle der Drucker */
		String tablePrinters                   = "LF_PRINTERS";
		/** Tabelle der Ressourcen */
		String tableResources                  = "LF_RESOURCES";

		HashMap printer	= sosConnection.getSingle("SELECT d.\"SYSTEM_NAME\", q.\"STATUS\", " +
				"d.\"STATUS\" as \"PRINTER_STATUS\", r.\"STATUS\" as \"RESOURCE_STATUS\" FROM " + 
				tableQueues   + " q, " +
				"( "+tablePrinters + " d LEFT OUTER JOIN " +
				tableResources +" r ON d.\"SYSTEM_NAME\"=r.\"RESOURCE_KEY\")"+
				" WHERE q.\"NAME\"='"+queue+"' AND d.\"PRINTER\"=q.\"PRINTER\" AND" +
				" (r.\"RESOURCE\" IS NULL OR r.\"RESOURCE_TYPE\"='printer')");		
				
		if (!printer.isEmpty()){
			rv = printer.get("system_name").toString();
			String status = printer.get("status").toString();
			if (status.equals("0")) throw new Exception("Queue "+queue+" is suspended.");
			String printerStatus = printer.get("printer_status").toString();
			if (printerStatus.equals("0")) throw new Exception("Printer "+rv+" is suspended.");
			if(printer.get("resource_status")!=null){
				String resourceStatus = printer.get("resource_status").toString();
				if (resourceStatus.equals("0")) throw new Exception("Resource for printer "+rv+" is suspended.");
			} 
		}
		
		return rv;
	}
	
	/**
	 * Email versenden.
	 * 
	 * 1. mail_it funktion. Ist eine mail_it Parameter angegeben bzw auf true gesetzt, dann wird mit
	 * der Scheduler Mail Funktion emails versendet. Es wird �berpr�ft, ob zus�tzlich
	 * die Parameter "mail_to", "mail_cc", "mail_bcc", "mail_subject" und "mail_body" angegeben 
	 * sind. Wenn ja, dann werden zu diesen Adressanten emails versendet. 
	 * Wenn diese Parametern nicht existieren, dann werden die Empf�nger aus den Einstellungen 
	 * des Schedulers (factory.ini) genommen. 
	 * 
	 * Ist der Jobparameter mail_it nicht 
	 * vorhanden bzw. als false gesetzt, dann wird der email mit Hilfe der Klasse SOSMailOrder versendet. 
	 * Die Voraussetzung zum email versenden mit der Klasse SOSMailOrder ist <br/><br/>
	 *   1. Datenbankverbindung ( entweder die Datenbankverbindung des Schedulers oder die Datenbankverbindung des Factorys 
	 *      Siehe Parameter factory_settings_file)
	 *   2. das Existieren der Tabelle "SETTINGS"
	 *   3. Eintr�ge zum email versenden in der Tabelle "SETTINGS"   
	 *  Die Empf�nger und der Subject und Body werden aus der Jobparameter ausgelesen. 
	 * 
	 * @param stateText
	 * @throws Exception
	 */	
	private String sendEmail(String stateText) throws Exception {
		SOSSchedulerLogger sosLogger = null;
		SOSConnection currConn = null;
		String currSubject = "";
		String currBody = "";
		try {
			sosLogger = new SOSSchedulerLogger(this.spooler_log);
			//mail_it L�sung			
			if (isMailIt()){
				sosLogger.debug("..email sending with mail_it Parameter.");
				//email erzwingen. Einstellungen wie sender/empf�nder siehe factory.ini
//				Sollen attachment mitversendet werden?
				if (!getSuspendAttachment()) {
					for(int i = 0; i < listOfOutputFilename.size(); i++) {
						this.spooler_log.mail().add_file(sosString.parseToString(listOfOutputFilename.get(i)));
					}						
				}
				
				if(sosString.parseToString(getMailSubject()).length() > 0) {
					currSubject = this.maskFilename(getMailSubject());
					spooler_log.mail().set_subject(currSubject);
					sosLogger.debug("..email subject: " + currSubject );
				} else {
					spooler_log.mail().set_subject("JasperReports: report delivery");
				}
				
				if(sosString.parseToString(getMailBody()).length() > 0) {
					currBody = this.maskFilename(getMailBody());
					spooler_log.mail().set_body(currBody );
					sosLogger.debug("..email body: " + currBody );
				} else {
					spooler_log.mail().set_body(stateText);
				}
				
				if(sosString.parseToString(getMailTo()).length() > 0) {
					spooler_log.mail().set_to(getMailTo());
					sosLogger.debug("..email send to: " + getMailTo());
				}
				
				if(sosString.parseToString(getMailCc()).length() > 0) {
					spooler_log.mail().set_cc(getMailCc());
					sosLogger.debug("..email CC send to: " + getMailCc());
				}
				
				if(sosString.parseToString(getMailBcc()).length() > 0) {
					spooler_log.mail().set_bcc(getMailBcc());
					sosLogger.debug("..email BCC send to: " + getMailBcc());
				}
				
				spooler_log.set_mail_it(true);
				sosLogger.debug("..email successfully send with mail_it Paramater. ");
				return stateText + "..email successfully send. ";
			}			
			//ende mail_it L�sung
			
			//keine emails zum versenden vorhanden
			if ((sosString.parseToString(getMailTo()).length() == 0) &&
					(sosString.parseToString(getMailCc()).length() == 0	)	&&
					(sosString.parseToString(getMailBcc()).length() == 0	)
			)	{
				sosLogger.debug("..there is no Recipient to send email.");
				return stateText;
			}
			
			
			SOSConnectionSettings sett = null;
			SOSMailOrder mailOrder = null;			
			try {
				if (sosString.parseToString(getFactorySettingsFile()).length() > 0) {
					sosLogger.debug9(".. get new Connection from " + this.getFactorySettingsFile());
					currConn = getFactoryConnection();
					
				} else {
					currConn = getConnection();
				}
				sett = new SOSConnectionSettings(currConn , "SETTINGS", sosLogger);
				//�berpr�fen, ob die Tabelle Settings vorhanden ist bzw. Eintr�ge hat.							
				//java.util.Properties entries = sett.getSection("email", "mail_server");				
				String val = currConn.getSingleValue("SELECT \"NAME\" FROM SETTINGS WHERE \"APPLICATION\" = 'email' AND \"SECTION\" = 'mail_server' AND \"SECTION\" <> \"NAME\"");
				if (sosString.parseToString(val).length() > 0) {
					mailOrder = new SOSMailOrder(sett, currConn);
				} else {
					//mailOrder = new SOSMailOrder(spooler_log.mail().smtp(), this.getConnection());
					sosLogger.warn("..error could not get application [email] and [mail_server] from SETTINGS " );
					throw new Exception("..error could not get application [email] and [mail_server] from SETTINGS ");
				}
			} catch (Exception e) {
				sosLogger.warn("..error could not get application [email] and [mail_server] from SETTINGS " + e.toString() );
				throw new Exception("..error could not get application [email] and [mail_server] from SETTINGS " + e.toString() );
				//mailOrder = new SOSMailOrder(spooler_log.mail().smtp(), this.getConnection());
			}
			mailOrder.setSOSLogger(sosLogger);
			//subject
			if (sosString.parseToString(this.getMailSubject()).length() > 0) {
				currSubject = this.maskFilename(getMailSubject());
				sosLogger.debug("Mail subject: " + currSubject);
				if (getMailSubject().startsWith("factory:")) {					
					mailOrder.setSubjectTemplateType(SOSMailOrder.TEMPLATE_TYPE_FACTORY);						
					mailOrder.setSubjectTemplate(currSubject.substring("factory:".length()));						
				} else if(getMailSubject().startsWith("factory_file:")) {					
					mailOrder.setSubjectTemplateType(SOSMailOrder.TEMPLATE_TYPE_FACTORY_FILE);						
					mailOrder.setSubjectTemplate(currSubject.substring("factory_file:".length()));						
				} else if(getMailSubject().startsWith("plain:")) {				
					mailOrder.setSubjectTemplateType(SOSMailOrder.TEMPLATE_TYPE_PLAIN);						
					mailOrder.setSubjectTemplate(currSubject.substring("plain:".length()));						
				} else if(getMailSubject().startsWith("plain_file:")) {				
					mailOrder.setSubjectTemplateType(SOSMailOrder.TEMPLATE_TYPE_PLAIN_FILE);						
					mailOrder.setSubjectTemplate(currSubject.substring("plain_file:".length()));						
				} else {					
					//mailOrder.setFrom(spooler_log.mail().from());
					mailOrder.setSOSLogger(sosLogger);							
					mailOrder.setSubject(currSubject);
				}
			} 
			
			if (sosString.parseToString(this.getMailBody()).length() > 0) {
				currBody = this.maskFilename(getMailBody());
				sosLogger.debug("Mail body: " + currBody);
				if (getMailBody().startsWith("factory:")) {					
					mailOrder.setBodyTemplateType(SOSMailOrder.TEMPLATE_TYPE_FACTORY);						
					mailOrder.setBodyTemplate(currBody.substring("factory:".length()));						
				} else if(getMailBody().startsWith("factory_file:")) {	
					mailOrder.setBodyTemplateType(SOSMailOrder.TEMPLATE_TYPE_FACTORY_FILE);						
					mailOrder.setBodyTemplate(currBody.substring("factory_file:".length()));						
				} else if(getMailBody().startsWith("plain:")) {		
					mailOrder.setBodyTemplateType(SOSMailOrder.TEMPLATE_TYPE_PLAIN);						
					mailOrder.setBodyTemplate(currBody.substring("plain:".length()));						
				} else if(getMailBody().startsWith("plain_file:")) {															
					mailOrder.setBodyTemplateType(SOSMailOrder.TEMPLATE_TYPE_PLAIN_FILE);						
					mailOrder.setBodyTemplate(currBody.substring("plain_file:".length()));						
				} else {				
					mailOrder.setSOSLogger(sosLogger);		
					mailOrder.setBody(currBody);
				}
			}
			
			mailOrder.addRecipient(this.getMailTo());
			
			if (sosString.parseToString(getMailBcc()).length() > 0)
				mailOrder.addBCC(this.getMailBcc());
			
			if (sosString.parseToString(getMailCc()).length() > 0)
				mailOrder.addCC(this.getMailCc());
			
			//Sollen attachment mitversendet werden?
			if (!getSuspendAttachment()) {
				for(int i = 0; i < listOfOutputFilename.size(); i++) {
					mailOrder.addAttachment(sosString.parseToString(listOfOutputFilename.get(i)));				
				}
			}
			
			sosLogger.debug("..replacement job_name = " + this.spooler_task.job().name());
			mailOrder.addReplacement("job_name", this.spooler_task.job().name());
			
			sosLogger.debug("..replacement job_title = " + this.spooler_task.job().title());
			mailOrder.addReplacement("job_title", this.spooler_task.job().title());
			
			sosLogger.debug("..replacement state_text = " + stateText);
			mailOrder.addReplacement("state_text", stateText);
			
			mailOrder.send();	
			
			
			spooler_log.debug("..email successfully send. ");
			return stateText + "..email successfully send. ";
			
		} catch (Exception e) {
			throw new Exception ("..error in " + SOSClassUtil.getClassName() + ": " + e);
		} finally {
			if (sosString.parseToString(getFactorySettingsFile()).length() > 0) {
				if (currConn != null) {
					currConn.rollback();
					currConn.disconnect();
				}
			}
		}
	}
	
	/**
	 * get job parameters from job configuration (scheduler.xml)
	 */	
	protected void getJobParameters() throws Exception {
		
		try { // to fetch default job parameters
			if (sosString.parseToString(spooler_task.params().var("settings_filename")).length() > 0) {
				this.setSettingsFilename(spooler_task.params().var("settings_filename"));
				spooler_log.debug1(".. job parameter [settings_filename]: " + this.getSettingsFilename());
			}
			
			if (sosString.parseToString(spooler_task.params().var("report_filename")).length() > 0) {
				this.setReportFilename(spooler_task.params().var("report_filename"));
				spooler_log.debug1(".. job parameter [report_filename]: " + this.getReportFilename());
			}
			
			if (sosString.parseToString(spooler_task.params().var("query_filename")).length() > 0) {
				this.setQueryFilename(spooler_task.params().var("query_filename"));
				spooler_log.debug1(".. job parameter [query_filename]: " + this.getQueryFilename());
			}
						
			
			if (sosString.parseToString(spooler_task.params().var("query_statement")).length() > 0) {
				this.setQueryStatement(spooler_task.params().var("query_statement"));
				spooler_log.debug1(".. job parameter [query_statement]: " + this.getQueryStatement());
			}
			
			if (sosString.parseToString(spooler_task.params().var("output_type")).length() > 0) {
				this.setOutputType(spooler_task.params().var("output_type"));
				spooler_log.debug1(".. job parameter [output_type]: " + this.getOutputType());
			}
			
			if (sosString.parseToString(spooler_task.params().var("output_filename")).length() > 0) {
				this.setOutputFilename(spooler_task.params().var("output_filename"));
				spooler_log.debug1(".. job parameter [output_filename]: " + this.getOutputFilename());
			}
			
			/*if (spooler_task.params().var("scheduler_order_report_mailto") != null && spooler_task.params().var("scheduler_order_report_mailto").length() > 0) {
			 this.setEmailAddress(spooler_task.params().var("scheduler_order_report_mailto"));
			 spooler_log.debug1(".. job parameter [scheduler_order_report_mailto]: " + this.getEmailAddress());
			 }*/			
			
			if (sosString.parseToString(spooler_task.params().var("printer_name")).length() > 0) {
				this.setPrinterName(spooler_task.params().var("printer_name"));
				spooler_log.debug1(".. job parameter [printer_name]: " + this.getPrinterName());
			}
			
			if (sosString.parseToString(spooler_task.params().var("factory_settings_file")).length() > 0) {
				this.setFactorySettingsFile(spooler_task.params().var("factory_settings_file"));
				spooler_log.debug1(".. job parameter [factory_settings_file]: " + this.getFactorySettingsFile());
			}
			
			if (sosString.parseToString(spooler_task.params().var("mail_it")).length() > 0) {
				this.setMailIt(sosString.parseToBoolean((spooler_task.params().var("mail_it"))));
				spooler_log.debug1(".. job parameter [mail_it]: " + this.isMailIt());
			}
			
			if (sosString.parseToString(spooler_task.params().var("parameter_query_filename")).length() > 0) {
				this.parameterQueryFilename = sosString.parseToString((spooler_task.params().var("parameter_query_filename")));
				spooler_log.debug1(".. job parameter [parameter_query_filename]: " + parameterQueryFilename);
			}
			
			if (sosString.parseToString(spooler_task.params().var("printer_copies")).length() > 0) {
				String pc = sosString.parseToString((spooler_task.params().var("printer_copies")));
				if (pc.equals("0")) {					
					spooler_log.warn(".. job parameter [printer_copies] is 0 not in range 1..2147483647 ");
					throw new Exception(".. job parameter [printer_copies] is 0 not in range 1..2147483647 ");
				}
				//�berpr�fen, ob der Parameter printer_copies ziffern ist
				char c[] = pc.toCharArray();				
				for (int i = 0; i < c.length; i++) {
					if (!(Character.isDigit(c[i]))) {
						spooler_log.warn(".. job parameter [printer_copies] is not digit: " + pc);
						throw new Exception(".. job parameter [printer_copies] is not digit: " + pc);
					}					
				}
				this.printerCopies = Integer.parseInt(pc);
				spooler_log.debug1(".. job parameter [printer_copies]: " + printerCopies);
			}
			
			if (sosString.parseToString(spooler_task.params().var("mail_to")).length() > 0) {
				this.setMailTo(sosString.parseToString((spooler_task.params().var("mail_to"))));
				spooler_log.debug1(".. job parameter [mail_to]: " + this.getMailTo());
			}
			
			if (sosString.parseToString(spooler_task.params().var("mail_cc")).length() > 0) {
				this.setMailCc(sosString.parseToString((spooler_task.params().var("mail_cc"))));
				spooler_log.debug1(".. job parameter [mail_cc]: " + this.getMailCc());
			}
			
			if (sosString.parseToString(spooler_task.params().var("mail_bcc")).length() > 0) {
				this.setMailBcc(sosString.parseToString((spooler_task.params().var("mail_bcc"))));
				spooler_log.debug1(".. job parameter [mail_bcc]: " + this.getMailBcc());
			}
			
			if (sosString.parseToString(spooler_task.params().var("mail_subject")).length() > 0) {
				this.setMailSubject(sosString.parseToString((spooler_task.params().var("mail_subject"))));
				spooler_log.debug1(".. job parameter [mail_subject]: " + this.getMailSubject());
			}
			
			if (sosString.parseToString(spooler_task.params().var("mail_body")).length() > 0) {
				this.setMailBody(sosString.parseToString((spooler_task.params().var("mail_body"))));
				spooler_log.debug1(".. job parameter [mail_body]: " + this.getMailBody());
			}
			if (sosString.parseToString(spooler_task.params().var("suspend_attachment")).length() > 0) {
				this.setSuspendAttachment(sosString.parseToBoolean((spooler_task.params().var("suspend_attachment"))));
				spooler_log.debug1(".. job parameter [suspend_attachment]: " + this.getSuspendAttachment());
			}
			//L�scht zuerst das alte Bericht, bevor eine neue erstellt wird			
			if (sosString.parseToString(spooler_task.params().var("delete_old_output_file")).length() > 0) {
				this.setDeleteOldFilename(sosString.parseToBoolean((spooler_task.params().var("delete_old_output_file"))));
				spooler_log.debug1(".. job parameter [delete_old_output_file]: " + this.isDeleteOldFilename());
			}
			
			
		} catch (Exception e) {
			throw new Exception("..error occurred processing job parameters: " + e.getMessage());
		}
	}
	
	/**
	 * to fetch parameters from orders that have precedence to job parameters
	 * @throws Exception
	 */
	private void getOrderParameters() throws Exception {		
		try { 
			if (spooler_task.job().order_queue() != null) {
				
				order = spooler_task.order();
				//order = createOrderPayload(order);
				
				// create order payload and xml payload from web service request
				if (order.web_service_operation_or_null() != null) {
					SOSXMLXPath xpath = null;
					Web_service_request request = order.web_service_operation().request();
					
					// should the request be previously transformed ...
					if (order.web_service().params().var("request_stylesheet") != null && order.web_service().params().var("request_stylesheet").length() > 0) {
						Xslt_stylesheet stylesheet = spooler.create_xslt_stylesheet();
						stylesheet.load_file(order.web_service().params().var("request_stylesheet"));
						String xml_document = stylesheet.apply_xml(request.string_content());
						spooler_log.debug3("content of request:\n" + request.string_content());
						spooler_log.debug3("content of request transformation:\n" + xml_document);
						
						xpath = new sos.xml.SOSXMLXPath(new java.lang.StringBuffer(xml_document));
						// add order parameters from transformed request
						Variable_set params = spooler.create_variable_set();
						if (xpath.selectSingleNodeValue("//param[@name[.='settings_filename']]/@value") != null) {
							params.set_var("settings_filename", xpath.selectSingleNodeValue("//param[@name[.='settings_filename']]/@value"));
						}
						if (xpath.selectSingleNodeValue("//param[@name[.='report_filename']]/@value") != null) {
							params.set_var("report_filename", xpath.selectSingleNodeValue("//param[@name[.='report_filename']]/@value"));
						}
						if (xpath.selectSingleNodeValue("//param[@name[.='query_filename']]/@value") != null) {
							params.set_var("query_filename", xpath.selectSingleNodeValue("//param[@name[.='query_filename']]/@value"));
						}
						if (xpath.selectSingleNodeValue("//param[@name[.='query_statement']]/@value") != null) {
							params.set_var("query_statement", xpath.selectSingleNodeValue("//param[@name[.='query_statement']]/@value"));
						}
						if (xpath.selectSingleNodeValue("//param[@name[.='output_type']]/@value") != null) {
							params.set_var("output_type", xpath.selectSingleNodeValue("//param[@name[.='output_type']]/@value"));
						}
						if (xpath.selectSingleNodeValue("//param[@name[.='output_filename']]/@value") != null) {
							params.set_var("output_filename", xpath.selectSingleNodeValue("//param[@name[.='output_filename']]/@value"));
						}							
						if (xpath.selectSingleNodeValue("//param[@name[.='scheduler_order_report_mailto']]/@value") != null) {
							params.set_var("scheduler_order_report_mailto", xpath.selectSingleNodeValue("//param[@name[.='scheduler_order_report_mailto']]/@value"));
						}
						if (xpath.selectSingleNodeValue("//param[@name[.='scheduler_order_report_printer_id']]/@value") != null) {
							params.set_var("scheduler_order_report_printer_id", xpath.selectSingleNodeValue("//param[@name[.='scheduler_order_report_printer_id']]/@value"));
						}
						if (xpath.selectSingleNodeValue("//param[@name[.='printer_name']]/@value") != null) {
							params.set_var("printer_name", xpath.selectSingleNodeValue("//param[@name[.='printer_name']]/@value"));
						}
						if (xpath.selectSingleNodeValue("//param[@name[.='factory_settings_file']]/@value") != null) {
							params.set_var("factory_settings_file", xpath.selectSingleNodeValue("//param[@name[.='factory_settings_file']]/@value"));
						}						
						if (xpath.selectSingleNodeValue("//param[@name[.='parameter_query_filename']]/@value") != null) {
							params.set_var("parameter_query_filename", xpath.selectSingleNodeValue("//param[@name[.='parameter_query_filename']]/@value"));
						}
						if (xpath.selectSingleNodeValue("//param[@name[.='printer_copies']]/@value") != null) {
							params.set_var("printer_copies", xpath.selectSingleNodeValue("//param[@name[.='printer_copies']]/@value"));
						}
						if (xpath.selectSingleNodeValue("//param[@name[.='mail_it']]/@value") != null) {
							params.set_var("mail_it", xpath.selectSingleNodeValue("//param[@name[.='mail_it']]/@value"));
						}
						if (xpath.selectSingleNodeValue("//param[@name[.='mail_to']]/@value") != null) {
							params.set_var("mail_to", xpath.selectSingleNodeValue("//param[@name[.='mail_to']]/@value"));
						}
						if (xpath.selectSingleNodeValue("//param[@name[.='mail_cc']]/@value") != null) {
							params.set_var("mail_cc", xpath.selectSingleNodeValue("//param[@name[.='mail_cc']]/@value"));
						}
						if (xpath.selectSingleNodeValue("//param[@name[.='mail_bcc']]/@value") != null) {
							params.set_var("mail_bcc", xpath.selectSingleNodeValue("//param[@name[.='mail_bcc']]/@value"));
						}
						if (xpath.selectSingleNodeValue("//param[@name[.='mail_subject']]/@value") != null) {
							params.set_var("mail_subject", xpath.selectSingleNodeValue("//param[@name[.='mail_subject']]/@value"));
						}
						if (xpath.selectSingleNodeValue("//param[@name[.='mail_body']]/@value") != null) {
							params.set_var("mail_body", xpath.selectSingleNodeValue("//param[@name[.='mail_body']]/@value"));
						}
						if (xpath.selectSingleNodeValue("//param[@name[.='suspend_attachment']]/@value") != null) {
							params.set_var("suspend_attachment", xpath.selectSingleNodeValue("//param[@name[.='suspend_attachment']]/@value"));
						}
//						L�scht zuerst das alte Bericht, bevor eine neue erstellt wird
						if (xpath.selectSingleNodeValue("//param[@name[.='delete_old_output_file']]/@value") != null) {
							params.set_var("delete_old_output_file", xpath.selectSingleNodeValue("//param[@name[.='delete_old_output_file']]/@value"));
						}
						
						
						order.set_payload(params);
					} else {
						xpath = new sos.xml.SOSXMLXPath(new java.lang.StringBuffer(request.string_content()));
						// add order parameters from request
						Variable_set params = spooler.create_variable_set();
						if (xpath.selectSingleNodeValue("//param[name[text()='settings_filename']]/value") != null) {
							params.set_var("settings_filename", xpath.selectSingleNodeValue("//param[name[text()='settings_filename']]/value"));
						}
						if (xpath.selectSingleNodeValue("//param[name[text()='report_filename']]/value") != null) {
							params.set_var("report_filename", xpath.selectSingleNodeValue("//param[name[text()='report_filename']]/value"));
						}
						if (xpath.selectSingleNodeValue("//param[name[text()='query_filename']]/value") != null) {
							params.set_var("query_filename", xpath.selectSingleNodeValue("//param[name[text()='query_filename']]/value"));
						}
						if (xpath.selectSingleNodeValue("//param[name[text()='query_statement']]/value") != null) {
							params.set_var("query_statement", xpath.selectSingleNodeValue("//param[name[text()='query_statement']]/value"));
						}
						if (xpath.selectSingleNodeValue("//param[name[text()='output_type']]/value") != null) {
							params.set_var("output_filename", xpath.selectSingleNodeValue("//param[name[text()='output_type']]/value"));
						}
						if (xpath.selectSingleNodeValue("//param[name[text()='output_filename']]/value") != null) {
							params.set_var("output_filename", xpath.selectSingleNodeValue("//param[name[text()='output_filename']]/value"));
						}							
						if (xpath.selectSingleNodeValue("//param[name[text()='scheduler_order_report_mailto']]/value") != null) {
							params.set_var("scheduler_order_report_mailto", xpath.selectSingleNodeValue("//param[name[text()='scheduler_order_report_mailto']]/value"));
						}
						if (xpath.selectSingleNodeValue("//param[name[text()='scheduler_order_report_printer_id']]/value") != null) {
							params.set_var("scheduler_order_report_printer_id", xpath.selectSingleNodeValue("//param[name[text()='scheduler_order_report_printer_id']]/value"));
						}
						if (xpath.selectSingleNodeValue("//param[name[text()='printer_name']]/value") != null) {
							params.set_var("printer_name", xpath.selectSingleNodeValue("//param[name[text()='printer_name']]/value"));
						}
						if (xpath.selectSingleNodeValue("//param[name[text()='factory_settings_file']]/value") != null) {
							params.set_var("factory_settings_file", xpath.selectSingleNodeValue("//param[name[text()='factory_settings_file']]/value"));
						}
						if (xpath.selectSingleNodeValue("//param[name[text()='parameter_query_filename']]/value") != null) {
							params.set_var("parameter_query_filename", xpath.selectSingleNodeValue("//param[name[text()='parameter_query_filename']]/value"));
						}
						if (xpath.selectSingleNodeValue("//param[name[text()='printer_copies']]/value") != null) {
							params.set_var("printer_copies", xpath.selectSingleNodeValue("//param[name[text()='printer_copies']]/value"));
						}			
						if (xpath.selectSingleNodeValue("//param[name[text()='mail_it']]/value") != null) {
							params.set_var("mail_it", xpath.selectSingleNodeValue("//param[name[text()='mail_it']]/value"));
						}	
						if (xpath.selectSingleNodeValue("//param[name[text()='mail_to']]/value") != null) {
							params.set_var("mail_to", xpath.selectSingleNodeValue("//param[name[text()='mail_to']]/value"));
						}			
						if (xpath.selectSingleNodeValue("//param[name[text()='mail_cc']]/value") != null) {
							params.set_var("mail_cc", xpath.selectSingleNodeValue("//param[name[text()='mail_cc']]/value"));
						}			
						if (xpath.selectSingleNodeValue("//param[name[text()='mail_bcc']]/value") != null) {
							params.set_var("mail_bcc", xpath.selectSingleNodeValue("//param[name[text()='mail_bcc']]/value"));
						}			
						if (xpath.selectSingleNodeValue("//param[name[text()='mail_subject']]/value") != null) {
							params.set_var("mail_subject", xpath.selectSingleNodeValue("//param[name[text()='mail_subject']]/value"));
						}			
						if (xpath.selectSingleNodeValue("//param[name[text()='mail_body']]/value") != null) {
							params.set_var("mail_body", xpath.selectSingleNodeValue("//param[name[text()='mail_body']]/value"));
						}
						if (xpath.selectSingleNodeValue("//param[name[text()='suspend_attachment']]/value") != null) {
							params.set_var("suspend_attachment", xpath.selectSingleNodeValue("//param[name[text()='suspend_attachment']]/value"));
						}			
						
						if (xpath.selectSingleNodeValue("//param[name[text()='delete_old_output_file']]/value") != null) {
							params.set_var("delete_old_output_file", xpath.selectSingleNodeValue("//param[name[text()='delete_old_output_file']]/value"));
						}									
						
						order.set_payload(params);
					}
					
					
				}
					
					orderData = (Variable_set) order.payload();
					if ( orderData != null && orderData.var("settings_filename") != null && orderData.var("settings_filename").toString().length() > 0) {
						this.setSettingsFilename(orderData.var("settings_filename").toString());
						spooler_log.debug1(".. order parameter [settings_filename]: " + this.getSettingsFilename());
					}
					if ( orderData != null && orderData.var("report_filename") != null && orderData.var("report_filename").toString().length() > 0) {
						this.setReportFilename(orderData.var("report_filename").toString());
						spooler_log.debug1(".. order parameter [report_filename]: " + this.getReportFilename());
					}
					if ( orderData != null && orderData.var("query_filename") != null && orderData.var("query_filename").toString().length() > 0) {
						this.setQueryFilename(orderData.var("query_filename").toString());
						spooler_log.debug1(".. order parameter [query_filename]: " + this.getQueryFilename());
					}
					if ( orderData != null && orderData.var("query_statement") != null && orderData.var("query_statement").toString().length() > 0) {
						this.setQueryStatement(orderData.var("query_statement").toString());
						spooler_log.debug1(".. order parameter [query_statement]: " + this.getQueryStatement());
					}
					
					if ( orderData != null && orderData.var("output_type") != null && orderData.var("output_type").toString().length() > 0) {
						this.setOutputType(orderData.var("output_type").toString());
						spooler_log.debug1(".. order parameter [output_type]: " + this.getOutputType());
					}
					if ( orderData != null && orderData.var("output_filename") != null && orderData.var("output_filename").toString().length() > 0) {
						this.setOutputFilename(orderData.var("output_filename").toString());
						spooler_log.debug1(".. order parameter [output_filename]: " + this.getOutputFilename());
					}
					/*if ( orderData != null && orderData.var("scheduler_order_report_mailto") != null && orderData.var("scheduler_order_report_mailto").toString().length() > 0) {
					 this.setEmailAddress(orderData.var("scheduler_order_report_mailto").toString());
					 spooler_log.debug1(".. order parameter [scheduler_order_report_mailto]: " + this.getEmailAddress());
					 }*/
					
					if ( orderData != null && orderData.var("printer_name") != null && orderData.var("printer_name").toString().length() > 0) {
						this.setPrinterName(orderData.var("printer_name").toString());
						spooler_log.debug1(".. order parameter [printer_name]: " + this.getPrinterName());
					}
					if ( orderData != null && orderData.var("factory_settings_file") != null && orderData.var("factory_settings_file").toString().length() > 0) {
						this.setFactorySettingsFile(orderData.var("factory_settings_file").toString());
						spooler_log.debug1(".. order parameter [factory_settings_file]: " + this.getFactorySettingsFile());
					}					
					if ( orderData != null && orderData.var("parameter_query_filename") != null && orderData.var("parameter_query_filename").toString().length() > 0) {
						this.parameterQueryFilename = this.sosString.parseToString((orderData.var("parameter_query_filename")));
						spooler_log.debug1(".. order parameter [parameter_query_filename]: " + parameterQueryFilename);
					}
					if ( orderData != null && orderData.var("printer_copies") != null && orderData.var("printer_copies").toString().length() > 0) {
						this.setPrinterCopies(Integer.parseInt(this.sosString.parseToString((orderData.var("printer_copies")))));
						spooler_log.debug1(".. order parameter [printer_copies]: " + this.getPrinterCopies());
					}
					if ( orderData != null && sosString.parseToString(orderData.var("mail_it")).length() > 0) {
						this.setMailIt(sosString.parseToBoolean((orderData.var("mail_it"))));
						spooler_log.debug1(".. order parameter [mail_it]: " + this.isMailIt());
					}
					if ( orderData != null && sosString.parseToString(orderData.var("mail_to")).length() > 0) {
						this.setMailTo(sosString.parseToString((orderData.var("mail_to"))));
						spooler_log.debug1(".. order parameter [mail_to]: " + this.getMailTo());
					}
					if ( orderData != null && sosString.parseToString(orderData.var("mail_cc")).length() > 0) {
						this.setMailCc(sosString.parseToString((orderData.var("mail_cc"))));
						spooler_log.debug1(".. order parameter [mail_cc]: " + this.getMailCc());
					}
					if ( orderData != null && sosString.parseToString(orderData.var("mail_bcc")).length() > 0) {
						this.setMailBcc(sosString.parseToString((orderData.var("mail_bcc"))));
						spooler_log.debug1(".. order parameter [mail_bcc]: " + this.getMailBcc());
					}
					if ( orderData != null && sosString.parseToString(orderData.var("mail_subject")).length() > 0) {
						this.setMailSubject(sosString.parseToString((orderData.var("mail_subject"))));
						spooler_log.debug1(".. order parameter [mail_subject]: " + this.getMailSubject());
					}
					if ( orderData != null && sosString.parseToString(orderData.var("mail_body")).length() > 0) {
						this.setMailBody(sosString.parseToString((orderData.var("mail_body"))));
						spooler_log.debug1(".. order parameter [mail_body]: " + this.getMailBody());
					}
					if ( orderData != null && sosString.parseToString(orderData.var("suspend_attachment")).length() > 0) {
						this.setSuspendAttachment(sosString.parseToBoolean((orderData.var("suspend_attachment"))));
						spooler_log.debug1(".. order parameter [suspend_attachment]: " + this.getSuspendAttachment());
					}	
					
					if ( orderData != null && sosString.parseToString(orderData.var("delete_old_output_file")).length() > 0) {
						this.setDeleteOldFilename(sosString.parseToBoolean((orderData.var("delete_old_output_file"))));
						spooler_log.debug1(".. order parameter [delete_old_output_file]: " + this.isDeleteOldFilename());
					}	
			}
		} catch (Exception e) {
			throw new Exception("error occurred processing parameters: " + e.toString());
		}
	}
	
	/**
	 * Angabe der Pflichtfelder/-parameter �berpr�fen.
	 * 
	 * @throws Exception
	 */
	private void checkParams() throws Exception { 
		try { 
			if (this.getReportFilename() == null || this.getReportFilename().length() == 0) 
				throw new Exception("no report filename was given");				
			if (this.getOutputType() == null || this.getOutputType().length() == 0) 
				throw new Exception("no output type [pdf, html, xml, xls, rtf] was given");
			if (!isValidOutputType()) {
				throw  new Exception("unsupported output type [pdf, htm, xml, xls, rtf]: " + this.getOutputType());
			}
			if (sosString.parseToString(this.queryFilename).length() > 0 &&
					sosString.parseToString(this.queryStatement).length() > 0 	) {
				throw new Exception("to many parameter [query_filename] and [query_statement]" );
			}
		} catch (Exception e) {
			throw new Exception("error occurred checking parameters: " + e.toString());
		}
	}


	public String getQueryStatement() {
		return queryStatement;
	}


	public void setQueryStatement(String queryStatement) {
		this.queryStatement = queryStatement;
	}


	public boolean getSuspendAttachment() {
		return suspendAttachment;
	}


	public void setSuspendAttachment(boolean suspendAttachment) {
		this.suspendAttachment = suspendAttachment;
	}
	
	/**
	 * neu initialisieren der Variablen
	 * @throws Exception
	 */
	private void init() throws Exception {
		try {
			settingsFilename       = "";
			reportFilename         = "";
			queryFilename          = "";
			outputType             = "pdf";
			outputFilename         = "";
			printerName            = "";
			factorySettingsFile    = "";
			sosString 			   = new SOSString();
			filledReportFile 	   = null;
			listOfOutputFilename   = null;
			parameterQueryFilename = "";
			order 				   = null;
			orderData 			   = null;
			printerCopies		   = 1;
			mailIt				   = false;
			mailTo				   = "";
			mailCc				   = "";
			mailBcc				   = "";
			mailSubject			   = "";
			mailBody			   = "";
			queryStatement         = "";
			suspendAttachment      = false;
			
		} catch (Exception e) {
			throw new Exception ("..error in " + SOSClassUtil.getMethodName() + " :" + e);
		}
	}
	
	
	private String maskFilename(String filename) throws Exception {
		
		String targetFilename = filename;
		try {			
			
			// check for a date format string given in the file mask
			if (targetFilename.matches("(.*)(\\[date\\:)([^\\]]+)(\\])(.*)")) {
				int posBegin = targetFilename.indexOf("[date:");
				if (posBegin > -1) {
					int posEnd = targetFilename.indexOf("]", posBegin + 6);
					if (posEnd > -1) {
						targetFilename = ((posBegin > 0) ? targetFilename.substring(0, posBegin) : "")
						+ SOSDate.getCurrentTimeAsString(targetFilename.substring(posBegin + 6, posEnd))
						+ ((targetFilename.length() > posEnd) ? targetFilename.substring(posEnd + 1) : "");
					}
				}
			}			
//			liefert das letzte Monat in der Form MM-YYYY
			if (targetFilename.indexOf("[lastmonth]") > -1) {
				String lastMonth_ = SOSDate.getDateAsString(getLastMonth(SOSDate.getCurrentTimeAsString("dd-MM-yyyy")), "MM-yyyy");
				targetFilename = targetFilename.replaceAll("\\[lastmonth\\]", lastMonth_);				
			}
			
			// should any opening and closing brackets be found in the file name, then this is an error
			Matcher m = Pattern.compile("\\[[^\\]]*\\]").matcher(targetFilename);
			if ( m.find() ) throw new Exception("unsupported file mask found:"+ m.group());
			
			spooler_log.debug(filename + " mask in " + targetFilename);
			
			return targetFilename;				
		} catch (Exception e) {
			throw new Exception ("..error in " + SOSClassUtil.getMethodName() + " : " + e.getMessage());
		}
	} 
	
	private Date getLastMonth(String  MMyy) throws Exception {
		try {		
			Date d = SOSDate.getDate(MMyy, "dd-MM-yy");
			
			//Date d = SOSDate.getDate("112007", "MMyy");
			
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			
			
			//System.out.println("..get the last day of the month " + new java.text.SimpleDateFormat("MMMM yyyy").format( cal.getTime()) );
			cal.add(cal.MONTH, -1);                    // add one month
			//cal.set(Calendar.DAY_OF_MONTH, 1);       // set the date to the 1st of the month
			//cal.add(Calendar.DAY_OF_MONTH, 1);     // go one day back
			//System.out.println("... is " + cal.get(Calendar.DAY_OF_MONTH) );
			//Date s = SOSDate.getDate(cal.get(Calendar.DAY_OF_MONTH) + "0212", "ddMMyy");
			Date s = cal.getTime();
			return s;
			
			
		} catch (Exception e) {        	
			throw e;
		}		
	}


	public boolean isDeleteOldFilename() {
		return deleteOldFilename;
	}


	public void setDeleteOldFilename(boolean deleteOldFilename) {
		this.deleteOldFilename = deleteOldFilename;
	}
	
	
	
}
