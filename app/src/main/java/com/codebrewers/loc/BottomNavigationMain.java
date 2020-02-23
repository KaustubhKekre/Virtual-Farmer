package com.codebrewers.loc;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.codebrewers.loc.App.CHANNEL_2_ID;

public class BottomNavigationMain extends AppCompatActivity {
Handler mHandler=new Handler();
    private NotificationManagerCompat notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LandingFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        notificationManager = NotificationManagerCompat.from(this);
        startRepeating();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment tempFragment = null;

                switch(item.getItemId()){
                    case R.id.weather:
                        tempFragment=new WeatherFragment();
                        break;
                    case R.id.profile:
                        tempFragment = new ProfileFragment();
                        break;
                    case R.id.landing:
                        tempFragment = new LandingFragment();
                        break;

                    case R.id.tracker:
                        tempFragment = new TrackerFragment();
                        break;

                    case R.id.chat:
                        tempFragment = new ChatFragment();
                        break;


                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, tempFragment).commit();
                return true;
            }
        });
    }
    public void startRepeating(){

mToastRunnable.run();

    }

    public void cancelJob() {
      mHandler.removeCallbacks(mToastRunnable);

    }

    private Runnable mToastRunnable=new Runnable() {
        public static final String CHANNEL_1_ID = "channel1";
        @Override
        public void run() {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.43.48:8000/api/").addConverterFactory(
                    GsonConverterFactory.create()).build();
            RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
            Call<notify> notifyCall = retrofitInterface.getNotif();
            notifyCall.enqueue(new Callback<notify>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onResponse(Call<notify> call, Response<notify> response) {
                    if(response.body()!=null){

                       if(response.body().getSoil().equals("1"))
{
                           Notification notification = new NotificationCompat.Builder(BottomNavigationMain.this, CHANNEL_1_ID)
                          .setSmallIcon(R.drawable.no_water).setContentTitle("Dry Soil")
                                    .setContentText("Soil dried up, watering protocol initiated")
                                   .setPriority(NotificationCompat.PRIORITY_HIGH)
                                   .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                   .build();

                           notificationManager.notify(1, notification);

                            cancelJob();
                        }
//                        if(response.body().getTank().equals("1"))
//
//                       {
//                           Notification notification = new NotificationCompat.Builder(BottomNavigationMain.this, CHANNEL_2_ID)
//                                   .setSmallIcon(R.drawable.no_water).setContentTitle("Tank Empty")
//                                   .setContentText("Pump started for filling up tank")
//                                   .setPriority(NotificationCompat.PRIORITY_HIGH)
//                                   .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                                   .build();
//
//                           notificationManager.notify(1, notification);
//
//                           cancelJob();
//                       }
//                        else
//                        {
//                            Log.d("tag", "onResponse: "+response.body().getTank());
//                        }

                    }
                    else
                    {
                        Toast.makeText(BottomNavigationMain.this,"Something went wrong",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<notify> call, Throwable t) {
                    Toast.makeText(BottomNavigationMain.this,""+t.getMessage(),Toast.LENGTH_LONG).show();

                }
            });
            mHandler.postDelayed(this,3000);
        }
    };

}
