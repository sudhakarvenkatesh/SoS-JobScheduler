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
package sos.scheduler.file;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Annotations.JSOptionClass;
import com.sos.JSHelper.Annotations.JSOptionDefinition;
import com.sos.JSHelper.Exceptions.JSExceptionMandatoryOptionMissing;
import com.sos.JSHelper.Listener.JSListener;
import com.sos.JSHelper.Options.JSOptionsClass;
import com.sos.JSHelper.Options.SOSOptionBoolean;
import com.sos.JSHelper.Options.SOSOptionFileName;
import com.sos.JSHelper.Options.SOSOptionFileSize;
import com.sos.JSHelper.Options.SOSOptionGracious;
import com.sos.JSHelper.Options.SOSOptionInteger;
import com.sos.JSHelper.Options.SOSOptionJobChainNode;
import com.sos.JSHelper.Options.SOSOptionRegExp;
import com.sos.JSHelper.Options.SOSOptionRelOp;
import com.sos.JSHelper.Options.SOSOptionString;
import com.sos.JSHelper.Options.SOSOptionTime;

/**
 * \class 		JSExistsFileOptionsSuperClass - check wether a file exist
 *
 * \brief 
 * An Options-Super-Class with all Options. This Class will be extended by the "real" Options-class (\see JSExistsFileOptions.
 * The "real" Option class will hold all the things, which are normaly overwritten at a new generation
 * of the super-class.
 *
 *

 *
 * see \see C:\Users\KB\Documents\xmltest\JSExistFile.xml for (more) details.
 * 
 * \verbatim ;
 * mechanicaly created by  from http://www.sos-berlin.com at 20110820120908 
 * \endverbatim
 * \section OptionsTable Tabelle der vorhandenen Optionen
 * 
 * Tabelle mit allen Optionen
 * 
 * MethodName
 * Title
 * Setting
 * Description
 * IsMandatory
 * DataType
 * InitialValue
 * TestValue
 * 
 * 
 *
 * \section TestData Eine Hilfe zum Erzeugen einer HashMap mit Testdaten
 *
 * Die folgenden Methode kann verwendet werden, um f�r einen Test eine HashMap
 * mit sinnvollen Werten f�r die einzelnen Optionen zu erzeugen.
 *
 * \verbatim
 private HashMap <String, String> SetJobSchedulerSSHJobOptions (HashMap <String, String> pobjHM) {
	pobjHM.put ("		JSExistsFileOptionsSuperClass.auth_file", "test");  // This parameter specifies the path and name of a user's pr
		return pobjHM;
  }  //  private void SetJobSchedulerSSHJobOptions (HashMap <String, String> pobjHM)
 * \endverbatim
 */
@JSOptionClass(name = "JSExistsFileOptionsSuperClass", description = "JSExistsFileOptionsSuperClass")
public class JSExistsFileOptionsSuperClass extends JSOptionsClass {
	private static final long	serialVersionUID	= -5168467682765829432L;
	private final String		conClassName		= "JSExistsFileOptionsSuperClass";
	@SuppressWarnings("unused")
	private static Logger		logger				= Logger.getLogger(JSExistsFileOptionsSuperClass.class);

	/**
	 * \var count_files : Return the size of resultset If this parameter is set true " true
	 * 
	 *
	 */
	@JSOptionDefinition(name = "count_files", description = "Return the size of resultset If this parameter is set true ", key = "count_files", type = "SOSOptionBoolean", mandatory = false)
	public SOSOptionBoolean		count_files			= new SOSOptionBoolean(this, conClassName + ".count_files", // HashMap-Key
															"Return the size of resultset If this parameter is set true ", // Titel
															"false", // InitValue
															"false", // DefaultValue
															false // isMandatory
													);

	/**
	 * \brief getcount_files : Return the size of resultset If this parameter is set true " true
	 * 
	 * \details
	 * 
	 *
	 * \return Return the size of resultset If this parameter is set true " true
	 *
	 */
	public SOSOptionBoolean getcount_files() {
		return count_files;
	}

	/**
	 * \brief setcount_files : Return the size of resultset If this parameter is set true " true
	 * 
	 * \details
	 * 
	 *
	 * @param count_files : Return the size of resultset If this parameter is set true " true
	 */
	public void setcount_files(SOSOptionBoolean p_count_files) {
		this.count_files = p_count_files;
	}

	public SOSOptionBoolean	DoCountFiles	= (SOSOptionBoolean) count_files.SetAlias(conClassName + ".DoCountFiles");

	/**
	 * \var create_order : Activate file-order creation With this parameter it is possible to specif
	 * 
	 *
	 */
	@JSOptionDefinition(name = "create_order", description = "Activate file-order creation With this parameter it is possible to specif", key = "create_order", type = "SOSOptionBoolean", mandatory = false)
	public SOSOptionBoolean	create_order	= new SOSOptionBoolean(this, conClassName + ".create_order", // HashMap-Key
													"Activate file-order creation With this parameter it is possible to specif", // Titel
													"false", // InitValue
													"false", // DefaultValue
													false // isMandatory
											);

	/**
	 * \brief getcreate_order : Activate file-order creation With this parameter it is possible to specif
	 * 
	 * \details
	 * 
	 *
	 * \return Activate file-order creation With this parameter it is possible to specif
	 *
	 */
	public SOSOptionBoolean getcreate_order() {
		return create_order;
	}

