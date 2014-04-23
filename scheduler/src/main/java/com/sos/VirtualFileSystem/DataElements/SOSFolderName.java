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
package com.sos.VirtualFileSystem.DataElements;

import com.sos.JSHelper.DataElements.JSDataElement;
import com.sos.i18n.annotation.I18NResourceBundle;

/**
* \class SOSFolderName 
* 
* \brief SOSFolderName - 
* 
* \details
*
* \section SOSFolderName.java_intro_sec Introduction
*
* \section SOSFolderName.java_samples Some Samples
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
* @version $Id: SOSFolderName.java 17456 2012-06-26 15:14:26Z kb $25.05.2010
* \see reference
*
* Created on 25.05.2010 12:30:25
 */

/**
 * @author KB
 *
 */
@I18NResourceBundle(baseName = "SOSVirtualFileSystem", defaultLocale = "en")
public class SOSFolderName extends JSDataElement { 

	@SuppressWarnings("unused")
	private final String	conClassName	= "SOSFolderName";

	SOSFolderName() {
		//
	}

	/**
	 * \brief SOSFolderName
	 *
	 * \details
	 *
	 * @param pstrValue
	 */
	public SOSFolderName(String pstrValue) {
		super(pstrValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * \brief SOSFolderName
	 *
	 * \details
	 *
	 * @param pstrValue
	 * @param pstrDescription
	 */
	public SOSFolderName(String pstrValue, String pstrDescription) {
		super(pstrValue, pstrDescription);
		// TODO Auto-generated constructor stub
	}

	/**
	 * \brief SOSFolderName
	 *
	 * \details
	 *
	 * @param pstrValue
	 * @param pstrDescription
	 * @param pintSize
	 * @param pintPos
	 * @param pstrFormatString
	 * @param pstrColumnHeader
	 * @param pstrXMLTagName
	 */
	public SOSFolderName(String pstrValue, String pstrDescription, int pintSize, int pintPos, String pstrFormatString, String pstrColumnHeader,
			String pstrXMLTagName) {
		super(pstrValue, pstrDescription, pintSize, pintPos, pstrFormatString, pstrColumnHeader, pstrXMLTagName);
		// TODO Auto-generated constructor stub
	}
}
