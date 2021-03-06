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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jdom.Element;

import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.MainWindow;
import sos.scheduler.editor.app.Messages;
import sos.scheduler.editor.app.Options;
import sos.scheduler.editor.app.ResourceManager;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;
import sos.scheduler.editor.conf.listeners.ExecuteListener;
import sos.scheduler.editor.conf.listeners.JobsListener;
import sos.scheduler.editor.conf.listeners.ScriptListener;

import com.swtdesigner.SWTResourceManager;

public class JobAssistentExecuteForms {

	private SchedulerDom		dom				= null;

	private ISchedulerUpdate	update			= null;

	private Button				butFinish		= null;

	private Button				butCancel		= null;

	private Button				butNext			= null;

	private Button				butShow			= null;

	private Button				butBack			= null;

	/** Wer hat ihn aufgerufen, der Job assistent oder job_chain assistent*/
	private int					assistentType	= -1;

	private Shell				shell			= null;

	private Combo				jobname			= null;

	private Button				butScript		= null;

	private Button				butProcess		= null;

	private Combo				comLanguage		= null;

	private Element				job				= null;

	private Text				txtInclude		= null;

	private Element				jobBackUp		= null;

	private ScriptJobMainForm			jobForm			= null;

	/** Hilsvariable f�r das Schliessen des Dialogs. 
	 * Das wird gebraucht wenn das Dialog �ber den "X"-Botten (oben rechts vom Dialog) geschlossen wird .*/
	private boolean				closeDialog		= false;

	/**
	 * @wbp.parser.entryPoint
	 */
	public JobAssistentExecuteForms(SchedulerDom dom_, ISchedulerUpdate update_, Element job_, int assistentType_) {
		dom = dom_;
		update = update_;
		assistentType = assistentType_;
		job = job_;
	}

	public void showExecuteForm() {

		shell = new Shell(MainWindow.getSShell(), SWT.CLOSE | SWT.TITLE | SWT.APPLICATION_MODAL | SWT.BORDER );
		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(final ShellEvent e) {
				if (!closeDialog)
					close();
				e.doit = shell.isDisposed();
			}
		});
		shell.setImage(ResourceManager.getImageFromResource("/sos/scheduler/editor/editor.png"));
		final GridLayout gridLayout = new GridLayout();
		gridLayout.marginTop = 5;
		gridLayout.marginRight = 5;
		gridLayout.marginLeft = 5;
		gridLayout.marginBottom = 5;
		gridLayout.numColumns = 3;
		shell.setLayout(gridLayout);
		shell.setSize(411, 200);

//		shell.setText("Execute");
		shell.setText(SOSJOEMessageCodes.JOE_M_JobAssistent_Execute.label());
		{
			final Group jobGroup = new Group(shell, SWT.NONE);
//			jobGroup.setText("Job: " + Utils.getAttributeValue("name", job));
			jobGroup.setText(SOSJOEMessageCodes.JOE_M_JobAssistent_JobGroup.params(Utils.getAttributeValue("name", job)));
			final GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true, true, 3, 1);
			jobGroup.setLayoutData(gridData);
			final GridLayout gridLayout_1 = new GridLayout();
			gridLayout_1.verticalSpacing = 10;
			gridLayout_1.horizontalSpacing = 10;
			gridLayout_1.marginWidth = 10;
			gridLayout_1.marginTop = 10;
			gridLayout_1.marginRight = 10;
			gridLayout_1.marginLeft = 10;
			gridLayout_1.marginHeight = 10;
			gridLayout_1.marginBottom = 10;
			gridLayout_1.numColumns = 5;
			jobGroup.setLayout(gridLayout_1);

			butProcess = SOSJOEMessageCodes.JOE_B_JobAssistent_Process.Control(new Button(jobGroup, SWT.RADIO));
			butProcess.setLayoutData(new GridData());
//			butProcess.setText("Process");
			butProcess.setSelection(job.getChild("process") != null);
			new Label(jobGroup, SWT.NONE);
			new Label(jobGroup, SWT.NONE);
			new Label(jobGroup, SWT.NONE);
			new Label(jobGroup, SWT.NONE);

			butScript = SOSJOEMessageCodes.JOE_B_JobAssistent_Script.Control(new Button(jobGroup, SWT.RADIO));
			butScript.setSelection(job.getChild("script") != null);
			butScript.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent e) {
					if (butScript.getSelection()) {
						comLanguage.setEnabled(true);
					}
					else {
						comLanguage.setEnabled(false);
					}
				}
			});
			final GridData gridData_3 = new GridData(GridData.BEGINNING, GridData.CENTER, false, true);
			gridData_3.widthHint = 69;
			butScript.setLayoutData(gridData_3);
