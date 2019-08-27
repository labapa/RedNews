package com.example.a2019rednews.ExtendActivity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.example.a2019rednews.R;
import com.example.a2019rednews.bluetoothservice;

import uk.co.senab.photoview.PhotoViewAttacher;

public class sj2Activity extends AppCompatActivity {
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
        setContentView(R.layout.activity_sj2);
        ImageView m_imageview = (ImageView) findViewById(R.id.two);
        PhotoViewAttacher mAttacher = new PhotoViewAttacher(m_imageview);

        Intent intent = new Intent(
                getApplicationContext(),
                bluetoothservice.class); // 이동할 서비스 객체
        bindService(intent, conn, Context.BIND_AUTO_CREATE);

        final ImageButton x1 = (ImageButton) findViewById(R.id.prohibition1);
        final ImageButton x2 = (ImageButton) findViewById(R.id.prohibition2);
        final ImageButton x3 = (ImageButton) findViewById(R.id.prohibition3);
        final ImageButton ud = (ImageButton) findViewById(R.id.update);

        //실시간으로 가스1,2비교값을 받아옴
        ud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int gas = bs.getGas();
                if (gas==1) {
                    x1.setVisibility(View.VISIBLE);
                }
                if (gas==2) {
                    x2.setVisibility(View.VISIBLE);
                    x3.setVisibility(View.VISIBLE);
                }
            }

        });
    }
}
