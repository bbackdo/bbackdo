package com.eos.caffeinproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class SearchActivity extends Fragment implements OnMapReadyCallback {
    View v;
    private GoogleMap mMap;
    private MapView mapView = null;

    public SearchActivity()
    {
        // required
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        v = inflater.inflate(R.layout.activity_search,container, false);

        mapView = (MapView)v.findViewById(R.id.map);
        mapView.getMapAsync(this);



        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        LatLng SEOUL = new LatLng(37.59039640884452, 126.94058289771243);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("202집..");
        googleMap.addMarker(markerOptions);
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 10));
    }

}
