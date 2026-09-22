package id.ac.polinema.lumajang.portalku.lampiran;

import id.ac.polinema.lumajang.portalku.pengumuman.Pengumuman;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lampiran")
@Getter
@Setter
@NoArgsConstructor
public class Lampiran {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nama_berkas", nullable = false)
    private String namaBerkas;

    @Column(nullable = false)
    private Integer ukuran;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pengumuman_id", nullable = false)
    private Pengumuman pengumuman;
}