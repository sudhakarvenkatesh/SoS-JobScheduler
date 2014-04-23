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
import org.apache.log4j.Logger;
import org.hibernate.Query;

import java.io.File;
import java.util.List;
// import org.apache.log4j.Logger;

/**
 * 
 * \class JobNetCmdHistoryDBLayer \brief JobNetCmdHistoryDBLayer -
 * 
 * \details
 * 
 * \section JobNetCmdHistoryDBLayer.java_intro_sec Introduction
 * 
 * \section JobNetCmdHistoryDBLayer.java_samples Some Samples
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
 * \author Uwe Risse \version 13.08.2013 \see reference
 * 
 * Created on 13.08.2013 14:40:18
 */

public class JobNetCmdHistoryDBLayer extends SOSHibernateDBLayer {
	

 
    private static final String START_OPTION = "startOption";

    private static final String NODE_TYPE = "nodeType";
    private static final String UUID = "uuid";

    private static final String START_TIME = "START_TIME";

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String START_TIME_TO = "startTimeTo";
    private static final String START_TIME_FROM = "startTimeFrom";
    private static final String HISTORY_ID = "historyId";
    
    private final static Logger logger = Logger.getLogger(JobNetCmdHistoryDBLayer.class);

    @SuppressWarnings("unused")
    private final String conClassName = "JobNetCmdHistoryDBLayer";
    public final String conSVNVersion = "$Id: JobNetCmdHistoryDBLayer.java 20037 2013-05-03 04:55:36Z ss $";


 

    private JobNetCmdHistoryFilter filter = null;


    public JobNetCmdHistoryDBLayer(File configurationFile_, String schedulerId_) {
        super();

        this.filter = new JobNetCmdHistoryFilter();

 
        this.setConfigurationFile(configurationFile_);
        this.filter.setDateFormat(DATE_FORMAT);
        this.filter.setOrderCriteria(START_TIME);
     }

    public JobNetCmdHistoryDBLayer(File configurationFile_) {
        super();
        this.filter = new JobNetCmdHistoryFilter();

        this.setConfigurationFile(configurationFile_);
        this.filter.setDateFormat(DATE_FORMAT);
        this.filter.setOrderCriteria(START_TIME);
     }
    
    private Query setQueryParams(String hql) {
        Query query = session.createQuery(hql);

        if (filter.getStartTimeFrom() != null && !filter.getStartTimeFrom().equals("")) {
            query.setTimestamp(START_TIME_FROM, filter.getStartTimeFrom());
        }

        if (filter.getStartTimeTo() != null && !filter.getStartTimeTo().equals("")) {
            query.setTimestamp(START_TIME_TO, filter.getStartTimeTo());
        }

        if (filter.getNodeType() != null && !filter.getNodeType().equals("")) {
            query.setParameter(NODE_TYPE, filter.getNodeType());
        }
        
        if (filter.getUuid() != null && !filter.getUuid().equals("")) {
            query.setParameter(UUID, filter.getUuid());
        }

        if (filter.getStartOption() != null && !filter.getStartOption().equals("")) {
            query.setParameter(START_OPTION, filter.getStartOption());
        }
 
        if (filter.getHistoryId() != null   ) {
            query.setParameter(HISTORY_ID, filter.getHistoryId());
        }

         
        return query;
    }

    public int delete() {

        String hql = "delete from JobNetCmdHistoryDBItem " + getWhere();

        Query query = setQueryParams(hql);
        int row = query.executeUpdate();

        return row;
    }

    private String getWhere() {
        String where = "";
        String and = "";

        if (filter.getStartTimeFrom() != null && !filter.getStartTimeFrom().equals("")) {
            where += and + " startTime>= :startTimeFrom";
            and = " and ";
        }

        if (filter.getStartTimeTo() != null && !filter.getStartTimeTo().equals("")) {
            where += and + " startTime< :startTimeTo";
            and = " and ";
        }

        if (filter.getNodeType() != null && !filter.getNodeType().equals("")) {
            where += and + " nodeType = :nodeType";
            and = " and "; 
        }

        if (filter.getUuid() != null && !filter.getUuid().equals("")) {
            where += and + " jobnet.jobNetHistoryDBItem.uuid = :uuid";
            and = " and "; 
        }

        if (filter.getStartOption() != null && !filter.getStartOption().equals("")) {
            where += and + " startOption = :startOption";
            and = " and ";
        }
      
        if (filter.getHistoryId() != null) {
            where += and + " historyId = :historyId";
            and = " and ";
        }
 
        if (where.trim().equals("")) {

        } else {
            where = "where " + where;
        }
        return where;

    }

    public List<JobNetCmdHistoryDBItem> getJobNetCmdHistoryList(int limit) {
        initSession();
     

        Query query = setQueryParams("from JobNetCmdHistoryDBItem jobnet " + getWhere() + this.filter.getOrderCriteria() + this.filter.getSortMode());

        if (limit > 0) {
            query.setMaxResults(limit);
        }

        @SuppressWarnings("unchecked")
        List<JobNetCmdHistoryDBItem> jobnetHistoryList = query.list();
        return jobnetHistoryList;

    }


    public List<JobNetCmdHistoryDBItem> getJobNetCmdHistoryList(JobNetHistoryDBItem jobNetHistoryDBItem, int limit) {
        initSession();
     
        resetFilter();
        filter.setHistoryId(jobNetHistoryDBItem.getHistoryId());

        Query query = setQueryParams("from JobNetCmdHistoryDBItem jobnet " + getWhere() + this.filter.getOrderCriteria() + this.filter.getSortMode());

        if (limit > 0) {
            query.setMaxResults(limit);
        }

        @SuppressWarnings("unchecked")
        List<JobNetCmdHistoryDBItem> jobnetHistoryList = query.list();
        return jobnetHistoryList;

    }

   
 
  
    public JobNetCmdHistoryFilter getFilter() {
        return filter;
    }

    public void resetFilter() {
        filter = new JobNetCmdHistoryFilter();
    }

    public void setFilter(JobNetCmdHistoryFilter filter) {
        this.filter = filter;
    }
	
 

}
