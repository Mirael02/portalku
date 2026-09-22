package id.ac.polinema.lumajang.portalku.shared;

import java.net.URI;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class PenanganGalatGlobal {

    private static final Logger log = LoggerFactory.getLogger(PenanganGalatGlobal.class);
    private static final String DASAR = "https://api.psdku.polinema.ac.id/galat/";

    @ExceptionHandler(SumberDayaTidakDitemukanException.class)
    public ProblemDetail tidakDitemukan(SumberDayaTidakDitemukanException ex) {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage());
        p.setTitle("Sumber daya tidak ditemukan");
        p.setType(URI.create(DASAR + "tidak-ditemukan"));
        p.setProperty("waktu", Instant.now());
        return p;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validasiGagal(MethodArgumentNotValidException ex) {
        Map<String, String> kesalahan = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                e -> kesalahan.put(e.getField(), e.getDefaultMessage()));

        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Terdapat " + kesalahan.size() + " kesalahan pada data yang dikirim");
        p.setTitle("Data yang dikirim tidak valid");
        p.setType(URI.create(DASAR + "validasi"));
        p.setProperty("waktu", Instant.now());
        p.setProperty("kesalahan", kesalahan);
        return p;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail badanTidakTerbaca(HttpMessageNotReadableException ex) {
        log.warn("Badan permintaan tidak terbaca: {}", ex.getMessage());
        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Badan permintaan tidak dapat dibaca. Pastikan formatnya JSON yang benar.");
        p.setTitle("Permintaan tidak dapat dipahami");
        p.setType(URI.create(DASAR + "permintaan-salah"));
        p.setProperty("waktu", Instant.now());
        return p;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail tipeTidakCocok(MethodArgumentTypeMismatchException ex) {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Nilai '" + ex.getValue() + "' tidak sesuai untuk parameter '"
                        + ex.getName() + "'");
        p.setTitle("Parameter tidak sesuai");
        p.setType(URI.create(DASAR + "permintaan-salah"));
        p.setProperty("waktu", Instant.now());
        return p;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail bentrokData(DataIntegrityViolationException ex) {
        log.warn("Pelanggaran batasan basis data", ex);
        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                "Data yang dikirim bentrok dengan data yang sudah ada. "
                        + "Periksa nilai yang harus unik.");
        p.setTitle("Data bentrok");
        p.setType(URI.create(DASAR + "bentrok"));
        p.setProperty("waktu", Instant.now());
        return p;
    }

    // Jaring pengaman terakhir untuk galat 500
    @ExceptionHandler(Exception.class)
    public ProblemDetail galatTakTerduga(Exception ex) {
        log.error("Galat tak terduga", ex); // Rinci ke log sistem
        ProblemDetail p = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Terjadi kesalahan di sisi server. Silakan hubungi pengelola.");
        p.setTitle("Kesalahan server");
        p.setType(URI.create(DASAR + "kesalahan-server"));
        p.setProperty("waktu", Instant.now());
        return p; // Secukupnya ke klien
    }
}