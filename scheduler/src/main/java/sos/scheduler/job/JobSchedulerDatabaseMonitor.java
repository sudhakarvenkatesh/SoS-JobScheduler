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
 * JobSchedulerDatabaseMonitor.java
 * Created on 30.05.2005
 * 
 */
package sos.scheduler.job;

import sos.connection.SOSConnection;
import sos.scheduler.job.JobSchedulerJob;
import sos.settings.SOSProfileSettings;
import sos.util.SOSSchedulerLogger;


public class JobSchedulerDatabaseMonitor extends JobSchedulerJob {

    public boolean spooler_init() {

        try {
            this.setLogger(new SOSSchedulerLogger(spooler_log));
            this.setJobSettings(new SOSProfileSettings(spooler.ini_path()));
            this.setJobProperties(this.getJobSettings().getSection("job " + spooler_job.name()));

            return true;
        } catch (Exception e) {
            try {
                this.getLogger().error("error occurred in spooler_init(): " + e.getMessage());
            } catch (Exception ex) {} // gracefully ignore this error
            return false;
        }
    }
	
	public boolean spooler_process() throws Exception {
        
		boolean new_connection=false;
		SOSConnection connection = this.getConnection();
		
		this.setLogger(new SOSSchedulerLogger(spooler_log));
        
		try{
			if (getJobProperties().getProperty("config") != null) {
	    	    connection = sos.connection.SOSConnection.createInstance(getJobProperties().getProperty("config"));
	            connection.connect();
	    	    new_connection = true;
	        } else {
                throw new Exception("no database connection has been configured by parameter [config]");
            }

		} catch (Exception e) {
			this.getLogger().error("error occurred checking database connection: "+e.getMessage());
			return false;
		} finally {
	        if (new_connection && (connection != null)) {
	            connection.disconnect();
	            connection = null;
	        }
	    }

	    return false;
	}
}
