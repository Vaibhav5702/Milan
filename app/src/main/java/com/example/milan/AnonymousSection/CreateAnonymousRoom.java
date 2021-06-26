package com.example.milan.AnonymousSection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.milan.AnonymousCall;
import com.example.milan.R;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CreateAnonymousRoom extends AppCompatActivity {

    private static final String LOG_TAG = "Agora API";
    EditText anonymous_create_et;
    Button create_ano_room_btn;
    String roomName;

    private static final int PERMISSION_REQ_ID_RECORD_AUDIO = 22;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_anonymous_room);
        anonymous_create_et=findViewById(R.id.anonymous_create_et);
        create_ano_room_btn=findViewById(R.id.create_ano_room_btn);
        create_ano_room_btn.setOnClickListener(v -> {
            storeDetails();
        });
    }
    public void createRoom()
    {
        Intent intent=new Intent(CreateAnonymousRoom.this, AnonymousCall.class);
        intent.putExtra("roomName",anonymous_create_et.getText().toString());
        startActivity(intent);

    }
    private void permissionReq() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // Permission is NOT granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                new AlertDialog.Builder(this)
                        .setTitle("Need Permissions")
                        .setMessage("Audio Permission is Required")
                        .setCancelable(false)
                        .setPositiveButton("Ok", (dialog, which) -> requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_REQ_ID_RECORD_AUDIO))
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO}, PERMISSION_REQ_ID_RECORD_AUDIO);
            }

        } else {
            // Permission is Granted
            createRoom();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_ID_RECORD_AUDIO) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission Granted
                createRoom();
            } else {
                //Permission NOT granted
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                    //This block here means PERMANENTLY DENIED PERMISSION
                    new AlertDialog.Builder(this)
                            .setMessage("You have permanently denied this permission, go to settings to enable this permission")
                            .setPositiveButton("Go to settings", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    gotoApplicationSettings();
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .setCancelable(false)
                            .show();


                } else {
                    //
                    Toast.makeText(this, "Cannot save the number as permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void gotoApplicationSettings() {

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", this.getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);

    }

    public void storeDetails(){
        roomName=anonymous_create_et.getText().toString().trim();
        if(roomName.isEmpty())
            Toast.makeText(this, "Please Fill all the required fields!", Toast.LENGTH_SHORT).show();
        else{
            Map<String,Object> map=new HashMap<>();
            map.put("roomName",roomName);
            map.put("category","N/A");
            FirebaseFirestore.getInstance().collection("Anonymous").add(map)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            permissionReq();
                        }
                        else {
                            Toast.makeText(this, "Error creating the room", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}