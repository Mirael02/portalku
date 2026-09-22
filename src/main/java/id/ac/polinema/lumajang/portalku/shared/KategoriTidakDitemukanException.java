package id.ac.polinema.lumajang.portalku.shared;

public class KategoriTidakDitemukanException extends SumberDayaTidakDitemukanException {
    public KategoriTidakDitemukanException(Integer id) {
        super("Kategori", id);
    }
}