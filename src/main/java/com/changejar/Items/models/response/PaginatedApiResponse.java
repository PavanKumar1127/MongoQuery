package com.changejar.Items.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginatedApiResponse {

    private boolean success = true;
    private Object data;
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalItems;
}