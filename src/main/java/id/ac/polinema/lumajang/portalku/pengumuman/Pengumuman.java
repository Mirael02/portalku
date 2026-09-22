package id.ac.polinema.lumajang.portalku.pengumuman;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import id.ac.polinema.lumajang.portalku.kategori.Kategori;
import id.ac.polinema.lumajang.portalku.lampiran.Lampiran;
import id.ac.polinema.lumajang.portalku.prodi.Prodi;

@Entity
@Table(name = "pengumuman")
@Getter
@Setter
@NoArgsConstructor
public class Pengumuman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 150)
    private String judul;

    @Column(nullable = false, length = 5000)
    private String isi;

    @Column(name = "tanggal_terbit", nullable = false)
    private LocalDate tanggalTerbit;

    @Column(name = "jumlah_dilihat", nullable = false)
    private Integer jumlahDilihat = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kategori_id", nullable = false)
    private Kategori kategori;

    @OneToMany(mappedBy = "pengumuman", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Lampiran> daftarLampiran = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "pengumuman_prodi", joinColumns = @JoinColumn(name = "pengumuman_id"), inverseJoinColumns = @JoinColumn(name = "prodi_id"))
    private Set<Prodi> prodiDituju = new HashSet<>();

    // Metode bantu untuk menjaga konsistensi dua arah di memori
    public void tambahLampiran(Lampiran lampiran) {
        daftarLampiran.add(lampiran);
        lampiran.setPengumuman(this);
    }

    public void hapusLampiran(Lampiran lampiran) {
        daftarLampiran.remove(lampiran);
        lampiran.setPengumuman(null);
    }
}