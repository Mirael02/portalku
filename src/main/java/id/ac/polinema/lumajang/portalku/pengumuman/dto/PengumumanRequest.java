package id.ac.polinema.lumajang.portalku.pengumuman.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record PengumumanRequest(
                @Schema(description = "Judul pengumuman, harus unik", example = "Jadwal UTS Semester Genap 2026/2027") @NotBlank(message = "Judul wajib diisi") @Size(max = 150, message = "Judul maksimal 150 karakter") String judul,

                @Schema(description = "Isi detail pengumuman", example = "Pelaksanaan UTS akan dimulai pada tanggal...") @NotBlank(message = "Isi pengumuman wajib diisi") String isi,

                @Schema(description = "Tanggal pengumuman diterbitkan", example = "2026-09-22") @NotNull(message = "Tanggal terbit wajib diisi") @PastOrPresent(message = "Tanggal terbit tidak boleh di masa depan") LocalDate tanggalTerbit,

                @Schema(description = "Nomor kategori", example = "1") @NotNull(message = "Kategori wajib dipilih") Integer idKategori) {
}