package id.ac.polinema.lumajang.portalku.pengumuman;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pengumuman")
@RequiredArgsConstructor
public class PengumumanController {

    private final PengumumanService pengumumanService;

    @GetMapping
    public List<PengumumanDTO> semua() {
        return pengumumanService.cariSemua();
    }

    @PostMapping
    public PengumumanDTO tambah(@RequestBody Pengumuman pengumuman) {
        return pengumumanService.tambah(pengumuman);
    }

    @DeleteMapping("/{id}")
    public void hapus(@PathVariable Integer id) {
        pengumumanService.hapus(id);
    }
}