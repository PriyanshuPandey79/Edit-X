package com.project_music.editx;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.github.dhaval2404.imagepicker.ImagePicker;
import com.project_music.editx.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
private Button compress;
ActivityMainBinding binding;
    public static Uri imgUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        compress=(Button) findViewById(R.id.compress);
        compress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCompress();
            }
        });

        binding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(MainActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .start();

            }
        });
    }
    public void openCompress(){
        Intent intent=new Intent(this,comepress.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            imgUri = data.getData();
            if(!imgUri.equals(" "))
            startActivity(new Intent(MainActivity.this, fina.class));
        }
        catch (Exception e){

        }
    }
}