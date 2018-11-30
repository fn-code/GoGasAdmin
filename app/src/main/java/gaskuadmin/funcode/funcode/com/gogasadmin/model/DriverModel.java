package gaskuadmin.funcode.funcode.com.gogasadmin.model;

import com.google.android.gms.drive.Drive;

/**
 * Created by funcode on 12/1/17.
 */

public class DriverModel {
    public String Nama;
    public String Alamat;
    public String NoTelp;
    public String Token;
    public String NoKTP;
    public String PlatNo;
    public Double Transaksi;
    public int jmlTransaksi;
    public String Photo;

    public DriverModel(){}

    public DriverModel(String nama, String alamat, String noTelp, String token, String noKTP, String platNo, Double transaksi, int jmlTransaksi, String photo) {
        Nama = nama;
        Alamat = alamat;
        NoTelp = noTelp;
        Token = token;
        NoKTP = noKTP;
        PlatNo = platNo;
        Transaksi = transaksi;
        this.jmlTransaksi = jmlTransaksi;
        Photo = photo;
    }
}
