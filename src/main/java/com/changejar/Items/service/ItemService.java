package com.changejar.Items.service;

import com.changejar.Items.entites.Item;
import com.changejar.Items.models.request.ItemRequest;
import com.changejar.Items.models.response.ApiResponse;
import com.changejar.Items.models.response.PaginatedApiResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface Item service.
 */
public interface ItemService {

    /**
     * Create item.
     *
     * @param itemRequest the item object
     */
    ApiResponse createItem(ItemRequest itemRequest);

    /**
     * Find item by name.
     *
     * @param name The name of the item to search for
     */
    ApiResponse findByName(String name);

    /**
     * Find all Items with pagination.
     *
     * @param pageable the pagination information
     */
    PaginatedApiResponse findAll(Pageable pageable);

    ApiResponse findItemsWithPriceGreaterThan(double priceThreshold);
    ApiResponse findAvailableItems();

    }
