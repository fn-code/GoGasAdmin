package gaskuadmin.funcode.funcode.com.gogasadmin;

import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

import gaskuadmin.funcode.funcode.com.gogasadmin.model.DriverModel;
import gaskuadmin.funcode.funcode.com.gogasadmin.model.GasModel;

public class StokActivity extends AppCompatActivity {

    private LinearLayoutManager mLayoutManager;
    NumberFormat rupiahFormat;
    String idStok;
    FirebaseRecyclerAdapter<GasModel, StokActivity.DataStokViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok);

        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.dataStok);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(StokActivity.this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        Query mDatabaseReference = database.getReference("Jenis");


        adapter = new FirebaseRecyclerAdapter<GasModel, StokActivity.DataStokViewHolder>(
                GasModel.class,
                R.layout.activity_stok_list,
                StokActivity.DataStokViewHolder.class,
                mDatabaseReference
        ) {

            @Override
            protected void populateViewHolder(final StokActivity.DataStokViewHolder viewHolder, final GasModel model, final int position) {

                rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                String harga = rupiahFormat.format(Double.parseDouble(String.valueOf(model.Harga)));
                String hargaisi = rupiahFormat.format(Double.parseDouble(String.valueOf(model.HargaIsi)));

                String ResHarga = "Rp. " + harga ;
                String ResHargaIsi = "Rp. " + hargaisi ;

                viewHolder.jenis.setText(model.Jenis);
                viewHolder.harga.setText("Harga Jual : "+ ResHarga);
                viewHolder.hargaisi.setText("Harga Isi Ulang : "+ ResHargaIsi);
                viewHolder.stok.setText(String.valueOf(model.Stok +" Stok"));

            }
        };

        mRecyclerView.setAdapter(adapter);

    }

    public static class DataStokViewHolder extends RecyclerView.ViewHolder {

        public TextView jenis, harga,hargaisi,stok;
        View mView;


        public DataStokViewHolder(View v) {
            super(v);
            mView = v;
            jenis = (TextView) v.findViewById(R.id.nama);
            harga = (TextView) v.findViewById(R.id.harga);
            hargaisi = (TextView) v.findViewById(R.id.hargaisi);
            stok = (TextView) v.findViewById(R.id.stok);

        }

    }
}
