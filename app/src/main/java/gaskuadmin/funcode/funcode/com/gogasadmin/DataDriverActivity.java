package gaskuadmin.funcode.funcode.com.gogasadmin;

import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

import gaskuadmin.funcode.funcode.com.gogasadmin.model.DriverModel;

public class DataDriverActivity extends AppCompatActivity {

    private LinearLayoutManager mLayoutManager;
    NumberFormat rupiahFormat;
    String idTransaksi;
    FirebaseRecyclerAdapter<DriverModel, DataDriverActivity.DataDriverViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_driver);

        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.dataDriver);
        FloatingActionButton daftar = (FloatingActionButton) findViewById(R.id.adddriver);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DataDriverActivity.this, DaftarDriverActivity.class);
                startActivity(i);
            }
        });

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(DataDriverActivity.this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        Query mDatabaseReference = database.getReference("driver");


        adapter = new FirebaseRecyclerAdapter<DriverModel, DataDriverViewHolder>(
                DriverModel.class,
                R.layout.activity_data_driver_list,
                DataDriverActivity.DataDriverViewHolder.class,
                mDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(final DataDriverActivity.DataDriverViewHolder viewHolder, final DriverModel model, final int position) {

                rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                String transaksi = rupiahFormat.format(Double.parseDouble(String.valueOf(model.Transaksi)));

                String ResTransaksi = "Rp. " + transaksi ;

                viewHolder.trans.setText("Transaksi : "+ResTransaksi);
                viewHolder.alamat.setText(model.Alamat);
                viewHolder.nama.setText(model.Nama);
                viewHolder.notelp.setText("No Telp : "+model.NoTelp);
                viewHolder.platno.setText("Plat No : "+model.PlatNo);
                viewHolder.ktp.setText("No KTP : "+model.NoKTP);
                viewHolder.jmltrans.setText(String.valueOf(model.jmlTransaksi+" Kali"));

                Picasso.with(DataDriverActivity.this).load(model.Photo)
                        .placeholder(R.drawable.gas)
                        .error(R.drawable.gas)
                        .resize(200,200).centerCrop().into(viewHolder.photo);


            }
        };

        mRecyclerView.setAdapter(adapter);
    }

    public static class DataDriverViewHolder extends RecyclerView.ViewHolder {

        public TextView alamat, nama, notelp, ktp, platno, jmltrans,trans;
        ImageView photo;
        View mView;


        public DataDriverViewHolder(View v) {
            super(v);
            mView = v;
            alamat = (TextView) v.findViewById(R.id.alamat);
            nama = (TextView) v.findViewById(R.id.nama);
            notelp = (TextView) v.findViewById(R.id.telp);
            ktp = (TextView) v.findViewById(R.id.ktp);
            platno = (TextView) v.findViewById(R.id.platno);
            jmltrans = (TextView) v.findViewById(R.id.jmlTransaksi);
            trans = (TextView) v.findViewById(R.id.transaksi);
            photo = (ImageView) v.findViewById(R.id.img_profile);

        }

    }
}
