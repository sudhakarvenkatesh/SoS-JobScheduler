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

import org.eclipse.swt.SWT;

import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.jdom.Element;

import sos.scheduler.editor.app.ContextMenu;
import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.IUnsaved;
import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.MainWindow;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;
import sos.scheduler.editor.conf.listeners.JobChainsListener;


public class JobChainsForm extends SOSJOEMessageCodes implements IUnsaved, IUpdateLanguage {


	private ISchedulerUpdate    update            = null;
	private JobChainsListener   listener          = null;
	private Group               group             = null;
	private static Table        tChains           = null;
	private Button              bRemoveChain      = null;
	private Button              bNewChain         = null;  
	private SashForm            sashForm          = null;
	private Button              butDetails        = null; 
	private SchedulerDom        _dom              = null;

	/**Hilfsvariable: Wenn Parameter Formular ge�ffnet wurde muss �berpr�ft werden, ob der Checkbox in der Tabelle - State gesetzt werden soll.*/
	private boolean             checkParameter             = false;



	public JobChainsForm(Composite parent, int style, SchedulerDom dom, Element config, ISchedulerUpdate update_) {
		super(parent, style);
		
		listener = new JobChainsListener(dom, config, update_);		
		_dom = dom;
		
		initialize();
		setToolTipText();
		update = update_;
		listener.fillChains(tChains);

	}

	private void initialize() {
		this.setLayout(new FillLayout());
		createGroup();
		setSize(new org.eclipse.swt.graphics.Point(676, 464));
	}


