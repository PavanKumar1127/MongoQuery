package com.changejar.Items.entites;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class Item {

    @Id private String id;

    @Indexed(unique = true)
    private String name;
    private Double price;
    private String description;
    private Integer quantity;
    private Boolean isAvailable;
    private Date createdAt;
    private String createdBy;
}
