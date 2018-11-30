package gaskuadmin.funcode.funcode.com.gogasadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPesananActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    String jenis;
    String idgas;
    FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pesanan);

        RadioGroup rg = (RadioGroup) findViewById(R.id.jenis);
        Button btn = (Button) findViewById(R.id.pesan);
        final EditText jmlPesanan = (EditText) findViewById(R.id.jml_pesanan);
        db = FirebaseDatabase.getInstance();

        rg.setOnCheckedChangeListener(this);
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        final String format = s.format(new Date());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jmlPesanan.getText().toString().equals("")){
                    Toast.makeText(AddPesananActivity.this, "Masukan Jumlah Pesanan Anda", Toast.LENGTH_SHORT).show();
                }else {
                    int jml = Integer.parseInt(jmlPesanan.getText().toString());
                    DatabaseReference pesan = db.getReference("pesanan").push();
                    String key = pesan.getRef().getKey();
                    pesan.child("Status").setValue(1);
                    pesan.child("IDPesanan").setValue(key);
                    pesan.child("IDGas").setValue(idgas);
                    pesan.child("Jenis").setValue(jenis);
                    pesan.child("Waktu").setValue(format);
                    pesan.child("JumlahPesanan").setValue(jml);
                    Intent i = new Intent(AddPesananActivity.this, PesananActivity.class);
                    startActivity(i);
                }

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.gas5kg){
            jenis = "5.5Kg";
            idgas = "1";
        }else if (i == R.id.gas12kg) {
            jenis = "12Kg";
            idgas = "2";
        }
    }
}
