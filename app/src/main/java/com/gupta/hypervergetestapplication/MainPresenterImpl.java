package com.gupta.hypervergetestapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;

import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import java.io.IOException;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.gupta.hypervergetestapplication.Constant.REQUEST_CODE;
import static com.gupta.hypervergetestapplication.Constant.REQUEST_CODE_ASK_PERMISSIONS;

public class MainPresenterImpl implements MainPresenter {

    private MainActivity mainPresenter;

    MainPresenterImpl(MainActivity mainPresenter){
        this.mainPresenter = mainPresenter;
    }

    @Override
    public void scanImage(MainActivity context) {

        if (ActivityCompat.checkSelfPermission(context, CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ){
            captureImage(context);
        }else {
            mainPresenter.requestPermissions(context,new String[]{CAMERA,READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
        }

    }

    private void captureImage(MainActivity context) {
        int preference = ScanConstants.OPEN_CAMERA;
        Intent intent = new Intent(context, ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        context.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void getImageResponse(Context context,Intent data) {
        Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            context.getContentResolver().delete(uri, null, null);
            mainPresenter.setImage(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
