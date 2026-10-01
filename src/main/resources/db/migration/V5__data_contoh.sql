-- 1. Isi data kategori dasar
INSERT INTO kategori (id, nama) 
VALUES (1, 'Akademik'), (2, 'Kemahasiswaan'), (3, 'Umum') 
ON CONFLICT (id) DO NOTHING;

-- 2. Generate 120 data pengumuman otomatis
INSERT INTO pengumuman (judul, isi, tanggal_terbit, jumlah_dilihat, kategori_id)
SELECT 
    'Pengumuman nomor ' || g || ' tentang kegiatan akademik',
    repeat('Isi pengumuman contoh nomor ' || g || '. ', 40),
    DATE '2026-01-01' + ((g * 3) % 240),
    (g * 7) % 500,
    ((g % 3) + 1)
FROM generate_series(1, 120) AS g;