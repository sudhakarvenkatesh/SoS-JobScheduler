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
package org.jobscheduler.dashboard.repository;

import org.jobscheduler.dashboard.config.audit.AuditEventConverter;
import org.jobscheduler.dashboard.domain.PersistentAuditEvent;
import org.joda.time.LocalDateTime;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Wraps an implementation of Spring Boot's AuditEventRepository.
 */
@Repository
public class CustomAuditEventRepository {

    @Inject
    private PersistenceAuditEventRepository persistenceAuditEventRepository;

    @Bean
    public AuditEventRepository auditEventRepository() {
        return new AuditEventRepository() {

            @Inject
            private AuditEventConverter auditEventConverter;

            @Override
            public List<AuditEvent> find(String principal, Date after) {
                final List<PersistentAuditEvent> persistentAuditEvents;
                if (principal == null && after == null) {
                    persistentAuditEvents = persistenceAuditEventRepository.findAll();
                } else if (after == null) {
                    persistentAuditEvents = persistenceAuditEventRepository.findByPrincipal(principal);
                } else {
                    persistentAuditEvents =
                            persistenceAuditEventRepository.findByPrincipalAndAuditEventDateGreaterThan(principal, new LocalDateTime(after));
                }

                return auditEventConverter.convertToAuditEvent(persistentAuditEvents);
            }

            @Override
            public void add(AuditEvent event) {
                PersistentAuditEvent persistentAuditEvent = new PersistentAuditEvent();
                persistentAuditEvent.setPrincipal(event.getPrincipal());
                persistentAuditEvent.setAuditEventType(event.getType());
                persistentAuditEvent.setAuditEventDate(new LocalDateTime(event.getTimestamp()));
                persistentAuditEvent.setData(auditEventConverter.convertDataToStrings(event.getData()));

                persistenceAuditEventRepository.save(persistentAuditEvent);
            }
        };
    }
}
