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
package com.sos.jobnet.db;

import com.sos.hibernate.layer.SOSHibernateDBLayer;
import com.sos.jobnet.classes.NodeStatus;
import org.apache.log4j.Logger;
import org.hibernate.Query;

import java.io.File;
import java.util.List;

// import org.apache.log4j.Logger;

/**
 * 
 * \class JobnetPlanDBLayer \brief JobnetPlanDBLayer -
 * 
 * \details
 * 
 * \section JobnetPlanDBLayer.java_intro_sec Introduction
 * 
 * \section JobnetPlanDBLayer.java_samples Some Samples
 * 
 * \code .... code goes here ... \endcode
 * 
 * <p style="text-align:center">
 * <br />
 * --------------------------------------------------------------------------- <br />
 * APL/Software GmbH - Berlin <br />
 * ##### generated by ClaviusXPress (http://www.sos-berlin.com) ######### <br />
 * ---------------------------------------------------------------------------
 * </p>
 * \author Uwe Risse \version 13.09.2011 \see reference
 * 
 * Created on 13.09.2011 14:40:18
 */

public class JobNetPlanDBLayer extends SOSHibernateDBLayer {
	
	private final static Logger logger = Logger.getLogger(JobNetPlanDBLayer.class);

    @SuppressWarnings("unused")
    private final String conClassName = "JobnetPlanDBLayer";
    public final String conSVNVersion = "$Id: JobNetPlanDBLayer.java 21040 2013-09-09 16:51:14Z ss $";

    private static final String PLAN_ID = "planId";
    private static final String NODE_ID = "nodeId";
    private static final String SUBNET_ID = "subnetId";
    private static final String CONNECTOR_ID = "connectorId";
    private static final String STATUS = "status";
    private static final String IS_DISPATCHER_SKIPPED = "isDispatcherSkipped";
    private static final String IS_RUNNER_SKIPPED = "isRunnerSkipped";
    private static final String IS_WAITER_SKIPPED = "isWaiterSkipped";
    private static final String IS_RUNNER_ON_DEMAND = "isRunnerOnDemand";
    private static final String STATUS_DISPATCHER = "statusDispatcher";
    private static final String STATUS_RUNNER = "statusRunner";
    private static final String STATUS_WAITER = "statusWaiter";
    private static final String JOBNET = "jobnet";
    private static final String NODE = "node";
    private static final String NODE_TYPE = "nodeType";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String UUID = "uuid";
    private static final String SCHEDULER_ID = "schedulerId";
    private static final String START_TIME_TO = "startTimeTo";
    private static final String START_TIME_FROM = "startTimeFrom";
    private static final String PLANNED_START_TIME_FROM = "plannedStartTimeFrom";
    private static final String PLANNED_START_TIME_TO = "plannedStartTimeTo";
    private static final String BOOTSTRAP = "bootstrap";

    private JobNetPlanFilter filter = null;


    public JobNetPlanDBLayer(File configurationFile_, String schedulerId_) {
        super();

        this.filter = new JobNetPlanFilter();

        this.filter.setSchedulerId(schedulerId_);

        this.setConfigurationFile(configurationFile_);
        this.filter = new JobNetPlanFilter();
        this.filter.setDateFormat(DATE_FORMAT);
        this.filter.setOrderCriteria(UUID);
        this.filter.setSchedulerId(schedulerId_);
    }

    public JobNetPlanDBLayer(File configurationFile_) {
        super();
        this.filter = new JobNetPlanFilter();

        this.setConfigurationFile(configurationFile_);
        this.filter = new JobNetPlanFilter();
        this.filter.setDateFormat(DATE_FORMAT);
        this.filter.setOrderCriteria(UUID);
     }
    
