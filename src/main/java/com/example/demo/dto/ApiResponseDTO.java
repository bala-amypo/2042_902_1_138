// package com.example.demo.dto;

// public class ApiResponseDTO {

//     private boolean success;
//     private String message;

//     public ApiResponseDTO(boolean success, String message) {
//         this.success = success;
//         this.message = message;
//     }

//     public boolean isSuccess() {
//         return success;
//     }

//     public String getMessage() {
//         return message;
//     }
// }
package com.example.demo.dto;

public class ApiResponseDTO<T> {

    private boolean success;
    private String message;
    private T data;

    public ApiResponseDTO() {
    }

    public ApiResponseDTO(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
