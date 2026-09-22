package id.ac.polinema.lumajang.portalku.pengumuman;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class PengumumanDTO {
    private Integer id;
    private String judul;
    private String isi;
    private LocalDate tanggalTerbit;
    private Integer jumlahDilihat;

    // Data turunan dari relasi
    private String namaKategori;
    private Integer jumlahLampiran;
}