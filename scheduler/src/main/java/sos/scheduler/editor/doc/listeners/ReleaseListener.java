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
package sos.scheduler.editor.doc.listeners;


import org.jdom.Element;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.doc.DocumentationDom;

public class ReleaseListener {
	
    private DocumentationDom _dom;

    //private Element          _parent;

    private Element          _release;

    //private boolean          _newRelease = false;


    public ReleaseListener(DocumentationDom dom, Element release) {
        _dom = dom;
        //_parent = parent;
        _release = release;
    }


    public String getTitle() {
        return Utils.getElement("title", _release, _dom.getNamespace());
    }


    public void setTitle(String title) {
        //Utils.setAttribute("title", title, _release, _dom);
        Utils.setElement("title", title, false, _release, _dom.getNamespace(), _dom);
    }

    
    public String getID() {
        return Utils.getAttributeValue("id", _release);
    }

    public void setId(String id) {
        Utils.setAttribute("id", id, _release, _dom);
    }

    public String getCreated() {
        return Utils.getAttributeValue("created", _release);
    }
    
    public void setCreated(String created) {
    	Utils.setAttribute("created", created, _release, _dom);
    }

    public String getModified() {
        return Utils.getAttributeValue("modified", _release);
    }

    public void setModified(String modified) {
    	 Utils.setAttribute("modified", modified, _release);
    }
    
    public Element getRelease() {
        return _release;
    }

    public String getNote(String which) {
    	if(_release != null && !_release.getChildren(which, _release.getNamespace()).isEmpty())
    		return _dom.noteAsStr(_release.getChild(which, _release.getNamespace()));
    	else 
    		return "";
    }
    
    public void applyRelease(String title, String id, String created, String modified) {
        Utils.setAttribute("id", id, _release);
        Utils.setAttribute("created", created, _release);
        Utils.setAttribute("modified", modified, _release);
        Utils.setElement("title", title, false, _release, _dom.getNamespace(), _dom);

              
        _dom.setChanged(true);
    }


    
}