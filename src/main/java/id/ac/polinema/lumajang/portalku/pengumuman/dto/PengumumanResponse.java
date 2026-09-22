package id.ac.polinema.lumajang.portalku.pengumuman.dto;

import java.time.LocalDate;

// DTO tanggapan — memuat id dan data turunan, tanpa aturan validasi
public record PengumumanResponse(
        Integer id,
        String judul,
        String isi,
        LocalDate tanggalTerbit,
        Integer jumlahDilihat,
        String namaKategori,
        int jumlahLampiran) { }