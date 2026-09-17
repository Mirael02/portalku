package id.ac.polinema.lumajang.portalku.jurnal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataAwalJurnal implements CommandLineRunner {
    private final JurnalRepository jurnalRepository;

    @Override
    public void run(String... args) {
        if (jurnalRepository.count() > 0) {
            return;
        }
        jurnalRepository.save(new Jurnal(null, "JISEBI", "Universitas Airlangga", 2015));
        jurnalRepository.save(new Jurnal(null, "Jurnal Informatika Polinema", "Politeknik Negeri Malang", 2015));
    }
}