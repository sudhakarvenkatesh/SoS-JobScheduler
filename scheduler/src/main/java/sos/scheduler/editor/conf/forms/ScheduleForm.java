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
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.Messages;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;
import sos.scheduler.editor.conf.listeners.ScheduleListener;
import org.jdom.Element;

import com.sos.dialog.components.SOSDateTime;

import sos.scheduler.editor.app.Utils;

public class ScheduleForm extends SOSJOEMessageCodes implements IUpdateLanguage {

    private ScheduleListener listener = null;
    private Group scheduleGroup = null;
    private SchedulerDom dom = null;
    private Text txtName = null;
    private boolean init = true;
    private Text txtTitle = null;
    private SOSDateTime validFromDate = null;
    private SOSDateTime validToDate = null;
    private SOSDateTime validFromTime = null;
    private SOSDateTime validToTime = null;
    private Combo cboCombo = null;

    public ScheduleForm(Composite parent, int style, SchedulerDom dom, org.jdom.Element schedule_, ISchedulerUpdate update) {
        super(parent, style);
        try {
            init = true;
            this.dom = dom;
            listener = new ScheduleListener(dom, update, schedule_);
            initialize();
            setToolTipText();
            init = false;

        } catch (Exception e) {
            try {
                new sos.scheduler.editor.app.ErrorLog(JOE_E_0002.params(sos.util.SOSClassUtil.getMethodName()), e);
            } catch (Exception ee) {
                // tu nichts
            }
            System.err.println(JOE_E_0002.params("ScheduleForm.init() ") + e.getMessage());
        }
    }

    private void initialize() {
        try {
            this.setLayout(new FillLayout());
            createGroup();

            txtName.setText(listener.getName());
            txtTitle.setText(listener.getTitle());

            String d = listener.getValidFrom();
            validFromDate.setDate(d);
            validFromTime.setTime(d);

            d = listener.getValidTo();
            validToDate.setDate(d);
            validToTime.setTime(d);

            String[] s = listener.getAllSchedules();
            if (s != null)
                cboCombo.setItems(s);

            cboCombo.setText(listener.getSubstitute());

            setSize(new org.eclipse.swt.graphics.Point(656, 400));
            txtName.setFocus();
        } catch (Exception e) {
            try {
                new sos.scheduler.editor.app.ErrorLog(JOE_E_0002.params(sos.util.SOSClassUtil.getMethodName()), e);
            } catch (Exception ee) {
                // tu nichts
            }
            System.err.println(JOE_E_0002.params("ScheduleForm.initialize() ") + e.getMessage());
        }
    }

    /**
     * This method initializes group
     */
    private void createGroup() {
        try {
            GridLayout gridLayout = new GridLayout();
            gridLayout.numColumns = 3;
            scheduleGroup = JOE_G_ScheduleForm_Schedule.Control(new Group(this, SWT.NONE));
            scheduleGroup.setLayout(gridLayout);

            @SuppressWarnings("unused")
			final Label nameLabel = JOE_L_Name.Control(new Label(scheduleGroup, SWT.NONE));

            txtName = JOE_T_ScheduleForm_Name.Control(new Text(scheduleGroup, SWT.BORDER));
            txtName.addVerifyListener(new VerifyListener() {
                public void verifyText(final VerifyEvent e) {
                    if (!init)// w�hrend der initialiserung sollen keine
                              // �berpr�fungen stattfinden
                        e.doit = Utils.checkElement(txtName.getText(), dom, sos.scheduler.editor.app.Editor.SCHEDULE, null);
                }
            });
            txtName.addModifyListener(new ModifyListener() {
                public void modifyText(final ModifyEvent e) {
                    if (!init && !existScheduleName())
                        listener.setName(txtName.getText());
                }
            });
            txtName.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false, 2, 1));

