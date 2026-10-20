package id.ac.polinema.lumajang.portalku.keamanan;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TokenPenyegarRepository extends JpaRepository<TokenPenyegar, Integer> {
    Optional<TokenPenyegar> findByToken(String token);
}