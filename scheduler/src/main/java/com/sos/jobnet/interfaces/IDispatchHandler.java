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
package com.sos.jobnet.interfaces;

import com.sos.jobnet.db.JobNetPlanDBItem;

/**
* \class IDispatchHandler 
* 
* \brief IDispatchHandler - 
* 
* \details
*
* \section IDispatchHandler.java_intro_sec Introduction
*
* \section IDispatchHandler.java_samples Some Samples
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
* \author Uwe Risse
* @version $Id: IDispatchHandler.java 21040 2013-09-09 16:51:14Z ss $
* \see reference
*
* Created on 24.02.2012 14:20:29
 */
public interface IDispatchHandler extends IJobNetHandler {
	public abstract void setDispatcherStatusToRunning();
	public abstract void setRunnerStatusToFinished();
	public abstract void setRunnerStatusToError();
	public abstract boolean isRunnerSkipped();
	public abstract boolean isRunnerInError();
	public abstract boolean isDispatcherSkipped();
	public abstract void setNodeStatusToDispatching();
	public abstract void setNodeStatusToFinished();
	public abstract void setDispatcherStatusToFinished();
	public abstract void setDispatcherStatusToError();
	public abstract void setNodeStatusToError(long exitCode);
    public abstract void setSkipRunner();
	// public abstract void setNodeStatusToError();
	// public abstract void setNodeStatusToStarting(JobNetPlanDBItem planItem);
	public abstract void setNodeStatusToStarted(JobNetPlanDBItem planItem);
	public abstract int getNumberOfDispatchingNodes();
	public abstract IJobNetCollection getDispatchingNodes();
	public abstract boolean isJobnetEnded();
    public abstract void updateHistory();
}
