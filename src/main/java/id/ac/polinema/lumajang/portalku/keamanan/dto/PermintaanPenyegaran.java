package id.ac.polinema.lumajang.portalku.keamanan.dto;

import jakarta.validation.constraints.NotBlank;

public record PermintaanPenyegaran(
        @NotBlank(message = "Token penyegar wajib diisi") String tokenPenyegar) {
}