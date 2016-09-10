package com.tumpi.tepesatu.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tumpi.tepesatu.R;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private CircleImageView gambar;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GALERRY = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gambar =(CircleImageView)findViewById(R.id.gambar);
    }

    private boolean isSupportCamera(){
        return getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) ? true : false;
    }

    public void takePicture(View view){
        showDialogChooice();
    }

    private void fromCamera(){
        if(isSupportCamera()){
            Intent take = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(take.resolveActivity(getPackageManager()) != null){
                startActivityForResult(take,REQUEST_IMAGE_CAPTURE);
            }
        }
        else{
            Toast.makeText(this,"camera not support",Toast.LENGTH_SHORT).show();
        }
    }

    private void fromGalerry(){
        if(isSupportCamera()){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(intent,REQUEST_IMAGE_GALERRY);
            }

        }
        else{
            Toast.makeText(this,"camera not support",Toast.LENGTH_SHORT).show();
        }
    }


    private void showDialogChooice(){
        CharSequence list[] = new CharSequence[]{getString(R.string.take_picture),getString(R.string.from_galerry)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        fromCamera();
                        break;
                    case 1:
                        fromGalerry();
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extra = data.getExtras();
            Bitmap btm = (Bitmap)extra.get("data");
            gambar.setImageBitmap(btm);
        }

        if(requestCode == REQUEST_IMAGE_GALERRY && resultCode == RESULT_OK){
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                gambar.setImageBitmap(bitmap);
              ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