	/**
	 * \brief setcreate_order : Activate file-order creation With this parameter it is possible to specif
	 * 
	 * \details
	 * 
	 *
	 * @param create_order : Activate file-order creation With this parameter it is possible to specif
	 */
	public void setcreate_order(SOSOptionBoolean p_create_order) {
		this.create_order = p_create_order;
	}

	/**
	 * \var create_orders_for_all_files : Create a file-order for every file in the result-list
	 * 
	 *
	 */
	@JSOptionDefinition(name = "create_orders_for_all_files", description = "Create a file-order for every file in the result-list", key = "create_orders_for_all_files", type = "SOSOptionBoolean", mandatory = false)
	public SOSOptionBoolean	create_orders_for_all_files	= new SOSOptionBoolean(this, conClassName + ".create_orders_for_all_files", // HashMap-Key
																"Create a file-order for every file in the result-list", // Titel
																"false", // InitValue
																"false", // DefaultValue
																false // isMandatory
														);

	/**
	 * \brief getcreate_orders_for_all_files : Create a file-order for every file in the result-list
	 * 
	 * \details
	 * 
	 *
	 * \return Create a file-order for every file in the result-list
	 *
	 */
	public SOSOptionBoolean getcreate_orders_for_all_files() {
		return create_orders_for_all_files;
	}

	/**
	 * \brief setcreate_orders_for_all_files : Create a file-order for every file in the result-list
	 * 
	 * \details
	 * 
	 *
	 * @param create_orders_for_all_files : Create a file-order for every file in the result-list
	 */
	public void setcreate_orders_for_all_files(SOSOptionBoolean p_create_orders_for_all_files) {
		this.create_orders_for_all_files = p_create_orders_for_all_files;
	}

	/**
	 * \var expected_size_of_result_set : number of expected hits in result-list
	 * 
	 *
	 */
	@JSOptionDefinition(name = "expected_size_of_result_set", description = "number of expected hits in result-list", key = "expected_size_of_result_set", type = "SOSOptionInteger", mandatory = false)
	public SOSOptionInteger	expected_size_of_result_set	= new SOSOptionInteger(this, conClassName + ".expected_size_of_result_set", // HashMap-Key
																"number of expected hits in result-list", // Titel
																"0", // InitValue
																"0", // DefaultValue
																false // isMandatory
														);

	/**
	 * \brief getexpected_size_of_result_set : number of expected hits in result-list
	 * 
	 * \details
	 * 
	 *
	 * \return number of expected hits in result-list
	 *
	 */
	public SOSOptionInteger getexpected_size_of_result_set() {
		return expected_size_of_result_set;
	}

	/**
	 * \brief setexpected_size_of_result_set : number of expected hits in result-list
	 * 
	 * \details
	 * 
	 *
	 * @param expected_size_of_result_set : number of expected hits in result-list
	 */
	public void setexpected_size_of_result_set(SOSOptionInteger p_expected_size_of_result_set) {
		this.expected_size_of_result_set = p_expected_size_of_result_set;
	}

	/**
	 * \var file : File or Folder to watch for Checked file or directory Supports
	 * 
	 *
	 */
	@JSOptionDefinition(name = "file", description = "File or Folder to watch for Checked file or directory Supports", key = "file", type = "SOSOptionString", mandatory = true)
	public SOSOptionFileName	file	= new SOSOptionFileName(this, conClassName + ".file", // HashMap-Key
											"File or Folder to watch for Checked file or directory Supports", // Titel
											".", // InitValue
											".", // DefaultValue
											true // isMandatory
									);

	@JSOptionDefinition(name = "target", description = "target or Folder to watch for Checked target or directory Supports", key = "target", type = "SOSOptionString", mandatory = true)
	public SOSOptionFileName	target	= new SOSOptionFileName(this, conClassName + ".target", // HashMap-Key
											"target or Folder to watch for Checked target or directory Supports", // Titel
											".", // InitValue
											".", // DefaultValue
											true // isMandatory
									);

	/**
	 * \brief getfile : File or Folder to watch for Checked file or directory Supports
	 * 
	 * \details
	 * 
	 *
	 * \return File or Folder to watch for Checked file or directory Supports
	 *
	 */
	public SOSOptionFileName getfile() {
		return file;
	}

	/**
	 * \brief setfile : File or Folder to watch for Checked file or directory Supports
	 * 
	 * \details
	 * 
	 *
	 * @param file : File or Folder to watch for Checked file or directory Supports
	 */
	public void setfile(SOSOptionFileName p_file) {
		this.file = p_file;
	}

	public SOSOptionFileName	FileName	= (SOSOptionFileName) file.SetAlias(conClassName + ".FileName");

	/**
	 * \var file_spec : Regular Expression for filename filtering Regular Expression for file fi
	 * 
	 *
	 */
	@JSOptionDefinition(name = "file_spec", description = "Regular Expression for filename filtering Regular Expression for file fi", key = "file_spec", type = "SOSOptionRegExp", mandatory = false)
	public SOSOptionRegExp	file_spec	= new SOSOptionRegExp(this, conClassName + ".file_spec", // HashMap-Key
												"Regular Expression for filename filtering Regular Expression for file fi", // Titel
												".*", // InitValue
												".*", // DefaultValue
												false // isMandatory
										);

