package id.ac.polinema.lumajang.portalku.shared;

public class PengumumanTidakDitemukanException extends SumberDayaTidakDitemukanException {
    public PengumumanTidakDitemukanException(Integer id) {
        super("Pengumuman", id);
    }
}