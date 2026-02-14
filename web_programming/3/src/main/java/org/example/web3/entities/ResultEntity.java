package org.example.web3.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "results")
public class ResultEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Min(value = -4, message = "X должен быть не меньше -4")
    @Max(value = 4, message = "X должен быть не больше 4")
    @Column(name = "x_coordinate", nullable = false)
    private Integer x;

    @Column(name = "y_coordinate", nullable = false)
    private String y;

    @DecimalMin(value = "1.0", message = "Радиус должен быть не менее 1")
    @DecimalMax(value = "3.0", message = "Радиус должен быть не более 3")
    @Column(name = "radius", nullable = false)
    private Double r;

    @Column(name = "hit_result", nullable = false)
    private Boolean hit;

    @Column(name = "check_time", nullable = false)
    private String currentTime;

    public ResultEntity() {}

    public ResultEntity(Integer x, String y, Double r, Boolean hit, String currentTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.currentTime = currentTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public Boolean getHit() {
        return hit;
    }

    public void setHit(Boolean hit) {
        this.hit = hit;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}