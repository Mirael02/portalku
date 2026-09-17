package id.ac.polinema.lumajang.portalku.dosen;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DosenRepository extends JpaRepository<Dosen, Integer> {
    Optional<Dosen> findByNip(String nip);
}