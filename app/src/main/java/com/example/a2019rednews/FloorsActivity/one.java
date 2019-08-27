package com.example.a2019rednews.FloorsActivity;

import androidx.appcompat.app.AppCompatActivity;
import uk.co.senab.photoview.PhotoViewAttacher;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.a2019rednews.PopupActivity;
import com.example.a2019rednews.R;

public class one extends AppCompatActivity {
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_one );
        txtResult = (TextView)findViewById(R.id.txtResult);

}

    //버튼
    public void mOnPopupClick(View v){
        //데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, PopupActivity.class);
        intent.putExtra("data", "1. 소화기의 안전핀을 뽑는다.\n" +
                "2. 바람을 등지고 소화기 호스를 불쪽으로 향하게 한다.\n" +
                "3. 소화기 손잡이를 힘껏 쥐고 약제를 뿌린다.");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                //데이터 받기
                String result = data.getStringExtra("result");
                txtResult.setText(result);
            }
        }
    }
}
