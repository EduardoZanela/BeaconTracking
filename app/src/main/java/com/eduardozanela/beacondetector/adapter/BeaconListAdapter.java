package com.eduardozanela.beacondetector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eduardozanela.beacondetector.R;

import org.altbeacon.beacon.Beacon;

import java.util.List;

public class BeaconListAdapter extends ArrayAdapter<Beacon> {

    public BeaconListAdapter(Context context, List<Beacon> beaconList){
        super(context, 0, beaconList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Beacon beacon = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.beacon_card, parent, false);
        }

        TextView uid = convertView.findViewById(R.id.uid);
        uid.setText(beacon.getId1().toString());

        TextView distance = convertView.findViewById(R.id.distance);
        distance.setText(""+beacon.getDistance());

        return convertView;
    }


}
