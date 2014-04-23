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

import com.sos.scheduler.engine.data.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SchedulerEventBus implements EventBus, Runnable {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerEventBus.class);

    private final HotEventBus hotEventBus = new HotEventBus();
    private final ColdEventBus coldEventBus = new ColdEventBus();

    @Override public void registerAnnotated(EventHandlerAnnotated o) {
        hotEventBus.registerAnnotated(o);
        coldEventBus.registerAnnotated(o);
    }

    @Override public void unregisterAnnotated(EventHandlerAnnotated o) {
        hotEventBus.unregisterAnnotated(o);
        coldEventBus.unregisterAnnotated(o);
    }

    public void register(EventSubscription s) {
        coldEventBus.register(s);
    }

    public void unregister(EventSubscription s) {
        coldEventBus.unregister(s);
    }

    public void registerHot(EventSubscription s) {
        hotEventBus.register(s);
    }

    public void unregisterHot(EventSubscription s) {
        hotEventBus.unregister(s);
    }

    public void publish(Event e, EventSource source) {
        publish(new EventSourceEvent(e, source));
    }

    @Override public void publish(Event e) {
        logger.trace("publish {}", e);
        hotEventBus.publish(e);
        publishCold(e);
    }

    public void publishCold(Event e) {
        coldEventBus.publish(e instanceof EventSourceEvent? ((EventSourceEvent)e).getEvent() : e);
    }

    public void dispatchEvents() {
        coldEventBus.dispatchEvents();
    }

    @Override public void run() {
        coldEventBus.run();
    }

    @Override public String toString() {
        return getClass().getSimpleName();
    }
}
