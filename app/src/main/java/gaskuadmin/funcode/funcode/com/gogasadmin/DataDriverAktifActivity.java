package gaskuadmin.funcode.funcode.com.gogasadmin;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Driver;
import java.text.NumberFormat;
import java.util.Locale;

import gaskuadmin.funcode.funcode.com.gogasadmin.model.DriverModel;
import gaskuadmin.funcode.funcode.com.gogasadmin.model.TransaksiModel;

public class DataDriverAktifActivity extends AppCompatActivity {

    private LinearLayoutManager mLayoutManager;
    NumberFormat rupiahFormat;
    String idTransaksi;
    FirebaseRecyclerAdapter<DriverModel, DataDriverAktifActivity.DriverActiveViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_driver_aktif);

        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.daftarDriverAktif);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(DataDriverAktifActivity.this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        idTransaksi = getIntent().getExtras().getString("idTransaksi");
        Toast.makeText(this, idTransaksi, Toast.LENGTH_SHORT).show();

        Query mDatabaseReference = database.getReference("driver").orderByChild("Status").equalTo(1);


        adapter = new FirebaseRecyclerAdapter<DriverModel, DataDriverAktifActivity.DriverActiveViewHolder>(
                DriverModel.class,
                R.layout.activity_data_driver_aktif_list,
                DataDriverAktifActivity.DriverActiveViewHolder.class,
                mDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(final DataDriverAktifActivity.DriverActiveViewHolder viewHolder, final DriverModel model, final int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.alamat.setText(model.Alamat);
                viewHolder.nama.setText(model.Nama);
                viewHolder.notelp.setText(model.NoTelp);
                final String token = model.Token;

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(final View view) {

                        send(token);
                        DatabaseReference addId = database.getReference("transaksi").child(idTransaksi);
                        DatabaseReference upDataDriver = database.getReference("driver").child(post_key);
                        addId.child("IDDriver").setValue(post_key);
                        addId.child("Status").setValue(3);
                        upDataDriver.child("Status").setValue(2);
                        Intent i = new Intent(DataDriverAktifActivity.this, TransaksiMasukActivity.class);
                        startActivity(i);
                    }

                });

            }
        };

        mRecyclerView.setAdapter(adapter);

    }


    public static class DriverActiveViewHolder extends RecyclerView.ViewHolder {

        public TextView alamat, nama, notelp;
        View mView;


        public DriverActiveViewHolder(View v) {
            super(v);
            mView = v;
            alamat = (TextView) v.findViewById(R.id.alamat);
            nama = (TextView) v.findViewById(R.id.nama);
            notelp = (TextView) v.findViewById(R.id.telp);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void send(String token){
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        try {
            HttpURLConnection httpcon = (HttpURLConnection) ((new URL("https://fcm.googleapis.com/fcm/send").openConnection()));
            httpcon.setDoOutput(true);
            httpcon.setRequestProperty("Content-Type", "application/json");
            httpcon.setRequestProperty("Authorization", "key=AAAAR0vgimU:APA91bEHyPFrliBghcPI3r6P67lDBjLtCFQ3qIP7sQXGqc5HPAlSbrN2XwV76FJ-HIy0cU_Ntc7CRDthp8MBzwwk1l5nVWSJYVBmaGqEKrv1y0kfMeMWDDHuIJ5FFZyS4CjRZCUi8wcR");
            httpcon.setRequestMethod("POST");
            httpcon.connect();

            JSONObject json = new JSONObject();
            json.put("to",token);
            json.put("priority", "high");
            JSONObject info = new JSONObject();
            info.put("title", "Pesanan Baru");   // Notification title
            info.put("body", "Pesana Masuk Dari Admin");
            info.put("sound", notificationSound);
            info.put("click_action", "com.funcode.funcode.gaskudriver_TARGET_PESANAN_DRIVER");// Notification body
            json.put("notification", info);

            OutputStreamWriter os = new OutputStreamWriter(httpcon.getOutputStream());
            os.write(json.toString());
            os.flush();
            os.close();

            // Reading response
            java.io.InputStream input = httpcon.getInputStream();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                for (String line; (line = reader.readLine()) != null;) {
                    System.out.println(line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
