package gaskuadmin.funcode.funcode.com.gogasadmin.model;

/**
 * Created by funcode on 12/1/17.
 */

public class TransaksiModel {
    public String Lokasi;
    public int TarifAntar;
    public int TotalBayar;
    public int Status;
    public String Waktu;

    public TransaksiModel(){}

    public TransaksiModel(String lokasi, int tarifAntar, int totalBayar, int status, String waktu) {
        Lokasi = lokasi;
        TarifAntar = tarifAntar;
        TotalBayar = totalBayar;
        Status = status;
        Waktu = waktu;
    }
}
