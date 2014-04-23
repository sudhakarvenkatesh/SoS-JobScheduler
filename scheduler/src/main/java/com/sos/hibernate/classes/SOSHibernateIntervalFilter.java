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
package com.sos.hibernate.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * \class Filter
 * 
 * \brief Filter -
 * 
 * \details
 * 
 * \section Filter.java_intro_sec Introduction
 * 
 * \section Filter.java_samples Some Samples
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
 * \author Uwe Risse \version 24.01.2012 \see reference
 * 
 * Created on 24.01.2012 12:34:16
 */
public abstract class SOSHibernateIntervalFilter extends SOSHibernateFilter {
	@SuppressWarnings("unused")
	private final String conClassName = "Filter";
	private static final Logger logger = Logger
			.getLogger(SOSHibernateIntervalFilter.class);

	public abstract void setIntervalFromDate(Date d);

	public abstract void setIntervalToDate(Date d);

	public abstract void setIntervalFromDateIso(String s);

	public abstract void setIntervalToDateIso(String s);

	public SOSHibernateIntervalFilter() {
		super();
 	}

       
  
  
	
	public SOSHibernateIntervalFilter(String strI28NPropertyFileName) {
		super(strI28NPropertyFileName);
	}

	public void setIntervalFrom(final Date from) {
		if (from != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			String d = formatter.format(from);
			try {
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				this.setIntervalFromDate(formatter.parse(d));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.setIntervalFromDateIso(formatter.format(from));
			logger.debug(String.format("Setting interval from: %s ",formatter.format(from)));
		} else {
			this.setIntervalFromDate(null);
		}
	}

	public void setIntervalTo(final Date to) {
		if (to != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
			String d = formatter.format(to);
			try {
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				this.setIntervalToDate(formatter.parse(d));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.setIntervalToDateIso(formatter.format(to));
			logger.debug(String.format("Setting interval to: %s ",formatter.format(to)));
		} else {
			this.setIntervalToDate(null);
		}
	}

}
