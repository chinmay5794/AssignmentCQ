package com.assignment.cq.repositories;

import com.assignment.cq.model.data.Item;
import com.assignment.cq.model.data.ItemProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByNameAndSource_Name(String name, String sourceName);
}
