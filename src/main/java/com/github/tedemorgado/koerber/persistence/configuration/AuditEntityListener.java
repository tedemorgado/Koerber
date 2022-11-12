package com.github.tedemorgado.koerber.persistence.configuration;

import com.github.tedemorgado.koerber.persistence.model.audit.AuditEntity;
import org.hibernate.envers.RevisionListener;

import java.time.Clock;
import java.util.UUID;

public class AuditEntityListener implements RevisionListener {

   private final Clock clock;

   public AuditEntityListener(final Clock clock) {
      this.clock = clock;
   }

   @Override
   public void newRevision(final Object revisionEntity) {
      final AuditEntity auditEntity = (AuditEntity) revisionEntity;
      // TODO: 12/11/2022  
      auditEntity.setUserId(UUID.randomUUID());
      auditEntity.setTimestamp(this.clock.millis());
   }
}
