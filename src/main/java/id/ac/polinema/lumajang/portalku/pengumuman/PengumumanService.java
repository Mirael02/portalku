package id.ac.polinema.lumajang.portalku.pengumuman;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PengumumanService {

    // Memanfaatkan dependency injection berbasis konstruktor dari Lombok
    private final PengumumanRepository pengumumanRepository;

    public List<PengumumanDTO> cariSemua() {
        // Menggunakan JOIN FETCH untuk mencegah N+1
        return pengumumanRepository.findAllWithKategori().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PengumumanDTO tambah(Pengumuman pengumuman) {
        pengumuman.setId(null); // Memastikan ini data baru, bukan update
        Pengumuman tersimpan = pengumumanRepository.save(pengumuman);
        return convertToDTO(tersimpan);
    }

    public void hapus(Integer id) {
        if (!pengumumanRepository.existsById(id)) {
            throw new RuntimeException("Pengumuman dengan ID " + id + " tidak ditemukan");
        }
        pengumumanRepository.deleteById(id);
    }

    // Metode bantu untuk mengubah Entity menjadi DTO sesuai syarat Tugas Mandiri
    private PengumumanDTO convertToDTO(Pengumuman entity) {
        PengumumanDTO dto = new PengumumanDTO();
        dto.setId(entity.getId());
        dto.setJudul(entity.getJudul());
        dto.setIsi(entity.getIsi());
        dto.setTanggalTerbit(entity.getTanggalTerbit());
        dto.setJumlahDilihat(entity.getJumlahDilihat());
        
        // Mengambil nama kategori dengan aman
        if (entity.getKategori() != null) {
            dto.setNamaKategori(entity.getKategori().getNama());
        }
        
        // Menghitung jumlah lampiran (solusi untuk menghindari rekursi JSON)
        if (entity.getDaftarLampiran() != null) {
            dto.setJumlahLampiran(entity.getDaftarLampiran().size());
        } else {
            dto.setJumlahLampiran(0);
        }
        
        return dto;
    }
}