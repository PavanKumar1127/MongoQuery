package com.changejar.Items.entites;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document
@Builder
public class Food {

    @Id private String id;

    @NonNull
    @NotBlank
    private String dishName;
    
    private List<Item> ingredients;
    
    private String cuisine;
    
    private Integer preparationTime;
}
