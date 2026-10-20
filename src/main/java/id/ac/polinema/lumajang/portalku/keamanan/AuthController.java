package id.ac.polinema.lumajang.portalku.keamanan;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.ac.polinema.lumajang.portalku.keamanan.dto.PermintaanLogin;
import id.ac.polinema.lumajang.portalku.keamanan.dto.TanggapanLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autentikasi", description = "Login, penyegaran token, dan keluar")
public class AuthController {
    private final LayananAuth layananAuth;

    @Operation(summary = "Masuk dan memperoleh token")
    @PostMapping("/login")
    public TanggapanLogin login(@Valid @RequestBody PermintaanLogin req) {
        return layananAuth.login(req);
    }
}