//			butScript.setText("Script");

			{
				final Label lblLanguage = SOSJOEMessageCodes.JOE_L_JobAssistent_Language.Control(new Label(jobGroup, SWT.NONE));
				lblLanguage.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, true));
//				lblLanguage.setText("Language");
			}

			comLanguage = SOSJOEMessageCodes.JOE_Cbo_JobAssistent_Language.Control(new Combo(jobGroup, SWT.NONE));
			comLanguage.setItems(ScriptListener._languagesJob);
			comLanguage.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent e) {
					if (comLanguage.getSelectionIndex() > 1) {
						txtInclude.setEditable(true);
					}
					else {
						txtInclude.setEditable(false);
					}
				}
			});

			txtInclude = SOSJOEMessageCodes.JOE_T_JobAssistent_Include.Control(new Text(jobGroup, SWT.BORDER));
			txtInclude.setEditable(false);
			final GridData gridData_2 = new GridData(GridData.FILL, GridData.CENTER, true, true);
			txtInclude.setLayoutData(gridData_2);

			comLanguage.select(0);
			comLanguage.setEnabled(true);
			final GridData gridData_4 = new GridData(GridData.BEGINNING, GridData.CENTER, true, true);
			gridData_4.widthHint = 60;
			comLanguage.setLayoutData(gridData_4);
			if (job.getChild("script") != null) {
				ScriptListener scriptlistener = new ScriptListener(dom, job, Editor.SCRIPT, update);
				comLanguage.select(scriptlistener.getLanguage());
				if (scriptlistener.getLanguage() > 1) {
					txtInclude.setEditable(true);
					if (scriptlistener.getIncludes().length > 0) {
						txtInclude.setText(scriptlistener.getIncludes()[scriptlistener.getIncludes().length - 1]);
					}
				}
				else {
					txtInclude.setEditable(false);
				}
			}
			else {
				comLanguage.setEnabled(false);
			}

			final Label includeLabel = SOSJOEMessageCodes.JOE_L_JobAssistent_Include.Control(new Label(jobGroup, SWT.NONE));
			final GridData gridData_1 = new GridData(GridData.FILL, GridData.CENTER, false, true);
			gridData_1.widthHint = 45;
			includeLabel.setLayoutData(gridData_1);
//			includeLabel.setText("Include");

		}

		java.awt.Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		shell.setBounds((screen.width - shell.getBounds().width) / 2, (screen.height - shell.getBounds().height) / 2, shell.getBounds().width,
				shell.getBounds().height);

		shell.open();

		{
			final Composite composite = SOSJOEMessageCodes.JOE_Composite1.Control(new Composite(shell, SWT.NONE));
			final GridLayout gridLayout_2 = new GridLayout();
			gridLayout_2.marginWidth = 0;
			gridLayout_2.horizontalSpacing = 0;
			composite.setLayout(gridLayout_2);
			{
				butCancel = SOSJOEMessageCodes.JOE_B_JobAssistent_Cancel.Control(new Button(composite, SWT.NONE));
				butCancel.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(final SelectionEvent e) {
						close();
					}
				});
//				butCancel.setText("Cancel");
			}
		}

		{
			final Composite composite = SOSJOEMessageCodes.JOE_Composite2.Control(new Composite(shell, SWT.NONE));
			composite.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false, 2, 1));
			final GridLayout gridLayout_2 = new GridLayout();
			gridLayout_2.marginWidth = 0;
			gridLayout_2.numColumns = 5;
			composite.setLayout(gridLayout_2);

			{
				butShow = SOSJOEMessageCodes.JOE_B_JobAssistent_Show.Control(new Button(composite, SWT.NONE));
				butShow.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(final SelectionEvent e) {
						refreshJob();
						Utils.showClipboard(Utils.getElementAsString(job), shell, false, null, false, null, false);
					}
				});
//				butShow.setText("Show");
			}

			{
				butFinish = SOSJOEMessageCodes.JOE_B_JobAssistent_Finish.Control(new Button(composite, SWT.NONE));
				butFinish.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(final SelectionEvent e) {
						refreshElement(true);
						closeDialog = true;
						shell.dispose();
					}
				});
//				butFinish.setText("Finish");
			}

			butBack = SOSJOEMessageCodes.JOE_B_JobAssistent_Back.Control(new Button(composite, SWT.NONE));
			butBack.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent e) {
					refreshJob();
					JobAssistentTasksForm tasks = new JobAssistentTasksForm(dom, update, job, assistentType);
					tasks.showTasksForm();
					if (jobname != null)
						tasks.setJobname(jobname);
					tasks.setBackUpJob(jobBackUp, jobForm);
					closeDialog = true;
					shell.dispose();
				}
			});