	/**
	 * This method initializes group
	 */
	private void createGroup() {
		group = new Group(this, SWT.NONE);        

		final GridLayout gridLayout = new GridLayout();
		group.setLayout(gridLayout);

		sashForm = new SashForm(group, SWT.VERTICAL);
		sashForm.setLayout(new GridLayout());

		sashForm.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		final Group jobchainsGroup = JOE_G_JobChainsForm_JobChains.Control(new Group(sashForm, SWT.NONE));
		final GridLayout gridLayout_2 = new GridLayout();
		gridLayout_2.numColumns = 2;
		jobchainsGroup.setLayout(gridLayout_2);
		
		tChains = JOE_Tbl_JobChainsForm_JobChains.Control(new Table(jobchainsGroup, SWT.BORDER));
		tChains.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(final MouseEvent e) {
				if(tChains.getSelectionCount() > 0)
					ContextMenu.goTo(tChains.getSelection()[0].getText(0), _dom, Editor.JOB_CHAIN);
			}
		});
		tChains.getHorizontalBar().setMaximum(0);
		tChains.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 1, 3));
		tChains.setHeaderVisible(true);
		tChains.setLinesVisible(true);
 	
		
		tChains.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				
				/*if (e.detail == SWT.CHECK) {
					e.doit = false;
					return;
				}*/
				boolean enabled = true;
				if (tChains.getSelectionCount() > 0) {
					listener.selectChain(tChains.getSelectionIndex());
					Element currElem = listener.getChain();
					if(currElem != null && !Utils.isElementEnabled("job_chain", _dom, currElem)) {
						enabled = false;
					}

					butDetails.setEnabled(true);
				}
				bRemoveChain.setEnabled(tChains.getSelectionCount() > 0 && enabled);
			}
		});
		
		
		TableColumn tableColumn1 = JOE_TCl_JobChainsForm_Name.Control(new TableColumn(tChains, SWT.NONE));
		tableColumn1.setWidth(150);
		
		TableColumn ordersRecoverableTableColumn = JOE_TCl_JobChainsForm_OrdersRecoverable.Control(new TableColumn(tChains, SWT.NONE));
		ordersRecoverableTableColumn.setWidth(104);
		
		TableColumn tableColumn2 = JOE_TCl_JobChainsForm_Visible.Control(new TableColumn(tChains, SWT.NONE));
		tableColumn2.setWidth(90);
		
		bNewChain = JOE_B_JobChainsForm_NewChain.Control(new Button(jobchainsGroup, SWT.NONE));
		bNewChain.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		getShell().setDefaultButton(bNewChain);
		bNewChain.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				createNewJobChain();
				/*listener.newChain();
				applyChain();
				tChains.deselectAll();
				butDetails.setEnabled(false);*/
			}
		});
		
		bRemoveChain = JOE_B_JobChainsForm_RemoveChain.Control(new Button(jobchainsGroup, SWT.NONE));
		bRemoveChain.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false));
		bRemoveChain.setEnabled(false);
		bRemoveChain.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				
				int c = MainWindow.message(getShell(), JOE_M_JobChainsForm_RemoveChain.label(), SWT.ICON_QUESTION | SWT.YES | SWT.NO );
				if(c != SWT.YES)
					return;
				
				
				if(Utils.checkElement(tChains.getSelection()[0].getText(0), listener.get_dom(), sos.scheduler.editor.app.Editor.JOB_CHAINS, null))//wird der Job woandes verwendet?
					deleteChain();
			}
		});

		butDetails = JOE_B_JobChainsForm_Details.Control(new Button(jobchainsGroup, SWT.NONE));
		butDetails.setEnabled(false);
		butDetails.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				checkParameter = true;
				showDetails(null);
			}
		});
		butDetails.addFocusListener(new FocusAdapter() {
			public void focusGained(final FocusEvent e) {
				if(checkParameter) {
					listener.fillChains(tChains);
					checkParameter = false;
				}
			}
		});
		butDetails.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false));

	}





	private void deleteChain() {
		if (tChains.getSelectionCount() > 0) {
			int index = tChains.getSelectionIndex();
			listener.deleteChain(index);
			tChains.remove(index);	
			if (index >= tChains.getItemCount())
				index--;
			if (tChains.getItemCount() > 0) {
				tChains.select(index);
				listener.selectChain(index);
			}
		}		
		boolean selection = tChains.getSelectionCount() > 0;
		bRemoveChain.setEnabled(selection);

	}


	private void applyChain() {
	
       int i = tChains.getItemCount() + 1;
//	   String newName = "job_chain" + i;
       String newName = JOE_M_JobChain.label() + i;
 	   while (listener.indexOf(newName) >= 0) {
 		   i++;
//		  newName = "job_chain" + i;
 		  newName = JOE_M_JobChain.label() + i;
	   }
 	    
	
		listener.applyChain(newName, true, true);
		int index = tChains.getSelectionIndex();
		if (index == -1)
			index = tChains.getItemCount();
		listener.fillChains(tChains);
		tChains.select(index);

		bRemoveChain.setEnabled(true);

		getShell().setDefaultButton(bNewChain);
		if(listener.getChainName() != null && listener.getChainName().length() > 0) {
			butDetails.setEnabled(true);
		}  else {
			butDetails.setEnabled(false);
		}
	}





	public void setToolTipText() {
//
	}

	public void setISchedulerUpdate(ISchedulerUpdate update_) {
		update = update_;
	}

	private void showDetails(String state) {
		String name = tChains.getSelection()[0].getText(0);
		if(name != null && name.length() > 0) {
			//OrdersListener ordersListener =  new OrdersListener(listener.get_dom(), update);
			//String[] listOfOrders = ordersListener.getOrderIds();
			boolean isLifeElement = listener.get_dom().isLifeElement() || listener.get_dom().isDirectory();
			
			if(state == null) {
				DetailDialogForm detail = new DetailDialogForm(name, isLifeElement, listener.get_dom().getFilename());
				detail.showDetails();
				detail.getDialogForm().setParamsForWizzard(_dom, update);				
			} else {
				DetailDialogForm detail = new DetailDialogForm(name, state, null, isLifeElement, listener.get_dom().getFilename());
				detail.showDetails();
				detail.getDialogForm().setParamsForWizzard(_dom, update);
			} 

		} else {
			MainWindow.message(getShell(), JOE_M_JobAssistent_CancelWizard.label(), SWT.ICON_WARNING | SWT.OK |SWT.CANCEL );
		}

	}

	public void apply() {

		applyChain();

	}


	public boolean isUnsaved() {
		return false;
	}

	public void createNewJobChain() {
		listener.newChain();
		applyChain();
		tChains.deselectAll();
		butDetails.setEnabled(false);
	}

	public static Table getTableChains() {
		return tChains;
	}
	
} // @jve:decl-index=0:visual-constraint="10,10"
