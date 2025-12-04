package org.example.Responses;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.example.Row;

import java.util.List;

@JsonAutoDetect
public class SuccessResponse extends Response {
    public List<Row> rows;

    public SuccessResponse(List<Row> rows) {
        this.rows = rows;
    }
}
