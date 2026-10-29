package id.ac.polinema.lumajang.portalku.keamanan;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;

@Component
public class PenanganAksesDitolak implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                "Anda sudah terautentikasi, tetapi tidak memiliki izin (peran) yang cukup untuk mengakses sumber daya ini."
        );
        p.setTitle("Akses Ditolak");
        p.setType(URI.create("https://api.psdku.polinema.ac.id/galat/otorisasi"));
        p.setProperty("waktu", Instant.now());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/problem+json");
        objectMapper.writeValue(response.getOutputStream(), p);
    }
}