package com.example.animate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import net.daum.android.map.MapViewEventListener;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

public class MapActivity extends AppCompatActivity {

    static final String API_KEY = "66c4132dcc0995b63499b6d92b0b908a";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapactivity);

        MapView mapView = new MapView(MapActivity.this);
        mapView.setDaumMapApiKey(API_KEY);

        ConstraintLayout mapViewContainer = (ConstraintLayout) findViewById(R.id.mapView);
        mapViewContainer.addView(mapView);

        mapView.setMapViewEventListener((MapViewEventListener) this); // this에 MapView.MapViewEventListener 구현.
        mapView.setPOIItemEventListener((MapView.POIItemEventListener) this);

        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(33.48904974711205, 126.49809226214053), true);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("제주도청");
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(33.48904974711205, 126.49809226214053));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);

        Button button2 = (Button) findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
