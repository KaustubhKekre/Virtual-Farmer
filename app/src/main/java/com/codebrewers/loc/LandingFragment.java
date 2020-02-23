package com.codebrewers.loc;

import android.content.Context;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LandingFragment extends Fragment {


Button save;
    public LandingFragment() {
        // Required empty public constructor
    }
//todo- Replace with actual landing page

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ListView grow,notGrow;
        final ProgressBar pbar;
        final TextView grown,notGrown;
        // Inflate the layout for this fragment
        final View v= inflater.inflate(R.layout.fragment_landing, container, false);
        save=v.findViewById(R.id.save);
        grow=v.findViewById(R.id.grow);
        pbar=v.findViewById(R.id.pbar);
        notGrow=v.findViewById(R.id.notGrow);
        grown=v.findViewById(R.id.grown);
        notGrown=v.findViewById(R.id.notGrown);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                pbar.setVisibility(View.VISIBLE);
                save.setVisibility(View.GONE);
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.43.48:8000/api/").addConverterFactory(
                        GsonConverterFactory.create()).build();
                RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
                Call<CropsList> cropsListCall = retrofitInterface.getCrops(new LogInRequest("crop","crop"));
                cropsListCall.enqueue(new Callback<CropsList>() {
                    @Override
                    public void onResponse(Call<CropsList> call, Response<CropsList> response) {
                        if(response.body()!=null){
                            pbar.setVisibility(View.GONE);
                            save.setVisibility(View.GONE);
                            grown.setVisibility(View.VISIBLE);
                            notGrown.setVisibility(View.VISIBLE);
                            ArrayList<String>growArray=response.body().getCrops();
                            ArrayAdapter adapter = new ArrayAdapter(v.getContext(),
                                    android.R.layout.simple_list_item_1, growArray);
                            grow.setAdapter(adapter);
                            ArrayAdapter adapter2 = new ArrayAdapter(v.getContext(),
                                    android.R.layout.simple_list_item_1, response.body().getCannot_grow_crops());
                            notGrow.setAdapter(adapter2);
                        }
                        else {
                            pbar.setVisibility(View.GONE);
                            save.setVisibility(View.VISIBLE);

                            Toast.makeText(v.getContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CropsList> call, Throwable t) {
                        Toast.makeText(v.getContext(),""+t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return v;
    }



}