    private Query setQueryParams(String hql) {
        Query query = session.createQuery(hql);

        if (filter.getPlannedStartTimeFrom() != null && !filter.getPlannedStartTimeFrom().equals("")) {
            query.setTimestamp(PLANNED_START_TIME_FROM, filter.getPlannedStartTimeFrom());
        }

        if (filter.getPlannedStartTimeTo() != null && !filter.getPlannedStartTimeTo().equals("")) {
            query.setTimestamp(PLANNED_START_TIME_TO, filter.getPlannedStartTimeTo());
        }

        if (filter.getStartTimeFrom() != null && !filter.getStartTimeFrom().equals("")) {
            query.setTimestamp(START_TIME_FROM, filter.getStartTimeFrom());
        }

        if (filter.getStartTimeTo() != null && !filter.getStartTimeTo().equals("")) {
            query.setTimestamp(START_TIME_TO, filter.getStartTimeTo());
        }

        if (filter.getSchedulerId() != null && !filter.getSchedulerId().equals("")) {
            query.setParameter(SCHEDULER_ID, filter.getSchedulerId());
        }

        if (filter.getUuid() != null && !filter.getUuid().equals("")) {
            query.setParameter(UUID, filter.getUuid());
        }

        if (filter.getNode() != null && !filter.getNode().equals("")) {
            query.setParameter(NODE, filter.getNode());
        }

        if (filter.getNodeId() != null) {
            query.setLong(NODE_ID, filter.getNodeId());
        }

        if (filter.getSubnetId() != null) {
            query.setLong(SUBNET_ID, filter.getSubnetId());
        }

        if (filter.getConnectorId() != null) {
            query.setLong(CONNECTOR_ID, filter.getConnectorId());
        }

        if (filter.getNodeType() != null) {
            query.setParameter(NODE_TYPE, filter.getNodeType());
        }

        if (filter.getPlanId() != null) {
            query.setLong(PLAN_ID, filter.getPlanId());
        }

        if (filter.getJobnet() != null && !filter.getJobnet().equals("")) {
            query.setParameter(JOBNET, filter.getJobnet());
        }

        if (filter.getStatus() != null && !filter.getStatus().equals("")) {
            query.setInteger(STATUS, filter.getStatus());
        }

        if (filter.getStatusWaiter() != null && !filter.getStatusWaiter().equals("")) {
            query.setInteger(STATUS_WAITER, filter.getStatusWaiter());
        }

        if (filter.getStatusRunner() != null && !filter.getStatusRunner().equals("")) {
            query.setInteger(STATUS_RUNNER, filter.getStatusRunner());
        }

        if (filter.getStatusDispatcher() != null && !filter.getStatusDispatcher().equals("")) {
            query.setInteger(STATUS_DISPATCHER, filter.getStatusDispatcher());
        }

        if (filter.getIsWaiterSkipped() != null) {
            query.setBoolean(IS_WAITER_SKIPPED, filter.getIsWaiterSkipped());
        }

        if (filter.getIsRunnerOnDemand() != null) {
            query.setBoolean(IS_RUNNER_ON_DEMAND, filter.getIsRunnerOnDemand());
        }
        
        if (filter.getIsRunnerSkipped() != null) {
            query.setBoolean(IS_RUNNER_SKIPPED, filter.getIsRunnerSkipped());
        }

        
        if (filter.getBootstrap() != null) {
            query.setBoolean(BOOTSTRAP, filter.getBootstrap());
        }

        if (filter.getIsDispatcherSkipped() != null) {
            query.setBoolean(IS_DISPATCHER_SKIPPED, filter.getIsDispatcherSkipped());
        }

        return query;

    }

    public int delete() {

        String hql = "delete from JobNetPlanDBItem " + getWhere();

        Query query = setQueryParams(hql);
        int row = query.executeUpdate();

        return row;
    }

