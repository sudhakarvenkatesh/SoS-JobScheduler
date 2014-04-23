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
package com.sos.JSHelper.Basics;

/**
* \class JSVersionInfo 
* 
* \brief JSVersionInfo - 
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
* \version 15.06.2011
* \see reference
*
* Created on 15.06.2011 10:55:37
 */

/**
 * @author KB
 *
 */
public class JSVersionInfo {

	private final static String	conClassName		= "JSVersionInfo";
	// private static final Logger logger = Logger.getLogger(JSVersionInfo.class);

	public static final String	conVersionNumber	= "1.5.{properties.timestamp_YDDD}";
	public static final String	conVersionDate		= "{build-date}";
	public static final String	conCopyrightText	= "Copyright 2003-2013 SOS GmbH Berlin";
	public static final String  conSVNVersion = "$Id: JSVersionInfo.java 20832 2013-08-19 08:31:46Z oh $";
	public JSVersionInfo() {
		//
	}

	public static final String getVersionString() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getVersionString";

		String strRet = conVersionNumber + " (" + conVersionDate + ") " + conCopyrightText;

		return strRet;
	} // private String toString

}
