package com.example.a2019rednews;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a2019rednews.FloorsActivity.five;
import com.example.a2019rednews.FloorsActivity.four;
import com.example.a2019rednews.FloorsActivity.one;
import com.example.a2019rednews.FloorsActivity.six;
import com.example.a2019rednews.FloorsActivity.three;
import com.example.a2019rednews.FloorsActivity.three_fire;
import com.example.a2019rednews.FloorsActivity.two;

public class MainActivity extends AppCompatActivity {

    bluetoothservice bs;
    boolean msg;
    boolean isService = false;

    ServiceConnection conn = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 서비스와 연결되었을 때 호출되는 메서드
            //서비스가 가지고 있는 바인더를 전달받는다 // 서비스에서 구체화한 메소드를 전달받을 수 있게 해준다
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
        setContentView(R.layout.activity_main);

        // 서비스 시작하기
        Intent intent = new Intent(
                getApplicationContext(),
                bluetoothservice.class); // 이동할 서비스 객체
        bindService(intent, conn, Context.BIND_AUTO_CREATE);

        Button b1 = (Button) findViewById(R.id.btn1F);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        one.class); // 다음 넘어갈 클래스 지정
                startActivity(intent); // 다음 화면으로 넘어간다
            }


        });
        Button b2 = (Button) findViewById(R.id.btn2F);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        two.class);
                startActivity(intent);
            }
        });
        Button b3 = (Button) findViewById(R.id.btn3F);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg = bs.isFireOn();
                if (msg) {
                    Intent intent = new Intent(
                            getApplicationContext(),
                            three_fire.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(
                            getApplicationContext(),
                            three.class);
                    startActivity(intent);
                }
            }

        });

        Button b4 = (Button) findViewById(R.id.btn4F);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        four.class);
                startActivity(intent);
            }
        });

        Button b5 = (Button) findViewById(R.id.btn5F);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        five.class);
                startActivity(intent);
            }
        });

        Button b6 = (Button) findViewById(R.id.btn6F);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        six.class);
                startActivity(intent);
            }
        });

        final Button btnSms = (Button) findViewById(R.id.btnSms);
        btnSms.setBackgroundColor(Color.WHITE);
        btnSms.setTextColor(Color.RED);
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSmsIntent("119");
            }
        });
    }


    public void sendSmsIntent(String number) {
        String location=" ";
        String location1 = "수정관 3층 340호";
        String location2 = "수정관 3층 323호";
        int where = bs.fireNum(); // 블루투스에서 불이 감지된 위치
        if (where == 1) { // 각 리턴 값에 따라 위치 변경
            location = location1;
        } else if (where == 2) {
            location = location2;
        }
        try {
            String sosText = "화재가 감지되었습니다."+location+"로 출동해주세요";
            Uri smsUri = Uri.parse("sms:" + number);
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
            sendIntent.putExtra("sms_body", sosText);
            startActivity(sendIntent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri imgUri = data.getData();
            }
        }
    }
}