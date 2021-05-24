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

import java.util.Timer;
import java.util.TimerTask;

import com.opencsv.CSVReader; 
import java.io.IOException;
import java.io.FileReader;

public class MapActivity extends AppCompatActivity {
    
    private double[] arr1 = new double[1000]; // 위도 좌표 저장배열
    private double[] arr2 = new double[1000]; // 경도 좌표 저장배열
    
    int a = 0;
    
    final Geocoder geocoder = new Geocoder(this);

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
                              
        Button button3 = (Button)findViewById(R.id.button3); //산책버튼
                              
        button3.setOnClickListener(new View.OnClickListener() { //산책버튼 누르면 1분마다 위도, 경도 저장후 라인으로 나타냄, 한번 더 누르면 종료
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"산책을 시작합니다.",Toast.LENGTH_SHORT).show();
                Timer timer = new Timer(); //타이머 클래스 : 자식 스레드 생성후 특정행동 반복

                TimerTask TT = new TimerTask() { //타이머 클래스를 사용하기 위한 객체 생성기
                    @Override
                    public void run() { //해당 구문 반복
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
                };

                timer.schedule(TT, 0, 60000); //수행할 작업, 딜레이, 반복 시간(60초)
                button3.setOnClickListener(new View.OnClickListener() { //두번째 누른 산책버튼
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"산책을 종료합니다.",Toast.LENGTH_SHORT).show();
                        timer.cancel(); //타이머 종료
                    }
                }
            }
            try{
                File csvfile = new File(Enviroment.getExternalStorageDirectory() + "/제주특별자치도_동물병원현황_20210218.csv"); //csv파일 불러오기
                CSVReader rd1 = new CSVReader(new FileReader(csvfile.getAbsolutePath()));
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) { //해당 csv파일을 전부 읽어올 때까지 반복
                    double[] li1 = new double[100]; //위도 경도값을 넣기 위한 배열
                    try {
                        double d1 = Arrays.toString(nextLine[위도]);
                        double d2 = Arrays.toString(nextLine[경도]);
                        
                        li1 = geocoder.getLocation(d1, d2, 100); //위도, 경도, 받아들일 값의 개수
                    } catch (IOException e) { //읽어오기를 실패했을 때
                        e.printStackTrace();
                        Toast.makeText(this."값을 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    
                    for(i=0; i<nextLine.LENGTH; i++){
                        MapPOIItem marker = new MapPOIItem();
                        marker.setItemName(Arrays.toString(nextLine[i])); //처음 업소명부터 출력
                        marker.setTag(0);
                        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(Arrays.toString(li1[i])));
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                        mapView.addPOIItem(marker);
                    } //csv파일내의 지역을 순서대로 마커로 표시하기 위함
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this."CSV파일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
            try{
                File csvfile2 = new File(Enviroment.getExternalStorageDirectory() + "/제주특별자치도 제주시_동물관련업현황_20191231");
                CSVReader rd2 = new CSVReader(new FileReader(csvfile.getAbsolutePath()));
                String[] nextLine2;
                while ((nextLine2 = reader.readNext()) != null) {
                    List li2 = reader.readAll();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this."CSV파일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
            try{
                File csvfile3 = new File(Enviroment.getExternalStorageDirectory() + "/제주특별자치도 제주시_동물약국현황_20200422");
                CSVReader rd3 = new CSVReader(new FileReader(csvfile.getAbsolutePath()));
                String[] nextLine3;
                while ((nextLine3 = reader.readNext()) != null) {
                    List li3 = reader.readAll();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this."CSV파일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
