package com.github.tedemorgado.koerber.persistence.configuration;

import com.github.tedemorgado.koerber.persistence.model.audit.AuditEntity;
import org.hibernate.envers.RevisionListener;

import java.time.Clock;

public class AuditEntityListener implements RevisionListener {

   private final Clock clock;

   public AuditEntityListener(final Clock clock) {
      this.clock = clock;
   }

   @Override
   public void newRevision(final Object revisionEntity) {
      final AuditEntity auditEntity = (AuditEntity) revisionEntity;
      auditEntity.setUserId("Need to do it");
      auditEntity.setTimestamp(this.clock.millis());
   }
}
