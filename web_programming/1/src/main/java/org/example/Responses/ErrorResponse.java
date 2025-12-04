package org.example.Responses;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class ErrorResponse extends Response {
    public String reason;

    public  ErrorResponse(String reason) {
        this.reason = reason;
    }
}