	/**
	 * \brief getfile_spec : Regular Expression for filename filtering Regular Expression for file fi
	 * 
	 * \details
	 * 
	 *
	 * \return Regular Expression for filename filtering Regular Expression for file fi
	 *
	 */
	public SOSOptionRegExp getfile_spec() {
		return file_spec;
	}

	/**
	 * \brief setfile_spec : Regular Expression for filename filtering Regular Expression for file fi
	 * 
	 * \details
	 * 
	 *
	 * @param file_spec : Regular Expression for filename filtering Regular Expression for file fi
	 */
	public void setfile_spec(SOSOptionRegExp p_file_spec) {
		this.file_spec = p_file_spec;
	}

	public SOSOptionRegExp		FileNameRegExp	= (SOSOptionRegExp) file_spec.SetAlias(conClassName + ".FileNameRegExp");

	/**
	 * \var gracious : Specify error message tolerance Enables or disables error messages that
	 * 
	 *
	 */
	@JSOptionDefinition(name = "gracious", description = "Specify error message tolerance Enables or disables error messages that", key = "gracious", type = "SOSOptionGracious", mandatory = false)
	public SOSOptionGracious	gracious		= new SOSOptionGracious(this, conClassName + ".gracious", // HashMap-Key
														"Specify error message tolerance Enables or disables error messages that", // Titel
														"false", // InitValue
														"false", // DefaultValue
														false // isMandatory
												);

	/**
	 * \brief getgracious : Specify error message tolerance Enables or disables error messages that
	 * 
	 * \details
	 * 
	 *
	 * \return Specify error message tolerance Enables or disables error messages that
	 *
	 */
	public SOSOptionGracious getgracious() {
		return gracious;
	}

	/**
	 * \brief setgracious : Specify error message tolerance Enables or disables error messages that
	 * 
	 * \details
	 * 
	 *
	 * @param gracious : Specify error message tolerance Enables or disables error messages that
	 */
	public void setgracious(SOSOptionGracious p_gracious) {
		this.gracious = p_gracious;
	}

	public SOSOptionGracious	ErrorBehaviour	= (SOSOptionGracious) gracious.SetAlias(conClassName + ".ErrorBehaviour");

	/**
	 * \var max_file_age : maximum age of a file Specifies the maximum age of a file. If a file
	 * 
	 *
	 */
	@JSOptionDefinition(name = "max_file_age", description = "maximum age of a file Specifies the maximum age of a file. If a file", key = "max_file_age", type = "SOSOptionTime", mandatory = false)
	public SOSOptionTime		max_file_age	= new SOSOptionTime(this, conClassName + ".max_file_age", // HashMap-Key
														"maximum age of a file Specifies the maximum age of a file. If a file", // Titel
														"0", // InitValue
														"0", // DefaultValue
														false // isMandatory
												);

	/**
	 * \brief getmax_file_age : maximum age of a file Specifies the maximum age of a file. If a file
	 * 
	 * \details
	 * 
	 *
	 * \return maximum age of a file Specifies the maximum age of a file. If a file
	 *
	 */
	public SOSOptionTime getmax_file_age() {
		return max_file_age;
	}

	/**
	 * \brief setmax_file_age : maximum age of a file Specifies the maximum age of a file. If a file
	 * 
	 * \details
	 * 
	 *
	 * @param max_file_age : maximum age of a file Specifies the maximum age of a file. If a file
	 */
	public void setmax_file_age(SOSOptionTime p_max_file_age) {
		this.max_file_age = p_max_file_age;
	}

	public SOSOptionTime		FileAgeMaximum	= (SOSOptionTime) max_file_age.SetAlias(conClassName + ".FileAgeMaximum");

	/**
	 * \var max_file_size : maximum size of a file Specifies the maximum size of a file in
	 * Specifies the maximum size of a file in bytes: should the size of one of the files exceed this value, then it is classified as non-existing.
	 *
	 */
	@JSOptionDefinition(name = "max_file_size", description = "maximum size of a file Specifies the maximum size of a file in", key = "max_file_size", type = "SOSOptionFileSize", mandatory = false)
	public SOSOptionFileSize	max_file_size	= new SOSOptionFileSize(this, conClassName + ".max_file_size", // HashMap-Key
														"maximum size of a file Specifies the maximum size of a file in", // Titel
														"-1", // InitValue
														"-1", // DefaultValue
														false // isMandatory
												);

	/**
	 * \brief getmax_file_size : maximum size of a file Specifies the maximum size of a file in
	 * 
	 * \details
	 * Specifies the maximum size of a file in bytes: should the size of one of the files exceed this value, then it is classified as non-existing.
	 *
	 * \return maximum size of a file Specifies the maximum size of a file in
	 *
	 */
	public SOSOptionFileSize getmax_file_size() {
		return max_file_size;
	}

	/**
	 * \brief setmax_file_size : maximum size of a file Specifies the maximum size of a file in
	 * 
	 * \details
	 * Specifies the maximum size of a file in bytes: should the size of one of the files exceed this value, then it is classified as non-existing.
	 *
	 * @param max_file_size : maximum size of a file Specifies the maximum size of a file in
	 */
	public void setmax_file_size(SOSOptionFileSize p_max_file_size) {
		this.max_file_size = p_max_file_size;
	}