    private String getWhere() {
        String where = "";
        String and = "";

        if (filter.getPlannedStartTimeFrom() != null && !filter.getPlannedStartTimeFrom().equals("")) {
            where += and + " plannedStartTime>= :plannedStartTimeFrom";
            and = " and ";
        }

        if (filter.getPlannedStartTimeTo() != null && !filter.getPlannedStartTimeTo().equals("")) {
            where += and + " plannedStartTime<= :plannedStartTimeTo";
            and = " and ";
        }

        if (filter.getStartTimeFrom() != null && !filter.getStartTimeFrom().equals("")) {
            where += and + " startTime>= :startTimeFrom";
            and = " and ";
        }

        if (filter.getStartTimeTo() != null && !filter.getStartTimeTo().equals("")) {
            where += and + " startTime< :startTimeTo";
            and = " and ";
        }

        /*
         * if (filter.getEndTimeFrom() != null &&
         * !filter.getEndTimeFrom().equals("")) { where += and +
         * " endTimeFrom>= :endTimeFrom"; and = " and "; }
         * 
         * if (filter.getEndTimeTo() != null &&
         * !filter.getEndTimeTo().equals("")) { where += and +
         * " endTimeTo< :endTimeTo"; and = " and "; }
         */

        if (!filter.getSchedulerId().equals("")) {
            where += and + " plan.jobnetNodeDBItem.schedulerId = :schedulerId";
            and = " and "; 
        }

        if (filter.getUuid() != null && !filter.getUuid().equals("")) {
            where += and + " uuid = :uuid";
            and = " and ";
        }

        if (filter.getNode() != null && !filter.getNode().equals("")) {
            where += and + " plan.jobnetNodeDBItem.node = :node";
            and = " and ";
        }

        if (filter.getNodeId() != null) {
            where += and + " nodeId = :nodeId";
            and = " and ";
        }

        if (filter.getSubnetId() != null) {
            where += and + " subnetId = :subnetId";
            and = " and ";
        }

        if (filter.getNodeType() != null) {
            where += and + " nodeType = :nodeType";
            and = " and ";
        }

        if (filter.getConnectorId() != null) {
            where += and + " connectorId = :connectorId";
            and = " and ";
        }

        if (filter.getPlanId() != null) {
            where += and + " planId = :planId";
            and = " and ";
        }

        if (filter.getJobnet() != null && !filter.getJobnet().equals("")) {
            where += and + " plan.jobnetNodeDBItem.jobnet = :jobnet";
            and = " and ";
        }

        if (filter.getStatus() != null) {
            where += and + " status  = :status";
            and = " and ";
        }

        if (filter.getStatusDispatcher() != null) {
            where += and + " statusDispatcher = :statusDispatcher";
            and = " and ";
        }

        if (filter.getStatusRunner() != null) {
            where += and + " statusRunner = :statusRunner";
            and = " and ";
        }

        if (filter.getStatusWaiter() != null) {
            where += and + " statusWaiter = :statusWaiter";
            and = " and ";
        }

        if (filter.getIsWaiterSkipped() != null) {
            where += and + " isWaiterSkipped = :isWaiterSkipped";
            and = " and ";
        }

        if (filter.getIsRunnerSkipped() != null) {
            where += and + " isRunnerSkipped = :isRunnerSkipped";
            and = " and ";
        }


        if (filter.getIsRunnerOnDemand() != null) {
            where += and + " isRunnerOnDemand= :isRunnerOnDemand";
            and = " and ";
        }

        if (filter.getIsDispatcherSkipped() != null) {
            where += and + " isDispatcherSkipped = :isDispatcherSkipped";
            and = " and ";
        }

        if (filter.getBootstrap() != null) {
            where += and + " bootstrap = :bootstrap";
            and = " and ";
        }

        if (where.trim().equals("")) {

        } else {
            where = "where " + where;
        }
        return where;

    }

    public List<JobNetPlanDBItem> getJobnetPlanList(int limit) {
//        initSession();
    	if (!getSession().isOpen()) initSession();

        Query query = setQueryParams("from JobNetPlanDBItem plan " + getWhere() + this.filter.getOrderCriteria() + this.filter.getSortMode());

        if (limit > 0) {
            query.setMaxResults(limit);
        }

        @SuppressWarnings("unchecked")
		List<JobNetPlanDBItem> jobnetPlanList = query.list();
        return jobnetPlanList;

    }

    public List<JobNetPlanDBItem> getJobnetPlanListWithLock(int limit) {
//        initSession();
    	if (!getSession().isOpen()) initSession();

        Query query = setQueryParams("from JobNetPlanDBItem plan " + getWhere() + this.filter.getOrderCriteria() + this.filter.getSortMode());
        if (limit > 0) {
            query.setMaxResults(limit);
        }

        // query.setLockOptions(new LockOptions(LockMode.PESSIMISTIC_WRITE));
        @SuppressWarnings("unchecked")
		List<JobNetPlanDBItem> jobnetPlanList = query.list();
        return jobnetPlanList;

    }
    
