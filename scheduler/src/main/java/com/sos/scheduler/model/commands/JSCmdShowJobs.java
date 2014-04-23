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
package com.sos.scheduler.model.commands;

import org.apache.log4j.Logger;

import com.sos.scheduler.model.SchedulerObjectFactory;
import com.sos.scheduler.model.SchedulerObjectFactory.enu4What;

/**
* \class JSCmdShowJobs 
* 
* \brief JSCmdShowJobs - 
* 
* \details
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
* @version $Id: JSCmdShowJobs.java 20718 2013-07-18 18:16:10Z kb $18.01.2011
* \see reference
*
* Created on 18.01.2011 18:28:20
 */

public class JSCmdShowJobs extends ShowJobs {

	private final String		conClassName	= "JSCmdShowJobs";
	@SuppressWarnings("unused")
	private static final Logger	logger			= Logger.getLogger(JSCmdShowJobs.class);

	public JSCmdShowJobs(SchedulerObjectFactory schedulerObjectFactory) {
		super();
		objFactory = schedulerObjectFactory;
	}
 
	public com.sos.scheduler.model.answers.Jobs getJobs() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getJobs";
		com.sos.scheduler.model.answers.Jobs objJobs = null;
		objJobs = this.getAnswer().getJobs();
		objJobs.setParent(objFactory);
		return objJobs;
	} // private Calendar getJobs

	/**
	 * 
	 * \brief setWhat
	 * 
	 * \details
	 *
	 * @param penuT
	 */
	public void setWhat(enu4What penuT) {
	
		@SuppressWarnings("unused")
		final String	conMethodName	= conClassName + "::setWhat";
	
		super.setWhat(penuT.Text()); 
	
	} // private void setWhat

	/**
	 * 
	 * \brief setWhat
	 * 
	 * \details
	 *
	 * @param penuT
	 */
	public void setWhat(enu4What... penuT) {
		
		@SuppressWarnings("unused")
		final String	conMethodName	= conClassName + "::setWhat";
		
		String strT = "";
		for (enu4What enuState4What : penuT) {
			strT += enuState4What.Text() + " ";
		}
		super.setWhat(strT); 
		
	} // private void setWhat

	
}