	public SOSOptionFileSize	FileSizeMaximum	= (SOSOptionFileSize) max_file_size.SetAlias(conClassName + ".FileSizeMaximum");

	/**
	 * \var min_file_age : minimum age of a file Specifies the minimum age of a files. If the fi
	 * Specifies the minimum age of a files. If the file(s) is newer then it is classified as non-existing, it will be not included in the result-list.
	 *
	 */
	@JSOptionDefinition(name = "min_file_age", description = "minimum age of a file Specifies the minimum age of a files. If the fi", key = "min_file_age", type = "SOSOptionTime", mandatory = false)
	public SOSOptionTime		min_file_age	= new SOSOptionTime(this, conClassName + ".min_file_age", // HashMap-Key
														"minimum age of a file Specifies the minimum age of a files. If the fi", // Titel
														"0", // InitValue
														"0", // DefaultValue
														false // isMandatory
												);

	/**
	 * \brief getmin_file_age : minimum age of a file Specifies the minimum age of a files. If the fi
	 * 
	 * \details
	 * Specifies the minimum age of a files. If the file(s) is newer then it is classified as non-existing, it will be not included in the result-list.
	 *
	 * \return minimum age of a file Specifies the minimum age of a files. If the fi
	 *
	 */
	public SOSOptionTime getmin_file_age() {
		return min_file_age;
	}

	/**
	 * \brief setmin_file_age : minimum age of a file Specifies the minimum age of a files. If the fi
	 * 
	 * \details
	 * Specifies the minimum age of a files. If the file(s) is newer then it is classified as non-existing, it will be not included in the result-list.
	 *
	 * @param min_file_age : minimum age of a file Specifies the minimum age of a files. If the fi
	 */
	public void setmin_file_age(SOSOptionTime p_min_file_age) {
		this.min_file_age = p_min_file_age;
	}

	public SOSOptionTime		FileAgeMinimum	= (SOSOptionTime) min_file_age.SetAlias(conClassName + ".FileAgeMinimum");

	/**
	 * \var min_file_size : minimum size of one or multiple files Specifies the minimum size of one
	 * 
	 *
	 */
	@JSOptionDefinition(name = "min_file_size", description = "minimum size of one or multiple files Specifies the minimum size of one", key = "min_file_size", type = "SOSOptionFileSize", mandatory = false)
	public SOSOptionFileSize	min_file_size	= new SOSOptionFileSize(this, conClassName + ".min_file_size", // HashMap-Key
														"minimum size of one or multiple files Specifies the minimum size of one", // Titel
														"-1", // InitValue
														"-1", // DefaultValue
														false // isMandatory
												);

	/**
	 * \brief getmin_file_size : minimum size of one or multiple files Specifies the minimum size of one
	 * 
	 * \details
	 * 
	 *
	 * \return minimum size of one or multiple files Specifies the minimum size of one
	 *
	 */
	public SOSOptionFileSize getmin_file_size() {
		return min_file_size;
	}

	/**
	 * \brief setmin_file_size : minimum size of one or multiple files Specifies the minimum size of one
	 * 
	 * \details
	 * 
	 *
	 * @param min_file_size : minimum size of one or multiple files Specifies the minimum size of one
	 */
	public void setmin_file_size(SOSOptionFileSize p_min_file_size) {
		this.min_file_size = p_min_file_size;
	}

	public SOSOptionFileSize		FileSizeMinimum	= (SOSOptionFileSize) min_file_size.SetAlias(conClassName + ".FileSizeMinimum");

	/**
	 * \var next_state : The first node to execute in a jobchain The name of the node of a jobchai
	 * 
	 *
	 */
	@JSOptionDefinition(name = "next_state", description = "The first node to execute in a jobchain The name of the node of a jobchai", key = "next_state", type = "SOSOptionJobChainNode", mandatory = false)
	public SOSOptionJobChainNode	next_state		= new SOSOptionJobChainNode(this, conClassName + ".next_state", // HashMap-Key
															"The first node to execute in a jobchain The name of the node of a jobchai", // Titel
															" ", // InitValue
															" ", // DefaultValue
															false // isMandatory
													);

	/**
	 * \brief getnext_state : The first node to execute in a jobchain The name of the node of a jobchai
	 * 
	 * \details
	 * 
	 *
	 * \return The first node to execute in a jobchain The name of the node of a jobchai
	 *
	 */
	public SOSOptionJobChainNode getnext_state() {
		return next_state;
	}

	/**
	 * \brief setnext_state : The first node to execute in a jobchain The name of the node of a jobchai
	 * 
	 * \details
	 * 
	 *
	 * @param next_state : The first node to execute in a jobchain The name of the node of a jobchai
	 */
	public void setnext_state(SOSOptionJobChainNode p_next_state) {
		this.next_state = p_next_state;
	}

	/**
	 * \var on_empty_result_set : Set next node on empty result set The next Node (Step, Job) to execute i
	 * 
	 *
	 */
	@JSOptionDefinition(name = "on_empty_result_set", description = "Set next node on empty result set The next Node (Step, Job) to execute i", key = "on_empty_result_set", type = "SOSOptionJobChainNode", mandatory = false)
	public SOSOptionJobChainNode	on_empty_result_set	= new SOSOptionJobChainNode(this, conClassName + ".on_empty_result_set", // HashMap-Key
																"Set next node on empty result set The next Node (Step, Job) to execute i", // Titel
																"", // InitValue
																"", // DefaultValue
																false // isMandatory
														);

