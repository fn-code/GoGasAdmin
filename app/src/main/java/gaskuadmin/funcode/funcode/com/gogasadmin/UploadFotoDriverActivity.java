package gaskuadmin.funcode.funcode.com.gogasadmin;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class UploadFotoDriverActivity extends AppCompatActivity {

    private ImageButton imgView;
    private Button bProfil;
    private static final int GALLERY_REQUEST = 1;
    private StorageReference mStorage;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    Uri mImageUri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_foto_driver);

        imgView = (ImageButton) findViewById(R.id.img);
        bProfil = (Button) findViewById(R.id.profil);

        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference().child("foto_driver");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("driver");

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, GALLERY_REQUEST);
            }
        });

        bProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadProfil();
            }
        });

    }

    private void uploadProfil() {

        if(mImageUri != null) {

            StorageReference filepath = mStorage.child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String downloadURL = taskSnapshot.getDownloadUrl().toString();
                    String id_driver = mAuth.getCurrentUser().getUid();

                    mDatabase.child(id_driver).child("Photo").setValue(downloadURL);
                    Toast.makeText(UploadFotoDriverActivity.this, "Upload Foto Profil Selesai", Toast.LENGTH_SHORT).show();

                    mAuth.signOut();
                    Intent i = new Intent(UploadFotoDriverActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK ){
            Uri imageUri = data.getData();


            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri = result.getUri();
                imgView.setImageURI(mImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public void onBackPressed() {
        mAuth.signOut();
        Intent i = new Intent(UploadFotoDriverActivity.this, DaftarDriverActivity.class);
        startActivity(i);
        finish();
        return;
    }

}
