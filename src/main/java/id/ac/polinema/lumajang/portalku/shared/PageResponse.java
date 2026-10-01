package id.ac.polinema.lumajang.portalku.shared;

import java.util.List;
import org.springframework.data.domain.Page;

public record PageResponse<T>(
        List<T> isi,
        int halaman,
        int ukuran,
        long totalData,
        int totalHalaman,
        boolean pertama,
        boolean terakhir) {

    public static <T> PageResponse<T> dari(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast());
    }
}