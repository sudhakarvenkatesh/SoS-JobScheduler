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
/* $Id: Update_cmd.java,v 1.5 2004/01/23 09:19:42 jz Exp $
 * 
 * Created on 20.10.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
 
 
package sos.ftphistory.sql;

/**
 * @author joacim
 *
 */


//import java.sql.*;
import java.util.ArrayList;

import sos.connection.SOSConnection;
import sos.ftphistory.sql.Write_cmd.Field_value;
import sos.util.SOSLogger;



public class Update_cmd extends Write_cmd 
{
    public boolean  single = false;
    
    String          where;
    
    //-----------------------------------------------------------------------------------Update_cmd
    
    public Update_cmd(SOSConnection conn_,SOSLogger logger_  )
    {
        super(conn_,logger_ );
    }

    //-----------------------------------------------------------------------------------Update_cmd

    public Update_cmd(SOSConnection conn_,SOSLogger logger_ , String table_name )
    {
        this(conn_,logger_ );
        set_table_name( table_name );
    }
    //------------------------------------------------------------------------------------set_where
    /**
     * Setzt die Bedingung der Where-Klausel.
     * 
     */
    public void set_where( String where_condition ) 
    {
        where = where_condition;
    }
    
    //------------------------------------------------------------------------------------make_cmd_
	/* (non-Javadoc)
	 * @see sos.dod.orderprepare.sql.Cmd#make_stmt()
	 */
    
     
	String make_cmd_() throws Exception
    {
        String settings = new String();
        String q = "";
        
        if (withQuote) {
        	q = "\"";
        }

        for( int i = 0; i < field_value_list.size(); i++ )
        {
            Field_value fv = (Field_value)field_value_list.get(i);

            if( settings.length() > 0 )  settings += ", ";
            settings += q + fv.field_name + q + "=" + fv.sql_value();
        }

    
        
        return conn.normalizeStatement("UPDATE " + table_name + " SET " + settings + " WHERE " + where);
	}
	
    public void copyFieldsFrom(Write_cmd cmd) {
       this.field_value_list = new ArrayList(50);   	
       for( int i = 0; i < cmd.field_value_list.size(); i++ ) {
           this.field_value_list.add(  cmd.field_value_list.get(i)) ; 
       }
     }
  
}
