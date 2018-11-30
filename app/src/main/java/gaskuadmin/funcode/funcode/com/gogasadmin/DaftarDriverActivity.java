package gaskuadmin.funcode.funcode.com.gogasadmin;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DaftarDriverActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mNama;
    private EditText mTelp;
    private EditText mPlatNo;
    private EditText mKTP;
    private EditText mAlamat;
    private Button daftar, lihat;
    private Uri mImageUri = null;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_driver);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mNama = (EditText) findViewById(R.id.nama);
        mTelp = (EditText) findViewById(R.id.telp);
        mPlatNo = (EditText) findViewById(R.id.platno);
        mKTP = (EditText) findViewById(R.id.noktp);
        mAlamat = (EditText) findViewById(R.id.alamat);
        daftar = (Button) findViewById(R.id.daftar);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("driver");

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();

            }
        });
    }


    private void startRegister() {
        final String nama = mNama.getText().toString().trim();
        final String plat = mPlatNo.getText().toString().trim();
        final String ktp = mKTP.getText().toString().trim();
        final String alamat = mAlamat.getText().toString().trim();
        final String telp = String.valueOf(mTelp.getText());
        String email = mEmailView.getText().toString().trim();
        String pass = mPasswordView.getText().toString().trim();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {

            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        String id_driver = mAuth.getCurrentUser().getUid();
                        DatabaseReference driver_db = mDatabase.child(id_driver);
                        driver_db.child("Latitude").setValue(0);
                        driver_db.child("Longitude").setValue(0);
                        driver_db.child("Nama").setValue(nama);
                        driver_db.child("Photo").setValue("0");
                        driver_db.child("Transaksi").setValue(0);
                        driver_db.child("NoTelp").setValue(telp);
                        driver_db.child("PlatNo").setValue(plat);
                        driver_db.child("NoKTP").setValue(ktp);
                        driver_db.child("Status").setValue(1);
                        driver_db.child("Alamat").setValue(alamat);
                        driver_db.child("IDDriver").setValue(id_driver);
                        driver_db.child("jmlTransaksi").setValue(0);

                        Intent LoginInten = new Intent(DaftarDriverActivity.this, UploadFotoDriverActivity.class);
                        LoginInten.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(LoginInten);


                    } else {
                        Toast.makeText(DaftarDriverActivity.this, " Email Sudah Terdaftar ", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }
}
