-- Membentuk tabel kategori sebagai master data
CREATE TABLE kategori (
    id SERIAL PRIMARY KEY,
    nama VARCHAR(50) NOT NULL UNIQUE
);

-- Membentuk tabel utama pengumuman
-- Pastikan tipe dan ukuran data (seperti VARCHAR) cocok dengan di @Column
CREATE TABLE pengumuman (
    id SERIAL PRIMARY KEY,
    judul VARCHAR(150) NOT NULL UNIQUE,
    isi TEXT NOT NULL,
    tanggal_terbit DATE NOT NULL,
    jumlah_dilihat INTEGER NOT NULL,
    kategori_id INTEGER NOT NULL,
    CONSTRAINT fk_pengumuman_kategori FOREIGN KEY (kategori_id) REFERENCES kategori(id)
);

-- Membentuk tabel lampiran (sisi BANYAK dari relasi One-to-Many)
CREATE TABLE lampiran (
    id SERIAL PRIMARY KEY,
    nama_berkas VARCHAR(255) NOT NULL,
    ukuran INTEGER NOT NULL,
    pengumuman_id INTEGER NOT NULL,
    CONSTRAINT fk_lampiran_pengumuman FOREIGN KEY (pengumuman_id) REFERENCES pengumuman(id)
);

-- Membentuk tabel penghubung (join table) Many-to-Many antara pengumuman dan prodi
CREATE TABLE pengumuman_prodi (
    pengumuman_id INTEGER NOT NULL,
    prodi_id INTEGER NOT NULL,
    PRIMARY KEY (pengumuman_id, prodi_id),
    CONSTRAINT fk_pp_pengumuman FOREIGN KEY (pengumuman_id) REFERENCES pengumuman(id),
    CONSTRAINT fk_pp_prodi FOREIGN KEY (prodi_id) REFERENCES program_studi(id)
);