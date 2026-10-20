package id.ac.polinema.lumajang.portalku.keamanan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table (name = "pengguna")
@Getter 
@Setter 
@NoArgsConstructor 

public class Pengguna {
    public enum Peran { ADMIN, EDITOR }

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column (nullable = false, unique = true, length = 150)
    private String email;

    @Column (nullable = false, length = 72)
    private String sandi;

    @Column (nullable = false, length = 100)
    private String nama;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false, length = 10)
    private Peran peran;

    @Column (nullable = false)
    private boolean aktif = true;
}
