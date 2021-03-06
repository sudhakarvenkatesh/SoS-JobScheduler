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
package com.sos.scheduler.engine.kernel.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileTypeMap;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;
import java.io.*;
import java.util.*;

import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.collect.Maps.newHashMap;

/** Nur für C++. */
public final class MailMessage {
    static final int current_version = 2;

    private final Properties _properties = System.getProperties();
    private final Map<String, String> settings = newHashMap();
    private final Session _session = Session.getInstance(_properties, new My_authenticator());
    private MimeMessage _msg = new MimeMessage(_session);
    private byte[] _body;
    private final List<MimeBodyPart> _attachments = new LinkedList<MimeBodyPart>();
    private final List<FileInputStream> file_input_streams = new ArrayList<FileInputStream>();      // Alle offenen Attachments, werden von close() geschlossen
    private String _smtp_user_name = "";
    private String _smtp_password = "";
    private String _encoding;
    private String _content_type;
    private boolean _built = false;

    public void close() throws Exception {
        Exception exception = null;
        for (FileInputStream o: file_input_streams) {
            try {
                o.close();
            } catch (Exception x) {
                if (exception == null) exception = x;
            }
        }
        if (exception != null) throw exception;
    }

    public void need_version(int version) throws Exception {
        if (version > current_version)
            throw new Exception("Class com.sos.scheduler.engine.kernel.mail.MailMessage (sos.mail.jar) is not up to date");
    }

    public void set(String what, byte[] value) throws UnsupportedEncodingException, MessagingException {
        if (what.equals("smtp"))
            _properties.put("mail.smtp.host", new String(value, "iso8859-1"));
        else
        if (what.equals("body"))
            _body = value;
        else
        if (what.equals("send_rfc822")) {
            _msg = new MimeMessage(_session, new ByteArrayInputStream(value));
            send2();
        }
        else
            settings.put(what, new String(value, "iso8859-1"));
    }

    public void transferSettings() throws MessagingException {
        for (Map.Entry<String, String> entry: settings.entrySet()) {
            String what = entry.getKey();
            String value = entry.getValue();

            if (what.equals("from")) {
                InternetAddress[] addr = InternetAddress.parse(value);
                if (addr.length != 0) _msg.setFrom(addr[0]);
            } else
            if (what.equals("reply-to")) _msg.setReplyTo(InternetAddress.parse(value));
            else
            if (what.equals("to")) _msg.setRecipients(RecipientType.TO, InternetAddress.parse(value));
            else
            if (what.equals("cc")) _msg.setRecipients(RecipientType.CC, InternetAddress.parse(value));
            else
            if (what.equals("bcc"))
                _msg.setRecipients(RecipientType.BCC, InternetAddress.parse(value));
            else
            if (what.equals("subject")) _msg.setSubject(value);
            else
            if (what.equals("content_type")) _content_type = value;
            else
            if (what.equals("encoding")) _encoding = value;
            else
            if (what.equals("debug")) _session.setDebug(value.equals("1"));
            else
                throw new RuntimeException("com.sos.scheduler.engine.kernel.mail.MailMessage.set: "+what);
        }
    }

    public void set_property(String name, String value) {
        if (name.equals("mail.smtp.user")) _smtp_user_name = value;     // Keine Java-Property, Jira JS-136
        else if (name.equals("mail.smtp.password")) _smtp_password = value;      // Keine Java-Property, Jira JS-136
        else
            _properties.put(name, value);
    }

    private String string_from_addresses(Address[] addresses) {
        if (addresses == null) return "";
        String result = "";
        for (int i = 0; i < addresses.length; i++) {
            if (!result.equals("")) result = result + ", ";
            result = result + addresses[i];
        }
        return result;
    }

    public String get(String what) throws Exception {
        if (what.equals("smtp"))
            return (String)_properties.get("mail.smtp.host");
        else
        if (what.equals("body"))
            return _body == null? "" : new String(_body, "iso8859-1");
        else
        if (what.equals("rfc822_text")) {
            build();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            _msg.writeTo(os);
            return os.toString();
        } else {
            return nullToEmpty(settings.get(what));
        }
    }

