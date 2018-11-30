package gaskuadmin.funcode.funcode.com.gogasadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    Button daftarDriver, transMasuk, btnMasya, btnTrans,btnStok, keluar, btnUbahPass, btnDTAdmin, btnPesanan;
    FirebaseAuth mAuthDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        daftarDriver = (Button) findViewById(R.id.daftar);
        transMasuk = (Button) findViewById(R.id.trans_masuk);
        btnMasya = (Button) findViewById(R.id.lapMasya);
        btnTrans = (Button) findViewById(R.id.lapTrans);
        btnStok = (Button) findViewById(R.id.stok);
        btnPesanan = (Button) findViewById(R.id.pesana);
        keluar = (Button) findViewById(R.id.keluar);
        btnUbahPass = (Button) findViewById(R.id.ubahPass);

        mAuthDriver = FirebaseAuth.getInstance();

        String idUsers = mAuthDriver.getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference admin = database.getReference("admin").child(idUsers);

        btnMasya.setVisibility(View.GONE);
        transMasuk.setVisibility(View.GONE);
        daftarDriver.setVisibility(View.GONE);
        btnStok.setVisibility(View.GONE);
        btnPesanan.setVisibility(View.GONE);
        admin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int Level = dataSnapshot.child("Level").getValue(int.class);
                if(Level == 1) {
                    transMasuk.setVisibility(View.VISIBLE);
                    daftarDriver.setVisibility(View.VISIBLE);
                    btnStok.setVisibility(View.VISIBLE);
                    btnPesanan.setVisibility(View.VISIBLE);
                    getSupportActionBar().setTitle("Go Gas Agen");

                }else if(Level == 2){
                    getSupportActionBar().setTitle("Go Gas Pertamina");
                    btnMasya.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(HomeActivity.this, PesananActivity.class);
                startActivity(p);
            }
        });
        daftarDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, DataDriverActivity.class);
                startActivity(i);
            }
        });


        transMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, TransaksiMasukActivity.class);
                startActivity(i);
            }
        });

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuthDriver.signOut();
                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });

        btnTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l = new Intent(HomeActivity.this, LaporanTransaksiActivity.class);
                startActivity(l);

            }
        });

        btnUbahPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(HomeActivity.this, UbahPasswordActivity.class);
                startActivity(p);
            }
        });
        btnStok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(HomeActivity.this, StokActivity.class);
                startActivity(s);
            }
        });


    }
}
