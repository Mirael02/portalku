package id.ac.polinema.lumajang.portalku.keamanan;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "token_penyegar")
@Getter @Setter @NoArgsConstructor
public class TokenPenyegar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 255)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pengguna", nullable = false)
    private Pengguna pengguna;

    @Column(nullable = false)
    private Instant kedaluwarsa;

    @Column(nullable = false)
    private boolean dicabut;
}