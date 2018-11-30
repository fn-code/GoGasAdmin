package gaskuadmin.funcode.funcode.com.gogasadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.NumberFormat;
import java.util.Locale;

import gaskuadmin.funcode.funcode.com.gogasadmin.model.TransaksiModel;

public class TransaksiMasukActivity extends AppCompatActivity {

    private LinearLayoutManager mLayoutManager;
    NumberFormat rupiahFormat;
    DatabaseReference trans;
    FirebaseRecyclerAdapter<TransaksiModel, TransaksiMasukViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_masuk);

        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.daftarTransaksiMasuk);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(TransaksiMasukActivity.this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        trans = database.getReference("transaksi");


        Query mDatabaseReference = database.getReference("transaksi").orderByChild("Status").equalTo(2);


        adapter = new FirebaseRecyclerAdapter<TransaksiModel, TransaksiMasukViewHolder>(
                TransaksiModel.class,
                R.layout.activity_transaksi_masuk_list,
                TransaksiMasukViewHolder.class,
                mDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(final TransaksiMasukViewHolder viewHolder, TransaksiModel model, final int position) {
                rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);


                String harga, tarif, total;
                try {
                    NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                    tarif = rupiahFormat.format(Double.parseDouble(String.valueOf(model.TarifAntar)));
                    total = rupiahFormat.format(Double.parseDouble(String.valueOf(model.TotalBayar)));
                } catch (NumberFormatException e) {
                    tarif = String.valueOf(0);
                    total = String.valueOf(0);
                }

                String ResTotal = "Rp. " + total;
                String ResTarif = "Rp. " + tarif;

                final String post_key = getRef(position).getKey();


                viewHolder.tarif.setText(ResTarif);
                viewHolder.total.setText(ResTotal);

                viewHolder.alamat.setText(model.Lokasi);
                viewHolder.lihat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(TransaksiMasukActivity.this, DataDriverAktifActivity.class);
                        i.putExtra("idTransaksi", post_key);
                        startActivity(i);
                    }
                });
                viewHolder.tolak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        trans.child(post_key).child("Status").setValue(6);
                    }
                });




            }
        };

        mRecyclerView.setAdapter(adapter);


    }

    public static class TransaksiMasukViewHolder extends RecyclerView.ViewHolder {

        public TextView alamat, tarif,total;
        RelativeLayout lihat,tolak;
        View mView;


        public TransaksiMasukViewHolder(View v) {
            super(v);
            mView = v;
            lihat = (RelativeLayout) v.findViewById(R.id.lihat_driver);
            tolak = (RelativeLayout) v.findViewById(R.id.tolak);
            tarif = (TextView) v.findViewById(R.id.tarifAntar);
            total = (TextView) v.findViewById(R.id.totbayar);
            alamat = (TextView) v.findViewById(R.id.alamat);

        }

    }


}
