package com.changejar.Items.repo;

import com.changejar.Items.entites.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * The interface Item data repo.
 */
@Repository
public interface ItemRepo extends MongoRepository<Item, String> {

    @Query("{ 'name': ?0 }")
    Item findByName(String name);
}





