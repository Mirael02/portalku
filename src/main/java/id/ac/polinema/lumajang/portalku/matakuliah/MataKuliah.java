package id.ac.polinema.lumajang.portalku.matakuliah;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import id.ac.polinema.lumajang.portalku.kurikulum.Kurikulum;
import id.ac.polinema.lumajang.portalku.dosen.Dosen;

@Entity
@Table(name = "mata_kuliah")
@Getter
@Setter
@NoArgsConstructor
public class MataKuliah {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true, length = 25)
    private String kode;
    @Column(nullable = false, length = 100)
    private String nama;
    @Column(name = "sks_teori", nullable = false)
    private Integer sksTeori;
    @Column(name = "sks_praktikum", nullable = false)
    private Integer sksPraktikum;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_kurikulum", nullable = false)
    private Kurikulum kurikulum;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "pengampu", joinColumns = @JoinColumn(name = "id_mata_kuliah"), inverseJoinColumns = @JoinColumn(name = "id_dosen"))
    private Set<Dosen> pengampu = new HashSet<>();

    public void tambahPengampu(Dosen dosen) {
        pengampu.add(dosen);
        dosen.getMataKuliah().add(this);
    }
}