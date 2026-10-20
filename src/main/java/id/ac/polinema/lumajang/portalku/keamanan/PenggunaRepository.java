package id.ac.polinema.lumajang.portalku.keamanan;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PenggunaRepository extends JpaRepository<Pengguna, Integer> {
    Optional<Pengguna> findByEmailAndAktifTrue(String email);
}