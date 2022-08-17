package com.assignment.cq.services;

import com.assignment.cq.dto.ItemPropertyDTO;
import com.assignment.cq.model.data.ItemProperty;

import java.util.List;
import java.util.Map;

public interface ItemHistoryService {

    public Map<String, ItemPropertyDTO> getItemPropertyRevisions(String propertyName, String itemName);
}