	public boolean isJobnetEnded(String uuid) {
		boolean result = true;
		JobNetPlanFilter jobNetPlanFilter = new JobNetPlanFilter();
		jobNetPlanFilter.setUuid(uuid);
		setFilter(jobNetPlanFilter);
		List<JobNetPlanDBItem> jobNetPlanDBItems  = getJobnetPlanList(0);
		if(jobNetPlanDBItems != null) {
			for(JobNetPlanDBItem record : jobNetPlanDBItems) {
                NodeStatus state = NodeStatus.valueOf(record.getStatusNode());
				if(state != NodeStatus.FINISHED && state != NodeStatus.ERROR && state != NodeStatus.IGNORED) {
					result = false;
					break;
				}
			}
		}
		return result;
	}
    
	public boolean isJobnetStarted(String uuid) {
		boolean result = false;
		JobNetPlanFilter jobNetPlanFilter = new JobNetPlanFilter();
		jobNetPlanFilter.setUuid(uuid);
		setFilter(jobNetPlanFilter);
		List<JobNetPlanDBItem> jobNetPlanDBItems  = getJobnetPlanList(0);
		if(jobNetPlanDBItems != null) {
			for(JobNetPlanDBItem record : jobNetPlanDBItems) {
                NodeStatus state = NodeStatus.valueOf(record.getStatusNode());
				if(state != NodeStatus.NOT_PROCESSED) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	public boolean isJobnetRunning(String uuid) {
		return(!isJobnetEnded(uuid) && isJobnetStarted(uuid));
	}

    public void addJobNetEdge(JobNetNodeDBItem jobNetNodeDBItem, JobNetEdgesDBItem jobNetEdgesDBItem) {
        jobNetNodeDBItem.addJobNetEdge(jobNetEdgesDBItem);
    }

    public void removeJobNetEdge(JobNetNodeDBItem jobNetNodeDBItem, JobNetEdgesDBItem jobNetEdgesDBItem) {
        jobNetNodeDBItem.removeJobNetEdge(jobNetEdgesDBItem);
    }

    public JobNetPlanFilter getFilter() {
        return filter;
    }

    public void setFilter(JobNetPlanFilter filter) {
        this.filter = filter;
    }
	
	public void deleteJobnetInstance(String uuid) {
		JobNetPlanFilter filter = new JobNetPlanFilter();
		filter.setUuid(uuid);
		setFilter(filter);
    	if (!getSession().isOpen()) initSession();
		Query query = setQueryParams("from JobNetPlanDBItem plan " + getWhere());
		@SuppressWarnings("unchecked")
		List<JobNetPlanDBItem> resultList = query.list();
		logger.info(resultList.size() + " records deleted for jobnet instance " + uuid + " deleted.");
		for(JobNetPlanDBItem item : resultList) {
			delete(item);
		}
	}

    public JobNetPlanDBItem getByPlanIdOrNull(Long id) {
        JobNetPlanFilter jobNetPlanFilter = new JobNetPlanFilter();
        jobNetPlanFilter.setPlanId(id);
        setFilter(jobNetPlanFilter);
        List<JobNetPlanDBItem> jobNetPlanDBItems  = getJobnetPlanList(1);
        return (jobNetPlanDBItems.size() == 0) ? null : jobNetPlanDBItems.get(0);
    }

    public JobNetPlanDBItem getByNodeIdOrNull(Long id) {
        JobNetPlanFilter jobNetPlanFilter = new JobNetPlanFilter();
        jobNetPlanFilter.setNodeId(id);
        setFilter(jobNetPlanFilter);
        List<JobNetPlanDBItem> jobNetPlanDBItems  = getJobnetPlanList(1);
        return (jobNetPlanDBItems.size() == 0) ? null : jobNetPlanDBItems.get(0);
    }

    public List<JobNetPlanDBItem> getMemberListForContext(JobNetPlanDBItem rootNode) {
        JobNetContextDBReader contextReader = new JobNetContextDBReader(this,rootNode);
        return  contextReader.getMemberList();
    }

    public JobNetPlanTree getMemberTreeForContext(JobNetPlanDBItem rootNode) {
        JobNetContextDBReader contextReader = new JobNetContextDBReader(this,rootNode);
        return  contextReader.getMemberTree();
    }

}
