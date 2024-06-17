package com.project.SLCAM;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class MobileOS extends ExpandableGroup<TrafficLight> {

    public MobileOS(String title, List<TrafficLight> items) {
        super(title, items);
    }
}