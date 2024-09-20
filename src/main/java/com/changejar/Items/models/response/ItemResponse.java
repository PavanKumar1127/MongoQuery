package com.changejar.Items.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemResponse {
    private String id;
    private String name;
    private double price;
    private String description;
    private int quantity;
    private boolean isAvailable;
}
