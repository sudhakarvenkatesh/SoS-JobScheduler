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
package com.sos.JSHelper.DataElements;

/**
* \class JSDataElementValidFromDate 
* 
* \brief JSDataElementValidFromDate - 
* 
* \details
*
* \section JSDataElementValidFromDate.java_intro_sec Introduction
*
* \section JSDataElementValidFromDate.java_samples Some Samples
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
* \author EQALS
* @version $Id: JSDataElementValidFromDate.java 14731 2011-07-05 20:50:42Z sos $16.12.2009
* \see reference
*
* Created on 16.12.2009 18:54:10
 */

public class JSDataElementValidFromDate extends JSDataElementDateISO {

	@SuppressWarnings("unused")
	private final String	conClassName	= "JSDataElementValidFromDate";

	JSDataElementValidFromDate() {
		//
	}
	/**
	 * \brief JSTimeStampISO
	 *
	 * \details
	 *
	 * @param pPstrValue
	 */
	public JSDataElementValidFromDate(final String pPstrValue) {
		super(pPstrValue);
	}

	public JSDataElementValidFromDate(final JSDataElementDate pdteDate) {
		this(pdteDate.Value());
	}
	/**
	 * \brief JSTimeStampISO
	 *
	 * \details
	 *
	 * @param pPstrValue
	 * @param pPstrDescription
	 */
	public JSDataElementValidFromDate(final String pPstrValue, final String pPstrDescription) {
		super(pPstrValue, pPstrDescription);
	}

	/**
	 * \brief JSTimeStampISO
	 *
	 * \details
	 *
	 * @param pPstrValue
	 * @param pPstrDescription
	 * @param pPintSize
	 * @param pPintPos
	 * @param pPstrFormatString
	 * @param pPstrColumnHeader
	 * @param pPstrXMLTagName
	 */
	public JSDataElementValidFromDate(final String pPstrValue, final String pPstrDescription, final int pPintSize, final int pPintPos, final String pPstrFormatString,
			final String pPstrColumnHeader, final String pPstrXMLTagName) {
		super(pPstrValue, pPstrDescription, pPintSize, pPintPos, pPstrFormatString, pPstrColumnHeader, pPstrXMLTagName);
	}
	
	
	public String FormattedValue()  {
		String strT = Value();
		if (FormatString().equals(JSDateFormat.dfDATE.toPattern()) 
				|| FormatString().equals(JSDateFormat.dfDATE_SHORT.toPattern())) {
			strT = strT.substring(0, 9 + 1);
		}
		this.Value(strT);
		return this.Value();
	}
	
}
