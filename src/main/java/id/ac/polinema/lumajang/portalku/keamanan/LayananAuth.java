package id.ac.polinema.lumajang.portalku.keamanan;

import id.ac.polinema.lumajang.portalku.keamanan.dto.PermintaanLogin;
import id.ac.polinema.lumajang.portalku.keamanan.dto.TanggapanLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LayananAuth {

    private final AuthenticationManager authenticationManager;
    private final LayananJwt layananJwt;
    private final PenggunaRepository penggunaRepository;
    private final TokenPenyegarRepository tokenPenyegarRepository;

    @Value("${jwt.kedaluwarsa-akses:900000}") // Default 15 menit dalam milidetik
    private long masaBerlakuAkses;

    @Value("${jwt.kedaluwarsa-penyegar:604800000}") // Default 7 hari dalam milidetik
    private long masaBerlakuPenyegar;

    @Transactional
    public TanggapanLogin login(PermintaanLogin req) {
        // 1. Verifikasi email dan kata sandi menggunakan AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.sandi()));

        // 2. Ambil data pengguna dari basis data
        Pengguna pengguna = penggunaRepository.findByEmailAndAktifTrue(req.email())
                .orElseThrow(() -> new RuntimeException("Pengguna tidak ditemukan"));

        // 3. Terbitkan token akses JWT[cite: 1]
        String tokenAkses = layananJwt.terbitkan(pengguna.getEmail(), pengguna.getPeran().name());

        // 4. Buat dan simpan token penyegar ke basis data[cite: 1]
        String tokenPenyegarStr = UUID.randomUUID().toString();
        TokenPenyegar tokenPenyegar = new TokenPenyegar();
        tokenPenyegar.setToken(tokenPenyegarStr);
        tokenPenyegar.setPengguna(pengguna);
        tokenPenyegar.setKedaluwarsa(Instant.now().plusMillis(masaBerlakuPenyegar));
        tokenPenyegar.setDicabut(false);
        tokenPenyegarRepository.save(tokenPenyegar);

        // 5. Kembalikan tanggapan login berisi kedua token
        return new TanggapanLogin(
                tokenAkses,
                tokenPenyegarStr,
                "Bearer",
                masaBerlakuAkses / 1000,
                pengguna.getNama(),
                pengguna.getPeran().name());
    }

    @Transactional
    public TanggapanLogin segarkan(String tokenPenyegarStr) {
        // 1. Cari token penyegar di basis data[cite: 1]
        TokenPenyegar tokenPenyegar = tokenPenyegarRepository.findByToken(tokenPenyegarStr)
                .orElseThrow(() -> new RuntimeException("Token penyegar tidak valid"));

        // 2. Periksa apakah sudah dicabut atau kedaluwarsa[cite: 1]
        if (tokenPenyegar.isDicabut() || tokenPenyegar.getKedaluwarsa().isBefore(Instant.now())) {
            throw new RuntimeException("Token penyegar sudah kedaluwarsa atau dicabut");
        }

        // 3. Cabut token penyegar lama (Rotasi Token)[cite: 1]
        tokenPenyegar.setDicabut(true);
        tokenPenyegarRepository.save(tokenPenyegar);

        // 4. Ambil data pengguna terkait
        Pengguna pengguna = tokenPenyegar.getPengguna();

        // 5. Terbitkan token akses baru dan token penyegar baru[cite: 1]
        String tokenAksesBaru = layananJwt.terbitkan(pengguna.getEmail(), pengguna.getPeran().name());
        String tokenPenyegarBaruStr = UUID.randomUUID().toString();

        TokenPenyegar tokenPenyegarBaru = new TokenPenyegar();
        tokenPenyegarBaru.setToken(tokenPenyegarBaruStr);
        tokenPenyegarBaru.setPengguna(pengguna);
        tokenPenyegarBaru.setKedaluwarsa(Instant.now().plusMillis(masaBerlakuPenyegar));
        tokenPenyegarBaru.setDicabut(false);
        tokenPenyegarRepository.save(tokenPenyegarBaru);

        // 6. Kembalikan tanggapan login baru
        return new TanggapanLogin(
                tokenAksesBaru,
                tokenPenyegarBaruStr,
                "Bearer",
                masaBerlakuAkses / 1000,
                pengguna.getNama(),
                pengguna.getPeran().name());
    }

    @Transactional
    public void cabut(String tokenPenyegarStr) {
        // Cari token penyegar di basis data dan tandai dicabut (proses logout)[cite: 1]
        TokenPenyegar tokenPenyegar = tokenPenyegarRepository.findByToken(tokenPenyegarStr)
                .orElseThrow(() -> new RuntimeException("Token penyegar tidak valid"));

        tokenPenyegar.setDicabut(true);
        tokenPenyegarRepository.save(tokenPenyegar);
    }
}