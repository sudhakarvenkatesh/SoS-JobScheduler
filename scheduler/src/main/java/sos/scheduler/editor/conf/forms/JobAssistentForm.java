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
package sos.scheduler.editor.conf.forms;


import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;
import sos.scheduler.editor.app.ErrorLog;
import sos.scheduler.editor.app.Options;
import sos.scheduler.editor.app.SOSJOEMessageCodes;

public class JobAssistentForm {
	
	private SchedulerDom dom = null;
	private ISchedulerUpdate update = null;
	
	
	public JobAssistentForm(SchedulerDom dom_, ISchedulerUpdate update_) {
		dom = dom_;
		update = update_;	
	}	
		
	
	public void startJobAssistant() {
		try {
			if(Options.isShowWizardInfo()) { 
				JobAssistentInfoForms info = new JobAssistentInfoForms(dom, update);
				info.showInfoForm();
			} else {
				JobAssistentTypeForms typeForms = new JobAssistentTypeForms(dom, update);												
				typeForms.showTypeForms();
			}
			
		} catch (Exception e){
			try {
//				new ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName() , e);
				new ErrorLog(SOSJOEMessageCodes.JOE_M_0010.params(sos.util.SOSClassUtil.getMethodName(), e));
			} catch(Exception ee) {
				//tu nichts
			}
//			System.out.println("error in JobAssistent.startJobAssistent: " + e.getMessage() );
			try {
				System.out.println(SOSJOEMessageCodes.JOE_M_0010.params(sos.util.SOSClassUtil.getMethodName(), e));
			}
			catch (Exception e1) {
				//tu nichts
			}
		}
		
		
	}
	
}
