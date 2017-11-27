package com.bhawyyamittal.foodie;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.Manifest;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddFood extends AppCompatActivity {
    private ImageButton foodImage;
    private static final int GALLREQ = 1;
    private EditText name;
    private EditText desc;
    private EditText price;
    private Uri uri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        name = findViewById(R.id.itemName);
        desc = findViewById(R.id.descName);
        price = findViewById(R.id.foodPrice);
        foodImage = findViewById(R.id.imageButton);
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("Items");

    }


    public void imageButtonClicked(View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);


            }else{
                Toast.makeText(this,"Permission is already granted!",Toast.LENGTH_LONG).show();
               // configureButton();
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLREQ);
            }
        } else {
            //configureButton();
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,GALLREQ);
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLREQ && resultCode == RESULT_OK){
            uri = data.getData();
            foodImage.setImageURI(uri);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 10:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.e("AccessExternalStorage","Permission granted");
                    //permissionGranted = true;

                    //   Toast.makeText(this,"Grant permission being called and permissionGranted = " + permissionGranted,Toast.LENGTH_LONG).show();
                   // configureButton();


                }else{
                    Toast.makeText(this,"Permission is not given",Toast.LENGTH_LONG).show();
                    // permissionGranted = false;
                    Log.e("AccessExternalStorage","Permission not granted at first call");

                }
                break;


        }
    }

    public void addItemClicked(View view) {
        final String name_text = name.getText().toString().trim();
        final String desc_text = desc.getText().toString().trim();
        final String price_text = price.getText().toString().trim();
        Log.e("task", "Iood Add Item clicked for " + name_text);
        if((!TextUtils.isEmpty(name_text)) && (!TextUtils.isEmpty(desc_text)) && (!TextUtils.isEmpty(price_text))) {

            StorageReference filePath = storageReference.child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Log.e("task", "Iood The download URL is " + downloadUrl.toString());
                    Toast.makeText(AddFood.this,"Image Uploaded",Toast.LENGTH_LONG).show();
                    final DatabaseReference newPost = databaseReference.push();
                    newPost.child("name").setValue(name_text);
                    newPost.child("description").setValue(desc_text);
                    newPost.child("price").setValue(price_text);
                    newPost.child("image").setValue(downloadUrl.toString());

                }
            });

        }


    }
}
