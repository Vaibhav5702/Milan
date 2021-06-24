package com.example.milan.AnonymousSection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.milan.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAnonymousRoom extends AppCompatActivity {

    EditText anonymous_create_et;
    Button create_ano_room_btn;
    String roomName;
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
                            createRoom();
                        }
                        else {
                            Toast.makeText(this, "Error creating the room", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}