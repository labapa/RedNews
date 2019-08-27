package com.example.a2019rednews.FloorsActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.a2019rednews.PopupActivity;
import com.example.a2019rednews.R;
import com.example.a2019rednews.ExtendActivity.sj1Activity;
import com.example.a2019rednews.ExtendActivity.sj2Activity;
import com.example.a2019rednews.ExtendActivity.sj3Activity;
import com.example.a2019rednews.ExtendActivity.sj4Activity;
import com.example.a2019rednews.ExtendActivity.sj5Activity;
import com.example.a2019rednews.ExtendActivity.sj6Activity;

public class three extends AppCompatActivity {

    TextView txtResult;
    TextView click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        txtResult = (TextView) findViewById(R.id.txtResult);
        click = (TextView) findViewById(R.id.click);
        click.bringToFront();
    }

    public void buttonClick(View v) {
        final Button sj1Button = (Button) findViewById(R.id.sj1);
        final Button sj2Button = (Button) findViewById(R.id.sj2);
        final Button sj3Button = (Button) findViewById(R.id.sj3);
        final Button sj4Button = (Button) findViewById(R.id.sj4);
        final Button sj5Button = (Button) findViewById(R.id.sj5);
        final Button sj6Button = (Button) findViewById(R.id.sj6);
        final Button sj1NButton = (Button) findViewById(R.id.sj1Next);
        final Button sj2NButton = (Button) findViewById(R.id.sj2Next);
        final Button sj3NButton = (Button) findViewById(R.id.sj3Next);
        final Button sj4NButton = (Button) findViewById(R.id.sj4Next);
        final Button sj5NButton = (Button) findViewById(R.id.sj5Next);
        final Button sj6NButton = (Button) findViewById(R.id.sj6Next);

        final ImageView[] exitArray = {
                (ImageView) findViewById(R.id.exit1), (ImageView) findViewById(R.id.exit2),
                (ImageView) findViewById(R.id.exit3), (ImageView) findViewById(R.id.exit4),
                (ImageView) findViewById(R.id.exit5), (ImageView) findViewById(R.id.exit6),
                (ImageView) findViewById(R.id.exit7)
        };

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                //클릭한 구역만 표시되도록
                if (v.getId() == R.id.sj1) {
                    sj1Button.setBackgroundColor(Color.parseColor("#4DFFA500"));
                    //클릭한 구역만 표시되도록 함
                    sj1NButton.setVisibility(View.VISIBLE);
                    //클릭한 구역에 맞는 출구 내보냄
                    exitArray[0].setVisibility(View.VISIBLE);
                    exitArray[1].setVisibility(View.VISIBLE);

                    sj1NButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            //sjNButton을 클릭하면 sjActivity 클래스로 넘어가는 intent가 실행
                            Intent intent1 = new Intent(getApplicationContext(), sj1Activity.class);
                            startActivity(intent1);
                        }
                    });

                } else { //구역이 클릭되지 않았을 때
                    sj1Button.setBackgroundColor(Color.parseColor("#00ff0000"));
                    sj1NButton.setVisibility(View.INVISIBLE); //"대피경로 보기"버튼이 출력되지 않도록
                }

                if (v.getId() == R.id.sj2) {
                    sj2Button.setBackgroundColor(Color.parseColor("#4DFFA500"));
                    sj2NButton.setVisibility(View.VISIBLE);
                    exitArray[2].setVisibility(View.VISIBLE);
                    exitArray[3].setVisibility(View.VISIBLE);
                    exitArray[4].setVisibility(View.VISIBLE);

                    sj2NButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent1 = new Intent(getApplicationContext(), sj2Activity.class);
                            startActivity(intent1);
                        }
                    });

                } else {
                    sj2Button.setBackgroundColor(Color.parseColor("#00ff0000"));
                    sj2NButton.setVisibility(View.INVISIBLE);
                }

                if (v.getId() == R.id.sj3) {
                    sj3Button.setBackgroundColor(Color.parseColor("#4DFFA500"));
                    sj3NButton.setVisibility(View.VISIBLE);
                    exitArray[2].setVisibility(View.VISIBLE);
                    exitArray[3].setVisibility(View.VISIBLE);
                    exitArray[4].setVisibility(View.VISIBLE);

                    sj3NButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent1 = new Intent(getApplicationContext(), sj3Activity.class);
                            startActivity(intent1);
                        }
                    });

                } else {
                    sj3Button.setBackgroundColor(Color.parseColor("#00ff0000"));
                    sj3NButton.setVisibility(View.INVISIBLE);
                }

                if (v.getId() == R.id.sj4) {
                    sj4Button.setBackgroundColor(Color.parseColor("#4DFFA500"));
                    sj4NButton.setVisibility(View.VISIBLE);

                    sj4NButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent1 = new Intent(getApplicationContext(), sj4Activity.class);
                            startActivity(intent1);
                        }
                    });

                } else {
                    sj4Button.setBackgroundColor(Color.parseColor("#00ff0000"));
                    sj4NButton.setVisibility(View.INVISIBLE);
                }

                if (v.getId() == R.id.sj5) {
                    sj5Button.setBackgroundColor(Color.parseColor("#4DFFA500"));
                    sj5NButton.setVisibility(View.VISIBLE);

                    sj5NButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent1 = new Intent(getApplicationContext(), sj5Activity.class);
                            startActivity(intent1);
                        }
                    });

                } else {
                    sj5Button.setBackgroundColor(Color.parseColor("#00ff0000"));
                    sj5NButton.setVisibility(View.INVISIBLE);
                }

                if (v.getId() == R.id.sj6) {
                    sj6Button.setBackgroundColor(Color.parseColor("#4DFFA500"));
                    sj6NButton.setVisibility(View.VISIBLE);

                    sj6NButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent1 = new Intent(getApplicationContext(), sj6Activity.class);
                            startActivity(intent1);
                        }
                    });

                } else {
                    sj6Button.setBackgroundColor(Color.parseColor("#00ff0000"));
                    sj6NButton.setVisibility(View.INVISIBLE);
                }

                //출구 표시(구역 출력하는 버튼과 혼동되므로 출구 출력 버튼 조건문은 따로 구현)
                //클릭된 구역에 맞게 출구 지정
                switch (v.getId()) {
                    case R.id.sj1:
                        exitArray[0].setVisibility(View.VISIBLE);
                        exitArray[1].setVisibility(View.VISIBLE);

                        for (int i = 2; i <= 6; i++) {
                            exitArray[i].setVisibility(View.INVISIBLE);
                        }
                        break;

                    case R.id.sj2:
                        exitArray[2].setVisibility(View.VISIBLE);
                        exitArray[3].setVisibility(View.VISIBLE);
                        exitArray[4].setVisibility(View.VISIBLE);

                        exitArray[0].setVisibility(View.INVISIBLE);
                        exitArray[1].setVisibility(View.INVISIBLE);
                        exitArray[5].setVisibility(View.INVISIBLE);
                        exitArray[6].setVisibility(View.INVISIBLE);
                        break;

                    case R.id.sj3:
                        exitArray[2].setVisibility(View.VISIBLE);
                        exitArray[3].setVisibility(View.VISIBLE);
                        exitArray[4].setVisibility(View.VISIBLE);

                        exitArray[0].setVisibility(View.INVISIBLE);
                        exitArray[1].setVisibility(View.INVISIBLE);
                        exitArray[5].setVisibility(View.INVISIBLE);
                        exitArray[6].setVisibility(View.INVISIBLE);
                        break;

                    case R.id.sj4:
                        exitArray[5].setVisibility(View.VISIBLE);
                        exitArray[6].setVisibility(View.VISIBLE);

                        for (int i = 0; i <= 4; i++) {
                            exitArray[i].setVisibility(View.INVISIBLE);
                        }
                        break;

                    case R.id.sj5:
                        exitArray[0].setVisibility(View.VISIBLE);
                        exitArray[2].setVisibility(View.VISIBLE);
                        exitArray[4].setVisibility(View.VISIBLE);

                        for (int i = 0; i < 3; i++) {
                            exitArray[i * 2 + 1].setVisibility(View.INVISIBLE);
                        }
                        exitArray[6].setVisibility(View.INVISIBLE);
                        break;

                    case R.id.sj6:
                        for (int i = 1; i <= 3; i++) {
                            exitArray[i * 2].setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < 3; i++) {
                            exitArray[i * 2 + 1].setVisibility(View.INVISIBLE);
                        }
                        exitArray[0].setVisibility(View.INVISIBLE);
                        break;
                }
            }
        };

        //각각 버튼 클릭 됐을 때 listner실행
        sj1Button.setOnClickListener(listener);
        sj2Button.setOnClickListener(listener);
        sj3Button.setOnClickListener(listener);
        sj4Button.setOnClickListener(listener);
        sj5Button.setOnClickListener(listener);
        sj6Button.setOnClickListener(listener);
    }

    //소화기 이미지 클릭 시
    public void mOnPopupClick(View v) {
        //데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, PopupActivity.class);
        intent.putExtra("data", "1. 소화기의 안전핀을 뽑는다.\n" +
                "2. 바람을 등지고 소화기 호스를 불쪽으로 향하게 한다.\n" +
                "3. 소화기 손잡이를 힘껏 쥐고 약제를 뿌린다.");
        startActivityForResult(intent, 1);
    }

}