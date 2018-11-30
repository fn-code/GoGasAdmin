package gaskuadmin.funcode.funcode.com.gogasadmin.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import gaskuadmin.funcode.funcode.com.gogasadmin.R;
import gaskuadmin.funcode.funcode.com.gogasadmin.model.PesananModel;

/**
 * Created by funcode on 1/25/18.
 */

public class PesananFragment extends Fragment {
    private LinearLayoutManager mLayoutManager;
    FirebaseRecyclerAdapter<PesananModel, DataPesananViewHolder> adapter;
    int stok;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pesanan;
        pesanan = inflater.inflate(R.layout.fragment_pesanan, container, false);

        final RecyclerView mRecyclerView = (RecyclerView) pesanan.findViewById(R.id.dataPesanan);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference gas = database.getReference("Jenis");


        final DatabaseReference mDatabaseReference = database.getReference("pesanan");

        adapter = new FirebaseRecyclerAdapter<PesananModel, DataPesananViewHolder>(
                PesananModel.class,
                R.layout.fragment_pesanan_list,
                DataPesananViewHolder.class,
                mDatabaseReference
        ) {

            @Override
            protected void populateViewHolder(final DataPesananViewHolder viewHolder, final PesananModel model, final int position) {

                if(model.Status == 1 || model.Status == 2) {

                    viewHolder.jenis.setText(model.Jenis);
                    if(model.Status == 1) {
                        viewHolder.sts.setText("Terkirim");
                    }
                    else if(model.Status == 2) {
                        viewHolder.sts.setText("Dikirim");
                    }
                    final String Key = getRef(position).getKey();
                    viewHolder.waktu.setText("Waktu Pesanan : "+model.Waktu);
                    viewHolder.jml.setText(String.valueOf("Jumlah Pesan : "+model.JumlahPesanan));

                    gas.child(model.IDGas).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int s = dataSnapshot.child("Stok").getValue(Integer.class);
                            stok = s;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    if(model.Status == 1) {
                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getActivity(), "Pesanan Baru Anda Kirimkan", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else if(model.Status == 2) {
                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                                alertDialog.setTitle("Perhatian.");
                                alertDialog.setMessage("Apakah Pesanan Gas Anda Telah Sampai ?");
                                alertDialog.setCancelable(false);

                                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, int which) {
                                        Integer tot = stok + model.JumlahPesanan;
                                        gas.child(model.IDGas).child("Stok").setValue(tot);
                                        mDatabaseReference.child(Key).child("Status").setValue(3);
                                        Toast.makeText(getActivity(), "Pesanan Selesai", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                            }
                        });
                    }
                }else {
                    viewHolder.lay.setVisibility(View.GONE);
                }
            }
        };

        mRecyclerView.setAdapter(adapter);
        return pesanan;


    }


    public static class DataPesananViewHolder extends RecyclerView.ViewHolder {

        public TextView jenis, jml,waktu,sts;
        View mView;
        LinearLayout lay;


        public DataPesananViewHolder(View v) {
            super(v);
            mView = v;
            lay = (LinearLayout) v.findViewById(R.id.baru);
            jenis = (TextView) v.findViewById(R.id.nama);
            jml = (TextView) v.findViewById(R.id.jml_pesanan);
            waktu = (TextView) v.findViewById(R.id.waktu);
            sts = (TextView) v.findViewById(R.id.sts);

        }

    }

}
