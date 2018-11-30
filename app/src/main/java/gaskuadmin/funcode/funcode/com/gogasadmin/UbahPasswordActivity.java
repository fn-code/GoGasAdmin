package gaskuadmin.funcode.funcode.com.gogasadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UbahPasswordActivity extends AppCompatActivity {

    FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);

        userAuth = FirebaseAuth.getInstance();
        final EditText pass = (EditText) findViewById(R.id.passBaru);
        Button btnPass = (Button) findViewById(R.id.btnPass);

        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser userInfo = userAuth.getCurrentUser();
                String newP = pass.getText().toString().trim();

                if(TextUtils.getTrimmedLength(newP) >= 7  ){

                    userInfo.updatePassword(newP)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(UbahPasswordActivity.this, "Password berhasil di ubah", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(UbahPasswordActivity.this, HomeActivity.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        Toast.makeText(UbahPasswordActivity.this, "Password gagal di ubah", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }else{
                    Toast.makeText(UbahPasswordActivity.this, "Password minimal 7 digit ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
