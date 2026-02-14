package org.example.Web4.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class PointRequest {

    @NotNull(message = "Введите значение X")
    @DecimalMin(value = "-5", inclusive = false, message = "X должен быть больше -5")
    @DecimalMax(value = "5", inclusive = false, message = "X должен быть меньше 5")
    private Double x;

    @NotNull(message = "Введите значение Y")
    @DecimalMin(value = "-5", inclusive = false, message = "Y должен быть больше -5")
    @DecimalMax(value = "3", inclusive = false, message = "Y должен быть меньше 3")
    private Double y;

    @NotNull(message = "Введите значение R")
    @DecimalMin(value = "0", inclusive = false, message = "R должен быть больше 0")
    @DecimalMax(value = "5", inclusive = false, message = "R должен быть меньше 5")
    private Double r;

    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }

    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }

    public Double getR() { return r; }
    public void setR(Double r) { this.r = r; }
}