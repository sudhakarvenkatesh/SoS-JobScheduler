//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.22 at 12:28:08 AM PDT 
//


package org.jobscheduler.converter.tws.scheduler;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jobscheduler.converter.tws.scheduler.RunTime.At;
import org.jobscheduler.converter.tws.scheduler.RunTime.Date;
import org.jobscheduler.converter.tws.scheduler.RunTime.Month;


/**
 * <p>Java class for run_time complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="run_time">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="at" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="at" type="{}Loose_date_time" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="date" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="date" use="required" type="{}String" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="weekdays" type="{}weekdays" minOccurs="0"/>
 *         &lt;element name="monthdays" type="{}monthdays" minOccurs="0"/>
 *         &lt;element name="ultimos" type="{}ultimos" minOccurs="0"/>
 *         &lt;element name="month" maxOccurs="12" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="weekdays" type="{}weekdays"/>
 *                   &lt;element name="monthdays" type="{}monthdays"/>
 *                   &lt;element name="ultimos" type="{}ultimos"/>
 *                 &lt;/choice>
 *                 &lt;attribute name="month">
 *                   &lt;simpleType>
 *                     &lt;list itemType="{}Month_name" />
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;choice>
 *           &lt;element ref="{}holidays"/>
 *           &lt;element ref="{}holiday" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="begin" type="{}Time_of_day" />
 *       &lt;attribute name="end" type="{}Time_of_day" />
 *       &lt;attribute name="let_run" type="{}Yes_no" />
 *       &lt;attribute name="name" type="{}Name" />
 *       &lt;attribute name="once" type="{}Yes_no" />
 *       &lt;attribute name="repeat" type="{}Duration" />
 *       &lt;attribute name="schedule" type="{}String" />
 *       &lt;attribute name="single_start" type="{}Time_of_day" />
 *       &lt;attribute name="start_time_function" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *       &lt;attribute name="substitute" type="{}Path" />
 *       &lt;attribute name="time_zone" type="{}String" />
 *       &lt;attribute name="title" type="{}String" />
 *       &lt;attribute name="valid_from" type="{}Date_time" />
 *       &lt;attribute name="valid_to" type="{}Date_time" />
 *       &lt;attribute name="when_holiday" type="{}When_holiday" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "run_time", propOrder = {
    "period",
    "at",
    "date",
    "weekdays",
    "monthdays",
    "ultimos",
    "month",
    "holidays",
    "holiday"
})
public class RunTime {

    @XmlElement(required = true)
    protected List<Period> period;
    @XmlElement(required = true)
    protected List<At> at;
    @XmlElement(required = true)
    protected List<Date> date;
    protected Weekdays weekdays;
    protected Monthdays monthdays;
    protected Ultimos ultimos;
    @XmlElement(required = true)
    protected List<Month> month;
    protected Holidays holidays;
    @XmlElement(required = true)
    protected List<Holiday> holiday;
    @XmlAttribute
    protected String begin;
    @XmlAttribute
    protected String end;
    @XmlAttribute(name = "let_run")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String letRun;
    @XmlAttribute
    protected String name;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String once;
    @XmlAttribute
    protected String repeat;
    @XmlAttribute
    protected String schedule;
    @XmlAttribute(name = "single_start")
    protected String singleStart;
    @XmlAttribute(name = "start_time_function")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String startTimeFunction;
    @XmlAttribute
    protected String substitute;
    @XmlAttribute(name = "time_zone")
    protected String timeZone;
    @XmlAttribute
    protected String title;
    @XmlAttribute(name = "valid_from")
    protected String validFrom;
    @XmlAttribute(name = "valid_to")
    protected String validTo;
    @XmlAttribute(name = "when_holiday")
    protected WhenHoliday whenHoliday;

    /**
     * Gets the value of the period property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the period property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPeriod().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Period }
     * 
     * 
     */
    public List<Period> getPeriod() {
        if (period == null) {
            period = new ArrayList<Period>();
        }
        return this.period;
    }

    /**
     * Gets the value of the at property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the at property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link At }
     * 
     * 
     */
    public List<At> getAt() {
        if (at == null) {
            at = new ArrayList<At>();
        }
        return this.at;
    }

    /**
     * Gets the value of the date property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the date property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Date }
     * 
     * 
     */
    public List<Date> getDate() {
        if (date == null) {
            date = new ArrayList<Date>();
        }
        return this.date;
    }

    /**
     * Gets the value of the weekdays property.
     * 
     * @return
     *     possible object is
     *     {@link Weekdays }
     *     
     */
    public Weekdays getWeekdays() {
        return weekdays;
    }

    /**
     * Sets the value of the weekdays property.
     * 
     * @param value
     *     allowed object is
     *     {@link Weekdays }
     *     
     */
    public void setWeekdays(Weekdays value) {
        this.weekdays = value;
    }

    /**
     * Gets the value of the monthdays property.
     * 
     * @return
     *     possible object is
     *     {@link Monthdays }
     *     
     */
    public Monthdays getMonthdays() {
        return monthdays;
    }

    /**
     * Sets the value of the monthdays property.
     * 
     * @param value
     *     allowed object is
     *     {@link Monthdays }
     *     
     */
    public void setMonthdays(Monthdays value) {
        this.monthdays = value;
    }

    /**
     * Gets the value of the ultimos property.
     * 
     * @return
     *     possible object is
     *     {@link Ultimos }
     *     
     */
    public Ultimos getUltimos() {
        return ultimos;
    }

    /**
     * Sets the value of the ultimos property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ultimos }
     *     
     */
    public void setUltimos(Ultimos value) {
        this.ultimos = value;
    }

    /**
     * Gets the value of the month property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the month property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonth().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Month }
     * 
     * 
     */
    public List<Month> getMonth() {
        if (month == null) {
            month = new ArrayList<Month>();
        }
        return this.month;
    }

    /**
     * Gets the value of the holidays property.
     * 
     * @return
     *     possible object is
     *     {@link Holidays }
     *     
     */
    public Holidays getHolidays() {
        return holidays;
    }

    /**
     * Sets the value of the holidays property.
     * 
     * @param value
     *     allowed object is
     *     {@link Holidays }
     *     
     */
    public void setHolidays(Holidays value) {
        this.holidays = value;
    }

    /**
     * Gets the value of the holiday property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the holiday property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHoliday().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Holiday }
     * 
     * 
     */
    public List<Holiday> getHoliday() {
        if (holiday == null) {
            holiday = new ArrayList<Holiday>();
        }
        return this.holiday;
    }

    /**
     * Gets the value of the begin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBegin() {
        return begin;
    }

    /**
     * Sets the value of the begin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBegin(String value) {
        this.begin = value;
    }

    /**
     * Gets the value of the end property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnd() {
        return end;
    }

    /**
     * Sets the value of the end property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnd(String value) {
        this.end = value;
    }

    /**
     * Gets the value of the letRun property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLetRun() {
        return letRun;
    }

    /**
     * Sets the value of the letRun property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLetRun(String value) {
        this.letRun = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the once property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnce() {
        return once;
    }

    /**
     * Sets the value of the once property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnce(String value) {
        this.once = value;
    }

    /**
     * Gets the value of the repeat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepeat() {
        return repeat;
    }

    /**
     * Sets the value of the repeat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepeat(String value) {
        this.repeat = value;
    }

    /**
     * Gets the value of the schedule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchedule() {
        return schedule;
    }

    /**
     * Sets the value of the schedule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchedule(String value) {
        this.schedule = value;
    }

    /**
     * Gets the value of the singleStart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSingleStart() {
        return singleStart;
    }

    /**
     * Sets the value of the singleStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSingleStart(String value) {
        this.singleStart = value;
    }

    /**
     * Gets the value of the startTimeFunction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartTimeFunction() {
        return startTimeFunction;
    }

    /**
     * Sets the value of the startTimeFunction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTimeFunction(String value) {
        this.startTimeFunction = value;
    }

    /**
     * Gets the value of the substitute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubstitute() {
        return substitute;
    }

    /**
     * Sets the value of the substitute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubstitute(String value) {
        this.substitute = value;
    }

    /**
     * Gets the value of the timeZone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * Sets the value of the timeZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeZone(String value) {
        this.timeZone = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the validFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidFrom() {
        return validFrom;
    }

    /**
     * Sets the value of the validFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidFrom(String value) {
        this.validFrom = value;
    }

    /**
     * Gets the value of the validTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidTo() {
        return validTo;
    }

    /**
     * Sets the value of the validTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidTo(String value) {
        this.validTo = value;
    }

    /**
     * Gets the value of the whenHoliday property.
     * 
     * @return
     *     possible object is
     *     {@link WhenHoliday }
     *     
     */
    public WhenHoliday getWhenHoliday() {
        return whenHoliday;
    }

    /**
     * Sets the value of the whenHoliday property.
     * 
     * @param value
     *     allowed object is
     *     {@link WhenHoliday }
     *     
     */
    public void setWhenHoliday(WhenHoliday value) {
        this.whenHoliday = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="at" type="{}Loose_date_time" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class At {

        @XmlAttribute
        protected String at;

        /**
         * Gets the value of the at property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAt() {
            return at;
        }

        /**
         * Sets the value of the at property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAt(String value) {
            this.at = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="date" use="required" type="{}String" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "period"
    })
    public static class Date {

        @XmlElement(required = true)
        protected List<Period> period;
        @XmlAttribute(required = true)
        protected String date;

        /**
         * Gets the value of the period property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the period property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPeriod().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Period }
         * 
         * 
         */
        public List<Period> getPeriod() {
            if (period == null) {
                period = new ArrayList<Period>();
            }
            return this.period;
        }

        /**
         * Gets the value of the date property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDate() {
            return date;
        }

        /**
         * Sets the value of the date property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDate(String value) {
            this.date = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;choice maxOccurs="unbounded" minOccurs="0">
     *         &lt;element ref="{}period" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="weekdays" type="{}weekdays"/>
     *         &lt;element name="monthdays" type="{}monthdays"/>
     *         &lt;element name="ultimos" type="{}ultimos"/>
     *       &lt;/choice>
     *       &lt;attribute name="month">
     *         &lt;simpleType>
     *           &lt;list itemType="{}Month_name" />
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "periodOrWeekdaysOrMonthdays"
    })
    public static class Month {

        @XmlElements({
            @XmlElement(name = "ultimos", required = true, type = Ultimos.class),
            @XmlElement(name = "period", required = true, type = Period.class),
            @XmlElement(name = "weekdays", required = true, type = Weekdays.class),
            @XmlElement(name = "monthdays", required = true, type = Monthdays.class)
        })
        protected List<Object> periodOrWeekdaysOrMonthdays;
        @XmlAttribute
        protected List<String> month;

        /**
         * Gets the value of the periodOrWeekdaysOrMonthdays property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the periodOrWeekdaysOrMonthdays property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPeriodOrWeekdaysOrMonthdays().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Ultimos }
         * {@link Period }
         * {@link Weekdays }
         * {@link Monthdays }
         * 
         * 
         */
        public List<Object> getPeriodOrWeekdaysOrMonthdays() {
            if (periodOrWeekdaysOrMonthdays == null) {
                periodOrWeekdaysOrMonthdays = new ArrayList<Object>();
            }
            return this.periodOrWeekdaysOrMonthdays;
        }

        /**
         * Gets the value of the month property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the month property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMonth().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getMonth() {
            if (month == null) {
                month = new ArrayList<String>();
            }
            return this.month;
        }

    }

}