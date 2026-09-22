package id.ac.polinema.lumajang.portalku.pengumuman;

import id.ac.polinema.lumajang.portalku.kategori.Kategori;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.*;
import org.springframework.stereotype.Component;

@Component
public class PengumumanMapper {
    public PengumumanResponse keResponse(Pengumuman p) {
        return new PengumumanResponse(
                p.getId(), p.getJudul(), p.getIsi(), p.getTanggalTerbit(),
                p.getJumlahDilihat(), p.getKategori().getNama(), p.getDaftarLampiran().size()
        );
    }

    public Pengumuman keEntity(PengumumanRequest req, Kategori kategori) {
        Pengumuman p = new Pengumuman();
        p.setJudul(req.judul());
        p.setIsi(req.isi());
        p.setTanggalTerbit(req.tanggalTerbit());
        p.setKategori(kategori);
        p.setJumlahDilihat(0); // ditentukan server, bukan klien
        return p;
    }

    public PengumumanRingkasResponse keRingkas(Pengumuman p) {
        return new PengumumanRingkasResponse(
                p.getId(), p.getJudul(), p.getKategori().getNama(),
                p.getTanggalTerbit(), p.getJumlahDilihat()
        );
    }

    // Mengubah entity yang sudah ada; id dan jumlahDilihat sengaja tidak disentuh
    public void terapkan(PengumumanRequest req, Pengumuman p, Kategori kategori) {
        p.setJudul(req.judul());
        p.setIsi(req.isi());
        p.setTanggalTerbit(req.tanggalTerbit());
        p.setKategori(kategori);
    }
}