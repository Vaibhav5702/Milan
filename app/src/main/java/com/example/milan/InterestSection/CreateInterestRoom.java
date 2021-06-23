package com.example.milan.InterestSection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.milan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CreateInterestRoom extends AppCompatActivity {

    EditText interest_create_et,interest_sub_et;
    Button create_btn;
    Spinner interest_spinner;
    String categorySelected="",roomName="",subCategory="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_interest_room);
        create_btn=findViewById(R.id.create_int_room_btn);
        interest_create_et=findViewById(R.id.interest_create_et);
        interest_spinner=findViewById(R.id.category_interest);
        interest_sub_et=findViewById(R.id.interest_sub_et);

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        interest_spinner.setAdapter(adapter);
        interest_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorySelected=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(CreateInterestRoom.this, "Select a category", Toast.LENGTH_SHORT).show();
            }
        });

        create_btn.setOnClickListener(v -> {
            storeDetails();
        });
    }

    private void createRoom() {
        try {
            if(!subCategory.equals("N/A"))
                roomName+="-"+subCategory;
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL(""))
                    .setRoom(roomName)
                    .setFeatureFlag("add-people.enabled",false)
                    .setFeatureFlag("chat.enabled",false)
                    .setFeatureFlag("invite.enabled",false)
                    .setFeatureFlag("meeting-password.enabled",false)
                    .setFeatureFlag("live-streaming.enabled",false)
                    .setFeatureFlag("kick-out.enabled",false)
                    .setFeatureFlag("recording.enabled",false)
                    .setFeatureFlag("calendar.enabled",false)
                    .setAudioMuted(false)
                    .setVideoMuted(false)
                    .setAudioOnly(false)
                    .setWelcomePageEnabled(false)
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void storeDetails(){
        roomName=interest_create_et.getText().toString().trim();
        subCategory=interest_sub_et.getText().toString().trim();
        if(roomName.isEmpty() || categorySelected.isEmpty())
            Toast.makeText(this, "Please Fill all the required fields!", Toast.LENGTH_SHORT).show();
        else{
            if (subCategory.isEmpty())
                subCategory="N/A";

            Map<String,Object> map=new HashMap<>();
            map.put("roomName",roomName);
            map.put("subCategory",subCategory);
            FirebaseFirestore.getInstance().collection("Interests")
                    .document("AllInterests").collection(categorySelected).add(map)
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