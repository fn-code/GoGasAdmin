package gaskuadmin.funcode.funcode.com.gogasadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.NumberFormat;
import java.util.Locale;

import gaskuadmin.funcode.funcode.com.gogasadmin.model.TransaksiModel;

public class LaporanTransaksiActivity extends AppCompatActivity {

    private LinearLayoutManager mLayoutManager;

    NumberFormat rupiahFormat;
    FirebaseAuth mAuthDriver;
    FirebaseRecyclerAdapter adapter;
    Query TransaksiPesanan;
    FirebaseDatabase database;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_transaksi);

        mRecyclerView = (RecyclerView) findViewById(R.id.lapTransaksi);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(LaporanTransaksiActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        database = FirebaseDatabase.getInstance();
        mAuthDriver = FirebaseAuth.getInstance();

        final String id_driver = mAuthDriver.getCurrentUser().getUid();

        TransaksiPesanan = database.getReference("transaksi");

        adapter = new FirebaseRecyclerAdapter<TransaksiModel, LaporanTransaksiActivity.LaporanViewHolder>(
                TransaksiModel.class,
                R.layout.activity_laporan_transaksi_list,
                LaporanTransaksiActivity.LaporanViewHolder.class,
                TransaksiPesanan
        ) {
            @Override
            protected void populateViewHolder(final LaporanTransaksiActivity.LaporanViewHolder viewHolder, final TransaksiModel model, final int position) {
                if(model.Status == 4 || model.Status == 5 || model.Status == 6){
                    rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                    String tarif = rupiahFormat.format(Double.parseDouble(String.valueOf(model.TarifAntar)));
                    String total = rupiahFormat.format(Double.parseDouble(String.valueOf(model.TotalBayar)));

                    String ResTarif = "Rp. " + tarif ;
                    String ResTotal = "Rp. " + total ;
                    if(model.Status == 4)
                        viewHolder.status.setText("Selesai");
                    else if(model.Status == 5)
                        viewHolder.status.setText("Dibatalkan Kurir");
                    else if(model.Status == 6)
                        viewHolder.status.setText("Dibatalkan Admin");

                    viewHolder.tarif.setText(ResTarif);
                    viewHolder.total.setText(ResTotal);
                    viewHolder.alamat.setText(model.Lokasi);
                    viewHolder.waktu.setText(model.Waktu);

                }
                else{
                    viewHolder.top.setVisibility(View.GONE);
                    viewHolder.total.setVisibility(View.GONE);
                    viewHolder.tarif.setVisibility(View.GONE);
                    viewHolder.alamat.setVisibility(View.GONE);
                    viewHolder.status.setVisibility(View.GONE);
                    viewHolder.waktu.setVisibility(View.GONE);
                    viewHolder.mView.setVisibility(View.GONE);
                    viewHolder.card.setVisibility(View.GONE);
                    viewHolder.second.setVisibility(View.GONE);
                    viewHolder.setIsRecyclable(false);
                }



            }

        };

        mRecyclerView.setAdapter(adapter);
    }

    //ViewHolder for our Firebase UI
    public static class LaporanViewHolder extends RecyclerView.ViewHolder {

        public TextView tarif,waktu,status,total,alamat;
        View mView;
        LinearLayout top,second;
        CardView card;


        public LaporanViewHolder(View v) {
            super(v);
            mView = v;
            top = (LinearLayout) v.findViewById(R.id.topmen);
            second = (LinearLayout) v.findViewById(R.id.detail);
            card = (CardView) v.findViewById(R.id.card);
            tarif = (TextView) v.findViewById(R.id.tarifAntar);
            alamat = (TextView) v.findViewById(R.id.alamat);
            total = (TextView) v.findViewById(R.id.totbayar);
            waktu = (TextView) v.findViewById(R.id.waktu);
            status = (TextView) v.findViewById(R.id.status);

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.cleanup();
    }
}
