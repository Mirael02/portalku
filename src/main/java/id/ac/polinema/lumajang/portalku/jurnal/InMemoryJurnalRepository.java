package id.ac.polinema.lumajang.portalku.jurnal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class InMemoryJurnalRepository implements JurnalRepository {
    private final Map<Integer, Jurnal> data = new ConcurrentHashMap<>();
    private final AtomicInteger urutan = new AtomicInteger(0);

    public InMemoryJurnalRepository() {
        save(new Jurnal(null, "JISEBI", "Universitas Airlangga", 2015));
        save(new Jurnal(null, "Jurnal Informatika Polinema", "Politeknik Negeri Malang",
                2015));
    }

    @Override
    public List<Jurnal> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<Jurnal> findById(Integer id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Jurnal save(Jurnal jurnal) {
        if (jurnal.getId() == null) {
            jurnal.setId(urutan.incrementAndGet());
        }
        data.put(jurnal.getId(), jurnal);
        return jurnal;
    }

    @Override
    public boolean deleteById(Integer id) {
        return data.remove(id) != null;
    }
}