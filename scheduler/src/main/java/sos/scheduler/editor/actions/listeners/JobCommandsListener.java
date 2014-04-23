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
package sos.scheduler.editor.actions.listeners;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.jdom.Element;

import sos.scheduler.editor.app.Utils;
//import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.actions.ActionsDom;
import sos.scheduler.editor.actions.forms.ActionsForm;

public class JobCommandsListener {


	private    ActionsDom            _dom        = null;

	private    ActionsForm           _main       = null;

	private    Element               _action        = null;

	private    List                  _commands   = null;


	public JobCommandsListener(ActionsDom dom, Element action, ActionsForm update) {

		_dom = dom;
		_main = update;
		_action = action;
		if (_action != null) {
			Element commands = _action.getChild("commands");
			if(commands != null)
				_commands = commands.getChildren("command");
		}

	}		
	
	private void initCommands() {
		if(_action.getChild("commands") == null)
			_action.addContent(new Element("commands"));
		_commands = _action.getChild("commands").getChildren("command");
	}


	public void fillTable(Table table) {

		table.removeAll();
		if (_commands != null) {
			for (Iterator it = _commands.iterator(); it.hasNext();) {
				Object o = it.next();
				if (o instanceof Element) {
					Element e = (Element) o;
					TableItem item = new TableItem(table, SWT.NONE);
					item.setData(e);
					
					item.setText(0, Utils.getAttributeValue("name", e));
					item.setText(1, Utils.getAttributeValue("scheduler_host", e));
					item.setText(2, Utils.getAttributeValue("schdeuler_port", e));
				}
			}
		}

	}


	/*private boolean haveCode(int code, Table table) {
		int count = table.getItemCount();
		for (int i = 0; i < count; i++) {
			TableItem item = table.getItem(i);
			String actCode = item.getText();
			if (actCode.indexOf(" " + String.valueOf(code)) >= 0)
				return true;
			if (actCode.indexOf(String.valueOf(code) + " ") >= 0)
				return true;
			if (actCode.trim().equals(String.valueOf(code)))
				return true;
		}
		return false;
	}*/


	public void newCommands(Table table) {

		
		Element command = new Element("command");
		command.setAttribute("name", "command_" +(table.getItemCount()+1));
		if (_commands == null)
			initCommands();
		_commands.add(command);
		_dom.setChanged(true);
		
		fillTable(table);
		table.setSelection(table.getItemCount() - 1);
		_main.updateCommands();
		
	}


	

	public boolean deleteCommands(Table table) {
		int index = table.getSelectionIndex();
		if (index >= 0) {
			TableItem item = table.getItem(index);
			Element e = (Element) item.getData();
			e.detach();
			_dom.setChanged(true);
			//_dom.setChangedForDirectory("job", Utils.getAttributeValue("name",_job), SchedulerDom.MODIFY);
			table.remove(index);
			_main.updateCommands();

			if (index >= table.getItemCount())
				index--;
			if (index >= 0) {
				table.setSelection(index);
				return true;
			}
		}
		return false;
	}

}