	/**
	 * \brief geton_empty_result_set : Set next node on empty result set The next Node (Step, Job) to execute i
	 * 
	 * \details
	 * 
	 *
	 * \return Set next node on empty result set The next Node (Step, Job) to execute i
	 *
	 */
	public SOSOptionJobChainNode geton_empty_result_set() {
		return on_empty_result_set;
	}

	/**
	 * \brief seton_empty_result_set : Set next node on empty result set The next Node (Step, Job) to execute i
	 * 
	 * \details
	 * 
	 *
	 * @param on_empty_result_set : Set next node on empty result set The next Node (Step, Job) to execute i
	 */
	public void seton_empty_result_set(SOSOptionJobChainNode p_on_empty_result_set) {
		this.on_empty_result_set = p_on_empty_result_set;
	}

	/**
	 * \var order_jobchain_name : The name of the jobchain which belongs to the order The name of the jobch
	 * 
	 *
	 */
	@JSOptionDefinition(name = "order_jobchain_name", description = "The name of the jobchain which belongs to the order The name of the jobch", key = "order_jobchain_name", type = "SOSOptionString", mandatory = false)
	public SOSOptionString	order_jobchain_name	= new SOSOptionString(this, conClassName + ".order_jobchain_name", // HashMap-Key
														"The name of the jobchain which belongs to the order The name of the jobch", // Titel
														" ", // InitValue
														" ", // DefaultValue
														false // isMandatory
												);

	/**
	 * \brief getorder_jobchain_name : The name of the jobchain which belongs to the order The name of the jobch
	 * 
	 * \details
	 * 
	 *
	 * \return The name of the jobchain which belongs to the order The name of the jobch
	 *
	 */
	public SOSOptionString getorder_jobchain_name() {
		return order_jobchain_name;
	}

	/**
	 * \brief setorder_jobchain_name : The name of the jobchain which belongs to the order The name of the jobch
	 * 
	 * \details
	 * 
	 *
	 * @param order_jobchain_name : The name of the jobchain which belongs to the order The name of the jobch
	 */
	public void setorder_jobchain_name(SOSOptionString p_order_jobchain_name) {
		this.order_jobchain_name = p_order_jobchain_name;
	}

	/**
	 * \var raise_error_if_result_set_is : raise error on expected size of result-set With this parameter it is poss
	 * 
	 *
	 */
	@JSOptionDefinition(name = "raise_error_if_result_set_is", description = "raise error on expected size of result-set With this parameter it is poss", key = "raise_error_if_result_set_is", type = "SOSOptionRelOp", mandatory = false)
	public SOSOptionRelOp	raise_error_if_result_set_is	= new SOSOptionRelOp(this, conClassName + ".raise_error_if_result_set_is", // HashMap-Key
																	"raise error on expected size of result-set With this parameter it is poss", // Titel
																	"", // InitValue
																	"", // DefaultValue
																	false // isMandatory
															);

	/**
	 * \brief getraise_error_if_result_set_is : raise error on expected size of result-set With this parameter it is poss
	 * 
	 * \details
	 * 
	 *
	 * \return raise error on expected size of result-set With this parameter it is poss
	 *
	 */
	public SOSOptionRelOp getraise_error_if_result_set_is() {
		return raise_error_if_result_set_is;
	}

	/**
	 * \brief setraise_error_if_result_set_is : raise error on expected size of result-set With this parameter it is poss
	 * 
	 * \details
	 * 
	 *
	 * @param raise_error_if_result_set_is : raise error on expected size of result-set With this parameter it is poss
	 */
	public void setraise_error_if_result_set_is(SOSOptionRelOp p_raise_error_if_result_set_is) {
		this.raise_error_if_result_set_is = p_raise_error_if_result_set_is;
	}

	/**
	 * \var result_list_file : Name of the result-list file If the value of this parameter specifies a v
	 * 
	 *
	 */
	@JSOptionDefinition(name = "result_list_file", description = "Name of the result-list file If the value of this parameter specifies a v", key = "result_list_file", type = "SOSOptionFileName", mandatory = false)
	public SOSOptionFileName	result_list_file	= new SOSOptionFileName(this, conClassName + ".result_list_file", // HashMap-Key
															"Name of the result-list file If the value of this parameter specifies a v", // Titel
															"", // InitValue
															"", // DefaultValue
															false // isMandatory
													);

	/**
	 * \brief getresult_list_file : Name of the result-list file If the value of this parameter specifies a v
	 * 
	 * \details
	 * 
	 *
	 * \return Name of the result-list file If the value of this parameter specifies a v
	 *
	 */
	public SOSOptionFileName getresult_list_file() {
		return result_list_file;
	}

	/**
	 * \brief setresult_list_file : Name of the result-list file If the value of this parameter specifies a v
	 * 
	 * \details
	 * 
	 *
	 * @param result_list_file : Name of the result-list file If the value of this parameter specifies a v
	 */
	public void setresult_list_file(SOSOptionFileName p_result_list_file) {
		this.result_list_file = p_result_list_file;
	}

