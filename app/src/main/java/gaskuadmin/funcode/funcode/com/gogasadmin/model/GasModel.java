package gaskuadmin.funcode.funcode.com.gogasadmin.model;

/**
 * Created by funcode on 1/23/18.
 */

public class GasModel {
    public Integer Harga;
    public Integer HargaIsi;
    public String Jenis;
    public Integer Stok;

    public GasModel(){

    }

    public GasModel(Integer harga, Integer hargaIsi, String jenis, Integer stok) {
        Harga = harga;
        HargaIsi = hargaIsi;
        Jenis = jenis;
        Stok = stok;
    }
}
