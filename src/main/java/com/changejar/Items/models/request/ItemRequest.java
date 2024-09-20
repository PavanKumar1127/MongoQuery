package com.changejar.Items.models.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ItemRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Min(value = 0, message = "Price cannot be negative")
    private Double price;
    @NotBlank
    private String description;

    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    private Boolean isAvailable;
}
