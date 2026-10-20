package id.ac.polinema.lumajang.portalku.keamanan.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PermintaanLogin(
        @NotBlank(message = "Email wajib diisi") @Email(message = "Format email tidak sah") String email,
        @NotBlank(message = "Kata sandi wajib diisi") String sandi) {
}