	/**
	 * \var scheduler_file_name : Name of the file to process for a file-order
	 * 
	 *
	 */
	@JSOptionDefinition(name = "scheduler_file_name", description = "Name of the file to process for a file-order", key = "scheduler_file_name", type = "SOSOptionFileName", mandatory = false)
	public SOSOptionFileName	scheduler_file_name	= new SOSOptionFileName(this, conClassName + ".scheduler_file_name", // HashMap-Key
															"Name of the file to process for a file-order", // Titel
															"", // InitValue
															"", // DefaultValue
															false // isMandatory
													);

	/**
	 * \brief getscheduler_file_name : Name of the file to process for a file-order
	 * 
	 * \details
	 * 
	 *
	 * \return Name of the file to process for a file-order
	 *
	 */
	public SOSOptionFileName getscheduler_file_name() {
		return scheduler_file_name;
	}

	/**
	 * \brief setscheduler_file_name : Name of the file to process for a file-order
	 * 
	 * \details
	 * 
	 *
	 * @param scheduler_file_name : Name of the file to process for a file-order
	 */
	public void setscheduler_file_name(SOSOptionFileName p_scheduler_file_name) {
		this.scheduler_file_name = p_scheduler_file_name;
	}

	/**
	 * \var scheduler_file_parent : pathanme of the file to process for a file-order
	 * 
	 *
	 */
	@JSOptionDefinition(name = "scheduler_file_parent", description = "pathanme of the file to process for a file-order", key = "scheduler_file_parent", type = "SOSOptionFileName", mandatory = false)
	public SOSOptionFileName	scheduler_file_parent	= new SOSOptionFileName(this, conClassName + ".scheduler_file_parent", // HashMap-Key
																"pathanme of the file to process for a file-order", // Titel
																"", // InitValue
																"", // DefaultValue
																false // isMandatory
														);

	/**
	 * \brief getscheduler_file_parent : pathanme of the file to process for a file-order
	 * 
	 * \details
	 * 
	 *
	 * \return pathanme of the file to process for a file-order
	 *
	 */
	public SOSOptionFileName getscheduler_file_parent() {
		return scheduler_file_parent;
	}

	/**
	 * \brief setscheduler_file_parent : pathanme of the file to process for a file-order
	 * 
	 * \details
	 * 
	 *
	 * @param scheduler_file_parent : pathanme of the file to process for a file-order
	 */
	public void setscheduler_file_parent(SOSOptionFileName p_scheduler_file_parent) {
		this.scheduler_file_parent = p_scheduler_file_parent;
	}

	/**
	 * \var scheduler_file_path : file to process for a file-order Using Directory Monitoring with
	 * 
	 *
	 */
	@JSOptionDefinition(name = "scheduler_file_path", description = "file to process for a file-order Using Directory Monitoring with", key = "scheduler_file_path", type = "SOSOptionFileName", mandatory = false)
	public SOSOptionFileName	scheduler_file_path	= new SOSOptionFileName(this, conClassName + ".scheduler_file_path", // HashMap-Key
															"file to process for a file-order Using Directory Monitoring with", // Titel
															"", // InitValue
															"", // DefaultValue
															false // isMandatory
													);

	/**
	 * \brief getscheduler_file_path : file to process for a file-order Using Directory Monitoring with
	 * 
	 * \details
	 * 
	 *
	 * \return file to process for a file-order Using Directory Monitoring with
	 *
	 */
	public SOSOptionFileName getscheduler_file_path() {
		return scheduler_file_path;
	}

	/**
	 * \brief setscheduler_file_path : file to process for a file-order Using Directory Monitoring with
	 * 
	 * \details
	 * 
	 *
	 * @param scheduler_file_path : file to process for a file-order Using Directory Monitoring with
	 */
	public void setscheduler_file_path(SOSOptionFileName p_scheduler_file_path) {
		this.scheduler_file_path = p_scheduler_file_path;
	}

	/**
	 * \var scheduler_sosfileoperations_file_count : Return the size of the result set after a file operation
	 * 
	 *
	 */
	@JSOptionDefinition(name = "scheduler_sosfileoperations_file_count", description = "Return the size of the result set after a file operation", key = "scheduler_sosfileoperations_file_count", type = "SOSOptionInteger", mandatory = false)
	public SOSOptionInteger	scheduler_sosfileoperations_file_count	= new SOSOptionInteger(this, conClassName + ".scheduler_sosfileoperations_file_count", // HashMap-Key
																			"Return the size of the result set after a file operation", // Titel
																			"0", // InitValue
																			"0", // DefaultValue
																			false // isMandatory
																	);

	/**
	 * \brief getscheduler_sosfileoperations_file_count : Return the size of the result set after a file operation
	 * 
	 * \details
	 * 
	 *
	 * \return Return the size of the result set after a file operation
	 *
	 */
	public SOSOptionInteger getscheduler_sosfileoperations_file_count() {
		return scheduler_sosfileoperations_file_count;
	}

	/**
	 * \brief setscheduler_sosfileoperations_file_count : Return the size of the result set after a file operation
	 * 
	 * \details
	 * 
	 *
	 * @param scheduler_sosfileoperations_file_count : Return the size of the result set after a file operation
	 */
	public void setscheduler_sosfileoperations_file_count(SOSOptionInteger p_scheduler_sosfileoperations_file_count) {
		this.scheduler_sosfileoperations_file_count = p_scheduler_sosfileoperations_file_count;
	}

