package com.project.SLCAM;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChartView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.systemcontrol_smartlight.R;
import com.triggertrap.seekarc.SeekArc;
public class TrafficLightDetailActivity extends AppCompatActivity {

    private TextView brightnessValue;
    private TextView environmentBrightnessValue;
    private SeekArc seekArc;

    private DatabaseReference lightLevelReference;
    private DatabaseReference lightEnvironmentReference;
    private int progress = 0;
    private int environmentProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_brightness_layout);

        brightnessValue = findViewById(R.id.brightness_value);
        environmentBrightnessValue = findViewById(R.id.environment_brightness_value);
        seekArc = findViewById(R.id.seekArc);
        AnyChartView anyChartView = findViewById(R.id.gaugeEnvironment); // Correctly initializing progressBarEnvironment

        setupSeekArc();
        setupFirebase();
    }

    private void setupSeekArc() {
        seekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int progress2, boolean fromUser) {
                if (fromUser) {
                    brightnessValue.setText("Brightness: " + progress2 + "%");
                    progress = progress2;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {
                // Handle touch start event if needed
            }

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {
                updateLedBrightness(progress);
            }
        });
    }

    private void setupFirebase() {
        // Initialize Firebase Database references
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("led1");
        lightLevelReference = databaseReference.child("DimmingLevel");
        lightEnvironmentReference = databaseReference.child("LightEnvironment");

        // Add listeners to read data from Firebase
        lightLevelReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer levelLight = dataSnapshot.getValue(Integer.class);
                if (levelLight != null) {
                    progress = levelLight;
                    seekArc.setProgress(progress);
                    brightnessValue.setText("Brightness: " + progress + "%");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error if needed
            }
        });

        lightEnvironmentReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer environmentLight = dataSnapshot.getValue(Integer.class);
                if (environmentLight != null) {
                    environmentProgress = environmentLight;
                    environmentBrightnessValue.setText("Cường độ sáng môi trường: " + environmentProgress + " lux");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error if needed
            }
        });
    }

    private void updateLedBrightness(int brightnessPercent) {
        lightLevelReference.setValue(brightnessPercent);
    }
}
