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
package sos.net.ssh;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Logging.Log4JHelper;
import com.sos.i18n.annotation.I18NResourceBundle;

/**
 * \class SOSSSHJob2Main - Start a Script using SSH
 * 
 * \brief AdapterClass of SOSSSHJob2Main for the SOSJobScheduler
 * 
 * Start a Script using SSH
 * 
 * This Class SOSSSHJob2Main works as a root-class for the worker-class \ref
 * SOSSSHJob2.
 * 
 * \section
 * 
 * \see
 * 
 * \code 
 * .... code goes here ... 
 * \endcode
 * 
 * \author Klaus Buettner - http://www.sos-berlin.com 
* @version $Id$1.1.0.20100506
 * 
 * This Source-Code was created by JETTemplate SOSJobSchedulerMainclass.javajet,
 * Version 1.0 from 2009-12-26, written by kb
 */

@I18NResourceBundle(baseName = "com_sos_net_messages", defaultLocale = "en")
public class SOSSSHJob2Main {

	private final static String	conClassName	= "SOSSSHJob2Main";

	private static Logger		logger			= null;
	@SuppressWarnings("unused")
	private static Log4JHelper	objLogger		= null;

	/**
	 * 
	 * \brief main
	 * 
	 * \details
	 *
	 * \return void
	 *
	 * @param pstrArgs
	 * @throws Exception
	 */
	public final static void main(final String[] pstrArgs) throws Exception {

		final String conMethodName = conClassName + "::Main"; //$NON-NLS-1$

		// TODO �ber eine Option steuern. Die auch in die Standard-Option-Class aufnehmen
		objLogger = new Log4JHelper("./log4j.properties"); //$NON-NLS-1$

		logger = Logger.getRootLogger();
		logger.info("SOSSSHJob2 - Main"); //$NON-NLS-1$
		logger.info("User-Dir : " + System.getProperty("user.dir"));   //$NON-NLS-1$

		try {
			SOSSSHJob2 objM = new SOSSSHJob2();
			SOSSSHJobOptions objO = objM.Options();
			
			objO.CommandLineArgs(pstrArgs);
			objM.Execute();
		}
		catch (Exception e) {
			System.out.println(conMethodName + ": " + "Error occured ..." + e.getMessage()); 
			e.printStackTrace();
			// TODO check exitcode from SSH Server. if not zero take the exit-code from the server instead of 99
			logger.info(conMethodName + ": terminated with exit-code 99");		
			System.exit(99);
		}
		
		logger.info(conMethodName + ": terminated without errors");		
	}

	/**
	 * 
	 * \brief setOptions
	 * 
	 * \details
	 *
	 * \return HashMap<String,String>
	 *
	 * @return
	 */
	@SuppressWarnings("unused")
	private HashMap<String, String> setOptions() {

		final String conMethodName = conClassName + "::setOptions";

		String conClassName = "SOSSSHJobOptions";
		HashMap<String, String> mapSettings = new HashMap<String, String>();
		String strKeyPrefix = conClassName + ".";

		mapSettings.put(strKeyPrefix + "UserName", "TestUser");

		return mapSettings;
	}

} // SOSSSHJob2Main
