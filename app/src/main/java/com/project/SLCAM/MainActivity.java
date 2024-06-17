package com.project.SLCAM;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.systemcontrol_smartlight.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<MobileOS> mobileOSes;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        mobileOSes = new ArrayList<>();

        setData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter(this, mobileOSes);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //@Override
    //public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    //    if (item.getItemId() == R.id.item_github) {
    //        Uri uri = Uri.parse("https://github.com/");
    //        startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, uri), "Choose Browser"));
    //    }
    //    return true;
    //}

    private void setData() {
        ArrayList<TrafficLight> iphones = new ArrayList<>();
        iphones.add(new TrafficLight("Đèn số 1"));
        iphones.add(new TrafficLight("Đèn số 2"));
        iphones.add(new TrafficLight("Đèn số 3"));
        iphones.add(new TrafficLight("Đèn số 4"));
        iphones.add(new TrafficLight("Đèn số 5"));


        ArrayList<TrafficLight> nexus = new ArrayList<>();
        nexus.add(new TrafficLight("Đèn số 1"));
        nexus.add(new TrafficLight("Đèn số 2"));
        nexus.add(new TrafficLight("Đèn số 3"));
        nexus.add(new TrafficLight("Đèn số 4"));
        nexus.add(new TrafficLight("Đèn số 5"));

        ArrayList<TrafficLight> windowTrafficLights = new ArrayList<>();
        windowTrafficLights.add(new TrafficLight("Đèn số 1"));
        windowTrafficLights.add(new TrafficLight("Đèn số 2"));
        windowTrafficLights.add(new TrafficLight("Đèn số 3"));
        windowTrafficLights.add(new TrafficLight("Đèn số 4"));
        windowTrafficLights.add(new TrafficLight("Đèn số 5"));

        mobileOSes.add(new MobileOS("Đường Trần Hưng Đạo - Quận 1", iphones));
        mobileOSes.add(new MobileOS("Đường Nguyễn Văn Cừ - Quận 5", nexus));
        mobileOSes.add(new MobileOS("Đường Huỳnh Lan Khanh - Tân Bình", windowTrafficLights));
    }
}