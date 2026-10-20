package id.ac.polinema.lumajang.portalku.keamanan;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LayananDetailPengguna implements UserDetailsService {

    private final PenggunaRepository penggunaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Pengguna p = penggunaRepository.findByEmailAndAktifTrue(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Pengguna tidak ditemukan"));

        return User.withUsername(p.getEmail())
                .password(p.getSandi())
                .authorities("ROLE_" + p.getPeran().name())
                .build();
    }
}