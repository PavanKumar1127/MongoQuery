package com.changejar.Items.exceptions;

import com.changejar.Items.constants.ErrorMessages;
import com.changejar.Items.models.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class ExceptionController {

    /**
     * Handles validation exceptions for request body fields and categorizes field errors.
     *
     * @param ex the MethodArgumentNotValidException thrown during validation
     * @return ResponseEntity containing categorized validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleFieldValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errorDetails = categorizeFieldErrors(ex.getBindingResult().getFieldErrors());
        return buildErrorResponse(ErrorMessages.VALIDATION_ERRORS_OCCURRED, errorDetails, HttpStatus.OK);
    }

    /**
     * Handles JSON parsing errors (invalid JSON) in requests.
     *
     * @param ex the HttpMessageNotReadableException thrown during deserialization
     * @return ResponseEntity with detailed JSON parsing error
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleJsonParsingException(HttpMessageNotReadableException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("error", ErrorMessages.INVALID_JSON_FORMAT);

        String fieldError = extractFieldErrorFromMessage(ex.getMessage());
        if (fieldError != null) {
            errorDetails.put("fieldError", fieldError);
        }
        return buildErrorResponse(ErrorMessages.MALFORMED_JSON_REQUEST, errorDetails, HttpStatus.OK);
    }

    /**
     * Handles NullPointerException and provides details about the missing fields.
     *
     * @param ex the NullPointerException thrown
     * @return ResponseEntity containing missing field details
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse> handleMissingFieldException(NullPointerException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("error", "Required field is missing");
        errorDetails.put("details", ex.getMessage());
        return buildErrorResponse(ErrorMessages.NULL_POINTER_EXCEPTION, errorDetails, HttpStatus.OK);
    }

    /**
     * Catches generic exceptions.
     *
     * @param ex the generic Exception thrown
     * @return ResponseEntity with generic error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("error", ErrorMessages.GENERIC_ERROR);
        errorDetails.put("details", ex.getMessage());
        return buildErrorResponse(ErrorMessages.UNEXPECTED_ERROR, errorDetails, HttpStatus.OK);
    }

    /**
     * Catches generic exceptions.
     *
     * @param ex the generic Exception thrown
     * @return ResponseEntity with generic error details
     */
    @ExceptionHandler(ApiControllerException.class)
    public ResponseEntity<ApiResponse> handleGenericException(ApiControllerException ex) {
        ApiResponse response = new ApiResponse();
        response.setSuccess(false);
        response.setErrorMessage(ex.getMessage());
        response.setErrorCode(ex.getErrorCode());
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Categorizes field errors into missing fields, type mismatches, and validation errors.
     *
     * @param fieldErrors the list of field errors from the validation exception
     * @return Map containing categorized field errors
     */
    private Map<String, List<String>> categorizeFieldErrors(List<FieldError> fieldErrors) {
        Map<String, List<String>> errorCategories = new HashMap<>();
        List<String> missingFields = new ArrayList<>();
        List<String> typeMismatchFields = new ArrayList<>();
        List<String> validationErrorMessages = new ArrayList<>();

        for (FieldError error : fieldErrors) {
            if (error.getRejectedValue() == null) {
                missingFields.add(error.getField());
            } else if (error.getCode() != null && error.getCode().contains("typeMismatch")) {
                typeMismatchFields.add(error.getField());
            } else {
                validationErrorMessages.add(error.getDefaultMessage());
            }
        }

        if (!missingFields.isEmpty()) errorCategories.put("missingFields", missingFields);
        if (!typeMismatchFields.isEmpty()) errorCategories.put("typeMismatchFields", typeMismatchFields);
        if (!validationErrorMessages.isEmpty()) errorCategories.put("validationErrors", validationErrorMessages);

        return errorCategories;
    }

    /**
     * Extracts the field name from the error message if deserialization fails due to type mismatch.
     *
     * @param message the exception message
     * @return String containing the field name or null if not found
     */
    private String extractFieldErrorFromMessage(String message) {
        if (message.contains("Cannot deserialize value of type")) {
            String[] parts = message.split("from String");
            if (parts.length > 1) {
                return "Check the field corresponding to: " + parts[1].trim();
            }
        }
        return null;
    }

    /**
     * Builds a standardized error response for the API.
     *
     * @param errorMessage
     * @param errorDetails
     * @param status
     * @return ResponseEntity with the API response containing the error message and details
     */
    private ResponseEntity<ApiResponse> buildErrorResponse(String errorMessage, Map<String, ?> errorDetails, HttpStatus status) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(false);
        apiResponse.setErrorMessage(errorDetails);
        apiResponse.setStatus(status.toString());
        return new ResponseEntity<>(apiResponse, status);
    }
}
