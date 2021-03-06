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
package com.sos.jitl.housekeeping.cleanupdb;

import java.io.File;

import org.apache.log4j.Logger;

import sos.ftphistory.db.JadeFilesDBLayer;

import com.sos.JSHelper.Basics.JSJobUtilities;
import com.sos.JSHelper.Basics.JSJobUtilitiesClass;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.dailyschedule.db.DailyScheduleDBLayer;
import com.sos.scheduler.history.db.SchedulerOrderHistoryDBLayer;
import com.sos.scheduler.history.db.SchedulerTaskHistoryDBLayer;
import com.sos.scheduler.messages.JSMessages;

/**
 * \class 		JobSchedulerCleanupSchedulerDb - Workerclass for "Delete log entries in the Job Scheduler history Databaser tables"
 *
 * \brief AdapterClass of JobSchedulerCleanupSchedulerDb for the SOSJobScheduler
 *
 * This Class JobSchedulerCleanupSchedulerDb is the worker-class.
 *

 *
 * see \see C:\Dokumente und Einstellungen\Uwe Risse\Lokale Einstellungen\Temp\scheduler_editor-3271913404894833399.html for (more) details.
 *
 * \verbatim ;
 * mechanicaly created by C:\Dokumente und Einstellungen\Uwe Risse\Eigene Dateien\sos-berlin.com\jobscheduler\scheduler_ur_current\config\JOETemplates\java\xsl\JSJobDoc2JSWorkerClass.xsl from http://www.sos-berlin.com at 20121211160841
 * \endverbatim
 */
public class JobSchedulerCleanupSchedulerDb extends JSJobUtilitiesClass<JobSchedulerCleanupSchedulerDbOptions> {
	private final String							conClassName		= "JobSchedulerCleanupSchedulerDb";						//$NON-NLS-1$
	private static Logger							logger				= Logger.getLogger(JobSchedulerCleanupSchedulerDb.class);

	protected JobSchedulerCleanupSchedulerDbOptions	objOptions			= null;
	private final JSJobUtilities							objJSJobUtilities	= this;

	/**
	 *
	 * \brief JobSchedulerCleanupSchedulerDb
	 *
	 * \details
	 *
	 */
	public JobSchedulerCleanupSchedulerDb() {
		super(new JobSchedulerCleanupSchedulerDbOptions());
	}

	/**
	 *
	 * \brief Options - returns the JobSchedulerCleanupSchedulerDbOptionClass
	 *
	 * \details
	 * The JobSchedulerCleanupSchedulerDbOptionClass is used as a Container for all Options (Settings) which are
	 * needed.
	 *
	 * \return JobSchedulerCleanupSchedulerDbOptions
	 *
	 */
	@Override
	public JobSchedulerCleanupSchedulerDbOptions Options() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Options"; //$NON-NLS-1$

		if (objOptions == null) {
			objOptions = new JobSchedulerCleanupSchedulerDbOptions();
		}
		return objOptions;
	}

	/**
	 *
	 * \brief Execute - Start the Execution of JobSchedulerCleanupSchedulerDb
	 *
	 * \details
	 *
	 * For more details see
	 *
	 * \see JobSchedulerAdapterClass
	 * \see JobSchedulerCleanupSchedulerDbMain
	 *
	 * \return JobSchedulerCleanupSchedulerDb
	 *
	 * @return
	 */
	public JobSchedulerCleanupSchedulerDb Execute() throws Exception {
		final String conMethodName = conClassName + "::Execute"; //$NON-NLS-1$

		logger.debug(String.format(JSMessages.JSJ_I_110.get(), conMethodName));

		try {
			Options().CheckMandatory();
			logger.debug(Options().dirtyString());

			SchedulerOrderHistoryDBLayer schedulerOrderHistoryDBLayer = new SchedulerOrderHistoryDBLayer(new File(
					Options().hibernate_configuration_file.Value()));
			if (!Options().delete_history_interval.isDirty()) {
				Options().delete_history_interval.Value(Options().delete_interval.Value());
			}
			int i = schedulerOrderHistoryDBLayer.deleteInterval(Options().delete_history_interval.Value());
			logger.info(String.format("%s records deleted from SCHEDULER_ORDER_HISTORY that are older than %s days", i, Options().delete_history_interval.Value()));

			SchedulerTaskHistoryDBLayer schedulerTaskHistoryDBLayer = new SchedulerTaskHistoryDBLayer(new File(Options().hibernate_configuration_file.Value()));
			schedulerTaskHistoryDBLayer.setSession(schedulerOrderHistoryDBLayer);
			schedulerTaskHistoryDBLayer.beginTransaction();
			i = schedulerTaskHistoryDBLayer.deleteInterval(Options().delete_history_interval.Value());

			logger.info(String.format("%s records deleted from SCHEDULER_HISTORY that are older than %s days", i, Options().delete_history_interval.Value()));

			DailyScheduleDBLayer dailyScheduleDBLayer = new DailyScheduleDBLayer(new File(Options().hibernate_configuration_file.Value()));
			dailyScheduleDBLayer.setSession(schedulerOrderHistoryDBLayer);
			if (!Options().delete_daily_plan_interval.isDirty()) {
				Options().delete_daily_plan_interval.Value(Options().delete_interval.Value());
			}
			i = dailyScheduleDBLayer.deleteInterval(Options().delete_daily_plan_interval.Value());
			logger.info(String.format("%s records deleted from DAYS_SCHEDULE that are older than %s days", i, Options().delete_history_interval.Value()));

			JadeFilesDBLayer jadeFilesDBLayer = new JadeFilesDBLayer(new File(Options().hibernate_configuration_file.Value()));
			jadeFilesDBLayer.setSession(schedulerOrderHistoryDBLayer);
			if (!Options().delete_ftp_history_interval.isDirty()) {
				Options().delete_ftp_history_interval.Value(Options().delete_interval.Value());
			}
			i = jadeFilesDBLayer.deleteInterval(Options().delete_ftp_history_interval.Value());
			logger.info(String.format("%s records deleted from SOSFTP_FILES that are older than %s days", i, Options().delete_ftp_history_interval.Value()));

			schedulerOrderHistoryDBLayer.commit();
			schedulerOrderHistoryDBLayer.closeSession();

		}
		catch (Exception e) {
			String strM = String.format(JSMessages.JSJ_F_107.get(), conMethodName);
			throw new JobSchedulerException(strM, e);
		}
		finally {
			logger.debug(String.format(JSMessages.JSJ_I_111.get(), conMethodName));
		}

		return this;
	}

	public void init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::init"; //$NON-NLS-1$
		doInitialize();
	}

	private void doInitialize() {
	} // doInitialize

} // class JobSchedulerCleanupSchedulerDb
