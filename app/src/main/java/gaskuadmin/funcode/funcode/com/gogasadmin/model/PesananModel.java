package gaskuadmin.funcode.funcode.com.gogasadmin.model;

/**
 * Created by funcode on 1/25/18.
 */

public class PesananModel {
    public Integer JumlahPesanan;
    public String Jenis;
    public String Waktu;
    public String IDGas;
    public Integer Status;

    public PesananModel(){}

    public PesananModel(Integer jumlahPesanan, String jenis, String waktu, Integer status) {
        JumlahPesanan = jumlahPesanan;
        Jenis = jenis;
        Waktu = waktu;
        Status = status;
    }
}
