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
/*
 * Created on 06.04.2011
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.sos.dialog.comparators;

import com.sos.hibernate.classes.SosSortTableItem;


/**
 * 
 * @author Administrator
 * Eigener Comparator der das Vergleichen der einzelnen Tabellenzeilen vornimmt... 
 */

public class StringComparator extends SortBaseComparator implements Comparable {

	/**
	 * Konstruktor ...
	 * @param textBuffer : Der Text der Zeile 
	 * @param rowNum : Die Zeilennr der Tabellenzeile 
	 * @param sortFlag : Aufsteigend false, Absteigend true
	 * @param colPos : Die Spalte nach der Sortiert werden soll
	 */

	public StringComparator(SosSortTableItem tableItem, int rowNum, int colPos) {
		super(tableItem,rowNum,colPos);
	}

	public final int compareTo(Object arg0) {
		if (sosSortTableItem.getTextBuffer()[_colPos] == null) {
			sosSortTableItem.getTextBuffer()[_colPos] = "";
		}
		SosSortTableItem compareItem = null;
		if (((StringComparator) arg0) == null) {
			compareItem = new SosSortTableItem();
			compareItem.setTextBuffer(sosSortTableItem.getTextBuffer());
		}else {
			compareItem = ((StringComparator) arg0).sosSortTableItem;
		}
		
		if  ( compareItem.getTextBuffer()[_colPos] == null){
			 compareItem.getTextBuffer()[_colPos] = "";
		}
		int ret =
			sosSortTableItem.getTextBuffer()[_colPos].compareTo(
					compareItem.getTextBuffer()[_colPos]);
		return _sortFlag ? ret : ret * -1;
	}

}