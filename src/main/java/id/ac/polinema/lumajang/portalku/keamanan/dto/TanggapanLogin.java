package id.ac.polinema.lumajang.portalku.keamanan.dto;

public record TanggapanLogin(
        String tokenAkses,
        String tokenPenyegar,
        String tipe,
        // selalu "Bearer"
        long kedaluwarsaDalamDetik,
        String nama,
        String peran) {
}