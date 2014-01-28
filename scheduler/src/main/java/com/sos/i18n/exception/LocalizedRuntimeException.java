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
/**
 * I18N Messages and Logging
 * Copyright (C) 2006 John J. Mazzitelli
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA 
 */
package com.sos.i18n.exception;

import com.sos.i18n.Msg;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

/**
 * A generic runtime exception that provides localized messages.
 *
 * @author  <a href="mailto:jmazzitelli@users.sourceforge.net">John Mazzitelli</a>
 * @version $Revision: 1.1 $
 * @see     Msg
 */
public class LocalizedRuntimeException
   extends RuntimeException
{
   private static final long serialVersionUID = 1L;

   private Msg m_msg;

   /**
    * Constructor for {@link LocalizedRuntimeException}.
    */
   public LocalizedRuntimeException()
   {
      super();
      m_msg = null;
   }

   /**
    * Constructor for {@link LocalizedRuntimeException}.
    *
    * @param cause
    */
   public LocalizedRuntimeException( Throwable cause )
   {
      super( cause );
      m_msg = null;
   }

   /**
    * Constructor for {@link LocalizedRuntimeException}.
    *
    * @param bundle  the basename of the resource bundle where the localized message can be found
    * @param locale  the locale used to determine the specified resource bundle to use
    * @param key     the key into the resource bundle to identify the specific message to get
    * @param varargs optional arguments to help fill in the localized message
    */
   public LocalizedRuntimeException( Msg.BundleBaseName bundle,
                                     Locale             locale,
                                     String             key,
                                     Object...          varargs )
   {
      super( key );
      m_msg = Msg.createMsg( bundle, locale, key, varargs );
   }

   /**
    * Constructor for {@link LocalizedRuntimeException}.
    *
    * @param locale  the locale used to determine the specified resource bundle to use
    * @param key     the key into the resource bundle to identify the specific message to get
    * @param varargs optional arguments to help fill in the localized message
    */
   public LocalizedRuntimeException( Locale    locale,
                                     String    key,
                                     Object... varargs )
   {
      super( key );
      m_msg = Msg.createMsg( locale, key, varargs );
   }

   /**
    * Constructor for {@link LocalizedRuntimeException}.
    *
    * @param bundle  the basename of the resource bundle where the localized message can be found
    * @param key     the key into the resource bundle to identify the specific message to get
    * @param varargs optional arguments to help fill in the localized message
    */
   public LocalizedRuntimeException( Msg.BundleBaseName bundle,
                                     String             key,
                                     Object...          varargs )
   {
      super( key );
      m_msg = Msg.createMsg( bundle, key, varargs );
   }

   /**
    * Constructor for {@link LocalizedRuntimeException}.
    *
    * @param key     the key into the resource bundle to identify the specific message to get
    * @param varargs optional arguments to help fill in the localized message
    */
   public LocalizedRuntimeException( String    key,
                                     Object... varargs )
   {
      super( key );
      m_msg = Msg.createMsg( key, varargs );
   }

   /**
    * Constructor for {@link LocalizedRuntimeException} that explicitly takes the localized message.
    *
    * @param msg the localized message
    */
   public LocalizedRuntimeException( Msg msg )
   {
      super( ( msg != null ) ? msg.toString() : null );
      m_msg = msg;
   }

   /**
    * Constructor for {@link LocalizedRuntimeException}.
    *
    * @param cause
    * @param bundle  the basename of the resource bundle where the localized message can be found
    * @param locale  the locale used to determine the specified resource bundle to use
    * @param key     the key into the resource bundle to identify the specific message to get
    * @param varargs optional arguments to help fill in the localized message
    */
   public LocalizedRuntimeException( Throwable          cause,
                                     Msg.BundleBaseName bundle,
                                     Locale             locale,
                                     String             key,
                                     Object...          varargs )
   {
      super( key, cause );
      m_msg = Msg.createMsg( bundle, locale, key, varargs );
   }

   /**
    * Constructor for {@link LocalizedRuntimeException}.
    *
    * @param cause
    * @param locale  the locale used to determine the specified resource bundle to use
    * @param key     the key into the resource bundle to identify the specific message to get
    * @param varargs optional arguments to help fill in the localized message
    */
   public LocalizedRuntimeException( Throwable cause,
                                     Locale    locale,
                                     String    key,
                                     Object... varargs )
   {
      super( key, cause );
      m_msg = Msg.createMsg( locale, key, varargs );
   }

   /**
    * Constructor for {@link LocalizedRuntimeException}.
    *
    * @param cause
    * @param bundle  the basename of the resource bundle where the localized message can be found
    * @param key     the key into the resource bundle to identify the specific message to get
    * @param varargs optional arguments to help fill in the localized message
    */
   public LocalizedRuntimeException( Throwable          cause,
                                     Msg.BundleBaseName bundle,
                                     String             key,
                                     Object...          varargs )
   {
      super( key, cause );
      m_msg = Msg.createMsg( bundle, key, varargs );
   }

   /**
    * Constructor for {@link LocalizedRuntimeException}.
    *
    * @param cause
    * @param key     the key into the resource bundle to identify the specific message to get
    * @param varargs optional arguments to help fill in the localized message
    */
   public LocalizedRuntimeException( Throwable cause,
                                     String    key,
                                     Object... varargs )
   {
      super( key, cause );
      m_msg = Msg.createMsg( key, varargs );
   }

   /**
    * Constructor for {@link LocalizedRuntimeException} that explicitly takes the localized message.
    *
    * @param cause
    * @param msg   the localized message
    */
   public LocalizedRuntimeException( Throwable cause,
                                     Msg       msg )
   {
      super( ( msg != null ) ? msg.toString() : null, cause );
      m_msg = msg;
   }

   /**
    * This will return the localized message if one exists, otherwise, delegates to the superclass.
    *
    * @see java.lang.Throwable#getMessage()
    */
   @Override
   public String getMessage()
   {
      if ( ( m_msg == null ) || ( m_msg.toString() == null ) )
      {
         return super.getMessage();
      }

      return m_msg.toString();
   }

   /**
    * This will return the localized message if one exists, otherwise, delegates to the superclass's
    * {@link #getMessage()}.
    *
    * @see java.lang.Throwable#getLocalizedMessage()
    */
   @Override
   public String getLocalizedMessage()
   {
      if ( ( m_msg == null ) || ( m_msg.toString() == null ) )
      {
         return super.getMessage();
      }

      return m_msg.toString();
   }

   /**
    * Convenience method that returns the stack trace as a String.
    *
    * @return stack trace
    */
   public String getStackTraceString()
   {
      StringWriter sw = new StringWriter();

      printStackTrace( new PrintWriter( sw ) );

      return sw.toString();
   }
}