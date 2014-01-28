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
package com.sos.scheduler.engine.eventbus;

import com.sos.scheduler.engine.eventbus.annotated.HotMethodEventSubscriptionFactory;
import com.sos.scheduler.engine.kernel.event.EventSubsystem;
import com.sos.scheduler.engine.data.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.Collection;

public class HotEventBus extends AbstractEventBus {
    private static final Logger logger = LoggerFactory.getLogger(HotEventBus.class);

    @Nullable private Event currentEvent = null;

    public HotEventBus() {
        super(HotMethodEventSubscriptionFactory.singleton);
    }

    @Override public final void publish(Event e) {
        publish(e, calls(e));
    }

    final void publish(Event e, Collection<Call> calls) {
        if (currentEvent != null)
            handleRecursiveEvent(e);
        else
            dispatchNonrecursiveEvent(e, calls);
    }

    //@Override public final void dispatchEvents() {}

    private void handleRecursiveEvent(Event e) {
        try {
            // Kein log().error(), sonst gibt es wieder eine Rekursion
            throw new Exception(EventSubsystem.class.getSimpleName() + ".publish("+e+"): ignoring the event triggered by handling the event '"+currentEvent+"'");
        }
        catch (Exception x) {
            logger.error("Ignored", x);
        }
    }

    private void dispatchNonrecursiveEvent(Event e, Collection<Call> calls) {
        currentEvent = e;
        try {
            for (Call c: calls)
                dispatchCall(c);
        }
        finally {
            currentEvent = null;
        }
    }
}