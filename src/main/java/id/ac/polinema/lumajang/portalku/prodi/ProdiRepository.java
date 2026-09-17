package id.ac.polinema.lumajang.portalku.prodi;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdiRepository extends JpaRepository<Prodi, Integer> {
    Optional<Prodi> findByKode(String kode);
}
