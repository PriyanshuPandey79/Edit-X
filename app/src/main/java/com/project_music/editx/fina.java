package com.project_music.editx;

import static com.project_music.editx.MainActivity.imgUri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;
import com.project_music.editx.databinding.ActivityFinaBinding;

public class fina extends AppCompatActivity {
    ActivityFinaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFinaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent dsPhotoEditorIntent = new Intent(this, DsPhotoEditorActivity.class);

        dsPhotoEditorIntent.setData(imgUri);


        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "Editx");


//        int[] toolsToHide = {DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_CROP};
//
//        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide);



        startActivityForResult(dsPhotoEditorIntent, 200);


//        Toast.makeText(this,imgUri.toString() Toast.LENGTH_SHORT).show();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case 200:

                    Uri outputUri = data.getData();

                    // handle the result uri as you want, such as display it in an imageView;

                  binding.imgView.setImageURI(outputUri);

                    break;

            }

        }

    }
}