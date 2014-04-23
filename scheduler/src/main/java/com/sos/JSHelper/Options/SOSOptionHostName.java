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
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.sos.JSHelper.Exceptions.JobSchedulerException;

/**
* \class SOSOptionHostName
*
* \brief SOSOptionHostName -
*
* \details
*
* \section SOSOptionHostName.java_intro_sec Introduction
*
* \section SOSOptionHostName.java_samples Some Samples
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
* @version $Id: SOSOptionHostName.java 20483 2013-06-04 07:21:08Z ur $12.07.2010
* \see reference
*
* Created on 12.07.2010 14:02:15
 */
/**
 * @author KB
 *
 */
public class SOSOptionHostName extends SOSOptionElement {
	/**
	 *
	 */
	private static final long	serialVersionUID	= 4006670598088800990L;
	private final String		conClassName		= "SOSOptionHostName";
	public static final String	conLocalHostName	= "localhost";
	public SOSOptionPortNumber	objPortNumber		= null;
	private SOSOptionString		objH				= null;				// new SOSOptionHostName(null, this.XMLTagName() + "_ip",
																			// "Description", this.getHostAdress(), "0.0.0.0", false);
	private String				strPID				= null;

	/**
	 * \brief SOSOptionHostName
	 *
	 * \details
	 *
	 * @param pobjParent
	 * @param pstrKey
	 * @param pstrDescription
	 * @param pstrValue
	 * @param pstrDefaultValue
	 * @param pflgIsMandatory
	 */
	public SOSOptionHostName(final JSOptionsClass pobjParent, final String pstrKey, final String pstrDescription, final String pstrValue,
			final String pstrDefaultValue, final boolean pflgIsMandatory) {
		super(pobjParent, pstrKey, pstrDescription, pstrValue, pstrDefaultValue, pflgIsMandatory);
		// TODO Auto-generated constructor stub
		objH = new SOSOptionString(null, this.XMLTagName() + "_ip", "Description", this.getHostAdress(), "0.0.0.0", false);
	}

	@Override
	public void Value (final String pstrHostName) {
		if (pstrHostName != null && pstrHostName.equalsIgnoreCase("local")) {
			super.Value("localhost");
		}
		else {
			super.Value(pstrHostName);
		}

	}
	/**
	 *
	 * \brief getHostAdress
	 *
	 * \details
	 *
	 * \return String
	 *
	 * @return
	 */
	public String getHostAdress() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getHostAdress";
		String strIpAdress = "";
		if (this.IsNotEmpty()) {
			try {
				strIpAdress = this.getInetAddress().getHostAddress();
			}
			catch (RuntimeException e) {
				throw new JobSchedulerException(String.format("RunTime Exception, HostName =' %1$s' ", strValue), e);
			}
		}
		return strIpAdress;
	} // private String getHostAdress

	/**
	 *
	 * \brief getInetAddress
	 *
	 * \details
	 *
	 * \return InetAddress
	 *
	 * @return
	 */
	public InetAddress getInetAddress() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getInetAdress";
		InetAddress objInetAdress = null;
		try {
			objInetAdress = InetAddress.getByName(this.getHostFromUrl());
		}
		catch (UnknownHostException e) {
			throw new JobSchedulerException(String.format("Unknown host, HostName = '%1$s'", strValue), e);
		}
		catch (RuntimeException e) {
			throw new JobSchedulerException(String.format("RuntimeException, HostName = '%1$s'", strValue), e);
		}
		return objInetAdress;
	} // private InetAdress getInetAddress

	/**
	 *
	 * \brief toString
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 */
	@Override
	public String toString() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::toString";
		String strToString = "";
		if (this.IsNotEmpty()) {
			String strHostAdress = "n.a.";
			try {
				strHostAdress = this.getHostAdress();
			}
			catch (Exception e) {
				//
			}

			strToString = this.Value() + " (" + strHostAdress + ")";
		}
		return strToString;
	} // private String toString

	/**
	 *
	 * \brief ping
	 *
	 * \details
	 *
	 * \return boolean
	 *
	 * @return
	 */
	public boolean ping() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::ping";
		boolean flgPingSuccessfull = false;
		try {
			flgPingSuccessfull = this.getInetAddress().isReachable(10000);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flgPingSuccessfull;
	} // private return_type ping

	public void setPort(final SOSOptionPortNumber pobjPortNumber) {
		objPortNumber = pobjPortNumber;
	}

	public boolean checkPortAvailable() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::checkPortAvailable";
		Socket s;
		try {
			s = new Socket(getInetAddress(), objPortNumber.value());
			s.close();
		}
		catch (IOException e) {
			return false;
		}
		return true;
	} // private boolean checkPortAvailable

	public String toXML(final String pstrXMLTagName) throws Exception {
		String strRet = "";
		String strT = this.XMLTagName();
		try {
			this.XMLTagName(pstrXMLTagName);
			strRet = toXml();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.XMLTagName(strT);
		}
		return strRet;
	}

	/**
	 * \brief toXml - XML-Tag mit dem Wert der Option liefern
	 *
	 * \details
	 *
	 * @return String - den Wert der Option im XML-Tag
	 * @throws Exception
	 */
	@Override
	public String toXml() throws Exception {
		String strT = "";
		if (gflgCreateShortXML == true) {
			strT = super.toXml();
		}
		else {
			strT = "<" + this.XMLTagName();
			strT += " mandatory=" + QuotedValue(boolean2String(this.isMandatory()));
			if (isNotEmpty(this.DefaultValue())) {
				strT += " default=" + QuotedValue(this.DefaultValue());
			}
			if (isNotEmpty(this.Title())) {
				strT += " title=" + QuotedValue(this.Title());
			}
			strT += ">";
			if (this.Value().length() > 0) {
				if (isCData) {
					strT += "<![CDATA[" + this.FormattedValue() + "]]>";
				}
				else {
					strT += this.Value();
				}
			}
			if (objPortNumber != null) {
				strT = strT + objPortNumber.toXml();
			}
			strT += "</" + this.XMLTagName() + ">";
			strT += objH.toXml();
		}
		return strT;
	}

	public String getPID() {

		if (strPID == null) {
			String pid = ManagementFactory.getRuntimeMXBean().getName();
			String strA[] = pid.split("@");
			strPID = strA[0];

			// System.out.println("name = " + pid + ", pid = " + strA[0]);
		}

		return strPID;

	}
	
	/**
	 * \brief getHostFromUrl
	 *
	 * \details
	 * This method returns hostname from url
	 * Example: It returns www.examle.org from
	 * http://hans:geheim@www.example.org:80/demo/example.cgi?land=de&stadt=aa#geschichte
	 * 
	 * SOSOptionHostName is also used for webdav.
	 * In this case its Value is an url.
	 *
	 * @return String - hostname
	 */
	public String getHostFromUrl() {
		String host = this.Value();
		if (this.IsNotEmpty()) {
			if(this.Value().matches("[^:]+:/+([^/@]+@)?([^/:]+).*")) {
				host = this.Value().replaceFirst("[^:]+:/+([^/@]+@)?([^/:]+).*", "$2");
			}
		}
		return host;
	}
}
