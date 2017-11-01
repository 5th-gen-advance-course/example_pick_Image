package com.example.ratha.selectordemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.ratha.selectordemo.entity.UrlModel;

import java.io.File;
import java.io.IOException;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterUrlActivity extends AppCompatActivity {

    @BindView(R.id.tvUrlTitle)
    EditText tvTitle;
    @BindView(R.id.rdWebsite)
    RadioButton rdWebsite;
    @BindView(R.id.rdFb)
    RadioButton rdFb;
    @BindView(R.id.tvUrl)
    EditText tvUrl;
    @BindView(R.id.tvEmail)
    EditText tvEmail;
    @BindView(R.id.imageView)
    ImageView ivImage;
    @BindView(R.id.tvDesc)
    EditText tvDesc;

    private UrlModel url;
    private static final int PICK_IMAGE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_url);
        ButterKnife.bind(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //takePictureButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, PICK_IMAGE);
        }

        //getData
    }

    @OnClick(R.id.btnPickImage)
    public void pickimage(View view){

        Intent intent=new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PICK_IMAGE){
            if(resultCode==RegisterUrlActivity.RESULT_OK){
                if(null!=data){
                    Uri uri=data.getData();
                    try{
                        String[] filePathColumn={MediaStore.Images.Media.DATA};
                        Cursor cursor =getContentResolver().query(
                                uri,filePathColumn,null,null,null
                        );
                        cursor.moveToFirst();
                        int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
                        String filepath=cursor.getString(columnIndex);
                        cursor.close();
                        Bitmap bitmap= BitmapFactory.decodeFile(filepath);
                        ivImage.setImageBitmap(bitmap);

                        /*Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);*/
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    return;
                }
            }

        }
    }



}
