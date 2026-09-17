package id.ac.polinema.lumajang.portalku.jurnal;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JurnalService {
    private final JurnalRepository jurnalRepository;

    public List<Jurnal> cariSemua() {
        return jurnalRepository.findAll();
    }

    public Jurnal cariSatu(Integer id) {
        return jurnalRepository.findById(id)
                .orElseThrow(() -> new JurnalTidakDitemukanException(id));
    }

    public Jurnal tambah(Jurnal jurnal) {
        jurnal.setId(null); // id selalu ditentukan oleh penyimpanan
        return jurnalRepository.save(jurnal);
    }

    public void hapus(Integer id) {
        if (!jurnalRepository.existsById(id)) {
            throw new JurnalTidakDitemukanException(id);
        }
        jurnalRepository.deleteById(id);
    }
}