//			butBack.setText("Back");
			{
				butNext = SOSJOEMessageCodes.JOE_B_JobAssistent_Next.Control(new Button(composite, SWT.NONE));
				butNext.setFont(SWTResourceManager.getFont("", 8, SWT.BOLD));
				butNext.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(final SelectionEvent e) {
						refreshElement(false);
						closeDialog = true;
						shell.dispose();
					}
				});
//				butNext.setText("Next");
			}
//			Utils.createHelpButton(composite, "assistent.script_or_execute", shell);
			Utils.createHelpButton(composite, "JOE_M_JobAssistentExecuteForm_Help.label", shell);

		}
//		setToolTipText();
		shell.layout();
	}

	public void setToolTipText() {
//		butCancel.setToolTipText(Messages.getTooltip("assistent.cancel"));
//		butNext.setToolTipText(Messages.getTooltip("assistent.next"));
//		butShow.setToolTipText(Messages.getTooltip("assistent.show"));
//		butFinish.setToolTipText(Messages.getTooltip("assistent.finish"));
//		butBack.setToolTipText(Messages.getTooltip("butBack"));
//		comLanguage.setToolTipText(Messages.getTooltip("assistent.script_or_execute.script"));
//		butProcess.setToolTipText(Messages.getTooltip("assistent.script_or_execute.process"));
//		butScript.setToolTipText(Messages.getTooltip("assistent.script_or_execute.language"));
//		txtInclude.setToolTipText(Messages.getTooltip("assistent.script_or_execute.include"));
	}

	private void close() {
//		int cont = MainWindow.message(shell, sos.scheduler.editor.app.Messages.getString("assistent.cancel"), SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
		int cont = MainWindow.message(shell, SOSJOEMessageCodes.JOE_M_JobAssistent_CancelWizard.label(), SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
		if (cont == SWT.OK) {
			if (jobBackUp != null)
				job.setContent(jobBackUp.cloneContent());
			shell.dispose();
		}
	}

	public void setJobname(Combo jobname) {
		this.jobname = jobname;
	}

	private void refreshJob() {

		if (butProcess.getSelection()) {

			ExecuteListener executeListener = new ExecuteListener(dom, job);
			executeListener.setExecutable(true);

		}
		else {

			ExecuteListener executeListener = new ExecuteListener(dom, job);
			executeListener.setExecutable(false);

			ScriptListener scriptlistener = new ScriptListener(dom, job, Editor.SCRIPT, update);

			if (comLanguage.getSelectionIndex() > -1) {
				scriptlistener.setLanguage(comLanguage.getSelectionIndex());
			}

			if (comLanguage.getSelectionIndex() > 1 && txtInclude.getText() != null && txtInclude.getText().trim().length() > 0) {
				// wurde eine neue include Datei angegeben
				if (scriptlistener.getIncludes().length == 0
						|| !scriptlistener.getIncludes()[scriptlistener.getIncludes().length - 1].equals(txtInclude.getText()))
					scriptlistener.addInclude(txtInclude.getText());
			}

		}

	}

	private void refreshElement(boolean apply) {
		refreshJob();
		if (!apply) {
			if (butProcess.getSelection()) {

				JobAssistentProcessForms process = new JobAssistentProcessForms(dom, update, job, assistentType);
				process.showProcessForm();
				if (jobname != null)
					process.setJobname(jobname);
				process.setBackUpJob(jobBackUp, jobForm);

			}
			else {

				JobAssistentScriptForms script = new JobAssistentScriptForms(dom, update, job, assistentType);
				script.showScriptForm();
				if (jobname != null)
					script.setJobname(jobname);
				script.setBackUpJob(jobBackUp, jobForm);
			}
		}

		if (apply) {
			if (assistentType == Editor.JOB_WIZARD) {
				jobForm.initForm();
			}
			else {
				if (jobname != null)
					jobname.setText(Utils.getAttributeValue("name", job));
				JobsListener listener = new JobsListener(dom, update);
				listener.newImportJob(job, assistentType);
			}
			if (Options.getPropertyBoolean("editor.job.show.wizard"))
//				Utils.showClipboard(Messages.getString("assistent.finish") + "\n\n" + Utils.getElementAsString(job), shell, false, null, false, null, true);
				Utils.showClipboard(SOSJOEMessageCodes.JOE_M_JobAssistent_Finish.label() + "\n\n" + Utils.getElementAsString(job), shell, false, null, false, null, true);

		}
	}

	/**
	 * Der Wizzard wurde f�r ein bestehende Job gestartet. 
	 * Beim verlassen der Wizzard ohne Speichern, muss der bestehende Job ohne �nderungen wieder zur�ckgesetz werden.
	 * @param backUpJob
	 */
	public void setBackUpJob(Element backUpJob, ScriptJobMainForm jobForm_) {
		if (backUpJob != null)
			jobBackUp = (Element) backUpJob.clone();
		jobForm = jobForm_;
	}

}