    public void add_header_field(String name, String value) throws MessagingException {
        _msg.setHeader(name, value);
    }

    public void add_file(String real_filename, String new_filename, String content_type, String encoding) throws Exception {
        if (new_filename == null || new_filename.length() == 0) new_filename = real_filename;
        if (content_type == null || content_type.length() == 0)
            content_type = FileTypeMap.getDefaultFileTypeMap().getContentType(new_filename);

        MimeBodyPart attachment = new MimeBodyPart();

        DataSource data_source = new File_data_source(new File(real_filename), new File(new_filename), content_type);
        DataHandler data_handler = new DataHandler(data_source);

        attachment.setDataHandler(data_handler);
        attachment.setFileName(data_handler.getName());

        _attachments.add(attachment);
    }

    public void add_attachment(byte[] data, String filename, String content_type, String encoding) throws MessagingException {
        if (content_type.length() == 0) content_type = FileTypeMap.getDefaultFileTypeMap().getContentType(filename);

        MimeBodyPart attachment = new MimeBodyPart();

        DataSource data_source = new Byte_array_data_source(data, new File(filename), content_type);
        DataHandler data_handler = new DataHandler(data_source);

        attachment.setDataHandler(data_handler);
        attachment.setFileName(data_handler.getName());

        _attachments.add(attachment);
    }

    public void send() throws Exception {
        build();
        send2();
    }

    public void build() throws Exception {
        transferSettings();
        if (_built) return;

        _msg.setSentDate(new Date());     // Damit rfc822_text das Datum liefert. Jira JS-81

        if (_content_type == null || _content_type.equals("")) _content_type = "text/plain";

        if (_attachments.size() == 0) {
            set_body_in(_msg);
        } else {
            MimeMultipart multipart = new MimeMultipart();

            MimeBodyPart b = new MimeBodyPart();
            set_body_in(b);
            multipart.addBodyPart(b);

            for (MimeBodyPart a: _attachments) multipart.addBodyPart(a);

            _msg.setContent(multipart);
        }

        _built = true;
    }

    private void set_body_in(MimePart body_part) throws Exception {
        body_part.setContent(new String(_body, "iso8859-1"), _content_type);
    }

    protected void send2() throws MessagingException {
        if (_smtp_user_name.length() > 0) _properties.put("mail.smtp.auth", "true");
        Transport.send(_msg);
    }

    abstract static class My_data_source implements DataSource {
        private final String name;
        private final String content_type;

        protected My_data_source(File new_filename, String content_type) {
            this.name = new_filename.getName();
            this.content_type = content_type;
        }

        public String getContentType() {
            return content_type;
        }

        public String getName() {
            return name;
        }

        public OutputStream getOutputStream() {
            throw new UnsupportedOperationException(getClass().getName() + " has no OutputStream");
        }
    }

    static final class Byte_array_data_source extends My_data_source {
        private final byte[] byte_array;

        Byte_array_data_source(byte[] byte_array, File new_filename, String content_type) {
            super(new_filename, content_type);
            this.byte_array = byte_array;
        }

        public InputStream getInputStream() {
            return new ByteArrayInputStream(byte_array);
        }
    }


    final class File_data_source extends My_data_source {
        // M�glicherweise kann FileDataSource verwendet werden.
        // Aber schlie�t die Klasse die Datei? Es gibt keinen close()
        private final File file;

        File_data_source(File file, File new_filename, String content_type) {
            super(new_filename, content_type);
            this.file = file;
        }

        public InputStream getInputStream() throws IOException {
            FileInputStream f = new FileInputStream(file);
            file_input_streams.add(f);    // wird von MailMessage.close() geschlossen
            return f;
        }
    }

    public class My_authenticator extends Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            //System.err.print( "getPasswordAuthentication " + _smtp_user_name + ", " + _smtp_password + "\n" );
            return new PasswordAuthentication(_smtp_user_name, _smtp_password);
        }
    }
}
