package com.example.colorfulimage;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.colorfulimage.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity{

    ActivityResultLauncher<Intent> mActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){

                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        assert result.getData() != null;
                        Bundle bundle = result.getData().getExtras();

                        bitmap = (Bitmap) bundle.get("data");
                        colorful = new Colorful(bitmap, 0.0f, 0.0f, 0.0f);
                        binding.imageView.setImageBitmap(bitmap);
                    }
                }
            }
    );

    ActivityMainBinding binding;
    Bitmap bitmap;
    private Colorful colorful;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.takPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionResult = ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA);
                if(permissionResult == PackageManager.PERMISSION_GRANTED){
                    PackageManager packageManager = getPackageManager();
                    if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        mActivityResult.launch(cameraIntent);
                    }else {
                        Toast.makeText(MainActivity.this, "Your device does not have a camera", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA},1);
                }
            }
        });
        ColorizationHandler colorizationHandler = new ColorizationHandler();
        binding.seekRed.setOnSeekBarChangeListener(colorizationHandler);
        binding.seekGreen.setOnSeekBarChangeListener(colorizationHandler);
        binding.seekBlue.setOnSeekBarChangeListener(colorizationHandler);
        binding.savePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck == PackageManager.PERMISSION_GRANTED){
                    try {
                        SaveFile.saveFile(MainActivity.this, bitmap);
                        Toast.makeText(MainActivity.this, "Saved",Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            200);
                }
            }
        });
    }

    private class ColorizationHandler implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser){
                if (seekBar == binding.seekRed){
                    colorful.setRedColorValue(progress/100);
                    binding.seekRed.setProgress( (int) (100 * (colorful.getRedColorValue())));
                    binding.txtRed.setText(colorful.getRedColorValue() + "");
                }else if (seekBar == binding.seekGreen){
                    colorful.setGreenColorValue(progress/100);
                    binding.seekGreen.setProgress( (int) (100 * (colorful.getGreenColorValue())));
                    binding.txtGreen.setText(colorful.getGreenColorValue() + "");
                }else if (seekBar == binding.seekBlue){
                    colorful.setBlueColorValue(progress/100);
                    binding.seekBlue.setProgress((int) (100 * (colorful.getBlueColorValue())));
                    binding.txtBlue.setText(colorful.getBlueColorValue() + "");
                }
                bitmap = colorful.ReturnColorizeTheBitmap();
                binding.imageView.setImageBitmap(bitmap);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}