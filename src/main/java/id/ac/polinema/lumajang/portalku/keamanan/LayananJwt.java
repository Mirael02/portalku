package id.ac.polinema.lumajang.portalku.keamanan;

import java.sql.Date;
import java.time.Instant;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class LayananJwt {
    private final SecretKey kunci;
    private final long masaBerlakuAkses;

    public LayananJwt(@Value("${jwt.secret}") String rahasia,
            @Value("${jwt.kedaluwarsa-akses}") long masaBerlakuAkses) {
        // Kunci dibaca sebagai Base64, bukan sebagai teks biasa:
        // 32 byte acak jauh lebih kuat daripada 32 karakter yang dapat dibaca manusia.
        this.kunci = Keys.hmacShaKeyFor(Decoders.BASE64.decode(rahasia));
        this.masaBerlakuAkses = masaBerlakuAkses;
    }

    public String terbitkan(String email, String peran) {
        Instant sekarang = Instant.now();
        return Jwts.builder()
                .subject(email)
                .claim("peran", peran)
                .issuedAt(Date.from(sekarang))
                .expiration(Date.from(sekarang.plusMillis(masaBerlakuAkses)))
                .signWith(kunci)
                .compact();
    }

    public String bacaSubjek(String token) {
        return Jwts.parser()
                .verifyWith(kunci)
                .build()
                .parseSignedClaims(token)
                // melempar galat bila tanda tangan
                .getPayload()
                // atau masa berlakunya tidak sah
                .getSubject();
    }
}