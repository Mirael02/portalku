package id.ac.polinema.lumajang.portalku.shared;

public class SumberDayaTidakDitemukanException extends RuntimeException {
    public SumberDayaTidakDitemukanException(String jenis, Object id) {
        super(jenis + " dengan id " + id + " tidak ditemukan");
    }
}