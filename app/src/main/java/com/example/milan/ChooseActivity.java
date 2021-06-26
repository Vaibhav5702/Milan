package com.example.milan;

import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.milan.AnonymousSection.CreateAnonymousRoom;
import com.example.milan.InterestSection.InterestDetailsAdapter;

import com.example.milan.InterestSection.RoomAdapter;
import com.example.milan.RestrictedSection.RestrictedRoomAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetUserInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class ChooseActivity extends AppCompatActivity implements InterestDetailsAdapter.ItemClick,
        RoomAdapter.ItemClick, RestrictedRoomAdapter.ItemClick
{
    TabLayout tabLayout;
    ViewPager2 viewPager;
    PagerAdapter adapter;
    JitsiMeetConferenceOptions options;
    Timer timer;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        adapter=new PagerAdapter(this);

        viewPager.setAdapter(adapter);
        TabLayoutMediator mediator=new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position){
                        case 0:
                            tab.setText("Interests");
                            break;
                        case 1:
                            tab.setText("Anonymous");
                            break;
                        case 2:
                            tab.setText("Restricted");
                            break;
                    }
                });
        mediator.attach();

        try {
            options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL(""))
                    .setWelcomePageEnabled(false)
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int i) {
    }

    @Override
    public void onItemClickRoom(String roomName) {
        createRoomInterest(roomName);
    }

    @Override
    public void onItemClickRestricted(String roomName,String section) {
        if(section.equals("anonymous"))
        {
            createRoomAnonymous(roomName);
        }
        else
        {
            roomName+="(Restricted)";
            createRoomRestricted(roomName);
        }
    }

    public void fromMic(SpeechConfig speechConfig) throws InterruptedException, ExecutionException {
        AudioConfig audioConfig = AudioConfig.fromDefaultMicrophoneInput();
        SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, audioConfig);

        Future<SpeechRecognitionResult> task = recognizer.recognizeOnceAsync();
        SpeechRecognitionResult result = task.get();
        Log.i("RECOGNIZED: Text=" , result.getText());
        if(result.getText().contains("***"))
        {
            startActivity(new Intent(this,ChooseActivity.class));
            JitsiMeetActivity activity=new JitsiMeetActivity();
            activity.leave();

            timer.cancel();
            thread.interrupt();
        }
    }


    public  void backgroundTask(){
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(
                getString(R.string.subscription_key), getString(R.string.location));

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

    private void createRoomRestricted(String roomName) {

        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                backgroundTask();
            }
        };

        thread=new Thread(runnable);
        thread.start();

        FirebaseFirestore.getInstance().collection("Users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(documentSnapshot -> {
            String userName=documentSnapshot.get("name").toString();
            JitsiMeetUserInfo info=new JitsiMeetUserInfo();
            info.setDisplayName(userName);

            options = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(roomName)
                    .setUserInfo(info)
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
                    .build();
            JitsiMeetActivity.launch(ChooseActivity.this,options);
        });
    }

    private void createRoomAnonymous(String roomName) {
        Intent intent=new Intent(ChooseActivity.this, AnonymousCall.class);
        intent.putExtra("roomName",roomName);
        startActivity(intent);
    }

    public void createRoomInterest(String roomName){
        FirebaseFirestore.getInstance().collection("Users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(documentSnapshot -> {
            String userName=documentSnapshot.get("name").toString();
            JitsiMeetUserInfo info=new JitsiMeetUserInfo();
            info.setDisplayName(userName);
            options = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(roomName)
                    .setUserInfo(info)
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
                    .build();
            JitsiMeetActivity.launch(ChooseActivity.this,options);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null) {
            timer.cancel();
        }
    }
}