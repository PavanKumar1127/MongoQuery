package com.changejar.Items.repo;

import com.changejar.Items.entites.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/** The type Item Data Access Object (DAO). */
@Repository
@RequiredArgsConstructor
public class ItemDAO {

    private final ItemRepo itemRepo;

    /**
     * Save an item to the database.
     *
     * @param item the item entity to save
     * @return the saved item entity
     */
    public Item save(Item item) {
        return itemRepo.save(item);
    }

    /**
     * Find all Items with pagination.
     *
     * @param pageable the pagination information (page number, size, sorting)
     * @return a paginated list of Items
     */
    public Page<Item> findAll(Pageable pageable) {
        return itemRepo.findAll(pageable);
    }

    /**
     * Find an item by its name.
     *
     * @param name the name of the item to find
     * @return the found item, or {@code null} if not found
     */
    public Item findByName(String name) {
        return itemRepo.findByName(name);
    }

    /**
     * Find an item by its ID.
     *
     * @param id the ID of the item to find
     * @return an {@link Optional} containing the item if found, or empty if not found
     */
    public Optional<Item> findById(String id) {
        return itemRepo.findById(id);
    }

    /**
     * Delete an item from the database by its ID.
     *
     * @param id the ID of the item to delete
     */
    public void deleteById(String id) {
        itemRepo.deleteById(id);
    }


}
