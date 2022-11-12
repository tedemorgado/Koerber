package com.github.tedemorgado.koerber.persistence.model.audit;

import com.github.tedemorgado.koerber.persistence.configuration.AuditEntityListener;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "audit_info")
@RevisionEntity(AuditEntityListener.class)
@AttributeOverrides({
   @AttributeOverride(name = "timestamp", column = @Column(name = "rev_timestamp")),
   @AttributeOverride(name = "id", column = @Column(name = "revision_id"))
}
)
public class AuditEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @RevisionNumber
   private Long id;

   @RevisionTimestamp
   private Long timestamp;

   private UUID userId;

   public Long getId() {
      return this.id;
   }

   public void setId(final Long id) {
      this.id = id;
   }

   public Long getTimestamp() {
      return this.timestamp;
   }

   public void setTimestamp(final Long timestamp) {
      this.timestamp = timestamp;
   }

   public UUID getUserId() {
      return this.userId;
   }

   public void setUserId(final UUID userId) {
      this.userId = userId;
   }
}
