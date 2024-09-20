package com.changejar.Items.service;

import com.changejar.Items.constants.ErrorCodes;
import com.changejar.Items.constants.ErrorMessages;
import com.changejar.Items.entites.Item;
import com.changejar.Items.exceptions.ApiControllerException;
import com.changejar.Items.models.request.ItemRequest;
import com.changejar.Items.models.response.ApiResponse;
import com.changejar.Items.models.response.ItemResponse;
import com.changejar.Items.models.response.PaginatedApiResponse;
import com.changejar.Items.repo.ItemDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemDAO itemDAO;
    private final MongoTemplate mongoTemplate;

    /**
     * Create item.
     */
    @Override
    public ApiResponse createItem(ItemRequest itemRequest) {
        if (itemDAO.findByName(itemRequest.getName()) != null) {
            throw new ApiControllerException(MessageFormat.format(ErrorMessages.ITEM_ALREADY_EXISTS_BY_NAME, itemRequest.getName()), ErrorCodes.ITEM_ALREADY_EXIST);
        }

        Item savedItem = itemDAO.save(convertToEntity(itemRequest));
        return new ApiResponse(true, convertToResponse(savedItem));
    }

    /**
     * Find item by name.
     */
    @Override
    public ApiResponse findByName(String itemName) {
        Item item = itemDAO.findByName(itemName);
        if (item == null) {
            throw new ApiControllerException(MessageFormat.format(ErrorMessages.ITEM_NOT_FOUND_BY_NAME, itemName), ErrorCodes.ITEM_NOT_FOUND);
        }
        return new ApiResponse(true, convertToResponse(item));
    }

    /**
     * Find all Items with pagination.
     */
    @Override
    public PaginatedApiResponse findAll(Pageable pageable) {
        Page<Item> itemsPage = itemDAO.findAll(pageable);

        List<ItemResponse> itemResponses = itemsPage.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return new PaginatedApiResponse(
                true,
                itemResponses,
                itemsPage.getNumber(),
                itemsPage.getSize(),
                itemsPage.getTotalPages(),
                itemsPage.getTotalElements()
        );
    }
    /**
     * Custom query to find available Items.
     */
    public ApiResponse findAvailableItems() {
        Query query = new Query();
        query.addCriteria(Criteria.where("isAvailable").is(true));
        List<Item> items = mongoTemplate.find(query, Item.class);
        return new ApiResponse(true, convertToResponse(items));
    }
    /**
     * Custom query to find Items by price greater than a specific value.
     */
    public ApiResponse findItemsWithPriceGreaterThan(double priceThreshold) {
        Query query = new Query();
        query.addCriteria(Criteria.where("price").gt(priceThreshold));
        List<Item> items = mongoTemplate.find(query, Item.class);
        return new ApiResponse(true, convertToResponse(items));
    }

    // Convert a list of Item entities to a list of ItemResponse objects
    private List<ItemResponse> convertToResponse(List<Item> items) {
        return items.stream()
                .map(this::convertToResponse) // Use method reference to convert each item
                .collect(Collectors.toList());
    }
    // Convert Item entity to ItemResponse
    private ItemResponse convertToResponse(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getDescription(),
                item.getQuantity(),
                item.getIsAvailable()
        );
    }

    // Convert ItemRequest to Item entity
    private Item convertToEntity(ItemRequest itemRequest) {
        Item item = new Item();
        item.setName(itemRequest.getName());
        item.setPrice(itemRequest.getPrice());
        item.setDescription(itemRequest.getDescription());
        item.setQuantity(itemRequest.getQuantity());
        item.setIsAvailable(itemRequest.getIsAvailable());
        return item;
    }

}
