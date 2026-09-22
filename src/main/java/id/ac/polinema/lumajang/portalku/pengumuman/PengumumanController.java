package id.ac.polinema.lumajang.portalku.pengumuman;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import id.ac.polinema.lumajang.portalku.pengumuman.dto.LampiranRequest;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.LampiranResponse;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.PengumumanRequest;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.PengumumanResponse;
import id.ac.polinema.lumajang.portalku.pengumuman.dto.PengumumanRingkasResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pengumuman")
@RequiredArgsConstructor
@Tag(name = "Pengumuman", description = "Pengelolaan pengumuman akademik")
public class PengumumanController {
    private final PengumumanService pengumumanService;

    @Operation(summary = "Daftar seluruh pengumuman", description = "Mengembalikan bentuk ringkas tanpa isi lengkap pengumuman")
    @ApiResponse(responseCode = "200", description = "Daftar berhasil diambil")
    @GetMapping
    public List<PengumumanRingkasResponse> semua() {
        return pengumumanService.cariSemua();
    }

    @Operation(summary = "Menambah pengumuman baru")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pengumuman berhasil dibuat"),
            @ApiResponse(responseCode = "422", description = "Data tidak lolos validasi", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Judul sudah dipakai", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<PengumumanResponse> tambah(
            @Valid @RequestBody PengumumanRequest req) {
        PengumumanResponse hasil = pengumumanService.tambah(req);

        // Merakit alamat (URI) untuk header Location
        URI lokasi = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(hasil.id())
                .toUri();

        // Mengembalikan status 201 beserta header lokasi dan body JSON
        return ResponseEntity.created(lokasi).body(hasil);
    }

    @GetMapping("/{id}/lampiran")
    @Operation(summary = "Dapatkan daftar lampiran dari suatu pengumuman")
    public List<LampiranResponse> daftarLampiran(@PathVariable Integer id) {
        return pengumumanService.cariLampiranByPengumuman(id);
    }

    @PostMapping("/{id}/lampiran")
    @Operation(summary = "Tambah lampiran baru pada suatu pengumuman")
    public ResponseEntity<LampiranResponse> tambahLampiran(
            @PathVariable Integer id,
            @Valid @RequestBody LampiranRequest req) {
        LampiranResponse hasil = pengumumanService.tambahLampiran(id, req);
        URI lokasi = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(hasil.id())
                .toUri();
        return ResponseEntity.created(lokasi).body(hasil);
    }

    @PatchMapping("/{id}/dilihat")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Tambah jumlah dilihat pengumuman sebanyak 1")
    public void incrementDilihat(@PathVariable Integer id) {
        pengumumanService.incrementJumlahDilihat(id);
    }
}