	public SOSOptionInteger	FileCount								= (SOSOptionInteger) scheduler_sosfileoperations_file_count.SetAlias(conClassName
																			+ ".FileCount");

	/**
	 * \var scheduler_sosfileoperations_resultset : The result of the operation as a list of items
	 * 
	 *
	 */
	@JSOptionDefinition(name = "scheduler_sosfileoperations_resultset", description = "The result of the operation as a list of items", key = "scheduler_sosfileoperations_resultset", type = "SOSOptionstring", mandatory = false)
	public SOSOptionString	scheduler_sosfileoperations_resultset	= new SOSOptionString(this, conClassName + ".scheduler_sosfileoperations_resultset", // HashMap-Key
																			"The result of the operation as a list of items", // Titel
																			"", // InitValue
																			"", // DefaultValue
																			false // isMandatory
																	);

	/**
	 * \brief getscheduler_sosfileoperations_resultset : The result of the operation as a list of items
	 * 
	 * \details
	 * 
	 *
	 * \return The result of the operation as a list of items
	 *
	 */
	public SOSOptionString getscheduler_sosfileoperations_resultset() {
		return scheduler_sosfileoperations_resultset;
	}

	/**
	 * \brief setscheduler_sosfileoperations_resultset : The result of the operation as a list of items
	 * 
	 * \details
	 * 
	 *
	 * @param scheduler_sosfileoperations_resultset : The result of the operation as a list of items
	 */
	public void setscheduler_sosfileoperations_resultset(SOSOptionString p_scheduler_sosfileoperations_resultset) {
		this.scheduler_sosfileoperations_resultset = p_scheduler_sosfileoperations_resultset;
	}

	public SOSOptionString	ResultSet									= (SOSOptionString) scheduler_sosfileoperations_resultset.SetAlias(conClassName
																				+ ".ResultSet");

	/**
	 * \var scheduler_sosfileoperations_resultsetsize : The amount of hits in the result set of the operation
	 * 
	 *
	 */
	@JSOptionDefinition(name = "scheduler_sosfileoperations_resultsetsize", description = "The amount of hits in the result set of the operation", key = "scheduler_sosfileoperations_resultsetsize", type = "SOSOptionsInteger", mandatory = false)
	public SOSOptionInteger	scheduler_sosfileoperations_resultsetsize	= new SOSOptionInteger(this, conClassName
																				+ ".scheduler_sosfileoperations_resultsetsize", // HashMap-Key
																				"The amount of hits in the result set of the operation", // Titel
																				"", // InitValue
																				"", // DefaultValue
																				false // isMandatory
																		);

	/**
	 * \brief getscheduler_sosfileoperations_resultsetsize : The amount of hits in the result set of the operation
	 * 
	 * \details
	 * 
	 *
	 * \return The amount of hits in the result set of the operation
	 *
	 */
	public SOSOptionInteger getscheduler_sosfileoperations_resultsetsize() {
		return scheduler_sosfileoperations_resultsetsize;
	}

	/**
	 * \brief setscheduler_sosfileoperations_resultsetsize : The amount of hits in the result set of the operation
	 * 
	 * \details
	 * 
	 *
	 * @param scheduler_sosfileoperations_resultsetsize : The amount of hits in the result set of the operation
	 */
	public void setscheduler_sosfileoperations_resultsetsize(SOSOptionInteger p_scheduler_sosfileoperations_resultsetsize) {
		this.scheduler_sosfileoperations_resultsetsize = p_scheduler_sosfileoperations_resultsetsize;
	}

	public SOSOptionInteger	ResultSetSize		= (SOSOptionInteger) scheduler_sosfileoperations_resultsetsize.SetAlias(conClassName + ".ResultSetSize");

	/**
	 * \var skip_first_files : number of files to remove from the top of the result-set The numbe
	 * 
	 *
	 */
	@JSOptionDefinition(name = "skip_first_files", description = "number of files to remove from the top of the result-set The numbe", key = "skip_first_files", type = "SOSOptionInteger", mandatory = false)
	public SOSOptionInteger	skip_first_files	= new SOSOptionInteger(this, conClassName + ".skip_first_files", // HashMap-Key
														"number of files to remove from the top of the result-set The numbe", // Titel
														"0", // InitValue
														"0", // DefaultValue
														false // isMandatory
												);

	/**
	 * \brief getskip_first_files : number of files to remove from the top of the result-set The numbe
	 * 
	 * \details
	 * 
	 *
	 * \return number of files to remove from the top of the result-set The numbe
	 *
	 */
	public SOSOptionInteger getskip_first_files() {
		return skip_first_files;
	}

	/**
	 * \brief setskip_first_files : number of files to remove from the top of the result-set The numbe
	 * 
	 * \details
	 * 
	 *
	 * @param skip_first_files : number of files to remove from the top of the result-set The numbe
	 */
	public void setskip_first_files(SOSOptionInteger p_skip_first_files) {
		this.skip_first_files = p_skip_first_files;
	}

	public SOSOptionInteger	NoOfFirstFiles2Skip	= (SOSOptionInteger) skip_first_files.SetAlias(conClassName + ".NoOfFirstFiles2Skip");

