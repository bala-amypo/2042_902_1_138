// package com.example.demo.exception;

// import com.example.demo.dto.ApiResponseDTO;
// import org.springframework.http.*;
// import org.springframework.web.bind.annotation.*;

// @RestControllerAdvice
// public class GlobalExceptionHandler {

//     @ExceptionHandler(ResourceNotFoundException.class)
//     public ResponseEntity<ApiResponseDTO> handleNotFound(ResourceNotFoundException ex) {
//         return new ResponseEntity<>(
//                 new ApiResponseDTO(false, ex.getMessage()),
//                 HttpStatus.NOT_FOUND
//         );
//     }

//     @ExceptionHandler(BadRequestException.class)
//     public ResponseEntity<ApiResponseDTO> handleBadRequest(BadRequestException ex) {
//         return new ResponseEntity<>(
//                 new ApiResponseDTO(false, ex.getMessage()),
//                 HttpStatus.BAD_REQUEST
//         );
//     }

//     @ExceptionHandler(UnauthorizedException.class)
//     public ResponseEntity<ApiResponseDTO> handleUnauthorized(UnauthorizedException ex) {
//         return new ResponseEntity<>(
//                 new ApiResponseDTO(false, ex.getMessage()),
//                 HttpStatus.UNAUTHORIZED
//         );
//     }

//     @ExceptionHandler(Exception.class)
//     public ResponseEntity<ApiResponseDTO> handleGeneric(Exception ex) {
//         return new ResponseEntity<>(
//                 new ApiResponseDTO(false, "Internal server error"),
//                 HttpStatus.INTERNAL_SERVER_ERROR
//         );
//     }
// }
package com.example.demo.exception;

import com.example.demo.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDTO<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleGeneric(Exception ex) {
        ex.printStackTrace(); // Print stack trace to console
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponseDTO<>(false, "Internal server error: " + ex.getMessage(), null));
    }
}
