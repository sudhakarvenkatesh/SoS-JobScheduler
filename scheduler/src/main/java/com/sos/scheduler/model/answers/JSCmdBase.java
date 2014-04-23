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
package com.sos.scheduler.model.answers;

import org.apache.log4j.Logger;

import com.sos.scheduler.model.exceptions.JSCommandErrorException;
import com.sos.scheduler.model.exceptions.JSCommandOKException;
import com.sos.scheduler.model.objects.JSObjBase;

/**
* \class JSCmdBase 
* 
* \brief JSCmdBase - 
* 
* \details
*
* \section JSCmdBase.java_intro_sec Introduction
*
* \section JSCmdBase.java_samples Some Samples
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
* \author KB
* @version $Id: JSCmdBase.java 20718 2013-07-18 18:16:10Z kb $
* \see reference
*
* Created on 19.01.2011 13:17:33
 */

/**
 * @author KB
 *
 */
public class JSCmdBase extends JSObjBase {

	private final String		conClassName		= "JSCmdBase";

	@SuppressWarnings("unused")
	private static final Logger	logger				= Logger.getLogger(JSCmdBase.class);
	protected Answer			objAnswer			= null;
	public static boolean		flgRaiseOKException	= true;								// raise an exception if the command was
																							// successfull
	public static boolean		flgLogXML			= true;

	public JSCmdBase() {
		//
	}

	public void run() {
		objAnswer = objFactory.run(this);
	}

	public Answer getAnswer() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getAnswer";

		if (objAnswer == null) {
			objAnswer = new ObjectFactory().createAnswer();
		}

		return objAnswer;
	} // public Answer getAnswer

	public Answer getAnswer(String pXMLStr) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getAnswer";

		objAnswer = objFactory.getAnswer(pXMLStr);
		return objAnswer;
	} // public Answer getAnswer

	public void getAnswerWithException() throws JSCommandErrorException, JSCommandOKException {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getAnswer";

		if (objAnswer == null) {
			objAnswer = new ObjectFactory().createAnswer();
		}
		if (objAnswer != null) {
			Ok objOK = objAnswer.getOk();
			ERROR objERROR = objAnswer.getERROR();
			String errMsg = "unknown error";
			if (objOK != null) {
				if (flgRaiseOKException == true) {
					throw new JSCommandOKException();
				}
			}
			else {
				if (objERROR != null) {
					errMsg = objERROR.getText();
				}
				else {
					throw new JSCommandErrorException(errMsg);
				}
			}
		}
		else {
			throw new JSCommandErrorException("unable to get an instance of Class 'Answer'");
		}

	} // private Answer getAnswer

	/**
	 * 
	 * \brief getERROR
	 * Returns an instance of the ERROR-Object if the last command returned an error 
	 * \details
	 *
	 * \return ERROR
	 *
	 * @return
	 */
	public ERROR getERROR() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getERROR";

		ERROR objError = null;
		if (objAnswer != null) {
			objError = objAnswer.getERROR();
		}

		return objError;
	} // private ERROR getERROR

}
