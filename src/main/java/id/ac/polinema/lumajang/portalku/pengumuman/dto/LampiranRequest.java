package id.ac.polinema.lumajang.portalku.pengumuman.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LampiranRequest(
        @Schema(description = "Nama berkas lampiran", example = "Panduan_Akademik.pdf") @NotBlank(message = "Nama berkas wajib diisi") String namaBerkas,

        @Schema(description = "Ukuran berkas dalam bytes", example = "1048576") @NotNull(message = "Ukuran berkas wajib diisi") @Positive(message = "Ukuran berkas harus bernilai positif") Integer ukuran) {
}