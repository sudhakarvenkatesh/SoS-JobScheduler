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
package com.sos.jobnet.context;

import com.sos.jobnetmodel.objects.JobnetNodeType;
import org.apache.log4j.Logger;

/**
 * @uthor ss
 * at 04.09.13 13:26
 */
public class ContextChecker {

    private final static Logger logger = Logger.getLogger(ContextChecker.class);

    private final Context initialContext;
    private final JobnetNodeType initialType;

    public ContextChecker(Context context, JobnetNodeType type) {
        this.initialContext = context;
        this.initialType = type;
    }

    public boolean isInContext(Context context) {

        boolean result = true;
        String jobName = context.getJob();
        String connectorName = context.getConnector();
        String subnetName = context.getSubnet();

        logger.info("Check context for context type '" + initialType.name() + "'.");
        logger.info("Check context for order '" + jobName + "' (subnet=" + subnetName + ", connector=" + connectorName + ").");
        if (initialType == JobnetNodeType.SUBNET && !subnetName.equals(initialContext.getSubnet()))
            result = false;
        if (initialType == JobnetNodeType.CONNECTOR && !connectorName.equals(initialContext.getConnector()))
            result = false;
        if (initialType == JobnetNodeType.JOB && !jobName.equals(initialContext.getJob()))
            result = false;

        if(!result)
            logger.info("The order is not in the same context like order '" + initialContext.getJob() + "' (subnet=" + initialContext.getSubnet() + ", connector=" + initialContext.getConnector() + ")");
        else
            logger.info("Context successfully checked.");

        return result;
    }

}
