package com.changejar.Items.controller;

import com.changejar.Items.entites.Item;
import com.changejar.Items.models.request.ItemRequest;
import com.changejar.Items.models.response.ApiResponse;
import com.changejar.Items.models.response.PaginatedApiResponse;
import com.changejar.Items.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ItemController handles HTTP requests related to item operations.
 *
 * Endpoints:
 * 1. GET /Items       - Retrieve a paginated list of Items.
 * 2. GET /Items/name  - Retrieve an item by name.
 * 3. POST /Items      - Create a new item.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /**
     * Retrieves a paginated list of Items.
     *
     * @param pageable A Pageable object used for pagination. Defaults to 5 Items per page.
     * @return PaginatedApiResponse - A paginated response containing a list of Items.
     */
    @GetMapping
    public ResponseEntity<PaginatedApiResponse> getAllItems(@PageableDefault(size = 5) Pageable pageable) {
        PaginatedApiResponse paginatedItems = itemService.findAll(pageable);
        return new ResponseEntity<>(paginatedItems, HttpStatus.OK);
    }

    /**
     * Finds an item by its name.
     *
     * @param name The name of the item to search for.
     * @return ApiResponse - A response containing the found item or an error message if not found.
     */
    @GetMapping("/name")
    public ResponseEntity<ApiResponse> findByName(@RequestParam String name) {
        ApiResponse response = itemService.findByName(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Creates a new item in the system.
     *
     * @param itemRequest A request body containing item details.
     * @return ApiResponse - A response containing the created item and HTTP status code.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createItem(@RequestBody @Valid ItemRequest itemRequest) {
        ApiResponse response = itemService.createItem(itemRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get Items with price greater than a specific value.
     *
     * @param priceThreshold the price threshold
     * @return ResponseEntity<List<Item>>
     */
    @GetMapping("/expensive")
    public ResponseEntity<ApiResponse> getExpensiveItems(@RequestParam double priceThreshold) {
        ApiResponse response = itemService.findItemsWithPriceGreaterThan(priceThreshold);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get available Items.
     *
     * @return ResponseEntity<List<Item>>
     */
    @GetMapping("/available")
    public ResponseEntity<ApiResponse> getAvailableItems() {
        ApiResponse response = itemService.findAvailableItems();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
