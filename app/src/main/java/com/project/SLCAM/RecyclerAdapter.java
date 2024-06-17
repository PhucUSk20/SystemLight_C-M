package com.project.SLCAM;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class RecyclerAdapter extends ExpandableRecyclerViewAdapter<OSViewHolder, TrafficLightViewHolder> {
    private Activity activity;

    public RecyclerAdapter(Activity activity, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.activity = activity;
    }

    @Override
    public OSViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.group_view_holder, parent, false);

        return new OSViewHolder(view);
    }

    @Override
    public TrafficLightViewHolder onCreateChildViewHolder(ViewGroup parent, final int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.child_view_holder, parent, false);

        return new TrafficLightViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(TrafficLightViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        TrafficLight trafficLight = ((MobileOS) group).getItems().get(childIndex);
        holder.onBind(trafficLight,group);
    }

    @Override
    public void onBindGroupViewHolder(OSViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGroupName(group);
    }
}
