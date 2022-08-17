package com.assignment.cq.services;

import com.assignment.cq.dto.ItemPropertyDTO;
import com.assignment.cq.model.AuditedRevisionEntity;
import com.assignment.cq.model.data.ItemProperty;
import com.assignment.cq.repositories.ItemPropertyRepository;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.projection.AuditProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ItemHistoryServiceImpl implements ItemHistoryService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ItemPropertyRepository itemPropertyRepository;

    public Map<String, ItemPropertyDTO> getItemPropertyRevisions(String propertyName, String itemName) {
        Map<String, ItemPropertyDTO> result = new HashMap<>();
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        List<ItemProperty> itemPropertyList = itemPropertyRepository.findByNameAndItem_Name(propertyName, itemName);
        if (itemPropertyList == null || itemPropertyList.size() <= 1) {
            return result;
        }
        ItemProperty itemProperty = itemPropertyList.get(0);

        List<Number> revisionNumbers =  auditReader.getRevisions(ItemProperty.class, itemProperty.getId());
        List<ItemProperty> itemPropertyRevisions = new ArrayList<>();
        for (Number revisionNumber : revisionNumbers) {
            ItemProperty itemPropertyRevision = auditReader.find(ItemProperty.class, itemProperty.getId(), revisionNumber);
            Date revisionDate = auditReader.getRevisionDate(revisionNumber);
            String revisionDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(revisionDate);
            result.put(revisionDateStr, new ItemPropertyDTO(itemPropertyRevision, false));
        }
        return result;
    }
}
