package id.ac.polinema.lumajang.portalku.prodi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import id.ac.polinema.lumajang.portalku.kurikulum.Kurikulum;
import id.ac.polinema.lumajang.portalku.pengumuman.Pengumuman;

@Entity
@Table(name = "program_studi")
@Getter
@Setter
@NoArgsConstructor
public class Prodi {
    public enum Jenjang {
        D3, D4
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true, length = 25)
    private String kode;
    @Column(nullable = false, length = 100)
    private String nama;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 5)
    private Jenjang jenjang;
    @OneToMany(mappedBy = "prodi", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Kurikulum> daftarKurikulum = new ArrayList<>();

    public void tambahKurikulum(Kurikulum kurikulum) {
        daftarKurikulum.add(kurikulum);
        kurikulum.setProdi(this);
    }

    public void hapusKurikulum(Kurikulum kurikulum) {
        daftarKurikulum.remove(kurikulum);
        kurikulum.setProdi(null);
    }

    @ManyToMany(mappedBy = "prodiDituju", fetch = FetchType.LAZY)
    private Set<Pengumuman> pengumuman = new HashSet<>();
}