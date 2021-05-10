package com.example.animate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import andorid.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import net.daum.android.map.MapViewEventListener;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

public class MapActivity extends AppCompatActivity {
    
    private double[] arr1 = new double[1000]; // 위도 좌표 저장배열
    private double[] arr2 = new double[1000]; // 경도 좌표 저장배열
    int a = 0;

    public GpsTracker(Context context) {
        this.mContext = context;
        getLocation();
    } //위치정보를 불러오기위한 gpsTracker함수
    
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
        
        MapReverseGeoCoder reverseGeoCoder = new MapReverseGeoCoder("66c4132dcc0995b63499b6d92b0b908a", mapPoint, reverseGeoCodingResultListener, mapActivity); 
        reverseGeoCoder.startFindingAddress(); //각각 APP키, 지도 중심, 이벤트리스너, 사용하는 액티비티 페이지

        // reverseGeoCodingResultListener
        @Override
        public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String addressString) { // 주소를 찾은 경우.
            gpstracker = new GpsTracker(MapActivity.this);
            double latitude = gpstracker.getLatitude(); //위도 구하기
            double longitude = gpstracker.getLongitude(); //경도 구하기
            arr1[a] = latitude;
            arr2[a] = longitude;
            a = a + 1;
        } //위치정보를 전송하면 각각의 배열에 위도, 경도값이 차례대로 저장됨

        @Override
        public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) { // 호출에 실패한 경우.
            Toast.makeText(getApplicationContext(),"주소를 찾을 수 없습니다.",Toast.LENGTH_SHORT).show();
        }
        
        for(int b = 0; b <= a; b++) {
            polyline.addPoint(MapPoint.mapPointWithGeoCoord(Arrays.toString(arr1.get(b)), Arrays.toString(arr2.get(b)));
        } // 찾은 주소의 위도 경도값을 배열에 넣은뒤 선으로 하나씩 연결
        
        mapView.addPolyline(polyline); //연결한 선을 지도에 표시
    }
}
