package com.assignment.cq.config;

import com.assignment.cq.model.AuditedRevisionEntity;
import org.hibernate.envers.RevisionListener;

import java.util.Date;

public class AuditingRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        AuditedRevisionEntity revEntity = (AuditedRevisionEntity) revisionEntity;
        revEntity.setModifiedAt(new Date());
    }
}
