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
package com.sos.scheduler.history.classes;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.sos.dialog.classes.SOSTableItem;
import com.sos.hibernate.classes.DbItem;
import com.sos.hibernate.interfaces.ISOSTableItem;

/**
* \class SchedulerHistoryTableItem 
* 
* \brief SchedulerHistoryTableItem - 
* 
* \details
*
* \section SchedulerHistoryTableItem.java_intro_sec Introduction
*
* \section SchedulerHistoryTableItem.java_samples Some Samples
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
* \version 14.12.2011
* \see reference
*
* Created on 14.12.2011 13:51:13
 */

public class SchedulerHistoryTableItem extends SOSTableItem implements ISOSTableItem {
    private static final int ERROR_COLUMN_NUMBER = 6;
    private DbItem           dbItem              = null;
    private String[]         textBuffer          = null;
 

    public SchedulerHistoryTableItem(Table arg0, int arg1) {
        super(arg0, arg1);
    }

    public DbItem getData() {
        return (DbItem) super.getData();
    }

    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }

    public void setDBItem(DbItem d) {
        dbItem = d;
        this.setData(d);
    }

    public void setColor() {

        org.eclipse.swt.graphics.Color red = Display.getDefault().getSystemColor(SWT.COLOR_RED);
        org.eclipse.swt.graphics.Color blue = Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
        org.eclipse.swt.graphics.Color white = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
        org.eclipse.swt.graphics.Color gray = Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
        org.eclipse.swt.graphics.Color black = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);

        this.setForeground(black);

        if (dbItem.getEndTime() == null) {
            this.setBackground(0, gray);
            this.setBackground(ERROR_COLUMN_NUMBER, gray);
        }
        else {
            if (dbItem.haveError()) {
                this.setBackground(0, red);
                this.setForeground(0, white);
                this.setBackground(ERROR_COLUMN_NUMBER, red);
                this.setForeground(ERROR_COLUMN_NUMBER, white);
            }
            else {
                this.setBackground(0, white);
                this.setBackground(ERROR_COLUMN_NUMBER, white);
            }
        }
        
        colorSave();
    }

    public void setColumns() {
        DbItem d = dbItem;

        textBuffer = new String[] { "", d.getSpoolerId(), d.getJobOrJobchain(), d.getStartTimeFormated(), d.getEndTimeFormated(), d.getDurationFormated(), d.getExecResult() };

        /*textBuffer = new String[] { d.getSpoolerId(),
                d.getJobName(),
                d.getJobChain(),
                d.getOrderId(),
                d.getStartTimeFormated(), 
                d.getEndTimeFormated(),
                d.getExecResult()
                };
        */

        this.setText(textBuffer);
    }

    public void setColumnsShort() {
        DbItem d = dbItem;

        textBuffer = new String[] { d.getStartTimeFormated(), d.getEndTimeFormated(), d.getDurationFormated(), String.valueOf(d.getExecResult())

        };
        this.setText(textBuffer);
    }

    public String[] getTextBuffer() {
        return textBuffer;
    }

    @SuppressWarnings("unused")
    private final String conClassName = "SchedulerHistoryTableItem";

    @Override
    public Color[] getBackgroundColumn() {
        return colorsBackground;
    }

    @Override
    public Color[] getForegroundColumn() {
        return colorsForeground;
    }

}
