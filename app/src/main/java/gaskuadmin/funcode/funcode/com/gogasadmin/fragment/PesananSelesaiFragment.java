package gaskuadmin.funcode.funcode.com.gogasadmin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import gaskuadmin.funcode.funcode.com.gogasadmin.R;
import gaskuadmin.funcode.funcode.com.gogasadmin.model.PesananModel;

/**
 * Created by funcode on 1/25/18.
 */

public class PesananSelesaiFragment extends Fragment {
    private LinearLayoutManager mLayoutManager;
    FirebaseRecyclerAdapter<PesananModel, DataPesananSelesaiViewHolder> adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pesananselesai;
        pesananselesai = inflater.inflate(R.layout.fragment_pesanan_selesai, container, false);

        final RecyclerView mRecyclerView = (RecyclerView) pesananselesai.findViewById(R.id.dataPesananSelesai);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        DatabaseReference mDatabaseReference = database.getReference("pesanan");

        adapter = new FirebaseRecyclerAdapter<PesananModel, DataPesananSelesaiViewHolder>(
                PesananModel.class,
                R.layout.fragment_pesanan_selesai_list,
                DataPesananSelesaiViewHolder.class,
                mDatabaseReference
        ) {

            @Override
            protected void populateViewHolder(final DataPesananSelesaiViewHolder viewHolder, final PesananModel model, final int position) {
                if(model.Status == 3 || model.Status == 4){
                    viewHolder.jenis.setText(model.Jenis);
                    if(model.Status == 3){
                        viewHolder.sts.setText("Selesai");
                    } else if(model.Status == 4){
                        viewHolder.sts.setText("Dibatalkan");
                    }
                    viewHolder.waktu.setText("Waktu Pesanan : "+model.Waktu);
                    viewHolder.jml.setText(String.valueOf("Jumlah Pesan : "+model.JumlahPesanan));
                }else {
                    viewHolder.lay.setVisibility(View.GONE);
                }

            }
        };

        mRecyclerView.setAdapter(adapter);
        return pesananselesai;
    }

    public static class DataPesananSelesaiViewHolder extends RecyclerView.ViewHolder {

        public TextView jenis, jml,waktu,sts;
        View mView;
        LinearLayout lay;


        public DataPesananSelesaiViewHolder(View v) {
            super(v);
            mView = v;
            lay = (LinearLayout) v.findViewById(R.id.selesai);
            jenis = (TextView) v.findViewById(R.id.nama);
            jml = (TextView) v.findViewById(R.id.jml_pesanan);
            waktu = (TextView) v.findViewById(R.id.waktu);
            sts = (TextView) v.findViewById(R.id.sts);

        }

    }
}
