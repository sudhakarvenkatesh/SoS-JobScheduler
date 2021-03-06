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
package com.sos.JSHelper.Options;

import java.io.IOException;
import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;

import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.JSHelper.Logging.SOSHtmlLayout;
import com.sos.JSHelper.io.Files.JSTextFile;

/**
* \class SOSOptionLogFileName
*
* \brief SOSOptionLogFileName -
*
* \details
* This class is to implement an adapter to change the logging file name for a log4j configuration.
*
* \code
*   .... code goes here ...
* \endcode
*
* <p style="text-align:center">
* <br />---------------------------------------------------------------------------
* <br /> APL/Software GmbH - Berlin
* <br />##### generated by ClaviusXPress (http://www.sos-berlin.com) #########
* <br />---------------------------------------------------------------------------
* </p>
* \author eqbfd
* @version $Id: SOSOptionOutFileName.java 14729 2011-07-05 20:47:16Z sos $23.01.2009
* \see reference
*
* Created on 23.01.2009 17:00:04
 */

/**
 * @author eqbfd
 *
 */
public class SOSOptionLogFileName extends SOSOptionOutFileName {

	private static final long	serialVersionUID	= 144340120069043974L;
	@SuppressWarnings("unused")
	private final String		conClassName		= "JSOptionOutFileName";
	private Logger				logger				= Logger.getLogger(getClass());
	private FileAppender		objFileAppender		= null;

	/**
	 * \brief SOSOptionLogFileName
	 *
	 * \details
	 *
	 * @param pPobjParent
	 * @param pPstrKey
	 * @param pPstrDescription
	 * @param pPstrValue
	 * @param pPstrDefaultValue
	 * @param pPflgIsMandatory
	 * @throws Exception
	 */
	public SOSOptionLogFileName(final JSOptionsClass pPobjParent, final String pPstrKey, final String pPstrDescription, final String pPstrValue, final String pPstrDefaultValue,
			final boolean pPflgIsMandatory) {
		super(pPobjParent, pPstrKey, pPstrDescription, pPstrValue, pPstrDefaultValue, pPflgIsMandatory);
		// TODO Auto-generated constructor stub
	}

	private String	strHtmlLogFile	= "";

	public String getHtmlLogFileName() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getHtmlLogFileName";

		if (isEmpty(strHtmlLogFile) == false) {
			return strHtmlLogFile;
		}
		else {
			return "";
		}

		//	return String;
	} // private String getHtmlLogFileName

	public void resetHTMLEntities() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::resetHTMLEntities";

		if (isNotEmpty(strHtmlLogFile) == true && objFileAppender != null) {
			objFileAppender.close();
			JSTextFile objF = new JSTextFile(strHtmlLogFile);
			try {
				objF.replaceString("&lt;", "<");
				objF.replaceString("&gt;", ">");
			}
			catch (IOException e) {
			}
		}

		// return String;
	} // private String resetHTMLEntities

	public String getContent() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getContent";

		String strContent = "";

		return strContent;
	} // private String getContent

	public void setLogger(final Logger pobjLogger) {
		if (pobjLogger != null && this.isDirty()) {
			try {
				logger = pobjLogger;
				@SuppressWarnings("rawtypes")
				Enumeration appenders = pobjLogger.getAllAppenders();
				objFileAppender = null;
				while (appenders.hasMoreElements()) {
					Appender currAppender = (Appender) appenders.nextElement();
					if (currAppender != null) {
						if (currAppender instanceof FileAppender || currAppender instanceof RollingFileAppender) {
							objFileAppender = (FileAppender) currAppender;
							if (objFileAppender != null) {
								String strLogFileName = this.Value();
								if (objFileAppender.getLayout() instanceof SOSHtmlLayout) {
									if (isNotNull(objParentClass)) {
										/**
										 * This is a dirty trick:
										 * get the optionname by name will check, wether the option is present.
										 * if not, the title will not changed
										 * This coding below, with profile and settings, is for JADE
										 */
										String strProfile = objParentClass.OptionByName("profile");
										if (isNotEmpty(strProfile)) {
											String strSettings = objParentClass.OptionByName("settings");
											if (isNotEmpty(strSettings)) {
												strSettings += ":";
											}
											else {
												strSettings = "";
											}
											SOSHtmlLayout objLayout = (SOSHtmlLayout) objFileAppender.getLayout();
											String strTitle = objLayout.getTitle();
											objLayout.setTitle("[" + strSettings + strProfile + "] - " + strTitle);

										}
									}
									strLogFileName = strLogFileName + ".html";
									objFileAppender.setFile(strLogFileName);
									logger.debug(Messages.getMsg("%2$s: filename changed to '%1$s'", strLogFileName, "log4J.HTMLLayout"));
									strHtmlLogFile = strLogFileName;
								}
								else {
									objFileAppender.setFile(strLogFileName);
									logger.debug(Messages.getMsg("%2$s: filename changed to '%1$s'", strLogFileName, "log4J.FileAppender"));
								}
								objFileAppender.activateOptions();
							}
						}
					}
				}
				if (objFileAppender == null) {
					logger.info("No File Appender found");
				}
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new JobSchedulerException("Problems with log4jappender", e);
			}
		}
		else {
			logger.info("setLogger without instance of logger called.");
		}
	}

}
