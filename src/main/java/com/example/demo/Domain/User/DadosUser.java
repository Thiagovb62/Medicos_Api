package com.example.demo.Domain.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


public record DadosUser(
        @NotBlank
        String email,
        @NotBlank
        String password,

        Permissao permissao


    ) {
}
