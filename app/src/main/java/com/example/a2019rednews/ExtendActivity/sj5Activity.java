package com.example.a2019rednews.ExtendActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.a2019rednews.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class sj5Activity extends AppCompatActivity {
    ImageView m_imageview;
    PhotoViewAttacher mAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sj5);
        m_imageview = (ImageView) findViewById(R.id.five);
        mAttacher = new PhotoViewAttacher(m_imageview);
    }
}
