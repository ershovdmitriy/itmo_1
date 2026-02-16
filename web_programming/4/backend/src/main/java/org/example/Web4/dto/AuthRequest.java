package org.example.Web4.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthRequest {

    @NotBlank(message = "Введите имя")
    @Size(min = 3, max = 50, message = "Имя должно содержать от 3 до 50 символов")
    private String username;

    @NotBlank(message = "Введите пароль")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}