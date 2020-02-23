package com.codebrewers.loc;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codebrewers.loc.weather.List;
import com.codebrewers.loc.weather.WeatherPOJO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


public class WeatherFragment extends Fragment {
    int count;
    String[] w;
    String[] img;
    String[] d;
    double[] tempmin;
    double[] tempmax;
    double[] humidity;


    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences spref;
        final TextView apology;
        final View v= inflater.inflate(R.layout.fragment_weather, container, false);
        final ProgressBar pbar=v.findViewById(R.id.progressbar);
        apology=v.findViewById(R.id.apology);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        spref = v.getContext().getSharedPreferences("user", MODE_PRIVATE);
        Call<WeatherPOJO> weatherPOJOCall = retrofitInterface.getWeatherData(spref.getString("lat","19.1071"),spref.getString("lon","72.8777"),"fa4ff94bda4c3a3b60a3b30d62821f9b","metrics");
        System.out.println(""+spref.getString("lat","19.0760")+" "+spref.getString("lon","72.8373"));

        weatherPOJOCall.enqueue(new Callback<WeatherPOJO>() {
            @Override
            public void onResponse(Call<WeatherPOJO> call, Response<WeatherPOJO> response) {
                if(response.body()!=null){
                    WeatherPOJO weatherPOJO = response.body();
                    count=weatherPOJO.getCnt();
                    System.out.println("count"+count);


                    ArrayList<List> weather = (ArrayList<List>)weatherPOJO.getList();
                    w=new String[count];
                    img=new String[count];
                    d=new String[count];
                    tempmin=new double[count];
                    tempmax=new double[count];
                    humidity=new double[count];
                    for(int i=0;i<count;i++){

                        d[i] = weather.get(i).getDtTxt();
                        tempmin[i] = weather.get(i).getMain().getTempMin();
                        tempmax[i] = weather.get(i).getMain().getTempMax();
                        humidity[i] = weather.get(i).getMain().getHumidity();
                        w[i]=weather.get(i).getWeather().get(0).getDescription();
                        img[i]=weather.get(i).getWeather().get(0).getIcon();
                   }
                    RecyclerView recycler = v.findViewById(R.id.recycler);
                    recycler.setLayoutManager(new LinearLayoutManager(v.getContext()));
                    pbar.setVisibility(View.GONE);
                    recycler.setAdapter(new weatheradapter(w, d, tempmin, tempmax, humidity,img));
                }
                else{
                    pbar.setVisibility(View.GONE);
                    apology.setVisibility(View.VISIBLE);
                    Toast.makeText(v.getContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherPOJO> call, Throwable t) {
                pbar.setVisibility(View.GONE);
                apology.setVisibility(View.VISIBLE);
                Toast.makeText(v.getContext(),""+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }
}