	/**
	 * \var skip_last_files : number of files to remove from the bottom of the result-set The numbe
	 * 
	 *
	 */
	@JSOptionDefinition(name = "skip_last_files", description = "number of files to remove from the bottom of the result-set The numbe", key = "skip_last_files", type = "SOSOptionInteger", mandatory = false)
	public SOSOptionInteger	skip_last_files		= new SOSOptionInteger(this, conClassName + ".skip_last_files", // HashMap-Key
														"number of files to remove from the bottom of the result-set The numbe", // Titel
														"0", // InitValue
														"0", // DefaultValue
														false // isMandatory
												);

	/**
	 * \brief getskip_last_files : number of files to remove from the bottom of the result-set The numbe
	 * 
	 * \details
	 * 
	 *
	 * \return number of files to remove from the bottom of the result-set The numbe
	 *
	 */
	public SOSOptionInteger getskip_last_files() {
		return skip_last_files;
	}

	/**
	 * \brief setskip_last_files : number of files to remove from the bottom of the result-set The numbe
	 * 
	 * \details
	 * 
	 *
	 * @param skip_last_files : number of files to remove from the bottom of the result-set The numbe
	 */
	public void setskip_last_files(SOSOptionInteger p_skip_last_files) {
		this.skip_last_files = p_skip_last_files;
	}

	public SOSOptionInteger	NoOfLastFiles2Skip	= (SOSOptionInteger) skip_last_files.SetAlias(conClassName + ".NoOfLastFiles2Skip");

	public JSExistsFileOptionsSuperClass() {
		objParentClass = this.getClass();
	} // public JSExistsFileOptionsSuperClass

	public JSExistsFileOptionsSuperClass(JSListener pobjListener) {
		this();
		this.registerMessageListener(pobjListener);
	} // public JSExistsFileOptionsSuperClass

	//

	public JSExistsFileOptionsSuperClass(HashMap<String, String> JSSettings) throws Exception {
		this();
		this.setAllOptions(JSSettings);
	} // public JSExistsFileOptionsSuperClass (HashMap JSSettings)

	/**
	 * \brief getAllOptionsAsString - liefert die Werte und Beschreibung aller
	 * Optionen als String
	 *
	 * \details
	 * 
	 * \see toString 
	 * \see toOut
	 */
	private String getAllOptionsAsString() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getAllOptionsAsString";
		String strT = conClassName + "\n";
		final StringBuffer strBuffer = new StringBuffer();
		// strT += IterateAllDataElementsByAnnotation(objParentClass, this,
		// JSOptionsClass.IterationTypes.toString, strBuffer);
		// strT += IterateAllDataElementsByAnnotation(objParentClass, this, 13,
		// strBuffer);
		strT += this.toString(); // fix
		//
		return strT;
	} // private String getAllOptionsAsString ()

	/**
	 * \brief setAllOptions - �bernimmt die OptionenWerte aus der HashMap
	 *
	 * \details In der als Parameter anzugebenden HashMap sind Schl�ssel (Name)
	 * und Wert der jeweiligen Option als Paar angegeben. Ein Beispiel f�r den
	 * Aufbau einer solchen HashMap findet sich in der Beschreibung dieser
	 * Klasse (\ref TestData "setJobSchedulerSSHJobOptions"). In dieser Routine
	 * werden die Schl�ssel analysiert und, falls gefunden, werden die
	 * dazugeh�rigen Werte den Properties dieser Klasse zugewiesen.
	 *
	 * Nicht bekannte Schl�ssel werden ignoriert.
	 *
	 * \see JSOptionsClass::getItem
	 *
	 * @param pobjJSSettings
	 * @throws Exception
	 */
	public void setAllOptions(HashMap<String, String> pobjJSSettings) throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::setAllOptions";
		flgSetAllOptions = true;
		objSettings = pobjJSSettings;
		super.Settings(objSettings);
		super.setAllOptions(pobjJSSettings);
		flgSetAllOptions = false;
	} // public void setAllOptions (HashMap <String, String> JSSettings)

	/**
	 * \brief CheckMandatory - pr�ft alle Muss-Optionen auf Werte
	 *
	 * \details
	 * @throws Exception
	 *
	 * @throws Exception
	 * - wird ausgel�st, wenn eine mandatory-Option keinen Wert hat
	 */
	@Override
	public void CheckMandatory() throws JSExceptionMandatoryOptionMissing //
			, Exception {
		try {
			super.CheckMandatory();
		}
		catch (Exception e) {
			throw new JSExceptionMandatoryOptionMissing(e.toString());
		}
	} // public void CheckMandatory ()

	/**
	 *
	 * \brief CommandLineArgs - �bernehmen der Options/Settings aus der
	 * Kommandozeile
	 *
	 * \details Die in der Kommandozeile beim Starten der Applikation
	 * angegebenen Parameter werden hier in die HashMap �bertragen und danach
	 * den Optionen als Wert zugewiesen.
	 *
	 * \return void
	 *
	 * @param pstrArgs
	 * @throws Exception
	 */
	@Override
	public void CommandLineArgs(String[] pstrArgs) throws Exception {
		super.CommandLineArgs(pstrArgs);
		this.setAllOptions(super.objSettings);
	}
} // public class JSExistsFileOptionsSuperClass
