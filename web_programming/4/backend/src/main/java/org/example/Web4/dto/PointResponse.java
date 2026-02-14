package org.example.Web4.dto;

import org.example.Web4.model.PointResult;

import java.time.LocalDateTime;

public class PointResponse {
    private Long id;
    private Double x;
    private Double y;
    private Double r;
    private Boolean hit;
    private Long executionTime;
    private LocalDateTime createdAt;

    public PointResponse(PointResult pointResult) {
        this.id = pointResult.getId();
        this.x = pointResult.getX();
        this.y = pointResult.getY();
        this.r = pointResult.getR();
        this.hit = pointResult.getHit();
        this.executionTime = pointResult.getExecutionTime();
        this.createdAt = pointResult.getCreatedAt();
    }

    public Long getId() { return id; }
    public Double getX() { return x; }
    public Double getY() { return y; }
    public Double getR() { return r; }
    public Boolean getHit() { return hit; }
    public Long getExecutionTime() { return executionTime; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}