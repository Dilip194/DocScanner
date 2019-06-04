package com.gupta.hypervergetestapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.gupta.hypervergetestapplication.Constant.REQUEST_CODE;
import static com.gupta.hypervergetestapplication.Constant.REQUEST_CODE_ASK_PERMISSIONS;

public class MainActivity extends AppCompatActivity implements MainViewPresenter{


    ImageView scannedImageView;
    MainPresenterImpl mainPresenter = new MainPresenterImpl(this) ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scannedImageView = findViewById(R.id.image);
    }

    public void scanDoc(View view) {
        scanImage();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mainPresenter.getImageResponse(getApplicationContext(),data);
        }
    }

    @Override
    public void scanImage() {
        mainPresenter.scanImage(this);
    }

    @Override
    public void setImage(Bitmap bitmap) {
        scannedImageView.setImageBitmap(bitmap);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode){
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                ){
                    mainPresenter.scanImage(this);
                }else {
                    requestPermissions(this,new String[]{CAMERA,READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    public void requestPermissions(Activity context, String[] permission, Integer requestCode) {
        ActivityCompat.requestPermissions(context, new String[]{permission[0],permission[1],permission[2]}, requestCode);    }

}
