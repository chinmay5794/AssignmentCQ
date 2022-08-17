package com.assignment.cq.repositories;

import com.assignment.cq.model.data.Item;
import com.assignment.cq.model.data.ItemProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPropertyRepository extends JpaRepository<ItemProperty, Long> {
    List<ItemProperty> findByNameAndItem_Name(String name, String itemName);
    List<ItemProperty> findByItem_Name(String itemName);
}
