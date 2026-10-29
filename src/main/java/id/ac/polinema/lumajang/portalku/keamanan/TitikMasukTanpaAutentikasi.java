package id.ac.polinema.lumajang.portalku.keamanan;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;

@Component
public class TitikMasukTanpaAutentikasi implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res,
            AuthenticationException ex) throws IOException {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                "Permintaan ini memerlukan autentikasi. Sertakan token pada header Authorization: Bearer <token>.");
        p.setTitle("Belum terautentikasi");
        p.setType(URI.create("https://api.psdku.polinema.ac.id/galat/autentikasi"));
        p.setProperty("waktu", Instant.now());

        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.setContentType("application/problem+json");
        objectMapper.writeValue(res.getOutputStream(), p);
    }
}