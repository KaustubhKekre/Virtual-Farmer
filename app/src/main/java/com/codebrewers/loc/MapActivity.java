package com.codebrewers.loc;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap map;
    SharedPreferences spref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment smap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        smap.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        spref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        map = googleMap;
        LatLng me = new LatLng(Double.parseDouble(spref.getString("lat", "19.1071")), Double.parseDouble(spref.getString("lon", "72.8373")));
        map.addMarker(new MarkerOptions().position(me).title("YOU"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(me, 15));


        LatLng atm = new LatLng(19.1029, 72.8374);
        map.addMarker(new MarkerOptions().position(atm).title("Equipment").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        map.moveCamera(CameraUpdateFactory.newLatLng(atm));
    }
}