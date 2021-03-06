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
package com.sos.JSHelper.io.Files;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
* \class SOSProfileSection 
* 
* \brief SOSProfileSection - 
* 
* \details
*
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
* \version $Id$
* \see reference
*
* Created on 26.08.2011 20:57:21
 */

/**
 * @author KB
 *
 */
public class SOSProfileSection {

	@SuppressWarnings("unused")
	private final String		conClassName	= "SOSProfileSection";
	private static final String	conSVNVersion	= "$Id$";
	private static final Logger	logger			= Logger.getLogger(SOSProfileSection.class);

	public SOSProfileSection() {
		//
	}

	public String						strSectionName;
	public Map<String, SOSProfileEntry>	mapEntries;

	public SOSProfileSection(String pstrSectionName) {
		strSectionName = pstrSectionName;
		mapEntries = new HashMap<String, SOSProfileEntry>();
	}

	/**
	 * 
	 * \brief addEntry
	 * 
	 * \details
	 *
	 * \return SOSProfileEntry
	 *
	 * @param pstrEntryName
	 * @param pstrEntryValue
	 * @return
	 */
	public SOSProfileEntry addEntry(String pstrEntryName, String pstrEntryValue) {

		SOSProfileEntry objPE = this.Entry(pstrEntryName);
		if (objPE == null) {
			objPE = new SOSProfileEntry(pstrEntryName, pstrEntryValue);
			Map m = this.Entries();
			m.put(pstrEntryName.toLowerCase(), objPE);
		}
		else {
			objPE.Value(pstrEntryValue);
		}
		return objPE;
	}

	public SOSProfileEntry deleteEntry(String pstrEntryName) {

		SOSProfileEntry objPE = this.Entry(pstrEntryName);
		if (objPE == null) {
		}
		else {
			Map m = this.Entries();
			m.remove(pstrEntryName.toLowerCase());
		}
		return objPE;
	}

	/**
	 * @return
	 */
	public Map<String, SOSProfileEntry> Entries() {
		if (mapEntries == null) {
			mapEntries = new HashMap<String, SOSProfileEntry>();
		}

		return mapEntries;
	}

	public SOSProfileEntry Entry(String pstrKey) {
		return (SOSProfileEntry) mapEntries.get(pstrKey.toLowerCase());
	}

	/**
	 * @return
	 */
	public String Name() {
		return strSectionName;
	}

	/**
	 * @param string
	 */
	public void Name(String string) {
		strSectionName = string;
	}

	public String toString() {
		String strT = "";

		strT = strT.concat("[" + strSectionName + "]\n");
		return strT;
	}

	public String toXML() {

		return "";
	}
}
