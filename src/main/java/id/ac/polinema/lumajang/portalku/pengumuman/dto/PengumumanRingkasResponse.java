package id.ac.polinema.lumajang.portalku.pengumuman.dto;

import java.time.LocalDate;

public record PengumumanRingkasResponse(
        Integer id,
        String judul,
        String namaKategori,
        LocalDate tanggalTerbit,
        Integer jumlahDilihat) {
}