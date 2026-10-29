package id.ac.polinema.lumajang.portalku.keamanan;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataAwalPengguna implements CommandLineRunner {

    private final PenggunaRepository penggunaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (penggunaRepository.count() > 0) {
            return;
        }

        // Membuat akun ADMIN
        Pengguna admin = new Pengguna();
        admin.setEmail("admin@example.com");
        admin.setSandi(passwordEncoder.encode("password123"));
        admin.setNama("Administrator Portal");
        admin.setPeran(Pengguna.Peran.ADMIN);
        admin.setAktif(true);
        penggunaRepository.save(admin);

        // Membuat akun EDITOR
        Pengguna editor = new Pengguna();
        editor.setEmail("editor@example.com");
        editor.setSandi(passwordEncoder.encode("password123"));
        editor.setNama("Editor Akademik");
        editor.setPeran(Pengguna.Peran.EDITOR);
        editor.setAktif(true);
        penggunaRepository.save(editor);

        System.out.println(">> Data awal pengguna (Admin & Editor) berhasil dimasukkan ke memori/database.");
    }
}