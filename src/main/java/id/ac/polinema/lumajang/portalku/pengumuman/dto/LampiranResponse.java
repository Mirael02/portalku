package id.ac.polinema.lumajang.portalku.pengumuman.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LampiranResponse(
        @Schema(example = "1") Integer id,
        @Schema(example = "Panduan_Akademik.pdf") String namaBerkas,
        @Schema(example = "1048576") Integer ukuran) {
}