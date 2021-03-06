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
/*
 * Created on 03.03.2011
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package sos.scheduler.InstallationService.batchInstallationModel;

import java.io.File;

import org.apache.log4j.Logger;

import sos.scheduler.InstallationService.batchInstallationModel.installations.Globals;
import sos.scheduler.InstallationService.batchInstallationModel.installations.Installation;
public class JSinstallation extends Installation {
 

   protected Globals globals;
   private File installationFile = null;
   private static Logger		logger				= Logger.getLogger(JSBatchInstallerExecuter.class);

	
   private String getValue(String installationValue, String globalValue) {
	  if (globalValue == null) {
		 globalValue = "";
	  } 
	  if (installationValue == null) {
		 installationValue = "";
	  }
	  if (installationValue.length() > 0 || globalValue.equals("")) {
		 return installationValue;
	  }
	  else {
		 return globalValue;
	  }
   }

   private Short getValue(Short installationValue, Short globalValue) {
	  if (globalValue == null) {
		 globalValue = 0;
	  }
	  if (installationValue == null) {
		 installationValue = 0;
	  }
	  if (installationValue != 0 || globalValue == 0) {
		 return installationValue;
	  }
	  else {
		 return globalValue;
	  }
   }
   
  
   public File getInstallationFile(File configurationPath) {
	  if (installationFile == null) {
		 installationFile = new File(configurationPath,this.getHost() + "_" + this.getSchedulerPort() + ".xml");
	  }
      return installationFile;
   }

   private String replace(String parameterValue,String parameterName,String newValue) {
		return parameterValue.replaceAll("\\$\\{" + parameterName + "\\}",newValue);
   }
   
   private String replaceAll(String value) {
	  if (value == null){
		 return value;
	  }
	  value = this.replace(value,"host",this.getHost());
	  value = this.replace(value,"licence",this.getLicence());
	  value = this.replace(value,"licence_options",this.getLicenceOptions());
	  value = this.replace(value,"install_path",this.getInstallPath());
	  value = this.replace(value,"scheduler_port",String.valueOf(this.getSchedulerPort()));
	  value = this.replace(value,"scheduler_allowed_host",this.getSchedulerAllowedHost());
	  value = this.replace(value,"scheduler_id",this.getSchedulerId());
	  value = this.replace(value,"userPathPanelElement",this.getUserPathPanelElement());

	  
	  //FTP
	  value = this.replace(value,"ftp_local_dir",this.getFtp().getLocalDir());
	  value = this.replace(value,"ftp_password",this.getFtp().getPassword());
	  value = this.replace(value,"ftp_port",String.valueOf(this.getFtp().getPort()));
	  value = this.replace(value,"ftp_localDir",this.getFtp().getLocalDir());
	  value = this.replace(value,"ftp_remote_dir",this.getFtp().getRemoteDir());
	  value = this.replace(value,"ftp_user",this.getFtp().getUser());
	  
	  //SSH
	  value = this.replace(value,"auth_method",this.getSsh().getAuthMethod());
	  value = this.replace(value,"command",this.getSsh().getCommand());
	  value = this.replace(value,"password",this.getSsh().getPassword());
	  value = this.replace(value,"sudo_password",this.getSsh().getSudoPassword());
	  value = this.replace(value,"port",String.valueOf(this.getSsh().getPort()));
      value = this.replace(value,"user",this.getSsh().getUser());
      if (this.installationFile == null) {
	      logger.debug("Installationfile is not set. Will not be replaces");
	      this.installationFile = new File(""); //Avoid multiple output of this debug.
      }else {
    	  if (!this.installationFile.getName().equals("")) {
        	  value = this.replace(value,"installation_file",this.installationFile.getName());
    	  }
    	  value = this.replace(value,"installation_file",this.installationFile.getName());
    	  
      }
	  
	  return value;
   }
   public void doReplacing() {
	  this.setHost(replaceAll(this.getHost()));
	  this.setLicence(replaceAll(this.getLicence()));
	  this.setLicenceOptions(replaceAll(this.getLicenceOptions()));
 	  this.setInstallPath(replaceAll(this.getInstallPath()));
	  this.setSchedulerAllowedHost(replaceAll(this.getSchedulerAllowedHost()));
	  this.setSchedulerId(replaceAll(this.getSchedulerId()));
 	  this.setUserPathPanelElement(replaceAll(this.getUserPathPanelElement()));
	  
	  this.getFtp().setLocalDir(replaceAll(this.getFtp().getLocalDir()));
	  this.getFtp().setPassword(replaceAll(this.getFtp().getPassword()));
 	  this.getFtp().setRemoteDir(replaceAll(this.getFtp().getRemoteDir()));
	  this.getFtp().setUser(replaceAll(this.getFtp().getUser()));
 	  
	  this.getSsh().setAuthMethod(replaceAll(this.getSsh().getAuthMethod()));
	  this.getSsh().setAuthFile(replaceAll(this.getSsh().getAuthFile()));
	  this.getSsh().setCommand(replaceAll(this.getSsh().getCommand()));
	  this.getSsh().setSudoPassword(replaceAll(this.getSsh().getSudoPassword()));
	  this.getSsh().setPassword(replaceAll(this.getSsh().getPassword()));
 	  this.getSsh().setUser(replaceAll(this.getSsh().getUser()));

   }
   
   public void setValues(Installation installation) {

	 /*1. Die Werte aus dem Marshal-Objekt hier setzen
	   *2. Dabei die globalen Werte ber�cksichtigen
	   *3. Ersetzungen ${} durchf�hren
	   * 
	   */
      this.setLicence(getValue(installation.getLicence(), globals.getLicence()));
	  this.setLicenceOptions(getValue(installation.getLicenceOptions(), globals.getLicenceOptions()));
	  this.setSchedulerPort(getValue(installation.getSchedulerPort(), globals.getSchedulerPort()));
	  this.setInstallPath(getValue(installation.getInstallPath(), globals.getInstallPath()));
	  this.setSchedulerAllowedHost(getValue(installation.getSchedulerAllowedHost(), globals.getSchedulerAllowedHost()));
	  this.setSchedulerId(getValue(installation.getSchedulerId(), globals.getSchedulerId()));
	  this.setUserPathPanelElement(getValue(installation.getUserPathPanelElement(), globals.getUserPathPanelElement()));
	  this.setHost(installation.getHost());
	  this.setLastRun(installation.getLastRun());
 	  

	  installation.getFtp().setLocalDir(getValue(installation.getFtp().getLocalDir(), globals.getFtp().getLocalDir()));
	  installation.getFtp().setPassword(getValue(installation.getFtp().getPassword(), globals.getFtp().getPassword()));
	  installation.getFtp().setPort(getValue(installation.getFtp().getPort(), globals.getFtp().getPort()));
	  installation.getFtp().setRemoteDir(getValue(installation.getFtp().getRemoteDir(), globals.getFtp().getRemoteDir()));
	  installation.getFtp().setUser(getValue(installation.getFtp().getUser(), globals.getFtp().getUser()));
	  this.setFtp(installation.getFtp());
	  
	  installation.getSsh().setAuthMethod(getValue(installation.getSsh().getAuthMethod(), globals.getSsh().getAuthMethod()));
	  installation.getSsh().setCommand(getValue(installation.getSsh().getCommand(), globals.getSsh().getCommand()));
	  installation.getSsh().setPassword(getValue(installation.getSsh().getPassword(), globals.getSsh().getPassword()));
	  installation.getSsh().setSudoPassword(getValue(installation.getSsh().getSudoPassword(), globals.getSsh().getSudoPassword()));
	  installation.getSsh().setPort(getValue(installation.getSsh().getPort(), globals.getSsh().getPort()));
	  installation.getSsh().setUser(getValue(installation.getSsh().getUser(), globals.getSsh().getUser()));
	  installation.getSsh().setAuthFile(getValue(installation.getSsh().getAuthFile(), globals.getSsh().getAuthFile()));
	  this.setSsh(installation.getSsh());
	  
	  
   }
}
