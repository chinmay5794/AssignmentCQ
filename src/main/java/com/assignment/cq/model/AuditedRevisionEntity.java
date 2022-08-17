package com.assignment.cq.model;

import com.assignment.cq.config.AuditingRevisionListener;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "revision_timestamps")
@RevisionEntity(AuditingRevisionListener.class)
public class AuditedRevisionEntity {
    @RevisionNumber
    @Id
    @SequenceGenerator(name = "revisionSeq", sequenceName = "revision_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revisionSeq")
    private int id;

    @RevisionTimestamp
    private Date modifiedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Temporal(TemporalType.TIMESTAMP)
    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

}
