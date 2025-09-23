package com.emna.micro_service1.DTO;

public record ResetPasswordDTO(
        Integer userId,
        String newPassword
) {}