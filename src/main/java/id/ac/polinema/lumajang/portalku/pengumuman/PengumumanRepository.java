package id.ac.polinema.lumajang.portalku.pengumuman;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PengumumanRepository extends JpaRepository<Pengumuman, Integer> {

    @Query(value = """
            SELECT p FROM Pengumuman p
            JOIN FETCH p.kategori
            WHERE (:kataKunci IS NULL OR LOWER(p.judul) LIKE LOWER(CONCAT('%', :kataKunci, '%')))
              AND (:kategoriId IS NULL OR p.kategori.id = :kategoriId)
            """, countQuery = """
            SELECT COUNT(p) FROM Pengumuman p
            WHERE (:kataKunci IS NULL OR LOWER(p.judul) LIKE LOWER(CONCAT('%', :kataKunci, '%')))
              AND (:kategoriId IS NULL OR p.kategori.id = :kategoriId)
            """)
    Page<Pengumuman> cariDenganFilter(
            @Param("kataKunci") String kataKunci,
            @Param("kategoriId") Integer kategoriId,
            Pageable pageable);
}