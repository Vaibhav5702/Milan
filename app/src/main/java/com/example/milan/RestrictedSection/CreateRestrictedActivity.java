package com.example.milan.RestrictedSection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.milan.ChooseActivity;
import com.example.milan.InterestSection.CreateInterestRoom;
import com.example.milan.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetOngoingConferenceService;
import org.jitsi.meet.sdk.JitsiMeetView;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.Permission;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CreateRestrictedActivity extends AppCompatActivity {

    EditText restricted_create_et;
    Spinner category_restricted;
    Button create_res_room_btn;
    String roomName,categorySelected;
    JitsiMeetConferenceOptions options;
    SpeechConfig speechConfig;
    Timer timer;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restricted);
        restricted_create_et=findViewById(R.id.restricted_create_et);
        category_restricted=findViewById(R.id.category_restricted);
        create_res_room_btn=findViewById(R.id.create_res_room_btn);
        speechConfig = SpeechConfig.fromSubscription(
                getString(R.string.subscription_key), getString(R.string.location));

        try {
            options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL(""))
                    .setWelcomePageEnabled(false)
                    .build();;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_restricted.setAdapter(adapter);
        category_restricted.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorySelected=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(CreateRestrictedActivity.this, "Select a category", Toast.LENGTH_SHORT).show();
            }
        });
        create_res_room_btn.setOnClickListener(v -> {
            storeDetails();
        });
    }
    public void storeDetails(){
        roomName=restricted_create_et.getText().toString().trim();
        if(roomName.isEmpty() || categorySelected.isEmpty())
            Toast.makeText(this, "Please Fill all the required fields!", Toast.LENGTH_SHORT).show();
        else{
            Map<String,Object> map=new HashMap<>();
            map.put("roomName",roomName);
            map.put("category",categorySelected);
            FirebaseFirestore.getInstance().collection("Restricted").add(map)
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

    private void createRoom() {
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                backgroundTask();
            }
        };

        thread=new Thread(runnable);
        thread.start();


        roomName+="(Restricted)";
        options = new JitsiMeetConferenceOptions.Builder()
                .setRoom(roomName)
                .setFeatureFlag("add-people.enabled",false)
                .setFeatureFlag("chat.enabled",false)
                .setFeatureFlag("invite.enabled",false)
                .setFeatureFlag("meeting-password.enabled",false)
                .setFeatureFlag("live-streaming.enabled",false)
                .setFeatureFlag("kick-out.enabled",false)
                .setFeatureFlag("recording.enabled",false)
                .setFeatureFlag("calendar.enabled",false)
                .setFeatureFlag("disableRemoteMute",true)
                .setWelcomePageEnabled(false)
                .setAudioMuted(false)
                .setVideoMuted(false)
                .setAudioOnly(false)
                .build();

        JitsiMeetActivity.launch(this,options);
    }

    public  void backgroundTask(){

        timer=new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    fromMic(speechConfig);
                }
                catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        },0,1000);
    }

    public void fromMic(SpeechConfig speechConfig) throws InterruptedException, ExecutionException {
        AudioConfig audioConfig = AudioConfig.fromDefaultMicrophoneInput();
        SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, audioConfig);

        Future<SpeechRecognitionResult> task = recognizer.recognizeOnceAsync();
        SpeechRecognitionResult result = task.get();
        Log.i("RECOGNIZED: Text=" , result.getText());
        if(result.getText().contains("***"))
        {
            JitsiMeetActivity activity=new JitsiMeetActivity();
            activity.leave();

            timer.cancel();
            thread.interrupt();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(timer!=null) {
            timer.cancel();
            finish();
        }

    }
}