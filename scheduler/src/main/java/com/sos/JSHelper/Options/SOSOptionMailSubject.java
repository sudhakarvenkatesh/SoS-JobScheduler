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
/**
 * 
 */
package com.sos.JSHelper.Options;

import java.io.File;

import com.sos.JSHelper.io.Files.JSFile;

/**
 * @author KB
 *
 */
public class SOSOptionMailSubject extends SOSOptionString {

	@SuppressWarnings("unused")
	private final String	conSVNVersion		= "$Id: JobSchedulerExistsFile.java 17751 2012-08-03 11:51:07Z kb $";
	private final String	conClassName		= this.getClass().getName();

	private boolean			flgSubjectFromFile	= false;
	private String			strSubjectFileName	= "";

	/**
	 * @param pPobjParent
	 * @param pPstrKey
	 * @param pPstrDescription
	 * @param pPstrValue
	 * @param pPstrDefaultValue
	 * @param pPflgIsMandatory
	 */
	public SOSOptionMailSubject(JSOptionsClass pPobjParent, String pPstrKey, String pPstrDescription, String pPstrValue, String pPstrDefaultValue,
			boolean pPflgIsMandatory) {
		super(pPobjParent, pPstrKey, pPstrDescription, pPstrValue, pPstrDefaultValue, pPflgIsMandatory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Value(final String pstrValue) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Value";

		JSFile objF = new JSFile(pstrValue);
		if (objF.exists()) {
			this.strSubjectFileName = pstrValue;
			flgSubjectFromFile = true;
			super.Value(objF.getContent());
		}
		else {
			super.Value(pstrValue);
		}

	} // public void Value

	public String getSubjectFile() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getSubjectFile";

		return strSubjectFileName;

	} // private String getSubjectFile

	public boolean isSubjectFile() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::isSubjectFile";

		return flgSubjectFromFile;

	} // private boolean isSubjectFile
}
