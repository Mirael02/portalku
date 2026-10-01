package id.ac.polinema.lumajang.portalku.shared;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({PengumumanTidakDitemukanException.class, KategoriTidakDitemukanException.class})
    public ResponseEntity<ProblemDetail> handleTidakDitemukan(RuntimeException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, 
                ex.getMessage()
        );
        problem.setTitle("Sumber Daya Tidak Ditemukan");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String detail = String.format("Parameter '%s' dengan nilai '%s' tidak sesuai. Diharapkan tipe %s.",
                ex.getName(), ex.getValue(), 
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "angka");
        
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
        problem.setTitle("Parameter Tidak Sesuai");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleConflict(DataIntegrityViolationException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                "Judul pengumuman sudah terpakai. Gunakan judul lain."
        );
        problem.setTitle("Data Bentrok");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }
}