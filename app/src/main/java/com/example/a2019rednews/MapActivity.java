package com.example.a2019rednews;
import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    //구글 맵 사용
    GoogleMap mMap;
    // 사용자 위치 수신기
    double lng; double lat;
    int REQUEST_CODE_LOCATION = 2;

    bluetoothservice bs;
    boolean isService = false;

    ServiceConnection conn = new ServiceConnection() {
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            // 서비스와 연결되었을 때 호출되는 메서드
            bluetoothservice.bluetoothBinder bb =
                    (bluetoothservice.bluetoothBinder) service;
            bs = bb.getService();
            isService = true; // 실행 여부를 판단
        }
        public void onServiceDisconnected(ComponentName name) {
            // 서비스와 연결이 끊기거나 종료되었을 때
            isService = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent bluetooth = new Intent(
                getApplicationContext(),// Activity Context
                bluetoothservice.class); // 이동할 서비스 객체
        bindService(bluetooth, conn, Context.BIND_AUTO_CREATE);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); // 맵을 어떤 형식으로 띄우느냐

        //위치 관리자 객체 참조
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final LocationListener gpsLocationListener = new LocationListener() {
            @Override
            //위치 정보가 전달될 때 호출되는 onLocationChanged()
            public void onLocationChanged(Location location) {
                lat = location.getLongitude(); //위도, 경도와 좌표 확인
                lng = location.getLatitude();
                userMarker(lat, lng);  //위치가 갱신되면 사용자 위치 새 마커찍기
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            // 사용자 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
        }
        else {
            //최근에 확인했던 위치 정보 확인
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lng = location.getLongitude();
            lat = location.getLatitude();

            //1초마다 0m 이동시 위치 갱신
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000,
                    0,
                    gpsLocationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000,
                    0,
                    gpsLocationListener);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng position = new LatLng(37.592344, 127.021056); //아두이노 고정 위치
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 18)); //아두이노가 있는 곳을 보여줌
        onAddMarker(); //지도 실행시 아두이노 마커 찍기
        userMarker(lat,lng); //지도 실행시 사용자 마커 찍기
        mMap.setOnMarkerClickListener( this );
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent(getApplicationContext() , MainActivity.class);
        startActivity(intent);
        return false;
    }

    //마커 , 반경추가
    public void onAddMarker(){
        LatLng arduino1 = new LatLng( 37.592344, 127.021056); //아두이노 위치

        MarkerOptions firemarker = new MarkerOptions()
                .position(arduino1);
        //비트맵 사용
        BitmapDrawable arduino =(BitmapDrawable)getResources().getDrawable(R.drawable.building_fire);
        Bitmap b = arduino.getBitmap();
        Bitmap resize = Bitmap.createScaledBitmap(b, 200, 200, true);
        firemarker.icon(BitmapDescriptorFactory.fromBitmap(resize));
        //아두이노 위치로부터 반경 100m이내 반경원 그리기
        CircleOptions firecircle = new CircleOptions()
                .center(arduino1)
                .radius(100)
                .strokeWidth(0f)
                .fillColor(Color.parseColor("#4DFFA500"));

        mMap.addCircle(firecircle); //반경원
        mMap.addMarker(firemarker); //화재 발생 위치
    }


    public void userMarker(double lat, double lng) {
        LatLng  myLocation = new LatLng(lat, lng);
        MarkerOptions myMarker = new MarkerOptions()
                .position(myLocation);
        BitmapDrawable myloc
                =(BitmapDrawable)getResources().getDrawable(R.drawable.placeholder);
        Bitmap c = myloc.getBitmap();
        mMap.addMarker(myMarker);
    }
}