package id.ac.polinema.lumajang.portalku.pengumuman;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.ac.polinema.lumajang.portalku.kategori.Kategori;
import id.ac.polinema.lumajang.portalku.kategori.KategoriRepository;
import id.ac.polinema.lumajang.portalku.lampiran.Lampiran;
import id.ac.polinema.lumajang.portalku.lampiran.LampiranRepository;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.LampiranRequest;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.LampiranResponse;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.PengumumanRequest;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.PengumumanResponse;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.PengumumanRingkasResponse;
import id.ac.polinema.lumajang.portalku.shared.KategoriTidakDitemukanException;
import id.ac.polinema.lumajang.portalku.shared.PengumumanTidakDitemukanException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PengumumanService {
    private final PengumumanRepository pengumumanRepository;
    private final KategoriRepository kategoriRepository;
    private final PengumumanMapper mapper;
    private final LampiranRepository lampiranRepository;

    public List<PengumumanRingkasResponse> cariSemua() {
        return pengumumanRepository.findAllWithKategori().stream()
                .map(mapper::keRingkas)
                .toList();
    }

    public PengumumanResponse cariSatu(Integer id) {
        return mapper.keResponse(ambilAtauGagal(id));
    }

    @Transactional
    public PengumumanResponse tambah(PengumumanRequest req) {
        Kategori kategori = kategoriRepository.findById(req.idKategori())
                .orElseThrow(() -> new KategoriTidakDitemukanException(req.idKategori()));
        Pengumuman baru = mapper.keEntity(req, kategori);
        return mapper.keResponse(pengumumanRepository.save(baru));
    }

    @Transactional
    public PengumumanResponse ubah(Integer id, PengumumanRequest req) {
        Pengumuman p = ambilAtauGagal(id);
        Kategori kategori = kategoriRepository.findById(req.idKategori())
                .orElseThrow(() -> new KategoriTidakDitemukanException(req.idKategori()));
        mapper.terapkan(req, p, kategori);
        return mapper.keResponse(p); // tersimpan otomatis saat transaksi selesai
    }

    @Transactional
    public void hapus(Integer id) {
        pengumumanRepository.delete(ambilAtauGagal(id));
    }

    private Pengumuman ambilAtauGagal(Integer id) {
        return pengumumanRepository.findById(id)
                .orElseThrow(() -> new PengumumanTidakDitemukanException(id));
    }
    
    public List<LampiranResponse> cariLampiranByPengumuman(Integer pengumumanId) {
        Pengumuman p = ambilAtauGagal(pengumumanId);
        return p.getDaftarLampiran().stream()
                .map(l -> new LampiranResponse(l.getId(), l.getNamaBerkas(), l.getUkuran()))
                .toList();
    }

    @Transactional
    public LampiranResponse tambahLampiran(Integer pengumumanId, LampiranRequest req) {
        Pengumuman p = ambilAtauGagal(pengumumanId);

        Lampiran lampiran = new Lampiran();
        lampiran.setNamaBerkas(req.namaBerkas());
        lampiran.setUkuran(req.ukuran());

        // Memanfaatkan method bantu pada entity Pengumuman untuk menjaga relasi dua
        // arah
        p.tambahLampiran(lampiran);

        lampiranRepository.save(lampiran); // Pastikan LampiranRepository tersedia
        return new LampiranResponse(lampiran.getId(), lampiran.getNamaBerkas(), lampiran.getUkuran());
    }

    @Transactional
    public void incrementJumlahDilihat(Integer id) {
        Pengumuman p = ambilAtauGagal(id);
        p.setJumlahDilihat(p.getJumlahDilihat() + 1);
    }
}