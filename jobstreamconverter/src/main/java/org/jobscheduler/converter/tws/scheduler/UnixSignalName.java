//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.22 at 12:28:08 AM PDT 
//


package org.jobscheduler.converter.tws.scheduler;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for Unix_signal_name.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Unix_signal_name">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="SIGHUP"/>
 *     &lt;enumeration value="SIGINT"/>
 *     &lt;enumeration value="SIGQUIT"/>
 *     &lt;enumeration value="SIGILL"/>
 *     &lt;enumeration value="SIGTRAP"/>
 *     &lt;enumeration value="SIGABRT"/>
 *     &lt;enumeration value="SIGIOT"/>
 *     &lt;enumeration value="SIGBUS"/>
 *     &lt;enumeration value="SIGFPE"/>
 *     &lt;enumeration value="SIGKILL"/>
 *     &lt;enumeration value="SIGUSR1"/>
 *     &lt;enumeration value="SIGSEGV"/>
 *     &lt;enumeration value="SIGUSR2"/>
 *     &lt;enumeration value="SIGPIPE"/>
 *     &lt;enumeration value="SIGALRM"/>
 *     &lt;enumeration value="SIGTERM"/>
 *     &lt;enumeration value="SIGSTKFLT"/>
 *     &lt;enumeration value="SIGCHLD"/>
 *     &lt;enumeration value="SIGCONT"/>
 *     &lt;enumeration value="SIGSTOP"/>
 *     &lt;enumeration value="SIGTSTP"/>
 *     &lt;enumeration value="SIGTTIN"/>
 *     &lt;enumeration value="SIGTTOU"/>
 *     &lt;enumeration value="SIGURG"/>
 *     &lt;enumeration value="SIGXCPU"/>
 *     &lt;enumeration value="SIGXFSZ"/>
 *     &lt;enumeration value="SIGVTALRM"/>
 *     &lt;enumeration value="SIGPROF"/>
 *     &lt;enumeration value="SIGWINCH"/>
 *     &lt;enumeration value="SIGPOLL"/>
 *     &lt;enumeration value="SIGIO"/>
 *     &lt;enumeration value="SIGPWR"/>
 *     &lt;enumeration value="SIGSYS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum UnixSignalName {

    SIGABRT("SIGABRT"),
    SIGALRM("SIGALRM"),
    SIGBUS("SIGBUS"),
    SIGCHLD("SIGCHLD"),
    SIGCONT("SIGCONT"),
    SIGFPE("SIGFPE"),
    SIGHUP("SIGHUP"),
    SIGILL("SIGILL"),
    SIGINT("SIGINT"),
    SIGIO("SIGIO"),
    SIGIOT("SIGIOT"),
    SIGKILL("SIGKILL"),
    SIGPIPE("SIGPIPE"),
    SIGPOLL("SIGPOLL"),
    SIGPROF("SIGPROF"),
    SIGPWR("SIGPWR"),
    SIGQUIT("SIGQUIT"),
    SIGSEGV("SIGSEGV"),
    SIGSTKFLT("SIGSTKFLT"),
    SIGSTOP("SIGSTOP"),
    SIGSYS("SIGSYS"),
    SIGTERM("SIGTERM"),
    SIGTRAP("SIGTRAP"),
    SIGTSTP("SIGTSTP"),
    SIGTTIN("SIGTTIN"),
    SIGTTOU("SIGTTOU"),
    SIGURG("SIGURG"),
    @XmlEnumValue("SIGUSR1")
    SIGUSR_1("SIGUSR1"),
    @XmlEnumValue("SIGUSR2")
    SIGUSR_2("SIGUSR2"),
    SIGVTALRM("SIGVTALRM"),
    SIGWINCH("SIGWINCH"),
    SIGXCPU("SIGXCPU"),
    SIGXFSZ("SIGXFSZ");
    private final String value;

    UnixSignalName(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UnixSignalName fromValue(String v) {
        for (UnixSignalName c: UnixSignalName.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
