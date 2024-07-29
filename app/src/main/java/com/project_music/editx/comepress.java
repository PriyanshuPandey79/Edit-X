package com.project_music.editx;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import id.zelory.compressor.Compressor;

public class comepress extends AppCompatActivity {
    ActivityResultLauncher<Intent> activityResultLauncher=
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                               int resultCode= activityResult.getResultCode();
                               Intent data=activityResult.getData();
                            if(resultCode==RESULT_OK){
                                btnCompress.setVisibility(View.VISIBLE);
                                final Uri imageUri=data.getData();
                                try {
                                    final InputStream imageStream=getContentResolver().openInputStream(imageUri);
                                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                    imgOriginal.setImageBitmap(selectedImage);
                                    originalImage =new File(imageUri.getPath().replace("raw/",""));
                                    txtOriginal.setText("Size: "+ Formatter.formatShortFileSize(comepress.this,originalImage.length()));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                    Toast.makeText(comepress.this, "Something Went wrong!", Toast.LENGTH_SHORT).show();

                                }
                            }
                            else{
                                Toast.makeText(comepress.this, "No image selected", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
public static final int RESULT_IMAGE=1;
ImageView imgOriginal, imgCompressed;
    TextView txtOriginal, txtCompressed, txtQuality;
    EditText txtHeight, txtWidth;
    SeekBar seekbar;
    Button btnPick, btnCompress;
    File originalImage, compressedImage;
    private static String filepath;
    File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myCompressor");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comepress);

        askPermission();
        imgOriginal = findViewById(R.id.imgOriginal);
        imgCompressed = findViewById(R.id.imgCompressed);
        txtOriginal = findViewById(R.id.txtOriginal);
        txtCompressed = findViewById(R.id.txtCompressed);
        txtQuality = findViewById(R.id.txtQuality);
        txtHeight = findViewById(R.id.txtHeight);
        txtWidth = findViewById(R.id.txtWidth);
        seekbar = findViewById(R.id.seekQuality);
        btnPick = findViewById(R.id.btnPick);
        btnCompress = findViewById(R.id.btnCompress);

        filepath = path.getAbsolutePath();
        if (!path.exists()) {
            path.mkdirs();
        }

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtQuality.setText("Quality: "+i);
                seekbar.setMax(100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        btnCompress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quality=seekbar.getProgress();
                int width=Integer.parseInt(txtWidth.getText().toString());
                int height=Integer.parseInt(txtHeight.getText().toString());
try{
                compressedImage=new Compressor(comepress.this)
                        .setMaxWidth(width)
                        .setMaxHeight(height)
                        .setQuality(quality)
                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
                        .setDestinationDirectoryPath(filepath)
                        .compressToFile(originalImage);
                File finalFile=new File(filepath,originalImage.getName());
                Bitmap finalBitmap=BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                imgCompressed.setImageBitmap(finalBitmap);
                txtCompressed.setText("Size: "+Formatter.formatShortFileSize(comepress.this,finalFile.length()));
    Toast.makeText(comepress.this, "Image compressed & saved", Toast.LENGTH_SHORT).show();
            }catch(IOException e){
            e.printStackTrace();
    Toast.makeText(comepress.this, "Error while compressing!!", Toast.LENGTH_SHORT).show();
            }
        }

        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        startActivityForResult(gallery,RESULT_IMAGE);
        activityResultLauncher.launch(gallery);
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode==RESULT_OK){
//            btnCompress.setVisibility(View.VISIBLE);
//            final Uri imageUri=data.getData();
//            try {
//                final InputStream imageStream=getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                imgOriginal.setImageBitmap(selectedImage);
//                originalImage =new File(imageUri.getPath().replace("raw/",""));
//                txtOriginal.setText("Size: "+ Formatter.formatShortFileSize(this,originalImage.length()));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Something Went wrong!", Toast.LENGTH_SHORT).show();
//
//            }
//        }
//        else{
//            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
//        }
//    }


//new

//    private void openSomeActivityForResult(){
//        ActivityResultLauncher<Intent> someActivityResultLauncher=registerForActivityResult()
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>(){
//            @Override
//                    public void onActivityResult
//
//                }
//
//    }


























    private void askPermission() {

        Dexter.withContext(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                          permissionToken.continuePermissionRequest();
                    }
                })
                .check();

}}

