package com.example.delme.smartair.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.delme.smartair.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ARFragment extends Fragment {

    private ImageView currentPhoto, arrow;
    private Button takePhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle s) {
        return inflater.inflate(R.layout.fragment_ar, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        takePhoto = getView().findViewById(R.id.fragmentAR_takePhoto);
        arrow = getView().findViewById(R.id.fragmentAR_arrow);
        currentPhoto = getView().findViewById(R.id.fragmentAR_photo);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 0);
                }else { requestPermissions(); }
            }
        });
    }

    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        if(requestCode == 0) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                currentPhoto.setImageBitmap(photo);
                takePhoto.setVisibility(View.INVISIBLE);
                arrow.setVisibility(View.VISIBLE);
            }
            catch(Exception ex) { }
        }
    }

    // MARK - PERMISSIONS

    // Check permissions for > 23 APIs
    private boolean checkPermission() {
        int permissionState = ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    // Resquest permissions
    private void requestPermissions() {
        boolean shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale (
                getActivity(), Manifest.permission.CAMERA);
        if (shouldProvideRationale) {
        } else {
            // Request permissions
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Our know code
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 0);
            } else {
                // Permission denied.
            }
        }
    }
}
