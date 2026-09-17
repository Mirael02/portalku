package id.ac.polinema.lumajang.portalku.matakuliah;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MataKuliahRepository extends JpaRepository<MataKuliah, Integer> {
    List<MataKuliah> findByKurikulumId(Integer idKurikulum);
}