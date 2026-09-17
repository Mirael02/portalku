package id.ac.polinema.lumajang.portalku.kurikulum;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import id.ac.polinema.lumajang.portalku.prodi.Prodi;
import id.ac.polinema.lumajang.portalku.matakuliah.MataKuliah;

@Entity
@Table(name = "kurikulum")
@Getter
@Setter
@NoArgsConstructor
public class Kurikulum {
    public enum Status {
        AKTIF, NONAKTIF
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true, length = 25)
    private String kode;
    @Column(name = "tahun_berlaku", nullable = false)
    private Integer tahunBerlaku;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_prodi", nullable = false)
    private Prodi prodi;
    @OneToMany(mappedBy = "kurikulum", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MataKuliah> daftarMataKuliah = new ArrayList<>();

    public void tambahMataKuliah(MataKuliah mk) {
        daftarMataKuliah.add(mk);
        mk.setKurikulum(this);
    }
}