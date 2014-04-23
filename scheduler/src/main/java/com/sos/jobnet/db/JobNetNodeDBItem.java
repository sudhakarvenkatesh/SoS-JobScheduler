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

/**
* \class JobnetNodesDBItem 
* 
* \brief JobnetNodesDBItem - 
* 
* \details
*
* \section JobnetNodesDBItem.java_intro_sec Introduction
*
* \section JobnetNodesDBItem.java_samples Some Samples
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
* \version 23.09.2011
* \see reference
*
* Created on 23.09.2011 15:08:05
 */

 
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.sos.hibernate.classes.DbItem;
import com.sos.jobnet.interfaces.IEdgesItem;
import com.sos.jobnet.interfaces.INodesItem;
 

@Entity
@Table(name="SCHEDULER_JOB_NET_NODES",
		uniqueConstraints = {@UniqueConstraint(columnNames={ "SCHEDULER_ID","NODE","JOB_NET"  })})
		
public class JobNetNodeDBItem  extends DbItem implements INodesItem {
	
	@SuppressWarnings("unused")

     private final String	conClassName	= "JobNetNodeDBItem";
	 public  final String	conSVNVersion	= "$Id: JobNetNodeDBItem.java 17391 2012-06-21 13:41:13Z ur $";

     private String jobnet;
	 private Long nodeId; 
     private String node;
	 private String schedulerId;
     

	private List <JobNetEdgesDBItem> listOfJobnetEdgesDBItems = new ArrayList<JobNetEdgesDBItem>();
     
 	public JobNetNodeDBItem() {
 	   super();
 	}
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="NODE_ID")
    public Long getNodeId() {
		return nodeId;
	}

	@Column(name="NODE_ID")
    public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

    @OneToMany (mappedBy = "parentNodeId", cascade = CascadeType.ALL, orphanRemoval = true)
    public List <JobNetEdgesDBItem> getListOfJobnetEdgesDBItems(){
    	return listOfJobnetEdgesDBItems;
    }
    public void setListOfJobnetEdgesDBItems(List<JobNetEdgesDBItem> listOfJobnetEdgesDBItems) {
		this.listOfJobnetEdgesDBItems = listOfJobnetEdgesDBItems;
	}
      
    @Column(name="SCHEDULER_ID",nullable=false)
	public void setSchedulerId(String schedulerId) {
		this.schedulerId = schedulerId;
	}
    
    @Column(name="SCHEDULER_ID",nullable=false)
	public String getSchedulerId() {
		return schedulerId;
	}
    

    @Column(name="NODE",nullable=false)
	public void setNode(String node) {
		this.node = node;
	}
 
    @Column(name="NODE",nullable=false)
	public String getNode() {
		return node;
	} 
    
    
    @Column(name="JOB_NET",nullable=true)
	public void setJobnet(String jobnet) {
		this.jobnet = jobnet;
	}
 
    @Column(name="JOB_NET",nullable=true)
	public String getJobnet() {
		return jobnet;
	}
    
    @Transient
    public void addJobNetEdge(JobNetEdgesDBItem jobNetEdgesDBItem) {
		this.getListOfJobnetEdgesDBItems().add(jobNetEdgesDBItem);
		jobNetEdgesDBItem.setJobnetNodeDBItem(this);
	}
    
    @Transient
	public void removeJobNetEdge(JobNetEdgesDBItem jobNetEdgesDBItem) {
		this.getListOfJobnetEdgesDBItems().remove(jobNetEdgesDBItem);
		jobNetEdgesDBItem.setJobnetNodeDBItem(null);
	}
 
    @Transient
    public void removeJobAllNetEdges() {
        this.getListOfJobnetEdgesDBItems().clear();
     }
    
	@Transient
	public List <IEdgesItem> getListOfJobnetEdgesItems(){
		List <IEdgesItem> listOfJobnetEdgesItems = new ArrayList<IEdgesItem>();
		listOfJobnetEdgesItems.addAll(listOfJobnetEdgesDBItems);
    	return listOfJobnetEdgesItems;
    }
	
}
