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

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
 
import org.jdom.Element;

import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;
import sos.scheduler.editor.conf.container.JobDocumentation;
import sos.scheduler.editor.conf.listeners.JobListener;

public class JobDocumentationForm extends Composite /* implements IUpdateLanguage */ {
 
  private JobListener	objDataProvider		= null;
 
 	public JobDocumentationForm(Composite parent, int style, SchedulerDom dom, Element job, ISchedulerUpdate main) {
		super(parent, style);
 		dom.setInit(true);
		this.setEnabled(Utils.isElementEnabled("job", dom, job));
		objDataProvider = new JobListener(dom, job, main);
		initialize();
		dom.setInit(false);
 	}

	public void apply() {
	}

	public boolean isUnsaved() {
		return false;
	}

	private void initialize() {
		this.setLayout(new FillLayout());
	    new JobDocumentation(this, objDataProvider);		
	    setSize(new org.eclipse.swt.graphics.Point(723, 566));
	}
  
    public void initForm(){
        
    }


} // @jve:decl-index=0:visual-constraint="10,10"
