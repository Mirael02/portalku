package id.ac.polinema.lumajang.portalku.pengumuman;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PengumumanRepository extends JpaRepository<Pengumuman, Integer> {

    // Mencegah N+1 saat menampilkan daftar pengumuman
    @Query("SELECT p FROM Pengumuman p JOIN FETCH p.kategori")
    List<Pengumuman> findAllWithKategori();
}