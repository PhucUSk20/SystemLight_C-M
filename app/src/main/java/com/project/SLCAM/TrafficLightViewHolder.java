package com.project.SLCAM;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class TrafficLightViewHolder extends ChildViewHolder {
    private final TextView phoneName;

    public TrafficLightViewHolder(View itemView) {
        super(itemView);
        phoneName = itemView.findViewById(R.id.phone_name);
    }

//    public void onBind(TrafficLight trafficLight, ExpandableGroup group) {
//        phoneName.setText(trafficLight.getName());
//        if (group.getTitle().equals("Android")) {
////            phoneName.setVisibility(View.GONE);
//            phoneName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_btn_expand, 0, 0, 0);
//        } else if (group.getTitle().equals("iOS")) {
//            phoneName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_btn_collapse, 0, 0, 0);
//        } else {
//            phoneName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_btn_expand, 0, 0, 0);
//        }
//    }
    public void onBind(TrafficLight trafficLight, ExpandableGroup group) {
        phoneName.setText(trafficLight.getName());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), LineChartActivity.class);
                intent.putExtra("TRAFFIC_LIGHT_NAME", trafficLight.getName());
                itemView.getContext().startActivity(intent);
            }
        });
    }
}