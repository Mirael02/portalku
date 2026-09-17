package id.ac.polinema.lumajang.portalku.dosen;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import id.ac.polinema.lumajang.portalku.matakuliah.MataKuliah;

@Entity
@Table(name = "dosen")
@Getter
@Setter
@NoArgsConstructor
public class Dosen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true, length = 30)
    private String nip;
    @Column(nullable = false, length = 100)
    private String nama;
    @ManyToMany(mappedBy = "pengampu", fetch = FetchType.LAZY)
    private Set<MataKuliah> mataKuliah = new HashSet<>();
}