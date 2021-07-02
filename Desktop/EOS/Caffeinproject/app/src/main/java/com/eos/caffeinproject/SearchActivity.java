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

        LatLng SEOUL = new LatLng(37.559970, 127.042217);

//        MarkerOptions markerOptions1 = new MarkerOptions();
//        markerOptions1.position(SEOUL);
//        markerOptions1.title("202집");
//        markerOptions1.snippet("웰컴...");
//        googleMap.addMarker(markerOptions1);
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(SEOUL);
        markerOptions2.title("보경이집");
        markerOptions2.snippet("홈스윗홈...");
        googleMap.addMarker(markerOptions2);

        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions3.position(new LatLng(37.556608, 127.045892));
        markerOptions3.title("티아모");
        markerOptions3.snippet("개높아...");
        googleMap.addMarker(markerOptions3);


        MarkerOptions markerOptions4 = new MarkerOptions();
        markerOptions4.position(new LatLng(37.55714419798944, 127.04212995357115));
        markerOptions4.title("스타벅스 한양대점");
        markerOptions4.snippet("사람개많아");
        googleMap.addMarker(markerOptions4);

        MarkerOptions markerOptions5 = new MarkerOptions();
        markerOptions5.position(new LatLng(37.55729911717727, 127.04176722695227));
        markerOptions5.title("탐앤탐스 한양대점");
        markerOptions5.snippet("아아개노맛");
        googleMap.addMarker(markerOptions5);

        MarkerOptions markerOptions6 = new MarkerOptions();
        markerOptions6.position(new LatLng(37.557361535473305, 127.04163576424162));
        markerOptions6.title("흥신소");
        markerOptions6.snippet("갬성충");
        googleMap.addMarker(markerOptions6);

        MarkerOptions markerOptions7 = new MarkerOptions();
        markerOptions7.position(new LatLng(37.561390317437834, 127.0383793136441));
        markerOptions7.title("투썸플레이스 왕십리역점");
        markerOptions7.snippet("티라미수존맛");
        googleMap.addMarker(markerOptions7);

        MarkerOptions markerOptions8 = new MarkerOptions();
        markerOptions8.position(new LatLng(37.56038654925872, 127.04099547113613));
        markerOptions8.title("쥬씨 한양대점");
        markerOptions8.snippet("가성비갑1");
        googleMap.addMarker(markerOptions8);

        MarkerOptions markerOptions9 = new MarkerOptions();
        markerOptions9.position(new LatLng(37.55988027206512, 127.04126523493424));
        markerOptions9.title("더벤티");
        markerOptions9.snippet("가성비갑2");
        googleMap.addMarker(markerOptions9);

        MarkerOptions markerOptions10 = new MarkerOptions();
        markerOptions10.position(new LatLng(37.55994757838761, 127.04085248421728));
        markerOptions10.title("빽다방 한양대점");
        markerOptions10.snippet("가성비갑3");
        googleMap.addMarker(markerOptions10);




        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 17));
    }

}