            final Label titleLabel = JOE_L_ScheduleForm_Title.Control(new Label(scheduleGroup, SWT.NONE));
            titleLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));

            txtTitle = JOE_T_ScheduleForm_Title.Control(new Text(scheduleGroup, SWT.BORDER));
            txtTitle.addModifyListener(new ModifyListener() {
                public void modifyText(final ModifyEvent e) {
                    if (!init)
                        listener.setTitle(txtTitle.getText());
                }
            });
            txtTitle.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false, 2, 1));

            new Label(scheduleGroup, SWT.NONE);
            new Label(scheduleGroup, SWT.NONE);
            new Label(scheduleGroup, SWT.NONE);
            
            @SuppressWarnings("unused")
			final Label substitueLabel = JOE_L_ScheduleForm_Substitute.Control(new Label(scheduleGroup, SWT.NONE));

            cboCombo = JOE_Cbo_ScheduleForm_Substitute.Control(new Combo(scheduleGroup, SWT.NONE));
            cboCombo.addModifyListener(new ModifyListener() {
                public void modifyText(final ModifyEvent e) {
                    if (!init)
                        listener.setSubstitut(cboCombo.getText());
                }
            });
            cboCombo.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false, 2, 1));

            Label validFromLabel = JOE_L_ScheduleForm_ValidFrom.Control(new Label(scheduleGroup, SWT.NONE));
            validFromLabel.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));

            validFromDate = JOE_ScheduleForm_ValidFromDate.Control(new SOSDateTime(scheduleGroup, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN));
            validFromDate.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    setValidDateFrom();
                }
            });

            validFromTime = JOE_ScheduleForm_ValidFromTime.Control(new SOSDateTime(scheduleGroup, SWT.BORDER | SWT.TIME | SWT.DROP_DOWN));
            validFromTime.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    setValidDateFrom();
                }
            });

            @SuppressWarnings("unused")
			final Label validToLabel = JOE_L_ScheduleForm_ValidTo.Control(new Label(scheduleGroup, SWT.NONE));

            validToDate = JOE_ScheduleForm_ValidToDate.Control(new SOSDateTime(scheduleGroup, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN));
            validToDate.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    setValidDateTo();
                }
            });
            
            validToTime = JOE_ScheduleForm_ValidToTime.Control(new SOSDateTime(scheduleGroup, SWT.BORDER | SWT.TIME | SWT.DROP_DOWN));
            validToTime.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    setValidDateTo();
                }
            });

            
        } catch (Exception e) {
            try {
                new sos.scheduler.editor.app.ErrorLog(JOE_E_0002.params(sos.util.SOSClassUtil.getMethodName()), e);
            } catch (Exception ee) {
                // tu nichts
            }
            System.err.println(JOE_E_0002.params("ScheduleForm.createGroup() ") + e.getMessage());
        }
    }

    public void setToolTipText() {
//
    }

    private boolean existScheduleName() {
        boolean retVal = false;
        if (!dom.isLifeElement()) {
            if (listener.getSchedule() != null && listener.getSchedule().getParentElement() != null) {
                Element parent = listener.getSchedule().getParentElement();
                java.util.List l = parent.getChildren("schedule");
                for (int i = 0; i < l.size(); i++) {
                    Element el = (Element) l.get(i);
                    if (Utils.getAttributeValue("name", el).equals(txtName.getText())) {
                        retVal = true;
                        break;
                    }
                }
            }
        }
        if (retVal)
            txtName.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW));
        else
            txtName.setBackground(null);
        return retVal;
    }

    private void setValidDateTo() {
        if (!init) {
            try {
                listener.setValidTo(validToDate.getISODate() + " " + validToTime.getISOTime());
            } catch (Exception es) {
                es.printStackTrace();
            }
        }
    }

    private void setValidDateFrom() {
        if (!init) {
            try {
                listener.setValidFrom(validFromDate.getISODate()  + " " + validFromTime.getISOTime());
            } catch (Exception es) {
                es.printStackTrace();
            }
        }
    }

} // @jve:decl-index=0:visual-constraint="10,10"
