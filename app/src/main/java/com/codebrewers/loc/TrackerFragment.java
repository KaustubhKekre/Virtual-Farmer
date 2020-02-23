package com.codebrewers.loc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;



public class TrackerFragment extends Fragment implements OnMapReadyCallback {

    public TrackerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final SharedPreferences.Editor editor;
        View v= inflater.inflate(R.layout.fragment_tracker, container, false);

        startActivity(new Intent(v.getContext(),MapActivity.class));
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
