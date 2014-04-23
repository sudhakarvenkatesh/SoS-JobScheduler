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
package com.sos.VirtualFileSystem.Interfaces;

import java.io.IOException;

import com.sos.VirtualFileSystem.DataElements.SOSFileList;
import com.sos.VirtualFileSystem.DataElements.SOSFolderName;

/**
* \class ISOSVirtualFileSystem 
* 
* \brief ISOSVirtualFileSystem - 
* 
* \details
*
* \section ISOSVirtualFileSystem.java_intro_sec Introduction
*
* \section ISOSVirtualFileSystem.java_samples Some Samples
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
* @version $Id: ISOSVirtualFileSystem.java 14789 2011-07-08 15:51:52Z sos $21.05.2010
* \see reference
*
* Created on 21.05.2010 10:55:28
 */

/**
 * @author KB
 *
 */
public interface ISOSVirtualFileSystem {

	public ISOSConnection getConnection() ;
	public ISOSSession getSession();

	/**
	 * 
	 * \brief mkdir - create a directory
	 * 
	 * \details
	 * Creates a (sub)directory on the FTP server in the current directory .
	 * 
	 * \return ISOSVirtualFolder
	 *
	 * @param pobjFolderName
	 * @throws IOException
	 */
	
	public ISOSVirtualFolder mkdir (final SOSFolderName pobjFolderName) throws IOException;
	
	/**
	 * 
	 * \brief rmdir - remove directory
	 * 
	 * \details
	 * Removes a directory on the FTP server (if empty).
	 * 
	 * \return boolean
	 *
	 * @param pobjFolderName
	 * @throws IOException
	 */
	
	public boolean rmdir(final SOSFolderName pobjFolderName) throws IOException;
	public SOSFileList dir(SOSFolderName pobjFolderName);
	public  SOSFileList dir(String pathname, int flag);
	
	
}
