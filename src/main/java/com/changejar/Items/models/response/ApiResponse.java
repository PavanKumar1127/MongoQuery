package com.changejar.Items.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private boolean success = true;
    private Object data;
    private String status;
    private String error;
    private Object errorMessage;
    private String errorCode;

    public ApiResponse(boolean success, Object itemResponse) {
        this.success = success;
        this.data = itemResponse;
    }
}
