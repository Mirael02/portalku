package id.ac.polinema.lumajang.portalku.percobaan;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.ac.polinema.lumajang.portalku.pengumuman.Pengumuman;
import id.ac.polinema.lumajang.portalku.pengumuman.PengumumanRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/percobaan")
@RequiredArgsConstructor
public class PercobaanController {
    private final PengumumanRepository pengumumanRepository;

    @GetMapping
    public List<Pengumuman> semua() {
        return pengumumanRepository.findAll(); // sengaja keliru
    }
}