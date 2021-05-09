package com.example.animate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import net.daum.android.map.MapViewEventListener;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

public class MapActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapactivity);
        MapView mapView = new MapView(this);

        mapView.setMapViewEventListener((MapViewEventListener) this); // this에 MapView.MapViewEventListener 구현.
        mapView.setPOIItemEventListener((MapView.POIItemEventListener) this);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(33.48904974711205, 126.49809226214053), true); // 중심점 변경
        mapView.setZoomLevel(7, true); // 줌 레벨 변경
        mapView.zoomIn(true); // 줌 인
        mapView.zoomOut(true); // 줌 아웃

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.mapView);
        mapViewContainer.addView(mapView);

        MapPOIItem marker = new MapPOIItem();
        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(33.48904974711205, 126.49809226214053);
        mapView.setMapCenterPoint(MARKER_POINT, true);
        marker.setItemName("제주도청");
        marker.setTag(0);
        marker.setMapPoint(MARKER_POINT);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker);

        //뒤로가기 버튼
        Button button2 = (Button)findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //산책 경로 표시 예시
        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(128, 255, 0, 0)); // Polyline 색 지정, 빨간색

        // Polyline 좌표 지정.
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(33.487974,126.495803)); //은남로
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(33.488994,126.498346)); //제주도청
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(33.491759,126.498732)); //연문 2길
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(33.491911,126.494698)); //삼무로 9길

        // Polyline 지도에 올리기.
        mapView.addPolyline(polyline);

        // 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        int padding = 100; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
    }
}
