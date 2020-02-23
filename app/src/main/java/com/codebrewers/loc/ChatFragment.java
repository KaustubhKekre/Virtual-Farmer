package com.codebrewers.loc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ChatFragment extends Fragment {
    CardView crop,soil,livestock;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_chat, container, false);
        crop=v.findViewById(R.id.crop);
        livestock=v.findViewById(R.id.livestock);
        soil=v.findViewById(R.id.soil);
        final Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://cb-video-call.herokuapp.com/"));
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(browserIntent);
            }
        });
        livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(browserIntent);
            }
        });
        soil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(browserIntent);
            }
        });
        return v;
    }

}
