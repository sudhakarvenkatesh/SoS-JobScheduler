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
package sos.scheduler.editor.app;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import com.sos.dialog.components.SOSDateTime;
import com.sos.localization.SOSMsg;
import org.eclipse.swt.widgets.Text;

public class SOSMsgJOE extends SOSMsg {

	private static String	conClassName	= "SOSMsgJOE";

	public SOSMsgJOE(String pstrMessageCode) {
		super(pstrMessageCode);
		if (this.Messages == null) {
			super.setMessageResource("JOEMessages");
			this.Messages = super.Messages;
		}
		else {
			super.Messages = this.Messages;
		}
	} // public SOSMsgJOE

	public Text Control(final Text pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
	// Text kommt im Normalfall nicht aus einer Propertie-Datei. Deswegen ergibt es keinen Sinn	
	//	pobjC.setText(label());
		
		pobjC.setToolTipText(tooltip());
		setKeyListener(pobjC);
		pobjC.addFocusListener(new FocusAdapter() {
			public void focusGained(final FocusEvent e) {
				pobjC.selectAll();
				// pobjC.setBackground(new Color(SWT.BLACK));
			}

			@Override
			public void focusLost(FocusEvent e) {
			}
		});
		return pobjC;
	} // public Text Control

	public Label Control(final Label pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
		pobjC.setText(label());
		pobjC.setToolTipText(this.tooltip());
		setKeyListener(pobjC);
		return pobjC;
	} // public Label Control

	public Group Control(final Group pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
		pobjC.setText(caption());
		pobjC.setToolTipText(tooltip());
		setKeyListener(pobjC);
		return pobjC;
	} // public Group Control

	public Button Control(final Button pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
		
		pobjC.setText(caption());
		pobjC.setToolTipText(tooltip());
		setKeyListener(pobjC);
		return pobjC;
	} // public Button Control

	public Combo Control(final Combo pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
		pobjC.setText(caption());
		pobjC.setToolTipText(tooltip());
		setKeyListener(pobjC);
		return pobjC;
	} // public Combo Control

	public Composite Control(final Composite pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
		pobjC.setToolTipText(tooltip());
		setKeyListener(pobjC);
		return pobjC;
	} // public Composite Control
	
	public CCombo Control(final CCombo pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
		pobjC.setToolTipText(tooltip());
		setKeyListener(pobjC);
		return pobjC;
	} // public CCombo Control

	public TableColumn Control(final TableColumn pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
		pobjC.setText(caption());
		pobjC.setToolTipText(tooltip());
		return pobjC;
	} // public TableColumn Control

	public Table Control(final Table pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
		pobjC.setToolTipText(tooltip());
		setKeyListener(pobjC);
		return pobjC;
	} // public Table Control

	public FileDialog Control(final FileDialog pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
		pobjC.setText(caption());
		return pobjC;
	} // public FileDialog Control

	public Spinner Control(final Spinner pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
		pobjC.setToolTipText(tooltip());
		setKeyListener(pobjC);
		return pobjC;
	} // public Spinner Control
	
	public MessageBox Control(final MessageBox pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
		pobjC.setMessage(this.caption());
		return pobjC;
	} // public MessageBox Control
	
	public List Control(final List pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
		pobjC.setToolTipText(tooltip());
		setKeyListener(pobjC);
		return pobjC;
	} // public List Control
	
	public Tree Control(final Tree pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
		pobjC.setToolTipText(this.tooltip());
		setKeyListener(pobjC);
		return pobjC;
	} // public Tree Control
	
	public Browser Control(final Browser pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
		pobjC.setText(caption());
		pobjC.setToolTipText(tooltip());
		setKeyListener(pobjC);
		return pobjC;
	} // public Browser Control
	
	public TreeColumn Control(final TreeColumn pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
		pobjC.setText(caption());
		pobjC.setToolTipText(tooltip());
		return pobjC;
	} // public TreeColumn Control
	
	public TabItem Control(final TabItem pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
		pobjC.setText(caption());
		pobjC.setToolTipText(this.tooltip());
		return pobjC;
	} // public TabItem Control
	
	public CTabItem Control(final CTabItem pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
		pobjC.setText(caption());
		pobjC.setToolTipText(this.tooltip());
		return pobjC;
	} // public CTabItem Control
	
	public SOSDateTime Control(final SOSDateTime pobjC) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Control";
		pobjC.setToolTipText(this.tooltip());
		return pobjC;
	} // public SOSDateTime Control
	
	

	private void setKeyListener(final Control pobjC) {
//		strControlName = pobjC.
		pobjC.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.keyCode == SWT.F1) {
					// MainWindow.message("F1 gedr�ckt", SWT.ICON_INFORMATION);
					openHelp(getF1()); // "http:www.sos-berlin.com/doc/en/scheduler.doc/xml/job.xml");
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}
		});

	} // private void setKeyListener

	public void openHelp(String helpKey) {
		String lang = Options.getLanguage();
		String url = helpKey;
		try {
			// TODO: �berpr�fen, ob Datei wirklich existiert
			if (url.contains("http:")) {
			}
			else {
				url = new File(url).toURL().toString();
			}
			Program prog = Program.findProgram("html");
			if (prog != null)
				prog.execute(url);
			else {
				Runtime.getRuntime().exec(Options.getBrowserExec(url, lang));
			}
		}
		catch (Exception e) {
			try {
				new ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName() + "; "
						+ sos.scheduler.editor.app.Messages.getString("MainListener.cannot_open_help", new String[] { url, lang, e.getMessage() }), e);
			}
			catch (Exception ee) {
				// tu nichts
			}
			e.printStackTrace();
			MainWindow.message(sos.scheduler.editor.app.Messages.getString("MainListener.cannot_open_help", new String[] { url, lang, e.getMessage() }),
					SWT.ICON_ERROR | SWT.OK);
		}
	} // public void openHelp

}
