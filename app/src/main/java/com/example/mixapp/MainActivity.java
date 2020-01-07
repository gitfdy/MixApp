package com.example.mixapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.soloader.SoLoader;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.toRN);
        mTextView.setOnClickListener(this);
        SoLoader.init(this, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.toRN) {
            startActivity(new Intent(this, MyReactActivity.class));
        }
    }
}
